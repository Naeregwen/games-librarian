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
public class ClearConsoleAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8931681856550892401L;

	WindowBuilderMask me;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public ClearConsoleAction(WindowBuilderMask me) {
		this.me = me;
		translate();
	}

	/**
	 * Translate using BundleManager
	 */
	public void translate() {
		// Defensive code to avoid NullPointerException in WindowBuilder when data are empty in bundle (Mnemonic and accelerator are not mandatory)
		if (BundleManager.getUITexts(me, "clearConsoleMnemonic") != null && !BundleManager.getUITexts(me, "clearConsoleMnemonic").equals("")) // WindowBuilder
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "clearConsoleMnemonic")).getKeyCode());
		if (BundleManager.getUITexts(me, "clearConsoleAccelerator") != null && !BundleManager.getUITexts(me, "clearConsoleAccelerator").equals("")) // WindowBuilder
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "clearConsoleAccelerator")));
		putValue(NAME, BundleManager.getUITexts(me, "clearConsoleMenuLabel"));
		putValue(SMALL_ICON, GamesLibrary.clearConsoleIcon);
		putValue(SHORT_DESCRIPTION, BundleManager.getUITexts(me, "clearConsoleToolTip"));
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		me.getLibrarian().getConsoleTextPane().setText("");
	}

}
