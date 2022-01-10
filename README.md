# Project 1 ORM

# Description
 This project is a basic ORM created by heavily using reflection. This will take specific annotaions on your classes and the fields of the classes and persist them onto a Postgresql database. The Annotations that are available to use are...

# Annotations

@Id - used to identify the field of the class that is going to be the primary key for the table being created

@Column - is used to identify what parts of a class will be columns for the table

@TableName - is used to annotate the class in order to let the ORM know what it will be using as the table name



# Requirements
 The requirements to use the ORM properly are that your the Classes that you wish to persist into the Database are named how the table will be named. The ORM will automatically name the table after the class and will turn it into lower case. So if your class is named SharkS, the table in the database will be named sharks. For the classes that you wish to make into tables, you must have a Id field and have it annotated with the @Id annotation. In order to start to have the ORM begin to create the tables, you must first put the classes into a list of metamodels (this is already created so you just need to put them into the list), and then run the initialize method. This will then begin the process of the table creation. The final requirement for to use the ORM is to make a application.properties file in your scr/main/resources folder and have your database url, username and password written onto it. You also have to clone the repository and have it stored locally into your repository. The dependencies you need to implement into your pom.xml file can be found below.
 
 		<dependency>
			<groupId>com.revature</groupId>
			<artifactId>Project1ORM</artifactId>
			<version>0.0.1-SNAPSHOT</version>
		</dependency>
 
 
 
 # Available methods
 
  - checkTables(MetaModel<?> metamodel) - Goes through metamodel list and checks to see if the tables for them exist. If not then it calls the create method.
  - createTable(MetaModel<?> metamodel) - Is called by the checkTables(), will go through and create the table for the class, then call the addcolumns() to finish creating the table
  - addcolumns(MetaModel<?> metamodels, Object o)- adds columns to the tables. Is called from the createTable()
  - readTable(MetaModel<?> clazz) - reads out what is in a specific table
  - readrow(Class<?>, Object o) - reads out a specific row for a table
  - addrow(Class<?> c, Object o); - persists a new object into a table
  - updaterow(Clas<?> c, Object o, Field f) - updates an object in the table
  - deleteTable(String tablename) - deletes a specifc table
  - deleterow(Class<?> c, Object o) - deletes a specific entry in a specific table
  - MetaModel.of(Class<?> c) - used to retrieve the metamodel of a specific class.

*Please note the in order to call the CRUD methods you do need an instance of the Queries class*
 
  Along with the methods that allow you to create the tables for your classes, you have the standard CRUD methods for use as well. These consist of Read, Update and Delete both the table of a specified class as well as a specific row for a table. For using CRUD methods on the tables themselves, it requires you to pass the respective class as an argument, while the rows require you to pass both the class for its respective table as well as the object you wish to perform the CRUD method on. 

 
