/**
 * 
 */
package commons.api.parsers;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;

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
import commons.enums.DumpMode;
import commons.enums.GameChoice;
import commons.enums.GameLeftClickAction;
import commons.enums.LocaleChoice;

/**
 * @author Naeregwen
 *
 */
public class ParametersParser extends ContextualParser<ParametersContexts> {

	/**
	 * Temporary parsing containers
	 */
	private SteamGame game;
	Integer position;
	
	/**
	 * Create a ParametersParser
	 * 
	 * @param debug
	 * @param tee
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
	 * @param contextsArrayLength
	 * @return
	 */
	protected boolean checkContextAndStay(String qName, ParametersContexts context) {
		return checkContext(qName, context, context, ParametersContexts.values().length, false);
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
	protected boolean checkContextAndSave(String qName, ParametersContexts context, ParametersContexts nextContext) {
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
			LocaleChoice localeChoice = LocaleChoice.values()[0]; // Default value - en_US;
			if (checkContextAndStay(qName, ParametersContexts.Parameters)) {
				Matcher matcher = Strings.startNumericPattern.matcher(characters);
				if (matcher.find()) {
					try {
						Integer integer = Integer.parseInt(characters);
						if (integer >= LocaleChoice.values().length) {
							tee.writelnError(String.format(parameters.getMessages().getString("errorUnexpectedValue"), characters, qName, LocaleChoice.getAcceptableValues()));
						} else {
							localeChoice = LocaleChoice.values()[integer];
						}
					} catch (NumberFormatException e) {
						tee.writelnError(String.format(parameters.getMessages().getString("errorUnexpectedValue"), characters, qName, LocaleChoice.getAcceptableValues()));
					}
				} else {
					matcher = LocaleChoice.localePattern.matcher(characters);
					if (matcher.find()) {
						LocaleChoice newLocaleChoice = LocaleChoice.getAcceptableValue(characters);
						if (newLocaleChoice == null)
							tee.writelnError(String.format(parameters.getMessages().getString("errorUnexpectedValue"), characters, qName, LocaleChoice.getAcceptableValues()));
						else
							localeChoice = newLocaleChoice;
					} else {
						tee.writelnError(String.format(parameters.getMessages().getString("errorUnexpectedValue"), characters, qName, LocaleChoice.getAcceptableValues()));
					}
				}
				parameters.setLocaleChoice(localeChoice);
			}
			
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
			
		} else if (qName.equalsIgnoreCase("dumpMode")) {
			// dumpMode
			DumpMode dumpMode = DumpMode.values()[0]; // Default value;
			if (checkContextAndStay(qName, ParametersContexts.Parameters)) {
				Matcher matcher = Strings.startNumericPattern.matcher(characters);
				if (matcher.find()) {
					try {
						Integer integer = Integer.parseInt(characters);
						if (integer >= DumpMode.values().length) {
							tee.writelnError(String.format(parameters.getMessages().getString("errorUnexpectedValue"), characters, qName, DumpMode.getAcceptableValues()));
						} else {
							dumpMode = DumpMode.values()[integer];
						}
					} catch (NumberFormatException e) {
						tee.writelnError(String.format(parameters.getMessages().getString("errorUnexpectedValue"), characters, qName, DumpMode.getAcceptableValues()));
					}
				} else {
					DumpMode newDumpMode = DumpMode.getAcceptableValue(characters);
					if (newDumpMode == null)
						tee.writelnError(String.format(parameters.getMessages().getString("errorUnexpectedValue"), characters, qName, DumpMode.getAcceptableValues()));
					else
						dumpMode = newDumpMode;
				}				
			}
			parameters.setDumpMode(dumpMode);
			
		} else if (qName.equalsIgnoreCase("gameChoice")) {
			// gameChoice
			GameChoice gameChoice = GameChoice.values()[0]; // Default value - One;
			if (checkContextAndStay(qName, ParametersContexts.Parameters)) {
				Matcher matcher = Strings.startNumericPattern.matcher(characters);
				if (matcher.find()) {
					try {
						Integer integer = Integer.parseInt(characters);
						if (integer >= GameChoice.values().length) {
							tee.writelnError(String.format(parameters.getMessages().getString("errorUnexpectedValue"), characters, qName, GameChoice.getAcceptableValues()));
						} else {
							gameChoice = GameChoice.values()[integer];
						}
					} catch (NumberFormatException e) {
						tee.writelnError(String.format(parameters.getMessages().getString("errorUnexpectedValue"), characters, qName, GameChoice.getAcceptableValues()));
					}
				} else {
					GameChoice newGameChoice = GameChoice.getAcceptableValue(characters);
					if (newGameChoice == null)
						tee.writelnError(String.format(parameters.getMessages().getString("errorUnexpectedValue"), characters, qName, GameChoice.getAcceptableValues()));
					else
						gameChoice = newGameChoice;
				}
			}
			parameters.setGameChoice(gameChoice);

		} else if (qName.equalsIgnoreCase("gameLeftClickAction")) {
			// gameLeftClickAction
			GameLeftClickAction gameLeftClickAction = GameLeftClickAction.values()[0]; // Default value
			if (checkContextAndStay(qName, ParametersContexts.Parameters)) {
				Matcher matcher = Strings.startNumericPattern.matcher(characters);
				if (matcher.find()) {
					try {
						Integer integer = Integer.parseInt(characters);
						if (integer >= GameLeftClickAction.values().length) {
							tee.writelnError(String.format(parameters.getMessages().getString("errorUnexpectedValue"), characters, qName, GameLeftClickAction.getAcceptableValues()));
						} else {
							gameLeftClickAction = GameLeftClickAction.values()[integer];
						}
					} catch (NumberFormatException e) {
						tee.writelnError(String.format(parameters.getMessages().getString("errorUnexpectedValue"), characters, qName, GameLeftClickAction.getAcceptableValues()));
					}
				} else {
					GameLeftClickAction newGameLeftClickAction = GameLeftClickAction.getAcceptableValue(characters);
					if (newGameLeftClickAction == null)
						tee.writelnError(String.format(parameters.getMessages().getString("errorUnexpectedValue"), characters, qName, GameLeftClickAction.getAcceptableValues()));
					else
						gameLeftClickAction = newGameLeftClickAction;
				}
			}
			parameters.setGameLeftClickAction(gameLeftClickAction);
			
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
			
		} else if (qName.equalsIgnoreCase("xmlOverrideRegistry")) {
			// xmlOverrideRegistry
			if (checkContextAndStay(qName, ParametersContexts.Parameters))
				parameters.setXmlOverrideRegistry(parseBooleanAlternatives(characters, qName));
			
		} else if (qName.equalsIgnoreCase("defaultSteamLaunchMethod")) {
			// defaultSteamLaunchMethod
			SteamLaunchMethod steamLaunchMethod = SteamLaunchMethod.values()[0]; // Default value;
			if (checkContextAndStay(qName, ParametersContexts.Parameters)) {
				Matcher matcher = Strings.startNumericPattern.matcher(characters);
				if (matcher.find()) {
					try {
						Integer integer = Integer.parseInt(characters);
						if (integer >= SteamLaunchMethod.values().length) {
							tee.writelnError(String.format(parameters.getMessages().getString("errorUnexpectedValue"), characters, qName, SteamLaunchMethod.getAcceptableValues()));
						} else {
							steamLaunchMethod = SteamLaunchMethod.values()[integer];
						}
					} catch (NumberFormatException e) {
						tee.writelnError(String.format(parameters.getMessages().getString("errorUnexpectedValue"), characters, qName, SteamLaunchMethod.getAcceptableValues()));
					}
				} else {
					SteamLaunchMethod newSteamLaunchMethod = SteamLaunchMethod.getAcceptableValue(characters);
					if (newSteamLaunchMethod == null)
						tee.writelnError(String.format(parameters.getMessages().getString("errorUnexpectedValue"), characters, qName, SteamLaunchMethod.getAcceptableValues()));
					else
						steamLaunchMethod = newSteamLaunchMethod;
				}				
			}
			parameters.setDefaultSteamLaunchMethod(steamLaunchMethod);
			
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
				SteamLaunchMethod steamLaunchMethod = null; // No default value;
				Matcher matcher = Strings.startNumericPattern.matcher(characters);
				if (matcher.find()) {
					try {
						Integer integer = Integer.parseInt(characters);
						if (integer >= SteamLaunchMethod.values().length) {
							tee.writelnError(String.format(parameters.getMessages().getString("errorUnexpectedValue"), characters, qName, SteamLaunchMethod.getAcceptableValues()));
						} else {
							steamLaunchMethod = SteamLaunchMethod.values()[integer];
						}
					} catch (NumberFormatException e) {
						tee.writelnError(String.format(parameters.getMessages().getString("errorUnexpectedValue"), characters, qName, SteamLaunchMethod.getAcceptableValues()));
					}
				} else {
					SteamLaunchMethod newSteamLaunchMethod = SteamLaunchMethod.getAcceptableValue(characters);
					if (newSteamLaunchMethod == null)
						tee.writelnError(String.format(parameters.getMessages().getString("errorUnexpectedValue"), characters, qName, SteamLaunchMethod.getAcceptableValues()));
					else
						steamLaunchMethod = newSteamLaunchMethod;
				}				
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
			tee.writelnMessage(Arrays.asList(new String(os.toByteArray()).split("\n")));
		} catch (JAXBException e) {
			tee.printStackTrace(e);
		}
	}
}
