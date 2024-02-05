package utils;

public class ParserUtils {

  /**
   * Checks if a string is consist of characters except white-spaces and the specified separation
   * character.
   *
   * @param text the string line.
   * @param character the separation character.
   * @return true if and only if the string consists of more than white-spaces and the separation
   * character.
   */
  public static boolean containsOnly(String text, char character) {
    if (text.isBlank()) {
      return false;
    }
    char[] charArray = text.toCharArray();
    for (char c : charArray) {
      if (c != ' ' && c != character) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks if the tuple size equals the specified size. Also checks if the string tuple contains
   * empty elements. Each tuple must not contain empty elements, otherwise false is returned. An
   * element is considered as empty also when it contains only white-spaces.
   *
   * @param tuple the tuple of strings.
   * @return true if and only if the tuple contain no empty elements and has the required length.
   */
  public static boolean isValideTuple(String[] tuple, int size) {

    if (tuple.length != size) {
      return false;
    }

    for (String s : tuple) {
      if (s.isBlank()) {
        return false;
      }
    }
    return true;
  }
}