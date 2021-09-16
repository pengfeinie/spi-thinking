package org.example;


public class App {
	
    public static void main( String[] args ) {
    	SuperLoggerConfiguration logger1 = LoggerFactory.getLogger(XMLConfiguration.class);
    	SuperLoggerConfiguration logger2 = LoggerFactory.getLogger(JsonConfiguration.class);
    	logger1.configure("default file");
    	logger2.configure("default file");
    }
}
