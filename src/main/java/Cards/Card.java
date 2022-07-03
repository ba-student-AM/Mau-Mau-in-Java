package Cards;

import java.util.Objects;

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

	// getter method for our Blatt 
	public String getBlatt() {
		return blatt;
	}

	// setter-method for our Card-Blatt (for possible future expansion)
	public void setBlatt(String blatt) {
		this.blatt = blatt;
	}

	// return String-representation of the Card-Type
	@Override
	public String toString() {
		return suit + "-" + type;
	}
	public String toTransString() {
		return suit.getTranslation() + "-" + type.getTranslation();
	}

	// check if one Card matches another 
	public boolean matches(Card othercard) {
		return this.getSuit() == othercard.getSuit() || this.getType() == othercard.getType();
	}

	// Generate Hash-Code of the Card 
	@Override
	public int hashCode() {
		return Objects.hash(blatt, suit, type);
	}

	// check if two Cards are equal (have identical attribute values)/ are both cards
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Card)) {
			return false;
		}
		Card other = (Card) obj;
		return Objects.equals(blatt, other.blatt) && suit == other.suit && type == other.type;
	}

	// Method to get our image path 
	public String getImagePath() {
		return "src/main/resources/card_img/" + this.blatt + "/" + this.suit + "-" + this.type + ".png";
	}
}