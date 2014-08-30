/**
 * 
 */
package components.actions.enums;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import commons.BundleManager;
import commons.enums.SteamGroupsSortMethod;
import components.GamesLibrarian;
import components.GamesLibrarian.WindowBuilderMask;
import components.actions.interfaces.EnumAction;

/**
 * @author Naeregwen
 *
 */
public class SteamGroupsSortMethodAction extends AbstractAction implements EnumAction<SteamGroupsSortMethod> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5509739286728380202L;

	WindowBuilderMask me;
	SteamGroupsSortMethod steamGroupsSortMethod;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public SteamGroupsSortMethodAction(WindowBuilderMask me, SteamGroupsSortMethod steamGroupsSortMethod) {
		this.me = me;
		this.steamGroupsSortMethod = steamGroupsSortMethod;
		translate();
	}

	/**
	 * Translate using BundleManager
	 */
	public void translate() {
		putValue(NAME, BundleManager.getUITexts(me, steamGroupsSortMethod.getLabelKey()));
		putValue(SMALL_ICON, new ImageIcon(GamesLibrarian.class.getResource(steamGroupsSortMethod.getIconPath())));
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {}

	@Override
	public SteamGroupsSortMethod getCurrentItem() {
		return steamGroupsSortMethod;
	}

}
