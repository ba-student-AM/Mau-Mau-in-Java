package Cards;

/**
 * @author Arved Meyer
 * @version 0.1.0
 * 
 * Class to model a player in the game 
 * 
 */

public class Player {
	
	private String playerName; 
	private CardStack hand; 
	
	private Card matchingCard; 
	
	/* TODO: - Create getter-Methods for playerName and hand
	 *       - Create a constructor for Player that instantiates an empty hand */

	// Method to pick a Card from the players Hand
	public Card pickCard(Card card) {
		// TODO: remove the selected card from the players hand and return it 
	}
	
	// Method to draw a Card from a specified stack 
	public Card drawCardFrom(CardStack stack) {
		// TODO: draw a Card from stack and add it to our hand, as well as return it 
		// * might be refactored to void later *
	}
	
	// Method to check if a player has a matching card to the uppermostCard in his hand
	public boolean hasMatchingCardTo(Card uppermostCard) {
		/* TODO: check if a player has a matching card to the uppermostCard in his hand, 
		 * if yes, assign this Card to the matchingCard of this object and return whether
		 * or not matchingCard is not equal to null*/
	}
	
}
