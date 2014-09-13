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
package components.buttons;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;

import commons.enums.ArrowDirection;

/**
 * @author Naeregwen
 *
 */
public class ArrowComboBoxButton extends JButton implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8072526756835497478L;
	
	ArrowDirection arrowDirection;
	JComboBox<?> comboBox;
	
	/**
	 * @param arrowDirection
	 */
	public ArrowComboBoxButton(ArrowDirection arrowDirection, JComboBox<?> comboBox) {
		super();
		this.arrowDirection = arrowDirection;
		this.comboBox = comboBox;
		setIcon(arrowDirection.getImageIcon());
		setMargin(new Insets(0, 0, 0, 0));
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int selectedIndex = -1;
		switch (arrowDirection) {
		case NEXT:
			if (comboBox.getItemCount() <= 0) return;
			selectedIndex = comboBox.getSelectedIndex();
			int nextIndex = selectedIndex == comboBox.getItemCount() - 1 ? 0 : selectedIndex + 1;
			comboBox.setSelectedIndex(nextIndex);
			break;
		case PREVIOUS:
			if (comboBox.getItemCount() <= 0) return;
			selectedIndex = comboBox.getSelectedIndex();
			int previousIndex = selectedIndex == 0 ? comboBox.getItemCount() - 1 : selectedIndex - 1;
			comboBox.setSelectedIndex(previousIndex);
			break;
		}
	}
	
	
}
