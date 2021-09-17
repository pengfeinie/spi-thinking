package org.example;


public class App {
	
    public static void main( String[] args ) {
    	SuperLogger logger1 = LoggerFactory.getLogger(XMLLogger.class);
    	SuperLogger logger2 = LoggerFactory.getLogger(JsonLogger.class);
    	logger1.configure("default file");
    	logger2.configure("default file");
    }
}
