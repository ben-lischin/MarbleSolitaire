import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

import controller.MarbleSolitaireController;
import controller.MarbleSolitaireControllerImpl;
import model.EnglishSolitaireModel;
import model.MarbleSolitaireModel;
import model.EuropeanSolitaireModel;
import model.TriangleSolitaireModel;
import view.MarbleSolitaireTextView;
import view.MarbleSolitaireView;
import view.TriangleSolitaireTextView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test class for the controller.
 */
public class MarbleSolitaireControllerImplTest {

  // mock for a solitaire model object
  private class ModelMock implements MarbleSolitaireModel {
    private final StringBuilder log;

    public ModelMock(StringBuilder log) throws IllegalArgumentException {
      if (log == null) {
        throw new IllegalArgumentException("log cannot be null");
      }
      this.log = log;
    }

    @Override
    public void move(int fromRow, int fromCol, int toRow, int toCol)
            throws IllegalArgumentException {
      log.append("from: (" + fromRow + "," + fromCol + "), to: (" + toRow + "," + toCol + ")\n");
    }

    @Override
    public boolean isGameOver() {
      return false;
    }

    @Override
    public int getBoardSize() {
      return 0;
    }

    @Override
    public SlotState getSlotAt(int row, int col) throws IllegalArgumentException {
      log.append("row = " + row + ", col = " + col + "\n");
      return null;
    }

    @Override
    public int getScore() {
      return 0;
    }
  }


  Readable in;
  Appendable out;
  AppendableMock badOut;
  MarbleSolitaireView badView;
  StringBuilder log;
  MarbleSolitaireModel modelIn;
  MarbleSolitaireView viewIn;
  MarbleSolitaireModel modelOut;
  MarbleSolitaireView viewOut;
  MarbleSolitaireController controller;
  String[] messages;

  String startBoard = "    O O O" +
          "\n    O O O" +
          "\nO O O O O O O" +
          "\nO O O _ O O O" +
          "\nO O O O O O O" +
          "\n    O O O" +
          "\n    O O O";

  @Before
  public void init() {
    // for both
    out = new StringBuilder();
    log = new StringBuilder();

    // for testInput()
    modelIn = new ModelMock(log);
    viewIn = new MarbleSolitaireTextView(modelIn, out);

    // for testOutput()
    modelOut = new EnglishSolitaireModel();
    viewOut = new MarbleSolitaireTextView(modelOut, out);
  }

  private void setEuro() {
    out = new StringBuilder();
    modelOut = new EuropeanSolitaireModel();
    viewOut = new MarbleSolitaireTextView(modelOut, out);
  }

  private void setTri() {
    out = new StringBuilder();
    modelOut = new TriangleSolitaireModel();
    viewOut = new TriangleSolitaireTextView(modelOut, out);
  }

  @Test
  // tests if the controller works with the model using a mock model
  // not responsible for determining if the move is valid, invalid, etc
  public void testInput() {
    // valid move (then quit)
    in = new StringReader("6 4 4 4 q");
    controller = new MarbleSolitaireControllerImpl(modelIn, viewIn, in);
    controller.playGame();
    assertEquals("from: (5,3), to: (3,3)\n", log.toString());

    init();
    // two valid moves (then quit)
    in = new StringReader("6 4 4 4 5 6 5 4 q");
    controller = new MarbleSolitaireControllerImpl(modelIn, viewIn, in);
    controller.playGame();
    assertEquals("from: (5,3), to: (3,3)\n" +
            "from: (4,5), to: (4,3)\n", log.toString());

    init();
    // invalid move (then quit)
    in = new StringReader("1 2 3 4 q");
    controller = new MarbleSolitaireControllerImpl(modelIn, viewIn, in);
    controller.playGame();
    assertEquals("from: (0,1), to: (2,3)\n", log.toString());

    init();
    // valid then invalid move (then quit)
    in = new StringReader("6 4 4 4 1 2 3 4 q");
    controller = new MarbleSolitaireControllerImpl(modelIn, viewIn, in);
    controller.playGame();
    assertEquals("from: (5,3), to: (3,3)\n"
            + "from: (0,1), to: (2,3)\n", log.toString());

    init();
    // invalid then valid move (then quit)
    in = new StringReader("1 2 3 4 6 4 4 4 q");
    controller = new MarbleSolitaireControllerImpl(modelIn, viewIn, in);
    controller.playGame();
    assertEquals("from: (0,1), to: (2,3)\n"
            + "from: (5,3), to: (3,3)\n", log.toString());

    init();
    // invalid then invalid move (then quit)
    in = new StringReader("1 2 3 4 5 6 7 8 q");
    controller = new MarbleSolitaireControllerImpl(modelIn, viewIn, in);
    controller.playGame();
    assertEquals("from: (0,1), to: (2,3)\n"
            + "from: (4,5), to: (6,7)\n", log.toString());

    init();
    // with an incomplete move (then quit)
    in = new StringReader("6 4 4 q");
    controller = new MarbleSolitaireControllerImpl(modelIn, viewIn, in);
    controller.playGame();
    assertEquals("", log.toString());

    init();
    // with an incomplete move after a valid move (then quit)
    in = new StringReader("6 4 4 4 5 6 q");
    controller = new MarbleSolitaireControllerImpl(modelIn, viewIn, in);
    controller.playGame();
    assertEquals("from: (5,3), to: (3,3)\n", log.toString());

    init();
    // with an incomplete move after an invalid move (then quit)
    in = new StringReader("10 5 4 9 7 1 q");
    controller = new MarbleSolitaireControllerImpl(modelIn, viewIn, in);
    controller.playGame();
    assertEquals("from: (9,4), to: (3,8)\n", log.toString());

    init();
    // with extra letters in the input (then quit), extra letters should be ignored
    in = new StringReader("t 6 hi 4 4 4 y q");
    controller = new MarbleSolitaireControllerImpl(modelIn, viewIn, in);
    controller.playGame();
    assertEquals("from: (5,3), to: (3,3)\n", log.toString());

  }

  @Test
  // test for playGame / if the controller is outputting the right messages
  public void testOutput() {
    // test bad appendable
    badOut = new AppendableMock();
    badView = new MarbleSolitaireTextView(modelOut, badOut);
    in = new StringReader("1 2 3 4 5 6 7 8 q");
    controller = new MarbleSolitaireControllerImpl(modelOut, badView, in);
    try {
      controller.playGame();
      fail("should have thrown exception due to Appendable unable to transmit");
    } catch (IllegalStateException e) {
      // do nothing; successfully threw exception
    }


    // test throwing an exception if game never ends / is never quit
    // (i.e. if a move is given then nothing else)
    try {
      in = new StringReader("6 4 4 4");
      controller = new MarbleSolitaireControllerImpl(modelOut, viewOut, in);
      assertEquals(startBoard, viewOut.toString());
      controller.playGame();
      fail("should have thrown an IllegalStateException");
    } catch (IllegalStateException e) {
      // do nothing; successfully threw an IllegalStateException
    }

    init();
    // quit (q in from-row)
    in = new StringReader("q");
    controller = new MarbleSolitaireControllerImpl(modelOut, viewOut, in);
    assertEquals(startBoard, viewOut.toString());
    controller.playGame();
    assertEquals(startBoard, viewOut.toString());
    messages = out.toString().split("\n");
    String score1 = messages[7];
    String quit = messages[8];
    String quitState = messages[9];
    String score2 = messages[17];
    assertEquals("Score: 32", score1);
    assertEquals("Game quit!", quit);
    assertEquals("State of game when quit:", quitState);
    assertEquals("Score: 32", score2);
    // checking that the output is as long as i expected it to be
    // (i.e. that it has all the information)
    assertEquals(18, messages.length);
    init();
    // also the same as an incomplete move then quit (q in from-col)
    in = new StringReader("6 q");
    controller = new MarbleSolitaireControllerImpl(modelOut, viewOut, in);
    assertEquals(startBoard, viewOut.toString());
    controller.playGame();
    assertEquals(startBoard, viewOut.toString());
    assertEquals("Score: 32", score1);
    assertEquals("Game quit!", quit);
    assertEquals("State of game when quit:", quitState);
    assertEquals("Score: 32", score2);
    init();
    // also the same as an incomplete move then quit (q in to-row)
    in = new StringReader("6 4 q");
    controller = new MarbleSolitaireControllerImpl(modelOut, viewOut, in);
    assertEquals(startBoard, viewOut.toString());
    controller.playGame();
    assertEquals(startBoard, viewOut.toString());
    assertEquals("Score: 32", score1);
    assertEquals("Game quit!", quit);
    assertEquals("State of game when quit:", quitState);
    assertEquals("Score: 32", score2);
    init();
    // also the same as an incomplete move then quit (q in to-col)
    in = new StringReader("6 4 4 q");
    controller = new MarbleSolitaireControllerImpl(modelOut, viewOut, in);
    assertEquals(startBoard, viewOut.toString());
    controller.playGame();
    assertEquals(startBoard, viewOut.toString());
    assertEquals("Score: 32", score1);
    assertEquals("Game quit!", quit);
    assertEquals("State of game when quit:", quitState);
    assertEquals("Score: 32", score2);
    init();
    // also the same as stuff after the quit
    in = new StringReader("q 6 4 4 4");
    controller = new MarbleSolitaireControllerImpl(modelOut, viewOut, in);
    assertEquals(startBoard, viewOut.toString());
    controller.playGame();
    assertEquals(startBoard, viewOut.toString());
    assertEquals("Score: 32", score1);
    assertEquals("Game quit!", quit);
    assertEquals("State of game when quit:", quitState);
    assertEquals("Score: 32", score2);
    init();
    // also the same as an incomplete move with extra non-q letters mixed in
    in = new StringReader("a 6 b q");
    controller = new MarbleSolitaireControllerImpl(modelOut, viewOut, in);
    assertEquals(startBoard, viewOut.toString());
    controller.playGame();
    assertEquals(startBoard, viewOut.toString());
    assertEquals("Score: 32", score1);
    assertEquals("Game quit!", quit);
    assertEquals("State of game when quit:", quitState);
    assertEquals("Score: 32", score2);
    init();


    // valid move
    init();
    in = new StringReader("6 4 4 4 q");
    controller = new MarbleSolitaireControllerImpl(modelOut, viewOut, in);
    assertEquals(startBoard, viewOut.toString());
    controller.playGame();
    assertEquals("    O O O" +
            "\n    O O O" +
            "\nO O O O O O O" +
            "\nO O O O O O O" +
            "\nO O O _ O O O" +
            "\n    O _ O" +
            "\n    O O O", viewOut.toString());
    messages = out.toString().split("\n");
    score1 = messages[7];
    score2 = messages[15];
    quit = messages[16];
    quitState = messages[17];
    String score3 = messages[25];
    assertEquals("Score: 32", score1);
    assertEquals("Score: 31", score2);
    assertEquals("Game quit!", quit);
    assertEquals("State of game when quit:", quitState);
    assertEquals("Score: 31", score3);
    // testing for the presence of the board states, ignoring what they actually look like
    assertEquals(26, messages.length);
    init();
    // also the same with extra non-q letters mixed in
    in = new StringReader("6 a y 4 4 z 4 q");
    controller = new MarbleSolitaireControllerImpl(modelOut, viewOut, in);
    assertEquals(startBoard, viewOut.toString());
    controller.playGame();
    assertEquals("    O O O" +
            "\n    O O O" +
            "\nO O O O O O O" +
            "\nO O O O O O O" +
            "\nO O O _ O O O" +
            "\n    O _ O" +
            "\n    O O O", viewOut.toString());
    assertEquals("Score: 32", score1);
    assertEquals("Score: 31", score2);
    assertEquals("Game quit!", quit);
    assertEquals("State of game when quit:", quitState);
    assertEquals("Score: 31", score3);
    assertEquals(26, messages.length);
    init();
    // also the same as an incomplete move after a valid move
    in = new StringReader("6 4 4 4 3 4 5 q");
    controller = new MarbleSolitaireControllerImpl(modelOut, viewOut, in);
    assertEquals(startBoard, viewOut.toString());
    controller.playGame();
    assertEquals("    O O O" +
            "\n    O O O" +
            "\nO O O O O O O" +
            "\nO O O O O O O" +
            "\nO O O _ O O O" +
            "\n    O _ O" +
            "\n    O O O", viewOut.toString());
    assertEquals("Score: 32", score1);
    assertEquals("Score: 31", score2);
    assertEquals("Game quit!", quit);
    assertEquals("State of game when quit:", quitState);
    assertEquals("Score: 31", score3);
    assertEquals(26, messages.length);


    // valid move after valid move
    init();
    in = new StringReader("6 4 4 4 5 6 5 4 q");
    controller = new MarbleSolitaireControllerImpl(modelOut, viewOut, in);
    assertEquals(startBoard, viewOut.toString());
    controller.playGame();
    assertEquals("    O O O" +
            "\n    O O O" +
            "\nO O O O O O O" +
            "\nO O O O O O O" +
            "\nO O O O _ _ O" +
            "\n    O _ O" +
            "\n    O O O", viewOut.toString());
    messages = out.toString().split("\n");
    score1 = messages[7];
    score2 = messages[15];
    score3 = messages[23];
    quit = messages[24];
    quitState = messages[25];
    String score4 = messages[33];
    assertEquals("Score: 32", score1);
    assertEquals("Score: 31", score2);
    assertEquals("Score: 30", score3);
    assertEquals("Game quit!", quit);
    assertEquals("State of game when quit:", quitState);
    assertEquals("Score: 30", score4);
    assertEquals(34, messages.length);


    // invalid move
    init();
    in = new StringReader("6 6 1 2 q");
    controller = new MarbleSolitaireControllerImpl(modelOut, viewOut, in);
    assertEquals(startBoard, viewOut.toString());
    controller.playGame();
    assertEquals(startBoard, viewOut.toString());
    messages = out.toString().split("\n");
    score1 = messages[7];
    String invalid1 = messages[8];
    quit = messages[9];
    quitState = messages[10];
    score2 = messages[18];
    assertEquals("Score: 32", score1);
    assertEquals("Invalid move. Play again.", invalid1);
    assertEquals("Game quit!", quit);
    assertEquals("State of game when quit:", quitState);
    assertEquals("Score: 32", score2);
    assertEquals(19, messages.length);
    init();
    // also the same as an invalid move with extra non-q letters mixed in
    in = new StringReader("a 1 2 d z 3 4 q");
    controller = new MarbleSolitaireControllerImpl(modelOut, viewOut, in);
    assertEquals(startBoard, viewOut.toString());
    controller.playGame();
    assertEquals(startBoard, viewOut.toString());
    assertEquals("Score: 32", score1);
    assertEquals("Invalid move. Play again.", invalid1);
    assertEquals("Game quit!", quit);
    assertEquals("State of game when quit:", quitState);
    assertEquals("Score: 32", score2);
    assertEquals(19, messages.length);
    init();
    // also the same as an incomplete move after invalid move
    in = new StringReader("1 2 3 4 6 q");
    controller = new MarbleSolitaireControllerImpl(modelOut, viewOut, in);
    assertEquals(startBoard, viewOut.toString());
    controller.playGame();
    assertEquals(startBoard, viewOut.toString());
    assertEquals("Score: 32", score1);
    assertEquals("Invalid move. Play again.", invalid1);
    assertEquals("Game quit!", quit);
    assertEquals("State of game when quit:", quitState);
    assertEquals("Score: 32", score2);
    assertEquals(19, messages.length);


    // invalid move after invalid move
    init();
    in = new StringReader("1 2 3 4 5 6 7 8 q");
    controller = new MarbleSolitaireControllerImpl(modelOut, viewOut, in);
    assertEquals(startBoard, viewOut.toString());
    controller.playGame();
    assertEquals(startBoard, viewOut.toString());
    messages = out.toString().split("\n");
    score1 = messages[7];
    invalid1 = messages[8];
    String invalid2 = messages[9];
    quit = messages[10];
    quitState = messages[11];
    score2 = messages[19];
    assertEquals("Score: 32", score1);
    assertEquals("Invalid move. Play again.", invalid1);
    assertEquals("Invalid move. Play again.", invalid2);
    assertEquals("Game quit!", quit);
    assertEquals("State of game when quit:", quitState);
    assertEquals("Score: 32", score2);
    assertEquals(20, messages.length);


    // valid move after invalid move
    init();
    in = new StringReader("1 2 3 4 6 4 4 4 q");
    controller = new MarbleSolitaireControllerImpl(modelOut, viewOut, in);
    assertEquals(startBoard, viewOut.toString());
    controller.playGame();
    assertEquals("    O O O" +
            "\n    O O O" +
            "\nO O O O O O O" +
            "\nO O O O O O O" +
            "\nO O O _ O O O" +
            "\n    O _ O" +
            "\n    O O O", viewOut.toString());
    messages = out.toString().split("\n");
    score1 = messages[7];
    invalid1 = messages[8];
    score2 = messages[16];
    quit = messages[17];
    quitState = messages[18];
    score3 = messages[26];
    assertEquals("Score: 32", score1);
    assertEquals("Invalid move. Play again.", invalid1);
    assertEquals("Score: 31", score2);
    assertEquals("Game quit!", quit);
    assertEquals("State of game when quit:", quitState);
    assertEquals("Score: 31", score3);
    assertEquals(27, messages.length);


    // invalid move after valid move
    init();
    in = new StringReader("6 4 4 4 1 2 3 4 q");
    controller = new MarbleSolitaireControllerImpl(modelOut, viewOut, in);
    assertEquals(startBoard, viewOut.toString());
    controller.playGame();
    assertEquals("    O O O" +
            "\n    O O O" +
            "\nO O O O O O O" +
            "\nO O O O O O O" +
            "\nO O O _ O O O" +
            "\n    O _ O" +
            "\n    O O O", viewOut.toString());
    messages = out.toString().split("\n");
    score1 = messages[7];
    score2 = messages[15];
    invalid1 = messages[16];
    quit = messages[17];
    quitState = messages[18];
    score3 = messages[26];
    assertEquals("Score: 32", score1);
    assertEquals("Score: 31", score2);
    assertEquals("Invalid move. Play again.", invalid1);
    assertEquals("Game quit!", quit);
    assertEquals("State of game when quit:", quitState);
    assertEquals("Score: 31", score3);
    assertEquals(27, messages.length);

    // all possible cases for valid/invalid/incomplete/quit messages already tested to display
    // now test game over message:
    init();
    // the input of valid moves that will complete the game (no quit)
    in = new StringReader("6 4 4 4 5 6 5 4 7 5 5 5  7 3 7 5  4 5 6 5 7 5 5 5 "
            + "2 5 4 5 3 7 3 5 5 7 3 7 3 4 3 6 3 7 3 5 3 2 3 4 "
            + "1 3 3 3 1 5 1 3 4 3 2 3 1 3 3 3 6 3 4 3 5 1 5 3 3 1 5 1 5 4 5 2 "
            + "5 1 5 3 3 4 3 2 3 2 5 2 5 2 5 4 5 4 5 6 5 6 3 6 3 6 3 4 4 4 4 6 2 4 4 4 "
            + "4 3 4 5 4 6 4 4 ");
    controller = new MarbleSolitaireControllerImpl(modelOut, viewOut, in);
    assertEquals(startBoard, viewOut.toString());
    controller.playGame();
    assertTrue(modelOut.isGameOver());
    assertEquals("    _ _ _" +
            "\n    _ _ _" +
            "\n_ _ _ _ _ _ _" +
            "\n_ _ _ O _ _ _" +
            "\n_ _ _ _ _ _ _" +
            "\n    _ _ _" +
            "\n    _ _ _", viewOut.toString());
    messages = out.toString().split("\n");
    // (ignore score messages; we already tested that those work)
    String end = messages[248];
    assertEquals("Game over!", end);
    assertEquals(257, messages.length);







    ////////////////////////////////////////////////////////////////////////////////////////////////
    // above we thoroughly tested the functionality of our controller and playGame
    // (using an english solitaire model)
    // ... now check that it is also compatible/functional with the european and triangle models:
    // (dont need to test all the cases again, just check compatibility with the other model types)
    ////////////////////////////////////////////////////////////////////////////////////////////////


    // european

    setEuro();
    // valid move
    in = new StringReader("6 4 4 4 q");
    controller = new MarbleSolitaireControllerImpl(modelOut, viewOut, in);
    assertEquals("    O O O" +
            "\n  O O O O O" +
            "\nO O O O O O O" +
            "\nO O O _ O O O" +
            "\nO O O O O O O" +
            "\n  O O O O O" +
            "\n    O O O", viewOut.toString());
    controller.playGame();
    assertEquals("    O O O" +
            "\n  O O O O O" +
            "\nO O O O O O O" +
            "\nO O O O O O O" +
            "\nO O O _ O O O" +
            "\n  O O _ O O" +
            "\n    O O O", viewOut.toString());
    messages = out.toString().split("\n");
    score1 = messages[7];
    score2 = messages[15];
    quit = messages[16];
    quitState = messages[17];
    score3 = messages[25];
    assertEquals("Score: 36", score1);
    assertEquals("Score: 35", score2);
    assertEquals("Game quit!", quit);
    assertEquals("State of game when quit:", quitState);
    assertEquals("Score: 35", score3);
    assertEquals(26, messages.length);

    setEuro();
    // plays game to completion / end message
    in = new StringReader("6 4 4 4 5 6 5 4 7 5 5 5  7 3 7 5  4 5 6 5 7 5 5 5 "
            + "2 5 4 5 3 7 3 5 5 7 3 7 3 4 3 6 3 7 3 5 3 2 3 4 "
            + "1 3 3 3 1 5 1 3 4 3 2 3 1 3 3 3 6 3 4 3 5 1 5 3 3 1 5 1 5 4 5 2 "
            + "5 1 5 3 3 4 3 2 3 2 5 2 5 2 5 4 5 4 5 6 5 6 3 6 3 6 3 4 4 4 4 6 2 4 4 4 "
            + "4 3 4 5 4 6 4 4 ");
    controller = new MarbleSolitaireControllerImpl(modelOut, viewOut, in);
    assertEquals("    O O O" +
            "\n  O O O O O" +
            "\nO O O O O O O" +
            "\nO O O _ O O O" +
            "\nO O O O O O O" +
            "\n  O O O O O" +
            "\n    O O O", viewOut.toString());
    controller.playGame();
    assertTrue(modelOut.isGameOver());
    assertEquals("    _ _ _" +
            "\n  O _ _ _ O" +
            "\n_ _ _ _ _ _ _" +
            "\n_ _ _ O _ _ _" +
            "\n_ _ _ _ _ _ _" +
            "\n  O _ _ _ O" +
            "\n    _ _ _", viewOut.toString());
    messages = out.toString().split("\n");
    end = messages[248];
    assertEquals("Game over!", end);
    assertEquals(257, messages.length);


    // triangle
    setTri();
    // valid move
    in = new StringReader("3 1 1 1 q");
    controller = new MarbleSolitaireControllerImpl(modelOut, viewOut, in);
    assertEquals("    _\n" +
            "   O O\n" +
            "  O O O\n" +
            " O O O O\n" +
            "O O O O O", viewOut.toString());
    controller.playGame();
    assertEquals("    O\n" +
            "   _ O\n" +
            "  _ O O\n" +
            " O O O O\n" +
            "O O O O O", viewOut.toString());
    messages = out.toString().split("\n");
    score1 = messages[5];
    score2 = messages[11];
    quit = messages[12];
    quitState = messages[13];
    score3 = messages[19];
    assertEquals("Score: 14", score1);
    assertEquals("Score: 13", score2);
    assertEquals("Game quit!", quit);
    assertEquals("State of game when quit:", quitState);
    assertEquals("Score: 13", score3);
    assertEquals(20, messages.length);

    setTri();
    // plays game to completion / end message
    in = new StringReader("3 1 1 1 3 3 3 1 1 1 3 3 5 2 3 2 4 1 2 1 3 3 3 1 " +
            "2 1 4 1 5 1 3 1 4 4 4 2 5 4 5 2 3 1 5 3 5 2 5 4 5 5 5 3");
    controller = new MarbleSolitaireControllerImpl(modelOut, viewOut, in);
    assertEquals("    _\n" +
            "   O O\n" +
            "  O O O\n" +
            " O O O O\n" +
            "O O O O O", viewOut.toString());
    controller.playGame();
    assertTrue(modelOut.isGameOver());
    assertEquals("    _\n" +
            "   _ _\n" +
            "  _ _ _\n" +
            " _ _ _ _\n" +
            "_ _ O _ _", viewOut.toString());
    messages = out.toString().split("\n");
    end = messages[78];
    assertEquals("Game over!", end);
    assertEquals(85, messages.length);

  }
}