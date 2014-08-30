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
import commons.Strings;
import commons.GamesLibrary.LoadingSource;
import commons.api.Parameters;
import commons.api.SteamProfile;
import commons.api.parsers.ParametersParser;
import commons.enums.DumpMode;
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
		// TODO: check parsing result
		(new ParametersParser(parameters, librarian.getTee())).parse(filename, librarian.getTee());
		
		// Complete missing parameters with default values
		if (parameters.getDumpMode() == null)
			parameters.setDumpMode(DumpMode.XML);
		
		// Display results
		librarian.getTee().writelnInfos(parameters.summarizeGamesList());
		
		// Update Control Tab
		librarian.getScrollLockAction().restore();
		
		// Update Library Tab
		librarian.updateGamesLibraryTab();
		
		// Clear Game Tab
		librarian.clearGameTab();
		
		// Update Profile Tab
		if (parameters.getMainPlayerSteamId() != null) {
			SteamProfile currentProfile = new SteamProfile();
			if (Strings.fullNumericPattern.matcher(parameters.getMainPlayerSteamId()).matches())
				currentProfile.setSteamID64(parameters.getMainPlayerSteamId());
			else
				currentProfile.setSteamID(parameters.getMainPlayerSteamId());
			currentProfile.setLoadingSource(LoadingSource.Preloading);
			librarian.updateProfileTab(currentProfile);
			librarian.addProfile(currentProfile, true);
			librarian.updateSelectedSteamProfile(currentProfile);
		}
		
		// Update Options Tab
		librarian.getWindowsDistributionTextField().setText(parameters.getWindowsDistribution());
		librarian.getSteamExecutableTextField().setText(parameters.getSteamExecutable());
		librarian.getGamerSteamIdTextField().setText(librarian.getCurrentSteamProfile().getId());
		
		// Options Tab Selector
		librarian.updateDefaultSteamLaunchMethod();
		librarian.updateDumpMode();
		librarian.updateLocaleChoice();
		
		// Roll a game
		librarian.getRollAction().forceRoll();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Confirm operation
		String filename = me.getLibrarian().confirmLoadConfigurationFile(Parameters.defaultConfigurationFilename);
		if (filename == null) return;
		// Process operation
		loadConfiguration(filename);
	}

}
