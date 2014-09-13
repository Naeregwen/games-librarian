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
package components.containers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JTextField;

import commons.api.Parameters;
import commons.api.SteamGame;
import components.Librarian;
import components.GamesLibrarian.WindowBuilderMask;
import components.comboboxes.SteamLaunchMethodComboBox;
import components.containers.remotes.LaunchButton;

/**
 * @author Naeregwen
 *
 */
public class GameLauncher extends JPanel implements MouseListener {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7953530352649400260L;
	
	Librarian librarian;
	LaunchButton launchButton;
	SteamLaunchMethodComboBox steamLaunchMethodComboBox;
	JTextField arguments;
	
	
	public GameLauncher(WindowBuilderMask me, LaunchButton launchButton, SteamLaunchMethodComboBox steamLaunchMethodComboBox, JTextField arguments) {
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		this.launchButton = launchButton;
		this.steamLaunchMethodComboBox = steamLaunchMethodComboBox;
		this.arguments = arguments;
		addMouseListener(this);
	}

	/**
	 * @return the launchButton
	 */
	public LaunchButton getLaunchButton() {
		return launchButton;
	}

	/**
	 * @return the steamLaunchMethodComboBox
	 */
	public SteamLaunchMethodComboBox getSteamLaunchMethodComboBox() {
		return steamLaunchMethodComboBox;
	}

	public void setGame(SteamGame game) {
		Parameters parameters = librarian.getParameters();
		launchButton.setGame(game);
		steamLaunchMethodComboBox.setSelectedItem(game != null && game.getSteamLaunchMethod() != null ? game.getSteamLaunchMethod() : parameters.getDefaultSteamLaunchMethod());
		arguments.setText(game == null || game.getArguments() == null ? "" : game.getArguments());
	}

	public SteamGame getGame() {
		return launchButton != null ? launchButton.getGame() : null;
	}
	
	public String getArguments() {
		return this.arguments.getText();
	}
	
	public String getGameArguments() {
		return launchButton != null && launchButton.getGame() != null ? launchButton.getGame().getArguments() : null;
	}
	
	public void setGameArguments(String arguments) {
		if (launchButton != null && launchButton.getGame() != null)
			launchButton.getGame().setArguments(arguments);
	}
	
	/**
	 * Update LaunchButton Tooltip
	 */
	public void updateTooltip() {
		if (launchButton != null)
			launchButton.updateTooltip();
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
		librarian.enterGame(launchButton.getGame());
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		librarian.leaveGame(launchButton.getGame());
	}

}
