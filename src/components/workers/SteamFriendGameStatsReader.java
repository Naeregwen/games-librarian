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
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import javax.swing.Icon;
import javax.swing.SwingWorker;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import commons.api.Steam;
import commons.api.SteamGame;
import commons.api.SteamGameStats;
import commons.api.SteamProfile;
import commons.api.Steam.SteamResponseFormat;
import commons.api.parsers.SteamGameStatsParser;
import components.Librarian;
import components.commons.ColoredTee;
import components.commons.ColoredTee.TeeColor;
import components.workers.responsehandlers.XMLResponseHandler;

/**
 * @author Naeregwen
 *
 */
public class SteamFriendGameStatsReader extends SwingWorker<SteamGameStats, String> {

	Librarian librarian;
	SteamProfile friend;
	Icon friendAvatarIcon;
	SteamGame game;
	CountDownLatch doneSignal;
	int initialPosition;
	
	/**
	 * Create a worker
	 * @param tee
	 * @param application
	 * @param friend
	 * @param friendAvatarIcon
	 * @param game
	 * @param initialPosition
	 * @param doneSignal null when called from readSteamFriendSteamGameStats (single read), initialized when called from readSteamFriendsSteamGameStats (multi read)
	 */
	public SteamFriendGameStatsReader(Librarian librarian, SteamProfile friend, Icon friendAvatarIcon, SteamGame game, int initialPosition, CountDownLatch doneSignal) {
		this.librarian = librarian;
		this.friend = friend;
		this.friendAvatarIcon = friendAvatarIcon;
		this.game = game;
		this.doneSignal = doneSignal;
		this.initialPosition = initialPosition;
	}

	@Override
	protected SteamGameStats doInBackground() throws Exception {
		
		if (friend == null || friend.getSteamID64() == null || game.getStatsLink() == null || friend.getGame(game) == null) return null;
		
		ResourceBundle messages = librarian.getParameters().getMessages();
		String steamId = friend.getId();
		String steamId64 = friend.getId64();
		SteamGameStatsParser steamGamesStatsParser = new SteamGameStatsParser(steamId64, steamId, initialPosition, friendAvatarIcon);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		try {
			
	    	SteamGame friendGame = friend.getGame(game);
	    	String url = Steam.gameStatsURLCommand(steamId64, friendGame, SteamResponseFormat.XML, null);
	    	
	    	new URL(url); // Reject malformed URL
	    	
	    	publish(TeeColor.Message.name(), String.format(messages.getString("infosReadGameStatsIsStarting"), initialPosition, friendGame.getName(), steamId), TeeColor.Info.name(), url);
			XMLReader steamGameStatsReader = XMLReaderFactory.createXMLReader();
			steamGameStatsReader.setContentHandler(steamGamesStatsParser);
			HttpGet httpget = new HttpGet(url);
			publish("SteamFriendGameStatsReader Executing request " + httpget.getRequestLine());
			
            Exception exception = httpclient.execute(httpget, new XMLResponseHandler(steamGameStatsReader));
            if (exception != null)
            	publish(TeeColor.Error.name(), String.format(messages.getString("errorExceptionMessageWithSteamID"), friend.getId(), 
            			friend.getPrivacyState(messages.getString("undefinedPrivacyState")), exception.getClass().getName(), exception.getLocalizedMessage()));
            
		} catch (CancellationException e) {
			publish("SteamFriendGameStatsReader " + steamId + " cancelled during doInBackground");
        } catch (IOException exception) {
			publish(TeeColor.Error.name(), String.format(messages.getString("errorExceptionMessageWithSteamID"), friend.getId(), 
					friend.getPrivacyState(messages.getString("undefinedPrivacyState")), exception.getClass().getName(), exception.getLocalizedMessage()));
        } finally {
            httpclient.close();
		}
    	return steamGamesStatsParser != null ? steamGamesStatsParser.getSteamGameStats() : null;
	}

	/*/
	 * (non-Javadoc)
	 * @see javax.swing.SwingWorker#done()
	 */
	@Override
	protected void done() {
		if (isCancelled()) {
			librarian.getTee().writelnMessage("SteamFriendGameStatsReader " + friend.getSteamID64() + " cancelled before done");
		} else {
			if (doneSignal == null) {
				librarian.setSteamGameStatsReading(false);
				librarian.updateGameTabTitle();
			}
			try {
				SteamGameStats steamGameStats = get();
				// Update gameStats of current game
				librarian.updateSteamAchievementsPane(friend, steamGameStats, doneSignal != null);
			} catch (InterruptedException e) {
				librarian.getTee().writelnMessage("SteamFriendGameStatsReader " + friend.getSteamID64() + " interrupted during done");
				librarian.getTee().printStackTrace(e);
			} catch (CancellationException e) {
				librarian.getTee().writelnInfos("SteamFriendGameStatsReader " + friend.getSteamID64() + " cancelled during done");
			} catch (ExecutionException e) {
				librarian.getTee().writelnMessage("SteamFriendGameStatsReader " + friend.getSteamID64() + " execution exception during done");
				librarian.getTee().printStackTrace(e);
			}
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
