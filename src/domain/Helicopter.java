package domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public final class Helicopter implements Item {

  private final UUID uuid;
  private int speed;
  private Coordinate coordinate;
  /* Mapping of location and the distance between this helicopter object and the location. */
  private final Map<Location, Integer> locationHelicopterMap;

  public Helicopter(int airSpeed) {
    this.uuid = UUID.randomUUID();
    this.speed = airSpeed;
    this.locationHelicopterMap = new HashMap<>();
  }

  @Override
  public Coordinate getCoordinate() {
    return this.coordinate;
  }

  @Override
  public UUID getUuid() {return this.uuid;}

  public void setHeliCoordinates(int x, int y) {
    this.coordinate = new Coordinate(x, y);
  }

  public void addLocation(Location location, int distance) {
    this.locationHelicopterMap.put(location, distance);
  }

  public Map<Location, Integer> getLocationHelicopterMapping() {
    return this.locationHelicopterMap;
  }

  public Set<Location> getAssignedLocations() {
    return this.locationHelicopterMap.keySet();
  }

  public int getSpeed() {
    return this.speed;
  }

  public void setSpeed(int velocity) {
    this.speed = velocity;
  }
}