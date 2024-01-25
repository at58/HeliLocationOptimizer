package test;

import core.Location;
import java.util.List;
import org.junit.jupiter.api.Test;
import services.mapper.ScaleMapper;

import static org.junit.jupiter.api.Assertions.*;

public class ScaleMapperTest {

  @Test
  void scaleTest() {
    // arrange
    List<Location> locationList = List.of(
        new Location("StadtA", 20, 50, 22),
        new Location("StadtB", 5, 29, 22),
        new Location("StadtC", 66, 12, 22),
        new Location("StadtD", 105, 65, 22)
    );
    int[][] expected = new int[][] {{5, 105}, {12, 65}};
    // act
    int[][] actual = ScaleMapper.determineAxisRanges(locationList);
    // assert
    assertArrayEquals(expected, actual);
  }
}
