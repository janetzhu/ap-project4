package object;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * The unit tests for the Cell Class, each 
 * method name is used with the word test before it to indicate 
 * that the method is being tested. 
 * 
 */

/**
 * CellTest Class
 * @author michaelng
 *
 */
public class CellTest {

	/**
	 * testCellConstructor()
	 * Method that tests the Cell constructor
	 */
	@Test
	public void testCellConstructor() {
		//Create an object of the Cell class
		Cell c= new Cell(); 
		
		//Check if the x value is zero
		assertEquals(c.getX(),0);
		
		//Check if the y value is zero
		assertEquals(c.getY(),0);
		
		//Check if the width is zero
		assertEquals(c.getWidth(),0);
		
		//Check if the height is zero
		assertEquals(c.getHeight(),0);
		
		//Check if the cell is not infected
		assertEquals(c.isInfected(),false);
	}
	
	/**
	 * testCellExplicitConstructor()
	 * Method that tests the default constructor
	 */
	@Test
	public void testCellExplicitConstructor() {
		//Create Cell object with input parameters
		Cell c=new Cell(1,5,3,7,true);
		
		//Check if the x value is 1
		assertEquals(c.getX(),1);
		
		//Check if the y value is 5
		assertEquals(c.getY(),5);
		
		//Check if the width value is 3
		assertEquals(c.getWidth(),3);
		
		//Check if the height value is 7
		assertEquals(c.getHeight(),7);
		
		//Check if the cell is infected
		assertEquals(c.isInfected(),true);
	}
	
	/**
	 * testgetX()
	 * Test get method
	 */
	@Test
	public void testgetX() {
		//Create Cell object
		Cell c=new Cell(); 
		
		//Check if the x value is zero
		assertEquals(c.getX(),0);
	}
	
	/**
	 * testsetX()
	 * Test set method
	 */
	@Test 
	public void testsetX() {
		//Create Cell object
		Cell c=new Cell();
		
		//Set x equal to 6
		c.setX(6);
		
		//Check if the get value is 6
		assertEquals(c.getX(), 6);	
	}
	
	/**
	 * testgetY()
	 * Test get method
	 */
	@Test
	public void testgetY() {
		//Create Cell object
		Cell c=new Cell(); 
		
		//Check if y value is zero
		assertEquals(c.getY(),0);
	}
	
	/**
	 * testsetY()
	 * Test set method
	 */
	@Test 
	public void testsetY() {
		//Create Cell object
		Cell c=new Cell(); 
		
		//Set y value to 3
		c.setY(3);
		
		//Checkif get value is 3
		assertEquals(c.getY(),3);
		
	}
	
	/**
	 * testgetWidth()
	 * Test get method
	 */
	@Test 
	public void testgetWidth() {
		//Create Cell object
		Cell c=new Cell(); 
		
		//Check if width is zero
		assertEquals(c.getWidth(),0);
		
	}
	
	/**
	 * testsetWidth()
	 * Set method
	 */
	@Test 
	public void testsetWidth() {
		//Create Cell object
		Cell c=new Cell(); 
		
		//Set width equal to 9
		c.setWidth(9);
		
		//Check if get value is 9
		assertEquals(c.getWidth(),9);	
	}
	
	/**
	 * testgetHeight()
	 * Test get method
	 */
	@Test
	public void testgetHeight() {
		//Create Cell object
		Cell c=new Cell(); 
		
		//Check if height is zero
		assertEquals(c.getHeight(),0);
		
	}
	
	/**
	 * testsetHeight()
	 * Test set method
	 */
	@Test
	public void testsetHeight() {
		//Create Cell object
		Cell c=new Cell(); 
		
		//Set height equal to 10
		c.setHeight(10);
		
		//Check if get value is 10
		assertEquals(c.getHeight(),10);
		
	}
	
	/**
	 * testisInfected
	 * Method that checks if cell is infected
	 */
	@Test 
	public void testisInfected() {
		//Create Cell object
		Cell c=new Cell(); 
		
		//Check if the cell is not infected
		assertEquals(c.isInfected(),false);
	}
	
	/**
	 * testsetInfected()
	 * Test set method
	 */
	@Test 
	public void testsetInfected() {
		//Create Cell object
		Cell c= new Cell();
		
		//Set infected to true
		c.setInfected(true);
		
		//Check if get value is true
		assertEquals(c.isInfected(),true);
	}
	
	/**
	 * testgetBound()
	 * Test get method
	 */
	@Test
	public void testgetBound() {
		//Create Cell object with input parameters
		Cell c=new Cell(2,5,6,7,true); 
		
		//Check all bounds of cell
		assertEquals(5, c.getBound(1));
		
		assertEquals(8,c.getBound(2));
		
		assertEquals(12,c.getBound(3));
		
		assertEquals(2,c.getBound(4));
	}
	
	/**
	 * testdecrementBodyCell()
	 * Method that confirms that body cell count decrements
	 */
	@Test
	public void testdecrementBodyCell() {
		//Create Cell object
		Cell c = new Cell(); 
		
		//Call decrementBodyCellCount()
		c.decrementBodyCellCount();
		
		//Check if equal to value 17
		assertEquals(17,c.bodyCellCount);	
	}
	
} //END CellTest
