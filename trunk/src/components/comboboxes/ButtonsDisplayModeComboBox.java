/**
 * 
 */
package components.comboboxes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.ListCellRenderer;

import commons.BundleManager;
import commons.enums.ButtonsDisplayMode;
import components.GamesLibrarian.WindowBuilderMask;
import components.comboboxes.renderers.ButtonsDisplayModeComboBoxRenderer;

/**
 * @author Naeregwen
 *
 */
public class ButtonsDisplayModeComboBox extends JComboBox<ButtonsDisplayMode> implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3634423743186840715L;

	WindowBuilderMask me;
	
	@SuppressWarnings("unchecked")
	public ButtonsDisplayModeComboBox(WindowBuilderMask me) {
		super(ButtonsDisplayMode.values());
		this.me = me;
		// http://www.fnogol.de/archives/2008/02/07/dont-subclass-defautlistcellrenderer-for-nimbus/
		setRenderer(new ButtonsDisplayModeComboBoxRenderer((ListCellRenderer<ButtonsDisplayMode>) this.getRenderer()));
		addActionListener(this);
	}
	
	/*/
	 * (non-Javadoc)
	 * @see javax.swing.JComboBox#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		ButtonsDisplayMode selectedButtonsDisplayMode = (ButtonsDisplayMode) this.getSelectedItem();
		me.getLibrarian().updateCommandButtons(selectedButtonsDisplayMode);
		me.getLibrarian().getTee().writelnMessage(String.format(BundleManager.getMessages(me, "buttonsDisplayModeSelectionMessage"), BundleManager.getUITexts(me, selectedButtonsDisplayMode.getLabelKey())));
	}

}
