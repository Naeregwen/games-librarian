/**
 * 
 */
package components.comboboxes.renderers;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager.LookAndFeelInfo;

import components.labels.ErroneousLabel;

/**
 * @author Naeregwen
 *
 */
public class LookAndFeelInfoComboBoxRenderer extends ErroneousLabel implements ListCellRenderer<LookAndFeelInfo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6870850559415375028L;

	private final ListCellRenderer<LookAndFeelInfo> listCellRenderer;
	
	public LookAndFeelInfoComboBoxRenderer(ListCellRenderer<LookAndFeelInfo> listCellRenderer) {
		this.listCellRenderer = listCellRenderer;
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends LookAndFeelInfo> list, LookAndFeelInfo lookAndFeelInfo, int index, boolean isSelected, boolean cellHasFocus) {
		JComponent component = (JComponent) listCellRenderer.getListCellRendererComponent(list, lookAndFeelInfo, index, isSelected, cellHasFocus);
		// Set the text
		if (component instanceof JLabel)
			((JLabel) component).setText(lookAndFeelInfo.getName());
		return component;
	}

}
