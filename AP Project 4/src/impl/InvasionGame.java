package impl;

import java.applet.Applet;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.EventObject;

import object.Board;
import object.Cell;
import object.Facts;
import object.Virus;

/**
 * Main class. 
 * Contains three JPanels at any one time: screens, optionPanel, and sidebarPanel.
 * gameScreens uses a CardLayout and holds the various game screens 
 * (welcome menu, instructions, game over), displaying one at a time.
 * The CardLayout allows gameScreens to be used to 'flip' between screens for the different stages of gameplay.
 * The optionPanel holds the menu bar at the top of the game.
 * The sidebarPanel is a sidebar that displays the facts to the user on the right side of the screen.
 *
 */

public class InvasionGame extends JApplet {
	/******** GAME CONSTANTS ********/
	// Heights and widths of the window and the various panels
	private int WINDOW_WIDTH = 970; //should == GAME_WIDTH + SIDEBAR_WIDTH
	private int WINDOW_HEIGHT = 800; //should == GAME_HEIGHT + OPTION_HEIGHT
	private int GAME_HEIGHT = 720;
	private int GAME_WIDTH = 720;
	private int OPTION_HEIGHT = 80;
	private int SIDEBAR_WIDTH = 250;

	/******** CLASS VARIABLES ********/
	// An ArrayList of Cell objects. Represents the body of cells.
	private ArrayList<Cell> cellList;
	// An ArrayList of Virus objects. Holds the instances of the hostile viruses that are
	// launched towards the body of cells throughout the game.
	private ArrayList<Virus> virusList;
	private Facts hivFacts;
	
	// Status of the game.
	private String gameStatus;
	// Count of the number of T-Cells. Determines the difficulty (number of clicks) of killing a virus.
	private int tCellCount;
	// User's score in the game, determined by number of viruses killed and time.
	private int gameScore;
	// Timer counting the time elapsed during the game. 
	private Timer gameTimer;
	// Difficulty of the game (number of clicks it takes to kill a virus, speed of viruses)
	private int difficultyLevel;
	
	/******** WINDOW COMPONENTS ********/
	// JPanel that holds all of the screens for the different stages of gameplay.
	// It only displays one at once, and can therefore be used to 'flip' between them.
    private JPanel gameScreens; 
    
    // Various screens, for different stages of gameplay.
	private JPanel welcomePanel, backgroundPanel, instructionPanel, gameOverPanel; 
	private CardLayout cardLayout;
    private Board gameBoard; //JPanel object
	private JPanel optionPanel;
	private JPanel sidebarPanel;
	private JLabel titleLabel, blankLabelSmall, blankLabelWide, blankLabelQuit;
	private JButton pause, quit, restart;
	
	
	public void init() {
		// Make the JApplet visible.
		setVisible(true);
		setLayout(new BorderLayout());
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        
	    // Initialize panels
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
	     * The gameScreens variable holds the various game screens (welcome menu, 
	     * instructions, game over, etc.) for the different stages of gameplay.
	     * It displays one at a time, and is therefore used to 'flip' between them.
	     * There are 5 gameScreens, each a JPanel object: welcome, background, instructions, game, game over
	     * cardLayout.show(gameScreens, "STRING") changes which JPanel is displayed
	     ****************************************/
	    
	    gameScreens = new JPanel(new CardLayout());
	    
	    // Add the 'cards' to gameScreens
	    gameScreens.add(welcomePanel, "Welcome Screen");
	    gameScreens.add(backgroundPanel, "Background");
	    gameScreens.add(instructionPanel, "Instructions");
	    gameScreens.add(gameBoard, "Game");
	    gameScreens.add(gameOverPanel, "Game Over Screen");
	    
	    cardLayout = (CardLayout) gameScreens.getLayout();
	    cardLayout.show(gameScreens, "Game"); //this command changes what's on the screen

	    
	    //Other components of the applet
	    optionPanel = new JPanel();
	    optionPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, OPTION_HEIGHT));
	    optionPanel.setBackground(Color.BLACK);
	    
	    titleLabel = new JLabel("");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setPreferredSize(new Dimension(450,40));

	    sidebarPanel = new JPanel();
	    sidebarPanel.setPreferredSize(new Dimension(SIDEBAR_WIDTH, WINDOW_HEIGHT));
	    sidebarPanel.setBackground(Color.CYAN);
	    
	    quit = new JButton("Quit");
	    quit.setPreferredSize(new Dimension(70,30));
	    quit.setBounds(10, 15, 70, 30);
	    //quit.addActionListener((ActionListener) this);
	    optionPanel.add(quit);
	    optionPanel.setVisible(true);
	    
	    restart = new JButton("Restart");
	    restart.setPreferredSize(new Dimension(70,30));
	    restart.setBounds(10, 15, 70, 30);
	    //restart.addActionListener((ActionListener) this);
	    optionPanel.add(restart);
	    optionPanel.setVisible(true);
	    
	    pause = new JButton("Pause");
	    pause.setPreferredSize(new Dimension(70,30));
	    pause.setBounds(10, 15, 70, 30);
	    //pause.addActionListener((ActionListener) this);
	    optionPanel.add(pause);
	    optionPanel.setVisible(true);
	    
	    add(optionPanel, BorderLayout.NORTH);
	    add(sidebarPanel, BorderLayout.EAST);
	    add(gameScreens, BorderLayout.CENTER); //adding center screen to layout
	    
	    setVisible(true);
	}
		
	/* Not sure we need this--i could be wrong though
	public void paint( Graphics g ) {
		Graphics2D g2 = (Graphics2D) g;
		
		editWelcomePanel(g2);
		editBackgroundPanel(g2);
		editInstructionPanel(g2);
		editGameOverPanel(g2);
	}
	*/
	
	public void editWelcomePanel(Graphics2D g2) {
		
	}
	
	public class BackgroundPanel extends JPanel implements ActionListener {
	
		private String title = "";
		private String instructions = "";
		private JButton nextButton;
		
		public BackgroundPanel(){
			
			initializeGUI();
		}
		
		private void initializeGUI(){
			
			JFrame backgroundWindow = new JFrame("Background Section");
			JButton nextButton = new JButton("Next");
			nextButton.setPreferredSize(new Dimension (100, 50));
			nextButton.setBounds(105, 110, 100, 50);
			nextButton.addActionListener(this);
			backgroundWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Container c = backgroundWindow.getContentPane();
			this.setLayout(null);
			this.add(nextButton);
			c.add(this);
			this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
			backgroundWindow.pack();
			backgroundWindow.setLocationRelativeTo(null);
			backgroundWindow.setVisible(true);
	
		}
		
		public void editBackgroundPanel(Graphics g) {
		
			Graphics2D graphicsObject = (Graphics2D) g;
			graphicsObject.setFont(new Font(title, 10, 18));
			graphicsObject.setFont(new Font(instructions, 10, 18));
			graphicsObject.setColor(Color.red);
			graphicsObject.drawString(title, 80, 20);
			graphicsObject.drawString(instructions,  75,  50);
			
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			EventObject actionEvent = null;
			Object action = actionEvent.getSource();
			
			if (action == nextButton){
				
				//new instructions window
				//dispose of background window
			}
		
		}
	
	}

	public void editInstructionPanel(Graphics2D g2) {
	
	}

	public void editGameOverPanel(Graphics2D g2) {
	
	}
	
	
	//this method should be adding to the instructionPanel JPanel object
	public void displayInstructions() {
		
	}
	
	//this method should be editing the gameOverPanel JPanel object
	public void displayGameOverMessage() {
		
	}

		  
}
