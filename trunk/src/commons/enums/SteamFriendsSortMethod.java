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
package commons.enums;

import javax.swing.ImageIcon;

import commons.GamesLibrary;
import commons.enums.interfaces.GamesLibrarianActionEnum;

/**
 * @author Naeregwen
 *
 */
public enum SteamFriendsSortMethod implements GamesLibrarianActionEnum {

	InitialAscendingOrder("initialOrder", GamesLibrary.steamFriendsInitialAscendingOrderIcon),
	NameAscendingOrder("nameAscendingOrder", GamesLibrary.steamFriendsNameAscendingOrderIcon),
	NameDescendingOrder("nameDescendingOrder", GamesLibrary.steamFriendsNameDescendingOrderIcon),
	LogoAscendingOrder("logoAscendingOrder", GamesLibrary.steamFriendsLogoAscendingOrderIcon),
	LogoDescendingOrder("logoDescendingOrder", GamesLibrary.steamFriendsLogoDescendingOrderIcon),
	OnlineStateAscendingOrder("onlineStateAscendingOrder", GamesLibrary.steamFriendsOnlineStateAscendingOrderIcon),
	OnlineStateDescendingOrder("onlineStateDescendingOrder", GamesLibrary.steamFriendsOnlineStateDescendingOrderIcon),
	StateMessageAscendingOrder("stateMessageAscendingOrder", GamesLibrary.steamFriendsStateMessageAscendingOrderIcon),
	StateMessageDescendingOrder("stateMessageDescendingOrder", GamesLibrary.steamFriendsStateMessageDescendingOrderIcon),
	SteamId64AscendingOrder("steamId64AscendingOrder", GamesLibrary.steamFriendsSteamId64AscendingOrderIcon),
	SteamId64DescendingOrder("steamId64DescendingOrder", GamesLibrary.steamFriendsSteamId64DescendingOrderIcon);

	String labelKey;
	ImageIcon icon;
	
	SteamFriendsSortMethod(String labelKey, ImageIcon icon) {
		this.labelKey = labelKey;
		this.icon = icon;
	}

	/**
	 * @return the labelKey
	 */
	@Override
	public String getLabelKey() {
		return labelKey;
	}

	/**
	 * @return the icon
	 */
	@Override
	public ImageIcon getIcon() {
		return icon;
	}
	
}
