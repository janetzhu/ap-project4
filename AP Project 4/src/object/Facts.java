package object;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

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
		
		// Throw IllegalArgumentException if resource could not be located
		if (tipsLocation == null) throw new IllegalArgumentException("tipsLocation is null."
				+ "Could not locate resource for tips.txt");
		
		
		// Locate resource for facts.txt
		factsLocation = Facts.class.getResource("facts.txt");
		
		// Throw IllegalArgumentException if resource could not be located
		if (factsLocation == null) throw new IllegalArgumentException("factsLocation is null."
				+ "Could not locate resource for facts.txt");
		
	}
	
	/**
	 * Read in strings from the tips.txt and facts.txt files into the 
	 * tips ArrayList and tCellFacts Hashmap
	 */
	
	public void readFiles() {
		
		try {
			
			// Open BufferedReader to open connection to tipslocation URL and read file
			BufferedReader reader1 = new BufferedReader(new InputStreamReader(tipsLocation.openStream()));
			
			// Create local variable to parse through the file
	        String tip;
	        
	        // While there are lines in the file to read, add lines to tips ArrayList
	        while ((tip = reader1.readLine()) != null) {
	            tips.add(tip);
	        }
	      
	        // Close the BufferedReader
	        reader1.close();
	        
	        
	        // Open BufferedReader to open connection to factsLocation URL and read file
	        BufferedReader reader2 = new BufferedReader(new InputStreamReader(factsLocation.openStream()));
	        
	        // Create local variable to parse through the file
	        String fact;
	        
	        // While there are lines in the file to read: parses the int that represents
	        // the t-cell count that is associated with the line, and add line and int to 
	        // tCellFacts hashmap
	        while ((fact = reader2.readLine()) != null) {
	        	
	        	int tCellCount = Integer.parseInt(fact);
	        	fact = reader2.readLine();
	        	
	            tCellFacts.put(tCellCount, fact);
	        }
	        
	        // Close the second BufferedReader
	        reader2.close();
	        
		} catch (FileNotFoundException e) {
			
			System.out.println("Error loading files"); e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Grabs tip from the tipNo position in the tips ArrayList 
	 * @param tipNo
	 * @return The tip
	 */
	public String getTip(int tipNo) {
		return tips.get(tipNo);
	}
	
	/**
	 * Returns the number of tips in the tips ArrayList
	 * @return size of the tips ArrayList
	 */
	
	public int getNumOfTips() {
		return tips.size();
	}
	
	/**
	 * Returns a fact from the hashmap that corresponds with a certain 
	 * t-cell count level (tCellBenchmark). 
	 * @param tCellBenchmark
	 * @return the fact
	 */
	
	public String getFact(int tCellBenchmark) {
		return tCellFacts.get(tCellBenchmark);
	}

}
