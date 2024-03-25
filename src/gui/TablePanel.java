package gui;

import controller.TableController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import java.util.Objects;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * The table panel is a dedicated panel for the data table which is nested in
 * it.
 */
public class TablePanel extends JPanel {

	private static final long serialVersionUID = -8266646540680422404L;
	
	/* Singleton instance */
	private static TablePanel tablePanel;
	private final JTable table;
	private boolean editLock = false;
	private int row = -1;
	private int col = -1;

	public static TablePanel getInstance() {
		if (Objects.isNull(tablePanel)) {
			tablePanel = new TablePanel();
		}
		return tablePanel;
	}

	private TablePanel() {

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.LIGHT_GRAY);

		table = new JTable(TableController.getTableModel());
		table.setFillsViewportHeight(true); // TODO (Ahmet): check if necessary
		table.setFont(Font.CONSOLAS14.getFont());
		table.getTableHeader().setFont(Font.CONSOLAS16.getFont());
		table.setToolTipText("Mit rechtem Mausklick eine Zeile entfernen.");

		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(1).setCellRenderer(renderer);
		table.getColumnModel().getColumn(2).setCellRenderer(renderer);
		table.getColumnModel().getColumn(3).setCellRenderer(renderer);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					int r = table.rowAtPoint(e.getPoint());
					r = table.convertRowIndexToModel(r);
					int c = table.columnAtPoint(e.getPoint());

					if (r == row && c == col) {
						editLock = true;
					} else {
						row = r;
						col = c;
						editLock = false;
					}
				}
				if (SwingUtilities.isRightMouseButton(e) && !editLock) {
					int row = table.rowAtPoint(e.getPoint());
					int col = table.columnAtPoint(e.getPoint());
					row = table.convertRowIndexToModel(row);

					if (row >= 0 && col >= 0) {
						// remove the row from table
						TableController.delete(row);
					}
				}
			}
		});

		// enable and configure sorting for table
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);

		Comparator<Integer> integerComparator = Comparator.comparingInt(Integer::intValue);
		sorter.setComparator(1, integerComparator);
		sorter.setComparator(2, integerComparator);
		sorter.setComparator(3, integerComparator);

		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);
	}
}