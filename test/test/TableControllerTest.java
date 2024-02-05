package test;

import controller.Controller;
import controller.TableController;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TableControllerTest {

  @Test
  void tableControllerTest() {

    Controller.importCSV();
    List<String[]> dataModel = TableController.getTableData();
    dataModel.forEach(d -> {
      Arrays.stream(d).forEach(e -> {
        System.out.print(e);
        System.out.print(", ");
      });
      System.out.println();
    });
  }
}