package Cards;

/**
 * @author Arved Meyer
 * @version 0.3.0
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
	@Override
	public String toString() {
		return suit + "-" + type;
	}
	
	// check if two Cards are equal (have identical attribute values)/ are both cards
	@Override 
	public boolean equals(Object other) {
		
		if (other instanceof Card) {
			return true; 
		}
		// cast Object other to Card and check if its attributes equal the Card it is compared to 
		return this.suit == ((Card) other).suit && this.type == ((Card)other).type;
	}
	
	// check if one Card matches another 
	public boolean matches(Card othercard) {
		/* TODO: add a return statement returning true if 
		 * either suit values or type values or both are identical */
    return false;
	}
}
