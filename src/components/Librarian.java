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
package components;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import commons.GamesLibrarianIcons;
import commons.GamesLibrarianIcons.LoadingSource;
import commons.OS;
import commons.Strings;
import commons.api.Parameters;
import commons.api.Steam;
import commons.api.SteamAchievementsList;
import commons.api.SteamFriend;
import commons.api.SteamGame;
import commons.api.SteamGameStats;
import commons.api.SteamGamesList;
import commons.api.SteamGroup;
import commons.api.SteamProfile;
import commons.api.parsers.SteamGameStatsParser;
import commons.api.parsers.SteamProfileParser;
import commons.comparators.LaunchButtonsComparator;
import commons.comparators.SteamFriendButtonsComparator;
import commons.comparators.SteamFriendsComparator;
import commons.comparators.SteamGamesComparator;
import commons.comparators.SteamGroupButtonsComparator;
import commons.comparators.SteamGroupsComparator;
import commons.enums.ButtonsDisplayMode;
import commons.enums.ComparisonDirection;
import commons.enums.DumpMode;
import commons.enums.GameChoice;
import commons.enums.LaunchType;
import commons.enums.LibrarianTabEnum;
import commons.enums.LibraryTabEnum;
import commons.enums.LocaleChoice;
import commons.enums.OnlineState;
import commons.enums.PrivacyState;
import commons.enums.ProfileTabEnum;
import commons.enums.SteamAchievementsListsSortMethod;
import commons.enums.SteamAchievementsSortMethod;
import commons.enums.SteamFriendsDisplayMode;
import commons.enums.SteamFriendsSortMethod;
import commons.enums.SteamGamesDisplayMode;
import commons.enums.SteamGamesSortMethod;
import commons.enums.SteamGroupsDisplayMode;
import commons.enums.SteamGroupsSortMethod;
import commons.enums.TabEnum;
import components.GamesLibrarian.WindowBuilderMask;
import components.actions.RollAction;
import components.actions.SteamFriendsDisplayModeAction;
import components.actions.SteamGroupsDisplayModeAction;
import components.comboboxes.SteamFriendsSortMethodComboBox;
import components.comboboxes.SteamGamesSortMethodComboBox;
import components.comboboxes.SteamGroupsSortMethodComboBox;
import components.comboboxes.observables.ButtonsDisplayModeObservable;
import components.comboboxes.observables.SteamAchievementsSortMethodObservable;
import components.comboboxes.observers.ButtonsDisplayModeObserver;
import components.comboboxes.observers.SteamAchievementsSortMethodObserver;
import components.commons.BundleManager;
import components.commons.ColoredTee;
import components.commons.interfaces.Translatable;
import components.commons.layouts.WrapLayout;
import components.commons.observers.Translator;
import components.containers.BoundedComponent;
import components.containers.GameLauncher;
import components.containers.MostPlayedGameLauncher;
import components.containers.remotes.LaunchButton;
import components.containers.remotes.SteamFriendButton;
import components.containers.remotes.SteamFriendWithSameGameButton;
import components.containers.remotes.SteamGroupButton;
import components.dialogs.AskForConfigurationFileDialog;
import components.tables.SteamAchievementsTable;
import components.tables.SteamFriendsTable;
import components.tables.SteamGamesTable;
import components.tables.SteamGroupsTable;
import components.tables.cells.renderers.SteamAchievementIconTableCellRenderer;
import components.tables.columns.ColumnResizer;
import components.tables.models.SteamAchievementsTableModel;
import components.tables.models.SteamFriendsTableModel;
import components.tables.models.SteamGroupsTableModel;
import components.workers.SteamAchievementsListIconReader;
import components.workers.SteamFriendGameStatsReader;
import components.workers.SteamGameStatsReader;
import components.workers.SteamGamesListReader;
import components.workers.SteamProfileReader;
import components.workers.latched.AllSteamGamesStatsReader;
import components.workers.latched.SteamFriendsGameListsReader;
import components.workers.latched.SteamFriendsGameStatsReader;
import components.workers.latched.SteamFriendsListReader;

/**
 * GamesLibrarian engine
 * @author Naeregwen
 *
 */
public class Librarian implements SteamAchievementsSortMethodObserver, ButtonsDisplayModeObserver, Translator {

	/**
	 * Setup data
	 */
	WindowBuilderMask me;
	GamesLibrarian view;
	Parameters parameters;
	
	/**
	 * Runtime variables
	 */
	private ArrayList<SteamAchievementsSortMethodObservable> achievementsSortMethodObservables;
	private ArrayList<ButtonsDisplayModeObservable> buttonsDisplayModeObservables;
	HashMap<String, SteamProfile> profiles;
	
	// I18n
	private ArrayList<Translatable> translatables;
	private HashMap<String,String> durationTokens;
	
	// Remember currently selected Objects
	SteamGame currentSteamGame;
	SteamProfile currentSteamProfile;

	// Remember last value functionality
	private SteamGame lastSteamGameWithStats;
	private String selectedDirectory;
	private String selectedFilename;
	// Remember last game name to display between mouse moves
	private String lastGameName;
	
	// SwingWorkers
	private SteamGamesListReader steamGamesListReader;
	private SteamGameStatsReader steamGameStatsReader;
	private SteamProfileReader steamProfileReader;
	private SteamFriendsListReader steamFriendsListReader;
	private SteamFriendsGameListsReader steamFriendsGameListsReader;
	private SteamFriendsGameStatsReader steamFriendsGameStatsReader;
	private SteamFriendGameStatsReader steamFriendGameStatsReader;
	private AllSteamGamesStatsReader allSteamGamesStatsReader;
	
	/**
	 * Runtime status
	 */
	boolean steamGamesListReading;
	boolean steamGameStatsReading;
	boolean loadAllAchievements; // FIXME: Task chaining problem
	boolean steamProfileReading;
	boolean steamFriendsListReading;
	boolean steamFriendsGameListsReading;
	boolean steamFriendGameStatsReading;
	boolean allSteamGamesStatsReading;
	
	/**
	 *  Mode to display informations in console 
	 */
	public static enum DisplayMode { verbose, silent }

	/**
	 * Max friends of a profile to chain a readSteamFriendsList request with a readSteamFriendsGameLists request
	 */
	public static final int MaxFriendsForGamesList = 40;
	
	/**
	 * Graphic containers
	 */
	private GameLauncher[] gameLaunchers;
	private SteamAchievementsTable steamAchievementsTable;
	private SteamGamesTable steamGamesTable;
	private SteamGroupsTable steamGroupsTable;
	private SteamFriendsTable steamFriendsTable;
	
	/**
	 * Custom graphic components
	 */
	private ColoredTee tee;
	
	/**
	 * Create a Librarian
	 * 
	 * @param gamesLibrarian
	 */
	public Librarian(WindowBuilderMask me) {
		this.me = me;
		// Attach the view
		// It is not because you a...are, ... qu'i am you, qu'i am you
		this.view = me.me.me();
		// Parameters will be created at setup
		// Initialize runtime variables
		achievementsSortMethodObservables = new ArrayList<SteamAchievementsSortMethodObservable>();
		buttonsDisplayModeObservables = new ArrayList<ButtonsDisplayModeObservable>();
		profiles = new HashMap<String, SteamProfile> (); // Create empty profiles list
		// Initialize I18n
		translatables = new ArrayList<Translatable>();
		durationTokens = new HashMap<String,String>();
		// Initialize currently selected Objects
		currentSteamGame = null;
		currentSteamProfile = null;
		// Initialize last value functionality
		lastSteamGameWithStats = null;
		selectedDirectory = "";
		selectedFilename = "";
		lastGameName = null;
		// SwingWorkers will be created when needed
		// Runtime status variables will be created at setup
		// Graphic containers will be created at startup or when needed
	}
	
	/**
	 * Cleanup a Librarian
	 * TODO: Finish properly
	 */
	public void cleanup() {
		cancelReadGamesList();
		cancelSteamGameStatsReading();
		cancelSteamProfileReading();
		cancelSteamFriendsListReading();
		cancelSteamFriendsGameListsReading();
		cancelSteamFriendGameStatsReading();
		cancelSteamFriendsGameStatsReading();
		cancelAllSteamGamesStatsReading();
	}
	
	//
	// Direct getters
	//
	
	/**
	 * @return the parameters
	 */
	public Parameters getParameters() {
		return parameters;
	}
	
	/**
	 * @return the view
	 */
	public GamesLibrarian getView() {
		return view;
	}

	/**
	 * @param view the view to set
	 */
	public void setView(GamesLibrarian view) {
		this.view = view;
	}

	/**
	 * @return the steamGame
	 */
	public SteamGame getCurrentSteamGame() {
		return currentSteamGame;
	}

	/**
	 * @return the currentSteamProfile
	 */
	public SteamProfile getCurrentSteamProfile() {
		return currentSteamProfile;
	}

	/**
	 * @return the steamGamesListReading
	 */
	public boolean isSteamGamesListReading() {
		return steamGamesListReading;
	}

	
	/**
	 * @return the loadAllAchievements
	 */
	public boolean isLoadAllAchievements() {
		return loadAllAchievements;
	}

	/**
	 * @param steamGamesListReading the steamGamesListReading to set
	 */
	public void setSteamGamesListReading(boolean gamesListReading) {
		this.steamGamesListReading = gamesListReading;
	}

	/**
	 * @return the steamGameStatsReading
	 */
	public boolean isSteamGameStatsReading() {
		return steamGameStatsReading;
	}

	/**
	 * @param steamGameStatsReading the steamGameStatsReading to set
	 */
	public void setSteamGameStatsReading(boolean steamGameStatsReading) {
		this.steamGameStatsReading = steamGameStatsReading;
	}

	/**
	 * @return the steamProfileReading
	 */
	public boolean isSteamProfileReading() {
		return steamProfileReading;
	}

	/**
	 * @param steamProfileReading the steamProfileReading to set
	 */
	public void setSteamProfileReading(boolean steamProfileReading) {
		this.steamProfileReading = steamProfileReading;
	}

	/**
	 * @return the steamFriendsListReading
	 */
	public boolean isSteamFriendsListReading() {
		return steamFriendsListReading;
	}

	/**
	 * @param steamFriendsListReading the steamFriendsListReading to set
	 */
	public void setSteamFriendsListReading(boolean steamFriendsListReading) {
		this.steamFriendsListReading = steamFriendsListReading;
	}

	/**
	 * @return the steamFriendsGameListsReading
	 */
	public boolean isSteamFriendsGameListsReading() {
		return steamFriendsGameListsReading;
	}

	/**
	 * @param steamFriendsGameListsReading the steamFriendsGameListsReading to set
	 */
	public void setSteamFriendsGameListsReading(boolean steamFriendsGameListsReading) {
		this.steamFriendsGameListsReading = steamFriendsGameListsReading;
	}

	/**
	 * @return the steamFriendSteamGameStatsReading
	 */
	public boolean isSteamFriendGameStatsReading() {
		return steamFriendGameStatsReading;
	}
	
	/**
	 * @param steamFriendSteamGameStatsReading the steamFriendSteamGameStatsReading to set
	 */
	public void setSteamFriendGameStatsReading(boolean steamFriendSteamGameStatsReading) {
		this.steamFriendGameStatsReading = steamFriendSteamGameStatsReading;
	}
	
	/**
	 * @param allSteamGamesStatsReading the allSteamGamesStatsReading to set
	 */
	public void setAllSteamGamesStatsReading(boolean allSteamGamesStatsReading) {
		this.allSteamGamesStatsReading = allSteamGamesStatsReading;
	}

	/**
	 * @return the gameLaunchers
	 */
	public GameLauncher[] getGameLaunchers() {
		return gameLaunchers;
	}

	/**
	 * @return the tee
	 */
	public ColoredTee getTee() {
		return tee;
	}
	
	//
	// Indirect getters
	//
	
	/**
	 * @return the bundleManager
	 */
	public BundleManager getBundleManager() {
		return view.getBundleManager();
	}
	
	/**
	 * @return the rollAction
	 */
	public RollAction getRollAction() {
		return view.rollAction;
	}
	
	/**
	 * @return consoleTextPane
	 */
	public JTextPane getConsoleTextPane() {
		return view.consoleTextPane;
	}
	
	/**
	 * @return the gameNameTextField
	 */
	public JTextField getGameNameTextField() {
		return view.gameNameTextField;
	}

	/**
	 * @return the gamerSteamIdTextField
	 */
	public JTextField getGamerSteamIdTextField() {
		return view.gamerSteamIdTextField;
	}

	/**
	 * @return the windowsDistributionTextField
	 */
	public JTextField getWindowsDistributionTextField() {
		return view.windowsDistributionTextField;
	}

	/**
	 * @return the steamExecutableTextField
	 */
	public JTextField getSteamExecutableTextField() {
		return view.steamExecutableTextField;
	}

	/**
	 * @return the libraryPane
	 */
	public JPanel getLibraryPane() {
		return view.libraryPane;
	}
	
	public SteamGamesSortMethodComboBox getSteamGamesSortMethodComboBox() {
		return view.librarySortMethodComboBox;
	}
	
	/**
	 * @return the profileGroupsTab
	 */
	public JPanel getProfileGroupsTab() {
		return view.profileGroupsPane;
	}

	public SteamGroupsSortMethodComboBox getSteamGroupsSortMethodComboBox() {
		return view.steamGroupsSortMethodComboBox;
	}
	
	public SteamFriendsSortMethodComboBox getSteamFriendsSortMethodComboBox() {
		return view.steamFriendsSortMethodComboBox;
	}
	
	/**
	 * @return the profileFriendsTab
	 */
	public JPanel getProfileFriendsTab() {
		return view.profileFriendsPane;
	}

	//
	// Utilities
	//

	/**
	 * @return Application Title
	 */
	public String getApplicationTitle() {
		return view.getApplicationTitle();
	}
	
	/**
	 * get Tab Component index by name
	 * 
	 * @param parent
	 * @param name
	 * @return
	 */
	public int getTabComponentIndexByName(JTabbedPane parent, String name) {
        Component[] components = parent.getComponents();
        for (int index = 0; index < components.length; index++) {
        	if (components[index] instanceof JPanel && components[index].getName().equalsIgnoreCase(name))
        		return index;
        }
        return -1;
	}	
	
	/**
	 * get Tab Component by name
	 * 
	 * @param parent
	 * @param name
	 * @return
	 */
	public Component getTabComponentName(JTabbedPane parent, String name) {
        Component[] components = parent.getComponents();
        for (int index = 0; index < components.length; index++) {
        	if (components[index] instanceof JPanel && components[index].getName().equalsIgnoreCase(name))
        		return components[index];
        }
        return null;
	}	
	
	/**
	 * Return visible component of a JComponent parent
	 * 
	 * @param parent JComponent to look up for visible component
	 * @return visible component or null
	 */
	public JComponent getCurrentCard(JComponent parent) {
		JComponent card = null;
		for (Component component : parent.getComponents()) {
			if (component.isVisible() == true) {
				card = (JComponent) component;
				break;
			}
		}
		return card;
	}
	
	/**
	 * Test if Steam Community is reachable
	 * @param mode display mode for the result
	 * @return true if Steam Community is reachable
	 */
	public boolean isSteamCommunityReachable(Librarian.DisplayMode mode) {
		
		String message;
		ResourceBundle messages = parameters.getMessages();
		
		tee.writelnInfos(messages.getString("checkingSteamCommunity"));
		try {
			if (Steam.isSteamCommunityReachable()) {
				if (mode.equals(Librarian.DisplayMode.verbose)) {
					String steamStatusLine = Steam.responseStatusLine != null ? Steam.responseStatusLine.toString() : null;
					String responseErrorCause = (Steam.responseErrorCause != null && !Steam.responseErrorCause.equals("")) ? Steam.responseErrorCause : "";
					message = parameters.getMessages().getString("steamCommunityIsUp");
					String steamCommunityStatusResponse = String.format(parameters.getMessages().getString("steamCommunityStatusResponse"), steamStatusLine != null ? steamStatusLine : responseErrorCause);
					tee.writelnMessage(message);
					tee.writelnMessage(steamCommunityStatusResponse);
				}
				return true;
			} else {
				if (mode.equals(Librarian.DisplayMode.verbose)) {
					String steamStatusLine = Steam.responseStatusLine != null ? Steam.responseStatusLine.toString() : null;
					String responseErrorCause = (Steam.responseErrorCause != null && !Steam.responseErrorCause.equals("")) ? Steam.responseErrorCause : "";
					message = String.format(parameters.getMessages().getString("steamCommunityIsDown"), steamStatusLine != null ? steamStatusLine : responseErrorCause);
					tee.writelnMessage(message);
				}
				return false;
			}
		} catch (MalformedURLException e) {
			tee.printStackTrace(e);
			message = messages.getString("steamCommunityMalformedURL");
			Steam.responseErrorCause = messages.getString("steamCommunityMalformedURLShortMessage");;
			tee.writelnError(message);
			return false;
		} catch (UnknownHostException e) {
			tee.printStackTrace(e);
			message = messages.getString("steamCommunityUnknownHost");
			Steam.responseErrorCause =  messages.getString("steamCommunityUnknownHostShortMessage");;
			tee.writelnError(message);
			return false;
		} catch (SocketTimeoutException e) {
			tee.printStackTrace(e);
			message = messages.getString("steamCommunityTimeout");
			Steam.responseErrorCause = messages.getString("steamCommunityTimeoutShortMessage");;
			tee.writelnError(message);
			return false;
		} catch (IOException e) {
			tee.printStackTrace(e);
			message = String.format(parameters.getMessages().getString("steamCommunityIsDown"), Steam.responseStatusLine);
			Steam.responseErrorCause = message;
			tee.writelnError(message);
			return false;
		}
	}
	
	/**
	 * Test if Steam Community is reachable
	 * Just for output message
	 * @param mode
	 */
	private boolean checkSteamCommunity(DisplayMode mode) {
		// Check if Steam Community is reachable
		if (!isSteamCommunityReachable(mode)) {
			String steamStatusLine = Steam.responseStatusLine != null ? Steam.responseStatusLine.toString() : null;
			String responseErrorCause = (Steam.responseErrorCause != null && !Steam.responseErrorCause.equals("")) ? Steam.responseErrorCause : "";
			String message = String.format(parameters.getMessages().getString("steamCommunityIsDown"), steamStatusLine != null ? steamStatusLine : responseErrorCause);
			String question = parameters.getUITexts().getString("errorSteamCommunityIsDown");
			int option = JOptionPane.showOptionDialog(view, message + question, getApplicationTitle(), JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
			if (option == JOptionPane.NO_OPTION)
				return false;
		}
		return true;
	}
	
	/**
	 * Save GameNameTextField in lastGameName
	 * @param game
	 */
	public void enterGame(SteamGame game) {
		lastGameName = view.gameNameTextField.getText();
		view.gameNameTextField.setText((game == null || game.getName() == null) ? 
				BundleManager.getMessages(me, "noGameSelected") : 
					game.getName());
	}
	
	/**
	 * Restore lastGameName in GameNameTextField
	 * @param game
	 */
	public void leaveGame(SteamGame game) {
		if (lastGameName == null) return;
		view.gameNameTextField.setText(lastGameName);
	}
	
	/**
	 * Signal roll error
	 * Reset launch button
	 */
	public void rollError() {
		tee.writelnError(parameters.getMessages().getString("errorRollGamerSteamIDIsNotSet"));
		for (GameLauncher gameLauncher : gameLaunchers)
			gameLauncher.setGame(null);
	}
	
	/**
	 * Signal read error
	 * Reset launch button
	 */
	public void readError() {
		ResourceBundle messages = parameters.getMessages();
		tee.writelnError(String.format(messages.getString("errorRollGamesListIsEmpty"), currentSteamProfile.getId()));
		for (GameLauncher gameLauncher : gameLaunchers)
			gameLauncher.setGame(null);
	}

	/**
	 * Create an ImageIcon according to currentSteamProfile.privacyState
	 * @return ImageIcon
	 */
	public ImageIcon getPrivacyStateIcon() {
		ImageIcon icon = null;
		try {
			icon = new ImageIcon(GamesLibrarian.class.getResource(PrivacyState.valueOf(currentSteamProfile.getPrivacyState().toUpperCase()).getIconPath()));
		} catch (IllegalArgumentException | NullPointerException e) {
			icon = new ImageIcon(GamesLibrarian.class.getResource(PrivacyState.UNKNOWN.getIconPath()));
		}
		return icon;
	}
	
	/**
	 * Create an ImageIcon according to currentSteamProfile.onlineState
	 * @return ImageIcon
	 */
	public ImageIcon getOnlineStateIcon() {
		ImageIcon icon = null;
		try {
			icon = new ImageIcon(GamesLibrarian.class.getResource(OnlineState.valueOf(currentSteamProfile.getOnlineState().toUpperCase()).getIconPath()));
		} catch (IllegalArgumentException | NullPointerException e) {
			icon = new ImageIcon(GamesLibrarian.class.getResource(OnlineState.UNKNOWN.getIconPath()));
		}
		return icon;
	}
	
	//
	// Extras Commands
	//
	
	/**
	 * Force GamesList reloading 
	 */
	public void forceRead() {
		view.rollAction.forceRead();
	}
	
	//
	// Modify runtime variables
	//

	/**
	 * Add a profile to the profiles list
	 * @param steamProfile the SteamProfile to add
	 */
	public void addProfile(SteamProfile steamProfile, boolean viewOnly) {
		if (!viewOnly) {
			if (profiles.containsKey(steamProfile.getSteamID64())) {
				SteamProfile oldSteamProfile = profiles.get(steamProfile.getSteamID64());
				steamProfile.setSteamGames(oldSteamProfile.getSteamGames());
				profiles.remove(steamProfile.getSteamID64());
			}
			profiles.put(steamProfile.getSteamID64(), steamProfile);
		}
		// Update Profile Commands Pane
		view.knownProfilesComboBox.addProfile(steamProfile);
	}
	
	/**
	 * When using a non validated profile
	 * Use this property to save currentSteamProfile
	 */
	private SteamProfile oldSteamProfile;
	
	/**
	 * Use a non validated profile
	 * Try to read 
	 * @param steamProfile
	 */
	public void addNewProfile(SteamProfile steamProfile) {
		oldSteamProfile = currentSteamProfile;
		currentSteamProfile = steamProfile;
		readSteamProfile();
	}
	
	/**
	 * Restore currentSteamProfile to oldSteamProfile
	 */
	public void restoreOldProfile() {
		if (oldSteamProfile != null)
			currentSteamProfile = oldSteamProfile;
	}
	
	/**
	 * Add a games list to a friend identified by steamID64
	 * @param steamID64 SteamID64 identifying the friend
	 * @param games games list to add
	 */
	public void addFriendGameList(String steamID64, Vector<SteamGame> games) {
		for (SteamProfile friend : currentSteamProfile.getSteamFriends())
			if (friend.getSteamID64().equalsIgnoreCase(steamID64)) {
				friend.setSteamGames(games);
				return;
			}
	}
	
	/**
	 * Attach SteamGameStats to SteamGame
	 * @param steamGameStats
	 */
	public void addSteamGameStats(SteamGameStats steamGameStats) {
		String appID = steamGameStats.getAppID();
		for (SteamGame steamGame : currentSteamProfile.getSteamGames()) {
			if (steamGame.getAppID().equals(appID)) {
				steamGame.setSteamGameStats(steamGameStats);
				break;
			}
		} 
	}
	
	/**
	 * Mark games statistics as fetched
	 */
	public void setStatisticsFetched(SteamGamesList steamGamesList) {
		if (parameters.getSteamGamesList() != null && parameters.getSteamGamesList().getSteamGames() != null) {
			parameters.getSteamGamesList().setStatisticsFetched();
			currentSteamProfile.setStatisticsFetched(true);
			if (currentSteamProfile.getId64().equals(steamGamesList.getSteamID64()))
				currentSteamProfile.setSteamGames(steamGamesList.getSteamGames());
		}
	}
	
	//
	// Query runtime variables
	//
	
	public String getCurrentSteamProfileAvatarIconURL() {
		return currentSteamProfile != null ? currentSteamProfile.getAvatarIcon() : null;
	}
	
	public SteamAchievementsSortMethod getSteamAchievementsSortMethod() {
		return ((SteamAchievementsTableModel) steamAchievementsTable.getModel()).getSteamAchievementsComparator().getSteamAchievementsSortMethod();
	}
	
	//
	// Sorting data
	//
	
	/**
	 * Sort libraryGames (2 panes)
	 * FIXME: Sort does not match in two tables for hoursPlayedLast2Weeks/hoursOnRecord
	 * @param librarySortMethod
	 */
	public void sort(SteamGamesSortMethod librarySortMethod) {
		
		if (view.gamesLibraryButtonGroup.getButtonCount() <= 1) return;
		
		// Select comparator
		Comparator<LaunchButton> launchButtonsComparator = null;
		Comparator<SteamGame> steamGamesComparator = null;
		
		switch(librarySortMethod) {
		case InitialAscendingOrder:
		case LogoAscendingOrder:
		case NameAscendingOrder:
		case ArgumentsAscendingOrder:
		case SteamLaunchMethodAscendingOrder:
		case HoursLast2WeeksAscendingOrder:
		case HoursOnRecordAscendingOrder:
		case AppIdAscendingOrder:
		case StoreLinkAscendingOrder:
		case GlobalStatsLinkAscendingOrder:
		case StatsLinkAscendingOrder:
		case AchievementsRatioAscendingOrder:
			launchButtonsComparator = new LaunchButtonsComparator(ComparisonDirection.Ascendant, librarySortMethod);
			steamGamesComparator = new SteamGamesComparator(ComparisonDirection.Ascendant, librarySortMethod);
			break;
		case LogoDescendingOrder:
		case NameDescendingOrder:
		case ArgumentsDescendingOrder:
		case SteamLaunchMethodDescendingOrder:
		case HoursLast2WeeksDescendingOrder:
		case HoursOnRecordDescendingOrder:
		case AppIdDescendingOrder:
		case StoreLinkDescendingOrder:
		case GlobalStatsLinkDescendingOrder:
		case StatsLinkDescendingOrder:
		case AchievementsRatioDescendingOrder:
			launchButtonsComparator = new LaunchButtonsComparator(ComparisonDirection.Descendant, librarySortMethod);
			steamGamesComparator = new SteamGamesComparator(ComparisonDirection.Descendant, librarySortMethod);
			break;
		}
		
		// Sort libraryPane
		Enumeration<AbstractButton> buttons = view.gamesLibraryButtonGroup.getElements();
		List<LaunchButton> buttonsToSort = new Vector<LaunchButton>();
		while (buttons.hasMoreElements()) {
			LaunchButton button = (LaunchButton) buttons.nextElement();
			buttonsToSort.add(button);
		}
		Collections.sort(buttonsToSort, launchButtonsComparator);
		while (view.gamesLibraryButtonGroup.getElements().hasMoreElements()) {
			LaunchButton button = (LaunchButton) view.gamesLibraryButtonGroup.getElements().nextElement();
			view.gamesLibraryButtonGroup.remove(button);
			view.buttonsLibraryPane.remove(button);
		}
		Iterator<LaunchButton> buttonsIterator = buttonsToSort.iterator();
		while (buttonsIterator.hasNext()) {
			LaunchButton button = buttonsIterator.next();
			view.gamesLibraryButtonGroup.add(button);
			view.buttonsLibraryPane.add(button);
		}
		
		// Update display
		view.buttonsLibraryPane.validate();
		
		// Sort steamGamesTable
		Integer column = null;
		switch(librarySortMethod) {
		case InitialAscendingOrder:
			column = -1;
			break;
		case LogoAscendingOrder:
		case LogoDescendingOrder:
			column = SteamGame.ColumnsOrder.logo.ordinal();
			break;
		case NameAscendingOrder:
		case NameDescendingOrder:
			column = SteamGame.ColumnsOrder.name.ordinal();
			break;
		case ArgumentsAscendingOrder:
		case ArgumentsDescendingOrder:
			column = SteamGame.ColumnsOrder.arguments.ordinal();
			break;
		case SteamLaunchMethodAscendingOrder:
		case SteamLaunchMethodDescendingOrder:
			column = SteamGame.ColumnsOrder.steamLaunchMethod.ordinal();
			break;
		case HoursLast2WeeksAscendingOrder:
		case HoursLast2WeeksDescendingOrder:
			column = SteamGame.ColumnsOrder.hoursLast2Weeks.ordinal();
			break;
		case HoursOnRecordAscendingOrder:
		case HoursOnRecordDescendingOrder:
			column = SteamGame.ColumnsOrder.hoursOnRecord.ordinal();
			break;
		case AppIdAscendingOrder:
		case AppIdDescendingOrder:
			column = SteamGame.ColumnsOrder.appID.ordinal();
			break;
		case StoreLinkAscendingOrder:
		case StoreLinkDescendingOrder:
			column = SteamGame.ColumnsOrder.storeLink.ordinal();
			break;
		case GlobalStatsLinkAscendingOrder:
		case GlobalStatsLinkDescendingOrder:
			column = SteamGame.ColumnsOrder.globalStatsLink.ordinal();
			break;
		case StatsLinkAscendingOrder:
		case StatsLinkDescendingOrder:
			column = SteamGame.ColumnsOrder.statsLink.ordinal();
			break;
		case AchievementsRatioAscendingOrder:
		case AchievementsRatioDescendingOrder:
			column = SteamGame.ColumnsOrder.steamAchievementsRatio.ordinal();
			break;
		}
		
//		if (librarySortMethod.equals(SteamGamesSortMethod.AchievementsRatioAscendingOrder) || librarySortMethod.equals(SteamGamesSortMethod.AchievementsRatioDescendingOrder)) {
//			
//		} else {
			if (steamGamesComparator != null) {
				if (column == -1)
					steamGamesTable.getRowSorter().setSortKeys(null);
				else if (column != null)
					steamGamesTable.getRowSorter().toggleSortOrder(column);
			}
//		}
	}
	
	/**
	 * Sort groups (2 panes)
	 * @param groupsSortMethod
	 */
	public void sort(SteamGroupsSortMethod groupsSortMethod) {
		
		if (view.steamGroupsButtonGroup.getButtonCount() <= 1) return;
		
		// Select comparator
		Comparator<SteamGroupButton> steamGroupsButtonComparator = null;
		Comparator<SteamGroup> steamGroupsComparator = null;
		
		switch (groupsSortMethod) {
		case InitialAscendingOrder:
		case NameAscendingOrder:
		case LogoAscendingOrder:
		case HeadlineAscendingOrder:
		case SummaryAscendingOrder:
		case SteamId64AscendingOrder:
			steamGroupsButtonComparator = new SteamGroupButtonsComparator(ComparisonDirection.Ascendant, groupsSortMethod);
			steamGroupsComparator = new SteamGroupsComparator(ComparisonDirection.Ascendant, groupsSortMethod);
			break;
		case NameDescendingOrder:
		case LogoDescendingOrder:
		case HeadlineDescendingOrder:
		case SummaryDescendingOrder:
		case SteamId64DescendingOrder:
			steamGroupsButtonComparator = new SteamGroupButtonsComparator(ComparisonDirection.Descendant, groupsSortMethod);
			steamGroupsComparator = new SteamGroupsComparator(ComparisonDirection.Descendant, groupsSortMethod);
			break;
		}
		
		// Sort GroupsPane
		Enumeration<AbstractButton> buttons = view.steamGroupsButtonGroup.getElements();
		List<SteamGroupButton> buttonsToSort = new Vector<SteamGroupButton>();
		while (buttons.hasMoreElements()) {
			SteamGroupButton button = (SteamGroupButton) buttons.nextElement();
			buttonsToSort.add(button);
		}
		Collections.sort(buttonsToSort, steamGroupsButtonComparator);
		while (view.steamGroupsButtonGroup.getElements().hasMoreElements()) {
			SteamGroupButton button = (SteamGroupButton) view.steamGroupsButtonGroup.getElements().nextElement();
			view.steamGroupsButtonGroup.remove(button);
			view.steamGroupsButtonsPane.remove(button);
		}
		Iterator<SteamGroupButton> buttonsIterator = buttonsToSort.iterator();
		while (buttonsIterator.hasNext()) {
			SteamGroupButton button = buttonsIterator.next();
			view.steamGroupsButtonGroup.add(button);
			view.steamGroupsButtonsPane.add(button);
		}
		
		Integer column = null;
		switch(groupsSortMethod) {
		case InitialAscendingOrder:
			column = -1;
			break;
		case LogoAscendingOrder:
		case LogoDescendingOrder:
			column = SteamGroup.ColumnsOrder.logo.ordinal();
			break;
		case NameAscendingOrder:
		case NameDescendingOrder:
			column = SteamGroup.ColumnsOrder.name.ordinal();
			break;
		case HeadlineAscendingOrder:
		case HeadlineDescendingOrder:
			column = SteamGroup.ColumnsOrder.headline.ordinal();
			break;
		case SteamId64AscendingOrder:
		case SteamId64DescendingOrder:
			column = SteamGroup.ColumnsOrder.groupID64.ordinal();
			break;
		case SummaryAscendingOrder:
		case SummaryDescendingOrder:
			column = SteamGroup.ColumnsOrder.summary.ordinal();
			break;
		}
		
		if (steamGroupsComparator != null) {
			if (column == -1)
				steamGroupsTable.getRowSorter().setSortKeys(null);
			else if (column != null)
				steamGroupsTable.getRowSorter().toggleSortOrder(column);
		}
	
	}
	
	/**
	 * Sort friends (2 panes)
	 * @param friendsSortMethod
	 */
	public void sort(SteamFriendsSortMethod friendsSortMethod) {
		
		if (view.steamFriendsButtonGroup.getButtonCount() <= 1) return;
		
		// Select comparator
		Comparator<SteamFriendButton> steamFriendsButtonComparator = null;
		Comparator<SteamProfile> steamFriendsComparator = null;
		
		switch (friendsSortMethod) {
		case InitialAscendingOrder:
		case NameAscendingOrder:
		case LogoAscendingOrder:
		case OnlineStateAscendingOrder:
		case StateMessageAscendingOrder:
		case SteamId64AscendingOrder:
			steamFriendsButtonComparator = new SteamFriendButtonsComparator(ComparisonDirection.Ascendant, friendsSortMethod);
			steamFriendsComparator = new SteamFriendsComparator(ComparisonDirection.Ascendant, friendsSortMethod);
			break;
		case NameDescendingOrder:
		case LogoDescendingOrder:
		case OnlineStateDescendingOrder:
		case StateMessageDescendingOrder:
		case SteamId64DescendingOrder:
			steamFriendsButtonComparator = new SteamFriendButtonsComparator(ComparisonDirection.Descendant, friendsSortMethod);
			steamFriendsComparator = new SteamFriendsComparator(ComparisonDirection.Descendant, friendsSortMethod);
			break;
		}
		
		// Sort FriendsPane
		Enumeration<AbstractButton> buttons = view.steamFriendsButtonGroup.getElements();
		List<SteamFriendButton> buttonsToSort = new Vector<SteamFriendButton>();
		while (buttons.hasMoreElements()) {
			SteamFriendButton button = (SteamFriendButton) buttons.nextElement();
			buttonsToSort.add(button);
		}
		Collections.sort(buttonsToSort, steamFriendsButtonComparator);
		while (view.steamFriendsButtonGroup.getElements().hasMoreElements()) {
			SteamFriendButton button = (SteamFriendButton) view.steamFriendsButtonGroup.getElements().nextElement();
			view.steamFriendsButtonGroup.remove(button);
			view.steamFriendsButtonsPane.remove(button);
		}
		Iterator<SteamFriendButton> buttonsIterator = buttonsToSort.iterator();
		while (buttonsIterator.hasNext()) {
			SteamFriendButton button = buttonsIterator.next();
			view.steamFriendsButtonGroup.add(button);
			view.steamFriendsButtonsPane.add(button);
		}
		
		Integer column = null;
		switch(friendsSortMethod) {
		case InitialAscendingOrder:
			column = -1;
			break;
		case LogoAscendingOrder:
		case LogoDescendingOrder:
			column = SteamFriend.ColumnsOrder.logo.ordinal();
			break;
		case NameAscendingOrder:
		case NameDescendingOrder:
			column = SteamFriend.ColumnsOrder.name.ordinal();
			break;
		case OnlineStateAscendingOrder:
		case OnlineStateDescendingOrder:
			column = SteamFriend.ColumnsOrder.onlineState.ordinal();
			break;
		case StateMessageAscendingOrder:
		case StateMessageDescendingOrder:
			column = SteamFriend.ColumnsOrder.stateMessage.ordinal();
			break;
		case SteamId64AscendingOrder:
		case SteamId64DescendingOrder:
			column = SteamFriend.ColumnsOrder.steamID64.ordinal();
			break;
		}
		
		if (steamFriendsComparator != null) {
			if (column == -1)
				steamFriendsTable.getRowSorter().setSortKeys(null);
			else if (column != null)
				steamFriendsTable.getRowSorter().toggleSortOrder(column);
		}
	
	}
	
	/**
	 * Set new AchievementsSortMethod and sort rows according to this new order
	 * @param achievementsSortMethod
	 */
	public void sortSteamAchievements(SteamAchievementsSortMethod achievementsSortMethod) {
		if (steamAchievementsTable == null) return;
		SteamAchievementsTableModel steamAchievementsTableModel = (SteamAchievementsTableModel) steamAchievementsTable.getModel();
		steamAchievementsTableModel.setAchievementSortMethod(achievementsSortMethod);
		steamAchievementsTableModel.sort();
	}

	/**
	 * Q&D solution to sort columns
	 * @param achievementsListSortMethod
	 */
	public void sortSteamAchievementsList(SteamAchievementsListsSortMethod achievementsListSortMethod) {
		if (steamAchievementsTable == null) return;
		// Set new order
		if (achievementsListSortMethod != null)
			((SteamAchievementsTableModel)steamAchievementsTable.getModel()).setAchievementsListSortMethod(achievementsListSortMethod);
		
		// Get new order result
		SteamAchievementsTableModel steamAchievementsTableModel = (SteamAchievementsTableModel) steamAchievementsTable.getModel();
		List<SteamAchievementsList> achievementsLists = steamAchievementsTableModel.sortColumns();
		
		// Remove columns and data
		for (SteamAchievementsList achievementsList : achievementsLists)
			steamAchievementsTable.removeColumn(steamAchievementsTable.getColumn(achievementsList.getPlayerSteamID()));
		steamAchievementsTableModel.clear();
		
		// Add data in new order
		for (SteamAchievementsList achievementsList : achievementsLists)
			steamAchievementsTable.addColumn(steamAchievementsTable.addColumn(achievementsList));
	}
	
	public void sortFriends() {
		
	}
	
	//
	// Normalize components dimensions
	//
	
	/**
	 * Resize all AbstractButton to the greatest dimensions of all the AbstractButton contained in the argument array
	 * All the AbstractButton are managed by a WrapLayout
	 * @param buttonGroup buttonGroup to resize
	 */
	public int normalize(Component[] components) {
		int count = 0;
		int maxWidth = 0;
		int maxHeight = 0;
		ArrayList<AbstractButton> buttons = new ArrayList<AbstractButton>();
		for (int index = 0; index < components.length; index++) {
			if (components[index] instanceof AbstractButton) {
				Component component = components[index];
				buttons.add((AbstractButton) component);
				if (component.getWidth() > maxWidth)
					maxWidth = component.getWidth();
				if (component.getHeight() > maxHeight)
					maxHeight = component.getHeight();
				count++;
			}
		}
		if (count > 1) {
			count = 0;
			Dimension dimension = new Dimension();
			dimension.setSize(maxWidth, maxHeight);
			Iterator<AbstractButton> buttonsIterator = buttons.iterator();
			while (buttonsIterator.hasNext()) {
				buttonsIterator.next().setPreferredSize(dimension);
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Prepare a ButtonGroup to resizing
	 * @param buttonGroup buttonGroup to resize
	 */
	public int normalize(ButtonGroup buttonGroup) {
		Component[] components = new Component[buttonGroup.getButtonCount()];
		int index = 0;
		Enumeration<AbstractButton> buttons = buttonGroup.getElements();
		while (buttons.hasMoreElements()) components[index++] = buttons.nextElement();	
		return normalize(components);
	}
	
	//
	// Adding components operations
	// 
	
	/**
	 * Add a FriendButton and linked SteamProfile
	 * @param friend
	 */
	public void addFriendButton(SteamProfile friend) {
		SteamFriendButton friendButton = new SteamFriendButton(tee, me, friend);
		view.steamFriendsButtonGroup.add(friendButton);
		view.steamFriendsButtonsPane.add(friendButton);
	}
	
	//
	// Removing components operations
	//

	/**
	 * Remove friendAchievements from steamAchievementsTable
	 * @param friend the friend to remove
	 */
	public void removeFriendAchievements(SteamProfile friend) {
		if (cancelSteamFriendGameStatsReading()) return;
		TableColumn column = steamAchievementsTable.getColumn(friend.getSteamID());
		if (column == null)
			column = steamAchievementsTable.getColumn(friend.getSteamID64());
		if (column != null) {
			steamAchievementsTable.removeColumn(column);
			if (((SteamAchievementsTableModel) steamAchievementsTable.getModel()).removeAchievementList(friend)) {
				// Something was broken
				Enumeration<TableColumn> columns = steamAchievementsTable.getColumnModel().getColumns();
				while (columns.hasMoreElements())
					columns.nextElement().setCellRenderer(new SteamAchievementIconTableCellRenderer(getParameters()));					
			}
		}
	}
	
	//
	// Clearing display operations
	//
	
	/**
	 * Remove LaunchButtons from ButtonGroup & Pane
	 * Remove steamGamesTable from libraryGamesListScrollPane
	 */
	void clearGamesLibraryPane() {
		
		// Empty libraryPane
		while (view.gamesLibraryButtonGroup.getElements().hasMoreElements()) {
			LaunchButton button = (LaunchButton) view.gamesLibraryButtonGroup.getElements().nextElement();
			view.gamesLibraryButtonGroup.remove(button);
			view.buttonsLibraryPane.remove(button);
		}
		
		// Empty steamGamesTable
		if (steamGamesTable != null) view.gamesLibraryListScrollPane.remove(steamGamesTable);
	}
	
	/**
	 * Clear TextFields
	 */
	void clearLibraryStatisticsPane() {
		
		// Clear all games statistics
		view.libraryTotalGamesTextField.setText("");
		view.libraryTotalGamesWithStatsTextField.setText("");
		view.libraryTotalGamesWithGlobalStatsTextField.setText("");
		view.libraryTotalGamesWithStoreLinkTextField.setText("");
		
		view.libraryTotalWastedHoursTextField.setText("");
		view.libraryTotalHoursLast2WeeksTextField.setText("");
		
		// Clear all games statistics count
		view.libraryTotalFinishedGamesTextField.setText(BundleManager.getUITexts(me, "unavailable"));
		view.libraryTotalGamesWithInvalidStatsTextField.setText(BundleManager.getUITexts(me, "unavailable"));
		view.libraryTotalAchievementsTextField.setText(BundleManager.getUITexts(me, "unavailable"));
		view.libraryTotalUnlockedAchievementsTextField.setText(BundleManager.getUITexts(me, "unavailable"));
		view.libraryPercentageAchievedTextField.setText(BundleManager.getUITexts(me, "unavailable"));
		view.libraryAveragePercentageAchievedTextField.setText(BundleManager.getUITexts(me, "unavailable"));
	}

	/**
	 * Remove steamAchievementsTable from steamAchievementsScrollPane
	 */
	public void clearAchievementsTable() {
		// Empty steamAchievementsTable
		if (steamAchievementsTable != null) {
			((SteamAchievementsTableModel) steamAchievementsTable.getModel()).clear();
			TableColumnModel tableColumnModel = steamAchievementsTable.getColumnModel();
			Enumeration<TableColumn> columns = tableColumnModel.getColumns();
			while (columns.hasMoreElements())
				tableColumnModel.removeColumn(columns.nextElement());
			((SteamAchievementsTableModel) steamAchievementsTable.getModel()).fireTableStructureChanged();
		}
	}
	
	/**
	 * Remove FriendWithSameGameButton from ButtonGroup & Pane
	 */
	public void clearFriendsWithSameGamePane() {
		Component[] components = view.friendsWithSameGamePane.getComponents();
		for (int index = 0; index < components.length; index++)
			if (components[index] instanceof SteamFriendWithSameGameButton)
				view.friendsWithSameGamePane.remove(components[index]);
	}
	
	/**
	 * Clear the played hours fields
	 */
	public void clearGamePlayedHours() {
		view.currentGameHoursPlayedLast2Weeks.setText(null);
		view.currentGameHoursPlayedTotal.setText(null);
	}
	
	/**
	 * Clear all Game Tab Components/SubTabs content
	 */
	public void clearGameTab() {

		currentSteamGame = null;
		
		// Tab title and main label
		updateGameTabTitle();
		view.currentGameTitleLabel.translate();
		
		// Reset GameLauncher
		view.currentGameLauncher.setGame(null);
		
		// Sort selectors not affected by configuration
		
		// Clear texts elements
		clearGamePlayedHours();
		
		// Clear graphics elements
		clearFriendsWithSameGamePane();
		clearAchievementsTable();
	}
	
	/**
	 * Clear Most Played Games Pane
	 */
	void clearMostPlayedGamesPane() {
		for (Component component : view.mostPlayedGamesPane.getComponents())
			view.mostPlayedGamesPane.remove(component);
	}
	
	/**
	 * Clear all Profile Tab Components/SubTabs content
	 */
	void clearProfileTab(boolean clearFriendsTab) {
		
		// Tab title
		updateProfileTabTitle();
		updateProfileSummaryTabTitle();
		updateProfileStatusTabTitle();
		
		// Commands bar
		view.currentProfileTitleLabel.setText("");
		
		// Main Tab
		view.gamerProfileIconFull.clearImage();
		view.gamerProfileRealName.setText("");
		view.gamerProfileLocation.setText("");
		view.gamerProfileCustomURL.setText("");
		view.gamerProfileSummary.setText("");
		view.gamerProfileMemberSince.setText("");
		view.gamerProfileSteamRating.setText("");
		view.gamerProfileHoursPlayedLast2Weeks.setText("");
		view.gamerProfileHeadline.setText("");
		
		clearMostPlayedGamesPane();
		
		// Account Status Tab
		view.gamerProfileAccountGamerSteamId64.setText("");
		view.gamerProfileAccountGamerSteamId.setText("");
		view.gamerProfileAccountOnlineState.setText("");
		view.gamerProfileAccountStateMessage.setText("");
		view.gamerProfileAccountPrivacyState.setText("");
		view.gamerProfileAccountVisibilityState.setText("");
		view.gamerProfileAccountVacBanned.setText("");
		view.gamerProfileAccountTradeBanState.setText("");
		view.gamerProfileAccountLimitedAccount.setText("");
		
		// Groups Tab
		clearGroupsTab();
		
		// Friends Tab
		if (clearFriendsTab)
			clearFriendsTab();
	}
	
	/**
	 * Remove GroupButtons from ButtonGroup & Pane
	 */
	void clearGroupsTab() {
		
		// Clear title
    	clearProfileGroupsTabTitle();
		
		// Empty buttons pane
		while (view.steamGroupsButtonGroup.getElements().hasMoreElements()) {
			SteamGroupButton button = (SteamGroupButton) view.steamGroupsButtonGroup.getElements().nextElement();
			view.steamGroupsButtonGroup.remove(button);
			view.steamGroupsButtonsPane.remove(button);
		}
		
		// Empty list pane
		if (steamGroupsTable != null)
			steamGroupsTable.setModel(new SteamGroupsTableModel(null));
	}

		
	/**
	 * Remove FriendButtons from ButtonGroup & Pane
	 */
	void clearFriendsTab() {
		
		// Clear title
		updateProfileFriendsTabTitle();
		
		// Clear buttons pane
		while (view.steamFriendsButtonGroup.getElements().hasMoreElements()) {
			SteamFriendButton button = (SteamFriendButton) view.steamFriendsButtonGroup.getElements().nextElement();
			view.steamFriendsButtonGroup.remove(button);
			view.steamFriendsButtonsPane.remove(button);
		}
		
		// Empty list pane
		if (steamFriendsTable != null)
			steamFriendsTable.setModel(new SteamFriendsTableModel(null));
	}
	
	//
	// Update display
	//
	
	//
	// Update Main tabs display 
	//
	
	/**
	 * Update display of gamesLibrary Tab
	 */
	public void updateGamesLibraryTab() {
		
		// Clear first
		clearGamesLibraryPane();
		
		// Refill gamesLibraryPane / steamGamesTable
		if (parameters.getSteamGamesList() != null && parameters.getSteamGamesList().getSteamGames() != null) {

			// Refill buttonsLibraryPane
			int maxHeight = 0;
			List<LaunchButton> launchButtons = new Vector<LaunchButton>();
			Iterator<SteamGame> gameIterator = parameters.getSteamGamesList().getSteamGames().iterator();
			while (gameIterator.hasNext()) {
				SteamGame game = gameIterator.next();
				LaunchButton button = new LaunchButton(me, "", LaunchType.library, null, game);
				launchButtons.add(button);
				view.buttonsLibraryPane.add(button);
				view.gamesLibraryButtonGroup.add(button);
				// Force dimensions computing
				view.buttonsLibraryPane.validate();
				// To get component height
				if (maxHeight < button.getHeight())
					maxHeight = button.getHeight();
			}
			// Refill steamGamesTable
			steamGamesTable = new SteamGamesTable(me, parameters.getSteamGamesList().getSteamGames(), launchButtons);
			view.gamesLibraryListScrollPane.add(steamGamesTable);
			view.gamesLibraryListScrollPane.setViewportView(steamGamesTable);
			
			// Set scrolling unit
			view.libraryScrollPane.getVerticalScrollBar().setUnitIncrement(maxHeight + ((WrapLayout)view.buttonsLibraryPane.getLayout()).getVgap());
		}
		
		// Update LibraryMainTab TitleLabel
		view.libraryMainTitleLabel.translate();
		
		// Update LibraryMainTab Title
		updateLibraryMainTabTitle();
	}
	
	/**
	 * Update display of libraryStatistics Main Tab and SubTabs
	 */
	public void updateLibraryStatisticsMainTab() {
		
		// Clear first
		clearLibraryStatisticsPane();
		
		// Compute library statistics
		if (parameters.getSteamGamesList() != null && parameters.getSteamGamesList().getSteamGames() != null) {
			
			SteamGamesList steamGamesList = parameters.getSteamGamesList();

			// Display steam global games statistics
			view.libraryTotalGamesTextField.setText(new Integer(steamGamesList.getSteamGames().size()).toString());
			view.libraryTotalGamesWithStatsTextField.setText(steamGamesList.getTotalGamesWithStats().toString());
			view.libraryTotalGamesWithGlobalStatsTextField.setText(steamGamesList.getTotalGamesWithGlobalStats().toString());
			view.libraryTotalGamesWithStoreLinkTextField.setText(steamGamesList.getTotalGamesWithStoreLink().toString());
			
			// Display total games times
			view.libraryTotalWastedHoursTextField.setText(String.format(BundleManager.getResources(me, "decimalTimeFormat"), steamGamesList.getTotalWastedHours()));
			view.libraryTotalHoursLast2WeeksTextField.setText(String.format(BundleManager.getResources(me, "decimalTimeFormat"), steamGamesList.getTotalHoursLast2Weeks()));
			
			// Display total gaming durations
			view.libraryTotalWastedHoursFormattedLabel.setText(Strings.translateDurationDoubleValue(steamGamesList.getTotalWastedHours(), durationTokens));
			view.libraryTotalHoursLast2WeeksFormattedLabel.setText(Strings.translateDurationDoubleValue(steamGamesList.getTotalHoursLast2Weeks(), durationTokens));

			steamGamesList.setAchievementsStatistics();
			
    		view.libraryTotalFinishedGamesTextField.setText(steamGamesList.getStatisticsFetched() ? steamGamesList.getFinishedGamesCount().toString() : BundleManager.getUITexts(me, "unavailable"));
    		view.libraryTotalGamesWithInvalidStatsTextField.setText(steamGamesList.getStatisticsFetched() ? steamGamesList.getGamesWithInvalidStatsCount().toString() : BundleManager.getUITexts(me, "unavailable"));
    		view.libraryTotalAchievementsTextField.setText(steamGamesList.getStatisticsFetched() ? steamGamesList.getAchievementsCount().toString() : BundleManager.getUITexts(me, "unavailable"));
    		view.libraryTotalUnlockedAchievementsTextField.setText(steamGamesList.getStatisticsFetched() ? steamGamesList.getUnlockedAchievementsCount().toString() : BundleManager.getUITexts(me, "unavailable"));
    		
    		if (steamGamesList.getStatisticsFetched()) {
    			// Percentage output formatter
    			NumberFormat percentFormat = NumberFormat.getPercentInstance();
    			percentFormat.setMaximumFractionDigits(2);
    			
    			// Display percentage achieved
    			Double unlockedAchievementsCount = new Double(steamGamesList.getUnlockedAchievementsCount());
    			Double achievementsCount = new Double(steamGamesList.getAchievementsCount());
    			view.libraryPercentageAchievedTextField.setText(percentFormat.format(achievementsCount == 0 ? 0 : unlockedAchievementsCount / achievementsCount));
    			
    			// Display average percentage achieved
    			Double achievementsForAveragePercentageCount = new Double(steamGamesList.getAchievementsForAveragePercentageCount());
    			view.libraryAveragePercentageAchievedTextField.setText(percentFormat.format(achievementsForAveragePercentageCount == 0 ? 0 : unlockedAchievementsCount / achievementsForAveragePercentageCount));
    			
    			// Display average percentage achieved formatted label
    			String labelName = "libraryAveragePercentageAchievedTooltip0";
    			if (unlockedAchievementsCount <= 1)
    				labelName = "libraryAveragePercentageAchievedTooltip0";
    			else
    				if (steamGamesList.getGamesWithUnlockedAchievementsCount() <= 1)
    					labelName = "libraryAveragePercentageAchievedTooltip1";
    				else
    					labelName = "libraryAveragePercentageAchievedTooltip2";
    			view.libraryAveragePercentageAchievedFormattedLabel.setText(String.format(BundleManager.getUITexts(me, labelName), unlockedAchievementsCount.intValue(), steamGamesList.getGamesWithUnlockedAchievementsCount()));
    		} else {
    			view.libraryAveragePercentageAchievedTextField.setText(BundleManager.getUITexts(me, "unavailable"));
    			view.libraryAveragePercentageAchievedFormattedLabel.setText("");
    		}
		}
	}
	
	/**
	 * Update display of all Library Tabs
	 */
	public void updateAllLibraryTabs() {
		updateGamesLibraryTab();
		updateLibraryStatisticsMainTab();
	}
	
	/**
	 * Update display of all Library Tabs using new steamGamesList
	 * @param steamGamesList
	 */
	public void updateGamesLibraryTab(SteamGamesList steamGamesList) {
		parameters.setSteamGamesList(steamGamesList);
		updateAllLibraryTabs();
	}
	
	/**
	 * Update display of GameTab after new selection in gamesLibrary or in steamProfile
	 * @param game
	 */
	public void updateGameTab(SteamGame game) {
		
		currentSteamGame = game;
		
		// Get GameTab index
		int index = getTabComponentIndexByName(view.mainPane, "gameTab");
		if (index < 0 || index >= view.mainPane.getTabCount()) return;
		
		// Update GameTab title
		updateGameTabTitle();

		// Update GameTab Title Label
		view.currentGameTitleLabel.translate();
		
		// Update GameLauncher
		view.currentGameLauncher.setGame(game);
		
		// Update stats
		view.currentGameHoursPlayedTotal.setText(game.getHoursOnRecord());
		view.currentGameHoursPlayedLast2Weeks.setText(game.getHoursLast2Weeks());
		
		// MostPlayedGame does not have an appId
		boolean sameGame = lastSteamGameWithStats != null && game.getAppID() != null && game.getAppID().equalsIgnoreCase(lastSteamGameWithStats.getAppID());
		
		// Update friendsWithSameGamePane & steamAchievementsTable
		if (sameGame || game.getSteamGameStats() != null)
			updateGameTab(currentSteamGame.getSteamGameStats());
		else {
			clearFriendsWithSameGamePane();
			view.steamAchievementsScrollPane.setViewportView(null);
		}
		
		// Update tooltips
		view.updateLoadAllAchievements();
	}

	/**
	 * Update display of GameTab after gameStats reading from Steam community
	 * @param steamGameStats
	 */
	public void updateGameTab(SteamGameStats steamGameStats) {
		
		currentSteamGame.setSteamGameStats(steamGameStats);
		lastSteamGameWithStats = currentSteamGame;
		
		// Get main player AchievementsList avatarIcon
		if (currentSteamGame != null && currentSteamGame.getSteamGameStats() != null && currentSteamGame.getSteamGameStats().getSteamAchievementsList() != null  
				&& currentSteamProfile != null && currentSteamProfile.getAvatarIcon() != null && !currentSteamProfile.getAvatarIcon().trim().equals(""))
				(new SteamAchievementsListIconReader(tee, currentSteamGame.getSteamGameStats().getSteamAchievementsList(), currentSteamProfile.getAvatarIcon(), parameters.getMessages())).execute();

		// Get GameTab index
		int index = getTabComponentIndexByName(view.mainPane, "gameTab");
		if (index < 0 || index >= view.mainPane.getTabCount()) return;
		view.mainPane.setIconAt(index, null);
		
		updateSteamAchievementsPane();
		updateFriendsWithSameGamePane();
//		if (loadAllAchievements)
//			loadAllAchievements();
		//dumpGameStats(steamGameStats);
	}

	/**
	 * Update display of GameTab after profile reading
	 */
	public void updateGameTab() {
		
		// Update GameTab Title Label
		view.currentGameTitleLabel.translate();
		
		// Clear texts elements
		clearGamePlayedHours();
		
		// Clear graphics elements
		clearFriendsWithSameGamePane();
		clearAchievementsTable();
	}
	
	/**
	 * Update display of ProfileTab
	 * @param steamProfile the steamProfile to set
	 */
	public void updateProfileTab(SteamProfile steamProfile) {
		
		// Clear first
		clearProfileTab(false);
		
		// Update current profile
		currentSteamProfile = steamProfile;
		
		// Update ProfileTab Title
		updateProfileTabTitle();
		
		// Update ProfileTab Title Label
		view.currentProfileTitleLabel.translate();
		
		// Update ProfileTab SubTabs titles
		updateProfileSummaryTabTitle();
		updateProfileStatusTabTitle();

		// Main data : Update Profile Main Tab content
		if (steamProfile.getRealname() != null) view.gamerProfileRealName.setText(steamProfile.getRealname());
		if (steamProfile.getLocation() != null) view.gamerProfileLocation.setText(steamProfile.getLocation());
		if (steamProfile.getCustomURL() != null) view.gamerProfileCustomURL.setText(steamProfile.getCustomURL());
		if (steamProfile.getSummary() != null) view.gamerProfileSummary.setText(steamProfile.getSummary());
		
		// Update avatar icon
		if (steamProfile.getAvatarFull() != null)
			view.gamerProfileIconFull.setImage(steamProfile.getAvatarFull());
		else
			view.gamerProfileIconFull.setImage(GamesLibrarianIcons.noAvatarFull);
		
		// Update MostPlayedGamePane
		updateMostPlayedGamesPane(steamProfile);
		
		// Extra data : Update Profile Main Tab content
		if (steamProfile.getMemberSince() != null) view.gamerProfileMemberSince.setText(steamProfile.getMemberSince());
		if (steamProfile.getSteamRating() != null) view.gamerProfileSteamRating.setText(steamProfile.getSteamRating());
		if (steamProfile.getHoursPlayedLast2Weeks() != null) view.gamerProfileHoursPlayedLast2Weeks.setText(steamProfile.getHoursPlayedLast2Weeks());
		
		// Extra data : Update Profile Account Tabcontent
		if (steamProfile.getSteamID64() != null) view.gamerProfileAccountGamerSteamId64.setText(steamProfile.getSteamID64());
		if (steamProfile.getSteamID() != null) view.gamerProfileAccountGamerSteamId.setText(steamProfile.getSteamID());
		if (steamProfile.getOnlineState() != null) view.gamerProfileAccountOnlineState.setText(steamProfile.getOnlineState());
		if (steamProfile.getStateMessage() != null) view.gamerProfileAccountStateMessage.setText(steamProfile.getStateMessage());
		if (steamProfile.getPrivacyState() != null) view.gamerProfileAccountPrivacyState.setText(steamProfile.getPrivacyState());
		if (steamProfile.getVisibilityState() != null) view.gamerProfileAccountVisibilityState.setText(steamProfile.getVisibilityState());
		if (steamProfile.getVacBanned() != null) view.gamerProfileAccountVacBanned.setText(steamProfile.getVacBanned().toString());
		if (steamProfile.getTradeBanState() != null) view.gamerProfileAccountTradeBanState.setText(steamProfile.getTradeBanState());
		if (steamProfile.getIsLimitedAccount() != null) view.gamerProfileAccountLimitedAccount.setText(steamProfile.getIsLimitedAccount().toString());
		
		// Update Profile Groups Tab
		// Extra data : Refill steamGroupsTable
		updateProfileGroupsTab(steamProfile);
		
		// Update Profile Friends Tab
		// Profile Friends Tab is updated by readSteamFriendsList()
		updateProfileFriendsTabTitle();
	}

	//
	// Update Sub tabs display
	//

	/**
	 * Update display of ProfileSummmaryTab Title
	 */
	public void updateProfileSummaryTabTitle() {
		// Find ProfileSummaryTab index
		int index = getTabComponentIndexByName(view.profilePane, "profileSummaryTab");
		if (index < 0 || index >= view.profilePane.getTabCount()) return;
		// Update Tab title
		String steamId = currentSteamProfile != null && currentSteamProfile.getId() != null ?  SteamProfile.htmlIdToText(currentSteamProfile.getId()) : "";
		ResourceBundle UITexts = parameters.getUITexts();
		if (currentSteamProfile != null && currentSteamProfile.getId() != null) {
			view.profilePane.setTitleAt(index, GamesLibrarian.getTabTitle(String.format(UITexts.getString("profileSummaryTabTitleWithName"), steamId)));
			view.profilePane.setIconAt(index, getOnlineStateIcon());
		} else
			view.profilePane.setTitleAt(index, GamesLibrarian.getTabTitle(UITexts.getString("profileSummaryTabTitle")));
	}
	
	/**
	 * Update display of ProfileStatusTab Title
	 */
	public void updateProfileStatusTabTitle() {
		// Find ProfileSummaryTab index
		int index = getTabComponentIndexByName(view.profilePane, "profileStatusTab");
		if (index < 0 || index >= view.profilePane.getTabCount()) return;
		// Update Tab title
		String steamId = currentSteamProfile != null && currentSteamProfile.getId() != null ? SteamProfile.htmlIdToText(currentSteamProfile.getId()) : "";
		ResourceBundle UITexts = parameters.getUITexts();
		if (currentSteamProfile != null && currentSteamProfile.getId() != null)
			view.profilePane.setTitleAt(index, GamesLibrarian.getTabTitle(String.format(UITexts.getString("profileStatusTabTitleWithName"), steamId)));
		else
			view.profilePane.setTitleAt(index, GamesLibrarian.getTabTitle(UITexts.getString("profileStatusTabTitle")));
		view.profilePane.setIconAt(index, getPrivacyStateIcon());
	}

	/**
	 * Update display of MostPlayedGamesPane
	 * @param steamProfile
	 */
	public void updateMostPlayedGamesPane(SteamProfile steamProfile) {
		
		// Clear first
		clearMostPlayedGamesPane();
		
		// Update MostPlayedGamePane
		if (steamProfile.getMostplayedGames() != null && steamProfile.getMostplayedGames().size() > 0) {
			for (SteamGame game : steamProfile.getMostplayedGames()) {
				MostPlayedGameLauncher mostPlayedGameLauncher = new MostPlayedGameLauncher(me);
				mostPlayedGameLauncher.setGame(game);
				view.mostPlayedGamesPane.add(mostPlayedGameLauncher);
			}
		}
	}
	
	/**
	 * Update display of ProfileGroupsTab
	 * @param steamProfile
	 */
	public void updateProfileGroupsTab(SteamProfile steamProfile) {
		
		// Clear first
		clearGroupsTab();

		int maxHeight = 0;
		List<SteamGroup> groups = steamProfile.getSteamGroups();
		if (groups != null && groups.size() > 0) {
			// Refill profileGroupsPane
			Iterator<SteamGroup> groupsIterator = groups.iterator();
			while (groupsIterator.hasNext()) {
				SteamGroup group = groupsIterator.next();
				SteamGroupButton button = new SteamGroupButton(tee, group);
				view.steamGroupsButtonGroup.add(button);
				view.steamGroupsButtonsPane.add(button);
				view.steamGroupsButtonsPane.validate();
				normalize(view.steamGroupsButtonGroup);
				if (maxHeight < button.getHeight())
					maxHeight = button.getHeight();
			}
			view.steamGroupsButtonsPane.revalidate();

			// Refill groupsListScrollPane
			steamGroupsTable = new SteamGroupsTable(me, groups);
			view.steamGroupsListScrollPane.add(steamGroupsTable);
			view.steamGroupsListScrollPane.setViewportView(steamGroupsTable);
			ColumnResizer.adjustColumnPreferredWidths(steamGroupsTable);
			steamGroupsTable.revalidate();

			// Set scrolling unit
			view.steamGroupsScrollPane.getVerticalScrollBar().setUnitIncrement(maxHeight + ((WrapLayout)view.steamGroupsButtonsPane.getLayout()).getVgap());

			// Update tab title label
			updateProfileGroupsTabTitle();
			
		} else {
			// Empty tab title label
			updateProfileGroupsTabTitle();
		}
		
	}
	
	/**
	 * Update display of ProfileFriendsTab
	 * @param steamProfile
	 */
	public void updateProfileFriendsTab(SteamProfile steamProfile) {
		
		// Clear first
		clearFriendsTab();

		int maxHeight = 0;
		List<SteamProfile> friends = steamProfile.getSteamFriends();
		if (friends != null && friends.size() > 0) {
			// Refill profileFriendsPane
			Iterator<SteamProfile> friendsIterator = friends.iterator();
			while (friendsIterator.hasNext()) {
				SteamProfile friend = friendsIterator.next();
				SteamFriendButton button = new SteamFriendButton(tee, me, friend);
				view.steamFriendsButtonGroup.add(button);
				view.steamFriendsButtonsPane.add(button);
				view.steamFriendsButtonsPane.validate();
				normalize(view.steamFriendsButtonGroup);
				if (maxHeight < button.getHeight())
					maxHeight = button.getHeight();
			}
			view.steamFriendsButtonsPane.revalidate();
			
			// Refill friendsListScrollPane
			steamFriendsTable = new SteamFriendsTable(me, friends);
			view.steamFriendsListScrollPane.add(steamFriendsTable);
			view.steamFriendsListScrollPane.setViewportView(steamFriendsTable);
			ColumnResizer.adjustColumnPreferredWidths(steamFriendsTable);
			steamFriendsTable.revalidate();
			
			// Set scrolling unit
			view.steamFriendsScrollPane.getVerticalScrollBar().setUnitIncrement(maxHeight + ((WrapLayout)view.steamFriendsButtonsPane.getLayout()).getVgap());

			// Update tab title label
			updateProfileFriendsTabTitle();
		} else {
			// Empty tab title label
			updateProfileFriendsTabTitle();
		}
		
	}
	
	/**
	 * Update display of ProfileFriendsTab
	 * @param steamProfile
	 */
	public void libraryStatistic(SteamProfile steamProfile) {
		
		// Clear first
		clearFriendsTab();

		int maxHeight = 0;
		List<SteamProfile> friends = steamProfile.getSteamFriends();
		if (friends != null && friends.size() > 0) {
			// Refill profileFriendsPane
			Iterator<SteamProfile> friendsIterator = friends.iterator();
			while (friendsIterator.hasNext()) {
				SteamProfile friend = friendsIterator.next();
				SteamFriendButton button = new SteamFriendButton(tee, me, friend);
				view.steamFriendsButtonGroup.add(button);
				view.steamFriendsButtonsPane.add(button);
				view.steamFriendsButtonsPane.validate();
				normalize(view.steamFriendsButtonGroup);
				if (maxHeight < button.getHeight())
					maxHeight = button.getHeight();
			}
			view.steamFriendsButtonsPane.revalidate();
			
			// Refill friendsListScrollPane
			steamFriendsTable = new SteamFriendsTable(me, friends);
			view.steamFriendsListScrollPane.add(steamFriendsTable);
			view.steamFriendsListScrollPane.setViewportView(steamFriendsTable);
			ColumnResizer.adjustColumnPreferredWidths(steamFriendsTable);
			steamFriendsTable.revalidate();
			
			// Set scrolling unit
			view.steamFriendsScrollPane.getVerticalScrollBar().setUnitIncrement(maxHeight + ((WrapLayout)view.steamFriendsButtonsPane.getLayout()).getVgap());

			// Update tab title label
			updateProfileFriendsTabTitle();
		} else {
			// Empty tab title label
			updateProfileFriendsTabTitle();
		}
		
	}
	
	//
	// Update parameters then components
	//
	
	public void updateSteamGroupsDisplayMode(SteamGroupsDisplayMode steamGroupsDisplayMode) {
		parameters.setSteamGroupsDisplayMode(steamGroupsDisplayMode);
		for (Action action : view.steamGroupsDisplayModeActionGroup.getActions())
			((SteamGroupsDisplayModeAction) action).setTooltip();
        CardLayout cardLayout = (CardLayout) (view.profileGroupsPane.getLayout());
        cardLayout.show(view.profileGroupsPane, steamGroupsDisplayMode.name());
    	displaySubTab(ProfileTabEnum.Groups);
	}
	
	public void updateSteamFriendsDisplayMode(SteamFriendsDisplayMode steamFriendsDisplayMode) {
		parameters.setSteamFriendsDisplayMode(steamFriendsDisplayMode);
		for (Action action : view.steamFriendsDisplayModeActionGroup.getActions())
			((SteamFriendsDisplayModeAction) action).setTooltip();
        CardLayout cardLayout = (CardLayout) (view.profileFriendsPane.getLayout());
        cardLayout.show(view.profileFriendsPane, steamFriendsDisplayMode.name());
        displaySubTab(ProfileTabEnum.Friends);
	}
	
	//
	// Update Tab titles
	//
	
	/**
	 * Update title of 
	 * <ul>
	 * <li>Library Main Tab</li>
	 * <li>Library Tab</li>
	 * </ul>
	 */
	public void updateLibraryMainTabTitle() {
		
		// Get libraryMainTab index
		int index = getTabComponentIndexByName(view.mainPane, "libraryMainTab");
		if (index < 0 || index >= view.mainPane.getTabCount()) return;
		
		// Get libraryTab index
		int libraryTabIndex = getTabComponentIndexByName(view.libraryMainPane, "libraryTab");
		if (libraryTabIndex < 0 || libraryTabIndex >= view.libraryMainPane.getTabCount()) return;
		
		// Get libraryStatisticsTab index
		int libraryStatisticsTabIndex = getTabComponentIndexByName(view.libraryMainPane, "libraryStatisticsTab");
		if (libraryStatisticsTabIndex < 0 || libraryStatisticsTabIndex >= view.libraryMainPane.getTabCount()) return;
		
		// Update Tab title
		String steamId = SteamProfile.htmlIdToText(currentSteamProfile.getId());
		ResourceBundle UITexts = parameters.getUITexts();
		if (steamGamesListReading || allSteamGamesStatsReading) {
			String title = GamesLibrarian.getTabTitle(UITexts.getString("libraryMainTabTitle"));
			view.mainPane.setIconAt(index, GamesLibrarianIcons.ajaxLoaderIcon);
			view.mainPane.setTitleAt(index, title);
			if (steamGamesListReading) {
				view.libraryMainPane.setIconAt(libraryTabIndex, GamesLibrarianIcons.ajaxLoaderIcon);
				view.libraryMainPane.setTitleAt(libraryTabIndex, title);
			} else if (allSteamGamesStatsReading) {
				title = GamesLibrarian.getTabTitle(BundleManager.getUITexts(me, "libraryStatisticsTabTitle"));
				view.libraryMainPane.setIconAt(libraryStatisticsTabIndex, GamesLibrarianIcons.ajaxLoaderIcon);
				view.libraryMainPane.setTitleAt(libraryStatisticsTabIndex, title);
			}
		} else {
			view.mainPane.setIconAt(index, null);
			view.libraryMainPane.setIconAt(libraryTabIndex, GamesLibrarianIcons.libraryMenuIcon);
			view.libraryMainPane.setIconAt(libraryStatisticsTabIndex, GamesLibrarianIcons.libraryStatisticsMenuIcon);
			String statisticsTitle = GamesLibrarian.getTabTitle(BundleManager.getUITexts(me, "libraryStatisticsTabTitle"));
			if (parameters.getSteamGamesList() != null && parameters.getSteamGamesList().getSteamGames() != null) {
				String title = GamesLibrarian.getTabTitle(String.format(UITexts.getString("libraryMainTabTitleWithNumber"), steamId, parameters.getSteamGamesList().getSteamGames().size()));
				view.mainPane.setTitleAt(index, title);
				view.libraryMainPane.setTitleAt(libraryTabIndex, title);
				view.libraryMainPane.setTitleAt(libraryStatisticsTabIndex, statisticsTitle);
			} else {
				String title = GamesLibrarian.getTabTitle(UITexts.getString("libraryMainTabTitle"));
				view.mainPane.setTitleAt(index, title);
				view.libraryMainPane.setTitleAt(libraryTabIndex, title);
				view.libraryMainPane.setTitleAt(libraryStatisticsTabIndex, statisticsTitle);
			}
		}
		
		
	}
	
	/**
	 * Update title of gameTab with specified data
	 * @param steamId the steamId to print
	 * @param index Tab index 
	 * @param game game whom name has to be extracted for completing the tab title
	 */
	private void updateGameTabTitleId(String steamId, int index, SteamGame game) {
		if (lastSteamGameWithStats != null && game.getAppID() != null && game.getAppID().equalsIgnoreCase(lastSteamGameWithStats.getAppID())) {
			int friendsWithSameGame = currentSteamProfile.getFriendsWithSameGameCount(game);
			view.mainPane.setTitleAt(index, friendsWithSameGame <= 1 ? 
					GamesLibrarian.getTabTitle(String.format(BundleManager.getUITexts(me, "gameTabTitleWithNameAndFriend"), game.getName(), steamId, friendsWithSameGame)) 
					: GamesLibrarian.getTabTitle(String.format(BundleManager.getUITexts(me, "gameTabTitleWithNameAndFriends"), game.getName(), steamId, friendsWithSameGame)));	
		} else {
			view.mainPane.setTitleAt(index, GamesLibrarian.getTabTitle(String.format(BundleManager.getUITexts(me, "gameTabTitleWithName"), game.getName(), steamId)));
		}
	}
	
	/**
	 * Update title of GameTab
	 */
	public void updateGameTabTitle() {
		// Get gameTab index
		int index = getTabComponentIndexByName(view.mainPane, "gameTab");
		if (index < 0 || index >= view.mainPane.getTabCount()) return;
		// Update Tab title
		String steamId = currentSteamProfile != null && currentSteamProfile.getId() != null ? SteamProfile.htmlIdToText(currentSteamProfile.getId()) : "";
		SteamGame game = view.getLibrarian().getCurrentSteamGame();
		ResourceBundle UITexts = parameters.getUITexts();
		if (steamGameStatsReading) {
			updateGameTabTitleId(steamId, index, game);
			view.mainPane.setIconAt(index, GamesLibrarianIcons.ajaxLoaderIcon);
		} else {
			if (view.getLibrarian() != null && view.getLibrarian().getCurrentSteamGame() != null && !view.getLibrarian().getCurrentSteamGame().getName().equals("")) {
				updateGameTabTitleId(steamId, index, game);
				if (game.getIcon() != null && !game.getIcon().isEmpty()) { 
					URL urlIcon;
					try {
						urlIcon = new URL(game.getIcon());
						view.mainPane.setIconAt(index, new ImageIcon(urlIcon));
						return;
					} catch (MalformedURLException e) {
						tee.printStackTrace(e);
					}
				} else
					view.mainPane.setIconAt(index, null);
			} else {
				view.mainPane.setTitleAt(index, GamesLibrarian.getTabTitle(UITexts.getString("gameTabTitle")));
				view.mainPane.setIconAt(index, null);
			}
		}
	}
	
	/**
	 * Update display of ProfileTab to show reading state
	 */
	public void updateProfileTabReading() {
		clearProfileTab(true);
		String steamId = SteamProfile.htmlIdToText(currentSteamProfile.getId());
		view.currentProfileTitleLabel.setText(String.format(parameters.getUITexts().getString("currentProfileTitleLabelReading"), steamId));
	}
	
	/**
	 * Update title of ProfileTab
	 */
	public void updateProfileTabTitle() {
		// Get profileTab index
		int index = getTabComponentIndexByName(view.mainPane, "profileTab");
		if (index < 0 || index >= view.mainPane.getTabCount()) return;
		// Update Tab title
		String steamId = currentSteamProfile != null && currentSteamProfile.getId() != null ? SteamProfile.htmlIdToText(currentSteamProfile.getId()) : "";
		ResourceBundle UITexts = parameters.getUITexts();
		if (steamProfileReading || steamFriendsListReading || steamFriendsGameListsReading) {
			view.mainPane.setTitleAt(index, GamesLibrarian.getTabTitle(String.format(parameters.getUITexts().getString("currentProfileTitleLabelReading"), steamId)));
			view.mainPane.setIconAt(index, GamesLibrarianIcons.ajaxLoaderIcon);
		} else {
			if (currentSteamProfile != null && steamId!= null && !steamId.trim().equals("")) {
				view.mainPane.setTitleAt(index, GamesLibrarian.getTabTitle(String.format(UITexts.getString("profileTabTitleWithName"), steamId)));
				view.mainPane.setIconAt(index, getOnlineStateIcon());
			} else {
				view.mainPane.setTitleAt(index, GamesLibrarian.getTabTitle(UITexts.getString("profileTabTitle")));
				view.mainPane.setIconAt(index, null);				
			}
		}
	}
	
	public void clearProfileGroupsTabTitle() {
		// Find profileGroupsTab index
		int index = getTabComponentIndexByName(view.profilePane, "profileGroupsTab");
		if (index < 0 || index >= view.profilePane.getTabCount()) return;
		// Update Tab title
		ResourceBundle UITexts = parameters.getUITexts();
		view.profilePane.setTitleAt(index, GamesLibrarian.getTabTitle(UITexts.getString("profileGroupsTabTitle")));
	}
	
	/**
	 * Update title of profileGroupsTab
	 */
	public void updateProfileGroupsTabTitle() {
		// Find profileGroupsTab index
		int index = getTabComponentIndexByName(view.profilePane, "profileGroupsTab");
		if (index < 0 || index >= view.profilePane.getTabCount()) return;
		// Update Tab title
		String steamId = SteamProfile.htmlIdToText(currentSteamProfile.getId());
		ResourceBundle UITexts = parameters.getUITexts();
		if (currentSteamProfile != null && currentSteamProfile.getSteamGroups().size() >= 0)
			view.profilePane.setTitleAt(index, GamesLibrarian.getTabTitle(String.format(UITexts.getString("profileGroupsTabTitleWithNumber"), steamId, currentSteamProfile.getSteamGroups().size())));
		else
			view.profilePane.setTitleAt(index, GamesLibrarian.getTabTitle(UITexts.getString("profileGroupsTabTitle")));
		view.profilePane.setIconAt(index, GamesLibrarianIcons.groupsIcon);
	}
	
	/**
	 * Update title of profileFriendsTab
	 */
	public void updateProfileFriendsTabTitle() {
		// Find profileFriendsTab index
		int index = getTabComponentIndexByName(view.profilePane, "profileFriendsTab");
		if (index < 0 || index >= view.profilePane.getTabCount()) return;
		// Update Tab title
		String steamId = currentSteamProfile != null && currentSteamProfile.getId() != null ? SteamProfile.htmlIdToText(currentSteamProfile.getId()) : "";
		ResourceBundle UITexts = parameters.getUITexts();
		if (steamProfileReading || steamFriendsListReading || steamFriendsGameListsReading) {
			if (steamFriendsListReading || steamFriendsGameListsReading) {
				updateProfileTabTitle();
				view.profilePane.setIconAt(index, GamesLibrarianIcons.ajaxLoaderIcon);
			} else
				view.profilePane.setIconAt(index, GamesLibrarianIcons.friendsIcon);
			if (steamFriendsListReading)
				view.profilePane.setTitleAt(index, GamesLibrarian.getTabTitle(String.format(UITexts.getString("profileFriendsTabTitleWithNumber"), steamId, view.profileFriendsPane.getComponentCount())));
			else
				view.profilePane.setTitleAt(index, GamesLibrarian.getTabTitle(UITexts.getString("profileFriendsTabTitle")));
		} else {
			view.profilePane.setIconAt(index, GamesLibrarianIcons.friendsIcon);
			if (currentSteamProfile != null && currentSteamProfile.getSteamFriends().size() >= 0)
				view.profilePane.setTitleAt(index, GamesLibrarian.getTabTitle(String.format(UITexts.getString("profileFriendsTabTitleWithNumber"), steamId, currentSteamProfile.getSteamFriends().size())));
			else
				view.profilePane.setTitleAt(index, GamesLibrarian.getTabTitle(UITexts.getString("profileFriendsTabTitle")));
		}
	}
	
	//
	// Update display of individual components
	//
	
	//
	// Update display of ControlTab components
	//
	
	/**
	 * Update display of GameChoice
	 */
	protected void displayGameChoice() {
		switch (parameters.getGameChoice()) {
		case One: view.oneGameRadioButton.setSelected(true); break;
		case Two: view.twoGamesRadioButton.setSelected(true); break;
		case Three: view.threeGamesRadioButton.setSelected(true); break;
		default: break;
		}
	}
	
	public void updateSelectedSteamProfile(SteamProfile steamProfile) {
		view.knownProfilesComboBox.setSelectedItem(steamProfile);
	}
	
	/**
	 * Update display of LaunchButtons
	 * @param gameChoice
	 */
	public void displayLaunchButtons(GameChoice gameChoice) {
		parameters.setGameChoice(gameChoice);
		switch (gameChoice) {
		case One: 
			if (!view.oneGameRadioButton.isSelected())
				view.oneGameRadioButton.setSelected(true);
			if (!view.oneGameChoiceMenuItem.isSelected())
				view.oneGameChoiceMenuItem.setSelected(true);
			view.gameLauncher1.setVisible(true);
			view.gameLauncher2.setVisible(false);
			view.gameLauncher3.setVisible(false);
			break;
		case Two: 
			if (!view.twoGamesRadioButton.isSelected())
				view.twoGamesRadioButton.setSelected(true);
			if (!view.twoGamesChoiceMenuItem.isSelected())
				view.twoGamesChoiceMenuItem.setSelected(true);
			view.gameLauncher1.setVisible(true);
			view.gameLauncher2.setVisible(true);
			view.gameLauncher3.setVisible(false);
			break;
		case Three: 
			if (!view.threeGamesRadioButton.isSelected())
				view.threeGamesRadioButton.setSelected(true);
			if (!view.threeGamesChoiceMenuItem.isSelected())
				view.threeGamesChoiceMenuItem.setSelected(true);
			view.gameLauncher1.setVisible(true);
			view.gameLauncher2.setVisible(true);
			view.gameLauncher3.setVisible(true);
			break;
		default: break;
		}
	}

	/**
	 * Update display of a LaunchButton ToolTip on Library pane according to launchButton
	 * @param launchButton
	 */
	public void updateLibraryTooltip(LaunchButton launchButton) {
		Enumeration<AbstractButton> buttons = view.gamesLibraryButtonGroup.getElements();
		while (buttons.hasMoreElements()) {
			LaunchButton button = (LaunchButton) buttons.nextElement();
			if (button.getGame().getAppID().equals(launchButton.getGame().getAppID())) {
				button.updateTooltip();
				break;
			}
		}
	}
	
	/**
	 * Update display of gameLaunchers SteamLaunchMethod selector to reflect new SteamLaunchMethod attached to LaunchButton
	 * @param launchButton
	 * @param game
	 */
	public void updateSteamLaunchMethod(LaunchButton launchButton, SteamGame game) {
		for (GameLauncher gameLauncher : gameLaunchers) {
			if (gameLauncher.getGame() == null) break;
			if (game == null) {
				if (gameLauncher.getGame().equals(launchButton.getGame())) {
					// Update selected steamLaunchMethod
					gameLauncher.getSteamLaunchMethodComboBox().setSelectedItem(launchButton.getGame().getSteamLaunchMethod());
					// Update LaunchButton Tooltip
					gameLauncher.updateTooltip();
					break;
				}
			} else {
				if (gameLauncher.getLaunchButton().equals(launchButton)) {
					// Update selected steamLaunchMethod
					gameLauncher.getSteamLaunchMethodComboBox().setSelectedItem(game != null && game.getSteamLaunchMethod() != null ? 
							game.getSteamLaunchMethod() : 
								parameters.getDefaultSteamLaunchMethod());
					break;
				}
			}
		}
	}
	
	/**
	 * Update display of GameLauncher according to LaunchButton
	 * @param launchButton
	 */
	public void updateGameLauncher(LaunchButton launchButton) {
		updateSteamLaunchMethod(launchButton, null);
	}
	
	/**
     * Update display of messages area
	 * @param message
	 * @param color
	 */
	public void appendMessage(String message, Color color) {
		Document document = view.consoleTextPane.getDocument();
		SimpleAttributeSet attributes = new SimpleAttributeSet();
	    StyleConstants.setForeground(attributes, color);
		try {
			document.insertString(document.getLength(), message, attributes);
			if (!parameters.isScrollLocked()) 
				view.consoleTextPane.setCaretPosition(document.getLength());
		} catch (BadLocationException e) {
			tee.printStackTrace(e);
		}
	}

	//
	// Update display of GameTab components
	//
	
	/**
	 * Update display of gamesLibraryListScrollPane
	 * @param launchButtons
	 */
	public void updateSteamGamesTable(List<LaunchButton> launchButtons) {
		// Fill steamGamesTable Tab
		if (parameters != null) {
			steamGamesTable = new SteamGamesTable(me, parameters.getSteamGamesList() != null ? parameters.getSteamGamesList().getSteamGames() : null, launchButtons);
			view.gamesLibraryListScrollPane.add(steamGamesTable);
			view.gamesLibraryListScrollPane.setViewportView(steamGamesTable);
		}
	}
	
	/**
	 * Sort new data
	 * Add to steamAchievementsTable
	 * Update display of SteamAchievementsPane 
	 * with friend statistics
	 * @param friend
	 * @param stats
	 * @param hasDoneSignal
	 */
	public void updateSteamAchievementsPane(SteamProfile friend, SteamGameStats stats, boolean hasDoneSignal) {
		if (stats != null && stats.getSteamAchievementsList() != null && stats.getSteamAchievementsList().getSteamAchievements() != null) {
			Collections.sort(stats.getSteamAchievementsList().getSteamAchievements(), ((SteamAchievementsTableModel)steamAchievementsTable.getModel()).getSteamAchievementsComparator());
			steamAchievementsTable.addColumn(steamAchievementsTable.addColumn(stats.getSteamAchievementsList()));
			sortSteamAchievementsList(null);
			if (hasDoneSignal)
				checkFriendWithSameGameButton(friend);
		}
	}
	
	/**
	 * Update display of Achievements Table Pane
	 * with main player statistics
	 */
	public void updateSteamAchievementsPane() {
		
		// Clear first
		clearAchievementsTable();
		if (parameters != null && currentSteamGame != null && currentSteamGame.getSteamGameStats() != null && currentSteamGame.getSteamGameStats().getSteamAchievementsList() != null && currentSteamGame.getSteamGameStats().getSteamAchievementsList().getSteamAchievements() != null && !currentSteamGame.getSteamGameStats().getSteamAchievementsList().getSteamAchievements().isEmpty()) {
			// Refill steamAchievementsTable
			// with current achievements sort method
			steamAchievementsTable = new SteamAchievementsTable(this, currentSteamGame.getSteamGameStats().getSteamAchievementsList(), (SteamAchievementsSortMethod) view.steamAchievementsSortMethodComboBox.getSelectedItem(), (SteamAchievementsListsSortMethod) view.steamAchievementsListsSortMethodComboBox.getSelectedItem());
			if (currentSteamGame.getSteamGameStats() != null && currentSteamGame.getSteamGameStats().getSteamAchievementsList() != null && currentSteamGame.getSteamGameStats().getSteamAchievementsList().getSteamAchievements() != null) {
				Collections.sort(currentSteamGame.getSteamGameStats().getSteamAchievementsList().getSteamAchievements(), ((SteamAchievementsTableModel)steamAchievementsTable.getModel()).getSteamAchievementsComparator());
			}
			view.steamAchievementsScrollPane.add(steamAchievementsTable);
			view.steamAchievementsScrollPane.setViewportView(steamAchievementsTable);
			// FIXME: better method ?
//			view.steamAchievementsScrollPane.paintAll(view.steamAchievementsScrollPane.getGraphics());
		}
	}
	
	//
	// Update display of ProfileTab components
	//

	public void updateKnownProfileComboBox() {
		if (view.knownProfilesComboBox.getSelectedItem() != null && !((SteamProfile) view.knownProfilesComboBox.getSelectedItem()).equals(currentSteamProfile))
			view.knownProfilesComboBox.setSelectedItem(currentSteamProfile);
	}
	
	public int getFriendsWithSameGame() {
		return view != null && view.friendsWithSameGamePane != null ? view.friendsWithSameGamePane.getComponents().length : -1;
	}
	
	public int achievementsLoaded() {
		int friendWithSameGameButtons = 0;
		int selectedButtons = 0;
		Component[] components = view != null && view.friendsWithSameGamePane != null ? view.friendsWithSameGamePane.getComponents() : null;
		int limit = components != null ? components.length : -1;
		for (int index = 0; index < limit; index++) {
			if (components[index] instanceof SteamFriendWithSameGameButton) {
				friendWithSameGameButtons++;
				SteamFriendWithSameGameButton friendWithSameGameButton = (SteamFriendWithSameGameButton) components[index];
				if (friendWithSameGameButton.isSelected())
					selectedButtons++;
			}
		}
		return friendWithSameGameButtons > 0 ? (selectedButtons == friendWithSameGameButtons ? friendWithSameGameButtons : selectedButtons) : -1;
	}
	
	/**
	 * Add a FriendWithSameGameButton for each friend with same game
	 */
	public void updateFriendsWithSameGamePane() {
		if (currentSteamGame == null || steamAchievementsTable == null || currentSteamProfile == null || currentSteamProfile.getSteamFriends() == null)
			return;
		
		// Remove old friends and success
		if (steamAchievementsTable.getColumnCount() > 1)
			for (SteamProfile friend : currentSteamProfile.getSteamFriends())
				if (friend.hasGame(currentSteamGame))
					removeFriendAchievements(friend);
		clearFriendsWithSameGamePane();
		
		// Add new friends
		Integer initialPosition = 1;
		String appId = currentSteamGame.digAppID(); // MostPlayedGame does not have an appId		
		for (SteamProfile friend : currentSteamProfile.getSteamFriends()) {
			for (SteamGame game : friend.getSteamGames()) {
				if (game.getAppID().equals(appId)) {
					view.friendsWithSameGamePane.add(new SteamFriendWithSameGameButton(this, friend, initialPosition++, currentSteamGame.getSteamGameStats() != null));
					view.friendsWithSameGamePane.validate();
					normalize(view.friendsWithSameGamePane.getComponents());
					break;
				}
			}
		}
		view.friendsWithSameGamePane.revalidate();
		updateGameTabTitle();
		view.updateLoadAllAchievements();
	}

	/**
	 * Determine MainTab to display from tabEnum type
	 * @param tabEnum
	 * @return the matching JPanel or null
	 */
	public JPanel getMainTabFromEnum(TabEnum tabEnum) {
		if (tabEnum instanceof ProfileTabEnum)
			return view.gamesLibrarianProfile;
		else if (tabEnum instanceof LibraryTabEnum)
			return view.gamesLibrarianLibrary;
		return null;
	}

	/**
	 * Determine SubTab container to display from tabEnum type
	 * @param tabEnum
	 * @return the matching JTabbedPane or null
	 */
	public JTabbedPane getSubTabFromEnum(TabEnum tabEnum) {
		if (tabEnum instanceof ProfileTabEnum)
			return view.profilePane;
		else if (tabEnum instanceof LibraryTabEnum)
			return view.libraryMainPane;
		return null;
	}

	/**
	 * Get JPanel Main Tab component from TabEnum
	 * @param tabEnum enumeration element identifying the tab 
	 * @return the matching JPanel or null
	 */
	public JPanel getTabFromEnum(TabEnum tabEnum) {
		if (tabEnum instanceof LibrarianTabEnum)
		switch ((LibrarianTabEnum)tabEnum) {
		case Controls: return view.gamesLibrarianControls;
		case Library: return view.gamesLibrarianLibrary;
		case Game: return view.gamesLibrarianGame;
		case Profile: return view.gamesLibrarianProfile;
		case Options: return view.gamesLibrarianOptions;
		} else if (tabEnum instanceof ProfileTabEnum)
			switch ((ProfileTabEnum)tabEnum) {
			case Summary: return view.profileSummaryPane;
			case Status: return view.profileStatusPane;
			case Groups: return view.profileGroupsPane;
			case Friends: return view.profileFriendsPane;
			}
		else if (tabEnum instanceof LibraryTabEnum)
			switch ((LibraryTabEnum)tabEnum) {
			case LibraryGamesList: return view.libraryPane;
			case LibraryStatistics: return view.libraryStatisticsMainPane;
			}
		return null;
	}
	
	/**
	 * Display tab according to tabEnum, if not already displayed
	 * @param tab
	 */
	public void displayMainTab(TabEnum tabEnum) {
		JPanel tab = tabEnum instanceof LibrarianTabEnum ? getTabFromEnum(tabEnum) : getMainTabFromEnum(tabEnum);
		if (tab == null) return;
		if (!view.mainPane.getSelectedComponent().equals(tab))
			view.mainPane.setSelectedComponent(tab);
	}
	
	/**
	 * Display passed mainTab/tab according to tabEnum, if not already displayed
	 * @param tab
	 */
	public void displaySubTab(TabEnum tabEnum) {
		JTabbedPane subTab = getSubTabFromEnum(tabEnum);
		JPanel tab = getTabFromEnum(tabEnum);
		if (subTab == null || tab == null) return;
		displayMainTab(tabEnum);
		if (!subTab.getSelectedComponent().equals(tab))
			subTab.setSelectedComponent(tab);
	}
	
	/**
	 * Check a FriendWithSameGameButton
	 * @param friend
	 */
	public void checkFriendWithSameGameButton(SteamProfile friend) {
		Component[] components = view.friendsWithSameGamePane.getComponents();
		for (int index = 0; index < components.length; index++) {
			if (components[index] instanceof SteamFriendWithSameGameButton) {
				SteamFriendWithSameGameButton friendWithSameGameButton = (SteamFriendWithSameGameButton) components[index];
				if (friendWithSameGameButton.getSteamProfile().getSteamID64().equalsIgnoreCase(friend.getSteamID64()))
					friendWithSameGameButton.setSelected(true);
			}
		}
	}
	
	/**
	 * Update display of all LeftClickActionButton and Label
	 */
	public void updateAllLeftClickActionButtonAndLabel() {
		view.libraryLeftClickSelectButton.translate();
		view.libraryLeftClickLaunchButton.translate();
		view.gameLeftClickSelectButton.translate();
		view.gameLeftClickLaunchButton.translate();
		view.profileLeftClickSelectButton.translate();
		view.profileLeftClickLaunchButton.translate();
	}
	
	/**
	 * Update Parameters with new value of displayTooltips
	 * Update Tool tips according to the new value
	 * @param displayTooltips the new value
	 */
	public void updateDisplayTooltips(Boolean displayTooltips) {
		parameters.setDisplayTooltips(displayTooltips);
		if (displayTooltips) {
			// TODO: Finish updateDisplayTooltips
		} else {
			// OR NOT
		}
	}

	/**
	 * Update Parameters with new value of buttonsDisplayMode
	 * Update display of command buttons according to the new value
	 * @param buttonsDisplayMode the new value
	 */
	public void updateCommandButtons(ButtonsDisplayMode buttonsDisplayMode) {
		parameters.setButtonsDisplayMode(buttonsDisplayMode);
		updateButtonsDisplayModeObservables();
	}

	/**
	 * Change Look And Feel
	 */
	public void setLookAndFeel(LookAndFeelInfo newLookAndFeelInfo) {
		try {
			// Find and install new Look and feel
			for (LookAndFeelInfo lookAndFeelInfo : Parameters.lookAndFeelInfos) {
				if (newLookAndFeelInfo.getName().equals(lookAndFeelInfo.getName())) {
					UIManager.setLookAndFeel(newLookAndFeelInfo.getClassName());
		            SwingUtilities.updateComponentTreeUI(view);
		            break;
	        	}
		    }
		} catch (UnsupportedLookAndFeelException
				|ClassNotFoundException
				|InstantiationException
				|IllegalAccessException e) {
			String message = String.format(
					parameters.getMessages().getString("errorInstallingLookAndFeel"),
					newLookAndFeelInfo.getName(),
					e.getLocalizedMessage() != null ? 
							e.getLocalizedMessage()
							: (e.getMessage() != null ?
									e.getMessage()
									: (e.getCause() != null ?
											e.getCause()
											: parameters.getMessages().getString("unknownErrorSource"))));
			tee.writelnError(message);
		}
	}
	
	/**
	 * Validate and complete all missing new application parameters with previousParameters set values.<br/>
	 * Update all UI elements impacted by a new application parameters set coming from:
	 * <ul>
	 * <li>Setup set</li>
	 * <li>Loaded set from a configuration file </li>
	 * </ul> 
	 * @param previousParameters prior parameters set used to complete new set
	 * @param startup indicate if application is in startup state. This state needs special UI actions (developper's preferred L&F, initial configuration...)
	 */
	public void updateUI(Parameters previousParameters, boolean startup) {
		
		//
		// Options Tab
		//
		
		// Update localeChoice
		if (parameters.getLocaleChoice() == null)
			if (previousParameters.getLocaleChoice() == null) {
				parameters.setLocaleChoice(Parameters.defaultLocaleChoice);
				view.localeChoiceComboBox.setSelectedItem(parameters.getLocaleChoice());
			} else
				parameters.setLocaleChoice(previousParameters.getLocaleChoice());
		else
			view.localeChoiceComboBox.setSelectedItem(parameters.getLocaleChoice());
		
		// Update lookAndFeelInfo
		// Note: when starting application, try Nimbus, a modern glassy L&F
		if (startup || parameters.getLookAndFeelInfo() != null) {
			int lookAndFeelInfoIndex = -1;
		    for (LookAndFeelInfo lookAndFeelInfo : Parameters.lookAndFeelInfos) {
		    	lookAndFeelInfoIndex++;
		        if ((startup && "Nimbus".equals(lookAndFeelInfo.getName())) || (!startup && parameters.getLookAndFeelInfo().getName().equals(lookAndFeelInfo.getName()))) {
		        	if (startup)
		        		parameters.setLookAndFeelInfo(Parameters.lookAndFeelInfos[lookAndFeelInfoIndex]);
		        	view.lookAndFeelInfoComboBox.setSelectedIndex(lookAndFeelInfoIndex);
		            break;
		        }
		    }
		} else
			if (previousParameters.getLookAndFeelInfo() == null) {
				parameters.setLookAndFeelInfo(Parameters.defaultLookAndFeelInfo);
				view.lookAndFeelInfoComboBox.setSelectedItem(parameters.getLookAndFeelInfo());
			} else
				parameters.setLookAndFeelInfo(previousParameters.getLookAndFeelInfo());
		
		//
		// Controls Tab
		//
		
		// Update scrollLock
		if (parameters.isScrollLocked() == null)
			if (previousParameters.isScrollLocked() == null) {
				parameters.setScrollLocked(Parameters.defaultScrollLocked);
				view.scrollLockButton.setSelected(parameters.isScrollLocked());
			} else
				parameters.setScrollLocked(previousParameters.isScrollLocked());
		else
			view.scrollLockButton.setSelected(parameters.isScrollLocked());

		// Update debug
		if (parameters.isDebug() == null)
			if (previousParameters.isDebug() == null) {
				parameters.setDebug(Parameters.defaultDebug);
				view.debugButton.setSelected(parameters.isDebug());
			} else
				parameters.setDebug(previousParameters.isDebug());
		else
			view.debugButton.setSelected(parameters.isDebug());
		if (parameters.isDebug())
			tee.writeMessage(BundleManager.getMessages(me, parameters.getDefaultSteamLaunchMethod().getDefaultSelectionMessageKey()), true);
		
		//
		// Options Tab
		//
		
		// Update dumpMode
		if (parameters.getDumpMode() == null)
			if (previousParameters.getDumpMode() == null) {
				parameters.setDumpMode(Parameters.defaultDumpMode);
				view.dumpModeComboBox.setSelectedItem(parameters.getDumpMode());
			} else
				parameters.setDumpMode(previousParameters.getDumpMode());
		else
			view.dumpModeComboBox.setSelectedItem(parameters.getDumpMode());
		
		// Update registry options fields
		if (parameters.getWindowsDistribution() == null)
			if (previousParameters.getWindowsDistribution() == null) {
				parameters.setWindowsDistribution(Parameters.defaultWindowsDistribution);
				view.windowsDistributionTextField.setText(parameters.getWindowsDistribution());
			} else
				parameters.setWindowsDistribution(previousParameters.getWindowsDistribution());
		else
			view.windowsDistributionTextField.setText(parameters.getWindowsDistribution());
		
		if (parameters.getSteamExecutable() == null)
			if (previousParameters.getSteamExecutable() == null) {
				parameters.setSteamExecutable(Parameters.defaultSteamExecutable);
				view.steamExecutableTextField.setText(parameters.getSteamExecutable());
			} else
				parameters.setSteamExecutable(previousParameters.getSteamExecutable());
		else
			view.steamExecutableTextField.setText(parameters.getSteamExecutable());
		
		//
		// Profile Tab
		//
		
		// Update steamGroupsSortMethod/steamFriendsSortMethod selectors
		if (startup) {
			view.steamGroupsSortMethodComboBox.setSelectedItem(SteamGroupsSortMethod.values()[0]);
			view.steamFriendsSortMethodComboBox.setSelectedItem(SteamFriendsSortMethod.values()[0]);
		}
		
		// Update steamGroupsDisplayMode selector 
		if (startup)
			parameters.setSteamGroupsDisplayMode(view.profileGroupsPane != null && getCurrentCard(view.profileGroupsPane).getName() != null ? // WindowBuilder
					SteamGroupsDisplayMode.valueOf(getCurrentCard(view.profileGroupsPane).getName()) :
						SteamGroupsDisplayMode.values()[0]);
		if (parameters.getSteamGroupsDisplayMode() == null)
			if (previousParameters.getSteamGroupsDisplayMode() == null)
				parameters.setSteamGroupsDisplayMode(Parameters.defaultSteamGroupsDisplayMode);
			else
				parameters.setDumpMode(previousParameters.getDumpMode());
		switch (parameters.getSteamGroupsDisplayMode()) {
		case Grid:
			view.steamGroupsDisplayModeGrid.setSelected(true);
			break;
		case List :
			view.steamGroupsDisplayModeList.setSelected(true);
			break;
		}
		
		// Update steamFriendsDisplayMode selector 
		if (startup)
			parameters.setSteamFriendsDisplayMode(view.profileFriendsPane != null && getCurrentCard(view.profileFriendsPane).getName() != null ? // WindowBuilder
					SteamFriendsDisplayMode.valueOf(getCurrentCard(view.profileFriendsPane).getName()) :
						SteamFriendsDisplayMode.values()[0]);
		if (parameters.getSteamFriendsDisplayMode() == null)
			if (previousParameters.getSteamFriendsDisplayMode() == null)
				parameters.setSteamFriendsDisplayMode(Parameters.defaultSteamFriendsDisplayMode);
			else
				parameters.setSteamFriendsDisplayMode(previousParameters.getSteamFriendsDisplayMode());
		switch (parameters.getSteamFriendsDisplayMode()) {
		case Grid:
			view.steamFriendsDisplayModeGrid.setSelected(true);
			break;
		case List :
			view.steamFriendsDisplayModeList.setSelected(true);
			break;
		}
		
		// Update currentSteamProfile and UI
		if (parameters.getMainPlayerSteamId() != null && !parameters.getMainPlayerSteamId().equals("")) {
			SteamProfile currentProfile = new SteamProfile();
			if (Strings.fullNumericPattern.matcher(parameters.getMainPlayerSteamId()).matches())
				currentProfile.setSteamID64(parameters.getMainPlayerSteamId());
			else
				currentProfile.setSteamID(parameters.getMainPlayerSteamId());
			currentProfile.setLoadingSource(LoadingSource.Preloading);
			updateProfileTab(currentProfile);
			addProfile(currentProfile, true);
			updateSelectedSteamProfile(currentProfile);
			
			//
			// Options Tab
			//
			
			// Continue updating Registry Options Fields
			getGamerSteamIdTextField().setText(currentSteamProfile.getId());
		} else
			parameters.setMainPlayerSteamId(previousParameters.getMainPlayerSteamId());
		
		
		//
		// Controls Tab
		//
		
		// Update gameChoice
		if (parameters.getGameChoice() == null)
			if (previousParameters.getGameChoice() == null) {
				parameters.setGameChoice(Parameters.defaultGameChoice);
				displayGameChoice();
				displayLaunchButtons(parameters.getGameChoice());
			} else
				parameters.setGameChoice(previousParameters.getGameChoice());
		else {
			displayGameChoice();
			displayLaunchButtons(parameters.getGameChoice());
		}
		
		//
		// Options Tab
		//
		
		// Update defaultSteamLaunchMethod
		if (parameters.getDefaultSteamLaunchMethod() == null)
			if (previousParameters.getDefaultSteamLaunchMethod() == null) {
				parameters.setDefaultSteamLaunchMethod(Parameters.defaultDefaultSteamLaunchMethod);
				view.defaultSteamLaunchMethodComboBox.setSelectedItem(parameters.getDefaultSteamLaunchMethod());
			} else
				parameters.setDefaultSteamLaunchMethod(previousParameters.getDefaultSteamLaunchMethod());
		else
			view.defaultSteamLaunchMethodComboBox.setSelectedItem(parameters.getDefaultSteamLaunchMethod());
		
		// Update gameLeftClickAction
		if (startup)
			parameters.updateGameLeftClickActionObservers();
		else
			if (parameters.getGameLeftClickAction() == null)
				if (previousParameters.getGameLeftClickAction() == null) {
					parameters.setGameLeftClickAction(Parameters.defaultGameLeftClickAction);
					view.gameLeftClickActionComboBox.setSelectedItem(parameters.getGameLeftClickAction());
				} else
					parameters.setGameLeftClickAction(previousParameters.getGameLeftClickAction());
			else
				view.gameLeftClickActionComboBox.setSelectedItem(parameters.getGameLeftClickAction());
		
		// Update displayTooltips
		if (parameters.getDisplayTooltips() == null)
			if (previousParameters.getDisplayTooltips() == null)
				parameters.setDisplayTooltips(Parameters.defaultDisplayToolTips);
			else
				parameters.setDisplayTooltips(previousParameters.getDisplayTooltips());
		if (parameters.getDisplayTooltips())
			view.displayTooltipsYesButton.setSelected(true);
		else
			view.displayTooltipsNoButton.setSelected(true);
		
		// Update buttonsDisplayMode
		if (parameters.getButtonsDisplayMode() == null)
			if (previousParameters.getButtonsDisplayMode() == null)
				parameters.setButtonsDisplayMode(Parameters.defaultButtonsDisplayMode);
			else
				parameters.setButtonsDisplayMode(previousParameters.getButtonsDisplayMode());
		view.buttonsDisplayModeComboBox.setSelectedItem(parameters.getButtonsDisplayMode());
		
		// dumpMode already updated
		// localeChoice already updated
		// lookAndFeelInfo already updated
		
		//
		// Library Tab
		//
		
		// Update sortMethod/displayMode selectors
		if (startup) {
			view.librarySortMethodComboBox.setSelectedItem(SteamGamesSortMethod.values()[0]);
			view.libraryDisplayModeComboBox.setSelectedItem(SteamGamesDisplayMode.values()[0]);
		}
		// Update Library Tab display
		updateAllLibraryTabs();
		
		//
		// Game Tab
		//
		
		// Update selectors
		if (startup) {
			view.steamAchievementsSortMethodComboBox.setSelectedItem(SteamAchievementsSortMethod.values()[0]);
			view.steamAchievementsListsSortMethodComboBox.setSelectedItem(SteamAchievementsListsSortMethod.values()[0]);
		}
		
		// Update Game Tab display
		clearGameTab();
		
		// Setup Profile visible pane at startup
		if (startup)
			view.profilePane.setSelectedComponent(view.profileSummaryPane);
	}
	
	//
	// Threads management
	// Cancellation management
	//
	
	public synchronized boolean cancelReadGamesList() {
		boolean cancelled = false;
		if (steamGamesListReading && steamGamesListReader != null && !steamGamesListReader.isDone() && !steamGamesListReader.isCancelled()) {
			if (cancelled = steamGamesListReader.cancel(true)) {
				steamGamesListReading = false;
				tee.writelnInfos("steamGamesListReader cancelled");
			} else
				tee.writelnError("Can not cancel steamGamesListReader");
		}
		return cancelled;
	}
	
	public synchronized boolean cancelAllSteamGamesStatsReading() {
		boolean cancelled = false;
		if (allSteamGamesStatsReading && allSteamGamesStatsReader != null && !allSteamGamesStatsReader.isDone() && !allSteamGamesStatsReader.isCancelled()) {
			if (cancelled = allSteamGamesStatsReader.cancel(true)) {
				allSteamGamesStatsReading = false;
				tee.writelnInfos("allSteamGamesStatsReader cancelled");
			} else
				tee.writelnError("Can not cancel allSteamGamesStatsReader");
		}
		return cancelled;
	}
	
	public synchronized boolean cancelSteamGameStatsReading() {
		boolean cancelled = false;
		if (steamGameStatsReading && steamGameStatsReader != null && !steamGameStatsReader.isDone() && !steamGameStatsReader.isCancelled()) {
			if (cancelled = steamGameStatsReader.cancel(true)) {
				steamGameStatsReading = false;
		    	loadAllAchievements = false;
				updateGameTabTitle();
				tee.writelnInfos("steamGameStatsReader cancelled");
				cancelSteamFriendsListReading();
//				cancelSteamProfileReading();
			} else
				tee.writelnError("Can not cancel steamGameStatsReader");
		}
		return cancelled;
	}
	
	public synchronized boolean cancelSteamProfileReading() {
		boolean cancelled = false;
		if (steamProfileReading && steamProfileReader != null && !steamProfileReader.isDone() && !steamProfileReader.isCancelled()) {
			if (cancelled = steamProfileReader.cancel(true)) {
		    	steamProfileReading = false;
		    	loadAllAchievements = false;
		    	clearProfileTab(true);
		    	clearFriendsTab();
				tee.writelnInfos("steamProfileReader cancelled");
				cancelSteamFriendsListReading();
			} else
				tee.writelnError("Can not cancel steamProfileReader");
		}
		return cancelled;
	}
	
	public synchronized boolean cancelSteamFriendsListReading() {
		boolean cancelled = false;
		if (steamFriendsListReading && steamFriendsListReader != null && !steamFriendsListReader.isDone() && !steamFriendsListReader.isCancelled()) {
			if (cancelled = steamFriendsListReader.cancel(true)) {
				steamFriendsListReading = false;
		    	loadAllAchievements = false;
				updateProfileFriendsTabTitle();
				tee.writelnInfos("steamFriendsListReader cancelled");
				cancelSteamFriendsGameListsReading();
			} else
				tee.writelnError("Can not cancel steamFriendsListReader");
		}
		return cancelled;
	}
	
	public synchronized boolean cancelSteamFriendsGameListsReading() {
		boolean cancelled = false;
		if (steamFriendsGameListsReading && steamFriendsGameListsReader != null && !steamFriendsGameListsReader.isDone() && !steamFriendsGameListsReader.isCancelled()) {
			if (cancelled = steamFriendsGameListsReader.cancel(true)) {
				steamFriendsGameListsReading = false;
		    	loadAllAchievements = false;
		    	updateGameTabTitle();
				tee.writelnInfos("steamFriendsGameListsReader cancelled");
			} else
				tee.writelnError("Can not cancel steamFriendsGameListsReader");
		}
		return cancelled;
	}
	
	public synchronized boolean cancelSteamFriendGameStatsReading() {
		boolean cancelled = false;
		if (steamFriendGameStatsReading && steamFriendGameStatsReader != null && !steamFriendGameStatsReader.isDone() && !steamFriendGameStatsReader.isCancelled()) {
			if (cancelled = steamFriendGameStatsReader.cancel(true)) {
				steamFriendGameStatsReading = false;
		    	loadAllAchievements = false;
				updateGameTabTitle();
				tee.writelnInfos("steamFriendGameStatsReader cancelled");
			} else
				tee.writelnError("Can not cancel steamFriendGameStatsReader");
		}
		return cancelled;
	}
	
	public synchronized boolean cancelSteamFriendsGameStatsReading() {
		boolean cancelled = false;
		if (steamFriendGameStatsReading && steamFriendsGameStatsReader != null && !steamFriendsGameStatsReader.isDone() && !steamFriendsGameStatsReader.isCancelled()) {
			if (cancelled = steamFriendsGameStatsReader.cancel(true)) {
				steamFriendGameStatsReading = false;
		    	loadAllAchievements = false;
				updateGameTabTitle();
				tee.writelnInfos("steamFriendsGameStatsReader cancelled");
			} else
				tee.writelnError("Can not cancel steamFriendsGameStatsReader");
		}
		return cancelled;
	}
	
	//
	// Read data from Steam Community
	//
	
	/**
	 * Read a gamesList from Steam community
	 */
	public synchronized void readSteamGamesList() {
		
		if (cancelReadGamesList()) return;
		
		// Get GamerSteamId value from textField
		ResourceBundle messages = parameters.getMessages();
		if (view.gamerSteamIdTextField.getText() != null && view.gamerSteamIdTextField.getText().trim().length() > 0) {
			String newGamerSteamId = view.gamerSteamIdTextField.getText().trim();
			if (!parameters.getMainPlayerSteamId().equalsIgnoreCase(newGamerSteamId)) {
				tee.writelnInfos(String.format(messages.getString("infosRollGamerSteamIDHasChanged"), parameters.getMainPlayerSteamId(), newGamerSteamId));
				SteamProfile steamProfile = new SteamProfile();
				if (Strings.fullNumericPattern.matcher(newGamerSteamId).matches()) 
					steamProfile.setSteamID64(newGamerSteamId);
				else
					steamProfile.setSteamID(newGamerSteamId);
				parameters.setMainPlayerSteamId(newGamerSteamId);
				currentSteamProfile = steamProfile;
			}
			
		}
		if (currentSteamProfile == null || (currentSteamProfile.getSteamID64() == null && currentSteamProfile.getSteamID() == null)) {
			rollError();
			return;
		}
		
		// Clear gameList
		parameters.setSteamGamesList(null);
		currentSteamProfile.setStatisticsFetched(false);
		updateAllLibraryTabs();
		
		// Read gameList
		steamGamesListReading = true;
		updateLibraryMainTabTitle();
		
		(steamGamesListReader = new SteamGamesListReader(this)).execute();
		
	}
	
	/**
	 * Read all SteamGameStats of current Library from currentSteamProfile from Steam community
	 * 
	 * TODO: LoadAllGamesStatsAction and LoadLibraryAction must be mutually exclusive
	 */
	public void readAllSteamGamesStats() {
		
		if (cancelAllSteamGamesStatsReading()) return;
		
		if (allSteamGamesStatsReading
				|| currentSteamProfile == null
				|| currentSteamProfile.getSteamGames() == null
				|| currentSteamProfile.getSteamGames().isEmpty()
				) return;
		
		allSteamGamesStatsReading = true;
		updateLibraryMainTabTitle();
		
		(allSteamGamesStatsReader = new AllSteamGamesStatsReader(this, currentSteamProfile)).execute();
	}
	
	/**
	 * Read GameStats of steamGame from Steam community
	 * TODO: Check potential execution chaining problems (Steam does not respond, empty response, 404, 503, whatever)
	 */
	public void readSteamGameStats() {
		
		if (currentSteamGame == null || currentSteamProfile == null
				|| cancelSteamGameStatsReading()
				|| cancelSteamProfileReading()
				|| cancelSteamFriendsListReading()
				|| cancelSteamFriendsGameListsReading()
				) return;
		
		if (!currentSteamProfile.getId().equals(parameters.getMainPlayerSteamId())) {
			
			if (currentSteamProfile.getPrivacyState() == null) {
				JOptionPane.showMessageDialog(view,
						String.format(BundleManager.getUITexts(me, "loadGameStatsIncompleteProfileError"), currentSteamProfile.getId(), currentSteamProfile.getPrivacyState()),
						BundleManager.getUITexts(me, "loadGameStatsTitle"),
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if (!currentSteamProfile.getPrivacyState().equalsIgnoreCase("public")) {
				JOptionPane.showMessageDialog(view,
						String.format(BundleManager.getUITexts(me, "loadGameStatsForProfileError"), currentSteamProfile.getId(), currentSteamProfile.getPrivacyState()),
						BundleManager.getUITexts(me, "loadGameStatsTitle"),
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
			
		boolean gameInMostPlayedGames = false;
			if (currentSteamProfile.getMostplayedGames().size() > 0 && currentSteamProfile.getMostplayedGames().contains(currentSteamGame))
				gameInMostPlayedGames = true;
			else
				if (currentSteamProfile.getSteamGames().size() == 0) {
					JOptionPane.showMessageDialog(me != null ? me.getLibrarian().getView() : null, // WindowBuilder
							String.format(BundleManager.getUITexts(me, "loadGameStatsEmptyGamesLibraryError"), currentSteamGame.getName(), currentSteamProfile.getId()),
							BundleManager.getUITexts(me, "loadGameStatsTitle"),
							JOptionPane.ERROR_MESSAGE);
					return;
				}
		
		if (!gameInMostPlayedGames && !currentSteamProfile.hasGame(currentSteamGame)) {
			JOptionPane.showMessageDialog(me != null ? me.getLibrarian().getView() : null, // WindowBuilder
					String.format(BundleManager.getUITexts(me, "loadGameStatsGameNotOwnedError"), currentSteamProfile.getId(), currentSteamGame.getName()),
					BundleManager.getUITexts(me, "loadGameStatsTitle"),
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		steamGameStatsReading = true;
		updateGameTabTitle();
		
		// Read game stats
		(steamGameStatsReader = new SteamGameStatsReader(this, currentSteamGame, null, 1)).execute();
		
		// Read friends list / friends games lists
		if (currentSteamProfile != null) {
			if (currentSteamProfile.getSteamFriends() == null || currentSteamProfile.getSteamFriends().size() == 0) {
				readSteamFriendsList();
			} else {
				for (SteamProfile friend : currentSteamProfile.getSteamFriends()) {
					if (friend.getSteamGames() == null || friend.getSteamGames().size() == 0) {
						readSteamFriendsGameLists();
						break;
					}
				}
			}
		} else {
			readSteamProfile();
		}		
	}
	
	/**
	 * Read a SteamProfile from Steam community
	 */
	public void readSteamProfile() {
		
		if (cancelSteamProfileReading()
				|| cancelSteamFriendsListReading()
				|| cancelSteamFriendsGameListsReading()
				) return;
		
		if (currentSteamProfile.getPrivacyState() != null && !currentSteamProfile.getPrivacyState().equalsIgnoreCase("public")) {
			ResourceBundle UITexts = parameters.getUITexts();
			String title = UITexts.getString("changeCurrentPlayer");
			String message = String.format(UITexts.getString("changeCurrentPlayerPrivacyError"), currentSteamProfile.getId(), currentSteamProfile.getPrivacyState());
			JOptionPane.showMessageDialog(view, message, title, JOptionPane.ERROR_MESSAGE);
			return;
		}
	
    	steamProfileReading = true;
    	updateProfileTabReading();
    	clearGroupsTab();
    	clearFriendsTab();
    	
		(steamProfileReader = new SteamProfileReader(this)).execute();
	}
	
	/**
	 * Read SteamFriendsList from Steam community
	 */
	public void readSteamFriendsList() {

		if (cancelSteamFriendsListReading()
				|| cancelSteamFriendsGameListsReading()
				) return;
		
		steamFriendsListReading = true;
		updateProfileFriendsTabTitle();
		
		(steamFriendsListReader = new SteamFriendsListReader(this, currentSteamProfile)).execute();
	}
	
	/**
	 * Read steamFriends gamelists
	 */
	public void readSteamFriendsGameLists() {
		
		if (cancelSteamFriendsGameListsReading()) return;
		
		steamGameStatsReading = true;
		if (currentSteamProfile.hasGame(currentSteamGame))
			updateGameTabTitle();
		else
			updateProfileTabTitle();

    	(steamFriendsGameListsReader = new SteamFriendsGameListsReader(this, currentSteamProfile, currentSteamGame, parameters.getDefaultSteamLaunchMethod())).execute();
	}
	
	/**
	 * Read GameStats of steamGame for steamFriend from Steam community
	 */
	public void readSteamFriendSteamGameStats(SteamProfile friend, Integer initialPosition, Icon friendAvatarIcon) {
		
		if (cancelSteamFriendGameStatsReading()) return;
		
		steamGameStatsReading = true;
		updateGameTabTitle();
		
		// Read friends game stats
		(steamFriendGameStatsReader = new SteamFriendGameStatsReader(this, friend, friendAvatarIcon, currentSteamGame, initialPosition, null)).execute();
		
	}
	
	/**
	 * Read multiple GameStats of steamGame for steamFriends from Steam community
	 * @param friendWithSameGameButtons list of FriendWithSameGameButton to read GameStats from
	 */
	public void readSteamFriendsSteamGameStats(List<SteamFriendWithSameGameButton> friendWithSameGameButtons) {
		
//		if (cancelSteamFriendsGameStatsReading()) return;
		
		steamGameStatsReading = true;
		updateGameTabTitle();
		
		// Read friends game stats
		(steamFriendsGameStatsReader = new SteamFriendsGameStatsReader(this, friendWithSameGameButtons, currentSteamGame)).execute();
	}
	
	public synchronized void loadAllAchievements() {
		
//		if (!loadAllAchievements && (currentSteamGame != null || currentSteamGame.getSteamGameStats() == null)) {
//			loadAllAchievements = true;
//			readSteamGameStats();
//			return;
//		}
			
		if (currentSteamProfile == null
				|| currentSteamGame == null
				|| currentSteamGame.getSteamGameStats() == null
				|| !view.steamFriendsButtonGroup.getElements().hasMoreElements()
				) 
			return;
		
		if (!currentSteamProfile.hasGame(currentSteamGame) && !currentSteamProfile.getMostplayedGames().contains(currentSteamGame))
			return;
		
		Component[] components = view.friendsWithSameGamePane.getComponents();
		List<SteamFriendWithSameGameButton> friendWithSameGameButtons = new Vector<SteamFriendWithSameGameButton>();
		List<SteamFriendWithSameGameButton> fullFriendWithSameGameButtons = new Vector<SteamFriendWithSameGameButton>();
		for (int index = 0; index < components.length; index++)
			if (components[index] instanceof SteamFriendWithSameGameButton) {
				SteamFriendWithSameGameButton friendWithSameGameButton = (SteamFriendWithSameGameButton) components[index];
				fullFriendWithSameGameButtons.add(friendWithSameGameButton);
				if (!friendWithSameGameButton.isSelected()) {
					friendWithSameGameButtons.add(friendWithSameGameButton);
				}
			}
		if (!friendWithSameGameButtons.isEmpty())
			readSteamFriendsSteamGameStats(friendWithSameGameButtons);
		else {
			for (SteamFriendWithSameGameButton friendWithSameGameButton : fullFriendWithSameGameButtons) {
				removeFriendAchievements(friendWithSameGameButton.getSteamProfile());
				friendWithSameGameButton.setSelected(false);
			}
			view.updateLoadAllAchievements();
		}
	}
	
	//
	// User Dialogs
	//
	
	private class ConfigurationSelection {
		
		int option;
		File file;
		String filename;
		
		ConfigurationSelection(int option, File file, String filename) {
			this.option = option;
			this.file = file;
			this.filename = filename;
		}
	}
	
	private ConfigurationSelection selectConfigurationFile(String filename, String title, String message, ImageIcon icon) {
		
		int option = -1;
		ResourceBundle UITexts = parameters.getUITexts();
		
		// Confirm configuration filename and save operation
		AskForConfigurationFileDialog askForConfigurationFile = new AskForConfigurationFileDialog(view, true, selectedDirectory, selectedFilename, icon, title, message, UITexts);
		if (!askForConfigurationFile.getInputDirectory().trim().equals("") && !askForConfigurationFile.getInputFilename().trim().equals("")) {
			selectedDirectory = askForConfigurationFile.getInputDirectory();
			selectedFilename = askForConfigurationFile.getInputFilename();
			filename = askForConfigurationFile.getFullFilename();
			option = JOptionPane.OK_OPTION;
		} else
			return null;
		
		// Check filename extension
		File file = new File(filename);
		int index = filename.lastIndexOf(".");
		String extension = null;
		if (index == -1 || (extension = filename.substring(index)) == null || extension.length() < 2) {
			// Complete extension when possible
			filename = ((index == -1 || extension == null) ? filename : filename.substring(0, index)) + ".xml";
			file = new File(filename);
			option = JOptionPane.OK_OPTION;
		} else if (extension != null && !extension.equalsIgnoreCase(".xml")) {
			// Non conventional extension, ask confirmation
			String confirmMessage = String.format(UITexts.getString("warnLoadConfigurationFileHasNotAConventionalExtension"), filename);
			option = JOptionPane.showConfirmDialog(view, confirmMessage, title, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, icon);
			if (option == JOptionPane.CANCEL_OPTION)
				return null;
		}
		
		return new ConfigurationSelection(option, file, filename);
	}
	
	/**
	 * Confirm Load configuration file
	 * 
	 * @param filename
	 * @return true if confirmed, false otherwise
	 */
	public String confirmLoadConfigurationFile(String filename) {
		
		String title;
		String askMessage;
		String message;
		boolean fileExists = false;
		boolean fileValid = false;
		String startDirectory = new String(selectedDirectory);
		String startFilename = new String(selectedFilename);
		ConfigurationSelection selectionConfirmation = new ConfigurationSelection(-1, null, filename);
		
		ResourceBundle UITexts = parameters.getUITexts();
		title = UITexts.getString("loadConfigurationFilenameTitle");
		askMessage = UITexts.getString("askLoadConfigurationFilename");
		ImageIcon loadIcon = BoundedComponent.resizeAndCenterIcon(GamesLibrarianIcons.loadParametersIcon, 32, 32);
		
		while (selectionConfirmation.option != JOptionPane.CANCEL_OPTION && (!fileExists || !fileValid)) {
			
			selectionConfirmation = selectConfigurationFile(selectionConfirmation.filename, title, askMessage, loadIcon);
			if (selectionConfirmation == null)
				return null;
			
			if (selectionConfirmation.option == JOptionPane.OK_OPTION) {
				// Check file exists
				if (!(fileExists = selectionConfirmation.file.exists())) {
					message = String.format(UITexts.getString("errorLoadConfigurationFileDoesNotExists"), selectionConfirmation.filename);
					selectedDirectory = startDirectory;
					selectedFilename = startFilename;
					JOptionPane.showMessageDialog(view, message, title, JOptionPane.ERROR_MESSAGE, loadIcon);
					if (parameters.isDebug()) tee.writelnError(message);
				// Check if file is valid (canRead)
				} else if (!(fileValid = selectionConfirmation.file.canRead())) {
					message = String.format(UITexts.getString("errorLoadConfigurationFileIsNotReadable"), selectionConfirmation.filename);
					JOptionPane.showMessageDialog(view, message, title, JOptionPane.ERROR_MESSAGE, loadIcon);
					if (parameters.isDebug()) tee.writelnInfos(message);
				} else
					return selectionConfirmation.filename;
			}			
		}
		
		return null;
	}
	
	/**
	 * Confirm Save configuration file
	 * @param filename
	 * @return
	 */
	public String confirmSaveConfigurationFile(String filename) {
		
		String title;
		String askMessage;
		String message;
		boolean fileExists = false;
		String startDirectory = new String(selectedDirectory);
		String startFilename = new String(selectedFilename);
		ConfigurationSelection selectionConfirmation = new ConfigurationSelection(-1, null, filename);
		
		ResourceBundle UITexts = parameters.getUITexts();
		title = UITexts.getString("saveConfigurationFilenameTitle");
		askMessage = UITexts.getString("askSaveConfigurationFilename");
		ImageIcon saveIcon = BoundedComponent.resizeAndCenterIcon(GamesLibrarianIcons.saveParametersIcon, 32, 32);
		
		while (selectionConfirmation.option != JOptionPane.CANCEL_OPTION) {
			
			selectionConfirmation = selectConfigurationFile(selectionConfirmation.filename, title, askMessage, saveIcon);
			if (selectionConfirmation == null)
				return null;
			
			if (selectionConfirmation.option == JOptionPane.OK_OPTION) {
				int option = -1;
				// Check file exists
				if ((fileExists = selectionConfirmation.file.exists())) {
					message = String.format(UITexts.getString("warnSaveConfigurationFileFileAlreadyExists"), selectionConfirmation.filename);
					option = JOptionPane.showConfirmDialog(view, message, title, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, saveIcon);
					if (option == JOptionPane.CANCEL_OPTION)
						return null;
					else if (option == JOptionPane.NO_OPTION) {
						selectedDirectory = startDirectory;
						selectedFilename = startFilename;
					}
				}
				// Check if file is valid (canWrite)
				if (fileExists && !selectionConfirmation.file.canWrite()) {
					message = String.format(UITexts.getString("errorLoadConfigurationFileIsNotReadable"), selectionConfirmation.filename);
					JOptionPane.showMessageDialog(view, message, title, JOptionPane.ERROR_MESSAGE, saveIcon);
					if (parameters.isDebug()) tee.writelnInfos(message);
				} else
					if (option != JOptionPane.NO_OPTION)
						return selectionConfirmation.file.getAbsolutePath();
			}
		}
		return null;
	}
	
	/**
	 * Request profileId when empty
	 * Then read SteamProfile
	 */
	public void requestAndLoadProfile() {
	    if (currentSteamProfile == null || currentSteamProfile.getId() == null) {
	    	SteamProfile steamProfile = view.addProfileAction.requestProfileIdAndLoadProfile(currentSteamProfile != null ? currentSteamProfile.getId() : null);
	    	if (steamProfile != null) {
	    		currentSteamProfile = steamProfile;
	    		updateKnownProfileComboBox();
	    		readSteamProfile();
	    	}
	    } else {
    		updateKnownProfileComboBox();
    		readSteamProfile();
	    }
	}
	
	/**
	 * Display a informative message on configuration change (load/save)
	 * 
	 * @param configurationContext
	 * @param filename
	 */
	public void printConfigurationMessage(String configurationContext, String filename) {
		tee.writelnMessage(configurationContext);
		File file = new File(filename);
		Vector<String> messages = new Vector<String>();
		try {
			messages.add(file.getCanonicalFile().getCanonicalPath());
		} catch (IOException e) {
			String message = String.format(parameters.getUITexts().getString("erroReadingCanonicalPathfile"), filename);
			messages.add(message);
			e.printStackTrace();
		}
		tee.writelnInfos(messages);
	}
	

	/**
	 * Display error message when given profile id was invalid
	 */
	public void displayErroredProfile(SteamProfile steamProfile) {
		JOptionPane.showMessageDialog(
			view, 
			String.format(parameters.getUITexts().getString("addProfileErrorMessage"), 
			steamProfile.getOnlineState() != null ?
					steamProfile.getOnlineState() 
							: parameters.getUITexts().getString("errorNoResponse")),
			parameters.getUITexts().getString("addProfileTitle"),
			JOptionPane.ERROR_MESSAGE);
		restoreOldProfile();
	}
	
	/**
	 * Dump current SteamProfile
	 */
	public void dumpSteamProfile() {
		Parameters parameters = getParameters();
		ResourceBundle messages = parameters.getMessages();
		DumpMode dumpMode = parameters.getDumpMode();
		if (dumpMode == DumpMode.Text || dumpMode == DumpMode.Both) {
			tee.writelnInfos(messages.getString("showCurrentProfileText"));
			tee.writelnMessage(currentSteamProfile.toStringList());
		}
		if (dumpMode == DumpMode.XML || dumpMode == DumpMode.Both) {
			tee.writelnInfos(messages.getString("showCurrentProfileXML"));
    		SteamProfileParser.dump(currentSteamProfile, tee);
		}
	}
	
	/**
	 * Dump GameStats
	 * @param steamGameStats
	 */
	public void dumpGameStats(SteamGameStats steamGameStats) {
		Parameters parameters = getParameters();
		ResourceBundle messages = parameters.getMessages();
		DumpMode dumpMode = parameters.getDumpMode();
		if (dumpMode == DumpMode.Text || dumpMode == DumpMode.Both) {
			tee.writelnInfos(messages.getString("showGameStatsText"));
//			tee.writelnMessage(steamGameStats.toStringList());
			tee.writelnMessage(steamGameStats.toString());
		}
		if (dumpMode == DumpMode.XML || dumpMode == DumpMode.Both) {
			tee.writelnInfos(messages.getString("showGameStatsXML"));
    		SteamGameStatsParser.dump(steamGameStats, tee);
		}
	}
	
	/**
	 * When finishing AllSteamGamesStatsReader work
	 * We can do this
	 */
	public void displayGamesWithInvalidStatistics() {
		for (SteamGame game : currentSteamProfile.getSteamGames()) {
			if ((game.getStatsLink() != null && !game.getStatsLink().trim().equals(""))
				&& (game.getSteamGameStats() == null
					|| game.getSteamGameStats().getSteamAchievementsList() == null
					|| game.getSteamGameStats().getSteamAchievementsList().getSteamAchievements() == null
					|| game.getSteamGameStats().getSteamAchievementsList().getSteamAchievements().size() <= 0)) {
				tee.writelnInfos("Game with invalid statistics = " + game.getName() + ", " + game.getStatsLink());
			}
		}
	}
	
	//
	// Localization
	//
	
	/**
	 * Set a new Locale default
	 * 
	 * @param localeChoice
	 * @param locale
	 */
	public void setDefaultLocale(LocaleChoice localeChoice, Locale locale) {
		Locale.setDefault(locale);
		parameters.setLocaleChoice(localeChoice);
		translate();
	}
	
	/**
	 * Reset Resources Bundles
	 */
	private void resetResourcesBundles() {
		LocaleChoice.checkAvailablesResources();
		parameters.setMessages(ResourceBundle.getBundle("i18n/messages"));
		parameters.setUITexts(ResourceBundle.getBundle("i18n/UITexts"));
		parameters.setResources(ResourceBundle.getBundle("i18n/resources"));
		
		// Reset duration translation tokens
		durationTokens.put("year", BundleManager.getResources(me, "year"));
		durationTokens.put("years", BundleManager.getResources(me, "years"));
		durationTokens.put("month", BundleManager.getResources(me, "month"));
		durationTokens.put("months", BundleManager.getResources(me, "months"));
		durationTokens.put("day", BundleManager.getResources(me, "day"));
		durationTokens.put("days", BundleManager.getResources(me, "days"));
		durationTokens.put("hour", BundleManager.getResources(me, "hour"));
		durationTokens.put("hours", BundleManager.getResources(me, "hours"));
		durationTokens.put("minute", BundleManager.getResources(me, "minute"));
		durationTokens.put("minutes", BundleManager.getResources(me, "minutes"));

	}
	
	/**
	 * Translate the application
	 */
	private void translate() {
		
		// Switch bundles
		resetResourcesBundles();
		ResourceBundle UITexts = parameters.getUITexts();
		
		// Do I18n on declared translatable components
		updateTranslatables();
		
		//
		// Main Tabs titles
		//
		int index = 0;
		for (Component component : view.mainPane.getComponents()) {
			if (component != null && component instanceof JPanel && component.getName() != null) 
				if (component.getName().equals("libraryMainTab"))
					updateLibraryMainTabTitle();
				else if (component.getName().equals("profileTab"))
					updateProfileTabTitle();
				else if (component.getName().equals("gameTab"))
					 updateGameTabTitle();		
				else
					view.mainPane.setTitleAt(index, UITexts.getString(((JPanel) component).getName() + "Title"));
			index += 1;
		}
		
		//
		// Library Tab
		//
		
		// Library Sub Tab titles
		index = 0;
		for (Component component : view.libraryMainPane.getComponents()) {
			if (component != null && component instanceof JPanel && component.getName() != null) 
				if (!component.getName().equals("libraryTab")) // Already done in updateLibraryMainTabTitle()
					view.libraryMainPane.setTitleAt(index, GamesLibrarian.getTabTitle(UITexts.getString(((JPanel) component).getName() + "Title")));
			index += 1;
		}
		
		//
		// Library Statistics Tab
		//
		
		updateLibraryStatisticsMainTab();
		
		//
		// Profile Tab
		//
		
		// Profile Sub Tab titles
		index = 0;
		for (Component component : view.profilePane.getComponents()) {
			if (component != null && component instanceof JPanel && component.getName() != null) 
				if (component.getName().equals("profileSummaryTab") && currentSteamProfile != null && currentSteamProfile.getSteamID() != null)
					updateProfileSummaryTabTitle();
				else if (component.getName().equals("profileStatusTab") && currentSteamProfile != null && currentSteamProfile.getSteamID() != null)
					updateProfileStatusTabTitle();
				else if (component.getName().equals("profileFriendsTab") && currentSteamProfile != null && currentSteamProfile.getSteamFriends().size() > 0)
					updateProfileFriendsTabTitle();
				else
					view.profilePane.setTitleAt(index, GamesLibrarian.getTabTitle(UITexts.getString(((JPanel) component).getName() + "Title")));
			index += 1;
		}
		
		// Update CommandButtons display
		updateButtonsDisplayModeObservables();
		
		// UpdateUI on entire view
        SwingUtilities.updateComponentTreeUI(view);
	}
	
	/**
	 * Setup 
	 * - Application parameters to default
	 * - Runtime variables to default
	 */
	public void setup() {
		
		// Create a runtime parameters set
		parameters = new Parameters();
		
		// Determine OS
		parameters.setOs(new OS());
		String osProperty = System.getProperty("os.name") != null ? System.getProperty("os.name").toLowerCase() : OS.Prefix.Unknown.name();
		if (osProperty.indexOf("win") >= 0) parameters.getOs().setPrefix(OS.Prefix.Win);
		else if (osProperty.indexOf("mac") >= 0) parameters.getOs().setPrefix(OS.Prefix.Mac);
		else if (!osProperty.equals(OS.Prefix.Unknown.name())) parameters.getOs().setPrefix(OS.Prefix.Nix);
		else parameters.getOs().setPrefix(OS.Prefix.Unknown);
		
		// initialize I18n
		// Reset I18n
		resetResourcesBundles();
		// Verify integrity of resources files (developper's help)
		// TODO: Add a parameter to ignore this operation
		// Problem : this operation must be done before reading parameters
		if (LocaleChoice.usablesLanguages.isEmpty())
			throw new RuntimeException("No usables languages. Unable to execute.");
		parameters.setLocaleChoice(Locale.getDefault().getLanguage().equalsIgnoreCase("fr") ? LocaleChoice.fr_FR : LocaleChoice.en_US);
		
		// Setup application parameters to default
		parameters.setLookAndFeelInfo(Parameters.defaultLookAndFeelInfo);
		
		parameters.setWindowsDistribution(Parameters.defaultWindowsDistribution);
		parameters.setSteamExecutable(Parameters.defaultSteamExecutable);
		
		parameters.setMainPlayerSteamId(Parameters.defaultMainPlayerSteamId);
		
		parameters.setGameChoice(Parameters.defaultGameChoice);		
		parameters.setGameLeftClickAction(Parameters.defaultGameLeftClickAction);
		parameters.setDefaultSteamLaunchMethod(Parameters.defaultDefaultSteamLaunchMethod);
		parameters.setSteamGroupsDisplayMode(Parameters.defaultSteamGroupsDisplayMode);
		parameters.setSteamFriendsDisplayMode(Parameters.defaultSteamFriendsDisplayMode);
		parameters.setDisplayTooltips(Parameters.defaultDisplayToolTips);
		parameters.setButtonsDisplayMode(Parameters.defaultButtonsDisplayMode);
		
		parameters.setDebug(Parameters.defaultDebug);
		parameters.setCheckCommunityOnStartup(Parameters.defaultCheckCommunityOnStartup);
		parameters.setDumpMode(Parameters.defaultDumpMode);
		parameters.setUseConsole(Parameters.defaultUseConsole);
		parameters.setUseDateTime(Parameters.defaultUseDateTime);
		parameters.setScrollLocked(Parameters.defaultScrollLocked);
		parameters.setXmlOverrideRegistry(Parameters.defaultXmlOverrideRegistry);
		
		// Setup runtime status to default
		steamGamesListReading = false;
		steamGameStatsReading = false;
		loadAllAchievements = false;
		steamProfileReading = false;
		steamFriendsListReading = false;
		steamFriendsGameListsReading = false;
		steamFriendGameStatsReading = false;
		
	}
	
	/**
	 * Application parameters are now set to default.<br/>
	 * UI should be almost ready.
	 * Finish UI construction with top level graphics elements.<br/>
	 * Make application configuration with OS elements
	 * <ul>
	 * <li>Registry (for Windows, where we could find interesting configuration data on Steam),</li>
	 * <li>Configuration(s) file(s)</li>
	 * </ul>
	 * 
	 * @return Startup status indicating if application should continue execution or stop after startup, due to misconfiguration of OS elements
	 */
	public boolean startup() {
		
		// Create Tee
		tee = new ColoredTee(parameters, this);
		
		// Write first Tee message
		tee.writelnInfos(String.format(parameters.getMessages().getString("usableLanguages"), LocaleChoice.usablesLanguages));
		
		// Create top level components
		// Must stay on top of startup
		// gameLaunchers contains translatable controls 
		// and is used in translation process
		// So it must be instantiated before selection of language
		gameLaunchers = new GameLauncher[] {
				view.gameLauncher1,
				view.gameLauncher2,
				view.gameLauncher3
		};
		
		// Create empty currentSteamProfile
		currentSteamProfile = new SteamProfile();
		
		// Read data from Windows registry
		view.resetOptionsAction.readRegitryOptions();
		if (parameters.getMainPlayerSteamId() != null) {
			if (Strings.fullNumericPattern.matcher(parameters.getMainPlayerSteamId()).matches())
				currentSteamProfile.setSteamID64(parameters.getMainPlayerSteamId());
			else
				currentSteamProfile.setSteamID(parameters.getMainPlayerSteamId());
		}
		
		// Preserve old set to complete missing parameters
		Parameters previousParameters = (Parameters) parameters.clone();
		
		// Read/Load local parameters if they exists and are readable
		// Look for two different filenames
		File configurationFile = new File(Parameters.defaultConfigurationFilename);
		String configurationFilename = Parameters.defaultConfigurationFilename;
		if (!configurationFile.exists() || !configurationFile.isFile() || !configurationFile.canRead()) {
			configurationFile = new File(configurationFilename = Parameters.defaultConfigurationShortFilename);
			if (!configurationFile.exists() || !configurationFile.isFile() || !configurationFile.canRead())
				configurationFile = null;
		}
		if (configurationFile != null) {
			String canonicalPath = null;
			try {
				canonicalPath = configurationFile.getCanonicalPath();
			} catch (IOException e) {
				tee.printStackTrace(e);
				return false;
			}
			if (!configurationFile.canRead()) {
				tee.writelnError(String.format(parameters.getMessages().getString("errorConfigurationFileIsUnreadable"), canonicalPath));
				return false;
			}
			view.loadParametersAction.loadConfiguration(configurationFilename);
		}

		// Replace currentSteamProfile.steamGames by parameters.steamGamesList.steamGames
		SteamGamesList steamGamesList = parameters.getSteamGamesList();
		if (currentSteamProfile.getSteamGames() == null || currentSteamProfile.getSteamGames().size() == 0)
			currentSteamProfile.setSteamGames(steamGamesList.getSteamGames());
		
		// Initialize knownProfilesComboBox
		if (currentSteamProfile.getId() != null) {
			view.knownProfilesComboBox.addProfile(currentSteamProfile);
		}	
		
		// Check if Steam Community is online
		if (parameters.isCheckCommunityOnStartup() && !checkSteamCommunity(DisplayMode.verbose))
			return false;
				
		// update UI according to new parameters
		updateUI(previousParameters, true);
		
		return true;
	}
	
	//
	// Observe runtime variables change to update display
	//
	
	// SteamGamesSortMethod
	public void updateSteamGamesSortMethod() {
		steamGamesTable.sorter();
	}
	
	// SteamGroupsSortMethod
	public void updateSteamGroupsSortMethod() {
		steamGroupsTable.sorter();
	}
	
	// SteamFriendsSortMethod
	public void updateSteamFriendsSortMethod() {
		steamFriendsTable.sorter();
	}

	// SteamAchievementsSortMethod
	
	@Override
	public void addSteamAchievementsSortMethodObservable(SteamAchievementsSortMethodObservable achievementsSortMethodObservable) {
		achievementsSortMethodObservables.add(achievementsSortMethodObservable);
	}

	@Override
	public void updateSteamAchievementsSortMethodObservables() {
		for (SteamAchievementsSortMethodObservable observable : achievementsSortMethodObservables)
			observable.update();
	}

	@Override
	public void removeSteamAchievementsSortMethodObservable(SteamAchievementsSortMethodObservable achievementsSortMethodObservable) {
		achievementsSortMethodObservables.remove(achievementsSortMethodObservable);
	}

	// ButtonsDisplayMode
	
	@Override
	public void addButtonsDisplayModeObservable(ButtonsDisplayModeObservable buttonsDisplayModeObservable) {
		buttonsDisplayModeObservables.add(buttonsDisplayModeObservable);
		
	}

	@Override
	public void updateButtonsDisplayModeObservables() {
		for (ButtonsDisplayModeObservable observable : buttonsDisplayModeObservables)
			observable.update();
	}

	@Override
	public void removeButtonsDisplayModeObservable(ButtonsDisplayModeObservable buttonsDisplayModeObservable) {
		buttonsDisplayModeObservables.remove(buttonsDisplayModeObservable);
	}
	
	// Translator
	
	@Override
	public void addTranslatable(Translatable translatable) {
		translatables.add(translatable);
	}

	@Override
	public void updateTranslatables() {
		for (Translatable translatable : translatables)
			translatable.translate();
	}

	@Override
	public void removeTranslatable(Translatable translatable) {
		translatables.remove(translatable);
	}

}
