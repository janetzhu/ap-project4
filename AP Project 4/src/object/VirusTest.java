package object;

import static org.junit.Assert.*;

import org.junit.Test;

public class VirusTest {

	@Test
	public void testVirusContructor() {
		
		Virus v=new Virus();
		
		assertEquals(v.getX(),0);
		
		assertEquals(v.getY(),0);
		
		assertEquals(v.getxSpeed(),0);
		
		assertEquals(v.getySpeed(),0);
		
		assertEquals(v.getHeight(),0);
		
		assertEquals(v.getWidth(),0);
		
		assertEquals(v.getStrength(),1);
		
		assertEquals(v.isAlive(),true);
		
	}
	
	
	@Test
	public void testExplicitConstructor() {
		
		Virus v=new Virus(2,5,6,7,8,11,13,false);
		
		assertEquals(v.getX(),2);
		
		assertEquals(v.getY(),5);
		
		assertEquals(v.getxSpeed(),6);
		
		assertEquals(v.getySpeed(),7);
		
		assertEquals(v.getHeight(),11);
		
		assertEquals(v.getWidth(),8);
		
		assertEquals(v.getStrength(),13);
		
		assertEquals(v.isAlive(),false);
		
	}

	@Test 
	public void testgetX() {
		
		Virus v=new Virus();
		
		assertEquals(v.getX(),0);
		
	}
	
	
	@Test 
	public void testsetX() {
		
		Virus v=new Virus();
		
		v.setX(3);
		
		assertEquals(v.getX(),3);
		
	}
	
	
	@Test 
	public void testgetY() { 
		
		Virus v=new Virus();
		
		assertEquals(v.getY(),0);
		
	}
	
	
	@Test 
	public void testsetY() {
		
		Virus v=new Virus();

		v.setY(7);
		
		assertEquals(v.getY(),7);
		
	}
	
	
	@Test 
	public void testgetxSpeed() {
		
		Virus v=new Virus();
		
		assertEquals(v.getxSpeed(),0);
		
		
	}
	
	@Test
	public void testgetySpeed() {
		
		Virus v=new Virus();
		
		v.setySpeed(9);
		
		assertEquals(v.getySpeed(),9);
		
	}
	
	
	@Test 
	public void testgetWidth() {
		
		Virus v=new Virus();
		
		assertEquals(v.getWidth(),0);
		
	}
	
	
	@Test
	public void testsetWidth() {
		
		Virus v=new Virus(); 
		
		v.setWidth(5);
		
		assertEquals(v.getWidth(),5);
		
	}
	
	
	@Test 
	public void testgetHeight() {
		
		Virus v=new Virus();
		
		assertEquals(v.getHeight(), 0);
		
	}
	
	
	@Test
	public void testsetHeight() {
		
		Virus v=new Virus();
		
		v.setHeight(5);
		
		assertEquals(v.getHeight(), 5);
		
	}
	
	@Test
	public void testgetStrength() {
		
		Virus v=new Virus();
		
		assertEquals(v.getStrength(),1);
		
	}
	
	
	@Test 
	public void testsetStrength() {
		
		Virus v=new Virus();
		
		v.setStrength(7);
		
		assertEquals(v.getStrength(),7);
		
	}
	
	
	
	@Test 
	public void testisAlive() {
		
		Virus v=new Virus(); 
		
		assertEquals(v.isAlive(),true);
		
	}
	
	
	@Test 
	public void testsetAlive() {
		
		Virus v=new Virus();
		
		v.setAlive(false);
		
		assertEquals(v.isAlive(),false);
		
	}
	
	
	@Test 
	public void testgetBound() {
		
		Virus v=new Virus(2,4,6,3,1,0,5,false);
		
		assertEquals(4,v.getBound(1));
		assertEquals(3,v.getBound(2));
		assertEquals(4,v.getBound(3));
		assertEquals(2,v.getBound(4));
		
	}
	
	@Test
	public void testanimateVirus() {
		
		Virus v=new Virus(2,4,6,3,1,0,5,false);
		
		v.animateVirus();
		
		assertEquals(8,v.getX());
		
		assertEquals(7,v.getY());
		
	}
	
	@Test 
	public void testwithinVirus() {
		
		Virus v=new Virus(4,2,6,1,3,5,0,false);
		
		assertEquals(v.withinVirus(6, 5),true);
		
	}
	
	
	
}
