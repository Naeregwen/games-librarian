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
			((JLabel) component).setText(steamProfile != null && steamProfile.getId() != null ? Jsoup.parseBodyFragment(steamProfile.getId()).body().text() : "");
		}
		return component;
	}

}
