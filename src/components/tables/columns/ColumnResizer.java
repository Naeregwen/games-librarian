/**
 * 
 */
package components.tables.columns;

/**
 * http://niravjavadeveloper.blogspot.fr/2011/05/resize-jtable-columns.html
 */
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.*;

import commons.api.Steam;
import commons.api.SteamFriend;
import commons.api.SteamGroup;

public class ColumnResizer {
	
	public static void adjustColumnPreferredWidths(JTable table) {
		// strategy - get max width for cells in column and
		// make that the preferred width
		for (int col = 0; col < table.getColumnCount(); col++) {
			
			TableColumn column = table.getColumnModel().getColumn(col);
			
			// Force logo columns size
			if (column.getIdentifier().equals(SteamFriend.ColumnsOrder.logo.name()) || column.getIdentifier().equals(SteamGroup.ColumnsOrder.logo.name())) {
				// Unavailable : int iconTextGap = UIManager.getLookAndFeelDefaults().getInt("Label.textIconGap"); -- Button.textIconGap
				int width = Steam.steamFriendIconWidth
						+ Math.max(UIManager.getIcon("Table.ascendingSortIcon").getIconWidth(), UIManager.getIcon("Table.descendingSortIcon").getIconWidth())
						+ new JLabel().getIconTextGap();
				column.setPreferredWidth(width);
				column.setWidth(width);
				column.setMaxWidth(width);
				column.setMinWidth(width);
			
			// Ignore SteamGroup Summary columns
			} else if (!column.getIdentifier().equals(SteamGroup.ColumnsOrder.summary.name())) {
				
				int maxwidth = 0;
				for (int row = 0; row < table.getRowCount(); row++) {
					TableCellRenderer rend = table.getCellRenderer(row, col);
					Object value = table.getValueAt(row, col);
					Component comp = rend.getTableCellRendererComponent(table,
							value, false, false, row, col);
					maxwidth = Math.max(comp.getPreferredSize().width, maxwidth);
				}
				column.setPreferredWidth(maxwidth);
			}
		}
	}
}
