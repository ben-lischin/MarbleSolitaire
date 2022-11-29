package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import model.MarbleSolitaireModel;
import view.MarbleSolitaireView;

/**
 * Represents an implementation of the MarbleSolitaireController.
 */
public class MarbleSolitaireControllerImpl implements MarbleSolitaireController {
  MarbleSolitaireModel model;
  MarbleSolitaireView view;
  Readable in;

  /**
   * Creates a controller.
   *
   * @param model the MarbleSolitaireModel object
   * @param view  the MarbleSolitaireView object
   * @param in    the input stream
   * @throws IllegalArgumentException when any of the inputs are null
   */
  // Any input coming from the user will be received via the Readable object, and any output sent to
  // the user should be transmitted to the view, which in turn will transmit it to whichever
  // Appendable object was used to set it up.
  public MarbleSolitaireControllerImpl(MarbleSolitaireModel model, MarbleSolitaireView view,
                                       Readable in) throws IllegalArgumentException {
    if (model == null || view == null || in == null) {
      throw new IllegalArgumentException("must have non-null model, view, and input");
    } else {
      this.model = model;
      this.view = view;
      this.in = in;
    }
  }

  @Override
  public void playGame() throws IllegalStateException {
    Scanner scan = new Scanner(this.in);
    boolean quit = false;
    List<Integer> input = new ArrayList<Integer>();

    // display starting board and score
    try {
      this.renderBoardAndScore();
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }


    // keep playing while game not over and the user did not quit
    while (!this.model.isGameOver() && !quit) {
      // construct array list for inputs, quit if necessary, ignore other characters
      while (input.size() < 4 && !quit) {
        if (scan.hasNextInt()) {
          // if next is an int, add it to the working list of int move inputs
          input.add(scan.nextInt());
        } else if (scan.hasNext("q") || scan.hasNext("Q")) {
          // quit if "q" or "Q"
          quit = true; // logically break out of loop, not just a break/return escape
        } else {
          // ignore all other character inputs
          try {
            scan.next();
          } catch (NoSuchElementException e) {
            throw new IllegalStateException(e);
          }
        }
      }

      // if the game was not quit (no "q" || "Q" found in previous while loop)
      if (!quit) {
        // assign values from input array list (known to be valid ints)
        int fromRow = input.get(0) - 1;
        int fromCol = input.get(1) - 1;
        int toRow = input.get(2) - 1;
        int toCol = input.get(3) - 1;

        // make the move, or ask for new move if invalid move
        try {
          this.model.move(fromRow, fromCol, toRow, toCol);
          // display board and score
          try {
            if (!model.isGameOver()) {
              this.renderBoardAndScore();
            }
          } catch (IOException e) {
            throw new IllegalStateException(e);
          }
        } catch (IllegalArgumentException e) {
          try {
            // this.view.renderMessage("Invalid move. Play again. (" + e.getMessage() + ")\n");
            this.view.renderMessage("Invalid move. Play again.\n");
          } catch (IOException io) {
            throw new IllegalStateException(io);
          }
        }
      }

      // remove the move inputs from the working list after we have done the move
      input.clear();
    }

    // outside the play while loop.
    // 2 ways we could have gotten here: (1) game quit (2) game naturally ended (no moves,win,etc)
    if (quit) { // game quit
      try {
        this.view.renderMessage("Game quit!\nState of game when quit:\n");
        this.renderBoardAndScore();
      } catch (IOException e) {
        throw new IllegalStateException(e);
      }
    } else { // implied natural game ending by running out of moves
      try {
        this.view.renderMessage("Game over!\n");
        this.renderBoardAndScore();
      } catch (IOException e) {
        throw new IllegalStateException(e);
      }
    }
  }

  /**
   * Renders the board, and score on new lines.
   *
   * @throws IOException when any input/output error occurs
   */
  private void renderBoardAndScore() throws IOException {
    // render board
    this.view.renderBoard();
    // display score
    this.view.renderMessage("\nScore: " + this.model.getScore() + "\n");
  }
}
