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
