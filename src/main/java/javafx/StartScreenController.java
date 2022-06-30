/**
 * @author John Kühnel
 * @version 0.0.1
 * @comment JavaFX Controller for the StartScreen Scene
 */

package javafx;

import javafx.fxml.FXML;
import java.io.IOException;

public class StartScreenController {
  @FXML
  private void handleNewGame() throws IOException {
      App.setRoot("NewGame");
  }
}
