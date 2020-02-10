package com.epita.project.service.transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseDAO {
	
	Statement st = null ;
	ResultSet result ;
	
	public  void createDatabase() throws SQLException, ClassNotFoundException {
		//creating Database and table 
		Connection connection = DriverManager.getConnection("jdbc:h2:~/test","sa","");
		Class.forName("org.h2.Driver");

			st = connection.createStatement();
			st.execute("CREATE TABLE IF NOT EXISTS "
					+ "Username("
					+ "id MEDIUMINT NOT NULL AUTO_INCREMENT,"
					+ "name VARCHAR(100), "
					+ ");"); 
			connection.close();
		System.out.println("DB created ");
	}

	public void insertionOfRecord(String username) throws Exception {
		Class.forName("org.h2.Driver");
		Connection connection = DriverManager.getConnection("jdbc:h2:~/test","sa","");
		st = connection.createStatement();
		st.executeUpdate("INSERT INTO Username VALUES(null,'"+username+"');");
		connection.close();
	}

	
	
	


}
