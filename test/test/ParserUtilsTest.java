package test;

import org.junit.jupiter.api.Test;
import utils.Separator;
import utils.ParserUtils;

import static org.junit.jupiter.api.Assertions.*;

public class ParserUtilsTest {

  @Test
  void containsOnlyTest() {
    // arrange
    String foo = "; ; ;";
    // act
    boolean actual = ParserUtils.containsOnly(foo, Separator.SEMICOLON.getCharacter());
    // assert
    assertTrue(actual);
  }

  @Test
  void containsOnlyTest_2() {
    // arrange
    String foo = ";;;";
    // act
    boolean actual = ParserUtils.containsOnly(foo, Separator.SEMICOLON.getCharacter());
    // assert
    assertTrue(actual);
  }

  @Test
  void containsOnlyTest_3() {
    // arrange
    String foo = ";; ;    ";
    // act
    boolean actual = ParserUtils.containsOnly(foo, Separator.SEMICOLON.getCharacter());
    // assert
    assertTrue(actual);
  }

  @Test
  void containsOnlyTest_Negative() {
    // arrange
    String foo = "a; b; c;";
    // act
    boolean actual = ParserUtils.containsOnly(foo, Separator.SEMICOLON.getCharacter());
    // assert
    assertFalse(actual);
  }

  @Test
  void containsOnlyTest_OnlyWhiteSpaces() {
    // arrange
    String foo = "   ";
    // act
    boolean actual = ParserUtils.containsOnly(foo, Separator.SEMICOLON.getCharacter());
    // assert
    assertFalse(actual);
  }

  @Test
  void containsOnlyTest_EmptyString() {
    // arrange
    String foo = "";
    // act
    boolean actual = ParserUtils.containsOnly(foo, Separator.SEMICOLON.getCharacter());
    // assert
    assertFalse(actual);
  }
}