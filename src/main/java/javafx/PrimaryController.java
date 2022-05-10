/**
 * @author Tobias Hering
 * @comment JavaFX-Controller Example, will be removed in the future
 * 
 * @documentation https://openjfx.io/
 */

package javafx;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
