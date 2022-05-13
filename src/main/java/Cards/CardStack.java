package Cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Arved Meyer
 * @author Tobias Hering
 * @version 1.0.0
 * @comment Class to Model a STACK of CARDS 
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

	// Method to get to uppermost Card in the Stack
	public Card getTopCard() {
		return cards.get(cards.size() - 1);
	}
	
	// Method to remove a card from our Stack 
	void removeCard(Card card) {
		cards.remove(card); 
	}

	// Method to get our Cards 
	public List<Card> getCards() {
		return cards;
	}
	
	// Method to empty our Stack 
	static CardStack empty() {
		CardStack newDeck = new CardStack();
		return newDeck;
	}

	// Method to clear our existing Stack
	public void clear() {
		cards.clear();
	}
	
	// Method to shuffle our cards 
	public void shuffle() {
		Collections.shuffle(cards);
	}
	
	// Method to check if our stack is empty 
	public boolean isEmpty() {
		return (size() == 0) ? true : false;
	}
	
	// Method to get the size of our stack 
	public int size() {
		return cards.size();
	}
	
	// Method to look through our stack for 
	Card getMatchingCard(Card checkcard) {
		for (Card card : cards) {
			if (card.matches(checkcard)) {
				return card;
			}
		}

		return null;
	}
	
	// Method to create a String-representation of our Stack 
	@Override
	public String toString() {
		String cardStackString = "";

		for (Card card : cards) {
			cardStackString += card.toString() + "\n";
		}

		return cardStackString;
	}
}
