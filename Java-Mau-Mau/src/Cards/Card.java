package Cards;

/**
 * @author Arved Meyer
 * @version 0.2.0
 * 
 * Class to Model Cards of the german Skat-Deck 
 * 
 */

public class Card {
	
	/* TODO: 
	 * - Finish TODO-Segments and Enum Suit
	 * - Increment  Version to 1.0.0
	 * - !!! When finished, remove this massive comment and push to Git-Hub
	 * */
	
	// Each card has a unique combination of Suit and Type 
	private final Suit suit; 
	private final Type type; 
	
	// Constructor for class Card 
	public Card(final Suit suit, final Type type) {
		// TODO: Create assignment of suit to suit here !!!
		this.type = type; 
	}
	
	// getter-method for suit 
	public Suit getSuit() {
		// TODO: Create return-statement for this getter-method 
	}
	
	// getter-method for type
	public Type getType() {
		return this.type; 
	}
	
	// return String-representation of the Card-Type
	public String toString() {
		return suit + "-" + type;
	}
}
