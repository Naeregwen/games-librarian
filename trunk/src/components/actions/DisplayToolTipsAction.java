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

import commons.BundleManager;
import commons.GamesLibrary;
import components.Librarian;
import components.GamesLibrarian.WindowBuilderMask;
import components.commons.interfaces.Translatable;

/**
 * @author Naeregwen
 *
 */
public class DisplayToolTipsAction extends AbstractAction implements Translatable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1302582245153154899L;

	WindowBuilderMask me;
	Librarian librarian;
	
	Boolean displayToolTips;
	
	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 * @param displayToolTips the binded Boolean displayToolTips value
	 */
	public DisplayToolTipsAction(WindowBuilderMask me, Boolean displayToolTips) {
		this.me = me;
		this.displayToolTips = displayToolTips;
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
		String displayToolTipsMnemonic = displayToolTips ? "displayToolTipsYesMnemonic" : "displayToolTipsNoMnemonic";
		String displayToolTipsAccelerator = displayToolTips ? "displayToolTipsYesAccelerator" : "displayToolTipsNoAccelerator";
		String displayToolTipName = displayToolTips ? "yes" : "no";
		if (BundleManager.getUITexts(me, displayToolTipsMnemonic) != null && !BundleManager.getUITexts(me, displayToolTipsMnemonic).equals("")) // WindowBuilder
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, displayToolTipsMnemonic)).getKeyCode());
		if (BundleManager.getUITexts(me, displayToolTipsAccelerator) != null && !BundleManager.getUITexts(me, displayToolTipsAccelerator).equals("")) // WindowBuilder
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, displayToolTipsAccelerator)));
		putValue(NAME, BundleManager.getUITexts(me, displayToolTipName));
		putValue(SMALL_ICON, displayToolTips ? GamesLibrary.displayToolTipsYesIcon : GamesLibrary.displayToolTipsNoIcon);
		putValue(SHORT_DESCRIPTION, BundleManager.getUITexts(me, "displayToolTipsToolTip"));
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		me.getLibrarian().updateDisplayTooltips(displayToolTips);
	}

}
