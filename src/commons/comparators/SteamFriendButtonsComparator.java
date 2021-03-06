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
package commons.comparators;

import java.util.Comparator;

import commons.enums.ComparisonDirection;
import commons.enums.SteamFriendsSortMethod;
import components.containers.remotes.SteamFriendButton;

/**
 * @author Naeregwen
 *
 */
public class SteamFriendButtonsComparator implements Comparator<SteamFriendButton> {

	ComparisonDirection comparisonDirection;
	SteamFriendsSortMethod steamFriendsSortMethod;
	
	public SteamFriendButtonsComparator(ComparisonDirection comparisonDirection, SteamFriendsSortMethod steamFriendsSortMethod) {
		this.comparisonDirection = comparisonDirection;
		this.steamFriendsSortMethod = steamFriendsSortMethod;
	}

	@Override
	public int compare(SteamFriendButton steamFriendButton1, SteamFriendButton steamFriendButton2) {		
		int comparisonResult = 0;
	
		switch (steamFriendsSortMethod) {
		case InitialAscendingOrder:
			comparisonResult = steamFriendButton1.getSteamProfile().getInitialPosition().compareTo(steamFriendButton2.getSteamProfile().getInitialPosition());
			break;
		case NameAscendingOrder:
		case NameDescendingOrder:
			if (steamFriendButton1.getSteamProfile().getSteamID() != null && !steamFriendButton1.getSteamProfile().getSteamID().equals("")) {
				if (steamFriendButton2.getSteamProfile().getSteamID() != null && !steamFriendButton2.getSteamProfile().getSteamID().equals(""))
					comparisonResult = steamFriendButton1.getSteamProfile().getSteamID().toLowerCase().compareTo(steamFriendButton2.getSteamProfile().getSteamID().toLowerCase());
				else
					comparisonResult = steamFriendButton1.getSteamProfile().getInitialPosition().compareTo(steamFriendButton2.getSteamProfile().getInitialPosition());
			} else {
				if (steamFriendButton2.getSteamProfile().getSteamID() != null && !steamFriendButton2.getSteamProfile().getSteamID().equals(""))
					comparisonResult = steamFriendButton1.getSteamProfile().getInitialPosition().compareTo(steamFriendButton2.getSteamProfile().getInitialPosition());
				else
					comparisonResult = 0;
			}
			break;
		case LogoAscendingOrder:
		case LogoDescendingOrder:
			if (steamFriendButton1.getSteamProfile().getAvatarIcon() != null && !steamFriendButton1.getSteamProfile().getAvatarIcon().equals("")) {
				if (steamFriendButton2.getSteamProfile().getAvatarIcon() != null && !steamFriendButton2.getSteamProfile().getAvatarIcon().equals(""))
					comparisonResult = steamFriendButton1.getSteamProfile().getAvatarIcon().toLowerCase().compareTo(steamFriendButton2.getSteamProfile().getAvatarIcon().toLowerCase());
				else
					comparisonResult = steamFriendButton1.getSteamProfile().getInitialPosition().compareTo(steamFriendButton2.getSteamProfile().getInitialPosition());
			} else {
				if (steamFriendButton2.getSteamProfile().getAvatarIcon() != null && !steamFriendButton2.getSteamProfile().getAvatarIcon().equals(""))
					comparisonResult = steamFriendButton1.getSteamProfile().getInitialPosition().compareTo(steamFriendButton2.getSteamProfile().getInitialPosition());
				else
					comparisonResult = 0;
			}
			break;
		case OnlineStateAscendingOrder:
		case OnlineStateDescendingOrder:
			if (steamFriendButton1.getSteamProfile().getOnlineState() != null && !steamFriendButton1.getSteamProfile().getOnlineState().equals("")) {
				if (steamFriendButton2.getSteamProfile().getOnlineState() != null && !steamFriendButton2.getSteamProfile().getOnlineState().equals(""))
					comparisonResult = steamFriendButton1.getSteamProfile().getOnlineState().toLowerCase().compareTo(steamFriendButton2.getSteamProfile().getOnlineState().toLowerCase());
				else
					comparisonResult = steamFriendButton1.getSteamProfile().getInitialPosition().compareTo(steamFriendButton2.getSteamProfile().getInitialPosition());
			} else {
				if (steamFriendButton2.getSteamProfile().getOnlineState() != null && !steamFriendButton2.getSteamProfile().getOnlineState().equals(""))
					comparisonResult = steamFriendButton1.getSteamProfile().getInitialPosition().compareTo(steamFriendButton2.getSteamProfile().getInitialPosition());
				else
					comparisonResult = 0;
			}
			break;
		case StateMessageAscendingOrder:
		case StateMessageDescendingOrder:
			if (steamFriendButton1.getSteamProfile().getStateMessage() != null && !steamFriendButton1.getSteamProfile().getStateMessage().equals("")) {
				if (steamFriendButton2.getSteamProfile().getStateMessage() != null && !steamFriendButton2.getSteamProfile().getStateMessage().equals(""))
					comparisonResult = steamFriendButton1.getSteamProfile().getStateMessage().toLowerCase().compareTo(steamFriendButton2.getSteamProfile().getStateMessage().toLowerCase());
				else
					comparisonResult = steamFriendButton1.getSteamProfile().getInitialPosition().compareTo(steamFriendButton2.getSteamProfile().getInitialPosition());
			} else {
				if (steamFriendButton2.getSteamProfile().getStateMessage() != null && !steamFriendButton2.getSteamProfile().getStateMessage().equals(""))
					comparisonResult = steamFriendButton1.getSteamProfile().getInitialPosition().compareTo(steamFriendButton2.getSteamProfile().getInitialPosition());
				else
					comparisonResult = 0;
			}
			break;
		case SteamId64AscendingOrder:
		case SteamId64DescendingOrder:
			comparisonResult = steamFriendButton1.getSteamProfile().getSteamID64().compareTo(steamFriendButton2.getSteamProfile().getSteamID64());
			break;
		}
		
		return comparisonDirection == ComparisonDirection.Ascendant ? comparisonResult : -comparisonResult;
	}

}
