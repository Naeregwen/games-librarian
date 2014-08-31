/**
 * 
 */
package components.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import commons.BundleManager;
import commons.GamesLibrary;
import commons.api.Parameters;
import commons.api.SteamGame;
import components.Librarian;
import components.GamesLibrarian.WindowBuilderMask;

/**
 * @author Naeregwen
 *
 */
public class SaveParametersAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2263273566679369655L;

	WindowBuilderMask me;
	String previousFilename;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public SaveParametersAction(WindowBuilderMask me) {
		this.me = me;
		translate();
	}

	/**
	 * Translate using BundleManager
	 */
	public void translate() {
		// Defensive code to avoid NullPointerException in WindowBuilder when data are empty in bundle (Mnemonic and accelerator are not mandatory)
		if (BundleManager.getUITexts(me, "saveParametersMnemonic") != null && !BundleManager.getUITexts(me, "saveParametersMnemonic").equals("")) // WindowBuilder
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "saveParametersMnemonic")).getKeyCode());
		if (BundleManager.getUITexts(me, "saveParametersAccelerator") != null && !BundleManager.getUITexts(me, "saveParametersAccelerator").equals("")) // WindowBuilder
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "saveParametersAccelerator")));
		putValue(NAME, BundleManager.getUITexts(me, "saveParametersMenuLabel"));
		putValue(SMALL_ICON, GamesLibrary.saveParametersIcon);
		putValue(SHORT_DESCRIPTION, BundleManager.getUITexts(me, "saveParametersToolTip"));
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		Librarian librarian =  me.getLibrarian();
		Parameters parameters = librarian.getParameters();
		String message;
		File file;
		
		// Check current application directory properties
		File curentDirectory = new File(".");
		
		// Check if current application is a directory
		if (!curentDirectory.isDirectory()) {
			JOptionPane.showMessageDialog(librarian.getView(), parameters.getUITexts().getString("errorSaveConfigurationLocationIsNotADirectory"), librarian.getApplicationTitle(), JOptionPane.OK_OPTION);
			return;
		}
		// Check if current application is a writable directory
		if (!curentDirectory.canWrite()) {
			JOptionPane.showMessageDialog(librarian.getView(), parameters.getUITexts().getString("errorSaveConfigurationLocationIsNotAWritableDirectory"), librarian.getApplicationTitle(), JOptionPane.OK_OPTION);
			return;
		}
		
		// Check current application parameters properties
		
		// Check if current application parameters GamerSteamID is set
		if (parameters.getMainPlayerSteamId() == null || parameters.getMainPlayerSteamId().trim().equals("")) {
			JOptionPane.showMessageDialog(librarian.getView(), parameters.getUITexts().getString("errorSaveConfigurationGamerSteamIDIsNotSet"), librarian.getApplicationTitle(), JOptionPane.OK_OPTION);
			return;
		}
		// Check if current application parameters GamesList is not empty
		int invalidGames = 0;
		if (parameters.getSteamGamesList() == null || parameters.getSteamGamesList().getSteamGames().size() <= 0) {
			JOptionPane.showMessageDialog(librarian.getView(), parameters.getUITexts().getString("errorSaveConfigurationGamesListIsEmpty"), librarian.getApplicationTitle(), JOptionPane.OK_OPTION);
			return;
		}
		// Check if current application parameters GamesList contains at least a valid game
		SteamGame steamGame;
		Iterator<SteamGame> steamGameIterator = parameters.getSteamGamesList().getSteamGames().iterator();
		while (steamGameIterator.hasNext()) {
			steamGame = steamGameIterator.next();
			message = steamGame.isValid();
			if (!message.equals("")) {
				librarian.getTee().writelnInfos(message);
				invalidGames++;
			}
		}
		if (invalidGames == parameters.getSteamGamesList().getSteamGames().size()) {
			JOptionPane.showMessageDialog(librarian.getView(), parameters.getUITexts().getString("errorSaveConfigurationGamesListIsEmpty"), librarian.getApplicationTitle(), JOptionPane.OK_OPTION);
			return;
		}

		// Confirm operation
		String filename = librarian.confirmSaveConfigurationFile(previousFilename != null ? previousFilename : Parameters.defaultConfigurationFilename);
		if (filename == null) return;
		previousFilename = filename;

		try {

			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

			// root element
			Document document = documentBuilder.newDocument();
			Element rootElement = document.createElement("parameters");
			document.appendChild(rootElement);

			// localeChoice element
			if (parameters.getLocaleChoice() != null) {
				Element localeChoice = document.createElement("localeChoice");
				localeChoice.appendChild(document.createTextNode(parameters.getLocaleChoice().toString()));
				rootElement.appendChild(localeChoice);
			}

			// gameChoice element
			if (parameters.getGameChoice() != null) {
				Element gameChoice = document.createElement("gameChoice");
				gameChoice.appendChild(document.createTextNode(parameters.getGameChoice().toString()));
				rootElement.appendChild(gameChoice);
			}

			// windowsDistribution element
			if (parameters.getWindowsDistribution() != null && !parameters.getWindowsDistribution().trim().equals("")) {
				Element windowsDistribution = document.createElement("windowsDistribution");
				windowsDistribution.appendChild(document.createTextNode(parameters.getWindowsDistribution()));
				rootElement.appendChild(windowsDistribution);
			}

			// steamExecutable element
			if (parameters.getSteamExecutable() != null && !parameters.getSteamExecutable().trim().equals("")) {
				Element steamExecutable = document.createElement("steamExecutable");
				steamExecutable.appendChild(document.createTextNode(parameters.getSteamExecutable()));
				rootElement.appendChild(steamExecutable);
			}
			
			// mainPlayerSteamId element
			Element gamerSteamId = document.createElement("gamerSteamId");
			gamerSteamId.appendChild(document.createTextNode(parameters.getMainPlayerSteamId()));
			rootElement.appendChild(gamerSteamId);

			// xmlOverrideRegistry element
			Element xmlOverrideRegistry = document.createElement("xmlOverrideRegistry");
			xmlOverrideRegistry.appendChild(document.createTextNode(new Boolean(parameters.isXmlOverrideRegistry()).toString()));
			rootElement.appendChild(xmlOverrideRegistry);

			// steamLaunchMethod element
			Element defaultSteamLaunchMethod = document.createElement("defaultSteamLaunchMethod");
			defaultSteamLaunchMethod.appendChild(document.createTextNode(parameters.getDefaultSteamLaunchMethod().toString()));
			rootElement.appendChild(defaultSteamLaunchMethod);

			// useConsole element
			Element useConsole = document.createElement("useConsole");
			useConsole.appendChild(document.createTextNode(new Boolean(parameters.isUseConsole()).toString()));
			rootElement.appendChild(useConsole);

			// useDateTime element
			Element useDateTime = document.createElement("useDateTime");
			useDateTime.appendChild(document.createTextNode(new Boolean(parameters.isUseDateTime()).toString()));
			rootElement.appendChild(useDateTime);

			// scrollLocked element
			Element scrollLocked = document.createElement("scrollLocked");
			scrollLocked.appendChild(document.createTextNode(new Boolean(parameters.isScrollLocked()).toString()));
			rootElement.appendChild(scrollLocked);
			
			// gamesList element
			Element gamesList = document.createElement("gamesList");
			rootElement.appendChild(gamesList);
			
			// Comment element
			Comment comment = document.createComment("Do not prevail on gamerSteamId");
			gamesList.appendChild(comment);
			
			// steamID64 element
			Element steamID64 = document.createElement("steamID64");
			steamID64.appendChild(document.createTextNode(parameters.getSteamGamesList().getSteamID64()));
			gamesList.appendChild(steamID64);
			
			// steamID element
			Element steamID = document.createElement("steamID");
			steamID.appendChild(document.createTextNode(parameters.getSteamGamesList().getSteamID()));
			gamesList.appendChild(steamID);
			
			// games element
			Element games = document.createElement("games");
			gamesList.appendChild(games);
			
			// gamesList element
			steamGameIterator = parameters.getSteamGamesList().getSteamGames().iterator();
			while (steamGameIterator.hasNext()) {
				
				steamGame = steamGameIterator.next();
				
				// Check game has minimal data to save
				message = steamGame.isValid();
				if (!message.equals("")) {
					librarian.getTee().writelnInfos(message);
					continue;
				}
				
				// game Element
				Element game = document.createElement("game");
				games.appendChild(game);
				
				// appID element
				Element appID = document.createElement("appID");
				appID.appendChild(document.createTextNode(steamGame.getAppID()));
				game.appendChild(appID);
				
				// name element
				Element name = document.createElement("name");
				name.appendChild(document.createTextNode(steamGame.getName()));
				game.appendChild(name);
				
				// logo element
				if (steamGame.getLogo() != null && !steamGame.getLogo().trim().equals("")) {
					Element logo = document.createElement("logo");
					logo.appendChild(document.createTextNode(steamGame.getLogo()));
					game.appendChild(logo);
				}
				
				// storeLink element
				if (steamGame.getStoreLink() != null && !steamGame.getStoreLink().trim().equals("")) {
					Element storeLink = document.createElement("storeLink");
					storeLink.appendChild(document.createTextNode(steamGame.getStoreLink()));
					game.appendChild(storeLink);
				}
				
				// hoursLast2Weeks element
				if (steamGame.getHoursLast2Weeks() != null && !steamGame.getHoursLast2Weeks().trim().equals("")) {
					Element hoursLast2Weeks = document.createElement("hoursLast2Weeks");
					hoursLast2Weeks.appendChild(document.createTextNode(steamGame.getHoursLast2Weeks()));
					game.appendChild(hoursLast2Weeks);
				}
				
				// hoursOnRecord element
				if (steamGame.getHoursOnRecord() != null && !steamGame.getHoursOnRecord().trim().equals("")) {
					Element hoursOnRecord = document.createElement("hoursOnRecord");
					hoursOnRecord.appendChild(document.createTextNode(steamGame.getHoursOnRecord()));
					game.appendChild(hoursOnRecord);
				}
				
				// statsLink element
				if (steamGame.getStatsLink() != null && !steamGame.getStatsLink().trim().equals("")) {
					Element statsLink = document.createElement("statsLink");
					statsLink.appendChild(document.createTextNode(steamGame.getStatsLink()));
					game.appendChild(statsLink);
				}
				
				// globalStatsLink element
				if (steamGame.getGlobalStatsLink() != null && !steamGame.getGlobalStatsLink().trim().equals("")) {
					Element globalStatsLink = document.createElement("globalStatsLink");
					globalStatsLink.appendChild(document.createTextNode(steamGame.getGlobalStatsLink()));
					game.appendChild(globalStatsLink);
				}
				
				// steamLaunchMethod element
				if (steamGame.getSteamLaunchMethod() != null) {
					Element steamLaunchMethod = document.createElement("steamLaunchMethod");
					steamLaunchMethod.appendChild(document.createTextNode(steamGame.getSteamLaunchMethod().name()));
					game.appendChild(steamLaunchMethod);
				}
				
				// arguments element
				if (steamGame.getArguments() != null && !steamGame.getArguments().trim().equals("")) {
					Element arguments = document.createElement("arguments");
					arguments.appendChild(document.createTextNode(steamGame.getArguments()));
					game.appendChild(arguments);
				}
				
			}

			// Prepare write
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			
			DOMSource source = new DOMSource(document);
			file = new File(filename);
			StreamResult result = new StreamResult(file);
			
			// Set indentation
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");
			
			// Write the content into xml file
			transformer.transform(source, result);

			try {
				Vector<String> messages = new Vector<String>();
				messages.add(parameters.getMessages().getString("saveConfigurationSuccess"));
				messages.add(file.getCanonicalFile().getCanonicalPath() + ".");
				librarian.getTee().writelnInfos(messages);
			} catch (IOException ioe) {
				librarian.getTee().printStackTrace(ioe);
			}

		} catch (ParserConfigurationException pce) {
			librarian.getTee().printStackTrace(pce);
		} catch (TransformerException tfe) {
			librarian.getTee().printStackTrace(tfe);
		}
	}

}
