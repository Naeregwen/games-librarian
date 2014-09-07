/**
 * 
 */
package components.workers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import commons.ColoredTee;
import commons.ColoredTee.TeeColor;
import commons.api.Steam;
import commons.api.SteamAchievement;
import commons.api.SteamAchievementsList;
import commons.api.SteamGame;
import commons.api.SteamGameStats;
import commons.api.Steam.SteamResponseFormat;
import commons.api.Steam.SteamResponseLanguage;
import commons.api.parsers.SteamGameStatsParser;
import commons.api.scrapers.SteamAchievementsListScraper;
import components.Librarian;
import components.workers.responsehandlers.XMLResponseHandler;

/**
 * @author Naeregwen
 *
 */
public class SteamGameStatsReader extends SwingWorker<SteamGameStats, String> {

	Librarian librarian;
	SteamGame game;
	CountDownLatch doneSignal;
	int index;
	
	public SteamGameStatsReader(Librarian librarian, SteamGame game, CountDownLatch doneSignal, int index) {
		this.librarian = librarian;
		this.game = game;
		this.doneSignal = doneSignal;
		this.index = index;
	}

	/**
	 * @return the game
	 */
	public SteamGame getGame() {
		return game;
	}

	/*/
	 * (non-Javadoc)
	 * @see javax.swing.SwingWorker#doInBackground()
	 */
	@Override
	protected SteamGameStats doInBackground() throws Exception {
		
		if (game == null || game.getStatsLink() == null || librarian.getCurrentSteamProfile() == null) return null;
		
		ResourceBundle messages = librarian.getParameters().getMessages();
		String steamId = librarian.getCurrentSteamProfile().getId();
		String steamId64 = librarian.getCurrentSteamProfile().getId64();
		SteamGameStatsParser steamGameStatsParser = new SteamGameStatsParser(steamId64, steamId, 0, null);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String url = "";
		
		try {
			
	    	url = Steam.gameMainStatsURLCommand(steamId64, game);
	    	publish(TeeColor.Message.name(), String.format(messages.getString("infosReadGameStatsIsStarting"), index, game.getName(), steamId), TeeColor.Info.name(), url);
	    	
	    	new URL(url); // Reject malformed URL
	    	
			XMLReader steamGameStatsReader = XMLReaderFactory.createXMLReader();
			steamGameStatsReader.setContentHandler(steamGameStatsParser);
			HttpGet httpGet = new HttpGet(url);
			publish("SteamGameStatsReader Executing request " + httpGet.getRequestLine());
			
            Exception exception = httpclient.execute(httpGet, new XMLResponseHandler(steamGameStatsReader));
            if (exception != null)
            	if (exception instanceof SAXParseException) {
                	publish(TeeColor.Error.name(), String.format(messages.getString("errorExceptionMessageWithSteamIDTextAndURL"), steamId,
                			librarian.getCurrentSteamProfile().getPrivacyState(messages.getString("undefinedPrivacyState")),
                			game.getID("unknown gamename"), url,
                			exception.getClass().getName(), messages.getString("invalidXML")));
            	} else
            		publish(TeeColor.Error.name(), String.format(messages.getString("errorExceptionMessageWithSteamIDTextAndURL"), steamId,
            				librarian.getCurrentSteamProfile().getPrivacyState(messages.getString("undefinedPrivacyState")),
            				game.getID("unknown gamename"), url,
            				exception.getClass().getName(), exception.getLocalizedMessage()));
            else if (steamGameStatsParser.getSteamGameStats() == null 
            			|| steamGameStatsParser.getSteamGameStats().getSteamAchievementsList() == null 
            			|| steamGameStatsParser.getSteamGameStats().getSteamAchievementsList().getSteamAchievements() == null 
            			|| steamGameStatsParser.getSteamGameStats().getSteamAchievementsList().getSteamAchievements().size() == 0) {
            	
            		// Try HTML Version
            		url = Steam.gameStatsURLCommand(steamId64, game, SteamResponseFormat.HTML, SteamResponseLanguage.English);
            		
            		publish("HTML SteamGameStatsReader Trying HTML request  : " + url);
            		
            		SteamAchievementsListScraper steamAchievementsListScraper = new SteamAchievementsListScraper(url);
            		Vector<SteamAchievement> steamAchievements = steamAchievementsListScraper.getAchievements();
            		
            		SteamAchievementsList steamAchievementsList = new SteamAchievementsList(steamId64, steamId, 0, null);
            		steamAchievementsList.setSteamAchievements(steamAchievements);
            		
            		SteamGameStats steamGameStats = new SteamGameStats(steamId64);
            		steamGameStats.setAppID(game.getAppID());
            		steamGameStats.setSteamAchievementsList(steamAchievementsList);
            		
            		return steamGameStats;
            }
            
            if (steamGameStatsParser.getSteamGameStats() == null)
            	System.err.println("NULL steamGameStatsParser.getSteamGameStats() " + game.getName());
            else if (steamGameStatsParser.getSteamGameStats().getSteamAchievementsList() == null)
            	System.err.println("NULL steamGameStatsParser.getSteamGameStats().getSteamAchievementsList()" + game.getName());
            else if (steamGameStatsParser.getSteamGameStats().getSteamAchievementsList().getSteamAchievements() == null)
            	System.err.println("NULL steamGameStatsParser.getSteamGameStats().getSteamAchievementsList().getSteamAchievements()" + game.getName());
            else if (steamGameStatsParser.getSteamGameStats().getSteamAchievementsList().getSteamAchievements().size() == 0)
            	System.err.println("NULL steamGameStatsParser.getSteamGameStats().getSteamAchievementsList().getSteamAchievements().size() == 0" + game.getName());
            
		} catch (CancellationException e) {
			publish("SteamGameStatsReader " + game.getName() + " cancelled during doInBackground");
        } catch (IOException exception) {
			publish(TeeColor.Error.name(), String.format(messages.getString("errorExceptionMessageWithSteamIDTextAndURL"), steamId,
					librarian.getCurrentSteamProfile().getPrivacyState(messages.getString("undefinedPrivacyState")),
					game.getID("unknown gamename"), url,
					exception.getClass().getName(), exception.getLocalizedMessage()));
        } finally {
            httpclient.close();
		}
		return steamGameStatsParser != null ? steamGameStatsParser.getSteamGameStats() : null;
	}

	/*/
	 * (non-Javadoc)
	 * @see javax.swing.SwingWorker#done()
	 */
	@Override
	protected void done() {
		librarian.setSteamGameStatsReading(false);
		try {
			SteamGameStats steamGameStats = get();
			if (steamGameStats != null)
				// Update gameStats of current game in non latched works
				if (doneSignal == null)
					librarian.updateGameTab(steamGameStats);
				// Update gameStats of currentSteamProfile in latched works
				else 
					librarian.addSteamGameStats(steamGameStats);
		} catch (InterruptedException e) {
			librarian.updateSteamAchievementsPane();
			librarian.updateGameTabTitle();
			librarian.getTee().writelnInfos("SteamGameStatsReader " + game.getName() + " interrupted during done");
			librarian.getTee().printStackTrace(e);
		} catch (CancellationException e) {
			librarian.updateSteamAchievementsPane();
			librarian.updateGameTabTitle();
			librarian.getTee().writelnInfos("SteamGameStatsReader " + game.getName() + " cancelled exception during done");
		} catch (ExecutionException e) {
			librarian.updateSteamAchievementsPane();
			librarian.updateGameTabTitle();
			librarian.getTee().writelnInfos("SteamGameStatsReader " + game.getName() + " execution exception during done");
			librarian.getTee().printStackTrace(e);
		}
		if (doneSignal != null)
			doneSignal.countDown();
	}
	
	/*/
	 * (non-Javadoc)
	 * @see javax.swing.SwingWorker#process(java.util.List)
	 */
	@Override
    protected void process(List<String> strings) {
		ColoredTee.writeln(librarian.getTee(), strings);
    }

}
