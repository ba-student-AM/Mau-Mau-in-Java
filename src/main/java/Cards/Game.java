package Cards;

import java.io.FileNotFoundException;

/**
 * @author Arved Meyer
 * @author John Kühnel
 * @author Tobias Hering
 * @version 0.3.3
 * @comment Class to Model the logic of a game of Mau-Mau
 */

final public class Game {

	public final static int NUM_INITIAL_CARDS = 5;

	private static int currentPlayerIndex;
	private static Player currentPlayer;
	private static Player[] players;
	private static String[] playerNames;

	private static Card declaredCard; // Card that is on the top of putStack during Gameplay
	private static Suit declaredSuit; 
	private static CardStack drawStack;
	private static CardStack putStack;
	

	// Constructor
	public Game() {

	}

	// Method to generate instances of class Player and add them to Players
	static void addPlayers() {
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

		addPlayers();
		createPlayerHands();

		putStack.addCard(drawStack.getTopCard());
		declaredCard = getDeclaredCard();
		drawStack.removeCardIndex(drawStack.getTopCardIndex());
		declaredSuit = declaredCard.getSuit();

		// TODO: Randomize who starts the game
		currentPlayerIndex = 1; 

		setCurrentPlayerName(currentPlayerIndex);
		
		// while (getWinningPlayer() == null) {
			
		// } 
		
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
	public static Player[] getPlayers() {
		return players;
	}

	public static String[] getPlayerNames() {
		return playerNames;
	}

	public static void setPlayerNames(String[] name) {
		playerNames = name;
	}

	public static int getPlayerCount() {
		return playerNames.length;
	}
	
	// Getter for currentPlayer 
	public Player getCurrentPlayer() {
		return players[currentPlayerIndex];
	}

	public String getCurrentPlayerName() {
		return playerNames[currentPlayerIndex];
	}

	public static void setCurrentPlayer(Player player) {
		currentPlayer = player;
	}

	public static void setCurrentPlayerName(int index) {
		currentPlayerIndex = index;
	}

	// Getter for current declaredCard
	public static Card getDeclaredCard() {
		return putStack.getTopCard();
	}

	// Method to get our winner
	private Player getWinningPlayer() {
		// TODO: Iterate through players and check if their hand is empty. If yes, return the Player;
		return null;
	}

	// Method to end our game
	private void endGame() {
		// TODO: Get our winning player, ...
	}
	
	// Method to get declaredCard 
	public Suit getDeclaredSuit() {
		return this.declaredSuit; 
	}
	
	// Method for when a player draws another Card 
	public void submitDraw() {
		
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
