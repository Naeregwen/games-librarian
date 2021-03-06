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

import commons.GamesLibrarianIcons;
import commons.enums.interfaces.GamesLibrarianActionEnum;

/**
 * @author Naeregwen
 *
 */
public enum ButtonsDisplayMode implements GamesLibrarianActionEnum {
	
	IconAndText ("buttonsDisplayModeIconAndTextLabel", "buttonsDisplayModeIconAndTextMnemonic", "buttonsDisplayModeIconAndTextAccelerator", GamesLibrarianIcons.buttonsDisplayModeIconAndTextIcon), 
	Icon ("buttonsDisplayModeIconLabel", "buttonsDisplayModeIconMnemonic", "buttonsDisplayModeIconAccelerator", GamesLibrarianIcons.buttonsDisplayModeIconIcon), 
	Text ("buttonsDisplayModeTextLabel", "buttonsDisplayModeTextMnemonic", "buttonsDisplayModeTextAccelerator", GamesLibrarianIcons.buttonsDisplayModeTextIcon);
	
	String labelKey;
	String mnemonicKey;
	String acceleratorKey;
	ImageIcon icon;
	
	ButtonsDisplayMode(String labelKey, String mnemonicKey, String acceleratorKey, ImageIcon icon) {
		this.labelKey = labelKey;
		this.mnemonicKey = mnemonicKey;
		this.acceleratorKey = acceleratorKey;
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
	@Override
	public ImageIcon getIcon() {
		return icon;
	}

	

}
