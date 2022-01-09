package com.revature.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Configuration {

	private String dbUrl;
	private String dbUsername;
	private String dbPassword;
	private static Connection conn = null;
	
	private List<MetaModel<Class<?>>> metaModelList;
	
	private static final Logger log = LoggerFactory.getLogger(Configuration.class);
	
	PropertiesCache prop = new PropertiesCache();
	
	public Configuration addAnnotatedClass(Class<?> annotatedclass) {
		
		if  (metaModelList == null) {
			metaModelList = new LinkedList<>();
		}
		
		metaModelList.add(MetaModel.of(annotatedclass));
		
		return this;
	}
	
	public List<MetaModel<Class<?>>> getMetaModels(){
		return (metaModelList == null) ? Collections.emptyList() : metaModelList;
	}
	
	public Connection getConnection () {
		
		log.info("*********************************");
		log.info("Trying to establish a connection");
		log.info("*********************************");
		
		try {
			if (conn != null && !conn.isClosed()) {
				return conn;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		String url = "";
		String username = "";
		String password = "";
		
		try{
			
			url = PropertiesCache.getInstance().getProperty("url");
			username = PropertiesCache.getInstance().getProperty("username");
			password = PropertiesCache.getInstance().getProperty("password");
			
			conn = DriverManager.getConnection(url, username, password);
			
			log.info("*********************************");
			log.info("Connected to Database");
			log.info("*********************************");
			
		} catch (SQLException e) {
			log.error("SQL exception thrown - Cannot establish DB connectionn");
		}
		
		return conn;
	}
	
}
