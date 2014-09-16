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

import java.util.Vector;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import commons.GamesLibrarianIcons.LoadingSource;
import components.commons.parsers.NumberParser;
import components.commons.parsers.UnacceptableValueTypeException;

/**
 * @author Naeregwen
 *
 */
@XmlRootElement (name = "steamGamesList")
@XmlType (propOrder = { 
	"steamID64",
	"steamID",
	
	"loadingSource",
	
	"totalGamesWithStats",
	"totalGamesWithGlobalStats",
	"totalGamesWithStoreLink",
	
	"totalWastedHours",
	"totalHoursLast2Weeks",

	"totalAchievements",
	"totalUnlockedAchievements",
	"totalGamesWithInvalidStats",
	"totalFinishedGames",
	
	"steamGames"
})
public class SteamGamesList {
	
	String steamID64;
	String steamID;
	Vector<SteamGame> steamGames;
	
	LoadingSource loadingSource;

	Integer totalGamesWithStats = 0;
	Integer totalGamesWithGlobalStats = 0;
	Integer totalGamesWithStoreLink = 0;
	
	Double totalWastedHours = 0d;
	Double totalHoursLast2Weeks = 0d;
	
	Integer totalAchievements = 0;
	Integer totalUnlockedAchievements = 0;
	Integer totalGamesWithInvalidStats = 0;
	Integer totalFinishedGames = 0;
	
	// Tools
	private NumberParser numberParser;
	
	/**
	 * Initialize base members
	 */
	public SteamGamesList() {
		steamGames = new Vector<SteamGame>();
		numberParser = new NumberParser();
		resetStatistics();
	}
	
	/**
	 * Initialization based on another profile  
	 */
	public SteamGamesList(SteamProfile steamProfile) {
		steamID64 = steamProfile.getSteamID64();
		steamID = steamProfile.getSteamID();
		loadingSource = steamProfile.getLoadingSource();
		steamGames = steamProfile.getSteamGames();
		numberParser = new NumberParser();
		resetStatistics();
		setStatistics();
	}
	
	/**
	 * @return the steamID64
	 */
	public String getSteamID64() {
		return steamID64;
	}

	/**
	 * @param steamID64 the steamID64 to set
	 */
	@XmlElement
	public void setSteamID64(String steamID64) {
		this.steamID64 = steamID64;
	}

	/**
	 * @return the steamID
	 */
	public String getSteamID() {
		return steamID;
	}

	/**
	 * @param steamID the steamID to set
	 */
	@XmlElement
	public void setSteamID(String steamID) {
		this.steamID = steamID;
	}

	/**
	 * @return the steamGames
	 */
	public Vector<SteamGame> getSteamGames() {
		return steamGames;
	}

	/**
	 * @param steamGames the steamGames to set
	 */
	@XmlElements(value = { @XmlElement(name = "steamGame", type = SteamGame.class) })
	@XmlElementWrapper(name = "steamGames")
	public void setSteamGames(Vector<SteamGame> steamGames) {
		this.steamGames = steamGames;
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
	 * @return the totalGamesWithStats
	 */
	public Integer getTotalGamesWithStats() {
		return totalGamesWithStats;
	}

	/**
	 * @param totalGamesWithStats the totalGamesWithStats to set
	 */
	@XmlElement
	public void setTotalGamesWithStats(Integer totalGamesWithStats) {
		this.totalGamesWithStats = totalGamesWithStats;
	}

	/**
	 * @return the totalGamesWithGlobalStats
	 */
	public Integer getTotalGamesWithGlobalStats() {
		return totalGamesWithGlobalStats;
	}

	/**
	 * @param totalGamesWithGlobalStats the totalGamesWithGlobalStats to set
	 */
	@XmlElement
	public void setTotalGamesWithGlobalStats(Integer totalGamesWithGlobalStats) {
		this.totalGamesWithGlobalStats = totalGamesWithGlobalStats;
	}

	/**
	 * @return the totalGamesWithStoreLink
	 */
	public Integer getTotalGamesWithStoreLink() {
		return totalGamesWithStoreLink;
	}

	/**
	 * @param totalGamesWithStoreLink the totalGamesWithStoreLink to set
	 */
	@XmlElement
	public void setTotalGamesWithStoreLink(Integer totalGamesWithStoreLink) {
		this.totalGamesWithStoreLink = totalGamesWithStoreLink;
	}

	/**
	 * @return the totalWastedHours
	 */
	public Double getTotalWastedHours() {
		return totalWastedHours;
	}

	/**
	 * @param totalWastedHours the totalWastedHours to set
	 */
	@XmlElement
	public void setTotalWastedHours(Double totalWastedHours) {
		this.totalWastedHours = totalWastedHours;
	}

	/**
	 * @return the totalHoursLast2Weeks
	 */
	public Double getTotalHoursLast2Weeks() {
		return totalHoursLast2Weeks;
	}

	/**
	 * @param totalHoursLast2Weeks the totalHoursLast2Weeks to set
	 */
	@XmlElement
	public void setTotalHoursLast2Weeks(Double totalHoursLast2Weeks) {
		this.totalHoursLast2Weeks = totalHoursLast2Weeks;
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
	protected void setTotalAchievements(Integer totalAchievements) {
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
	protected void setTotalUnlockedAchievements(Integer totalUnlockedAchievements) {
		this.totalUnlockedAchievements = totalUnlockedAchievements;
	}

	/**
	 * @return the totalGamesWithInvalidStats
	 */
	public Integer getTotalGamesWithInvalidStats() {
		return totalGamesWithInvalidStats;
	}

	/**
	 * @param totalGamesWithInvalidStats the totalGamesWithInvalidStats to set
	 */
	@XmlElement
	protected void setTotalGamesWithInvalidStats(Integer totalGamesWithErroredStats) {
		this.totalGamesWithInvalidStats = totalGamesWithErroredStats;
	}

	/**
	 * @return the totalFinishedGames
	 */
	public Integer getTotalFinishedGames() {
		return totalFinishedGames;
	}

	/**
	 * @param totalFinishedGames the totalFinishedGames to set
	 */
	@XmlElement
	protected void setTotalFinishedGames(Integer totalFinishedGames) {
		this.totalFinishedGames = totalFinishedGames;
	}

	/**
	 * @return the numberParser
	 */
	public NumberParser getNumberParser() {
		return numberParser;
	}

	/**
	 * @param numberParser the numberParser to set
	 */
	@XmlTransient
	public void setNumberParser(NumberParser numberParser) {
		this.numberParser = numberParser;
	}

	/**
	 * @param steamGame the steamGame to add
	 */
	public void add(SteamGame steamGame) {
		if (steamGames.size() == 0)
			resetStatistics();
		steamGames.add(steamGame);
		addGameStats(steamGame);
	}

	/**
	 * Add game's non empty values relatives to achievements to the counters serving the statistics of the games list
	 * @param steamGame
	 */
	private void addGameAchievementsStats(SteamGame steamGame) {
		if (steamGame.getSteamGameStats() != null
				&& steamGame.getSteamGameStats().getSteamAchievementsList() != null 
				&& steamGame.getSteamGameStats().getSteamAchievementsList().getSteamAchievements() != null
				&& steamGame.getSteamGameStats().getSteamAchievementsList().getSteamAchievements().size() > 0) {
			totalAchievements += steamGame.getTotalAchievements();
			totalUnlockedAchievements += steamGame.getTotalUnlockedAchievements();
			if (steamGame.getTotalAchievements().equals(steamGame.getTotalUnlockedAchievements()))
				totalFinishedGames++;
		} else if ((steamGame.getStatsLink() != null && !steamGame.getStatsLink().trim().equals(""))
				&& (steamGame.getSteamGameStats() == null
					|| steamGame.getSteamGameStats().getSteamAchievementsList() == null
					|| steamGame.getSteamGameStats().getSteamAchievementsList().getSteamAchievements() == null
					|| steamGame.getSteamGameStats().getSteamAchievementsList().getSteamAchievements().size() <= 0)) {
			totalGamesWithInvalidStats++;
		}
	}
		
	/**
	 * Add game's non empty values to the counters serving the statistics of the games list
	 * @param steamGame
	 */
	private void addGameStats(SteamGame steamGame) {
		
		if (steamGame.getStatsLink() != null && !steamGame.getStatsLink().trim().equals(""))
			totalGamesWithStats++;
		if (steamGame.getGlobalStatsLink() != null && steamGame.getGlobalStatsLink().startsWith(Steam.steamCommunityShortURL))
			totalGamesWithGlobalStats++;
		if (steamGame.getStoreLink() != null && (steamGame.getStoreLink().startsWith(Steam.steamCommunityShortURL) || steamGame.getStoreLink().startsWith(Steam.steamCommunityStoreURL)))
			totalGamesWithStoreLink++;
		
		if (steamGame.getHoursOnRecord() != null && !steamGame.getHoursOnRecord().trim().equals("")) {
			try {
				Double hoursOnRecord = numberParser.parseDouble(steamGame.getHoursOnRecord());
				totalWastedHours += hoursOnRecord;
			} catch (UnacceptableValueTypeException e) {}
		}
		if (steamGame.getHoursLast2Weeks() != null && !steamGame.getHoursLast2Weeks().trim().equals("")) {
			try {
				Double hoursLast2Weeks = numberParser.parseDouble(steamGame.getHoursLast2Weeks());
				totalHoursLast2Weeks += hoursLast2Weeks;
			} catch (UnacceptableValueTypeException e) {}
		}
		
		addGameAchievementsStats(steamGame);
	}
	
	/**
	 * Reset to zero only statistics counters relatives to achievements
	 */
	private void resetAchievementsStatistics() {
		totalFinishedGames = 0;
		totalAchievements = 0;
		totalUnlockedAchievements = 0;
		totalGamesWithInvalidStats = 0;
	}
	
	/**
	 * Reset to zero all statistics counters
	 */
	private void resetStatistics() {
		
		totalGamesWithStats = 0;
		totalGamesWithGlobalStats = 0;
		totalGamesWithStoreLink = 0;
		
		totalWastedHours = 0d;
		totalHoursLast2Weeks = 0d;
		
		resetAchievementsStatistics();
	}
	
	/**
	 * Set the statistics counters relatives to achievements
	 */
	public void setAchievementsStatistics() {
		resetAchievementsStatistics();
		for (SteamGame steamGame : steamGames) 
			addGameAchievementsStats(steamGame);
	}
	
	/**
	 * Set the statistics counters for the steamGamesList
	 */
	public void setStatistics() {
		for (SteamGame steamGame : steamGames) 
			addGameStats(steamGame);
	}
	
}
