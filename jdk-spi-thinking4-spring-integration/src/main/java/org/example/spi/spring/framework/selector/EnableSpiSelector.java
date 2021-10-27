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
