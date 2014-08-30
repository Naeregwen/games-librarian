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

import commons.enums.SteamGamesSortMethod;
import components.labels.ErroneousLabel;

/**
 * @author Naeregwen
 *
 */
public class SteamGamesSortMethodComboBoxRenderer extends ErroneousLabel implements ListCellRenderer<SteamGamesSortMethod> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6177701522340798711L;

	private static final ImageIcon[] images;
	
	static {
		// Create the combo box.
		images = new ImageIcon[SteamGamesSortMethod.values().length];
		int i = 0;
		for (SteamGamesSortMethod steamGamesSortMethod : SteamGamesSortMethod.values()) {
			images[i] = createImageIcon(steamGamesSortMethod.getIconPath());
			if (images[i] != null)
				images[i].setDescription(steamGamesSortMethod.name());
			i += 1;
		}
	}
	
	private final ListCellRenderer<SteamGamesSortMethod> listCellRenderer;
	
	public SteamGamesSortMethodComboBoxRenderer(ListCellRenderer<SteamGamesSortMethod> listCellRenderer) {
		this.listCellRenderer = listCellRenderer;
	}
	
	/*/
	 * (non-Javadoc)
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 * 
	 * This method finds the image and text corresponding to the selected
	 * value and returns the label, set up to display the text and image.
	 */
	public Component getListCellRendererComponent(JList<? extends SteamGamesSortMethod> list, SteamGamesSortMethod selectedMethod, int index, boolean isSelected, boolean cellHasFocus) {
		JComponent component = (JComponent) listCellRenderer.getListCellRendererComponent(list, selectedMethod, index, isSelected, cellHasFocus);
		if (component instanceof JLabel) {
			// Set the icon
			ImageIcon icon = images[selectedMethod.ordinal()];
			((JLabel) component).setIcon(icon);
			// Set the text
			// Replace text by error message when icon is null
			ResourceBundle UITexts = ResourceBundle.getBundle("i18n/UITexts");
			String text = UITexts.getString(SteamGamesSortMethod.values()[selectedMethod.ordinal()].getLabelKey());
			if (icon != null)
				((JLabel) component).setText(text);
			else
				setUhOhText((JLabel) component, String.format(UITexts.getString("errorComboBoxNoImageAvailable"), text), list.getFont());
		}
		return component;
	}
	
}
