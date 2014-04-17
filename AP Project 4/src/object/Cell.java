package object;


/**
 * Contains data and methods associated with an individual Cell object.
 *
 */

public class Cell {

<<<<<<< HEAD
	// The x coordinate of the top-left corner of the cell
	private int xCoord;
	// The x coordinate of the top-left corner of the cell
	private int yCoord;
	
	// The width of the cell
=======
	// Location of the Cell - x and y-coordinates
	private int xCoord, yCoord;
	// Width and height of the cell
>>>>>>> FETCH_HEAD
	private int width;
	// The height of the cell
	private int height;
	// Boolean that holds whether a cell has been infected by a virus or not
	private boolean infected;
	
	// Infected is true if the cell has been infected by a virus or disease, false if healthy
	private boolean infected;
	
	public Cell() {
		this.setX(0);
		this.setY(0);
		this.setWidth(0);
		this.setHeight(0);
		this.setInfected(false);
	}
	
	public Cell(int x, int y, int width, int height, boolean infected) {
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
		this.setInfected(infected);
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
	 * @return the infected
	 */
	public boolean isInfected() {
		return infected;
	}

	/**
	 * @param infected the infected to set
	 */
	public void setInfected(boolean infected) {
		this.infected = infected;
	}
	
	/**
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
		switch (sideRequested) {
		case 1: 
			return getY();
		case 2:
			return getX() + getWidth();
		case 3:
			return getY() + getHeight();
		case 4:
			return getX();
		default:
			return 0;
		}
	}
	
}
