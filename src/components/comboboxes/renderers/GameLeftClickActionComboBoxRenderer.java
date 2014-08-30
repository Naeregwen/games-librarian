/**
 * 
 */
package components.comboboxes.renderers;

import java.awt.Component;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import commons.enums.GameLeftClickAction;
import components.labels.ErroneousLabel;

/**
 * @author Naeregwen
 *
 */
public class GameLeftClickActionComboBoxRenderer extends ErroneousLabel implements ListCellRenderer<GameLeftClickAction> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7927619325250647757L;

	private static final ImageIcon[] images;
	
	static {
		// Create the combo box.
		images = new ImageIcon[GameLeftClickAction.values().length];
		int i = 0;
		for (GameLeftClickAction gameLeftClickAction : GameLeftClickAction.values()) {
			images[i] = createImageIcon(gameLeftClickAction.getIconPath());
			if (images[i] != null)
				images[i].setDescription(gameLeftClickAction.name());
			i += 1;
		}
	}

	private final ListCellRenderer<GameLeftClickAction> listCellRenderer;
	
	public GameLeftClickActionComboBoxRenderer(ListCellRenderer<GameLeftClickAction> listCellRenderer) {
		this.listCellRenderer = listCellRenderer;
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends GameLeftClickAction> list, GameLeftClickAction gameLeftClickAction, int index, boolean isSelected, boolean cellHasFocus) {
		JComponent component = (JComponent) listCellRenderer.getListCellRendererComponent(list, gameLeftClickAction, index, isSelected, cellHasFocus);
		if (component instanceof JLabel) {
			// Set the icon
			ImageIcon icon = images[gameLeftClickAction.ordinal()];
			((JLabel) component).setIcon(icon);
			// Set the text
			// Replace text by error message when icon is null
			ResourceBundle UITexts = ResourceBundle.getBundle("i18n/UITexts");
			String text = UITexts.getString(GameLeftClickAction.values()[gameLeftClickAction.ordinal()].getOptionLabel());
			if (icon != null)
				((JLabel) component).setText(text);
			else
				setUhOhText((JLabel) component, String.format(UITexts.getString("errorComboBoxNoImageAvailable"), text), list.getFont());
		}
		return component;
	}
}
