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
package components.comboboxes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.regex.Matcher;

import javax.swing.JComboBox;
import javax.swing.ListCellRenderer;

import commons.enums.LocaleChoice;
import components.Librarian;
import components.GamesLibrarian.WindowBuilderMask;
import components.comboboxes.renderers.enums.GamesLibrarianActionEnumCellRenderer;
import components.commons.BundleManager;
import components.commons.interfaces.Translatable;

/**
 * @author Naeregwen
 *
 */
public class LocaleChoiceComboBox extends JComboBox<LocaleChoice> implements Translatable, ActionListener {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7505492166723454021L;

	WindowBuilderMask me;
	Librarian librarian;
	
	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	@SuppressWarnings("unchecked")
	public LocaleChoiceComboBox(WindowBuilderMask me) {
		super(LocaleChoice.values());
		this.me = me;
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		if (librarian != null) // WindowBuilder
			librarian.addTranslatable(this);
		setRenderer(new GamesLibrarianActionEnumCellRenderer(me, (ListCellRenderer<LocaleChoice>) this.getRenderer()));
		setMaximumRowCount(3);
		addActionListener(this);
		translate();
	}

	/* (non-Javadoc)
	 * @see components.commons.interfaces.Translatable#translate()
	 */
	@Override
	public void translate() {
		setToolTipText(BundleManager.getUITexts(me, "languageComboBoxToolTip"));
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComboBox#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		LocaleChoice localeChoice = (LocaleChoice) this.getSelectedItem();
		Matcher countryMatcher = LocaleChoice.countryPattern.matcher(localeChoice.toString().trim());
		Matcher languageMatcher = LocaleChoice.languagePattern.matcher(localeChoice.toString());
		if (countryMatcher.find() && languageMatcher.find()) {
			Locale newLocale = new Locale(languageMatcher.group(1), countryMatcher.group(1));
			me.getLibrarian().setDefaultLocale(localeChoice, newLocale);
			me.getLibrarian().getTee().writelnMessage(String.format(BundleManager.getMessages(me, "localeChoiceMessage"), localeChoice.getTranslation()));
		} else {
			me.getLibrarian().getTee().writelnError(String.format(BundleManager.getMessages(me, "errorLocaleChoiceUnableToDetermineLocale"), localeChoice.toString()));
		}
	}

}
