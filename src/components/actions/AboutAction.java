/**
 * 
 */
package components.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import commons.BundleManager;
import commons.GamesLibrary;
import components.GamesLibrarian.WindowBuilderMask;

/**
 * @author Naeregwen
 * 
 */
public class AboutAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5530142826221677715L;
	
	WindowBuilderMask me;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public AboutAction(WindowBuilderMask me) {
		this.me = me;
		translate();
	}

	/**
	 * Translate using BundleManager
	 */
	public void translate() {
		// Defensive code to avoid NullPointerException in WindowBuilder when data are empty in bundle (Mnemonic and accelerator are not mandatory)
		if (BundleManager.getUITexts(me, "aboutMnemonic") != null && !BundleManager.getUITexts(me, "aboutMnemonic").equals("")) // WindowBuilder
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "aboutMnemonic")).getKeyCode());
		if (BundleManager.getUITexts(me, "aboutAccelerator") != null && !BundleManager.getUITexts(me, "aboutAccelerator").equals("")) // WindowBuilder
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "aboutAccelerator")));
		putValue(NAME, BundleManager.getUITexts(me, "aboutMenuLabel"));
		putValue(SMALL_ICON, GamesLibrary.aboutIcon);
		putValue(SHORT_DESCRIPTION, BundleManager.getUITexts(me, "aboutToolTip"));
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(me != null ? me.getLibrarian().getView() : null, // WindowBuilder
			"<html>" 
			+ me.getLibrarian().getApplicationTitle() + " " + BundleManager.getUITexts(me, "by") + " " + "Naeregwen."
			+ "<br/><br/>"
			+ BundleManager.getUITexts(me, "aboutDescriptionMessage")
			+ "<br/><br/>"
			+ BundleManager.getUITexts(me, "aboutSilkIconsMessage")
			+ "</html>",
			BundleManager.getUITexts(me, "aboutTitle"),
			JOptionPane.INFORMATION_MESSAGE);
	}

}
