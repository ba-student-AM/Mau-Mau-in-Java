/**
 * @author John Kühnel
 * @author Tobias Hering
 * @comment JavaFX Controller for the NewGame Scene
 * @documentation https://openjfx.io/
 */

package javafx;

import java.io.IOException;
import Cards.Game;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class NewGameController {

  @FXML
  private TextField textField_P1;
  @FXML
  private TextField textField_P2;
  @FXML
  private TextField textField_P3;
  @FXML
  private TextField textField_P4;
  @FXML
  private Button button_startGame;
  
  private int playerCount;
  private String[] playerNames;
  private int minNameLength = 2;
  private int maxNameLength = 10;

  @FXML
  public void submit() {
    playerNames = new String[playerCount];

    switch (playerCount) {
      case 4:
        playerNames[3] = textField_P4.getText();
      case 3:
        playerNames[2] = textField_P3.getText();
      case 2:
        playerNames[1] = textField_P2.getText();
        playerNames[0] = textField_P1.getText();
        break;
    }

    //check for duplicate names
    boolean duplicate = false;
    for (int j = 0; j < playerCount; j++) {
      for (int k = j + 1; k < playerCount; k++) {
        if (playerNames[j].equals(playerNames[k])) {
          duplicate = true;
        }
      }
    }

    // check for the name length
    boolean length = false;
    for (String string : playerNames) {
      if (string.length() < minNameLength || string.length() > maxNameLength) {
        length = true;
      }
    }

    if (!duplicate && !length) {
      button_startGame.setDisable(true);
      Game.addPlayers(playerNames);
      App.setRoot("gui");
    } else {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Falsche Eingaben");
      alert.setHeaderText("Bitte verwende eindeutige Spielernamen! (" + minNameLength + "-" + maxNameLength + " Zeichen)");
      alert.showAndWait();
    }
  }


  @FXML
  public void abort() {
    App.setRoot("gui");
  }

  // enable the next textfield when the previous one is filled
  @FXML
  public void keyPressed(KeyEvent event) {
    playerCount = 0;
    button_startGame.setDisable(true);
    if (!textField_P1.getText().isEmpty()) {
      enableTextField(textField_P2);
      playerCount = 1;
    } else {
      disableTextField(textField_P2);
    }

    if (!textField_P2.getText().isEmpty()) {
      enableTextField(textField_P3);
      playerCount = 2;
    } else {
      disableTextField(textField_P3);
    }

    if (!textField_P3.getText().isEmpty()) {
      enableTextField(textField_P4);
      playerCount = 3;
    } else {
      disableTextField(textField_P4);
    }

    if (!textField_P4.getText().isEmpty()) {
      playerCount = 4;
    }
  }

  private void enableTextField(TextField field) {
    field.setDisable(false);
    if (playerCount >= 1) {
      button_startGame.setDisable(false);
    }
  }

  private void disableTextField(TextField field) {
    field.setDisable(true);
    field.setText("");
  }
}