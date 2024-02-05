package services.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class TableDataMapper {

  public static Object[][] mapToTableModel(List<String[]> csvTuples) {

    Object[][] result = new Object[csvTuples.size() - 1][4];
    for (int i = 1; i < csvTuples.size(); i++) {
      Object[] modelTuple = new Object[4];
      String[] csvTuple = csvTuples.get(i);
      modelTuple[0] = csvTuple[0];
      modelTuple[1] = Integer.parseInt(csvTuple[1]);
      modelTuple[2] = Integer.parseInt(csvTuple[2]);
      modelTuple[3] = Integer.parseInt(csvTuple[3]);
      result[i-1] = modelTuple;
    }
    return result;
  }

  public static List<String[]> mapToStringArrayList(Vector<Vector> dataVector) {

    List<String[]> result = new ArrayList<>();
    dataVector.forEach(vector -> {
      Object[] objects = vector.toArray();
      String[] strings = new String[objects.length];
      for (int i = 0; i < objects.length; i++) {
        strings[i] = String.valueOf(objects[i]);
      }
      result.add(strings);
    });
    return result;
  }
}