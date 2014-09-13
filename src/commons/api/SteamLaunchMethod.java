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
package commons.api;

import javax.swing.ImageIcon;

import commons.GamesLibrary;
import commons.enums.interfaces.GamesLibrarianActionEnum;

/**
 * @author Naeregwen
 *
 */
public enum SteamLaunchMethod implements GamesLibrarianActionEnum {
	
	/**
	 * i18n keys from bundles
	 */
	SteamProtocol (
			"steamProtocolLaunchOptionLabel", 
			"steamProtocolLaunchOptionToolTip",
			"steamProtocolOptionSelectionMessage",
			"steamProtocolOptionDefaultSelectionMessage",
			"/images/icons/application_lightning.png",
			GamesLibrary.steamLaunchMethodSteamProtocolIcon),
			
	SteamExecutable (
			"steamExecutableLaunchOptionLabel", 
			"steamExecutableLaunchOptionToolTip",
			"steamExecutableOptionSelectionMessage",
			"steamExecutableOptionDefaultSelectionMessage",
			"/images/icons/application_xp_terminal.png",
			GamesLibrary.steamLaunchMethodSteamExecutableIcon);
	
	String labelKey;
	String toolTipKey;
	String selectionMessageKey;
	String defaultSelectionMessageKey;
	String iconPath;
	ImageIcon icon;
	
	SteamLaunchMethod(String labelKey, String toolTipKey, String selectionMessageKey, String defaultSelectionMessageKey, String iconPath, ImageIcon icon) {
		this.labelKey = labelKey;
		this.toolTipKey = toolTipKey;
		this.selectionMessageKey = selectionMessageKey;
		this.defaultSelectionMessageKey = defaultSelectionMessageKey;
		this.iconPath = iconPath; 
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
	 * @return the toolTipKey
	 */
	public String getToolTipKey() {
		return toolTipKey;
	}

	/**
	 * @return the selectionMessageKey
	 */
	public String getSelectionMessageKey() {
		return selectionMessageKey;
	}

	/**
	 * @return the defaultSelectionMessageKey
	 */
	public String getDefaultSelectionMessageKey() {
		return defaultSelectionMessageKey;
	}

	/**
	 * @return the icon
	 */
	@Override
	public ImageIcon getIcon() {
		return icon;
	}
	
	/**
	 * @return the iconPath
	 */
	public String getIconPath() {
		return iconPath;
	}

	/**
	 * @return a list of acceptable string values for parsing
	 */
	public static String getAcceptableValues() {
		StringBuilder acceptableValues = new StringBuilder("");
		for (SteamLaunchMethod steamLaunchMethod : SteamLaunchMethod.values()) 
			acceptableValues.append((acceptableValues.length() == 0 ? steamLaunchMethod : ", " + steamLaunchMethod) + ", " + steamLaunchMethod.ordinal());
		return acceptableValues.toString();	
	}
	
	/**
	 * Return the SteamLaunchMethod the most corresponding to passed value
	 *  
	 * @param value the value to make pass the test
	 * @return SteamLaunchMethod if found, null otherwise
	 */
	public static SteamLaunchMethod getAcceptableValue (String value) {
		for (SteamLaunchMethod steamLaunchMethod : SteamLaunchMethod.values())
			if (steamLaunchMethod.toString().equalsIgnoreCase(value))
				return steamLaunchMethod;
		return null;
	}
}

