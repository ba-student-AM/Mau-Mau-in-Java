/**
 * @author John Kühnel
 * @version 0.0.1
 * @comment JavaFX Controller for the StartScreen Scene
 */

package javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.util.Optional;

public class StartScreenController {
  @FXML
  private void handleNewGame() throws IOException {
      App.setRoot("NewGame");
  }
}
