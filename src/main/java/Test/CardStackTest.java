package Test;

import Cards.CardStack;

public class CardStackTest {

	public static void main(String[] args) {
		
		CardStack testStack = new CardStack();
		CardStack secondStack = new CardStack(); 
		testStack = CardStack.initDeck();
		testStack.shuffle();
		
		testStack.moveAllCards(secondStack);
		
		
		System.out.println(testStack.toString());
		System.out.println("Dies ist die größe von testStack: " + testStack.size());
		
		System.out.println(secondStack.toString());
		System.out.println("Dies ist die größe von secondStack: " + secondStack.size());
	}

}
