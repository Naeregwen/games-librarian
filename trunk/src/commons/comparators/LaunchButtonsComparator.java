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
import components.containers.remotes.LaunchButton;

/**
 * @author Naeregwen
 *
 */
public class LaunchButtonsComparator implements Comparator<LaunchButton> {

	ComparisonDirection comparisonDirection;
	SteamGamesSortMethod librarySortMethod;
	
	public LaunchButtonsComparator(ComparisonDirection comparisonDirection, SteamGamesSortMethod librarySortMethod) {
		this.comparisonDirection = comparisonDirection;
		this.librarySortMethod = librarySortMethod;
	}

	@Override
	public int compare(LaunchButton launchButton1, LaunchButton launchButton2) {
		int comparisonResult = 0;
		SteamGame steamGame1 = launchButton1 != null ? launchButton1.getGame() : null;
		SteamGame steamGame2 = launchButton2 != null ? launchButton2.getGame() : null;
		comparisonResult = (new SteamGamesComparator(ComparisonDirection.Ascendant, librarySortMethod)).compare(steamGame1, steamGame2);
		return comparisonDirection == ComparisonDirection.Ascendant ? comparisonResult : -comparisonResult;
	}

}
