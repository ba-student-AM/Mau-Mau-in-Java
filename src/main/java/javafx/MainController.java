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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.CacheHint;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class MainController {
  @FXML
  private ImageView putStack;
  @FXML
  private ImageView drawStack;
  @FXML
  private Label currentPlayer;
  @FXML
  private Label gameStatus;
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
      setGameStatus("Das Spiel wurde noch nicht initialisiert!");
    }
  }

  private void initializeGame () throws FileNotFoundException {
		Game.startGame();
		putStack.setImage(new Image(new FileInputStream(Game.getDeclaredCard().getImagePath())));

    setCurrentPlayerName();
    setGameStatus("Neues Spiel - " + Game.getCurrentPlayerName() + " beginnt!");
    coverCards();
  }

  private void coverCards() throws FileNotFoundException {
    drawStack.setCursor(Cursor.DEFAULT);
    btnNextPlayer.setDisable(false);
    handCards.getChildren().clear();
    for (int i = 0; i < Game.getCurrentPlayer().getHand().size(); i++) {
      ImageView imageView = new ImageView();
      imageView.setImage(new Image(new FileInputStream("src/main/resources/card_img/standard_blatt/CARD-BACK.png")));
      imageView.setPreserveRatio(true);
      imageView.setFitWidth(100);
      imageView.setCursor(Cursor.DEFAULT);
      handCards.getChildren().add(imageView);
      covered = true;
    }
      handCards.setSpacing((680 - 100* Game.getCurrentPlayer().getHand().size()) / (Game.getCurrentPlayer().getHand().size() - 1));
  }

  private void uncoverCards() throws FileNotFoundException {
    drawStack.setCursor(Cursor.HAND);
    btnNextPlayer.setDisable(true);
    handCards.getChildren().clear();
    for (int i = 0; i < Game.getCurrentPlayer().getHand().size(); i++) {
      ImageView imageView = new ImageView();
      imageView.setImage(new Image(new FileInputStream(Game.getCurrentPlayer().getHand().drawNthCard(i).getImagePath())));
      imageView.setPreserveRatio(true);
      imageView.setFitWidth(100);  //TODO: find good size for cards so it doesnt get eaten

      int finalI = i;
      imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          try {
            Card clickedCard = Game.getCurrentPlayer().getHand().drawNthCard(finalI);
            if (!clickedCard.matches(Game.getDeclaredCard())) {
              setGameStatus("Wähle eine passende Karte, oder ziehe eine neue Karte vom Stapel!");
              ColorAdjust darken = new ColorAdjust();

              Blend blend = new Blend(
                BlendMode.MULTIPLY,
                darken,
                new ColorInput(
                  0,
                  0,
                  imageView.getFitWidth(),
                  imageView.getFitWidth()/ imageView.getImage().getWidth() * imageView.getImage().getHeight(),
                  Color.RED
                )
              );
              Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, evt -> {
                  imageView.setEffect(blend);
                  imageView.setCache(true);
                  imageView.setCacheHint(CacheHint.SPEED);
                }),
                new KeyFrame(Duration.seconds(.1), evt -> {
                  imageView.setEffect(null);
                }),
                new KeyFrame(Duration.seconds(.2), evt -> {
                  imageView.setEffect(blend);
                }),
                new KeyFrame(Duration.seconds(.3), evt -> {
                  imageView.setEffect(null);
                })
              );
              timeline.play();
            }
            else{
              playCard(clickedCard);
            }
          } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
          }
          event.consume();
        }
      });
      imageView.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          imageView.setScaleX(1.3);
          imageView.setScaleY(1.3);
          imageView.setCursor(Cursor.HAND);
          event.consume();
        }
      });
      imageView.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          imageView.setScaleX(1);
          imageView.setScaleY(1);
          event.consume();
        }
      });

      handCards.getChildren().add(imageView);
    }

    covered = false;
  }

  @FXML
  private void drawCard() throws FileNotFoundException {

    // TODO: Fix the exception when the drawStack and the putStack are empty at the same time

    if(!covered) {
      Game.submitDraw();
      setGameStatus(Game.getCurrentPlayerName() + " hat eine Karte vom Stapel gezogen!");

      endTurn();
    }
  }

  private void playCard(Card card) throws FileNotFoundException {
    if(!covered) {
      if(Game.getDeclaredCard().matches(card)) {
        Game.submitCard(card);

        // update putStack Card
        putStack.setImage(new Image(new FileInputStream(card.getImagePath())));

        // check if a player wins
        if (!Game.getCurrentPlayer().getHand().isEmpty()) {
          setGameStatus(Game.getCurrentPlayerName() + " hat die Karte " + Game.getDeclaredCard().toString() + " gespielt.");
          endTurn();
        } else {
          endGame();
        }
      }
    }
  }

  private void endTurn() throws FileNotFoundException {
    Game.setCurrentPlayerNext();
    setCurrentPlayerName();
    System.out.println("Next Player: " + Game.getCurrentPlayer().getName());
    coverCards();
  }

  private void endGame() throws FileNotFoundException {
    setGameStatus(Game.getCurrentPlayerName() + " hat gewonnen!");
    // TODO: Tell the user that the game is over in the GUI
    coverCards();
    drawStack.disableProperty().set(true);
    btnNextPlayer.disableProperty().set(true);
    playTime.stop();
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
    // TODO: if running: ask if you want to start a new game / save the current game
		if (playTime != null) {
			playTime.pause();
		}
		App.setRoot("NewGame");
  }

  @FXML
  private void handleBtnNextPlayer() throws IOException {
    if (!covered) {
      coverCards();
    } else {    
      uncoverCards();
    }
  }

	// set Text in the GUI
  @FXML
  public void setPlayTime(String text) {
    timerPlayTime.setText(text);
  }

  @FXML
  public void setCurrentPlayerName() {
    String text = Game.getCurrentPlayerName();
    currentPlayer.setText(text);
  }

  @FXML
  public void setGameStatus(String text) {
    gameStatus.setText(text);
  }

  @FXML
  private void handleMenuTest() throws FileNotFoundException {
    //putStack.setImage(new Image("https://cataas.com/cat/says/hello%20world!"));
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
