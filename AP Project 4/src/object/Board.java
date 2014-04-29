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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * JPanel class that contains the portion of the window where the main game play occurs
 *
 */

public class Board extends JPanel implements Runnable, MouseListener {
	

	/******** CLASS VARIABLES ********/
	private final int DELAY = 20;
	private final int VIRUS_POS_XMIN = 15;
    private final int VIRUS_POS_XMAX = 635;
    private final int VIRUS_POS_YMIN = 10;
    private final int VIRUS_POS_YMAX = 325;
    private final int INIT_VIRUS_X_SPEED = 2;
    private final int INIT_VIRUS_Y_SPEED = 2;
    private final int CELL_ROWS = 3;
    private final int CELL_COLUMNS = 6;
    private final int START_VIRUS_COUNT = 2; //number of viruses at start of game
    private final long GAME_WON_TIME = 60000;
    private final long HIV_INTRO_TIME = 10000;
    
    SidebarPanel sidebarPanel; //send over sidebar panel from 
    
    BufferedImage gameOver_image, gameWon_image;

    //Send over side bar panel from
    private SidebarPanel sidebarPanel; 
    public CountDownLatch latch;
        
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
	private ArrayList<Virus> virusList = new ArrayList<Virus>();
	private Facts hivFacts;
		
	private String gameStatus;
	
	private int tCellCount;
	private int gameScore;
	
	private Timer gameTimer;
	private int difficultyLevel;
	private boolean infected;
	

	private int cellCounter;

	//Declare boolean antiretroviralOffered
	private boolean antiretroviralOffered = false;
	
	// Fact to display
	private int factNo = 0; 


	private InvasionGame ig;
	
	public Board(int height, int width) {
		gameHeight = height;
		gameWidth = width;


	}

	/**
	 * getgameHeight()
	 * Get method
	 * @return
	 */
	public int getgameHeight() {

		return gameHeight;


	}

	/**
	 * getgameWidth()
	 * Get method
	 * @return
	 */
	public int getgameWidth() {

		return gameWidth;

	}

	/**
	 * getTCellCount()
	 * Get method
	 * @return
	 */
	public int getTCellCount() {

		return tCellCount; 	
	}

	/**
	 * setTCellCount()
	 * Set method
	 * @param number
	 */
	public void setTCellCount(int number) {

		tCellCount=number;	
	}

	/**
	 * getDifficultyLevel()
	 * Get method
	 * @return
	 */
	public int getDiffcultyLevel() {

		return difficultyLevel;	
	}

	/**
	 * getGameScore()
	 * Get method
	 * @return
	 */
	public int getGameScore() {

		return gameScore;	
	}

	/**
	 * setGameScore()
	 * Set method
	 * @param score
	 */
	public void setGameScore(int score) {

		gameScore = score;
	}

	/**
	 * getGameStartTime()
	 * Get method
	 * @return
	 */
	public long getGameStartTime() {

		return gameStartTime;
	}

	/**
	 * setGameStartTime()
	 * Set method
	 * @param time
	 */
	public void setGameStartTime(long time) {

		gameStartTime = time;
	}

	/**
	 * setVirusList()
	 * Set method
	 * @param v
	 * @param i
	 */
	public void setVirusList(Virus currentVirus, int index) {

		virusList.set(index, currentVirus);


	}
	
	 //method called from InvasionGame class to start the gameplay
	 //sends SidebarPanel object as a parameter to be able to add facts and information as the game progresses
	 public void initBoard(SidebarPanel sidebar) {
		
		setVisible(true);
		
		setPreferredSize(new Dimension(gameWidth, gameHeight));
		setBackground(Color.GRAY);
		
		this.addMouseListener(this);
		
		infected = false;
		cellCounter = 18;
		gameScore = 0;
		tCellCount = 1000;
		
		sidebarPanel = sidebar;
		sidebarPanel.inGame();
		
		// Body Cells are created in a 3 x 6 array toward the bottom of the board
        for (int j = 0; j < CELL_ROWS; j++) {
	        for (int i = 0; i < CELL_COLUMNS; i++) {
	        	// locations are spaced apart slightly
	        	cellList[i][j] = new Cell(100*i+15,450+60*j,95,50, false);
	        }
        }
        
        for(int i = 0; i < START_VIRUS_COUNT; i++){
        	introduceVirus();
        }
        
        gameStartTime = System.currentTimeMillis();
        

        loadImages();
        
        repaint();

        //Call to initCells()
        initCells();
    	
    	//Call to repaint()
        repaint();
  	}

	/**
	 * start()
	 * Method that starts the game
	 * @param countLatch
	 */
	public void start(CountDownLatch countLatch) {
		//Set game status to playing
		gameStatus = "playing";
    	
    	//Clear the virus list
    	virusList.clear();
    	
    	//Create for loop which iterates through body of cells
    	for (int j = 0; j < CELL_ROWS; j++) {
	        for (int i = 0; i < CELL_COLUMNS; i++) {
	        	cellList[i][j].setInfected(false);
	        }
        }
    	
    	//Initialize board
    	this.initBoard(sidebarPanel);
    	
    	//Latch is equal to count latch
		this.latch = countLatch;

		//Set up sidebar 
		sidebarPanel.inGame();
    	sidebarPanel.repaint();
    	
    	//Start game time
        gameStartTime = System.currentTimeMillis();
    	
        //Create new instance of Thread()
        Thread gamePlayThread = new Thread(this);
        
        start();
	}
	
	public void start() {
    	Thread th = new Thread (this);
    	th.start();
    	gameStatus = "playing";
    }
	
    public void stop() {}
    
    public void destroy() {}
	
    //load all BufferedImage objects
	public void loadImages() {
    
    /**
     * restartGame()
     * Restarts the game
     */
    public void restartGame() {
    	//Set game status to playing
    	gameStatus = "playing";
    	
    	//Clear the virus list
    	virusList.clear();
    	
    	//Create for loop that iterates through body cells
    	for (int j = 0; j < CELL_ROWS; j++) {
	        for (int i = 0; i < CELL_COLUMNS; i++) {
	        	cellList[i][j].setInfected(false);
	        }
        }
    	
    	//Initialize sidebar panel on board
    	this.initBoard(sidebarPanel);
    	
    	//Start game time
        gameStartTime = System.currentTimeMillis();
    	
        //Create new instance of Thread()
        Thread gamePlayThread = new Thread(this);
        
        //Start the game thread
    	gamePlayThread.start();
    }

    /**
     * loadResources()
     * Load all BufferedImage objects and Fonts
     */
	public void loadResources() {
		//Create try/catch block
		try {
			gameOver_image = ImageIO.read(getClass().getResource("/game_over.png"));
			gameWon_image = ImageIO.read(getClass().getResource("/game_won.png"));
		} catch (IOException e) {
			System.out.println("Error loading images");
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
        g2.drawString("Score: " + gameScore, 10 , 35);
        
        //the T-Cell counter 
        g2.drawString("T-Cells Remaining: " + tCellCount, 360 , 35);
        
        
        
        if(gameStatus == "gameOver") {
        	g2.setColor(new Color(0,0,0,215));
        	g2.fillRect(0, 0, gameWidth, gameHeight);
        	g2.drawImage(gameOver_image, 50, 150, this);
        	sidebarPanel.dimSidebar();
        }
        
        if(gameStatus == "gameWon") {
        	g2.setColor(new Color(0,0,0,215));
        	g2.fillRect(0, 0, gameWidth, gameHeight);
        	g2.drawImage(gameWon_image, 50, 150, this);
        	sidebarPanel.dimSidebar();
        }
	}
    
	
	/**
     * Draw the body cells currently in the cellList as rectangles.  Set the color to black to show they're not infected
     * and fill the cells.
     * 
     * @param g the graphics object that will be painted
     */
    private void drawCells(Graphics g) {
    	
    	Graphics2D g2d = (Graphics2D) g;
    	
    	for (int j = 0; j < CELL_ROWS; j++) {
	        for (int i = 0; i < CELL_COLUMNS; i++) {
	        	Cell cell = cellList[i][j];
	        	if (!cell.isInfected()) {
		        	g.drawRect((int) cell.getX(), (int) cell.getY(), (int) cell.getWidth(), (int) cell.getHeight());
		        	g.setColor(Color.GREEN);
		        	g.fillRect((int) cell.getX(), (int) cell.getY(), (int) cell.getWidth(), (int) cell.getHeight());
		        	//g.drawImage(ig.bodyCellImage, (int) cell.getX(), (int) cell.getY(), this);
	        	}
	        	if (cell.isInfected()) {
		        	g.drawRect((int) cell.getX(), (int) cell.getY(), (int) cell.getWidth(), (int) cell.getHeight());
		        	g.setColor(Color.RED);
		        	g.fillRect((int) cell.getX(), (int) cell.getY(), (int) cell.getWidth(), (int) cell.getHeight());
		        	
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
        for (int i = 0; i < virusList.size(); i++) {
        	Virus virus = virusList.get(i);
        	if (virus.isAlive()) {
	        	g.drawRect((int) virus.getX(), (int) virus.getY(), (int) virus.getWidth(), (int) virus.getHeight());
	        	switch (virus.getStrength()) {
		        	case 1:
		        		g.setColor(Color.YELLOW);
		        		break;
			    	case 2:
			    		g.setColor(Color.ORANGE);
			    		break;
				    case 3:
						g.setColor(Color.RED);
						break;
				    case 4:
						g.setColor(Color.DARK_GRAY);
						break;
					case 5:
						g.setColor(Color.BLACK);
						break;
					default:
						g.setColor(Color.YELLOW);
				}
	        	g.fillRect((int) virus.getX(), (int) virus.getY(), (int) virus.getWidth(), (int) virus.getHeight());
        	}
        	else if (!virus.isAlive()) {
        		// Do nothing, do not draw virus if it is dead
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
	 * Starts the game play by setting gameStatus to "playing"
	 */
	public void startGame() {
		
	}
	
	/**
	 * Called after a certain amount of time has passed in the game. 
	 * Starts decrementing the T-cell count. 
	 * After this method is called, the game becomes increasingly difficult
	 * (i.e. the immune system becomes increasingly deficient).
	 * 
	 */
	public void infectHIV() {
		System.out.println("Infected!");
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
	    
	    Random random = new Random();
	    
	    // Initializes virus at the random location each time a new one is introduced
	    Virus newVirus = new Virus(randomNumberX,randomNumberY, INIT_VIRUS_X_SPEED*(random.nextBoolean() ? 1 : -1), INIT_VIRUS_Y_SPEED);
	    	    
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
		if (tCellCount == 750) {
			System.out.println("T Cells at 750");
			for (int i = 0; i < virusList.size(); i++) {
				Virus thisVirus = virusList.get(i);
				System.out.println("Checking alive for " + i);
				if (thisVirus.isAlive()) {
					System.out.println("Changing strength for " + i);
					thisVirus.setStrength(2);
 					virusList.set(i, thisVirus);
				}
			}
		}
		else if (tCellCount == 500) {
			for (int i = 0; i < virusList.size(); i++) {
				Virus thisVirus = virusList.get(i);
				if (thisVirus.isAlive()) {
					thisVirus.setStrength(3);
					virusList.set(i, thisVirus);
				}
			}
		}
		else if (tCellCount == 350) {
			for (int i = 0; i < virusList.size(); i++) {
				Virus thisVirus = virusList.get(i);
				if (thisVirus.isAlive()) {
					thisVirus.setStrength(4);
					virusList.set(i, thisVirus);
				}
			}
		}
		else if (tCellCount == 200) {
			for (int i = 0; i < virusList.size(); i++) {
				Virus thisVirus = virusList.get(i);
				if (thisVirus.isAlive()) {
					thisVirus.setStrength(5);
					virusList.set(i, thisVirus);
				}
			}
		}
		else if (tCellCount == 100) {
			for (int i = 0; i < virusList.size(); i++) {
				Virus thisVirus = virusList.get(i);
				if (thisVirus.isAlive()) {
					thisVirus.setStrength(6);
					virusList.set(i, thisVirus);
				}
			}
		}
	}
	
	
	public void calculate_score() {
		if(Math.abs((System.currentTimeMillis() - gameStartTime) % 2000) < 15) {	
			gameScore = gameScore + 10;
		}
	}

	/**
	 * displayFact()
	 * Obtains String from Fact object and sends it to sidebarPanel 
	 */
	public void displayFact() {
		//Add facts to the side bar panel
		
		
		if (factNo <= hivFacts.getNumOfTips()) {
			sidebarPanel.addTextToPane(hivFacts.getTip(factNo));
			factNo++;
		}
		
		
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
	 * Checks if tCellCount has hit certain fact benchmarks. If benchmarks match, 
	 * add fact to pane.
	 */
	
	public void checkFactBenchmark() {
		if (tCellCount == 900 || tCellCount == 500 || tCellCount == 350 || tCellCount == 200) {
			sidebarPanel.addTextToPane(hivFacts.getFact(tCellCount));
		} 
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
				// Animate objects
				cycle();
		// Code for the timing of the animation is adapted from code in class
		long beforeTime, timeDiff, sleep;
		int cellReduceCounter = 0;

		//Create beforeTime variable
        beforeTime = System.currentTimeMillis();
                
        // Run this while loop by the game is being played
		while(gameStatus == "playing") {
			//if(System.currentTimeMillis)
			// Animate objects
			cycle();
			
			//Call to calculateScore()
			calculateScore();
			
			
			
			// If t-cell count hits 650, prompt user if they want to take antiretrovirals.
			// Timing of when to initiate treatment has been a source of controversy.
			// An NA-ACCORD study observed patients who started antiretroviral 
			// therapy either at a CD4 count of less than 500 versus less than 350 
			// and showed that patients who started ART at lower CD4 counts had a 
			// 69% increase in the risk of death.
			if (tCellCount == 800 && !antiretroviralOffered) {
				// TODO
				// Options for the antiretroviral option dialog box
				Object[] antiretroviralOptions = {"Take antiretrovirals", "Decline treatment"};
				
				// JOptionPane that prompts user, asking whether he/she wants to take antiretroviral treatment.
				// Response is stored in userDecision: 0 is yes, 1 is no.
				int userDecision = JOptionPane.showOptionDialog(null, 
						"Your t-cell count is at 500, and your doctor has offered to put you"
						+ "on antiretroviral treatment.\n"
						+ "Though it has proven successful at controlling HIV,"
						+ "there are also side effects and complications that\n"
						+ "make it risky. Take antiretrovirals?", 
						"Antiretroviral Treatment", JOptionPane.YES_NO_OPTION,  
						JOptionPane.QUESTION_MESSAGE, null, antiretroviralOptions, antiretroviralOptions[0]);
				
				calculate_score();
				
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
				//Set antiretroviralOffered to true
				antiretroviralOffered = true;
			}
			
			
			

			int oneTenthTime = (int) (GAME_WON_TIME / 10); 


			//Progress bar is incremented as the game time progresses
			for(int i = 0; i < 10; i++) {
				if((System.currentTimeMillis() - gameStartTime) > oneTenthTime * i) {
					currentProgressImage = progressImages[i];
				}
			}

			// Calibrate difficulty
			calibrateDifficulty();
			
			//If chosen time is greater than HIV introduction time
			if (System.currentTimeMillis() - gameStartTime > HIV_INTRO_TIME) {
				//If not infected
				if (!infected) {
					//Call to infectHIV()
					infectHIV();
					
					// Infected option dialog box
					Object[] infectedOption = {"OK"};
					
					// JOptionPane that notifies user that he/she has been infected with HIV.
					JOptionPane.showOptionDialog(null, 
							"You have been infected with HIV.", 
							"Infected with HIV", JOptionPane.OK_OPTION,  
							JOptionPane.QUESTION_MESSAGE, null, infectedOption, infectedOption[0]);
					
				}
				
				//Else,
				else if (infected) {
					
					//Increment reduce cell counter
					cellReduceCounter++;
					
					//If it is equal to 7
					if (cellReduceCounter == 7) {
						
						//Decrement t cell count
						tCellCount--;
						
						checkFactBenchmark();
						
						//Set cellReduceCounter to 0
						cellReduceCounter = 0;
					}
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
		
		// Check if the user click on top of an actual virus
				System.out.println("Mouse Clicked! at time " + System.currentTimeMillis());

				for (int i = 0; i < virusList.size(); i++) {

					// Iterate through all viruses
					Virus virus = virusList.get(i);
					int strength = virus.getStrength();
					
					System.out.println("Checking click for virus " + i + " with strength " + strength);

					//Check if click location is within bounds of any virus 
					if (virus.withinVirus(e.getX(), e.getY())) {

					  if(virus.isAlive()) {	
							//System.out.println("Virus clicked");

						if (strength == 1) {
							// If so, and strength is only 1, kill virus
							System.out.println("Virus destroyed");
							virus.setAlive(false);
							gameScore = gameScore + 15;
							repaint();
						}
						else if (strength > 1) {
							// If so, but strength is greater than 1, decrement strength
							int newStrength = strength - 1;
							System.out.println("Virus weakened from " + strength + " to " + newStrength);
							virus.setStrength(newStrength);
							repaint();
						}

						// Replace virus with killed one or one with weaker strength
						virusList.set(i, virus);
						//Break from loop
						break;
					  }
					}		
				}	

>>>>>>> master
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
	
	//obtains String from Fact object
	public void getFact() {
		
	}
}
