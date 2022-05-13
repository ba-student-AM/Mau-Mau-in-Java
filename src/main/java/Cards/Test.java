package Cards;

class Test {
  public static void main(String args[]) {

    CardStack mainCardStack = CardStack.initDeck();

    System.out.println("before:\n" + mainCardStack.toString());

    mainCardStack.shuffle();

    System.out.println("after:\n" + mainCardStack.toString());

  }
}