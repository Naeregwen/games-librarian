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
import commons.api.Parameters;
import components.GamesLibrarian.WindowBuilderMask;

/**
 * @author Naeregwen
 *
 */
public class DebugAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4416194683104365739L;

	WindowBuilderMask me;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public DebugAction(WindowBuilderMask me) {
		this.me = me;
		translate();
	}

	/**
	 * Translate using BundleManager
	 */
	public void translate() {
		// Defensive code to avoid NullPointerException in WindowBuilder when data are empty in bundle (Mnemonic and accelerator are not mandatory)
		if (BundleManager.getUITexts(me, "debugMnemonic") != null && !BundleManager.getUITexts(me, "debugMnemonic").equals("")) // WindowBuilder
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "debugMnemonic")).getKeyCode());
		if (BundleManager.getUITexts(me, "debugAccelerator") != null && !BundleManager.getUITexts(me, "debugAccelerator").equals("")) // WindowBuilder
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "debugAccelerator")));
		if (me == null || me.getLibrarian() == null || me.getLibrarian().getParameters().isDebug()) { // WindowBuilder 
			putValue(NAME, BundleManager.getUITexts(me, "debugOffMenuLabel"));
			putValue(SMALL_ICON, GamesLibrary.debugStopIcon);
			putValue(SHORT_DESCRIPTION, BundleManager.getUITexts(me, "debugOffToolTip"));
		} else {
			putValue(NAME, BundleManager.getUITexts(me, "debugOnMenuLabel"));
			putValue(SMALL_ICON, GamesLibrary.debugStartIcon);
			putValue(SHORT_DESCRIPTION, BundleManager.getUITexts(me, "debugOnToolTip"));
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Parameters parameters = me.getLibrarian().getParameters();
		parameters.setDebug(!parameters.isDebug());
		translate();
		if (me != null && me.getLibrarian() != null) // WindowBuilder 
			me.getLibrarian().getTee().writelnInfos(me.getLibrarian().getParameters().isDebug() ? 
				BundleManager.getMessages(me, "debugOnMessage") : 
					BundleManager.getMessages(me, "debugOffMessage"));
	}

}
