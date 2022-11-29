import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.EnglishSolitaireModel;
import model.MarbleSolitaireModelState;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests solitaire board construction and their methods.
 */
public class EnglishSolitaireModelTest {
  EnglishSolitaireModel defaultBoard;
  EnglishSolitaireModel emptyPosBoard;
  EnglishSolitaireModel armBoard;
  EnglishSolitaireModel armAndPosBoard;

  @Before
  public void init() {
    this.defaultBoard = new EnglishSolitaireModel();
    this.emptyPosBoard = new EnglishSolitaireModel(2, 3);
    this.armBoard = new EnglishSolitaireModel(9);
    this.armAndPosBoard = new EnglishSolitaireModel(5, 1, 5);
  }

  @Test
  public void testValidInitialization() {
    this.defaultBoard = new EnglishSolitaireModel();
    assertEquals(32, this.defaultBoard.getScore());
    this.emptyPosBoard = new EnglishSolitaireModel(2, 3);
    assertEquals(32, this.emptyPosBoard.getScore());
    this.armBoard = new EnglishSolitaireModel(9);
    assertEquals(368, this.armBoard.getScore());
    this.armAndPosBoard = new EnglishSolitaireModel(5, 1, 5);
    assertEquals(104, this.armAndPosBoard.getScore());
  }

  @Test
  public void testInvalidInitialization() {
    // constructor 1 : default board
    // default board initialization cannot throw errors (omitted in this test)


    // constructor 2 : given empty position
    // given pos is invalid
    try {
      this.emptyPosBoard = new EnglishSolitaireModel(0, 0);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }
    // also try outside board, in line with marble cross
    // (not considered in my invalid corner check)
    try {
      this.emptyPosBoard = new EnglishSolitaireModel(8, 3);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }


    // constructor 3 : given armSize
    // given size is non-positive
    try {
      this.armBoard = new EnglishSolitaireModel(-3);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }
    // given size is even
    try {
      this.armBoard = new EnglishSolitaireModel(2);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }
    // given size is non-positive and even
    try {
      this.armBoard = new EnglishSolitaireModel(-2);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }


    // constructor 4 : given armSize and empty position
    // given size is non-positive
    try {
      this.armAndPosBoard = new EnglishSolitaireModel(-3, 2, 2);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }
    // given size is even
    try {
      this.armAndPosBoard = new EnglishSolitaireModel(2, 2, 2);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }
    // given size is non-positive and even
    try {
      this.armAndPosBoard = new EnglishSolitaireModel(-2, 2, 2);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }
    // given size is valid, but the pos is invalid
    try {
      this.armAndPosBoard = new EnglishSolitaireModel(3, 0, 0);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }
    try {
      this.armAndPosBoard = new EnglishSolitaireModel(3, 8, 3);
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
      this.defaultBoard.move(5,1,5,3);
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
  }

  @Test
  public void getBoardSize() {
    assertEquals(7, this.defaultBoard.getBoardSize());
    assertEquals(7, this.emptyPosBoard.getBoardSize());
    assertEquals(25, this.armBoard.getBoardSize());
    assertEquals(13, this.armAndPosBoard.getBoardSize());
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
    Assert.assertEquals(MarbleSolitaireModelState.SlotState.Invalid,
            this.defaultBoard.getSlotAt(0,0));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble,
            this.defaultBoard.getSlotAt(3,2));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty,
            this.defaultBoard.getSlotAt(3,3));
  }

  @Test
  public void getScore() {
    assertEquals(32, this.defaultBoard.getScore());
    assertEquals(32, this.emptyPosBoard.getScore());
    assertEquals(368, this.armBoard.getScore());
    assertEquals(104, this.armAndPosBoard.getScore());

    // score decreases after moves
    this.defaultBoard.move(5,3,3,3);
    assertEquals(31, this.defaultBoard.getScore());
    this.defaultBoard.move(4,5,4,3);
    assertEquals(30, this.defaultBoard.getScore());
    this.defaultBoard.move(6,4,4,4);
    assertEquals(29, this.defaultBoard.getScore());

  }
}