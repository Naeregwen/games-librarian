/**
 * 
 */
package commons.enums;

/**
 * @author Naeregwen
 *
 */
public enum SteamFriendsSortMethod {

	InitialAscendingOrder("initialOrder", "/images/icons/arrow_switch_bluegreen.png"),
	NameAscendingOrder("nameAscendingOrder", "/images/icons/sort_ascending.png"),
	NameDescendingOrder("nameDescendingOrder", "/images/icons/sort_descending.png"),
	LogoAscendingOrder("logoAscendingOrder", "/images/icons/sort_ascending_image.png"),
	LogoDescendingOrder("logoDescendingOrder", "/images/icons/sort_descending_image.png"),
	OnlineStateAscendingOrder("onlineStateAscendingOrder", "/images/icons/sort_ascending_status.png"),
	OnlineStateDescendingOrder("onlineStateDescendingOrder", "/images/icons/sort_descending_status.png"),
	StateMessageAscendingOrder("stateMessageAscendingOrder", "/images/icons/sort_ascending_message_status.png"),
	StateMessageDescendingOrder("stateMessageDescendingOrder", "/images/icons/sort_descending_message_status.png"),
	SteamId64AscendingOrder("steamId64AscendingOrder", "/images/icons/sort_ascending_key.png"),
	SteamId64DescendingOrder("steamId64DescendingOrder", "/images/icons/sort_descending_key.png");

	String labelKey;
	String iconPath;
	
	SteamFriendsSortMethod(String labelKey, String iconPath) {
		this.labelKey = labelKey;
		this.iconPath = iconPath;
	}

	/**
	 * @return the labelKey
	 */
	public String getLabelKey() {
		return labelKey;
	}

	/**
	 * @return the iconPath
	 */
	public String getIconPath() {
		return iconPath;
	}
	
}