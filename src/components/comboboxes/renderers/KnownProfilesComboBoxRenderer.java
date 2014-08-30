/**
 * 
 */
package components.comboboxes.renderers;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.jsoup.Jsoup;

import commons.api.SteamProfile;
import components.labels.ErroneousLabel;

/**
 * @author Naeregwen
 *
 */
public class KnownProfilesComboBoxRenderer extends ErroneousLabel implements ListCellRenderer<SteamProfile> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6264219919721395348L;

	private final ListCellRenderer<SteamProfile> listCellRenderer;
	
	public KnownProfilesComboBoxRenderer(ListCellRenderer<SteamProfile> listCellRenderer) {
		this.listCellRenderer = listCellRenderer;
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends SteamProfile> list, SteamProfile steamProfile, int index, boolean isSelected, boolean cellHasFocus) {
		JComponent component = (JComponent) listCellRenderer.getListCellRendererComponent(list, steamProfile, index, isSelected, cellHasFocus);
		if (component instanceof JLabel && /* WindowBuilder */ steamProfile != null) {
			// Set the text
			((JLabel) component).setText(Jsoup.parseBodyFragment(steamProfile.getId()).body().text());
			// Set the icon
//			ImageIcon icon = images[selectedMethod.ordinal()];
//			((JLabel) component).setIcon(icon);
//			// Replace text by error message when icon is null
//			ResourceBundle UITexts = ResourceBundle.getBundle("i18n/UITexts");
//			String text = UITexts.getString(SteamGamesSortMethod.values()[selectedMethod.ordinal()].getOptionLabel());
//			if (icon != null)
//				((JLabel) component).setText(text);
//			else
//				setUhOhText((JLabel) component, String.format(UITexts.getString("errorComboBoxNoImageAvailable"), text), list.getFont());
//			
//			if (steamProfile != null && steamProfile.getId() != null)
//				setText(Jsoup.parseBodyFragment(steamProfile.getId()).body().text());
//			if (isSelected) {
//				setBackground(list.getSelectionBackground());
//				setForeground(list.getSelectionForeground());
//			} else {
//				setBackground(list.getBackground());
//				setForeground(list.getForeground());
//			}
		}
		return component;
	}

}
