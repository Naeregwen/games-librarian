/**
 * 
 */
package components.actions.enums;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import commons.BundleManager;
import commons.enums.SteamAchievementsListsSortMethod;
import components.GamesLibrarian;
import components.GamesLibrarian.WindowBuilderMask;
import components.actions.interfaces.EnumAction;

/**
 * @author Naeregwen
 *
 */
public class SteamAchievementsListsSortMethodAction extends AbstractAction implements EnumAction<SteamAchievementsListsSortMethod> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7694709428361055363L;

	WindowBuilderMask me;
	SteamAchievementsListsSortMethod steamAchievementsListsSortMethod;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public SteamAchievementsListsSortMethodAction(WindowBuilderMask me, SteamAchievementsListsSortMethod steamAchievementsListsSortMethod) {
		this.me = me;
		this.steamAchievementsListsSortMethod = steamAchievementsListsSortMethod;
		translate();
	}

	/**
	 * Translate using BundleManager
	 */
	public void translate() {
		putValue(NAME, BundleManager.getUITexts(me, steamAchievementsListsSortMethod.getLabelKey()));
		putValue(SMALL_ICON, new ImageIcon(GamesLibrarian.class.getResource(steamAchievementsListsSortMethod.getIconPath())));
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {}

	@Override
	public SteamAchievementsListsSortMethod getCurrentItem() {
		return steamAchievementsListsSortMethod;
	}

}
