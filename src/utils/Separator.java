package utils;

public enum Separator {

  COMMA(","),
  SEMICOLON(";"),
  COLON(":");

  private final String separator;

  Separator(String c) {
    this.separator = c;
  }

  public String getCharacter() {
    return this.separator;
  }
}