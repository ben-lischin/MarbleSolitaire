import org.junit.Before;
import org.junit.Test;

import model.MarbleSolitaireModelState;
import model.EuropeanSolitaireModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Testing for the european solitaire model.
 */

public class EuropeanSolitaireModelTest {
  EuropeanSolitaireModel defaultBoard;
  EuropeanSolitaireModel emptyPosBoard;
  EuropeanSolitaireModel sizeBoard;
  EuropeanSolitaireModel sizeAndPosBoard;

  @Before
  public void init() {
    this.defaultBoard = new EuropeanSolitaireModel();
    this.emptyPosBoard = new EuropeanSolitaireModel(2, 3);
    this.sizeBoard = new EuropeanSolitaireModel(9);
    this.sizeAndPosBoard = new EuropeanSolitaireModel(5, 1, 5);
  }

  @Test
  public void testValidInitialization() {
    this.defaultBoard = new EuropeanSolitaireModel();
    assertEquals(36, this.defaultBoard.getScore());
    this.emptyPosBoard = new EuropeanSolitaireModel(2, 3);
    assertEquals(36, this.emptyPosBoard.getScore());
    this.sizeBoard = new EuropeanSolitaireModel(9);
    assertEquals(480, this.sizeBoard.getScore());
    this.sizeAndPosBoard = new EuropeanSolitaireModel(5, 1, 5);
    assertEquals(128, this.sizeAndPosBoard.getScore());

    // new test : armSize can be even in european boards
    this.sizeBoard = new EuropeanSolitaireModel(4);
    assertEquals(75, this.sizeBoard.getScore());
  }

  @Test
  public void testInvalidInitialization() {
    // constructor 1 : default board
    // default board initialization cannot throw errors (omitted in this test)


    // constructor 2 : given empty position
    // given pos is invalid
    try {
      this.emptyPosBoard = new EuropeanSolitaireModel(0, 0);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }
    // also try outside board, in line with marble cross
    // (not considered in my invalid corner check)
    try {
      this.emptyPosBoard = new EuropeanSolitaireModel(8, 3);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }


    // constructor 3 : given armSize
    // given size is non-positive
    try {
      this.sizeBoard = new EuropeanSolitaireModel(-3);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }


    // constructor 4 : given armSize and empty position
    // given size is non-positive
    try {
      this.sizeAndPosBoard = new EuropeanSolitaireModel(-3, 2, 2);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }
    // given size is valid, but the pos is invalid
    try {
      this.sizeAndPosBoard = new EuropeanSolitaireModel(3, 0, 0);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }
    try {
      this.sizeAndPosBoard = new EuropeanSolitaireModel(3, 8, 3);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }
  }

  @Test
  public void move() {
    // test moving on the default board
    // valid moves, for example
    try {
      this.defaultBoard.move(5,3,3,3);
      this.defaultBoard.move(4,5,4,3);
      this.defaultBoard.move(6,4,4,4);
      this.defaultBoard.move(3,4,5,4);
      this.defaultBoard.move(6,2,6,4);
      this.defaultBoard.move(6,4,4,4);

      init();
      // testing with the new valid diagonal slots
      this.defaultBoard.move(5,3,3,3);
      // from bottom right diagonal marble
      this.defaultBoard.move(5,5,5,3);
      // to bottom right diagonal marble
      this.defaultBoard.move(3,5,5,5);



      //...
    } catch (IllegalArgumentException e) {
      fail("Should not have thrown an IllegalArgumentException");
    }
    init();
    // invalid move : from out of bounds
    try {
      this.defaultBoard.move(3,7,3,5);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }
    init();
    // invalid move : to out of bounds
    try {
      this.defaultBoard.move(3,5,3,7);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }
    init();
    this.defaultBoard.move(5,3,3,3); // already tested (valid)
    // invalid move : not 2 slots away
    try {
      this.defaultBoard.move(2,3,5,3);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }
    // invalid move : 2 diagonally
    init();
    this.defaultBoard.move(5,3,3,3); // already tested (valid)
    try {
      this.defaultBoard.move(2,1,4,3);
      fail("Should have thrown an IllegalArgumentException");
      // the diagonal move (moving 2 slots diagonally, otherwise valid)
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }
    init();
    this.defaultBoard.move(5,3,3,3); // already tested (valid)
    // invalid move : from not marble
    try {
      this.defaultBoard.move(7,3,5,3);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }
    init();
    // invalid move : to not empty
    try {
      this.defaultBoard.move(5,3,5,1);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }
    init();
    this.defaultBoard.move(5,3,3,3); // already tested (valid)
    // invalid move : middle slot not marble
    try {
      this.defaultBoard.move(3,3,5,3);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }
  }

  @Test
  public void isGameOver() {
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(5,3,3,3);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(4,5,4,3);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(6,4,4,4);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(3,4,5,4);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(6,2,6,4);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(6,4,4,4);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(1,4,3,4);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(2,6,2,4);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(2,3,2,5);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(4,6,2,6);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(2,6,2,4);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(2,1,2,3);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(0,2,2,2);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(3,2,1,2);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(0,4,0,2);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(0,2,2,2);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(5,2,3,2);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(4,0,4,2);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(2,0,4,0);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(4,3,4,1);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(4,0,4,2);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(2,3,2,1);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(2,1,4,1);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(4,1,4,3);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(4,3,4,5);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(4,5,2,5);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(2,5,2,3);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(3,3,3,5);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(1,3,3,3);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(3,2,3,4);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(3,5,3,3);
    assertTrue(this.defaultBoard.isGameOver());
    // game over now; no more valid moves available
  }

  @Test
  public void getBoardSize() {
    assertEquals(7, this.defaultBoard.getBoardSize());
    assertEquals(7, this.emptyPosBoard.getBoardSize());
    assertEquals(25, this.sizeBoard.getBoardSize());
    assertEquals(13, this.sizeAndPosBoard.getBoardSize());
  }

  @Test
  public void getSlotAt() {
    // invalid slot position (off the board)
    try {
      this.defaultBoard.getSlotAt(100,100);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }
    try {
      this.defaultBoard.getSlotAt(-10,5);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }

    // valid slot positions (on board), for example
    try {
      this.defaultBoard.getSlotAt(0,0);
      this.defaultBoard.getSlotAt(3,2);
      this.defaultBoard.getSlotAt(3,3);
      //...
    } catch (IllegalArgumentException e) {
      fail("Should not have thrown an IllegalArgumentException");
    }
    assertEquals(MarbleSolitaireModelState.SlotState.Invalid,
            this.defaultBoard.getSlotAt(0,0));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble,
            this.defaultBoard.getSlotAt(3,2));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty,
            this.defaultBoard.getSlotAt(3,3));
  }

  @Test
  public void getScore() {
    assertEquals(36, this.defaultBoard.getScore());
    assertEquals(36, this.emptyPosBoard.getScore());
    assertEquals(480, this.sizeBoard.getScore());
    assertEquals(128, this.sizeAndPosBoard.getScore());

    // score decreases after moves
    this.defaultBoard.move(5,3,3,3);
    assertEquals(35, this.defaultBoard.getScore());
    this.defaultBoard.move(4,5,4,3);
    assertEquals(34, this.defaultBoard.getScore());
    this.defaultBoard.move(6,4,4,4);
    assertEquals(33, this.defaultBoard.getScore());

  }
}