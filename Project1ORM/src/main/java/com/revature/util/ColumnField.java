package com.revature.util;

import java.lang.reflect.Field;

import com.revature.annotations.Column;

public class ColumnField {

	private Field field;
	
	public ColumnField(Field field) {
		if(field.getAnnotation(Column.class)== null){
			throw new IllegalStateException("Cannot create ColumnField Object! Provided field, "
				+ getName() + " is not annoteated with @Column");
				
		}
		this.field = field;
	}
	
	public String getName() {
		return field.getName();
	}
	
	public Class<?> getType(){
		return field.getType();
	}
	
	public String getColumnName() {
		return field.getAnnotation(Column.class).columnName();
	}
}
