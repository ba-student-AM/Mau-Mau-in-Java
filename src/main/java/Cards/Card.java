package Cards;

/**
 * @author Arved Meyer
 * @author Tobias Hering
 * @version 1.0.0
 * @comment Class to Model Cards of the german Skat-Deck
 */

public class Card {
	
	// Each card has a unique combination of Suit and Type 
	private final Suit suit;
	private final Type type;

	public String blatt = "standard_blatt";

	// Constructor for class Card 
	public Card(final Suit suit, final Type type) {
		this.suit = suit; 
		this.type = type; 
	}
	
	// getter-method for suit 
	public Suit getSuit() {
		return this.suit;
	}
	
	// getter-method for type
	public Type getType() {
		return this.type; 
	}

	public String getBlatt() {
		return blatt;
	}

	public void setBlatt(String blatt) {
		this.blatt = blatt;
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

		if (othercard instanceof Card) {
			return true; 
		}

		return this.suit == othercard.suit || this.type == othercard.type;
	}
	public String getImagePath() {
//		String image = "src/main/resources/card-img/" + this.toString() + ".png";
//		File file = new File(image);
		return "src/main/resources/card_img/" + blatt + "/" + suit + "-" + type + ".png";
	}
}
