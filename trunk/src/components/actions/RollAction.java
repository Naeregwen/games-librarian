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
package components.actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import commons.BundleManager;
import commons.GamesLibrary;
import commons.api.Parameters;
import commons.api.SteamGame;
import commons.enums.ButtonsDisplayMode;
import commons.enums.GameChoice;
import commons.enums.LibrarianTabEnum;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.actions.interfaces.IconAndTextAction;
import components.commons.interfaces.Translatable;
import components.containers.GameLauncher;

/**
 * @author Naeregwen
 *
 */
public class RollAction extends AbstractAction implements Translatable, IconAndTextAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8293594900473762837L;

	WindowBuilderMask me;
	Librarian librarian;
	
	private static final Random random = new Random();
	private static int randomAppId;
	
	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public RollAction(WindowBuilderMask me) {
		this.me = me;
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		if (librarian != null) // WindowBuilder
			librarian.addTranslatable(this);
		translate();
	}
	
	/* (non-Javadoc)
	 * @see components.commons.interfaces.Translatable#translate()
	 */
	@Override
	public void translate() {
		// Defensive code to avoid NullPointerException in WindowBuilder/Runtime when data are empty in bundle (Mnemonic and accelerator are not mandatory)
		if (BundleManager.getUITexts(me, "rollMnemonic") != null && !BundleManager.getUITexts(me, "rollMnemonic").equals("")) // WindowBuilder
			putValue(MNEMONIC_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "rollMnemonic")).getKeyCode());
		if (BundleManager.getUITexts(me, "rollAccelerator") != null && !BundleManager.getUITexts(me, "rollAccelerator").equals("")) // WindowBuilder
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "rollAccelerator")));
		putValue(NAME, BundleManager.getUITexts(me, "rollMenuLabel"));
		putValue(SMALL_ICON, GamesLibrary.rollIcon);
		putValue(SHORT_DESCRIPTION, BundleManager.getUITexts(me, "rollToolTip"));
	}
	
	/* (non-Javadoc)
	 * @see components.actions.interfaces.IconAndTextAction#getLabelKey()
	 */
	@Override
	public String getLabelKey() {
		if (librarian.getParameters().getButtonsDisplayMode().equals(ButtonsDisplayMode.Icon))
			return null;
		return "rollMenuLabel";
	}

	/* (non-Javadoc)
	 * @see components.actions.interfaces.IconAndTextAction#getIcon()
	 */
	@Override
	public ImageIcon getIcon() {
		if (librarian.getParameters().getButtonsDisplayMode().equals(ButtonsDisplayMode.Text))
			return null;
		return GamesLibrary.rollIcon;
	}

	/**
	 * Choose between read or roll.
	 * If nothing to roll then read.
	 */
	private void readOrRoll() {
		Librarian librarian = me.getLibrarian();
		Parameters parameters = librarian.getParameters();
		if ((librarian.getCurrentSteamProfile().getSteamID64() != null || librarian.getCurrentSteamProfile().getSteamID() != null) 
				&& (parameters.getSteamGamesList() == null 
				|| parameters.getSteamGamesList().getSteamGames() == null
				|| parameters.getSteamGamesList().getSteamGames().size() <= 0))
			read();
		if (librarian.getCurrentSteamProfile().getSteamID64() == null && librarian.getCurrentSteamProfile().getSteamID() == null )
			librarian.rollError();
		else if (parameters.getSteamGamesList() != null && parameters.getSteamGamesList().getSteamGames() != null && parameters.getSteamGamesList().getSteamGames().size() > 0) 
			roll();
	}
	
	/**
	 * Read XML Steam gamesList from url
	 */
	private void read() {		
		me.getLibrarian().readSteamGamesList();		
	}
	
	/**
	 * Force read of Steam games list from internet data
	 */
	public void forceRead() {
		me.getLibrarian().getParameters().setSteamGamesList(null);
		readOrRoll();
	}
	
	/**
	 * Force roll of Steam games list from internal data
	 */
	public void forceRoll() {
		readOrRoll();
	}
	
	/**
	 * Compute the number of games that can be rolled
	 * Depends on :
	 * <ul>
	 * <li>number of games requested by user</li>
	 * <li>number of games available in user list</li>
	 * </ul>
	 * @return minimum number of roll allowed
	 */
	private int minSuffle() {
		Parameters parameters = me.getLibrarian().getParameters();
		Vector<SteamGame> list = parameters.getSteamGamesList().getSteamGames();
		int minSize = list.size() < 2 ? 0 : list.size() - 1;
		return parameters.getGameChoice().ordinal() + 1 < minSize ? parameters.getGameChoice().ordinal() + 1 : minSize;
	}
	
	/**
	 * Randomize only first elements of games list
	 * @return shuffled games list
	 */
	private Vector<SteamGame> shuffle() {
		Librarian librarian = me.getLibrarian();
		Parameters parameters = librarian.getParameters();
		Vector<SteamGame> oldList = parameters.getSteamGamesList().getSteamGames();
		if (oldList.size() < 2) return oldList;
		
		Vector<SteamGame> list = new Vector<SteamGame>(oldList);
		SteamGame game;
		int min = minSuffle();
		
		for (int i = 0; i < min; i++) {
			// Random from i to length
			randomAppId = random.nextInt(list.size() - i) + i;
			if (parameters.isDebug())
				librarian.getTee().writelnInfos("shuffle - i = " + i + ", randomAppId = " + randomAppId);
			// Swap values
			game = list.get(randomAppId);
			list.set(randomAppId, list.get(i));
			list.set(i, game);
		}
		
		return list;
	}

	/**
	 * @return the maximum number of LaunchButton that can be updated with a roll 
	 */
	private int maxButtonsToRoll() {
		Parameters parameters = me.getLibrarian().getParameters();
		Vector<SteamGame> list = parameters.getSteamGamesList().getSteamGames();
		int minSize = list.size() < 2 ? 1 : list.size();
		return parameters.getGameChoice().ordinal() + 1 < minSize ? parameters.getGameChoice().ordinal() + 1 : minSize;
	}
	
	/**
	 * Roll the games from user list
	 */
	private void roll() {
		Librarian librarian = me.getLibrarian();
		Parameters parameters = librarian.getParameters();
		if (parameters.getSteamGamesList() == null || parameters.getSteamGamesList().getSteamGames().size() == 0) {
			librarian.getTee().writelnError(parameters.getMessages().getString("errorRollGamesListIsEmpty"));
		} else {
			GameLauncher[] gameLaunchers = librarian.getGameLaunchers();
			int index;
			String message;
//			if (librarian.isSteamCommunityReachable(Librarian.DisplayMode.silent)) {
				
				// Check changed arguments
				class ChangedGame {
					SteamGame game;
					String modifiedArguments;
					ChangedGame(SteamGame game, String modifiedArguments) {
						this.game = game;
						this.modifiedArguments= modifiedArguments; 
					}
				}
				List<ChangedGame> changedGames = new ArrayList<ChangedGame>();
				for (GameLauncher gameLauncher : gameLaunchers) {
					if (gameLauncher.getGame() == null) continue;
					String arguments = gameLauncher.getArguments() != null ? gameLauncher.getArguments().trim() : null;
					String gameArguments = gameLauncher.getGameArguments() != null ? gameLauncher.getGameArguments().trim() : null; 
					if (arguments != null && !arguments.equals("")) {
						if (gameArguments == null || !gameArguments.equals(arguments))
							changedGames.add(new ChangedGame(gameLauncher.getGame(), arguments));
					} else {
						if (gameArguments != null && !gameArguments.equals(arguments))
							changedGames.add(new ChangedGame(gameLauncher.getGame(), arguments));
					}
				}
				// Some game arguments have been changed, ask confirmation for saving 
				if (changedGames.size() > 0) {
					message = parameters.getUITexts().getString("warnGameArgumentsHasChanged");
					for (ChangedGame changedGame : changedGames)
						message += "\n" + String.format(parameters.getUITexts().getString("warnGameArgumentsModifications"), 
								changedGame.game.getName(),
								changedGame.game.getArguments() != null ? changedGame.game.getArguments() : parameters.getUITexts().getString("warnGameArgumentsEmpty"),
								changedGame.modifiedArguments != null ? changedGame.modifiedArguments : parameters.getUITexts().getString("warnGameArgumentsEmpty"));
					message += "\n" + parameters.getUITexts().getString("askGameArgumentsHasChanged");
					int option = JOptionPane.showOptionDialog(librarian.getView(), message, librarian.getApplicationTitle(), JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
					if (option == JOptionPane.YES_OPTION)
						for (ChangedGame changedGame : changedGames)
							changedGame.game.setArguments(changedGame.modifiedArguments);
				}
				
				// Shuffle
				Vector<SteamGame> games = shuffle();
				// Roll allowed buttons
				int maxButtonsToRoll = maxButtonsToRoll();
				for (index = 0; index < maxButtonsToRoll; index ++) {
					gameLaunchers[index].setGame(games.get(index));
					message = String.format(parameters.getMessages().getString("infosRollNewGameSelected"), index+1, games.get(index).getName(), games.get(index).getAppID());
					librarian.getTee().writelnMessage(message);
				}
				// Clear other buttons
				while (index < GameChoice.values().length) {
					gameLaunchers[index++].setGame(null);
				}
				// Restore first game name if available
				if (games.size() > 0) librarian.getGameNameTextField().setText(games.get(0).getName());
//			} else {
//				// Clear all buttons
//				message = parameters.getUITexts().getString("errorRollSteamCommunityUnreachable");
//				JOptionPane.showMessageDialog(librarian.getView(), message, librarian.getApplicationTitle(), JOptionPane.OK_OPTION);
//				for (index = 0; index < GameChoice.values().length; index ++) {
//					gameLaunchers[index++].setGame(null);
//				}
//			}
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		librarian.displayMainTab(LibrarianTabEnum.Controls);
		readOrRoll();
	}

}
