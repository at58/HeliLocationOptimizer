package domain;

import java.util.UUID;

public interface Item {

  Coordinate getCoordinate();
  UUID getUuid();
}