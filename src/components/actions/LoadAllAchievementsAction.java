/**
 * 
 */
package components.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import commons.BundleManager;
import commons.GamesLibrary;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;

/**
 * @author Naeregwen
 *
 */
public class LoadAllAchievementsAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2497042861748420942L;

	WindowBuilderMask me;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public LoadAllAchievementsAction(WindowBuilderMask me) {
		this.me = me;
		translate();
	}

	/**
	 * Translate using BundleManager
	 */
	public void translate() {
		// Defensive code to avoid NullPointerException in WindowBuilder when data are empty in bundle (Mnemonic and accelerator are not mandatory)
		if (BundleManager.getUITexts(me, "loadAllAchievementsMnemonic") != null && !BundleManager.getUITexts(me, "loadAllAchievementsMnemonic").equals("")) // WindowBuilder
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "loadAllAchievementsMnemonic")).getKeyCode());
		if (BundleManager.getUITexts(me, "loadAllAchievementsAccelerator") != null && !BundleManager.getUITexts(me, "loadAllAchievementsAccelerator").equals("")) // WindowBuilder
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "loadAllAchievementsAccelerator")));

		Librarian librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		int friendsWithSameGame = librarian != null ? librarian.getFriendsWithSameGame() : 0;
		int achievementsLoaded = librarian != null ? librarian.achievementsLoaded() : -1;
		
		putValue(NAME, BundleManager.getUITexts(me, achievementsLoaded == friendsWithSameGame ? "unloadAllAchievementsMenuLabel" : "loadAllAchievementsMenuLabel"));
		
		if (librarian == null || librarian.getCurrentSteamGame() == null) {
			putValue(SHORT_DESCRIPTION, BundleManager.getUITexts(me, "loadAllAchievementsTooltipNoGameSelected"));
			putValue(SMALL_ICON, GamesLibrary.unableToloadAllAchievements);
		} else
			if (achievementsLoaded == -1) {
				putValue(SHORT_DESCRIPTION, BundleManager.getUITexts(me, "loadAllAchievementsTooltipNoFriendsWithSameGame"));
				putValue(SMALL_ICON, GamesLibrary.unableToloadAllAchievements);
			} else if (achievementsLoaded == friendsWithSameGame) {
				putValue(SHORT_DESCRIPTION, BundleManager.getUITexts(me, "unloadAllAchievementsTooltip"));
				putValue(SMALL_ICON, GamesLibrary.unloadAllAchievements);
			} else {
				putValue(SHORT_DESCRIPTION, BundleManager.getUITexts(me, "loadAllAchievementsTooltip"));
				putValue(SMALL_ICON, GamesLibrary.loadAllAchievements);
			}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		me.getLibrarian().loadAllAchievements();
	}

}
