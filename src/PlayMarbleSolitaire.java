import java.io.InputStreamReader;

import controller.MarbleSolitaireControllerImpl;
import model.EnglishSolitaireModel;
import model.MarbleSolitaireModel;
import model.EuropeanSolitaireModel;
import model.TriangleSolitaireModel;
import view.MarbleSolitaireTextView;
import view.MarbleSolitaireView;
import view.TriangleSolitaireTextView;

/**
 * Class for play testing the game. Represents a game.
 */
public class PlayMarbleSolitaire {
  /**
   * The main method to play the game.
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    // information used to make the appropriate model
    String gameType = "";
    int size = 0;
    int row = 0;
    int col = 0;
    boolean inputPos = false;

    // information used to make the controller
    MarbleSolitaireModel model = null;
    MarbleSolitaireView view = null;
    InputStreamReader in = new InputStreamReader(System.in);

    // interpret the command-line arguments, looking for the key words (type, size, hole),
    // saving the information to be used later
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("english") || args[i].equals("european")) {
        // assign default construction parameters, possibly customize later
        gameType = args[i];
        size = 3;
      } else if (args[i].equals("triangular")) {
        // assign default construction parameters, possibly customize later
        gameType = args[i];
        size = 5;
        row = 0;
        col = 0;
      } else if (args[i].equals("-size")) {
        size = Integer.parseInt(args[i + 1]);
      } else if (args[i].equals("-hole")) {
        row = Integer.parseInt(args[i + 1]) - 1;
        col = Integer.parseInt(args[i + 2]) - 1;
        inputPos = true;
      }
    }

    // dynamically calculate the center for english/european if only size was provided
    if (!inputPos && !gameType.equals("triangular")) {
      double center = (1.5 * size) - 1.5;
      row = (int) center;
      col = (int) center;
    }

    // create the appropriate model and view
    switch (gameType) {
      case "english":
        model = new EnglishSolitaireModel(size, row, col);
        view = new MarbleSolitaireTextView(model);
        break;
      case "european":
        model = new EuropeanSolitaireModel(size, row, col);
        view = new MarbleSolitaireTextView(model);
        break;
      case "triangular":
        model = new TriangleSolitaireModel(size, row, col);
        view = new TriangleSolitaireTextView(model);
        break;
      default:
        break;
    }

    // make controller from information and use it to play the game
    MarbleSolitaireControllerImpl controller = new MarbleSolitaireControllerImpl(model, view, in);
    controller.playGame();
  }
}
