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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Naeregwen
 *
 */
@XmlRootElement (name = "playerstats")
@XmlType (propOrder = { 
	"privacyState", 
	"visibilityState",
	"gameFriendlyName",
	"gameName",
	"gameLink",
	"gameIcon",
	"gameLogo",
	"gameLogoSmall",
	"appID",
	"steamID64",
	"playerSteamID",
	"customURL",
	"hoursPlayed",
	
	"steamAchievementsList"
})
public class SteamGameStats {

	String privacyState;
	String visibilityState;
	String gameFriendlyName;
	String gameName;
	String gameLink;
	String gameIcon;
	String gameLogo;
	String gameLogoSmall;
	String appID; // Needed to attach a SteamGameStats to SteamGame. Builded by SteamGameStatsParser during SteamGameStats parsing
	String steamID64;
	String playerSteamID; // Custom for SteamAchievementsTable columns management
	String customURL;
	String hoursPlayed;
	
	SteamAchievementsList steamAchievementsList;
	
	/**
	 * 
	 */
	public SteamGameStats() {}

	/**
	 * 
	 * @param playerSteamID the playerSteamID to set
	 */
	public SteamGameStats(String playerSteamID) {
		this.playerSteamID = playerSteamID;
	}
	
	/**
	 * @return the privacyState
	 */
	public String getPrivacyState() {
		return privacyState;
	}

	/**
	 * @param privacyState the privacyState to set
	 */
	@XmlElement
	public void setPrivacyState(String privacyState) {
		this.privacyState = privacyState;
	}

	/**
	 * @return the visibilityState
	 */
	public String getVisibilityState() {
		return visibilityState;
	}

	/**
	 * @param visibilityState the visibilityState to set
	 */
	@XmlElement
	public void setVisibilityState(String visibilityState) {
		this.visibilityState = visibilityState;
	}

	/**
	 * @return the gameFriendlyName
	 */
	public String getGameFriendlyName() {
		return gameFriendlyName;
	}

	/**
	 * @param gameFriendlyName the gameFriendlyName to set
	 */
	@XmlElement
	public void setGameFriendlyName(String gameFriendlyName) {
		this.gameFriendlyName = gameFriendlyName;
	}

	/**
	 * @return the gameName
	 */
	public String getGameName() {
		return gameName;
	}

	/**
	 * @param gameName the gameName to set
	 */
	@XmlElement
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	/**
	 * @return the gameLink
	 */
	public String getGameLink() {
		return gameLink;
	}

	/**
	 * @param gameLink the gameLink to set
	 */
	@XmlElement
	public void setGameLink(String gameLink) {
		this.gameLink = gameLink;
	}

	/**
	 * @return the gameIcon
	 */
	public String getGameIcon() {
		return gameIcon;
	}

	/**
	 * @param gameIcon the gameIcon to set
	 */
	@XmlElement
	public void setGameIcon(String gameIcon) {
		this.gameIcon = gameIcon;
	}

	/**
	 * @return the gameLogo
	 */
	public String getGameLogo() {
		return gameLogo;
	}

	/**
	 * @param gameLogo the gameLogo to set
	 */
	@XmlElement
	public void setGameLogo(String gameLogo) {
		this.gameLogo = gameLogo;
	}

	/**
	 * @return the gameLogoSmall
	 */
	public String getGameLogoSmall() {
		return gameLogoSmall;
	}

	/**
	 * @param gameLogoSmall the gameLogoSmall to set
	 */
	@XmlElement
	public void setGameLogoSmall(String gameLogoSmall) {
		this.gameLogoSmall = gameLogoSmall;
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
	 * @return the playerSteamID
	 */
	public String getPlayerSteamID() {
		return playerSteamID;
	}

	/**
	 * @param playerSteamID the playerSteamID to set
	 */
	@XmlElement
	public void setPlayerSteamID(String playerSteamID) {
		this.playerSteamID = playerSteamID;
	}

	/**
	 * @return the customURL
	 */
	public String getCustomURL() {
		return customURL;
	}

	/**
	 * @param customURL the customURL to set
	 */
	@XmlElement
	public void setCustomURL(String customURL) {
		this.customURL = customURL;
	}

	/**
	 * @return the hoursPlayed
	 */
	public String getHoursPlayed() {
		return hoursPlayed;
	}

	/**
	 * @param hoursPlayed the hoursPlayed to set
	 */
	@XmlElement
	public void setHoursPlayed(String hoursPlayed) {
		this.hoursPlayed = hoursPlayed;
	}

	/**
	 * @return the steamAchievementsList
	 */
	public SteamAchievementsList getSteamAchievementsList() {
		return steamAchievementsList;
	}

	/**
	 * @param steamAchievementsList the steamAchievementsList to set
	 */
	@XmlElement(type = SteamAchievementsList.class)
	public void setSteamAchievementsList(SteamAchievementsList steamAchievementsList) {
		this.steamAchievementsList = steamAchievementsList;
	}

}
