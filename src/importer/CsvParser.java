package importer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import utils.Separator;
import utils.log.Logger;

public class CsvParser implements Parser <File, List<String[]>> {

  @Override
  public List<String[]> parse(File file, Separator separator) {

    List<String[]> tuples = new ArrayList<>();

    try {
      BufferedReader reader = new BufferedReader(new FileReader(file.getName()));
      String line;
      while((line = reader.readLine()) != null) {
        tuples.add(line.split(separator.getCharacter()));
      }
    } catch (IOException e) {
      Logger.log(e.getMessage());
    }
    return tuples;
  }
}
