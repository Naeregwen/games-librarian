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
package components.actions.texts;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.UIManager.LookAndFeelInfo;

import commons.GamesLibrarianIcons;
import components.Librarian;
import components.GamesLibrarian.WindowBuilderMask;
import components.comboboxes.interfaces.TextAction;
import components.commons.BundleManager;
import components.commons.interfaces.Translatable;

/**
 * @author Naeregwen
 *
 */
public class LookAndFeelAction extends AbstractAction implements Translatable, TextAction<LookAndFeelInfo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3004202897304167371L;

	WindowBuilderMask me;
	Librarian librarian;
	
	LookAndFeelInfo lookAndFeelInfo;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 * @param lookAndFeelInfo the binded LookAndFeelInfo
	 */
	public LookAndFeelAction(WindowBuilderMask me, LookAndFeelInfo lookAndFeelInfo) {
		this.me = me;
		this.lookAndFeelInfo = lookAndFeelInfo;
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
		putValue(NAME, lookAndFeelInfo.getName());
		putValue(SMALL_ICON, GamesLibrarianIcons.lookAndFeelIcon);
		putValue(SHORT_DESCRIPTION, BundleManager.getUITexts(me, "lookAndFeelToolTip") + " = " + lookAndFeelInfo.getName());
	}
	
	/* (non-Javadoc)
	 * @see components.actions.interfaces.EnumAction#getCurrentItem()
	 */
	@Override
	public LookAndFeelInfo getObject() {
		return lookAndFeelInfo;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {}

}
