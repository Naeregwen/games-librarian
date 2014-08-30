/**
 * 
 */
package components.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import commons.BundleManager;
import commons.GamesLibrary;
import commons.api.Parameters;
import components.GamesLibrarian.WindowBuilderMask;

/**
 * @author Naeregwen
 *
 */
public class ScrollLockAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3576233444504865502L;

	WindowBuilderMask me;
	
	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public ScrollLockAction(WindowBuilderMask me) {
		this.me = me;
		translate();
	}
	
	/**
	 * Translate using BundleManager
	 */
	public void translate() {
		// Defensive code to avoid NullPointerException in WindowBuilder when data are empty in bundle (Mnemonic and accelerator are not mandatory)
		if (BundleManager.getUITexts(me, "scrollLockMnemonic") != null && !BundleManager.getUITexts(me, "scrollLockMnemonic").equals("")) // WindowBuilder
			putValue(MNEMONIC_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "scrollLockMnemonic")).getKeyCode());
		if (BundleManager.getUITexts(me, "scrollLockAccelerator") != null && !BundleManager.getUITexts(me, "scrollLockAccelerator").equals("")) // WindowBuilder
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "scrollLockAccelerator")));
		if (me == null || me.getLibrarian() == null || !me.getLibrarian().getParameters().isScrollLocked()) { // WindowBuilder
			putValue(NAME, BundleManager.getUITexts(me, "scrollLockStarMenutLabel"));
			putValue(SMALL_ICON, GamesLibrary.lockStartIcon);
			putValue(SHORT_DESCRIPTION, BundleManager.getUITexts(me, "scrollLockStartToolTip"));
		} else {
			putValue(NAME, BundleManager.getUITexts(me, "scrollLockStopMenuLabel"));
			putValue(SMALL_ICON, GamesLibrary.lockStopIcon);
			putValue(SHORT_DESCRIPTION, BundleManager.getUITexts(me, "scrollLockStopToolTip"));
		}
	}
	
	/**
	 * Restore action properties according to parameters
	 */
	public void restore() {
		translate();
		Parameters parameters = me.getLibrarian().getParameters();
		putValue(NAME, parameters.isScrollLocked() ? BundleManager.getUITexts(me, "scrollLockStopMenuLabel") : BundleManager.getUITexts(me, "scrollLockStarMenutLabel"));
		putValue(SMALL_ICON, parameters.isScrollLocked() ? GamesLibrary.lockStopIcon : GamesLibrary.lockStartIcon);
		putValue(SHORT_DESCRIPTION, parameters.isScrollLocked() ? 
				BundleManager.getUITexts(me, "scrollLockStopToolTip") : 
					BundleManager.getUITexts(me, "scrollLockStartToolTip"));
		me.getLibrarian().getTee().writelnMessage(parameters.isScrollLocked() ? 
				BundleManager.getMessages(me, "lockStopMessage") : 
					BundleManager.getMessages(me, "lockStartMessage"));
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Parameters parameters = me.getLibrarian().getParameters();
		parameters.setScrollLocked(!parameters.isScrollLocked());
		restore();
	}

}
