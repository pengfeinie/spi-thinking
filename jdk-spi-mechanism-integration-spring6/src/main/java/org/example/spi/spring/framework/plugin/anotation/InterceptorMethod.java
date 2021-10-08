package org.example.spi.spring.framework.plugin.anotation;


import java.lang.annotation.*;

import org.example.spi.spring.framework.plugin.interceptor.Interceptor;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InterceptorMethod {

 Class<? extends Interceptor>[] interceptorClasses() default {};
}
