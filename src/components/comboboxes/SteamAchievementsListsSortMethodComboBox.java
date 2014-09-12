/**
 * 
 */
package components.comboboxes;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.ListCellRenderer;

import commons.enums.LibrarianTabEnum;
import commons.enums.SteamAchievementsListsSortMethod;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.comboboxes.renderers.SteamAchievementsListSortMethodComboBoxRenderer;

/**
 * @author Naeregwen
 *
 */
public class SteamAchievementsListsSortMethodComboBox extends JComboBox<SteamAchievementsListsSortMethod> implements ItemListener {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5295790922474873666L;

	Librarian librarian;
	
	@SuppressWarnings("unchecked")
	public SteamAchievementsListsSortMethodComboBox(WindowBuilderMask me) {
		super(SteamAchievementsListsSortMethod.values());
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		setRenderer(new SteamAchievementsListSortMethodComboBoxRenderer((ListCellRenderer<SteamAchievementsListsSortMethod>) this.getRenderer()));
		addItemListener(this);
	}
	
	/**
	 * (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(ItemEvent itemEvent) {
		librarian.displayMainTab(LibrarianTabEnum.Game);
		if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
			librarian.sortSteamAchievementsList((SteamAchievementsListsSortMethod) itemEvent.getItem());
			librarian.updateSteamAchievementsColumnsSortMethodTooltips();
		}
	}

}
