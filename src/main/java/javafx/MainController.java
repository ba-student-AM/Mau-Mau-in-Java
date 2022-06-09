/**
 * @author Tobias Hering
 * @version 0.0.2
 * @comment JavaFX-Controllers for the main scene
 * @documentation https://openjfx.io/
 */

package javafx;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import Cards.Card;
import Cards.Game;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class MainController {
  @FXML
  private ImageView currentCard;
  @FXML
  private Text currentPlayer;
  @FXML
  private Text timerPlayTime;
  @FXML
  private Button btnNextPlayer;
  @FXML
  private HBox handCards;

	private GameTimer playTime;
  private Boolean covered = true;


	// executed on scene loading
  public void initialize() throws FileNotFoundException {
    try {
      // DEBUG
      System.out.println("PlayerCount (Controller: Main): " + Game.getPlayerCount());
      System.out.println("PlayerNames (Controller: Main): " + Game.getPlayerNames());
      for (String string : Game.getPlayerNames()) {
        System.out.println(string);
      }

      // INIT
      initializeGame();
      startTimer();
    } catch (Exception e) {
      //TODO: handle exception
      System.out.println("Game is not initialized yet! Start a new game!");
    }
  }

  private void initializeGame () throws FileNotFoundException {
		Game.startGame();
		Card topCard_drawStack = Game.getDeclaredCard();
		currentCard.setImage(new Image(new FileInputStream(topCard_drawStack.getImagePath())));
		currentPlayer.setText(Game.getPlayers()[0].getName());
    coverCards();
  }

  private void coverCards() throws FileNotFoundException {
    btnNextPlayer.setDisable(false);
    handCards.getChildren().clear();
    for (int i = 0; i < Game.getCurrentPlayer().getHand().size(); i++) {
      ImageView imageView = new ImageView();
      imageView.setImage(new Image(new FileInputStream("src/main/resources/card_img/standard_blatt/CARD-BACK.png")));
      imageView.setPreserveRatio(true);
      imageView.setFitWidth(100);
      handCards.getChildren().add(imageView);
      covered = true;
      //TODO: disable playCard() while covered
    }
    //handCards.getChildren().add(new ImageView().setImage(Image.(new FileInputStream("src/main/resources/card_img/standard_blatt/CARD-BACK.png")));
  }

  private void uncoverCards() throws FileNotFoundException {
    btnNextPlayer.setDisable(true);
    handCards.getChildren().clear();
    for (int i = 0; i < Game.getCurrentPlayer().getHand().size(); i++) {
      ImageView imageView = new ImageView();
      imageView.setImage(new Image(new FileInputStream(Game.getCurrentPlayer().getHand().drawNthCard(i).getImagePath())));
      imageView.setPreserveRatio(true);
      imageView.setFitWidth(100);
      int finalI = i;
      imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          try {
            playCard(Game.getCurrentPlayer().getHand().drawNthCard(finalI));
          } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
          }
          event.consume();
        }
      });
      handCards.getChildren().add(imageView);
    }
    covered = false;
    //TODO: enable playCard() while uncovered
  }
  @FXML
  private void drawCard() throws FileNotFoundException {
    if(!covered) {
      Game.submitDraw();
      System.out.println("draw");
      uncoverCards();
      coverCards();
    }
  }
  private void playCard(Card card) throws FileNotFoundException {
    if(!covered) {
      if (Game.getDeclaredCard().getSuit() == card.getSuit() || Game.getDeclaredCard().getType() == card.getType()) {  //TODO: does not work with Card.machtes(Card)
        System.out.println("Card matches!");
        Game.submitCard(card);
        uncoverCards();
      } else {
        System.out.println("Card does not match");
      }
    }
  }

  private void startTimer() {
		playTime = new GameTimer();
    playTime.start();
  }

  // close the application
  @FXML
  private void handleExit() throws IOException {
    System.exit(0);
  }

  // handle Buttons in the scene
  @FXML
  private void handleNewGame() throws IOException {
    // TODO: function to start a new game (in NewGame window?)
    // TODO: if running: ask if you want to start a new game / save the current game
    // needed: boolean to check if game is running
		if (playTime != null) {
			playTime.pause();
		}
		App.setRoot("NewGame");
  }

  @FXML
  private void handleBtnNextPlayer() throws IOException {
    // TODO: function to switch to next player
    uncoverCards();
  }

	// set Text in the GUI
  @FXML
  public void setPlayTime(String text) {
    timerPlayTime.setText(text);
  }

  @FXML
  public void setCurrentPlayer() {
    String text = Game.getCurrentPlayerName();
    currentPlayer.setText(text);
  }

  @FXML
  private void handleMenuTest() throws FileNotFoundException {
    //currentCard.setImage(new Image("https://cataas.com/cat/says/hello%20world!"));
    Game.printStatus();
  }


  private class GameTimer extends TimerTask {

		private byte seconds = 0;
		private byte minutes = 0;
		private int hours = 0;

    private TimerTask task;
    private Timer timer;
    private boolean isRunning = false;

    // start the timer
    private void start() {
      if (!isRunning) {
        timer = new Timer();
        task = new GameTimer();
        timer.scheduleAtFixedRate(task, 0, 1000);
        isRunning = true;
      }
    }

    // restart the timer
    private void restart() {
      stop();
      start();
    }

    // pause the timer
    private void pause() {
			// TODO: properly pause the timer
      clear();
    }

    // stop the timer
    private void stop() {
      clear();
      seconds = 0;
      minutes = 0;
      hours = 0;
    }

    // clear an existing timer
    private void clear() {
      if (isRunning) {
        timer.cancel();
        timer.purge();
        isRunning = false;
      }
    }

    // update the playTime - runs in the defined interval
    @Override
    public void run() {
      String playTime = toString();
      // System.out.println(playTime);
      setPlayTime(playTime);

      seconds++;
      if (seconds == 60) {
        seconds = 0;
        minutes++;
        if (minutes == 60) {
          minutes = 0;
          hours++;
        }
      }
    }

    public String toString() {
      String pattern = "%02d:%02d:%02d";
      return String.format(pattern, hours, minutes, seconds);
    }
  }
}
