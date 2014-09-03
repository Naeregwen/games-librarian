package components.comboboxes.adapters;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.JComboBox;

import commons.groups.ActionConstants;
import commons.groups.ActionGroup;
import components.comboboxes.interfaces.TextAction;

public class TextSelectionStateAdapter<ComboBoxItemType> implements ActionListener, PropertyChangeListener {

	private ActionGroup actionsGroup;
	private JComboBox<? extends ComboBoxItemType> comboBox;
	
	public TextSelectionStateAdapter(ActionGroup actionsGroup, JComboBox<? extends ComboBoxItemType> comboBox) {
		this.actionsGroup = actionsGroup;
		this.comboBox = comboBox;			
	}
	
	public void configure() {
		for (Action actionGroup : actionsGroup.getActions())
			actionGroup.addPropertyChangeListener(this);
		comboBox.addActionListener(this);
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(ActionConstants.SELECTED_KEY)) {
			Boolean newSelectedState = (Boolean) evt.getNewValue();
			if (newSelectedState) {
				@SuppressWarnings("unchecked")
				TextAction<? extends ComboBoxItemType> action = (TextAction<? extends ComboBoxItemType>) evt.getSource();
				for (int index = 0; index < comboBox.getItemCount(); index++) {
					if (action.getObject().equals(comboBox.getItemAt(index))) {
						comboBox.setSelectedIndex(index);
						break;
					}
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (Action actionGroup : actionsGroup.getActions()) {
			TextAction<?> action = (TextAction<?>) actionGroup;
			if (action.getObject().equals(comboBox.getSelectedItem())) {
				actionGroup.putValue(ActionConstants.SELECTED_KEY, true);
				break;
			}
		}
	}
}
