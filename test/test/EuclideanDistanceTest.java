package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import core.Coordinate;
import services.calculations.Euclid;
import utils.MathUtils;

public class EuclideanDistanceTest {

  @Test
  void calculateDistanceTest() {
    // Arrange
    Coordinate A = new Coordinate(9, 10);
    Coordinate B = new Coordinate(5, 5);
    double expected = 6.403124237;
    // Act
    double actual = MathUtils.round(Euclid.calculateDistance(A, B), 9);
    // Assert
    assertEquals(expected, actual);
  }

  @Test
  void calculateDistanceTest_2() {
    // Arrange
    Coordinate A = new Coordinate(160, 120);
    Coordinate B = new Coordinate(200, 360);
    double expected = 243.3105012;
    // Act
    double actual = MathUtils.round(Euclid.calculateDistance(A, B), 7);
    // Assert
    assertEquals(expected, actual);
  }

  @Test
  void calculateDistanceTest_3() {
    // Arrange
    Coordinate A = new Coordinate(1, 1);
    Coordinate B = new Coordinate(2, 2);
    double expected = 1.414213562;
    // Act
    double actual = MathUtils.round(Euclid.calculateDistance(A, B), 9);
    // Assert
    assertEquals(expected, actual);
  }
}