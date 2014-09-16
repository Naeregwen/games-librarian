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
package components.labels.titles;

import commons.BundleManager;
import commons.GamesLibrary.LoadingSource;
import commons.api.SteamGame;
import commons.api.SteamProfile;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.commons.interfaces.Translatable;
import components.labels.TitleLabel;

/**
 * @author Naeregwen
 *
 */
public class CurrentGameTitleLabel extends TitleLabel implements Translatable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8741573346768323326L;

	WindowBuilderMask me;
	Librarian librarian;

	String labelKey;
	
	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 * @param labelKey the labelKey for label translation
	 */
	public CurrentGameTitleLabel(WindowBuilderMask me, String labelKey) {
		super();
		this.me = me;
		this.labelKey = labelKey;
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
		if (librarian == null)
			setText(BundleManager.getUITexts(me, labelKey));
		else {
			SteamGame currentSteamGame = librarian.getCurrentSteamGame();
			if (currentSteamGame != null) {
				// Update game title label
				String steamId = librarian.getCurrentSteamProfile().getId();
				String gameId = currentSteamGame != null ? currentSteamGame.getID(BundleManager.getMessages(me, "undefinedSteamGameID"))  : BundleManager.getMessages(me, "undefinedSteamGameID");
				if (currentSteamGame.getLoadingSource().equals(LoadingSource.Preloading))
					setText(String.format(BundleManager.getUITexts(me, "currentGameTitleLabelPreloaded"), gameId, SteamProfile.htmlIdToText(steamId)));
				else
					setText(String.format(BundleManager.getUITexts(me, "currentGameTitleLabel"), gameId, SteamProfile.htmlIdToText(steamId)));
			} else
				// Empty game title label
				setText(BundleManager.getUITexts(me, "currentGameTitleLabelEmpty"));
		}
	}

}
