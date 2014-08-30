package commons.api.parsers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import commons.GamesLibrary.LoadingSource;
import commons.api.SteamGame;
import commons.api.SteamGamesList;
import commons.api.SteamLaunchMethod;


public class SteamGamesListParser extends DefaultHandler {

	/**
	 * Temporary parsing containers
	 */
	SteamLaunchMethod defaultSteamLaunchMethod;
	LoadingSource loadingSource;
	
	private String characters;
	private SteamGame game;
	Integer position;
	
	/**
	 * Final parsing container
	 */
	SteamGamesList gamesList;
	
	
	public SteamGamesListParser(SteamLaunchMethod defaultSteamLaunchMethod, LoadingSource loadingSource) {
		this.defaultSteamLaunchMethod = defaultSteamLaunchMethod;
		this.loadingSource = loadingSource;
	}

	/**
	 * @return the gamesList
	 */
	public SteamGamesList getGamesList() {
		return gamesList;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		position = 0;
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
		if (qName.equalsIgnoreCase("gamesList")) {
			gamesList = new SteamGamesList();
			gamesList.setLoadingSource(loadingSource);
		} else if (qName.equalsIgnoreCase("game")) {
			game = new SteamGame(loadingSource);
			game.setSteamLaunchMethod(defaultSteamLaunchMethod);
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
			if (gamesList != null) gamesList.setSteamID64(characters);
		} else if (qName.equalsIgnoreCase("steamID")) {
			if (gamesList != null) gamesList.setSteamID(characters);
		} else if(qName.equalsIgnoreCase("game")) {
			if (gamesList != null) {
				// complete game data
				position += 1;
				game.setInitialPosition(position);
				if (game.getHoursLast2Weeks() == null)
					game.setHoursLast2Weeks("0");
				if (game.getHoursOnRecord() == null)
					game.setHoursOnRecord("0");
				// Add game to the list
				gamesList.add(game); 
			}
		} else if (qName.equalsIgnoreCase("appID")) {
			if (game != null) game.setAppID(characters);
		} else if (qName.equalsIgnoreCase("name")) {
			if (game != null) game.setName(characters);
		} else if (qName.equalsIgnoreCase("logo")) {
			if (game != null) game.setLogo(characters);
		} else if (qName.equalsIgnoreCase("storeLink")) {
			if (game != null) game.setStoreLink(characters);
		} else if (qName.equalsIgnoreCase("hoursLast2Weeks")) {
			if (game != null) game.setHoursLast2Weeks(characters);
		} else if (qName.equalsIgnoreCase("hoursOnRecord")) {
			if (game != null) game.setHoursOnRecord(characters);
		} else if (qName.equalsIgnoreCase("statsLink")) {
			if (game != null) game.setStatsLink(characters);
		} else if (qName.equalsIgnoreCase("globalStatsLink")) {
			if (game != null) game.setGlobalStatsLink(characters);
		} else if (qName.equalsIgnoreCase("arguments")) {
			if (game != null) game.setArguments(characters);
		} else if (qName.equalsIgnoreCase("steamLaunchMethod")) {
			if (game != null) game.setSteamLaunchMethod(SteamLaunchMethod.valueOf(characters));
		}

	}	
	
}
