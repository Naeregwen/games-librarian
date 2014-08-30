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

import commons.enums.SteamAchievementsSortMethod;
import components.labels.ErroneousLabel;

/**
 * @author Naeregwen
 *
 */
public class SteamAchievementsSortMethodComboBoxRenderer extends ErroneousLabel implements ListCellRenderer<SteamAchievementsSortMethod> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4182933648943007580L;

	private static final ImageIcon[] images;
	
	static {
		// Create the combo box.
		images = new ImageIcon[SteamAchievementsSortMethod.values().length];
		int i = 0;
		for (SteamAchievementsSortMethod steamAchievementsSortMethod : SteamAchievementsSortMethod.values()) {
			images[i] = createImageIcon(steamAchievementsSortMethod.getIconPath());
			if (images[i] != null)
				images[i].setDescription(steamAchievementsSortMethod.name());
			i += 1;
		}
	}

	private final ListCellRenderer<SteamAchievementsSortMethod> listCellRenderer;
	
	public SteamAchievementsSortMethodComboBoxRenderer(ListCellRenderer<SteamAchievementsSortMethod> listCellRenderer) {
		this.listCellRenderer = listCellRenderer;
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends SteamAchievementsSortMethod> list, SteamAchievementsSortMethod selectedMethod, int index, boolean isSelected, boolean cellHasFocus) {
		JComponent component = (JComponent) listCellRenderer.getListCellRendererComponent(list, selectedMethod, index, isSelected, cellHasFocus);
		if (component instanceof JLabel) {
			// Set the icon
			ImageIcon icon = images[selectedMethod.ordinal()];
			((JLabel) component).setIcon(icon);
			// Set the text
			// Replace text by error message when icon is null
			ResourceBundle UITexts = ResourceBundle.getBundle("i18n/UITexts");
			String text = UITexts.getString(SteamAchievementsSortMethod.values()[selectedMethod.ordinal()].getLabelKey());
			if (icon != null)
				((JLabel) component).setText(text);
			else
				setUhOhText((JLabel) component, String.format(UITexts.getString("errorComboBoxNoImageAvailable"), text), list.getFont());
		}
		return component;
	}

}
