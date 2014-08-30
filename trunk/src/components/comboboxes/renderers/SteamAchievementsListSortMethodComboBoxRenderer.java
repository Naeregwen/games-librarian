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

import commons.enums.SteamAchievementsListsSortMethod;
import components.labels.ErroneousLabel;

/**
 * @author Naeregwen
 *
 */
public class SteamAchievementsListSortMethodComboBoxRenderer extends ErroneousLabel implements ListCellRenderer<SteamAchievementsListsSortMethod> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3312107646614484391L;

	private static final ImageIcon[] images;
	
	static {
		// Create the combo box.
		images = new ImageIcon[SteamAchievementsListsSortMethod.values().length];
		int i = 0;
		for (SteamAchievementsListsSortMethod steamAchievementsColumnsSortMethod : SteamAchievementsListsSortMethod.values()) {
			images[i] = createImageIcon(steamAchievementsColumnsSortMethod.getIconPath());
			if (images[i] != null)
				images[i].setDescription(steamAchievementsColumnsSortMethod.name());
			i += 1;
		}
	}

	private final ListCellRenderer<SteamAchievementsListsSortMethod> listCellRenderer;
	
	public SteamAchievementsListSortMethodComboBoxRenderer(ListCellRenderer<SteamAchievementsListsSortMethod> listCellRenderer) {
		this.listCellRenderer = listCellRenderer;
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends SteamAchievementsListsSortMethod> list, SteamAchievementsListsSortMethod selectedMethod, int index, boolean isSelected, boolean cellHasFocus) {
		JComponent component = (JComponent) listCellRenderer.getListCellRendererComponent(list, selectedMethod, index, isSelected, cellHasFocus);
		if (component instanceof JLabel) {
			// Set the icon
			ImageIcon icon = images[selectedMethod.ordinal()];
			((JLabel) component).setIcon(icon);
			// Set the text
			// Replace text by error message when icon is null
			ResourceBundle UITexts = ResourceBundle.getBundle("i18n/UITexts");
			String text = UITexts.getString(SteamAchievementsListsSortMethod.values()[selectedMethod.ordinal()].getLabelKey());
			if (icon != null)
				((JLabel) component).setText(text);
			else
				setUhOhText((JLabel) component, String.format(UITexts.getString("errorComboBoxNoImageAvailable"), text), list.getFont());
		}
		return component;
	}

}
