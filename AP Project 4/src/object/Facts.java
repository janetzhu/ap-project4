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

	//Science Facts
	private String science1 = "The immune system protects the body from harmful germs and diseases.";
	private String science2 = "HIV weakens the immune system, allowing germs and diseases to attack the body.";
	private String science3 = "Without treatment, HIV usually causes the disease AIDS in 2 to 10 years.";
	private String science4 = "There is currently no cure for HIV but there are drugs, which can strengthen the immune system.";
	private String science5 = "These drugs can help a person live 10 to 20 years longer.";
 
	//T-Cell Facts
	private String cell1 = "Normal t-cell level is about 700 to 1000 cells in a pea sized drop of blood.";
	private String cell2 = "HIV positive people are considered to have a normal t-cell count if the number is above 500 cells per drop.";
	private String cell3 = "You should begin treatment when your t-cell count falls below 350 cells per drop";

	// Staged Facts
	private String stage1 = "When initially infected, a person may experience flu-like sickness.";
	private String stage2 = "After the early symptoms, there is typically a long period without symptoms.";

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
