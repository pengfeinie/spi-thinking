package org.example.spi.spring.framework.factory;


import cn.hutool.core.util.ObjectUtil;

import org.apache.commons.lang3.StringUtils;
import org.example.spi.spring.framework.plugin.anotation.InterceptorMethod;
import org.example.spi.spring.framework.plugin.interceptor.Interceptor;
import org.example.spi.spring.framework.plugin.model.InterceptorChain;
import org.example.spi.spring.framework.plugin.proxy.SpiProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class SpiFactory<T> {

    private final Map<String,T> spiMap = new ConcurrentHashMap<>(1024);

    /**
     * key: intercepter className
     * value : intercepter
     */
    private final Map<String,Object> interceptorMap = new LinkedHashMap<>();

    public List<? extends T> listServices(Class<? extends T> clz) throws InstantiationException, IllegalAccessException{
        return listServicesAndPutMapIfNecessary(clz,false);
    }

    public Map<String,? extends T> getSpiMap(Class<? extends T> clz) throws InstantiationException, IllegalAccessException{
        listServicesAndPutMapIfNecessary(clz,true);
        return spiMap;
    }


    private List<? extends T> listServicesAndPutMapIfNecessary(Class<? extends T> clz,boolean isNeedPutMap) throws InstantiationException, IllegalAccessException{
        List<T> serviceList = new ArrayList<T>();
        ServiceLoader<? extends T> services = ServiceLoader.load(clz);
        Iterator<? extends T> iterator = services.iterator();
        while(iterator.hasNext()){
            T service = iterator.next();
            serviceList.add(service);
            setSevices2Map(isNeedPutMap, service);
        }
        return serviceList;
    }

    private void setSevices2Map(boolean isNeedPutMap, T service) throws InstantiationException, IllegalAccessException {
        if(isNeedPutMap){
            String serviceName = StringUtils.uncapitalize(service.getClass().getSimpleName());
            service = getProxyIfIfNecessary(service);

            spiMap.put(serviceName,service);
        }
    }

    @SuppressWarnings("unchecked")
	private T getProxyIfIfNecessary(T service) throws InstantiationException, IllegalAccessException {
        InterceptorChain interceptorChain = getInterceptorChain(service);
        if(interceptorChain.isExposeProxy()){
            Map<Class<?>, InterceptorChain> signatureMap = new HashMap<>();
            signatureMap.put(service.getClass(),interceptorChain);
            SpiProxy spiProxy = new SpiProxy(service,signatureMap);
            service = (T) Proxy.newProxyInstance(service.getClass().getClassLoader(),service.getClass().getInterfaces(),spiProxy);
        }
        return service;
    }

    private InterceptorChain getInterceptorChain(T service) throws InstantiationException, IllegalAccessException {
        InterceptorChain interceptorChain = new InterceptorChain();

        synchronized (interceptorMap){
            for (Method declaredMethod : service.getClass().getDeclaredMethods()) {
                InterceptorMethod interceptorMethod = declaredMethod.getDeclaredAnnotation(InterceptorMethod.class);
                if(ObjectUtil.isNotNull(interceptorMethod)){
                    interceptorChain.setMethod(declaredMethod);
                    interceptorChain.setExposeProxy(true);
                    Class<? extends Interceptor>[] interceptorClasses = interceptorMethod.interceptorClasses();
                    for (Class<? extends Interceptor> interceptorClass : interceptorClasses) {
                        String interceptorClassName = interceptorClass.getName();
                        Interceptor interceptor = (Interceptor) interceptorMap.get(interceptorClassName);
                        if(ObjectUtil.isNull(interceptor)){
                            interceptor = interceptorClass.newInstance();
                            interceptorMap.put(interceptorClassName,interceptor);
                        }
                        interceptorChain.addInterceptor(interceptor);
                    }
                }
            }
        }
        return interceptorChain;
    }
}
