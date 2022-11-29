package controller;

/**
 * This interface represents the controller for the marble solitaire game.
 */
public interface MarbleSolitaireController {

  /**
   * Plays a new game of Marble Solitaire.
   * @throws IllegalStateException when the controller is unable to successfully read input or
   *     transmit output. Exceptions should be documented as per standard Javadoc requirements and
   *     formatting. The nature of input/output will be an implementation detail.
   */
  void playGame() throws IllegalStateException;

}
