package model;

/**
 * Represents the Triangle Solitaire game board.
 */
public class TriangleSolitaireModel extends EnglishSolitaireModel {
  public TriangleSolitaireModel() {
    super(5, 0, 0);
  }

  public TriangleSolitaireModel(int sRow, int sCol) throws IllegalArgumentException {
    super(5, sRow, sCol);
  }

  public TriangleSolitaireModel(int sideLength) throws IllegalArgumentException {
    super(sideLength, 0, 0);
  }

  public TriangleSolitaireModel(int sideLength, int sRow, int sCol)
          throws IllegalArgumentException {
    super(sideLength, sRow, sCol);
  }

  @Override
  protected boolean invalidSize(int armSize) {
    return armSize < 0;
  }


  @Override
  protected boolean invalidEmptyPos(int sideLength, int sRow, int sCol) {
    return (sCol > sRow) || (sRow >= sideLength) || (sCol < 0) || (sRow < 0);
  }

  @Override
  protected void generateBoard(int sideLength, int sRow, int sCol) {
    // create board of proper size, all slots are initialized to null
    this.board = new SlotState[sideLength][sideLength];

    for (int row = 0; row < sideLength; row++) {
      for (int col = 0; col < sideLength; col++) {
        // assign marbles
        if (col <= row) {
          this.board[row][col] = SlotState.Marble;
          score += 1;
        }
        // make others Invalid
        else {
          this.board[row][col] = SlotState.Invalid;
        }
      }
    }
    // make the input position empty
    this.board[sRow][sCol] = SlotState.Empty;
    // account for changing the marble to empty in the score
    this.score -= 1;
  }

  @Override
  protected void checkValidMove(int fromRow, int fromCol, int toRow, int toCol)
          throws IllegalArgumentException {
    // check that they are 2 slots away
    // diagonals ARE permitted
    if (!((Math.abs(toRow - fromRow) == 2 &&
            (toCol == fromCol || Math.abs(toCol - fromCol) == 2))
            || (Math.abs(toCol - fromCol) == 2 &&
            (toRow == fromRow || (Math.abs(toRow - fromRow) == 2))))) {
      throw new IllegalArgumentException("Must move 2 slots");
    }
    // check rest
    this.checkValidMoveHelper(fromRow, fromCol, toRow, toCol);
  }

  @Override
  public boolean isGameOver() {
    for (int row = 0; row < this.getBoardSize(); row++) {
      for (int col = 0; col < this.getBoardSize(); col++) {
        // check for a move in all directions for each slot
        // if no exception is thrown, return false
        // (i.e. there is a valid move to be made, so the game is not over)

        // if theres a valid move up --> false
        // catch IllegalArgumentException
        try {
          this.checkValidMove(row, col, row + 2, col);
          return false;
        } catch (IllegalArgumentException e) {
          // do nothing
        }

        // if theres a valid move down --> false
        // catch IllegalArgumentException
        try {
          this.checkValidMove(row, col, row - 2, col);
          return false;
        } catch (IllegalArgumentException e) {
          // do nothing
        }

        // if theres a valid move left --> false
        // catch IllegalArgumentException
        try {
          this.checkValidMove(row, col, row, col - 2);
          return false;
        } catch (IllegalArgumentException e) {
          // do nothing
        }

        // if theres a valid move right --> false
        // catch IllegalArgumentException
        try {
          this.checkValidMove(row, col, row, col + 2);
          return false;
        } catch (IllegalArgumentException e) {
          // do nothing
        }

        // ********* NEW MOVES WITH TRIANGLE MODEL : DIAGONALS *********

        try {
          this.checkValidMove(row, col, row + 2, col + 2);
          return false;
        } catch (IllegalArgumentException e) {
          // do nothing
        }

        try {
          this.checkValidMove(row, col, row - 2, col - 2);
          return false;
        } catch (IllegalArgumentException e) {
          // do nothing
        }

        try {
          this.checkValidMove(row, col, row + 2, col - 2);
          return false;
        } catch (IllegalArgumentException e) {
          // do nothing
        }

        try {
          this.checkValidMove(row, col, row - 2, col + 2);
          return false;
        } catch (IllegalArgumentException e) {
          // do nothing
        }


      }
    }

    // if no move was found while looping through the board, then the game is over
    return true;
  }
}
