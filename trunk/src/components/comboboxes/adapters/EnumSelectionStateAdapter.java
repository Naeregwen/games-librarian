/**
 * Copyright 2012-2014 Naeregwen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package components.comboboxes.adapters;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.JComboBox;

import commons.groups.ActionConstants;
import commons.groups.ActionGroup;
import components.actions.interfaces.EnumAction;

/**
 * @author Naeregwen
 *
 */
public class EnumSelectionStateAdapter<ComboBoxItemType> implements ActionListener, PropertyChangeListener {

	private ActionGroup actionsGroup;
	private JComboBox<? extends ComboBoxItemType> comboBox;
	
	public EnumSelectionStateAdapter(ActionGroup actionsGroup, JComboBox<? extends ComboBoxItemType> comboBox) {
		this.actionsGroup = actionsGroup;
		this.comboBox = comboBox;			
	}
	
	public void configure() {
		for (Action actionGroup : actionsGroup.getActions())
			actionGroup.addPropertyChangeListener(this);
		comboBox.addActionListener(this);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(ActionConstants.SELECTED_KEY)) {
			Boolean newSelectedState = (Boolean) evt.getNewValue();
			if (newSelectedState) {
				@SuppressWarnings("unchecked")
				EnumAction<? extends ComboBoxItemType> action = (EnumAction<? extends ComboBoxItemType>) evt.getSource();
				for (int index = 0; index < comboBox.getItemCount(); index++) {
					if (action.getCurrentItem().equals(comboBox.getItemAt(index))) {
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
			@SuppressWarnings("unchecked")
			EnumAction<? extends ComboBoxItemType> action = (EnumAction<? extends ComboBoxItemType>) actionGroup;
			if (action.getCurrentItem().equals(comboBox.getSelectedItem())) {
				actionGroup.putValue(ActionConstants.SELECTED_KEY, true);
				break;
			}
		}
	}
}
