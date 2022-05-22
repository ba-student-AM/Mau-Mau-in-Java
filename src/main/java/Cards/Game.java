package Cards;

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
	
	private Card topCard; // Card that is on the top of drawStack during Gameplay 
	private CardStack drawStack;
	private CardStack putStack; 
	
	private Suit validSuit;
	private Type validType; 
	
	// Constructor
	Game (String[] pNames) {
		playerNames = pNames; 
	}
	
	// Method to generate instances of class Player and add them to Players 
	void addPlayers() {
		// TODO: For each element of playerNames, generate a new player-object and add it to players
	}
	
	// Method to create our main Stack of cards 
	private void createDrawStack() {
		// TODO: Create our mainStack via initDeck and then shuffle it 
	}
	// Method to start our game; 
	public void startGame(Game game) {
		createDrawStack();
		this.createPlayerHands(); 
		
	}

	// Method to give out Cards to players 
	public void createPlayerHands() {
		// TODO: iterate through players and add NUM_INITIAL_CARDS to their hand from drawStack 
		// Use both forEach and traditional for-loop 
	}
	
	// Getter for PlayerNames 
	public Player[] getPlayers() {
		return players;
	}
	
	// Getter for current topCard
	public Card getTopCard() {
		return topCard; 
	}
	
	// Method to get our winner 
	private Player getWinningPlayer() {
		// TODO: Iterate through players and check if their hand is empty. If yes, return them. 
	}
	
	// Method to end our game 
	private void gameEnds() {
		// TODO: Get our winning player, ... 
	}
}
