package com.revature.persistence;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.annotations.Column;
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
	
	public void checkTables(MetaModel<?> metamodel) throws SQLException {
		
		System.out.println(metamodel.getSimpleClassName() + " At check table");
		
				
				//See if Table exists
				String tablecheck = "SELECT to_regclass('public." + metamodel.getSimpleClassName().toLowerCase() + "');";
				
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
							createTable(metamodel);
							
						}
					}
				} catch (SQLException e) {
					log.error("There was an issue checking the table. Check the connection to the database");
				}
				
	}
	
	
	public void createTable(MetaModel<?> metamodel) {
		
		log.info("*************************************");
		log.info("CREATING TABLE");
		log.info("*************************************");
		
		System.out.println(metamodel.getSimpleClassName() + " At create table");
		
		
			
				
				String tablecreate = "CREATE TABLE " + metamodel.getSimpleClassName().toLowerCase() + "()";
				
				Connection conn = null;
				PreparedStatement stmt = null;
				
				try {
					conn = config.getConnection();
					stmt = conn.prepareStatement(tablecreate);
					
					if (stmt != null) {
						stmt.execute();
						log.info("Table Created Adding Rows");
						addcolumns(metamodel);
					}
					
				}catch (SQLException e) {
					
					log.error("Unable to create the table. There was an error");
					e.printStackTrace();
				}
		
	}
	
	public void addcolumns(MetaModel<?> metamodels) {
		log.info("*************************************");
		log.info("Adding Columns to Table");
		log.info("*************************************");
		
		System.out.println(metamodels.getSimpleClassName() + " At add column");
		
		String columnadd = "ALTER TABLE " + metamodels.getSimpleClassName().toLowerCase() + " ADD COLUMN "
				+ metamodels.getSimpleClassName().toLowerCase() + "_id" + " SERIAL PRIMARY KEY";
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
				e.printStackTrace();
			}
				
			for (ColumnField field : metamodels.getColumns()) {
	
				if (field.getType().equals(String.class)) {
					String columnadds = "ALTER TABLE " + metamodels.getSimpleClassName().toLowerCase() + " ADD COLUMN "
							+ field.getColumnName() + " VARCHAR";
					Connection connn = null;
					PreparedStatement stm = null;
					ResultSet r = null;
					
					try {
						connn = config.getConnection();
						stm = connn.prepareStatement(columnadds);
						
						if (stm != null) {
							r = stm.executeQuery();
							log.info("Successfully added VARCHAR Column");
						}
						
					}catch (SQLException e) {
						log.error("Unable to add VARCHAR column");
					}
					
				}
				
				else if (field.getType().equals(Boolean.class)) {
					String columnadds = "ALTER TABLE " + metamodels.getSimpleClassName().toLowerCase() + " ADD COLUMN "
							+ field.getColumnName() + " BOOLEAN";
					Connection connn = null;
					PreparedStatement stm = null;
					ResultSet r = null;
					
					try {
						connn = config.getConnection();
						stm = connn.prepareStatement(columnadds);
						
						if (stm != null) {
							r = stm.executeQuery();
							log.info("Successfully added BOOLEAN Column");
						}
						
					}catch (SQLException e) {
						log.error("Unable to add BOOLEAN column");
					}
					
				}
				
				
				else if (field.getType().equals(Integer.TYPE)) {
					String columnadds = "ALTER TABLE " + metamodels.getSimpleClassName().toLowerCase() + " ADD COLUMN "
							+ field.getColumnName() + " INTEGER";
					Connection connn = null;
					PreparedStatement stm = null;
					ResultSet r = null;
					
					try {
						connn = config.getConnection();
						stm = connn.prepareStatement(columnadds);
						
						if (stm != null) {
							r = stm.executeQuery();
							log.info("Successfully added INTEGER Column");
						}
						
					}catch (SQLException e) {
						log.error("Unable to add INTEGER column");
					}
					
				}
				
			
				
				
				else if (field.getType().equals(Double.TYPE)) {
					String columnadds = "ALTER TABLE " + metamodels.getSimpleClassName().toLowerCase() + " ADD COLUMN "
							+ field.getColumnName() + " NUMERIC";
					Connection connn = null;
					PreparedStatement stm = null;
					ResultSet r = null;
					
					try {
						connn = config.getConnection();
						stm = connn.prepareStatement(columnadds);
						
						if (stm != null) {
							r = stm.executeQuery();
							log.info("Successfully added NUMERIC Column");
						}
						
					}catch (SQLException e) {
						log.error("Unable to add NUMERIC column");
					}
					
				}
					
					
				}
		}
						
						
						
	
		
		
	
	public List<Objects> readTable(MetaModel<?> clazz) {
		log.info("*************************************");
		log.info("Reading Table");
		log.info("*************************************");
		
		List<Objects> fullTable = new LinkedList<Objects>();
		
		String sql = "SELECT * FROM " + clazz.getSimpleClassName().toLowerCase();
			
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
			return null;
	}
	
	public void readrow(Class<?> clazz, Object o) throws IllegalArgumentException, IllegalAccessException {
		log.info("*************************************");
		log.info("Reading Row");
		log.info("*************************************");
			
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
	
	public void addrow(MetaModel<?> metamodels, Object o) throws IllegalArgumentException, IllegalAccessException {
		
		log.info("*************************************");
		log.info("Adding Row");
		log.info("*************************************");
		
		
		String sql = "INSERT INTO " + metamodels.getSimpleClassName().toLowerCase() + "(";
		
		int fieldcount = 0;
		
		for (Field field : o.getClass().getDeclaredFields()) {
			 if (!(field.getName().equals("id"))){
				 fieldcount++;
			 }
		}
		
		int fieldcount1 = 0;
		
		for (ColumnField field : metamodels.getColumns()) {
			fieldcount1 ++;
			sql += " " + field.getColumnName();
			if (fieldcount > fieldcount1) {
				sql += ",";
				}
			if (fieldcount1 >= fieldcount) {
				break;
			}
			
		}
		sql += ") VALUES (";
		
		int count = 0;
		
		for (Field field : o.getClass().getDeclaredFields()) {
			if (!(field.getName().equals("id"))) {
				if (field.getType().equals(String.class)) {
					count ++;
					field.setAccessible(true);
					Object value = field.get(o);	
					sql += " '" + value + "'";
				} else {
				count ++;
				field.setAccessible(true);
				Object value = field.get(o);
				sql += " " + value;
				}
				if (fieldcount > count) {
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
			e.getStackTrace();
		}

			
		/*
		sql += ") VALUES (";
			
		*/
		
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
	

	
	public void deleteTable(MetaModel<?> metamodel) {
		log.info("*************************************");
		log.info("REMOVING TABLE");
		log.info("*************************************");
		
		String sql = "DROP TABLE " + metamodel.getSimpleClassName().toLowerCase();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		int rs;
		try {
			conn = config.getConnection();
			stmt = conn.prepareStatement(sql);
			
			if (stmt != null) {
				rs = stmt.executeUpdate();
				if (rs >= 0) {
					log.info("Table successfully deleted");	
				}
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
