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

import commons.BundleManager;
import commons.enums.LocaleChoice;
import components.GamesLibrarian.WindowBuilderMask;
import components.actions.interfaces.EnumAction;

/**
 * @author Naeregwen
 *
 */
public class LocaleChoiceAction extends AbstractAction implements EnumAction<LocaleChoice> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2182803923157642657L;

	WindowBuilderMask me;
	LocaleChoice localeChoice;

	/**
	 * 
	 */
	public LocaleChoiceAction(WindowBuilderMask me, LocaleChoice localeChoice) {
		this.me = me;
		this.localeChoice = localeChoice;
		translate();
	}

	/**
	 * Translate using BundleManager
	 */
	public void translate() {
		putValue(NAME, BundleManager.getUITexts(me, localeChoice.getLabelKey()));
		putValue(SMALL_ICON, localeChoice.getIcon());
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {}

	@Override
	public LocaleChoice getCurrentItem() {
		return localeChoice;
	}

}
