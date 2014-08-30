/**
 * 
 */
package components.buttons;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import commons.GamesLibrary;
import components.Librarian;
import components.GamesLibrarian.WindowBuilderMask;

/**
 * @author Naeregwen
 *
 */
public class NextSteamAchievementsColumnsSortMethodButton extends JButton implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2514261410088200839L;

	Librarian librarian;
	
	/**
	 * Create a NextSteamAchievementsColumnsSortMethodButton
	 */
	public NextSteamAchievementsColumnsSortMethodButton(WindowBuilderMask me) {
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		setIcon(GamesLibrary.nextIcon);
		setMargin(new Insets(0, 0, 0, 0));
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		librarian.setNextSteamAchievementsColumnsSortMethod();
	}

}
