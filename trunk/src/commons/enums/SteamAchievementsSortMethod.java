/**
 * 
 */
package commons.enums;

/**
 * @author Naeregwen
 *
 */
public enum SteamAchievementsSortMethod {

	InitialAscendingOrder("initialOrder", "/images/icons/arrow_switch_bluegreen.png"),
	NameAscendingOrder("nameAscendingOrder", "/images/icons/sort_ascending.png"),
	NameDescendingOrder("nameDescendingOrder", "/images/icons/sort_descending.png"),
	UnlockDateAscendingOrder("unlockDateAscendingOrder", "/images/icons/sort_ascending_dates.png"),
	UnlockDateDescendingOrder("unlockDateDescendingOrder", "/images/icons/sort_descending_dates.png");
	
	String labelKey;
	String iconPath;
	
	SteamAchievementsSortMethod(String labelKey, String iconPath) {
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
