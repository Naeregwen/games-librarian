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
package components.commons.adapters;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import commons.api.SteamProfile;
import commons.enums.ProfileTabEnum;
import components.Librarian;
import components.GamesLibrarian.WindowBuilderMask;

/**
 * @author Naeregwen
 *
 */
public class SteamProfileMouseAdapter extends MouseAdapter {

	Librarian librarian;
	SteamProfile steamProfile;
	
	/**
	 * 
	 */
	public SteamProfileMouseAdapter(WindowBuilderMask me) {
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
		if ((mouseEvent.getClickCount() == 1 || mouseEvent.getClickCount() == 2) && steamProfile != null)
			librarian.updateProfileTab(steamProfile);
        if (mouseEvent.getClickCount() == 2)
        	librarian.displaySubTab(ProfileTabEnum.Summary);
	}

}
