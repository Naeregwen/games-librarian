/**
 * 
 */
package commons.api.parsers;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.UIManager.LookAndFeelInfo;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import commons.ColoredTee;
import commons.GamesLibrary.LoadingSource;
import commons.Strings;
import commons.api.Parameters;
import commons.api.SteamGame;
import commons.api.SteamGamesList;
import commons.api.SteamLaunchMethod;
import commons.api.parsers.contexts.ContextualParser;
import commons.api.parsers.contexts.ParametersContexts;
import commons.enums.ButtonsDisplayMode;
import commons.enums.DumpMode;
import commons.enums.GameChoice;
import commons.enums.GameLeftClickAction;
import commons.enums.LocaleChoice;
import commons.enums.SteamFriendsDisplayMode;
import commons.enums.SteamGroupsDisplayMode;
import commons.enums.helpers.ParsableEnum;

/**
 * @author Naeregwen
 *
 */
public class ParametersParser extends ContextualParser<ParametersContexts> {

	/**
	 * Temporary parsing containers
	 */
	private SteamGame game;
	private Integer position;
	
	/**
	 * Create a ParametersParser
	 * 
	 * @param parameters parameters to set
	 * @param tee ColoredTee to display messages 
	 */
	public ParametersParser(Parameters parameters, ColoredTee tee) {
		super(parameters, tee);
	}
	
	/**
	 * Check current context against passed context
	 * Stay in same context
	 * 
	 * @param qName
	 * @param context
	 * @return
	 */
	private boolean checkContextAndStay(String qName, ParametersContexts context) {
		return checkContext(qName, context, context, ParametersContexts.values().length, false);
	}
	
	/**
	 * Check current context against passed context
	 * Push current context and set next context
	 * 
	 * @param qName
	 * @param context
	 * @param nextContext
	 * @return
	 */
	private boolean checkContextAndSave(String qName, ParametersContexts context, ParametersContexts nextContext) {
		return checkContext(qName, context, nextContext, ParametersContexts.values().length, true);
	}
	
	/*/
	 * (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#startDocument()
	 */
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		context = ParametersContexts.Start;
		position = 0;
	}
	
	/**
	 * Try to parse characters as Enumeration
	 * <ul>
	 * <li>by their ordinal identifier</li>
	 * <li>or by their alphabetical identifier</li>
	 * </ul>
	 * 
	 * @param qName qualified tag name 
	 * @param defaultValue value carrying the type to parse
	 * @return parsed value or defaultValue
	 */
	private Enum<?> parseEnum(String qName, Enum<?> defaultValue, Pattern pattern) {
		Enum<?> value = defaultValue;
		Matcher matcher = Strings.startNumericPattern.matcher(characters);
		if (matcher.find()) {
			try {
				Integer integer = Integer.parseInt(characters);
				if (integer >= defaultValue.getClass().getEnumConstants().length)
					tee.writelnError(String.format(parameters.getMessages().getString("errorUnexpectedValue"), characters, qName, ParsableEnum.getAcceptableValues(defaultValue)));
				else
					value = defaultValue.getClass().getEnumConstants()[integer];
			} catch (NumberFormatException e) {
				tee.writelnError(String.format(parameters.getMessages().getString("errorUnexpectedValue"), characters, qName, ParsableEnum.getAcceptableValues(defaultValue)));
			}
		} else {
			boolean patternMatched = pattern != null ? pattern.matcher(characters).find() : true;
			if (patternMatched) {
				Enum<?> newValue = (Enum<?>) ParsableEnum.getAcceptableValue(characters, defaultValue);
				if (newValue == null)
					tee.writelnError(String.format(parameters.getMessages().getString("errorUnexpectedValue"), characters, qName, ParsableEnum.getAcceptableValues(defaultValue)));
				else
					value = newValue;
			} else {
				tee.writelnError(String.format(parameters.getMessages().getString("errorUnexpectedValue"), characters, qName, ParsableEnum.getAcceptableValues(Parameters.defaultLocaleChoice)));
			}
		}				
		return value;
	}
	
	/*/
	 * (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		super.startElement(uri, localName, qName, attributes);
		
		// Update context
		// Create data container
		if (qName.equalsIgnoreCase("parameters")) {
			checkContextAndSave(qName, ParametersContexts.Start, ParametersContexts.Parameters);
		} else if (qName.equalsIgnoreCase("gamesList")) {
			if (checkContextAndSave(qName, ParametersContexts.Parameters, ParametersContexts.GamesList)) {
				parameters.setSteamGamesList(new SteamGamesList());
				parameters.getSteamGamesList().setLoadingSource(LoadingSource.Preloading);
			}
		} else if (qName.equalsIgnoreCase("games")) {
			checkContextAndSave(qName, ParametersContexts.GamesList, ParametersContexts.Games);
		} else if (qName.equalsIgnoreCase("game")) {
			if (checkContextAndSave(qName, ParametersContexts.Games, ParametersContexts.Game)) {
				game = new SteamGame(LoadingSource.Preloading);
				game.setSteamLaunchMethod(parameters.getDefaultSteamLaunchMethod() != null ? parameters.getDefaultSteamLaunchMethod() : SteamLaunchMethod.SteamProtocol);
			}
		}
	}
	
	/*/
	 * (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (context == ParametersContexts.Start || contexts.empty()) {
			tee.writelnError(String.format(parameters.getMessages().getString("errorCharactersOutOfContext"), new String(ch, start, length).trim()));
			characters = "";
		} else
			characters = new String(ch, start, length).trim();
	}
	
	/*/
	 * (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		super.endElement(uri, localName, qName);
		
		// Collect steam parameters
		if (qName.equalsIgnoreCase("steamID64")) {
			// steamID64
			if (checkContextAndStay(qName, ParametersContexts.GamesList) && parameters.getSteamGamesList() != null) 
				parameters.getSteamGamesList().setSteamID64(characters);
			
		} else if (qName.equalsIgnoreCase("steamID")) {
			// steamID
			if (checkContextAndStay(qName, ParametersContexts.GamesList) && parameters.getSteamGamesList() != null) 
				parameters.getSteamGamesList().setSteamID(characters);
		
		// Collect startup parameters
		} else if (qName.equalsIgnoreCase("localeChoice")) {
			// localeChoice
			if (checkContextAndStay(qName, ParametersContexts.Parameters))
				parameters.setLocaleChoice((LocaleChoice) parseEnum(qName, Parameters.defaultLocaleChoice, LocaleChoice.localePattern));
			
		} else if (qName.equalsIgnoreCase("lookAndFeelInfo")) {
			// lookAndFeelInfo
			if (checkContextAndStay(qName, ParametersContexts.Parameters)) {
				if (characters.trim().length() > 0) {
					boolean lookAndFeelInstalled = false;
					for (LookAndFeelInfo lookAndFeelInfo : Parameters.lookAndFeelInfos) {
						if (characters.equals(lookAndFeelInfo.getName())) {
							parameters.setLookAndFeelInfo(lookAndFeelInfo);
							lookAndFeelInstalled = true;
							break;
						}
					}
					if (!lookAndFeelInstalled) {
						StringBuffer lookAndFeelNamesList = new StringBuffer();
						for (LookAndFeelInfo lookAndFeelInfo : Parameters.lookAndFeelInfos)
							lookAndFeelNamesList.append((lookAndFeelNamesList.length() > 0 ? ", " : "") + lookAndFeelInfo.getName());
						tee.writelnError(String.format(parameters.getMessages().getString("errorUnexpectedValue"), characters, qName, lookAndFeelNamesList));
					}
				}
			}
			
		} else if (qName.equalsIgnoreCase("windowsDistribution")) {
			// windowsDistribution
			if (checkContextAndStay(qName, ParametersContexts.Parameters)) 
				parameters.setWindowsDistribution(characters);

		} else if (qName.equalsIgnoreCase("steamExecutable")) {
			// steamExecutable
			if (checkContextAndStay(qName, ParametersContexts.Parameters)) 
				parameters.setSteamExecutable(characters);
			
		} else if (qName.equalsIgnoreCase("gamerSteamId")) {
			// gamerSteamId
			if (checkContextAndStay(qName, ParametersContexts.Parameters)) 
				parameters.setMainPlayerSteamId(characters);
			
		} else if (qName.equalsIgnoreCase("gameChoice")) {
			// gameChoice
			if (checkContextAndStay(qName, ParametersContexts.Parameters))
				parameters.setGameChoice((GameChoice) parseEnum(qName, Parameters.defaultGameChoice, null));

		} else if (qName.equalsIgnoreCase("gameLeftClickAction")) {
			// gameLeftClickAction
			if (checkContextAndStay(qName, ParametersContexts.Parameters))
				parameters.setGameLeftClickAction((GameLeftClickAction) parseEnum(qName, Parameters.defaultGameLeftClickAction, null));
			
		} else if (qName.equalsIgnoreCase("defaultSteamLaunchMethod")) {
			// defaultSteamLaunchMethod
			if (checkContextAndStay(qName, ParametersContexts.Parameters))
				parameters.setDefaultSteamLaunchMethod((SteamLaunchMethod) parseEnum(qName, Parameters.defaultDefaultSteamLaunchMethod, null));
			
		} else if (qName.equalsIgnoreCase("dumpMode")) {
			// dumpMode
			if (checkContextAndStay(qName, ParametersContexts.Parameters))
				parameters.setDumpMode((DumpMode) parseEnum(qName, Parameters.defaultDumpMode, null));
			
		} else if (qName.equalsIgnoreCase("buttonsDisplayMode")) {
			// buttonsDisplayMode
			if (checkContextAndStay(qName, ParametersContexts.Parameters))
				parameters.setButtonsDisplayMode((ButtonsDisplayMode) parseEnum(qName, Parameters.defaultButtonsDisplayMode, null));
			
		} else if (qName.equalsIgnoreCase("steamGroupsDisplayMode")) {
			// steamGroupsDisplayMode
			if (checkContextAndStay(qName, ParametersContexts.Parameters))
				parameters.setSteamGroupsDisplayMode((SteamGroupsDisplayMode) parseEnum(qName, Parameters.defaultSteamGroupsDisplayMode, null));
			
		} else if (qName.equalsIgnoreCase("steamFriendsDisplayMode")) {
			// steamFriendsDisplayMode
			if (checkContextAndStay(qName, ParametersContexts.Parameters))
				parameters.setSteamFriendsDisplayMode((SteamFriendsDisplayMode) parseEnum(qName, Parameters.defaultSteamFriendsDisplayMode, null));
			
		} else if (qName.equalsIgnoreCase("displayTooltips")) {
			// useConsole
			if (checkContextAndStay(qName, ParametersContexts.Parameters))
				parameters.setDisplayTooltips(parseBooleanAlternatives(characters, qName));

		} else if (qName.equalsIgnoreCase("debug")) {
			// debug
			if (checkContextAndStay(qName, ParametersContexts.Parameters))
				parameters.setDebug(parseBooleanAlternatives(characters, qName));

		} else if (qName.equalsIgnoreCase("checkCommunityOnStartup")) {
			// checkCommunityOnStartup
			if (checkContextAndStay(qName, ParametersContexts.Parameters))
				parameters.setCheckCommunityOnStartup(parseBooleanAlternatives(characters, qName));

		} else if (qName.equalsIgnoreCase("useConsole")) {
			// useConsole
			if (checkContextAndStay(qName, ParametersContexts.Parameters))
				parameters.setUseConsole(parseBooleanAlternatives(characters, qName));

		} else if (qName.equalsIgnoreCase("useDateTime")) {
			// useDateTime
			if (checkContextAndStay(qName, ParametersContexts.Parameters))
				parameters.setUseDateTime(parseBooleanAlternatives(characters, qName));

		} else if (qName.equalsIgnoreCase("scrollLocked")) {
			// scrollLocked
			if (checkContextAndStay(qName, ParametersContexts.Parameters))
				parameters.setScrollLocked(parseBooleanAlternatives(characters, qName));
			
		} else if (qName.equalsIgnoreCase("xmlOverrideRegistry")) {
			// xmlOverrideRegistry
			if (checkContextAndStay(qName, ParametersContexts.Parameters))
				parameters.setXmlOverrideRegistry(parseBooleanAlternatives(characters, qName));
			
		// Add a game to the list
		// Pop context anyway
		} else if(qName.equalsIgnoreCase("game")) {
			if (checkPopContext(qName, ParametersContexts.Game) && game != null) {
				String message = game.isValid();
				if (message.equals(""))
					if (parameters.getSteamGamesList() != null) {
						position += 1;
						game.setInitialPosition(position);
						parameters.getSteamGamesList().add(game);
					} else
						tee.writelnInfos(message);
			}
			context = contexts.pop();
		
		// Collect game data
		} else if (qName.equalsIgnoreCase("appID")) {
			if (checkContextAndStay(qName, ParametersContexts.Game) && game != null)
				game.setAppID(characters);
			
		} else if (qName.equalsIgnoreCase("name")) {
			if (checkContextAndStay(qName, ParametersContexts.Game) && game != null)
				game.setName(characters);
			
		} else if (qName.equalsIgnoreCase("logo")) {
			if (checkContextAndStay(qName, ParametersContexts.Game) && game != null)
				game.setLogo(characters);
			
		} else if (qName.equalsIgnoreCase("storeLink")) {
			if (checkContextAndStay(qName, ParametersContexts.Game) && game != null)
				game.setStoreLink(characters);
			
		} else if (qName.equalsIgnoreCase("hoursLast2Weeks")) {
			if (checkContextAndStay(qName, ParametersContexts.Game) && game != null)
				game.setHoursLast2Weeks(characters);
			
		} else if (qName.equalsIgnoreCase("hoursOnRecord")) {
			if (checkContextAndStay(qName, ParametersContexts.Game) && game != null)
				game.setHoursOnRecord(characters);
			
		} else if (qName.equalsIgnoreCase("statsLink")) {
			if (checkContextAndStay(qName, ParametersContexts.Game) && game != null)
				game.setStatsLink(characters);
			
		} else if (qName.equalsIgnoreCase("globalStatsLink")) {
			if (checkContextAndStay(qName, ParametersContexts.Game) && game != null)
				game.setGlobalStatsLink(characters);
			
		} else if (qName.equalsIgnoreCase("steamLaunchMethod")) {
			if (checkContextAndStay(qName, ParametersContexts.Game) && game != null) {
				SteamLaunchMethod steamLaunchMethod = (SteamLaunchMethod) parseEnum(qName, Parameters.defaultDefaultSteamLaunchMethod, null);
				if (steamLaunchMethod != null)
					game.setSteamLaunchMethod(steamLaunchMethod);
			}
			
		} else if (qName.equalsIgnoreCase("arguments")) {
			if (checkContextAndStay(qName, ParametersContexts.Game) && game != null)
				game.setArguments(characters);
			
		// Pop context ?
		} else if(qName.equalsIgnoreCase("games")) {
			if (checkPopContext(qName, ParametersContexts.Games))
				context = contexts.pop();
			
		} else if(qName.equalsIgnoreCase("gamesList")) {
			if (checkPopContext(qName, ParametersContexts.GamesList))
				context = contexts.pop();
			
		} else if(qName.equalsIgnoreCase("parameters")) {
			if (checkPopContext(qName, ParametersContexts.Parameters))
				context = contexts.pop();
		}

	}	

	/**
	 * Parse XML parameters from filename
	 * 
	 * @param parametersParser
	 * @param filename
	 * @param tee
	 * @return
	 */
	public Parameters parse(String filename, ColoredTee tee) {
		try {
			XMLReader parametersReader = XMLReaderFactory.createXMLReader();
			parametersReader.setContentHandler(this);
			parametersReader.parse(new InputSource(new FileInputStream(filename)));
		} catch (SAXException | IOException e) {
			tee.printStackTrace(e);
		}
		return parameters;
	}
	
	
	/**
	 * Dump marshaled parameters to console
	 * 
	 * @param parameters
	 * @param tee
	 */
	public static void dump(Parameters parameters, ColoredTee tee) {
		try {
			Marshaller marshaller = JAXBContext.newInstance(Parameters.class).createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			marshaller.marshal(parameters, os);  
			// FIXME: Parameters could contains a lot of data, freezing the GUI. Worker ?
			tee.writelnMessage(Arrays.asList(new String(os.toByteArray()).split("\n")));
		} catch (JAXBException e) {
			tee.printStackTrace(e);
		}
	}
}
