/**
 * @author Tobias Hering
 * @comment JavaFX-Controller for the main scene
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
import Cards.Suit;
import Cards.Type;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.CacheHint;
import javafx.scene.Cursor;
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

import static Cards.Type.UNTER;

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
  @FXML
  private HBox Hbox_Buttons;

	private GameTimer playTime;
  private Boolean covered = true;
  private Boolean allowAnyCard = false; // for debug purposes


	// executed on scene loading
  public void initialize() throws FileNotFoundException {
    try {
      // INIT GAME
      initializeGame();
      startTimer();
      // System.out.println(btnNextPlayer.getId());
    } catch (Exception e) {
      //TODO: handle exception
      System.out.println("Game is not initialized yet! Start a new game!");
      setGameStatus("Das Spiel wurde noch nicht initialisiert!");
    }
  }

  private void initializeGame () throws FileNotFoundException {
		Game.startGame();
		putStack.setImage(new Image(new FileInputStream(Game.getDeclaredCard().getImagePath())));
    createNextPlayerButton();
    setCurrentPlayerName();
    setGameStatus("Neues Spiel - " + Game.getCurrentPlayer().getName() + " beginnt!");
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
      updateSpacing();  }

  private void uncoverCards() throws FileNotFoundException {
    drawStack.setCursor(Cursor.HAND);
    btnNextPlayer.setDisable(true);
    handCards.getChildren().clear();
    updateSpacing();
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

            // check if card can be played
            if (!allowAnyCard && (clickedCard.getType() != Game.getDeclaredType() && clickedCard.getSuit() != Game.getDeclaredSuit())) {
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
            } else{
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
          imageView.setViewOrder(imageView.getViewOrder() - 2);
          event.consume();
        }
      });
      imageView.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          imageView.setScaleX(1);
          imageView.setScaleY(1);
          imageView.setViewOrder(imageView.getViewOrder() + 2);
          event.consume();
        }
      });

      handCards.getChildren().add(imageView);
    }

    covered = false;
  }

  private void createNextPlayerButton(){
    Hbox_Buttons.getChildren().clear();
    btnNextPlayer = new Button();
    btnNextPlayer.setId("btnNextPlayer");
    btnNextPlayer.setText("Karten aufdecken");
    btnNextPlayer.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        try {
          handleBtnNextPlayer();
          System.out.println("Button test");
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    });
    Hbox_Buttons.getChildren().add(btnNextPlayer);
  }

  private void createColorChangeButtons(){
    Hbox_Buttons.getChildren().clear();
    for (Suit suit : Suit.values()) {
      Button button = new Button();
      button.setText(suit.toString());
      button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          try {
            putStack.setImage(new Image(new FileInputStream("src/main/resources/card_img/standard_blatt/" + suit + "-UNTER.png"))); //TODO: imagepath of ober of suit
            coverCards();
          } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
          }
          setGameStatus(Game.getCurrentPlayer().getName() + " hat sich " + suit + " gewünscht!");
          System.out.println(suit);
          Game.setDeclaredSuit(suit);
          createNextPlayerButton();
        }
      });;
      Hbox_Buttons.getChildren().add(button);
    }
  }

  private void updateSpacing() {
    if(Game.getCurrentPlayer().getHand().size() > 5) {
               //handCard.width - imageView.width
      handCards.setSpacing((680 - 100 * Game.getCurrentPlayer().getHand().size()) / (Game.getCurrentPlayer().getHand().size() - 1));
    }
    else handCards.setSpacing(20);
  }

  @FXML
  private void drawCard() throws FileNotFoundException {
    // TODO: Fix the exception when the drawStack and the putStack are empty at the same time
    if(!covered) {
      Game.submitDraw();
      setGameStatus(Game.getCurrentPlayer().getName() + " hat eine Karte vom Stapel gezogen!");

      endTurn();
    }
  }

  private void playCard(Card card) throws FileNotFoundException {
    if(!covered) {
      Boolean endTurn = true;
      Game.playCard(card);
      setGameStatus(Game.getCurrentPlayer().getName() + " hat die Karte " + Game.getDeclaredCard().toString() + " gespielt.");

      if (!Game.isGameOver()) {
        switch (card.getType()) {
          // if card is of type SIEBEN, let our (new) currentPlayer draw two Cards
          case SIEBEN:
            appendGameStatus(" " + Game.getNextPlayer().getName() + " muss zwei Karten ziehen!");
            Game.draw2Cards();
            break;
    
          // TODO: - if card is of type ACHT, increment our currentPlayer again (the next Player is skipped)
          case ACHT:        
            if (Game.getPrevPlayer() == Game.getNextPlayer()) {
              appendGameStatus(" Da " + Game.getNextPlayer().getName() + " übersprungen wird, bist du nochmal dran!");
              endTurn = false;
            } else {
              appendGameStatus(" " + Game.getNextPlayer().getName() + " wird übersprungen!");
              Game.setCurrentPlayerNext();
            }
            break;
  
          // let the player choose a color
          // TODO: fix bugged UNTER (hand cards can be played in color picker)
          case UNTER:
            setGameStatus("Wähle deine gewünschte Farbe aus!");
            createColorChangeButtons();
            uncoverCards();
            break;
  
          // let the player place another card
          case ASS:
            setGameStatus("Du darfst eine weitere Karte spielen!");
            endTurn = false;
            break;
    
          /* TODO: For our UI-Team: implement functionality to tell the player whether his card's Suit or Type are invalid*/
          default:
            // do nothing, card has no special action
            break;
        }

        // check if the turn is over or another card can be played
        if (endTurn) {
          endTurn(card);
        } else {
          uncoverCards();
        }
      } else {
        endGame();
      }

      // update putStack Card
      putStack.setImage(new Image(new FileInputStream(card.getImagePath())));
    }
  }

  // end the turn and switch to the next player
  private void endTurn() throws FileNotFoundException {
    System.out.println("Next Player: " + Game.getNextPlayer().getName());
    Game.setCurrentPlayerNext();
    setCurrentPlayerName();
    coverCards();
  }
  private void endTurn(Card card) throws FileNotFoundException {
    Game.setCurrentPlayerNext();
    setCurrentPlayerName();
    System.out.println("Next Player: " + Game.getCurrentPlayer().getName());
    if(card.getType() != UNTER) {
      coverCards();
    }
    if(card.getType()== UNTER) {
      handCards.disableProperty().set(true);
    }
  }

  // end the game and show the winner, disallow further actions
  private void endGame() throws FileNotFoundException {
    setGameStatus(Game.getCurrentPlayer().getName() + " hat gewonnen!");
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
    String text = Game.getCurrentPlayer().getName();
    currentPlayer.setText(text);
  }

  @FXML
  public void setGameStatus(String text) {
    gameStatus.setText(text);
  }

  @FXML
  public void appendGameStatus(String text) {
    String statusBefore = gameStatus.getText();
    gameStatus.setText(statusBefore + text);
  }

  @FXML
  private void handleMenuTest() throws FileNotFoundException {
    //putStack.setImage(new Image("https://cataas.com/cat/says/hello%20world!"));
    Game.printStatus();
    // createColorChangeButtons();
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
