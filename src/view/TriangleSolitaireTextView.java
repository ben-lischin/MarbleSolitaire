package view;

import model.MarbleSolitaireModelState;

/**
 * Represents the text version of the Triangle Solitaire game.
 */
public class TriangleSolitaireTextView extends MarbleSolitaireTextView {
  public TriangleSolitaireTextView(MarbleSolitaireModelState gameState)
          throws IllegalArgumentException {
    super(gameState);
  }

  public TriangleSolitaireTextView(MarbleSolitaireModelState gameState, Appendable out)
          throws IllegalArgumentException {
    super(gameState, out);
  }

  @Override
  public String toString() {
    // the base string for the result that will be constructed in the for loops
    String boardString = "";

    for (int row = 0; row < this.gameState.getBoardSize(); row++) {
      if (row != 0) {
        boardString += "\n";
      }
      for (int i = 1; i < this.gameState.getBoardSize() - row; i++) {
        boardString += " ";
      }
      for (int col = 0; col <= row; col++) {
        // add appropriate text to represent the slot
        switch (this.gameState.getSlotAt(row, col)) {
          case Marble:
            if (col != 0) {
              boardString += " ";
            }
            boardString += "O";
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
}
