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

import java.util.List;
import java.util.Vector;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import commons.ColoredTee;
import commons.api.SteamGame;
import commons.api.SteamGamesList;
import commons.api.SteamProfile;
import components.Librarian;
import components.workers.SteamGameStatsReader;

/**
 * @author Naeregwen
 *
 */
public class AllSteamGamesStatsReader extends SwingWorker<SteamGamesList, String> {

	Librarian librarian;
	SteamProfile steamProfile;
	List<SteamGameStatsReader> steamGameStatsReaders;
	
	public AllSteamGamesStatsReader(Librarian librarian, SteamProfile steamProfile) {
		this.librarian = librarian;
		this.steamProfile = steamProfile;
		this.steamGameStatsReaders = new Vector<SteamGameStatsReader>();
	}

	private void cancelSteamGameStatsReaders() {
		for (SteamGameStatsReader steamGameStatsReader : steamGameStatsReaders) {
			if (!steamGameStatsReader.isDone() && !steamGameStatsReader.isCancelled())
				if (steamGameStatsReader.cancel(true))
					librarian.getTee().writelnInfos("steamGameStatsReader " + steamGameStatsReader.getGame().getName() + " cancelled");
				else
					librarian.getTee().writelnError("Can not cancel steamGameStatsReader");
			else
				librarian.getTee().writelnInfos("steamGameStatsReader " + steamGameStatsReader.getGame().getName() + " already done/cancelled");
		}
	}
	
	private void clearProgressIndicators() {
		librarian.setAllSteamGamesStatsReading(false);
		librarian.updateLibraryMainTabTitle();
	}
	
	@Override
	protected SteamGamesList doInBackground() throws Exception {
		
		if (steamProfile.getSteamID64() == null && steamProfile.getSteamID() == null) return null;
		
		SteamGamesList steamGamesList = null;
		
    	try {
    		
	    	if (steamProfile.getSteamGames() != null && !steamProfile.getSteamGames().isEmpty()) {
	    		
	    		steamGamesList = new SteamGamesList(steamProfile);
	    		
	    		// Must know this number before constructing a CountDownLatch
	    		int gamesWithStats = 0;
	    		for (SteamGame game : steamProfile.getSteamGames())
	    			if (game.getStatsLink() != null)
	    				gamesWithStats++;
	    		
	    		CountDownLatch doneSignal = new CountDownLatch(gamesWithStats);
	    		int index = 1;
	    		
	    		for (SteamGame game : steamProfile.getSteamGames()) {
	    			
	    			if (game.getStatsLink() == null) continue;
	    			
	    			try {
	    				Thread.sleep((long)(Math.random() * 100) + 50); // Add some delay between requests
	    			} catch (InterruptedException e) {
	    				librarian.getTee().writelnInfos("AllSteamGamesStatsReader interrupted during doInBackground sleep");
	    				break;
					}
	
	    			SteamGameStatsReader steamGameStatsReader = new SteamGameStatsReader(librarian, game, doneSignal, index++);
	    			steamGameStatsReaders.add(steamGameStatsReader);
	    			steamGameStatsReader.execute();
	    		}
	    		
				doneSignal.await();
	    	}
		} catch (InterruptedException e) {
			publish("AllSteamGamesStatsReader " + steamProfile.getId() + " interrupted during doInBackground");
		} catch (CancellationException e) {
			publish("AllSteamGamesStatsReader " + steamProfile.getId() + " cancelled during doInBackground");
		}
    	return steamGamesList;
	}

	/*/
	 * (non-Javadoc)
	 * @see javax.swing.SwingWorker#done()
	 */
	@Override
	protected void done() {
		if (isCancelled()) {
			cancelSteamGameStatsReaders();
			librarian.getTee().writelnInfos("AllSteamGamesStatsReader " + steamProfile.getId() + " cancelled before done");
		} else {
			try {
				SteamGamesList steamGamesList = get();
				if (steamGamesList != null) {
					// Update libraryStatisticsPane
					librarian.updateLibraryStatisticsMainTab();
				}
			} catch (InterruptedException e) {
				cancelSteamGameStatsReaders();
				librarian.getTee().writelnInfos("AllSteamGamesStatsReader " + steamProfile.getId() + " interrupted during done");
				librarian.getTee().printStackTrace(e);
			} catch (ExecutionException e) {
				cancelSteamGameStatsReaders();
				librarian.getTee().writelnInfos("AllSteamGamesStatsReader " + steamProfile.getId() + " execution exception during done");
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
