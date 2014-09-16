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
import java.util.Vector;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import commons.GamesLibrarianIcons.LoadingSource;
import commons.api.Parameters;
import commons.api.Steam;
import commons.api.SteamGame;
import commons.api.SteamGroup;
import commons.api.SteamLaunchMethod;
import commons.api.SteamProfile;
import commons.api.parsers.contexts.ContextualParser;
import commons.api.parsers.contexts.SteamProfileContexts;
import components.commons.ColoredTee;

/**
 * @author Naeregwen
 *
 */
public class SteamProfileParser extends ContextualParser<SteamProfileContexts> {

	/**
	 * Temporary parsing containers
	 */
	Integer initialPosition;
	Integer mostPlayedGameInitialPosition;
	Integer groupInitialPosition;
	private SteamGame game;
	private SteamGroup group;

	/**
	 * Final parsing container
	 */
	SteamProfile steamProfile;
	
	/**
	 * Create a ParametersParser
	 * 
	 * @param debug
	 * @param tee
	 */
	public SteamProfileParser(Parameters parameters, ColoredTee tee, Integer initialPosition) {
		super(parameters, tee);
		this.initialPosition = initialPosition;
		this.groupInitialPosition = 0;
		this.mostPlayedGameInitialPosition = 0;
	}
	
	/**
	 * @return the steamProfile
	 */
	public SteamProfile getSteamProfile() {
		return steamProfile;
	}

	/**
	 * Check current context against passed context
	 * Stay in same context
	 * 
	 * @param qName
	 * @param context
	 * @param contextsArrayLength
	 * @return
	 */
	protected boolean checkContextAndStay(String qName, SteamProfileContexts context) {
		return checkContext(qName, context, context, SteamProfileContexts.values().length, false);
	}
	
	/**
	 * Check current context against passed context
	 * Push current context and set next context
	 * 
	 * @param qName
	 * @param context
	 * @param contextsArrayLength
	 * @return
	 */
	protected boolean checkContextAndSave(String qName, SteamProfileContexts context, SteamProfileContexts nextContext) {
		return checkContext(qName, context, nextContext, SteamProfileContexts.values().length, true);
	}
	
	/*/
	 * (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#startDocument()
	 */
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		context = SteamProfileContexts.Start;
	}
	
	/*/
	 * (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		super.startElement(uri, localName, qName, attributes);
		
		// Update context
		// Create data containers
		if (qName.equalsIgnoreCase("response"))
			checkContextAndStay(qName, SteamProfileContexts.Start);
		else if (qName.equalsIgnoreCase("error")) {
			if (checkContextAndSave(qName, SteamProfileContexts.Start, SteamProfileContexts.Error)) {
				if (steamProfile == null)
					steamProfile = new SteamProfile();
				steamProfile.setLoadingSource(LoadingSource.Errored);
			}
		} else if (qName.equalsIgnoreCase("profile")) {
			if (checkContextAndSave(qName, SteamProfileContexts.Start, SteamProfileContexts.Profile)) {
				if (steamProfile == null)
					steamProfile = new SteamProfile();
				steamProfile.setLoadingSource(LoadingSource.Steam);
				steamProfile.setInitialPosition(initialPosition);
			}
		} else if (qName.equalsIgnoreCase("mostPlayedGames")) {
			if (checkContextAndSave(qName, SteamProfileContexts.Profile, SteamProfileContexts.MostPlayedGames))
				if (steamProfile != null)
					steamProfile.setMostplayedGames(new Vector<SteamGame>());
		} else if (qName.equalsIgnoreCase("mostPlayedGame")) {
			if (checkContextAndSave(qName, SteamProfileContexts.MostPlayedGames, SteamProfileContexts.MostPlayedGame)) {
				game = new SteamGame(LoadingSource.Steam);
				game.setInitialPosition(mostPlayedGameInitialPosition++);
				game.setSteamLaunchMethod(parameters.getDefaultSteamLaunchMethod() != null ? parameters.getDefaultSteamLaunchMethod() : SteamLaunchMethod.SteamProtocol);
			}
		} else if (qName.equalsIgnoreCase("groups")) {
			if (checkContextAndSave(qName, SteamProfileContexts.Profile, SteamProfileContexts.Groups))
				if (steamProfile != null)
					steamProfile.setSteamGroups(new Vector<SteamGroup>());
		} else if (qName.equalsIgnoreCase("group")) {
			if (checkContextAndSave(qName, SteamProfileContexts.Groups, SteamProfileContexts.Group)) {
				group = new SteamGroup();
				group.setInitialPosition(groupInitialPosition++);
			}
		}
	}
	
	/*/
	 * (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (context == SteamProfileContexts.Start || contexts.empty()) {
			tee.writelnError(String.format(parameters.getMessages().getString("errorCharactersOutOfContext"), new String(ch, start, length).trim()));
			characters = "";
		} else
			characters = new String(ch, start, length).trim();
		if (context == SteamProfileContexts.Error)
			steamProfile.setOnlineState(characters);
	}

	/*/
	 * (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		super.endElement(uri, localName, qName);
		
		// Catch error case
		if (qName.equalsIgnoreCase("steamID64")) {
			// steamID64
			if (checkContextAndStay(qName, SteamProfileContexts.Profile)) 
				steamProfile.setSteamID64(characters);
			
		} else if (qName.equalsIgnoreCase("steamID")) {
			// steamID
			if (checkContextAndStay(qName, SteamProfileContexts.Profile))
				steamProfile.setSteamID(characters);
			
		} else if (qName.equalsIgnoreCase("onlineState")) {
			// onlineState
			if (checkContextAndStay(qName, SteamProfileContexts.Profile))
				steamProfile.setOnlineState(characters);
			
		} else if (qName.equalsIgnoreCase("stateMessage")) {
			// stateMessage
			if (checkContextAndStay(qName, SteamProfileContexts.Profile))
				steamProfile.setStateMessage(characters);
			
		} else if (qName.equalsIgnoreCase("privacyState")) {
			// privacyState
			if (checkContextAndStay(qName, SteamProfileContexts.Profile))
				steamProfile.setPrivacyState(characters);
			
		} else if (qName.equalsIgnoreCase("visibilityState")) {
			// visibilityState
			if (checkContextAndStay(qName, SteamProfileContexts.Profile))
				steamProfile.setVisibilityState(characters);
			
		} else if (qName.equalsIgnoreCase("avatarIcon")) {
			// avatarIcon
			// Profile / Group
			if (context == SteamProfileContexts.Profile) {
				if (checkContextAndStay(qName, SteamProfileContexts.Profile))
					steamProfile.setAvatarIcon(characters);
			} else if (context == SteamProfileContexts.Group) {
				if (checkContextAndStay(qName, SteamProfileContexts.Group))
					if (group != null)
						group.setAvatarIcon(characters);
			} else
				checkContextAndStay(qName, SteamProfileContexts.Profile);

		} else if (qName.equalsIgnoreCase("avatarMedium")) {
			// avatarMedium
			// Profile / Group
			if (context == SteamProfileContexts.Profile) {
				if (checkContextAndStay(qName, SteamProfileContexts.Profile))
					steamProfile.setAvatarMedium(characters);
			} else if (context == SteamProfileContexts.Group) {
				if (checkContextAndStay(qName, SteamProfileContexts.Group))
					if (group != null)
						group.setAvatarMedium(characters);
			} else
				checkContextAndStay(qName, SteamProfileContexts.Profile);
			
		} else if (qName.equalsIgnoreCase("avatarFull")) {
			// avatarFull
			// Profile / Group
			if (context == SteamProfileContexts.Profile) {
				if (checkContextAndStay(qName, SteamProfileContexts.Profile))
					steamProfile.setAvatarFull(characters);
			} else if (context == SteamProfileContexts.Group) {
				if (checkContextAndStay(qName, SteamProfileContexts.Group))
					if (group != null)
						group.setAvatarFull(characters);
			} else
				checkContextAndStay(qName, SteamProfileContexts.Profile);
			
		} else if (qName.equalsIgnoreCase("vacBanned")) {
			// vacBanned
			if (checkContextAndStay(qName, SteamProfileContexts.Profile))
				try {
					Integer vacBanned = Integer.parseInt(characters);
					if (steamProfile != null) steamProfile.setVacBanned(vacBanned);
				} catch (NumberFormatException nfe) {}
			
		} else if (qName.equalsIgnoreCase("tradeBanState")) {
			// tradeBanState
			if (checkContextAndStay(qName, SteamProfileContexts.Profile))
				steamProfile.setTradeBanState(characters);
			
		} else if (qName.equalsIgnoreCase("isLimitedAccount")) {
			// isLimitedAccount
			if (checkContextAndStay(qName, SteamProfileContexts.Profile))
				try {
					Integer isLimitedAccount = Integer.parseInt(characters);
					if (steamProfile != null) steamProfile.setIsLimitedAccount(isLimitedAccount);
				} catch (NumberFormatException nfe) {}
			
		} else if (qName.equalsIgnoreCase("customURL")) {
			// customURL
			if (checkContextAndStay(qName, SteamProfileContexts.Profile))
				steamProfile.setCustomURL(characters);
			
		} else if (qName.equalsIgnoreCase("memberSince")) {
			// memberSince
			if (checkContextAndStay(qName, SteamProfileContexts.Profile))
				steamProfile.setMemberSince(characters);
			
		} else if (qName.equalsIgnoreCase("steamRating")) {
			// steamRating
			if (checkContextAndStay(qName, SteamProfileContexts.Profile))
				steamProfile.setSteamRating(characters);
			
		} else if (qName.equalsIgnoreCase("hoursPlayed2Wk")) {
			// hoursPlayed2Wk
			if (checkContextAndStay(qName, SteamProfileContexts.Profile))
				steamProfile.setHoursPlayedLast2Weeks(characters);
			
		} else if (qName.equalsIgnoreCase("headline")) {
			// headline
			// Profile / Group
			if (context == SteamProfileContexts.Profile) {
				if (checkContextAndStay(qName, SteamProfileContexts.Profile))
					steamProfile.setHeadline(characters);
			} else if (context == SteamProfileContexts.Group) {
				if (checkContextAndStay(qName, SteamProfileContexts.Group))
					if (group != null)
						group.setHeadline(characters);
			} else
				checkContextAndStay(qName, SteamProfileContexts.Profile);
			
		} else if (qName.equalsIgnoreCase("location")) {
			// location
			if (checkContextAndStay(qName, SteamProfileContexts.Profile))
				steamProfile.setLocation(characters);
			
		} else if (qName.equalsIgnoreCase("realname")) {
			// realname
			if (checkContextAndStay(qName, SteamProfileContexts.Profile))
				steamProfile.setRealname(characters);
			
		} else if (qName.equalsIgnoreCase("summary")) {
			// summary
			// Profile / Group
			if (context == SteamProfileContexts.Profile) {
				if (checkContextAndStay(qName, SteamProfileContexts.Profile))
					steamProfile.setSummary(characters);
			} else if (context == SteamProfileContexts.Group) {
				if (checkContextAndStay(qName, SteamProfileContexts.Group))
					if (group != null)
						group.setSummary(characters);
			} else
				checkContextAndStay(qName, SteamProfileContexts.Profile);
			
		// Add game to the list
		// Pop context anyway
		} else if(qName.equalsIgnoreCase("mostPlayedGame")) {
			if (checkPopContext(qName, SteamProfileContexts.MostPlayedGame) && steamProfile.getMostplayedGames() != null) {
				mostPlayedGameInitialPosition += 1;
				game.setInitialPosition(mostPlayedGameInitialPosition);
				steamProfile.getMostplayedGames().add(game);
			}
			context = contexts.pop();
		
		// Collect game data	
		} else if (qName.equalsIgnoreCase("gameName")) {
			if (checkContextAndStay(qName, SteamProfileContexts.MostPlayedGame) && game != null)
				game.setName(characters);
			
		} else if (qName.equalsIgnoreCase("gameLink")) {
			if (checkContextAndStay(qName, SteamProfileContexts.MostPlayedGame) && game != null) {
				game.setStoreLink(characters);
				// Extract AppId from gameLink when data is missing
				// Mostly for MostPlayedGame.
				// Other case could be that the SteamAPI results has changed 
				// but parser could have crashed long before this state
				if (game.getAppID() == null || game.getAppID().equals("")) {
					String appID = Steam.getAppIdFromGameLink(characters);
					if (appID != null)
						game.setAppID(appID);
				}
			}
			
		} else if (qName.equalsIgnoreCase("gameIcon")) {
			if (checkContextAndStay(qName, SteamProfileContexts.MostPlayedGame) && game != null)
				game.setIcon(characters);
			
		} else if (qName.equalsIgnoreCase("gameLogo")) {
			if (checkContextAndStay(qName, SteamProfileContexts.MostPlayedGame) && game != null)
				game.setLogo(characters);
			
		} else if (qName.equalsIgnoreCase("gameLogoSmall")) {
			if (checkContextAndStay(qName, SteamProfileContexts.MostPlayedGame) && game != null)
				game.setLogoSmall(characters);
			
		} else if (qName.equalsIgnoreCase("hoursPlayed")) {
			if (checkContextAndStay(qName, SteamProfileContexts.MostPlayedGame) && game != null)
				game.setHoursLast2Weeks(characters);
			
		} else if (qName.equalsIgnoreCase("hoursOnRecord")) {
			if (checkContextAndStay(qName, SteamProfileContexts.MostPlayedGame) && game != null)
				game.setHoursOnRecord(characters);
			
		} else if (qName.equalsIgnoreCase("statsName")) {
			if (checkContextAndStay(qName, SteamProfileContexts.MostPlayedGame) && game != null)
				game.setStatsLink(characters);
			
		} else if (qName.equalsIgnoreCase("globalStatsLink")) {
			if (checkContextAndStay(qName, SteamProfileContexts.MostPlayedGame) && game != null)
				game.setGlobalStatsLink(characters);
		
		// Add group to the list
		// Pop context anyway
		} else if(qName.equalsIgnoreCase("group")) {
			if (checkPopContext(qName, SteamProfileContexts.Group) && steamProfile.getSteamGroups() != null) {
				steamProfile.getSteamGroups().add(group);
			}
			context = contexts.pop();
			
		// Collect group data
		} else if (qName.equalsIgnoreCase("groupID64")) {
			if (checkPopContext(qName, SteamProfileContexts.Group) && group != null)
				group.setGroupID64(characters);
			
		} else if (qName.equalsIgnoreCase("groupName")) {
			if (checkPopContext(qName, SteamProfileContexts.Group) && group != null)
				group.setGroupName(characters);
			
		} else if (qName.equalsIgnoreCase("groupURL")) {
			if (checkPopContext(qName, SteamProfileContexts.Group) && group != null)
				group.setGroupURL(characters);
			
		} else if (qName.equalsIgnoreCase("headline")) {
			if (checkPopContext(qName, SteamProfileContexts.Group) && group != null)
				group.setHeadline(characters);
			
		} else if (qName.equalsIgnoreCase("summary")) {
			if (checkPopContext(qName, SteamProfileContexts.Group) && group != null)
				group.setSummary(characters);
			
		} else if (qName.equalsIgnoreCase("avatarIcon")) {
			if (checkPopContext(qName, SteamProfileContexts.Group) && group != null)
				group.setAvatarIcon(characters);
			
		} else if (qName.equalsIgnoreCase("avatarMedium")) {
			if (checkPopContext(qName, SteamProfileContexts.Group) && group != null)
				group.setAvatarMedium(characters);
			
		} else if (qName.equalsIgnoreCase("avatarFull")) {
			if (checkPopContext(qName, SteamProfileContexts.Group) && group != null)
				group.setAvatarFull(characters);
			
		} else if (qName.equalsIgnoreCase("memberCount")) {
			if (checkPopContext(qName, SteamProfileContexts.Group) && group != null)
				try {
					Integer memberCount = Integer.parseInt(characters);
					if (group != null) group.setMemberCount(memberCount);
				} catch (NumberFormatException nfe) {}
			
		} else if (qName.equalsIgnoreCase("membersInChat")) {
			if (checkPopContext(qName, SteamProfileContexts.Group) && group != null)
				try {
					Integer membersInChat = Integer.parseInt(characters);
					if (group != null) group.setMembersInChat(membersInChat);
				} catch (NumberFormatException nfe) {}
			
		} else if (qName.equalsIgnoreCase("membersInGame")) {
			if (checkPopContext(qName, SteamProfileContexts.Group) && group != null)
				try {
					Integer membersInGame = Integer.parseInt(characters);
					if (group != null) group.setMembersInGame(membersInGame);
				} catch (NumberFormatException nfe) {}
			
		} else if (qName.equalsIgnoreCase("membersOnline")) {
			if (checkPopContext(qName, SteamProfileContexts.Group) && group != null)
				try {
					Integer membersOnline = Integer.parseInt(characters);
					if (group != null) group.setMembersOnline(membersOnline);
				} catch (NumberFormatException nfe) {}
			
		// Pop context ?
		} else if(qName.equalsIgnoreCase("mostPlayedGames")) {
			if (checkPopContext(qName, SteamProfileContexts.MostPlayedGames))
				context = contexts.pop();
				
		} else if(qName.equalsIgnoreCase("groups")) {
			if (checkPopContext(qName, SteamProfileContexts.Groups))
				context = contexts.pop();
			
		} else if(qName.equalsIgnoreCase("profile")) {
			if (checkPopContext(qName, SteamProfileContexts.Profile))
				context = contexts.pop();
				
		}

	}	
	
	/**
	 * Dump marshaled profile to console
	 * 
	 * @param steamProfile
	 * @param tee
	 */
	public static void dump(SteamProfile steamProfile, ColoredTee tee) {
		if (steamProfile == null) return;
		try {
			Marshaller marshaller = JAXBContext.newInstance(SteamProfile.class).createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			marshaller.marshal(steamProfile, os);  
			tee.writelnMessage(Arrays.asList(new String(os.toByteArray()).split("\n")));
		} catch (JAXBException e) {
			tee.printStackTrace(e);
		}
	}
}
