/**
 * 
 */
package components.tables.listeners;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;


/**
 * @author Naeregwen
 *
 */
public class GamesLibrarianTabChangeListener implements ChangeListener {

	Librarian librarian;
	JTabbedPane mainTabbedPane;
	
	public GamesLibrarianTabChangeListener(WindowBuilderMask me, JTabbedPane mainTabbedPane) {
		super();
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		this.mainTabbedPane = mainTabbedPane;
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	@Override
	public void stateChanged(ChangeEvent event) {
		// Check if selected Tab has onChange action
//        int selectedIndex = ((JTabbedPane) event.getSource()).getSelectedIndex();
//        int profileTabIndex = librarian.getTabComponentIndexByName(mainTabbedPane, "profileTab");
//        if (profileTabIndex == selectedIndex && librarian.getCurrentSteamProfile() == null && !librarian.isSteamProfileReading()) {
//        	librarian.readSteamProfile();
//        }
	}

}
