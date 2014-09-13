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

import commons.api.SteamAchievementsList;
import commons.enums.SteamAchievementsListsSortMethod;

/**
 * @author Naeregwen
 *
 */
public class SteamAchievementsListsComparator implements Comparator<SteamAchievementsList> {

	private SteamAchievementsListsSortMethod steamAchievementsListsSortMethod;
	private boolean ascending = true;

	public SteamAchievementsListsComparator(SteamAchievementsListsSortMethod steamAchievementsColumnsSortMethod) {
		this.steamAchievementsListsSortMethod = steamAchievementsColumnsSortMethod;
	}
	
	/**
	 * @param steamAchievementsListsSortMethod the steamAchievementsListsSortMethod to set
	 */
	public void setSteamAchievementsListSortMethod(SteamAchievementsListsSortMethod steamAchievementsListsSortMethod) {
		this.steamAchievementsListsSortMethod = steamAchievementsListsSortMethod;
	}

	/**
	 * @param ascending the ascending to set
	 */
	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}

	@Override
	public int compare(SteamAchievementsList steamAchievementsList1, SteamAchievementsList steamAchievementsList2) {
		int comparisonResult = 0;
		switch (steamAchievementsListsSortMethod) {
		case InitialAscendingOrder:
			comparisonResult = steamAchievementsList1.getInitialPosition().compareTo(steamAchievementsList2.getInitialPosition());
			break;
		case NameAscendingOrder:
		case NameDescendingOrder:
			comparisonResult = steamAchievementsList1.getPlayerSteamID().toLowerCase().compareTo(steamAchievementsList2.getPlayerSteamID().toLowerCase());
			break;
		case AchievementsRatioAscendingOrder:
		case AchievementsRatioDescendingOrder:
			comparisonResult = steamAchievementsList1.getAchievementsRatio().compareTo(steamAchievementsList2.getAchievementsRatio());
			break;
		}
		return ascending ? comparisonResult : -comparisonResult;
	}

}
