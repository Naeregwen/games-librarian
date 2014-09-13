/**
 * Copyright 2012-2014 Naeregwen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
