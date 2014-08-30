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

import commons.api.SteamLaunchMethod;
import components.labels.ErroneousLabel;


/**
 * @author Naeregwen
 *
 */
public class SteamLaunchMethodComboBoxRenderer extends ErroneousLabel implements ListCellRenderer<SteamLaunchMethod> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6891516350846953999L;

	private static ImageIcon[] images;
	
	static {
		// Create the combo box.
		images = new ImageIcon[SteamLaunchMethod.values().length];
		int i = 0;
		for (SteamLaunchMethod steamLaunchMethod : SteamLaunchMethod.values()) {
			images[i] = createImageIcon(steamLaunchMethod.getIconPath());
			if (images[i] != null)
				images[i].setDescription(steamLaunchMethod.name());
			i += 1;
		}
	}

	private final ListCellRenderer<SteamLaunchMethod> listCellRenderer;
	
	public SteamLaunchMethodComboBoxRenderer(ListCellRenderer<SteamLaunchMethod> listCellRenderer) {
		this.listCellRenderer = listCellRenderer;
	}
	
	/*/
	 * (non-Javadoc)
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 * 
	 * This method finds the image and text corresponding to the selected
	 * value and returns the label, set up to display the text and image.
	 */
	public Component getListCellRendererComponent(JList<? extends SteamLaunchMethod> list, SteamLaunchMethod selectedMethod, int index, boolean isSelected, boolean cellHasFocus) {
		JComponent component = (JComponent) listCellRenderer.getListCellRendererComponent(list, selectedMethod, index, isSelected, cellHasFocus);
		if (component instanceof JLabel) {
			// Set the icon
			ImageIcon icon = images[selectedMethod.ordinal()];
			((JLabel) component).setIcon(icon);
			// Set the text
			// Replace text by error message when icon is null
			ResourceBundle UITexts = ResourceBundle.getBundle("i18n/UITexts");
			String text = UITexts.getString(SteamLaunchMethod.values()[selectedMethod.ordinal()].getLabelKey());
			if (icon != null)
				((JLabel) component).setText(text);
			else
				setUhOhText((JLabel) component, String.format(UITexts.getString("errorComboBoxNoImageAvailable"), text), list.getFont());
		}
		return component;
	}

}
