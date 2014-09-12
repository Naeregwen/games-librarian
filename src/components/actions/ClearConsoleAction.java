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
public class ClearConsoleAction extends AbstractAction implements IconAndTextAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8931681856550892401L;

	WindowBuilderMask me;
	Librarian librarian;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public ClearConsoleAction(WindowBuilderMask me) {
		this.me = me;
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		translate();
	}

	/**
	 * Translate using the BundleManager
	 */
	public void translate() {
		// Defensive code to avoid NullPointerException in WindowBuilder/Runtime when data are empty in bundle (Mnemonic and accelerator are not mandatory)
		if (BundleManager.getUITexts(me, "clearConsoleMnemonic") != null && !BundleManager.getUITexts(me, "clearConsoleMnemonic").equals("")) // WindowBuilder
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "clearConsoleMnemonic")).getKeyCode());
		if (BundleManager.getUITexts(me, "clearConsoleAccelerator") != null && !BundleManager.getUITexts(me, "clearConsoleAccelerator").equals("")) // WindowBuilder
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "clearConsoleAccelerator")));
		putValue(NAME, BundleManager.getUITexts(me, "clearConsoleMenuLabel"));
		putValue(SMALL_ICON, GamesLibrary.clearConsoleIcon);
		putValue(SHORT_DESCRIPTION, BundleManager.getUITexts(me, "clearConsoleToolTip"));
	}
	
	@Override
	public String getLabelKey() {
		if (librarian.getParameters().getButtonsDisplayMode().equals(ButtonsDisplayMode.Icon))
			return null;
		return "clearConsoleMenuLabel";
	}

	@Override
	public ImageIcon getIcon() {
		if (librarian.getParameters().getButtonsDisplayMode().equals(ButtonsDisplayMode.Text))
			return null;
		return GamesLibrary.clearConsoleIcon;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		librarian.displayMainTab(LibrarianTabEnum.Controls);
		me.getLibrarian().getConsoleTextPane().setText("");
	}

}
