package core;

public final class Location {

  private final String name;
  private final Coordinate coordinate;
  private final int accidents;

  public Location(String name, int x, int y, int accidents) {
    this.name = name;
    this.coordinate = new Coordinate(x, y);
    this.accidents = accidents;
  }

  public String getName() {

    return name;
  }

  public Coordinate getCoordinate() {

    return coordinate;
  }

  public int getAccidents() {

    return accidents;
  }

  public String[] toStringArray() {

    return new String[] {
        this.name,
        String.valueOf(this.coordinate.x()),
        String.valueOf(this.coordinate.y()), String.valueOf(this.accidents)
    };
  }
}