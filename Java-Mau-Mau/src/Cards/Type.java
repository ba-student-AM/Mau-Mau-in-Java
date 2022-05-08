package Cards;

/**
 * @author Arved Meyer
 * @version 2.0.0
 * 
 * Enum to model the eight Types of Skat-Cards 
 * 
 */

public enum Type {
	
	// Acht Karten-Typen 
	Sieben, Acht, Neun, Zehn, Ass, Ober, König, Unter;
	
	/* Access all the Values of the Enum from an Array via a getter-Method
	 * No Setter needed, as Enum-Values cannot be changed at runtime */
	private static final Type[] types = Type.values();
	public static Type getType(int index) {
		return Type.types[index]; 
	}

}
