/**
 * 
 */
package components.actions.enums;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import commons.BundleManager;
import commons.enums.SteamFriendsSortMethod;
import components.GamesLibrarian;
import components.GamesLibrarian.WindowBuilderMask;
import components.actions.interfaces.EnumAction;

/**
 * @author Naeregwen
 *
 */
public class SteamFriendsSortMethodAction extends AbstractAction implements EnumAction<SteamFriendsSortMethod> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1762321151814627032L;

	WindowBuilderMask me;
	SteamFriendsSortMethod steamFriendsSortMethod;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public SteamFriendsSortMethodAction(WindowBuilderMask me, SteamFriendsSortMethod steamFriendsSortMethod) {
		this.me = me;
		this.steamFriendsSortMethod = steamFriendsSortMethod;
		translate();
	}

	/**
	 * Translate using BundleManager
	 */
	public void translate() {
		putValue(NAME, BundleManager.getUITexts(me, steamFriendsSortMethod.getLabelKey()));
		putValue(SMALL_ICON, new ImageIcon(GamesLibrarian.class.getResource(steamFriendsSortMethod.getIconPath())));
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {}

	@Override
	public SteamFriendsSortMethod getCurrentItem() {
		return steamFriendsSortMethod;
	}

}
