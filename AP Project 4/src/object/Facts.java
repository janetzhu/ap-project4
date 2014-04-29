package object;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Facts {

	private String dbName;
	private String dbUsername;
	private String dbPassword;
	Statement statement;
	Connection myConnection;
	
	private ArrayList<String> tips;
	private HashMap<Integer, String> tCellFacts;
	
	public Facts() {
			
			// Locate resource for tips.txt
			tips = new ArrayList<String>();
			URL tipsViaClass = Facts.class.getResource("tips.txt");
			if (tipsViaClass == null) {
				System.out.println("tipsViaClass is null");
			}
			
			// Locate resource
			// TODO make this a try-catch block
			tCellFacts = new HashMap<Integer,String>();
			URL factsViaClass = Facts.class.getResource("facts.txt");
			if (factsViaClass == null) {
				System.out.println("factsViaClass is null");
			}
			
			try {
				
				// Open and read tips.txt
				BufferedReader reader1 = new BufferedReader(new InputStreamReader(tipsViaClass.openStream()));
				
				StringBuilder tipsOut = new StringBuilder();
		        String tip;
		        while ((tip = reader1.readLine()) != null) {
		            tips.add(tip);
		        }
		        
		        System.out.println(tipsOut.toString());   //Prints the string content read from input stream
		        reader1.close();
		        
		        // Open and read facts.txt
		        BufferedReader reader2 = new BufferedReader(new InputStreamReader(factsViaClass.openStream()));
		        
		        StringBuilder factsOut = new StringBuilder();
		        String fact;
		        while ((fact = reader2.readLine()) != null) {
		        	int tCellCount = Integer.parseInt(fact);
		        	fact = reader2.readLine();
		            
		            tCellFacts.put(tCellCount, fact);
		        }
		        
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("Error loading files"); e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		//} 
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

	public void getTipFromDB() {

	}

	public void getFactFromDB() {

	}

	
	//grabs a random tip from the tips arraylist
	public String getTip(int tipNo) {
		return tips.get(tipNo);
	}
	
	public int getNumOfTips() {
		return tips.size();
	}
	
	//returns a fact from the hashmap with the corresponding tcellcount
	public String getFact(int tCellBenchmark) {
		return tCellFacts.get(tCellBenchmark);
	}

}
