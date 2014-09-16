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
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import commons.BundleManager;
import commons.GamesLibrary;
import commons.api.SteamProfile;
import commons.enums.ButtonsDisplayMode;
import commons.enums.ProfileTabEnum;
import components.Librarian;
import components.GamesLibrarian.WindowBuilderMask;
import components.actions.interfaces.IconAndTextAction;
import components.commons.interfaces.Translatable;

/**
 * @author Naeregwen
 *
 */
public class AddProfileAction extends AbstractAction implements Translatable, IconAndTextAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2525714252951132850L;

	WindowBuilderMask me;
	Librarian librarian;

	String currentProfileID;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public AddProfileAction(WindowBuilderMask me) {
		this.me = me;
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		if (librarian != null) // WindowBuilder
			librarian.addTranslatable(this);
		translate();
	}

	/* (non-Javadoc)
	 * @see components.commons.interfaces.Translatable#translate()
	 */
	@Override
	public void translate() {
		// Defensive code to avoid NullPointerException in WindowBuilder when data are empty in bundle (Mnemonic and accelerator are not mandatory)
		if (BundleManager.getUITexts(me, "addProfileMnemonic") != null && !BundleManager.getUITexts(me, "addProfileMnemonic").equals("")) // WindowBuilder
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "addProfileMnemonic")).getKeyCode());
		if (BundleManager.getUITexts(me, "addProfileAccelerator") != null && !BundleManager.getUITexts(me, "addProfileAccelerator").equals("")) // WindowBuilder
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "addProfileAccelerator")));
		putValue(NAME, BundleManager.getUITexts(me, "addProfileMenuLabel"));
		putValue(SMALL_ICON, GamesLibrary.addProfile);
		putValue(SHORT_DESCRIPTION, BundleManager.getUITexts(me, "addProfileTooltip"));
	}
	
	/* (non-Javadoc)
	 * @see components.actions.interfaces.IconAndTextAction#getLabelKey()
	 */
	@Override
	public String getLabelKey() {
		if (librarian.getParameters().getButtonsDisplayMode().equals(ButtonsDisplayMode.Icon))
			return null;
		return "addProfileMenuLabel";
	}

	/* (non-Javadoc)
	 * @see components.actions.interfaces.IconAndTextAction#getIcon()
	 */
	@Override
	public ImageIcon getIcon() {
		if (librarian.getParameters().getButtonsDisplayMode().equals(ButtonsDisplayMode.Text))
			return null;
		return GamesLibrary.addProfile;
	}

	/**
	 * Adding a profile based on user input
	 */
	public SteamProfile requestProfileIdAndLoadProfile(String initialInput) {
		String profileID = (String)JOptionPane.showInputDialog(me.getLibrarian().getView(),
				me.getLibrarian().getParameters().getUITexts().getString("addProfileInputMessage"),
				me.getLibrarian().getParameters().getUITexts().getString("addProfileTitle"),
				JOptionPane.PLAIN_MESSAGE, null, null, currentProfileID == null ? initialInput : currentProfileID);
		if (profileID != null && !profileID.trim().equals("")) {
			currentProfileID = profileID;
			SteamProfile steamProfile = new SteamProfile();
			steamProfile.setId(currentProfileID);
			return steamProfile;
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		librarian.displaySubTab(ProfileTabEnum.Summary);
		SteamProfile steamProfile = requestProfileIdAndLoadProfile(null);
		if (steamProfile != null)
			me.getLibrarian().addNewProfile(steamProfile);
	}

}
