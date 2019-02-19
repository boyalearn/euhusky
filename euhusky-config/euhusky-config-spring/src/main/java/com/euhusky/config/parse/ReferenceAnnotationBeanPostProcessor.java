package com.euhusky.config.parse;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import com.euhusky.config.ReferenceBean;
import com.euhusky.config.annotation.context.Reference;

import static org.springframework.core.annotation.AnnotationUtils.getAnnotation;

public class ReferenceAnnotationBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter 
        implements MergedBeanDefinitionPostProcessor,PriorityOrdered, ApplicationContextAware, BeanClassLoaderAware,DisposableBean{
	
	
	public static final String BEAN_NAME = "referenceAnnotationBeanPostProcessor";
	
	
	@SuppressWarnings("unused")
	private ClassLoader classLoader;
	
	
	private ApplicationContext context;
	
	private final ConcurrentMap<String, InjectionMetadata> injectionMetadataCache =
            new ConcurrentHashMap<String, InjectionMetadata>(256);
	
	private final ConcurrentMap<String,ReferenceBean>  referenceBeansCache=new ConcurrentHashMap<String, ReferenceBean>(256);
	
	
	private final Log logger = LogFactory.getLog(getClass());
	
	
	public ReferenceAnnotationBeanPostProcessor() {
		logger.info(ReferenceAnnotationBeanPostProcessor.class.getName()+" init.....");;
	}


	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.classLoader=classLoader;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context=applicationContext;
	}
	
	
	@Override
	public PropertyValues postProcessPropertyValues(
			PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
		 InjectionMetadata metadata = findReferenceMetadata(beanName, bean.getClass(), pvs);
	        try {
	            metadata.inject(bean, beanName, pvs);
	        } catch (BeanCreationException ex) {
	            throw ex;
	        } catch (Throwable ex) {
	            throw new BeanCreationException(beanName, "Injection of @Reference dependencies failed", ex);
	        }
	        return pvs;
	}
	
	private InjectionMetadata findReferenceMetadata(String beanName, Class<?> clazz, PropertyValues pvs) {
        // Fall back to class name as cache key, for backwards compatibility with custom callers.
        String cacheKey = (StringUtils.hasLength(beanName) ? beanName : clazz.getName());
        // Quick check on the concurrent map first, with minimal locking.
        InjectionMetadata metadata = this.injectionMetadataCache.get(cacheKey);
        if (InjectionMetadata.needsRefresh(metadata, clazz)) {
            synchronized (this.injectionMetadataCache) {
                metadata = this.injectionMetadataCache.get(cacheKey);
                if (InjectionMetadata.needsRefresh(metadata, clazz)) {
                    if (metadata != null) {
                        metadata.clear(pvs);
                    }
                    try {
                        metadata = buildReferenceMetadata(clazz);
                        this.injectionMetadataCache.put(cacheKey, metadata);
                    } catch (NoClassDefFoundError err) {
                        throw new IllegalStateException("Failed to introspect bean class [" + clazz.getName() +
                                "] for reference metadata: could not find class that it depends on", err);
                    }
                }
            }
        }
        return metadata;
    }
	
	private InjectionMetadata buildReferenceMetadata(final Class<?> beanClass) {

        final List<InjectionMetadata.InjectedElement> elements = new LinkedList<InjectionMetadata.InjectedElement>();

        elements.addAll(findFieldReferenceMetadata(beanClass));


        return new InjectionMetadata(beanClass, elements);

    }
	
	private List<InjectionMetadata.InjectedElement> findFieldReferenceMetadata(final Class<?> beanClass) {

        final List<InjectionMetadata.InjectedElement> elements = new LinkedList<InjectionMetadata.InjectedElement>();

        ReflectionUtils.doWithFields(beanClass, new ReflectionUtils.FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {

                Reference reference = getAnnotation(field, Reference.class);

                if (reference != null) {

                    if (Modifier.isStatic(field.getModifiers())) {
                        if (logger.isWarnEnabled()) {
                            logger.warn("@Reference annotation is not supported on static fields: " + field);
                        }
                        return;
                    }

                    elements.add(new ReferenceFieldElement(field, reference));
                }

            }
        });

        return elements;

    }

	@Override
	public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {

	}
	
	
	
	private class ReferenceFieldElement extends InjectionMetadata.InjectedElement {

        private final Field field;

        private final Reference reference;

        protected ReferenceFieldElement(Field field, Reference reference) {
            super(field, null);
            this.field = field;
            this.reference = reference;
        }

        @Override
        protected void inject(Object bean, String beanName, PropertyValues pvs) throws Throwable {

            Class<?> referenceClass = field.getType();

            Object referenceBean = buildReferenceBean(reference, referenceClass);

            ReflectionUtils.makeAccessible(field);

            field.set(bean, referenceBean);

        }

    }
	private Object buildReferenceBean(Reference reference, Class<?> referenceClass) throws Exception {

        String referenceBeanCacheKey = referenceClass.getName();

        ReferenceBean referenceBean = referenceBeansCache.get(referenceBeanCacheKey);

        if (referenceBean == null) {
        	referenceBean=new ReferenceBean();
        	if(ApplicationContextAware.class.isAssignableFrom(referenceBean.getClass())){
        		referenceBean.setApplicationContext(context);
        	}
        	if(referenceClass.isInterface()){
        		referenceBean.setRefClass(referenceClass);
        	}else{
        		if(reference.remote()){
        			referenceBean.setRefClass(referenceClass);
        		}else{
        		    return context.getBean(referenceClass);
        		}
        	} 	
            referenceBeansCache.putIfAbsent(referenceBeanCacheKey, referenceBean);
        }


        return referenceBean.getObject();
    }

}
