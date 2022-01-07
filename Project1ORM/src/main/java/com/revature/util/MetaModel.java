package com.revature.util;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.annotations.Column;
import com.revature.annotations.TableName;
import com.revature.annotations.Id;
import com.revature.annotations.JoinColumn;

public class MetaModel<T> {

	private static final Logger log = LoggerFactory.getLogger(MetaModel.class);
	
	private Class<?> clazz;
	private PrimaryKeyField primaryKeyField;
	private List<ColumnField> columnFields;
	private List<ForeignKeyField> foreignKeyFields;

	
	
	public static MetaModel<Class<?>> of (Class<?> clazz){
		if (clazz.getAnnotation(TableName.class) == null) {
			throw new IllegalStateException("Cannot create MetaModel object! Provided class" + 
					clazz.getName() + " is not annotated with @TableName");
		}
		return new MetaModel<>(clazz);
	}
	
	public MetaModel(Class<?> clazz) {
		this.clazz = clazz;
		this.columnFields = new LinkedList<>();
		this.foreignKeyFields = new LinkedList<>();
	}
	
	public List<ColumnField> getColumns(){
		
		Field[] fields = clazz.getDeclaredFields();
		
		for (Field field : fields) {
			Column column = field.getAnnotation(Column.class);
			
			if (column != null) {
				columnFields.add(new ColumnField(field));
			}
		}
		
		if (columnFields.isEmpty()) {
			throw new RuntimeException("NO columns found in: " + clazz.getName());
		}
		
		return columnFields;
	}
	
	public PrimaryKeyField getPrimaryKey() {
		Field[] fields = clazz.getDeclaredFields();
		
		for(Field field : fields) {
			Id primaryKey = field.getAnnotation(Id.class);
			
			if (primaryKey!= null) {
				return new PrimaryKeyField(field);
			}
		}
		
		throw new RuntimeException("Did not find a field annotated with @Id in " + clazz.getName());
	}
	
	public List<ForeignKeyField> getForeignKeys(){
		Field[] fields = clazz.getDeclaredFields();
		
		for (Field field : fields) {
			JoinColumn foreignKey = field.getAnnotation(JoinColumn.class);
			
			if (foreignKey != null) {
				foreignKeyFields.add(new ForeignKeyField(field));
			}
		}
		
		if (foreignKeyFields.isEmpty()) {
			throw new RuntimeException ("No columns found in: " + clazz.getName());
		}
		return foreignKeyFields;
	}
	
	public String getSimpleClassName() {
		return clazz.getSimpleName();
	}
	
	public String getClassName() {
		return clazz.getName();
	}
}
