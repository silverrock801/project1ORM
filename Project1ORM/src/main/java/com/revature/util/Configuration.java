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
		
		try {
			conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Properties prop = new Properties();
		
		String url = "";
		String username = "";
		String password = "";
		
		try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")){
			prop.load(input);
			
			url = prop.getProperty("url");
			username = prop.getProperty("username");
			password = prop.getProperty("password");
			
			conn = DriverManager.getConnection(url, username, password);
			
			log.info("*********************************");
			log.info("Connected to Database");
			log.info("*********************************");
			
		} catch (IOException e) {
			log.error("Something is wrong with the app.properties file");
		}catch (SQLException e) {
			log.error("SQL exception thrown - Cannot establish DB connectionn");
		}
		
		return conn;
	}
	
}
