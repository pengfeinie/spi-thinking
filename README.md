# 1. Introduction

A feature for discovering and loading implementations matching a given interface: Service Provider Interface (SPI). SPI is called Service Provider Interface, which is a service discovery mechanism. The essence of SPI is to configure the fully qualified name of the interface implementation class in a file, and the service loader reads the configuration file and loads the implementation class. This can dynamically replace the implementation class for the interface at runtime. Because of this feature, we can easily provide extended functions for our programs through the SPI mechanism.

This article is mainly an introduction to features & usage, and does not involve source code analysis (the source code is very simple, I believe you will understand it at a glance)

# 2. What is the use of SPI

For example, now we have designed a brand new logging framework: **super-logger** . By default, the XML file is used as the configuration file of our log, and an interface for configuration file parsing is designed:

```
package org.example;

public  interface  SuperLoggerConfiguration  {

	 void  configure (String configFile) ;
	 
}
```

Then come to a default XML implementation:

```
package org.example;

public  class  XMLConfiguration  implements  SuperLoggerConfiguration {
	 
	 public  void  configure (String configFile) {
    	   ......
    }
}
```

Then when we initialize and parse the configuration, we only need to call this XMLConfiguration to parse the XML configuration file.

```
package org.example;

public  class  LoggerFactory  {
	 static {
        SuperLoggerConfiguration configuration = new XMLConfiguration();
		configuration.configure(configFile);
    }
    
    public  static  getLogger (Class clazz) {
    	......
    }
}
```

This completes a basic model, which seems to be no problem. But the scalability is not very good, because if I want to customize/extend/rewrite the parsing function, I have to redefine the entry code, and the LoggerFactory has to be rewritten, which is not flexible enough and too intrusive.

For example, if the user wants to add a yml file as a log configuration file, then only need to create a new YAMLConfiguration and implement SuperLoggerConfiguration. But... how to inject? How to use the newly created YAMLConfiguration in LoggerFactory? Is it possible that even LoggerFactory has been rewritten?

If you use the SPI mechanism, this matter is very simple, and you can easily complete the expansion function of this entry.

# 2. JDK SPI

## 2.1 Terms and Definitions of Java SPI

Java SPI defines four main components.

### 2.1.1 Service

A well-known set of programming interfaces and classes that provide access to some specific application functionality or feature.

### 2.1.2 Service Provider Interface

An interface or abstract class that acts as a proxy or an endpoint to the service. If the service is one interface, then it is the same as a service provider interface. Service and SPI together are well-known in the Java Ecosystem as API.

### 2.1.3 Service Provider

A specific implementation of the SPI. The Service Provider contains one or more concrete classes that implement or extend the service type.

A Service Provider is configured and identified through a provider configuration file which we put in the resource directory *META-INF/services*. The file name is the fully-qualified name of the SPI and its content is the fully-qualified name of the SPI implementation.

The Service Provider is installed in the form of extensions, a jar file which we place in the application classpath, the Java extension classpath or the user-defined classpath.

### 2.1.4 ServiceLoader

At the heart of the SPI is the [*ServiceLoader*](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/ServiceLoader.html) class. This has the role of discovering and loading implementations lazily. It uses the context classpath to locate provider implementations and put them in an internal cache.



# 3. Dubbo SPI

# 4. Spring SPI

