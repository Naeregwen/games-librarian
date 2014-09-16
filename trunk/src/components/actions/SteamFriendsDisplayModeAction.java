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
import javax.swing.KeyStroke;

import commons.enums.SteamFriendsDisplayMode;
import components.Librarian;
import components.GamesLibrarian.WindowBuilderMask;
import components.commons.BundleManager;
import components.commons.interfaces.Translatable;

/**
 * @author Naeregwen
 *
 */
public class SteamFriendsDisplayModeAction extends AbstractAction implements Translatable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2802644000915935385L;

	WindowBuilderMask me;
	Librarian librarian;
	
	SteamFriendsDisplayMode steamFriendsDisplayMode;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 * @param steamFriendsDisplayMode the binded SteamFriendsDisplayMode enumeration element
	 */
	public SteamFriendsDisplayModeAction(WindowBuilderMask me, SteamFriendsDisplayMode steamFriendsDisplayMode) {
		this.me = me;
		this.steamFriendsDisplayMode = steamFriendsDisplayMode;
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		if (librarian != null) // WindowBuilder
			librarian.addTranslatable(this);
		translate();
	}

	/**
	 * @return the steamFriendsDisplayMode
	 */
	public SteamFriendsDisplayMode getSteamFriendsDisplayMode() {
		return steamFriendsDisplayMode;
	}

	/**
	 * Update ToolTip
	 */
	public void setTooltip() {
		putValue(SHORT_DESCRIPTION, me != null && me.getLibrarian() != null ? 
				String.format(BundleManager.getUITexts(me, "steamFriendsDisplayModeTooltip"), 
						BundleManager.getUITexts(me,me.getLibrarian().getParameters().getSteamFriendsDisplayMode().getLabelKey())) : "");
	}
	
	/* (non-Javadoc)
	 * @see components.commons.interfaces.Translatable#translate()
	 */
	@Override
	public void translate() {
		// Defensive code to avoid NullPointerException in WindowBuilder when data are empty in bundle (Mnemonic and accelerator are not mandatory)
		if (BundleManager.getUITexts(me, steamFriendsDisplayMode.getMnemonicKey()) != null && !BundleManager.getUITexts(me, steamFriendsDisplayMode.getMnemonicKey()).equals("")) // WindowBuilder
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, steamFriendsDisplayMode.getMnemonicKey())).getKeyCode());
		if (BundleManager.getUITexts(me, steamFriendsDisplayMode.getAcceleratorKey()) != null && !BundleManager.getUITexts(me, steamFriendsDisplayMode.getAcceleratorKey()).equals("")) // WindowBuilder
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, steamFriendsDisplayMode.getAcceleratorKey())));
		putValue(NAME, BundleManager.getUITexts(me, steamFriendsDisplayMode.getLabelKey()));
		putValue(SMALL_ICON, steamFriendsDisplayMode.getIcon());
		setTooltip();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		me.getLibrarian().updateSteamFriendsDisplayMode(steamFriendsDisplayMode);
	}

}
