package object;

import java.applet.Applet;
import java.awt.*;

import javax.swing.*;

import java.awt.Dimension;
import java.util.ArrayList;

//JPanel class that contains the portion of the window where the main gameplay occurs

public class Board extends JPanel {

	private int gameHeight;
	private int gameWidth;
	
	//Class variables
	private ArrayList<Cell> cellList;
	private ArrayList<Virus> virusList;
	private Facts hivFacts;
	private String gameStatus;
	private int tCellCount;
	private int gameScore;
	private Timer gameTimer;
	private int difficultyLevel;
	
	public Board(int height, int width) {
		gameHeight = height;
		gameWidth = width;
		
		setVisible(true);
		
		setPreferredSize(new Dimension(gameWidth, gameHeight));
		setBackground(Color.RED);
		
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		
		//turn on anti-aliasing to smooth out shapes
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(Color.RED);
	}

	
	public void initTimer() {
		
	}
	
	public void startGame() {
		
	}
	
	public void drawVirus() {
		
	}
	
	public void infectHIV() {
		
	}
	
	public void drawCells() {
		
	}
	
	public void getGameStatus() {
		
	}
	
	public void setGameStatus(String status) {
		
	}
	public void mouseClicked() {
		
	}
	
	public void cycle() {
		
	}
	
	public void detectInfection() {
		
	}
	
	public void calibrateDifficulty() {
		
	}
	public void introduceVirus() {
		
	}
	
	public void checkWallCollision() {
		
	}
	
	public void checkMembraneCollision() {
		
	}
	
	//obtains String from Fact object
	public void getFact() {
		
	}
}
