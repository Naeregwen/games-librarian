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
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import commons.GamesLibrarianIcons;
import components.Librarian;
import components.GamesLibrarian.WindowBuilderMask;
import components.commons.BundleManager;
import components.commons.interfaces.Translatable;

/**
 * @author Naeregwen
 * 
 */
public class AboutAction extends AbstractAction implements Translatable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5530142826221677715L;
	
	WindowBuilderMask me;
	Librarian librarian;
	
	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public AboutAction(WindowBuilderMask me) {
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
		if (BundleManager.getUITexts(me, "aboutMnemonic") != null && !BundleManager.getUITexts(me, "aboutMnemonic").equals("")) // WindowBuilder
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "aboutMnemonic")).getKeyCode());
		if (BundleManager.getUITexts(me, "aboutAccelerator") != null && !BundleManager.getUITexts(me, "aboutAccelerator").equals("")) // WindowBuilder
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "aboutAccelerator")));
		putValue(NAME, BundleManager.getUITexts(me, "aboutMenuLabel"));
		putValue(SMALL_ICON, GamesLibrarianIcons.aboutIcon);
		putValue(SHORT_DESCRIPTION, BundleManager.getUITexts(me, "aboutToolTip"));
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(me != null ? me.getLibrarian().getView() : null, // WindowBuilder
			"<html>" 
			+ "<p>" + me.getLibrarian().getApplicationTitle() + " - Copyright 2012-2014 " + BundleManager.getUITexts(me, "by") + " " + "Naeregwen" + "</p>"
			+ "<br/>"
			+ BundleManager.getUITexts(me, "aboutDescriptionMessage")
			+ BundleManager.getUITexts(me, "aboutDescriptionComplementMessage")
			+ "<br/>"
			+ BundleManager.getUITexts(me, "aboutLicenceMessage")
			+ BundleManager.getUITexts(me, "aboutLicenceLinkMessage")
			+ "<br/>"
			+ BundleManager.getUITexts(me, "aboutSilkIconsMessage")
			+ BundleManager.getUITexts(me, "aboutSilkIconsLinkMessage")
			+ "</html>",
			BundleManager.getUITexts(me, "aboutTitle"),
			JOptionPane.INFORMATION_MESSAGE);
	}

}
