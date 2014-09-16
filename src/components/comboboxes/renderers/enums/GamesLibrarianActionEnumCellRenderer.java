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

package components.comboboxes.renderers.enums;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import commons.enums.interfaces.GamesLibrarianActionEnum;
import components.GamesLibrarian.WindowBuilderMask;
import components.commons.BundleManager;
import components.labels.ErroneousLabel;

/**
 * @author Naeregwen
 *
 */
public class GamesLibrarianActionEnumCellRenderer extends ErroneousLabel implements ListCellRenderer<GamesLibrarianActionEnum> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2993112913576686385L;

	WindowBuilderMask me;
	
	private final ListCellRenderer<GamesLibrarianActionEnum> listCellRenderer;
	
	@SuppressWarnings("unchecked")
	public GamesLibrarianActionEnumCellRenderer(WindowBuilderMask me, ListCellRenderer<? extends GamesLibrarianActionEnum> listCellRenderer) {
		this.me = me;
		this.listCellRenderer = (ListCellRenderer<GamesLibrarianActionEnum>) listCellRenderer;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent(JList<? extends GamesLibrarianActionEnum> list, GamesLibrarianActionEnum value, int index, boolean isSelected, boolean cellHasFocus) {
		JComponent component = (JComponent) listCellRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		if (component instanceof JLabel) {
			// Set the icon
			((JLabel) component).setIcon(value.getIcon());
			// Set the text
			// Replace text by error message when icon is null
			String text = BundleManager.getUITexts(me, value.getLabelKey());
			if (value.getIcon() != null)
				((JLabel) component).setText(text);
			else
				setUhOhText((JLabel) component, String.format(BundleManager.getUITexts(me, "errorComboBoxNoImageAvailable"), text), list.getFont());
		}
		return component;
	}

}
