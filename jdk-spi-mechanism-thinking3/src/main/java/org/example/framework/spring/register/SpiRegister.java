package org.example.framework.spring.register;


import cn.hutool.core.map.MapUtil;

import org.apache.commons.lang3.StringUtils;
import org.example.framework.anotation.EnableSpi;
import org.example.framework.anotation.Spi;
import org.example.framework.factory.SpiFactory;
import org.example.framework.plugin.proxy.SpiProxy;
import org.reflections.Reflections;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Proxy;
import java.util.*;

public class SpiRegister implements ImportBeanDefinitionRegistrar,BeanFactoryAware {


    private DefaultListableBeanFactory beanFactory;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        registerSingleton(importingClassMetadata);

    }

    private void registerSingleton(AnnotationMetadata importingClassMetadata) {
        List<Class<?>> spiInterfaceList = getSpiInterfaceList(importingClassMetadata);
        if (!CollectionUtils.isEmpty(spiInterfaceList)) {
    		for (Class<?> spiInterface : spiInterfaceList) {
    			Map<String, ?> spiMap = null;
    			try {
    				spiMap = new SpiFactory().getSpiMap(spiInterface);
    			} catch (InstantiationException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} catch (IllegalAccessException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
                if(MapUtil.isNotEmpty(spiMap)){
                    spiMap.forEach((beanName,bean) -> {
                        registerSpiInterfaceSingleton(spiInterface, bean);
                        beanFactory.registerSingleton(beanName,bean);
                    });
                }
    		}
        }
    }

    private void registerSpiInterfaceSingleton(Class<?> spiInterface, Object bean) {
        Spi spi = spiInterface.getAnnotation(Spi.class);
        String defalutSpiImplClassName = spi.defalutSpiImplClassName();
        if(StringUtils.isBlank(defalutSpiImplClassName)){
            defalutSpiImplClassName = spi.value();
        }

        String beanName = bean.getClass().getName();
        if(bean.toString().startsWith(SpiProxy.class.getName())){
            SpiProxy spiProxy = (SpiProxy) Proxy.getInvocationHandler(bean);
            beanName = spiProxy.getTarget().getClass().getName();
        }
        if(beanName.equals(defalutSpiImplClassName)){
            String spiInterfaceBeanName = StringUtils.uncapitalize(spiInterface.getSimpleName());
            beanFactory.registerSingleton(spiInterfaceBeanName,bean);
        }
    }
    
    private List<Class<?>> getSpiInterfaceList(AnnotationMetadata importingClassMetadata) {
        List<String> basePackages = getBasePackages(importingClassMetadata);
        List<Class<?>> spiClazzList = new ArrayList<Class<?>>();
        for (String basePackage : basePackages) {
            Reflections reflections = new Reflections(basePackage);
            Set<Class<?>> spiClasses = reflections.getTypesAnnotatedWith(Spi.class);
            if(!CollectionUtils.isEmpty(spiClasses)){
                for (Class<?> spiClass : spiClasses) {
                    if(spiClass.isInterface()){
                    	spiClazzList.add(spiClass);
                    }
                }
            }
        }
        return spiClazzList;
    }

    private List<String> getBasePackages(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> enableSpi = importingClassMetadata.getAnnotationAttributes(EnableSpi.class.getName());
        String[] spiBackagepackages = (String[]) enableSpi.get("basePackages");
        List<String> basePackages =  Arrays.asList(spiBackagepackages);
        if(CollectionUtils.isEmpty(basePackages)){
            basePackages = new ArrayList<>();
            basePackages.add(ClassUtils.getPackageName(importingClassMetadata.getClassName()));
        }
        return basePackages;
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory)beanFactory;
    }
}

