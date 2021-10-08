package org.example.spi.spring.framework.anotation;


import org.example.spi.spring.framework.spring.registrar.SpiRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Import(SpiRegistrar.class)
public @interface EnableSpi {

    String[] basePackages() default {};
}
