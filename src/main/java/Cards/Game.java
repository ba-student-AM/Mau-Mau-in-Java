package Cards;

import javafx.MainController;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author Arved Meyer
 * @version 0.3.1
 * @comment Class to Model the logic of a game of Mau-Mau
 */

public class Game {

	public final int NUM_INITIAL_CARDS = 5;

	private Player[] players;
	private String[] playerNames;
	public int numCurrentPlayer;
	private Player currentPlayer;

	private Card declaredCard; // Card that is on the top of drawStack during Gameplay
	private CardStack drawStack;
	private CardStack putStack;

	/*private Suit validSuit;
	 *private Type validType;*/

	@FXML
	private ImageView currentCard;

	// Constructor
	public Game(String[] pNames) {
		playerNames = pNames;
	}

	// Method to generate instances of class Player and add them to Players
	void addPlayers() {
		//For each element of playerNames, generate a new player-object and add it to players
		players = new Player[playerNames.length];
		for (int i = 0; i < playerNames.length; i++) {
			players[i] = new Player(playerNames[i]);
		}
	}

	// Method to create our main Stack of cards
	private void createDrawStack() {
		drawStack = CardStack.initDeck();
		drawStack.shuffle();
	}
	// Method to start our game;
	public void startGame(Game game) throws FileNotFoundException {
		createDrawStack();
		addPlayers();
		this.createPlayerHands();

		CardStack putStack = new CardStack(); 
		putStack.addCard(drawStack.getTopCard());
		declaredCard = getDeclaredCard();
		//initializeGUI();

	}
	public void initializeGUI() throws FileNotFoundException {
		// topCard = putStack.getCurrentTopCard();
		try {
			currentCard.setImage(new Image(new FileInputStream(declaredCard.getImagePath())));
		}catch (FileNotFoundException e){
			System.out.println("File not found");
		}
	}
	// Method to give out Cards to players
	public void createPlayerHands() {
		// iterate through players and add NUM_INITIAL_CARDS to their hand from drawStack
		for (int i = 0; i < players.length; i++) {
			for (int j = 0; j < NUM_INITIAL_CARDS; j++) {
				players[i].drawCardFromStack(drawStack);
			}
		}
		//print out the cards in the players hands
		for (int i = 0; i < players.length; i++) {
			System.out.println(players[i].getName() + " has " + players[i].getHand().size() + " cards.");
			System.out.println("drawStack has " + drawStack.size() + " cards.");
		}
		// Use both forEach and traditional for-loop
	}

	// Getter for PlayerNames
	public Player[] getPlayers() {
		return players;
	}

	// Getter for current declaredCard
	public Card getDeclaredCard() {
		return putStack.getTopCard();
	}

	// Method to get our winner
	private Player getWinningPlayer() {
		// TODO: Iterate through players and check if their hand is empty. If yes, return the Player; 
		return null;
	}

	// Method to end our game
	private void gameEnds() {
		// TODO: Get our winning player, ...
	}
}
