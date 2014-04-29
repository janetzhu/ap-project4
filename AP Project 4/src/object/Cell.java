package object;


/**
 * Contains data and methods associated with an individual Cell object.
 * Includes data about the location (coordinates) and size of the cell, and
 * whether it has been infected with HIV.
 *
 */

public class Cell {

	/******** CLASS VARIABLES ********/
	// The x coordinate of the top-left corner of the cell
	private int xCoord;
	// The x coordinate of the top-left corner of the cell
	private int yCoord;
	
	// The width of the cell
	private int width;
	// The height of the cell
	private int height;
	
	// Infected is true if the cell has been infected by a virus or disease, false if healthy
	private boolean infected;
	
	//Body cell count 
	public int bodyCellCount = 18; 
	
	
	/******** METHODS ********/
	
	/**
	 * Cell()
	 * Constructor. Initializes variables to 0 and false
	 */
	
	public Cell() {
		xCoord = 0;
		yCoord = 0;
		width = 0;
		height = 0;
		infected = false;
	}
	
	/**
	 * Cell()
	 * Default constructor initializes variables with new variables
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param infected
	 */
	public Cell(int x, int y, int width, int height, boolean infected) {
		this.xCoord = x;
		this.yCoord = y;
		this.width = width;
		this.height = height;
		this.infected = infected;
	}
	
	/**
	 * getX()
	 * Get method
	 * @return xCoord
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
	 * @return yCoord
	 */
	public int getY() {
		return yCoord;
	}

	/**
	 * setY()
	 * Set method
	 * @param yCoord the yCoord to set
	 */
	public void setY(int yCoord) {
		this.yCoord = yCoord;
	}

	/**
	 * getWidth()
	 * Get method
	 * @return width
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
	 * @return height
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
	 * isInfected()
	 * @return infected
	 */
	public boolean isInfected() {
		return infected;
	}

	/**
	 * setInfected()
	 * Set method
	 * @param infected the infected to set
	 */
	public void setInfected(boolean infected) {
		this.infected = infected;
	}
	
	/**
	 * getBound()
	 * Get method
	 * This method takes in an integer that refers to one of the 4 "sides" of the cell object, and returns the x or y
	 * coordinate of that side as an integer value.  This is used to accommodate for the cell size.
	 * 
	 * The integer values of sideRequested possible are 1 for the top of the cell, 2 for the right side, 3 for the bottom,
	 * and 4 for the left side.
	 * 
	 * @param sideRequested is an integer that refers to the side for which the boundary coordinate has been requested
	 * @return the coordinate value of the side that was requested by the application
	 */
	public int getBound(int sideRequested) {
		//Create switch statement
		switch (sideRequested) {
		
		case 1: 
			// Top
			return getY();
		case 2:
			//Right
			return getX() + getWidth();
		case 3:
			//Bottom
			return getY() + getHeight();
		case 4:
			//Left
			return getX();
		default:
			return 0;
		}
	}
	
	/**
	 * decrementBodyCellCount()
	 * Decrements body cell count when called
	 */
	public void decrementBodyCellCount() { 
			
		bodyCellCount--; 
			
	}
	
} //END Cell 
