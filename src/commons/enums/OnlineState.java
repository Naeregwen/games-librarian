/**
 * 
 */
package commons.enums;

/**
 * @author Naeregwen
 *
 */
public enum OnlineState {

	ONLINE ("online", "/images/icons/status_green.png"),
	INGAME ("in-game", "/images/icons/status_busy.png"),
	OFFLINE ("offline", "/images/icons/status_offline.png"),
	UNKNOWN ("unknown", "/images/icons/status_invisible.png");
	
	String steamLabel;
	String iconPath;
	
	OnlineState(String steamLabel, String iconPath) {
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
