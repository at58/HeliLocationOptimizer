package utils;

public enum Separator {

  COMMA(','),
  SEMICOLON(';'),
  COLON(':');

  private final char separator;

  Separator(char c) {
    this.separator = c;
  }

  public char getCharacter() {
    return this.separator;
  }
}