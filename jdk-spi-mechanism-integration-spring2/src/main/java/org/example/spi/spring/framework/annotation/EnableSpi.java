package org.example.spi.spring.framework.annotation;

import org.example.spi.spring.framework.registrar.SpiRegistrar;
import org.springframework.context.annotation.Import;
import java.lang.annotation.*;

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Import(SpiRegistrar.class)
public @interface EnableSpi {

    String[] basePackages() default {};
}