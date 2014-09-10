/**
 * 
 */
package components.actions.enums;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import commons.BundleManager;
import commons.enums.DumpMode;
import components.GamesLibrarian;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.actions.interfaces.EnumAction;

/**
 * @author Naeregwen
 *
 */
public class DumpModeAction extends AbstractAction implements EnumAction<DumpMode> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2269763346796610621L;

	WindowBuilderMask me;
	Librarian librarian;
	DumpMode dumpMode;

	/**
	 * 
	 */
	public DumpModeAction(WindowBuilderMask me, DumpMode dumpMode) {
		this.me = me;
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		this.dumpMode = dumpMode;
		translate();
	}

	/**
	 * Translate using the BundleManager
	 */
	public void translate() {
		if (librarian == null) {
			putValue(NAME, BundleManager.getUITexts(me, dumpMode.getLabelKey()));
			putValue(SMALL_ICON, new ImageIcon(GamesLibrarian.class.getResource(dumpMode.getIconPath())));
		} else {
			putValue(NAME, BundleManager.getUITexts(me, dumpMode.getLabelKey()));
			putValue(SMALL_ICON, new ImageIcon(GamesLibrarian.class.getResource(dumpMode.getIconPath())));
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {}

	/*/
	 * (non-Javadoc)
	 * @see components.actions.interfaces.EnumAction#getCurrentItem()
	 */
	@Override
	public DumpMode getCurrentItem() {
		return dumpMode;
	}

}
