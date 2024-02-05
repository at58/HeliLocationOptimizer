package test;

import static org.junit.jupiter.api.Assertions.*;

import domain.Location;
import org.junit.jupiter.api.Test;

public class LocationTest {

  @Test
  void toStringArrayTest() {
    // arrange
    Location location = new Location("A", 20,30, 55);
    String[] expected = new String[] {"A", "20", "30", "55"};
    // act
    String[] actual = location.toStringArray();
    // assert
    assertArrayEquals(expected, actual);
  }
}