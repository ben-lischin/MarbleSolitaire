package model;

/**
 * Represents the European Solitaire game board.
 */
public class EuropeanSolitaireModel extends EnglishSolitaireModel {

  public EuropeanSolitaireModel() {
    super();
  }

  public EuropeanSolitaireModel(int sRow, int sCol) throws IllegalArgumentException {
    super(sRow, sCol);
  }

  public EuropeanSolitaireModel(int sideLength) throws IllegalArgumentException {
    super(sideLength);
  }

  public EuropeanSolitaireModel(int sideLength, int sRow, int sCol)
          throws IllegalArgumentException {
    super(sideLength, sRow, sCol);
  }

  @Override
  protected boolean invalidSize(int armSize) {
    return armSize < 0;
  }

  @Override
  protected boolean invalidEmptyPos(int sideLength, int sRow, int sCol) {
    return (!(((sCol <= sideLength - 2) && (sRow >= sideLength - 1 - sCol)
            && (sRow <= (2 * sideLength) - 2 + sCol))
            || ((sCol > sideLength - 2) && (sCol < (2 * sideLength) - 1))
            || ((sCol >= (2 * sideLength) - 1) && (sRow >= sCol - (2 * sideLength) + 2)
            && (sRow <= -5 + (5 * sideLength) - sCol))))
            || sRow < 0 || sRow >= (sideLength * 3) - 2 || sCol < 0 || sCol >= (sideLength * 3) - 2;
  }

  @Override
  protected void generateBoard(int sideLength, int sRow, int sCol) {
    // calculate the length/width of the board
    int dimension = 3 * (sideLength - 1) + 1;
    //create board of proper size, all slots are initialized to null
    this.board = new SlotState[dimension][dimension];
    for (int row = 0; row < dimension; row++) {
      for (int col = 0; col < dimension; col++) {
        // check to make marble octagon
        // (break into left trapezoid, middle rectangle, right trapezoid)
        if  (((col <= sideLength - 2) && (row >= sideLength - 1 - col)
                && (row <= (2 * sideLength) - 2 + col))
                || ((col > sideLength - 2) && (col < (2 * sideLength) - 1))
                || ((col >= (2 * sideLength) - 1) && (row >= col - (2 * sideLength) + 2)
                && (row <= -5 + (5 * sideLength) - col))) {
          this.board[row][col] = SlotState.Marble;
          // count the marble in the score
          this.score += 1;
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
}
