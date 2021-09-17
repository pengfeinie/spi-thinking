package org.example;

import java.util.List;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

public class SuperLoggerConfigurationImportSelector implements ImportSelector{

	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		List<String> list = SpringFactoriesLoader.loadFactoryNames(SuperLogger.class, SuperLogger.class.getClassLoader());
		return StringUtils.toStringArray(list);
	}

}
