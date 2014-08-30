/**
 * 
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
	 * Application image icons
	 */
	public static final ImageIcon ajaxLoaderIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/ajax-loader.gif"));
	
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
	
	public static final ImageIcon fileMenuIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/disk.png"));
	public static final ImageIcon gotoMenuIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/tab.png"));
	public static final ImageIcon controlsMenuIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/wrench.png"));
	public static final ImageIcon libraryMenuIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/pictures_thumbs.png"));
	public static final ImageIcon gameMenuIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/controller.png"));
	public static final ImageIcon profileMenuIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/user_blue.png"));
	public static final ImageIcon optionsMenuIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/cog.png"));
	
	public static final ImageIcon libraryDisplayMode = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/layout.png"));
	public static final ImageIcon librarySortMethod = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/table_sort.png"));
	
	public static final ImageIcon statusOnlineIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/status_online.png"));
	public static final ImageIcon statusBusyIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/status_busy.png"));
	public static final ImageIcon statusAwayIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/status_away.png"));
	public static final ImageIcon statusOfflineIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/status_offline.png"));
	
	public static final ImageIcon accountSummaryIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/user_blue_home.png"));
	public static final ImageIcon accountStatusIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/user_blue_database.png"));
	public static final ImageIcon groupsIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/group.png"));
	public static final ImageIcon friendsIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/group_friends.png"));
	
	public static final ImageIcon defaultSteamLaunchMethodIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/bullet_lightning.png"));
	
	public static final ImageIcon displayToolTipsIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/comments.png"));
	public static final ImageIcon displayToolTipsYesIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/comments_add.png"));
	public static final ImageIcon displayToolTipsNoIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/comments_delete.png"));
	
	public static final ImageIcon dumpModeIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/script.png"));
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
	public static final ImageIcon debugOffIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/bug_delete.png"));
	public static final ImageIcon debugOnIcon = new ImageIcon(GamesLibrarian.class.getResource("/images/icons/bug_add.png"));
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
	 * Identify loading source of steamProfile/steamGamesLibrary
	 */
	public enum LoadingSource {
		Preloading, // Local file source
		Steam, // URL source
		Errored // Invalid;
	}
}
