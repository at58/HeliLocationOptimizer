package services.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import java.util.regex.Pattern;
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

  public static List<String[]> mapToStringArrayList(Vector dataVector) {

    List<String[]> result = new ArrayList<>();
    Object[] outerArray = dataVector.toArray();
    char[] dataString = Arrays.toString(outerArray).toCharArray();
    List<Character> charList = new ArrayList<>();
    for (char c : dataString) {
      charList.add(c);
    }
    charList.remove(0);
    charList.remove(charList.size() - 1);

    StringBuilder tupleBuilder = new StringBuilder();
    int index = 0;
    while (index < charList.size()) {
      char c = charList.get(index);
      if (c != '[' && c != ']' && c != ' ') {
        tupleBuilder.append(c);
        index++;
      } else if (c == ']') {
        String[] tuple = tupleBuilder.toString().split(",");
        result.add(tuple);
        tupleBuilder = new StringBuilder();
        index += 2;
      } else {
        index++;
      }
    }
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