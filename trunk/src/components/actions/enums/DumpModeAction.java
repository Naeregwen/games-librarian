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
	DumpMode dumpMode;

	/**
	 * 
	 */
	public DumpModeAction(WindowBuilderMask me, DumpMode dumpMode) {
		this.me = me;
		this.dumpMode = dumpMode;
		translate();
	}

	/**
	 * Translate using BundleManager
	 */
	public void translate() {
		putValue(NAME, BundleManager.getUITexts(me, dumpMode.getLabelKey()));
		putValue(SMALL_ICON, new ImageIcon(GamesLibrarian.class.getResource(dumpMode.getIconPath())));
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
	}

	@Override
	public DumpMode getCurrentItem() {
		return dumpMode;
	}

}
