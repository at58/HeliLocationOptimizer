package services.mapper;

import java.util.Arrays;
import java.util.List;

public class TableDataMapper {

  public static Object[][] mapToTableModel(List<String[]> csvTuples) {

    Object[][] result = new Object[csvTuples.size()][4];
    for (int i = 1; i < csvTuples.size(); i++) {
      Object[] modelTuple = new Object[4];
      String[] csvTuple = csvTuples.get(i);
      Arrays.stream(csvTuple).forEach(System.out::println);
      modelTuple[0] = csvTuple[0];
      modelTuple[1] = Integer.parseInt(csvTuple[1]);
      modelTuple[2] = Integer.parseInt(csvTuple[2]);
      modelTuple[3] = Integer.parseInt(csvTuple[3]);
      result[i-1] = modelTuple;
    }
    return result;
  }
}
