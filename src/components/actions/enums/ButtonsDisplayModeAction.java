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
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import commons.BundleManager;
import commons.enums.ButtonsDisplayMode;
import components.GamesLibrarian.WindowBuilderMask;
import components.actions.interfaces.EnumAction;
import components.actions.interfaces.IconAndTextAction;

/**
 * @author Naeregwen
 *
 */
public class ButtonsDisplayModeAction extends AbstractAction implements IconAndTextAction, EnumAction<ButtonsDisplayMode> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1582737327957441107L;
	
	WindowBuilderMask me;
	ButtonsDisplayMode buttonsDisplayMode;

	/**
	 * 
	 */
	public ButtonsDisplayModeAction(WindowBuilderMask me, ButtonsDisplayMode buttonsDisplayMode) {
		this.me = me;
		this.buttonsDisplayMode = buttonsDisplayMode;
		translate();
	}

	/* (non-Javadoc)
	 * @see components.actions.interfaces.TranslatablesActions#translate()
	 */
	public void translate() {
		// Defensive code to avoid NullPointerException in WindowBuilder/Runtime when data are empty in bundle (Mnemonic and accelerator are not mandatory)
		if (BundleManager.getUITexts(me, buttonsDisplayMode.getMnemonicKey()) != null && !BundleManager.getUITexts(me, buttonsDisplayMode.getMnemonicKey()).equals("")) // WindowBuilder
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, buttonsDisplayMode.getMnemonicKey())).getKeyCode());
		if (BundleManager.getUITexts(me, buttonsDisplayMode.getAcceleratorKey()) != null && !BundleManager.getUITexts(me, buttonsDisplayMode.getAcceleratorKey()).equals("")) // WindowBuilder
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, buttonsDisplayMode.getAcceleratorKey())));
		putValue(NAME, BundleManager.getUITexts(me, buttonsDisplayMode.getLabelKey()));
		putValue(SMALL_ICON, buttonsDisplayMode.getIcon());
	}
	
	@Override
	public String getLabelKey() {
		return buttonsDisplayMode.getLabelKey();
	}

	@Override
	public ImageIcon getIcon() {
		return buttonsDisplayMode.getIcon();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {}

	/*/
	 * (non-Javadoc)
	 * @see components.actions.interfaces.EnumAction#getCurrentItem()
	 */
	@Override
	public ButtonsDisplayMode getCurrentItem() {
		return buttonsDisplayMode;
	}

}
