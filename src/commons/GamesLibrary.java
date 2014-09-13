/**
 * Copyright 2012-2014 Naeregwen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package commons;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import commons.enums.OnlineState;
import components.GamesLibrarian;

/**
 * @author Naeregwen
 *
 */
public class GamesLibrary {

	/**
	 * Application Menu Icons
	 */
	public static final ImageIcon fileMenuIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/disk.png"));
	public static final ImageIcon gotoMenuIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/tab.png"));
	public static final ImageIcon controlsMenuIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/wrench.png"));
	public static final ImageIcon libraryMenuIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/pictures_thumbs.png"));
	public static final ImageIcon gameMenuIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/controller.png"));
	public static final ImageIcon profileMenuIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/user_blue.png"));
	public static final ImageIcon optionsMenuIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/cog.png"));
	
	public static final ImageIcon libraryStatisticsMenuIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/chart_bar.png"));
	public static final ImageIcon libraryDisplayMode = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/layout.png"));
	public static final ImageIcon librarySortMethod = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/table_sort.png"));
	
	/**
	 * Application menu icons
	 */
	public static final ImageIcon gameChoiceIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/control_tick.png"));
	public static final ImageIcon oneGameChoiceIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/control_1.png"));
	public static final ImageIcon twoGamesChoiceIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/control_2.png"));
	public static final ImageIcon threeGamesChoiceIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/control_3.png"));
	
	public static final ImageIcon loadAllAchievements = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/medal_gold_add.png"));
	public static final ImageIcon unloadAllAchievements = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/medal_gold_delete.png"));
	public static final ImageIcon unableToloadAllAchievements = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/medal_gold_1.png"));

	public static final ImageIcon achievementsListInitialAscendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/arrow_switch_bluegreen.png"));
	public static final ImageIcon achievementsListNameAscendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_ascending.png"));
	public static final ImageIcon achievementsListNameDescendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_descending.png"));
	public static final ImageIcon achievementsListAchievementsRatioAscendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_ascending_achievements.png"));
	public static final ImageIcon achievementsListAchievementsRatioDescendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_descending_achievements.png"));

	public static final ImageIcon achievementsInitialAscendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/arrow_switch_bluegreen.png"));
	public static final ImageIcon achievementsNameAscendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_ascending.png"));
	public static final ImageIcon achievementsNameDescendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_descending.png"));
	public static final ImageIcon achievementsUnlockDateAscendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_ascending_dates.png"));
	public static final ImageIcon achievementsUnlockDateDescendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_descending_dates.png"));
	
	public static final ImageIcon steamFriendsDisplayModeListIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/application_view_list.png"));
	public static final ImageIcon steamFriendsDisplayModeGridIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/application_view_tile.png"));

	public static final ImageIcon steamFriendsInitialAscendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/arrow_switch_bluegreen.png"));
	public static final ImageIcon steamFriendsNameAscendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_ascending.png"));
	public static final ImageIcon steamFriendsNameDescendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_descending.png"));
	public static final ImageIcon steamFriendsLogoAscendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_ascending_image.png"));
	public static final ImageIcon steamFriendsLogoDescendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_descending_image.png"));
	public static final ImageIcon steamFriendsOnlineStateAscendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_ascending_status.png"));
	public static final ImageIcon steamFriendsOnlineStateDescendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_descending_status.png"));
	public static final ImageIcon steamFriendsStateMessageAscendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_ascending_message_status.png"));
	public static final ImageIcon steamFriendsStateMessageDescendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_descending_message_status.png"));
	public static final ImageIcon steamFriendsSteamId64AscendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_ascending_key.png"));
	public static final ImageIcon steamFriendsSteamId64DescendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_descending_key.png"));
	
	public static final ImageIcon steamGamesDisplayModeLaunchPaneIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/application_view_tile.png"));
	public static final ImageIcon steamGamesDisplayModeConfigurationPaneIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/application_view_list.png"));

	public static final ImageIcon steamGamesSortMethodInitialAscendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/arrow_switch_bluegreen.png"));
	public static final ImageIcon steamGamesSortMethodLogoAscendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_ascending_image.png"));
	public static final ImageIcon steamGamesSortMethodLogoDescendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_descending_image.png"));
	public static final ImageIcon steamGamesSortMethodNameAscendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_ascending.png"));
	public static final ImageIcon steamGamesSortMethodNameDescendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_descending.png"));
	public static final ImageIcon steamGamesSortMethodArgumentsAscendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_ascending_wrench.png"));
	public static final ImageIcon steamGamesSortMethodArgumentsDescendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_descending_wrench.png"));
	public static final ImageIcon steamGamesSortMethodSteamLaunchMethodAscendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_ascending_lightning.png"));
	public static final ImageIcon steamGamesSortMethodSteamLaunchMethodDescendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_descending_lightning.png"));
	public static final ImageIcon steamGamesSortMethodHoursLast2WeeksAscendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_ascending_dates.png"));
	public static final ImageIcon steamGamesSortMethodHoursLast2WeeksDescendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_descending_dates.png"));
	public static final ImageIcon steamGamesSortMethodHoursOnRecordAscendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_ascending_hours.png"));
	public static final ImageIcon steamGamesSortMethodHoursOnRecordDescendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_descending_hours.png"));
	public static final ImageIcon steamGamesSortMethodAppIdAscendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_ascending_key.png"));
	public static final ImageIcon steamGamesSortMethodAppIdDescendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_descending_key.png"));
	public static final ImageIcon steamGamesSortMethodStoreLinkAscendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_ascending_cart.png"));
	public static final ImageIcon steamGamesSortMethodStoreLinkDescendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_descending_cart.png"));
	public static final ImageIcon steamGamesSortMethodGlobalStatsLinkAscendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_ascending_chart_line.png"));
	public static final ImageIcon steamGamesSortMethodGlobalStatsLinkDescendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_descending_chart_line.png"));
	public static final ImageIcon steamGamesSortMethodStatsLinkAscendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_ascending_chart_bar.png"));
	public static final ImageIcon steamGamesSortMethodStatsLinkDescendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_descending_chart_bar.png"));
	
	public static final ImageIcon steamGroupsDisplayModeListIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/application_view_list.png"));
	public static final ImageIcon steamGroupsDisplayModeGridIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/application_view_tile.png"));

	public static final ImageIcon steamGroupsSortMethodInitialAscendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/arrow_switch_bluegreen.png"));
	public static final ImageIcon steamGroupsSortMethodNameAscendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_ascending.png"));
	public static final ImageIcon steamGroupsSortMethodNameDescendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_descending.png"));
	public static final ImageIcon steamGroupsSortMethodLogoAscendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_ascending_image.png"));
	public static final ImageIcon steamGroupsSortMethodLogoDescendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_descending_image.png"));
	public static final ImageIcon steamGroupsSortMethodHeadlineAscendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_ascending_message_status.png"));
	public static final ImageIcon steamGroupsSortMethodHeadlineDescendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_descending_message_status.png"));
	public static final ImageIcon steamGroupsSortMethodSummaryAscendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_ascending_page_white.png"));
	public static final ImageIcon steamGroupsSortMethodSummaryDescendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_descending_page_white.png"));
	public static final ImageIcon steamGroupsSortMethodSteamId64AscendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_ascending_key.png"));
	public static final ImageIcon steamGroupsSortMethodSteamId64DescendingOrderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/sort_descending_key.png"));
	
	public static final ImageIcon steamLaunchMethodSteamProtocolIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/application_lightning.png"));
	public static final ImageIcon steamLaunchMethodSteamExecutableIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/application_xp_terminal.png"));
	
	public static final ImageIcon statusOnlineIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/status_online.png"));
	public static final ImageIcon statusBusyIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/status_busy.png"));
	public static final ImageIcon statusAwayIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/status_away.png"));
	public static final ImageIcon statusOfflineIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/status_offline.png"));
	
	public static final ImageIcon accountSummaryIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/user_blue_home.png"));
	public static final ImageIcon accountStatusIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/user_blue_database.png"));
	public static final ImageIcon groupsIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/group.png"));
	public static final ImageIcon friendsIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/group_friends.png"));
	
	public static final ImageIcon defaultSteamLaunchMethodIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/bullet_lightning.png"));
	
	public static final ImageIcon debugIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/bug.png"));
	public static final ImageIcon debugStartIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/bug_start.png"));
	public static final ImageIcon debugStopIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/bug_stop.png"));
	
	public static final ImageIcon gameLeftClickSelectIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/cursor.png"));
	public static final ImageIcon gameLeftClickLaunchIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/lightning.png"));
	
	public static final ImageIcon dumpModeIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/script.png"));
	public static final ImageIcon dumpModeTextIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/script_edit.png"));
	public static final ImageIcon dumpModeXMLIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/script_code.png"));
	public static final ImageIcon dumpModeBothIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/script_gear.png"));
	
	public static final ImageIcon displayToolTipsIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/comments.png"));
	public static final ImageIcon displayToolTipsYesIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/comments_add.png"));
	public static final ImageIcon displayToolTipsNoIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/comments_delete.png"));
	
	public static final ImageIcon buttonsDisplayModeIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/star.png"));
	public static final ImageIcon buttonsDisplayModeIconAndTextIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/star_gold.png"));
	public static final ImageIcon buttonsDisplayModeIconIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/star_gold_half_grey.png"));
	public static final ImageIcon buttonsDisplayModeTextIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/star_grey.png"));
	
	public static final ImageIcon localeChoiceIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/flag_france.png"));
	public static final ImageIcon lookAndFeelIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/java.png"));
	
	public static final ImageIcon aboutIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/information.png"));
	public static final ImageIcon exitIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/door_out.png"));
	
	/**
	 * Application image icons
	 */
	public static final ImageIcon gogIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/gog/gog-icon-16.png"));
	public static final ImageIcon steamIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/steam/steam-icon-16.png"));
	public static final ImageIcon clearConsoleIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/application_clear.png"));
	public static final ImageIcon loadParametersIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/disk_upload.png"));
	public static final ImageIcon lockStartIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/lock_open.png"));
	public static final ImageIcon lockStopIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/lock.png"));
	public static final ImageIcon refreshGamesListIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/steam/steam-icon-16.png"));
	public static final ImageIcon resetOptionsIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/refresh.png"));
	public static final ImageIcon rollIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/dice.png"));
	public static final ImageIcon saveParametersIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/disk_download.png"));
	public static final ImageIcon steamExecutableIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/application_steam.png"));
	public static final ImageIcon viewParametersIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/script_go.png"));
	public static final ImageIcon previousIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/resultset_previous.png"));
	public static final ImageIcon nextIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/resultset_next.png"));
	public static final ImageIcon addProfile = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/user_add.png"));
	public static final ImageIcon connectedUserIcon = new ImageIcon(GamesLibrarian.class.getResource(OnlineState.ONLINE.getIconPath()));
	public static final ImageIcon disconnectedUserIcon = new ImageIcon(GamesLibrarian.class.getResource(OnlineState.OFFLINE.getIconPath()));
	public static final ImageIcon ajaxLoaderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/ajax-loader.gif"));
	
	/**
	 * Steam replacement image icons
	 */
	public static final ImageIcon achievementImageUnavailableIcon = new ImageIcon(GamesLibrarian.class.getResource(ResourceBundle.getBundle("i18n/resources").getString("achievementImageUnavailable")));
	public static final ImageIcon gameImageUnavailableIcon = new ImageIcon(GamesLibrarian.class.getResource(ResourceBundle.getBundle("i18n/resources").getString("gameImageUnavailable")));
	public static final ImageIcon invalidAchievementIcon = new ImageIcon(GamesLibrarian.class.getResource(ResourceBundle.getBundle("i18n/resources").getString("invalidAchievement")));
	public static final ImageIcon noAvatarIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/steam/noAvatarIcon.jpg"));
	public static final ImageIcon noAvatarFull = new ImageIcon(GamesLibrarian.class.getResource("/images/steam/noAvatarFull.jpg"));
	public static final ImageIcon noGameSelectedIcon = new ImageIcon(GamesLibrarian.class.getResource(ResourceBundle.getBundle("i18n/resources").getString("noGameSelected")));
	
	/**
	 * I18n flags
	 */
	public static final ImageIcon en_USIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/locales/en_US.png"));
	public static final ImageIcon fr_FRIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/locales/fr_FR.png"));
	
	/**
	 * Identify loading source of steamProfile/steamGamesLibrary
	 */
	public enum LoadingSource {
		Preloading, // Local file source
		Steam, // URL source
		Errored // Invalid;
	}
}
