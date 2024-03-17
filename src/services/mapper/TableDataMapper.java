package services.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import java.util.regex.Pattern;
import javax.swing.JTextField;
import utils.exceptions.ColumnIdentifierException;
import utils.log.Logger;

/**
 * Mapper from or to the table model into another data structure.
 */
public class TableDataMapper {

  // Ort, x, y, Unfallzahlen
  private static final String[] columnIdentifiers = new String[] {"ort",
                                                                  "x koordinate",
                                                                  "y koordinate",
                                                                  "unfallzahl"};

  public static Object[][] stringListToTableModel(List<String[]> csvTuples)
      throws ColumnIdentifierException, NumberFormatException {

    String[] columns = extractColumnIdentifiers(csvTuples);
    Integer[] legendIndexes = new Integer[columns.length];

    for (int i = 0; i < legendIndexes.length; i++) {
      String ref = columnIdentifiers[i];
      Pattern pattern = Pattern.compile(".*" + ref + ".*");
      for (int j = 0; j < columns.length; j++) {
        if (columns[j].matches(pattern.pattern())) {
          legendIndexes[i] = j;
          break;
        }
      }
    }
    for (Integer i : legendIndexes) {
      if (i == null) {
        throw new ColumnIdentifierException("Die Spalten der importierten Datei sind inkompatibel!");
      }
    }

    Object[][] result = new Object[csvTuples.size() - 1][4];
    for (int i = 1; i < csvTuples.size(); i++) {
      Object[] modelTuple = new Object[4];
      String[] csvTuple = csvTuples.get(i);
      modelTuple[0] = csvTuple[legendIndexes[0]];
      modelTuple[1] = Integer.parseInt(csvTuple[legendIndexes[1]]);
      modelTuple[2] = Integer.parseInt(csvTuple[legendIndexes[2]]);
      modelTuple[3] = Integer.parseInt(csvTuple[legendIndexes[3]]);
      result[i-1] = modelTuple;
    }
    return result;
  }

  public static List<String[]> vectorToStringList(Vector dataVector) {

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

  public static String[] extractColumnIdentifiers(List<String[]> tupleList) {
    // the first tuple is the legend of the input, converted to lower-case due to easier processing.
    return Arrays.stream(tupleList.get(0))
                 .map(String::toLowerCase)
                 .map(c -> c.replaceAll("-", " "))
                 .toArray(String[]::new);
  }

  public static Object[] extractTextFieldContent(JTextField[] textFields) {

    Object[] newTuple;
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