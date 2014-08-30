/**
 * 
 */
package components.actions.enums;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import commons.BundleManager;
import commons.enums.SteamGamesDisplayMode;
import components.GamesLibrarian;
import components.GamesLibrarian.WindowBuilderMask;
import components.actions.interfaces.EnumAction;

/**
 * @author Naeregwen
 *
 */
public class SteamGamesDisplayModeAction extends AbstractAction implements EnumAction<SteamGamesDisplayMode> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3276704701609360287L;

	WindowBuilderMask me;
	SteamGamesDisplayMode steamGamesDisplayMode;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public SteamGamesDisplayModeAction(WindowBuilderMask me, SteamGamesDisplayMode steamGamesDisplayMode) {
		this.me = me;
		this.steamGamesDisplayMode = steamGamesDisplayMode;
		translate();
	}

	/**
	 * Translate using BundleManager
	 */
	public void translate() {
		putValue(NAME, BundleManager.getUITexts(me, steamGamesDisplayMode.getLabelKey()));
		putValue(SMALL_ICON, new ImageIcon(GamesLibrarian.class.getResource(steamGamesDisplayMode.getIconPath())));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {}

	@Override
	public SteamGamesDisplayMode getCurrentItem() {
		return steamGamesDisplayMode;
	}

}
