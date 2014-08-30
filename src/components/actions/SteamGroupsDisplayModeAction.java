/**
 * 
 */
package components.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import commons.BundleManager;
import commons.enums.SteamGroupsDisplayMode;
import components.GamesLibrarian;
import components.GamesLibrarian.WindowBuilderMask;

/**
 * @author Naeregwen
 *
 */
public class SteamGroupsDisplayModeAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7449451714540720900L;

	WindowBuilderMask me;
	SteamGroupsDisplayMode steamGroupsDisplayMode;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public SteamGroupsDisplayModeAction(WindowBuilderMask me, SteamGroupsDisplayMode steamGroupsDisplayMode) {
		this.me = me;
		this.steamGroupsDisplayMode = steamGroupsDisplayMode;
		translate();
	}

	/**
	 * @return the steamGroupsDisplayMode
	 */
	public SteamGroupsDisplayMode getSteamGroupsDisplayMode() {
		return steamGroupsDisplayMode;
	}

	/**
	 * Update ToolTip
	 */
	public void setTooltip() {
		putValue(SHORT_DESCRIPTION, me != null && me.getLibrarian() != null ? 
				String.format(BundleManager.getUITexts(me, "steamGroupsDisplayModeTooltip"), 
						BundleManager.getUITexts(me,me.getLibrarian().getParameters().getSteamGroupsDisplayMode().getLabelKey())) : "");
	}
	
	/**
	 * Translate using BundleManager
	 */
	public void translate() {
		// Defensive code to avoid NullPointerException in WindowBuilder when data are empty in bundle (Mnemonic and accelerator are not mandatory)
		if (BundleManager.getUITexts(me, steamGroupsDisplayMode.getMnemonicKey()) != null && !BundleManager.getUITexts(me, steamGroupsDisplayMode.getMnemonicKey()).equals("")) // WindowBuilder
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, steamGroupsDisplayMode.getMnemonicKey())).getKeyCode());
		if (BundleManager.getUITexts(me, steamGroupsDisplayMode.getAcceleratorKey()) != null && !BundleManager.getUITexts(me, steamGroupsDisplayMode.getAcceleratorKey()).equals("")) // WindowBuilder
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, steamGroupsDisplayMode.getAcceleratorKey())));
		putValue(NAME, BundleManager.getUITexts(me, steamGroupsDisplayMode.getLabelKey()));
		putValue(SMALL_ICON, new ImageIcon(GamesLibrarian.class.getResource(steamGroupsDisplayMode.getIconPath())));
		setTooltip();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		me.getLibrarian().updateSteamGroupsDisplayMode(steamGroupsDisplayMode);
	}

}
