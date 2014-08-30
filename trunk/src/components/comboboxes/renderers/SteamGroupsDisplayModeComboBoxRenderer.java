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

import commons.enums.SteamGroupsDisplayMode;
import components.labels.ErroneousLabel;

/**
 * @author Naeregwen
 *
 */
public class SteamGroupsDisplayModeComboBoxRenderer extends ErroneousLabel implements ListCellRenderer<SteamGroupsDisplayMode> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4651346200209305661L;

	private static ImageIcon[] images;
	
	static {
		// Create the combo box.
		images = new ImageIcon[SteamGroupsDisplayMode.values().length];
		int i = 0;
		for (SteamGroupsDisplayMode steamGroupsDisplayMode : SteamGroupsDisplayMode.values()) {
			images[i] = createImageIcon(steamGroupsDisplayMode.getIconPath());
			if (images[i] != null)
				images[i].setDescription(steamGroupsDisplayMode.name());
			i += 1;
		}
	}

	private final ListCellRenderer<SteamGroupsDisplayMode> listCellRenderer;
	
	public SteamGroupsDisplayModeComboBoxRenderer(ListCellRenderer<SteamGroupsDisplayMode> listCellRenderer) {
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
	public Component getListCellRendererComponent(JList<? extends SteamGroupsDisplayMode> list, SteamGroupsDisplayMode displayMode, int index, boolean isSelected, boolean cellHasFocus) {
		JComponent component = (JComponent) listCellRenderer.getListCellRendererComponent(list, displayMode, index, isSelected, cellHasFocus);
		if (component instanceof JLabel) {
			// Set the icon
			ImageIcon icon = images[displayMode.ordinal()];
			((JLabel) component).setIcon(icon);
			// Set the text
			// Replace text by error message when icon is null
			ResourceBundle UITexts = ResourceBundle.getBundle("i18n/UITexts");
			String text = UITexts.getString(SteamGroupsDisplayMode.values()[displayMode.ordinal()].getLabelKey());
			if (icon != null)
				((JLabel) component).setText(text);
			else
				setUhOhText((JLabel) component, String.format(UITexts.getString("errorComboBoxNoImageAvailable"), text), list.getFont());
		}
		return component;
	}

}
