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
	
	/******** METHODS ********/
	// ArrayList of strings that holds tips (facts about HIV
	// that pop up in regular intervals every few seconds)
	private ArrayList<String> tips;
	// Hashmap of tCellFacts (facts about HIV that pop up
	// at various T-cell count levels)
	private HashMap<Integer, String> tCellFacts;
	
	// Holds URLs (Uniform Resource Locator) for tips.txt and fact.txt
	URL tipsLocation;
	URL factsLocation;
	
	
	/******** METHODS ********/
	
	/**
	 * Facts constructor calls the methods that locate the tips.txt and facts.txt 
	 * resources, and reads the files, storing the strings
	 */
	
	public Facts() {
		
		// Allocate memory for tips and tCellFacts variables
		tips = new ArrayList<String>();
		tCellFacts = new HashMap<Integer,String>();
		
		// Calls the method that locates the resource locations for
		// tips.txt and facts.txt
		locateResources();
			
		// Calls the method that reads the files
		readFiles();
		
	}
	
	/**
	 * Uses the Facts class to locate tips.txt and facts.txt resource locations.
	 */
	
	public void locateResources() {
		
		// Locate resource for tips.txt
		tipsLocation = Facts.class.getResource("tips.txt");
		
		// Throw IllegalArgumentException is resource could not be located
		if (tipsLocation == null) throw new IllegalArgumentException("tipsLocation is null."
				+ "Could not locate resource for tips.txt");
		
		
		// Locate resource for facts.txt
		factsLocation = Facts.class.getResource("facts.txt");
		
		if (factsLocation == null) throw new IllegalArgumentException("factsLocation is null."
				+ "Could not locate resource for facts.txt");
		
	}
	
	/**
	 * Read in strings from the tips.txt and facts.txt files into the 
	 * tips ArrayList and tCellFacts Hashmap
	 */
	
	public void readFiles() {
		
		try {
			
			// Open BufferedReader to 
			BufferedReader reader1 = new BufferedReader(new InputStreamReader(tipsLocation.openStream()));
			
			StringBuilder tipsOut = new StringBuilder();
	        String tip;
	        while ((tip = reader1.readLine()) != null) {
	            tips.add(tip);
	        }
	        
	        System.out.println(tipsOut.toString());   //Prints the string content read from input stream
	        reader1.close();
	        
	        // Open and read facts.txt
	        BufferedReader reader2 = new BufferedReader(new InputStreamReader(factsLocation.openStream()));
	        
	        String fact;
	        while ((fact = reader2.readLine()) != null) {
	        	int tCellCount = Integer.parseInt(fact);
	        	fact = reader2.readLine();
	            
	            tCellFacts.put(tCellCount, fact);
	        }
	        reader2.close();
	        
	        reader2.close();
	        
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Error loading files"); e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
