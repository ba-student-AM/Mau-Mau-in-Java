package Cards;

/**
 * @author Arved Meyer
 * @author Tobias Hering
 * @version 2.1.0
 * @comment Enum to model the possible Types of Skat-Cards 
 * 
 * @documentation https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html
 * @info Because they are constants, the names of an enum type's fields are in uppercase letters.
 */

public enum Type {
  ACE,
  // TWO,
  // THREE,
  // FOUR,
  // FIVE,
  // SIX,
  // SEVEN,
  EIGHT,
  NINE,
  TEN,
  JACK,
  QUEEN,
  KING;
	
	/* Access all the Values of the Enum from an Array via a getter-Method
	 * No Setter needed, as Enum-Values cannot be changed at runtime */
	private static final Type[] TYPES = Type.values();
	public static Type getType(int index) {
		return Type.TYPES[index]; 
	}

}
