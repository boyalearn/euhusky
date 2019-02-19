package com.euhusky.config.parse;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.euhusky.config.ServiceBean;
import com.euhusky.config.annotation.context.Service;

import static org.springframework.context.annotation.AnnotationConfigUtils.CONFIGURATION_BEAN_NAME_GENERATOR;
import static org.springframework.util.ClassUtils.resolveClassName;
import static org.springframework.core.annotation.AnnotationUtils.findAnnotation;
import static org.springframework.beans.factory.support.BeanDefinitionBuilder.rootBeanDefinition;

public class ServiceAnnotationBeanPostProcessor
		implements BeanDefinitionRegistryPostProcessor, EnvironmentAware, ResourceLoaderAware, BeanClassLoaderAware {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final Set<String> packagesToScan;

	private Environment environment;

	private ClassLoader classLoader;

	private ResourceLoader resourceLoader;
	

	public ServiceAnnotationBeanPostProcessor(Collection<String> packagesToScan) {
		this(new LinkedHashSet<String>(packagesToScan));
	}

	public ServiceAnnotationBeanPostProcessor(Set<String> packagesToScan) {
		this.packagesToScan = packagesToScan;
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;

	}

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;

	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		Set<String> resolvedPackagesToScan = resolvePackagesToScan(packagesToScan);

		if (!CollectionUtils.isEmpty(resolvedPackagesToScan)) {
			registerServiceBeans(resolvedPackagesToScan, registry);
		} else {
			if (logger.isWarnEnabled()) {
				logger.warn("packagesToScan is empty , ServiceBean registry will be ignored!");
			}
		}

	}

	private Set<String> resolvePackagesToScan(Set<String> packagesToScan) {
		Set<String> resolvedPackagesToScan = new LinkedHashSet<String>(packagesToScan.size());
		for (String packageToScan : packagesToScan) {
			if (StringUtils.hasText(packageToScan)) {
				String resolvedPackageToScan = environment.resolvePlaceholders(packageToScan.trim());
				resolvedPackagesToScan.add(resolvedPackageToScan);
			}
		}
		return resolvedPackagesToScan;
	}

	private BeanNameGenerator resolveBeanNameGenerator(BeanDefinitionRegistry registry) {

		BeanNameGenerator beanNameGenerator = null;

		if (registry instanceof SingletonBeanRegistry) {
			SingletonBeanRegistry singletonBeanRegistry = SingletonBeanRegistry.class.cast(registry);
			beanNameGenerator = (BeanNameGenerator) singletonBeanRegistry
					.getSingleton(CONFIGURATION_BEAN_NAME_GENERATOR);
		}

		if (beanNameGenerator == null) {

			if (logger.isInfoEnabled()) {

				logger.info("BeanNameGenerator bean can't be found in BeanFactory with name ["
						+ CONFIGURATION_BEAN_NAME_GENERATOR + "]");
				logger.info("BeanNameGenerator will be a instance of " + AnnotationBeanNameGenerator.class.getName()
						+ " , it maybe a potential problem on bean name generation.");
			}

			beanNameGenerator = new AnnotationBeanNameGenerator();

		}

		return beanNameGenerator;

	}

	private void registerServiceBeans(Set<String> packagesToScan, BeanDefinitionRegistry registry) {

		EuHuskyClassPathBeanDefinitionScanner scanner = new EuHuskyClassPathBeanDefinitionScanner(registry, environment,
				resourceLoader);

		BeanNameGenerator beanNameGenerator = resolveBeanNameGenerator(registry);

		scanner.setBeanNameGenerator(beanNameGenerator);

		scanner.addIncludeFilter(new AnnotationTypeFilter(Service.class));

		for (String packageToScan : packagesToScan) {

			// Registers @Service Bean first
			scanner.scan(packageToScan);

			// Finds all BeanDefinitionHolders of @Service whether
			// @ComponentScan scans or not.
			Set<BeanDefinitionHolder> beanDefinitionHolders = findServiceBeanDefinitionHolders(scanner, packageToScan,
					registry, beanNameGenerator);

			if (!CollectionUtils.isEmpty(beanDefinitionHolders)) {

				for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolders) {
					registerServiceBean(beanDefinitionHolder, registry, scanner);
				}

				if (logger.isInfoEnabled()) {
					logger.info(beanDefinitionHolders.size() + " annotated EuHusky's @Service Components { "
							+ beanDefinitionHolders + " } were scanned under package[" + packageToScan + "]");
				}

			} else {

				if (logger.isWarnEnabled()) {
					logger.warn("No Spring Bean annotating EuHusky's @Service was found under package[" + packageToScan
							+ "]");
				}

			}

		}

	}

	private void registerServiceBean(BeanDefinitionHolder beanDefinitionHolder, BeanDefinitionRegistry registry,
			EuHuskyClassPathBeanDefinitionScanner scanner) {
		Class<?> beanClass = resolveClass(beanDefinitionHolder);

		Service service = findAnnotation(beanClass, Service.class);
		String beanName=buildBeanName(beanClass,service);
		AbstractBeanDefinition serviceBeanDefinition=buildServiceBeanDefinition(service,beanClass);
		registry.registerBeanDefinition(beanName, serviceBeanDefinition);

	}
	
	private AbstractBeanDefinition buildServiceBeanDefinition(Service service, Class<?> beanClass) {
		BeanDefinitionBuilder builder = rootBeanDefinition(ServiceBean.class);
		
		addPropertyValue(builder, "service", beanClass);
		
		String value=service.value();
		if(StringUtils.hasText(value)){
			addPropertyValue(builder, "value", value);
		}
		return builder.getBeanDefinition();
	}
	private void addPropertyValue(BeanDefinitionBuilder builder, String propertyName, String beanName) {
        String resolvedBeanName = environment.resolvePlaceholders(beanName);
        builder.addPropertyValue(propertyName, resolvedBeanName);
    }
	
	private void addPropertyValue(BeanDefinitionBuilder builder, String propertyName, Object propertyValue) {
        builder.addPropertyValue(propertyName, propertyValue);
    }
	
	private String buildBeanName(Class<?> beanClass,Service service){
		return "@ServiceBean:"+beanClass.getName();
	}
	
	


	private Class<?> resolveClass(BeanDefinitionHolder beanDefinitionHolder) {

		BeanDefinition beanDefinition = beanDefinitionHolder.getBeanDefinition();

		return resolveClass(beanDefinition);

	}

	private Class<?> resolveClass(BeanDefinition beanDefinition) {

		String beanClassName = beanDefinition.getBeanClassName();

		return resolveClassName(beanClassName, classLoader);

	}

	private Set<BeanDefinitionHolder> findServiceBeanDefinitionHolders(ClassPathBeanDefinitionScanner scanner,
			String packageToScan, BeanDefinitionRegistry registry, BeanNameGenerator beanNameGenerator) {

		Set<BeanDefinition> beanDefinitions = scanner.findCandidateComponents(packageToScan);

		Set<BeanDefinitionHolder> beanDefinitionHolders = new LinkedHashSet<BeanDefinitionHolder>(
				beanDefinitions.size());

		for (BeanDefinition beanDefinition : beanDefinitions) {

			String beanName = beanNameGenerator.generateBeanName(beanDefinition, registry);
			BeanDefinitionHolder beanDefinitionHolder = new BeanDefinitionHolder(beanDefinition, beanName);
			beanDefinitionHolders.add(beanDefinitionHolder);

		}

		return beanDefinitionHolders;

	}

}
