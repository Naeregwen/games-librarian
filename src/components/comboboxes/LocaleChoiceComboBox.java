/**
 * 
 */
package components.comboboxes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.regex.Matcher;

import javax.swing.JComboBox;
import javax.swing.ListCellRenderer;

import commons.BundleManager;
import commons.enums.LocaleChoice;
import components.GamesLibrarian.WindowBuilderMask;
import components.comboboxes.renderers.LocaleChoiceComboBoxRenderer;

/**
 * @author Naeregwen
 * 
 */
public class LocaleChoiceComboBox extends JComboBox<LocaleChoice> implements ActionListener {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7505492166723454021L;

	WindowBuilderMask me;
	
	@SuppressWarnings("unchecked")
	public LocaleChoiceComboBox(WindowBuilderMask me) {
		super(LocaleChoice.values());
		this.me = me;
		setRenderer(new LocaleChoiceComboBoxRenderer((ListCellRenderer<LocaleChoice>) this.getRenderer()));
		setMaximumRowCount(3);
		addActionListener(this);
	}

	/*/
	 * (non-Javadoc)
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
