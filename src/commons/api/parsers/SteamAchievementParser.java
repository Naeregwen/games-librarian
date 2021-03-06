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

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import commons.api.SteamAchievement;

/**
 * @author Naeregwen
 *
 */
public class SteamAchievementParser extends DefaultHandler {

	/**
	 * Temporary parsing containers
	 */
	private String characters;
	
	/**
	 * Final parsing container
	 */
	SteamAchievement steamAchievement;
	
	/**
	 * @return the steamAchievement
	 */
	public SteamAchievement getSteamAchievement() {
		return steamAchievement;
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
		if (qName.equalsIgnoreCase("achievement")) {
			steamAchievement = new SteamAchievement();
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

		if (qName.equalsIgnoreCase("iconClosed")) {
			if (steamAchievement != null) steamAchievement.setIconClosed(characters);
		} else if (qName.equalsIgnoreCase("iconOpen")) {
			if (steamAchievement != null) steamAchievement.setIconOpen(characters);
		} else if(qName.equalsIgnoreCase("name")) {
			if (steamAchievement != null) steamAchievement.setName(characters);
		} else if (qName.equalsIgnoreCase("apiname")) {
			if (steamAchievement != null) steamAchievement.setApiname(characters);
		} else if (qName.equalsIgnoreCase("description")) {
			if (steamAchievement != null) steamAchievement.setDescription(characters);
		} else if (qName.equalsIgnoreCase("unlockTimestamp")) {
			if (steamAchievement != null) steamAchievement.setUnlockTimestamp(characters);
		}

	}	
	
}
