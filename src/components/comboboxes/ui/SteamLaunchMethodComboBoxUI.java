/**
 * 
 */
package components.comboboxes.ui;

import java.awt.Rectangle;

import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.plaf.metal.MetalComboBoxUI;

/**
 * @author Naeregwen
 * 
 *         http://stackoverflow.com/questions/956003/how-can-i-change-the-width-of-a-jcombobox-dropdown-list
 */
public class SteamLaunchMethodComboBoxUI extends MetalComboBoxUI {

	public SteamLaunchMethodComboBoxUI() {
		super();
	}


//	ComboBoxUI comboBoxUI;
//	
//	public SteamLaunchMethodComboBoxUI(ComboBoxUI comboBoxUI) {
//		this.comboBoxUI = comboBoxUI;
//	}

	protected ComboPopup createPopup() {

		BasicComboPopup popup = new BasicComboPopup(comboBox) {

			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = -4639226143809609954L;

			@Override
			protected Rectangle computePopupBounds(int px, int py, int pw, int ph) {
				return super.computePopupBounds(px, py, Math.max(comboBox.getPreferredSize().width, pw), ph);
			}
		};
		popup.getAccessibleContext().setAccessibleParent(comboBox);
		return popup;
	}
}
