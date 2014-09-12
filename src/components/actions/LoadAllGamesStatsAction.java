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
import commons.GamesLibrary;
import commons.enums.ButtonsDisplayMode;
import commons.enums.LibraryTabEnum;
import components.Librarian;
import components.GamesLibrarian.WindowBuilderMask;
import components.actions.interfaces.IconAndTextAction;

/**
 * @author Naeregwen
 *
 */
public class LoadAllGamesStatsAction extends AbstractAction implements IconAndTextAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1955853328167022526L;

	WindowBuilderMask me;
	Librarian librarian;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public LoadAllGamesStatsAction(WindowBuilderMask me) {
		this.me = me;
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		translate();
	}

	/**
	 * Translate using the BundleManager
	 */
	public void translate() {
		// Defensive code to avoid NullPointerException in WindowBuilder/Runtime when data are empty in bundle (Mnemonic and accelerator are not mandatory)
		if (BundleManager.getUITexts(me, "loadAllGamesStatsMnemonic") != null && !BundleManager.getUITexts(me, "loadAllGamesStatsMnemonic").equals("")) // WindowBuilder
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "loadAllGamesStatsMnemonic")).getKeyCode());
		if (BundleManager.getUITexts(me, "loadAllGamesStatsAccelerator") != null && !BundleManager.getUITexts(me, "loadAllGamesStatsAccelerator").equals("")) // WindowBuilder
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "loadAllGamesStatsAccelerator")));
		putValue(NAME, BundleManager.getUITexts(me, "loadAllGamesStatsMenuLabel"));
		putValue(SMALL_ICON, GamesLibrary.steamIcon);
		putValue(SHORT_DESCRIPTION, BundleManager.getUITexts(me, "loadAllGamesStatsTooltip"));
	}
	
	@Override
	public String getLabelKey() {
		if (librarian.getParameters().getButtonsDisplayMode().equals(ButtonsDisplayMode.Icon))
			return null;
		return "loadAllGamesStatsMenuLabel";
	}

	@Override
	public ImageIcon getIcon() {
		if (librarian.getParameters().getButtonsDisplayMode().equals(ButtonsDisplayMode.Text))
			return null;
		return GamesLibrary.steamIcon;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		librarian.displaySubTab(LibraryTabEnum.LibraryStatistics);
		me.getLibrarian().readAllSteamGamesStats();
	}

}
