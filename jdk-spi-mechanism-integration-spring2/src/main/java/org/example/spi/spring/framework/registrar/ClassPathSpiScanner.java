package org.example.spi.spring.framework.registrar;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;

import org.apache.commons.lang3.ClassUtils;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

public class ClassPathSpiScanner extends ClassPathScanningCandidateComponentProvider{

 
  private Class<? extends Annotation> annotationClass;

  private DefaultListableBeanFactory beanFactory;

  public ClassPathSpiScanner(boolean useDefaultFilters, Class<? extends Annotation> annotationClass,DefaultListableBeanFactory beanFactory) {
	super(useDefaultFilters);
	this.annotationClass = annotationClass;
	this.beanFactory = beanFactory;
  }


  /**
   * Configures parent scanner to search for the right interfaces. It can search
   * for all interfaces or just for those that extends a markerInterface or/and
   * those annotated with the annotationClass
   */
  public void registerFilters() {
    if (this.annotationClass != null) {
      addIncludeFilter(new AnnotationTypeFilter(this.annotationClass));
    }
  }

  @Override
  public Set<BeanDefinition> findCandidateComponents(String basePackage) {
	  	Set<BeanDefinition> beanDefinitions = super.findCandidateComponents(basePackage);
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
	  return beanDefinitions;
   }

@Override
  protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
    return (beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent());
  }
}
