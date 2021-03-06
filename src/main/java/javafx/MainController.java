/**
 * @author Tobias Hering
 * @author John Kühnel
 * @version 0.1.0
 * @comment JavaFX-Controller for the main scene
 * @documentation https://openjfx.io/
 */

package javafx;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import Cards.Card;
import Cards.Game;
import Cards.Suit;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
  @FXML
  private VBox Vbox_gameScreen;

  private GameTimer playTime;
  private Boolean covered = true;


  // executed on scene loading
  public void initialize() {
    try {
      // INIT GAME
      initializeGame();
      startTimer();
    } catch (Exception e) {
      // Game not initialized
      System.out.println("ERROR: " + e);
      setGameStatus("FEHLER: " + e);
    }
  }

  // initialize game
  private void initializeGame() {
    Game.startGame();

    String image = Game.getDeclaredCard().getImagePath();
    try {
      putStack.setImage(new Image(getClass().getResource(image).toString()));
    } catch (Exception e) {
      System.out.println("ERROR: " + image + " not found!");
    }
    createNextPlayerButton();
    setCurrentPlayerName();
    setGameStatus("Neues Spiel - " + Game.getCurrentPlayer().getName() + " beginnt!");
    coverCards();
  }

  // check the count of the playerHand and display cards in ImageViews
  private void coverCards() {
    drawStack.setCursor(Cursor.DEFAULT);
    btnNextPlayer.setDisable(false);
    handCards.getChildren().clear();
    for (int i = 0; i < Game.getCurrentPlayer().getHand().size(); i++) {
      ImageView imageView = new ImageView();
      String image = Game.getImageDir() + "CARD-BACK.png";
      try {
        imageView.setImage(new Image(getClass().getResource(image).toString()));
      } catch (Exception e) {
        System.out.println("ERROR: " + image + " not found!");
      }
      imageView.setPreserveRatio(true);
      imageView.setFitWidth(100);
      imageView.setCursor(Cursor.DEFAULT);
      handCards.getChildren().add(imageView);
      covered = true;
    }
    updateSpacing();
  }

  // add click event to the player cards and check if the selected card can be played
  private void uncoverCards() {
    drawStack.setCursor(Cursor.HAND);
    btnNextPlayer.setDisable(true);
    handCards.getChildren().clear();
    updateSpacing();
    for (int i = 0; i < Game.getCurrentPlayer().getHand().size(); i++) {
      ImageView imageView = new ImageView();
      String image = Game.getCurrentPlayer().getHand().drawNthCard(i).getImagePath();
      try {
        imageView.setImage(new Image(getClass().getResource(image).toString()));
      } catch (Exception e) {
        System.out.println("ERROR: " + image + " not found!");
      }
      imageView.setPreserveRatio(true);
      imageView.setFitWidth(100); //TODO: find good size for cards so it doesnt get eaten

      int finalI = i;
      imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          Card clickedCard = Game.getCurrentPlayer().getHand().drawNthCard(finalI);

          // check if card can be played
          if (!Game.allowAnyCard && (clickedCard.getType() != Game.getDeclaredType() && clickedCard.getSuit() != Game.getDeclaredSuit())) {
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
          } else {
            playCard(clickedCard);
          }
          event.consume();
        }
      });

      // scale the hovered card to make the selection visible
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

      // reset the scale when not hovered
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

  // create the button to switch to the next player when turn is over
  private void createNextPlayerButton() {
    Hbox_Buttons.getChildren().clear();
    btnNextPlayer = new Button();
    btnNextPlayer.setId("btnNextPlayer");
    btnNextPlayer.setText("Karten aufdecken");
    btnNextPlayer.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        handleBtnNextPlayer();
      }
    });
    Hbox_Buttons.getChildren().add(btnNextPlayer);
  }

  // create a button to select a suit when UNTER is played
  private void createColorChangeButtons() {
    Hbox_Buttons.getChildren().clear();
    Hbox_Buttons.setSpacing(5);
    for (Suit suit : Suit.values()) {
      Button button = new Button();
      button.setText(suit.getTranslation());
      button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          String image = Game.getImageDir() + suit + "-UNTER.png";
          try {
            putStack.setImage(new Image(getClass().getResource(image).toString()));
          } catch (Exception e) {
            System.out.println("ERROR: " + image + " not found!");
          }
          coverCards();
          setGameStatus(Game.getCurrentPlayer().getName() + " hat sich " + suit.getTranslation() + " gewünscht!");
          Game.setDeclaredSuit(suit);
          handCards.setDisable(false);
          createNextPlayerButton();
        }
      });;
      Hbox_Buttons.getChildren().add(button);
    }
  }

  // create button for the winner to claim his "price" :)
  private void createWinnerButton() {
    String base = "https://cataas.com/cat/says/";
    String params = "Gl%C3%BCckwunsch%20" + Game.getCurrentPlayer().getName() + "!?size=35&color=white";
    String url = base + params;
    //check if url is reachable
    try {
      URL u = new URL(url);
      HttpURLConnection huc = (HttpURLConnection) u.openConnection();
      huc.setRequestMethod("GET");
      huc.connect();
      if (huc.getResponseCode() == 200) {
        Hbox_Buttons.getChildren().clear();
        Button button = new Button();
        button.setText("Preis abholen :)");
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            Vbox_gameScreen.getChildren().clear();
            Vbox_gameScreen.autosize();
            ImageView img = new ImageView(new Image(url));
            img.setPreserveRatio(true);
            img.setFitHeight(400);
            Vbox_gameScreen.setAlignment(Pos.CENTER);
            Vbox_gameScreen.getChildren().add(img);
          }
        });
        Hbox_Buttons.getChildren().add(button);
      }
    } catch (IOException e) {
      System.out.println("URL is not reachable");
    }

  }

  // update the spacing of the handCards when there are too much cards
  private void updateSpacing() {
    if (Game.getCurrentPlayer().getHand().size() > 5) {
      // handCard.width - imageView.width
      handCards.setSpacing((680 - 100 * Game.getCurrentPlayer().getHand().size()) / (Game.getCurrentPlayer().getHand().size() - 1));
    } else {
      handCards.setSpacing(20);
    }
  }

  // draw a card from the drawStack and add it to the current player's hand
  @FXML
  private void drawCard() {
    if (!covered) {
      Boolean drawn = Game.submitDraw();
      if (drawn == true) {
        setGameStatus(Game.getCurrentPlayer().getName() + " hat eine Karte vom Stapel gezogen!");
      } else {
        setGameStatus(Game.getCurrentPlayer().getName() + " konnte keine Karte ziehen, da der Stapel leer ist!");
      }
      endTurn();
    }
  }

  // check if the selected card is a special card and if so, handle it
  // check if the game is over
  private void playCard(Card card) {
    if (!covered) {
      Boolean endTurn = true;
      Game.playCard(card);
      setGameStatus(Game.getCurrentPlayer().getName() + " hat die Karte " + Game.getDeclaredCard().toTransString() + " gespielt.");

      if (!Game.isGameOver()) {
        switch (card.getType()) {
          // SIEBEN - let our (new) currentPlayer draw two Cards
          case SIEBEN:
            int drawn = Game.draw2Cards();
            switch (drawn) {
              case 2:
                appendGameStatus(" " + Game.getNextPlayer().getName() + " muss zwei Karten ziehen!");
                break;
              case 1:
                appendGameStatus(" Glück gehabt! " + Game.getNextPlayer().getName() + " muss nur eine Karte ziehen, da der Stapel leer ist!");
                break;
              case 0:
                appendGameStatus(" Glück gehabt! " + Game.getNextPlayer().getName() + " muss 2 Karten ziehen, aber der Stapel ist leer!");
                break;
            }
            break;

            // ACHT - increment our currentPlayer again (the next Player is skipped)
          case ACHT:
            if (Game.getPrevPlayer() == Game.getNextPlayer()) {
              appendGameStatus(" Da " + Game.getNextPlayer().getName() + " übersprungen wird, bist du nochmal dran!");
              endTurn = false;
            } else {
              appendGameStatus(" " + Game.getNextPlayer().getName() + " wird übersprungen!");
              Game.setCurrentPlayerNext();
            }
            break;

            // UNTER - let the player choose a color
          case UNTER:
            setGameStatus("Wähle deine gewünschte Farbe aus!");
            createColorChangeButtons();
            uncoverCards();
            break;

            // ASS - let the player place another card
          case ASS:
            setGameStatus("Du darfst eine weitere Karte spielen!");
            endTurn = false;
            break;

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

      // update putStack Card Image
      String image = card.getImagePath();
      try {
        putStack.setImage(new Image(getClass().getResource(image).toString()));
      } catch (Exception e) {
        System.out.println("ERROR: " + image + " not found!");
      }
    }
  }

  // end the turn and switch to the next player
  private void endTurn() {
    Game.setCurrentPlayerNext();
    setCurrentPlayerName();
    coverCards();
  }

  private void endTurn(Card card) {
    Game.setCurrentPlayerNext();
    setCurrentPlayerName();
    if (card.getType() != UNTER) {
      coverCards();
    }
    if (card.getType() == UNTER) {
      handCards.disableProperty().set(true);
    }
  }

  // end the game and show the winner, disallow further actions
  private void endGame() {
    setGameStatus(Game.getCurrentPlayer().getName() + " hat gewonnen!");
    coverCards();
    drawStack.disableProperty().set(true);
    btnNextPlayer.disableProperty().set(true);
    createWinnerButton();
  }

  private void startTimer() {
    playTime = new GameTimer();
    playTime.start();
  }

  // close the application
  @FXML
  private void handleExit() {
    System.exit(0);
  }

  // handle Buttons in the scene
  @FXML
  private void handleNewGame() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Neues Spiel");
    alert.setHeaderText("Willst du ein neues Spiel starten?");
    alert.setContentText("Das aktuelle Spiel wird beendet und ein neues Spiel gestartet.");
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
      try {
        App.setRoot("NewGame");
      } catch (Exception e) {
        System.out.println("ERROR: " + e);
      }
    }
  }

  @FXML
  private void handleBtnNextPlayer() {
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
  private void handleMenuTest() {
    Game.printStatus();
  }

  // Game timer
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

    // update the playTime - runs in the defined interval
    @Override
    public void run() {
      String playTime = toString();
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