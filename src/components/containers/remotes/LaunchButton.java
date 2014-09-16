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
package components.containers.remotes;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JToolTip;
import javax.swing.border.LineBorder;

import commons.ColoredTee;
import commons.GamesLibrary;
import commons.OS;
import commons.api.Parameters;
import commons.api.Steam;
import commons.api.SteamGame;
import commons.api.SteamLaunchMethod;
import commons.enums.GameChoice;
import commons.enums.GameLeftClickAction;
import commons.enums.LaunchType;
import commons.windows.WinProcesses;
import components.GamesLibrarian;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.commons.ImageToolTip;
import components.commons.adapters.LaunchButtonMouseAdapter;
import components.commons.adapters.SteamObjectsMouseAdapter;
import components.commons.interfaces.Translatable;
import components.commons.ui.ImageToolTipUIHelper;
import components.containers.BoundedButton;
import components.containers.commons.RemoteIconButton;
import components.workers.RemoteIconReader;

/**
 * @author Naeregwen
 *
 */
public class LaunchButton extends BoundedButton implements Translatable, RemoteIconButton, ImageToolTipUIHelper, ActionListener {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2067082870581001110L;
	
	Librarian librarian;
	
	private LaunchType launchType;
	private GameChoice gameChoice;
	private SteamGame game;
	
	/**
	 * Create a LaunchButton
	 * 
	 * @param label the label to set
	 * @param tee the tee to set
	 * @param application the application to set
	 * @param gameChoice the gameChoice to set
	 */
	public LaunchButton(WindowBuilderMask me, String label, LaunchType launchType, GameChoice gameChoice, SteamGame game) {
		super(label, Steam.steamGameIconWidth, Steam.steamGameIconHeight);
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		if (librarian != null) // WindowBuilder
			librarian.addTranslatable(this);
		this.launchType = launchType;
		this.gameChoice = gameChoice;
		if (this.librarian != null) { // WindowBuilder
			updateIconAndTooltip();
		} else {
			if (game == null)
				setIconNoGameSelected();
			else
				setIconGameImageUnavailable();
		}
		addActionListener(this);
		addMouseListener(new LaunchButtonMouseAdapter(me, this));
		addMouseListener(new SteamObjectsMouseAdapter());
		if (game != null) setGame(game);
	}
	
	/*/
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#createToolTip()
	 */
	@Override
	public JToolTip createToolTip() {
		JToolTip tooltip = new ImageToolTip((ImageIcon) getIcon(), this);
		tooltip.setBorder(new LineBorder(Color.BLACK));
		return tooltip;
    }

	/**
	 * @return the gameChoice
	 */
	public GameChoice getGameChoice() {
		return gameChoice;
	}

	/**
	 * @param steamLaunchMethod the game steamLaunchMethod to set
	 */
	public void setSteamLaunchMethod(SteamLaunchMethod steamLaunchMethod) {
		if (game != null) {
			game.setSteamLaunchMethod(steamLaunchMethod);
			updateTooltip();
		}
	}
	
	/**
	 * @return the game steamLaunchMethod
	 */
	public SteamLaunchMethod getSteamLaunchMethod() {
		return game.getSteamLaunchMethod();
	}
	
	/**
	 * Set icon to "noGameSelected"
	 */
	public void setIconNoGameSelected() {
		ImageIcon icon = GamesLibrary.noGameSelectedIcon;
		if (icon != null && icon.getImage() != null && icon.getIconWidth() > 0 && icon.getIconHeight() > 0)
			super.setIcon(icon);
	}
	
	/**
	 * Set icon to "gameImageUnavailable"
	 */
	private void setIconGameImageUnavailable() {
		ImageIcon icon = GamesLibrary.gameImageUnavailableIcon;
		if (icon != null && icon.getImage() != null && icon.getIconWidth() > 0 && icon.getIconHeight() > 0)
			super.setIcon(icon);
	}

	/**
	 * @param game the game to set
	 */
	public void setGame(SteamGame game) {
		
		this.game = game;
		setIconGameImageUnavailable();
		
		Parameters parameters = librarian.getParameters();
		
		String message;
		
		if (game != null) {
			// New game, set button properties
			librarian.getGameNameTextField().setText(game.getName());
			// Check game image availability
			if (getIconURL() != null && !getIconURL().trim().equals("")) {
				(new RemoteIconReader(librarian.getTee(), this)).execute();
			} else {
				// Button image will be set with empty image in translate
				message = String.format(parameters.getMessages().getString("gameImageUnavailable"), (gameChoice != null ? gameChoice.ordinal() + 1 : 0), game.getName() != null ? game.getName() : "");
				librarian.getTee().writelnError(message);
			}
		} else {
			// Reset button properties
			librarian.getGameNameTextField().setText("");
			// Clear image and tooltip
			updateIconAndTooltip();
		}
		
	}
	
	/**
	 * @return the game
	 */
	public SteamGame getGame() {
		return game;
	}

	/**
	 * @return the HoursOnRecord ToolTipText
	 */
	private String getHoursOnRecordToolTipText() {
		Parameters parameters = librarian.getParameters();
		return String.format(parameters.getUITexts().getString("launchButtonHoursOnRecordToolTip"), (game.getHoursOnRecord() != null ? game.getHoursOnRecord() : "0"));
	} 
	
	/**
	 * @return the HoursLast2Weeks ToolTipText
	 */
	private String getHoursLast2WeeksToolTipText() {
		Parameters parameters = librarian.getParameters();
		return String.format(parameters.getUITexts().getString("launchButtonHoursLast2WeeksToolTip"), (game.getHoursLast2Weeks() != null ? game.getHoursLast2Weeks() : "0"));
	} 
	
	/**
	 * launch a game with Steam Protocol
	 * 
	 * @param game the game to launch
	 * @param tee the tee to write errors/messages
	 * @return true if no execption
	 */
	private boolean launchGameWithSteamProtocol(SteamGame game, ColoredTee tee) {
		try {
			Steam.launchGameWithSteamProtocol(game);
		} catch (IOException e) {
			tee.printStackTrace(e);
			return false;
		} catch (URISyntaxException e) {
			tee.printStackTrace(e);
			return false;
		}
		return true;
	}
	
	/**
	 * launch a game with Steam Executable
	 * 
	 * @param game the game to launch
	 * @param tee the tee to write errors/messages
	 * @return true if no exception
	 */
	private boolean launchGameWithSteamExecutable(SteamGame game, ColoredTee tee) {

		Parameters parameters = librarian.getParameters();
		String message;
		
		JTextField steamExecutableTextField = librarian.getSteamExecutableTextField();
		if (steamExecutableTextField == null) {
			message = String.format(parameters.getMessages().getString("errorLaunchButtonSteamExecutableTextFieldIsNotDefined"), (gameChoice.ordinal() + 1), game.getName());
			tee.writelnError(message);
			return false;
		}
		
		// Check if steam executable location is defined
		if (steamExecutableTextField.getText() == null|| steamExecutableTextField.getText().trim().length() == 0) {
			message = String.format(parameters.getMessages().getString("errorLaunchButtonSteamExecutableIsNotDefined"), (gameChoice.ordinal() + 1), game.getName());
			tee.writelnError(message);
			return false;
		}
		
		String steamExecutable = steamExecutableTextField.getText().trim();
		
		// Check if Steam Executable file exist && is normal
		File file = new File(steamExecutable);
		if (!file.exists() || !file.isFile()) {
			message = String.format(parameters.getMessages().getString("errorLaunchButtonSteamExecutableIsNotNormal"), (gameChoice.ordinal() + 1), game.getName());
			tee.writelnError(message);
			return false;
		}
		
		// Check if Steam Executable file canRead && canExecute
		if (!file.canRead() || !file.canExecute()) {
			message = String.format(parameters.getMessages().getString("errorLaunchButtonSteamExecutableIsNotExecutable"), (gameChoice.ordinal() + 1), game.getName());
			tee.writelnError(message);
			return false;
		}
		
		// Check if steam process is running on Windows
		boolean steamProcessFound = false;
		if (librarian.getParameters().getOs().getPrefix() == OS.Prefix.Win) {
			WinProcesses winProcesses = new WinProcesses(tee);
			List<String> processesList = winProcesses.listRunningProcesses();
			for (String process : processesList)
				if (process.equalsIgnoreCase("steam.exe")) {
					steamProcessFound = true;
				}
		}
		if (!steamProcessFound) {
			message = parameters.getMessages().getString("errorLaunchButtonNoSteamProcessFound");
			tee.writelnError(message);
			return false;
		}
		
		try {
			Steam.launchGameWithSteamExecutable(game, steamExecutable);
		} catch (IOException e) {
			tee.printStackTrace(e);
			return false;
		}
		return true;
	}

	/**
	 * Update Tooltip
	 * @see javax.swing.text.html.HTMLEditorKit
	 * @see javax.swing.text.html.HTMLEditorKit.HTMLFactory
	 * @see javax.swing.text.html.HTML.Attribute
	 * @see javax.swing.text.html.CSS
	 * @see javax.swing.text.html.CSS.Attribute
	 */
	public void updateTooltip() {
		
		Parameters parameters = librarian.getParameters();
		String htmlStart = parameters.getUITexts().getString("launchButtonStartToolTip");
		String htmlStop = parameters.getUITexts().getString("launchButtonStopToolTip");
		String tooltipTitle = parameters.getUITexts().getString("launchButtonTitleToolTip");
		
		if (game == null) {
			setToolTipText(htmlStart + parameters.getUITexts().getString("launchButtonErrorToolTip") + htmlStop);
		} else {
			if (game.getName() != null) {
				String gameTitle = String.format(parameters.getUITexts().getString("launchButtonGameTitleToolTip"), game.getName());
				String steamLaunchMethod = parameters.getUITexts().getString(SteamLaunchMethod.values()[game.getSteamLaunchMethod().ordinal()].getLabelKey());
				URL url = GamesLibrarian.class.getResource(game.getSteamLaunchMethod().getIconPath());
				setToolTipText(
						htmlStart +
						// Do it old school (HTML 3.2 with Swing HTMLEditorKit)
						// Table style seems to be the only way to vertically align an image and a text with swing HTMLEditorKit
						// 3px is the padding from javax.swing.plaf.basic.BasicToolTipUI.paint(Graphics g, JComponent c)
						"<table style='padding: 0px; margin: 3px 0px 0px 0px; border: 1px solid black'>" +
						"<tr style='margin: 0px; padding: 0px'>" + 
						"<td style='margin: 0px; padding: 0px'><img width='16px' height='16px' src='" + url + "'></td>" +
						"<td style='margin: 0px; padding: 0px'><div style='font-weight: bold'>" + steamLaunchMethod + "</div></td>" +
						"</tr>" +
						"</table>" +
						"<h1 style='text-align:center; vertical-align: super'>" + gameTitle + "</h1>" +
						getHoursOnRecordToolTipText() + 
						"<br/>" + 
						getHoursLast2WeeksToolTipText() + 
						htmlStop);
			} else
				setToolTipText(htmlStart + tooltipTitle + htmlStop);
		}
	}
	
	/**
	 * Update Icon And Tooltip
	 * Translate UI elements
	 */
	public void updateIconAndTooltip() {
		if (game == null)
			setIconNoGameSelected();
		else
			if (((ImageIcon)getIcon()).getImage() == null)
				setIconGameImageUnavailable();
		updateTooltip();
	}
	
	/**
	 * Launch associated game
	 */
	private void launchGame() {
		
		Parameters parameters = librarian.getParameters();
		String message;
		
		// No game, no honey
		if (game == null || game.getAppID() == null || game.getAppID().trim().equals("")) {
			message = String.format(parameters.getMessages().getString("errorLaunchButtonGameNotRolled"), gameChoice.ordinal() + 1);
			librarian.getTee().writelnError(message);
			return;
		}

		// Check if Steam Community is reachable
		if (!librarian.isSteamCommunityReachable(Librarian.DisplayMode.silent)) {
			String steamStatusLine = Steam.responseStatusLine != null ? Steam.responseStatusLine.toString() : null;
			String responseErrorCause = (Steam.responseErrorCause != null && !Steam.responseErrorCause.equals("")) ? Steam.responseErrorCause : "";
			message = String.format(parameters.getMessages().getString("steamCommunityIsDown"), steamStatusLine != null ? steamStatusLine : responseErrorCause);
			String question = parameters.getUITexts().getString("errorSteamCommunityIsDown");
			int option = JOptionPane.showOptionDialog(librarian.getView(), message + question, librarian.getApplicationTitle(), JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
			if (option == JOptionPane.NO_OPTION)
				return;
		}
		
		// Use selected SteamLaunchMethod
		SteamLaunchMethod steamLaunchMethod = game.getSteamLaunchMethod() != null ? game.getSteamLaunchMethod() : librarian.getParameters().getDefaultSteamLaunchMethod();
		message = String.format(parameters.getMessages().getString("infosLaunchButton"), 
				game.getName(), 
				game.getSteamLaunchMethod() == null ? " " + parameters.getMessages().getString("infosLaunchButtonDefaultMethod") + " " : "") 
					+ (steamLaunchMethod == SteamLaunchMethod.SteamProtocol ? "steam protocol" : "steam executable");
		librarian.getTee().writelnInfos(message);
		switch (steamLaunchMethod) {
		
		case SteamProtocol:
			
			if (!launchGameWithSteamProtocol(game, librarian.getTee())) {
				// Second try, with Steam Protocol
				message = String.format(parameters.getMessages().getString("errorLaunchButtonUnableToUseSteamProtocol"), gameChoice.ordinal() + 1);
				String optionMessage = message + parameters.getUITexts().getString("errorLaunchButtonUnableToUseSteamProtocol");
				librarian.getTee().writelnError(message);
				int option = JOptionPane.showOptionDialog(librarian.getView(), optionMessage, librarian.getApplicationTitle(), JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
				if (option == JOptionPane.YES_OPTION) {
					// Update parameters
					game.setSteamLaunchMethod(SteamLaunchMethod.SteamExecutable);
					// Update UI
					librarian.updateSteamLaunchMethod(this, game);
					// Launch game
					launchGameWithSteamExecutable(game, librarian.getTee());
				}
			}
			break;
			
		case SteamExecutable:
			
			if (!launchGameWithSteamExecutable(game, librarian.getTee())) {
				// Second try, with Steam Protocol
				message = String.format(parameters.getMessages().getString("errorLaunchButtonUnableToUseSteamExecutable"), gameChoice.ordinal() + 1);
				String optionMessage = message + parameters.getUITexts().getString("errorLaunchButtonUnableToUseSteamExecutable");
				librarian.getTee().writelnError(message);
				int option = JOptionPane.showOptionDialog(librarian.getView(), optionMessage, librarian.getApplicationTitle(), JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
				if (option == JOptionPane.YES_OPTION) {
					// Update parameters
					game.setSteamLaunchMethod(SteamLaunchMethod.SteamProtocol);
					// Update UI
					librarian.updateSteamLaunchMethod(this, game);
					// Launch game
					launchGameWithSteamProtocol(game, librarian.getTee());
				}
			}
			break;
		}
	}
	
	/**
	 * The associated game become the current game 
	 */
	private void selectGame() {
		if (game == null) return;
		librarian.updateGameTab(game);
	}

	/*/
	 * (non-Javadoc)
	 * @see components.containers.commons.RemoteIconButton#getIconURL()
	 */
	@Override
	public String getIconURL() {
		return game.getLogo();
	}

	/*/
	 * (non-Javadoc)
	 * @see components.commons.ui.ImageToolTipUIHelper#getImageIcon()
	 */
	@Override
	public Icon getImageIcon() {
		return super.getIcon();
	}

	/*/
	 * (non-Javadoc)
	 * @see components.commons.ui.ImageToolTipUIHelper#setImageIcon(javax.swing.ImageIcon)
	 */
	@Override
	public void setImageIcon(ImageIcon icon) {
		resizeAndSetIcon(icon);
		if (game != null) updateIconAndTooltip(); // WindowBuilder
	}

	/*/
	 * (non-Javadoc)
	 * @see components.containers.commons.RemoteIconButton#setIcon(javax.swing.ImageIcon)
	 */
	@Override
	public void setIcon(ImageIcon icon) {
		setImageIcon(icon);
	}

	/*/
	 * (non-Javadoc)
	 * @see components.containers.commons.RemoteIconButton#setAvatarIconAsResource()
	 */
	@Override
	public void setAvatarIconAsResource() {
		URL url = GamesLibrarian.class.getResource(getIconURL());
		if (url != null) {
			ImageIcon icon = new ImageIcon(url);
			if (icon != null && icon.getImage() != null && icon.getIconHeight() > 0 && icon.getIconWidth() > 0) {
				resizeAndSetIcon(icon);
				if (game != null) updateIconAndTooltip(); // WindowBuilder
			}
		}
	}

	/* (non-Javadoc)
	 * @see components.commons.interfaces.Translatable#translate()
	 */
	@Override
	public void translate() {
		updateIconAndTooltip();
	}

	/*/
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		
		switch (launchType) {
		case rolled :
		case library:
		case current:
			if (librarian.getParameters().getGameLeftClickAction().equals(GameLeftClickAction.Launch)) 
				launchGame();
			else
				selectGame();
			break;
		case mostPlayed:
			selectGame();
			break;
		}
	}

}
