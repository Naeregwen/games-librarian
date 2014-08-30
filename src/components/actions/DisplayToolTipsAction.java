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
public class DisplayToolTipsAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1302582245153154899L;

	WindowBuilderMask me;
	Boolean displayToolTips;
	
	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public DisplayToolTipsAction(WindowBuilderMask me, Boolean displayToolTips) {
		this.me = me;
		this.displayToolTips = displayToolTips;
		translate();
	}

	/**
	 * Translate using BundleManager
	 */
	public void translate() {
		// Defensive code to avoid NullPointerException in WindowBuilder when data are empty in bundle (Mnemonic and accelerator are not mandatory)
		String displayToolTipsMnemonic = displayToolTips ? "displayToolTipsYesMnemonic" : "displayToolTipsNoMnemonic";
		String displayToolTipsAccelerator = displayToolTips ? "displayToolTipsYesAccelerator" : "displayToolTipsNoAccelerator";
		String displayToolTipName = displayToolTips ? "yes" : "no";
		if (BundleManager.getUITexts(me, displayToolTipsMnemonic) != null && !BundleManager.getUITexts(me, displayToolTipsMnemonic).equals("")) // WindowBuilder
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, displayToolTipsMnemonic)).getKeyCode());
		if (BundleManager.getUITexts(me, displayToolTipsAccelerator) != null && !BundleManager.getUITexts(me, displayToolTipsAccelerator).equals("")) // WindowBuilder
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, displayToolTipsAccelerator)));
		putValue(NAME, BundleManager.getUITexts(me, displayToolTipName));
		putValue(SMALL_ICON, displayToolTips ? GamesLibrary.displayToolTipsYesIcon : GamesLibrary.displayToolTipsNoIcon);
		putValue(SHORT_DESCRIPTION, BundleManager.getUITexts(me, "displayToolTipsToolTip"));
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		me.getLibrarian().updateDisplayTooltips(displayToolTips);
	}

}
