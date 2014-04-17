package object;

/**
 * Contains data and methods associated with an individual Virus object.
 */

public class Virus {

	// Location of the virus - x and y-coordinates
	private int xCoord, yCoord;
	// Velocity - x and y-velocity
	private int xSpeed, ySpeed;
	// Dimensions of the Virus object
	private int width;
	private int height;
	// Strength of the virus - determines how difficult it is to kill
	private int strength;
	// Whether the virus is alive, or has been killed 
	private boolean alive;
	
	public Virus() {
		// TODO Auto-generated constructor stub
	}

}
