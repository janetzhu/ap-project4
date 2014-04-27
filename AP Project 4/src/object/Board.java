package object;

import impl.InvasionGame;
import impl.InvasionGame.SidebarPanel;

import java.applet.Applet;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * JPanel class that contains the portion of the window where the main game play occurs
 *
 */

public class Board extends JPanel implements Runnable, MouseListener {
	

	/******** CLASS  VARIABLES ********/
	private final int DELAY = 20;
	private final int VIRUS_POS_XMIN = 15;
    private final int VIRUS_POS_XMAX = 635;
    private final int VIRUS_POS_YMIN = 10;
    private final int VIRUS_POS_YMAX = 250;
    private final int INIT_VIRUS_X_SPEED = 2;
    private final int INIT_VIRUS_Y_SPEED = 2;
    
    private final int CELL_ROWS = 3;
    private final int CELL_COLUMNS = 9;
    private final int CELL_WIDTH = 66;
    private final int CELL_HEIGHT = 50;
    
    private final int START_VIRUS_COUNT = 2; //number of viruses at start of game
    private final int START_TCELL_COUNT = 1000;
    private final int START_DIFFICULTY_LEVEL = 1;
    private final long GAME_WON_TIME = 60000;
    private final long HIV_INTRO_TIME = 10000;
    
    private final int LEVEL_2_BENCHMARK = 950;
    private final int LEVEL_3_BENCHMARK = 940;
    private final int LEVEL_4_BENCHMARK = 930;
    private final int LEVEL_5_BENCHMARK = 920;
    private final int LEVEL_6_BENCHMARK = 910;
    
    private SidebarPanel sidebarPanel; //send over sidebar panel from 
    
    //Resource objects
    private BufferedImage gameOverImage, gameWonImage;
    private BufferedImage[] bodyCells;
    private BufferedImage[] infectedCells;
    private BufferedImage[] virusImages = new BufferedImage[6];
    private BufferedImage[] progressImages = new BufferedImage[10];
    private BufferedImage currentProgressImage;
    
    private long gameStartTime;
	private int gameHeight;
	private int gameWidth;
	
	private Cell[][] cellList = new Cell[CELL_COLUMNS][CELL_ROWS];
	private BufferedImage[][] cellImages = new BufferedImage[CELL_COLUMNS][CELL_ROWS];
	private BufferedImage[][] infectedCellImages = new BufferedImage[CELL_COLUMNS][CELL_ROWS];
	private ArrayList<Virus> virusList = new ArrayList<Virus>();
	private Facts hivFacts;
		
	private String gameStatus;
	
	private int tCellCount;
	private int gameScore;
	
	private int difficultyLevel;
	private boolean infected;
	
	private int cellCounter;
	
	public Board(int height, int width) {
		gameHeight = height;
		gameWidth = width;
		
	}
	
	 //method called from InvasionGame class to start the game play
	 //sends SidebarPanel object as a parameter to be able to add facts and information as the game progresses
	 public void initBoard(SidebarPanel sidebar) {
		
		setVisible(true);
		
		setPreferredSize(new Dimension(gameWidth, gameHeight));
		setBackground(Color.GRAY);
		
		this.addMouseListener(this);
		
		infected = false;
		cellCounter = CELL_ROWS * CELL_COLUMNS;
		gameScore = 0;
		tCellCount = START_TCELL_COUNT;
		difficultyLevel = START_DIFFICULTY_LEVEL;
		
		sidebarPanel = sidebar;
		sidebarPanel.inGame();
		
		// Body Cells are created in a 3 x 6 array toward the bottom of the board
        for (int j = 0; j < CELL_ROWS; j++) {
	        for (int i = 0; i < CELL_COLUMNS; i++) {
	        	// locations are spaced apart slightly
	        	cellList[i][j] = new Cell((CELL_WIDTH + 5) * i + 10, 450 + (CELL_HEIGHT + 10) * j,CELL_WIDTH, CELL_HEIGHT, false);
	        }
        }
        
        for(int i = 0; i < START_VIRUS_COUNT; i++){
        	introduceVirus();
        }
        
        gameStartTime = System.currentTimeMillis();
        
        loadResources();
        currentProgressImage = progressImages[0];
        initCells();
        
        repaint();
        
        start();
	}
	
	public void start() {
    	Thread gamePlayThread = new Thread (this);
    	gamePlayThread.start();
    	gameStatus = "playing";
    }
	
    public void stop() {}
    
    public void destroy() {}
	
    //load all BufferedImage objects and Fonts
	public void loadResources() {
		try {
			gameOverImage = ImageIO.read(getClass().getResource("/game_over.png"));
			gameWonImage = ImageIO.read(getClass().getResource("/game_won.png"));
			
			//Load progress bar images
			for(int i = 1; i <= 10; i++) {
<<<<<<< HEAD
				String imagePath = "/progress_images/progress_bar" + i + ".png";
=======
				String imagePath = "/progress_images/progress_bar_" + i + ".png";
>>>>>>> Janet
				progressImages[i-1] = ImageIO.read(getClass().getResource(imagePath));
			}
			
			bodyCells = new BufferedImage[4];
			for(int i = 1; i <= 4; i++) {
				String imagePath = "/cell_images/body_cell" + i + ".png";
				bodyCells[i-1] = ImageIO.read(getClass().getResource(imagePath));
			}
			
			infectedCells = new BufferedImage[4];
			for(int i = 1; i <= 4; i++) {
				String imagePath = "/cell_images/infected_cell" + i + ".png";
				infectedCells[i-1] = ImageIO.read(getClass().getResource(imagePath));
			}
			
			for(int i = 1; i <= 6; i++) {
				String imagePath = "/cell_images/virus" + i + ".png";
				virusImages[i-1] = ImageIO.read(getClass().getResource(imagePath));
			}

//			File fontFile = new File("/DS-DIGI.TTF");
//			displayFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.PLAIN, 15f);
//				
//			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//			ge.registerFont(displayFont);
//			InputStream inStream = getClass().getResourceAsStream("/DS-DIGI.TTF");
//			Font displayFont = Font.createFont(Font.TRUETYPE_FONT, inStream);
//
//			displayFont = displayFont.deriveFont(12f);
			//myLabel.setFont(siFont);
			
		} catch (IOException e) {
			System.out.println("Error loading images");
		} //catch (FontFormatException e) {
//			System.out.println("Error loading fonts");
//		}
    }
	
	//initialize all cell images by randomly choosing from four image options for each index
	public void initCells() {
		int randInt;
		
		for (int j = 0; j < CELL_ROWS; j++) {
	        for (int i = 0; i < CELL_COLUMNS; i++) {
	        	randInt = (int)(Math.random() * 4);
	        	cellImages[i][j] = bodyCells[randInt];
	        	infectedCellImages[i][j] = infectedCells[randInt];
	        }
		}
	}
    
	/**
	 * Re-Paints the objects to the screen
	 * 
	 * @param g
	 */
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
				
		// Turn on anti-aliasing to smooth out shapes
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        BufferedImage background;
        
        try {
    		//load background image
    		background = ImageIO.read(getClass().getResource("/liver_cells_bg.png"));
    		g2.drawImage(background,0,0,this);
        } catch(IOException exception) {
        	System.out.println("Error loading image");
        }
        
        drawCells(g);
        drawViruses(g);
        
        //the score board 
        g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        g2.setColor(Color.WHITE);
        g2.drawString("Score: " + gameScore, 10, 35);
        
        //Progress Bar
<<<<<<< HEAD
        g2.drawImage(currentProgressImage, 40, 5, this);
=======
        g2.drawImage(currentProgressImage, 135, 17, this);
>>>>>>> Janet
        
        //the T-Cell counter 
        g2.drawString("T-Cells Remaining: " + tCellCount, 360 , 35);
        
        
        JTextArea takeawaysText;
        
        String takeaways = 
        					"Remember...prevention is the best way to avoid getting HIV/AIDS " +
        					"You should practice the following preventive methods: " +
        					"Abstain from sex (don't have sex) " + 
        					"Only have one partner at a time " +
        					"Use a condom during sex " +
        					"Avoid blood to blood contact ";
        
        
        if(gameStatus == "gameOver") {
        	g2.setColor(new Color(0,0,0,215));
        	g2.fillRect(0, 0, gameWidth, gameHeight);
        	g2.drawImage(gameOverImage, 50, 150, this);
        	
        	/*
        	takeawaysText = new JTextArea(takeaways,25,50);
        	takeawaysText.setBounds(25, 75, 400, 300);
        	takeawaysText = styleText(takeawaysText);
			
			add(takeawaysText);
			*/
			
        	/*
        	//the takeaway message 
            g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
            g2.setColor(Color.WHITE);
            g2.drawString(
            		"Remember...prevention is the best way to avoid getting " +
					"\n HIV/AIDS. You should practice the following preventive " +
            		"\n methods: " +
					"\n Abstain from sex (don't have sex) " + 
					"\n Only have one partner at a time " +
					"\n Use a condom during sex " +
					"\n Avoid blood to blood contact ", 10 , 35);
			*/
            
        	sidebarPanel.dimSidebar();
        }
        
        if(gameStatus == "gameWon") {
        	g2.setColor(new Color(0,0,0,215));
        	g2.fillRect(0, 0, gameWidth, gameHeight);
        	g2.drawImage(gameWonImage, 50, 150, this);
        	sidebarPanel.dimSidebar();
        }
	}
    
	
	private JTextArea styleText(JTextArea takeawaysText) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
     * Draw the body cells currently in the cellList as rectangles.  Set the color to black to show they're not infected
     * and fill the cells.
     * 
     * @param g the graphics object that will be painted
     */
    private void drawCells(Graphics g) {
    	
    	Graphics2D g2 = (Graphics2D) g;
    	
    	for (int j = 0; j < CELL_ROWS; j++) {
	        for (int i = 0; i < CELL_COLUMNS; i++) {
	        	Cell cell = cellList[i][j];
	        	if (!cell.isInfected()) {
		        	//g.setColor(Color.GREEN);
		        	//g.fillRect((int) cell.getX(), (int) cell.getY(), (int) cell.getWidth(), (int) cell.getHeight());
		        	g2.drawImage(cellImages[i][j], (int) cell.getX(), (int) cell.getY(), this);
	        	}
	        	if (cell.isInfected()) {
		        	//g.setColor(Color.RED);
		        	//g.fillRect((int) cell.getX(), (int) cell.getY(), (int) cell.getWidth(), (int) cell.getHeight());
		        	g2.drawImage(infectedCellImages[i][j], (int) cell.getX(), (int) cell.getY(), this);

	        	}
	        }
        }
    }
    
    /**
     * Draw the viruses currently in the virusList as rectangles.
     * 
     * @param g the graphics object that will be painted
     */
    private void drawViruses(Graphics g) {
    	Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < virusList.size(); i++) {
        	Virus virus = virusList.get(i);
        	//only draw virus if it is alive
        	if (virus.isAlive()) {
	        	g2.drawImage(virusImages[virus.getStrength()-1], virus.getX(), virus.getY(), this);
        	}
        }
    }
    
    /**
	 * Detects user mouse clicks.
	 */
	public void mousePressed(MouseEvent e) {
		// Check if the user click on top of an actual virus
		for (int i = 0; i < virusList.size(); i++) {

			// Iterate through all viruses
			Virus virus = virusList.get(i);
			int strength = virus.getStrength();
			
			//Check if click location is within bounds of any virus 
			if (virus.withinVirus(e.getX(), e.getY())) {
			  if(virus.isAlive()) {	
					System.out.println("Virus clicked");

				if (strength == 1) {
					// If so, and strength is only 1, kill virus
					virus.setAlive(false);
					gameScore = gameScore + 15;
					repaint();
				}
				else if (strength > 1) {
					// If so, but strength is greater than 1, decrement strength
					virus.setStrength(strength - 1);
					
				}
				
			
				// Replace virus with killed one or one with weaker strength
				virusList.set(i, virus);
				//Break from loop
				break;
			  }
			}		
		}	
	}
	
	/**
	 * Called after a certain amount of time has passed in the game. 
	 * Starts decrementing the T-cell count. 
	 * After this method is called, the game becomes increasingly difficult
	 * (i.e. the immune system becomes increasingly deficient).
	 * 
	 */
	public void infectHIV() {
		infected = true;
		sidebarPanel.displayInfected();
	}
	
	/**
	 * Returns the game status.
	 * @return gameStatus
	 */
	public String getGameStatus() {
		return gameStatus;
	}
	
	/**
	 * Sets the game status.
	 * @param status
	 */
	public void setGameStatus(String status) {
		this.gameStatus = status;
	}
    
	/**
	 * Introduces a new virus or disease to the game to be killed
	 */
    public void introduceVirus() {
	    /* 
	     * Used a random number generator to get random numbers for the initial position of the virus introduced
	     * Inspired by a similar post on stack overflow:
	     * http://stackoverflow.com/questions/363681/generating-random-numbers-in-a-range-with-java
	     */
	    int randomNumberX = VIRUS_POS_XMIN + (int)(Math.random() * ((VIRUS_POS_XMAX - VIRUS_POS_XMIN) + 1));
	    int randomNumberY = VIRUS_POS_YMIN + (int)(Math.random() * ((VIRUS_POS_YMAX - VIRUS_POS_YMIN) + 1));
	    int randomNumberDifficulty;
	    
	    if (difficultyLevel > 1) {
	    	int minDifficulty = difficultyLevel - 3;
	    	if (minDifficulty < 1) {
	    		minDifficulty = 1;
	    	}
		    randomNumberDifficulty = minDifficulty + (int)(Math.random() * ((difficultyLevel - minDifficulty) + 1));
	    }
	    else {
	    	randomNumberDifficulty = difficultyLevel;
	    }

	    Random random = new Random();
	    
	    // Initializes virus at the random location each time a new one is introduced
	    Virus newVirus = new Virus(randomNumberX,randomNumberY, INIT_VIRUS_X_SPEED*(random.nextBoolean() ? 1 : -1), INIT_VIRUS_Y_SPEED, randomNumberDifficulty);
	    	    
	    virusList.add(newVirus);
    }
	
    /**
	 * Handles the animation and collision checking for viruses while playing game.
	 */
    public void cycle() {
		
		// Animate Viruses and check for collisions or infections
		for (int i = 0; i < virusList.size(); i++) {
        	Virus virus = virusList.get(i);
        	if (virus.isAlive()) {
        		
	        	virus.animateVirus();
	        	
	        	detectInfection(i);
	    		
	    		checkWallCollision(i);
	    		
	    		checkMembraneCollision(i);
        	}
        }
	}

    /**
	 * Detects collisions between Virus objects and Cell objects.
	 */
	public void detectInfection(int virusIndex) {
		Virus thisVirus = virusList.get(virusIndex);
		
		// Check if the virus infects a cell
       	if (thisVirus.getY() > 350) {
    		for (int j = 0; j < CELL_ROWS; j++) {
    	        for (int i = 0; i < CELL_COLUMNS; i++) {
    	        	// Iterate through cell List and store the location parameters of the cell into variables for convenience
    	        	Cell cell = cellList[i][j];
    	        	
    	        	// Only infect if the cell has not already been infected
    	        	if (!cell.isInfected()) {
	    	        	
	    	        	int top = (int) cell.getBound(1);
	    	        	int right = (int) cell.getBound(2);
	    	        	int bottom = (int) cell.getBound(3);
	    	        	int left = (int) cell.getBound(4);
	    	        	
	    	        	// Infect cell and set virus to not-active so that it is not redrawn
	    	        	if ((Math.abs(thisVirus.getBound(1) - bottom) < 4) && thisVirus.getBound(2) >= left && thisVirus.getBound(4) <= right) {
	    	        		// Hit bottom of cell, infect cell
	    	        		thisVirus.setAlive(false);
	    	        		cellList[i][j].setInfected(true);
	    	        		cellCounter--;
	    	        	}
	    	        	else if ((Math.abs(thisVirus.getBound(3) -top) < 4) && thisVirus.getBound(2) >= left && thisVirus.getBound(4) <= right) {
	    	        		// Hit top of cell, infect cell
	    	        		thisVirus.setAlive(false);
	    	        		cellList[i][j].setInfected(true);
	    	        		cellCounter--;
	    	        	}
	    	        	else if ((Math.abs(thisVirus.getBound(2) - left) < 4) && thisVirus.getBound(3) >= top && thisVirus.getBound(1) <= bottom) {
	    	        		// Hit left of cell, infect cell
	    	        		thisVirus.setAlive(false);
	    	        		cellList[i][j].setInfected(true);
	    	        		cellCounter--;
	    	        	}
	    	        	else if ((Math.abs(thisVirus.getBound(4) - right) < 4) && thisVirus.getBound(3) >= top && thisVirus.getBound(1) <= bottom) {
	    	        		// Hit right of cell, infect cell
	    	        		thisVirus.setAlive(false);
	    	        		cellList[i][j].setInfected(true);
	    	        		cellCounter--;
	    	        	}
    	        	}
    	        }
            }
       	}   
       	
       	// Reset virus in list with new status (if there was contact)
       	virusList.set(virusIndex, thisVirus);
    }
    
	/**
	 * Handles the collision of viruses with the four 'walls' of the board.
	 */
	public void checkWallCollision(int virusIndex) {
		Virus thisVirus = virusList.get(virusIndex);
		
		// Check the bounds of the game board for a collision between this virus and one of the walls.
		// If there is a collision, change the direction vector of the virus so that it will bounce
    	// off the wall properly.
    	if (thisVirus.getBound(3) >= gameHeight) {
    		// Hit top of board
    		thisVirus.setySpeed(-2);
    	}
    	else if (thisVirus.getBound(2) >= gameWidth) {
    		// Hit right wall
    		thisVirus.setxSpeed(-2);
    	}
    	else if (thisVirus.getBound(4) <= 0) {
    		// Hit left wall
    		thisVirus.setxSpeed(2);
    	}
    	else if (thisVirus.getBound(1) <= 0) {
    		// Hit top of board
    		thisVirus.setySpeed(2);
    	}
    	
    	virusList.set(virusIndex, thisVirus);
	}
    
	/**
	 * Handles collision of viruses with the membrane.
	 */
	public void checkMembraneCollision(int virusIndex) {
		Virus thisVirus = virusList.get(virusIndex);
		
		// TODO Check if virus bounds are at the membrane and change x or y speed accordingly to make it bounce off
    	
    	virusList.set(virusIndex, thisVirus);
	}
	
	/**
	 * Re-Calabrates the difficulty of the game (number of clicks needed to kill a virus, virus speed).
	 * Responds to changes in T-cell count.
	 * 
	 */
	public void calibrateDifficulty() {
		// Based on level of t cell count, loop through all viruses and increase strength respectively if need be
		
		if (tCellCount == LEVEL_2_BENCHMARK) {
			difficultyLevel = 2;
			//sidebarPanel.changeText("The difficulty level has now been increased to two clicks");
		}
		else if (tCellCount == LEVEL_3_BENCHMARK) {
			difficultyLevel = 3;
		}
		else if (tCellCount == LEVEL_4_BENCHMARK) {
			difficultyLevel = 4;
		}
		else if (tCellCount == LEVEL_5_BENCHMARK) {
			difficultyLevel = 5;
		}
		else if (tCellCount == LEVEL_6_BENCHMARK) {
			difficultyLevel = 6;
		}
		
	}
	
	
	public void calculateScore() {
		if(Math.abs((System.currentTimeMillis() - gameStartTime) % 2000) < 15) {	
			gameScore = gameScore + 10;
		}
	}
	
	//obtains String from Fact object
	public void getFact() {
		
	}
	
	/**
	 * Edits the gameOverPanel JPanel object
	 */
	public boolean displayGameOverMessage() {
		if (getGameStatus() == "gameOver") {
			// Show game lost screen
			System.out.println("LOST!");
			return true;
		}
		else if (getGameStatus() == "gameWon") {
			// Show game won screen
			System.out.println("WON!");
			return true;
		}
		return false;
		
	}

    /**
     * Run method for the thread that plays the game
     */
	@Override
	public void run() {
		//Infinite loop, game runs until user x's out of the window.
		while (true) {
			// Code for the timing of the animation is adapted from code in class
			long beforeTime, timeDiff, sleep;
			int cellReduceCounter = 0;
	
	        beforeTime = System.currentTimeMillis();
	        
	        
	        
	        // Run this while loop by the game is being played
			while(gameStatus == "playing") {
				//if(System.currentTimeMillis)
				// Animate objects
				cycle();
				
				calculateScore();
				
				int oneTenthTime = (int) (GAME_WON_TIME / 10);
				
				//Progress bar is incremented as the game time progresses
				for(int i = 0; i < 10; i++) {
					if((System.currentTimeMillis() - gameStartTime) > oneTenthTime * i) {
						currentProgressImage = progressImages[i];
					}
				}
				
				// Calibrate difficulty
				calibrateDifficulty();
				
				if (System.currentTimeMillis() - gameStartTime > HIV_INTRO_TIME) {
					if (!infected) {
						infectHIV();
					}
					else if (infected) {
						cellReduceCounter++;
						if (cellReduceCounter == 7) {
							tCellCount--;
							cellReduceCounter = 0;
						}
					}
				}
				
				if (Math.abs((System.currentTimeMillis() - gameStartTime) % 2000) < 25) {
					introduceVirus();
				}
				
				//Check game timer, if > time value, end game (Win)
				if (cellCounter == 0) {
					setGameStatus("gameOver");
				}
				
				if (System.currentTimeMillis() - gameStartTime > GAME_WON_TIME) {
					setGameStatus("gameWon");
				}
				
				// Repaint objects
				repaint();
				
				// See if the method are running at the
	            // same rate. If not, sleep.
	            timeDiff = System.currentTimeMillis() - beforeTime;
	            sleep = DELAY - timeDiff;
	
	            if (sleep < 0) {
	                sleep = 2;
	            }
	
	            try {
	                Thread.sleep(sleep);
	            } catch (InterruptedException e) {
	                System.out.println("Interrupted: " + e.getMessage());
	            }
	
	            beforeTime = System.currentTimeMillis();
			}
			
			if(displayGameOverMessage()){
				repaint();
				break;
			}
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}
