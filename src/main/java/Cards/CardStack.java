package Cards;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Arved Meyer
 * @version 0.2.0
 * 
 * Class to Model a STACK of CARDS 
 */

public class CardStack {
	
	// We use an Implementation of Interface List to represent our Stack of Cards 
	private List<Card> cards;
	
	// No-Args Constructor - We use ArrayList for the dynamically sized Array 
	public CardStack() {
        this.cards = new ArrayList<>();
    }
	
	// Method to create a deck of 32 unique Cards
	public static CardStack initDeck() {
		CardStack newDeck = new CardStack();
		newDeck.getDeck();
		
		return newDeck;
	}
	
	// Method to get all 32 individual Cards 
	private void getDeck() {
		for (Suit suit : Suit.values()) {
			for (Type type : Type.values()) {
				this.addCard(new Card(suit, type));
			}
		}
	}

	// Method to add a Card to our Stack of Cards 
	public void addCard(Card card) {
		cards.add(card);
	}
	
	// Method to get our Cards 
	public List<Card> getCards() {
		return cards;
	}
	
	// Method to empty our Stack 
	static CardStack empty() {
		// TODO: return an empty Stack of Cards 
	}
	
	// Method to shuffle our cards 
	public void shuffle() {
		// TODO: shuffle our cards
	}
	
	// Method to check if our stack is empty 
	public boolean isEmpty() {
		// TODO: Check if cards is empty and return boolean value 
	}
	
	// Method to get the size of our stack 
	public int size() {
		// TODO: get the number of cards and return them as an int 
	}
	
	// Method to remove a card from our Stack 
	void removeCard(Card card) {
		// TODO: remove card from cards 
	}
	
	// Method to look through our stack for 
	Card getMatchingCard(Card card) {
		/* TODO: look through our cards for a card MATCHING our card, 
		 * if success, return the matching card, else return null */
	}
	
	// Method to create a String-representation of our Stack 
	@Override
	public String toString() {
		// TODO: turn our cards into a String-representation 
		// Look at how it is done with collections 
	}

}
