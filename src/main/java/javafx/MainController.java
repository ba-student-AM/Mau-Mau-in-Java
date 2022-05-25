/**
 * @author Tobias Hering
 * @version 0.0.1
 * @comment JavaFX-Controllers for the main scene
 * 
 * @documentation https://openjfx.io/
 */

package javafx;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

import Cards.Card;
import Cards.CardStack;
import Cards.Game;
import Cards.Player;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainController {
  @FXML
  private ImageView currentCard;
  @FXML
  private ImageView handCard_0;
  @FXML
  private ImageView handCard_1;
  @FXML
  private ImageView handCard_2;
  @FXML
  private ImageView handCard_3;
  @FXML
  private ImageView handCard_4;
  @FXML
  private ImageView handCard_5;

  // (re)start the game
  @FXML private void handleNewGame() throws IOException {
    // TODO: function to start a new game (in NewGame window?)
    // TODO: if running: ask if you want to start a new game / save the current game
    // needed: boolean to check if game is running
    App.setRoot("NewGame");
  }

  // close the application
  @FXML private void handleExit() throws IOException {
    System.exit(0);
  }

  // show the about dialog
  @FXML private void handleAbout() throws IOException {
    // TODO: make an about scene
    App.setRoot("About");
  }

  @FXML private void handleBtnNextPlayer() throws IOException {
    // TODO: function to switch to next player
  }

  @FXML private void handleMenuTest() throws IOException {
//    System.out.println("test");
  }
  public void initializeGUI(Game game) throws FileNotFoundException {
    Card topCard = game.getDeclaredCard();
    currentCard.setImage(new Image(new FileInputStream(topCard.getImagePath())));
  }
}
