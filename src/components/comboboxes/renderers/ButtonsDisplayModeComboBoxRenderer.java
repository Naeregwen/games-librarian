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

import commons.enums.ButtonsDisplayMode;
import components.labels.ErroneousLabel;

/**
 * @author Naeregwen
 *
 */
public class ButtonsDisplayModeComboBoxRenderer extends ErroneousLabel implements ListCellRenderer<ButtonsDisplayMode> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6849598632261042849L;

	private static final ImageIcon[] images;
	
	static {
		// Create the combo box.
		images = new ImageIcon[ButtonsDisplayMode.values().length];
		int i = 0;
		for (ButtonsDisplayMode buttonsDisplayMode : ButtonsDisplayMode.values()) {
			images[i] = buttonsDisplayMode.getIcon();
			if (images[i] != null)
				images[i].setDescription(buttonsDisplayMode.name());
			i += 1;
		}
	}

	private final ListCellRenderer<ButtonsDisplayMode> listCellRenderer;
	
	public ButtonsDisplayModeComboBoxRenderer(ListCellRenderer<ButtonsDisplayMode> listCellRenderer) {
		this.listCellRenderer = listCellRenderer;
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends ButtonsDisplayMode> list, ButtonsDisplayMode buttonsDisplayMode, int index, boolean isSelected, boolean cellHasFocus) {
		JComponent component = (JComponent) listCellRenderer.getListCellRendererComponent(list, buttonsDisplayMode, index, isSelected, cellHasFocus);
		if (component instanceof JLabel) {
			// Set the icon
			ImageIcon icon = images[buttonsDisplayMode.ordinal()];
			((JLabel) component).setIcon(icon);
			// Set the text
			// Replace text by error message when icon is null
			ResourceBundle UITexts = ResourceBundle.getBundle("i18n/UITexts");
			String text = UITexts.getString(ButtonsDisplayMode.values()[buttonsDisplayMode.ordinal()].getLabelKey());
			if (icon != null)
				((JLabel) component).setText(text);
			else
				setUhOhText((JLabel) component, String.format(UITexts.getString("errorComboBoxNoImageAvailable"), text), list.getFont());
		}
		return component;
	}


}
