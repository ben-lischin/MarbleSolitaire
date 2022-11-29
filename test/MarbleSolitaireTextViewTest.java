import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import model.EnglishSolitaireModel;
import model.EuropeanSolitaireModel;
import view.MarbleSolitaireTextView;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests creating a string representation of a solitaire board (english and european).
 */
public class MarbleSolitaireTextViewTest {
  EnglishSolitaireModel defaultBoard;
  EnglishSolitaireModel emptyPosBoard;
  EnglishSolitaireModel armBoard;
  EnglishSolitaireModel armAndPosBoard;
  EuropeanSolitaireModel defaultBoardEuro;
  EuropeanSolitaireModel emptyPosBoardEuro;
  EuropeanSolitaireModel sizeBoard;
  EuropeanSolitaireModel sizeAndPosBoard;
  MarbleSolitaireTextView defaultText;
  MarbleSolitaireTextView emptyPosText;
  MarbleSolitaireTextView armText;
  MarbleSolitaireTextView armAndPosText;
  MarbleSolitaireTextView defaultTextEuro;
  MarbleSolitaireTextView emptyPosTextEuro;
  MarbleSolitaireTextView sizeText;
  MarbleSolitaireTextView sizeAndPosText;
  MarbleSolitaireTextView invalidText;
  Appendable in;
  MarbleSolitaireTextView inputTextDefault;
  MarbleSolitaireTextView inputTextEmptyPos;
  MarbleSolitaireTextView inputTextArm;
  MarbleSolitaireTextView inputTextArmAndPos;
  MarbleSolitaireTextView inputTextDefaultEuro;
  MarbleSolitaireTextView inputTextEmptyPosEuro;
  MarbleSolitaireTextView inputTextSize;
  MarbleSolitaireTextView inputTextSizeAndPos;
  AppendableMock badInput = new AppendableMock();
  MarbleSolitaireTextView invalidInputTextDefault;
  MarbleSolitaireTextView invalidInputTextArm;
  MarbleSolitaireTextView invalidInputTextPos;
  MarbleSolitaireTextView invalidInputTextArmAndPos;
  MarbleSolitaireTextView invalidInputTextDefaultEuro;
  MarbleSolitaireTextView invalidInputTextSize;
  MarbleSolitaireTextView invalidInputTextPosEuro;
  MarbleSolitaireTextView invalidInputTextSizeAndPos;


  @Before
  public void init() {
    this.defaultBoard = new EnglishSolitaireModel();
    this.emptyPosBoard = new EnglishSolitaireModel(2, 3);
    this.armBoard = new EnglishSolitaireModel(9);
    this.armAndPosBoard = new EnglishSolitaireModel(5, 1, 5);

    this.defaultBoardEuro = new EuropeanSolitaireModel();
    this.emptyPosBoardEuro = new EuropeanSolitaireModel(2, 3);
    this.sizeBoard = new EuropeanSolitaireModel(9);
    this.sizeAndPosBoard = new EuropeanSolitaireModel(5, 1, 5);



    this.defaultText = new MarbleSolitaireTextView(this.defaultBoard);
    this.emptyPosText = new MarbleSolitaireTextView(this.emptyPosBoard);
    this.armText = new MarbleSolitaireTextView(this.armBoard);
    this.armAndPosText = new MarbleSolitaireTextView(this.armAndPosBoard);

    this.defaultTextEuro = new MarbleSolitaireTextView(this.defaultBoardEuro);
    this.emptyPosTextEuro = new MarbleSolitaireTextView(this.emptyPosBoardEuro);
    this.sizeText = new MarbleSolitaireTextView(this.sizeBoard);
    this.sizeAndPosText = new MarbleSolitaireTextView(this.sizeAndPosBoard);



    this.in = new StringBuilder();

    this.inputTextDefault = new MarbleSolitaireTextView(this.defaultBoard, this.in);
    this.inputTextEmptyPos = new MarbleSolitaireTextView(this.emptyPosBoard, this.in);
    this.inputTextArm = new MarbleSolitaireTextView(this.armBoard, this.in);
    this.inputTextArmAndPos = new MarbleSolitaireTextView(this.armAndPosBoard, this.in);

    this.inputTextDefaultEuro = new MarbleSolitaireTextView(this.defaultBoardEuro, this.in);
    this.inputTextEmptyPosEuro = new MarbleSolitaireTextView(this.emptyPosBoardEuro, this.in);
    this.inputTextSize = new MarbleSolitaireTextView(this.sizeBoard, this.in);
    this.inputTextSizeAndPos = new MarbleSolitaireTextView(this.sizeAndPosBoard, this.in);



    this.invalidInputTextDefault = new MarbleSolitaireTextView(this.defaultBoard, this.badInput);
    this.invalidInputTextArm = new MarbleSolitaireTextView(this.armBoard, this.badInput);
    this.invalidInputTextPos = new MarbleSolitaireTextView(this.emptyPosBoard, this.badInput);
    this.invalidInputTextArmAndPos = new MarbleSolitaireTextView(this.armAndPosBoard,
            this.badInput);

    this.invalidInputTextDefaultEuro = new MarbleSolitaireTextView(this.defaultBoardEuro,
            this.badInput);
    this.invalidInputTextSize = new MarbleSolitaireTextView(this.sizeBoard, this.badInput);
    this.invalidInputTextPosEuro = new MarbleSolitaireTextView(this.emptyPosBoardEuro,
            this.badInput);
    this.invalidInputTextSizeAndPos = new MarbleSolitaireTextView(this.sizeAndPosBoard,
            this.badInput);
  }

  @Test
  public void testValidInitialization() {
    // english solitaire
    // test valid construction for both constructors, with all 4 types of boards
    try {
      this.defaultText = new MarbleSolitaireTextView(this.defaultBoard);
      this.defaultText = new MarbleSolitaireTextView(defaultBoard, System.out);
      this.emptyPosText = new MarbleSolitaireTextView(this.emptyPosBoard);
      this.emptyPosText = new MarbleSolitaireTextView(this.emptyPosBoard, System.out);
      this.armText = new MarbleSolitaireTextView(this.armBoard);
      this.armText = new MarbleSolitaireTextView(this.armBoard, System.out);
      this.armAndPosText = new MarbleSolitaireTextView(this.armAndPosBoard);
      this.armAndPosText = new MarbleSolitaireTextView(this.armAndPosBoard, System.out);
      // do nothing; test successfully constructed the text view object
    } catch (IllegalArgumentException e) {
      fail("Should not have thrown an IllegalArgumentException");
    }

    // european solitaire
    // test valid construction for both constructors, with all 4 types of boards
    try {
      this.defaultTextEuro = new MarbleSolitaireTextView(this.defaultBoardEuro);
      this.defaultTextEuro = new MarbleSolitaireTextView(defaultBoardEuro, System.out);
      this.emptyPosTextEuro = new MarbleSolitaireTextView(this.emptyPosBoardEuro);
      this.emptyPosTextEuro = new MarbleSolitaireTextView(this.emptyPosBoardEuro, System.out);
      this.sizeText = new MarbleSolitaireTextView(this.sizeBoard);
      this.sizeText = new MarbleSolitaireTextView(this.sizeBoard, System.out);
      this.sizeAndPosText = new MarbleSolitaireTextView(this.sizeAndPosBoard);
      this.sizeAndPosText = new MarbleSolitaireTextView(this.sizeAndPosBoard, System.out);
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
      invalidText = new MarbleSolitaireTextView(null);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }

    // constructor 2
    try {
      invalidText = new MarbleSolitaireTextView(null, System.out);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }
    try {
      invalidText = new MarbleSolitaireTextView(defaultBoard, null);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }
    try {
      invalidText = new MarbleSolitaireTextView(defaultBoardEuro, null);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }

  }

  @Test
  public void testToString() {
    // english
    assertEquals("    O O O" +
            "\n    O O O" +
            "\nO O O O O O O" +
            "\nO O O _ O O O" +
            "\nO O O O O O O" +
            "\n    O O O" +
            "\n    O O O", this.defaultText.toString());
    // test after making a move
    this.defaultBoard.move(3, 1, 3, 3);
    this.defaultText = new MarbleSolitaireTextView(this.defaultBoard);
    assertEquals("    O O O" +
            "\n    O O O" +
            "\nO O O O O O O" +
            "\nO _ _ O O O O" +
            "\nO O O O O O O" +
            "\n    O O O" +
            "\n    O O O", this.defaultText.toString());


    // other tests...
    assertEquals("    O O O" +
            "\n    O O O" +
            "\nO O O _ O O O" +
            "\nO O O O O O O" +
            "\nO O O O O O O" +
            "\n    O O O" +
            "\n    O O O", this.emptyPosText.toString());
    assertEquals("                O O O O O O O O O" +
            "\n                O O O O O O O O O" +
            "\n                O O O O O O O O O" +
            "\n                O O O O O O O O O" +
            "\n                O O O O O O O O O" +
            "\n                O O O O O O O O O" +
            "\n                O O O O O O O O O" +
            "\n                O O O O O O O O O" +
            "\nO O O O O O O O O O O O O O O O O O O O O O O O O" +
            "\nO O O O O O O O O O O O O O O O O O O O O O O O O" +
            "\nO O O O O O O O O O O O O O O O O O O O O O O O O" +
            "\nO O O O O O O O O O O O O O O O O O O O O O O O O" +
            "\nO O O O O O O O O O O O _ O O O O O O O O O O O O" +
            "\nO O O O O O O O O O O O O O O O O O O O O O O O O" +
            "\nO O O O O O O O O O O O O O O O O O O O O O O O O" +
            "\nO O O O O O O O O O O O O O O O O O O O O O O O O" +
            "\nO O O O O O O O O O O O O O O O O O O O O O O O O" +
            "\n                O O O O O O O O O" +
            "\n                O O O O O O O O O" +
            "\n                O O O O O O O O O" +
            "\n                O O O O O O O O O" +
            "\n                O O O O O O O O O" +
            "\n                O O O O O O O O O" +
            "\n                O O O O O O O O O" +
            "\n                O O O O O O O O O", this.armText.toString());
    assertEquals("        O O O O O" +
            "\n        O _ O O O" +
            "\n        O O O O O" +
            "\n        O O O O O" +
            "\nO O O O O O O O O O O O O" +
            "\nO O O O O O O O O O O O O" +
            "\nO O O O O O O O O O O O O" +
            "\nO O O O O O O O O O O O O" +
            "\nO O O O O O O O O O O O O" +
            "\n        O O O O O" +
            "\n        O O O O O" +
            "\n        O O O O O" +
            "\n        O O O O O", this.armAndPosText.toString());

    // european
    assertEquals("    O O O" +
            "\n  O O O O O" +
            "\nO O O O O O O" +
            "\nO O O _ O O O" +
            "\nO O O O O O O" +
            "\n  O O O O O" +
            "\n    O O O", this.defaultTextEuro.toString());
    // test after making a move
    this.defaultBoardEuro.move(3, 1, 3, 3);
    this.defaultTextEuro = new MarbleSolitaireTextView(this.defaultBoardEuro);
    assertEquals("    O O O" +
            "\n  O O O O O" +
            "\nO O O O O O O" +
            "\nO _ _ O O O O" +
            "\nO O O O O O O" +
            "\n  O O O O O" +
            "\n    O O O", this.defaultTextEuro.toString());


    // other tests...
    assertEquals("    O O O" +
            "\n  O O O O O" +
            "\nO O O _ O O O" +
            "\nO O O O O O O" +
            "\nO O O O O O O" +
            "\n  O O O O O" +
            "\n    O O O", this.emptyPosTextEuro.toString());
    assertEquals("                O O O O O O O O O" +
            "\n              O O O O O O O O O O O" +
            "\n            O O O O O O O O O O O O O" +
            "\n          O O O O O O O O O O O O O O O" +
            "\n        O O O O O O O O O O O O O O O O O" +
            "\n      O O O O O O O O O O O O O O O O O O O" +
            "\n    O O O O O O O O O O O O O O O O O O O O O" +
            "\n  O O O O O O O O O O O O O O O O O O O O O O O" +
            "\nO O O O O O O O O O O O O O O O O O O O O O O O O" +
            "\nO O O O O O O O O O O O O O O O O O O O O O O O O" +
            "\nO O O O O O O O O O O O O O O O O O O O O O O O O" +
            "\nO O O O O O O O O O O O O O O O O O O O O O O O O" +
            "\nO O O O O O O O O O O O _ O O O O O O O O O O O O" +
            "\nO O O O O O O O O O O O O O O O O O O O O O O O O" +
            "\nO O O O O O O O O O O O O O O O O O O O O O O O O" +
            "\nO O O O O O O O O O O O O O O O O O O O O O O O O" +
            "\nO O O O O O O O O O O O O O O O O O O O O O O O O" +
            "\n  O O O O O O O O O O O O O O O O O O O O O O O" +
            "\n    O O O O O O O O O O O O O O O O O O O O O" +
            "\n      O O O O O O O O O O O O O O O O O O O" +
            "\n        O O O O O O O O O O O O O O O O O" +
            "\n          O O O O O O O O O O O O O O O" +
            "\n            O O O O O O O O O O O O O" +
            "\n              O O O O O O O O O O O" +
            "\n                O O O O O O O O O", this.sizeText.toString());
    assertEquals("        O O O O O" +
            "\n      O O _ O O O O" +
            "\n    O O O O O O O O O" +
            "\n  O O O O O O O O O O O" +
            "\nO O O O O O O O O O O O O" +
            "\nO O O O O O O O O O O O O" +
            "\nO O O O O O O O O O O O O" +
            "\nO O O O O O O O O O O O O" +
            "\nO O O O O O O O O O O O O" +
            "\n  O O O O O O O O O O O" +
            "\n    O O O O O O O O O" +
            "\n      O O O O O O O" +
            "\n        O O O O O", this.sizeAndPosText.toString());
  }

  @Test
  public void testRenderBoard() {
    // english
    // test invalid renderBoard that throws IO exception (with all the 4 board types)
    try {
      this.invalidInputTextDefault.renderBoard();
      fail("should have thrown IOException");
    } catch (IOException e) {
      // do nothing; successfully threw IO exception
    }
    try {
      this.invalidInputTextArm.renderBoard();
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
      this.invalidInputTextArmAndPos.renderBoard();
      fail("should have thrown IOException");
    } catch (IOException e) {
      // do nothing; successfully threw IO exception
    }


    // render the default board
    try {
      this.inputTextDefault.renderBoard();
      assertEquals("    O O O" +
              "\n    O O O" +
              "\nO O O O O O O" +
              "\nO O O _ O O O" +
              "\nO O O O O O O" +
              "\n    O O O" +
              "\n    O O O", this.in.toString());
    } catch (IOException e) {
      fail("should not have thrown IO exception");
    }


    // test after making a move
    init();
    this.defaultBoard.move(3, 1, 3, 3);
    try {
      this.inputTextDefault.renderBoard();
      this.defaultText = new MarbleSolitaireTextView(this.defaultBoard);
      assertEquals("    O O O" +
              "\n    O O O" +
              "\nO O O O O O O" +
              "\nO _ _ O O O O" +
              "\nO O O O O O O" +
              "\n    O O O" +
              "\n    O O O", this.in.toString());
    } catch (IOException e) {
      fail("should not have thrown IO exception");
    }


    // other tests...

    init();
    try {
      this.inputTextEmptyPos.renderBoard();
      assertEquals("    O O O" +
              "\n    O O O" +
              "\nO O O _ O O O" +
              "\nO O O O O O O" +
              "\nO O O O O O O" +
              "\n    O O O" +
              "\n    O O O", this.in.toString());
    } catch (IOException e) {
      fail("should not have thrown IO exception");
    }

    init();
    try {
      this.inputTextArm.renderBoard();
      assertEquals("                O O O O O O O O O" +
              "\n                O O O O O O O O O" +
              "\n                O O O O O O O O O" +
              "\n                O O O O O O O O O" +
              "\n                O O O O O O O O O" +
              "\n                O O O O O O O O O" +
              "\n                O O O O O O O O O" +
              "\n                O O O O O O O O O" +
              "\nO O O O O O O O O O O O O O O O O O O O O O O O O" +
              "\nO O O O O O O O O O O O O O O O O O O O O O O O O" +
              "\nO O O O O O O O O O O O O O O O O O O O O O O O O" +
              "\nO O O O O O O O O O O O O O O O O O O O O O O O O" +
              "\nO O O O O O O O O O O O _ O O O O O O O O O O O O" +
              "\nO O O O O O O O O O O O O O O O O O O O O O O O O" +
              "\nO O O O O O O O O O O O O O O O O O O O O O O O O" +
              "\nO O O O O O O O O O O O O O O O O O O O O O O O O" +
              "\nO O O O O O O O O O O O O O O O O O O O O O O O O" +
              "\n                O O O O O O O O O" +
              "\n                O O O O O O O O O" +
              "\n                O O O O O O O O O" +
              "\n                O O O O O O O O O" +
              "\n                O O O O O O O O O" +
              "\n                O O O O O O O O O" +
              "\n                O O O O O O O O O" +
              "\n                O O O O O O O O O", this.in.toString());
    } catch (IOException e) {
      fail("should not have thrown IO exception");
    }

    init();
    try {
      this.inputTextArmAndPos.renderBoard();
      assertEquals("        O O O O O" +
              "\n        O _ O O O" +
              "\n        O O O O O" +
              "\n        O O O O O" +
              "\nO O O O O O O O O O O O O" +
              "\nO O O O O O O O O O O O O" +
              "\nO O O O O O O O O O O O O" +
              "\nO O O O O O O O O O O O O" +
              "\nO O O O O O O O O O O O O" +
              "\n        O O O O O" +
              "\n        O O O O O" +
              "\n        O O O O O" +
              "\n        O O O O O", this.in.toString());
    } catch (IOException e) {
      fail("should not have thrown IO exception");
    }


    // european
    // test invalid renderBoard that throws IO exception (with all the 4 board types)
    try {
      this.invalidInputTextDefaultEuro.renderBoard();
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
      this.invalidInputTextPosEuro.renderBoard();
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
    init();
    try {
      this.inputTextDefaultEuro.renderBoard();
      assertEquals("    O O O" +
              "\n  O O O O O" +
              "\nO O O O O O O" +
              "\nO O O _ O O O" +
              "\nO O O O O O O" +
              "\n  O O O O O" +
              "\n    O O O", this.in.toString());
    } catch (IOException e) {
      fail("should not have thrown IO exception");
    }


    // test after making a move
    init();
    this.defaultBoardEuro.move(3, 1, 3, 3);
    try {
      this.inputTextDefaultEuro.renderBoard();
      this.defaultTextEuro = new MarbleSolitaireTextView(this.defaultBoardEuro);
      assertEquals("    O O O" +
              "\n  O O O O O" +
              "\nO O O O O O O" +
              "\nO _ _ O O O O" +
              "\nO O O O O O O" +
              "\n  O O O O O" +
              "\n    O O O", this.in.toString());
    } catch (IOException e) {
      fail("should not have thrown IO exception");
    }


    // other tests...

    init();
    try {
      this.inputTextEmptyPosEuro.renderBoard();
      assertEquals("    O O O" +
              "\n  O O O O O" +
              "\nO O O _ O O O" +
              "\nO O O O O O O" +
              "\nO O O O O O O" +
              "\n  O O O O O" +
              "\n    O O O", this.in.toString());
    } catch (IOException e) {
      fail("should not have thrown IO exception");
    }

    init();
    try {
      this.inputTextSize.renderBoard();
      assertEquals("                O O O O O O O O O" +
              "\n              O O O O O O O O O O O" +
              "\n            O O O O O O O O O O O O O" +
              "\n          O O O O O O O O O O O O O O O" +
              "\n        O O O O O O O O O O O O O O O O O" +
              "\n      O O O O O O O O O O O O O O O O O O O" +
              "\n    O O O O O O O O O O O O O O O O O O O O O" +
              "\n  O O O O O O O O O O O O O O O O O O O O O O O" +
              "\nO O O O O O O O O O O O O O O O O O O O O O O O O" +
              "\nO O O O O O O O O O O O O O O O O O O O O O O O O" +
              "\nO O O O O O O O O O O O O O O O O O O O O O O O O" +
              "\nO O O O O O O O O O O O O O O O O O O O O O O O O" +
              "\nO O O O O O O O O O O O _ O O O O O O O O O O O O" +
              "\nO O O O O O O O O O O O O O O O O O O O O O O O O" +
              "\nO O O O O O O O O O O O O O O O O O O O O O O O O" +
              "\nO O O O O O O O O O O O O O O O O O O O O O O O O" +
              "\nO O O O O O O O O O O O O O O O O O O O O O O O O" +
              "\n  O O O O O O O O O O O O O O O O O O O O O O O" +
              "\n    O O O O O O O O O O O O O O O O O O O O O" +
              "\n      O O O O O O O O O O O O O O O O O O O" +
              "\n        O O O O O O O O O O O O O O O O O" +
              "\n          O O O O O O O O O O O O O O O" +
              "\n            O O O O O O O O O O O O O" +
              "\n              O O O O O O O O O O O" +
              "\n                O O O O O O O O O", this.in.toString());
    } catch (IOException e) {
      fail("should not have thrown IO exception");
    }

    init();
    try {
      this.inputTextSizeAndPos.renderBoard();
      assertEquals("        O O O O O" +
              "\n      O O _ O O O O" +
              "\n    O O O O O O O O O" +
              "\n  O O O O O O O O O O O" +
              "\nO O O O O O O O O O O O O" +
              "\nO O O O O O O O O O O O O" +
              "\nO O O O O O O O O O O O O" +
              "\nO O O O O O O O O O O O O" +
              "\nO O O O O O O O O O O O O" +
              "\n  O O O O O O O O O O O" +
              "\n    O O O O O O O O O" +
              "\n      O O O O O O O" +
              "\n        O O O O O", this.in.toString());
    } catch (IOException e) {
      fail("should not have thrown IO exception");
    }
  }

  @Test
  public void testRenderMessage() {
    // english
    // test invalid renderMessage that throws IO exception (with all the 4 board types)
    try {
      this.invalidInputTextDefault.renderMessage("hi");
      fail("should have thrown IOException");
    } catch (IOException e) {
      // do nothing; successfully threw IO exception
    }
    try {
      this.invalidInputTextArm.renderMessage("hi");
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
      this.invalidInputTextArmAndPos.renderMessage("hi");
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
      inputTextArm.renderMessage("hi");
      assertEquals("hi", this.in.toString());
    } catch (IOException e) {
      fail("should not have thrown IO exception");
    }

    init();
    try {
      inputTextArmAndPos.renderMessage("hi");
      assertEquals("hi", this.in.toString());
    } catch (IOException e) {
      fail("should not have thrown IO exception");
    }

    // european
    // test invalid renderMessage that throws IO exception (with all the 4 board types)
    try {
      this.invalidInputTextDefaultEuro.renderMessage("hi");
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
      this.invalidInputTextPosEuro.renderMessage("hi");
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
    init();
    try {
      inputTextDefaultEuro.renderMessage("hi");
      assertEquals("hi", this.in.toString());
    } catch (IOException e) {
      fail("should not have thrown IO exception");
    }

    init();
    try {
      inputTextEmptyPosEuro.renderMessage("hi");
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