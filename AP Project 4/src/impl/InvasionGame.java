package impl;

import java.applet.Applet;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
	private int WINDOW_WIDTH = 850; //should == GAME_WIDTH + SIDEBAR_WIDTH
	private int WINDOW_HEIGHT = 720; //should == GAME_HEIGHT + OPTION_HEIGHT
	private int GAME_HEIGHT = 650;
	private int GAME_WIDTH = 650;
	private int OPTION_HEIGHT = 70;
	private int SIDEBAR_WIDTH = 200;
	
	/******** WINDOW COMPONENTS ********/
	// JPanel that holds all of the screens for the different stages of gameplay.
	// It only displays one at once, and can therefore be used to 'flip' between them.
    private JPanel gameScreens; 
    
    // Various screens, for different stages of gameplay.
	private WelcomePanel welcomePanel;
	private BackgroundPanel backgroundPanel;
	private InstructionPanel instructionPanel;
	private GameOverPanel gameOverPanel;
    //private JPanel welcomePanel, backgroundPanel, instructionPanel, gameOverPanel; 
	private CardLayout cardLayout;
    private Board gameBoard; //JPanel object
	private JPanel optionPanel;
	private SidebarPanel sidebarPanel;
	private JLabel titleLabel, blankLabelSmall, blankLabelWide, blankLabelQuit;
	private JButton pause, quit, restart;
	private String currentScreen;
	
	
	public void init() {
		// Make the JApplet visible.
		setVisible(true);
		setLayout(new BorderLayout());
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        
	    // Initialize panels
	    gameBoard = new Board(GAME_WIDTH, GAME_HEIGHT);
	    
	    welcomePanel = new WelcomePanel();
	    sidebarPanel = new SidebarPanel();
	    
	    backgroundPanel = new BackgroundPanel();
	    backgroundPanel.setBackground(Color.GRAY);
	    
	    instructionPanel = new InstructionPanel();
	    instructionPanel.setBackground(Color.GREEN);
	    
	    gameOverPanel = new GameOverPanel();
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
	    cardLayout.show(gameScreens, "Welcome Screen"); //this command changes what's on the screen
	    currentScreen = "Welcome Screen";
	    
	    //Other components of the applet
	    optionPanel = new JPanel();
	    optionPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, OPTION_HEIGHT));
	    optionPanel.setBackground(Color.BLACK);
	    
	    titleLabel = new JLabel("");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setPreferredSize(new Dimension(450,40));
	    
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
		
	}
	*/
	

	public class WelcomePanel extends JPanel {
		private JButton startButton;
	
		public WelcomePanel() {
			setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));	
			startButton = new JButton("START");
			startButton.setPreferredSize(new Dimension(100, 50));
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
			
			g2.setColor(Color.RED);
	        g2.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
	        
	        BufferedImage background, logo;
	        	        
	        try {                
	        	//add background image
	        	background = ImageIO.read(getClass().getResource("/liver_cells_bg.png"));
	            g2.drawImage(background,0,0,this);
	            
	            	//add logo image
	            	logo = ImageIO.read(getClass().getResource("/aidsinvasion_logo_main.png"));
	            	g2.drawImage(logo, 0, 50, this);
	            
	         } catch (IOException ex) {
	              System.out.println("Error loading images");
	         }
            
		}
	}
	

	public class BackgroundPanel extends JPanel {
	
		private String title = "HIV is a virus that makes your body very vulnerable to being attacked" 
				+ "by bad diseases that will harm you. The virus tries to attack all the good cells "
				+ "in your body and if there are too few good cells then you may be in big trouble. "
				+ "There are over 1.1 million people in America living with HIV. Approximately 25% of "
				+ "new infections can affect a kid like you. Want to learn more but not have to read fifty "
				+ "textbooks? This game will not only teach you the facts but also you will have fun playing it! "
				+ "With your help, we can help stop the epidemic together and save lives. "
				+ "Enter through each stage of the game and on completion you will be a AIDS/HIV scientist.";
		private String instructions = "Instructions: " +
	"1. Double click on the black squares which are the bad cells to remove as many of them as possible which will stop them from killing the good cells." + 
 	"2. As the game gets harder, the bad cells become harder to take down." + 
	"3. Remember to read the facts which appear in the side window that give you useful information about the bad cells and why they are attacking the good ones."; 

		private JButton nextButton;
		
		public BackgroundPanel(){
			sidebarPanel.repaint();
			initializeGUI();
		}
		
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			
			BufferedImage background;
			
			try {
				//add background image
				background = ImageIO.read(getClass().getResource("/liver_cells_bg.png"));
	            g2.drawImage(background,0,0,this);
			
			} catch (IOException ex) {
				System.out.println("Error loading images");
			}
			
        	editBackgroundPanel(g);
			
		}
		
		private void initializeGUI(){
			
			JButton nextButton = new JButton("Next");
			JTextArea instructionsText = new JTextArea(title,10,50);
			instructionsText.setLineWrap(true);
			instructionsText.setWrapStyleWord(true);
			nextButton.setPreferredSize(new Dimension (100, 50));
			nextButton.setBounds(105, 110, 100, 50);
			nextButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent event) {
					cardLayout.show(gameScreens, "Instructions");
					currentScreen = "Instructions";
					sidebarPanel.repaint();
				}
				
			});
			add(instructionsText);
			add(nextButton);

			setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
	
		}
		
		public void editBackgroundPanel(Graphics g) {
		
			Graphics2D graphicsObject = (Graphics2D) g;
			
			
		}
	}
	
	public class InstructionPanel extends JPanel {
		public InstructionPanel() {
			sidebarPanel.repaint();
			setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));	

		}

		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			
			g2.setColor(Color.BLACK);
			g2.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		}
	}
	
	public class GameOverPanel extends JPanel {
		public GameOverPanel() {
			setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));	
			
		}
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
		}
	}
	
	public class SidebarPanel extends JPanel {
		public SidebarPanel() {
			setPreferredSize(new Dimension(SIDEBAR_WIDTH, WINDOW_HEIGHT));
			
		}
		
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			
			BufferedImage background, logo;
			
			try {                
	        	//add background image
	        	background = ImageIO.read(getClass().getResource("/liver_cells_sidebar.png"));
	            g2.drawImage(background,0,0,this);
	            
	            System.out.println(currentScreen);
	            if(currentScreen.equals("Welcome Screen")){

		            //add logo image
		            logo = ImageIO.read(getClass().getResource("/aidsinvasion_logo_sidebar.png"));
		            g2.drawImage(logo, 0, 50, this);
	            }
	         } catch (IOException ex) {
	              System.out.println("Error loading images");
	         }
		}
	}
	
	

		  
}
