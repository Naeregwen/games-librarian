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
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import commons.GamesLibrarianIcons.LoadingSource;
import commons.api.Steam;
import commons.api.SteamGame;
import commons.api.SteamLaunchMethod;
import commons.api.SteamProfile;
import commons.api.parsers.SteamGamesListParser;
import components.Librarian;
import components.commons.ColoredTee;
import components.commons.ColoredTee.TeeColor;
import components.workers.responsehandlers.XMLResponseHandler;

/**
 * @author Naeregwen
 *
 */
public class SteamFriendGameListReader extends SwingWorker<Vector<SteamGame>, String> {

	Librarian librarian;
	SteamProfile friend;
	int index;
	CountDownLatch doneSignal;
	SteamLaunchMethod defaultSteamLaunchMethod;
	ResourceBundle messages;
	
	public SteamFriendGameListReader(Librarian librarian, SteamProfile friend, int index, CountDownLatch doneSignal, SteamLaunchMethod defaultSteamLaunchMethod, ResourceBundle messages) {
		this.librarian = librarian;
		this.friend = friend;
		this.index = index;
		this.doneSignal = doneSignal;
		this.defaultSteamLaunchMethod = defaultSteamLaunchMethod;
		this.messages = messages;
	}

	public String getFriendID() {
		return friend.getId();
	}
	
	/*/
	 * (non-Javadoc)
	 * @see javax.swing.SwingWorker#doInBackground()
	 */
	@Override
	protected Vector<SteamGame> doInBackground() throws Exception {
		
		if (friend == null || friend.getSteamID64() == null) return null;
		
		SteamGamesListParser steamGamesListParser = new SteamGamesListParser(defaultSteamLaunchMethod, LoadingSource.Steam);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		try {
			
			String url = Steam.gamesListURLCommand(friend.getSteamID64());
			publish(TeeColor.Message.name(), String.format(messages.getString("infosRollReadingGamesListFromURL"), index, friend.getId(), friend.getPrivacyState(messages.getString("undefinedPrivacyState"))), TeeColor.Info.name(), url);
			
			new URL(url); // Reject malformed URL
			
			XMLReader steamGamesListReader = XMLReaderFactory.createXMLReader();
			steamGamesListReader.setContentHandler(steamGamesListParser);
            HttpGet httpget = new HttpGet(url);
            publish("SteamFriendGameListReader Executing request " + httpget.getRequestLine());
            
            Exception exception = httpclient.execute(httpget, new XMLResponseHandler(steamGamesListReader));
            if (exception != null)
            	publish(TeeColor.Error.name(), String.format(messages.getString("errorExceptionMessageWithSteamID"), friend.getId(), 
            			friend.getPrivacyState(messages.getString("undefinedPrivacyState")), exception.getClass().getName(), exception.getLocalizedMessage()));
            
		} catch (CancellationException e) {
			publish("SteamFriendGameStatsReader " + friend.getSteamID64() + " cancelled during doInBackground");
		} catch (IOException e) {
			publish(TeeColor.Error.name(), String.format(messages.getString("errorExceptionMessageWithSteamID"), friend.getId(), 
					friend.getPrivacyState(messages.getString("undefinedPrivacyState")), e.getClass().getName(), e.getLocalizedMessage()));
        } finally {
            httpclient.close();
		}
		return steamGamesListParser != null && steamGamesListParser.getGamesList() != null ? steamGamesListParser.getGamesList().getSteamGames() : null;
	}
		
	/*/
	 * (non-Javadoc)
	 * @see javax.swing.SwingWorker#done()
	 */
	@Override
	protected void done() {
		if (isCancelled()) {
			librarian.getTee().writelnMessage("SteamFriendGameListReader " + friend.getId() + " cancelled before done");
		} else {
			try {
				Vector<SteamGame> gameslist = (Vector<SteamGame>) get();
				if (gameslist != null) friend.setSteamGames(gameslist);
			} catch (InterruptedException e) {
				librarian.getTee().writelnMessage("SteamFriendGameListReader " + friend.getId() + " interrupted during done");
				librarian.getTee().printStackTrace(e);
			} catch (CancellationException e) {
				librarian.getTee().writelnMessage("SteamFriendGameListReader " + friend.getId() + " cancelled during done");
			} catch (ExecutionException e) {
				librarian.getTee().writelnMessage("SteamFriendGameListReader " + friend.getId() + " execution exception during done");
				librarian.getTee().printStackTrace(e);
			}
		}
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
