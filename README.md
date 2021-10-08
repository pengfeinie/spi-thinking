## 1. Introduction

A feature for discovering and loading implementations matching a given interface: Service Provider Interface (SPI). SPI is called Service Provider Interface, which is a service discovery mechanism. The essence of SPI is to configure the fully qualified name of the interface implementation class in a file, and the service loader reads the configuration file and loads the implementation class. This can dynamically replace the implementation class for the interface at runtime. Because of this feature, we can easily provide extended functions for our programs through the SPI mechanism.

This article is mainly an introduction to features & usage, and does not involve source code analysis (the source code is very simple, I believe you will understand it at a glance)

## 2. What is the use of SPI

For example, now we have designed a brand new logging framework: **super-logger** . By default, the XML file is used as the configuration file of our log, and an interface for configuration file parsing is designed:

```
package org.example;

public  interface  SuperLogger  {

	 void  configure();
}
```

Then come to a default XML implementation:

```
package org.example;

public class XMLLogger implements SuperLogger{

	public void configure() {
		System.out.println("xml config load success");
	}
}
```

Then when we initialize and parse the logger, we only need to call this XMLLogger to parse the XML logger.

This completes a basic model, which seems to be no problem. But the scalability is not very good, because if I want to customize/extend/rewrite the parsing function, I have to redefine the entry code.

For example, if the user wants to add a yml file as a log configuration file, then only need to create a new YAMLLogger and implement SuperLogger. But... how to inject? 

If you use the SPI mechanism, this matter is very simple, and you can easily complete the expansion function of this entry.

## 2. JDK SPI

### 2.1 Terms and Definitions of Java SPI

Java SPI defines four main components.

#### 2.1.1 Service

A well-known set of programming interfaces and classes that provide access to some specific application functionality or feature.

#### 2.1.2 Service Provider Interface

An interface or abstract class that acts as a proxy or an endpoint to the service. If the service is one interface, then it is the same as a service provider interface. Service and SPI together are well-known in the Java Ecosystem as API.

#### 2.1.3 Service Provider

A specific implementation of the SPI. The Service Provider contains one or more concrete classes that implement or extend the service type.

A Service Provider is configured and identified through a provider configuration file which we put in the resource directory *META-INF/services*. The file name is the fully-qualified name of the SPI and its content is the fully-qualified name of the SPI implementation.

The Service Provider is installed in the form of extensions, a jar file which we place in the application classpath, the Java extension classpath or the user-defined classpath.

#### 2.1.4 ServiceLoader

At the heart of the SPI is the [*ServiceLoader*](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/ServiceLoader.html) class. This has the role of discovering and loading implementations lazily. It uses the context classpath to locate provider implementations and put them in an internal cache.

### 2.2 How to above scalability problem

Let's first take a look at how to use JDK's SPI mechanism to solve the above scalability problem. A SPI function is provided in the JDK, and the core class is java.util.ServiceLoader. Its function is to obtain multiple configuration implementation files under "META-INF/services/" through the class name. In order to solve the above expansion problem, now we are META-INF/services/ create one org.example.SuperLoggerConfiguration File (without suffix). There is only one line of code in the file, that is our default org.example.XMLConfiguration. (Note that multiple implementations can also be written in one file, separated by carriage return).

Then get the implementation class of our SPI mechanism configuration through ServiceLoader:

```
package org.example;

import java.util.Iterator;
import java.util.ServiceLoader;

public class App {
	
    public static void main( String[] args ) {
	ServiceLoader<SuperLogger> serviceLoader =ServiceLoader.load(SuperLogger.class);
	Iterator<SuperLogger> iterator = serviceLoader.iterator();
	while (iterator.hasNext()) {
		SuperLogger logger = iterator.next();
		logger.configure();
	}
    }
}
```

### 2.3 Integrate SPI based on JDK with spring

#### 2.3.1 BeanFactoryPostProcessor

```
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
```

#### 2.3.2 FactoryBean

```
package org.example.spi.spring.framework.factory;


import org.springframework.beans.factory.FactoryBean;

import cn.hutool.core.util.ServiceLoaderUtil;

public class SpiFactoryBean<T> implements FactoryBean<T> {
	
	private Class<? extends T> spiInterface;
	
	public T getObject() throws Exception {
		return ServiceLoaderUtil.loadFirstAvailable(spiInterface);
	}

	public Class<? extends T> getObjectType() {
		return spiInterface;
	}

	public boolean isSingleton() {
		return true;
	}

	public Class<? extends T> getLoggerInterface() {
		return spiInterface;
	}

	public void setSpiInterface(Class<? extends T> spiInterface) {
		this.spiInterface = spiInterface;
	}
}
```

```
package org.example;

import org.example.spi.spring.framework.factory.SpiFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Bean
	public SpiFactoryBean<SuperLogger> superLogger(){
		SpiFactoryBean<SuperLogger> superLogger = new SpiFactoryBean<SuperLogger>();
		superLogger.setSpiInterface(SuperLogger.class);
		return superLogger;
	}
	
	@Bean
	public SpiFactoryBean<HelloService> helloService(){
		SpiFactoryBean<HelloService> helloService = new SpiFactoryBean<HelloService>();
		helloService.setSpiInterface(HelloService.class);
		return helloService;
	}
}
```

#### 2.3.3 @Configuration + @Bean

```
package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import cn.hutool.core.util.*;

@Configuration
public class AppConfig {
	
	@Bean
	public SuperLogger superLogger(){
		SuperLogger service = ServiceLoaderUtil.loadFirstAvailable(SuperLogger.class);
		return service;
	}
	
	@Bean
	public HelloService helloService(){
		HelloService service = ServiceLoaderUtil.loadFirstAvailable(HelloService.class);
		return service;
	}	
}
```

#### 2.3.4 ImportSelector

```
package org.example.spi.spring.framework.selector;
import java.util.Arrays;
import java.util.Map;

import org.example.spi.spring.framework.annotation.EnableSpi;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class EnableSpiSelector implements ImportSelector{

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		Map<String,Object> defaultAttributes = importingClassMetadata.getAnnotationAttributes(EnableSpi.class.getName(), true);
		String spiHandler = (String) defaultAttributes.get("spiHandler");
		return Arrays.asList(spiHandler).toArray(new String[0]);
	}
}

```

#### 2.3.5 ImportBeanDefinitionRegistrar

```
package org.example.spi.spring.framework.registrar;


import org.example.spi.spring.framework.annotation.EnableSpi;
import org.example.spi.spring.framework.annotation.Spi;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.util.CollectionUtils;
import java.util.*;

public class ClasspathSpiRegistrar implements ImportBeanDefinitionRegistrar,BeanFactoryAware {


    private DefaultListableBeanFactory beanFactory;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
    	List<String> basePackages = getBasePackages(importingClassMetadata);
    	ClassPathSpiScanner scanner = new ClassPathSpiScanner(true, Spi.class);
    	scanner.registerFilters();
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
```

## 3. Dubbo SPI

## 4. Spring SPI

