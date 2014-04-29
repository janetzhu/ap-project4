package impl;

/*
 * Project 4 
 * Names: Janet Zhu, Alexander Martino, Stephanie Zhou, Michael Ng, Naveen Yarlagadda
 * E-mails: jjz9@georgetown.edu, aomartino93@gmail.com, smz25@georgetown.edu, man53@georgetown.edu, ny62@georgetown.edu
 * 
 * In accordance with the class policies and Georgetown's Honor Code, We certify that with
 * the exceptions of the lecture notes and those items noted below, we have neither given nor received, 
 * any assistance on this project.  
 *
 * Help Items: <we should note down where we got the images or any outside sources> 
 * 
 * Description: This program helps young individuals learn about HIV/AIDS through a fun interactive 
 * game. The user learns the time constraints of such a fatal disease and also pauses through certain 
 * sections of the game to learn more information about HIV/AIDS. 
 * 
 */

import java.applet.Applet;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.concurrent.CountDownLatch;

import object.Board;
import object.Cell;
import object.DisplayPanel;
import object.Facts;
import object.Virus;

/**
 * Main class. 
 * Contains two JPanels at any one time: screens and sidebarPanel.
 * gameScreens uses a CardLayout and holds the various game screens 
 * (welcome menu, instructions, game over), displaying one at a time.
 * The CardLayout allows gameScreens to be used to 'flip' between screens for the different stages of game play.
 * The optionPanel holds the menu bar at the top of the game.
 * The sidebarPanel is a side bar that displays the facts to the user on the right side of the screen.
 */

public class InvasionGame extends JApplet implements Runnable{

	/******** GAME CONSTANTS ********/
	// Heights and widths of the window and the various panels
	private int WINDOW_WIDTH = 850; //should == GAME_WIDTH + SIDEBAR_WIDTH
	private int WINDOW_HEIGHT = 650;
	private int GAME_HEIGHT = 650;
	private int GAME_WIDTH = 650;
	private int SIDEBAR_WIDTH = 200;

    public CountDownLatch latch;

	/******** WINDOW COMPONENTS ********/
	// JPanel that holds all of the screens for the different stages of game play.
	// It only displays one at once, and can therefore be used to 'flip' between them.
    private JPanel gameScreens; 
    
    // Various screens, for different stages of game play.
    private DisplayPanel welcomePanel, backgroundPanel, instructionPanel, takeawaysPanel,
    					 gameOverPanel, gameWonPanel;
    
    //CardLayout object
	private CardLayout cardLayout;
	
	//Board object
    private Board gameBoard; 
    
    //SidebarPanel object
	private SidebarPanel sidebarPanel;
	
	//JLabel objects
	private JLabel titleLabel, blankLabelSmall, blankLabelWide, blankLabelQuit;
	
	//JButton objects
	private JButton pause, quit, restart;
	
	//Current Screen variable
	private static String currentScreen;
	
	//Buffered Image objects
	private BufferedImage background, logo, background_sidebar, instructions_sidebar, logo_sidebar, what_is_hiv, instructions_img;
	public BufferedImage bodyCellImage;
	
	//Facts object
	private Facts gameFacts;

	//Boolean object to determine if game is playing
	private boolean playingGame;
	
	//Thread object
	private Thread mainThread;

	//Private Strings to hold text for background information, instructions,
	//and takeaways
	private String background_information= 
			"HIV is a virus that weakens the body's defense system (immune system)."
			+ "It destroys good helper T cells that protect the body from harmful "
			+ "infections, viruses, and diseases.\n\n"
			+ "HIV gradually reproduces itself and kills more T-cells, making the "
			+ "immune system weaker and weaker.\n\n" 
			+ "After a certain point, the good T cells can no longer fight against  " 
			+ "the HIV virus or protect the body against other diseases.\n\n" 
			+ "Now, AIDS has developed, and diseases are free to attack the body.";

	private String instructions = "1. Click on the incoming viruses to destroy them as they emerge from the top of the screen." + 
			"\n2. Stop them from infecting the good cells at the bottom of the screen." +
			"\n3. As the game moves along, the viruses become harder to destroy." + 
			"\n4. Be sure to read the facts as they appear in the sidebar for useful information about HIV." +
			"\n5. Good Luck!";
	
	private String takeaways = "Remember...prevention is the best way to avoid getting HIV/AIDS. " +
			"\n\nYou should practice the following preventive methods: " +
			"\n\n-Abstain from sex (don't have sex) " + 
			"\n\n-Only have one partner at a time " +
			"\n\n-Use a condom during sex " +
			"\n\n-Avoid blood to blood contact ";
	
	/**
	 * initUI()
	 * Sets up the game and and the initial sizes of the window, buttons, 
	 * and text area. Essentially, the structure needed for the game. 
	 * 
	 * @param none
	 * @return none 
	 * 
	 */
	public void init() {
		// Make the JApplet visible.
		setVisible(true);
		
		//Set the layout
		setLayout(new BorderLayout());
		
		//Set the size with window parameters
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

		//Call loadImages()
		loadImages();
		
		//Declare new instance of Facts()
		gameFacts = new Facts();

		//Set playingGame equal to false
		playingGame = false;
        
	    //Initialize game panels   
	    welcomePanel = new DisplayPanel("Play Game!", 0);
	    
	    sidebarPanel = new SidebarPanel();
	    
	    backgroundPanel = new DisplayPanel("Next Page", 1);
	    backgroundPanel.setBackground(Color.GRAY);

	    instructionPanel = new DisplayPanel("Start Game!", 2);
	    instructionPanel.setBackground(Color.GREEN);
	    
	    gameBoard = new Board(GAME_WIDTH, GAME_HEIGHT);
	    gameBoard.initBoard(sidebarPanel);
	    
	    gameOverPanel = new DisplayPanel("Next Page", 4);
	    gameOverPanel.setBackground(Color.GRAY);

	    gameWonPanel = new DisplayPanel("Next Page", 5);
	    gameWonPanel.setBackground(Color.GRAY);

	    takeawaysPanel = new DisplayPanel("Replay", takeaways, 6);
	    takeawaysPanel.setBackground(Color.GRAY);
	    
	    //Create new instance of CountDownLatch()
	    latch = new CountDownLatch(1);

	    /*****************************************
	     * The gameScreens variable holds the various game screens (welcome menu, 
	     * instructions, game over, etc.) for the different stages of game play.
	     * It displays one at a time, and is therefore used to 'flip' between them.
	     * There are 5 gameScreens, each a JPanel object: welcome, background, instructions, game, game over
	     * cardLayout.show(gameScreens, "STRING") changes which JPanel is displayed
	     ****************************************/

	    //Create a new instance of JPanel and CardLayout
	    gameScreens = new JPanel(new CardLayout());

	    // Add the 'cards' to gameScreens
	    gameScreens.add(welcomePanel, "Welcome Screen");
	    gameScreens.add(backgroundPanel, "Background");
	    gameScreens.add(instructionPanel, "Instructions");
	    gameScreens.add(gameBoard, "Game");
	    gameScreens.add(gameOverPanel, "Game Over");
	    gameScreens.add(gameWonPanel, "Game Won");
	    gameScreens.add(takeawaysPanel, "Takeaways");

	    //Set up card layout
	    cardLayout = (CardLayout) gameScreens.getLayout();

	    //Create a new instance of JLabel for title
	    titleLabel = new JLabel("");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setPreferredSize(new Dimension(450,40));

        //Restart and Pause Buttons
	    restart = new JButton("Restart");
	    restart.setPreferredSize(new Dimension(120,50));
	    restart = styleButton(restart);
	    
	    pause = new JButton("Pause");
	    pause.setPreferredSize(new Dimension(100,50));
	    pause = styleButton(pause);

	    //Adding sidebar to layout
	    add(sidebarPanel, BorderLayout.EAST);
	    
	    //Adding center screen to layout
	    add(gameScreens, BorderLayout.CENTER);

	    //Set visible equal to true
	    setVisible(true);

	    //Create new instance of Thread()
	    mainThread = new Thread (this);
	    
	    //Start the thread
	    mainThread.start();
	}

	/**
	 * changeDisplayPanel()
	 * Sets new display panel eaul to the current screen
	 * @param newDisplayPanel
	 */
	public static void changeDisplayPanel (String newDisplayPanel) {
		
		currentScreen = newDisplayPanel;
	}


	/**
	 *loadImages()  
	 * This method loads the pictures for the game
	 * 
	 * @param none 
	 * @return none 
	 * 
	 */
	public void loadImages() {
	    try {
			//load background image
			background = ImageIO.read(getClass().getResource("/liver_cells_bg.png"));

			//add logo image
        	logo = ImageIO.read(getClass().getResource("/aidsinvasion_logo_main.png"));
        	
        	//load sidebar background
        	background_sidebar = ImageIO.read(getClass().getResource("/liver_cells_sidebar.png"));
        	
        	//lode sidebar logo image
            logo_sidebar = ImageIO.read(getClass().getResource("/aidsinvasion_logo_sidebar.png"));
            
            //"What is HIV?" title image
            what_is_hiv = ImageIO.read(getClass().getResource("/whatishiv.png"));
            
            //instructions sidebar
            instructions_sidebar = ImageIO.read(getClass().getResource("/instructions_sidebar.png"));
            
            //Body Cell Image
            bodyCellImage = ImageIO.read(getClass().getResource("/body_cell.png"));
            

		} catch (IOException ex) {
			System.out.println("Error loading image");
		}
	}

	/**
	 * styleButton()
	 * adds color and styles to JButton elements
	 * @param button
	 * @return
	 */
	public JButton styleButton(JButton button) {
		button.setMargin(new Insets(10, 0, 0, 0));
		button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
    	Border line = new LineBorder(new Color(85,1,14));
  	  	button.setBorder(line);
  	  	button.setBackground(new Color(85,1,14));
  	  	button.setOpaque(true);
    	button.setForeground(Color.WHITE);
		return button;
	}

	//adds color and styles to JTextArea elements
	/*public JTextArea styleText(JTextArea text) {
		text.setMargin(new Insets(20, 20, 20, 20));
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		text.setEditable(false);
		text.setForeground(Color.WHITE);
		text.setBackground(new Color(0,0,0, 150));
		text.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		return text;
		
	}*/

/*
 * This class is the structure of the welcome screen where you hit start and directs you 
 * to the next page. 
 * 
 */

	/*//JPanel object that contains the logo and Start button
	public class WelcomePanel extends JPanel {
		private JButton startButton;

		public WelcomePanel() {
			setLayout(null);
			setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));	
			startButton = new JButton("START GAME!");
			startButton = styleButton(startButton);
			startButton.setBounds(325, 560, 200, 50);
			startButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent event) {
					cardLayout.show(gameScreens, "Background");
					currentScreen = "Background";
					sidebarPanel.repaint();
				}

			});
			add(startButton);
		}

		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;

	        g2.drawImage(background, 0, 0, this);
	        g2.drawImage(logo, 0, 50, this);
            
		}

	}*/


	/*
	 * This class tells the user the background and reason for playing 
	 * the game and why it is important. It is directed toward a kid audience. 
	 * 
	 */

	//JPanel that gives the user backgrond information about HIV/AIDS

	/*public class BackgroundPanel extends JPanel {
	
		private String background_information= 
				"HIV is a virus that weakens the body's defense system (immune system)."
				+ "It destroys good helper T cells that protect the body from harmful "
				+ "infections, viruses, and diseases.\n\n"
				+ "HIV gradually reproduces itself and kills more T-cells, making the "
				+ "immune system weaker and weaker.\n\n" 
				+ "After a certain point, the good T cells can no longer fight against  " 
				+ "the HIV virus or protect the body against other diseases.\n\n" 
				+ "Now, AIDS has developed, and diseases are free to attack the body.";

		private JButton nextButton;
		private JTextArea backgroundText;
		private BufferedImage HIV_image;
		private JLabel picLabel;

		public BackgroundPanel(){
			setLayout(null);
			sidebarPanel.repaint();
			initializeGUI();
		}

		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;

			g2.drawImage(background,0,0,this);	
			g2.drawImage(what_is_hiv,0,6,this);

		}

		private void initializeGUI(){
			setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));

			nextButton = new JButton("NEXT PAGE");
			nextButton = styleButton(nextButton);
			nextButton.setBounds(325, 560, 200, 50);

			backgroundText = new JTextArea(background_information,10,50);
			backgroundText.setBounds(25, 75, GAME_WIDTH-50, 300);
			backgroundText = styleText(backgroundText);

			
			try {
				HIV_image = ImageIO.read(new File("HIV_attack.jpg"));
			} catch (IOException e) {
				System.out.println("Error loading image");
			}

			picLabel = new JLabel(new ImageIcon(HIV_image));
			picLabel.setBounds(-120, 375, GAME_WIDTH-50, 250);

			nextButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent event) {
					cardLayout.show(gameScreens, "Instructions");
					currentScreen = "Instructions";
					sidebarPanel.repaint();
				}

			});
			add(backgroundText);
			add(nextButton);
			add(picLabel);

		}
		
	}*/

	/*
	 * This class encompasses the Instruction Panel which 
	 * tells the user what to do and how to play the game. 
	 */

	/*public class InstructionPanel extends JPanel {
		

		private JTextArea instructionsText;
		private BufferedImage HIV_picture;
		private JLabel pictureLabel;

		private String instructions = "1. Click on the invading viruses to destroy them as they emerge from the top of the screen." + 
		"\n2. Stop them from infecting the good cells at the bottom of the screen." +
		"\n3. As the game moves along, the viruses become harder to destroy." + 
		"\n4. Be sure to read the facts as they appear in the sidebar for useful information about HIV." +
		"\n5. Good Luck!";

		public InstructionPanel() {
			setLayout(null);

			initializeGUI();
		}

		public void initializeGUI() {
			sidebarPanel.repaint();
			setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));	

			JButton nextButton = new JButton("PLAY GAME");
			nextButton = styleButton(nextButton);
			nextButton.setBounds(325, 560, 200, 50);
			nextButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent event) {
					cardLayout.show(gameScreens, "Game");
					currentScreen = "Game";
					gameBoard.initBoard(sidebarPanel);
					sidebarPanel.repaint();
				}

			});

			instructionsText = new JTextArea(instructions,10,50);
			instructionsText.setBounds(25, 75, GAME_WIDTH-50, 300);
			instructionsText = styleText(instructionsText);
			BufferedImage HIV_picture = null;
			try {
				HIV_picture = ImageIO.read(new File("HIV_invasion.jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			pictureLabel = new JLabel(new ImageIcon(HIV_picture));
			pictureLabel.setBounds(-75, 375, GAME_WIDTH-50, 300);
			
			add(instructionsText);
			add(nextButton);
			add(pictureLabel);
		}

		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;

			g2.drawImage(background,0,0,this);
			g2.drawImage(instructions_img,0,6,this);
		}

	}*/


	/**
	 * SidebarPanel Class
	 * Sets up sidebar with a JPanel implementation
	 * @author michaelng
	 *
	 */
	public class SidebarPanel extends JPanel {
		private boolean dimmed, inGame;

		/****JTextPane Variables ****/
		// Variables to hold the text and scroll pane objects
		private JTextPane sidebarTextPane;
		private JScrollPane scrollPane;

		// Interface for a generic styled document. 
		// Handles styling for JTextPane text.
		StyledDocument doc;

		// ArrayList that holds all of the text that will be added to the sidebar.
		// New text can be pushed to the ArrayList.
		ArrayList<String> sidebarText = new ArrayList<String>();

		// Holds the styles for sidebar text. 2 possible styles: red or white text.
		// Colors alternate for the facts that pop up on the sidebar.
		String[] textStyles = {"red", "white"};

		// Transparent color
		SimpleAttributeSet background;

		/**
		 * SidebarPanel()
		 * Constructor
		 */
		public SidebarPanel() {
			setPreferredSize(new Dimension(SIDEBAR_WIDTH, WINDOW_HEIGHT));
			dimmed = false;


			/**** JTEXTPANE IMPLEMENTATION ****/
			// Call the createTextPane method to create text pane and add styles
			sidebarTextPane = createTextPane();
			// Set the background of the sidebar to transparent
			sidebarTextPane.setBackground(new Color(0,0,0,0));
			sidebarTextPane.setEditable(false);
			sidebarTextPane.setVisible(true);

			// Add the sidebar to a JScrollPane. Enables scrolling functionality
			// so that user can scroll back to old text.
			scrollPane = new JScrollPane(sidebarTextPane);
			// Set the scrollbar to only show up as needed, i.e. once sidebar is filled
			scrollPane.setVerticalScrollBarPolicy(
	                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setPreferredSize(new Dimension(SIDEBAR_WIDTH - 4, WINDOW_HEIGHT - 4));
			scrollPane.setMinimumSize(new Dimension(10, 10));
			scrollPane.setBackground(new Color (0,0,0,0));
			scrollPane.setBorder(null);
			scrollPane.setVisible(true);

			// Add the JScrollPane to the sidebarPanel
			add(scrollPane);
		}

		/**
		 * createTextPane()
		 * Creates JTextPane
		 * @return textPane		
		 */

		private JTextPane createTextPane() {

			// Allocates memory for new text pane
			JTextPane textPane = new JTextPane();
			textPane.setEditable(false);

			// Define a default transparent background color attribute
			Color transparentBackground = new Color (0,0,0,0);
	        background = new SimpleAttributeSet();
	        StyleConstants.setBackground(background, transparentBackground);

			// Set the paragraph attributes of StyledDocument to have transparent background
			doc = textPane.getStyledDocument();
			doc.setParagraphAttributes(0, 
		            textPane.getDocument().getLength(), background, false);
	        addStylesToDocument(doc);     

	        return textPane;

		}
		
		/**
		 * addTextToPane()
		 * Provides implementation for adding text to the sidebar
		 * @param textToAdd
		 */
		public void addTextToPane(String textToAdd) {
			sidebarText.add(textToAdd + "\n");

			 try {

				 // If the last item in the sidebar is in an even position or 0, 
				 // add text to JTextPane and style as red
				 // Else, add text to JTextPane and style as white
				 if (((sidebarText.size() - 1) % 2 == 0) || sidebarText.size() == 0) {
					 doc.insertString(doc.getLength(), sidebarText.get(sidebarText.size() -1),
	                         doc.getStyle(textStyles[0]));
				 } else {
					 doc.insertString(doc.getLength(), sidebarText.get(sidebarText.size() -1),
	                         doc.getStyle(textStyles[1]));
				 }
		        } catch (BadLocationException ble) {
		            System.err.println("Couldn't insert text into text pane.");
		        }
			 
		}

		/**
		 * addStylesToDocument()
		 * Initializes font and text size styles for game layout
		 * @param doc
		 */
		protected void addStylesToDocument(StyledDocument doc) {
			//Initialize some styles.
	        Style def = StyleContext.getDefaultStyleContext().
	                        getStyle(StyleContext.DEFAULT_STYLE);

	        Style red = doc.addStyle("red", def);
	        StyleConstants.setAlignment(red, StyleConstants.ALIGN_CENTER);
	        StyleConstants.setFontFamily(red, "Sans Serif");
	        StyleConstants.setFontSize(red, 12);
	        StyleConstants.setForeground(red, Color.RED);
	        StyleConstants.setSpaceBelow(red, 5);
	        StyleConstants.setBold(red, true);

	        Style white = doc.addStyle("white", red);
	        StyleConstants.setForeground(white, Color.WHITE);
	        
		}

		/**
		 * paintComponet()
		 */
		public void paintComponent(Graphics g) {
			//Declare graphics2d object
			Graphics2D g2 = (Graphics2D) g;

			//If current screen doesn't equal instructions
			if(!currentScreen.equals("Instructions"))
				
				//Draw the background sidebar
				g2.drawImage(background_sidebar,0,0,this);
			else {
				
				//Otherwise draw the instructions sidebar
				g2.drawImage(instructions_sidebar,0,0,this);
			}

			//If the current screen is the welcome screen
	        if(currentScreen.equals("Welcome Screen"))
	        	
	        	//Draw the logo on it
	        	g2.drawImage(logo_sidebar, 0, 50, this);

	        //If sidebar dimmed
	        if(dimmed) {
	        	
	        	//Set up the sidebar
	        	g2.setColor(new Color(0,0,0,215));
	        	g2.fillRect(0,0,SIDEBAR_WIDTH, GAME_HEIGHT);
	        }
	        
	        //If game in progress
	        if(inGame) {
	        	
	        	//Set up the sidebar
	        	g2.setColor(new Color(0,0,0,150));
	        	g2.fillRect(0,0,SIDEBAR_WIDTH, GAME_HEIGHT);
	        }
	        
		}

		/**
		 * inGame()
		 * repaint() is called if in the game
		 */
		public void inGame() {
			inGame = true;
			repaint();
		}

		/**
		 * dimSidebar()
		 * if not in game, dimmed is set to true and repaint() is called
		 */
		public void dimSidebar() {
			inGame = false;
			dimmed = true;
			repaint();
		}

		/**
		 * lightenSidebar()
		 * if not dimmed, repaint() is called
		 */
		public void lightenSidebar() {
			dimmed = false;
			repaint();
		}



	}

	/**
	 * run()
	 * run method for the invasion game
	 */
	@Override
	public void run() {
		//Show the initial welcome screen
		cardLayout.show(gameScreens, "Welcome Screen"); //this command changes what's on the screen
	    currentScreen = "Welcome Screen";

	    //Create while loop
		while (true) {
			
			//If currently playing game
			if (playingGame) {
				
				//Create try/catch block
				try {
					
					//Call await()
					latch.await();
					
					//Reset latch
					latch = new CountDownLatch(1);
					
					//Set playingGame to false
					playingGame = false;
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Else
			else {
				
				//If current screen is game
				if (currentScreen == "Game"){
					
					//Start the gameboard
					gameBoard.start(latch);
					
					//Set playingGame equal to true
					playingGame = true;
				}

				//Create try/catch blcok
				try {
					
					//Thread sleeps at 300
					mainThread.sleep(300);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				//Make cardlayout show the current screen
				cardLayout.show(gameScreens, currentScreen);

				//Repaint the sidebarPanel
				sidebarPanel.repaint();
			}
		}
		//mainThread;
	}

} //END InvasionGame
