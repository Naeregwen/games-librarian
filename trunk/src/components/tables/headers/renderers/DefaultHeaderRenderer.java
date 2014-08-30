/**
 * 
 */
package components.tables.headers.renderers;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * @author Naeregwen
 *
 */
public class DefaultHeaderRenderer extends JLabel implements TableCellRenderer {

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8506276273837345185L;

	public DefaultHeaderRenderer() {}
    
	/* (non-Javadoc)
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		setText((String) value);
		return this;
	}

}
