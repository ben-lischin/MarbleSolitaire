import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import model.TriangleSolitaireModel;
import view.TriangleSolitaireTextView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests creating a string representation of a triangle solitaire board.
 */
public class TriangleSolitaireTextViewTest {
  TriangleSolitaireModel defaultBoard;
  TriangleSolitaireModel emptyPosBoard;
  TriangleSolitaireModel sizeBoard;
  TriangleSolitaireModel sizeAndPosBoard;

  TriangleSolitaireTextView defaultText;
  TriangleSolitaireTextView emptyPosText;
  TriangleSolitaireTextView sizeText;
  TriangleSolitaireTextView sizeAndPosText;

  TriangleSolitaireTextView invalidText;
  Appendable in;
  TriangleSolitaireTextView inputTextDefault;
  TriangleSolitaireTextView inputTextEmptyPos;
  TriangleSolitaireTextView inputTextSize;
  TriangleSolitaireTextView inputTextSizeAndPos;

  AppendableMock badInput = new AppendableMock();
  TriangleSolitaireTextView invalidInputTextDefault;
  TriangleSolitaireTextView invalidInputTextSize;
  TriangleSolitaireTextView invalidInputTextPos;
  TriangleSolitaireTextView invalidInputTextSizeAndPos;


  @Before
  public void init() {
    this.defaultBoard = new TriangleSolitaireModel();
    this.emptyPosBoard = new TriangleSolitaireModel(2, 1);
    this.sizeBoard = new TriangleSolitaireModel(10);
    this.sizeAndPosBoard = new TriangleSolitaireModel(7, 4, 3);

    this.defaultText = new TriangleSolitaireTextView(this.defaultBoard);
    this.emptyPosText = new TriangleSolitaireTextView(this.emptyPosBoard);
    this.sizeText = new TriangleSolitaireTextView(this.sizeBoard);
    this.sizeAndPosText = new TriangleSolitaireTextView(this.sizeAndPosBoard);

    this.in = new StringBuilder();

    this.inputTextDefault = new TriangleSolitaireTextView(this.defaultBoard, this.in);
    this.inputTextEmptyPos = new TriangleSolitaireTextView(this.emptyPosBoard, this.in);
    this.inputTextSize = new TriangleSolitaireTextView(this.sizeBoard, this.in);
    this.inputTextSizeAndPos = new TriangleSolitaireTextView(this.sizeAndPosBoard, this.in);

    this.invalidInputTextDefault = new TriangleSolitaireTextView(this.defaultBoard, this.badInput);
    this.invalidInputTextPos = new TriangleSolitaireTextView(this.emptyPosBoard, this.badInput);
    this.invalidInputTextSize = new TriangleSolitaireTextView(this.sizeBoard, this.badInput);
    this.invalidInputTextSizeAndPos = new TriangleSolitaireTextView(this.sizeAndPosBoard,
            this.badInput);
  }

  @Test
  public void testValidInitialization() {
    // test valid construction for both constructors, with all 4 types of boards
    try {
      this.defaultText = new TriangleSolitaireTextView(this.defaultBoard);
      this.defaultText = new TriangleSolitaireTextView(defaultBoard, System.out);
      this.emptyPosText = new TriangleSolitaireTextView(this.emptyPosBoard);
      this.emptyPosText = new TriangleSolitaireTextView(this.emptyPosBoard, System.out);
      this.sizeText = new TriangleSolitaireTextView(this.sizeBoard);
      this.sizeText = new TriangleSolitaireTextView(this.sizeBoard, System.out);
      this.sizeAndPosText = new TriangleSolitaireTextView(this.sizeAndPosBoard);
      this.sizeAndPosText = new TriangleSolitaireTextView(this.sizeAndPosBoard, System.out);
      // do nothing; test successfully constructed the text view object
    } catch (IllegalArgumentException e) {
      fail("Should not have thrown an IllegalArgumentException");
    }
  }

  @Test
  public void testInvalidInitialization() {
    // constructor 1
    // only invalid case : null argument passed
    try {
      invalidText = new TriangleSolitaireTextView(null);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }

    // constructor 2
    try {
      invalidText = new TriangleSolitaireTextView(null, System.out);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }
    try {
      invalidText = new TriangleSolitaireTextView(defaultBoard, null);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }

  }

  @Test
  public void testToString() {
    assertEquals("    _\n" +
            "   O O\n" +
            "  O O O\n" +
            " O O O O\n" +
            "O O O O O", this.defaultText.toString());
    // test after making a move
    this.defaultBoard.move(2,0,0,0);
    this.defaultText = new TriangleSolitaireTextView(this.defaultBoard);
    assertEquals("    O\n" +
            "   _ O\n" +
            "  _ O O\n" +
            " O O O O\n" +
            "O O O O O", this.defaultText.toString());


    // other tests...
    assertEquals("    O\n" +
            "   O O\n" +
            "  O _ O\n" +
            " O O O O\n" +
            "O O O O O", this.emptyPosText.toString());
    assertEquals("         _\n" +
            "        O O\n" +
            "       O O O\n" +
            "      O O O O\n" +
            "     O O O O O\n" +
            "    O O O O O O\n" +
            "   O O O O O O O\n" +
            "  O O O O O O O O\n" +
            " O O O O O O O O O\n" +
            "O O O O O O O O O O", this.sizeText.toString());
    assertEquals("      O\n" +
            "     O O\n" +
            "    O O O\n" +
            "   O O O O\n" +
            "  O O O _ O\n" +
            " O O O O O O\n" +
            "O O O O O O O", this.sizeAndPosText.toString());
  }

  @Test
  public void testRenderBoard() {
    // test invalid renderBoard that throws IO exception (with all the 4 board types)
    try {
      this.invalidInputTextDefault.renderBoard();
      fail("should have thrown IOException");
    } catch (IOException e) {
      // do nothing; successfully threw IO exception
    }
    try {
      this.invalidInputTextSize.renderBoard();
      fail("should have thrown IOException");
    } catch (IOException e) {
      // do nothing; successfully threw IO exception
    }
    try {
      this.invalidInputTextPos.renderBoard();
      fail("should have thrown IOException");
    } catch (IOException e) {
      // do nothing; successfully threw IO exception
    }
    try {
      this.invalidInputTextSizeAndPos.renderBoard();
      fail("should have thrown IOException");
    } catch (IOException e) {
      // do nothing; successfully threw IO exception
    }


    // render the default board
    try {
      this.inputTextDefault.renderBoard();
      assertEquals("    _\n" +
              "   O O\n" +
              "  O O O\n" +
              " O O O O\n" +
              "O O O O O", this.in.toString());
    } catch (IOException e) {
      fail("should not have thrown IO exception");
    }


    // test after making a move
    init();
    this.defaultBoard.move(2,0,0,0);
    try {
      this.inputTextDefault.renderBoard();
      this.defaultText = new TriangleSolitaireTextView(this.defaultBoard);
      assertEquals("    O\n" +
              "   _ O\n" +
              "  _ O O\n" +
              " O O O O\n" +
              "O O O O O", this.in.toString());
    } catch (IOException e) {
      fail("should not have thrown IO exception");
    }


    // other tests...

    init();
    try {
      this.inputTextEmptyPos.renderBoard();
      assertEquals("    O\n" +
              "   O O\n" +
              "  O _ O\n" +
              " O O O O\n" +
              "O O O O O", this.in.toString());
    } catch (IOException e) {
      fail("should not have thrown IO exception");
    }

    init();
    try {
      this.inputTextSize.renderBoard();
      assertEquals("         _\n" +
              "        O O\n" +
              "       O O O\n" +
              "      O O O O\n" +
              "     O O O O O\n" +
              "    O O O O O O\n" +
              "   O O O O O O O\n" +
              "  O O O O O O O O\n" +
              " O O O O O O O O O\n" +
              "O O O O O O O O O O", this.in.toString());
    } catch (IOException e) {
      fail("should not have thrown IO exception");
    }

    init();
    try {
      this.inputTextSizeAndPos.renderBoard();
      assertEquals("      O\n" +
              "     O O\n" +
              "    O O O\n" +
              "   O O O O\n" +
              "  O O O _ O\n" +
              " O O O O O O\n" +
              "O O O O O O O", this.in.toString());
    } catch (IOException e) {
      fail("should not have thrown IO exception");
    }

  }

  @Test
  public void testRenderMessage() {
    // test invalid renderMessage that throws IO exception (with all the 4 board types)
    try {
      this.invalidInputTextDefault.renderMessage("hi");
      fail("should have thrown IOException");
    } catch (IOException e) {
      // do nothing; successfully threw IO exception
    }
    try {
      this.invalidInputTextSize.renderMessage("hi");
      fail("should have thrown IOException");
    } catch (IOException e) {
      // do nothing; successfully threw IO exception
    }
    try {
      this.invalidInputTextPos.renderMessage("hi");
      fail("should have thrown IOException");
    } catch (IOException e) {
      // do nothing; successfully threw IO exception
    }
    try {
      this.invalidInputTextSizeAndPos.renderMessage("hi");
      fail("should have thrown IOException");
    } catch (IOException e) {
      // do nothing; successfully threw IO exception
    }

    //test valid rendering a message with a text view object created with the
    // 4 types of boards (4 board constructors)
    try {
      inputTextDefault.renderMessage("hi");
      assertEquals("hi", this.in.toString());
    } catch (IOException e) {
      fail("should not have thrown IO exception");
    }

    init();
    try {
      inputTextEmptyPos.renderMessage("hi");
      assertEquals("hi", this.in.toString());
    } catch (IOException e) {
      fail("should not have thrown IO exception");
    }

    init();
    try {
      inputTextSize.renderMessage("hi");
      assertEquals("hi", this.in.toString());
    } catch (IOException e) {
      fail("should not have thrown IO exception");
    }

    init();
    try {
      inputTextSizeAndPos.renderMessage("hi");
      assertEquals("hi", this.in.toString());
    } catch (IOException e) {
      fail("should not have thrown IO exception");
    }

  }
}