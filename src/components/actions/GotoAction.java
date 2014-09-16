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
package components.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import commons.enums.LibraryTabEnum;
import commons.enums.ProfileTabEnum;
import commons.enums.TabEnum;
import components.Librarian;
import components.GamesLibrarian.WindowBuilderMask;
import components.commons.BundleManager;
import components.commons.interfaces.Translatable;

/**
 * @author Naeregwen
 *
 */
public class GotoAction extends AbstractAction implements Translatable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2835382948936353366L;

	WindowBuilderMask me;
	Librarian librarian;
	
	TabEnum tabEnum;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public GotoAction(WindowBuilderMask me, TabEnum tabEnum) {
		this.me = me;
		this.tabEnum = tabEnum;
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
		// Defensive code to avoid NullPointerException in WindowBuilder when data are empty in bundle (Mnemonic and accelerator are not mandatory)
		if (BundleManager.getUITexts(me, tabEnum.getMnemonicKey()) != null && !BundleManager.getUITexts(me, tabEnum.getMnemonicKey()).equals("")) // WindowBuilder
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, tabEnum.getMnemonicKey())).getKeyCode());
		if (BundleManager.getUITexts(me, tabEnum.getAcceleratorKey()) != null && !BundleManager.getUITexts(me, tabEnum.getAcceleratorKey()).equals("")) // WindowBuilder
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, tabEnum.getAcceleratorKey())));
		putValue(NAME, BundleManager.getUITexts(me, tabEnum.getLabelKey()));
		putValue(SMALL_ICON, tabEnum.getIcon());
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (tabEnum.isMain())
			me.getLibrarian().displayMainTab(tabEnum);
		else if (tabEnum instanceof LibraryTabEnum || tabEnum instanceof ProfileTabEnum)
			me.getLibrarian().displaySubTab(tabEnum);
	}

}
