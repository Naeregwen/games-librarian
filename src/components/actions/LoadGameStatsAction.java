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
import commons.enums.LibrarianTabEnum;
import components.Librarian;
import components.GamesLibrarian.WindowBuilderMask;
import components.actions.interfaces.IconAndTextAction;

/**
 * @author Naeregwen
 *
 */
public class LoadGameStatsAction extends AbstractAction implements IconAndTextAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4576700534942875559L;

	WindowBuilderMask me;
	Librarian librarian;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public LoadGameStatsAction(WindowBuilderMask me) {
		this.me = me;
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		translate();
	}

	/**
	 * Translate using BundleManager
	 */
	public void translate() {
		// Defensive code to avoid NullPointerException in WindowBuilder when data are empty in bundle (Mnemonic and accelerator are not mandatory)
		if (BundleManager.getUITexts(me, "loadGameStatsMnemonic") != null && !BundleManager.getUITexts(me, "loadGameStatsMnemonic").equals("")) // WindowBuilder
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "loadGameStatsMnemonic")).getKeyCode());
		if (BundleManager.getUITexts(me, "loadGameStatsAccelerator") != null && !BundleManager.getUITexts(me, "loadGameStatsAccelerator").equals("")) // WindowBuilder
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "loadGameStatsAccelerator")));
		putValue(NAME, BundleManager.getUITexts(me, "loadGameStatsMenuLabel"));
		putValue(SMALL_ICON, GamesLibrary.steamIcon);
		putValue(SHORT_DESCRIPTION, BundleManager.getUITexts(me, "loadGameStatsTooltip"));
	}
	
	@Override
	public String getLabelKey() {
		if (librarian.getParameters().getButtonsDisplayMode().equals(ButtonsDisplayMode.Icon))
			return null;
		return "loadGameStatsMenuLabel";
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
		librarian.displayMainTab(LibrarianTabEnum.Game);
		me.getLibrarian().readSteamGameStats();
	}

}
