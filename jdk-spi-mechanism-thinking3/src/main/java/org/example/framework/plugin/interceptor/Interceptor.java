package org.example.framework.plugin.interceptor;


import org.example.framework.plugin.model.Invocation;

public interface Interceptor {

    int HIGHEST_PRECEDENCE = Integer.MIN_VALUE;

    int LOWEST_PRECEDENCE = Integer.MAX_VALUE;

    default boolean preHandle(Invocation invocation){
        return true;
    }

    default void afterCompletion(Invocation invocation){

    }

    int getOrder();


}
