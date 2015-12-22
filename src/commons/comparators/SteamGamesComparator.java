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

import commons.api.SteamGame;
import commons.enums.ComparisonDirection;
import commons.enums.SteamGamesSortMethod;

/**
 * @author Naeregwen
 *
 */
public class SteamGamesComparator implements Comparator<SteamGame> {

	ComparisonDirection comparisonDirection;
	SteamGamesSortMethod librarySortMethod;
	
	public SteamGamesComparator(ComparisonDirection comparisonDirection, SteamGamesSortMethod librarySortMethod) {
		this.comparisonDirection = comparisonDirection;
		this.librarySortMethod = librarySortMethod;
	}

	private int compareString(String string1, String string2) {
		if (string1 != null && string2 != null)
			return string1.compareTo(string2);
		else if (string1 != null)
			return 1;
		else if (string2 != null)
			return -1;
		else
			return 0;
	}
	
	private int compareNumbers(SteamGame steamGame1, SteamGame steamGame2, SteamGamesSortMethod steamGamesSortMethod) {
		
		int comparisonResult = 0;
		
		boolean isGameTime1Valid = false;
		boolean isGameTime2Valid = false;
		
		Double gameTime1 = null;
		Double gameTime2 = null;
		
		String gameName1 = null;
		String gameName2 = null;
		
		Double steamAchievementsRatio1 = steamGame1.getAchievementsRatio();
		Double steamAchievementsRatio2 = steamGame2.getAchievementsRatio();
		
		boolean steamGamesSortMethodIsHoursOnRecord = steamGamesSortMethod.equals(SteamGamesSortMethod.HoursOnRecordAscendingOrder) || steamGamesSortMethod.equals(SteamGamesSortMethod.HoursOnRecordDescendingOrder) ? true : false;
		boolean steamGamesSortMethodIsHoursLast2Weeks = steamGamesSortMethod.equals(SteamGamesSortMethod.HoursLast2WeeksAscendingOrder) || steamGamesSortMethod.equals(SteamGamesSortMethod.HoursLast2WeeksDescendingOrder) ? true : false;
		boolean steamGamesSortMethodIsAppId = steamGamesSortMethod.equals(SteamGamesSortMethod.AppIdAscendingOrder) || steamGamesSortMethod.equals(SteamGamesSortMethod.AppIdDescendingOrder) ? true : false;
		boolean steamGamesSortMethodIsAchievementsRatio = steamGamesSortMethod.equals(SteamGamesSortMethod.AchievementsRatioAscendingOrder) || steamGamesSortMethod.equals(SteamGamesSortMethod.AchievementsRatioDescendingOrder) ? true : false;
		
		Boolean ascending = (steamGamesSortMethod.equals(SteamGamesSortMethod.HoursOnRecordAscendingOrder) 
				|| steamGamesSortMethod.equals(SteamGamesSortMethod.HoursLast2WeeksAscendingOrder)
				|| steamGamesSortMethod.equals(SteamGamesSortMethod.AppIdAscendingOrder)
				|| steamGamesSortMethod.equals(SteamGamesSortMethod.AchievementsRatioAscendingOrder));
		
		if (steamGame1 != null && (
				(steamGamesSortMethodIsHoursOnRecord && steamGame1.getHoursOnRecord() != null)
				|| (steamGamesSortMethodIsHoursLast2Weeks && steamGame1.getHoursLast2Weeks() != null)
				|| (steamGamesSortMethodIsAppId && steamGame1.getAppID() != null)
				|| (steamGamesSortMethodIsAchievementsRatio && steamAchievementsRatio1 != null))) {
			try {
				gameTime1 = steamGamesSortMethodIsAchievementsRatio ? steamAchievementsRatio1 : Double.parseDouble(
						steamGamesSortMethodIsHoursOnRecord ? steamGame1.getHoursOnRecord() : 
							(steamGamesSortMethodIsHoursLast2Weeks ? steamGame1.getHoursLast2Weeks() : steamGame1.getAppID()));
				if (gameTime1 != null && (!steamGamesSortMethodIsAchievementsRatio || steamAchievementsRatio1 != -1.0))
					isGameTime1Valid = true;
			} catch (NumberFormatException e) {}
		}
		
		if (steamGame2 != null && (
				(steamGamesSortMethodIsHoursOnRecord && steamGame2.getHoursOnRecord() != null)
				|| (steamGamesSortMethodIsHoursLast2Weeks && steamGame2.getHoursLast2Weeks() != null)
				|| (steamGamesSortMethodIsAppId && steamGame2.getAppID() != null)
				|| (steamGamesSortMethodIsAchievementsRatio && steamAchievementsRatio2 != null))) {
			try {
				gameTime2 = steamGamesSortMethodIsAchievementsRatio ? steamAchievementsRatio2 : Double.parseDouble(
						steamGamesSortMethodIsHoursOnRecord ? steamGame2.getHoursOnRecord() : 
							(steamGamesSortMethodIsHoursLast2Weeks ? steamGame2.getHoursLast2Weeks() : steamGame2.getAppID()));
				if (gameTime2 != null && (!steamGamesSortMethodIsAchievementsRatio || steamAchievementsRatio2 != -1.0))
					isGameTime2Valid = true;
			} catch (NumberFormatException e) {}
		}
		
		gameName1 = steamGame1 != null ? steamGame1.getName() : null;
		gameName2 = steamGame2 != null ? steamGame2.getName() : null;
		
		if (isGameTime1Valid && isGameTime2Valid) {
			comparisonResult = gameTime1.compareTo(gameTime2);
		} else if (isGameTime1Valid)
			comparisonResult = 1;
		else if (isGameTime2Valid)
			comparisonResult = -1;
		else if (gameName1 != null && gameName2 != null)
			comparisonResult = gameName1.compareTo(gameName2);
		else if (gameName1 != null)
			comparisonResult = 1;
		else if (gameName2 != null)
			comparisonResult = -1;
		else
			comparisonResult = 0;
		
		if (!isGameTime1Valid)
			if (!isGameTime2Valid)
				// Restore initial position when time stamps are empty
				// Ensure initial position stability (does not respect asked order)
				comparisonResult = compareString(gameName1, gameName2);
			else
				comparisonResult = ascending ? 1 : -1; // Null values are always positioned on last results
		else
			if (!isGameTime2Valid)
				comparisonResult = ascending ? -1 : 1;
			else
				comparisonResult = gameTime1.compareTo(gameTime2);				
				
		return comparisonResult;
	}
	
	@Override
	public int compare(SteamGame steamGame1, SteamGame steamGame2) {
		
		int comparisonResult = 0;
		System.out.println("librarySortMethod = " + librarySortMethod);
		switch (librarySortMethod) {
		
		case InitialAscendingOrder:
			comparisonResult = steamGame1.getInitialPosition().compareTo(steamGame2.getInitialPosition());
			break;
		case LogoAscendingOrder:
		case LogoDescendingOrder:
			comparisonResult =  compareString(steamGame1.getLogo(), steamGame2.getLogo());
			break;
		case NameAscendingOrder:
		case NameDescendingOrder:
			comparisonResult =  compareString(steamGame1.getName(), steamGame2.getName());
			break;
		case ArgumentsAscendingOrder:
		case ArgumentsDescendingOrder:
			comparisonResult = compareString(steamGame1.getArguments(), steamGame2.getArguments());
			break;
		case SteamLaunchMethodAscendingOrder:
		case SteamLaunchMethodDescendingOrder:
			comparisonResult = steamGame1.getSteamLaunchMethod().compareTo(steamGame2.getSteamLaunchMethod());
			break;
		case HoursLast2WeeksAscendingOrder:
		case HoursLast2WeeksDescendingOrder:
			comparisonResult = compareNumbers(steamGame1, steamGame2, librarySortMethod);
			break;
		case HoursOnRecordAscendingOrder:
		case HoursOnRecordDescendingOrder:
			comparisonResult = compareNumbers(steamGame1, steamGame2, librarySortMethod);
			break;
		case AppIdAscendingOrder:
		case AppIdDescendingOrder:
			comparisonResult = compareNumbers(steamGame1, steamGame2, librarySortMethod);
			break;
		case StoreLinkAscendingOrder:
		case StoreLinkDescendingOrder:
			comparisonResult =  compareString(steamGame1.getStoreLink(), steamGame2.getStoreLink());
			break;
		case GlobalStatsLinkAscendingOrder:
		case GlobalStatsLinkDescendingOrder:
			comparisonResult =  compareString(steamGame1.getGlobalStatsLink(), steamGame2.getGlobalStatsLink());
			break;
		case StatsLinkAscendingOrder:
		case StatsLinkDescendingOrder:
			comparisonResult =  compareString(steamGame1.getStatsLink(), steamGame2.getStatsLink());
			break;
		case AchievementsRatioAscendingOrder:
		case AchievementsRatioDescendingOrder:
			comparisonResult = compareNumbers(steamGame1, steamGame2, librarySortMethod);
			System.out.println("librarySortMethod = " + librarySortMethod + ", steamGame1 = " + (steamGame1.getAchievementsRatio() == null ? "null" : steamGame1.getAchievementsRatio()) + ", steamGame2 = " + (steamGame2.getAchievementsRatio() == null ? "null" : steamGame2.getAchievementsRatio()) + ", comparisonResult = " + comparisonResult);
			break;
		}
		return comparisonResult;
	}
}
