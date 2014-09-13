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
package components.comboboxes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.ListCellRenderer;

import commons.enums.ProfileTabEnum;
import commons.enums.SteamFriendsSortMethod;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.comboboxes.renderers.enums.GamesLibrarianActionEnumCellRenderer;

/**
 * @author Naeregwen
 *
 */
public class SteamFriendsSortMethodComboBox extends JComboBox<SteamFriendsSortMethod> implements ActionListener {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2309717919379074157L;

	Librarian librarian;
	SteamFriendsSortMethod currentSteamFriendsSortMethod;
	
	@SuppressWarnings("unchecked")
	public SteamFriendsSortMethodComboBox(WindowBuilderMask me) {
		super(SteamFriendsSortMethod.values());
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		setRenderer(new GamesLibrarianActionEnumCellRenderer(me, (ListCellRenderer<SteamFriendsSortMethod>) this.getRenderer()));
		addActionListener(this);
		currentSteamFriendsSortMethod = (SteamFriendsSortMethod) getSelectedItem();
	}
	
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		librarian.displaySubTab(ProfileTabEnum.Friends);
		if (currentSteamFriendsSortMethod != (SteamFriendsSortMethod) getSelectedItem()) {
			currentSteamFriendsSortMethod = (SteamFriendsSortMethod) getSelectedItem();
			librarian.sort((SteamFriendsSortMethod) getSelectedItem());
		}
    }
	
}
