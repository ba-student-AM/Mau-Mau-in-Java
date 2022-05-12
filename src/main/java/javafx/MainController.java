/**
 * @author Tobias Hering
 * @version 0.0.1
 * @comment JavaFX-Controllers for the main scene
 * 
 * @documentation https://openjfx.io/
 */

package javafx;

import java.io.IOException;
import javafx.fxml.FXML;

public class MainController {

  // (re)start the game
  @FXML private void buttonNewGame() throws IOException {
    // TODO: function to start a new game (in NewGame window?)
    // TODO: if running: ask if you want to start a new game / save the current game
    // needed: boolean to check if game is running
    App.setRoot("NewGame");
  }

  // close the application
  @FXML private void buttonExit() throws IOException {
    System.exit(0);
  }

  // show the about dialog
  @FXML private void buttonAbout() throws IOException {
    // TODO: make an about scene
    App.setRoot("About");
  }

  @FXML private void buttonNextPlayer() throws IOException {
    // TODO: function to switch to next player
  }
}
