/**
 * 
 */
package commons.enums;

/**
 * @author Naeregwen
 *
 */
public enum PrivacyState {

	PUBLIC ("public", "/images/icons/user_green.png"),
	FRIENDSONLY ("friendsonly", "/images/icons/user_blue.png"),
	FRIENDSFRIENDSONLY ("friendsfriendsonly", "/images/icons/user_orange.png"),
	PRIVATE ("private", "/images/icons/user_gray.png"),
	UNKNOWN ("unknown", "/images/icons/user_red.png");
	
	String steamLabel;
	String iconPath;
	
	PrivacyState(String steamLabel, String iconPath) {
		this.steamLabel = steamLabel;
		this.iconPath = iconPath;
	}

	/**
	 * @return the steamLabel
	 */
	public String getSteamLabel() {
		return steamLabel;
	}

	/**
	 * @return the iconPath
	 */
	public String getIconPath() {
		return iconPath;
	}
	
}
