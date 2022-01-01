package com.revature.util;

import java.util.LinkedList;
import java.util.List;
import java.sql.Connection;
import java.util.Collections;

public class Configuration {

	private String dbUrl;
	private String dbUsername;
	private String dbPassword;
	
	private List<MetaModel<Class<?>>> metaModelList;
	
	public Configuration addAnnotatedClass(Class annotatedclass) {
		
		if  (metaModelList == null) {
			metaModelList = new LinkedList<>();
		}
		
		metaModelList.add(MetaModel.of(annotatedclass));
		
		return this;
	}
	
	public List<MetaModel<Class<?>>> getMetaModels(){
		return (metaModelList == null) ? Collections.emptyList() : metaModelList;
	}
	
	public Connection getConnection (String dbUrl, String dbUsername, String dbPassword) {
		
		this.dbUrl = dbUrl;
		this.dbUsername = dbUsername;
		this.dbPassword = dbPassword;
		
		return null;
	}
	
}
