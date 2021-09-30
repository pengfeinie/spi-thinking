package org.example;

import java.util.List;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

public class SpiAutoConfigurationImportSelector implements ImportSelector{

	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		List<String> list = SpringFactoriesLoader.loadFactoryNames(EnableSpiAutoConfiguration.class, getClass().getClassLoader());
		return StringUtils.toStringArray(list);
	}

}
