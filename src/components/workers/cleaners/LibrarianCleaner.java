/**
 * 
 */
package components.workers.cleaners;

import components.GamesLibrarian.WindowBuilderMask;

/**
 * @author Naeregwen
 *
 */
public class LibrarianCleaner extends Thread {
	
	WindowBuilderMask me;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public LibrarianCleaner(WindowBuilderMask me) {
		this.me = me;
	}

	public void run() {}
}
