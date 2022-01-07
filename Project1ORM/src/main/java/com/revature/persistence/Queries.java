package com.revature.persistence;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.annotations.Id;
import com.revature.annotations.Ignore;
import com.revature.annotations.JoinColumn;
import com.revature.util.ColumnField;
import com.revature.util.Configuration;
import com.revature.util.MetaModel;

public class Queries {
	
	//constants
	private static final Logger log = LoggerFactory.getLogger(Queries.class);
	
	public Configuration config = new Configuration();
	
	public void checkTables() throws SQLException {
		
		List<MetaModel<Class<?>>> metaList = config.getMetaModels();
		
		for (MetaModel<Class<?>> m : metaList) {
			
			if (!((AnnotatedElement) m).isAnnotationPresent(Ignore.class)) {
				
				int columns = 0;
				for (ColumnField field : m.getColumns()) {
					
					if (!((AnnotatedElement) field).isAnnotationPresent(Ignore.class)) {
						
						columns ++;
					}
				}
				
				//See if Table exists
				String tablecheck = "SELECT to_regclass('public." + m.getSimpleClassName().toLowerCase() + "');";
				
				Connection conn = null;
				Statement stmt = null;
				ResultSet rs = null;
				
				try {
					conn = config.getConnection();
					stmt = conn.createStatement();
					
					if (stmt != null) {
						rs = stmt.executeQuery(tablecheck);
						log.info("Table exists. Updating the table");
						//updateTable();
					}
					
					if (rs != null) {
						if (!rs.next()) {
							log.info("Table does not exist creating table...");
							createTable();
							
						}
					}
				} catch (SQLException e) {
					log.error("There was an issue checking the table. Check the connection to the database");
				}
				
			}
		}
	}
	
	
	public void createTable() {
		
		log.info("*************************************");
		log.info("CREATING TABLE");
		log.info("*************************************");
		
		List<MetaModel<Class<?>>> metaList = config.getMetaModels();
		
		for (MetaModel<Class<?>> m: metaList) {
			
			if (!((AnnotatedElement)m).isAnnotationPresent(Ignore.class)) {
				
				String tablecreate = "CREATE TABLE " + m.getSimpleClassName().toLowerCase() + "()";
				
				Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;
				
				try {
					conn = config.getConnection();
					stmt = conn.prepareStatement(tablecreate);
					
					if (stmt != null) {
						rs = stmt.executeQuery();
						log.info("Table Created Adding Rows");
						addcolumns();
					}
					
				}catch (SQLException e) {
					log.error("Unable to create the table. There was an error");
				}
			}
		}
		
		
	}
	
	public void addcolumns() {
		log.info("*************************************");
		log.info("Adding Columns to Table");
		log.info("*************************************");
		
		List<MetaModel<Class<?>>> metaList = config.getMetaModels();
		
		for (MetaModel<Class<?>> m : metaList) {
			if (!((AnnotatedElement)m).isAnnotationPresent(Ignore.class)) {
				
				for (ColumnField field : m.getColumns()) {
					
					if (!((AnnotatedElement)field).isAnnotationPresent(Ignore.class)) {
						
						log.info("Creating columns");
						
						if (((AnnotatedElement)field).isAnnotationPresent(Id.class)) {
							String columnadd = "ALTER TABLE " + m.getSimpleClassName().toLowerCase() + " ADD COLUMN "
									+ field.getColumnName() + " SERIAL PRIMARY KEY";
							Connection conn = null;
							PreparedStatement stmt = null;
							ResultSet rs = null;
							
							try {
								conn = config.getConnection();
								stmt = conn.prepareStatement(columnadd);
								
								if (stmt != null) {
									rs = stmt.executeQuery();
									log.info("Successfully added Primary Key Column");
								}
								
							}catch (SQLException e) {
								log.error("Unable to add primary key column");
							}
							
							}
						if (field.getType().equals(String.class)) {
							String columnadd = "ALTER TABLE " + m.getSimpleClassName().toLowerCase() + " ADD COLUMN"
									+ field.getColumnName() + " VARCHAR";
							Connection conn = null;
							PreparedStatement stmt = null;
							ResultSet rs = null;
							
							try {
								conn = config.getConnection();
								stmt = conn.prepareStatement(columnadd);
								
								if (stmt != null) {
									rs = stmt.executeQuery();
									log.info("Successfully added VARCHAR Column");
								}
								
							}catch (SQLException e) {
								log.error("Unable to add VARCHAR column");
							}
							
						}
						
						if (field.getType().equals(Integer.class)) {
							String columnadd = "ALTER TABLE " + m.getSimpleClassName().toLowerCase() + " ADD COLUMN"
									+ field.getColumnName() + " INTEGER";
							Connection conn = null;
							PreparedStatement stmt = null;
							ResultSet rs = null;
							
							try {
								conn = config.getConnection();
								stmt = conn.prepareStatement(columnadd);
								
								if (stmt != null) {
									rs = stmt.executeQuery();
									log.info("Successfully added INTEGER Column");
								}
								
							}catch (SQLException e) {
								log.error("Unable to add INTEGER column");
							}
						}
						
						if (field.getType().equals(Boolean.class)) {
							String columnadd = "ALTER TABLE " + m.getSimpleClassName().toLowerCase() + " ADD COLUMN"
									+ field.getColumnName() + " Boolean";
							Connection conn = null;
							PreparedStatement stmt = null;
							ResultSet rs = null;
							
							try {
								conn = config.getConnection();
								stmt = conn.prepareStatement(columnadd);
								
								if (stmt != null) {
									rs = stmt.executeQuery();
									log.info("Successfully added Boolean Column");
								}
								
							}catch (SQLException e) {
								log.error("Unable to add Boolean column");
							}
						}
						
						if (field.getType().equals(Double.class)) {
							String columnadd = "ALTER TABLE " + m.getSimpleClassName().toLowerCase() + " ADD COLUMN"
									+ field.getColumnName() + " NUMERIC";
							Connection conn = null;
							PreparedStatement stmt = null;
							ResultSet rs = null;
							
							try {
								conn = config.getConnection();
								stmt = conn.prepareStatement(columnadd);
								
								if (stmt != null) {
									rs = stmt.executeQuery();
									log.info("Successfully added NUMERIC Column");
								}
								
							}catch (SQLException e) {
								log.error("Unable to add NUMERIC column");
							}
						}
						
						/*if (((AnnotatedElement)field).isAnnotationPresent(JoinColumn.class)) {
							String columnadd = "ALTER TABLE " + m.getSimpleClassName().toLowerCase() + " ADD COLUMN "
									+ field.getColumnName() + " SERIAL PRIMARY KEY";
							Connection conn = null;
							PreparedStatement stmt = null;
							ResultSet rs = null;
							
							try {
								conn = config.getConnection();
								stmt = conn.prepareStatement(columnadd);
								
								if (stmt != null) {
									rs = stmt.executeQuery();
									log.info("Successfully added Primary Key Column");
								}
								
							}catch (SQLException e) {
								log.error("Unable to add primary key column");
							}
						}*/
						
						// Need to Figure out how to create the second table then add the foreign key into this one
						
					}
				}
			}
		}
		
	}
	
	public void readTable(Class<?> clazz) {
		log.info("*************************************");
		log.info("Reading Table");
		log.info("*************************************");
		
		if (!((AnnotatedElement)clazz).isAnnotationPresent(Ignore.class)) {
			
			String sql = "SELECT * FROM " + clazz.getSimpleName().toLowerCase();
			
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			
			try {
				conn = config.getConnection();
				stmt = conn.prepareStatement(sql);
				
				if (stmt != null) {
					rs = stmt.executeQuery();
					log.info("Table successfully read");
				}
				
			} catch (SQLException e) {
				log.error("Unable to read table");
			}
		}
		
	}
	
	public void readrow(Class<?> clazz, Object o) throws IllegalArgumentException, IllegalAccessException {
		log.info("*************************************");
		log.info("Reading Row");
		log.info("*************************************");
		
		if (!((AnnotatedElement)clazz).isAnnotationPresent(Ignore.class)) {
			
			String sql = "SELECT * FROM " + clazz.getSimpleName().toLowerCase() + " WHERE";
			
			Field[] fieldz = clazz.getFields();
			
			Object value = fieldz[1].get(o);
			
			sql += " " + fieldz[1] + " = " + value;
			
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			
			try {
				conn = config.getConnection();
				stmt = conn.prepareStatement(sql);
				
				if (stmt != null) {
					rs = stmt.executeQuery();
					log.info("Row successfully read");
				}
				
			} catch (SQLException e) {
				log.error("Unable to read row");
			}
			
		}
	}
	
	public void addrow(Class<?> clazz, Object o) throws IllegalArgumentException, IllegalAccessException {
		
		log.info("*************************************");
		log.info("Adding Row");
		log.info("*************************************");
		
		if (!((AnnotatedElement)clazz).isAnnotationPresent(Ignore.class)) {
			
			String sql = "INSERT INTO " + clazz.getSimpleName().toLowerCase() + "(";
			
			int fieldcount1 = 0;
			
			for (Field field : clazz.getFields()) {
				fieldcount1 ++;
			}
		
			int fieldcount = 0;
			
			for (Field fields : clazz.getFields()) {
				
				if(!((AnnotatedElement)fields).isAnnotationPresent(Ignore.class)){
					
					fieldcount ++;
					sql += " " + fields.getName();
					if (fieldcount1 > fieldcount) {
						sql += ",";
					}
					
				}
			}
			
			sql += ") VALUES (";
			
			int count = 0;
			
			Field[] fieldz = clazz.getFields();
			
			for (int i = 0; i < fieldz.length; i++) {
				Object value = fieldz[i].get(o);
				for (Field fields : o.getClass().getDeclaredFields()) {
					count++;
					sql += " " + value;
					if (fieldcount1 > count) {
						sql += ",";
					}
				}
			}
			
			sql += ")";
			
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			
			try {
				conn = config.getConnection();
				stmt = conn.prepareStatement(sql);
				
				if (stmt != null) {
					rs = stmt.executeQuery();
					log.info("Sucessfully added row");
				}
				
			} catch (SQLException e) {
				log.error("Unable to add row");
			}
			
		}
		
	}
	
	public void updaterow(Class<?> clazz, Object o, Field f) throws IllegalArgumentException, IllegalAccessException {
		
		log.info("*************************************");
		log.info("Updating Row");
		log.info("*************************************");
		
		if (!((AnnotatedElement)clazz).isAnnotationPresent(Ignore.class)) {
			
			String sql = "UPDATE " + clazz.getSimpleName().toLowerCase() + " SET ";
			
			Field field[] = clazz.getFields();
			
			Object value = field[1].get(o);
			
			Object changed = f.get(o);
			
			sql += f + " = " + changed + " WHERE " + field[1] + " = " + value;
			
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			
			try {
				conn = config.getConnection();
				stmt = conn.prepareStatement(sql);
				
				if (stmt != null) {
					rs = stmt.executeQuery();
					log.info("Row successfully updated");
				}
				
			} catch (SQLException e) {
				log.error("Unable to update row");
			}
				
			}
		}
	

	
	public void deleteTable(String tablename) {
		log.info("*************************************");
		log.info("REMOVING TABLE");
		log.info("*************************************");
		
		String sql = "DROP TABLE " + tablename;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = config.getConnection();
			stmt = conn.prepareStatement(sql);
			
			if (stmt != null) {
				rs = stmt.executeQuery();
				log.info("Table successfully deleted");
			}
		}catch (SQLException e) {
			log.error("There was an error with deleting the table. It might be due to a foreign key");
		}
		
	}
	
	public void deleterow(Class<?> clazz, Object o) throws IllegalArgumentException, IllegalAccessException {
		log.info("*************************************");
		log.info("REMOVING ROW");
		log.info("*************************************");
		
		String sql = "DELETE FROM " + clazz.getSimpleName().toLowerCase() + " WHERE ";
		
		Field field[] = clazz.getFields();
		
		Object value = field[1].get(o);
		
		sql += field[1] + " = " + value;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = config.getConnection();
			stmt = conn.prepareStatement(sql);
			
			if (stmt != null) {
				rs = stmt.executeQuery();
				log.info("Row successfully deleted");
			}
		}catch (SQLException e) {
			log.error("There was an error with deleting the row. It might be due to a foreign key");
		}
		
	}

}
