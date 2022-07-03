package Cards;

import java.io.FileNotFoundException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Arved Meyer
 * @author John Kühnel
 * @author Tobias Hering
 * @version 1.0.0
 * @comment Class to Model the logic of a game of Mau-Mau
 */

final public class Game {

	public final static int NUM_INITIAL_CARDS = 5;
	public final static Boolean allowAnyCard = false; // for debug purposes

	private static int currentPlayerIndex = 0;
	private static Player currentPlayer;
	private static Player[] players;

	private static Card declaredCard; // Card that is on the top of putStack during Gameplay
	private static Type declaredType;
	private static Suit declaredSuit;
	private static CardStack drawStack;
	private static CardStack putStack;


	// Method to generate instances of class Player and add them to Players
	public static void addPlayers(String[] playerNames) {
		//For each element of playerNames, generate a new player-object and add it to players
		players = new Player[playerNames.length];
		for (int i = 0; i < playerNames.length; i++) {
			players[i] = new Player(playerNames[i]);
		}
	}

	// Method to create our main Stack of cards
	private static void createDrawStack() {
		drawStack = CardStack.initDeck();
		drawStack.shuffle();
	}

	// Method to create our Stack of cards that are put on the table
	private static void createPutStack() {
		putStack = new CardStack();
	}

	// Method to start our game;
	public static void startGame() throws FileNotFoundException {
		createDrawStack();
		createPutStack();
		createPlayerHands();

		// initialize card stacks for game
		putStack.addCard(drawStack.getTopCard());
		drawStack.removeCardIndex(drawStack.getTopCardIndex());

		// initialize declared card variables
		declaredCard = getDeclaredCard();
		declaredType = declaredCard.getType();
		declaredSuit = declaredCard.getSuit();

		// Randomize the current player on start
		int randomPlayerIndex = ThreadLocalRandom.current().nextInt(0, getPlayerCount());
		setCurrentPlayerIndex(randomPlayerIndex);
		currentPlayer = players[currentPlayerIndex];
	}

	public static void printStatus() {
		System.out.println("putStack has " + putStack.size() + " cards.");
		System.out.println("drawStack has " + drawStack.size() + " cards.");
		System.out.println("declaredCard is " + declaredCard.toString());
		System.out.println("Previous player is " + getPrevPlayer().getName());
		System.out.println("Current player is " + getCurrentPlayer().getName());
		System.out.println("Current player is " + getNextPlayer().getName());
		for (int i = 0; i < getPlayerCount(); i++) {
			System.out.println("Player " + getPlayerName(players[i]) + " has " + players[i].getHand().size() + " cards.");
		}
	}

	// Method to give out Cards to players
	public static void createPlayerHands() {
		//TODO: sort cards? with insertionSort or binary? also sort cards when drawing a card?
		// iterate through players and add NUM_INITIAL_CARDS to their hand from drawStack
		for (int i = 0; i < players.length; i++) {
			for (int j = 0; j < NUM_INITIAL_CARDS; j++) {
				players[i].drawCardFromStack(drawStack);
			}
		}
	}

	// Getters and Setters for Players and their names
	public static Player[] getPlayers() {
		return players;
	}

	public static String getPlayerName(Player player) {
		return player.getName();
	}

	public static String[] getPlayerNames() {
		String[] playerNames = new String[players.length];
		int i = 0;
		for (Player player : players) {
			playerNames[i] = player.getName();
			i++;
		}
		return playerNames;
	}

	public static int getPlayerCount() {
		return players.length;
	}

	// Getters and Setters for currentPlayer 
	public static Player getCurrentPlayer() {
		return players[currentPlayerIndex];
	}

	public static void setCurrentPlayerIndex(int index) {
		currentPlayerIndex = index;
	}

	// Getters and setters for the next player
	public static Player getNextPlayer() {
		return players[getNextPlayerIndex()];
	}

	public static int getNextPlayerIndex() {
		if (currentPlayerIndex + 1 >= players.length) {
			return 0;
		} else {
			return currentPlayerIndex + 1;
		}
	}

	// Getters and setters for the previous player
	public static Player getPrevPlayer() {
		return players[getPrevPlayerIndex()];
	}

	public static int getPrevPlayerIndex() {
		if (currentPlayerIndex - 1 < 0) {
			return players.length - 1;
		} else {
			return currentPlayerIndex - 1;
		}
	}

	// Method to change the current player
	public static void setCurrentPlayerNext() {
		setCurrentPlayerIndex(getNextPlayerIndex());
	}

	public static void setCurrentPlayerPrev() {
		setCurrentPlayerIndex(getPrevPlayerIndex());
	}

	// Method to get our winner
	public static Player getWinningPlayer() {
		if (currentPlayer.getHand().isEmpty()) {
			return currentPlayer;
		}
		return null;
	}

	// Method to check if our game is over
	public static boolean isGameOver() {
		for (Player player: players) {
			if (player.hasEmptyHand()) {
				return true;
			}
		}
		return false;
	}

	// Getters and Setters for the declaredCard
	public static Card getDeclaredCard() {
		return putStack.getTopCard();
	}

	public static void setDeclaredCard(Card card) {
		declaredCard = card;
	}

	public static Suit getDeclaredSuit() {
		return declaredSuit;
	}

	public static void setDeclaredSuit(Suit suit) {
		declaredSuit = suit;
	}

	public static Type getDeclaredType() {
		return Game.declaredType;
	}

	// Method for when a player draws another Card 
	public static Boolean submitDraw() {
		if (drawStack.isEmpty()) {
			putStackToDrawStack();
		}
		return getCurrentPlayer().drawCardFromStack(drawStack);
	}

	// move the putStack to the drawStack and leave the topCard on the putStack
	public static void putStackToDrawStack() {
		Card topCard = putStack.getTopCard();
		putStack.moveAllCards(drawStack);
		putStack.addCard(topCard);
		drawStack.removeCard(topCard);

		drawStack.shuffle();
	}

	public static void playCard(Card card) {
		getCurrentPlayer().putCardOnStack(putStack, card);
		declaredCard = card;
		declaredSuit = card.getSuit();
		declaredType = card.getType();
	}

	// Method to get out current Players chosen card
	public Card getPlayerChoice(int choice) {
		return getCurrentPlayer().getPlayerCard(choice);
	}

	// next player draws two cards
	// return the count of cards drawn
	public static int draw2Cards() {
		if (drawStack.isEmpty()) {
			putStackToDrawStack();
		}
		Player nextPlayer = getNextPlayer();
		for (int i = 0; i < 2; i++) {
			Boolean drawn = nextPlayer.drawCardFromStack(drawStack);
			if (!drawn) {
				return i;
			}
		}
		return 2;
	}
}

/* Rules of the game: 
 * - declaredCard has TYPE "SIEBEN" ? Player must draw two cards from drawStack - next Player can just add a card with the same SUIT 
 * 
 * - declaredCard has TYPE "ASS"    ? Player can play another card
 * - declaredCard has TYPE "ACHT"   ? Next Player is skipped - we go from player 1 to Player 3, for two players, the effect is practically the same as an "ASS"
 * - declaredCard has TYPE "BUBE"   ? Current player gets a Pop-Up to select which SUIT he wishes for - his selection overwrites the current declaredSUIT 
 * */