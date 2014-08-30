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

import commons.enums.LocaleChoice;
import components.labels.ErroneousLabel;

/**
 * @author Naeregwen
 *
 */
public class LocaleChoiceComboBoxRenderer extends ErroneousLabel implements ListCellRenderer<LocaleChoice> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1978231150274244115L;
	
	private static final ImageIcon[] images;
	
	static {
		// Create the combo box.
		images = new ImageIcon[LocaleChoice.values().length];
		int i = 0;
		for (LocaleChoice localeChoice : LocaleChoice.values()) {
			images[i] = createImageIcon("/images/locales/" + localeChoice + ".png");
			if (images[i] != null)
				images[i].setDescription(localeChoice.name());
			i += 1;
		}
	}

	private final ListCellRenderer<LocaleChoice> listCellRenderer;
	
	public LocaleChoiceComboBoxRenderer(ListCellRenderer<LocaleChoice> listCellRenderer) {
		this.listCellRenderer = listCellRenderer;
	}
	
	/*/
	 * (non-Javadoc)
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 * 
	 * This method finds the image and text corresponding to the selected
	 * value and returns the label, set up to display the text and image.
	 */
	@Override
	public Component getListCellRendererComponent(JList<? extends LocaleChoice> list, LocaleChoice localeChoice, int index, boolean isSelected, boolean cellHasFocus) {
		JComponent component = (JComponent) listCellRenderer.getListCellRendererComponent(list, localeChoice, index, isSelected, cellHasFocus);
		if (component instanceof JLabel) {
			// Set the icon
			ImageIcon icon = images[localeChoice.ordinal()];
			((JLabel) component).setIcon(icon);
			// Set the text
			// Replace text by error message when icon is null
			ResourceBundle UITexts = ResourceBundle.getBundle("i18n/UITexts");
			String translation = LocaleChoice.values()[localeChoice.ordinal()].getTranslation();
			if (icon != null)
				((JLabel) component).setText(translation);
			else
				setUhOhText((JLabel) component, String.format(UITexts.getString("errorComboBoxNoImageAvailable"), translation), list.getFont());
		}
		return component;
	}

}
