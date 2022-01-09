package com.revature.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesCache {

	private static final Logger log = LoggerFactory.getLogger(PropertiesCache.class);
	
	private final Properties configProp = new Properties();
	
	PropertiesCache() {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("application.properties");
		try {
			configProp.load(in);
		} catch (IOException e) {
			log.error("There was an issue loading the application.properties file");
		}
			
	}
	
	private static class LazyHolder
	{
		private static final PropertiesCache INSTANCE = new PropertiesCache();
	}
	
	public static PropertiesCache getInstance() {
		return LazyHolder.INSTANCE;
	}
	
	public String getProperty(String key) {
		return configProp.getProperty(key);
	}
	
}
