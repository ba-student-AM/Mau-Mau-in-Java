package Cards;

import java.util.HashMap;

/**
 * @author Arved Meyer
 * @author Tobias Hering
 * @version 3.0.0
 * @comment Enum to model the possible Types of Skat-Cards 
 * 
 * @documentation https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html
 * @info Because they are constants, the names of an enum type's fields are in uppercase letters.
 */

public enum Type {
  SIEBEN,
  ACHT,
  NEUN,
  ZEHN,
  ASS,
  OBER,
  KOENIG,
  UNTER;

  /* Access all the Values of the Enum from an Array via a getter-Method
   * No Setter needed, as Enum-Values cannot be changed at runtime */
  private static final Type[] TYPES = Type.values();
  public static Type getType(int index) {
    return Type.TYPES[index];
  }
  public String getTranslation() {
    HashMap<String, String> hashmap = new HashMap<>();
    hashmap.put("SIEBEN", "SIEBEN");
    hashmap.put("ACHT", "ACHT");
    hashmap.put("NEUN", "NEUN");
    hashmap.put("ZEHN", "ZEHN");
    hashmap.put("ASS", "ASS");
    hashmap.put("OBER", "DAME");
    hashmap.put("KOENIG", "KÖNIG");
    hashmap.put("UNTER", "BUBE");
    return hashmap.get(this.name());
  }

}