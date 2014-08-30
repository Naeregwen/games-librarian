/**
 * 
 */
package commons.enums;

/**
 * @author Naeregwen
 *
 */
public enum SteamAchievementsListsSortMethod {

	InitialAscendingOrder("initialOrder", "/images/icons/arrow_switch_bluegreen.png"),
	NameAscendingOrder("nameAscendingOrder", "/images/icons/sort_ascending.png"),
	NameDescendingOrder("nameDescendingOrder", "/images/icons/sort_descending.png"),
	AchievementsRatioAscendingOrder("achievementsRatioAscendingOrder", "/images/icons/sort_ascending_achievements.png"),
	AchievementsRatioDescendingOrder("achievementsRatioDescendingOrder", "/images/icons/sort_descending_achievements.png");
	
	String labelKey;
	String iconPath;
	
	SteamAchievementsListsSortMethod(String labelKey, String iconPath) {
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
