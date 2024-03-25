package domain;

import java.util.UUID;

/**
 * Domain class for Location objects.
 */
public final class Location implements Item {

  private final UUID uuid;
  private final String name;
  private final Coordinate coordinate;
  private final int accidents;

  public Location(String name, int x, int y, int accidents) {
    this.uuid = UUID.randomUUID();
    this.name = name;
    this.coordinate = new Coordinate(x, y);
    this.accidents = accidents;
  }

  @Override
  public Coordinate getCoordinate() {
    return this.coordinate;
  }

  @Override
  public UUID getUuid() {return this.uuid;}

  public String getName() {return this.name;}

  public int getAccidents() {
    return this.accidents;
  }

  public String[] toStringArray() {
    return new String[] {
        this.name,
        String.valueOf(this.coordinate.x()),
        String.valueOf(this.coordinate.y()), String.valueOf(this.accidents)
    };
  }
}