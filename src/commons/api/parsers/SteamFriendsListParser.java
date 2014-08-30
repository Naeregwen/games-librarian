/**
 * 
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

import commons.ColoredTee;
import commons.api.Parameters;
import commons.api.SteamFriendsList;
import commons.api.SteamProfile;
import commons.api.parsers.contexts.ContextualParser;
import commons.api.parsers.contexts.SteamFriendsListContexts;

/**
 * @author Naeregwen
 *
 */
public class SteamFriendsListParser extends ContextualParser<SteamFriendsListContexts> {

	/**
	 * Temporary parsing containers
	 */
	SteamProfile steamProfile;
	Integer friendInitialPosition;
	
	/**
	 * Final parsing container
	 */
	SteamFriendsList steamFriendsList;
	
	public SteamFriendsListParser(Parameters parameters, ColoredTee tee) {
		super(parameters, tee);
		this.friendInitialPosition = 0;
	}

	/**
	 * @return the steamFriendsList
	 */
	public SteamFriendsList getSteamFriendsList() {
		return steamFriendsList;
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
		if (qName.equalsIgnoreCase("friendsList")) {
			steamFriendsList = new SteamFriendsList();
		} else if (qName.equalsIgnoreCase("friends")) {
			steamFriendsList.setFriends(new Vector<SteamProfile>());
		} else if (qName.equalsIgnoreCase("friend")) {
			steamProfile = new SteamProfile();
			steamProfile.setInitialPosition(friendInitialPosition++);
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

		if (qName.equalsIgnoreCase("steamID64")) {
			if (steamFriendsList != null) steamFriendsList.setSteamID64(characters);
		} else if (qName.equalsIgnoreCase("steamID")) {
			if (steamFriendsList != null) steamFriendsList.setSteamID(characters);
		} else if(qName.equalsIgnoreCase("friend")) {
			if (steamFriendsList != null) {
				steamProfile.setSteamID64(characters);
				// Add friend to the list
				steamFriendsList.add(steamProfile); 
			}
		}

	}	
	
	/**
	 * Dump marshaled profile to console
	 * 
	 * @param steamFriendsList
	 * @param tee
	 */
	public static void dump(SteamFriendsList steamFriendsList, ColoredTee tee) {
		try {
			Marshaller marshaller = JAXBContext.newInstance(SteamFriendsList.class).createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			marshaller.marshal(steamFriendsList, os);  
			tee.writelnMessage(Arrays.asList(new String(os.toByteArray()).split("\n")));
		} catch (JAXBException e) {
			tee.printStackTrace(e);
		}
	}
}
