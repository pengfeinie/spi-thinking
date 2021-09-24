package org.example.spi.spring.framework.annotation;

import org.example.spi.spring.framework.registrar.ClasspathSpiRegistrar;
import org.example.spi.spring.framework.registrar.ReflectionSpiRegistrar;
import org.springframework.context.annotation.Import;
import java.lang.annotation.*;

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Import(ClasspathSpiRegistrar.class)
public @interface EnableSpi {

    String[] basePackages() default {};
}