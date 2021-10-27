package org.example.spi.spring.framework.registrar;

import java.lang.annotation.Annotation;
import java.util.Set;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

public class ClassPathSpiScanner extends ClassPathScanningCandidateComponentProvider{

 
  private Class<? extends Annotation> annotationClass;

  public ClassPathSpiScanner(boolean useDefaultFilters, Class<? extends Annotation> annotationClass) {
	super(useDefaultFilters);
	this.annotationClass = annotationClass;
  }

  public void registerFilters() {
    if (this.annotationClass != null) {
      addIncludeFilter(new AnnotationTypeFilter(this.annotationClass));
    }
  }

  @Override
  public Set<BeanDefinition> findCandidateComponents(String basePackage) {
	  	return super.findCandidateComponents(basePackage);
   }

@Override
  protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
    return (beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent());
  }
}
