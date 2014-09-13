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
package components.workers.latched;

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

import commons.ColoredTee;
import commons.ColoredTee.TeeColor;
import commons.api.Parameters;
import commons.api.Steam;
import commons.api.SteamFriendsList;
import commons.api.SteamProfile;
import commons.api.parsers.SteamFriendsListParser;
import components.Librarian;
import components.workers.SteamFriendProfileReader;
import components.workers.responsehandlers.XMLResponseHandler;

/**
 * @author Naeregwen
 *
 */
public class SteamFriendsListReader extends SwingWorker<SteamFriendsList, String> {

	Librarian librarian;
	SteamProfile steamProfile;
	List<SteamFriendProfileReader> steamFriendProfileReaders;
	
	public SteamFriendsListReader(Librarian librarian, SteamProfile steamProfile) {
		this.librarian = librarian;
		this.steamProfile = steamProfile;
		this.steamFriendProfileReaders = new Vector<SteamFriendProfileReader>();
	}

	private void cancelSteamFriendProfileReaders() {
		for (SteamFriendProfileReader steamFriendProfileReader : steamFriendProfileReaders) {
			if (!steamFriendProfileReader.isDone() && !steamFriendProfileReader.isCancelled())
				if (steamFriendProfileReader.cancel(true))
					librarian.getTee().writelnInfos("steamFriendProfileReader " + steamFriendProfileReader.getSteamId64() + " cancelled");
				else
					librarian.getTee().writelnError("Can not cancel steamFriendProfileReader");
			else
				librarian.getTee().writelnInfos("steamFriendProfileReader " + steamFriendProfileReader.getSteamId64() + " already done/cancelled");
		}
	}
	
	private void clearProgressIndicators() {
		librarian.setSteamProfileReading(false);
		librarian.setSteamFriendsListReading(false);
		librarian.updateProfileTabTitle();
		librarian.updateProfileFriendsTabTitle();
	}
	
	@Override
	protected SteamFriendsList doInBackground() throws Exception {
		
		if (steamProfile.getSteamID64() == null && steamProfile.getSteamID() == null) return null;
		
		SteamFriendsList steamFriendsList = null;
		Parameters parameters = librarian.getParameters();
		ResourceBundle messages = parameters.getMessages();
		String steamId = steamProfile.getId();
		String steamId64 = steamProfile.getId64();
		SteamFriendsListParser steamFirendsListParser = new SteamFriendsListParser(parameters, librarian.getTee());
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		try {
			
			String url = Steam.friendsListURLCommand(steamId64);
	    	publish(TeeColor.Message.name(), String.format(messages.getString("infosReadFriendsListIsStarting"), steamId), TeeColor.Info.name(), url);
	    	
			XMLReader steamFriendsListReader = XMLReaderFactory.createXMLReader();
			steamFriendsListReader.setContentHandler(steamFirendsListParser);
			new URL(url); // Reject malformed URL
			HttpGet httpget = new HttpGet(url);
			
			publish("SteamFriendsListReader Executing request " + httpget.getRequestLine());
			
            Exception exception = httpclient.execute(httpget, new XMLResponseHandler(steamFriendsListReader));
            if (exception != null)
            	publish(TeeColor.Error.name(), String.format(messages.getString("errorExceptionMessageWithSteamID"), steamId, 
           			 librarian.getCurrentSteamProfile().getPrivacyState(messages.getString("undefinedPrivacyState")), exception.getClass().getName(), exception.getLocalizedMessage()));
            
            steamFriendsList = steamFirendsListParser != null ? steamFirendsListParser.getSteamFriendsList() : null;
            
	    	if (steamFriendsList != null && !steamFriendsList.getFriends().isEmpty()) {
	    		
	    		librarian.setSteamFriendsListReading(true);
	    		librarian.updateProfileFriendsTabTitle();
	    		CountDownLatch doneSignal = new CountDownLatch(steamFriendsList.getFriends().size());
	    		int index = 1;
	    		
	    		for (SteamProfile friend : steamFriendsList.getFriends()) {
	    			
//	    			try {
//	    				Thread.sleep((long)(Math.random() * 100) + 50); // Add some delay between requests
//	    			} catch (InterruptedException e) {
//	    				cancelSteamFriendProfileReaders();
//	    				librarian.getTee().writelnInfos("SteamFriendsListReader interrupted during doInBackground sleep");
//	    				break;
//					}
	
	    			SteamFriendProfileReader steamFriendProfileReader = new SteamFriendProfileReader(librarian, friend.getSteamID64(), doneSignal, index++);
	    			steamFriendProfileReaders.add(steamFriendProfileReader);
	    			steamFriendProfileReader.execute();
	    		}
	    		
    			doneSignal.await();
    			
    			publish(String.format(messages.getString("infosFriendsResume"), steamProfile.getId(), steamFriendsList.getFriends() != null ? steamFriendsList.getFriends().size() : 0));
	    	}
		} catch (InterruptedException e) {
			cancelSteamFriendProfileReaders();
			publish("SteamFriendsListReader " + steamProfile.getId() + " interrupted during doInBackground");
		} catch (CancellationException e) {
			cancelSteamFriendProfileReaders();
			publish("SteamFriendsListReader " + steamProfile.getId() + " cancelled during doInBackground");
		} finally {
			httpclient.close();
		}
    	return steamFriendsList;
	}

	/*/
	 * (non-Javadoc)
	 * @see javax.swing.SwingWorker#done()
	 */
	@Override
	protected void done() {
		if (isCancelled()) {
			cancelSteamFriendProfileReaders();
			librarian.getTee().writelnInfos("SteamFriendsListReader " + steamProfile.getId() + " cancelled before done");
		} else {
			try {
				SteamFriendsList steamFriendsList = get();
				if (steamFriendsList != null) {
					// Update friendsButtonGroup
					librarian.updateProfileFriendsTab(librarian.getCurrentSteamProfile());
					// Chain with friends gameLists
					if (steamFriendsList.getFriends().size() < Librarian.MaxFriendsForGamesList)
						librarian.readSteamFriendsGameLists();
				}
			} catch (InterruptedException e) {
				cancelSteamFriendProfileReaders();
				librarian.getTee().writelnInfos("SteamFriendsListReader " + steamProfile.getId() + " interrupted during done");
				librarian.getTee().printStackTrace(e);
			} catch (CancellationException e) {
				cancelSteamFriendProfileReaders();
				librarian.getTee().writelnInfos("SteamFriendsListReader " + steamProfile.getId() + " cancelled during done");
			} catch (ExecutionException e) {
				cancelSteamFriendProfileReaders();
				librarian.getTee().writelnInfos("SteamFriendsListReader " + steamProfile.getId() + " execution exception during done");
				librarian.getTee().printStackTrace(e);
			}
		}
		clearProgressIndicators();
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
