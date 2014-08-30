/**
 * 
 */
package components.comboboxes;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.ListCellRenderer;

import commons.enums.SteamAchievementsSortMethod;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.comboboxes.observers.SteamAchievementsSortMethodObserver;
import components.comboboxes.renderers.SteamAchievementsSortMethodComboBoxRenderer;

/**
 * @author Naeregwen
 *
 */
public class SteamAchievementsSortMethodComboBox extends JComboBox<SteamAchievementsSortMethod> implements ItemListener, SteamAchievementsSortMethodObserver {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1503901705561301531L;

	Librarian librarian;
	
	@SuppressWarnings("unchecked")
	public SteamAchievementsSortMethodComboBox(WindowBuilderMask me) {
		super(SteamAchievementsSortMethod.values());
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		setRenderer(new SteamAchievementsSortMethodComboBoxRenderer((ListCellRenderer<SteamAchievementsSortMethod>) this.getRenderer()));
		if (librarian != null) // WindowBuilder
			librarian.addSteamAchievementsSortMethodObserver(this);
		addItemListener(this);
	}
	
	/*
	 * (non-Javadoc)
	 * @see components.comboboxes.observers.SteamAchievementsSortMethodObserver#update()
	 */
	@Override
	public void update() {
		setSelectedItem(librarian.getSteamAchievementsSortMethod());
	}

	/**
	 * (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(ItemEvent itemEvent) {
		if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
			librarian.sortSteamAchievements((SteamAchievementsSortMethod) itemEvent.getItem());
			librarian.updateSteamAchievementsSortMethodTooltips();
		}
	}

}
