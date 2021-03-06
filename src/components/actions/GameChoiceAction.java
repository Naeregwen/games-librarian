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
import javax.swing.KeyStroke;

import commons.api.Parameters;
import commons.enums.GameChoice;
import commons.enums.LibrarianTabEnum;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.commons.BundleManager;
import components.commons.interfaces.Translatable;

/**
 * @author Naeregwen
 *
 */
public class GameChoiceAction extends AbstractAction implements Translatable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7585072116154627162L;

	WindowBuilderMask me;
	Librarian librarian;
	
	GameChoice gameChoice;
	
	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 * @param gameChoice the binded GameChoice
	 */
	public GameChoiceAction(WindowBuilderMask me, GameChoice gameChoice) {
		this.me = me;
		this.gameChoice = gameChoice;
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
		if (BundleManager.getUITexts(me, gameChoice.getMnemonicKey()) != null && !BundleManager.getUITexts(me, gameChoice.getMnemonicKey()).equals("")) // WindowBuilder
			putValue(MNEMONIC_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, gameChoice.getMnemonicKey())).getKeyCode());
		if (BundleManager.getUITexts(me, gameChoice.getAcceleratorKey()) != null && !BundleManager.getUITexts(me, gameChoice.getAcceleratorKey()).equals("")) // WindowBuilder
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, gameChoice.getAcceleratorKey())));
		putValue(NAME, BundleManager.getUITexts(me, gameChoice.getLabelKey()));
		putValue(SMALL_ICON, gameChoice.getIcon());
		putValue(SHORT_DESCRIPTION, BundleManager.getUITexts(me, gameChoice.getToolTipKey()));
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Parameters parameters = librarian.getParameters();
		librarian.displayMainTab(LibrarianTabEnum.Controls);
		librarian.displayLaunchButtons(gameChoice);
		if (parameters.isDebug()) 
			librarian.getTee().writelnInfos(String.format(parameters.getMessages().getString("gameChoiceChanged"), gameChoice.ordinal()+1));
	}

}
