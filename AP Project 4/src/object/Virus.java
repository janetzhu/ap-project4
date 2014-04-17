package object;

/**
 * Contains data and methods associated with an individual Virus object.
 */

public class Virus {

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
	
	public Virus() {
		this.setX(0);
		this.setY(0);
		this.setxSpeed(0);
		this.setxSpeed(0);
		this.setWidth(0);
		this.setHeight(0);
		this.setAlive(true);
	}
	
	public Virus(int xCoord, int yCoord, int xSpeed, int ySpeed) {
		this.setX(xCoord);
		this.setY(yCoord);
		this.setxSpeed(xSpeed);
		this.setxSpeed(ySpeed);
		this.setWidth(20);
		this.setHeight(20);
		this.setAlive(true);
	}
	
	public Virus(int xCoord, int yCoord, int xSpeed, int ySpeed, int width, int height, boolean isAlive) {
		this.setX(xCoord);
		this.setY(yCoord);
		this.setxSpeed(xSpeed);
		this.setxSpeed(ySpeed);
		this.setWidth(width);
		this.setHeight(height);
		this.setAlive(isAlive);
	}

	/**
	 * @return the xCoord
	 */
	public int getX() {
		return xCoord;
	}

	/**
	 * @param xCoord the xCoord to set
	 */
	public void setX(int xCoord) {
		this.xCoord = xCoord;
	}

	/**
	 * @return the yCoord
	 */
	public int getY() {
		return yCoord;
	}

	/**
	 * @param yCoord the yCoord to set
	 */
	public void setY(int yCoord) {
		this.yCoord = yCoord;
	}

	/**
	 * @return the xSpeed
	 */
	public int getxSpeed() {
		return xSpeed;
	}

	/**
	 * @param xSpeed the xSpeed to set
	 */
	public void setxSpeed(int xSpeed) {
		this.xSpeed = xSpeed;
	}

	/**
	 * @return the ySpeed
	 */
	public int getySpeed() {
		return ySpeed;
	}

	/**
	 * @param ySpeed the ySpeed to set
	 */
	public void setySpeed(int ySpeed) {
		this.ySpeed = ySpeed;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the strength
	 */
	public int getStrength() {
		return strength;
	}

	/**
	 * @param strength the strength to set
	 */
	public void setStrength(int strength) {
		this.strength = strength;
	}

	/**
	 * @return the alive
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * @param alive the alive to set
	 */
	public void setAlive(boolean isAlive) {
		this.alive = isAlive;
	}
	
	/**
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
	 * This method animates the virus given its current location and the current values of the speed variables by
	 * adding the value of the x and y speed parameters to the current x and y coordinates. Then, when the virus is
	 * reprinted from Board it will appear as if the virus has moved to this new location.
	 */
	public void animateVirus () {
		setX(this.getX() + this.getxSpeed());
    	setY(this.getY() + this.getySpeed());
	}
	
}
