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

public class Board extends JPanel {

	/******** CLASS VARIABLES ********/
	private int gameHeight;
	private int gameWidth;
	
	ArrayList<Cell> cellList;
	ArrayList<Virus> virusList;
	Facts hivFacts;
	String gameStatus;
	int tCellCount;
	int gameScore;
	Timer gameTimer;
	int difficultyLevel;
	
	public Board(int height, int width) {
		gameHeight = height;
		gameWidth = width;
		
		setVisible(true);
		
		setPreferredSize(new Dimension(gameWidth, gameHeight));
		setBackground(Color.RED);
		
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		
		// Turn on anti-aliasing to smooth out shapes
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(Color.RED);
        
        g2.fillRect(0, 0, gameWidth, gameHeight);
	}

}
