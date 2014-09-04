/**
 * 
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

	@Override
	protected SteamGamesList doInBackground() throws Exception {
		if (steamProfile.getSteamID64() == null && steamProfile.getSteamID() == null) return null;
		SteamGamesList steamGamesList = null;
    	if (steamProfile.getSteamGames() != null && !steamProfile.getSteamGames().isEmpty()) {
    		steamGamesList = new SteamGamesList(steamProfile);
//    		librarian.setSteamFriendsListReading(true);
//    		librarian.updateProfileFriendsTabTitle();
    		
	    	publish("AllSteamGamesStatsReader starting");
	    	
    		CountDownLatch doneSignal = new CountDownLatch(steamProfile.getSteamGames().size());
    		int index = 1;
    		for (SteamGame game : steamProfile.getSteamGames()) {
    			
//    			try {
//    				Thread.sleep(100);
//    			} catch (CancellationException e) {
//    				cancelSteamFriendProfileReaders();
//    				librarian.getTee().writelnInfos("SteamFriendsListReader cancelled");
//    				break;
//    			} catch (InterruptedException e) {
//    				librarian.getTee().printStackTrace(e);
//				}

    			SteamGameStatsReader steamGameStatsReader = new SteamGameStatsReader(librarian, game, doneSignal);
    			steamGameStatsReaders.add(steamGameStatsReader);
    			steamGameStatsReader.execute();
    		}
			doneSignal.await();
			publish("AllSteamGamesStatsReader finishing");
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
//			cancelSteamFriendProfileReaders();
			librarian.getTee().writelnInfos("AllSteamGamesStatsReader " + steamProfile.getId() + " cancelled before done");
		} else {
			try {
				SteamGamesList steamGamesList = get();
				if (steamGamesList != null) {
					// Update libraryStatisticsPane
					librarian.updateLibraryStatisticsMainTab();
				}
			} catch (InterruptedException e) {
//				cancelSteamFriendProfileReaders();
				librarian.getTee().writelnInfos("AllSteamGamesStatsReader " + steamProfile.getId() + " interrupted during done");
				librarian.getTee().printStackTrace(e);
			} catch (CancellationException e) {
//				cancelSteamFriendProfileReaders();
				librarian.getTee().writelnInfos("AllSteamGamesStatsReader " + steamProfile.getId() + " cancelled during done");
			} catch (ExecutionException e) {
//				cancelSteamFriendProfileReaders();
				librarian.getTee().writelnInfos("AllSteamGamesStatsReader " + steamProfile.getId() + " execution exception during done");
				librarian.getTee().printStackTrace(e);
			}
		}
//		clearProgressIndicators();
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
