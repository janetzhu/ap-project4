package object;

import static org.junit.Assert.*;

import org.junit.Test;

/*
 *This class tests the essential methods of the Board class  
 *and the graphical methods are left out since one can already 
 *see that on the screen. 
 * 
 */

public class BoardTest {

/*
 * @Test 
 * testBoardConstructor() which makes sure  
 * that the members of the Board class are initialized properly. 
 * 
 * @param none
 * @return none
 * 	
 */
	
	@Test
	public void testBoardConstructor() {

		Board b=new Board(2,3);

		assertEquals(2,b.getgameHeight());

		assertEquals(3,b.getgameWidth());

	}
	
/*
 * @Test
 * testcaliberateDifficulty() 
 * 
 * This method is important to make sure the game difficulty is computed 
 * appropriately. 
 * 
 * 
 *@param none
 *@return none 
 */

	@Test
	public void testcaliberateDifficulty() {

		Board b=new Board(5,4);

		b.setTCellCount(950);

		b.calibrateDifficulty();

		assertEquals(b.getDiffcultyLevel(),2);

		b.setTCellCount(940);

		b.calibrateDifficulty();

		assertEquals(b.getDiffcultyLevel(),3);

		b.setTCellCount(930);

		b.calibrateDifficulty();

		assertEquals(b.getDiffcultyLevel(),4);

		b.setTCellCount(920);

		b.calibrateDifficulty();

		assertEquals(b.getDiffcultyLevel(),5);

		b.setTCellCount(910);

		b.calibrateDifficulty();

		assertEquals(b.getDiffcultyLevel(),6);

	}

	/*
	 * @test
	 * 
	 * testCalculateScore() is a method that verifies the score is calculated 
	 * correctly.
	 *
	 *@param none
	 *@return none 
	 * 
	 */
	
	@Test
	public void testCalculateScore() {

		Board b = new Board(5, 4);

		b.setGameScore(20);
		b.setGameStartTime(1000);
		b.calculateScore();

		assertEquals(b.getGameScore(), 20);
	}
}

