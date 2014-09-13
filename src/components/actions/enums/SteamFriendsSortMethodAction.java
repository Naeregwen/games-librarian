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
package components.actions.enums;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import commons.BundleManager;
import commons.enums.SteamFriendsSortMethod;
import components.GamesLibrarian.WindowBuilderMask;
import components.actions.interfaces.EnumAction;

/**
 * @author Naeregwen
 *
 */
public class SteamFriendsSortMethodAction extends AbstractAction implements EnumAction<SteamFriendsSortMethod> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1762321151814627032L;

	WindowBuilderMask me;
	SteamFriendsSortMethod steamFriendsSortMethod;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public SteamFriendsSortMethodAction(WindowBuilderMask me, SteamFriendsSortMethod steamFriendsSortMethod) {
		this.me = me;
		this.steamFriendsSortMethod = steamFriendsSortMethod;
		translate();
	}

	/**
	 * Translate using BundleManager
	 */
	public void translate() {
		putValue(NAME, BundleManager.getUITexts(me, steamFriendsSortMethod.getLabelKey()));
		putValue(SMALL_ICON, steamFriendsSortMethod.getIcon());
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {}

	@Override
	public SteamFriendsSortMethod getCurrentItem() {
		return steamFriendsSortMethod;
	}

}
