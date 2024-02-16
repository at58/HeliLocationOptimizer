package domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Domain class for the helicopter base.
 */
public final class Helicopter implements Item {

  private final UUID uuid;
  private int speed;
  private Coordinate coordinate;
  /* Mapping of location and the distance between this helicopter object and the assigned location.*/
  private final Map<Location, Double> locationHelicopterMap;

  public Helicopter(int airSpeed) {
    this.uuid = UUID.randomUUID();
    this.speed = airSpeed;
    this.locationHelicopterMap = new HashMap<>();
  }

  @Override
  public Coordinate getCoordinate() {
    return this.coordinate;
  }

  public void setCoordinates(int x, int y) {
    this.coordinate = new Coordinate(x, y);
  }

  @Override
  public UUID getUuid() {return this.uuid;}

  public void allocateLocation(Location location, double distance) {
    this.locationHelicopterMap.put(location, distance);
  }

  public void removeLocation(Location location) {
    this.locationHelicopterMap.remove(location);
  }

  public Map<Location, Double> getLocationHelicopterMapping() {
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