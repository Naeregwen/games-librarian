/**
 * 
 */
package components.containers;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import commons.enums.ArrowDirection;
import components.buttons.ArrowComboBoxButton;
import components.labels.Label;

/**
 * @author Naeregwen
 *
 */
public class ArrowedComboBox extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5995253721657994616L;

	public ArrowedComboBox(Label label, JComboBox<?> comboBox) {
		if (label != null) add(label);
		add(new ArrowComboBoxButton(ArrowDirection.PREVIOUS, comboBox));
		add(comboBox);
		add(new ArrowComboBoxButton(ArrowDirection.NEXT, comboBox));
	}

}
