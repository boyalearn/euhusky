package com.euhusky.config.parse;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import com.euhusky.annotation.EuHuskyComponetScan;

import static org.springframework.beans.factory.support.BeanDefinitionBuilder.rootBeanDefinition;

public class EuHuskyComponentScanRegistrar implements ImportBeanDefinitionRegistrar{

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		Set<String> packagesToScan = getPackagesToScan(importingClassMetadata);
		
        registerServiceAnnotationBeanPostProcessor(packagesToScan, registry);
        registerReferenceAnnotationBeanPostProcessor(registry);
		
	}
	
	private void registerServiceAnnotationBeanPostProcessor(Set<String> packagesToScan, BeanDefinitionRegistry registry) {

        BeanDefinitionBuilder builder = rootBeanDefinition(ServiceAnnotationBeanPostProcessor.class);
        builder.addConstructorArgValue(packagesToScan);
        builder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
        BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, registry);

    }

    private void registerReferenceAnnotationBeanPostProcessor(BeanDefinitionRegistry registry) {

 
            RootBeanDefinition beanDefinition = new RootBeanDefinition(ReferenceAnnotationBeanPostProcessor.class);
            beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
            registry.registerBeanDefinition(ReferenceAnnotationBeanPostProcessor.BEAN_NAME, beanDefinition);


    }
	
	private Set<String> getPackagesToScan(AnnotationMetadata metadata){
		 AnnotationAttributes attributes = AnnotationAttributes.fromMap(
	                metadata.getAnnotationAttributes(EuHuskyComponetScan.class.getName()));
	        String[] basePackages = attributes.getStringArray("basePackages");
	
	        // Appends value array attributes
	        Set<String> packagesToScan = new LinkedHashSet<String>();
	        packagesToScan.addAll(Arrays.asList(basePackages));
	        return packagesToScan;
	}

}
