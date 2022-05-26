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
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class MainController {
  @FXML
  public ImageView currentCard;
  @FXML
  public Text currentPlayer;
  @FXML
  public Text timerPlayTime;
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

	GameTimer playTime;


	// executed on scene loading
  public void initialize() throws FileNotFoundException {
    if (NewGameController.playerNames != null) { //TODO: check if game is running instead
      initializeGame();
      startTimer();
    }
  }

  // close the application
  @FXML
  private void handleExit() throws IOException {
    System.exit(0);
  }

  // (re)start the game
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
  }

	// set playtime in the gui
  @FXML
  public void setPlayTime(String time) {
    timerPlayTime.setText(time);
  }

  @FXML
  private void handleMenuTest() {
    System.out.println("test");
    currentCard.setImage(new Image("https://cataas.com/cat/says/hello%20world!"));
  }

  private void startTimer() {
		playTime = new GameTimer();
    playTime.start();
  }

  private void initializeGame () throws FileNotFoundException {
		Game game = new Game(NewGameController.playerNames);
		game.startGame(game);
		Card topCard_drawStack = game.getDeclaredCard();
		currentCard.setImage(new Image(new FileInputStream(topCard_drawStack.getImagePath())));
		currentPlayer.setText(game.getPlayers()[0].getName());
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
      System.out.println(playTime);
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
