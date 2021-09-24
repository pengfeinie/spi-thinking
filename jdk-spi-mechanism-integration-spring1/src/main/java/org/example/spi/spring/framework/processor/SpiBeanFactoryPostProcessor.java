package org.example.spi.spring.framework.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;
import org.apache.commons.lang3.ClassUtils;
import org.example.AppConfig;
import org.example.spi.spring.framework.annotation.EnableSpi;
import org.example.spi.spring.framework.annotation.Spi;
import org.example.spi.spring.framework.scan.ClassPathSpiScanner;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.CollectionUtils;
import cn.hutool.core.annotation.AnnotationUtil;

public class SpiBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		ClassPathSpiScanner scanner = new ClassPathSpiScanner(true, Spi.class);
    	scanner.registerFilters();
    	List<String> basePackages = getBasePackages();
    	for (String packageName : basePackages) {
    		Set<BeanDefinition> beanDefinitions = scanner.findCandidateComponents(packageName);
    		 for (BeanDefinition definition : beanDefinitions) {
    			    String spiInterfaceClassName = definition.getBeanClassName();
    			    List<Class<?>> spiInterfaceList = ClassUtils.convertClassNamesToClasses(Arrays.asList(spiInterfaceClassName));
    			    ServiceLoader<?> services = ServiceLoader.load(spiInterfaceList.get(0));
    			    Iterator<?> iterator = services.iterator();
    			    while(iterator.hasNext()){
    			        Object service = iterator.next();
    			        beanFactory.registerSingleton(service.getClass().getName(),service);
    			    }
    		 }
    	}		
	}

	private List<String> getBasePackages() {
		List<String> basePackages = Arrays.asList(AnnotationUtil.getAnnotationValue(AppConfig.class, EnableSpi.class, "basePackages"));
		if (CollectionUtils.isEmpty(basePackages)) {
			basePackages = new ArrayList<>();
			basePackages.add(ClassUtils.getPackageName(AppConfig.class.getName()));
		}
		return basePackages;
	}

}
