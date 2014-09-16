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
package commons.api.parsers;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import javax.swing.Icon;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import commons.api.Steam;
import commons.api.SteamAchievement;
import commons.api.SteamAchievementsList;
import commons.api.SteamGameStats;
import components.commons.ColoredTee;

/**
 * @author Naeregwen
 *
 */
public class SteamGameStatsParser extends DefaultHandler {

	/**
	 * Initial data containers
	 */
	private String playerSteamID64;
	private String playerSteamID;
	private Integer initialPosition;
	private Icon playerAvatarIcon;
	
	/**
	 * Temporary parsing containers
	 */
	private String characters;
	private SteamAchievement steamAchievement;
	
	/**
	 * Final parsing container
	 */
	SteamGameStats steamGameStats;
	
	/**
	 * 
	 * @param playerSteamID
	 */
	public SteamGameStatsParser(String playerSteamID64, String playerSteamID, Integer initialPosition, Icon playerAvatarIcon) {
		this.playerSteamID64 = playerSteamID64;
		this.playerSteamID = playerSteamID;
		this.initialPosition = initialPosition;
		this.playerAvatarIcon = playerAvatarIcon;
	}

	/**
	 * @return the steamGameStats
	 */
	public SteamGameStats getSteamGameStats() {
		return steamGameStats;
	}

	/*/
	 * (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		// Reset characters container
		characters = "";
		
		// Create data containers
		if (qName.equalsIgnoreCase("playerstats")) {
			steamGameStats = new SteamGameStats(playerSteamID);
		} else if (qName.equalsIgnoreCase("achievements")) {
			steamGameStats.setSteamAchievementsList(new SteamAchievementsList(playerSteamID64, playerSteamID, initialPosition, playerAvatarIcon));
		} else if (qName.equalsIgnoreCase("achievement")) {
			steamAchievement = new SteamAchievement();
			steamAchievement.setInitialPosition(++initialPosition);
			String closedAttribute = attributes.getValue("closed");
			if (closedAttribute != null) {
				closedAttribute = closedAttribute.trim();
				if (closedAttribute.equals("0"))
					steamAchievement.setClosed(false);
				else if (closedAttribute.equals("1"))
					steamAchievement.setClosed(true);
			}
		}
	}
	
	/*/
	 * (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		characters = new String(ch, start, length).trim();
	}

	/*/
	 * (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if (qName.equalsIgnoreCase("privacyState")) {
			if (steamGameStats != null) steamGameStats.setPrivacyState(characters);
		} else if (qName.equalsIgnoreCase("visibilityState")) {
			if (steamGameStats != null) steamGameStats.setVisibilityState(characters);
		} else if(qName.equalsIgnoreCase("gameFriendlyName")) {
			if (steamGameStats != null) steamGameStats.setGameFriendlyName(characters);
		} else if (qName.equalsIgnoreCase("gameName")) {
			if (steamGameStats != null) steamGameStats.setGameName(characters);
		} else if (qName.equalsIgnoreCase("gameLink")) {
			if (steamGameStats != null) {
				steamGameStats.setGameLink(characters);
				steamGameStats.setAppID(characters.replaceFirst(Steam.steamCommunityAppURL, ""));
			}
		} else if (qName.equalsIgnoreCase("gameIcon")) {
			if (steamGameStats != null) steamGameStats.setGameIcon(characters);
		} else if (qName.equalsIgnoreCase("gameLogo")) {
			if (steamGameStats != null) steamGameStats.setGameLogo(characters);
		} else if (qName.equalsIgnoreCase("gameLogoSmall")) {
			if (steamGameStats != null) steamGameStats.setGameLogoSmall(characters);
			
		} else if (qName.equalsIgnoreCase("steamID64")) {
			if (steamGameStats != null) steamGameStats.setSteamID64(characters);
		} else if (qName.equalsIgnoreCase("customURL")) {
			if (steamGameStats != null) steamGameStats.setCustomURL(characters);
			
		} else if (qName.equalsIgnoreCase("hoursPlayed")) {
			if (steamGameStats != null) steamGameStats.setHoursPlayed(characters);
			
		} else if (qName.equalsIgnoreCase("hoursPlayed")) {
			if (steamGameStats != null) steamGameStats.setHoursPlayed(characters);
			
		} else if (qName.equalsIgnoreCase("iconClosed")) {
			if (steamAchievement != null) steamAchievement.setIconClosed(characters);
		} else if (qName.equalsIgnoreCase("iconOpen")) {
			if (steamAchievement != null) steamAchievement.setIconOpen(characters);
		} else if (qName.equalsIgnoreCase("name")) {
			if (steamAchievement != null) steamAchievement.setName(characters);
		} else if (qName.equalsIgnoreCase("apiname")) {
			if (steamAchievement != null) steamAchievement.setApiname(characters);
		} else if (qName.equalsIgnoreCase("description")) {
			if (steamAchievement != null) steamAchievement.setDescription(characters);
		} else if (qName.equalsIgnoreCase("unlockTimestamp")) {
			if (steamAchievement != null) steamAchievement.setUnlockTimestamp(characters);

		} else if (qName.equalsIgnoreCase("achievement")) {
			if (steamAchievement != null && steamGameStats != null && steamGameStats.getSteamAchievementsList() != null && steamGameStats.getSteamAchievementsList() != null) steamGameStats.getSteamAchievementsList().add(steamAchievement);
		}

	}	
	
	/**
	 * Dump marshaled gameStats to console
	 * 
	 * @param steamGameStats
	 * @param tee
	 */
	public static void dump(SteamGameStats steamGameStats, ColoredTee tee) {
		try {
			Marshaller marshaller = JAXBContext.newInstance(SteamGameStats.class).createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			marshaller.marshal(steamGameStats, os);  
			tee.writelnMessage(Arrays.asList(new String(os.toByteArray()).split("\n")));
		} catch (JAXBException e) {
			tee.printStackTrace(e);
		}
	}
}
