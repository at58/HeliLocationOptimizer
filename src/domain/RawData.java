package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * In memory database for raw data with CRUD-operations.
 */
public class RawData {

  private static List<String[]> data = new ArrayList<>();

  private RawData() {}

  public static void create(String[] tuple) {
    if (tuple.length != 4) {
      // do nothing.
    } else {
      data.add(tuple);
    }
  }

  public static List<String[]> read() {
    return data;
  }

  public static void update(String[] tuple, int index) {
    data.set(index, tuple);
  }

  public static void delete(int index) {
    data.remove(index);
  }

  public static void rebase(List<String[]> newDataBase) {
    data = new ArrayList<>(newDataBase);
  }
}