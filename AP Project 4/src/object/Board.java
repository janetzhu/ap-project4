package object;

import java.applet.Applet;
import java.awt.*;

import javax.swing.*;

import java.awt.Dimension;
import java.util.ArrayList;

/**
 * JPanel class that contains the portion of the window where the main gameplay occurs
 *
 */

public class Board extends JPanel implements Runnable{

	/******** CLASS VARIABLES ********/
	private int gameHeight;
	private int gameWidth;
	
	private Cell[][] cellList = new Cell[7][3];
	private ArrayList<Virus> virusList = new ArrayList<Virus>();
	private Facts hivFacts;
		
	private String gameStatus;
	
	private int tCellCount;
	private int gameScore;
	
	private Timer gameTimer;
	private int difficultyLevel;
	
	private final int DELAY = 15;
	private final int VIRUS_POS_XMIN = 15;
    private final int VIRUS_POS_XMAX = 700;
    private final int VIRUS_POS_YMIN = 10;
    private final int VIRUS_POS_YMAX = 375;
	
	public Board(int height, int width) {
		initBoard(height, width);
	}
	
	public void initBoard(int height, int width) {
		gameHeight = height;
		gameWidth = width;
		
		setVisible(true);
		
		setPreferredSize(new Dimension(gameWidth, gameHeight));
		setBackground(Color.RED);
		
		// Body Cells are created in a 3 x 10 array toward the bottom of the board
        for (int j = 0; j < 3; j++) {
	        for (int i = 0; i < 7; i++) {
	        	// locations are spaced apart slightly
	        	cellList[i][j] = new Cell(100*i+5,500+60*j,95,50, false);
	        }
        }
        
        introduceVirus();
        
        repaint();
        
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
				
		// Turn on anti-aliasing to smooth out shapes
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(Color.RED);
        
        g2.fillRect(0, 0, gameWidth, gameHeight);
        
        drawCells(g);
        drawViruses(g);
	}
    

	/**
     * Draw the body cells currently in the cellList as rectangles.  Set the color to black to show they're not infected
     * and fill the cells.
     * 
     * @param g the graphics object that will be painted
     */
    private void drawCells(Graphics g) {
    	for (int j = 0; j < 3; j++) {
	        for (int i = 0; i < 7; i++) {
	        	Cell cell = cellList[i][j];
	        	if (!cell.isInfected()) {
		        	g.drawRect((int) cell.getX(), (int) cell.getY(), (int) cell.getWidth(), (int) cell.getHeight());
		        	g.setColor(Color.BLACK);
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
	        	g.setColor(Color.BLACK);
	        	g.fillRect((int) virus.getX(), (int) virus.getY(), (int) virus.getWidth(), (int) virus.getHeight());
        	}
        }
    }
    
    public void introduceVirus() {
	    /* 
	     * Used a random number generator to get random numbers for the initial position of the virus introduced
	     * Inspired by a similar post on stack overflow:
	     * http://stackoverflow.com/questions/363681/generating-random-numbers-in-a-range-with-java
	     */
	    int randomNumberX = VIRUS_POS_XMIN + (int)(Math.random() * ((VIRUS_POS_XMAX - VIRUS_POS_XMIN) + 1));
	    int randomNumberY = VIRUS_POS_YMIN + (int)(Math.random() * ((VIRUS_POS_YMAX - VIRUS_POS_YMIN) + 1));
	    
	    // Initializes virus at the random location each time a new one is introduced
	    Virus newVirus = new Virus(randomNumberX,randomNumberY,1,-1);
	    
	    virusList.add(newVirus);
    }
	
    
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

	public void detectInfection(int virusIndex) {
		Virus thisVirus = virusList.get(virusIndex);
		
		// TODO Check if virus bounds are at the bounds of any of the body cells, if so infect the cell with the virus
		// Then remove virus
    	
    }
    
	public void checkWallCollision(int virusIndex) {
		Virus thisVirus = virusList.get(virusIndex);
		
		// Check the bounds of the game board for a collision between this virus and one of the walls.
		// If there is a collision, change the direction vector of the virus so that it will bounce
    	// off the wall properly.
    	if (thisVirus.getBound(3) >= gameHeight) {
    		// Hit top of board
    		thisVirus.setySpeed(-3);
    	}
    	else if (thisVirus.getBound(2) >= gameWidth) {
    		// Hit right wall
    		thisVirus.setxSpeed(-3);
    	}
    	else if (thisVirus.getBound(4) <= 0) {
    		// Hit left wall
    		thisVirus.setxSpeed(3);
    	}
    	else if (thisVirus.getBound(1) <= 0) {
    		// Hit top of board
    		thisVirus.setySpeed(3);
    	}
    	
    	virusList.set(virusIndex, thisVirus);
	}
    
	public void checkMembraneCollision(int virusIndex) {
		Virus thisVirus = virusList.get(virusIndex);
		
		// TODO Check if virus bounds are at the membrane and change x or y speed accordingly to make it bounce off
    	
    	virusList.set(virusIndex, thisVirus);
	}
	
	public void calibrateDifficulty() {
		// TODO Auto-generated method stub
		
	}
	
	public void displayGameOverMessage() {
		// TODO Auto-generated method stub
		
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
	
	        beforeTime = System.currentTimeMillis();
	        
	        // Run this while loop by the game is being played
			while(gameStatus == "playing") {
				// Animate objects
				cycle();
				
				// Repaint objects
				repaint();
				
				// Calibrate difficulty
				calibrateDifficulty();
				
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
			
			displayGameOverMessage();
		}
		
	}

    
}
