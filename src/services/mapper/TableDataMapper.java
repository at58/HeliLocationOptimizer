package services.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import javax.swing.JTextField;
import utils.log.Logger;

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

  public static Object[] extractTextFieldContent(JTextField[] textFields) {

    Object[] newTuple = null;
    try {
      Arrays.stream(textFields).forEach(field -> {
        if (Objects.isNull(field) || field.getText().isBlank()) {
          throw new IllegalArgumentException();
        }
      });
      newTuple = new Object[4];
      newTuple[0] = textFields[0].getText();
      newTuple[1] = Integer.parseInt(textFields[1].getText());
      newTuple[2] = Integer.parseInt(textFields[2].getText());
      newTuple[3] = Integer.parseInt(textFields[3].getText());
    } catch (IllegalArgumentException e) {
      newTuple = null;
      Logger.log("Incomplete or invalid data tuple was submitted.");
    }
    return newTuple;
  }
}