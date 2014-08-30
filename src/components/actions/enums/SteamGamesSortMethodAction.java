/**
 * 
 */
package components.actions.enums;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import commons.BundleManager;
import commons.enums.SteamGamesSortMethod;
import components.GamesLibrarian;
import components.GamesLibrarian.WindowBuilderMask;
import components.actions.interfaces.EnumAction;

/**
 * @author Naeregwen
 *
 */
public class SteamGamesSortMethodAction extends AbstractAction implements EnumAction<SteamGamesSortMethod> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3475539144253951584L;

	WindowBuilderMask me;
	SteamGamesSortMethod steamGamesSortMethod;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public SteamGamesSortMethodAction(WindowBuilderMask me, SteamGamesSortMethod steamGamesSortMethod) {
		this.me = me;
		this.steamGamesSortMethod = steamGamesSortMethod;
		translate();
	}

	/**
	 * Translate using BundleManager
	 */
	public void translate() {
		putValue(NAME, BundleManager.getUITexts(me, steamGamesSortMethod.getLabelKey()));
		putValue(SMALL_ICON, new ImageIcon(GamesLibrarian.class.getResource(steamGamesSortMethod.getIconPath())));
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {}

	@Override
	public SteamGamesSortMethod getCurrentItem() {
		return steamGamesSortMethod;
	}

}
