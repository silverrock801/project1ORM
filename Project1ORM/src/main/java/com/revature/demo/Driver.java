package com.revature.demo;

import com.revature.util.Configuration;

public class Driver {

	public static void main(String[] args) {
		
		Configuration config = new Configuration();
		
		config.getConnection();
		
		System.out.println("***************************************");
		System.out.println(" END OF CONNECTION TEST ");
		System.out.println("***************************************");

	}

}
