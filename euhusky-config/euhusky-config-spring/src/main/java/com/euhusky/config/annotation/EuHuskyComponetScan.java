package com.euhusky.config.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
import org.springframework.context.annotation.Import;

import com.euhusky.config.parse.EuHuskyComponentScanRegistrar;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({EuHuskyComponentScanRegistrar.class})
public @interface EuHuskyComponetScan {
	String[] basePackages() default {};
}
