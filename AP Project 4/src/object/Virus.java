package object;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Contains data and methods associated with an individual Virus object.
 */

public class Virus {
	//Declare private width and height variables
	private final int VIRUS_WIDTH = 60;
	private final int VIRUS_HEIGHT = 60;

	// The x coordinate of the top-left of the virus
	private int xCoord;
	// The y coordinate of the top-left of the virus
	private int yCoord;
	
	// The speed in the x-direction of the virus being animated
	private int xSpeed;
	// The speed in the y-direction of the virus being animated
	private int ySpeed;
	
	// The width of the virus object
	private int width;
	// The height of the virus object
	private int height;
	
	// The strength of the virus (resistance to clicks)
	private int strength;
	// Alive is true if the virus is still alive, and false if it has been destroyed by the user
	private boolean alive;
	
	/**
	 * Virus()
	 * Constructor
	 */
	public Virus() {
		this.setX(0);
		this.setY(0);
		this.setxSpeed(0);
		this.setySpeed(0);
		this.setWidth(0);
		this.setHeight(0);
		this.setStrength(1);
		this.setAlive(true);
	}
	
	/**
	 * Virus()
	 * Default Constructor
	 * @param xCoord
	 * @param yCoord
	 * @param xSpeed
	 * @param ySpeed
	 * @param virusStrength
	 */
	public Virus(int xCoord, int yCoord, int xSpeed, int ySpeed, int virusStrength) {
		this.setX(xCoord);
		this.setY(yCoord);
		this.setxSpeed(xSpeed);
		this.setySpeed(ySpeed);
		this.setWidth(VIRUS_WIDTH);
		this.setHeight(VIRUS_HEIGHT);
		this.setStrength(virusStrength);
		this.setAlive(true);
	}
	
	/**
	 * Virus()
	 * Default Constructor
	 * @param xCoord
	 * @param yCoord
	 * @param xSpeed
	 * @param ySpeed
	 * @param width
	 * @param height
	 * @param virusStrength
	 * @param isAlive
	 */
	public Virus(int xCoord, int yCoord, int xSpeed, int ySpeed, int width, int height, int virusStrength, boolean isAlive) {
		this.setX(xCoord);
		this.setY(yCoord);
		this.setxSpeed(xSpeed);
		this.setySpeed(ySpeed);
		this.setWidth(width);
		this.setHeight(height);
		this.setStrength(virusStrength);
		this.setAlive(isAlive);
	}

	/**
	 * getX()
	 * Get method
	 * @return the xCoord
	 */
	public int getX() {
		return xCoord;
	}

	/**
	 * setX()
	 * Set method
	 * @param xCoord the xCoord to set
	 */
	public void setX(int xCoord) {
		this.xCoord = xCoord;
	}

	/**
	 * getY()
	 * Get method
	 * @return the yCoord
	 */
	public int getY() {
		return yCoord;
	}

	/**
	 * setY()
	 * Set Method
	 * @param yCoord the yCoord to set
	 */
	public void setY(int yCoord) {
		this.yCoord = yCoord;
	}

	/**
	 * getxSpeed()
	 * Get method
	 * @return the xSpeed
	 */
	public int getxSpeed() {
		return xSpeed;
	}

	/**
	 * setxSpeed()
	 * Set method
	 * @param xSpeed the xSpeed to set
	 */
	public void setxSpeed(int xSpeed) {
		this.xSpeed = xSpeed;
	}

	/**
	 * getySpeed()
	 * Get method
	 * @return the ySpeed
	 */
	public int getySpeed() {
		return ySpeed;
	}

	/**
	 * setySpeed()
	 * Set method
	 * @param ySpeed the ySpeed to set
	 */
	public void setySpeed(int ySpeed) {
		this.ySpeed = ySpeed;
	}

	/**
	 * getWidth()
	 * Get method
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * setWidth()
	 * Set method
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * getHeight()
	 * Get method
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * setHeight()
	 * Set method
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * getStrength()
	 * Get method
	 * @return the strength
	 */
	public int getStrength() {
		return strength;
	}

	/**
	 * setStrength()
	 * Set method
	 * @param strength the strength to set
	 */
	public void setStrength(int strength) {
		this.strength = strength;
	}

	/**
	 * isAlive()
	 * Boolean that checks if the body cell is alive
	 * @return the alive
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * setAlive()
	 * Set method
	 * @param alive the alive to set
	 */
	public void setAlive(boolean isAlive) {
		this.alive = isAlive;
	}
	
	/**
	 * getBound()
	 * This method takes in an integer that refers to one of the 4 "sides" of the virus object, and returns the x or y
	 * coordinate of that side as an integer value.  This is used to accommodate for the virus size.
	 * 
	 * The integer values of sideRequested possible are 1 for the top of the virus, 2 for the right side, 3 for the bottom,
	 * and 4 for the left side.
	 * 
	 * @param sideRequested is an integer that refers to the side for which the boundary coordinate has been requested
	 * @return the coordinate value of the side that was requested by the application
	 */
	public int getBound(int sideRequested) {
		switch (sideRequested) {
		case 1: 
			return this.getY();
		case 2:
			return this.getX() + this.getWidth();
		case 3:
			return this.getY() + this.getHeight();
		case 4:
			return this.getX();
		default:
			return 0;
		}
	}

	/**
	 * animateVirus()
	 * This method animates the virus given its current location and the current values of the speed variables by
	 * adding the value of the x and y speed parameters to the current x and y coordinates. Then, when the virus is
	 * reprinted from Board it will appear as if the virus has moved to this new location.
	 */
	public void animateVirus () {
		setX(this.getX() + this.getxSpeed());
    	setY(this.getY() + this.getySpeed());
	}
	
	/**
	 * withinVirus()
	 * Checks if a coordinate is within the bounds of the virus
	 */
	public boolean withinVirus (int x, int y) {
		if (x <= this.getBound(2) && x >= this.getBound(4) && y <= this.getBound(3) && y >= this.getBound(1)) {
			return true;
		}
		else {
			return false;
		}
	}
	
} //END Virus
