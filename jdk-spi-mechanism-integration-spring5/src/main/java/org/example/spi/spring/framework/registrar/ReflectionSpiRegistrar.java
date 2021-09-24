package org.example.spi.spring.framework.registrar;


import org.example.spi.spring.framework.annotation.EnableSpi;
import org.example.spi.spring.framework.annotation.Spi;
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
import java.util.*;

public class ReflectionSpiRegistrar implements ImportBeanDefinitionRegistrar,BeanFactoryAware {


    private DefaultListableBeanFactory beanFactory;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        registerSingleton(importingClassMetadata);
    }

    private void registerSingleton(AnnotationMetadata importingClassMetadata) {
    	List<Class<?>> spiInterfaceList = getSpiInterfaceList(importingClassMetadata);
    	if (!CollectionUtils.isEmpty(spiInterfaceList)) {
    		for (Class<?> spiInterface : spiInterfaceList) {
    	        ServiceLoader<?> services = ServiceLoader.load(spiInterface);
    	        Iterator<?> iterator = services.iterator();
    	        while(iterator.hasNext()){
    	            Object service = iterator.next();
    	            beanFactory.registerSingleton(spiInterface.getName(),service);
    	        }
    		}
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

