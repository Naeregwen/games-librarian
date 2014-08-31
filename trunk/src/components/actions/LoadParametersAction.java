/**
 * 
 */
package components.actions;

import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import commons.BundleManager;
import commons.GamesLibrary;
import commons.api.Parameters;
import commons.api.parsers.ParametersParser;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;

/**
 * @author Naeregwen
 *
 */
public class LoadParametersAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6764755728240241613L;

	WindowBuilderMask me;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public LoadParametersAction(WindowBuilderMask me) {
		this.me = me;
		translate();
	}

	/**
	 * Translate using BundleManager
	 */
	public void translate() {
		// Defensive code to avoid NullPointerException in WindowBuilder when data are empty in bundle (Mnemonic and accelerator are not mandatory)
		if (BundleManager.getUITexts(me, "loadParametersMnemonic") != null && !BundleManager.getUITexts(me, "loadParametersMnemonic").equals("")) // WindowBuilder
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "loadParametersMnemonic")).getKeyCode());
		if (BundleManager.getUITexts(me, "loadParametersAccelerator") != null && !BundleManager.getUITexts(me, "loadParametersAccelerator").equals("")) // WindowBuilder
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "loadParametersAccelerator")));
		putValue(NAME, BundleManager.getUITexts(me, "loadParametersMenuLabel"));
		putValue(SMALL_ICON, GamesLibrary.loadParametersIcon);
		putValue(SHORT_DESCRIPTION, BundleManager.getUITexts(me, "loadParametersToolTip"));
	}
	
	/**
	 * Load a configuration from given filename
	 * @param filename
	 */
	public void loadConfiguration(String filename) {
		
		Librarian librarian =  me.getLibrarian();
		Parameters parameters = librarian.getParameters();
		ResourceBundle messages = parameters.getMessages();
		
		librarian.printConfigurationMessage(messages.getString("infosLoadConfigurationIsStarting"), filename);
		
		// Load data from local XML file
		(new ParametersParser(parameters, librarian.getTee())).parse(filename, librarian.getTee());
		
		// Display results
		librarian.getTee().writelnInfos(parameters.summarizeGamesList());
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Confirm operation
		String filename = me.getLibrarian().confirmLoadConfigurationFile(Parameters.defaultConfigurationFilename);
		if (filename == null) return;
		// Preserve old set to complete missing parameters
		Parameters previousParameters = (Parameters) me.getLibrarian().getParameters().clone();
		// Process operation
		loadConfiguration(filename);
		// update UI according to new parameters
		me.getLibrarian().updateUI(previousParameters, false);
		// Roll a game
		me.getLibrarian().getRollAction().forceRoll();
	}

}
