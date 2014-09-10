/**
 * 
 */
package components.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.lang.reflect.InvocationTargetException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import commons.BundleManager;
import commons.GamesLibrary;
import commons.OS;
import commons.Strings;
import commons.GamesLibrary.LoadingSource;
import commons.api.Parameters;
import commons.api.SteamProfile;
import commons.enums.ButtonsDisplayMode;
import commons.windows.WinRegistry;
import components.Librarian;
import components.GamesLibrarian.WindowBuilderMask;
import components.actions.interfaces.IconAndTextAction;

/**
 * @author Naeregwen
 *
 */
public class ResetOptionsAction extends AbstractAction implements IconAndTextAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8163469661066437230L;

	WindowBuilderMask me;
	Librarian librarian;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public ResetOptionsAction(WindowBuilderMask me) {
		this.me = me;
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		translate();
	}

	/**
	 * Translate using BundleManager
	 */
	public void translate() {
		// Defensive code to avoid NullPointerException in WindowBuilder/Runtime when data are empty in bundle (Mnemonic and accelerator are not mandatory)
		if (BundleManager.getUITexts(me, "resetOptionsMnemonic") != null && !BundleManager.getUITexts(me, "resetOptionsMnemonic").equals("")) // WindowBuilder
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "resetOptionsMnemonic")).getKeyCode());
		if (BundleManager.getUITexts(me, "resetOptionsAccelerator") != null && !BundleManager.getUITexts(me, "resetOptionsAccelerator").equals("")) // WindowBuilder
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "resetOptionsAccelerator")));
		putValue(NAME, BundleManager.getUITexts(me, "resetOptionsMenuLabel"));
		putValue(SMALL_ICON, GamesLibrary.resetOptionsIcon);
		putValue(SHORT_DESCRIPTION, BundleManager.getUITexts(me, "resetOptionsToolTip"));
	}
	
	@Override
	public String getLabelKey() {
		if (librarian.getParameters().getButtonsDisplayMode().equals(ButtonsDisplayMode.Icon))
			return null;
		return "resetOptionsMenuLabel";
	}

	@Override
	public ImageIcon getIcon() {
		if (librarian.getParameters().getButtonsDisplayMode().equals(ButtonsDisplayMode.Text))
			return null;
		return GamesLibrary.resetOptionsIcon;
	}

	/**
	 * Common operation
	 */
	public void readRegitryOptions() {
		
		Librarian librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		Parameters parameters = librarian.getParameters();
		String message;
		
		// Read keys from Windows registry
		if (parameters.getOs().getPrefix() != OS.Prefix.Win) {
			message = parameters.getMessages().getString("errorResetOptionsUnableToInitialize");
			librarian.getTee().writelnError(message);
			return;
		}
		
		message = parameters.getMessages().getString("infosResetOptionIsStarting");
		librarian.getTee().writelnMessage(message);
		
		try {
			// Read Windows Distribution Name
			// NT Versions
			String windowsDistribution = WinRegistry.readString(WinRegistry.HKEY_LOCAL_MACHINE, "SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion", "ProductName");
			if (windowsDistribution != null) {
				librarian.getParameters().setWindowsDistribution(windowsDistribution);
				librarian.getWindowsDistributionTextField().setText(librarian.getParameters().getWindowsDistribution());
				message = String.format(parameters.getMessages().getString("infosResetOptionsWindowsDistribution"), librarian.getParameters().getWindowsDistribution());
				librarian.getTee().writelnInfos(message);
			} else {
				// Prior NT Versions
				windowsDistribution = WinRegistry.readString(WinRegistry.HKEY_LOCAL_MACHINE, "SOFTWARE\\Microsoft\\Windows\\CurrentVersion", "ProductName");
				if (windowsDistribution != null) {
					librarian.getParameters().setWindowsDistribution(windowsDistribution);
					librarian.getWindowsDistributionTextField().setText(librarian.getParameters().getWindowsDistribution());
					message = String.format(parameters.getMessages().getString("infosResetOptionsWindowsDistribution"), librarian.getParameters().getWindowsDistribution());
					librarian.getTee().writelnInfos(message);
				} else 	{
					message = parameters.getMessages().getString("errorResetOptionsWindowsDistribution");
					librarian.getTee().writelnError(message);
				}
			}
			
			// Read Steam Executable Location (SteamExe in registry)
			String steamExecutable = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\Valve\\Steam", "SteamExe");
			if (steamExecutable != null) {
				if (librarian.getParameters().getOs().getPrefix() == OS.Prefix.Win) 
					steamExecutable = 
					steamExecutable.substring(0, 1).toUpperCase() /* Camelcase is nice */
					+ steamExecutable.substring(1).replaceAll("/", File.separator + File.separator);
				librarian.getParameters().setSteamExecutable(steamExecutable);
				librarian.getSteamExecutableTextField().setText(librarian.getParameters().getSteamExecutable());
				message = String.format(parameters.getMessages().getString("infosResetOptionsSteamExecutable"), librarian.getParameters().getSteamExecutable());
				librarian.getTee().writelnInfos(message);
			} else {
				message = parameters.getMessages().getString("errorResetOptionsSteamExecutable");
				librarian.getTee().writelnError(message);
			}
			
			// Read Steam Gamer ID (LastGameNameUsed in registry)
			String gamerSteamId = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\Valve\\Steam", "LastGameNameUsed");
			if (gamerSteamId != null) {
				librarian.getParameters().setMainPlayerSteamId(gamerSteamId);
				librarian.getGamerSteamIdTextField().setText(gamerSteamId);
				message = String.format(parameters.getMessages().getString("infosResetOptionsSteamId"), gamerSteamId);
				librarian.getTee().writelnInfos(message);
				SteamProfile currentProfile = new SteamProfile();
				if (Strings.fullNumericPattern.matcher(gamerSteamId).matches())
					currentProfile.setSteamID64(gamerSteamId);
				else
					currentProfile.setSteamID(gamerSteamId);
				currentProfile.setLoadingSource(LoadingSource.Preloading);
				librarian.updateProfileTab(currentProfile);
				librarian.updateSelectedSteamProfile(currentProfile);
			} else {
				message = parameters.getMessages().getString("errorResetOptionsSteamId");
				librarian.getTee().writelnError(message);
			}
			
		} catch (IllegalArgumentException e) {
			librarian.getTee().printStackTrace(e);
		} catch (IllegalAccessException e) {
			librarian.getTee().printStackTrace(e);
		} catch (InvocationTargetException e) {
			librarian.getTee().printStackTrace(e);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		readRegitryOptions();
	}

}
