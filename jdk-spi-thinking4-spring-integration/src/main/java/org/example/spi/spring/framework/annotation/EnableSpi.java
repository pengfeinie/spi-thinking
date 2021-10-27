package org.example.spi.spring.framework.annotation;

import org.example.SpiHandler;
import org.example.spi.spring.framework.selector.EnableSpiSelector;
import org.springframework.context.annotation.Import;
import java.lang.annotation.*;

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Import(EnableSpiSelector.class)
public @interface EnableSpi {

	Class<?> spiHandler() default SpiHandler.class;
	
    String[] basePackages() default {};
}