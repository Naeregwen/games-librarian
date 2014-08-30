/**
 * 
 */
package components.actions.adapters;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import components.GamesLibrarian.WindowBuilderMask;

/**
 * @author Naeregwen
 *
 */
public class LibrarianCloser extends WindowAdapter {

	WindowBuilderMask me;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public LibrarianCloser(WindowBuilderMask me) {
		this.me = me;
	}

	public void windowClosing(WindowEvent we) {
		me.getLibrarian().cleanup();
	}
}
