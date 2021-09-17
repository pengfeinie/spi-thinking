package org.example.spring;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

@Target(ElementType.TYPE)
@Retention(value=java.lang.annotation.RetentionPolicy.RUNTIME)
@Documented
@Import(SuperLoggerConfigurationImportSelector.class)
public @interface EnableHelloService {


}
