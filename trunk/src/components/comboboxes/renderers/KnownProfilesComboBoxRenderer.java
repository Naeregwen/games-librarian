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

import org.jsoup.Jsoup;

import commons.api.SteamProfile;
import components.labels.ErroneousLabel;

/**
 * @author Naeregwen
 *
 */
public class KnownProfilesComboBoxRenderer extends ErroneousLabel implements ListCellRenderer<SteamProfile> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6264219919721395348L;

	private final ListCellRenderer<SteamProfile> listCellRenderer;
	
	public KnownProfilesComboBoxRenderer(ListCellRenderer<SteamProfile> listCellRenderer) {
		this.listCellRenderer = listCellRenderer;
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends SteamProfile> list, SteamProfile steamProfile, int index, boolean isSelected, boolean cellHasFocus) {
		JComponent component = (JComponent) listCellRenderer.getListCellRendererComponent(list, steamProfile, index, isSelected, cellHasFocus);
		if (component instanceof JLabel && /* WindowBuilder */ steamProfile != null) {
			// Set the text
			((JLabel) component).setText(steamProfile != null && steamProfile.getId() != null ? Jsoup.parseBodyFragment(steamProfile.getId()).body().text() : "");
		}
		return component;
	}

}
