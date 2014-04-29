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
import java.util.concurrent.CountDownLatch;

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
    
    private final int START_VIRUS_COUNT = 1; //number of viruses at start of game
    private final int START_TCELL_COUNT = 1000;
    private final int START_DIFFICULTY_LEVEL = 1;
    private final long GAME_WON_TIME = 60000;
    private final long HIV_INTRO_TIME = 10000;
    
    private final int LEVEL_2_BENCHMARK = 950;
    private final int LEVEL_3_BENCHMARK = 750;
    private final int LEVEL_4_BENCHMARK = 600;
    private final int LEVEL_5_BENCHMARK = 500;
    private final int LEVEL_6_BENCHMARK = 400;
    
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
    
    //Game variables
    private long gameStartTime;
	private int gameHeight;
	private int gameWidth;

	//More resource objects
	private Cell[][] cellList = new Cell[CELL_COLUMNS][CELL_ROWS];
	private BufferedImage[][] cellImages = new BufferedImage[CELL_COLUMNS][CELL_ROWS];
	private BufferedImage[][] infectedCellImages = new BufferedImage[CELL_COLUMNS][CELL_ROWS];
	private ArrayList<Virus> virusList = new ArrayList<Virus>();
	public Virus thisVirus=new Virus();

	//Declare new instance of Facts()
	private Facts hivFacts = new Facts();

	//Declare game status variable
	private String gameStatus;

	//Game variables
	private int tCellCount;
	private int gameScore;
	private int difficultyLevel;

	//Declare boolean infected variable
	private boolean infected;

	//Declare cell counter
	private int cellCounter;

	//Declare boolean antiretroviralOffered
	private boolean antiretroviralOffered = false;

	// Fact to display
	private int factNo = 0; 

	/**
	 * Board()
	 * Constructor
	 * @param height
	 * @param width
	 */
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


	 /**
	  * initBoard()
	  * Method called from InvasionGame class to start the game play
	  * Sends SidebarPanel object as a parameter to be able to add facts and information as the game progresses
	  * @param sidebar
	  */
	 public void initBoard(SidebarPanel sidebar) {
		//Set visible to true
		setVisible(true);

		//Set size to game dimensions
		setPreferredSize(new Dimension(gameWidth, gameHeight));

		//Set background color
		setBackground(Color.GRAY);

		//Add listener
		this.addMouseListener(this);

		//Set infected to false
		infected = false;

		//Set up cell counter
		cellCounter = CELL_ROWS * CELL_COLUMNS;

		//Initialize game score
		gameScore = 0;

		//Initialize t cell count
		tCellCount = START_TCELL_COUNT;

		//Initialize difficulty level
		difficultyLevel = START_DIFFICULTY_LEVEL;

		//Initialize side bar panel
		sidebarPanel = sidebar;

		// Body Cells are created in a 3 x 6 array toward the bottom of the board
        for (int j = 0; j < CELL_ROWS; j++) {
	        for (int i = 0; i < CELL_COLUMNS; i++) {
	        	// locations are spaced apart slightly
	        	cellList[i][j] = new Cell((CELL_WIDTH + 5) * i + 10, 450 + (CELL_HEIGHT + 10) * j,CELL_WIDTH, CELL_HEIGHT, false);
	        }
        }
        
        //Introduce virus as long as less than start virus count
        for(int i = 0; i < START_VIRUS_COUNT; i++){
        	introduceVirus();
        }
               
        //Call to loadResources()
        loadResources();
        
        //Set current progress image
        currentProgressImage = progressImages[0];
        
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
        
        //Start the game play thread
    	gamePlayThread.start();
    }

    public void stop() {}
    
    public void destroy() {}
    
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
    	
    	// Reset facts
    	factNo = 0;
    	
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
			//Call game over/won images
			gameOverImage = ImageIO.read(getClass().getResource("/game_over.png"));
			gameWonImage = ImageIO.read(getClass().getResource("/game_won.png"));

			//Load progress bar images
			for(int i = 1; i <= 10; i++) {
				String imagePath = "/progress_images/progress_bar_" + i + ".png";
				progressImages[i-1] = ImageIO.read(getClass().getResource(imagePath));
			}

			//Create body cells
			bodyCells = new BufferedImage[4];

			//Create for loop of body cells
			for(int i = 1; i <= 4; i++) {
				String imagePath = "/cell_images/body_cell" + i + ".png";
				bodyCells[i-1] = ImageIO.read(getClass().getResource(imagePath));
			}

			//Create infected cells
			infectedCells = new BufferedImage[4];

			//Create for loop of infected cells
			for(int i = 1; i <= 4; i++) {
				String imagePath = "/cell_images/infected_cell" + i + ".png";
				infectedCells[i-1] = ImageIO.read(getClass().getResource(imagePath));
			}

			//Create for loop of viruses
			for(int i = 1; i <= 6; i++) {
				String imagePath = "/cell_images/virus" + i + ".png";
				virusImages[i-1] = ImageIO.read(getClass().getResource(imagePath));
			}

		} catch (IOException e) {
			System.out.println("Error loading images");
		}
    }

	/**
	 * initCells()
	 * Initialize all cell images by randomly choosing from four image options for each index
	 */
	public void initCells() {
		int randInt;

		//Create for loop of cell images
		for (int j = 0; j < CELL_ROWS; j++) {
	        for (int i = 0; i < CELL_COLUMNS; i++) {

	        	//Create randInt variable
	        	randInt = (int)(Math.random() * 4);
	        	cellImages[i][j] = bodyCells[randInt];
	        	infectedCellImages[i][j] = infectedCells[randInt];
	        }
		}
	}
    
	/**
	 * paintComponent()
	 * Re-Paints the objects to the screen
	 * @param g
	 */
	public void paintComponent (Graphics g) {
		//Create graphics2d object
		Graphics2D g2 = (Graphics2D)g;

		// Turn on anti-aliasing to smooth out shapes
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        //Create background object
        BufferedImage background;
        
        //Create try/catch block
        try {
    		//load background image
    		background = ImageIO.read(getClass().getResource("/liver_cells_bg.png"));
    		g2.drawImage(background,0,0,this);
    		
        } catch(IOException exception) {
        	System.out.println("Error loading image");
        }
        
        //Draw the cells and viruses
        drawCells(g);
        drawViruses(g);
        
        //the score board 
        g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        g2.setColor(Color.WHITE);
        g2.drawString("Score: " + gameScore, 10, 35);
        
        //Progress Bar

        g2.drawImage(currentProgressImage, 135, 17, this);
        
        //the T-Cell counter 
        g2.drawString("T-Cells Remaining: " + tCellCount, 360 , 35);
        
	}
   
	/**
	 * 
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
		        	g2.drawImage(cellImages[i][j], (int) cell.getX(), (int) cell.getY(), this);
	        	}
	        	if (cell.isInfected()) {
		        	g2.drawImage(infectedCellImages[i][j], (int) cell.getX(), (int) cell.getY(), this);

	        	}
	        }
        }
    }
    
    /**
     * drawViruses()
     * Draw the viruses currently in the virusList as rectangles.
     * @param g the graphics object that will be painted
     */
    private void drawViruses(Graphics g) {
    	//Create graphics2d object
    	Graphics2D g2 = (Graphics2D) g;
    	
    	//Create for loop of virusList
        for (int i = 0; i < virusList.size(); i++) {
        	Virus virus = virusList.get(i);
        	//Only draw virus if it is alive
        	if (virus.isAlive()) {
	        	g2.drawImage(virusImages[virus.getStrength()-1], virus.getX(), virus.getY(), this);
        	}
        }
    }
    
    /**
     * mousePressed()
	 * Detects user mouse clicks.
	 */
	public void mousePressed(MouseEvent e) {

	}

	/**
	 * infectHIV()
	 * Called after a certain amount of time has passed in the game. 
	 * Starts decrementing the T-cell count. 
	 * After this method is called, the game becomes increasingly difficult
	 * (i.e. the immune system becomes increasingly deficient).
	 * 
	 */
	public void infectHIV() {
		//Set infected to true
		infected = true;

		//Add infection text to sidebar panel
		sidebarPanel.addTextToPane("You have been infected with HIV!");

		// JOptionPane that notifies user that he/she has been infected with HIV.
		JOptionPane.showMessageDialog(this, 
				"You have been infected with HIV.", 
				"Infected with HIV", JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * getGameStatus()
	 * Get method
	 * Returns the game status.
	 * @return gameStatus
	 */
	public String getGameStatus() {
		return gameStatus;
	}

	/**
	 * setGameStatus()
	 * Set method
	 * Sets the game status.
	 * @param status
	 */
	public void setGameStatus(String status) {
		this.gameStatus = status;
	}
    
	/**
	 * introduceVirus()
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

	    //If difficulty level is greater than 1
	    if (difficultyLevel > 1) {

	    	//Set minDifficulty
	    	int minDifficulty = difficultyLevel - 3;

	    	//If minDiffuclty is less than 1
	    	if (minDifficulty < 1) {

	    		//Set minDiffulty equal to 1
	    		minDifficulty = 1;
	    	}

	    	//Create a random difficulty
		    randomNumberDifficulty = minDifficulty + (int)(Math.random() * ((difficultyLevel - minDifficulty) + 1));
	    }

	    //Else
	    else {

	    	//Set random difficulty to difficulty level
	    	randomNumberDifficulty = difficultyLevel;
	    }

	    //Create new instance of Random()
	    Random random = new Random();

	    // Initializes virus at the random location each time a new one is introduced
	    Virus newVirus = new Virus(randomNumberX,randomNumberY, INIT_VIRUS_X_SPEED*(random.nextBoolean() ? 1 : -1), INIT_VIRUS_Y_SPEED, randomNumberDifficulty);

	    //Add new virus to virus list
	    virusList.add(newVirus);
    }

    /**
     * cycle()
	 * Handles the animation and collision checking for viruses while playing game.
	 */
    public void cycle() {

		// Animate Viruses and check for collisions or infections
    	
    	//Create for loop for virus list
		for (int i = 0; i < virusList.size(); i++) {
        	Virus virus = virusList.get(i);
        	
        	//If virus is alive
        	if (virus.isAlive()) {
        		
        		//Animate virus
	        	virus.animateVirus();

	        	//Call detect infection
	        	detectInfection(i);

	        	//Call check wall collision
	    		checkWallCollision(i);

	    		//Call check membrane collision
	    		checkMembraneCollision(i);
        	}
        }
	}

    /**
     * detectInfection()
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

    	        		//Establish cell bounds
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
	 * checkWallCollision()
	 * Handles the collision of viruses with the four 'walls' of the board.
	 */
	public void checkWallCollision(int virusIndex) {
		 thisVirus = virusList.get(virusIndex);

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
    	
    	//Set the current virus in the virus list
    	virusList.set(virusIndex, thisVirus);
	}
    
	/**
	 * checkMembraneCollision()
	 * Handles collision of viruses with the membrane.
	 */
	public void checkMembraneCollision(int virusIndex) {
		Virus thisVirus = virusList.get(virusIndex);

		// TODO Check if virus bounds are at the membrane and change x or y speed accordingly to make it bounce off
    	//Set the current virus in the virus list
    	virusList.set(virusIndex, thisVirus);
	}

	/**
	 * calibrateDifficulty()
	 * Re-calibrates the difficulty of the game (number of clicks needed to kill a virus).
	 * Responds to changes in T-cell count.
	 * 
	 */
	public void calibrateDifficulty() {
		// Based on level of t cell count, loop through all viruses 
		//and increase strength respectively if need be
		if (tCellCount == LEVEL_2_BENCHMARK) {
			difficultyLevel = 2;
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

	/**
	 * calculateScore()
	 * Increments the game score + 10 as time progresses
	 */
	public void calculateScore() {
		//If timer less than given time
		if(Math.abs((System.currentTimeMillis() - gameStartTime) % 2000) < 15) {	
			//Increment game score
			gameScore = gameScore + 10;
		}
	}

	/**
	 * displayFact()
	 * Obtains String from Fact object and sends it to sidebarPanel 
	 */
	public void displayFact() {
		//Add facts to the side bar panel
		// and display popup

		
		if (factNo <= hivFacts.getNumOfTips()) {

			sidebarPanel.addTextToPane(hivFacts.getTip(factNo));


			// JOptionPane that pops up a message dialog displaying the fact.
			JOptionPane.showMessageDialog(this, 
					hivFacts.getTip(factNo), 
					"Fast Fact #" + (factNo + 1), JOptionPane.PLAIN_MESSAGE);

			factNo++;

		}


	}

	/**
	 * useAntiretrovirals()
	 * Method that increases t cell count
	 */
	public void useAntiretrovirals() {
		//Increment t cell count
		tCellCount += 50;
	}


	/**
	 * displayGameOverMessage()
	 * Edits the gameOverPanel JPanel object
	 */
	public void displayGameOverMessage() {
		//If game status is game over, 
		if (getGameStatus() == "gameOver") {
			// Show game lost screen
			InvasionGame.changeDisplayPanel("Game Over");
			System.out.println("LOST!");
		}
		//If game status is game won,
		else if (getGameStatus() == "gameWon") {
			// Show game won screen
			InvasionGame.changeDisplayPanel("Game Won");
			System.out.println("WON!");
		}		
	}

	/**
	 * Dialog box that propts user if they want to take antiretrovirals.
	 * If t-cell count hits 650, prompt user if they want to take antiretrovirals.
	 * Timing of when to initiate treatment has been a source of controversy.
	 * An NA-ACCORD study observed patients who started antiretroviral 
	 * therapy either at a CD4 count of less than 500 versus less than 350 
	 * and showed that patients who started ART at lower CD4 counts had a 
	 * 69% increase in the risk of death.
	 */

	public void displayAntiretroviralsDialog() {

		// Options for the antiretroviral option dialog box
		Object[] antiretroviralOptions = {"Take antiretrovirals", "Decline treatment"};

		// JOptionPane that prompts user, asking whether he/she wants to take antiretroviral treatment.
		// Response is stored in userDecision: 0 is yes, 1 is no.
		int userDecision = JOptionPane.showOptionDialog(this, 
				"Your t-cell count is at 500, and your doctor has offered to put you"
				+ "on antiretroviral treatment.\n"
				+ "Though it has proven successful at controlling HIV,"
				+ "there are also side effects and complications that\n"
				+ "make it risky. Take antiretrovirals?", 
				"Antiretroviral Treatment", JOptionPane.YES_NO_OPTION,  
				JOptionPane.QUESTION_MESSAGE, null, antiretroviralOptions, antiretroviralOptions[0]);

		//If user decides to use antiretrovirals,
		if (userDecision == 0) {
			sidebarPanel.addTextToPane("You have chosen to take antiretrovirals. "
					+ "A successful round of treatment increased your t-cell "
					+ "count by 50.");

			useAntiretrovirals();
		}

		//Else
		else {
			sidebarPanel.addTextToPane("You have opted out of taking antiretrovirals."
					+ " Even though antiretroviral treatment comes with risks, it has proven"
					+ "effective in managing HIV.");
		} 

		//Set antiretroviralOffered to true
		antiretroviralOffered = true;

	} // end displayAntiretroviralsDialog method

	/**
	 * Checks if tCellCount has hit certain fact benchmarks. If benchmarks match, 
	 * add fact to pane.
	 */

	public void checkFactBenchmark() {
		if (tCellCount == 900 || tCellCount == 500 || tCellCount == 350 || tCellCount == 200) {

			sidebarPanel.addTextToPane(hivFacts.getFact(tCellCount));

			// JOptionPane that pops up a message dialog displaying the fact.
						JOptionPane.showMessageDialog(this, 
								hivFacts.getFact(tCellCount),
								"Fast Fact: T-Cell Count " + tCellCount, JOptionPane.PLAIN_MESSAGE);
		} 
	}

    /**
     * run()
     * Run method for the thread that plays the game
     */
	@Override
	public void run() {
		//Infinite loop, game runs until user x's out of the window.
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


			// If t-cell count is 800 and antiretrovirals have never been offered before
			// display the antiretrovirals dialog
			if (tCellCount == 800 && !antiretroviralOffered) {
				displayAntiretroviralsDialog();
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


				}

				//Else,
				else if (infected) {

					//Increment reduce cell counter
					cellReduceCounter++;

					//If it is equal to 7
					if (cellReduceCounter == 7) {

						//Decrement t cell count
						tCellCount--;

						// Check to see if reduced T-cell count aligns with a
						// fact benchmark
						checkFactBenchmark();

						//Set cellReduceCounter to 0
						cellReduceCounter = 0;
					}
				}
			}


			//If certain amount of time has passed,
			if (Math.abs((System.currentTimeMillis() - gameStartTime) % 2000) < 4) {

				//Call to displayFact()

				displayFact();

			}

			//If certain amount of time has passed,
			if (Math.abs((System.currentTimeMillis() - gameStartTime) % 2000) < 20) {

				//Call to introduceVirus()
				introduceVirus();
			}

			//Check game timer, if > time value, end game
			if (cellCounter == 0) {
				setGameStatus("gameOver");
			}

			//If user makes it a certain amount of time, win game
			if (System.currentTimeMillis() - gameStartTime > GAME_WON_TIME) {
				setGameStatus("gameWon");
			}

			// Repaint objects
			repaint();
			setDoubleBuffered(true);

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

		//Call to displayGameOverMessage()
		displayGameOverMessage();
    	
		//Call to repaint() screen
		repaint();

		//Call to repaint() and lightenSidebar()
		sidebarPanel.lightenSidebar();
    	sidebarPanel.repaint();

    	//Call to countDown()
		latch.countDown();	

		}

	@Override
	public void mouseClicked(MouseEvent e) {
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
