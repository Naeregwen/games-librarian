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

import javax.swing.Icon;

import commons.GamesLibrary;

/**
 * @author Naeregwen
 *
 */
public enum LibrarianTabEnum implements TabEnum {

	Controls ("gotoControlsMenuLabel", "gotoControlsMnemonic", "gotoControlsAccelerator", GamesLibrary.controlsMenuIcon),
	Library ("gotoLibraryMenuLabel", "gotoLibraryMnemonic", "gotoLibraryAccelerator", GamesLibrary.libraryMenuIcon),
	Game ("gotoGameMenuLabel", "gotoGameMnemonic", "gotoGameAccelerator", GamesLibrary.gameMenuIcon),
	Profile ("gotoProfileMenuLabel", "gotoProfileMnemonic", "gotoProfileAccelerator", GamesLibrary.profileMenuIcon),
	Options ("gotoOptionsMenuLabel", "gotoOptionsMnemonic", "gotoOptionsAccelerator", GamesLibrary.optionsMenuIcon);
	
	String labelKey;
	String mnemonicKey;
	String acceleratorKey;
	Icon icon;

	LibrarianTabEnum(String labelKey, String mnemonicKey, String acceleratorKey, Icon icon) {
		this.labelKey = labelKey;
		this.mnemonicKey = mnemonicKey;
		this.acceleratorKey = acceleratorKey;
		this.icon = icon;
	}
	
	/**
	 * @return the labelKey
	 */
	public String getLabelKey() {
		return labelKey;
	}

	/**
	 * @return the mnemonicKey
	 */
	public String getMnemonicKey() {
		return mnemonicKey;
	}

	/**
	 * @return the acceleratorKey
	 */
	public String getAcceleratorKey() {
		return acceleratorKey;
	}

	/**
	 * @return the icon
	 */
	public Icon getIcon() {
		return icon;
	}

	@Override
	public boolean isMain() {
		return true;
	}

}
