package domain;

import java.util.UUID;

/**
 * API for Objects that are drawn as points in the coordinate system such as locations or helicopter
 * bases.
 */
public interface Item {

  Coordinate getCoordinate();
  UUID getUuid();
}