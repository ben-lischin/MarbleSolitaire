import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.MarbleSolitaireModelState;
import model.TriangleSolitaireModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * testing for the triangle solitaire model.
 */
public class TriangleSolitaireModelTest {
  TriangleSolitaireModel defaultBoard;
  TriangleSolitaireModel emptyPosBoard;
  TriangleSolitaireModel sizeBoard;
  TriangleSolitaireModel sizeAndPosBoard;

  @Before
  public void init() {
    this.defaultBoard = new TriangleSolitaireModel();
    this.emptyPosBoard = new TriangleSolitaireModel(3, 2);
    this.sizeBoard = new TriangleSolitaireModel(9);
    this.sizeAndPosBoard = new TriangleSolitaireModel(6, 4, 3);
  }

  @Test
  public void testValidInitialization() {
    this.defaultBoard = new TriangleSolitaireModel();
    assertEquals(14, this.defaultBoard.getScore());
    this.emptyPosBoard = new TriangleSolitaireModel(3,2);
    assertEquals(14, this.emptyPosBoard.getScore());
    this.sizeBoard = new TriangleSolitaireModel(9);
    assertEquals(44, this.sizeBoard.getScore());
    this.sizeAndPosBoard = new TriangleSolitaireModel(6, 4,3);
    assertEquals(20, this.sizeAndPosBoard.getScore());
  }

  @Test
  public void testInvalidInitialization() {
    // constructor 1 : default board
    // default board initialization cannot throw errors (omitted in this test)


    // constructor 2 : given empty position
    // given pos is invalid
    try {
      this.emptyPosBoard = new TriangleSolitaireModel(2, 3);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }
    // also try outside board, in line with marble cross
    // (not considered in my invalid corner check)
    try {
      this.emptyPosBoard = new TriangleSolitaireModel(10, 3);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }


    // constructor 3 : given size is non-positive
    try {
      this.sizeBoard = new TriangleSolitaireModel(-3);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }


    // constructor 4 : given size and empty position
    // given size is non-positive
    try {
      this.sizeAndPosBoard = new TriangleSolitaireModel(-3, 2, 1);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }
    // given size is valid, but the pos is invalid
    try {
      this.sizeAndPosBoard = new TriangleSolitaireModel(4, 2, 3);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }
    try {
      this.sizeAndPosBoard = new TriangleSolitaireModel(3, 8, 3);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }
  }

  @Test
  public void move() {
    // test moving on the default board (up/down/left/right, and diagonals)
    // all types of valid moves, for example
    try {
      // diagonal (up left)
      this.defaultBoard.move(2,2,0,0);
      // diagonal (up right)
      this.defaultBoard.move(4,0,2,2);
      // left
      this.defaultBoard.move(3,3,3,1);
      // diagonal (down right)
      this.defaultBoard.move(1,0,3,2);
      // diagonal (down left)
      this.defaultBoard.move(2,2,4,0);
      // up
      this.defaultBoard.move(3,0,1,0);
      // down
      this.defaultBoard.move(0,0,2,0);
      // diagonal (up left) (already tested this type of move, but it enables us to go right next)
      this.defaultBoard.move(4,3,2,1);
      // right
      this.defaultBoard.move(2,0,2,2);

      // ...

    } catch (IllegalArgumentException e) {
      fail("Should not have thrown an IllegalArgumentException");
    }
    init();
    this.defaultBoard.move(2,0,0,0); // already tested (valid)
    // invalid move : from out of bounds
    try {
      this.defaultBoard.move(-1,0,1,0);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }
    init();
    this.defaultBoard.move(2,0,0,0); // already tested (valid)
    // invalid move : to out of bounds
    try {
      this.defaultBoard.move(3,0,5,0);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }
    init();
    this.defaultBoard.move(2,0,0,0); // already tested (valid)
    // invalid move : not 2 slots away
    try {
      this.defaultBoard.move(4,0,1,0);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }
    init();
    this.defaultBoard.move(2,0,0,0);
    this.defaultBoard.move(4,0,2,0);
    // invalid move : from not marble
    try {
      this.defaultBoard.move(1,0,3,0);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }
    init();
    // invalid move : to not empty
    try {
      this.defaultBoard.move(1,0,3,0);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }
    init();
    this.defaultBoard.move(2,0,0,0);
    // invalid move : middle slot not marble
    try {
      this.defaultBoard.move(0,0,2,0);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }
  }

  @Test
  public void isGameOver() {
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(2,0,0,0);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(2,2,2,0);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(0,0,2,2);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(4,1,2,1);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(3,0,1,0);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(2,2,2,0);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(1,0,3,0);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(4,0,2,0);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(3,3,3,1);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(4,3,4,1);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(2,0,4,2);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(4,1,4,3);
    assertFalse(this.defaultBoard.isGameOver());
    this.defaultBoard.move(4,4,4,2);
    assertTrue(this.defaultBoard.isGameOver());
  }

  @Test
  public void getBoardSize() {
    assertEquals(5, this.defaultBoard.getBoardSize());
    assertEquals(5, this.emptyPosBoard.getBoardSize());
    assertEquals(9, this.sizeBoard.getBoardSize());
    assertEquals(6, this.sizeAndPosBoard.getBoardSize());
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
    Assert.assertEquals(MarbleSolitaireModelState.SlotState.Empty,
            this.defaultBoard.getSlotAt(0,0));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble,
            this.defaultBoard.getSlotAt(1,0));
    assertEquals(MarbleSolitaireModelState.SlotState.Invalid,
            this.defaultBoard.getSlotAt(3,4));
  }

  @Test
  public void getScore() {
    assertEquals(14, this.defaultBoard.getScore());
    assertEquals(14, this.emptyPosBoard.getScore());
    assertEquals(44, this.sizeBoard.getScore());
    assertEquals(20, this.sizeAndPosBoard.getScore());

    // score decreases after moves
    this.defaultBoard.move(2,0,0,0);
    assertEquals(13, this.defaultBoard.getScore());
    this.defaultBoard.move(2,2,2,0);
    assertEquals(12, this.defaultBoard.getScore());
    this.defaultBoard.move(0,0,2,2);
    assertEquals(11, this.defaultBoard.getScore());
  }
}