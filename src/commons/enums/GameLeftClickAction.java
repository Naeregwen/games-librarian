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
package commons.enums;

import javax.swing.ImageIcon;

import commons.GamesLibrary;
import commons.enums.interfaces.GamesLibrarianActionEnum;

/**
 * @author Naeregwen
 *
 */
public enum GameLeftClickAction implements GamesLibrarianActionEnum {

	Select ("gameLeftClickActionSelectLabel", "gameLeftClickActionSelectLabel", GamesLibrary.gameLeftClickSelectIcon),
	Launch ("gameLeftClickActionLaunchLabel", "gameLeftClickActionLaunchLabel", GamesLibrary.gameLeftClickLaunchIcon);
	
	String labelKey;
	String translationKey;
	ImageIcon icon;
	
	private GameLeftClickAction(String labelKey, String translationKey, ImageIcon icon) {
		this.labelKey = labelKey;
		this.translationKey = translationKey;
		this.icon = icon;
	}
	
	/**
	 * @return the labelKey
	 */
	@Override
	public String getLabelKey() {
		return labelKey;
	}

	/**
	 * @return the translationKey
	 */
	public String getTranslationKey() {
		return translationKey;
	}
	
	/**
	 * @return the icon
	 */
	@Override
	public ImageIcon getIcon() {
		return icon;
	}

}
