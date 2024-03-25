package test;

import controller.MainController;
import controller.TableController;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TableMainControllerTest {

  @Test
  void tableControllerTest() {

    MainController.importCSV();
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