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

	
	final private String[] GAME_SCREENS = {"Welcome Screen", "Background", "Instructions", "Game",
										   "Game Over", "Game Won", "Takeaways"};

	private BufferedImage background, logo, background_sidebar, logo_sidebar, what_is_hiv, instructions_img, 
						  HIV_attacks, HIV_invasion, preventHIVImage, gameOverImage, gameWonImage, precautionsImage;
	
	private JButton progressButton;
	private JTextArea contentText;
	private BufferedImage backgroundImage;
	private BufferedImage headerImage;
	private BufferedImage mainImage;
	private JLabel pictureLabel;
	private int panelType;
	private boolean textAreaIncluded;
	
	/**
	 * DisplayPanel()
	 * Constructor
	 * @param buttonText
	 * @param textToDisplay
	 * @param displayPanelType
	 */
	public DisplayPanel(String buttonText, String textToDisplay, int displayPanelType) {
		// Constructor for DisplayPanel with text in main content section
		
		//Set layout equal to null
		setLayout(null);
		
		//Set the size dimensions
		setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
		
		//Call loadImages()
		loadImages();

		//Set panelType equal to new variable
		panelType = displayPanelType;
		
		//Set textAreaIncluded equal to true
		textAreaIncluded = true;
		
		//Set up progress button to be used in game
		progressButton = new JButton(buttonText);
		progressButton = styleButton(progressButton);
		progressButton.setBounds(350, 600, 150, 50);
		
		//Set up content text to be used in game
		contentText = new JTextArea(textToDisplay, 10, 50);
		contentText = styleText(contentText);
		contentText.setBounds(25, 75, GAME_WIDTH - 50, 300);
		
		//If panelType is 6
		if (panelType == 6) {
			
			//Add listener to progressButton
			progressButton.addActionListener(new ActionListener() {

				//Create action performed event that changes display panel to game
				@Override
				public void actionPerformed(ActionEvent event) {
					InvasionGame.changeDisplayPanel("Game");
				}
				
			});
		}
		
		//Else
		else {
			
			//Add listener to progressButton anyway
			progressButton.addActionListener(new ActionListener() {

			//Create action event that changes display panel to one ahead
			@Override
			public void actionPerformed(ActionEvent event) {
				InvasionGame.changeDisplayPanel(GAME_SCREENS[panelType + 1]);
			}
			
			});
		}
		
		//Call initDisplayPanel()
		initDisplayPanel();
		
	}

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
		progressButton.setBounds(350, 600, 150, 50);
		progressButton.addActionListener(new ActionListener() {

			//Create action performed event that changes screen to one ahead
			@Override
			public void actionPerformed(ActionEvent event) {
				if(panelType == 4)
					InvasionGame.changeDisplayPanel(GAME_SCREENS[panelType + 2]);
				else {
					InvasionGame.changeDisplayPanel(GAME_SCREENS[panelType + 1]);
				}
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
        
        //If the panel is equal to welcome screen
        if (GAME_SCREENS[panelType] == "Welcome Screen") {
        	
        	//Draw the logo image on the panel
            g2.drawImage(logo, 0, 50, this);
        }
        
        //If panel is equal to the background screen
        else if (GAME_SCREENS[panelType] == "Background") {
			//Draw the images to be displayed
        	g2.drawImage(what_is_hiv,0,6,this);
			g2.drawImage(HIV_invasion, -40, 375, this);
			g2.drawImage(HIV_attacks, 325, 330, this);
			
        }
        
        //If panel is equal to the instructions screen
        else if (GAME_SCREENS[panelType] == "Instructions") {
        	//Draw the instructions image on the panel
        	g2.drawImage(instructions_img,0,0,this);
			
        }  
        
        //If the panel is equal to the game over screen
        else if (GAME_SCREENS[panelType] == "Game Over") {
			//Draw the game over image
        	g2.drawImage(gameOverImage,0,100,this);
        }
        
        //If the panel is equal to the game won screen
        else if (GAME_SCREENS[panelType] == "Game Won") {
			//Draw the game won image 
        	g2.drawImage(gameWonImage,0,100,this);
        }  
        
        //If the panel is equal to the takeaways screen
        else if (GAME_SCREENS[panelType] == "Takeaways") {
        	//Draw the precautions image
        	g2.drawImage(precautionsImage, 75, 375, this);
        }
       
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
        	
        	//load sidebar background
        	background_sidebar = ImageIO.read(getClass().getResource("/liver_cells_sidebar.png"));
        	
        	//lode sidebar logo image
            logo_sidebar = ImageIO.read(getClass().getResource("/aidsinvasion_logo_sidebar.png"));
            
            //"What is HIV?" title image
            what_is_hiv = ImageIO.read(getClass().getResource("/whatishiv.png"));
            
            //instructions image
            instructions_img = ImageIO.read(getClass().getResource("/instructions_screen.png"));
            
            // HIV attacks T-cells
            HIV_attacks = ImageIO.read(getClass().getResource("/HIV_attack.png"));
            
            // HIV attacks T-cells
            HIV_invasion = ImageIO.read(getClass().getResource("/HIV_invasion.jpg"));

            //Prevent HIV
            preventHIVImage = ImageIO.read(getClass().getResource("/prevent_HIV.png"));
            
            //Game Over image
            gameOverImage = ImageIO.read(getClass().getResource("/game_over.png"));
            
            // Game won image
			gameWonImage = ImageIO.read(getClass().getResource("/game_won.png"));
            
			//Precautions image
			precautionsImage = ImageIO.read(getClass().getResource("/hiv-aids-vaccine.jpg"));
			
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
