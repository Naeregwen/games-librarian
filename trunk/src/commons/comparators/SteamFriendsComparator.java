package commons.comparators;

import java.util.Comparator;

import commons.api.SteamProfile;
import commons.enums.SteamFriendsSortMethod;

/**
 * @author Naeregwen
 *
 */
public class SteamFriendsComparator implements Comparator<SteamProfile> {

	ComparisonDirection comparisonDirection;
	SteamFriendsSortMethod steamFriendsSortMethod;
	
	public SteamFriendsComparator(ComparisonDirection comparisonDirection, SteamFriendsSortMethod steamFriendsSortMethod) {
		this.comparisonDirection = comparisonDirection;
		this.steamFriendsSortMethod = steamFriendsSortMethod;
	}

	@Override
	public int compare(SteamProfile steamProfile1, SteamProfile steamProfile2) {
		int comparisonResult = 0;
		switch (steamFriendsSortMethod) {
		case InitialAscendingOrder:
			comparisonResult = steamProfile1.getInitialPosition().compareTo(steamProfile2.getInitialPosition());
			break;
		case NameAscendingOrder:
		case NameDescendingOrder:
			if (steamProfile1.getSteamID() != null && !steamProfile1.getSteamID().equals(""))
				if (steamProfile2.getSteamID() != null && !steamProfile2.getSteamID().equals(""))
					comparisonResult = steamProfile1.getSteamID().compareTo(steamProfile2.getSteamID());
				else
					// 1
					comparisonResult = comparisonDirection == ComparisonDirection.Ascendant ?
							steamProfile1.getInitialPosition().compareTo(steamProfile2.getInitialPosition()) : 
								steamProfile2.getInitialPosition().compareTo(steamProfile1.getInitialPosition());
			else
				if (steamProfile2.getSteamID() != null && !steamProfile2.getSteamID().equals(""))
					// -1
					comparisonResult = comparisonDirection == ComparisonDirection.Ascendant ?
							steamProfile1.getInitialPosition().compareTo(steamProfile2.getInitialPosition()) : 
								steamProfile2.getInitialPosition().compareTo(steamProfile1.getInitialPosition());
				else
					comparisonResult = 0;
			break;
		case LogoAscendingOrder:
		case LogoDescendingOrder:
			if (steamProfile1.getAvatarIcon() != null && !steamProfile1.getAvatarIcon().equals(""))
				if (steamProfile2.getAvatarIcon() != null && !steamProfile2.getAvatarIcon().equals(""))
					comparisonResult = steamProfile1.getAvatarIcon().compareTo(steamProfile2.getAvatarIcon());
				else
					// 1
					comparisonResult = comparisonDirection == ComparisonDirection.Ascendant ?
							steamProfile1.getInitialPosition().compareTo(steamProfile2.getInitialPosition()) : 
								steamProfile2.getInitialPosition().compareTo(steamProfile1.getInitialPosition());
			else
				if (steamProfile2.getAvatarIcon() != null && !steamProfile2.getAvatarIcon().equals(""))
					// -1
					comparisonResult = comparisonDirection == ComparisonDirection.Ascendant ? 
							steamProfile1.getInitialPosition().compareTo(steamProfile2.getInitialPosition()) : 
								steamProfile2.getInitialPosition().compareTo(steamProfile1.getInitialPosition());
				else
					comparisonResult = 0;
			break;
		case OnlineStateAscendingOrder:
		case OnlineStateDescendingOrder:
			if (steamProfile1.getOnlineState() != null && !steamProfile1.getOnlineState().equals(""))
				if (steamProfile2.getOnlineState() != null && !steamProfile2.getOnlineState().equals(""))
					comparisonResult = steamProfile1.getOnlineState().compareTo(steamProfile2.getOnlineState());
				else
					// 1
					comparisonResult = comparisonDirection == ComparisonDirection.Ascendant ?
							steamProfile1.getInitialPosition().compareTo(steamProfile2.getInitialPosition()) : 
								steamProfile2.getInitialPosition().compareTo(steamProfile1.getInitialPosition());
			else
				if (steamProfile2.getOnlineState() != null && !steamProfile2.getOnlineState().equals(""))
					// -1
					comparisonResult = comparisonDirection == ComparisonDirection.Ascendant ?
							steamProfile1.getInitialPosition().compareTo(steamProfile2.getInitialPosition()) : 
								steamProfile2.getInitialPosition().compareTo(steamProfile1.getInitialPosition());
				else
					comparisonResult = 0;
			break;
		case StateMessageAscendingOrder:
		case StateMessageDescendingOrder:
			if (steamProfile1.getStateMessage() != null && !steamProfile1.getStateMessage().equals(""))
				if (steamProfile2.getStateMessage() != null && !steamProfile2.getStateMessage().equals(""))
					comparisonResult = steamProfile1.getStateMessage().compareTo(steamProfile2.getStateMessage());
				else
					// 1
					comparisonResult = comparisonDirection == ComparisonDirection.Ascendant ?
							steamProfile1.getInitialPosition().compareTo(steamProfile2.getInitialPosition()) : 
								steamProfile2.getInitialPosition().compareTo(steamProfile1.getInitialPosition());
			else
				if (steamProfile2.getStateMessage() != null && !steamProfile2.getStateMessage().equals(""))
					// -1
					comparisonResult = comparisonDirection == ComparisonDirection.Ascendant ?
							steamProfile1.getInitialPosition().compareTo(steamProfile2.getInitialPosition()) : 
								steamProfile2.getInitialPosition().compareTo(steamProfile1.getInitialPosition());
				else
					comparisonResult = 0;
			break;
		case SteamId64AscendingOrder:
		case SteamId64DescendingOrder:
			comparisonResult = steamProfile1.getSteamID64().compareTo(steamProfile2.getSteamID64());
			break;
		}
		
		return comparisonDirection == ComparisonDirection.Ascendant ? comparisonResult : -comparisonResult;
	}

}
