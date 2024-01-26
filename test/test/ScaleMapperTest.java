package test;

import core.Location;
import java.util.ArrayList;
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
    Integer[] expected_xScale = new Integer[] {12, 24, 36, 48, 60, 72, 84, 96, 108, 120, 132};
    Integer[] expected_yScale = new Integer[] {6, 12, 18, 24, 30, 36, 42, 48, 54, 60, 66};
    // act
    Map<String, List<Integer>> actual = ScaleMapper.specifyAxisScales(locationList);
    List<String> keyList = new ArrayList<>(actual.keySet());
    // assert x scale
    String xKey = keyList.get(0).startsWith("x") ? keyList.get(0) : keyList.get(1);
    List<Integer> actual_xScale = actual.get(xKey);
    Integer[] actual_xScaleArray = actual_xScale.toArray(Integer[]::new);
    assertArrayEquals(expected_xScale, actual_xScaleArray);

    // assert y sale
    String yKey = keyList.get(0).startsWith("y") ? keyList.get(0) : keyList.get(1);
    List<Integer> actual_yScale = actual.get(yKey);
    Integer[] actual_yScaleArray = actual_yScale.toArray(Integer[]::new);
    assertArrayEquals(expected_yScale, actual_yScaleArray);
  }

  @Test
  void axisScaleTest_2() {
    // arrange
    List<Location> locationList = List.of(
        new Location("StadtA", 20, 50, 22),
        new Location("StadtB", 5, 29, 22),
        new Location("StadtD", 120, 64, 22),
        new Location("StadtC", 66, 12, 22),
        new Location("StadtD", 210, 5, 22),
        new Location("StadtD", 420, 120, 22),
        new Location("StadtD", 211, 390, 22)
    );
    Integer[] expected_xScale = new Integer[] {42, 84, 126, 168, 210, 252, 294, 336, 378, 420, 462};
    Integer[] expected_yScale = new Integer[] {39, 78, 117, 156, 195, 234, 273, 312, 351, 390, 429};
    // act
    Map<String, List<Integer>> actual = ScaleMapper.specifyAxisScales(locationList);
    List<String> keyList = new ArrayList<>(actual.keySet());
    // assert x scale
    String xKey = keyList.get(0).startsWith("x") ? keyList.get(0) : keyList.get(1);
    List<Integer> actual_xScale = actual.get(xKey);
    Integer[] actual_xScaleArray = actual_xScale.toArray(Integer[]::new);
    assertArrayEquals(expected_xScale, actual_xScaleArray);

    // assert y sale
    String yKey = keyList.get(0).startsWith("y") ? keyList.get(0) : keyList.get(1);
    List<Integer> actual_yScale = actual.get(yKey);
    Integer[] actual_yScaleArray = actual_yScale.toArray(Integer[]::new);
    assertArrayEquals(expected_yScale, actual_yScaleArray);
  }

  @Test
  void axisScaleTest_RangeOverAxis() {
    // arrange
    List<Location> locationList = List.of(
        new Location("StadtA", 20, 50, 22),
        new Location("StadtB", 5, 29, 22),
        new Location("StadtD", 1500, 1200, 22)
    );
    Integer[] expected_xScale = new Integer[] {151, 302, 453, 604, 755, 906, 1057, 1208, 1359, 1510};
    Integer[] expected_yScale = new Integer[] {120, 240, 360, 480, 600, 720, 840, 960, 1080, 1200, 1320};
    // act
    Map<String, List<Integer>> actual = ScaleMapper.specifyAxisScales(locationList);
    List<String> keyList = new ArrayList<>(actual.keySet());
    // assert x scale
    String xKey = keyList.get(0).startsWith("x") ? keyList.get(0) : keyList.get(1);
    List<Integer> actual_xScale = actual.get(xKey);
    Integer[] actual_xScaleArray = actual_xScale.toArray(Integer[]::new);
    assertArrayEquals(expected_xScale, actual_xScaleArray);

    // assert y sale
    String yKey = keyList.get(0).startsWith("y") ? keyList.get(0) : keyList.get(1);
    List<Integer> actual_yScale = actual.get(yKey);
    Integer[] actual_yScaleArray = actual_yScale.toArray(Integer[]::new);
    assertArrayEquals(expected_yScale, actual_yScaleArray);
  }

  @Test
  void axisScaleTest_RangeWithZero() {
    // arrange
    List<Location> locationList = List.of(
        new Location("StadtA", 20, 50, 22),
        new Location("StadtB", 5, 0, 22),
        new Location("StadtD", 0, 1200, 22)
    );
    Integer[] expected_xScale = new Integer[] {2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22};
    Integer[] expected_yScale = new Integer[] {120, 240, 360, 480, 600, 720, 840, 960, 1080, 1200, 1320};
    // act
    Map<String, List<Integer>> actual = ScaleMapper.specifyAxisScales(locationList);
    List<String> keyList = new ArrayList<>(actual.keySet());
    // assert x scale
    String xKey = keyList.get(0).startsWith("x") ? keyList.get(0) : keyList.get(1);
    List<Integer> actual_xScale = actual.get(xKey);
    Integer[] actual_xScaleArray = actual_xScale.toArray(Integer[]::new);
    assertArrayEquals(expected_xScale, actual_xScaleArray);

    // assert y sale
    String yKey = keyList.get(0).startsWith("y") ? keyList.get(0) : keyList.get(1);
    List<Integer> actual_yScale = actual.get(yKey);
    Integer[] actual_yScaleArray = actual_yScale.toArray(Integer[]::new);
    assertArrayEquals(expected_yScale, actual_yScaleArray);
  }
}