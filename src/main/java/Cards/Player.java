package Cards;

/**
 * @author Arved Meyer
 * @version 0.1.0
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

    public String getPlayerName() {
        return playerName;
    }

    public CardStack getHand() {
        return hand;
    }

    public void setPlayerName(String playerName) {
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
    public Card drawCardFromStack(CardStack stack) {
        Card card = stack.getTopCard();
        hand.addCard(card);
        stack.removeCard(card);
        return card;
    }

    // 
    public boolean hasCardMatching(Card card) {
        this.matchingCard = hand.getMatchingCard(card);
        return this.matchingCard != null; 
    }

}
