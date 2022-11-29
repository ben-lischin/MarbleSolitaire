package model;

/**
 * Represents the English Solitaire game board.
 */
public class EnglishSolitaireModel implements MarbleSolitaireModel {
  protected SlotState[][] board;
  protected int score;


  /**
   * Constructs a default solitaire game board.
   */
  public EnglishSolitaireModel() {
    score = 0;
    this.generateBoard(3, 3, 3);
  }

  /**
   * Constructs a solitaire game board given the empty slot position.
   *
   * @param sRow the vertical index of the empty slot
   * @param sCol the horizontal index of the empty slot
   * @throws IllegalArgumentException throws when the given position is invalid
   */
  public EnglishSolitaireModel(int sRow, int sCol) throws IllegalArgumentException {
    score = 0;
    // check for invalid corners
    // (top left) or (top right) or (bottom left) or (bottom right) respectively
    // throw exception if given position is in an invalid corner
    if (invalidEmptyPos(3, sRow, sCol)) {
      throw new IllegalArgumentException("Invalid empty cell position (" + sRow + "," + sCol + ")");
    } else {
      this.generateBoard(3, sRow, sCol);
    }
  }

  /**
   * Constructs a solitaire game board given the size of an arm.
   *
   * @param armSize the size of an arm on the board
   * @throws IllegalArgumentException throws when (@code armSize) is negative or even
   */
  public EnglishSolitaireModel(int armSize) throws IllegalArgumentException {
    score = 0;
    // throw exception if size is not positive and odd (aka if it is negative or even)
    if (invalidSize(armSize)) {
      throw new IllegalArgumentException("Invalid size");
    } else {
      double center = (1.5 * armSize) - 1.5;
      // since the armSize is implied to be odd in this if branch,
      // this computation will result in an integer (so we can safely cast it from double to int)
      this.generateBoard(armSize, (int) center, (int) center);
    }
  }

  /**
   * Constructs a solitaire game board given the size of an arm, and the empty slot position.
   *
   * @param armSize the size of an arm on the board
   * @param sRow    the vertical index of the empty slot
   * @param sCol    the horizontal index of the empty slot
   * @throws IllegalArgumentException when the given position is invalid
   *                                  or when (@code armSize) is negative or even
   */
  public EnglishSolitaireModel(int armSize, int sRow, int sCol) throws IllegalArgumentException {
    score = 0;
    // throw exception if size is not positive and odd
    if (invalidSize(armSize)) {
      throw new IllegalArgumentException("Invalid size");
    }
    // throw exception if given position is in an invalid corner
    else if (invalidEmptyPos(armSize, sRow, sCol)) {
      throw new IllegalArgumentException("Invalid empty cell position (" + sRow + "," + sCol + ")");
    } else {
      this.generateBoard(armSize, sRow, sCol);
    }
  }

  protected boolean invalidSize(int armSize) {
    return armSize < 0 || (armSize % 2 == 0);
  }

  /**
   * Determines if the given empty slot position is invalid.
   * @param armSize the size of an arm on the board
   * @param sRow    the vertical index of the empty slot
   * @param sCol    the horizontal index of the empty slot
   * @return true if the empty slot position is invalid
   */
  protected boolean invalidEmptyPos(int armSize, int sRow, int sCol) {
    return (sRow <= armSize - 2 && sCol <= armSize - 2)
            || (sRow <= armSize - 2 && sCol >= (2 * armSize) - 1)
            || (sRow >= (2 * armSize) - 1 && sCol <= armSize - 2)
            || (sRow >= (2 * armSize) - 1 && sCol >= (2 * armSize) - 1)
            || sRow < 0 || sRow >= (armSize * 3) - 2 || sCol < 0 || sCol >= (armSize * 3) - 2;
  }

  /**
   * Generates the marble solitaire board with each slot represented by its (@code SlotState).
   *
   * @param armSize the size of an arm of the board
   * @param sRow    the vertical index of the starting empty slot
   * @param sCol    the horizontal index of the starting empty slot
   */
  protected void generateBoard(int armSize, int sRow, int sCol) {
    // calculate the length/width of the board
    int dimension = 3 * (armSize - 1) + 1;
    //create board of proper size, all slots are initialized to null
    this.board = new SlotState[dimension][dimension];

    for (int row = 0; row < dimension; row++) {
      for (int col = 0; col < dimension; col++) {
        // check to make marble cross
        if (((row > armSize - 2) && (row < (2 * armSize) - 1))
                || ((col > armSize - 2) && (col < (2 * armSize) - 1))) {
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

  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) throws IllegalArgumentException {
    // check if the move is valid
    this.checkValidMove(fromRow, fromCol, toRow, toCol);
    // move method will stop here if an exception is thrown (if it is not a valid move)

    // if it is a valid move...

    // make the old slot empty
    this.board[fromRow][fromCol] = SlotState.Empty;
    // make the middle slot empty
    this.board[(toRow + fromRow) / 2][(toCol + fromCol) / 2] = SlotState.Empty;
    // move the marble to the end slot
    this.board[toRow][toCol] = SlotState.Marble;
    this.score -= 1;
  }

  /**
   * Processes the given move, throws an appropriate error if the move is not valid.
   *
   * @param fromRow the row of the starting position
   * @param fromCol the column of the starting position
   * @param toRow   the row of the end position
   * @param toCol   the column of the end position
   * @throws IllegalArgumentException when the move is not valid
   */
  protected void checkValidMove(int fromRow, int fromCol, int toRow, int toCol)
          throws IllegalArgumentException {
    // check that they are 2 slots away
    // diagonals NOT permitted
    if (!((Math.abs(toRow - fromRow) == 2 && toCol == fromCol)
            || (Math.abs(toCol - fromCol) == 2 && toRow == fromRow))) {
      throw new IllegalArgumentException("Must move 2 slots");
    }
    // check rest
    this.checkValidMoveHelper(fromRow, fromCol, toRow, toCol);
  }

  /**
   * Processes the given move, throws an appropriate error if the move is not valid.
   *
   * @param fromRow the row of the starting position
   * @param fromCol the column of the starting position
   * @param toRow   the row of the end position
   * @param toCol   the column of the end position
   * @throws IllegalArgumentException when the move is not valid
   */
  protected void checkValidMoveHelper(int fromRow, int fromCol, int toRow, int toCol)
          throws IllegalArgumentException {
    // check valid from position (not out of bounds)
    if (fromRow < 0 || fromRow >= this.getBoardSize() || fromCol < 0
            || fromCol >= this.getBoardSize()) {
      throw new IllegalArgumentException("From position out of bounds");
    }
    // check valid to position (not out of bounds)
    if (toRow < 0 || toRow >= this.getBoardSize()
            || toCol < 0 || toCol >= this.getBoardSize()) {
      throw new IllegalArgumentException("To position out of bounds");
    }
    // check from is marble
    if (this.board[fromRow][fromCol] != SlotState.Marble) {
      throw new IllegalArgumentException("Must move from a marble");
    }
    // check to is empty
    if (this.board[toRow][toCol] != SlotState.Empty) {
      throw new IllegalArgumentException("Must move to an empty slot");
    }
    // check if the middle slot in a move is a marble
    if (this.board[(toRow + fromRow) / 2][(toCol + fromCol) / 2] != SlotState.Marble) {
      throw new IllegalArgumentException("Must move over a marble");
    }
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
      }
    }

    // if no move was found while looping through the board, then the game is over
    return true;
  }

  @Override
  public int getBoardSize() {
    // the number of rows/columns (dimension, not index)
    return this.board.length;
  }

  @Override
  public SlotState getSlotAt(int row, int col) throws IllegalArgumentException {
    // if the position is out of the bounds of the solitaire board grid
    if (row < 0 || row >= this.getBoardSize() || col < 0 || col >= this.getBoardSize()) {
      throw new IllegalArgumentException("Position out of bounds");
    } else {
      return this.board[row][col];
    }
  }

  @Override
  public int getScore() {
    // calculate as board is made/changed for efficiency
    return this.score;
  }
}
