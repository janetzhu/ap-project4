/**
 * 
 */
package object;

import impl.InvasionGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class DisplayPanel extends JPanel {

	/******** BOARD CONSTANTS ********/
	// Heights and widths of the window and the various panels
	private int WINDOW_WIDTH = 850; //should == GAME_WIDTH + SIDEBAR_WIDTH
	private int WINDOW_HEIGHT = 650;
	private int GAME_HEIGHT = 650;
	private int GAME_WIDTH = 650;

	
	final private String[] GAME_SCREENS = {"Welcome Screen", "Background", "Instructions1", "Instructions2",
											"Instructions3","Instructions4", "Game",
										   "Game Over", "Game Won", "Takeaways"};

	private BufferedImage background, logo, backgroundScreen, background_sidebar, logo_sidebar, 
						  instructionsScreen1, instructionsScreen2, instructionsScreen3, instructionsScreen4,
						  gameOverImage, gameWonImage, preventionScreen;
	
	private JButton progressButton;
	private JTextArea contentText;
	private int panelType;
	private boolean textAreaIncluded;
	
	/**
	 * DisplayPanel()
	 * Constructor
	 * @param buttonText
	 * @param textToDisplay
	 * @param displayPanelType
	 */

	/**
	 * DisplayPanel()
	 * Constructor
	 * @param buttonText
	 * @param displayPanelType
	 */
	public DisplayPanel(String buttonText, int displayPanelType) {
		// Constructor for displayPanel with an image as the main content
		
		//Set layout equal to null
		setLayout(null);
		
		//Set size to game dimensions
		setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
		
		//Call to loadImages()
		loadImages();

		//Set panelType equal to new variable
		panelType = displayPanelType;
		
		//Set textAreaIncluded to false
		textAreaIncluded = false;
		
		//Set up progressButton with listener
		progressButton = new JButton(buttonText);
		progressButton = styleButton(progressButton);
		progressButton.setBounds(350, 580, 150, 50);
		progressButton.addActionListener(new ActionListener() {

			//Create action performed event that changes screen to one ahead
			@Override
			public void actionPerformed(ActionEvent event) {
				System.out.println(GAME_SCREENS[panelType] + " " + panelType);
				if(panelType == 7)
					InvasionGame.changeDisplayPanel(GAME_SCREENS[panelType + 2]);
				//if you have reached the end of the game and need to restart
				else if(panelType == 9) {
					InvasionGame.changeDisplayPanel("Game");
					InvasionGame.resetClearedFacts();
				}
				else
					InvasionGame.changeDisplayPanel(GAME_SCREENS[panelType + 1]);
			}

		});

		//Call initDisplayPanel()
		initDisplayPanel();
	}

	/**
	 * initDisplayPanel()
	 * Method that initializes the display panel
	 */
	public void initDisplayPanel() {		
        //Add the progress button to the panel
		add(progressButton);
		
		//If textAreaIncluded
		if (textAreaIncluded) {
			
			//Add the content text
			add(contentText);
		}

		//Call to revalidate()
		revalidate();
		
		//Call to repaint()
		repaint();
	}

	/**
	 * paintComponent()
	 */
	@Override
	public void paintComponent(Graphics g) {
		//Create graphics2d object
		Graphics2D g2 = (Graphics2D) g;
        
		//Draw the background image
        g2.drawImage(background, 0, 0, this);
        
        switch(panelType) {
        //Welcome Screen
        case 0:
        	g2.drawImage(logo, 0, 50, this);
        	break;
        	
        //Background
        case 1:
        	g2.drawImage(backgroundScreen, 0,0,this);
        	break;
        
        //Instructions 1
        case 2:
        	g2.drawImage(instructionsScreen1,0,0,this);
        	break;
        
        //Instructions 2
        case 3:
        	g2.drawImage(instructionsScreen2,0,0,this);
        	break;
        
        //Instructions 3
        case 4:
        	g2.drawImage(instructionsScreen3,0,0,this);
        	break;
        
        //Instructions 4
        case 5:
        	g2.drawImage(instructionsScreen4,0,0,this);
        	break;
        	
        //Game Over
        case 7:
        	g2.drawImage(gameOverImage,0,100,this);
        	break;
        
        //Game Won
        case 8:
        	g2.drawImage(gameWonImage,0,100,this);
        	break;
        
        //Takeaways
        case 9:
        	g2.drawImage(preventionScreen, 0, 0, this);
        	break;
        }//end switch
       
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
	    //Create try/catch block
		try {
			//load background image
			background = ImageIO.read(getClass().getResource("/liver_cells_bg.png"));

			//add logo image
        	logo = ImageIO.read(getClass().getResource("/aidsinvasion_logo_main.png"));
        	
        	//load background screen image
        	backgroundScreen = ImageIO.read(getClass().getResource("/background_screen.png"));
        	
        	//load sidebar background
        	background_sidebar = ImageIO.read(getClass().getResource("/liver_cells_sidebar.png"));
        	
        	//lode sidebar logo image
            logo_sidebar = ImageIO.read(getClass().getResource("/aidsinvasion_logo_sidebar.png"));
            
            //instructions images
            instructionsScreen1 = ImageIO.read(getClass().getResource("/instructions_screen1.png"));
            instructionsScreen2 = ImageIO.read(getClass().getResource("/instructions_screen2.png"));
            instructionsScreen3 = ImageIO.read(getClass().getResource("/instructions_screen3.png"));
            instructionsScreen4 = ImageIO.read(getClass().getResource("/instructions_screen4.png"));
            
            //Game Over image
            gameOverImage = ImageIO.read(getClass().getResource("/game_over.png"));
            
            // Game won image
			gameWonImage = ImageIO.read(getClass().getResource("/game_won.png"));
            
			//Precautions image
			preventionScreen = ImageIO.read(getClass().getResource("/prevention_screen.png"));
			
		} catch (IOException ex) {
			System.out.println("Error loading image");
		}
	}

	/**
	 * styleButton()
	 * Adds color and styles to generic JButton elements
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

	/**
	 * styleText()
	 * Adds color and styles to JTextArea elements
	 * @param text
	 * @return
	 */
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

} //END DisplayPanel
