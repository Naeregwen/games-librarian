/**
 * 
 */
package commons.api;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import commons.GamesLibrary;
import commons.GamesLibrary.LoadingSource;
import commons.enums.OnlineState;
import commons.enums.PrivacyState;
import commons.Strings;
import components.GamesLibrarian;

/**
 * @author Naeregwen
 *
 */
@XmlRootElement (name = "steamProfile")
@XmlType (propOrder = { 
	"steamID64", 
	"steamID", 
	
	"onlineState", 
	"stateMessage", 
	"privacyState", 
	"visibilityState", 
	
	"avatarIcon", 
	"avatarMedium", 
	"avatarFull", 
	
	"vacBanned", 
	"tradeBanState", 
	"isLimitedAccount", 
	
	"customURL", 
	"memberSince", 
	"steamRating", 
	"hoursPlayedLast2Weeks",
	
	"headline", 
	"location", 
	"realname", 
	"summary", 
	
	"loadingSource",
	"initialPosition",
	
	"mostplayedGames",
	"steamGames",
	"steamFriends",
	"steamGroups"
})
public class SteamProfile implements Comparable<SteamProfile> {

	LoadingSource loadingSource;
	Integer initialPosition;

	String steamID64;
	String steamID;
	
	String onlineState;
	String stateMessage;
	String privacyState;
	String visibilityState;
	
	String avatarIcon;
	String avatarMedium;
	String avatarFull;
	
	Integer vacBanned;
	String tradeBanState;
	Integer isLimitedAccount;
	
	String customURL;
	String memberSince;
	String steamRating;
	String hoursPlayedLast2Weeks;
	
	String headline;
	String location;
	String realname;
	String summary;
	
	Vector<SteamGame> mostplayedGames;
	Vector<SteamGame> steamGames;
	Vector<SteamProfile> steamFriends;
	Vector<SteamGroup> steamGroups;
	
	/**
	 * Initialize base members
	 */
	public SteamProfile() {
		mostplayedGames = new Vector<SteamGame>();
		steamGames = new Vector<SteamGame>();
		steamFriends = new Vector<SteamProfile>();
		steamGroups = new Vector<SteamGroup>();
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
	 * @return the loadingSource
	 */
	public LoadingSource getLoadingSource() {
		return loadingSource;
	}

	/**
	 * @param loadingSource the loadingSource to set
	 */
	@XmlElement
	public void setLoadingSource(LoadingSource loadingSource) {
		this.loadingSource = loadingSource;
	}

	/**
	 * @return the steamID64
	 */
	public String getSteamID64() {
		return steamID64;
	}

	/**
	 * @param steamID64 the steamID64 to set
	 */
	@XmlElement
	public void setSteamID64(String steamID64) {
		this.steamID64 = steamID64;
	}

	/**
	 * @return the steamID
	 */
	public String getSteamID() {
		return steamID;
	}

	/**
	 * @param steamID the steamID to set
	 */
	@XmlElement
	public void setSteamID(String steamID) {
		this.steamID = steamID;
	}
	
	
	/**
	 * Determine ID between SteamID and SteamID64 in this order
	 * @return ID if found, null otherwise
	 */
	public String getId() {
		return steamID != null && steamID.length() > 0 ? steamID : steamID64 != null && steamID64.length() > 0 ? steamID64 : null;
	}
	
	/**
	 * Determine steam ID type
	 * Then set proper identifier
	 * @param id
	 */
	@XmlTransient
	public void setId(String id) {
		if (Steam.isAPICallBySteamId64(id))
			this.steamID64 = id;
		else
			this.steamID = id;
	}

	/**
	 * Determine ID between SteamID64 and SteamID in this order
	 * @return ID if found, null otherwise
	 */
	public String getId64() {
		return steamID64 != null && steamID64.length() > 0 ? steamID64 : steamID != null && steamID.length() > 0 ? steamID : null;
	}
	
	/**
	 * @return the onlineState
	 */
	public String getOnlineState() {
		return onlineState;
	}

	/**
	 * @param onlineState the onlineState to set
	 */
	@XmlElement
	public void setOnlineState(String onlineState) {
		this.onlineState = onlineState;
	}

	/**
	 * @return the stateMessage
	 */
	public String getStateMessage() {
		return stateMessage;
	}

	/**
	 * @param stateMessage the stateMessage to set
	 */
	@XmlElement
	public void setStateMessage(String stateMessage) {
		this.stateMessage = stateMessage;
	}

	/**
	 * @return the privacyState
	 */
	public String getPrivacyState() {
		return privacyState;
	}

	/**
	 * @param privacyState the privacyState to set
	 */
	@XmlElement
	public void setPrivacyState(String privacyState) {
		this.privacyState = privacyState;
	}

	/**
	 * @return the visibilityState
	 */
	public String getVisibilityState() {
		return visibilityState;
	}

	/**
	 * @param visibilityState the visibilityState to set
	 */
	@XmlElement
	public void setVisibilityState(String visibilityState) {
		this.visibilityState = visibilityState;
	}

	/**
	 * @return the avatarIcon
	 */
	public String getAvatarIcon() {
		return avatarIcon;
	}

	/**
	 * @param avatarIcon the avatarIcon to set
	 */
	@XmlElement
	public void setAvatarIcon(String avatarIcon) {
		this.avatarIcon = avatarIcon;
	}

	/**
	 * @return the avatarMedium
	 */
	public String getAvatarMedium() {
		return avatarMedium;
	}

	/**
	 * @param avatarMedium the avatarMedium to set
	 */
	@XmlElement
	public void setAvatarMedium(String avatarMedium) {
		this.avatarMedium = avatarMedium;
	}

	/**
	 * @return the avatarFull
	 */
	public String getAvatarFull() {
		return avatarFull;
	}

	/**
	 * @param avatarFull the avatarFull to set
	 */
	@XmlElement
	public void setAvatarFull(String avatarFull) {
		this.avatarFull = avatarFull;
	}

	/**
	 * @return the vacBanned
	 */
	public Integer getVacBanned() {
		return vacBanned;
	}

	/**
	 * @param vacBanned the vacBanned to set
	 */
	@XmlElement
	public void setVacBanned(Integer vacBanned) {
		this.vacBanned = vacBanned;
	}

	/**
	 * @return the tradeBanState
	 */
	public String getTradeBanState() {
		return tradeBanState;
	}

	/**
	 * @param tradeBanState the tradeBanState to set
	 */
	@XmlElement
	public void setTradeBanState(String tradeBanState) {
		this.tradeBanState = tradeBanState;
	}

	/**
	 * @return the isLimitedAccount
	 */
	public Integer getIsLimitedAccount() {
		return isLimitedAccount;
	}

	/**
	 * @param isLimitedAccount the isLimitedAccount to set
	 */
	@XmlElement
	public void setIsLimitedAccount(Integer isLimitedAccount) {
		this.isLimitedAccount = isLimitedAccount;
	}

	/**
	 * @return the customURL
	 */
	public String getCustomURL() {
		return customURL;
	}

	/**
	 * @param customURL the customURL to set
	 */
	@XmlElement
	public void setCustomURL(String customURL) {
		this.customURL = customURL;
	}

	/**
	 * @return the memberSince
	 */
	public String getMemberSince() {
		return memberSince;
	}

	/**
	 * @param memberSince the memberSince to set
	 */
	@XmlElement
	public void setMemberSince(String memberSince) {
		this.memberSince = memberSince;
	}

	/**
	 * @return the steamRating
	 */
	public String getSteamRating() {
		return steamRating;
	}

	/**
	 * @param steamRating the steamRating to set
	 */
	@XmlElement
	public void setSteamRating(String steamRating) {
		this.steamRating = steamRating;
	}

	/**
	 * @return the hoursPlayedLast2Weeks
	 */
	public String getHoursPlayedLast2Weeks() {
		return hoursPlayedLast2Weeks;
	}

	/**
	 * @param hoursPlayedLast2Weeks the hoursPlayedLast2Weeks to set
	 */
	@XmlElement
	public void setHoursPlayedLast2Weeks(String hoursPlayedLast2Weeks) {
		this.hoursPlayedLast2Weeks = hoursPlayedLast2Weeks;
	}

	/**
	 * @return the headline
	 */
	public String getHeadline() {
		return headline;
	}

	/**
	 * @param headline the headline to set
	 */
	@XmlElement
	public void setHeadline(String headline) {
		this.headline = headline;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	@XmlElement
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the realname
	 */
	public String getRealname() {
		return realname;
	}

	/**
	 * @param realname the realname to set
	 */
	@XmlElement
	public void setRealname(String realname) {
		this.realname = realname;
	}

	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary the summary to set
	 */
	@XmlElement
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @return the mostplayedGames
	 */
	public Vector<SteamGame> getMostplayedGames() {
		return mostplayedGames;
	}

	/**
	 * @param mostplayedGames the mostplayedGames to set
	 */
	@XmlElements(value = { @XmlElement(name = "mostplayedGame", type = SteamGame.class) })
	@XmlElementWrapper(name = "mostplayedGames")
	public void setMostplayedGames(Vector<SteamGame> mostplayedGames) {
		this.mostplayedGames = mostplayedGames;
	}

	/**
	 * @return the steamGames
	 */
	public Vector<SteamGame> getSteamGames() {
		return steamGames;
	}

	/**
	 * @param steamGames the steamGames to set
	 */
	@XmlElements(value = { @XmlElement(name = "steamGame", type = SteamGame.class) })
	@XmlElementWrapper(name = "steamGames")
	public void setSteamGames(Vector<SteamGame> steamGames) {
		this.steamGames = steamGames;
	}

	/**
	 * @return the steamFriends
	 */
	public Vector<SteamProfile> getSteamFriends() {
		return steamFriends;
	}

	/**
	 * @param steamFriends the steamFriends to set
	 */
	@XmlElements(value = { @XmlElement(name = "steamFriend", type = SteamProfile.class) })
	@XmlElementWrapper(name = "steamFriends")
	public void setSteamFriends(Vector<SteamProfile> steamFriends) {
		this.steamFriends = steamFriends;
	}

	/**
	 * @return the steamGroups
	 */
	public Vector<SteamGroup> getSteamGroups() {
		return steamGroups;
	}

	/**
	 * @param steamGroups the steamGroups to set
	 */
	@XmlElements(value = { @XmlElement(name = "steamGroup", type = SteamGroup.class) })
	@XmlElementWrapper(name = "steamGroups")
	public void setSteamGroups(Vector<SteamGroup> steamGroups) {
		this.steamGroups = steamGroups;
	}
	
	//
	// Utilities
	//
	
	/**
	 * @param steamFriend the steamFriend to add
	 */
	public void addFriend(SteamProfile steamFriend) {
		if (steamFriend.getSteamID64() == null)
			return;
		if (steamFriends == null) 
			steamFriends = new Vector<SteamProfile>();
		for (SteamProfile mainFriend : steamFriends)
			if (mainFriend.getSteamID64().equalsIgnoreCase(steamFriend.getSteamID64())) {
				mainFriend.copy(steamFriend);
				return;
			}
		steamFriends.add(steamFriend);
	}
	
	public boolean hasGame(SteamGame steamGame) {
		for (SteamGame game : steamGames)
			if (steamGame.getAppID().equals(game.appID))
				return true;
		return false;
	}
	
	public SteamGame getGame(SteamGame steamGame) {
		String appID = steamGame.digAppID(); // MostPlayedGame does not have an appId
		for (SteamGame game : steamGames)
			if (game.appID.equals(appID))
				return game;
		return null;
	}
	
	/**
	 * Determine PrivacyState
	 * @return PrivacyState if found, replacement otherwise
	 */
	public String getPrivacyState(String replacement) {
		return privacyState != null && privacyState.length() > 0 ? privacyState : replacement;
	}
	
	/**
	 * Get privacyStateIcon URL from PrivacyState
	 * @return privacyStateIcon URL
	 */
	public URL getPrivacyStateIconURL() {
		URL statusIconURL = GamesLibrarian.class.getResource(PrivacyState.UNKNOWN.getIconPath());
		if (privacyState != null) {
			String clearedPrivacyState = privacyState.trim();
			for (PrivacyState privacyStateValue : PrivacyState.values()) {
				if (clearedPrivacyState.equalsIgnoreCase(privacyStateValue.getSteamLabel())) {
					statusIconURL = GamesLibrarian.class.getResource(privacyStateValue.getIconPath());
					break;
				}
			}
		}
		return statusIconURL;
	}
	
	/**
	 * Get onlineStateIcon URL from OnlineState
	 * @return onlineStateIcon URL
	 */
	public URL getOnlineStateIconURL() {
		URL statusIconURL = GamesLibrarian.class.getResource(OnlineState.UNKNOWN.getIconPath());
		if (getOnlineState() != null) {
			String clearedOnlineState = getOnlineState().trim();
			for (OnlineState onlineStateValue : OnlineState.values()) {
				if (clearedOnlineState.equalsIgnoreCase(onlineStateValue.getSteamLabel())) {
					statusIconURL = GamesLibrarian.class.getResource(onlineStateValue.getIconPath());
					break;
				}
			}
		}
		return statusIconURL;
	}
	
	/**
	 * Copy needed properties after XML parsing
	 * @param steamProfile
	 */
	public void copy(SteamProfile steamProfile) {
		steamID64 = steamProfile.getSteamID64();
		steamID = steamProfile.getSteamID();
		onlineState = steamProfile.getOnlineState();
		stateMessage = steamProfile.getStateMessage();
		privacyState = steamProfile.getPrivacyState();
		visibilityState = steamProfile.getVisibilityState();
		avatarIcon = steamProfile.getAvatarIcon();
		avatarMedium = steamProfile.getAvatarMedium();
		avatarFull = steamProfile.getAvatarFull();
		vacBanned = steamProfile.getVacBanned();
		tradeBanState = steamProfile.getTradeBanState();
		isLimitedAccount = steamProfile.getIsLimitedAccount();
		customURL = steamProfile.getCustomURL();
		memberSince = steamProfile.getMemberSince();
		steamRating = steamProfile.getSteamRating();
		hoursPlayedLast2Weeks = steamProfile.getHoursPlayedLast2Weeks();
		headline = steamProfile.getHeadline();
		location = steamProfile.getLocation();
		realname = steamProfile.getRealname();
		summary = steamProfile.getSummary();
		loadingSource = steamProfile.getLoadingSource();
		initialPosition = steamProfile.getInitialPosition();
		mostplayedGames = steamProfile.getMostplayedGames();
		steamGames = steamProfile.getSteamGames();
		steamFriends = steamProfile.getSteamFriends();
		steamGroups = steamProfile.getSteamGroups();
	}
	
	/**
	 * Build tool tip content
	 * @return tool tip content
	 */
	public String getTooltipText() {
		
		String privacyStateIcon = "<img src='" + getPrivacyStateIconURL() + "'>";
		String onlineStateIcon = "<img src='" + getOnlineStateIconURL() + "'>";
		
		StringBuffer tooltipText = new StringBuffer("<html>");
		tooltipText.append("<style type='text/css'>");
		tooltipText.append(".minimalTable { border: 0; margin: 0; padding: 0; }");
		tooltipText.append(".statusIconTitle { margin-top: 5px; padding: 0 }");
		tooltipText.append(".statusIconContainer { margin: 0; padding: 0}");
		tooltipText.append(".statusIcon { margin-right: 3px }");
		tooltipText.append(".h1 { text-align: left }");
		tooltipText.append(".fullWitdh { width: 100% }");
		tooltipText.append(".lineHeaderRight { text-align: right; font-weight: bold }");
		tooltipText.append(".lineHeaderLeft { text-align: left; font-weight: bold }");
		tooltipText.append(".data { text-align: left; border: 1px solid black }");
		tooltipText.append(".gameLogo { margin-top: 3px }");
		tooltipText.append("</style>");
		tooltipText.append("<table border='0' class='fullWitdh'>");
		
		tooltipText.append("<tr>");
		
		// avatarFullIcon
		tooltipText.append("<td valign='top' rowspan='4'>");
		if (getAvatarFull() != null)
			tooltipText.append("<img width='" + Steam.avatarFullIconWidth + "' height='" + Steam.avatarFullIconHeight + "' src='"+getAvatarFull()+"'/>");
		else
			tooltipText.append("<img width='" + Steam.avatarFullIconWidth + "' height='" + Steam.avatarFullIconHeight + "' src='"+GamesLibrary.noAvatarFull+"'/>");
		tooltipText.append("</td>");
		
		// status icons and (steam)Id
		tooltipText.append("<td valign='middle' colspan='4'>");
		tooltipText.append("<table class='minimalTable'>");
		tooltipText.append("<tr>");
		tooltipText.append("<td class='statusIconTitle'>");
		tooltipText.append(privacyStateIcon);
		tooltipText.append("</td>");
		tooltipText.append("<td class='statusIconTitle'>");
		tooltipText.append(onlineStateIcon);
		tooltipText.append("</td>");
		tooltipText.append("<td>");
		tooltipText.append("<h1>");
		tooltipText.append(getId());
		tooltipText.append("</h1>");
		tooltipText.append("</td>");
		tooltipText.append("</tr>");
		tooltipText.append("</table>");
		tooltipText.append("</td>");
		
		tooltipText.append("</tr>");
		
		// accountRealname
		tooltipText.append("<tr>");
		
		tooltipText.append("<td class='lineHeaderRight' valign='middle'>");
		tooltipText.append(Strings.htmlEOL(ResourceBundle.getBundle("i18n/UITexts").getString("accountRealname")));
		tooltipText.append("</td>");
		
		tooltipText.append("<td class='data' colspan='3'>");
		if (getRealname() != null) tooltipText.append(getRealname());
		tooltipText.append("</td>");
		
		tooltipText.append("</tr>");
		
		// accountLocation
		tooltipText.append("<tr>");
		
		tooltipText.append("<td class='lineHeaderRight' valign='middle'>");
		tooltipText.append(Strings.htmlEOL(ResourceBundle.getBundle("i18n/UITexts").getString("accountLocation")));
		tooltipText.append("</td>");
		
		tooltipText.append("<td class='data' colspan='3'>");
		if (getLocation() != null) tooltipText.append(getLocation());
		tooltipText.append("</td>");
		
		tooltipText.append("</tr>");
		
		// accountCustomURL
		tooltipText.append("<tr>");
		
		tooltipText.append("<td class='lineHeaderRight' valign='middle'>");
		tooltipText.append(Strings.htmlEOL(ResourceBundle.getBundle("i18n/UITexts").getString("accountCustomURL")));
		tooltipText.append("</td>");
		
		tooltipText.append("<td class='data' colspan='3'>");
		if (getCustomURL() != null) tooltipText.append(getCustomURL());
		tooltipText.append("</td>");
		
		tooltipText.append("</tr>");
		
		// accountSummary - Label
		tooltipText.append("<tr>");
		
		tooltipText.append("<td class='lineHeaderLeft' valign='top' colspan='3'>");
		tooltipText.append(Strings.htmlEOL(ResourceBundle.getBundle("i18n/UITexts").getString("accountSummary")));
		tooltipText.append("</td>");
		
		// accountHeadline - Label
		tooltipText.append("<td class='lineHeaderLeft' valign='top' colspan='2'>");
		tooltipText.append(Strings.htmlEOL(ResourceBundle.getBundle("i18n/UITexts").getString("accountHeadline")));
		tooltipText.append("</td>");
		
		tooltipText.append("</tr>");
		
		tooltipText.append("<tr>");
		
		//
		// Use those two block to divide approximatively the frame in two parts
		//
		
		// accountSummary - Data
		tooltipText.append("<td class='data' colspan='3' width='50%'>");
		if (getSummary() != null) tooltipText.append(getSummary());
		tooltipText.append("</td>");
		
		// accountHeadline - Data
		tooltipText.append("<td class='data' colspan='2' width='50%'>");
		if (getHeadline() != null) tooltipText.append(getHeadline());
		tooltipText.append("</td>");
		
		tooltipText.append("</tr>");
		
		tooltipText.append("<tr>");
		
		// accountMemberSince
		tooltipText.append("<td class='lineHeaderRight' valign='top' colspan='2'>");
		tooltipText.append(Strings.htmlEOL(ResourceBundle.getBundle("i18n/UITexts").getString("accountMemberSince")));
		tooltipText.append("</td>");
		
		tooltipText.append("<td class='data'>");
		if (getMemberSince() != null) tooltipText.append(getMemberSince());
		tooltipText.append("</td>");
		
		// accountOnlineState
		tooltipText.append("<td class='lineHeaderRight' valign='top'>");
		tooltipText.append(Strings.htmlEOL(ResourceBundle.getBundle("i18n/UITexts").getString("accountOnlineState")));
		tooltipText.append("</td>");
		
		// Cannot convert those properties to CSS class. It's seems to be supported only inline
		// Conflicts/erase 'data' CSS class border properties
		tooltipText.append("<td class='data' style='margin: 0; padding: 0'>");
		tooltipText.append("<table class='minimalTable' cellpadding='0' cellspacing='0'>");
		tooltipText.append("<tr>");
		tooltipText.append("<td class='statusIcon'>");
		tooltipText.append(onlineStateIcon);
		tooltipText.append("</td>");
		tooltipText.append("<td>");
		if (getOnlineState() != null) tooltipText.append(getOnlineState());
		tooltipText.append("</td>");
		tooltipText.append("</tr>");
		tooltipText.append("</table>");
		tooltipText.append("</td>");
		
		tooltipText.append("</tr>");
		
		tooltipText.append("<tr>");
		
		// accoutSteamRating
		tooltipText.append("<td class='lineHeaderRight' valign='top' colspan='2'>");
		tooltipText.append(Strings.htmlEOL(ResourceBundle.getBundle("i18n/UITexts").getString("accoutSteamRating")));
		tooltipText.append("</td>");
		
		tooltipText.append("<td class='data'>");
		if (getSteamRating() != null) tooltipText.append(getSteamRating());
		tooltipText.append("</td>");
		
		// accountStateMessage
		tooltipText.append("<td class='lineHeaderRight' valign='top'>");
		tooltipText.append(Strings.htmlEOL(ResourceBundle.getBundle("i18n/UITexts").getString("accountStateMessage")));
		tooltipText.append("</td>");
		
		tooltipText.append("<td class='data'>");
		if (getStateMessage() != null) tooltipText.append(getStateMessage());
		tooltipText.append("</td>");
		
		tooltipText.append("</tr>");
		
		tooltipText.append("<tr>");
		
		// accountHoursPlayedLastTwoWeeks
		tooltipText.append("<td class='lineHeaderRight' valign='top' colspan='2'>");
		tooltipText.append(Strings.htmlEOL(ResourceBundle.getBundle("i18n/UITexts").getString("accountHoursPlayedLastTwoWeeks")));
		tooltipText.append("</td>");
		
		tooltipText.append("<td class='data'>");
		if (getHoursPlayedLast2Weeks() != null) tooltipText.append(getHoursPlayedLast2Weeks());
		tooltipText.append("</td>");
		
		// accountPrivacystate
		tooltipText.append("<td class='lineHeaderRight' valign='top'>");
		tooltipText.append(Strings.htmlEOL(ResourceBundle.getBundle("i18n/UITexts").getString("accountPrivacystate")));
		tooltipText.append("</td>");
		
		// Cannot convert those properties to CSS class. It's seems to be supported only inline
		// Conflicts/erase 'data' CSS class border properties
		tooltipText.append("<td class='data' style='margin: 0; padding: 0'>");
		tooltipText.append("<table class='minimalTable' cellpadding='0' cellspacing='0'>");
		tooltipText.append("<tr>");
		tooltipText.append("<td class='statusIcon'>");
		tooltipText.append(privacyStateIcon);
		tooltipText.append("</td>");
		tooltipText.append("<td>");
		if (getPrivacyState() != null) tooltipText.append(getPrivacyState());
		tooltipText.append("</td>");
		tooltipText.append("</tr>");
		tooltipText.append("</table>");
		tooltipText.append("</td>");
		
		tooltipText.append("</tr>");
		
		tooltipText.append("<tr>");
		
		// accountSteamID64
		tooltipText.append("<td class='lineHeaderRight' valign='top' colspan='2'>");
		tooltipText.append(Strings.htmlEOL(ResourceBundle.getBundle("i18n/UITexts").getString("accountSteamID64")));
		tooltipText.append("</td>");
		
		tooltipText.append("<td class='data'>");
		if (getSteamID64() != null) tooltipText.append(getSteamID64());
		tooltipText.append("</td>");
		
		// accountVisibilityState
		tooltipText.append("<td class='lineHeaderRight' valign='top'>");
		tooltipText.append(Strings.htmlEOL(ResourceBundle.getBundle("i18n/UITexts").getString("accountVisibilityState")));
		tooltipText.append("</td>");
		
		tooltipText.append("<td class='data'>");
		if (getVisibilityState() != null) tooltipText.append(getVisibilityState());
		tooltipText.append("</td>");
		
		tooltipText.append("</tr>");
		
		tooltipText.append("<tr>");
		
		// accountGamerSteamID
		tooltipText.append("<td class='lineHeaderRight' valign='top' colspan='2'>");
		tooltipText.append(Strings.htmlEOL(ResourceBundle.getBundle("i18n/UITexts").getString("accountGamerSteamID")));
		tooltipText.append("</td>");
		
		tooltipText.append("<td class='data'>");
		if (getSteamID() != null) tooltipText.append(getSteamID());
		tooltipText.append("</td>");
		
		// accountLimitedAccount
		tooltipText.append("<td class='lineHeaderRight' valign='top'>");
		tooltipText.append(Strings.htmlEOL(ResourceBundle.getBundle("i18n/UITexts").getString("accountLimitedAccount")));
		tooltipText.append("</td>");
		
		tooltipText.append("<td class='data'>");
		if (getIsLimitedAccount() != null) tooltipText.append(getIsLimitedAccount());
		tooltipText.append("</td>");
		
		tooltipText.append("</tr>");
		
		tooltipText.append("<tr>");
		
		// accountVACBanned
		tooltipText.append("<td class='lineHeaderRight' valign='top' colspan='2'>");
		tooltipText.append(Strings.htmlEOL(ResourceBundle.getBundle("i18n/UITexts").getString("accountVACBanned")));
		tooltipText.append("</td>");
		
		tooltipText.append("<td class='data'>");
		if (getVacBanned() != null) tooltipText.append(getVacBanned());
		tooltipText.append("</td>");
		
		// accountTradeBanState
		tooltipText.append("<td class='lineHeaderRight' valign='top'>");
		tooltipText.append(Strings.htmlEOL(ResourceBundle.getBundle("i18n/UITexts").getString("accountTradeBanState")));
		tooltipText.append("</td>");
		
		tooltipText.append("<td class='data'>");
		if (getTradeBanState() != null) tooltipText.append(getTradeBanState());
		tooltipText.append("</td>");
		
		tooltipText.append("</tr>");
		
		tooltipText.append("<tr>");
		
		// groups count
		tooltipText.append("<td class='lineHeaderRight' valign='top' colspan='2'>");
		tooltipText.append(Strings.htmlEOL(ResourceBundle.getBundle("i18n/UITexts").getString("profileGroupsTooltipsLabel")));
		tooltipText.append("</td>");
		
		tooltipText.append("<td class='data'>");
		if (getIsLimitedAccount() != null) tooltipText.append(getSteamGroups().size());
		tooltipText.append("</td>");
		
		// friends count
		tooltipText.append("<td class='lineHeaderRight' valign='top'>");
		tooltipText.append(Strings.htmlEOL(ResourceBundle.getBundle("i18n/UITexts").getString("profileFriendsTooltipsLabel")));
		tooltipText.append("</td>");
		
		tooltipText.append("<td class='data'>");
		if (getTradeBanState() != null) tooltipText.append(getSteamFriends().size());
		tooltipText.append("</td>");
		
		tooltipText.append("</tr>");
		
		// mostPlayedGames
		if (getMostplayedGames() != null && getMostplayedGames().size() > 0) {
			tooltipText.append("<tr>");
			tooltipText.append("<td colspan='5'>");
			tooltipText.append("<table class='minimalTable' width='100%' cellpadding='0' cellspacing='0'>");
			tooltipText.append("<tr>");
			int imagesCount = 0;
			Iterator<SteamGame> mostPlayedGames = getMostplayedGames().iterator();
			while (mostPlayedGames.hasNext()) {
				SteamGame steamGame = mostPlayedGames.next();
				if (steamGame.getLogo() != null) {
					tooltipText.append("<td class='gameLogo' align='center'><img width='" + Steam.steamGameIconWidth + "' height='" + Steam.steamGameIconHeight + "' src='"+steamGame.getLogo()+"'/></td>");
					if (imagesCount++ == 2) {
						tooltipText.append("</tr>");
						if (mostPlayedGames.hasNext())
							tooltipText.append("<tr>");
						imagesCount = 0;
					}
				}
			}
			if (imagesCount != 0)
				tooltipText.append("</tr>");
			tooltipText.append("</table>");
			tooltipText.append("</td>");
			tooltipText.append("</tr>");
		}
		
		tooltipText.append("</table>");
		
		tooltipText.append("</html>");
		
		return tooltipText.toString();
	}
	/**
	 * Prepare a List<String> to display profile data later
	 * 
	 * @return List<String>
	 */
	public List<String> toStringList() {
		
		List<String> result = new Vector<String>();
		
		result.add("steamID64 : " + (steamID64 != null ? steamID64 : "null"));
		result.add("steamID : " + (steamID != null ? steamID : "null"));
		result.add("onlineState : " + (onlineState != null ? onlineState : "null"));
		result.add("stateMessage : " + (stateMessage != null ? stateMessage : "null"));
		result.add("privacyState : " + (privacyState != null ? privacyState : "null"));
		result.add("visibilityState : " + (visibilityState != null ? visibilityState : "null"));
		result.add("avatarIcon : " + (avatarIcon != null ? avatarIcon : "null"));
		result.add("avatarMedium : " + (avatarMedium != null ? avatarMedium : "null"));
		result.add("avatarFull : " + (avatarFull != null ? avatarFull : "null"));
		result.add("vacBanned : " + (vacBanned != null ? vacBanned : "null"));
		result.add("tradeBanState : " + (tradeBanState != null ? tradeBanState : "null"));
		result.add("isLimitedAccount : " + (isLimitedAccount != null ? isLimitedAccount : "null"));
		result.add("customURL : " + (customURL != null ? customURL : "null"));
		result.add("memberSince : " + (memberSince != null ? memberSince : "null"));
		result.add("steamRating : " + (steamRating != null ? steamRating : "null"));
		result.add("hoursPlayedLast2Weeks : " + (hoursPlayedLast2Weeks != null ? hoursPlayedLast2Weeks : "null"));
		result.add("headline : " + (headline != null ? headline : "null"));
		result.add("location : " + (location != null ? location : "null"));
		result.add("realname : " + (realname != null ? realname : "null"));
		result.add("summary : " + (summary != null ? summary : "null"));
		result.add("loadingSource : " + (loadingSource != null ? loadingSource : "null"));
		result.add("initialPosition : " + (initialPosition != null ? initialPosition : "null"));
		
		result.add("mostPlayedGames :");
		for (SteamGame game : mostplayedGames) {
			result.add("mostPlayedGames - game :");
			for(String string : game.toMostPlayedGameStringList())
				result.add("mostPlayedGames - game - " + string);
		}
		
		result.add("games :");
		for (SteamGame game : steamGames) {
			result.add("games - game :");
			for(String string : game.toStringList())
				result.add("games - game - " + string);
		}
		
		result.add("friends :");
		for (SteamProfile friend : steamFriends) {
			result.add("friends - friend :");
			for(String string : friend.toFriendStringList())
				result.add("friends - friend - " + string);
		}
		
		result.add("groups :");
		for (SteamGroup group : steamGroups) {
			result.add("groups - group :");
			for(String string : group.toStringList())
				result.add("groups - group - " + string);
		}
		
		return result;
	}

		
	/**
	 * Prepare a List<String> to display friend profile data later
	 * 
	 * @return List<String>
	 */
	public List<String> toFriendStringList() {
		List<String> result = new Vector<String>();		
		result.add("steamID64 : " + (steamID64 != null ? steamID64 : "null"));
		result.add("steamID : " + (steamID != null ? steamID : "null"));
		return result;
	}

	/**
	 * Parse html Id as bodyFragment
	 * And return text result
	 * @param html
	 * @return parsed body text result
	 */
	public static String htmlIdToText(String htmlId) {
		return htmlId != null ? Jsoup.parseBodyFragment(Jsoup.clean(htmlId, new Whitelist())).body().text() : "";
	}
	
	/**
	 * Compare instances of this class
	 */
	@Override
	public int compareTo(SteamProfile steamProfile) {
		if (getSteamID() != null) {
			if (steamProfile.getSteamID() == null) {
				if (steamProfile.getSteamID64() != null)
					return htmlIdToText(getSteamID()).toLowerCase().compareTo(steamProfile.getSteamID64());
				else
					return 1;
			} else
				return htmlIdToText(getSteamID()).toLowerCase().compareTo(htmlIdToText(steamProfile.getSteamID()).toLowerCase());
		} else {
			if (getSteamID64() != null) {
				if (steamProfile.getSteamID() == null)
					return 1;
				else
					return getSteamID64().compareTo(htmlIdToText(steamProfile.getSteamID()));
			} else {
				if (htmlIdToText(steamProfile.getSteamID()).toLowerCase() != null || steamProfile.getSteamID64() != null)
					return -1;
				else
					return 0;
			}
		}
	}	
}
