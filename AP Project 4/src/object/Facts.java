package object;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Facts {

	private String dbName;
	private String dbUsername;
	private String dbPassword;
	Statement statement;
	Connection myConnection;

	public Facts() {
		// TODO Auto-generated constructor stub
	}
	
	public void connectToDB(String name, String user, String pass) {
		dbName = name;
		String url = "jdbc:postgresql://localhost/" + dbName;
		dbUsername = user;
		dbPassword = pass;
		
		try {

			  Class.forName("org.postgresql.Driver");
		
			  myConnection = DriverManager.getConnection(url + "?user=" + dbUsername + "&password=" + dbPassword);	
			  
				statement = myConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		 }
		 catch(ClassNotFoundException e) {
			  e.printStackTrace();
		  }
		 catch (SQLException ex) {
			  System.out.print("SQLException1: "+ex.getMessage());
			  System.out.print("SQLState1: " + ex.getSQLState());
			  System.out.print("VendorError1: " + ex.getErrorCode());
		  }//end catch
	}
	
	public void getFactFromDB() {
		
	}

}
