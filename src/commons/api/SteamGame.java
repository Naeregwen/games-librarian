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
package commons.api;

import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import commons.GamesLibrary.LoadingSource;
import components.commons.parsers.NumberParser;

/**
 * @author Naeregwen
 *
 */
@XmlRootElement (name = "steamGame")
@XmlType (propOrder = { 
	"name",
	"appID",
	"logo",
	"icon",
	"logoSmall",
	"storeLink",
	"hoursLast2Weeks",
	"hoursOnRecord",
	"statsLink",
	"globalStatsLink",
	
	"loadingSource",
	"arguments",
	"steamLaunchMethod",
	"initialPosition",
	
	"totalAchievements",
	"totalUnlockedAchievements",
	
	"steamGameStats"
})
public class SteamGame extends Game {
	
	public static enum ColumnsOrder {
		
		logo("gamesHeaderLogo"), 
		name("gamesHeaderName"), 
		arguments("gamesHeaderArguments"), 
		steamLaunchMethod("gamesHeaderSteamLaunchMethod"), 
		hoursLast2Weeks("gamesHeaderHoursLast2Weeks"), 
		hoursOnRecord("gamesHeaderHoursOnRecord"), 
		appID("gamesHeaderAppID"),
		storeLink("gamesHeaderStoreLink"), 
		statsLink("gamesHeaderStatsLink"), 
		globalStatsLink("gamesHeaderGlobalStatsLink");
			
		String headerName;
		
		ColumnsOrder(String headerName) {
			this.headerName = headerName;
		}

		/**
		 * @return the headerName
		 */
		public String getHeaderName() {
			return headerName;
		}	
		
	};
	
	LoadingSource loadingSource;

	String name;
	String appID;
	String logo;
	String icon;
	String logoSmall;
	String storeLink;
	String hoursLast2Weeks;
	String hoursOnRecord;
	String statsLink;
	String globalStatsLink;
	
	String arguments;
	SteamLaunchMethod steamLaunchMethod;
	Integer initialPosition;
	
	Integer totalAchievements = 0;
	Integer totalUnlockedAchievements = 0;
	
	// Tools
	private NumberParser numberParser;
	
	SteamGameStats steamGameStats;
	
	public SteamGame() {
		this.loadingSource = LoadingSource.Preloading;
		setStatistics();
	}
	
	public SteamGame(LoadingSource loadingSource) {
		this.loadingSource = loadingSource;
		setStatistics();
	}
	
	/**
	 * @return the loadingSource
	 */
	public LoadingSource getLoadingSource() {
		return loadingSource;
	}

	/**
	 * @param loadingSource the loadingSource to set
	 */
	@XmlElement
	public void setLoadingSource(LoadingSource loadingSource) {
		this.loadingSource = loadingSource;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the appID
	 */
	public String getAppID() {
		return appID;
	}

	/**
	 * @param appID the appID to set
	 */
	@XmlElement
	public void setAppID(String appID) {
		this.appID = appID;
	}

	/**
	 * @return the logo
	 */
	public String getLogo() {
		return logo;
	}

	/**
	 * @param logo the logo to set
	 */
	@XmlElement
	public void setLogo(String logo) {
		this.logo = logo;
	}

	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	@XmlElement
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @return the logoSmall
	 */
	public String getLogoSmall() {
		return logoSmall;
	}

	/**
	 * @param logoSmall the logoSmall to set
	 */
	@XmlElement
	public void setLogoSmall(String logoSmall) {
		this.logoSmall = logoSmall;
	}

	/**
	 * @return the storeLink
	 */
	public String getStoreLink() {
		return storeLink;
	}

	/**
	 * @param storeLink the storeLink to set
	 */
	@XmlElement
	public void setStoreLink(String storeLink) {
		this.storeLink = storeLink;
	}

	/**
	 * @return the hoursLast2Weeks
	 */
	public String getHoursLast2Weeks() {
		return hoursLast2Weeks;
	}

	/**
	 * @param hoursLast2Weeks the hoursLast2Weeks to set
	 */
	@XmlElement
	public void setHoursLast2Weeks(String hoursLast2Weeks) {
		this.hoursLast2Weeks = hoursLast2Weeks;
	}

	/**
	 * @return the hoursOnRecord
	 */
	public String getHoursOnRecord() {
		return hoursOnRecord;
	}

	/**
	 * @param hoursOnRecord the hoursOnRecord to set
	 */
	@XmlElement
	public void setHoursOnRecord(String hoursOnRecord) {
		this.hoursOnRecord = hoursOnRecord;
	}

	/**
	 * @return the statsLink
	 */
	public String getStatsLink() {
		return statsLink;
	}

	/**
	 * @param statsLink the statsLink to set
	 */
	@XmlElement
	public void setStatsLink(String statsLink) {
		this.statsLink = statsLink;
	}

	/**
	 * @return the globalStatsLink
	 */
	public String getGlobalStatsLink() {
		return globalStatsLink;
	}

	/**
	 * @param globalStatsLink the globalStatsLink to set
	 */
	@XmlElement
	public void setGlobalStatsLink(String globalStatsLink) {
		this.globalStatsLink = globalStatsLink;
	}

	/**
	 * @return the arguments
	 */
	public String getArguments() {
		return arguments;
	}

	/**
	 * @param arguments the arguments to set
	 */
	@XmlElement
	public void setArguments(String arguments) {
		this.arguments = arguments;
	}

	/**
	 * @return the steamLaunchMethod
	 */
	public SteamLaunchMethod getSteamLaunchMethod() {
		return steamLaunchMethod;
	}

	/**
	 * @param steamLaunchMethod the steamLaunchMethod to set
	 */
	@XmlElement
	public void setSteamLaunchMethod(SteamLaunchMethod steamLaunchMethod) {
		this.steamLaunchMethod = steamLaunchMethod;
	}

	/**
	 * @return the initialPosition
	 */
	public Integer getInitialPosition() {
		return initialPosition;
	}

	/**
	 * @param initialPosition the initialPosition to set
	 */
	@XmlElement
	public void setInitialPosition(Integer initialPosition) {
		this.initialPosition = initialPosition;
	}

	/**
	 * @return the totalAchievements
	 */
	public Integer getTotalAchievements() {
		return totalAchievements;
	}

	/**
	 * @param totalAchievements the totalAchievements to set
	 */
	@XmlElement
	public void setTotalAchievements(Integer totalAchievements) {
		this.totalAchievements = totalAchievements;
	}

	/**
	 * @return the totalUnlockedAchievements
	 */
	public Integer getTotalUnlockedAchievements() {
		return totalUnlockedAchievements;
	}

	/**
	 * @param totalUnlockedAchievements the totalUnlockedAchievements to set
	 */
	@XmlElement
	public void setTotalUnlockedAchievements(Integer totalUnlockedAchievements) {
		this.totalUnlockedAchievements = totalUnlockedAchievements;
	}

	/**
	 * @return the numberParser
	 */
	protected NumberParser getNumberParser() {
		return numberParser;
	}

	/**
	 * @param numberParser the numberParser to set
	 */
	@XmlTransient
	protected void setNumberParser(NumberParser numberParser) {
		this.numberParser = numberParser;
	}

	/**
	 * @return the steamGameStats
	 */
	public SteamGameStats getSteamGameStats() {
		return steamGameStats;
	}

	/**
	 * @param steamGameStats the steamGameStats to set
	 */
	@XmlElement
	public void setSteamGameStats(SteamGameStats steamGameStats) {
		this.steamGameStats = steamGameStats;
		setStatistics();
	}

	//
	// Utilities
	//
	
	/**
	 * Reset all game statistics counters to zero
	 */
	private void resetStatistics() {
		totalAchievements = 0;
		totalUnlockedAchievements = 0;
	}
	
	/**
	 * Set game statistics counters
	 */
	private void setStatistics() {
		resetStatistics();
		if (steamGameStats != null
				&& steamGameStats.getSteamAchievementsList() != null 
				&& steamGameStats.getSteamAchievementsList().getSteamAchievements() != null
				&& steamGameStats.getSteamAchievementsList().getSteamAchievements().size() > 0) {
			for (SteamAchievement steamAchievement : steamGameStats.getSteamAchievementsList().getSteamAchievements()) {
				totalAchievements++;
				if (steamAchievement.isClosed())
					totalUnlockedAchievements++;
			}
		}
	}
	
	/**
	 * Determine ID between name and appID in this order
	 * @return ID if found, replacement otherwise
	 */
	public String getID(String replacement) {
		return name != null && name.length() > 0 ? name : appID != null && appID.length() > 0 ? appID : replacement;
	}
	
	
	/**
	 * Test minimal validity of game data
	 * @return empty String => valid, filled string => not valid/error message
	 */
	public String isValid() {
		
		ResourceBundle messages = ResourceBundle.getBundle("i18n/messages");
		
		String answer = "";
		// Must have at least a appId and a game name
		boolean appIdEmpty = true;
		if (appID != null && !appID.trim().equals("")) {
			appIdEmpty = false;
		}
		boolean gameNameEmpty = true;
		if (name != null && !name.trim().equals("")) {
			gameNameEmpty = false;
		}
		if (appIdEmpty || gameNameEmpty) {
			answer = messages.getString("errorSaveConfigurationCannotSaveGame") + " " + (appIdEmpty ? (gameNameEmpty ?
					messages.getString("errorSaveConfigurationCannotSaveGameAppIdNameAreEmpty") :
						String.format(messages.getString("errorSaveConfigurationCannotSaveGameAppIdIsEmpty"), name)) :
							String.format(messages.getString("errorSaveConfigurationCannotSaveGameGameNameIsEmpty"), appID));
		}
		return answer;
	}
	
	/**
	 * MostPlayedGame does not have an appId
	 * So dig the game data to find one
	 * @return appID, if found, null otherwise
	 */
	public String digAppID() {
		String diggedAppId = getAppID();
		if (diggedAppId == null)
			diggedAppId = Steam.getAppIdFromGameLink(getStoreLink());
		if (diggedAppId == null)
			diggedAppId = Steam.getAppIdFromStatsLink(getStatsLink());
		if (diggedAppId == null)
			diggedAppId = Steam.getAppIdFromGameImageLink(getIcon());
		if (diggedAppId == null)
			diggedAppId = Steam.getAppIdFromGameImageLink(getLogo());
		if (diggedAppId == null)
			diggedAppId = Steam.getAppIdFromGameImageLink(getLogoSmall());
		return diggedAppId;
	}
	
	/**
	 * Prepare a List<String> to display SteamGame data later with only data as MostPlayedGame
	 * @param prefix
	 * @return
	 */
	public List<String> toMostPlayedGameStringList(String prefix) {
		
		List<String> result = new Vector<String>();
		
		result.add(prefix + " - name :" + (name != null ? name : "null"));
		result.add(prefix + " - storeLink :" + (storeLink != null ? storeLink : "null"));
		result.add(prefix + " - icon :" + (icon != null ? icon : "null"));
		result.add(prefix + " - logo :" + (logo != null ? logo : "null"));
		result.add(prefix + " - logoSmall :" + (logoSmall != null ? logoSmall : "null"));
		result.add(prefix + " - hoursLast2Weeks :" + (hoursLast2Weeks != null ? hoursLast2Weeks : "null"));
		result.add(prefix + " - statsLink :" + (statsLink != null ? statsLink : "null"));
		
		return result;
	}

	/**
	 * Prepare a List<String> to display SteamGame data later
	 * @param prefix
	 * @return
	 */
	public List<String> toStringList(String prefix) {
		
		List<String> result = new Vector<String>();
		
		result.add(prefix + " - initialPosition : " + (initialPosition != null ? initialPosition : "null"));
		result.add(prefix + " - appID : " + (appID != null ? appID : "null"));
		result.add(prefix + " - name : " + (name != null ? name : "null"));
		result.add(prefix + " - logo : " + (logo != null ? logo : "null"));
		result.add(prefix + " - logoSmall : " + (logoSmall != null ? logoSmall : "null"));
		result.add(prefix + " - storeLink : " + (storeLink != null ? storeLink : "null"));
		result.add(prefix + " - hoursLast2Weeks : " + (hoursLast2Weeks != null ? hoursLast2Weeks : "null"));
		result.add(prefix + " - hoursOnRecord : " + (hoursOnRecord != null ? hoursOnRecord : "null"));
		result.add(prefix + " - statsLink : " + (statsLink != null ? statsLink : "null"));
		result.add(prefix + " - globalStatsLink : " + (globalStatsLink != null ? globalStatsLink : "null"));
		result.add(prefix + " - totalAchievements : " + (totalAchievements != null ? totalAchievements : "null"));
		result.add(prefix + " - totalUnlockedAchievements : " + (totalUnlockedAchievements != null ? totalUnlockedAchievements : "null"));
		result.add(prefix + " - loadingSource : " + (loadingSource != null ? loadingSource : "null"));
		result.add(prefix + " - arguments : " + (arguments != null ? arguments : "null"));
		result.add(prefix + " - steamLaunchMethod : " + (steamLaunchMethod != null ? steamLaunchMethod : "null"));
		
		if (steamGameStats != null) {
			prefix = prefix + " - steamGameStats";
			result.add(prefix + " :");
			for (String string : steamGameStats.toStringList(prefix))
				result.add(string);
		}

		return result;
	}
}
