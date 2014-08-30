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

import commons.enums.SteamFriendsSortMethod;
import components.labels.ErroneousLabel;

/**
 * @author Naeregwen
 *
 */
public class SteamFriendsSortMethodComboBoxRenderer extends ErroneousLabel implements ListCellRenderer<SteamFriendsSortMethod> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2931533878016143071L;

	private static final ImageIcon[] images;
	
	static {
		// Create the combo box.
		images = new ImageIcon[SteamFriendsSortMethod.values().length];
		int i = 0;
		for (SteamFriendsSortMethod steamFriendsSortMethod : SteamFriendsSortMethod.values()) {
			images[i] = createImageIcon(steamFriendsSortMethod.getIconPath());
			if (images[i] != null)
				images[i].setDescription(steamFriendsSortMethod.name());
			i += 1;
		}
	}

	private final ListCellRenderer<SteamFriendsSortMethod> listCellRenderer;
	
	public SteamFriendsSortMethodComboBoxRenderer(ListCellRenderer<SteamFriendsSortMethod> listCellRenderer) {
		this.listCellRenderer = listCellRenderer;
	}
	
	/*/
	 * (non-Javadoc)
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 * 
	 * This method finds the image and text corresponding to the selected
	 * value and returns the label, set up to display the text and image.
	 */
	@Override
	public Component getListCellRendererComponent(JList<? extends SteamFriendsSortMethod> list, SteamFriendsSortMethod selectedMethod, int index, boolean isSelected, boolean cellHasFocus) {
		JComponent component = (JComponent) listCellRenderer.getListCellRendererComponent(list, selectedMethod, index, isSelected, cellHasFocus);
		if (component instanceof JLabel) {
			// Set the icon
			ImageIcon icon = images[selectedMethod.ordinal()];
			((JLabel) component).setIcon(icon);
			// Set the text
			// Replace text by error message when icon is null
			ResourceBundle UITexts = ResourceBundle.getBundle("i18n/UITexts");
			String text = UITexts.getString(SteamFriendsSortMethod.values()[selectedMethod.ordinal()].getLabelKey());
			if (icon != null)
				((JLabel) component).setText(text);
			else
				setUhOhText((JLabel) component, String.format(UITexts.getString("errorComboBoxNoImageAvailable"), text), list.getFont());
		}
		return component;
	}

}
