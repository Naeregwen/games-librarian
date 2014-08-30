/**
 * 
 */
package components.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import commons.BundleManager;
import commons.GamesLibrary;
import commons.OS;
import commons.api.Parameters;
import components.Librarian;
import components.GamesLibrarian.WindowBuilderMask;
import components.dialogs.CenteredFileChooser;

/**
 * @author Naeregwen
 *
 */
public class SteamExecutableAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5221761608074437230L;

	WindowBuilderMask me;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public SteamExecutableAction(WindowBuilderMask me) {
		this.me = me;
		translate();
	}

	/**
	 * Translate using BundleManager
	 */
	public void translate() {
		// Defensive code to avoid NullPointerException in WindowBuilder when data are empty in bundle (Mnemonic and accelerator are not mandatory)
		if (BundleManager.getUITexts(me, "steamExecutableMnemonic") != null && !BundleManager.getUITexts(me, "steamExecutableMnemonic").equals("")) // WindowBuilder
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "steamExecutableMnemonic")).getKeyCode());
		if (BundleManager.getUITexts(me, "steamExecutableAccelerator") != null && !BundleManager.getUITexts(me, "steamExecutableAccelerator").equals("")) // WindowBuilder
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "steamExecutableAccelerator")));
		putValue(NAME, BundleManager.getUITexts(me, "steamExecutableMenuLabel"));
		putValue(SMALL_ICON, GamesLibrary.steamExecutableIcon);
		putValue(SHORT_DESCRIPTION, BundleManager.getUITexts(me, "steamExecutableToolTip"));
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Librarian librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		Parameters parameters = librarian.getParameters();
		String message;
		
		CenteredFileChooser fileChooser = new CenteredFileChooser(librarian.getView().getRootPane());
		fileChooser.setCurrentDirectory(new File(librarian.getSteamExecutableTextField().getText()));
		
		if (parameters.getOs().getPrefix() == OS.Prefix.Win) {			
			message = parameters.getUITexts().getString("steamExecutableFilenameFilter");
			FileNameExtensionFilter exeFileFilter = new FileNameExtensionFilter(message, "exe", "bat", "cmd");
			fileChooser.addChoosableFileFilter(exeFileFilter);
			fileChooser.setFileFilter(exeFileFilter);
		}
		
		int userAction = fileChooser.showOpenDialog(librarian.getSteamExecutableTextField());
		if (userAction == JFileChooser.APPROVE_OPTION) {
			String newSteamExecutableLocation = fileChooser.getCurrentDirectory().toString() + File.separator + fileChooser.getSelectedFile().getName();
			librarian.getSteamExecutableTextField().setText(newSteamExecutableLocation);
			message = String.format(parameters.getMessages().getString("steamExecutableNewLocationSelected"), newSteamExecutableLocation);
			librarian.getTee().writelnMessage(message);
		}
		if (userAction == JFileChooser.CANCEL_OPTION) {
			message = String.format(parameters.getMessages().getString("steamExecutableNewLocationCanceled"));
			librarian.getTee().writelnMessage(message);
		}
		if (userAction == JFileChooser.ERROR_OPTION) {
			message = String.format(parameters.getMessages().getString("errorSteamExecutableNewLocationCanceled"));
			librarian.getTee().writelnError(message);
		}
	}

}
