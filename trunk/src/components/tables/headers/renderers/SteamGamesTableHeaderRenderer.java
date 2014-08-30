/**
 * 
 */
package components.tables.headers.renderers;

import java.awt.Component;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JTable;

import commons.api.SteamGame.ColumnsOrder;

/**
 * @author Naeregwen
 *
 */
public class SteamGamesTableHeaderRenderer extends DefaultTableHeaderCellRenderer {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8121776560937019794L;

	/* (non-Javadoc)
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		
		Component header = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		if (header instanceof JLabel) {
			ColumnsOrder columnsOrder = ColumnsOrder.valueOf((String) value); 
			ResourceBundle UITexts = ResourceBundle.getBundle("i18n/UITexts");
			((JLabel) header).setText(UITexts.getString(columnsOrder.getHeaderName()));
		}
        return header;
	}

}
