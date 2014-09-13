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
package components.comboboxes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComboBox;
import javax.swing.ListCellRenderer;

import commons.BundleManager;
import commons.api.Parameters;
import commons.api.SteamLaunchMethod;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.comboboxes.renderers.enums.GamesLibrarianActionEnumCellRenderer;
import components.containers.remotes.LaunchButton;

/**
 * @author Naeregwen
 *
 */
public class SteamLaunchMethodComboBox extends JComboBox<SteamLaunchMethod> implements ActionListener, MouseListener {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7505492166723454021L;

	public enum Type {
		GameLauncher,
		LibraryList,
		DefaultMethod
	}
	
	WindowBuilderMask me;
	Librarian librarian;
	LaunchButton launchButton;
	Type type;
	
	@SuppressWarnings("unchecked")
	public SteamLaunchMethodComboBox(WindowBuilderMask me, LaunchButton launchButton, Type type) {
		super(SteamLaunchMethod.values());
		this.me = me;
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		this.launchButton = launchButton;
		this.type = type;
		setRenderer(new GamesLibrarianActionEnumCellRenderer(me, (ListCellRenderer<SteamLaunchMethod>) this.getRenderer()));
		setMaximumRowCount(SteamLaunchMethod.values().length);
		addActionListener(this);
		addMouseListener(this);
	}

	/**
	 * @return the launchButton
	 */
	public LaunchButton getLaunchButton() {
		return launchButton;
	}

	/**
	 * @param launchButton the launchButton to set
	 */
	public void setLaunchButton(LaunchButton launchButton) {
		this.launchButton = launchButton;
	}

	/*/
	 * (non-Javadoc)
	 * @see javax.swing.JComboBox#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		
		Parameters parameters = librarian.getParameters();
		SteamLaunchMethod steamLaunchMethod = (SteamLaunchMethod) this.getSelectedItem();
		// Options DefaultSteamLaunchMethod
		if (launchButton == null) {
			if (type.equals(Type.DefaultMethod)) {
				if (!parameters.getDefaultSteamLaunchMethod().equals(steamLaunchMethod)) {
					parameters.setDefaultSteamLaunchMethod(steamLaunchMethod);
					librarian.getTee().writelnMessage(BundleManager.getMessages(me, steamLaunchMethod.getDefaultSelectionMessageKey()));
				}
			} 
		// Buttons SteamLaunchMethod
		} else {
			if (launchButton.getGame() == null || launchButton.getGame().getSteamLaunchMethod().equals(steamLaunchMethod)) return;
			launchButton.setSteamLaunchMethod(steamLaunchMethod);
			switch (type) {
			case LibraryList:
				librarian.updateGameLauncher(launchButton);
				break;
			case GameLauncher:
				librarian.updateLibraryTooltip(launchButton);
				break;
			default:
				break;
			}
			if (parameters.isDebug()) {
				librarian.getTee().writelnMessage(String.format(BundleManager.getMessages(me, steamLaunchMethod.getSelectionMessageKey()), launchButton.getGame().getName()));
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (launchButton != null)
			librarian.enterGame(launchButton.getGame());
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (launchButton != null)
			librarian.leaveGame(launchButton.getGame());
	}
	
}
