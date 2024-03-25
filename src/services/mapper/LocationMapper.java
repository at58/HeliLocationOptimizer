package services.mapper;

import domain.Location;
import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for location objects.
 */
public class LocationMapper {

  /**
   * Maps the parsed csv data to objects of {@link Location}.
   *
   * @param tupleList The parsed list of string arrays.
   * @return A list of locations.
   */
  public static List<Location> mapToLocationObjects(List<String[]> tupleList) {
    List<Location> locationList = new ArrayList<>();

    for (int i = 0; i < tupleList.size(); i ++) {
      String[] tuple = tupleList.get(i);
      String locationName = tuple[0];
      int x = Integer.parseInt(tuple[1]);
      int y = Integer.parseInt(tuple[2]);
      int accidents = Integer.parseInt(tuple[3]);
      Location location = new Location(locationName, x, y, accidents);
      locationList.add(location);
    }
    return locationList;
  }
}