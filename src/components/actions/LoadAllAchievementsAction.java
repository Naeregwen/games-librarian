/**
 * 
 */
package components.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import commons.BundleManager;
import commons.GamesLibrary;
import commons.enums.ButtonsDisplayMode;
import components.GamesLibrarian.WindowBuilderMask;
import components.actions.interfaces.IconAndTextAction;
import components.Librarian;

/**
 * @author Naeregwen
 *
 */
public class LoadAllAchievementsAction extends AbstractAction implements IconAndTextAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2497042861748420942L;

	WindowBuilderMask me;
	Librarian librarian;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public LoadAllAchievementsAction(WindowBuilderMask me) {
		this.me = me;
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
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

		int friendsWithSameGame = librarian != null ? librarian.getFriendsWithSameGame() : 0; // WindowBuilder
		int achievementsLoaded = librarian != null ? librarian.achievementsLoaded() : -1; // WindowBuilder
		
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
	
	@Override
	public String getLabelKey() {
		
		if (librarian.getParameters().getButtonsDisplayMode().equals(ButtonsDisplayMode.Icon))
			return null;
		
		int friendsWithSameGame = librarian != null ? librarian.getFriendsWithSameGame() : 0; // WindowBuilder
		int achievementsLoaded = librarian != null ? librarian.achievementsLoaded() : -1; // WindowBuilder
		
		return achievementsLoaded == friendsWithSameGame ? "unloadAllAchievementsMenuLabel" : "loadAllAchievementsMenuLabel";
	}

	@Override
	public ImageIcon getIcon() {
		
		if (librarian.getParameters().getButtonsDisplayMode().equals(ButtonsDisplayMode.Text))
			return null;
		
		int friendsWithSameGame = librarian != null ? librarian.getFriendsWithSameGame() : 0; // WindowBuilder
		int achievementsLoaded = librarian != null ? librarian.achievementsLoaded() : -1; // WindowBuilder
		
		if (achievementsLoaded == -1)
			return GamesLibrary.unableToloadAllAchievements;
		else if (achievementsLoaded == friendsWithSameGame)
			return GamesLibrary.unloadAllAchievements;
		else
			return GamesLibrary.loadAllAchievements;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		me.getLibrarian().loadAllAchievements();
	}

}
