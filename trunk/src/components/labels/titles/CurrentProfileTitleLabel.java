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

import commons.GamesLibrarianIcons.LoadingSource;
import commons.api.SteamProfile;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.commons.BundleManager;
import components.commons.interfaces.Translatable;
import components.labels.TitleLabel;

/**
 * @author Naeregwen
 *
 */
public class CurrentProfileTitleLabel extends TitleLabel implements Translatable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4595421313648402277L;

	WindowBuilderMask me;
	Librarian librarian;

	String labelKey;
	
	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 * @param labelKey the labelKey for label translation
	 */
	public CurrentProfileTitleLabel(WindowBuilderMask me, String labelKey) {
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
			SteamProfile currentSteamProfile = librarian.getCurrentSteamProfile();
			if (currentSteamProfile != null && currentSteamProfile.getSteamID64() != null) {
				// Update profile title label
				String steamId = currentSteamProfile.getId();
				if (currentSteamProfile.getLoadingSource().equals(LoadingSource.Preloading))
					setText(String.format(BundleManager.getUITexts(me, "currentProfileTitleLabelPreloaded"), SteamProfile.htmlIdToText(steamId)));
				else
					setText(String.format(BundleManager.getUITexts(me, "currentProfileTitleLabel"), SteamProfile.htmlIdToText(steamId)));
			} else
				// Empty profile title label
				setText(BundleManager.getUITexts(me, "currentProfileTitleLabelEmpty"));
		}

	}

}
