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
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import commons.GamesLibrarianIcons;
import commons.enums.ButtonsDisplayMode;
import commons.enums.LibrarianTabEnum;
import components.Librarian;
import components.GamesLibrarian.WindowBuilderMask;
import components.actions.interfaces.IconAndTextAction;
import components.commons.BundleManager;
import components.commons.interfaces.Translatable;

/**
 * @author Naeregwen
 *
 */
public class LoadGameStatsAction extends AbstractAction implements Translatable, IconAndTextAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4576700534942875559L;

	WindowBuilderMask me;
	Librarian librarian;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public LoadGameStatsAction(WindowBuilderMask me) {
		this.me = me;
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
		if (BundleManager.getUITexts(me, "loadGameStatsMnemonic") != null && !BundleManager.getUITexts(me, "loadGameStatsMnemonic").equals("")) // WindowBuilder
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "loadGameStatsMnemonic")).getKeyCode());
		if (BundleManager.getUITexts(me, "loadGameStatsAccelerator") != null && !BundleManager.getUITexts(me, "loadGameStatsAccelerator").equals("")) // WindowBuilder
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "loadGameStatsAccelerator")));
		putValue(NAME, BundleManager.getUITexts(me, "loadGameStatsMenuLabel"));
		putValue(SMALL_ICON, GamesLibrarianIcons.steamIcon);
		putValue(SHORT_DESCRIPTION, BundleManager.getUITexts(me, "loadGameStatsTooltip"));
	}
	
	/* (non-Javadoc)
	 * @see components.actions.interfaces.IconAndTextAction#getLabelKey()
	 */
	@Override
	public String getLabelKey() {
		if (librarian.getParameters().getButtonsDisplayMode().equals(ButtonsDisplayMode.Icon))
			return null;
		return "loadGameStatsMenuLabel";
	}

	/* (non-Javadoc)
	 * @see components.actions.interfaces.IconAndTextAction#getIcon()
	 */
	@Override
	public ImageIcon getIcon() {
		if (librarian.getParameters().getButtonsDisplayMode().equals(ButtonsDisplayMode.Text))
			return null;
		return GamesLibrarianIcons.steamIcon;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		librarian.displayMainTab(LibrarianTabEnum.Game);
		me.getLibrarian().readSteamGameStats();
	}

}
