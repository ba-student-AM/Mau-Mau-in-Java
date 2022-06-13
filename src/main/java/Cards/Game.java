package Cards;

import java.io.FileNotFoundException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Arved Meyer
 * @author John Kühnel
 * @author Tobias Hering
 * @version 0.4.0
 * @comment Class to Model the logic of a game of Mau-Mau
 */

final public class Game {

	public final static int NUM_INITIAL_CARDS = 5;

	private static int currentPlayerIndex = 0;
	private static Player currentPlayer;
	private static Player[] players;

	private static Card declaredCard; // Card that is on the top of putStack during Gameplay
	private static Type declaredType;
	private static Suit declaredSuit; 
	private static CardStack drawStack;
	private static CardStack putStack;

	private static boolean isRunning;
	

	// Getter and setter for game state
	public static boolean isRunning() {
		return isRunning;
	}

	public static void	isRunning(Boolean state) {
		isRunning = state;
	}

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

		putStack.addCard(drawStack.getTopCard());
		declaredCard = getDeclaredCard();
		drawStack.removeCardIndex(drawStack.getTopCardIndex());
		declaredSuit = declaredCard.getSuit();

		// Randomize the current player on start
		int randomPlayerIndex = ThreadLocalRandom.current().nextInt(0, getPlayerCount());
		setCurrentPlayerIndex(randomPlayerIndex);
		
		// TODO: create the round structure;
		// TODO: BUT: not in while --> MainController! otherwise: no javafx interaction!
		// while (getWinningPlayer() == null) {
		// } 
		
		// TODO: end the game --> see above (MainController)
		// endGame();
		
		System.out.println("putStack topCard is " + declaredCard);
		System.out.println("putStack has " + putStack.size() + " cards.");
		System.out.println("drawStack topCard is " + drawStack.getTopCard());
		System.out.println("drawStack has " + drawStack.size() + " cards.");
	}
	
	// Method to give out Cards to players
	public static void createPlayerHands() { //TODO: sort cards? with insertionSort or binary? also sort cards when drawing a card?
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
	}

	// Getters and Setters for Players and their names
	// TODO: --> remove playerNames array and replace with Object references (redundant)
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

	public static String getCurrentPlayerName() {
		return getCurrentPlayer().getName();
	}

	public static int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}

	public static void setCurrentPlayerIndex(int index) {
		currentPlayerIndex = index;
	}

	// Getter for current declaredCard
	public static Card getDeclaredCard() {
		return putStack.getTopCard();
	}

	// Method to change the current player
	public static void setCurrentPlayerNext() {
		currentPlayerIndex++;
		if (currentPlayerIndex >= players.length) {
			setCurrentPlayerIndex(0);
		}
	}

	public static void setCurrentPlayerPrev() {
		currentPlayerIndex--;
		if (currentPlayerIndex < 0) {
			setCurrentPlayerIndex(players.length - 1);
		}
	}

	// Method to get our winner
	public static Player getWinningPlayer() {
		
		if (currentPlayer.getHand().isEmpty()) {
			return currentPlayer;
		}
		return null;
	}

	// Method to check if our game is over 
	public boolean isGameOver() {
		for (Player player: players) {
			if (player.hasEmptyHand()) {
				return true;
			}
		}
		return false;
	}
	
	// Method to get declaredCard 
	public static Suit getDeclaredSuit() {
		return declaredSuit; 
	}
	
	// Method for when a player draws another Card 
	public static void submitDraw() {
		
		if (drawStack.isEmpty()) {
			putStack.moveAllCards(drawStack);
			drawStack.shuffle();
			putStack.addCard(drawStack.getTopCard());
			declaredCard = getDeclaredCard();
		}
		currentPlayer.drawCardFromStack(drawStack); 
		
		// Important: change the current Player
		currentPlayerIndex = currentPlayerIndex +1 % players.length;
		currentPlayer = players[currentPlayerIndex];
	}
	
	/* Method to get out current Players chosen card 
	 * !!! This is a three levels deep-Method call for what is essentially the same functionality - Should be refactored later!!! */
	public Card getPlayerChoice (int choice) {
		
		return getCurrentPlayer().getPlayerCard(choice);
	}
	
	// Method to get declaredCardType
	public Type getDeclaredType(){
		return Game.declaredType;
	}
	
	// Method to submit our chosen card 
	public void submitCard (Card card, Suit selectedSuit) {
		
		if (!card.matches(declaredCard)) {
			if (card.getType() == Type.UNTER) {
				
				declaredSuit = card.getSuit();
				declaredType = card.getType();
			}
			
			System.out.println("Ungültiger Spielzug, entweder gleiche Farbe" + declaredSuit + "oder gleicher Typ" + declaredType);
			/* TODO: For our UI-Team: implement functionality to tell the player whether his card's Suit or Type are invalid*/
		}
		
		/* TODO: remove card from the currentPlayer's hand */
		
		if (currentPlayer.hasEmptyHand()) {
			/* TODO: For our UI-Team: implement functionality top tell our player that he has won the game !!! */
		}
		
		/* TODO: For our UI-Team: Implement functionality for our player to select and set a new selectedSuit if his Card is of type UNTER in the GUI departement*/
		
		/* TODO: - from card, set our new declaredType and declaredSuit, as well as our declared Card
		 *       - add card to our putStack */
		
		/* TODO: - increment our current Player like in submitDraw (just copy it) */
		
		// TODO: - if card is of type SIEBEN, let our (new) currentPlayer draw two Cards 
		if(card.getType()==Type.SIEBEN){
			currentPlayerIndex=currentPlayerIndex +1 % players.length;
			submitDraw();
			submitDraw();
		}
		 
		 /* TODO: - if card is of type ACHT, increment our currentPlayer again (the next Player is skipped) */
		 if(card.getType()==Type.ACHT){
			 currentPlayerIndex = currentPlayerIndex + 2 % players.length;
		 }
		
		if (card.getType() == Type.ASS) {
			
			currentPlayerIndex = currentPlayerIndex - 1 % players.length; 
			
			if (currentPlayerIndex == -1) {
				 currentPlayerIndex = players.length -1;
			}
			
			currentPlayer = players[currentPlayerIndex]; 
		}
		
		// Our selectedSuit has been selected by the player 
		if (card.getType() == Type.UNTER) {
			declaredSuit = selectedSuit; 
		}
	
	}
	
	
}

/* Rules of the game: 
 * - declaredCard has TYPE "SIEBEN" ? Player must draw two cards from drawStack - next Player can just add a card with the same SUIT 
 * 
 * - declaredCard has TYPE "ASS"    ? Player can play another card
 * - declaredCard has TYPE "ACHT"   ? Next Player is skipped - we go from player 1 to Player 3, for two players, the effect is practically the same as an "ASS"
 * - declaredCard has TYPE "BUBE"   ? Current player gets a Pop-Up to select which SUIT he wishes for - his selection overwrites the current declaredSUIT 
 * */
