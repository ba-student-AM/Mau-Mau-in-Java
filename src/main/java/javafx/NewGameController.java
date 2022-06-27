/**
 * @author John Kühnel
 * @author Tobias Hering
 * @comment JavaFX Controller for the NewGame Scene
 * @documentation https://openjfx.io/
 */
//TODO spielername müssen mindesanzahl an zeichen haben
//TODO einzigaritge spielernamen -> Fehlermeldung

package javafx;

import java.io.IOException;
import java.util.Optional;

import Cards.Game;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
  private int i;

  private String[] playerNames;

  @FXML
  public void submit() throws IOException { //TODO: check for valid inputs and duplicate names, maybe add min and max nameLength
    playerCount = 0;
    if (!textField_P1.getText().isEmpty()) {
      playerCount++;
    }
    if (!textField_P2.getText().isEmpty()) {
      playerCount++;
    }
    if (!textField_P3.getText().isEmpty()) {
      playerCount++;
    }
    if (!textField_P4.getText().isEmpty()) {
      playerCount++;
    }

    playerNames = new String[playerCount];

    i = 0;
    if (!textField_P1.getText().isEmpty()) {
      playerNames[i] = textField_P1.getText();
      i++;
    }
    if (!textField_P2.getText().isEmpty()) {
      playerNames[i] = textField_P2.getText();
      i++;
    }
    if (!textField_P3.getText().isEmpty()) {
      playerNames[i] = textField_P3.getText();
      i++;
    }
    if (!textField_P4.getText().isEmpty()) {
      playerNames[i] = textField_P4.getText();
      i++;
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
    if (!duplicate) {
      button_startGame.setDisable(true);
      Game.addPlayers(playerNames);
      App.setRoot("gui");
    }
    else{
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Fehler");
      alert.setHeaderText("Bitte verwende eindeutige Spielernamen für jeden Spieler");
      alert.getButtonTypes().setAll(new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE));
      Optional<ButtonType> result = alert.showAndWait();
    }
  }


  @FXML
  public void abort() throws IOException {
    App.setRoot("gui");
  }

  //enable button only when >= 2 textfields are filled
  @FXML
  public void keyPressed(KeyEvent event) { //TODO: change to event key-released
    playerCount = 0;
    button_startGame.setDisable(true);
    if (!textField_P1.getText().isEmpty()) {
      playerCount++;
    }
    if (!textField_P2.getText().isEmpty()) {
      playerCount++;
    }
    if (!textField_P3.getText().isEmpty()) {
      playerCount++;
    }
    if (!textField_P4.getText().isEmpty()) {
      playerCount++;
    }
    if (playerCount >= 2) {
      button_startGame.setDisable(false);
    }
  }

}