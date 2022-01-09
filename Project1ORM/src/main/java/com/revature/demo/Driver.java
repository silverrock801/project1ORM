package com.revature.demo;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.persistence.Queries;
import com.revature.util.Configuration;
import com.revature.util.MetaModel;

public class Driver {

	public static void main(String[] args) throws SQLException, IllegalArgumentException, IllegalAccessException {
		
	
		Configuration cfg = new Configuration();
		
		Queries quer = new Queries();
		
		cfg.addAnnotatedClass(Pokemon.class);
		Pokemon charmander = new Pokemon();
		charmander.setDexnum(4);
		charmander.setPkname("Charmander");
		charmander.setPktype("Fire");
		charmander.setWild(true);
		
		Pokemon bulbasaur = new Pokemon();
		bulbasaur.setDexnum(1);
		bulbasaur.setPkname("Bulbasaur");
		bulbasaur.setPktype("Grass");
		bulbasaur.setWild(false);
		
		 int count = 0;
		 
		// cfg.addAnnotatedClass(Trainers.class);
		 

	 System.out.println("Final count is: " + count);
		
		
		 for (MetaModel<?> metamodels : cfg.getMetaModels()) {
			
			System.out.println("Beginning test");
			System.out.println("Creating table for " + metamodels.getSimpleClassName());
			System.out.println("***************************************");
			
			quer.checkTables(metamodels);
			
			quer.createTable(metamodels);
			
			quer.addcolumns(metamodels);
			
			quer.addrow(metamodels, charmander);
			
			quer.addrow(metamodels, bulbasaur);
			
			quer.readTable(metamodels);

			
			
			//quer.deleteTable(metamodels);
		}
		 
		 
		 
		
	}

}
