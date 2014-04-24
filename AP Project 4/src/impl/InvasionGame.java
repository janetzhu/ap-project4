package impl;

import java.applet.Applet;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

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
 * Contains two JPanels at any one time: screens and sidebarPanel.
 * gameScreens uses a CardLayout and holds the various game screens 
 * (welcome menu, instructions, game over), displaying one at a time.
 * The CardLayout allows gameScreens to be used to 'flip' between screens for the different stages of game play.
 * The optionPanel holds the menu bar at the top of the game.
 * The sidebarPanel is a side bar that displays the facts to the user on the right side of the screen.
 */

public class InvasionGame extends JApplet {

	/******** GAME CONSTANTS ********/
	// Heights and widths of the window and the various panels
	private int WINDOW_WIDTH = 850; //should == GAME_WIDTH + SIDEBAR_WIDTH
	private int WINDOW_HEIGHT = 650;
	private int GAME_HEIGHT = 650;
	private int GAME_WIDTH = 650;
	private int SIDEBAR_WIDTH = 200;

	/******** WINDOW COMPONENTS ********/
	// JPanel that holds all of the screens for the different stages of game play.
	// It only displays one at once, and can therefore be used to 'flip' between them.
    private JPanel gameScreens; 
    
    // Various screens, for different stages of game play.
	private WelcomePanel welcomePanel;
	private BackgroundPanel backgroundPanel;
	private InstructionPanel instructionPanel;
	private GameOverPanel gameOverPanel;
    //private JPanel welcomePanel, backgroundPanel, instructionPanel, gameOverPanel; 
	private CardLayout cardLayout;
    private Board gameBoard; //JPanel object
	private SidebarPanel sidebarPanel;
	private JLabel titleLabel, blankLabelSmall, blankLabelWide, blankLabelQuit;
	private JButton pause, quit, restart;
	private String currentScreen;
	private BufferedImage background, logo, background_sidebar, logo_sidebar, what_is_hiv, instructions_img;
	public BufferedImage bodyCellImage;


	/*
	 * initUI()
	 * 
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
		setLayout(new BorderLayout());
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

		loadImages();
        
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
	     * instructions, game over, etc.) for the different stages of game play.
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

	    titleLabel = new JLabel("");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setPreferredSize(new Dimension(450,40));

	    restart = new JButton("Restart");
	    restart.setPreferredSize(new Dimension(120,50));
	    restart = styleButton(restart);
	    //restart.addActionListener((ActionListener) this);

	    pause = new JButton("Pause");
	    pause.setPreferredSize(new Dimension(100,50));
	    pause = styleButton(pause);
	    //pause.addActionListener((ActionListener) this);

	    add(sidebarPanel, BorderLayout.EAST);
	    add(gameScreens, BorderLayout.CENTER); //adding center screen to layout

	    setVisible(true);
	}

	/*
	 *loadImages()  
	 * 
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
            
            //"What is HIV?" title image
            instructions_img = ImageIO.read(getClass().getResource("/instructions.png"));
            
            //Body Cell Image
            bodyCellImage = ImageIO.read(getClass().getResource("/body_cell.png"));
            
            

		} catch (IOException ex) {
			System.out.println("Error loading image");
		}
	}

	//adds color and styles to JButton elements
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
	public JTextArea styleText(JTextArea text) {
		text.setMargin(new Insets(20, 20, 20, 20));
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		text.setEditable(false);
		text.setForeground(Color.WHITE);
		text.setBackground(new Color(0,0,0, 150));
		text.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		return text;

	}

/*
 * This class is the structure of the welcome screen where you hit start and directs you 
 * to the next page. 
 * 
 */

	//JPanel object that contains the logo and Start button
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
	}

	/*
	 * This class tells the user the background and reason for playing 
	 * the game and why it is important. It is directed toward a kid audience. 
	 * 
	 */

	//JPanel that gives the user backgrond information about HIV/AIDS
	public class BackgroundPanel extends JPanel {

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

			BufferedImage HIV_image = null;
			try {
				HIV_image = ImageIO.read(new File("HIV_attack.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
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

	}

	/*
	 * This class encompasses the Instruction Panel which 
	 * tells the user what to do and how to play the game. 
	 */
	public class InstructionPanel extends JPanel {

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
	}

	public class GameOverPanel extends JPanel {

		private JTextArea gameOverText;

		private String message = "GAME OVER";

		private JTextArea takeawaysText;
        
        private String takeaways = 
        					"Remember...prevention is the best way to avoid getting HIV/AIDS " +
        					"You should practice the following preventive methods: " +
        					"Abstain from sex (don't have sex) " + 
        					"Only have one partner at a time " +
        					"Use a condom during sex " +
        					"Avoid blood to blood contact ";
        
		public GameOverPanel() {

			setLayout(null);

			initializeGUI();

		}

		public void initializeGUI() {

			setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));

			gameOverText = new JTextArea(message,25,50);
			gameOverText.setBounds(25, 75, GAME_WIDTH-50, 300);
			gameOverText = styleText(gameOverText);

			takeawaysText = new JTextArea(takeaways,10,50);
			takeawaysText.setBounds(25, 400, 400, 300);
			takeawaysText = styleText(takeawaysText);

			add(gameOverText);
			add(takeawaysText);
		}

		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;

			g2.drawImage(background,0,0,this);
		} 

	}

	public class SidebarPanel extends JPanel {
		private boolean dimmed, inGame;
		private JTextArea infectedText;
		private String displayText;

		public SidebarPanel() {
			setPreferredSize(new Dimension(SIDEBAR_WIDTH, WINDOW_HEIGHT));
			dimmed = false;

			infectedText = new JTextArea();
			infectedText.setForeground(Color.WHITE);
			infectedText.setBackground(new Color(0,0,0,0));
			infectedText.setEditable(false);
			infectedText.setLineWrap(true);
			infectedText.setWrapStyleWord(true);
			infectedText.setVisible(false);

			add(infectedText);
		}

		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;

	        g2.drawImage(background_sidebar,0,0,this);

	        if(currentScreen.equals("Welcome Screen"))
	        	g2.drawImage(logo_sidebar, 0, 50, this);

	        if(dimmed) {
	        	g2.setColor(new Color(0,0,0,215));
	        	g2.fillRect(0,0,SIDEBAR_WIDTH, GAME_HEIGHT);
	        }
	        if(inGame) {
	        	g2.setColor(new Color(0,0,0,150));
	        	g2.fillRect(0,0,SIDEBAR_WIDTH, GAME_HEIGHT);
	        }
	        /*if(infected) {
	        	g2.setColor(Color.WHITE);
	        	g2.drawString("You have been infected with HIV!", 0, 20);
	        }*/
		}

		public void inGame() {
			inGame = true;
			repaint();
		}

		public void dimSidebar() {
			inGame = false;
			dimmed = true;
			infectedText.setVisible(false);
			repaint();
		}

		public void lightenSidebar() {
			dimmed = false;
			repaint();
		}
		public void displayInfected() {
			infectedText.setText("You have been infected with HIV!");
			infectedText.setVisible(true);
			revalidate();
		}

		public void changeText(String newText) {
			infectedText.setText(newText);

			revalidate();
		}

	}




}