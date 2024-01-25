package test;

import core.Location;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import services.mapper.ScaleMapper;

import static org.junit.jupiter.api.Assertions.*;

public class ScaleMapperTest {

  @Test
  void axisRangeTest() {
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

  @Test
  void axisScaleTest() {
    // arrange
    List<Location> locationList = List.of(
        new Location("StadtA", 20, 50, 22),
        new Location("StadtB", 5, 29, 22),
        new Location("StadtC", 66, 12, 22),
        new Location("StadtD", 120, 65, 22)
    );
    Map<String, int[]> expected = Map.of("x-Axis/1:",
                                         new int[] {10, 20, 30, 40, 50, 60, 70, 80, 90, 100},
                                         "y-Axis/",
                                         new int[] {5, 10, 15, 20, 25, 30, 35, 40, 45, 50});
    // act
    Map<String, int[]> actual = ScaleMapper.specifyAxisScales(locationList,
                                                              Toolkit.getDefaultToolkit()
                                                                     .getScreenSize(), 10);
    List<String> keyList = new ArrayList<>(actual.keySet());
    // assert
    assertArrayEquals(expected.get("x-Axis/1:"), actual.get(keyList.get(0)));
  }
}
