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

import commons.enums.SteamGamesDisplayMode;
import components.labels.ErroneousLabel;

/**
 * @author Naeregwen
 *
 */
public class SteamGamesDisplayModeComboBoxRenderer extends ErroneousLabel implements ListCellRenderer<SteamGamesDisplayMode> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6961514804538336392L;

	private static final ImageIcon[] images;
	
	static {
		// Create the combo box.
		images = new ImageIcon[SteamGamesDisplayMode.values().length];
		int i = 0;
		for (SteamGamesDisplayMode steamGamesDisplayMode : SteamGamesDisplayMode.values()) {
			images[i] = createImageIcon(steamGamesDisplayMode.getIconPath());
			if (images[i] != null)
				images[i].setDescription(steamGamesDisplayMode.name());
			i += 1;
		}
	}

	private final ListCellRenderer<SteamGamesDisplayMode> listCellRenderer;
	
	public SteamGamesDisplayModeComboBoxRenderer(ListCellRenderer<SteamGamesDisplayMode> listCellRenderer) {
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
	public Component getListCellRendererComponent(JList<? extends SteamGamesDisplayMode> list, SteamGamesDisplayMode selectedDisplayMode, int index, boolean isSelected, boolean cellHasFocus) {
		JComponent component = (JComponent) listCellRenderer.getListCellRendererComponent(list, selectedDisplayMode, index, isSelected, cellHasFocus);
		if (component instanceof JLabel) {
			// Set the icon and text. 
			ImageIcon icon = images[selectedDisplayMode.ordinal()];
			((JLabel) component).setIcon(icon);
			// Set the text
			// Replace text by error message when icon is null
			ResourceBundle UITexts = ResourceBundle.getBundle("i18n/UITexts");
			String text = UITexts.getString(SteamGamesDisplayMode.values()[selectedDisplayMode.ordinal()].getLabelKey());
			if (icon != null)
				((JLabel) component).setText(text);
			else {
				setUhOhText((JLabel) component, String.format(UITexts.getString("errorComboBoxNoImageAvailable"), text), list.getFont());
			}
		}
		return component;
	}

}
