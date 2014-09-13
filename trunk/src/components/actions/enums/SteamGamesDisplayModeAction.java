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
import commons.enums.SteamGamesDisplayMode;
import components.GamesLibrarian.WindowBuilderMask;
import components.actions.interfaces.EnumAction;

/**
 * @author Naeregwen
 *
 */
public class SteamGamesDisplayModeAction extends AbstractAction implements EnumAction<SteamGamesDisplayMode> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3276704701609360287L;

	WindowBuilderMask me;
	SteamGamesDisplayMode steamGamesDisplayMode;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public SteamGamesDisplayModeAction(WindowBuilderMask me, SteamGamesDisplayMode steamGamesDisplayMode) {
		this.me = me;
		this.steamGamesDisplayMode = steamGamesDisplayMode;
		translate();
	}

	/**
	 * Translate using BundleManager
	 */
	public void translate() {
		putValue(NAME, BundleManager.getUITexts(me, steamGamesDisplayMode.getLabelKey()));
		putValue(SMALL_ICON, steamGamesDisplayMode.getIcon());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {}

	@Override
	public SteamGamesDisplayMode getCurrentItem() {
		return steamGamesDisplayMode;
	}

}
