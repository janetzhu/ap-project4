package object;

import static org.junit.Assert.*;

import org.junit.Test;

/*
 * The unit tests for the Cell Class, each 
 * method name is used with the word test before it to indicate 
 * that the method is being tested. 
 * 
 */

public class CellTest {

	@Test
	public void testCellConstructor() {
		
		Cell c= new Cell(); 
		
		assertEquals(c.getX(),0);
		
		assertEquals(c.getY(),0);
		
		assertEquals(c.getWidth(),0);
		
		assertEquals(c.getHeight(),0);
		
		assertEquals(c.isInfected(),false);
	}
	
	
	@Test
	public void testCellExplicitConstructor() {
		
		Cell c=new Cell(1,5,3,7,true);
		
		assertEquals(c.getX(),1);
		
		assertEquals(c.getY(),5);
		
		assertEquals(c.getWidth(),3);
		
		assertEquals(c.getHeight(),7);
		
		assertEquals(c.isInfected(),true);
		
	}
	
	
	@Test
	public void testgetX() {
		
		Cell c=new Cell(); 
		
		assertEquals(c.getX(),0);
		
	}
	
	@Test 
	public void testsetX() {
		
		Cell c=new Cell();
		
		c.setX(6);
		
		assertEquals(c.getX(), 6);
		
	}
	
	@Test
	public void testgetY() {
		
		Cell c=new Cell(); 
		
		assertEquals(c.getY(),0);
		
	}
	
	@Test 
	public void testsetY() {
		
		Cell c=new Cell(); 
		
		c.setY(3);
		
		assertEquals(c.getY(),3);
		
	}
	
	
	@Test 
	public void testgetWidth() {
		
		Cell c=new Cell(); 
		
		assertEquals(c.getWidth(),0);
		
	}
	
	@Test 
	public void testsetWidth() {
		
		Cell c=new Cell(); 
		
		c.setWidth(9);
		
		assertEquals(c.getWidth(),9);
		
	}
	
	@Test
	public void testgetHeight() {
		
		Cell c=new Cell(); 
		
		assertEquals(c.getHeight(),0);
		
	}
	
	@Test
	public void testsetHeight() {
		
		Cell c=new Cell(); 
		
		c.setHeight(10);
		
		assertEquals(c.getHeight(),10);
		
	}
	
	@Test 
	public void testisInfected() {
		
		Cell c=new Cell(); 
		
		assertEquals(c.isInfected(),false);
		
	}
	
	
	@Test 
	public void testsetInfected() {
		
		Cell c= new Cell();
		
		c.setInfected(true);
		
		assertEquals(c.isInfected(),true);
		
		
	}
	
	@Test
	public void testgetBound() {
		
		Cell c=new Cell(2,5,6,7,true); 
		
		assertEquals(5, c.getBound(1));
		
		assertEquals(8,c.getBound(2));
		
		assertEquals(12,c.getBound(3));
		
		assertEquals(2,c.getBound(4));
		
		
	}
	
	
	@Test
	public void testdecrementBodyCell() {
		
		Cell c = new Cell(); 
		
		c.decrementBodyCellCount();
		
		assertEquals(17,c.bodyCellCount);
		
	}
	
	

}
