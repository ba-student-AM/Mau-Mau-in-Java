/**
 * @author John Kühnel
 * @author Tobias Hering
 * @comment Class to model the game
 * @documentation https://openjfx.io/
 */
//TODO spielername müssen mindesanzahl an zeichen haben
//TODO einzigaritge spielernamen -> Fehlermeldung

package javafx;

import java.io.IOException;
import Cards.Game;
import javafx.fxml.FXML;
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

    button_startGame.setDisable(true);

    Game.addPlayers(playerNames);
    Game.getPlayerCount();

    // DEBUG
    System.out.println("PlayerCount (Controller: NewGame): " + Game.getPlayerCount());
    System.out.println("PlayerNames (Controller: NewGame): " + Game.getPlayerNames());
    for (String string: Game.getPlayerNames()) {
      System.out.println(string);
    }

    App.setRoot("gui");
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