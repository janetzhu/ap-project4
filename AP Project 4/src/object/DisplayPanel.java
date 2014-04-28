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
	
	final private String[] GAME_SCREENS = {"Welcome Screen", "Background", "Instructions", "Game", "Takeaways",
										   "Game Over", "Game Won"};

	private BufferedImage background, logo, background_sidebar, logo_sidebar, what_is_hiv, instructions_img, 
						  HIV_attacks, HIV_invasion, preventHIVImage;
	
	private JButton progressButton;
	private JTextArea contentText;
	private BufferedImage backgroundImage;
	private BufferedImage headerImage;
	private BufferedImage mainImage;
	private JLabel pictureLabel;
	private int panelType;
	
	
	public DisplayPanel(String buttonText, String textToDisplay, int displayPanelType) {
		// Constructor for DisplayPanel with text in main content section
		setLayout(null);
		setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
		loadImages();
		
		panelType = displayPanelType;
		
		progressButton = new JButton(buttonText);
		progressButton = styleButton(progressButton);
		progressButton.setBounds(325, 560, 200, 50);
		
		contentText = new JTextArea(textToDisplay, 10, 50);
		contentText = styleText(contentText);
		contentText.setBounds(25, 75, GAME_WIDTH - 50, 300);
		
		progressButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				InvasionGame.changeDisplayPanel(GAME_SCREENS[panelType + 1]);
			}
			
		});
		
		initDisplayPanel();
	}
	
	public DisplayPanel(String buttonText, int displayPanelType) {
		// Constructor for displayPanel with an image as the main content
		setLayout(null);
		setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
		loadImages();
		
		panelType = displayPanelType;
		
		progressButton = new JButton(buttonText);
		progressButton = styleButton(progressButton);
		progressButton.setBounds(325, 560, 200, 50);
		
		progressButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				InvasionGame.changeDisplayPanel(GAME_SCREENS[panelType + 1]);
			}
			
		});
				
		initDisplayPanel();
	}

	public void initDisplayPanel() {		
        add(progressButton);
		
		if (GAME_SCREENS[panelType] != "Welcome Screen") {
			add(contentText);
		}
		
		revalidate();
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
        
        g2.drawImage(background, 0, 0, this);
        
        if (GAME_SCREENS[panelType] == "Welcome Screen") {
            g2.drawImage(logo, 0, 50, this);
        }
        else if (GAME_SCREENS[panelType] == "Background") {
			g2.drawImage(what_is_hiv,0,6,this);
			g2.drawImage(HIV_attacks, 25, 375, this);
        }
        else if (GAME_SCREENS[panelType] == "Instructions") {
			g2.drawImage(instructions_img,0,6,this);
			g2.drawImage(HIV_invasion, 25, 350, this);
        }  
        /*
        else if (GAME_SCREENS[panelType] == "Game Over") {
			g2.drawImage(instructions_img,0,6,this);
			//g2.drawImage(HIV_invasion, 25, 350, this);
        }
        else if (GAME_SCREENS[panelType] == "Game Won") {
			g2.drawImage(instructions_img,0,6,this);
			//g2.drawImage(HIV_invasion, 25, 350, this);
        }  
        */
        else if (GAME_SCREENS[panelType] == "Takeaways") {
        	//g2.drawImage(instructions_img, 0, 6, this);
        	g2.drawImage(preventHIVImage, 25, 350, this);
        }
       
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
            
            // HIV attacks T-cells
            HIV_attacks = ImageIO.read(getClass().getResource("/HIV_attack.jpg"));
            
            // HIV attacks T-cells
            HIV_invasion = ImageIO.read(getClass().getResource("/HIV_invasion.jpg"));

            //Prevent HIV
            preventHIVImage = ImageIO.read(getClass().getResource("/prevent_HIV.png"));
            
		} catch (IOException ex) {
			System.out.println("Error loading image");
		}
	}
	
	//adds color and styles to generic JButton elements
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
	
}
