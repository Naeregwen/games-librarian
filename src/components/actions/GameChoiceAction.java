/**
 * 
 */
package components.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import commons.BundleManager;
import commons.api.Parameters;
import commons.enums.GameChoice;
import commons.enums.LibrarianTabEnum;
import components.Librarian;
import components.GamesLibrarian.WindowBuilderMask;

/**
 * @author Naeregwen
 *
 */
public class GameChoiceAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7585072116154627162L;

	WindowBuilderMask me;
	GameChoice gameChoice;
	
	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 * @param gameChoice the binded GameChoice
	 */
	public GameChoiceAction(WindowBuilderMask me, GameChoice gameChoice) {
		this.me = me;
		this.gameChoice = gameChoice;
		translate();
	}
	
	/**
	 * Translate using BundleManager
	 */
	public void translate() {
		// Defensive code to avoid NullPointerException in WindowBuilder when data are empty in bundle (Mnemonic and accelerator are not mandatory)
		if (BundleManager.getUITexts(me, gameChoice.getMnemonicKey()) != null && !BundleManager.getUITexts(me, gameChoice.getMnemonicKey()).equals("")) // WindowBuilder
			putValue(MNEMONIC_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, gameChoice.getMnemonicKey())).getKeyCode());
		if (BundleManager.getUITexts(me, gameChoice.getAcceleratorKey()) != null && !BundleManager.getUITexts(me, gameChoice.getAcceleratorKey()).equals("")) // WindowBuilder
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, gameChoice.getAcceleratorKey())));
		putValue(NAME, BundleManager.getUITexts(me, gameChoice.getLabelKey()));
		putValue(SMALL_ICON, gameChoice.getIcon());
		putValue(SHORT_DESCRIPTION, BundleManager.getUITexts(me, gameChoice.getToolTipKey()));
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Librarian librarian =  me.getLibrarian();
		Parameters parameters = librarian.getParameters();
		librarian.displayMainTab(LibrarianTabEnum.Controls);
		librarian.displayLaunchButtons(gameChoice);
		if (parameters.isDebug()) 
			librarian.getTee().writelnInfos(String.format(parameters.getMessages().getString("gameChoiceChanged"), gameChoice.ordinal()+1));
	}

}
