package impl;

import java.applet.Applet;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import object.Board;
import object.Cell;
import object.Facts;
import object.Virus;

//Main class

public class InvasionGame extends JApplet implements Runnable {
	//Game constants
	private int WINDOW_WIDTH = 970; //should == GAME_WIDTH + SIDEBAR_WIDTH
	private int WINDOW_HEIGHT = 800; //should == GAME_HEIGHT + OPTION_HEIGHT
	private int GAME_HEIGHT = 720;
	private int GAME_WIDTH = 720;
	private int OPTION_HEIGHT = 80;
	private int SIDEBAR_WIDTH = 250;
	
	//Class variables
	private ArrayList<Cell> cellList;
	private ArrayList<Virus> virusList;
	private Facts hivFacts;
	private String gameStatus;
	private int tCellCount;
	private int gameScore;
	private Timer gameTimer;
	private int difficultyLevel;
	
	//Screen components
    private JPanel screens; //JPanel that flips between screens
	private JPanel welcomePanel, backgroundPanel, instructionPanel, gameOverPanel; //various screens
	private CardLayout cardLayout;
    private Board gameBoard; //JPanel object
	private JPanel optionPanel;
	private JPanel sidebarPanel;
	private JLabel titleLabel, blankLabelSmall, blankLabelWide, blankLabelQuit;
	private JButton pause, quit, restart;
	
	
	public void init() {
		setVisible(true);
		setLayout(new BorderLayout());
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	    
	    //initialize panels
	    gameBoard = new Board(GAME_WIDTH, GAME_HEIGHT);
	    
	    welcomePanel = new JPanel();
	    welcomePanel.setBackground(Color.BLACK);
	    
	    backgroundPanel = new JPanel();
	    backgroundPanel.setBackground(Color.GRAY);
	    
	    instructionPanel = new JPanel();
	    instructionPanel.setBackground(Color.GREEN);
	    
	    gameOverPanel = new JPanel();
	    gameOverPanel.setBackground(Color.MAGENTA);
	    
	    /*****************************************
	     * screens variable holds the various screens that the center component flips between
	     * There are 5 screens, each JPanel objects: welcome, background, instructions, game, game over
	     * cardLayout.show(screens, "STRING") changes which JPanel is displayed
	     ****************************************/
	    
	    screens = new JPanel(new CardLayout());
	    
	    
	    screens.add(welcomePanel, "Welcome Screen");
	    screens.add(backgroundPanel, "Background");
	    screens.add(instructionPanel, "Instructions");
	    screens.add(gameBoard, "Game");
	    screens.add(gameOverPanel, "Game Over Screen");
	    
	    cardLayout = (CardLayout) screens.getLayout();
	    cardLayout.show(screens, "Background"); //this command changes what's on the screen
	    
	    //Other components of the applet
	    optionPanel = new JPanel();
	    optionPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, OPTION_HEIGHT));
	    optionPanel.setBackground(Color.BLUE);
	    
	    titleLabel = new JLabel("");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setPreferredSize(new Dimension(450,40));

	    sidebarPanel = new JPanel();
	    sidebarPanel.setPreferredSize(new Dimension(SIDEBAR_WIDTH, WINDOW_HEIGHT));
	    sidebarPanel.setBackground(Color.CYAN);
	    
	    add(optionPanel, BorderLayout.NORTH);
	    add(sidebarPanel, BorderLayout.EAST);
	    add(screens, BorderLayout.CENTER); //adding center screen to layout
	    
	    setVisible(true);
	}
		

	public void paint( Graphics g ) {
		Graphics2D g2 = (Graphics2D) g;
		
		editWelcomePanel(g2);
		editBackgroundPanel(g2);
		editInstructionPanel(g2);
		editGameOverPanel(g2);
	}
	
	public void editWelcomePanel(Graphics2D g2) {
		
	}
	
	public void editBackgroundPanel(Graphics2D g2) {
		
	}

	public void editInstructionPanel(Graphics2D g2) {
	
	}

	public void editGameOverPanel(Graphics2D g2) {
	
	}

	public void initTimer() {
		
	}
	
	//this method should be adding to the instructionPanel JPanel object
	public void displayInstructions() {
		
	}
	
	public void startGame() {
		
	}
	
	public void drawVirus() {
		
	}
	
	public void infectHIV() {
		
	}
	
	public void drawCells() {
		
	}
	
	public void getGameStatus() {
		
	}
	
	public void setGameStatus(String status) {
		
	}
	public void mouseClicked() {
		
	}
	
	public void cycle() {
		
	}
	
	public void detectInfection() {
		
	}
	
	public void calibrateDifficulty() {
		
	}
	public void introduceVirus() {
		
	}
	
	public void checkWallCollision() {
		
	}
	
	public void checkMembraneCollision() {
		
	}
	
	//obtains String from Fact object
	public void getFact() {
		
	}
	
	//this method should be editing the gameOverPanel JPanel object
	public void displayGameOverMessage() {
		
	}
	
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
		
		  
	  

}
