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
import commons.enums.ProfileTabEnum;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.actions.interfaces.IconAndTextAction;

/**
 * @author Naeregwen
 *
 */
public class LoadProfileAction extends AbstractAction implements IconAndTextAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5136431556983680457L;

	WindowBuilderMask me;
	Librarian librarian;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public LoadProfileAction(WindowBuilderMask me) {
		this.me = me;
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		translate();
	}

	/**
	 * Translate using BundleManager
	 */
	public void translate() {
		// Defensive code to avoid NullPointerException in WindowBuilder/Runtime when data are empty in bundle (Mnemonic and accelerator are not mandatory)
		if (BundleManager.getUITexts(me, "loadProfileMnemonic") != null && !BundleManager.getUITexts(me, "loadProfileMnemonic").equals("")) // WindowBuilder
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "loadProfileMnemonic")).getKeyCode());
		if (BundleManager.getUITexts(me, "loadProfileAccelerator") != null && !BundleManager.getUITexts(me, "loadProfileAccelerator").equals("")) // WindowBuilder
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "loadProfileAccelerator")));
		putValue(NAME, BundleManager.getUITexts(me, "loadProfileMenuLabel"));
		putValue(SMALL_ICON, GamesLibrary.steamIcon);
		putValue(SHORT_DESCRIPTION, BundleManager.getUITexts(me, "loadProfileTooltip"));
	}
	
	@Override
	public String getLabelKey() {
		if (librarian.getParameters().getButtonsDisplayMode().equals(ButtonsDisplayMode.Icon))
			return null;
		return "loadProfileMenuLabel";
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
		librarian.displaySubTab(ProfileTabEnum.Summary);
		me.getLibrarian().requestAndLoadProfile();
	}

}
