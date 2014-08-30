/**
 * 
 */
package components.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import commons.BundleManager;
import commons.GamesLibrary;
import components.GamesLibrarian.WindowBuilderMask;

/**
 * @author Naeregwen
 *
 */
public class RefreshGamesListAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4487971627334029038L;

	WindowBuilderMask me;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public RefreshGamesListAction(WindowBuilderMask me) {
		this.me = me;
		translate();
	}

	/**
	 * Translate using BundleManager
	 */
	public void translate() {
		// Defensive code to avoid NullPointerException in WindowBuilder when data are empty in bundle (Mnemonic and accelerator are not mandatory)
		if (BundleManager.getUITexts(me, "refreshGamesListMnemonic") != null && !BundleManager.getUITexts(me, "refreshGamesListMnemonic").equals("")) // WindowBuilder
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "refreshGamesListMnemonic")).getKeyCode());
		if (BundleManager.getUITexts(me, "refreshGamesListAccelerator") != null && !BundleManager.getUITexts(me, "refreshGamesListAccelerator").equals("")) // WindowBuilder
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "refreshGamesListAccelerator")));
		putValue(NAME, BundleManager.getUITexts(me, "refreshGamesListMenuLabel"));
		putValue(SMALL_ICON, GamesLibrary.refreshGamesListIcon);
		putValue(SHORT_DESCRIPTION, BundleManager.getUITexts(me, "refreshGamesListToolTip"));
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		me.getLibrarian().getRollAction().forceRead();
	}

}
