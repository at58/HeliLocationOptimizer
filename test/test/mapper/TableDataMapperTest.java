package test.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import services.mapper.TableDataMapper;

public class TableDataMapperTest {

  @Test
  void mapToStringArrayListTest() {
    // arrange
    DefaultTableModel model = new DefaultTableModel();
    model.setColumnIdentifiers(new Object[] {"Ort", "x", "y", "Unfallzahl"});
    model.addRow(new Object[] {"Wien", "5", "50", "68"});
    model.addRow(new Object[] {"Berlin", "120", "15", "156"});
    model.addRow(new Object[] {"Munich", "105", "220", "20"});
    model.addRow(new Object[] {"Kiel", "90", "2", "12"});

    List<String[]> expected = new ArrayList<>(List.of(
        new String[] {"Wien", "5", "50", "68"},
        new String[] {"Berlin", "120", "15", "156"},
        new String[] {"Munich", "105", "220", "20"},
        new String[] {"Kiel", "90", "2", "12"}
    ));

    // act
    List<String[]> actual = TableDataMapper.vectorToStringList(model.getDataVector());

    // assert
    assertEquals(expected.size(), actual.size());

    for (int i = 0; i < expected.size(); i++) {
      assertArrayEquals(expected.get(i), actual.get(i));
    }
  }
}
