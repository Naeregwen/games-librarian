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

import commons.api.SteamAchievement;
import commons.enums.SteamAchievementsSortMethod;

/**
 * @author Naeregwen
 * 
 */
public class SteamAchievementsComparator implements Comparator<SteamAchievement> {

	private SteamAchievementsSortMethod steamAchievementsSortMethod;
	private boolean ascending = true;

	public SteamAchievementsComparator(SteamAchievementsSortMethod steamAchievementsSortMethod) {
		this.steamAchievementsSortMethod = steamAchievementsSortMethod;
	}
	
	/**
	 * @return the steamAchievementsSortMethod
	 */
	public SteamAchievementsSortMethod getSteamAchievementsSortMethod() {
		return steamAchievementsSortMethod;
	}

	/**
	 * @param steamAchievementsSortMethod the steamAchievementsSortMethod to set
	 */
	public void setSteamAchievementsSortMethod(SteamAchievementsSortMethod steamAchievementsSortMethod) {
		this.steamAchievementsSortMethod = steamAchievementsSortMethod;
	}

	/**
	 * @return the ascending
	 */
	public boolean isAscending() {
		return ascending;
	}

	/**
	 * @param ascending the ascending to set
	 */
	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}

	@Override
	public int compare(SteamAchievement steamAchievement1, SteamAchievement steamAchievement2) {
		int comparisonResult = 0;
		switch (steamAchievementsSortMethod) {
		case InitialAscendingOrder:
			comparisonResult = steamAchievement1.getInitialPosition().compareTo(steamAchievement2.getInitialPosition());
			break;
		case NameAscendingOrder:
		case NameDescendingOrder:
			comparisonResult = steamAchievement1.getName().compareTo(steamAchievement2.getName());
			break;
		case UnlockDateAscendingOrder:
		case UnlockDateDescendingOrder:
			String unlockTimeString1 = steamAchievement1.getUnlockTimestamp();
			String unlockTimeString2 = steamAchievement2.getUnlockTimestamp();
			if (unlockTimeString1 == null)
				if (unlockTimeString2 == null)
					// Restore initial position when time stamps are empty
					// Ensure initial position stability (does not respect asked order)
					comparisonResult = ascending ? 
							steamAchievement2.getInitialPosition().compareTo(steamAchievement1.getInitialPosition()) :
								steamAchievement1.getInitialPosition().compareTo(steamAchievement2.getInitialPosition());
				else
					comparisonResult = ascending ? 1 : -1; // Null values are always positioned on last results
			else
				if (unlockTimeString2 == null)
					comparisonResult =  ascending ? -1 : 1;
				else {
					Integer unlockTimestamp1 = null;
					Integer unlockTimestamp2 = null;
					try {
						unlockTimestamp1 = Integer.parseInt(unlockTimeString1);
					} catch (NumberFormatException nfe) {}
					try {
						unlockTimestamp2 = Integer.parseInt(unlockTimeString2);
					} catch (NumberFormatException nfe) {}
					if (unlockTimestamp1 == null)
						if (unlockTimestamp2 == null)
							// Restore initial position when time stamps are empty
							// Ensure initial position stability (does not respect asked order)
							comparisonResult = ascending ? 
									steamAchievement2.getInitialPosition().compareTo(steamAchievement1.getInitialPosition()) :
										steamAchievement1.getInitialPosition().compareTo(steamAchievement2.getInitialPosition());
						else
							comparisonResult = ascending ? 1 : -1;
					else
						if (unlockTimestamp2 == null)
							comparisonResult = ascending ? -1 : 1;
						else
							comparisonResult = steamAchievement1.getUnlockTimestamp().compareTo(steamAchievement2.getUnlockTimestamp());
					
					
				}
			break;
		}
		return ascending ? comparisonResult : -comparisonResult;
	}

}
