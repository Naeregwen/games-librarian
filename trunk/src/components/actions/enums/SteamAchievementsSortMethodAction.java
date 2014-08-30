/**
 * 
 */
package components.actions.enums;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import commons.BundleManager;
import commons.enums.SteamAchievementsSortMethod;
import components.GamesLibrarian;
import components.GamesLibrarian.WindowBuilderMask;
import components.actions.interfaces.EnumAction;

/**
 * @author Naeregwen
 *
 */
public class SteamAchievementsSortMethodAction extends AbstractAction implements EnumAction<SteamAchievementsSortMethod> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3400334735104641987L;

	WindowBuilderMask me;
	SteamAchievementsSortMethod steamAchievementsSortMethod;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public SteamAchievementsSortMethodAction(WindowBuilderMask me, SteamAchievementsSortMethod steamAchievementsSortMethod) {
		this.me = me;
		this.steamAchievementsSortMethod = steamAchievementsSortMethod;
		translate();
	}

	/**
	 * Translate using BundleManager
	 */
	public void translate() {
		putValue(NAME, BundleManager.getUITexts(me, steamAchievementsSortMethod.getLabelKey()));
		putValue(SMALL_ICON, new ImageIcon(GamesLibrarian.class.getResource(steamAchievementsSortMethod.getIconPath())));
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {}

	@Override
	public SteamAchievementsSortMethod getCurrentItem() {
		return steamAchievementsSortMethod;
	}

}
