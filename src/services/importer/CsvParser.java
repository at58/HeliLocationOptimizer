package services.importer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import utils.Separator;
import utils.StringUtils;
import utils.log.Logger;

public class CsvParser implements Parser <String, List<String[]>> {

  @Override
  public List<String[]> parse(String path, Separator separator) throws IOException {

    if (Objects.isNull(separator)) {
      separator = Separator.SEMICOLON;
    }

    List<String[]> tupleList = new ArrayList<>();
    int lineCounter = 0;

    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
      String line;
      while ((line = reader.readLine()) != null) {
        line = line.strip();
        if (lineCounter <= 1) {
          if (!line.isBlank() && !StringUtils.containsOnly(line, separator.getCharacter())) {
            lineCounter++;
          }
        }
        String[] tuple = line.split(String.valueOf(separator.getCharacter()));
        if (StringUtils.isValideTuple(tuple, 4)) {
          tupleList.add(tuple);
        }
      }
      if (lineCounter <= 1) {
        throw new IOException("The csv file located in "
                                  + path
                                  + " is completely empty or contains no data except of the header.");
      }
      if (tupleList.size() <= 1) {
        throw new IOException("The csv file located in "
                                  + path
                                  + " consist of less than one valid tuple.");
      }
    } catch (IOException e) {
      // e.printStackTrace();
      Logger.log(e.getMessage());
      throw e;
    }
    return tupleList;
  }
}