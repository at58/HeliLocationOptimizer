package utils;

public class StringUtil {

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
}