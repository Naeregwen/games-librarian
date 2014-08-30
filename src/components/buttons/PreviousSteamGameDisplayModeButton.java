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
public class PreviousSteamGameDisplayModeButton extends JButton implements ActionListener {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8996040866031301953L;

	Librarian librarian;
	
	/**
	 * Create a PreviousSteamGameDisplayModeButton
	 */
	public PreviousSteamGameDisplayModeButton(WindowBuilderMask me) {
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		setIcon(GamesLibrary.previousIcon);
		setMargin(new Insets(0, 0, 0, 0));
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		librarian.setPreviousSteamGameDisplayMode();
	}

}
