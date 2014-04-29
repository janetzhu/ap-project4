package object;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * The unit tests for VirusTest class. The word test is placed before each 
 * method name of the virus class to indicate the method is being tested. 
 * 
 */

public class VirusTest {

	/**
	 * testVirusConstructor()
	 * Constructor
	 * 
	 * @param none 
	 * @return none
	 */
	@Test
	public void testVirusContructor() {
		//Create Virus object
		Virus v=new Virus();
		
		//Check if x value is zero
		assertEquals(v.getX(),0);
		
		//Check if y value is zero
		assertEquals(v.getY(),0);
		
		//Check if x speed is zero
		assertEquals(v.getxSpeed(),0);
		
		//Check if y speed is zero
		assertEquals(v.getySpeed(),0);
		
		//Check if height is zero
		assertEquals(v.getHeight(),0);
		
		//Check if width is zero
		assertEquals(v.getWidth(),0);
		
		//Check if strength is 1
		assertEquals(v.getStrength(),1);
		
		//Check is virus is alive
		assertEquals(v.isAlive(),true);
	}
	
	/**
	 * testExplicitConstructor()
	 * Default Constructor
	 */
	@Test
	public void testExplicitConstructor() {
		//Create Virus object with inputs
		Virus v=new Virus(2,5,6,7,8,11,13,false);
		
		//Check if x value is 2
		assertEquals(v.getX(),2);
		
		//Check if y value is 5
		assertEquals(v.getY(),5);
		
		//Check if x speed is 6
		assertEquals(v.getxSpeed(),6);
		
		//Check if y speed is 7
		assertEquals(v.getySpeed(),7);
		
		//Check if height is 11
		assertEquals(v.getHeight(),11);
		
		//Check if width is 8
		assertEquals(v.getWidth(),8);
		
		//Check if strength is 13
		assertEquals(v.getStrength(),13);
		
		//Check if virus is not alive
		assertEquals(v.isAlive(),false);	
	}

	/**
	 * testgetX()
	 * Test get method
	 * 
	 * @param none 
	 * @return none
	 */
	@Test 
	public void testgetX() {
		//Create Virus object
		Virus v=new Virus();
		
		//Check if x value is 0
		assertEquals(v.getX(),0);
		
	}
	
	/**
	 * testsetX()
	 * Test set method
	 * 
	 * @param none 
	 * @return none
	 */
	@Test 
	public void testsetX() {
		//Create Virus object
		Virus v=new Virus();
		
		//Set x value to 3
		v.setX(3);
		
		//Check if x value is 3
		assertEquals(v.getX(),3);
	}
	
	/**
	 * testgetY()
	 * Test get method
	 * 
	 * @param none 
	 * @return none
	 */
	@Test 
	public void testgetY() { 
		//Create Virus object
		Virus v=new Virus();
		
		//Check if y value equal to zero
		assertEquals(v.getY(),0);
	}
	
	/**
	 * testsetY()
	 * Test set method
	 * 
	 * @param none 
	 * @return none
	 */
	@Test 
	public void testsetY() {
		//Create Virus object
		Virus v=new Virus();

		//Set y value to 7
		v.setY(7);
		
		//Check if y value is 7
		assertEquals(v.getY(),7);
	}
	
	/**
	 * testgetxSpeed()
	 * Test get method
	 * 
	 * @param none 
	 * @return none
	 */
	@Test 
	public void testgetxSpeed() {
		//Create Virus object
		Virus v=new Virus();
		
		//Check if x speed equal to zero
		assertEquals(v.getxSpeed(),0);
	}
	
	/**
	 * testgetySpeed
	 * 
	 * @param none 
	 * @return none
	 */
	@Test
	public void testgetySpeed() {
		//Create Virus object
		Virus v=new Virus();
		
		//Set y speed to 9
		v.setySpeed(9);
		
		//Check if y speed equal to 9
		assertEquals(v.getySpeed(),9);
	}
	
	/**
	 * testgetWidth()
	 * Test get method
	 * 
	 * @param none 
	 * @return none
	 */
	@Test 
	public void testgetWidth() {
		//Create Virus object
		Virus v=new Virus();
		
		//Check if width equal to zero
		assertEquals(v.getWidth(),0);
	}
	
	/**
	 * testsetWidth()
	 * Test set method
	 * 
	 * @param none 
	 * @return none
	 */
	@Test
	public void testsetWidth() {
		//Create Virus object
		Virus v=new Virus(); 
		
		//Set width to 5
		v.setWidth(5);
		
		//Check if get value equal to 5
		assertEquals(v.getWidth(),5);	
	}
	
	/**
	 * testgetHeight()
	 * Test get method
	 */
	@Test 
	public void testgetHeight() {
		//Create Virus object
		Virus v=new Virus();
		
		//Check if height equal to zero
		assertEquals(v.getHeight(), 0);	
	}
	
	/**
	 * testsetHeight()
	 * Test set method
	 * 
	 * @param none 
	 * @return none
	 */
	@Test
	public void testsetHeight() {
		//Create Virus object
		Virus v=new Virus();
		
		//Set height to 5
		v.setHeight(5);
		
		//Check if get value equal to 5
		assertEquals(v.getHeight(), 5);
	}
	
	/**
	 * testgetStrength()
	 * Test get method
	 * 
	 * @param none 
	 * @return none
	 */
	@Test
	public void testgetStrength() {
		//Create Virus object
		Virus v=new Virus();
		
		//Check if strength equal to 1
		assertEquals(v.getStrength(),1);	
	}
	
	/**
	 * testsetStrength()
	 * Test set method
	 * 
	 * @param none 
	 * @return none
	 */
	@Test 
	public void testsetStrength() {
		//Create Virus object
		Virus v=new Virus();
		
		//Set strength to 7
		v.setStrength(7);
		
		//Check if get value is 7
		assertEquals(v.getStrength(),7);
	}
	
	/**
	 * testisAlive()
	 * Method checks if virus is alive
	 * 
	 * @param none 
	 * @return none
	 */
	@Test 
	public void testisAlive() {
		//Create Virus object
		Virus v=new Virus(); 
		
		//Check if virus is alive
		assertEquals(v.isAlive(),true);
	}
	
	/**
	 * testsetAlive()
	 * Test set method
	 * 
	 * @param none 
	 * @return none
	 */
	@Test 
	public void testsetAlive() {
		//Create Virus object
		Virus v=new Virus();
		
		//Set alive to false
		v.setAlive(false);
		
		//Check if virus is not alive
		assertEquals(v.isAlive(),false);
	}
	
	/**
	 * testgetBound()
	 * Test get method
	 * 
	 * @param none 
	 * @return none
	 */
	@Test 
	public void testgetBound() {
		//Create Virus object with input parameters
		Virus v=new Virus(2,4,6,3,1,0,5,false);
		
		//Check all the bounds of the virus
		assertEquals(4,v.getBound(1));
		assertEquals(3,v.getBound(2));
		assertEquals(4,v.getBound(3));
		assertEquals(2,v.getBound(4));
	}
	
	/**
	 * testanimateVirus()
	 * Method checks if the virus is animated
	 * 
	 * @param none 
	 * @return none
	 */
	@Test
	public void testanimateVirus() {
		//Create Virus object with parameters
		Virus v=new Virus(2,4,6,3,1,0,5,false);
		
		//Call animateVirus()
		v.animateVirus();
		
		//Check if x value is 8
		assertEquals(8,v.getX());
		
		//Check if y value is 7
		assertEquals(7,v.getY());
	}
	
	/**
	 * testwithinVirus()
	 * Method tests if inside the virus
	 * 
	 * 
	 * @param none 
	 * @return none
	 */
	@Test 
	public void testwithinVirus() {
		//Create Virus object
		Virus v=new Virus(4,2,6,1,3,5,0,false);
		
		//Check if inside the virus
		assertEquals(v.withinVirus(6, 5),true);
	}
	
} //END VirusTest
