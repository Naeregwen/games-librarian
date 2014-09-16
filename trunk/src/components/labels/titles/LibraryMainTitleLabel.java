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

import java.awt.Font;

import javax.swing.UIManager;

import commons.BundleManager;
import commons.GamesLibrary.LoadingSource;
import commons.api.Parameters;
import commons.api.SteamProfile;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.commons.interfaces.Translatable;
import components.labels.TitleLabel;

/**
 * @author Naeregwen
 *
 */
public class LibraryMainTitleLabel extends TitleLabel implements Translatable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -996062795021545741L;
	
	WindowBuilderMask me;
	Librarian librarian;

	String labelKey;
	
	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 * @param labelKey the labelKey for label translation
	 */
	public LibraryMainTitleLabel(WindowBuilderMask me, String labelKey) {
		super();
		this.me = me;
		this.labelKey = labelKey;
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		if (librarian != null) // WindowBuilder
			librarian.addTranslatable(this);
		setFont(new Font(UIManager.getFont("Label.font").getFontName(), Font.BOLD, UIManager.getFont("Label.font").getSize() + 4));
		translate();
	}

	/* (non-Javadoc)
	 * @see components.commons.interfaces.Translatable#translate()
	 */
	@Override
	public void translate() {
		if (librarian == null) // WindowBuilder
			setText(BundleManager.getUITexts(me, labelKey));
		else {
			Parameters parameters = librarian.getParameters();
			SteamProfile currentSteamProfile = librarian.getCurrentSteamProfile();
			if (parameters.getSteamGamesList() != null && parameters.getSteamGamesList().getSteamGames() != null && (currentSteamProfile != null)) {
				// Update library title label
				String steamId = currentSteamProfile.getId();
				if (parameters.getSteamGamesList().getLoadingSource().equals(LoadingSource.Preloading))
					setText(parameters.getSteamGamesList().getSteamGames().size() <= 1 ?
						String.format(BundleManager.getUITexts(me, "libraryTitleLabelPreloadedSingular"), SteamProfile.htmlIdToText(steamId), parameters.getSteamGamesList().getSteamGames().size())
						: String.format(BundleManager.getUITexts(me, "libraryTitleLabelPreloaded"), SteamProfile.htmlIdToText(steamId), parameters.getSteamGamesList().getSteamGames().size()));
				else
					setText(parameters.getSteamGamesList().getSteamGames().size() <= 1 ?
						String.format(BundleManager.getUITexts(me, "libraryTitleLabelSingular"), SteamProfile.htmlIdToText(steamId), parameters.getSteamGamesList().getSteamGames().size())
						: String.format(BundleManager.getUITexts(me, "libraryTitleLabel"), SteamProfile.htmlIdToText(steamId), parameters.getSteamGamesList().getSteamGames().size()));
			} else
				// Empty library title label
				setText(BundleManager.getUITexts(me, "libraryTitleLabelEmpty"));
		} 
			
	}

}
