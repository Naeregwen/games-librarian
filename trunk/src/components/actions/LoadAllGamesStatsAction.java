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
public class LoadAllGamesStatsAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1955853328167022526L;

	WindowBuilderMask me;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public LoadAllGamesStatsAction(WindowBuilderMask me) {
		this.me = me;
		translate();
	}

	/**
	 * Translate using BundleManager
	 */
	public void translate() {
		// Defensive code to avoid NullPointerException in WindowBuilder when data are empty in bundle (Mnemonic and accelerator are not mandatory)
		if (BundleManager.getUITexts(me, "loadAllGamesStatsMnemonic") != null && !BundleManager.getUITexts(me, "loadAllGamesStatsMnemonic").equals("")) // WindowBuilder
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "loadAllGamesStatsMnemonic")).getKeyCode());
		if (BundleManager.getUITexts(me, "loadAllGamesStatsAccelerator") != null && !BundleManager.getUITexts(me, "loadAllGamesStatsAccelerator").equals("")) // WindowBuilder
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "loadAllGamesStatsAccelerator")));
		putValue(NAME, BundleManager.getUITexts(me, "loadAllGamesStatsMenuLabel"));
		putValue(SMALL_ICON, GamesLibrary.steamIcon);
		putValue(SHORT_DESCRIPTION, BundleManager.getUITexts(me, "loadAllGamesStatsTooltip"));
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		me.getLibrarian().readAllSteamGamesStats();
	}

}
