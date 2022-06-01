package Cards;

import java.io.FileNotFoundException;

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

		// TODO: Randomize who starts the game random(0...player.length)
		setCurrentPlayerIndex(currentPlayerIndex);
		
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
		return players[currentPlayerIndex].getName();
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
		
		// Important: changes the current Player
		currentPlayer = players[currentPlayerIndex + 1 % players.length];
		currentPlayerIndex = currentPlayerIndex +1 % players.length;  
	}
	
	/* Method to get out current Players chosen card 
	 * !!! This is a three levels deep-Method call for what is essentially the same functionality - Should be refactored later!!! */
	public Card getPlayerChoice (int choice) {
		
		return getCurrentPlayer().getPlayerCard(choice);
	}
	
	/* Method to get declaredCardType
	public Type getDeclaredType(){
		return this.declaredType;
	}
	
	Method for when DeclaredType=="SIEBEN"
		for(int i=2;declaredType==7;i--;){
			currentPlayer.drawCardFromStack(drawStack);
		}
	*/
}

/* Rules of the game: 
 * - declaredCard has TYPE "SIEBEN" ? Player must draw two cards from drawStack - next Player can just add a card with the same SUIT 
 * 
 * - declaredCard has TYPE "ASS"    ? Player can play another card
 * - declaredCard has TYPE "ACHT"   ? Next Player is skipped - we go from player 1 to Player 3, for two players, the effect is practically the same as an "ASS"
 * - declaredCard has TYPE "BUBE"   ? Current player gets a Pop-Up to select which SUIT he wishes for - his selection overwrites the current declaredSUIT 
 * */
