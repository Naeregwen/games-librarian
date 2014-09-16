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
package components.actions.enums;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import commons.enums.SteamGroupsSortMethod;
import components.Librarian;
import components.GamesLibrarian.WindowBuilderMask;
import components.actions.interfaces.EnumAction;
import components.commons.BundleManager;
import components.commons.interfaces.Translatable;

/**
 * @author Naeregwen
 *
 */
public class SteamGroupsSortMethodAction extends AbstractAction implements Translatable, EnumAction<SteamGroupsSortMethod> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5509739286728380202L;

	WindowBuilderMask me;
	Librarian librarian;
	
	SteamGroupsSortMethod steamGroupsSortMethod;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 * @param steamGroupsSortMethod the binded SteamGroupsSortMethod enumeration element
	 */
	public SteamGroupsSortMethodAction(WindowBuilderMask me, SteamGroupsSortMethod steamGroupsSortMethod) {
		this.me = me;
		this.steamGroupsSortMethod = steamGroupsSortMethod;
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		if (librarian != null) // WindowBuilder
			librarian.addTranslatable(this);
		translate();
	}

	/* (non-Javadoc)
	 * @see components.commons.interfaces.Translatable#translate()
	 */
	@Override
	public void translate() {
		putValue(NAME, BundleManager.getUITexts(me, steamGroupsSortMethod.getLabelKey()));
		putValue(SMALL_ICON, steamGroupsSortMethod.getIcon());
	}
	
	/* (non-Javadoc)
	 * @see components.actions.interfaces.EnumAction#getCurrentItem()
	 */
	@Override
	public SteamGroupsSortMethod getCurrentItem() {
		return steamGroupsSortMethod;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {}

}
