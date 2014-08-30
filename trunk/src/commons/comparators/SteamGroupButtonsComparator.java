/**
 * 
 */
package commons.comparators;

import java.util.Comparator;

import commons.enums.SteamGroupsSortMethod;
import components.containers.remotes.SteamGroupButton;

/**
 * @author Naeregwen
 *
 */
public class SteamGroupButtonsComparator implements Comparator<SteamGroupButton> {

	ComparisonDirection comparisonDirection;
	SteamGroupsSortMethod steamGroupsSortMethod;
	
	public SteamGroupButtonsComparator(ComparisonDirection comparisonDirection, SteamGroupsSortMethod steamGroupsSortMethod) {
		this.comparisonDirection = comparisonDirection;
		this.steamGroupsSortMethod = steamGroupsSortMethod;
	}

	
	@Override
	public int compare(SteamGroupButton steamGroupButton1, SteamGroupButton steamGroupButton2) {		
		int comparisonResult = 0;
	
		switch (steamGroupsSortMethod) {
		case InitialAscendingOrder:
			comparisonResult = steamGroupButton1.getSteamGroup().getInitialPosition().compareTo(steamGroupButton2.getSteamGroup().getInitialPosition());
			break;
		case NameAscendingOrder:
		case NameDescendingOrder:
			if (steamGroupButton1.getSteamGroup().getGroupName() != null && !steamGroupButton1.getSteamGroup().getGroupName().equals("")) {
				if (steamGroupButton2.getSteamGroup().getGroupName() != null && !steamGroupButton2.getSteamGroup().getGroupName().equals(""))
					comparisonResult = steamGroupButton1.getSteamGroup().getGroupName().toLowerCase().compareTo(steamGroupButton2.getSteamGroup().getGroupName().toLowerCase());
				else
					comparisonResult = steamGroupButton1.getSteamGroup().getInitialPosition().compareTo(steamGroupButton2.getSteamGroup().getInitialPosition());
			} else {
				if (steamGroupButton2.getSteamGroup().getGroupName() != null && !steamGroupButton2.getSteamGroup().getGroupName().equals(""))
					comparisonResult = steamGroupButton1.getSteamGroup().getInitialPosition().compareTo(steamGroupButton1.getSteamGroup().getInitialPosition());
				else
					comparisonResult = 0;
			}
			break;
		case LogoAscendingOrder:
		case LogoDescendingOrder:
			if (steamGroupButton1.getSteamGroup().getAvatarIcon() != null && !steamGroupButton1.getSteamGroup().getAvatarIcon().equals("")) {
				if (steamGroupButton2.getSteamGroup().getAvatarIcon() != null && !steamGroupButton2.getSteamGroup().getAvatarIcon().equals(""))
					comparisonResult = steamGroupButton1.getSteamGroup().getAvatarIcon().toLowerCase().compareTo(steamGroupButton2.getSteamGroup().getAvatarIcon().toLowerCase());
				else
					comparisonResult = steamGroupButton1.getSteamGroup().getInitialPosition().compareTo(steamGroupButton2.getSteamGroup().getInitialPosition());
			} else {
				if (steamGroupButton2.getSteamGroup().getAvatarIcon() != null && !steamGroupButton2.getSteamGroup().getAvatarIcon().equals(""))
					comparisonResult = steamGroupButton1.getSteamGroup().getInitialPosition().compareTo(steamGroupButton1.getSteamGroup().getInitialPosition());
				else
					comparisonResult = 0;
			}
			break;
		case HeadlineAscendingOrder:
		case HeadlineDescendingOrder:
			if (steamGroupButton1.getSteamGroup().getHeadline() != null && !steamGroupButton1.getSteamGroup().getHeadline().equals("")) {
				if (steamGroupButton2.getSteamGroup().getHeadline() != null && !steamGroupButton2.getSteamGroup().getHeadline().equals(""))
					comparisonResult = steamGroupButton1.getSteamGroup().getHeadline().toLowerCase().compareTo(steamGroupButton2.getSteamGroup().getHeadline().toLowerCase());
				else
					comparisonResult = steamGroupButton1.getSteamGroup().getInitialPosition().compareTo(steamGroupButton2.getSteamGroup().getInitialPosition());
			} else {
				if (steamGroupButton2.getSteamGroup().getHeadline() != null && !steamGroupButton2.getSteamGroup().getHeadline().equals(""))
					comparisonResult = steamGroupButton1.getSteamGroup().getInitialPosition().compareTo(steamGroupButton1.getSteamGroup().getInitialPosition());
				else
					comparisonResult = 0;
			}
			break;
		case SummaryAscendingOrder:
		case SummaryDescendingOrder:
			if (steamGroupButton1.getSteamGroup().getSummary() != null && !steamGroupButton1.getSteamGroup().getSummary().equals("")) {
				if (steamGroupButton2.getSteamGroup().getSummary() != null && !steamGroupButton2.getSteamGroup().getSummary().equals(""))
					comparisonResult = steamGroupButton1.getSteamGroup().getSummary().toLowerCase().compareTo(steamGroupButton2.getSteamGroup().getSummary().toLowerCase());
				else
					comparisonResult = steamGroupButton1.getSteamGroup().getInitialPosition().compareTo(steamGroupButton2.getSteamGroup().getInitialPosition());
			} else {
				if (steamGroupButton2.getSteamGroup().getSummary() != null && !steamGroupButton2.getSteamGroup().getSummary().equals(""))
					comparisonResult = steamGroupButton1.getSteamGroup().getInitialPosition().compareTo(steamGroupButton1.getSteamGroup().getInitialPosition());
				else
					comparisonResult = 0;
			}
			break;
		case SteamId64AscendingOrder:
		case SteamId64DescendingOrder:
			comparisonResult = steamGroupButton1.getSteamGroup().getGroupID64().compareTo(steamGroupButton2.getSteamGroup().getGroupID64());
			break;
		}
		
		return comparisonDirection == ComparisonDirection.Ascendant ? comparisonResult : -comparisonResult;
	}

}
