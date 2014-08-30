/**
 * 
 */
package components.actions.enums;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import commons.BundleManager;
import commons.enums.LocaleChoice;
import components.GamesLibrarian;
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
		putValue(SMALL_ICON, new ImageIcon(GamesLibrarian.class.getResource(localeChoice.getIconPath())));
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
