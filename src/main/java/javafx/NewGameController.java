/**
 * @author John Kühnel
 * @comment JavaFX-Controller Example, will be removed in the future
 * @documentation https://openjfx.io/
 */
//TODO spielername müssen mindesanzahl an zeichen haben
//TODO einzigaritge spielernamen -> Fehlermeldung

package javafx;

import java.io.IOException;

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

    @FXML
    public void submit() {
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

        String[] players = new String[playerCount];
        i = 0;
        if (!textField_P1.getText().isEmpty()) {
            players[i] = textField_P1.getText();
            i++;
        }
        if (!textField_P2.getText().isEmpty()) {
            players[i] = textField_P2.getText();
            i++;
        }
        if (!textField_P3.getText().isEmpty()) {
            players[i] = textField_P3.getText();
            i++;
        }
        if (!textField_P4.getText().isEmpty()) {
            players[i] = textField_P4.getText();
            i++;
        }
        //print out all players
        for (String player : players) {
            System.out.println(player);
        }

        System.out.println(players.length);
        button_startGame.setDisable(true);
    }

    @FXML
    public void abort() throws IOException {
        App.setRoot("gui");
    }

    //enable button only when >= 2 textfields are filled
    @FXML
    public void keyPressed(KeyEvent event) {
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


