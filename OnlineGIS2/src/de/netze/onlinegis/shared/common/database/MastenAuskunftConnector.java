package de.netze.onlinegis.shared.common.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MastenAuskunftConnector {

	private Connection connection = null;
	
	public MastenAuskunftConnector(){}
	
	public Connection getConnection() throws ClassNotFoundException, SQLException{
		Class.forName("org.postgresql.Driver");
		
		connection = DriverManager.getConnection(
				   "jdbc:postgresql://localhost:5433/MastenAuskunft","tester", "test");
		
		if (connection != null) {
			//System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
		
		return connection;
	}
	
	public void closeConnection(){try {
		this.connection.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}}
}
