/**
 * @author John Kühnel
 * @version 0.0.1
 * @comment JavaFX Controller for the StartScreen Scene
 */

package javafx;

import javafx.fxml.FXML;

public class StartScreenController {
  @FXML
  private void handleNewGame() {
    App.setRoot("NewGame");
  }
}