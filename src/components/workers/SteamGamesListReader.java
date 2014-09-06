/**
 * 
 */
package components.workers;

import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import commons.ColoredTee;
import commons.ColoredTee.TeeColor;
import commons.GamesLibrary.LoadingSource;
import commons.api.Parameters;
import commons.api.Steam;
import commons.api.SteamGamesList;
import commons.api.parsers.SteamGamesListParser;
import components.Librarian;
import components.workers.responsehandlers.XMLResponseHandler;

/**
 * @author Naeregwen
 *
 */
public class SteamGamesListReader extends SwingWorker<SteamGamesList, String> {

	Librarian librarian;
	
	Exception exception;
	
	public SteamGamesListReader(Librarian librarian) {
		this.librarian = librarian;
	}
	
	@Override
	protected SteamGamesList doInBackground() throws Exception {
		
		if (librarian.getCurrentSteamProfile().getId64() == null) return null;
		
		Parameters parameters = librarian.getParameters();
		ResourceBundle messages = parameters.getMessages();
		String steamId64 = librarian.getCurrentSteamProfile().getId64();
		SteamGamesListParser steamGamesListParser = new SteamGamesListParser(parameters.getDefaultSteamLaunchMethod(), LoadingSource.Steam);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		try {
			
			String url = Steam.gamesListURLCommand(steamId64);
			
			new URL(url); // Reject malformed URL
			
			XMLReader steamGameListsReader = XMLReaderFactory.createXMLReader();
			steamGameListsReader.setContentHandler(steamGamesListParser);
			HttpGet httpget = new HttpGet(url);
			publish("SteamGamesListReader Executing request " + httpget.getRequestLine());
			
            Exception exception = httpclient.execute(httpget, new XMLResponseHandler(steamGameListsReader));
            if (exception != null)
            	publish(TeeColor.Error.name(), String.format(messages.getString("errorExceptionMessageWithSteamID64"), steamId64, 
            			 exception.getClass().getName(), exception.getLocalizedMessage()));
            
		} catch (ConnectException e) {
			exception = e;
		} catch (MalformedURLException e) {
			exception = e;
		} catch (SAXException e) {
			exception = e;
		} catch (IOException e) {
			exception = e;
		} finally {
			httpclient.close();
		}
		return steamGamesListParser != null ? steamGamesListParser.getGamesList() : null;
	}

	/*/
	 * (non-Javadoc)(
	 * @see javax.swing.SwingWorker#done()
	 * 
	 * TODO: CancellationException
	 */
	@Override
	protected void done() {
		librarian.setSteamGamesListReading(false);
		try {
			if (exception != null) {
				librarian.getTee().printStackTrace(exception);
			} else {
				SteamGamesList steamGamesList = get();
//				if (steamGamesList == null) {
//					librarian.updateLibraryMainTabTitle();
//				} else {
				if (steamGamesList != null) {
					Parameters parameters = librarian.getParameters();
					parameters.setSteamGamesList(steamGamesList);
					if (steamGamesList != null && steamGamesList.getSteamGames() != null && steamGamesList.getSteamGames().size() > 0) {
						librarian.getTee().writelnInfos(parameters.summarizeGamesList());
						// Replace currentSteamProfile.steamGames by parameters.steamGamesList.steamGames
						librarian.replaceSteamGamesList();
					} else {
						librarian.readError();
					}
//					librarian.updateLibraryMainTabTitle();
					librarian.updateAllLibraryTabs();
				}
			}
		} catch (InterruptedException | ExecutionException e) {
//			librarian.updateLibraryMainTabTitle();
			librarian.getTee().printStackTrace(e);
		}
		librarian.updateLibraryMainTabTitle();
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
