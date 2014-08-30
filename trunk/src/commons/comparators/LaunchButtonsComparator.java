/**
 * 
 */
package commons.comparators;

import java.util.Comparator;

import commons.api.SteamGame;
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
