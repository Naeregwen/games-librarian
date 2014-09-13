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
public enum SteamGamesSortMethod implements GamesLibrarianActionEnum {

	InitialAscendingOrder("initialOrder", GamesLibrary.steamGamesSortMethodInitialAscendingOrderIcon),
	LogoAscendingOrder("logoAscendingOrder", GamesLibrary.steamGamesSortMethodLogoAscendingOrderIcon),
	LogoDescendingOrder("logoDescendingOrder", GamesLibrary.steamGamesSortMethodLogoDescendingOrderIcon),
	NameAscendingOrder("nameAscendingOrder", GamesLibrary.steamGamesSortMethodNameAscendingOrderIcon),
	NameDescendingOrder("nameDescendingOrder", GamesLibrary.steamGamesSortMethodNameDescendingOrderIcon),
	ArgumentsAscendingOrder("argumentsAscendingOrder", GamesLibrary.steamGamesSortMethodArgumentsAscendingOrderIcon),
	ArgumentsDescendingOrder("argumentsDescendingOrder", GamesLibrary.steamGamesSortMethodArgumentsDescendingOrderIcon),
	SteamLaunchMethodAscendingOrder("steamLaunchMethodAscendingOrder", GamesLibrary.steamGamesSortMethodSteamLaunchMethodAscendingOrderIcon),
	SteamLaunchMethodDescendingOrder("steamLaunchMethodDescendingOrder", GamesLibrary.steamGamesSortMethodSteamLaunchMethodDescendingOrderIcon),
	HoursLast2WeeksAscendingOrder("hoursLast2WeeksdAscendingOrder", GamesLibrary.steamGamesSortMethodHoursLast2WeeksAscendingOrderIcon),
	HoursLast2WeeksDescendingOrder("hoursLast2WeeksdDescendingOrder", GamesLibrary.steamGamesSortMethodHoursLast2WeeksDescendingOrderIcon),
	HoursOnRecordAscendingOrder("hoursOnRecordAscendingOrder", GamesLibrary.steamGamesSortMethodHoursOnRecordAscendingOrderIcon),
	HoursOnRecordDescendingOrder("hoursOnRecordDescendingOrder", GamesLibrary.steamGamesSortMethodHoursOnRecordDescendingOrderIcon),
	AppIdAscendingOrder("appIdAscendingOrder", GamesLibrary.steamGamesSortMethodAppIdAscendingOrderIcon),
	AppIdDescendingOrder("appIdDescendingOrder", GamesLibrary.steamGamesSortMethodAppIdDescendingOrderIcon),
	StoreLinkAscendingOrder("storeLinkAscendingOrder", GamesLibrary.steamGamesSortMethodStoreLinkAscendingOrderIcon),
	StoreLinkDescendingOrder("storeLinkDescendingOrder", GamesLibrary.steamGamesSortMethodStoreLinkDescendingOrderIcon),
	GlobalStatsLinkAscendingOrder("globalStatsLinkAscendingOrder", GamesLibrary.steamGamesSortMethodGlobalStatsLinkAscendingOrderIcon),
	GlobalStatsLinkDescendingOrder("globalStatsLinkDescendingOrder", GamesLibrary.steamGamesSortMethodGlobalStatsLinkDescendingOrderIcon),
	StatsLinkAscendingOrder("statsLinkAscendingOrder", GamesLibrary.steamGamesSortMethodStatsLinkAscendingOrderIcon),
	StatsLinkDescendingOrder("statsLinkDescendingOrder", GamesLibrary.steamGamesSortMethodStatsLinkDescendingOrderIcon);
	
	String labelKey;
	ImageIcon icon;
	
	SteamGamesSortMethod(String labelKey, ImageIcon icon) {
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
