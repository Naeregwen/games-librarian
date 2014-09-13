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

import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import commons.ColoredTee;
import commons.ColoredTee.TeeColor;
import commons.api.SteamGame;
import commons.api.SteamLaunchMethod;
import commons.api.SteamProfile;
import components.Librarian;
import components.workers.SteamFriendGameListReader;

/**
 * @author Naeregwen
 *
 */
public class SteamFriendsGameListsReader extends SwingWorker<List<SteamProfile>, String> {

	Librarian librarian;
	SteamProfile steamProfile;
	SteamGame steamGame;
	SteamLaunchMethod defaultSteamLaunchMethod;
	List<SteamFriendGameListReader> steamFriendGameListReaders;
	
	
	Exception exception;
	
	public SteamFriendsGameListsReader(Librarian librarian, SteamProfile steamProfile, SteamGame steamGame, SteamLaunchMethod defaultSteamLaunchMethod) {
		this.librarian = librarian;
		this.steamProfile = steamProfile;
		this.steamGame = steamGame;
		this.defaultSteamLaunchMethod = defaultSteamLaunchMethod;
		this.steamFriendGameListReaders = new Vector<SteamFriendGameListReader>();
	}
	
	private void cancelSteamFriendGameListReaders() {
		for (SteamFriendGameListReader steamFriendGameListReader : steamFriendGameListReaders) {
			if (!steamFriendGameListReader.isDone() && !steamFriendGameListReader.isCancelled())
				if (steamFriendGameListReader.cancel(true))
					librarian.getTee().writelnError("steamFriendGameListReader " + steamFriendGameListReader.getFriendID() + " cancelled");
				else
					librarian.getTee().writelnError("Can not cancel steamFriendGameListReader");
			else
				librarian.getTee().writelnInfos("steamFriendGameListReader " + steamFriendGameListReader.getFriendID() + " already done/cancelled");
		}
	}
	
	private void clearProgressIndicators() {
		librarian.setSteamFriendsGameListsReading(false);
		librarian.setSteamGameStatsReading(false);
		if (librarian.getCurrentSteamProfile().hasGame(librarian.getCurrentSteamGame()))
			librarian.updateGameTabTitle();
	}
	
	@Override
	protected List<SteamProfile> doInBackground() throws Exception {
		
		if (steamProfile == null || steamProfile.getSteamFriends().isEmpty()) return null;
		
		Vector<SteamProfile> friendsWithoutGamelist = new Vector<SteamProfile>();
		ResourceBundle messages = librarian.getParameters().getMessages();
		
		try {
			
    		// Must know this number before constructing a CountDownLatch
			List<SteamProfile> friends = steamProfile.getSteamFriends();
			Iterator<SteamProfile> friendsIterator = friends.iterator();
			while (friendsIterator.hasNext()) {
				SteamProfile friend = friendsIterator.next();
				if (friend.getSteamGames() == null || friend.getSteamGames().size() == 0)
					friendsWithoutGamelist.add(friend);
			}
			
			if (friendsWithoutGamelist.size() > 0) {
				CountDownLatch doneSignal = new CountDownLatch(friendsWithoutGamelist.size());
				int index = 1;
				
				for (SteamProfile friend : friendsWithoutGamelist) {
					
//	    			try {
//	    				Thread.sleep((long)(Math.random() * 100) + 1000); // Add some delay between requests
//	    			} catch (CancellationException e) {
////	    				cancelSteamFriendGameListReaders();
//	    				librarian.getTee().writelnInfos("SteamFriendsGameListsReader " + steamProfile.getId() + " cancelled during doInBackground");
//	    			} catch (InterruptedException e) {
////	    				cancelSteamFriendGameListReaders();
//	    				librarian.getTee().writelnInfos("SteamFriendsGameListsReader interrupted during doInBackground sleep");
//	    				break;
//					}
					
	    			SteamFriendGameListReader steamFriendGameListReader = new SteamFriendGameListReader(librarian, friend, index++, doneSignal, defaultSteamLaunchMethod, messages);
	    			steamFriendGameListReaders.add(steamFriendGameListReader);
	    			steamFriendGameListReader.execute();
				}
				
				doneSignal.await();
				
				friendsIterator = friends.iterator();
				while (friendsIterator.hasNext()) {
					SteamProfile friend = friendsIterator.next();
    				publish(TeeColor.Info.name(), String.format(messages.getString("summarizeGamesList"), URLDecoder.decode(friend.getId(), "UTF-8"), friend.getSteamGames() != null? friend.getSteamGames().size() : 0));
				}
			}
		} catch (InterruptedException e) {
//			cancelSteamFriendGameListReaders();
			librarian.getTee().writelnInfos("SteamFriendsGameListsReader " + steamProfile.getId() + " interrupted during doInBackground");
		} catch (CancellationException e) {
//			cancelSteamFriendGameListReaders();
			librarian.getTee().writelnInfos("SteamFriendsGameListsReader " + steamProfile.getId() + " cancelled during doInBackground");
		}

		return friendsWithoutGamelist;
	}

	/*/
	 * (non-Javadoc)(
	 * @see javax.swing.SwingWorker#done()
	 */
	@Override
	protected void done() {
		if (isCancelled()) {
			cancelSteamFriendGameListReaders();
			librarian.getTee().writelnInfos("SteamFriendsGameListsReader " + steamProfile.getId() + " cancelled before done");
		} else {
			try {
				List<SteamProfile> friendsWithoutGamelist = (List<SteamProfile>) get();
				for (SteamProfile friend : friendsWithoutGamelist)
					librarian.addFriendGameList(friend.getSteamID64(), friend.getSteamGames());
				// Update currentSteamGame Pane only if game owned by currentSteamProfile
				if (librarian.getCurrentSteamProfile().hasGame(librarian.getCurrentSteamGame()))
					librarian.updateFriendsWithSameGamePane();
				else {
					librarian.setSteamFriendsGameListsReading(false);
					librarian.updateProfileTabTitle();
				}
			} catch (InterruptedException e) {
				cancelSteamFriendGameListReaders();
				librarian.getTee().writelnInfos("SteamFriendsGameListsReader " + steamProfile.getId() + " interrupted during done");
				librarian.getTee().printStackTrace(e);
			} catch (CancellationException e) {
				cancelSteamFriendGameListReaders();
				librarian.getTee().writelnInfos("SteamFriendsGameListsReader " + steamProfile.getId() + " cancelled during done");
				cancelSteamFriendGameListReaders();
			} catch (ExecutionException e) {
				cancelSteamFriendGameListReaders();
				librarian.getTee().writelnInfos("SteamFriendsGameListsReader " + steamProfile.getId() + " execution exception during done");
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
