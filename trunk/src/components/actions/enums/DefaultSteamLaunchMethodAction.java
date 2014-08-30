/**
 * 
 */
package components.actions.enums;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import commons.BundleManager;
import commons.api.SteamLaunchMethod;
import components.GamesLibrarian;
import components.GamesLibrarian.WindowBuilderMask;
import components.actions.interfaces.EnumAction;

/**
 * @author Naeregwen
 *
 */
public class DefaultSteamLaunchMethodAction extends AbstractAction implements EnumAction<SteamLaunchMethod> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8470378462316545406L;

	WindowBuilderMask me;
	SteamLaunchMethod steamLaunchMethod;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public DefaultSteamLaunchMethodAction(WindowBuilderMask me, SteamLaunchMethod steamLaunchMethod) {
		this.me = me;
		this.steamLaunchMethod = steamLaunchMethod;
		translate();
	}

	/**
	 * Translate using BundleManager
	 */
	public void translate() {
		putValue(NAME, BundleManager.getUITexts(me, steamLaunchMethod.getLabelKey()));
		putValue(SMALL_ICON,new ImageIcon(GamesLibrarian.class.getResource(steamLaunchMethod.getIconPath())));
		putValue(SHORT_DESCRIPTION, BundleManager.getUITexts(me, steamLaunchMethod.getToolTipKey()));
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {}

	@Override
	public SteamLaunchMethod getCurrentItem() {
		return steamLaunchMethod;
	}

}
