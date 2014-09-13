/**
 * Copyright 2012-2014 Naeregwen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package components.workers;

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
import commons.ColoredTee.TeeColor;
import commons.GamesLibrary.LoadingSource;
import commons.api.Parameters;
import commons.api.Steam;
import commons.api.SteamGamesList;
import commons.api.SteamProfile;
import commons.api.parsers.SteamProfileParser;
import components.Librarian;
import components.workers.responsehandlers.XMLResponseHandler;

/**
 * @author Naeregwen
 *
 */
public class SteamProfileReader extends SwingWorker<SteamProfile, String> {

	Librarian librarian;
	
	public SteamProfileReader(Librarian librarian) {
		this.librarian = librarian;
	}

	private void clearProgressIndicators() {
		librarian.setSteamProfileReading(false);
		librarian.setSteamFriendsListReading(false);
		librarian.updateProfileTabTitle();
		librarian.updateProfileFriendsTabTitle();
	}
	
	/*/
	 * (non-Javadoc)
	 * @see javax.swing.SwingWorker#doInBackground()
	 */
	@Override
	protected SteamProfile doInBackground() throws Exception {
		
		if (librarian.getCurrentSteamProfile() == null || (librarian.getCurrentSteamProfile().getSteamID() == null && librarian.getCurrentSteamProfile().getSteamID64() == null)) return null;
		
		Parameters parameters = librarian.getParameters();
    	ResourceBundle messages = parameters.getMessages();
		String steamId = librarian.getCurrentSteamProfile().getId();
		String steamId64 = librarian.getCurrentSteamProfile().getId64();
		SteamProfileParser steamProfileParser = new SteamProfileParser(parameters, librarian.getTee(), 0);
		CloseableHttpClient httpClient = HttpClients.createDefault();
//		CloseableHttpClient httpclient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
		
		try {
			
	    	String url = Steam.profileURLCommand(steamId64);
	    	publish(TeeColor.Message.name(), String.format(messages.getString("infosReadProfileIsStarting"), 1), TeeColor.Info.name(), url);
	    	
	    	new URL(url); // Reject malformed URL
	    	
			XMLReader steamProfileReader = XMLReaderFactory.createXMLReader();
			steamProfileReader.setContentHandler(steamProfileParser);
			XMLResponseHandler responseHandler = new XMLResponseHandler(steamProfileReader);
			HttpGet httpGet = new HttpGet(url);
			publish("SteamProfileReader Executing request " + httpGet.getRequestLine());
			
            Exception exception = httpClient.execute(httpGet, responseHandler);
            if (exception != null)
            	publish(TeeColor.Error.name(), String.format(messages.getString("errorExceptionMessageWithSteamID"), steamId, 
           			 librarian.getCurrentSteamProfile().getPrivacyState(messages.getString("undefinedPrivacyState")), exception.getClass().getName(), exception.getLocalizedMessage()));
            if (responseHandler.getHttpStatus() != 200) {
            	if (steamProfileParser != null && steamProfileParser.getSteamProfile() != null) {
            		steamProfileParser.getSteamProfile().setLoadingSource(LoadingSource.Errored);
            		steamProfileParser.getSteamProfile().setOnlineState(new Integer(responseHandler.getHttpStatus()).toString());
            	}
            }
            	
		} catch (CancellationException e) {
			publish("SteamProfileReader " + steamId + " cancelled during doInBackground");
		} finally {
			httpClient.close();
		}
    	return steamProfileParser != null ? steamProfileParser.getSteamProfile() : null;
	}

	/*/
	 * (non-Javadoc)
	 * @see javax.swing.SwingWorker#done()
	 */
	@Override
	protected void done() {
		if (librarian.getCurrentSteamProfile() == null || (librarian.getCurrentSteamProfile().getSteamID() == null && librarian.getCurrentSteamProfile().getSteamID64() == null)) return;
		String steamId = librarian.getCurrentSteamProfile().getSteamID() != null ? librarian.getCurrentSteamProfile().getSteamID() : librarian.getCurrentSteamProfile().getSteamID64();
		if (isCancelled()) {
			clearProgressIndicators();
			librarian.getTee().writelnMessage("SteamProfileReader " + steamId + " cancelled before done");
		} else {
			try {
				SteamProfile steamProfile = get();
				if (steamProfile != null) {
					if (steamProfile.getLoadingSource().equals(LoadingSource.Errored)) {
						clearProgressIndicators();
						librarian.displayErroredProfile(steamProfile);
					} else {
						// Replace steamProfile.steamGames by parameters.steamGamesList.steamGames, when id are matching
						SteamGamesList steamGamesList = librarian.getParameters().getSteamGamesList();
						if (steamGamesList != null && ((steamGamesList.getSteamID64() != null && steamGamesList.getSteamID64().equals(steamProfile.getSteamID64())
								|| (steamGamesList.getSteamID() != null && steamGamesList.getSteamID().equals(steamProfile.getSteamID())))))
							steamProfile.setSteamGames(steamGamesList.getSteamGames());
						// Update profiles list
						librarian.addProfile(steamProfile, false);
						// Update profile tab
						librarian.updateSelectedSteamProfile(steamProfile);
						// Chain with friendsList reading
						librarian.readSteamFriendsList();
					}
				}
			} catch (InterruptedException e) {
				clearProgressIndicators();
				librarian.getTee().writelnMessage("SteamProfileReader " + steamId + " interrupted during done");
				librarian.getTee().printStackTrace(e);
			} catch (CancellationException e) {
				clearProgressIndicators();
				librarian.getTee().writelnMessage("SteamProfileReader " + steamId + " cancelled during done");
			} catch (ExecutionException e) {
				clearProgressIndicators();
				librarian.getTee().writelnMessage("SteamProfileReader " + steamId + " execution exception during done");
				librarian.getTee().printStackTrace(e);
			}
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
