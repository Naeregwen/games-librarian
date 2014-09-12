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
import components.Librarian;
import components.containers.remotes.SteamFriendWithSameGameButton;
import components.workers.SteamFriendGameStatsReader;

/**
 * @author Naeregwen
 *
 */
public class SteamFriendsGameStatsReader extends SwingWorker<Boolean, String> {

	Librarian librarian;
	List<SteamFriendWithSameGameButton> friendWithSameGameButtons;
	SteamGame game;
	
	List<SteamFriendGameStatsReader> steamFriendGameStatsReaders;
	
	public SteamFriendsGameStatsReader(Librarian librarian, List<SteamFriendWithSameGameButton> friendWithSameGameButtons, SteamGame game) {
		this.librarian = librarian;
		this.friendWithSameGameButtons = friendWithSameGameButtons;
		this.game = game;
		steamFriendGameStatsReaders = new Vector<SteamFriendGameStatsReader>();
	}

	private void cancelSteamFriendGameStatsReaders() {
		for (SteamFriendGameStatsReader steamFriendGameStatsReader : steamFriendGameStatsReaders) {
			if (!steamFriendGameStatsReader.isDone() && !steamFriendGameStatsReader.isCancelled())
				if (steamFriendGameStatsReader.cancel(true))
					librarian.getTee().writelnInfos("steamFriendGameStatsReader " + game.getName() + " cancelled");
				else
					librarian.getTee().writelnError("Can not cancel steamFriendGameStatsReader");
			else
				librarian.getTee().writelnInfos("steamFriendGameStatsReader " + game.getName() + " already done/cancelled");
		}
	}
	
	private void clearProgressIndicators() {
		librarian.setSteamGameStatsReading(false);
		librarian.updateGameTabTitle();
	}
	
	@Override
	protected Boolean doInBackground() throws Exception {
		
		if (game == null || friendWithSameGameButtons == null) return null;
		
    	try {
    		
	    	int initialPosition = 1;
			CountDownLatch doneSignal = new CountDownLatch(friendWithSameGameButtons.size());
			
			for (SteamFriendWithSameGameButton friendWithSameGameButton : friendWithSameGameButtons) {
				
//				try {
//    				Thread.sleep((long)(Math.random() * 100) + 50); // Add some delay between requests
//				} catch (InterruptedException e) {
//					cancelSteamFriendGameStatsReaders();
//					librarian.getTee().writelnInfos("SteamFriendsListReader interrupted during doInBackground sleep");
//					break;
//				}
				
				SteamFriendGameStatsReader steamFriendGameStatsReader = new SteamFriendGameStatsReader(librarian, friendWithSameGameButton.getSteamProfile(), friendWithSameGameButton.getIcon(), game, initialPosition++, doneSignal);
				steamFriendGameStatsReaders.add(steamFriendGameStatsReader);
				steamFriendGameStatsReader.execute();
			}
			
			doneSignal.await();
			
		} catch (InterruptedException e) {
			cancelSteamFriendGameStatsReaders();
			publish("SteamFriendsGameStatsReader " + game.getName() + " interrupted during doInBackground");
		} catch (CancellationException e) {
			cancelSteamFriendGameStatsReaders();
			publish("SteamFriendsGameStatsReader " + game.getName() + " cancelled during doInBackground");
		}
		return true;
	}

	/*/
	 * (non-Javadoc)
	 * @see javax.swing.SwingWorker#done()
	 */
	@Override
	protected void done() {
		if (isCancelled()) {
			cancelSteamFriendGameStatsReaders();
			librarian.getTee().writelnInfos("SteamFriendsGameStatsReader " + game.getName() + " cancelled before done");
		} else {
			try {
				get();
				librarian.getView().translateLoadAllAchievements();
//				if (librarian.isLoadAllAchievements())
//					librarian.loadAllAchievements();
			} catch (InterruptedException e) {
				cancelSteamFriendGameStatsReaders();
				librarian.getTee().writelnInfos("SteamFriendsGameStatsReader " + game.getName() + " interrupted during done");
				librarian.getTee().printStackTrace(e);
			} catch (CancellationException e) {
				cancelSteamFriendGameStatsReaders();
				librarian.getTee().writelnInfos("SteamFriendsGameStatsReader " + game.getName() + " cancelled during done");
			} catch (ExecutionException e) {
				cancelSteamFriendGameStatsReaders();
				librarian.getTee().writelnInfos("SteamFriendsGameStatsReader " + game.getName() + " execution exception during done");
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
