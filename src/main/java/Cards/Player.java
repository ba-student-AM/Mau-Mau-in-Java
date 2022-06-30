package Cards;

/**
 * @author Arved Meyer
 * @version 1.0.0
 * @comment Class to model a player in the game
 */

public class Player {

    private String playerName;
    private CardStack hand;

    private Card matchingCard;

    public Player(String playerName) {
        this.playerName = playerName;
        hand = new CardStack();
    }

    public String getName() {
        return playerName;
    }

    public CardStack getHand() {
        return hand;
    }

    public void setName(String playerName) {
        this.playerName = playerName;
    }

    public void setHand(CardStack hand) {
        this.hand = hand;
    }

    // Method to pick a Card from the players Hand
    public Card pickCard(Card card) {
        hand.removeCard(card);
        return card;
    }

    // Method to draw a Card from a specified stack
    public void drawCardFromStack(CardStack stack) {
        int index = stack.getTopCardIndex();
        this.hand.addCard(stack.drawNthCard(index));
        stack.removeCardIndex(index);
    }
    
    public void putCardOnStack(CardStack stack, Card card) {
      this.hand.removeCard(card);
        stack.addCard(card);
    }

    // Method to test if a player has a matching card 
    public boolean hasCardMatching(Card card) {
        this.matchingCard = hand.getMatchingCard(card);
        return this.matchingCard != null; 
    }
    
    // Method to get the players selected Card 
    public Card getPlayerCard(int n) {
    	return hand.drawNthCard(n);
    }
    
    // Method to check if our Hand is empty 
    public boolean hasEmptyHand() {
    	return this.getHand().isEmpty();
    }

}
