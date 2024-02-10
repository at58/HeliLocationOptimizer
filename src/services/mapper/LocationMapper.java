package services.mapper;

import domain.Location;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class LocationMapper {

  public static List<Location> mapToLocationObjects(List<String[]> tupleList) {
    List<Location> locationList = new ArrayList<>();

/*    // the first tuple is the legend of the input, converted to lower-case due to easier processing.
    String[] columns = Arrays.stream(tupleList.get(0))
                             .map(String::toLowerCase)
                             .map(c -> c.replaceAll("-", " "))
                             .toArray(String[]::new);
    // Ort, x, y, Unfallzahlen
    String[] legend = new String[] {"ort", "x koordinate", "y koordinate", "unfallzahl"};
    int[] legendIndexes = new int[columns.length];

    for (int i = 0; i < legendIndexes.length; i++) {
      String ref = legend[i];
      Pattern pattern = Pattern.compile(".*" + ref + ".*");
      for (int j = 0; j < columns.length; j++) {
        if (columns[j].matches(pattern.pattern())) {
          legendIndexes[i] = j;
          break;
        }
      }
    }*/
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