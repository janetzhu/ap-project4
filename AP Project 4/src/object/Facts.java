package object;

import java.io.File;
import java.io.FileNotFoundException;
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
	Scanner tipsScanner;
	Scanner factsScanner;
	
	private ArrayList<String> tips;
	private HashMap<Integer, String> tcellFacts;
	
	public Facts() {
		tips = new ArrayList<String>();
		try {
			tipsScanner = new Scanner (new File("tips.txt"));
			
			while(tipsScanner.hasNextLine())
				tips.add(tipsScanner.nextLine());
			
			factsScanner = new Scanner (new File("facts.txt"));
			
			while(factsScanner.hasNextLine()) {
				int tcellCount = Integer.parseInt(factsScanner.nextLine());
				String fact = factsScanner.nextLine();
				
				tcellFacts.put(tcellCount, fact);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error loading files");
		}
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
	public String getRandomTip() {
		int randInt = (int)(Math.random() * tips.size());
		
		return tips.get(randInt);
	}
	
	//returns a fact from the hashmap with the corresponding tcellcount
	public String getFact(int tcellCount) {
		return tcellFacts.get(tcellCount);
	}

}
