/**
 * 
 */
package components.actions.texts;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.UIManager.LookAndFeelInfo;

import commons.BundleManager;
import commons.GamesLibrary;
import components.GamesLibrarian.WindowBuilderMask;
import components.comboboxes.interfaces.TextAction;

/**
 * @author Naeregwen
 *
 */
public class LookAndFeelAction extends AbstractAction implements TextAction<LookAndFeelInfo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3004202897304167371L;

	WindowBuilderMask me;
	LookAndFeelInfo lookAndFeelInfo;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public LookAndFeelAction(WindowBuilderMask me, LookAndFeelInfo lookAndFeelInfo) {
		this.me = me;
		this.lookAndFeelInfo = lookAndFeelInfo;
		translate();
	}

	/**
	 * Translate using BundleManager
	 */
	public void translate() {
		putValue(NAME, lookAndFeelInfo.getName());
		putValue(SMALL_ICON, GamesLibrary.lookAndFeelIcon);
		putValue(SHORT_DESCRIPTION, BundleManager.getUITexts(me, "lookAndFeelToolTip") + " = " + lookAndFeelInfo.getName());
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {}

	@Override
	public LookAndFeelInfo getObject() {
		return lookAndFeelInfo;
	}

}
