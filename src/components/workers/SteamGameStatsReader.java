/**
 * 
 */
package components.workers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import commons.ColoredTee;
import commons.ColoredTee.TextColor;
import commons.api.Steam;
import commons.api.SteamGame;
import commons.api.SteamGameStats;
import commons.api.parsers.SteamGameStatsParser;
import components.Librarian;
import components.workers.responsehandlers.XMLResponseHandler;

/**
 * @author Naeregwen
 *
 */
public class SteamGameStatsReader extends SwingWorker<SteamGameStats, String> {

	Librarian librarian;
	SteamGame game;
	
	public SteamGameStatsReader(Librarian librarian, SteamGame game) {
		this.librarian = librarian;
		this.game = game;
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
		try {
	    	String url = Steam.gameMainStatsURLCommand(steamId64, game);
	    	publish(TextColor.Message.name(), String.format(messages.getString("infosReadGameStatsIsStarting"), game.getName(), steamId), TextColor.Info.name(), url);
			XMLReader steamGameStatsReader = XMLReaderFactory.createXMLReader();
			steamGameStatsReader.setContentHandler(steamGameStatsParser);
			new URL(url); // Reject malformed URL
			HttpGet httpget = new HttpGet(url);
			publish("SteamGameStatsReader Executing request " + httpget.getRequestLine());
            Exception exception = httpclient.execute(httpget, new XMLResponseHandler(steamGameStatsReader));
            if (exception != null)
            	publish("error", String.format(messages.getString("errorExceptionMessageWithSteamID"), steamId, 
            			 librarian.getCurrentSteamProfile().getPrivacyState(messages.getString("undefinedPrivacyState")), exception.getClass().getName(), exception.getLocalizedMessage()));
		} catch (CancellationException e) {
			publish("SteamGameStatsReader " + game.getName() + " cancelled during doInBackground");
        } catch (IOException exception) {
			publish("error", String.format(messages.getString("errorExceptionMessageWithSteamID"), steamId, 
					librarian.getCurrentSteamProfile().getPrivacyState(messages.getString("undefinedPrivacyState")), exception.getClass().getName(), exception.getLocalizedMessage()));
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
			// Update gameStats of current game
			librarian.updateGameTab(steamGameStats);
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
