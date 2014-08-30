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
import commons.api.SteamProfile;
import commons.api.parsers.ParametersParser;
import commons.api.parsers.SteamProfileParser;
import components.Librarian;
import components.GamesLibrarian.WindowBuilderMask;

/**
 * @author Naeregwen
 *
 */
public class ViewParametersAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9198712344515258987L;

	WindowBuilderMask me;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public ViewParametersAction(WindowBuilderMask me) {
		this.me = me;
		translate();
	}

	/**
	 * Translate using BundleManager
	 */
	public void translate() {
		// Defensive code to avoid NullPointerException in WindowBuilder when data are empty in bundle (Mnemonic and accelerator are not mandatory)
		if (BundleManager.getUITexts(me, "viewParametersMnemonic") != null && !BundleManager.getUITexts(me, "viewParametersMnemonic").equals("")) // WindowBuilder
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "viewParametersMnemonic")).getKeyCode());
		if (BundleManager.getUITexts(me, "viewParametersAccelerator") != null && !BundleManager.getUITexts(me, "viewParametersAccelerator").equals("")) // WindowBuilder
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "viewParametersAccelerator")));
		putValue(NAME, BundleManager.getUITexts(me, "viewParametersMenuLabel"));
		putValue(SMALL_ICON, GamesLibrary.viewParametersIcon);
		putValue(SHORT_DESCRIPTION, BundleManager.getUITexts(me, "viewParametersToolTip"));
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Librarian librarian = me.getLibrarian();
		Parameters parameters = me.getLibrarian().getParameters();
		SteamProfile steamProfile = me.getLibrarian().getCurrentSteamProfile();
    	switch (parameters.getDumpMode()) {
    	case Text :
    		librarian.getTee().writelnInfos(parameters.getMessages().getString("showCurrentParametersText"));
    		librarian.getTee().writelnMessage(parameters.toStringList());
    		librarian.getTee().writelnMessage(steamProfile.toStringList());
    		break;
    	case XML :
			librarian.getTee().writelnInfos(parameters.getMessages().getString("showCurrentParametersXML"));
    		ParametersParser.dump(parameters, librarian.getTee());
    		SteamProfileParser.dump(steamProfile, librarian.getTee());
    		break;
    	case Both :
    		librarian.getTee().writelnInfos(parameters.getMessages().getString("showCurrentParametersText"));
    		librarian.getTee().writelnMessage(parameters.toStringList());
    		librarian.getTee().writelnMessage(steamProfile.toStringList());
			librarian.getTee().writelnInfos(parameters.getMessages().getString("showCurrentParametersXML"));
    		ParametersParser.dump(parameters, librarian.getTee());
    		SteamProfileParser.dump(steamProfile, librarian.getTee());
    		break;
    	}
	}

}
