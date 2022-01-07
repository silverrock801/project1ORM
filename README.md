# Project 1 ORM

# Description
 This project is a basic ORM created by heavily using reflection. This will take specific annotaions on your classes and the fields of the classes and persist them onto a Postgresql database. The Annotations that are available to use are...

@Id - used to identify the field of the class that is going to be the primary key for the table being created
@Ignore - used to skip fields that you do not wish to add to the table in the database

#Requirements
 The requirements to use the ORM properly are that your the Classes that you wish to persist into the Database are named how the table will be named. The ORM will automatically name the table after the class and will turn it into lower case. So if your class is named SharkS, the table in the database will be named sharks. For the classes that you wish to make into tables, you must have a Id field and have it annotated with the @Id annotation. The final requirement for to use the ORM is to make a application.properties file in your scr/main/resources folder and have your database url, username and password written onto it.
 
