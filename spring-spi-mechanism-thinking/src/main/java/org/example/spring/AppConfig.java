package org.example.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SuperLoggerConfigurationImportSelector.class)
public class AppConfig {

}
