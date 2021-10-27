package org.example.spi.spring.framework.annotation;

import org.example.spi.spring.framework.processor.SpiBeanFactoryPostProcessor;
import org.springframework.context.annotation.Import;
import java.lang.annotation.*;

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Import(SpiBeanFactoryPostProcessor.class)
public @interface EnableSpi {

    String[] basePackages() default {};
}