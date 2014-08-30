package commons.api;

import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import commons.Strings;

/**
 * API for Steam Community Data
 * 
 * https://partner.steamgames.com/documentation/community_data
 * https://developer.valvesoftware.com/wiki/Steam_Web_API
 * https://developer.valvesoftware.com/wiki/Steam_browser_protocol
 * 
 * <ul>
 * 		<li> XML Gamer Profile
 * 			<ul>
 * 				<li>http://steamcommunity.com/id/gamerSteamId?xml=1</li>
 * 				<li>http://steamcommunity.com/profiles/gamerSteamId64?xml=1</li>
 * 			</ul>
 * 		</li>
 * 		<li> XML Gamer GamesList
 * 			<ul>
 * 				<li>http://steamcommunity.com/id/gamerSteamId/games?xml=1</li>
 * 				<li>http://steamcommunity.com/profiles/gamerSteamId64/games?xml=1</li>
 * 	http://steamcommunity.com/profile/76561198012547353/games?xml=1
 * 			</ul>
 * 		</li>
 * 		<li> XML Gamer FriendsList
 * 			<ul>
 * 				<li>http://steamcommunity.com/id/gamerSteamId/friends?xml=1</li>
 * 				<li>http://steamcommunity.com/profiles/gamerSteamId64/friends?xml=1</li>
 * 			</ul>
 * 		</li>
 * 		<li><hr/></li>
 * 		<li> XML Gamer GroupsList : XML unavailable
 * 			<ul>
 * 				<li>http://steamcommunity.com/id/gamerSteamId/groups?xml=1</li>
 * 				<li>http://steamcommunity.com/profiles/gamerSteamId64/groups?xml=1</li>
 * 			</ul>
 * 		</li>
 * 		<li><hr/></li>
 * 		<li> XML Gamer/Game Stats
 * 			<ul>
 * 				<li>http://steamcommunity.com/id/gamerSteamId/stats/GameStatsName/?xml=1</li>
 * 				<li>http://steamcommunity.com/profiles/gamerSteamId64/GameStatsName/?xml=1</li>
 * 			</ul>
 * 		</li>
 * </ul>
 * 
 * @author Naeregwen
 */
public class Steam {

	// Base URL for Steam Community
	public static final String steamCommunityURL = "http://www.steamcommunity.com";
	public static int responseStatusCode = 404;
	public static StatusLine responseStatusLine = null;
	public static String responseErrorCause = "";
	
	// Base dimensions for avatarFull icons
	public static final int avatarFullIconHeight = 184;
	public static final int avatarFullIconWidth = 184;
	
	// Base dimensions for game icons
	public static final int steamGameIconWidth = 184;
	public static final int steamGameIconHeight = 69;
	
	// Base dimensions for group icons
	public static final int steamGroupIconWidth = 32;
	public static final int steamGroupIconHeight = 32;
	
	// Base dimensions for friend icons
	public static final int steamFriendIconWidth = 32;
	public static final int steamFriendIconHeight = 32;
	
	// Base dimensions for achievement icons
	public static final int steamAchievementIconWidth = 64;
	public static final int steamAchievementIconHeight = 64;
	
	// Base dimensions for application icons
	public static final int applicationIconWidth = 16;
	public static final int applicationIconHeight = 16;
	
	// Base dimensions for dialog icons
	public static final int dialogIconWidth = 32;
	public static final int dialogIconHeight = 32;

	// Useful pattern
	private static final Pattern steamGameLinkPattern = Pattern.compile("https?://steamcommunity.com/app/(\\d+)/?");
	private static final Pattern steamGameStatsLinkPattern = Pattern.compile("https?://steamcommunity.com/(id|profiles)/([^/]+)/stats/([^/]+)/?");
	private static final Pattern steamGameImageLinkPattern = Pattern.compile("https?://media.steampowered.com/steamcommunity/public/images/apps/([^/]+)/[^\\.]+(\\.([^\\.]+))?");
	
	/**
	 * Steam API Call Method
	 * 
	 * Profile : http://steamcommunity.com/profile/gamerSteamId64?xml=1
	 * Id : http://steamcommunity.com/id/gamerSteamId?xml=1
	 */
	public static enum SteamAPICallMethod { Profile, Id };
	
	/**
	 * Determine if API call by SteamID64 is permitted with gamerSteamId
	 * @param gamerSteamId
	 * @return
	 */
	public static boolean isAPICallBySteamId64(String gamerSteamId) {
		return Strings.fullNumericPattern.matcher(gamerSteamId).matches();
	}
	
	/**
	 * Determine if API call by SteamID is permitted with gamerSteamId
	 * @param gamerSteamId
	 * @return
	 */
	public static boolean isAPICallBySteamId(String gamerSteamId) {
		return !isAPICallBySteamId64(gamerSteamId);
	}
	
	/**
	 * Compute baseProfile URL
	 * 
	 * Determine Steam API call method
	 * Only numeric => using profile method
	 * Not only numeric => using id method
	 * 
	 * @param gamerSteamId
	 * @return
	 */
	private static String baseProfileURL(String gamerSteamId) {
		return isAPICallBySteamId64(gamerSteamId) ? "profiles/" : "id/";
	}
	
	/**
	 * Extract appId from gameLink
	 * @param gameLink
	 * @return
	 */
	public static String getAppIdFromGameLink(String gameLink) {
		Matcher matcher = steamGameLinkPattern.matcher(gameLink);
		return matcher.matches() ? matcher.group(1) : null;
	}
	
	/**
	 * Extract appId from statsLink
	 * @param statsLink
	 * @return
	 */
	public static String getAppIdFromStatsLink(String statsLink) {
		Matcher fullNumericMatcher = Strings.fullNumericPattern.matcher(statsLink);
		Matcher gameStatsLinkMatcher = steamGameStatsLinkPattern.matcher(statsLink);
		if (fullNumericMatcher.matches())
			return fullNumericMatcher.group(1);
		if (gameStatsLinkMatcher.matches()) {
			fullNumericMatcher = Strings.fullNumericPattern.matcher(gameStatsLinkMatcher.group(3));
			if (fullNumericMatcher.matches())
				return fullNumericMatcher.group(1);
		}
		return null;
	}
	
	/**
	 * Extract appId from gameImageLink
	 * @param gameLink
	 * @return
	 */
	public static String getAppIdFromGameImageLink(String gameImageLink) {
		Matcher matcher = steamGameImageLinkPattern.matcher(gameImageLink);
		return matcher.matches() ? matcher.group(1) : null;
	}
	
	/**
	 * Build URL command to get XML profile from Steam Community for gamerSteamId
	 * 
	 * @param gamerSteamId
	 * @return URL command
	 */
	public static String profileURLCommand(String gamerSteamId) {
		gamerSteamId = gamerSteamId.trim();
		return Steam.steamCommunityURL + "/" + baseProfileURL(gamerSteamId) + gamerSteamId + "/?xml=1";
	}
	
	/**
	 * Build URL command to get XML gamesList from Steam Community for gamerSteamId
	 * 
	 * @param gamerSteamId
	 * @return URL command
	 */
	public static String gamesListURLCommand(String gamerSteamId) {
		gamerSteamId = gamerSteamId.trim();
		return Steam.steamCommunityURL + "/" + baseProfileURL(gamerSteamId) + gamerSteamId + "/games?xml=1";
	}
	
	/**
	 * Build URL command to get XML friendsList from Steam Community for gamerSteamId
	 * 
	 * @param gamerSteamId
	 * @return URL command
	 */
	public static String friendsListURLCommand(String gamerSteamId) {
		gamerSteamId = gamerSteamId.trim();
		return Steam.steamCommunityURL + "/" + baseProfileURL(gamerSteamId) + gamerSteamId + "/friends?xml=1";
	}
	
	/**
	 * Build URL command to get XML statsList from Steam Community for game
	 * 
	 * @param game
	 * @return
	 */
	public static String gameMainStatsURLCommand(String gamerSteamId, SteamGame game) {
		return steamGameStatsLinkPattern.matcher(game.getStatsLink()).matches() ? 
				game.getStatsLink() + "/?xml=1" : 
					Steam.steamCommunityURL + "/" + baseProfileURL(gamerSteamId) + gamerSteamId + "/stats/" + game.getStatsLink() + "/?xml=1";
	}
	
	/**
	 * Build URL command to get XML statsList from Steam Community for gamerSteamId/game
	 * 
	 * @param gamerSteamId
	 * @param game
	 * @return
	 */
	public static String gameStatsURLCommand(String gamerSteamId, SteamGame game) {
		if (Strings.fullNumericPattern.matcher(gamerSteamId).matches()) {
			return steamGameStatsLinkPattern.matcher(game.getStatsLink()).matches() ? game.getStatsLink() + "/?xml=1" : 
				Steam.steamCommunityURL + "/" + baseProfileURL(gamerSteamId) + gamerSteamId + "/stats/" + game.getStatsLink() + "/?xml=1";
		} else {
			gamerSteamId = gamerSteamId.trim();
			return steamGameStatsLinkPattern.matcher(game.getStatsLink()).matches() ? game.getStatsLink() + "/?xml=1" : 
				Steam.steamCommunityURL + "/" + baseProfileURL(gamerSteamId) + gamerSteamId + "/stats/" + game.getStatsLink() + "/?xml=1";
		} 
	}
	
	/**
	 * Launch a game with Steam Protocol
	 * "steam://run/" + appID
	 * 
	 * @param game the game to launch
	 */
	public static void launchGameWithSteamProtocol(SteamGame game) throws IOException, URISyntaxException {
		URI uri = new URI("steam://run/" + game.getAppID());
		if (Desktop.isDesktopSupported())
			Desktop.getDesktop().browse(uri);
	}
	
	/**
	 * Launch a game with Steam Executable
	 * steamExecutable + " -applaunch " + appID
	 * 
	 * @param game the game to launch
	 * @param steamExecutablePath the steam executable path
	 */
	public static void launchGameWithSteamExecutable(SteamGame game, String steamExecutablePath) throws IOException {
		String call = steamExecutablePath + " -applaunch " + game.getAppID();
		Runtime.getRuntime().exec(call); 
	}
	
	/**
	 * @return if http://www.steamcommunity.com is reachable then return true , else return false
	 */
	public static boolean isSteamCommunityReachable() throws MalformedURLException, UnknownHostException, SocketTimeoutException, IOException  {		
		CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(steamCommunityURL);
            RequestConfig defaultRequestConfig = RequestConfig.custom().build();
            RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig)
                    .setSocketTimeout(1500)
                    .setConnectTimeout(1500)
                    .setConnectionRequestTimeout(1500)
                    .build();
            httpGet.setConfig(requestConfig);
            CloseableHttpResponse response = httpclient.execute(httpGet);
            try {
            	responseStatusCode = response.getStatusLine().getStatusCode();
				if (responseStatusCode >= 200 && responseStatusCode < 300) {
					responseStatusLine = response.getStatusLine();
					EntityUtils.consume(response.getEntity());
				} else {
					responseErrorCause = response.getStatusLine().getReasonPhrase();
					return false;
				}
            } catch (Exception e) {
            	responseErrorCause = response.getStatusLine().getReasonPhrase();
            	throw (e);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }        
        return true;        
	}

}
