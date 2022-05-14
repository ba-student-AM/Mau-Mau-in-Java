package Test;

import Cards.CardStack;

public class CardStackTest {

	public static void main(String[] args) {
		
		CardStack testStack = new CardStack();
		CardStack auxxStack = new CardStack(); 
		testStack = CardStack.initDeck();
		testStack.shuffle();
		
		System.out.println(testStack.toString());
		System.out.println(testStack.size());
		
	}

}
