/**
 * 
 */
package components.comboboxes.renderers;

import java.awt.Component;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import commons.enums.DumpMode;
import components.labels.ErroneousLabel;

/**
 * @author Naeregwen
 *
 */
public class DumpModeComboBoxRenderer extends ErroneousLabel implements ListCellRenderer<DumpMode> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -9212667712154260444L;

	private static final ImageIcon[] images;
	
	static {
		// Create the combo box.
		images = new ImageIcon[DumpMode.values().length];
		int i = 0;
		for (DumpMode dumpMode : DumpMode.values()) {
			images[i] = createImageIcon(dumpMode.getIconPath());
			if (images[i] != null)
				images[i].setDescription(dumpMode.name());
			i += 1;
		}
	}

	private final ListCellRenderer<DumpMode> listCellRenderer;
	
	public DumpModeComboBoxRenderer(ListCellRenderer<DumpMode> listCellRenderer) {
		this.listCellRenderer = listCellRenderer;
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends DumpMode> list, DumpMode dumpMode, int index, boolean isSelected, boolean cellHasFocus) {
		JComponent component = (JComponent) listCellRenderer.getListCellRendererComponent(list, dumpMode, index, isSelected, cellHasFocus);
		if (component instanceof JLabel) {
			// Set the icon
			ImageIcon icon = images[dumpMode.ordinal()];
			((JLabel) component).setIcon(icon);
			// Set the text
			// Replace text by error message when icon is null
			ResourceBundle UITexts = ResourceBundle.getBundle("i18n/UITexts");
			String text = UITexts.getString(DumpMode.values()[dumpMode.ordinal()].getLabelKey());
			if (icon != null)
				((JLabel) component).setText(text);
			else
				setUhOhText((JLabel) component, String.format(UITexts.getString("errorComboBoxNoImageAvailable"), text), list.getFont());
		}
		return component;
	}


}
