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
import org.springframework.util.StringUtils;

import java.util.*;

public class ClasspathSpiRegistrar implements ImportBeanDefinitionRegistrar,BeanFactoryAware {


    private DefaultListableBeanFactory beanFactory;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
    	List<String> basePackages = getBasePackages(importingClassMetadata);
    	ClassPathSpiScanner scanner = new ClassPathSpiScanner(registry);
    	scanner.registerFilters();
        scanner.doScan(StringUtils.toStringArray(basePackages));
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

