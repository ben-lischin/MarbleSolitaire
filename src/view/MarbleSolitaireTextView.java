package view;

import java.io.IOException;

import model.MarbleSolitaireModelState;

/**
 * Represents the text version of the Marble Solitaire game
 *     (can be utilized for either English or European games).
 */
public class MarbleSolitaireTextView implements MarbleSolitaireView {
  protected MarbleSolitaireModelState gameState;
  protected Appendable out;

  /**
   * Creates a text representation of the solitaire game.
   *
   * @param gameState a (@code MarbleSolitaireModelState) object that represents the solitaire game
   * @throws IllegalArgumentException when the provided (@code gameState) is null
   */
  public MarbleSolitaireTextView(MarbleSolitaireModelState gameState) throws
          IllegalArgumentException {
    if (gameState == null) {
      throw new IllegalArgumentException("Must provide a non-null game model");
    } else {
      this.gameState = gameState;
      this.out = System.out;
    }
  }

  /**
   * Creates a text representation of the solitaire game.
   * @param gameState a (@code MarbleSolitaireModelState) object that represents the solitaire game
   * @param out an (@code Appendable) output stream
   * @throws IllegalArgumentException when any of the inputs are null
   */
  public MarbleSolitaireTextView(MarbleSolitaireModelState gameState, Appendable out)
          throws IllegalArgumentException {
    if (gameState == null || out == null) {
      throw new IllegalArgumentException("Must provide a non-null game model");
    } else {
      this.gameState = gameState;
      this.out = out;
    }

  }

  @Override
  public String toString() {
    // the base string for the result that will be constructed in the for loops
    String boardString = "";

    for (int row = 0; row < this.gameState.getBoardSize(); row++) {
      if (row != 0) {
        boardString += "\n";
      }
      for (int col = 0; col < this.gameState.getBoardSize(); col++) {

        // add appropriate text to represent the slot
        switch (this.gameState.getSlotAt(row, col)) {
          case Marble:
            if (col != 0) {
              boardString += " ";
            }
            boardString += "O";
            break;
          case Invalid:
            int center = (this.gameState.getBoardSize() - 1) / 2;
            if (col < center) {
              boardString += " ";
              if (col != 0) {
                boardString += " ";
              }
            }
            break;
          case Empty:
            if (col != 0) {
              boardString += " ";
            }
            boardString += "_";
            break;
          default:
            // should never even reach this case
            break;
        }
      }
    }
    return boardString;
  }

  // transmits the state of the board produced by this view (exactly as before) to the Appendable
  // object provided through its constructor
  // If any transmission fails, then these methods should throw an IOException exception.
  @Override
  public void renderBoard() throws IOException {
    this.renderMessage(this.toString());
  }

  // transmits the given message to the Appendable object
  // If any transmission fails, then these methods should throw an IOException exception.
  @Override
  public void renderMessage(String message) throws IOException {
    this.out.append(message);
  }
}
