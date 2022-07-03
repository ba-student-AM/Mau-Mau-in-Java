package Cards;

import java.util.HashMap;

/**
 * @author Arved Meyer
 * @author Tobias Hering
 * @version 1.0.0
 * @comment Enum to model the four Suits of Skat-Cards
 * 
 * @documentation https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html
 * @info Because they are constants, the names of an enum type's fields are in uppercase letters.
 */

public enum Suit {
	
  HERZEN,
  SCHELLEN,
  BLAETTER,
  EICHELN;
	
	/* Access all the Values of the Enum from an Array via a getter-Method
	 * No Setter needed, as Enum-Values cannot be changed at runtime */
	private static final Suit[] SUITS = Suit.values();
	public static Suit getType(int index) {
		return Suit.SUITS[index]; 
	}

  public String getTranslation(){
    HashMap<String, String> hashmap = new HashMap<>();
    hashmap.put("HERZEN", "HERZ");
    hashmap.put("SCHELLEN", "KARO");
    hashmap.put("BLAETTER", "PIK");
    hashmap.put("EICHELN", "KREUZ");
    return hashmap.get(this.name());
  }
}
