/**
 * 
 */
package commons.enums;

/**
 * @author Naeregwen
 *
 */
public enum SteamGamesDisplayMode {

	LaunchPane ("steamGamesDisplayModeLaunchPane", "/images/icons/application_view_tile.png"),
	ConfigurationPane ("steamGamesDisplayModeConfigurationPane", "/images/icons/application_view_list.png");
	
	String labelKey;
	String iconPath;
	
	SteamGamesDisplayMode(String labelKey, String iconPath) {
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
