/**
 * 
 */
package commons.enums;

/**
 * @author Naeregwen
 *
 */
public enum SteamGamesSortMethod {

	InitialAscendingOrder("initialOrder", "/images/icons/arrow_switch_bluegreen.png"),
	LogoAscendingOrder("logoAscendingOrder", "/images/icons/sort_ascending_image.png"),
	LogoDescendingOrder("logoDescendingOrder", "/images/icons/sort_descending_image.png"),
	NameAscendingOrder("nameAscendingOrder", "/images/icons/sort_ascending.png"),
	NameDescendingOrder("nameDescendingOrder", "/images/icons/sort_descending.png"),
	ArgumentsAscendingOrder("argumentsAscendingOrder", "/images/icons/sort_ascending_wrench.png"),
	ArgumentsDescendingOrder("argumentsDescendingOrder", "/images/icons/sort_descending_wrench.png"),
	SteamLaunchMethodAscendingOrder("steamLaunchMethodAscendingOrder", "/images/icons/sort_ascending_lightning.png"),
	SteamLaunchMethodDescendingOrder("steamLaunchMethodDescendingOrder", "/images/icons/sort_descending_lightning.png"),
	HoursLast2WeeksAscendingOrder("hoursLast2WeeksdAscendingOrder", "/images/icons/sort_ascending_dates.png"),
	HoursLast2WeeksDescendingOrder("hoursLast2WeeksdDescendingOrder", "/images/icons/sort_descending_dates.png"),
	HoursOnRecordAscendingOrder("hoursOnRecordAscendingOrder", "/images/icons/sort_ascending_hours.png"),
	HoursOnRecordDescendingOrder("hoursOnRecordDescendingOrder", "/images/icons/sort_descending_hours.png"),
	AppIdAscendingOrder("appIdAscendingOrder", "/images/icons/sort_ascending_key.png"),
	AppIdDescendingOrder("appIdDescendingOrder", "/images/icons/sort_descending_key.png"),
	StoreLinkAscendingOrder("storeLinkAscendingOrder", "/images/icons/sort_ascending_cart.png"),
	StoreLinkDescendingOrder("storeLinkDescendingOrder", "/images/icons/sort_descending_cart.png"),
	GlobalStatsLinkAscendingOrder("globalStatsLinkAscendingOrder", "/images/icons/sort_ascending_chart_line.png"),
	GlobalStatsLinkDescendingOrder("globalStatsLinkDescendingOrder", "/images/icons/sort_descending_chart_line.png"),
	StatsLinkAscendingOrder("statsLinkAscendingOrder", "/images/icons/sort_ascending_chart_bar.png"),
	StatsLinkDescendingOrder("statsLinkDescendingOrder", "/images/icons/sort_descending_chart_bar.png");
	
	String labelKey;
	String iconPath;
	
	SteamGamesSortMethod(String labelKey, String iconPath) {
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
