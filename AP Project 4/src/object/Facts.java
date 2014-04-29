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

	
	//HIV Basics Facts
	private String basic1 = "You cannot tell if someone has HIV by looking at him or her.";
	private String basic2 = "The only way for someone to know his or her HIV status is to go for HIV testing.";
	private String basic3 = "You can get tested for HIV at hospitals and clinics.";
	private String basic4 = "HIV has no known cure, though there are medicines that help manage it.";
	private String basic5 = "You can make choices to avoid getting or spreading HIV/AIDS.";
	private String basic6 = "There are many myths about HIV/AIDS: We must learn to identify what is fact and what is nonsense.";

	//MythBusters Facts
	private String buster1 = "Knowing your HIV status can help you live a healthy life.";
	private String buster2 = "Having more than one sexual partner can put you at greater risk to having HIV.";
	private String buster3 = "According to statistics, the black population in the US is at a greater risk of contracting HIV.";
	private String buster4 = "You are more likely to get HIV if you already have an STD.";
	private String buster5 = "HIV is not the same thing as AIDS.";
	private String buster6 = "HIV is present in the blood and sexual fluids.";
	private String buster7 = "In the US, HIV is a problem for people of all races, genders, and sexual orientations.";

	//Preventive Facts
	private String prevention1 = "Abstain from sex";
	private String prevention2 = "Be faithful to your current partner";
	private String prevention3 = "Use a condom when having sex";
	private String prevention4 = "Avoid blood to blood contact";

	public Facts() {
		// TODO Auto-generated constructor stub
			
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
	
	public void getFactFromDB() {
		
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
