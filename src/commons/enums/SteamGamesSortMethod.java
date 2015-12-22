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

import commons.GamesLibrarianIcons;
import commons.enums.interfaces.GamesLibrarianActionEnum;

/**
 * @author Naeregwen
 *
 */
public enum SteamGamesSortMethod implements GamesLibrarianActionEnum {

	InitialAscendingOrder("initialOrder", GamesLibrarianIcons.steamGamesSortMethodInitialAscendingOrderIcon),
	LogoAscendingOrder("logoAscendingOrder", GamesLibrarianIcons.steamGamesSortMethodLogoAscendingOrderIcon),
	LogoDescendingOrder("logoDescendingOrder", GamesLibrarianIcons.steamGamesSortMethodLogoDescendingOrderIcon),
	NameAscendingOrder("nameAscendingOrder", GamesLibrarianIcons.steamGamesSortMethodNameAscendingOrderIcon),
	NameDescendingOrder("nameDescendingOrder", GamesLibrarianIcons.steamGamesSortMethodNameDescendingOrderIcon),
	ArgumentsAscendingOrder("argumentsAscendingOrder", GamesLibrarianIcons.steamGamesSortMethodArgumentsAscendingOrderIcon),
	ArgumentsDescendingOrder("argumentsDescendingOrder", GamesLibrarianIcons.steamGamesSortMethodArgumentsDescendingOrderIcon),
	SteamLaunchMethodAscendingOrder("steamLaunchMethodAscendingOrder", GamesLibrarianIcons.steamGamesSortMethodSteamLaunchMethodAscendingOrderIcon),
	SteamLaunchMethodDescendingOrder("steamLaunchMethodDescendingOrder", GamesLibrarianIcons.steamGamesSortMethodSteamLaunchMethodDescendingOrderIcon),
	HoursLast2WeeksAscendingOrder("hoursLast2WeeksdAscendingOrder", GamesLibrarianIcons.steamGamesSortMethodHoursLast2WeeksAscendingOrderIcon),
	HoursLast2WeeksDescendingOrder("hoursLast2WeeksdDescendingOrder", GamesLibrarianIcons.steamGamesSortMethodHoursLast2WeeksDescendingOrderIcon),
	HoursOnRecordAscendingOrder("hoursOnRecordAscendingOrder", GamesLibrarianIcons.steamGamesSortMethodHoursOnRecordAscendingOrderIcon),
	HoursOnRecordDescendingOrder("hoursOnRecordDescendingOrder", GamesLibrarianIcons.steamGamesSortMethodHoursOnRecordDescendingOrderIcon),
	AppIdAscendingOrder("appIdAscendingOrder", GamesLibrarianIcons.steamGamesSortMethodAppIdAscendingOrderIcon),
	AppIdDescendingOrder("appIdDescendingOrder", GamesLibrarianIcons.steamGamesSortMethodAppIdDescendingOrderIcon),
	StoreLinkAscendingOrder("storeLinkAscendingOrder", GamesLibrarianIcons.steamGamesSortMethodStoreLinkAscendingOrderIcon),
	StoreLinkDescendingOrder("storeLinkDescendingOrder", GamesLibrarianIcons.steamGamesSortMethodStoreLinkDescendingOrderIcon),
	GlobalStatsLinkAscendingOrder("globalStatsLinkAscendingOrder", GamesLibrarianIcons.steamGamesSortMethodGlobalStatsLinkAscendingOrderIcon),
	GlobalStatsLinkDescendingOrder("globalStatsLinkDescendingOrder", GamesLibrarianIcons.steamGamesSortMethodGlobalStatsLinkDescendingOrderIcon),
	StatsLinkAscendingOrder("statsLinkAscendingOrder", GamesLibrarianIcons.steamGamesSortMethodStatsLinkAscendingOrderIcon),
	StatsLinkDescendingOrder("statsLinkDescendingOrder", GamesLibrarianIcons.steamGamesSortMethodStatsLinkDescendingOrderIcon),
	AchievementsRatioAscendingOrder("achievementsRatioAscendingOrder", GamesLibrarianIcons.steamAchievementsListAchievementsRatioAscendingOrderIcon),
	AchievementsRatioDescendingOrder("achievementsRatioDescendingOrder", GamesLibrarianIcons.steamAchievementsListAchievementsRatioDescendingOrderIcon);
	
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
