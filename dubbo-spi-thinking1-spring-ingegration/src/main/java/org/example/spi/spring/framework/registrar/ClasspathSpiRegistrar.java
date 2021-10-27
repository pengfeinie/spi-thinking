package org.example.spi.spring.framework.registrar;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.common.extension.SPI;
import org.example.spi.spring.framework.annotation.EnableSpi;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ClasspathSpiRegistrar implements ImportBeanDefinitionRegistrar,BeanFactoryAware {

	public static Map<String,String> loadFactoryNames(Class<?> factoryClass) {
		String factoryClassName = factoryClass.getName();
		try {
			Enumeration<URL> urls = ClassLoader.getSystemResources("META-INF/dubbo/"+factoryClassName);
			Map<String,String> result = new HashMap<String,String>();
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
				Properties properties = PropertiesLoaderUtils.loadProperties(new UrlResource(url));
				Set<Object> set = properties.keySet();
				for (Object key : set) {
					String providerName = (String) properties.get(key);
					result.put((String)key, providerName);
				}
			}
			return result;
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		return Collections.emptyMap();
	}
	
    private DefaultListableBeanFactory beanFactory;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
    	List<String> basePackages = getBasePackages(importingClassMetadata);
    	ClassPathSpiScanner scanner = new ClassPathSpiScanner(true, SPI.class);
    	scanner.registerFilters();
    	for (String packageName : basePackages) {
    		Set<BeanDefinition> beanDefinitions = scanner.findCandidateComponents(packageName);
    		 for (BeanDefinition definition : beanDefinitions) {
    			    String spiInterfaceClassName = definition.getBeanClassName();
    			    List<Class<?>> spiInterfaceList = ClassUtils.convertClassNamesToClasses(Arrays.asList(spiInterfaceClassName));
    			    ExtensionLoader<?> extensionLoader = ExtensionLoader.getExtensionLoader(spiInterfaceList.get(0));
    			    Map<String,String> map = loadFactoryNames(spiInterfaceList.get(0));
    			    Set<String> set = map.keySet();
    			    for (String key : set) {
    			    	Object instance = extensionLoader.getExtension(key);
    			    	beanFactory.registerSingleton(key,instance);
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

