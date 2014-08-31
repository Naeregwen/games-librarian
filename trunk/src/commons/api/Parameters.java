/**
 * 
 */
package commons.api;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import commons.OS;
import commons.enums.DumpMode;
import commons.enums.GameChoice;
import commons.enums.GameLeftClickAction;
import commons.enums.LocaleChoice;
import commons.enums.SteamFriendsDisplayMode;
import commons.enums.SteamGroupsDisplayMode;
import components.GamesLibrarian;
import components.buttons.observables.GameLeftClickActionObservables;
import components.buttons.observers.GameLeftClickActionObserver;

/**
 * @author Naeregwen
 *
 */
@XmlRootElement (name = "parameters")
@XmlType (propOrder = {
		
	"os", 
	
	"localeChoice",
	"lookAndFeelInfo",
	"UITexts",
	"messages",
	"resources",
	
	"windowsDistribution",
	"steamExecutable",
	
	"mainPlayerSteamId",
	
	"gameChoice",
	"gameLeftClickAction",
	"defaultSteamLaunchMethod",
	"steamGroupsDisplayMode",
	"steamFriendsDisplayMode",
	"displayTooltips",
	
	"debug",
	"checkCommunityOnStartup",
	"dumpMode",
	"useConsole",
	"useDateTime",
	"scrollLocked",
	"xmlOverrideRegistry",
	
	"steamGamesList"
})
public class Parameters implements GameLeftClickActionObservables {
	
	public static final String defaultConfigurationFilename = GamesLibrarian.class.getSimpleName() + ".xml";
	public static final LookAndFeelInfo[] lookAndFeelInfos = UIManager.getInstalledLookAndFeels();
	
	public static final LocaleChoice defaultLocaleChoice = LocaleChoice.values()[0];
	public static final LookAndFeelInfo defaultLookAndFeelInfo = lookAndFeelInfos[0];
	
	public static final String defaultWindowsDistribution = "";
	public static final String defaultSteamExecutable = "";
	public static final String defaultMainPlayerSteamId = "";
	
	public static final GameChoice defaultGameChoice = GameChoice.values()[0];
	public static final GameLeftClickAction defaultGameLeftClickAction = GameLeftClickAction.Select;
	public static final SteamLaunchMethod defaultDefaultSteamLaunchMethod = SteamLaunchMethod.SteamProtocol;
	public static final SteamGroupsDisplayMode defaultSteamGroupsDisplayMode = SteamGroupsDisplayMode.values()[0];
	public static final SteamFriendsDisplayMode defaultSteamFriendsDisplayMode = SteamFriendsDisplayMode.values()[0];
	public static final Boolean defaultDisplayToolTips = true;
	
	public static final Boolean defaultDebug = false;
	public static final Boolean defaultCheckCommunityOnStartup = false;
	public static final DumpMode defaultDumpMode = DumpMode.values()[0];
	public static final Boolean defaultUseConsole = true;
	public static final Boolean defaultUseDateTime = true;
	public static final Boolean defaultScrollLocked = false;
	public static final Boolean defaultXmlOverrideRegistry = false;
	
	OS os;
	
	LocaleChoice localeChoice;
	LookAndFeelInfo lookAndFeelInfo;
	ResourceBundle UITexts;
	ResourceBundle messages;
	ResourceBundle resources;
	
	String windowsDistribution;
	String steamExecutable;
	
	String mainPlayerSteamId;
	
	GameChoice gameChoice; 
	GameLeftClickAction gameLeftClickAction;
	SteamLaunchMethod defaultSteamLaunchMethod;
	SteamGroupsDisplayMode steamGroupsDisplayMode;
	SteamFriendsDisplayMode steamFriendsDisplayMode;
	Boolean displayTooltips;
	
	Boolean debug;
	Boolean checkCommunityOnStartup;
	DumpMode dumpMode;
	Boolean useConsole;
	Boolean useDateTime;
	Boolean scrollLocked;
	Boolean xmlOverrideRegistry;
	
	SteamGamesList steamGamesList;
	
	ArrayList<GameLeftClickActionObserver> gameLeftClickActionObservers;
	
	public Parameters() {
		gameLeftClickActionObservers = new ArrayList<GameLeftClickActionObserver>();
	}
	
	/**
	 * @return the os
	 */
	public OS getOs() {
		return os;
	}

	/**
	 * @param os the os to set
	 */
	@XmlElement
	public void setOs(OS os) {
		this.os = os;
	}

	/**
	 * @return the localeChoice
	 */
	public LocaleChoice getLocaleChoice() {
		return localeChoice;
	}

	/**
	 * @param localeChoice the localeChoice to set
	 */
	@XmlElement
	public void setLocaleChoice(LocaleChoice localeChoice) {
		this.localeChoice = localeChoice;
	}

	/**
	 * @return the lookAndFeelInfo
	 */
	public LookAndFeelInfo getLookAndFeelInfo() {
		return lookAndFeelInfo;
	}

	/**
	 * @param lookAndFeelInfo the lookAndFeelInfo to set
	 */
	public void setLookAndFeelInfo(LookAndFeelInfo lookAndFeelInfo) {
		this.lookAndFeelInfo = lookAndFeelInfo;
	}

	/**
	 * @return the UITexts
	 */
	public ResourceBundle getUITexts() {
		return UITexts != null ? UITexts : ResourceBundle.getBundle("i18n/UITexts");
	}

	/**
	 * @param UITexts the UITexts to set
	 */
	@XmlElement
	public void setUITexts(ResourceBundle UITexts) {
		this.UITexts = UITexts;
	}

	/**
	 * @return the messages
	 */
	public ResourceBundle getMessages() {
		return messages != null ? messages : ResourceBundle.getBundle("i18n/messages");
	}

	/**
	 * @param messages the messages to set
	 */
	@XmlElement
	public void setMessages(ResourceBundle messages) {
		this.messages = messages;
	}

	/**
	 * @return the resources
	 */
	public ResourceBundle getResources() {
		return resources != null ? resources : ResourceBundle.getBundle("i18n/resources");
	}

	/**
	 * @param resources the resources to set
	 */
	@XmlElement
	public void setResources(ResourceBundle resources) {
		this.resources = resources;
	}

	/**
	 * @return the windowsDistribution
	 */
	public String getWindowsDistribution() {
		return windowsDistribution;
	}

	/**
	 * @param windowsDistribution the windowsDistribution to set
	 */
	@XmlElement
	public void setWindowsDistribution(String windowsDistribution) {
		this.windowsDistribution = windowsDistribution;
	}

	/**
	 * @return the steamExecutable
	 */
	public String getSteamExecutable() {
		return steamExecutable;
	}

	/**
	 * @param steamExecutable the steamExecutable to set
	 */
	@XmlElement
	public void setSteamExecutable(String steamExecutable) {
		this.steamExecutable = steamExecutable;
	}

	/**
	 * @return the mainPlayerSteamId
	 */
	public String getMainPlayerSteamId() {
		return mainPlayerSteamId;
	}

	/**
	 * @param mainPlayerSteamId the mainPlayerSteamId to set
	 */
	@XmlElement
	public void setMainPlayerSteamId(String mainPlayerSteamId) {
		this.mainPlayerSteamId = mainPlayerSteamId;
	}

	/**
	 * @return the gameChoice
	 */
	public GameChoice getGameChoice() {
		return gameChoice;
	}

	/**
	 * @param gameChoice the gameChoice to set
	 */
	@XmlElement
	public void setGameChoice(GameChoice gameChoice) {
		this.gameChoice = gameChoice;
	}

	/**
	 * @return the gameLeftClickAction
	 */
	public GameLeftClickAction getGameLeftClickAction() {
		return this.gameLeftClickAction;
	}

	/**
	 * @param gameLeftClickAction the gameLeftClickAction to set
	 */
	@XmlElement
	public void setGameLeftClickAction(GameLeftClickAction gameLeftClickAction) {
		this.gameLeftClickAction = gameLeftClickAction;
		updateGameLeftClickActionObservers();
	}

	/**
	 * @return the defaultSteamLaunchMethod
	 */
	public SteamLaunchMethod getDefaultSteamLaunchMethod() {
		return defaultSteamLaunchMethod;
	}

	/**
	 * @param defaultSteamLaunchMethod the defaultSteamLaunchMethod to set
	 */
	@XmlElement
	public void setDefaultSteamLaunchMethod(
			SteamLaunchMethod defaultSteamLaunchMethod) {
		this.defaultSteamLaunchMethod = defaultSteamLaunchMethod;
	}

	/**
	 * @return the steamGroupsDisplayMode
	 */
	public SteamGroupsDisplayMode getSteamGroupsDisplayMode() {
		return steamGroupsDisplayMode;
	}

	/**
	 * @param steamGroupsDisplayMode the steamGroupsDisplayMode to set
	 */
	@XmlElement
	public void setSteamGroupsDisplayMode(SteamGroupsDisplayMode steamGroupsDisplayMode) {
		this.steamGroupsDisplayMode = steamGroupsDisplayMode;
	}

	/**
	 * @return the steamFriendsDisplayMode
	 */
	public SteamFriendsDisplayMode getSteamFriendsDisplayMode() {
		return steamFriendsDisplayMode;
	}

	/**
	 * @param steamFriendsDisplayMode the steamFriendsDisplayMode to set
	 */
	@XmlElement
	public void setSteamFriendsDisplayMode(SteamFriendsDisplayMode steamFriendsDisplayMode) {
		this.steamFriendsDisplayMode = steamFriendsDisplayMode;
	}

	/**
	 * @return the displayTooltips
	 */
	public Boolean getDisplayTooltips() {
		return displayTooltips;
	}

	/**
	 * @param displayTooltips the displayTooltips to set
	 */
	@XmlElement
	public void setDisplayTooltips(Boolean displayTooltips) {
		this.displayTooltips = displayTooltips;
	}

	/**
	 * @return the debug
	 */
	public Boolean isDebug() {
		return debug;
	}

	/**
	 * @param debug the debug to set
	 */
	@XmlElement
	public void setDebug(Boolean debug) {
		this.debug = debug;
	}

	/**
	 * @return the checkCommunityOnStartup
	 */
	public Boolean isCheckCommunityOnStartup() {
		return checkCommunityOnStartup;
	}

	/**
	 * @param checkCommunityOnStartup the checkCommunityOnStartup to set
	 */
	public void setCheckCommunityOnStartup(Boolean checkCommunityOnStartup) {
		this.checkCommunityOnStartup = checkCommunityOnStartup;
	}

	/**
	 * @return the dumpMode
	 */
	public DumpMode getDumpMode() {
		return dumpMode;
	}

	/**
	 * @param dumpMode the dumpMode to set
	 */
	@XmlElement
	public void setDumpMode(DumpMode dumpMode) {
		this.dumpMode = dumpMode;
	}

	/**
	 * @return the useConsole
	 */
	public Boolean isUseConsole() {
		return useConsole;
	}

	/**
	 * @param useConsole the useConsole to set
	 */
	@XmlElement
	public void setUseConsole(Boolean useConsole) {
		this.useConsole = useConsole;
	}

	/**
	 * @return the useDateTime
	 */
	public Boolean isUseDateTime() {
		return useDateTime;
	}

	/**
	 * @param useDateTime the useDateTime to set
	 */
	@XmlElement
	public void setUseDateTime(Boolean useDateTime) {
		this.useDateTime = useDateTime;
	}

	/**
	 * @return the scrollLocked
	 */
	public Boolean isScrollLocked() {
		return scrollLocked;
	}

	/**
	 * @param scrollLocked the scrollLocked to set
	 */
	@XmlElement
	public void setScrollLocked(Boolean scrollLocked) {
		this.scrollLocked = scrollLocked;
	}

	/**
	 * @return the xmlOverrideRegistry
	 */
	public Boolean isXmlOverrideRegistry() {
		return xmlOverrideRegistry;
	}

	/**
	 * @param xmlOverrideRegistry the xmlOverrideRegistry to set
	 */
	@XmlElement
	public void setXmlOverrideRegistry(Boolean xmlOverrideRegistry) {
		this.xmlOverrideRegistry = xmlOverrideRegistry;
	}

	/**
	 * @return the steamGamesList
	 */
	public SteamGamesList getSteamGamesList() {
		return steamGamesList;
	}

	/**
	 * @param steamGamesList the steamGamesList to set
	 */
	@XmlElement
	public void setSteamGamesList(SteamGamesList steamGamesList) {
		this.steamGamesList = steamGamesList;
	}

	/**
	 * Create a message to summarize gamesList content
	 * 
	 * @return String message
	 */
	public String summarizeGamesList() {
		return String.format(messages.getString("summarizeGamesList"), getSteamGamesList().getSteamID(), getSteamGamesList().getSteamGames().size());
	}
	
	/**
	 * Prepare a List<String> to display parameters data later
	 * @return List<String>
	 */
	public List<String> toStringList() {
		
		List<String> result = new Vector<String>();
		
		result.add("OS : " + (os != null ? (os.getPrefix() != null ? os.getPrefix().name() : "os.Prefix null") : "null"));
		
		result.add("localeChoice : " + (localeChoice != null ? localeChoice + ", " + localeChoice.getTranslation() : "null"));
		result.add("lookAndFeelInfo : " + (lookAndFeelInfo != null ? lookAndFeelInfo.getName() : UIManager.getLookAndFeel().getName()));
		result.add("UITexts : " + (UITexts != null ? UITexts : "null"));
		result.add("messages : " + (messages != null ? messages : "null"));
		result.add("resources : " + (resources != null ? resources : "null"));
		
		result.add("windowsDistribution : " + (windowsDistribution != null ? windowsDistribution : "null"));
		result.add("steamExecutable : " + (steamExecutable != null ? steamExecutable : "null"));
		
		result.add("mainPlayerSteamId : " + (mainPlayerSteamId != null ? mainPlayerSteamId : "null"));
		
		result.add("gameChoice : " + (gameChoice != null ? gameChoice.name() : "null"));
		result.add("gameLeftClickAction : " + (gameLeftClickAction != null ? gameLeftClickAction.name() : "null"));
		result.add("defaultSteamLaunchMethod : " + (defaultSteamLaunchMethod != null ? defaultSteamLaunchMethod.name() : "null"));
		result.add("steamGroupsDisplayMode : " + (steamGroupsDisplayMode != null ? steamGroupsDisplayMode : "null"));
		result.add("steamFriendsDisplayMode : " + (steamFriendsDisplayMode != null ? steamFriendsDisplayMode : "null"));
		result.add("displayTooltips : " + (displayTooltips != null ? displayTooltips : "null"));
		
		result.add("debug : " + (debug != null ? debug : "null"));
		result.add("checkCommunityOnStartup : " + (checkCommunityOnStartup != null ? checkCommunityOnStartup : "null"));
		result.add("dumpMode : " + (dumpMode != null ? dumpMode : "null"));
		result.add("useConsole : " + (useConsole != null ? useConsole : "null"));
		result.add("useDateTime : " + (useDateTime != null ? useDateTime : "null"));
		result.add("scrollLocked : " + (scrollLocked != null ? scrollLocked : "null"));
		result.add("xmlOverrideRegistry : " + (xmlOverrideRegistry != null ? xmlOverrideRegistry : "null"));
		
		if (steamGamesList != null) {
			result.add("gamesList - steamID64 : " + steamGamesList.getSteamID64());
			result.add("gamesList - steamID : " + steamGamesList.getSteamID());
			for (SteamGame game : steamGamesList.getSteamGames()) {
				result.add("gameList - game :");
				for(String string : game.toStringList())
					result.add("gameList - game - " + string);
			}
		} else 
			result.add("gamesList : null");
		
		return result;
	}
	
	/**
	 * Copy only exposed member
	 */
	@Override
	public Object clone() {
		Parameters parameters = new Parameters();
		parameters.setOs(os);
		parameters.setLocaleChoice(localeChoice);
		parameters.setLookAndFeelInfo(lookAndFeelInfo);
		parameters.setUITexts(UITexts);
		parameters.setMessages(messages);
		parameters.setResources(resources);
		parameters.setWindowsDistribution(windowsDistribution);
		parameters.setSteamExecutable(steamExecutable);
		parameters.setMainPlayerSteamId(mainPlayerSteamId);
		parameters.setGameChoice(gameChoice);
		parameters.setGameLeftClickAction(gameLeftClickAction);
		parameters.setDefaultSteamLaunchMethod(defaultSteamLaunchMethod);
		parameters.setSteamGroupsDisplayMode(steamGroupsDisplayMode);
		parameters.setSteamFriendsDisplayMode(steamFriendsDisplayMode);
		parameters.setDisplayTooltips(displayTooltips);
		parameters.setDebug(debug);
		parameters.setCheckCommunityOnStartup(checkCommunityOnStartup);
		parameters.setDumpMode(dumpMode);
		parameters.setUseConsole(useConsole);
		parameters.setUseDateTime(useDateTime);
		parameters.setScrollLocked(scrollLocked);
		parameters.setXmlOverrideRegistry(xmlOverrideRegistry);
		return parameters;
	}
	
	/*/
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		for (String line : toStringList())
			string.append(line);
		return string.toString();
	}

	@Override
	public void addGameLeftClickActionObserver(GameLeftClickActionObserver gameLeftClickActionObserver) {
		gameLeftClickActionObservers.add(gameLeftClickActionObserver);
	}

	@Override
	public void updateGameLeftClickActionObservers() {
		for (GameLeftClickActionObserver observer : gameLeftClickActionObservers)
			observer.update();
	}

	@Override
	public void removeGameLeftClickActionObserver(GameLeftClickActionObserver gameLeftClickActionObserver) {
		gameLeftClickActionObservers.remove(gameLeftClickActionObserver);
	}

}
