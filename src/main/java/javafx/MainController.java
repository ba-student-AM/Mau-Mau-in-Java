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
  private ImageView currentCard;
  @FXML
  private Text currentPlayer;
  @FXML
  private Text timerPlayTime;
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

	private GameTimer playTime;


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
		setCurrentPlayer();
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
  private void handleMenuTest() {
    System.out.println("test");
    currentCard.setImage(new Image("https://cataas.com/cat/says/hello%20world!"));
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
