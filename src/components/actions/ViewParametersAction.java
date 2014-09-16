/**
 * Copyright 2012-2014 Naeregwen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package components.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import commons.BundleManager;
import commons.GamesLibrary;
import commons.api.Parameters;
import commons.api.SteamProfile;
import commons.api.parsers.ParametersParser;
import commons.api.parsers.SteamProfileParser;
import commons.enums.ButtonsDisplayMode;
import commons.enums.LibrarianTabEnum;
import components.Librarian;
import components.GamesLibrarian.WindowBuilderMask;
import components.actions.interfaces.IconAndTextAction;
import components.commons.interfaces.Translatable;

/**
 * @author Naeregwen
 *
 */
public class ViewParametersAction extends AbstractAction implements Translatable, IconAndTextAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9198712344515258987L;

	WindowBuilderMask me;
	Librarian librarian;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public ViewParametersAction(WindowBuilderMask me) {
		this.me = me;
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		if (librarian != null) // WindowBuilder
			librarian.addTranslatable(this);
		translate();
	}

	/* (non-Javadoc)
	 * @see components.commons.interfaces.Translatable#translate()
	 */
	public void translate() {
		// Defensive code to avoid NullPointerException in WindowBuilder/Runtime when data are empty in bundle (Mnemonic and accelerator are not mandatory)
		if (BundleManager.getUITexts(me, "viewParametersMnemonic") != null && !BundleManager.getUITexts(me, "viewParametersMnemonic").equals("")) // WindowBuilder
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "viewParametersMnemonic")).getKeyCode());
		if (BundleManager.getUITexts(me, "viewParametersAccelerator") != null && !BundleManager.getUITexts(me, "viewParametersAccelerator").equals("")) // WindowBuilder
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "viewParametersAccelerator")));
		putValue(NAME, BundleManager.getUITexts(me, "viewParametersMenuLabel"));
		putValue(SMALL_ICON, GamesLibrary.viewParametersIcon);
		putValue(SHORT_DESCRIPTION, BundleManager.getUITexts(me, "viewParametersToolTip"));
	}
	
	/* (non-Javadoc)
	 * @see components.actions.interfaces.IconAndTextAction#getLabelKey()
	 */
	@Override
	public String getLabelKey() {
		if (librarian.getParameters().getButtonsDisplayMode().equals(ButtonsDisplayMode.Icon))
			return null;
		return "viewParametersMenuLabel";
	}

	/* (non-Javadoc)
	 * @see components.actions.interfaces.IconAndTextAction#getIcon()
	 */
	@Override
	public ImageIcon getIcon() {
		if (librarian.getParameters().getButtonsDisplayMode().equals(ButtonsDisplayMode.Text))
			return null;
		return GamesLibrary.viewParametersIcon;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Librarian librarian = me.getLibrarian();
		Parameters parameters = me.getLibrarian().getParameters();
		SteamProfile steamProfile = me.getLibrarian().getCurrentSteamProfile();
		librarian.displayMainTab(LibrarianTabEnum.Controls);
    	switch (parameters.getDumpMode()) {
    	case Text :
    		librarian.getTee().writelnInfos(parameters.getMessages().getString("showCurrentParametersText"));
    		librarian.getTee().writelnMessage(parameters.toStringList());
    		librarian.getTee().writelnInfos(parameters.getMessages().getString("showCurrentProfileText"));
    		librarian.getTee().writelnMessage(steamProfile.toStringList());
    		break;
    	case XML :
			librarian.getTee().writelnInfos(parameters.getMessages().getString("showCurrentParametersXML"));
    		ParametersParser.dump(parameters, librarian.getTee());
    		librarian.getTee().writelnInfos(parameters.getMessages().getString("showCurrentProfileXML"));
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
