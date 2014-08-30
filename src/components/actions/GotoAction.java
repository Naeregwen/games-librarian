/**
 * 
 */
package components.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import commons.BundleManager;
import commons.enums.ProfileTab;
import commons.enums.TabEnum;
import components.GamesLibrarian.WindowBuilderMask;

/**
 * @author Naeregwen
 *
 */
public class GotoAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2835382948936353366L;

	WindowBuilderMask me;
	TabEnum tabEnum;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public GotoAction(WindowBuilderMask me, TabEnum tabEnum) {
		this.me = me;
		this.tabEnum = tabEnum;
		translate();
	}

	/**
	 * Translate using BundleManager
	 */
	public void translate() {
		// Defensive code to avoid NullPointerException in WindowBuilder when data are empty in bundle (Mnemonic and accelerator are not mandatory)
		if (BundleManager.getUITexts(me, tabEnum.getMnemonicKey()) != null && !BundleManager.getUITexts(me, tabEnum.getMnemonicKey()).equals("")) // WindowBuilder
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, tabEnum.getMnemonicKey())).getKeyCode());
		if (BundleManager.getUITexts(me, tabEnum.getAcceleratorKey()) != null && !BundleManager.getUITexts(me, tabEnum.getAcceleratorKey()).equals("")) // WindowBuilder
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, tabEnum.getAcceleratorKey())));
		putValue(NAME, BundleManager.getUITexts(me, tabEnum.getLabelKey()));
		putValue(SMALL_ICON, tabEnum.getIcon());
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (tabEnum.isMain())
			me.getLibrarian().displayMainTab(tabEnum);
		else if (tabEnum instanceof ProfileTab)
			me.getLibrarian().displaySubTab(tabEnum);
	}

}
