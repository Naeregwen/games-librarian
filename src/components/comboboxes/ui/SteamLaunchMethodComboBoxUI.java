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
package components.comboboxes.ui;

import java.awt.Rectangle;

import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.plaf.metal.MetalComboBoxUI;

/**
 * http://stackoverflow.com/questions/956003/how-can-i-change-the-width-of-a-jcombobox-dropdown-list
 */
public class SteamLaunchMethodComboBoxUI extends MetalComboBoxUI {

	public SteamLaunchMethodComboBoxUI() {
		super();
	}


//	ComboBoxUI comboBoxUI;
//	
//	public SteamLaunchMethodComboBoxUI(ComboBoxUI comboBoxUI) {
//		this.comboBoxUI = comboBoxUI;
//	}

	protected ComboPopup createPopup() {

		BasicComboPopup popup = new BasicComboPopup(comboBox) {

			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = -4639226143809609954L;

			@Override
			protected Rectangle computePopupBounds(int px, int py, int pw, int ph) {
				return super.computePopupBounds(px, py, Math.max(comboBox.getPreferredSize().width, pw), ph);
			}
		};
		popup.getAccessibleContext().setAccessibleParent(comboBox);
		return popup;
	}
}
