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

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.Icon;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Naeregwen
 *
 */
@XmlRootElement (name = "steamAchievementsList")
@XmlType (propOrder = {
	"playerSteamID64", 
	"playerSteamID", 
	"initialPosition",
	"steamAchievements"
})
public class SteamAchievementsList {

	String playerSteamID64;
	String playerSteamID;
	Integer initialPosition;
	Icon playerAvatarIcon;
	Vector<SteamAchievement> steamAchievements;
	
	/**
	 * 
	 */
	public SteamAchievementsList() {
		playerSteamID64 = "";
		playerSteamID = "";
		initialPosition = 0;
		steamAchievements = new Vector<SteamAchievement>();
	}

	/**
	 * 
	 */
	public SteamAchievementsList(String playerSteamID64, String playerSteamID, Integer initialPosition, Icon playerAvatarIcon) {
		this.playerSteamID64 = playerSteamID64;
		this.playerSteamID = playerSteamID;
		this.initialPosition = initialPosition;
		this.playerAvatarIcon = playerAvatarIcon;
		steamAchievements = new Vector<SteamAchievement>();
	}

	/**
	 * @return the playerSteamID64
	 */
	public String getPlayerSteamID64() {
		return playerSteamID64;
	}

	/**
	 * @param playerSteamID64 the playerSteamID64 to set
	 */
	@XmlElement
	public void setPlayerSteamID64(String playerSteamID64) {
		this.playerSteamID64 = playerSteamID64;
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
	 * @return the playerAvatarIcon
	 */
	public Icon getPlayerAvatarIcon() {
		return playerAvatarIcon;
	}

	/**
	 * @param playerAvatarIcon the playerAvatarIcon to set
	 */
	@XmlTransient
	public void setPlayerAvatarIcon(Icon playerAvatarIcon) {
		this.playerAvatarIcon = playerAvatarIcon;
	}

	/**
	 * @return the steamAchievements
	 */
	public Vector<SteamAchievement> getSteamAchievements() {
		return steamAchievements;
	}

	/**
	 * @param steamAchievements the steamAchievements to set
	 */
	@XmlElements(value = { @XmlElement(name = "steamAchievement", type = SteamAchievement.class) })
	@XmlElementWrapper(name = "steamAchievements")
	public void setSteamAchievements(Vector<SteamAchievement> steamAchievements) {
		this.steamAchievements = steamAchievements;
	}

	/**
	 * Add a steamAchievement to the steamAchievement
	 * @param steamAchievement the {@link SteamAchievement} to add
	 */
	public void add(SteamAchievement steamAchievement) {
		steamAchievements.add(steamAchievement);
	}
	
	//
	// Utilities
	//
	
	/**
	 * Prepare a List<String> to display SteamAchievementsList data later
	 * @return
	 */
	public List<String> toStringList(String prefix) {
		
		List<String> result = new Vector<String>();

		result.add(prefix + " - playerSteamID64 : " + (playerSteamID64 != null ? playerSteamID64 : "null"));
		result.add(prefix + " - playerSteamID : " + (playerSteamID != null ? playerSteamID : "null"));
		result.add(prefix + " - initialPosition : " + (initialPosition != null ? initialPosition : "null"));
		
		prefix = prefix + " - steamAchievements";
		result.add(prefix + " :");
		prefix = prefix + " - steamAchievement";
		Iterator<SteamAchievement> steamAchievementsIterator = steamAchievements.iterator();
		while (steamAchievementsIterator.hasNext()) {
			SteamAchievement steamAchievement = steamAchievementsIterator.next();
			result.add(prefix + " :");
			result.add(prefix + " - initialPosition : " + (steamAchievement.initialPosition != null ? steamAchievement.initialPosition : "null"));
			result.add(prefix + " - name : " + (steamAchievement.name != null ? steamAchievement.name : "null"));
			result.add(prefix + " - description : " + (steamAchievement.description != null ? steamAchievement.description : "null"));
			result.add(prefix + " - unlockTimestamp : " + (steamAchievement.unlockTimestamp != null ? steamAchievement.unlockTimestamp : "null"));
			result.add(prefix + " - iconClosed : " + (steamAchievement.iconClosed != null ? steamAchievement.iconClosed : "null"));
			result.add(prefix + " - iconOpen : " + (steamAchievement.iconOpen != null ? steamAchievement.iconOpen : "null"));
			result.add(prefix + " - apiname : " + (steamAchievement.apiname != null ? steamAchievement.apiname : "null"));
		}

		return result;
	}
	
	public Double getAchievementsRatio() {
		if (steamAchievements == null || steamAchievements.size() == 0)
			return 0.0;
		else {
			int achieved = 0;
			for (SteamAchievement achievement : steamAchievements)
				if (achievement.isClosed())
					achieved++;
			return achieved / new Double(steamAchievements.size());
		}
		
	}
	
	public String getPrintableAchievementsRatio(Double achievementsRatio) {
        return String.format("%4.2f %%", achievementsRatio != null ? achievementsRatio : getAchievementsRatio());
	}
	
	public int getCeiledAchievementsRatio(Double achievementsRatio) {
		return Math.round(new Double(Math.ceil(achievementsRatio)).intValue());	
	}
}
