package components.buttons;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import commons.GamesLibrary;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;

/**
 * @author Naeregwen
 *
 */
public class NextSteamProfileButton extends JButton implements ActionListener {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 532887720748313456L;

	Librarian librarian;
	
	/**
	 * Create a NextSteamProfileButton
	 */
	public NextSteamProfileButton(WindowBuilderMask me) {
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		setIcon(GamesLibrary.nextIcon);
		setMargin(new Insets(0, 0, 0, 0));
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		librarian.setNextSteamProfile();
	}

}
