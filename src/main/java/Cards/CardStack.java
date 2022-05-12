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
		
	}

}
