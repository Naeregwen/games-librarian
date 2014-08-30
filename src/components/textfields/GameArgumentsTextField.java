/**
 * 
 */
package components.textfields;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;

import components.Librarian;
import components.GamesLibrarian.WindowBuilderMask;
import components.containers.remotes.LaunchButton;


/**
 * @author Naeregwen
 *
 */
public class GameArgumentsTextField extends JTextField implements MouseListener {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6326520602558561598L;
	
	Librarian librarian;
	private LaunchButton launchButton;
	
	public GameArgumentsTextField(WindowBuilderMask me, LaunchButton launchButton) {
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		this.launchButton = launchButton; 
		addMouseListener(this);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		if (launchButton != null)
			librarian.enterGame(launchButton.getGame());
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		if (launchButton != null)
			librarian.leaveGame(launchButton.getGame());
	}

}
