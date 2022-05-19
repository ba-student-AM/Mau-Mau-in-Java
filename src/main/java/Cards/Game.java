package Cards;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Arved Meyer
 * @version 0.2.0
 * @comment Class to Model the logic of a game of Mau-Mau 
 */

public class Game {
	
	public final int NUM_INITIAL_CARDS = 5; 
	
	private List<Player> players; 
	private Player currentPlayer; 
	private CardStack mainStack;
	
	private Suit validSuit;
	private Type validType; 
	
	// Constructor
	Game () {
		players = new ArrayList<>();
	}
	
	// Method to generate instances of class Player and add them to Players 
	void addPlayers(String[] playerNames) {
		// TODO: For each element of playerNames, generate a new player-object and add it to players
	}
	
	// Method to create our main Stack of cards 
	private void createMainStack() {
		// TODO: Create our mainStack via initDeck and then shuffle it 
	}
	// Method to start our game; 
	public void startGame(Game game) {
		createMainStack();
		this.createPlayerHands(); 
		
	}

	// Method to give out Cards to players 
	public void createPlayerHands() {
		// TODO: iterate through players and add NUM_INITIAL_CARDS to their hand from mainStack 
		// Use both forEach and traditional for-loop 
	}
}
