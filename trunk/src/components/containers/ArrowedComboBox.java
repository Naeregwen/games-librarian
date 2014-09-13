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
