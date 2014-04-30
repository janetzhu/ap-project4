package test;

import static org.junit.Assert.*;
import impl.InvasionGame;
import impl.InvasionGame.SidebarPanel;
import object.Board;

import org.junit.BeforeClass;
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
	
	// Static board object
	private static Board testBoard;
	
	/**** UNIT TESTS ***/
	
	@BeforeClass
	public static void setUpBeforeClass() {
		
		testBoard=new Board(5,4);
	}
	
	
	@Test
	public void testBoardConstructor() {


		assertEquals(5,testBoard.getgameHeight());

		assertEquals(4,testBoard.getgameWidth());

	}
	
/*
 * @Test
 * testcalibrateDifficulty() 
 * 
 * This method is important to make sure the game difficulty is computed 
 * appropriately. 
 * 
 * 
 *@param none
 *@return none 
 */

	@Test
	public void testcalibrateDifficulty() {

		testBoard.setTCellCount(950);

		testBoard.calibrateDifficulty();

		assertEquals(testBoard.getDifficultyLevel(),2);

		testBoard.setTCellCount(750);

		testBoard.calibrateDifficulty();

		assertEquals(testBoard.getDifficultyLevel(),3);

		testBoard.setTCellCount(600);

		testBoard.calibrateDifficulty();

		assertEquals(testBoard.getDifficultyLevel(),4);

		testBoard.setTCellCount(500);

		testBoard.calibrateDifficulty();

		assertEquals(testBoard.getDifficultyLevel(),5);

		testBoard.setTCellCount(400);

		testBoard.calibrateDifficulty();

		assertEquals(testBoard.getDifficultyLevel(),6);

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

		testBoard.setGameScore(20);
		testBoard.setGameStartTime(1000);
		testBoard.calculateScore();

		assertEquals(testBoard.getGameScore(), 20);
	}
	
}

