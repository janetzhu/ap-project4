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
	
		private String title = "";
		private String instructions = "";
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
			add(nextButton);

			setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
	
		}
		
		public void editBackgroundPanel(Graphics g) {
		
			Graphics2D graphicsObject = (Graphics2D) g;
			graphicsObject.setFont(new Font(title, 10, 18));
			graphicsObject.setFont(new Font(instructions, 10, 18));
			graphicsObject.setColor(Color.red);
			graphicsObject.drawString(title, 80, 20);
			graphicsObject.drawString(instructions,  75,  50);
			
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
	
	
	//this method should be adding to the instructionPanel JPanel object
	public void displayInstructions() {
		
	}
	
	//this method should be editing the gameOverPanel JPanel object
	public void displayGameOverMessage() {
		
	}

		  
}
