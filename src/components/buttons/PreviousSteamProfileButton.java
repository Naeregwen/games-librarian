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
public class PreviousSteamProfileButton extends JButton implements ActionListener {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3656298746430490188L;

	Librarian librarian;
	
	/**
	 * Create a PreviousSteamProfileButton
	 */
	public PreviousSteamProfileButton(WindowBuilderMask me) {
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		setIcon(GamesLibrary.previousIcon);
		setMargin(new Insets(0, 0, 0, 0));
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		librarian.setPreviousSteamProfile();
	}

}
