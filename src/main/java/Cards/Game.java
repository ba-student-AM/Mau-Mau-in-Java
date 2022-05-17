package Cards;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Arved Meyer
 * @version 0.2.0
 * @comment Class to Model the logic of a game of Mau-Mau 
 */

public class Game {
	
	private List<Player> players; 
	private Player currentPlayer; 
	private CardStack mainStack; 
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

}
