package commons.comparators;

import java.util.Comparator;

import commons.api.SteamGroup;
import commons.enums.SteamGroupsSortMethod;

public class SteamGroupsComparator implements Comparator<SteamGroup> {

	ComparisonDirection comparisonDirection;
	SteamGroupsSortMethod steamGroupsSortMethod;
	
	public SteamGroupsComparator(ComparisonDirection comparisonDirection, SteamGroupsSortMethod steamGroupsSortMethod) {
		this.comparisonDirection = comparisonDirection;
		this.steamGroupsSortMethod = steamGroupsSortMethod;
	}

	@Override
	public int compare(SteamGroup steamGroup1, SteamGroup steamGroup2) {
		int comparisonResult = 0;
		switch (steamGroupsSortMethod) {
		case InitialAscendingOrder:
			comparisonResult = steamGroup1.getInitialPosition().compareTo(steamGroup2.getInitialPosition());
			break;
		case NameAscendingOrder:
		case NameDescendingOrder:
			if (steamGroup1.getGroupName() != null && !steamGroup1.getGroupName().equals(""))
				if (steamGroup2.getGroupName() != null && !steamGroup2.getGroupName().equals(""))
					comparisonResult = steamGroup1.getGroupName().compareTo(steamGroup2.getGroupName());
				else
					// 1
					comparisonResult = comparisonDirection == ComparisonDirection.Ascendant ?
							steamGroup1.getInitialPosition().compareTo(steamGroup2.getInitialPosition()) : 
								steamGroup2.getInitialPosition().compareTo(steamGroup1.getInitialPosition());
			else
				if (steamGroup2.getGroupName() != null && !steamGroup2.getGroupName().equals(""))
					// -1
					comparisonResult = comparisonDirection == ComparisonDirection.Ascendant ?
							steamGroup1.getInitialPosition().compareTo(steamGroup2.getInitialPosition()) : 
								steamGroup2.getInitialPosition().compareTo(steamGroup1.getInitialPosition());
				else
					comparisonResult = 0;
			break;
		case LogoAscendingOrder:
		case LogoDescendingOrder:
			if (steamGroup1.getAvatarIcon() != null && !steamGroup1.getAvatarIcon().equals(""))
				if (steamGroup2.getAvatarIcon() != null && !steamGroup2.getAvatarIcon().equals(""))
					comparisonResult = steamGroup1.getAvatarIcon().compareTo(steamGroup2.getAvatarIcon());
				else
					// 1
					comparisonResult = comparisonDirection == ComparisonDirection.Ascendant ?
							steamGroup1.getInitialPosition().compareTo(steamGroup2.getInitialPosition()) : 
								steamGroup2.getInitialPosition().compareTo(steamGroup1.getInitialPosition());
			else
				if (steamGroup2.getAvatarIcon() != null && !steamGroup2.getAvatarIcon().equals(""))
					// -1
					comparisonResult = comparisonDirection == ComparisonDirection.Ascendant ? 
							steamGroup1.getInitialPosition().compareTo(steamGroup2.getInitialPosition()) : 
								steamGroup2.getInitialPosition().compareTo(steamGroup1.getInitialPosition());
				else
					comparisonResult = 0;
			break;
		case HeadlineAscendingOrder:
		case HeadlineDescendingOrder:
			if (steamGroup1.getHeadline() != null && !steamGroup1.getHeadline().equals(""))
				if (steamGroup2.getHeadline() != null && !steamGroup2.getHeadline().equals(""))
					comparisonResult = steamGroup1.getHeadline().compareTo(steamGroup2.getHeadline());
				else
					// 1
					comparisonResult = comparisonDirection == ComparisonDirection.Ascendant ?
							steamGroup1.getInitialPosition().compareTo(steamGroup2.getInitialPosition()) : 
								steamGroup2.getInitialPosition().compareTo(steamGroup1.getInitialPosition());
			else
				if (steamGroup2.getHeadline() != null && !steamGroup2.getHeadline().equals(""))
					// -1
					comparisonResult = comparisonDirection == ComparisonDirection.Ascendant ?
							steamGroup1.getInitialPosition().compareTo(steamGroup2.getInitialPosition()) : 
								steamGroup2.getInitialPosition().compareTo(steamGroup1.getInitialPosition());
				else
					comparisonResult = 0;
			break;
		case SummaryAscendingOrder:
		case SummaryDescendingOrder:
			if (steamGroup1.getSummary() != null && !steamGroup1.getSummary().equals(""))
				if (steamGroup2.getSummary() != null && !steamGroup2.getSummary().equals(""))
					comparisonResult = steamGroup1.getSummary().compareTo(steamGroup2.getSummary());
				else
					// 1
					comparisonResult = comparisonDirection == ComparisonDirection.Ascendant ?
							steamGroup1.getInitialPosition().compareTo(steamGroup2.getInitialPosition()) : 
								steamGroup2.getInitialPosition().compareTo(steamGroup1.getInitialPosition());
			else
				if (steamGroup2.getSummary() != null && !steamGroup2.getSummary().equals(""))
					// -1
					comparisonResult = comparisonDirection == ComparisonDirection.Ascendant ?
							steamGroup1.getInitialPosition().compareTo(steamGroup2.getInitialPosition()) : 
								steamGroup2.getInitialPosition().compareTo(steamGroup1.getInitialPosition());
				else
					comparisonResult = 0;
			break;
		case SteamId64AscendingOrder:
		case SteamId64DescendingOrder:
			comparisonResult = steamGroup1.getGroupID64().compareTo(steamGroup2.getGroupID64());
			break;
		}
		
		return comparisonDirection == ComparisonDirection.Ascendant ? comparisonResult : -comparisonResult;
	}

}
