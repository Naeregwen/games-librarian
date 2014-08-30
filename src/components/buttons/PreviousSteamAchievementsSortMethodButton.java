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
public class PreviousSteamAchievementsSortMethodButton extends JButton implements ActionListener {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3101143652520440976L;

	Librarian librarian;
	
	/**
	 * Create a PreviousSteamAchievementsSortMethodButton
	 */
	public PreviousSteamAchievementsSortMethodButton(WindowBuilderMask me) {
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		setIcon(GamesLibrary.previousIcon);
		setMargin(new Insets(0, 0, 0, 0));
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		librarian.setPreviousSteamAchievementsSortMethod();
	}

}
