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

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;

import commons.BundleManager;
import commons.GamesLibrary;
import commons.api.Parameters;
import commons.api.Steam;
import commons.api.SteamLaunchMethod;
import commons.enums.ButtonsDisplayMode;
import commons.enums.DumpMode;
import commons.enums.GameChoice;
import commons.enums.GameLeftClickAction;
import commons.enums.LaunchType;
import commons.enums.LibrarianTabEnum;
import commons.enums.LibraryTabEnum;
import commons.enums.LocaleChoice;
import commons.enums.ProfileTabEnum;
import commons.enums.SteamAchievementsListsSortMethod;
import commons.enums.SteamAchievementsSortMethod;
import commons.enums.SteamFriendsDisplayMode;
import commons.enums.SteamFriendsSortMethod;
import commons.enums.SteamGamesDisplayMode;
import commons.enums.SteamGamesSortMethod;
import commons.enums.SteamGroupsDisplayMode;
import commons.enums.SteamGroupsSortMethod;
import commons.groups.ActionGroup;
import commons.groups.ActionGroupFactory;
import commons.layouts.WrapLayout;
import components.actions.AboutAction;
import components.actions.AddProfileAction;
import components.actions.ClearConsoleAction;
import components.actions.DebugAction;
import components.actions.DisplayToolTipsAction;
import components.actions.ExitAction;
import components.actions.GameChoiceAction;
import components.actions.GotoAction;
import components.actions.LoadAllAchievementsAction;
import components.actions.LoadAllGamesStatsAction;
import components.actions.LoadGameStatsAction;
import components.actions.LoadLibraryAction;
import components.actions.LoadParametersAction;
import components.actions.LoadProfileAction;
import components.actions.RefreshGamesListAction;
import components.actions.ResetOptionsAction;
import components.actions.RollAction;
import components.actions.SaveParametersAction;
import components.actions.ScrollLockAction;
import components.actions.SteamExecutableAction;
import components.actions.SteamFriendsDisplayModeAction;
import components.actions.SteamGroupsDisplayModeAction;
import components.actions.ViewParametersAction;
import components.actions.adapters.LibrarianCloser;
import components.actions.enums.ButtonsDisplayModeAction;
import components.actions.enums.DefaultSteamLaunchMethodAction;
import components.actions.enums.DumpModeAction;
import components.actions.enums.LocaleChoiceAction;
import components.actions.enums.SteamAchievementsListsSortMethodAction;
import components.actions.enums.SteamAchievementsSortMethodAction;
import components.actions.enums.SteamFriendsSortMethodAction;
import components.actions.enums.SteamGamesDisplayModeAction;
import components.actions.enums.SteamGamesSortMethodAction;
import components.actions.enums.SteamGroupsSortMethodAction;
import components.actions.texts.LookAndFeelAction;
import components.buttons.CommandButton;
import components.buttons.GameLeftClickActionButton;
import components.buttons.listeners.GameLeftClickActionButtonListener;
import components.comboboxes.ButtonsDisplayModeComboBox;
import components.comboboxes.DumpModeComboBox;
import components.comboboxes.GameLeftClickActionComboBox;
import components.comboboxes.KnownProfilesComboBox;
import components.comboboxes.LocaleChoiceComboBox;
import components.comboboxes.LookAndFeelInfoComboBox;
import components.comboboxes.SteamAchievementsListsSortMethodComboBox;
import components.comboboxes.SteamAchievementsSortMethodComboBox;
import components.comboboxes.SteamFriendsSortMethodComboBox;
import components.comboboxes.SteamGamesDisplayModeComboBox;
import components.comboboxes.SteamGamesSortMethodComboBox;
import components.comboboxes.SteamGroupsSortMethodComboBox;
import components.comboboxes.SteamLaunchMethodComboBox;
import components.comboboxes.adapters.EnumSelectionStateAdapter;
import components.comboboxes.adapters.TextSelectionStateAdapter;
import components.containers.ArrowedComboBox;
import components.containers.GameLauncher;
import components.containers.IconPane;
import components.containers.MostPlayedGameLauncher;
import components.containers.remotes.LaunchButton;
import components.containers.remotes.SteamFriendButton;
import components.containers.remotes.SteamGroupButton;
import components.labels.TranslatableLabel;
import components.labels.titles.CurrentGameTitleLabel;
import components.labels.titles.CurrentProfileTitleLabel;
import components.labels.titles.LibraryMainTitleLabel;
import components.menus.TranslatableMenu;
import components.tables.listeners.GamesLibrarianTabChangeListener;
import components.textfields.TranslatableGameArgumentsTextField;
import components.textfields.TranslatableTextField;
import components.workers.cleaners.LibrarianCleaner;

/**
 * GamesLibrarian GUI
 * @author Naeregwen
 *
 */
public class GamesLibrarian extends JFrame {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -904623119948462522L;
	
	/**
	 * Main frame initial dimensions 
	 */
	private static final int initialFrameWidth = 1280;
	private static final int initialFrameHeight = 640;
	
	/**
	 * A wrapper class to allow WindowBuilder UI parser to reverse code
	 * and thus allow us to work with modern and graphical UI design tools.
	 * </ul>
	 * <li>new CustomComponent(this); => WindowBuilder UI parser Exception while switching to design mode</li>
	 * <li>new CustomComponent(new WindowBuilder(this)); =>  WindowBuilder UI parser can reverse UI code and translate components </li>
	 * </ul>
	 * Allow also auto translation of a well formed UI during design mode.
	 * <br/>By delegating translation to WindowBuilder components/parser, based on local/embedded bundles files and current JVM locale settings
	 * <br/>This delegation is visible from 
	 * <ul>
	 * <li>WindowBuilder's design mode, when designing Frame</li>
	 * <li>from preview function, inside WindowBuilder's design mode, when testing design</li>
	 * </ul>
	 */
	public class WindowBuilderMask {
		
		GamesLibrarian me;
		
		public WindowBuilderMask(GamesLibrarian me) {
			super();
			this.me = me;
		}
		
		public Librarian getLibrarian() {
			return me.librarian;
		}
		
	} 
	
	/**
	 * Application variables
	 */
	private WindowBuilderMask me;
	Librarian librarian;
	
	/**
	 * It is not because you are, qu'i am yours, qu'i am yours
	 * @return this
	 */
	GamesLibrarian me() { return this; }
	
	/**
	 * @return the librarian
	 */
	public Librarian getLibrarian() {
		return librarian;
	}

	/**
	 * @return Application Title
	 */
	public String getApplicationTitle() {
		return "Games Librarian";
	}
	
	/**
	 * Runtime variables
	 */
	BundleManager bundleManager;
	JPanel gameLaunchersPane;

	/**
	 * @return the bundleManager
	 */
	public BundleManager getBundleManager() {
		return bundleManager;
	}
	
	/**
	 * Graphic components
	 */
	
	// Main containers
	JTabbedPane mainPane;

	// Main Tabs
	
	JPanel gamesLibrarianControls;
	JPanel gamesLibrarianLibrary;
	JPanel gamesLibrarianGame;
	JPanel gamesLibrarianProfile;
	JPanel gamesLibrarianOptions;
	
	// Controls Tab
	
	CommandButton rollButton;
	TranslatableGameArgumentsTextField gameNameTextField;
	
	JPanel gameChoicePane;
	ButtonGroup gameChoiceButtonGroup;
	JRadioButton oneGameRadioButton;
	JRadioButton twoGamesRadioButton;
	JRadioButton threeGamesRadioButton;
	
	GameLauncher gameLauncher1;
	GameLauncher gameLauncher2;
	GameLauncher gameLauncher3;
	LaunchButton launchGame1Button;
	LaunchButton launchGame2Button;
	LaunchButton launchGame3Button;
	SteamLaunchMethodComboBox steamLaunchMethod1ComboBox;
	SteamLaunchMethodComboBox steamLaunchMethod2ComboBox;
	SteamLaunchMethodComboBox steamLaunchMethod3ComboBox;
	TranslatableGameArgumentsTextField argumentsGame1TextField;
	TranslatableGameArgumentsTextField argumentsGame2TextField;
	TranslatableGameArgumentsTextField argumentsGame3TextField;
	
	CommandButton refreshGamesListButton;
	CommandButton scrollLockButton;
	CommandButton debugButton;
	CommandButton clearConsoleButton;
	CommandButton loadParametersButton;
	CommandButton saveParametersButton;
	CommandButton viewParametersButton;
	
	JTextPane consoleTextPane;

	// Library Main pane
	
	JTabbedPane libraryMainPane;
	CardLayout libraryPaneCardLayout;
	
	// Library Tab
	
	JPanel libraryCommandsPane;
	
	LibraryMainTitleLabel libraryMainTitleLabel;
	CommandButton loadLibraryButton;
	
	TranslatableLabel librarySortMethodLabel;
	SteamGamesSortMethodComboBox librarySortMethodComboBox;
	
	TranslatableLabel libraryDisplayModeLabel;
	SteamGamesDisplayModeComboBox libraryDisplayModeComboBox;
	
	private JPanel libraryOptionsPane;
	private JPanel libraryLeftClickActionPane;
	TranslatableLabel libraryLeftClickActionLabel;
	GameLeftClickActionButton libraryLeftClickSelectButton;
	GameLeftClickActionButton libraryLeftClickLaunchButton;
	
	JPanel libraryPane;
	
	JScrollPane libraryScrollPane;
	JScrollPane gamesLibraryListScrollPane;
	
	JPanel buttonsLibraryPane;
	ButtonGroup gamesLibraryButtonGroup;

	LaunchButton launchButton1;
	LaunchButton launchButton2;
	LaunchButton launchButton3;
	LaunchButton launchButton4;
	LaunchButton launchButton5;
	LaunchButton launchButton6;	

	// Library Statistics Pane
	
	JPanel libraryStatisticsMainPane;
	
	TranslatableLabel libraryTotalGamesLabel;
	JTextField libraryTotalGamesTextField;
	
	TranslatableLabel libraryTotalGamesWithStatsLabel;
	JTextField libraryTotalGamesWithStatsTextField;
	
	TranslatableLabel libraryTotalGamesWithGlobalStatsLabel;
	JTextField libraryTotalGamesWithGlobalStatsTextField;
	
	TranslatableLabel libraryTotalGamesWithStoreLinkLabel;
	JTextField libraryTotalGamesWithStoreLinkTextField;
	
	TranslatableLabel libraryTotalWastedHoursLabel;
	JTextField libraryTotalWastedHoursTextField;
	JLabel libraryTotalWastedHoursFormattedLabel;
	
	TranslatableLabel libraryTotalHoursLast2WeeksLabel;
	JTextField libraryTotalHoursLast2WeeksTextField;
	JLabel libraryTotalHoursLast2WeeksFormattedLabel;
	
	private CommandButton loadAllGamesStatsButton;
	
	TranslatableLabel libraryTotalFinishedGamesLabel;
	JTextField libraryTotalFinishedGamesTextField;
	
	TranslatableLabel libraryTotalGamesWithInvalidStatsLabel;
	JTextField libraryTotalGamesWithInvalidStatsTextField;
	
	TranslatableLabel libraryTotalAchievementsLabel;
	JTextField libraryTotalAchievementsTextField;
	
	TranslatableLabel libraryTotalUnlockedAchievementsLabel;
	JTextField libraryTotalUnlockedAchievementsTextField;
	
	TranslatableLabel libraryPercentageAchievedLabel;
	JTextField libraryPercentageAchievedTextField;
	
	// Game Tab
	
	JPanel gameCommandsPane;
	
	CurrentGameTitleLabel currentGameTitleLabel;
	CommandButton loadGameStatsButton;
	
	TranslatableLabel steamAchievementsSortMethodLabel;
	SteamAchievementsSortMethodComboBox steamAchievementsSortMethodComboBox;
	
	TranslatableLabel steamAchievementsColumnsSortMethodLabel;
	SteamAchievementsListsSortMethodComboBox steamAchievementsListsSortMethodComboBox;

	private JPanel gameOptionsPane;
	private JPanel gameLeftClickActionPane;
	TranslatableLabel gameLeftClickActionLabel;
	GameLeftClickActionButton gameLeftClickSelectButton;
	GameLeftClickActionButton gameLeftClickLaunchButton;
	
	GameLauncher currentGameLauncher;
	LaunchButton currentLaunchButton;
	SteamLaunchMethodComboBox currentSteamLaunchMethodComboBox;
	JTextField currentGameArgumentsTextField;
	
	TranslatableLabel currentGameHoursPlayedLast2WeeksLabel;
	JTextField currentGameHoursPlayedLast2Weeks;
	TranslatableLabel currentGameHoursPlayedTotalLabel;
	JTextField currentGameHoursPlayedTotal;

	CommandButton loadAllAchievementsButton;
	TranslatableLabel friendsWithSameGameLabel;
	JScrollPane friendsWithSameGameScrollPane;
	JPanel friendsWithSameGamePane;

	JScrollPane steamAchievementsScrollPane;
	
	// Profile Main Tab
	
	JTabbedPane profilePane;
	
	JPanel profileCommandsPane;
	
	CurrentProfileTitleLabel currentProfileTitleLabel;
	
	CommandButton loadProfileButton;
	CommandButton addProfileButton;
	
	KnownProfilesComboBox knownProfilesComboBox;
	
	TranslatableLabel steamGroupsSortMethodLabel;
	SteamGroupsSortMethodComboBox steamGroupsSortMethodComboBox;
	
	TranslatableLabel steamFriendsSortMethodLabel;
	SteamFriendsSortMethodComboBox steamFriendsSortMethodComboBox;
	
	private JPanel steamGroupsDisplayModePane;
	TranslatableLabel steamGroupsDisplayModeLabel;
	JToggleButton steamGroupsDisplayModeList;
	JToggleButton steamGroupsDisplayModeGrid;
	
	private JPanel steamFriendsDisplayModePane;
	TranslatableLabel steamFriendsDisplayModeLabel;
	JToggleButton steamFriendsDisplayModeList;
	JToggleButton steamFriendsDisplayModeGrid;
	
	private JPanel profileOptionsPane;
	
	private JPanel profileLeftClickActionPane;
	TranslatableLabel profileLeftClickActionLabel;
	GameLeftClickActionButton profileLeftClickSelectButton;
	GameLeftClickActionButton profileLeftClickLaunchButton;

	// Profile Sub Tab
	
	JPanel profileSummaryPane;
	JPanel profileStatusPane;
	JPanel profileGroupsPane;
	JPanel profileFriendsPane;

	// Profile Summary Sub Tab
	
	IconPane gamerProfileIconFull;
	
	TranslatableLabel realNameLabel;
	JTextField gamerProfileRealName;
	TranslatableLabel locationLabel;
	JTextField gamerProfileLocation;
	TranslatableLabel customURLLabel;
	JTextField gamerProfileCustomURL;
	TranslatableLabel summaryLabel;
	JScrollPane gamerProfileSummaryScrollPane;
	JEditorPane gamerProfileSummary;
	
	TranslatableLabel memberSinceLabel;
	JTextField gamerProfileMemberSince;
	TranslatableLabel steamRatingLabel;
	JTextField gamerProfileSteamRating;
	TranslatableLabel hoursPlayedLastTwoWeeksLabel;
	JTextField gamerProfileHoursPlayedLast2Weeks;
	
	TranslatableLabel headlineLabel;
	JTextPane gamerProfileHeadline;
	
	JScrollPane gamerProfileHeadlineScrollPane;
	JPanel mostPlayedGamesPane;

	JScrollPane mostPlayedGamesScrollPane;
	MostPlayedGameLauncher mostPlayedGameLauncher1;
	MostPlayedGameLauncher mostPlayedGameLauncher2;
	MostPlayedGameLauncher mostPlayedGameLauncher3;

	// Profile Status Sub Tab
	
	TranslatableLabel gamerProfileAccountGamerSteamId64Label;
	JTextField gamerProfileAccountGamerSteamId64;
	TranslatableLabel gamerProfileAccountOnlineStateLabel;
	JTextField gamerProfileAccountOnlineState;
	TranslatableLabel gamerProfileAccountGamerSteamIdLabel;
	JTextField gamerProfileAccountGamerSteamId;
	TranslatableLabel gamerProfileAccountStateMessageLabel;
	JTextField gamerProfileAccountStateMessage;
	TranslatableLabel gamerProfileAccountPrivacyStateLabel;
	JTextField gamerProfileAccountPrivacyState;
	TranslatableLabel gamerProfileAccountVisibilityStateLabel;
	JTextField gamerProfileAccountVisibilityState;
	TranslatableLabel gamerProfileAccountVacBannedLabel;
	JTextField gamerProfileAccountVacBanned;
	TranslatableLabel gamerProfileAccountTradeBanStateLabel;
	JTextField gamerProfileAccountTradeBanState;
	TranslatableLabel gamerProfileAccountLimitedAccountLabel;
	JTextField gamerProfileAccountLimitedAccount;

	// Profile Steam Groups Sub Tab
	
	CardLayout profileSteamGroupsTabCardLayout;
	JPanel steamGroupsButtonsPane;
	
	JScrollPane steamGroupsScrollPane;
	ButtonGroup steamGroupsButtonGroup;
	
	// Design purpose only
	
	SteamGroupButton steamGroupButton1;
	SteamGroupButton steamGroupButton2;
	
	JScrollPane steamGroupsListScrollPane;
	
	// Profile Steam Friends Sub Tab
	
	CardLayout profileSteamFriendsTabCardLayout;
	JPanel steamFriendsButtonsPane;
	
	JScrollPane steamFriendsScrollPane;
	ButtonGroup steamFriendsButtonGroup;
	
	// Design purpose only
	
	SteamFriendButton steamFriendButton1;
	SteamFriendButton steamFriendButton2;
	SteamFriendButton steamFriendButton3;
	SteamFriendButton steamFriendButton4;
	SteamFriendButton steamFriendButton5;
	
	JScrollPane steamFriendsListScrollPane;
	
	// Options Tab
	
	TranslatableLabel windowsDistributionLabel;
	CommandButton resetOptionsButton;
	TranslatableTextField windowsDistributionTextField;
	
	TranslatableLabel steamExecutableLabel;
	CommandButton steamExecutableButton;
	TranslatableTextField steamExecutableTextField;
	
	TranslatableLabel gamerSteamIdLabel;
	TranslatableTextField gamerSteamIdTextField;
	
	TranslatableLabel defaultSteamLaunchMethodLabel;
	SteamLaunchMethodComboBox defaultSteamLaunchMethodComboBox;
	
	TranslatableLabel gameLeftClickActionOptionLabel;
	GameLeftClickActionComboBox gameLeftClickActionComboBox;
	
	TranslatableLabel dumpModeLabel;
	DumpModeComboBox dumpModeComboBox;
	
	TranslatableLabel displayToolTipsLabel;
	JToggleButton displayTooltipsYesButton;
	JToggleButton displayTooltipsNoButton;
	
	TranslatableLabel buttonsDisplayModeLabel;
	ButtonsDisplayModeComboBox buttonsDisplayModeComboBox;
	
	TranslatableLabel localeChoiceLabel;
	LocaleChoiceComboBox localeChoiceComboBox;
	
	TranslatableLabel lookAndFeelLabel;
	LookAndFeelInfoComboBox lookAndFeelInfoComboBox;

	//
	// Main Menu
	//
	
	private JMenuBar mainMenuBar;
	
	// File Menu
	
	private TranslatableMenu fileMenu;

	LoadParametersAction loadParametersAction;
	private JMenuItem loadParametersMenuItem;
	
	SaveParametersAction saveParametersAction;
	private JMenuItem saveParametersMenuItem;
	
	ExitAction exitAction;
	private JMenuItem exitMenuItem;

	// Goto Menu
	
	private TranslatableMenu gotoMenu;
	
	GotoAction gotoControlsAction;
	GotoAction gotoLibraryAction;
	GotoAction gotoGameAction;
	GotoAction gotoProfileAction;
	GotoAction gotoOptionsAction;
	GotoAction gotoProfileSummaryAction;
	GotoAction gotoProfileStatusAction;
	GotoAction gotoProfileGroupsAction;
	GotoAction gotoProfileFriendsAction;
	GotoAction gotoLibraryGamesListAction;
	GotoAction gotoLibraryStatisticsAction;
	
	// Controls Menu
	
	private TranslatableMenu controlsMenu;
	
	RollAction rollAction;
	private JMenuItem rollMenuItem;
	
	private TranslatableMenu gameChoiceMenu;
	
	private GameChoiceAction oneGameChoiceAction;
	JRadioButtonMenuItem oneGameChoiceMenuItem;
	private GameChoiceAction twoGamesChoiceAction;
	JRadioButtonMenuItem twoGamesChoiceMenuItem;
	private GameChoiceAction threeGamesChoiceAction;
	JRadioButtonMenuItem threeGamesChoiceMenuItem;
	
	RefreshGamesListAction refreshGamesListAction;
	private JMenuItem refreshGamesListMenuItem;
	
	ScrollLockAction scrollLockAction;
	private JMenuItem scrollLockMenuItem;
	
	DebugAction debugAction;
	private JMenuItem debugMenuItem;
	
	ClearConsoleAction clearConsoleAction;
	private JMenuItem clearConsoleMenuItem;
	
	ViewParametersAction viewParametersAction;
	private JMenuItem viewParametersMenuItem;
	
	// Library Menu
	
	private TranslatableMenu libraryMenu;
	
	LoadLibraryAction loadLibraryAction;
	private JMenuItem loadLibraryMenuItem;

	LoadAllGamesStatsAction loadAllGamesStatsAction;
	private JMenuItem loadAllGamesStatsMenuItem;
	
	private TranslatableMenu libraryDisplayModeMenu;
	private ActionGroup libraryDisplayModeActionGroup;
	
	private TranslatableMenu librarySortMethodMenu;
	private ActionGroup librarySortMethodActionGroup;
	
	// Game Menu
	
	private TranslatableMenu gameMenu;
	
	LoadGameStatsAction loadGameStatsAction;
	private JMenuItem loadGameStatsMenuItem;
	
	LoadAllAchievementsAction loadAllAchievementsAction;
	private JMenuItem loadAllAchievementsMenuItem;
	
	private TranslatableMenu steamAchievementsSortMethodMenu;
	private ActionGroup steamAchievementsSortMethodActionGroup;
	
	private TranslatableMenu steamAchievementsListsSortMethodMenu;
	private ActionGroup steamAchievementsListsSortMethodActionGroup;
	
	// Profile Menu
	
	private TranslatableMenu profileMenu;
	
	LoadProfileAction loadProfileAction;
	private JMenuItem loadProfileMenuItem;
	
	AddProfileAction addProfileAction;
	private JMenuItem addProfileMenuItem;
	
	private TranslatableMenu steamGroupsSortMethodMenu;
	private ActionGroup steamGroupsSortMethodActionGroup;
	
	private TranslatableMenu steamFriendsSortMethodMenu;
	private ActionGroup steamFriendsSortMethodActionGroup;
	
	private TranslatableMenu steamGroupsDisplayModeMenu;
	ActionGroup steamGroupsDisplayModeActionGroup;
	SteamGroupsDisplayModeAction steamGroupsDisplayModeListAction;
	SteamGroupsDisplayModeAction steamGroupsDisplayModeGridAction;
	
	private TranslatableMenu steamFriendsDisplayModeMenu;
	ActionGroup steamFriendsDisplayModeActionGroup;
	SteamFriendsDisplayModeAction steamFriendsDisplayModeListAction;
	SteamFriendsDisplayModeAction steamFriendsDisplayModeGridAction;
	
	// Options Menu
	
	private TranslatableMenu optionsMenu;
	
	ResetOptionsAction resetOptionsAction;
	private JMenuItem resetOptionsMenuItem;
	SteamExecutableAction steamExecutableAction;
	private JMenuItem steamExecutableMenuItem;
	
	private TranslatableMenu defaultSteamLaunchMethodMenu;
	private ActionGroup defaultSteamLaunchMethodActionGroup;
	
	private TranslatableMenu dumpModeMenu;
	private ActionGroup dumpModeActionGroup;
	
	private TranslatableMenu displayToolTipsMenu;
	private ActionGroup displayToolTipsActionGroup;
	DisplayToolTipsAction displayToolTipsYesAction;
	DisplayToolTipsAction displayToolTipsNoAction;

	private TranslatableMenu buttonsDisplayModeMenu;
	private ActionGroup buttonsDisplayModeActionGroup;
	ButtonsDisplayModeAction buttonsDisplayModeIconAndTextAction;
	ButtonsDisplayModeAction buttonsDisplayModeIconAction;
	ButtonsDisplayModeAction buttonsDisplayModeTextAction;
	
	private TranslatableMenu localeChoiceMenu;
	private ActionGroup localeChoiceActionGroup;
	
	private TranslatableMenu lookAndFeelMenu;
	private ActionGroup lookAndFeelActionGroup;
	
	// Help Menu
	
	private TranslatableMenu helpMenu;
	
	AboutAction aboutAction;
	private JMenuItem aboutMenuItem;
	private JPanel gamePane;
	
	//
	// Application frame modification
	//
	
	/**
	 * Restore initial frame size
	 */
	public void setInitialSize() {
		setSize(initialFrameWidth, initialFrameHeight);
	}
	
	/**
	 * Restore initial frame size
	 * Center frame
	 */
	public void setInitialiseSizeAndCenter() {
		setInitialSize();
		setLocationRelativeTo(null);
	}
	
	//
	// Setup, create, startup & run application
	// 
	
	/**
	 * Setup application
	 */
	private void setup() {
		// Wrap -this- reference to use it 
		// in a mask for WindowBuilder
		me = new WindowBuilderMask(this);
		// Create and setup a librarian
		(librarian = new Librarian(me)).setup();
	}
	
	/**
	 * Create the Main Menu
	 */
	private void createMainMenu() {
		
		mainMenuBar = new JMenuBar();
		setJMenuBar(mainMenuBar);
		
		//
		// File Menu
		//
		
		fileMenu = new TranslatableMenu(me, "fileMenuLabel");
		fileMenu.setIcon(GamesLibrary.fileMenuIcon);
		mainMenuBar.add(fileMenu);
		
		// loadParametersMenuItem
		loadParametersAction = new LoadParametersAction(me);
		loadParametersMenuItem = fileMenu.add(loadParametersAction);
		
		// saveParametersMenuItem
		saveParametersAction = new SaveParametersAction(me);
		saveParametersMenuItem = fileMenu.add(saveParametersAction);
		
		fileMenu.addSeparator();
		
		// exitMenuItem
		exitAction = new ExitAction(me);
		exitMenuItem = fileMenu.add(exitAction);
		
		//
		// Goto Menu
		//
		
		gotoMenu = new TranslatableMenu(me, "gotoMenuLabel");
		gotoMenu.setIcon(GamesLibrary.gotoMenuIcon);
		mainMenuBar.add(gotoMenu);
		
		// gotoMenuItems
		gotoControlsAction = new GotoAction(me, LibrarianTabEnum.Controls);
		gotoLibraryAction = new GotoAction(me, LibrarianTabEnum.Library);
		gotoGameAction = new GotoAction(me, LibrarianTabEnum.Game);
		gotoProfileAction = new GotoAction(me, LibrarianTabEnum.Profile);
		gotoOptionsAction = new GotoAction(me, LibrarianTabEnum.Options);
		
		// gotoControls
		gotoMenu.add(gotoControlsAction);
		
		// gotoLibrary
		JMenu gotoLibrarySubTabSubMenu = new JMenu(gotoLibraryAction);
		
		// gotoLibrary Sub Items
		JMenuItem gotoLibraryGamesListMenuItem = new JMenuItem(gotoLibraryGamesListAction = new GotoAction(me, LibraryTabEnum.LibraryGamesList));
		JMenuItem gotoLibraryStatisticsMenuItem = new JMenuItem(gotoLibraryStatisticsAction = new GotoAction(me, LibraryTabEnum.LibraryStatistics));
		
		gotoLibrarySubTabSubMenu.add(gotoLibraryGamesListMenuItem);
		gotoLibrarySubTabSubMenu.add(gotoLibraryStatisticsMenuItem);
		
		gotoMenu.add(gotoLibrarySubTabSubMenu);
		
		// gotoGame
		gotoMenu.add(gotoGameAction);
		
		// gotoProfile
		JMenu gotoProfileSubTabSubMenu = new JMenu(gotoProfileAction);
		
		// gotoProfile Sub Items
		JMenuItem gotoProfileSummaryMenuItem = new JMenuItem(gotoProfileSummaryAction = new GotoAction(me, ProfileTabEnum.Summary));
		JMenuItem gotoProfileStatusMenuItem = new JMenuItem(gotoProfileStatusAction = new GotoAction(me, ProfileTabEnum.Status));
		JMenuItem gotoProfileGroupsMenuItem = new JMenuItem(gotoProfileGroupsAction = new GotoAction(me, ProfileTabEnum.Groups));
		JMenuItem gotoProfileFriendsMenuItem = new JMenuItem(gotoProfileFriendsAction = new GotoAction(me, ProfileTabEnum.Friends));
		
		gotoProfileSubTabSubMenu.add(gotoProfileSummaryMenuItem);
		gotoProfileSubTabSubMenu.add(gotoProfileStatusMenuItem);
		gotoProfileSubTabSubMenu.add(gotoProfileGroupsMenuItem);
		gotoProfileSubTabSubMenu.add(gotoProfileFriendsMenuItem);
		
		gotoMenu.add(gotoProfileSubTabSubMenu);
		
		// gotoOptions
		gotoMenu.add(gotoOptionsAction);
		
		//
		// Controls Menu
		//
		
		controlsMenu = new TranslatableMenu(me, "controlsTabTitle");
		controlsMenu.setIcon(GamesLibrary.controlsMenuIcon);
		mainMenuBar.add(controlsMenu);
		
		// rollMenuItem
		rollAction = new RollAction(me);
		rollMenuItem = controlsMenu.add(rollAction);
		
		// gameChoiceMenu
		gameChoiceMenu = new TranslatableMenu(me, "gameChoiceMenuLabel");
		gameChoiceMenu.setIcon(GamesLibrary.gameChoiceIcon);
		controlsMenu.add(gameChoiceMenu);
		
		// gameChoiceMenuItems
		oneGameChoiceAction = new GameChoiceAction(me, GameChoice.One);
		twoGamesChoiceAction = new GameChoiceAction(me, GameChoice.Two);
		threeGamesChoiceAction = new GameChoiceAction(me, GameChoice.Three);

		ActionGroup gameChoiceMenuButtonGroup = new ActionGroup();
		gameChoiceMenuButtonGroup.add(oneGameChoiceAction);
		gameChoiceMenuButtonGroup.add(twoGamesChoiceAction);
		gameChoiceMenuButtonGroup.add(threeGamesChoiceAction);
		
		oneGameChoiceMenuItem = (JRadioButtonMenuItem) ActionGroupFactory.getRadioMenuItem(oneGameChoiceAction);
		twoGamesChoiceMenuItem = (JRadioButtonMenuItem) ActionGroupFactory.getRadioMenuItem(twoGamesChoiceAction);
		threeGamesChoiceMenuItem = (JRadioButtonMenuItem) ActionGroupFactory.getRadioMenuItem(threeGamesChoiceAction);

		gameChoiceMenu.add(oneGameChoiceMenuItem);
		gameChoiceMenu.add(twoGamesChoiceMenuItem);
		gameChoiceMenu.add(threeGamesChoiceMenuItem);

		controlsMenu.addSeparator();
		
		// refreshGamesListMenuItem
		refreshGamesListAction = new RefreshGamesListAction(me);
		refreshGamesListMenuItem = controlsMenu.add(refreshGamesListAction);
		
		controlsMenu.addSeparator();
		
		// scrollLockMenuItem
		scrollLockAction = new ScrollLockAction(me);
		scrollLockMenuItem = controlsMenu.add(scrollLockAction);
				
		// debugMenuItem
		debugAction = new DebugAction(me);
		debugMenuItem = controlsMenu.add(debugAction);
		
		// clearConsoleMenuItem
		clearConsoleAction = new ClearConsoleAction(me);
		clearConsoleMenuItem = controlsMenu.add(clearConsoleAction);
		
		// viewParametersMenuItem
		viewParametersAction = new ViewParametersAction(me);
		viewParametersMenuItem = controlsMenu.add(viewParametersAction);
		
		//
		// Library Menu
		//
		
		libraryMenu = new TranslatableMenu(me, "libraryMenuLabel");
		libraryMenu.setIcon(GamesLibrary.libraryMenuIcon);
		mainMenuBar.add(libraryMenu);
		
		// loadLibraryMenuItem
		loadLibraryAction = new LoadLibraryAction(me);
		loadLibraryMenuItem = libraryMenu.add(loadLibraryAction);
		
		loadAllGamesStatsAction = new LoadAllGamesStatsAction(me);
		loadAllGamesStatsMenuItem = libraryMenu.add(loadAllGamesStatsAction);
		
		libraryMenu.addSeparator();
		
		// libraryDisplayModeMenu
		libraryDisplayModeMenu = new TranslatableMenu(me, "libraryDisplayModeMenuLabel");
		libraryDisplayModeMenu.setIcon(GamesLibrary.libraryDisplayMode);
		libraryMenu.add(libraryDisplayModeMenu);
		
		libraryDisplayModeActionGroup = new ActionGroup();
		for (SteamGamesDisplayMode steamGamesDisplayMode : SteamGamesDisplayMode.values()) {
			SteamGamesDisplayModeAction steamGamesSortMethodAction = new SteamGamesDisplayModeAction(me, steamGamesDisplayMode);
			libraryDisplayModeActionGroup.add(steamGamesSortMethodAction);
			libraryDisplayModeMenu.add(ActionGroupFactory.getRadioMenuItem(steamGamesSortMethodAction));
		}
		
		// librarySortMethodMenu
		librarySortMethodMenu = new TranslatableMenu(me, "librarySortMethodMenuLabel");
		librarySortMethodMenu.setIcon(GamesLibrary.librarySortMethod);
		libraryMenu.add(librarySortMethodMenu);
		
		librarySortMethodActionGroup = new ActionGroup();
		for (SteamGamesSortMethod steamGamesSortMethod : SteamGamesSortMethod.values()) {
			SteamGamesSortMethodAction steamGamesSortMethodAction = new SteamGamesSortMethodAction(me, steamGamesSortMethod);
			librarySortMethodActionGroup.add(steamGamesSortMethodAction);
			librarySortMethodMenu.add(ActionGroupFactory.getRadioMenuItem(steamGamesSortMethodAction));
		}
		
		//
		// Game Menu
		//
		
		gameMenu = new TranslatableMenu(me, "gameMenuLabel");
		gameMenu.setIcon(GamesLibrary.gameMenuIcon);
		mainMenuBar.add(gameMenu);
		
		// loadGameStatsMenuItem
		loadGameStatsAction = new LoadGameStatsAction(me);
		loadGameStatsMenuItem = gameMenu.add(loadGameStatsAction);
		
		// loadAllAchievementsMenuItem
		loadAllAchievementsAction = new LoadAllAchievementsAction(me);
		loadAllAchievementsMenuItem = gameMenu.add(loadAllAchievementsAction);
		
		gameMenu.addSeparator();
		
		// achievementsSortMethodMenu
		steamAchievementsSortMethodMenu = new TranslatableMenu(me, "steamAchievementsSortMethodMenuLabel");
		steamAchievementsSortMethodMenu.setIcon(GamesLibrary.librarySortMethod);
		gameMenu.add(steamAchievementsSortMethodMenu);
		
		steamAchievementsSortMethodActionGroup = new ActionGroup();
		for (SteamAchievementsSortMethod steamAchievementsSortMethod : SteamAchievementsSortMethod.values()) {
			SteamAchievementsSortMethodAction steamAchievementsSortMethodAction = new SteamAchievementsSortMethodAction(me, steamAchievementsSortMethod);
			steamAchievementsSortMethodActionGroup.add(steamAchievementsSortMethodAction);
			steamAchievementsSortMethodMenu.add(ActionGroupFactory.getRadioMenuItem(steamAchievementsSortMethodAction));
		}

		// steamAchievementsListsSortMethodMenu
		steamAchievementsListsSortMethodMenu = new TranslatableMenu(me, "steamAchievementsListsSortMethodMenuLabel");
		steamAchievementsListsSortMethodMenu.setIcon(GamesLibrary.librarySortMethod);
		gameMenu.add(steamAchievementsListsSortMethodMenu);
		
		steamAchievementsListsSortMethodActionGroup = new ActionGroup();
		for (SteamAchievementsListsSortMethod steamAchievementsListsSortMethod : SteamAchievementsListsSortMethod.values()) {
			SteamAchievementsListsSortMethodAction steamAchievementsListsSortMethodAction = new SteamAchievementsListsSortMethodAction(me, steamAchievementsListsSortMethod);
			steamAchievementsListsSortMethodActionGroup.add(steamAchievementsListsSortMethodAction);
			steamAchievementsListsSortMethodMenu.add(ActionGroupFactory.getRadioMenuItem(steamAchievementsListsSortMethodAction));
		}
		
		//
		// Profile Menu
		//
		
		profileMenu = new TranslatableMenu(me, "profileMenuLabel");
		profileMenu.setIcon(GamesLibrary.profileMenuIcon);
		mainMenuBar.add(profileMenu);
		
		// loadProfileMenuItem
		loadProfileAction = new LoadProfileAction(me);
		loadProfileMenuItem = profileMenu.add(loadProfileAction);
		
		// addProfileMenuItem
		addProfileAction = new AddProfileAction(me);
		addProfileMenuItem = profileMenu.add(addProfileAction);
		
		profileMenu.addSeparator();
		
		// steamGroupsSortMethodMenu
		steamGroupsSortMethodMenu = new TranslatableMenu(me, "steamGroupsSortMethodMenuLabel");
		steamGroupsSortMethodMenu.setIcon(GamesLibrary.librarySortMethod);
		profileMenu.add(steamGroupsSortMethodMenu);
		
		steamGroupsSortMethodActionGroup = new ActionGroup();
		for (SteamGroupsSortMethod steamGroupsSortMethod : SteamGroupsSortMethod.values()) {
			SteamGroupsSortMethodAction steamGroupsSortMethodAction = new SteamGroupsSortMethodAction(me, steamGroupsSortMethod);
			steamGroupsSortMethodActionGroup.add(steamGroupsSortMethodAction);
			steamGroupsSortMethodMenu.add(ActionGroupFactory.getRadioMenuItem(steamGroupsSortMethodAction));
		}

		// steamFriendsSortMethodMenu
		steamFriendsSortMethodMenu = new TranslatableMenu(me, "steamFriendsSortMethodMenuLabel");
		steamFriendsSortMethodMenu.setIcon(GamesLibrary.librarySortMethod);
		profileMenu.add(steamFriendsSortMethodMenu);
		
		steamFriendsSortMethodActionGroup = new ActionGroup();
		for (SteamFriendsSortMethod steamFriendsSortMethod : SteamFriendsSortMethod.values()) {
			SteamFriendsSortMethodAction steamFriendsSortMethodAction = new SteamFriendsSortMethodAction(me, steamFriendsSortMethod);
			steamFriendsSortMethodActionGroup.add(steamFriendsSortMethodAction);
			steamFriendsSortMethodMenu.add(ActionGroupFactory.getRadioMenuItem(steamFriendsSortMethodAction));
		}
		
		profileMenu.addSeparator();
		
		// steamGroupsDisplayModeMenu
		steamGroupsDisplayModeMenu = new TranslatableMenu(me, "steamGroupsDisplayModeMenuLabel");
		steamGroupsDisplayModeMenu.setIcon(GamesLibrary.groupsIcon);
		profileMenu.add(steamGroupsDisplayModeMenu);
		
		steamGroupsDisplayModeListAction = new SteamGroupsDisplayModeAction(me, SteamGroupsDisplayMode.List); // WindowBuilder
		steamGroupsDisplayModeGridAction = new SteamGroupsDisplayModeAction(me, SteamGroupsDisplayMode.Grid); // WindowBuilder
		steamGroupsDisplayModeActionGroup = new ActionGroup();
		SteamGroupsDisplayModeAction steamGroupsDisplayModeAction = null;
		for (SteamGroupsDisplayMode steamGroupsDisplayMode : SteamGroupsDisplayMode.values()) {
			switch (steamGroupsDisplayMode) {
			case List : steamGroupsDisplayModeAction = steamGroupsDisplayModeListAction; break;
			case Grid : steamGroupsDisplayModeAction = steamGroupsDisplayModeGridAction; break;
			}
			steamGroupsDisplayModeActionGroup.add(steamGroupsDisplayModeAction);
			steamGroupsDisplayModeMenu.add(ActionGroupFactory.getRadioMenuItem(steamGroupsDisplayModeAction));
		}
		
		// steamFriendsDisplayModeMenu
		steamFriendsDisplayModeMenu = new TranslatableMenu(me, "steamFriendsDisplayModeMenuLabel");
		steamFriendsDisplayModeMenu.setIcon(GamesLibrary.friendsIcon);
		profileMenu.add(steamFriendsDisplayModeMenu);
		
		steamFriendsDisplayModeListAction = new SteamFriendsDisplayModeAction(me, SteamFriendsDisplayMode.List); // WindowBuilder
		steamFriendsDisplayModeGridAction = new SteamFriendsDisplayModeAction(me, SteamFriendsDisplayMode.Grid); // WindowBuilder
		steamFriendsDisplayModeActionGroup = new ActionGroup();
		SteamFriendsDisplayModeAction steamFriendsDisplayModeAction = null;
		for (SteamFriendsDisplayMode steamFriendsDisplayMode : SteamFriendsDisplayMode.values()) {
			switch (steamFriendsDisplayMode) {
			case List : steamFriendsDisplayModeAction = steamFriendsDisplayModeListAction; break;
			case Grid : steamFriendsDisplayModeAction = steamFriendsDisplayModeGridAction; break;
			}
			steamFriendsDisplayModeActionGroup.add(steamFriendsDisplayModeAction);
			steamFriendsDisplayModeMenu.add(ActionGroupFactory.getRadioMenuItem(steamFriendsDisplayModeAction));
		}
		
		//
		// Options Menu
		//
		
		optionsMenu = new TranslatableMenu(me, "optionsMenuLabel");
		optionsMenu.setIcon(GamesLibrary.optionsMenuIcon);
		mainMenuBar.add(optionsMenu);
		
		// resetOptionsMenuItem
		resetOptionsAction = new ResetOptionsAction(me);
		resetOptionsMenuItem = optionsMenu.add(resetOptionsAction);
		
		// steamExecutableMenuItem
		steamExecutableAction = new SteamExecutableAction(me);
		steamExecutableMenuItem = optionsMenu.add(steamExecutableAction);

		optionsMenu.addSeparator();
		
		// defaultSteamLaunchMethodMenu
		defaultSteamLaunchMethodMenu = new TranslatableMenu(me, "defaultSteamLaunchMethodMenuLabel");
		defaultSteamLaunchMethodMenu.setIcon(GamesLibrary.defaultSteamLaunchMethodIcon);
		optionsMenu.add(defaultSteamLaunchMethodMenu);

		defaultSteamLaunchMethodActionGroup = new ActionGroup();
		for (SteamLaunchMethod steamLaunchMethod : SteamLaunchMethod.values()) {
			DefaultSteamLaunchMethodAction defaultSteamLaunchMethodAction = new DefaultSteamLaunchMethodAction(me, steamLaunchMethod);
			defaultSteamLaunchMethodActionGroup.add(defaultSteamLaunchMethodAction);
			defaultSteamLaunchMethodMenu.add(ActionGroupFactory.getRadioMenuItem(defaultSteamLaunchMethodAction));
		}
		
		// dumpModeMenu
		dumpModeMenu = new TranslatableMenu(me, "dumpModeMenuLabel");
		dumpModeMenu.setIcon(GamesLibrary.dumpModeIcon);
		optionsMenu.add(dumpModeMenu);
		
		dumpModeActionGroup = new ActionGroup();
		for (DumpMode dumpMode : DumpMode.values()) {
			DumpModeAction dumpModeAction = new DumpModeAction(me, dumpMode);
			dumpModeActionGroup.add(dumpModeAction);
			dumpModeMenu.add(ActionGroupFactory.getRadioMenuItem(dumpModeAction));
		}
		
		// displayToolTipsMenu
		displayToolTipsMenu = new TranslatableMenu(me, "displayToolTipsMenuLabel");
		displayToolTipsMenu.setIcon(GamesLibrary.displayToolTipsIcon);
		optionsMenu.add(displayToolTipsMenu);

		displayToolTipsYesAction = new DisplayToolTipsAction(me, true);
		displayToolTipsNoAction = new DisplayToolTipsAction(me, false);
		
		displayToolTipsActionGroup = new ActionGroup();
		displayToolTipsActionGroup.add(displayToolTipsYesAction);
		displayToolTipsActionGroup.add(displayToolTipsNoAction);

		displayToolTipsMenu.add(ActionGroupFactory.getRadioMenuItem(displayToolTipsYesAction));
		displayToolTipsMenu.add(ActionGroupFactory.getRadioMenuItem(displayToolTipsNoAction));
		
		// buttonsDisplayModeMenu
		buttonsDisplayModeMenu = new TranslatableMenu(me, "buttonsDisplayModeMenuLabel");
		buttonsDisplayModeMenu.setIcon(GamesLibrary.buttonsDisplayModeIcon);
		optionsMenu.add(buttonsDisplayModeMenu);

		buttonsDisplayModeIconAndTextAction = new ButtonsDisplayModeAction(me, ButtonsDisplayMode.IconAndText);
		buttonsDisplayModeIconAction = new ButtonsDisplayModeAction(me, ButtonsDisplayMode.Icon);
		buttonsDisplayModeTextAction = new ButtonsDisplayModeAction(me, ButtonsDisplayMode.Text);
		
		buttonsDisplayModeActionGroup = new ActionGroup();
		buttonsDisplayModeActionGroup.add(buttonsDisplayModeIconAndTextAction);
		buttonsDisplayModeActionGroup.add(buttonsDisplayModeIconAction);
		buttonsDisplayModeActionGroup.add(buttonsDisplayModeTextAction);

		buttonsDisplayModeMenu.add(ActionGroupFactory.getRadioMenuItem(buttonsDisplayModeIconAndTextAction));
		buttonsDisplayModeMenu.add(ActionGroupFactory.getRadioMenuItem(buttonsDisplayModeIconAction));
		buttonsDisplayModeMenu.add(ActionGroupFactory.getRadioMenuItem(buttonsDisplayModeTextAction));
		
		optionsMenu.addSeparator();
		
		// localeChoiceMenu
		localeChoiceMenu = new TranslatableMenu(me, "localeChoiceMenuLabel");
		localeChoiceMenu.setIcon(GamesLibrary.localeChoiceIcon);
		optionsMenu.add(localeChoiceMenu);
		
		localeChoiceActionGroup = new ActionGroup();
		for (LocaleChoice localeChoice : LocaleChoice.values()) {
			LocaleChoiceAction localeChoiceAction = new LocaleChoiceAction(me, localeChoice);
			localeChoiceActionGroup.add(localeChoiceAction);
			localeChoiceMenu.add(ActionGroupFactory.getRadioMenuItem(localeChoiceAction));
		}
		
		// lookAndFeelMenu
		lookAndFeelMenu = new TranslatableMenu(me, "lookAndFeelMenuLabel");
		lookAndFeelMenu.setIcon(GamesLibrary.lookAndFeelIcon);
		optionsMenu.add(lookAndFeelMenu);
		
		lookAndFeelActionGroup = new ActionGroup();
		for (LookAndFeelInfo lookAndFeelInfo : Parameters.lookAndFeelInfos) {
			LookAndFeelAction lookAndFeelAction = new LookAndFeelAction(me, lookAndFeelInfo);
			lookAndFeelActionGroup.add(lookAndFeelAction);
			lookAndFeelMenu.add(ActionGroupFactory.getRadioMenuItem(lookAndFeelAction));
		}
		
		//
		// Help Menu
		//
		
		helpMenu = new TranslatableMenu(me, "helpMenuLabel");
		mainMenuBar.add(helpMenu);
		
		// aboutMenuItem
		aboutAction = new AboutAction(me);
		aboutMenuItem = helpMenu.add(aboutAction);
		
		@SuppressWarnings("unused")
		JMenuItem[] menuitems = {
			loadParametersMenuItem, saveParametersMenuItem, exitMenuItem, 
			rollMenuItem, oneGameChoiceMenuItem, twoGamesChoiceMenuItem, threeGamesChoiceMenuItem,
			refreshGamesListMenuItem, scrollLockMenuItem, debugMenuItem, clearConsoleMenuItem, viewParametersMenuItem,
			loadLibraryMenuItem, loadAllGamesStatsMenuItem,
			loadGameStatsMenuItem, loadAllAchievementsMenuItem,
			loadProfileMenuItem, addProfileMenuItem,
			resetOptionsMenuItem, steamExecutableMenuItem,
			aboutMenuItem
		};
		
	}
		
	/**
	 * Doing I18n during some other works will allow to update
	 * 
	 * <ul>
	 * <li>button state</li>
	 * <li>button tool tip</li>
	 * </ul>
	 * 
	 * Used as callback from :
	 * 
	 * <ul>
	 * <li>loadAllAchievements()</li>
	 * <li>updateFriendsWithSameGamePane()</li>
	 * <li>updateGameTab(SteamGame game)</li>
	 * <li>SteamFriendsGameStatsReader#done()</li>
	 * <li>SteamFriendWithSameGameButton#actionPerformed(ActionEvent event)</li>
	 * </ul>
	 */
	public void updateLoadAllAchievements() {
		loadAllAchievementsAction.translate();
	}
	
	/**
	 * Produce an HTML formatted tab title from passed title
	 * Made static to be supported by WindowBuilder
	 * @param title string to format
	 * @return an HTML formatted string
	 */
	static String getTabTitle(String title) {
		return "<html><div style='padding: 2px'>" + title + "</div></html>";
	}
	
	/**
	 * Create the Controls Tab
	 */
	private void createControlsTab() {
		
		// Main pane
		gamesLibrarianControls = new JPanel();
		gamesLibrarianControls.setName("controlsTab");
		gamesLibrarianControls.setLayout(new MigLayout("", "[][][grow]", "[][][][][][][][][grow]"));
		
		mainPane.addTab(getTabTitle(BundleManager.getUITexts(me, "controlsTabTitle")), null, gamesLibrarianControls, null);

		// rollButton
		rollButton = new CommandButton(me, rollAction);
		gamesLibrarianControls.add(rollButton, "cell 0 0,alignx left,aligny center");
		
		// gameNameTextField
		gameNameTextField = new TranslatableGameArgumentsTextField(me, null);
		gameNameTextField.setEditable(false);
		gameNameTextField.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		gamesLibrarianControls.add(gameNameTextField, "cell 1 0 2 1,growx,aligny center");
		
		// gameChoicePane
		gameChoicePane = new JPanel();
		gameChoicePane.setLayout(new MigLayout("", "[]", "10[][][]"));
		
		gamesLibrarianControls.add(gameChoicePane, "cell 0 1,alignx center,aligny top");
		
		gameChoiceButtonGroup = new ButtonGroup();
		
		oneGameRadioButton = (JRadioButton) ActionGroupFactory.getRadioButton(oneGameChoiceAction);
		
		gameChoicePane.add(oneGameRadioButton, "flowy,cell 0 0");
		gameChoiceButtonGroup.add(oneGameRadioButton);
		
		twoGamesRadioButton = (JRadioButton) ActionGroupFactory.getRadioButton(twoGamesChoiceAction);
		
		gameChoicePane.add(twoGamesRadioButton, "cell 0 1");
		gameChoiceButtonGroup.add(twoGamesRadioButton);
		
		threeGamesRadioButton = (JRadioButton) ActionGroupFactory.getRadioButton(threeGamesChoiceAction);
		
		gameChoicePane.add(threeGamesRadioButton, "cell 0 2");
		gameChoiceButtonGroup.add(threeGamesRadioButton);
		
		gameLaunchersPane = new JPanel();
		
		WrapLayout gameLaunchersPaneWrapLayout = new WrapLayout();
		gameLaunchersPaneWrapLayout.setAlignment(FlowLayout.LEFT);
		
		gameLaunchersPane.setLayout(gameLaunchersPaneWrapLayout);

		gamesLibrarianControls.add(gameLaunchersPane, "cell 1 1 2 1,growx,aligny top");

		// launchGame1Button
		launchGame1Button = new LaunchButton(me, "", LaunchType.rolled, GameChoice.One, null);
		steamLaunchMethod1ComboBox = new SteamLaunchMethodComboBox(me, launchGame1Button, SteamLaunchMethodComboBox.Type.GameLauncher);
		
		argumentsGame1TextField = new TranslatableGameArgumentsTextField(me, launchGame1Button);
		argumentsGame1TextField.setColumns(10);
		
		gameLauncher1 = new GameLauncher(me, launchGame1Button, steamLaunchMethod1ComboBox, argumentsGame1TextField);
		gameLauncher1.setLayout(new MigLayout("", "[grow]", "[][][]"));
		
		gameLauncher1.add(launchGame1Button, "cell 0 0,growx");
		gameLauncher1.add(steamLaunchMethod1ComboBox, "cell 0 1,growx");
		gameLauncher1.add(argumentsGame1TextField, "cell 0 2,growx");
		
		gameLaunchersPane.add(gameLauncher1);
		
		// launchGame2Button
		launchGame2Button = new LaunchButton(me, "", LaunchType.rolled, GameChoice.Two, null);
		steamLaunchMethod2ComboBox = new SteamLaunchMethodComboBox(me, launchGame2Button, SteamLaunchMethodComboBox.Type.GameLauncher);
		
		argumentsGame2TextField = new TranslatableGameArgumentsTextField(me, launchGame2Button);
		argumentsGame2TextField.setColumns(10);
		
		gameLauncher2 = new GameLauncher(me, launchGame2Button, steamLaunchMethod2ComboBox, argumentsGame2TextField);
		gameLauncher2.setLayout(new MigLayout("", "[grow]", "[][][]"));
		
		gameLauncher2.add(launchGame2Button, "cell 0 0,growx");
		gameLauncher2.add(steamLaunchMethod2ComboBox, "cell 0 1,growx");
		gameLauncher2.add(argumentsGame2TextField, "cell 0 2,growx");
		
		gameLaunchersPane.add(gameLauncher2);
		
		// launchGame3Button
		launchGame3Button = new LaunchButton(me, "", LaunchType.rolled, GameChoice.Three, null);
		steamLaunchMethod3ComboBox = new SteamLaunchMethodComboBox(me, launchGame3Button, SteamLaunchMethodComboBox.Type.GameLauncher);
		
		argumentsGame3TextField = new TranslatableGameArgumentsTextField(me, launchGame3Button);
		argumentsGame3TextField.setColumns(10);
		
		gameLauncher3 = new GameLauncher(me, launchGame3Button, steamLaunchMethod3ComboBox, argumentsGame3TextField);
		gameLauncher3.setLayout(new MigLayout("", "[grow]", "[][][]"));
		
		gameLauncher3.add(launchGame3Button, "cell 0 0,growx");
		gameLauncher3.add(steamLaunchMethod3ComboBox, "cell 0 1,growx");
		gameLauncher3.add(argumentsGame3TextField, "cell 0 2,growx");
		
		gameLaunchersPane.add(gameLauncher3);
		
		// refreshGamesListButton
		refreshGamesListButton = new CommandButton(me, refreshGamesListAction);
		gamesLibrarianControls.add(refreshGamesListButton, "cell 0 2 2 1,alignx left");
		
		// console
		JScrollPane consoleScrollPane = new JScrollPane();
		gamesLibrarianControls.add(consoleScrollPane, "cell 2 2 1 7,grow");
		
		consoleTextPane = new JTextPane();
		consoleTextPane.setEditable(false);
		consoleTextPane.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		consoleScrollPane.setViewportView(consoleTextPane);
		
		// scrollLockButton
		scrollLockButton = new CommandButton(me, scrollLockAction);
		gamesLibrarianControls.add(scrollLockButton, "cell 0 3 2 1,alignx left");
		
		// debugButton
		debugButton = new CommandButton(me, debugAction);
		gamesLibrarianControls.add(debugButton, "cell 0 4 2 1,alignx left");
		
		// clearConsoleButton
		clearConsoleButton = new CommandButton(me, clearConsoleAction);
		gamesLibrarianControls.add(clearConsoleButton, "cell 0 5 2 1,alignx left");
		
		// loadParametersButton
		loadParametersButton = new CommandButton(me, loadParametersAction);
		gamesLibrarianControls.add(loadParametersButton, "cell 0 6 2 1,alignx left");
		
		// saveParametersButton
		saveParametersButton = new CommandButton(me, saveParametersAction);
		gamesLibrarianControls.add(saveParametersButton, "cell 0 7 2 1,alignx left");
		
		// viewParametersButton
		viewParametersButton = new CommandButton(me, viewParametersAction);
		gamesLibrarianControls.add(viewParametersButton, "cell 0 8 2 1,alignx left,aligny top");
		
	}
	
	/**
	 * Create the Library Sub Tab
	 */
	private void createLibraryTab() {
		
		libraryPaneCardLayout = new CardLayout(0, 0);
		
		// Main pane
		libraryPane = new JPanel();
		libraryPane.setName("libraryTab");
		libraryPane.setLayout(libraryPaneCardLayout);
		
		libraryMainPane.addTab(getTabTitle(BundleManager.getUITexts(me, "libraryMainTabTitle")), GamesLibrary.libraryMenuIcon, libraryPane, null);
		
		// libraryPane
		buttonsLibraryPane = new JPanel();
		
		WrapLayout libraryPaneWrapLayout = new WrapLayout();
		libraryPaneWrapLayout.setAlignment(FlowLayout.LEFT);
		
		buttonsLibraryPane.setLayout(libraryPaneWrapLayout);
		
		libraryScrollPane = new JScrollPane();
		libraryScrollPane.add(buttonsLibraryPane);
		libraryScrollPane.setViewportView(buttonsLibraryPane);
		
		gamesLibraryButtonGroup = new ButtonGroup();
		List<LaunchButton> launchButtons = new Vector<LaunchButton>();
		
		launchButton1 = new LaunchButton(me, "", LaunchType.library, null, null);
		launchButton1.setIcon(GamesLibrary.gameImageUnavailableIcon);
		
		buttonsLibraryPane.add(launchButton1);
		gamesLibraryButtonGroup.add(launchButton1);
		launchButtons.add(launchButton1);
		
		launchButton2 = new LaunchButton(me, "", LaunchType.library, null, null);
		launchButton2.setIcon(GamesLibrary.gameImageUnavailableIcon);
		
		buttonsLibraryPane.add(launchButton2);
		gamesLibraryButtonGroup.add(launchButton2);
		launchButtons.add(launchButton2);
		
		launchButton3 = new LaunchButton(me, "", LaunchType.library, null, null);
		launchButton3.setIcon(GamesLibrary.gameImageUnavailableIcon);
		
		buttonsLibraryPane.add(launchButton3);
		gamesLibraryButtonGroup.add(launchButton3);
		launchButtons.add(launchButton3);
		
		launchButton4 = new LaunchButton(me, "", LaunchType.library, null, null);
		launchButton4.setIcon(GamesLibrary.gameImageUnavailableIcon);
		
		buttonsLibraryPane.add(launchButton4);
		gamesLibraryButtonGroup.add(launchButton4);
		launchButtons.add(launchButton4);
		
		launchButton5 = new LaunchButton(me, "", LaunchType.library, null, null);
		launchButton5.setIcon(GamesLibrary.gameImageUnavailableIcon);
		
		buttonsLibraryPane.add(launchButton5);
		gamesLibraryButtonGroup.add(launchButton5);
		launchButtons.add(launchButton5);
		
		launchButton6 = new LaunchButton(me, "", LaunchType.library, null, null);
		launchButton6.setIcon(GamesLibrary.gameImageUnavailableIcon);
		
		buttonsLibraryPane.add(launchButton6);
		gamesLibraryButtonGroup.add(launchButton6);
		launchButtons.add(launchButton6);
		
		libraryPane.add(libraryScrollPane, SteamGamesDisplayMode.LaunchPane.name());
		
		gamesLibraryListScrollPane = new JScrollPane();
		libraryPane.add(gamesLibraryListScrollPane, SteamGamesDisplayMode.ConfigurationPane.name());
		
		if (librarian != null)
			librarian.updateSteamGamesTable(launchButtons);
	}
	
	/**
	 * Create the Library Statistics Main Tab
	 */
	private void createLibraryStatisticsMainTab() {
		
		// Main pane
		libraryStatisticsMainPane = new JPanel();
		libraryStatisticsMainPane.setName("libraryStatisticsTab");
		libraryStatisticsMainPane.setLayout(new MigLayout("", "[][][]", "[][][][][][][][][][][]"));
		
		libraryMainPane.addTab(getTabTitle(BundleManager.getUITexts(me, "libraryStatisticsTabTitle")), GamesLibrary.libraryStatisticsMenuIcon, libraryStatisticsMainPane, null);
		
		// libraryTotalGames
		libraryTotalGamesLabel = new TranslatableLabel(me, "libraryTotalGamesLabel");
		libraryStatisticsMainPane.add(libraryTotalGamesLabel, "cell 0 0,alignx trailing");
		
		libraryTotalGamesTextField = new JTextField();
		libraryTotalGamesTextField.setEditable(false);
		libraryTotalGamesTextField.setColumns(10);
		
		libraryStatisticsMainPane.add(libraryTotalGamesTextField, "flowx,cell 1 0,growx");
		
		// libraryTotalGamesWithStats
		libraryTotalGamesWithStatsLabel = new TranslatableLabel(me, "libraryTotalGamesWithStatsLabel");
		libraryStatisticsMainPane.add(libraryTotalGamesWithStatsLabel, "cell 0 1,alignx trailing");
		
		libraryTotalGamesWithStatsTextField = new JTextField();
		libraryTotalGamesWithStatsTextField.setEditable(false);
		libraryTotalGamesWithStatsTextField.setColumns(10);
		
		libraryStatisticsMainPane.add(libraryTotalGamesWithStatsTextField, "cell 1 1");
		
		// libraryTotalGamesWithGlobalStats
		libraryTotalGamesWithGlobalStatsLabel = new TranslatableLabel(me, "libraryTotalGamesWithGlobalStatsLabel");
		libraryStatisticsMainPane.add(libraryTotalGamesWithGlobalStatsLabel, "cell 0 2,alignx trailing");
		
		libraryTotalGamesWithGlobalStatsTextField = new JTextField();
		libraryTotalGamesWithGlobalStatsTextField.setEditable(false);
		libraryTotalGamesWithGlobalStatsTextField.setColumns(10);
		
		libraryStatisticsMainPane.add(libraryTotalGamesWithGlobalStatsTextField, "cell 1 2,growx");
		
		// libraryTotalGamesWithStoreLink
		libraryTotalGamesWithStoreLinkLabel = new TranslatableLabel(me, "libraryTotalGamesWithStoreLinkLabel");
		libraryStatisticsMainPane.add(libraryTotalGamesWithStoreLinkLabel, "cell 0 3,alignx trailing");
		
		libraryTotalGamesWithStoreLinkTextField = new JTextField();
		libraryTotalGamesWithStoreLinkTextField.setEditable(false);
		libraryTotalGamesWithStoreLinkTextField.setColumns(10);
		
		libraryStatisticsMainPane.add(libraryTotalGamesWithStoreLinkTextField, "cell 1 3,growx");
		
		// libraryTotalWastedHours
		libraryTotalWastedHoursLabel = new TranslatableLabel(me, "libraryTotalWastedHoursLabel");
		libraryStatisticsMainPane.add(libraryTotalWastedHoursLabel, "cell 0 4,alignx trailing");
		
		libraryTotalWastedHoursTextField = new JTextField();
		libraryTotalWastedHoursTextField.setEditable(false);
		libraryTotalWastedHoursTextField.setColumns(10);
		
		libraryStatisticsMainPane.add(libraryTotalWastedHoursTextField, "cell 1 4,growx");
		
		libraryTotalWastedHoursFormattedLabel = new JLabel("");
		libraryStatisticsMainPane.add(libraryTotalWastedHoursFormattedLabel, "cell 2 4");
		
		// libraryTotalHoursLast2Week
		libraryTotalHoursLast2WeeksLabel = new TranslatableLabel(me, "libraryTotalHoursLast2WeeksLabel");
		libraryStatisticsMainPane.add(libraryTotalHoursLast2WeeksLabel, "cell 0 5,alignx trailing");
		
		libraryTotalHoursLast2WeeksTextField = new JTextField();
		libraryTotalHoursLast2WeeksTextField.setEditable(false);
		libraryTotalHoursLast2WeeksTextField.setColumns(10);
		
		libraryStatisticsMainPane.add(libraryTotalHoursLast2WeeksTextField, "cell 1 5,growx");
		
		libraryTotalHoursLast2WeeksFormattedLabel = new JLabel("");
		libraryStatisticsMainPane.add(libraryTotalHoursLast2WeeksFormattedLabel, "cell 2 5");
		
		// libraryTotalFinishedGames
		libraryTotalFinishedGamesLabel = new TranslatableLabel(me, "libraryTotalFinishedGamesLabel");
		libraryStatisticsMainPane.add(libraryTotalFinishedGamesLabel, "cell 0 6,alignx trailing");
		
		libraryTotalFinishedGamesTextField = new JTextField();
		libraryTotalFinishedGamesTextField.setEditable(false);
		libraryTotalFinishedGamesTextField.setColumns(10);
		libraryStatisticsMainPane.add(libraryTotalFinishedGamesTextField, "cell 1 6,growx");
		
		// libraryTotalGamesWithInvalidStats
		libraryTotalGamesWithInvalidStatsLabel = new TranslatableLabel(me, "libraryTotalGamesWithInvalidStatsLabel");
		libraryStatisticsMainPane.add(libraryTotalGamesWithInvalidStatsLabel, "cell 0 7,alignx trailing");
		
		libraryTotalGamesWithInvalidStatsTextField = new JTextField();
		libraryTotalGamesWithInvalidStatsTextField.setEditable(false);
		libraryTotalGamesWithInvalidStatsTextField.setColumns(10);
		libraryStatisticsMainPane.add(libraryTotalGamesWithInvalidStatsTextField, "cell 1 7,growx");
		
		// libraryTotalAchievements
		libraryTotalAchievementsLabel = new TranslatableLabel(me, "libraryTotalAchievementsLabel");
		libraryStatisticsMainPane.add(libraryTotalAchievementsLabel, "cell 0 8,alignx trailing");
		
		libraryTotalAchievementsTextField = new JTextField();
		libraryTotalAchievementsTextField.setEditable(false);
		libraryTotalAchievementsTextField.setColumns(10);
		libraryStatisticsMainPane.add(libraryTotalAchievementsTextField, "cell 1 8,growx");
		
		// libraryTotalUnlockedAchievements
		libraryTotalUnlockedAchievementsLabel = new TranslatableLabel(me, "libraryTotalUnlockedAchievementsLabel");
		libraryStatisticsMainPane.add(libraryTotalUnlockedAchievementsLabel, "cell 0 9,alignx trailing");
		
		libraryTotalUnlockedAchievementsTextField = new JTextField();
		libraryTotalUnlockedAchievementsTextField.setEditable(false);
		libraryTotalUnlockedAchievementsTextField.setColumns(10);
		libraryStatisticsMainPane.add(libraryTotalUnlockedAchievementsTextField, "cell 1 9,growx");
		
		// libraryPercentageAchieved
		libraryPercentageAchievedLabel = new TranslatableLabel(me, "libraryPercentageAchievedLabel");
		libraryStatisticsMainPane.add(libraryPercentageAchievedLabel, "cell 0 10,alignx trailing");
		
		libraryPercentageAchievedTextField = new JTextField();
		libraryPercentageAchievedTextField.setEditable(false);
		libraryPercentageAchievedTextField.setColumns(10);
		libraryStatisticsMainPane.add(libraryPercentageAchievedTextField, "cell 1 10,growx");
	}
	
	/**
	 * Create the Library Main Tab
	 */
	private void createLibraryMainTab() {
		
		// Main pane
		gamesLibrarianLibrary = new JPanel();
		gamesLibrarianLibrary.setName("libraryMainTab");
		gamesLibrarianLibrary.setLayout(new MigLayout("", "[grow]", "[][][][grow]"));
		
		mainPane.addTab(getTabTitle(BundleManager.getUITexts(me, "libraryMainTabTitle")), null, gamesLibrarianLibrary, null);
		
		// libraryTitle
		libraryMainTitleLabel = new LibraryMainTitleLabel(me, "libraryTitleLabelEmpty");
		gamesLibrarianLibrary.add(libraryMainTitleLabel, "cell 0 0");
		
		// Commands pane
		libraryCommandsPane = new JPanel();
		libraryCommandsPane.setBorder(new LineBorder(Color.GRAY));
		
		WrapLayout libraryCommandsPaneWrapLayout = new WrapLayout();
		libraryCommandsPaneWrapLayout.setAlignment(FlowLayout.LEFT);
		
		libraryCommandsPane.setLayout(libraryCommandsPaneWrapLayout);
		
		gamesLibrarianLibrary.add(libraryCommandsPane, "cell 0 1,grow");
		
		// loadLibraryButton
		loadLibraryButton = new CommandButton(me, loadLibraryAction);
		libraryCommandsPane.add(loadLibraryButton);
		
		// loadAllGameStatsButton
		loadAllGamesStatsButton = new CommandButton(me, loadAllGamesStatsAction);
		libraryCommandsPane.add(loadAllGamesStatsButton);
		
		// librarySortMethod
		librarySortMethodLabel = new TranslatableLabel(me, "librarySortMethodLabel");
		librarySortMethodComboBox = new SteamGamesSortMethodComboBox(me, librarySortMethodLabel);
		
		libraryCommandsPane.add(new ArrowedComboBox(librarySortMethodLabel, librarySortMethodComboBox));
		
		new EnumSelectionStateAdapter<SteamGamesSortMethod>(librarySortMethodActionGroup, librarySortMethodComboBox).configure();
		
		// libraryDisplayMode
		libraryDisplayModeLabel = new TranslatableLabel(me, "libraryDisplayModeLabel");
		libraryDisplayModeComboBox = new SteamGamesDisplayModeComboBox(me, libraryDisplayModeLabel);
		
		libraryCommandsPane.add(new ArrowedComboBox(libraryDisplayModeLabel, libraryDisplayModeComboBox));
		
		new EnumSelectionStateAdapter<SteamGamesDisplayMode>(libraryDisplayModeActionGroup, libraryDisplayModeComboBox).configure();
		
		// libraryOptionsPane
		libraryOptionsPane = new JPanel();
		libraryOptionsPane.setBorder(new LineBorder(Color.GRAY));
		
		WrapLayout libraryOptionsPaneWrapLayout = new WrapLayout();
		libraryOptionsPaneWrapLayout.setAlignment(FlowLayout.LEFT);
		
		libraryOptionsPane.setLayout(libraryOptionsPaneWrapLayout);
		
		// libraryLeftClickAction
		libraryLeftClickActionPane = new JPanel();
		libraryOptionsPane.add(libraryLeftClickActionPane);
		
		libraryLeftClickActionLabel = new TranslatableLabel(me, "leftClickActionLabel");
		libraryLeftClickActionPane.add(libraryLeftClickActionLabel);
		
		ButtonGroup libraryLeftClickActionButtonGroup = new ButtonGroup();
		
		libraryLeftClickSelectButton = new GameLeftClickActionButton(me, GameLeftClickAction.Select, "leftClickActionSelect", libraryLeftClickActionLabel);
		libraryLeftClickSelectButton.addItemListener(new GameLeftClickActionButtonListener(me));
		
		libraryLeftClickActionPane.add(libraryLeftClickSelectButton);
		libraryLeftClickActionButtonGroup.add(libraryLeftClickSelectButton);
		
		libraryLeftClickLaunchButton = new GameLeftClickActionButton(me, GameLeftClickAction.Launch, "leftClickActionLaunch", libraryLeftClickActionLabel);
		libraryLeftClickLaunchButton.addItemListener(new GameLeftClickActionButtonListener(me));
		
		libraryLeftClickActionPane.add(libraryLeftClickLaunchButton);
		libraryLeftClickActionButtonGroup.add(libraryLeftClickLaunchButton);
		
		gamesLibrarianLibrary.add(libraryOptionsPane, "cell 0 2,grow");
		
		// libraryMainPane
		libraryMainPane = new JTabbedPane(JTabbedPane.TOP);
		gamesLibrarianLibrary.add(libraryMainPane, "cell 0 3,grow");
		
		// Create the Library Sub Tab
		createLibraryTab();
		
		// Create the Library Statistics Sub Tab
		createLibraryStatisticsMainTab();
		
		// First visible card panel
		libraryPaneCardLayout.show(libraryPane, SteamGamesDisplayMode.LaunchPane.name());
	}
	
	/**
	 * Create the Game Tab
	 */
	private void createGameTab() {
		
		// Main pane
		gamesLibrarianGame = new JPanel();
		gamesLibrarianGame.setName("gameTab");
		gamesLibrarianGame.setLayout(new MigLayout("", "[grow]", "[][][][][grow]"));
		
		mainPane.addTab(getTabTitle(BundleManager.getUITexts(me, "gameTabTitle")), null, gamesLibrarianGame, null);
		
		// currentGameTitle
		currentGameTitleLabel = new CurrentGameTitleLabel(me, "currentGameTitleLabelEmpty");
		gamesLibrarianGame.add(currentGameTitleLabel, "cell 0 0");

		// Commands pane
		gameCommandsPane = new JPanel();
		gameCommandsPane.setBorder(new LineBorder(Color.GRAY));
		
		WrapLayout gameCommandsPaneWrapLayout = new WrapLayout();
		gameCommandsPaneWrapLayout.setAlignment(FlowLayout.LEFT);
		
		gameCommandsPane.setLayout(gameCommandsPaneWrapLayout);
		
		gamesLibrarianGame.add(gameCommandsPane, "cell 0 1,grow");
		
		// loadGameStatsButton
		loadGameStatsButton = new CommandButton(me, loadGameStatsAction);
		gameCommandsPane.add(loadGameStatsButton);
		
		// loadAllAchievementsButton
		loadAllAchievementsButton = new CommandButton(me, loadAllAchievementsAction);
		gameCommandsPane.add(loadAllAchievementsButton);
		
		// steamAchievementsSortMethod
		steamAchievementsSortMethodLabel = new TranslatableLabel(me, "achievementsSortMethodLabel");
		steamAchievementsSortMethodComboBox = new SteamAchievementsSortMethodComboBox(me, steamAchievementsSortMethodLabel);
		
		gameCommandsPane.add(new ArrowedComboBox(steamAchievementsSortMethodLabel, steamAchievementsSortMethodComboBox));
		
		new EnumSelectionStateAdapter<SteamAchievementsSortMethod>(steamAchievementsSortMethodActionGroup, steamAchievementsSortMethodComboBox).configure();
		
		// steamAchievementsColumnsSortMethod
		steamAchievementsColumnsSortMethodLabel = new TranslatableLabel(me, "steamAchievementsListsSortMethodLabel");
		steamAchievementsListsSortMethodComboBox = new SteamAchievementsListsSortMethodComboBox(me, steamAchievementsColumnsSortMethodLabel);
		
		gameCommandsPane.add(new ArrowedComboBox(steamAchievementsColumnsSortMethodLabel, steamAchievementsListsSortMethodComboBox));

		new EnumSelectionStateAdapter<SteamAchievementsListsSortMethod>(steamAchievementsListsSortMethodActionGroup, steamAchievementsListsSortMethodComboBox).configure();
		
		// gameOptionsPane
		gameOptionsPane = new JPanel();
		gameOptionsPane.setBorder(new LineBorder(Color.GRAY));
		
		WrapLayout gameOptionsPaneWrapLayout = new WrapLayout();
		gameOptionsPaneWrapLayout.setAlignment(FlowLayout.LEFT);
		
		gameOptionsPane.setLayout(gameOptionsPaneWrapLayout);
		
		gamesLibrarianGame.add(gameOptionsPane, "cell 0 2,grow");
		
		// gameLeftClickAction
		gameLeftClickActionPane = new JPanel();
		gameOptionsPane.add(gameLeftClickActionPane);
		
		gameLeftClickActionLabel = new TranslatableLabel(me, "leftClickActionLabel");
		gameLeftClickActionPane.add(gameLeftClickActionLabel);
		
		ButtonGroup gameLeftClickActionButtonGroup = new ButtonGroup();
		
		gameLeftClickSelectButton = new GameLeftClickActionButton(me, GameLeftClickAction.Select, "leftClickActionSelect", gameLeftClickActionLabel);
		gameLeftClickSelectButton.addItemListener(new GameLeftClickActionButtonListener(me));
		
		gameLeftClickActionPane.add(gameLeftClickSelectButton);
		gameLeftClickActionButtonGroup.add(gameLeftClickSelectButton);
		
		gameLeftClickLaunchButton = new GameLeftClickActionButton(me, GameLeftClickAction.Launch, "leftClickActionLaunch", gameLeftClickActionLabel);
		gameLeftClickLaunchButton.addItemListener(new GameLeftClickActionButtonListener(me));
		
		gameLeftClickActionPane.add(gameLeftClickLaunchButton);
		gameLeftClickActionButtonGroup.add(gameLeftClickLaunchButton);
		
		// gamePane
		gamePane = new JPanel();
		gamePane.setLayout(new MigLayout("", "[][][grow]", "[][][][]"));
		gamePane.setBorder(new LineBorder(Color.GRAY));
		gamesLibrarianGame.add(gamePane, "cell 0 3,grow");
		
		// currentGameLauncher
		currentLaunchButton = new LaunchButton(me, "", LaunchType.current, null, null);
		currentSteamLaunchMethodComboBox = new SteamLaunchMethodComboBox(me, currentLaunchButton, SteamLaunchMethodComboBox.Type.GameLauncher);
		
		currentGameArgumentsTextField = new TranslatableGameArgumentsTextField(me, currentLaunchButton);
		currentGameArgumentsTextField.setColumns(10);
		
		currentGameLauncher = new GameLauncher(me, currentLaunchButton, currentSteamLaunchMethodComboBox, currentGameArgumentsTextField);
		gamePane.add(currentGameLauncher, "cell 0 0 1 4,alignx left,aligny top");
		currentGameLauncher.setLayout(new MigLayout("", "[]", "[][][]"));
		
		currentGameLauncher.add(currentLaunchButton, "cell 0 0,growx");
		currentGameLauncher.add(currentSteamLaunchMethodComboBox, "cell 0 1,growx");
		currentGameLauncher.add(currentGameArgumentsTextField, "cell 0 2,growx");
		
		// currentGameHoursPlayedLast2Weeks
		currentGameHoursPlayedLast2WeeksLabel = new TranslatableLabel(me, "accountHoursPlayedLastTwoWeeks");
		gamePane.add(currentGameHoursPlayedLast2WeeksLabel, "cell 1 1,alignx trailing");
		
		currentGameHoursPlayedLast2Weeks = new JTextField();
		gamePane.add(currentGameHoursPlayedLast2Weeks, "cell 2 1");
		currentGameHoursPlayedLast2Weeks.setEditable(false);
		currentGameHoursPlayedLast2Weeks.setColumns(10);
		
		// currentGameHoursPlayedTotal
		currentGameHoursPlayedTotalLabel = new TranslatableLabel(me, "mostPlayedGameHoursTotal");
		gamePane.add(currentGameHoursPlayedTotalLabel, "cell 1 2,alignx trailing");
		
		currentGameHoursPlayedTotal = new JTextField();
		gamePane.add(currentGameHoursPlayedTotal, "cell 2 2");
		currentGameHoursPlayedTotal.setEditable(false);
		currentGameHoursPlayedTotal.setColumns(10);
		
		// friendsWithSameGame
		friendsWithSameGameLabel = new TranslatableLabel(me, "friendsWithSameGameLabel");
		gamePane.add(friendsWithSameGameLabel, "cell 1 3,alignx trailing,aligny top");
		
		friendsWithSameGamePane = new JPanel();
		
		WrapLayout friendsWithSameGameLayout = new WrapLayout();
		friendsWithSameGameLayout.setAlignment(FlowLayout.LEFT);
		
		friendsWithSameGamePane.setLayout(friendsWithSameGameLayout);
		
		friendsWithSameGameScrollPane = new JScrollPane();
		gamePane.add(friendsWithSameGameScrollPane, "cell 2 3,grow");
		friendsWithSameGameScrollPane.add(friendsWithSameGamePane);
		friendsWithSameGameScrollPane.setViewportView(friendsWithSameGamePane);
		
		// steamAchievementsScrollPane
		steamAchievementsScrollPane = new JScrollPane();
		gamesLibrarianGame.add(steamAchievementsScrollPane, "cell 0 4,grow");		
	}
	
	/**
	 * Create the Profile Summary Sub Tab
	 */
	private void createProfileSummarySubTab() {
		
		// Main pane
		profileSummaryPane = new JPanel();
		profileSummaryPane.setName("profileSummaryTab");
		profileSummaryPane.setLayout(new MigLayout("", "[][][][grow]", "[][][][][][][][grow]"));
		
		profilePane.addTab(getTabTitle(BundleManager.getUITexts(me, profileSummaryPane.getName() + "Title")), null, profileSummaryPane, null);
		
		// gamerProfileIconFull
		gamerProfileIconFull = new IconPane(me);
		gamerProfileIconFull.setMinimumSize(new Dimension(Steam.avatarFullIconWidth, Steam.avatarFullIconHeight));
		gamerProfileIconFull.setPreferredSize(new Dimension(Steam.avatarFullIconWidth, Steam.avatarFullIconHeight));
		gamerProfileIconFull.setLayout(new BorderLayout(0, 0));
		
		profileSummaryPane.add(gamerProfileIconFull, "cell 0 0 1 4,alignx left,aligny top");
		
		// realName
		realNameLabel = new TranslatableLabel(me, "accountRealname");
		profileSummaryPane.add(realNameLabel, "cell 1 0,alignx trailing");
		
		gamerProfileRealName = new JTextField();
		gamerProfileRealName.setEditable(false);
		gamerProfileRealName.setColumns(10);
		
		profileSummaryPane.add(gamerProfileRealName, "cell 2 0 2 1,growx");
		
		// location
		locationLabel = new TranslatableLabel(me, "accountLocation");
		profileSummaryPane.add(locationLabel, "cell 1 1,alignx trailing");
		
		gamerProfileLocation = new JTextField();
		gamerProfileLocation.setEditable(false);
		gamerProfileLocation.setColumns(10);
		
		profileSummaryPane.add(gamerProfileLocation, "cell 2 1 2 1,growx");
		
		// customURL
		customURLLabel = new TranslatableLabel(me, "accountCustomURL");
		profileSummaryPane.add(customURLLabel, "cell 1 2,alignx trailing");
		
		gamerProfileCustomURL = new JTextField();
		gamerProfileCustomURL.setEditable(false);
		gamerProfileCustomURL.setColumns(10);

		profileSummaryPane.add(gamerProfileCustomURL, "cell 2 2 2 1,growx");
		
		// summary
		summaryLabel = new TranslatableLabel(me, "accountSummary");
		profileSummaryPane.add(summaryLabel, "cell 1 3,alignx trailing,aligny top");
		
		gamerProfileSummary = new JEditorPane();
		gamerProfileSummary.setEditable(false);
		gamerProfileSummary.setContentType("text/html");
		
		gamerProfileSummaryScrollPane = new JScrollPane();
		gamerProfileSummaryScrollPane.setViewportView(gamerProfileSummary);
		
		profileSummaryPane.add(gamerProfileSummaryScrollPane, "cell 2 3 2 1,grow");
		
		// memberSince
		memberSinceLabel = new TranslatableLabel(me, "accountMemberSince");
		profileSummaryPane.add(memberSinceLabel, "cell 0 4,alignx trailing");
		
		gamerProfileMemberSince = new JTextField();
		gamerProfileMemberSince.setEditable(false);
		gamerProfileMemberSince.setColumns(10);
		
		profileSummaryPane.add(gamerProfileMemberSince, "cell 1 4,growx");
		
		// headline
		headlineLabel = new TranslatableLabel(me, "accountHeadline");
		profileSummaryPane.add(headlineLabel, "cell 2 4");
		
		gamerProfileHeadline = new JTextPane();
		gamerProfileHeadline.setEditable(false);
		
		gamerProfileHeadlineScrollPane = new JScrollPane();
		gamerProfileHeadlineScrollPane.setViewportView(gamerProfileHeadline);
		
		profileSummaryPane.add(gamerProfileHeadlineScrollPane, "cell 3 4 1 3,grow");
		
		// steamRating
		steamRatingLabel = new TranslatableLabel(me, "accoutSteamRating");
		profileSummaryPane.add(steamRatingLabel, "cell 0 5,alignx trailing");
		
		gamerProfileSteamRating = new JTextField();
		gamerProfileSteamRating.setEditable(false);
		gamerProfileSteamRating.setColumns(10);
		
		profileSummaryPane.add(gamerProfileSteamRating, "cell 1 5,growx");
		
		// hoursPlayedLastTwoWeeks
		hoursPlayedLastTwoWeeksLabel = new TranslatableLabel(me, "accountHoursPlayedLastTwoWeeks");
		profileSummaryPane.add(hoursPlayedLastTwoWeeksLabel, "cell 0 6,alignx trailing");
		
		gamerProfileHoursPlayedLast2Weeks = new JTextField();
		gamerProfileHoursPlayedLast2Weeks.setEditable(false);
		gamerProfileHoursPlayedLast2Weeks.setColumns(10);
		
		profileSummaryPane.add(gamerProfileHoursPlayedLast2Weeks, "cell 1 6,growx");
		
		// mostPlayedGamesPane
		WrapLayout mostPlayedGamesPaneLayout = new WrapLayout();
		mostPlayedGamesPaneLayout.setVgap(1);
		mostPlayedGamesPaneLayout.setHgap(1);
		mostPlayedGamesPaneLayout.setAlignment(FlowLayout.LEFT);
		
		mostPlayedGamesPane = new JPanel();
		mostPlayedGamesPane.setLayout(mostPlayedGamesPaneLayout);
		
		mostPlayedGamesScrollPane = new JScrollPane();
		mostPlayedGamesScrollPane.setViewportView(mostPlayedGamesPane);
		
		profileSummaryPane.add(mostPlayedGamesScrollPane, "cell 0 7 4 1,grow");
		
		// Design
		mostPlayedGameLauncher1 = new MostPlayedGameLauncher(me);
		mostPlayedGamesPane.add(mostPlayedGameLauncher1);
		
		mostPlayedGameLauncher2 = new MostPlayedGameLauncher(me);
		mostPlayedGamesPane.add(mostPlayedGameLauncher2);
		
		mostPlayedGameLauncher3 = new MostPlayedGameLauncher(me);
		mostPlayedGamesPane.add(mostPlayedGameLauncher3);
	}
	
	/**
	 * Create the Profile Status Sub Tab
	 */
	private void createProfileStatusSubTab() {
		
		// Main pane
		profileStatusPane = new JPanel();
		profileStatusPane.setName("profileStatusTab");
		profileStatusPane.setLayout(new MigLayout("", "[][grow][][grow]", "[][][][][]"));
		
		profilePane.addTab(getTabTitle(BundleManager.getUITexts(me, profileStatusPane.getName() + "Title")), null, profileStatusPane, null);
		
		// gamerProfileAccountGamerSteamId64
		gamerProfileAccountGamerSteamId64Label = new TranslatableLabel(me, "accountSteamID64");
		profileStatusPane.add(gamerProfileAccountGamerSteamId64Label, "cell 0 0,alignx trailing");
		
		gamerProfileAccountGamerSteamId64 = new JTextField();
		gamerProfileAccountGamerSteamId64.setEditable(false);
		gamerProfileAccountGamerSteamId64.setColumns(10);
		
		profileStatusPane.add(gamerProfileAccountGamerSteamId64, "cell 1 0,growx");
		
		// gamerProfileAccountOnlineState
		gamerProfileAccountOnlineStateLabel = new TranslatableLabel(me, "accountOnlineState");
		profileStatusPane.add(gamerProfileAccountOnlineStateLabel, "cell 2 0,alignx trailing");
		
		gamerProfileAccountOnlineState = new JTextField();
		gamerProfileAccountOnlineState.setEditable(false);
		gamerProfileAccountOnlineState.setColumns(10);
		
		profileStatusPane.add(gamerProfileAccountOnlineState, "cell 3 0,growx");
		
		// gamerProfileAccountGamerSteamId
		gamerProfileAccountGamerSteamIdLabel = new TranslatableLabel(me, "accountGamerSteamID");
		profileStatusPane.add(gamerProfileAccountGamerSteamIdLabel, "cell 0 1,alignx trailing");
		
		gamerProfileAccountGamerSteamId = new JTextField();
		gamerProfileAccountGamerSteamId.setEditable(false);
		gamerProfileAccountGamerSteamId.setColumns(10);
		
		profileStatusPane.add(gamerProfileAccountGamerSteamId, "cell 1 1,growx");
		
		// gamerProfileAccountStateMessage
		gamerProfileAccountStateMessageLabel = new TranslatableLabel(me, "accountStateMessage");
		profileStatusPane.add(gamerProfileAccountStateMessageLabel, "flowx,cell 2 1,alignx trailing");
		
		gamerProfileAccountStateMessage = new JTextField();
		gamerProfileAccountStateMessage.setEditable(false);
		gamerProfileAccountStateMessage.setColumns(10);
		
		profileStatusPane.add(gamerProfileAccountStateMessage, "cell 3 1,growx");
		
		// gamerProfileAccountPrivacyState
		gamerProfileAccountPrivacyStateLabel = new TranslatableLabel(me, "accountPrivacystate");
		profileStatusPane.add(gamerProfileAccountPrivacyStateLabel, "cell 0 2,alignx trailing");
		
		gamerProfileAccountPrivacyState = new JTextField();
		gamerProfileAccountPrivacyState.setEditable(false);
		gamerProfileAccountPrivacyState.setColumns(10);
		
		profileStatusPane.add(gamerProfileAccountPrivacyState, "cell 1 2,growx");
		
		// gamerProfileAccountVisibilityState
		gamerProfileAccountVisibilityStateLabel = new TranslatableLabel(me, "accountVisibilityState");
		profileStatusPane.add(gamerProfileAccountVisibilityStateLabel, "cell 2 2,alignx trailing");
		
		gamerProfileAccountVisibilityState = new JTextField();
		gamerProfileAccountVisibilityState.setEditable(false);
		gamerProfileAccountVisibilityState.setColumns(10);
		
		profileStatusPane.add(gamerProfileAccountVisibilityState, "cell 3 2,growx");
		
		// gamerProfileAccountVacBanned
		gamerProfileAccountVacBannedLabel = new TranslatableLabel(me, "accountVACBanned");
		profileStatusPane.add(gamerProfileAccountVacBannedLabel, "cell 0 3,alignx trailing");
		
		gamerProfileAccountVacBanned = new JTextField();
		gamerProfileAccountVacBanned.setEditable(false);
		gamerProfileAccountVacBanned.setColumns(10);
		
		profileStatusPane.add(gamerProfileAccountVacBanned, "cell 1 3,growx");
		
		// gamerProfileAccountTradeBanState
		gamerProfileAccountTradeBanStateLabel = new TranslatableLabel(me, "accountTradeBanState");
		profileStatusPane.add(gamerProfileAccountTradeBanStateLabel, "cell 2 3,alignx trailing");
		
		gamerProfileAccountTradeBanState = new JTextField();
		gamerProfileAccountTradeBanState.setEditable(false);
		gamerProfileAccountTradeBanState.setColumns(10);
		
		profileStatusPane.add(gamerProfileAccountTradeBanState, "cell 3 3,growx");
		
		// gamerProfileAccountLimitedAccount
		gamerProfileAccountLimitedAccountLabel = new TranslatableLabel(me, "accountLimitedAccount");
		profileStatusPane.add(gamerProfileAccountLimitedAccountLabel, "cell 0 4,alignx trailing");
		
		gamerProfileAccountLimitedAccount = new JTextField();
		gamerProfileAccountLimitedAccount.setEditable(false);
		gamerProfileAccountLimitedAccount.setColumns(10);
		
		profileStatusPane.add(gamerProfileAccountLimitedAccount, "cell 1 4,growx");
	}
	
	/**
	 * Create the Profile Groups Sub Tab
	 */
	private void createProfileGroupsSubTab() {
		
		profileSteamGroupsTabCardLayout = new CardLayout(0, 0);
		
		// Main pane
		profileGroupsPane = new JPanel();
		profileGroupsPane.setName("profileGroupsTab");
		profileGroupsPane.setLayout(profileSteamGroupsTabCardLayout);
		
		profilePane.addTab(getTabTitle(BundleManager.getUITexts(me, profileGroupsPane.getName() + "Title")), null, profileGroupsPane, null);

		// steamGroupsScrollPane
		steamGroupsScrollPane = new JScrollPane();
		steamGroupsScrollPane.setName(SteamGroupsDisplayMode.Grid.name());
		
		profileGroupsPane.add(steamGroupsScrollPane, SteamGroupsDisplayMode.Grid.name());
		
		WrapLayout profileGoupsPaneWrapLayout = new WrapLayout();
		profileGoupsPaneWrapLayout.setAlignment(FlowLayout.LEFT);
		
		steamGroupsButtonsPane = new JPanel();
		steamGroupsButtonsPane.setLayout(profileGoupsPaneWrapLayout);
		
		steamGroupsButtonGroup = new ButtonGroup();
		
		steamGroupButton1 = new SteamGroupButton(me, "groupLabel");
		steamGroupButton2 = new SteamGroupButton(me, "groupLabel");
		
		steamGroupsButtonGroup.add(steamGroupButton1);
		steamGroupsButtonGroup.add(steamGroupButton2);
		
		steamGroupsButtonsPane.add(steamGroupButton1);
		steamGroupsButtonsPane.add(steamGroupButton2);
		
		steamGroupsScrollPane.add(steamGroupsButtonsPane);
		steamGroupsScrollPane.setViewportView(steamGroupsButtonsPane);
		
		// steamGroupsListScrollPane
		steamGroupsListScrollPane = new JScrollPane();
		steamGroupsListScrollPane.setName(SteamGroupsDisplayMode.List.name());
		
		profileGroupsPane.add(steamGroupsListScrollPane, SteamGroupsDisplayMode.List.name());

		// First visible Groups card pane
		profileSteamGroupsTabCardLayout.show(profileGroupsPane, SteamGroupsDisplayMode.List.name());
	}
	
	/**
	 * Create the Profile Friends Sub Tab
	 */
	private void createProfileFriendsSubTab() {
		
		profileSteamFriendsTabCardLayout = new CardLayout(0, 0);
		
		// Main pane
		profileFriendsPane = new JPanel();
		profileFriendsPane.setName("profileFriendsTab");
		profileFriendsPane.setLayout(profileSteamFriendsTabCardLayout);
		
		profilePane.addTab(getTabTitle(BundleManager.getUITexts(me, profileFriendsPane.getName() + "Title")), null, profileFriendsPane, null);

		// steamFriendsScrollPane
		steamFriendsScrollPane = new JScrollPane();
		steamFriendsScrollPane.setName(SteamFriendsDisplayMode.Grid.name());
		
		profileFriendsPane.add(steamFriendsScrollPane, SteamFriendsDisplayMode.Grid.name());
		
		WrapLayout profileFriendsPaneWrapLayout = new WrapLayout();
		profileFriendsPaneWrapLayout.setAlignment(FlowLayout.LEFT);
		
		steamFriendsButtonsPane = new JPanel();
		steamFriendsButtonsPane.setLayout(profileFriendsPaneWrapLayout);
		
		steamFriendsButtonGroup = new ButtonGroup();
		steamFriendButton1 = new SteamFriendButton(me, "friendLabel");
		steamFriendButton2 = new SteamFriendButton(me, "friendLabel");
		steamFriendButton3 = new SteamFriendButton(me, "friendLabel");
		steamFriendButton4 = new SteamFriendButton(me, "friendLabel");
		steamFriendButton5 = new SteamFriendButton(me, "friendLabel");
		
		steamFriendsButtonsPane.add(steamFriendButton1);
		steamFriendsButtonGroup.add(steamFriendButton1);
		steamFriendsButtonsPane.add(steamFriendButton2);
		steamFriendsButtonGroup.add(steamFriendButton2);
		steamFriendsButtonsPane.add(steamFriendButton3);
		steamFriendsButtonGroup.add(steamFriendButton3);
		steamFriendsButtonsPane.add(steamFriendButton4);
		steamFriendsButtonGroup.add(steamFriendButton4);
		steamFriendsButtonsPane.add(steamFriendButton5);
		steamFriendsButtonGroup.add(steamFriendButton5);
		
		steamFriendsScrollPane.add(steamFriendsButtonsPane);
		steamFriendsScrollPane.setViewportView(steamFriendsButtonsPane);
		
		// steamFriendsListScrollPane
		steamFriendsListScrollPane = new JScrollPane();
		steamFriendsListScrollPane.setName(SteamFriendsDisplayMode.List.name());
		
		profileFriendsPane.add(steamFriendsListScrollPane, SteamFriendsDisplayMode.List.name());
		
		// First visible Friends card pane
		profileSteamFriendsTabCardLayout.show(profileFriendsPane, SteamFriendsDisplayMode.Grid.name());
	}
	
	/**
	 * Create the Profile Tab
	 */
	private void createProfileTab() {
		
		// Main pane
		gamesLibrarianProfile = new JPanel();
		gamesLibrarianProfile.setName("profileTab");
		gamesLibrarianProfile.setLayout(new MigLayout("", "[grow]", "[][][][grow]"));
		
		mainPane.addTab(getTabTitle(BundleManager.getUITexts(me, "profileTabTitle")), null, gamesLibrarianProfile, null);
		
		// currentProfileTitle
		currentProfileTitleLabel = new CurrentProfileTitleLabel(me, "currentProfileTitleLabelEmpty");
		gamesLibrarianProfile.add(currentProfileTitleLabel, "cell 0 0");
		
		// Commands pane
		profileCommandsPane = new JPanel();
		profileCommandsPane.setBorder(new LineBorder(Color.GRAY));

		WrapLayout profileCommandsPaneWrapLayout = new WrapLayout();
		profileCommandsPaneWrapLayout.setAlignment(FlowLayout.LEFT);
		
		profileCommandsPane.setLayout(profileCommandsPaneWrapLayout);
		
		gamesLibrarianProfile.add(profileCommandsPane, "cell 0 1,grow");
		
		// loadProfileButton
		loadProfileButton = new CommandButton(me, loadProfileAction);
		profileCommandsPane.add(loadProfileButton);
		
		// addProfileButton
		addProfileButton = new CommandButton(me, addProfileAction);
		profileCommandsPane.add(addProfileButton);
		
		// knownProfilesComboBox
		knownProfilesComboBox = new KnownProfilesComboBox(me);
		
		profileCommandsPane.add(new ArrowedComboBox(null, knownProfilesComboBox));
		
		// steamGroupsSortMethodComboBox
		steamGroupsSortMethodLabel = new TranslatableLabel(me, "steamGroupsSortMethodLabel");
		steamGroupsSortMethodComboBox = new SteamGroupsSortMethodComboBox(me, steamGroupsSortMethodLabel);
		
		profileCommandsPane.add(new ArrowedComboBox(steamGroupsSortMethodLabel, steamGroupsSortMethodComboBox));

		new EnumSelectionStateAdapter<SteamGroupsSortMethod>(steamGroupsSortMethodActionGroup, steamGroupsSortMethodComboBox).configure();

		// steamFriendsSortMethodComboBox
		steamFriendsSortMethodLabel = new TranslatableLabel(me, "steamFriendsSortMethodLabel");
		steamFriendsSortMethodComboBox = new SteamFriendsSortMethodComboBox(me, steamFriendsSortMethodLabel);
		
		profileCommandsPane.add(new ArrowedComboBox(steamFriendsSortMethodLabel, steamFriendsSortMethodComboBox));

		new EnumSelectionStateAdapter<SteamFriendsSortMethod>(steamFriendsSortMethodActionGroup, steamFriendsSortMethodComboBox).configure();
		
		// profileOptionsPane
		profileOptionsPane = new JPanel();
		profileOptionsPane.setBorder(new LineBorder(Color.GRAY));
		
		WrapLayout profileOptionsPaneWrapLayout = new WrapLayout();
		profileOptionsPaneWrapLayout.setAlignment(FlowLayout.LEFT);
		
		profileOptionsPane.setLayout(profileOptionsPaneWrapLayout);
		
		gamesLibrarianProfile.add(profileOptionsPane, "cell 0 2,grow");
		
		// profileLeftClickAction
		profileLeftClickActionPane = new JPanel();
		profileOptionsPane.add(profileLeftClickActionPane);
		
		profileLeftClickActionLabel = new TranslatableLabel(me, "leftClickActionLabel");
		profileLeftClickActionPane.add(profileLeftClickActionLabel);
		
		ButtonGroup profileLeftClickActionButtonGroup = new ButtonGroup();
		
		profileLeftClickSelectButton = new GameLeftClickActionButton(me, GameLeftClickAction.Select, "leftClickActionSelect", profileLeftClickActionLabel);
		profileLeftClickSelectButton.addItemListener(new GameLeftClickActionButtonListener(me));
		
		profileLeftClickActionPane.add(profileLeftClickSelectButton);
		profileLeftClickActionButtonGroup.add(profileLeftClickSelectButton);
		
		profileLeftClickLaunchButton = new GameLeftClickActionButton(me, GameLeftClickAction.Launch, "leftClickActionLaunch", profileLeftClickActionLabel);
		profileLeftClickLaunchButton.addItemListener(new GameLeftClickActionButtonListener(me));
		
		profileLeftClickActionPane.add(profileLeftClickLaunchButton);
		profileLeftClickActionButtonGroup.add(profileLeftClickLaunchButton);
		
		//steamGroupsDisplayMode
		steamGroupsDisplayModePane = new JPanel();
		profileOptionsPane.add(steamGroupsDisplayModePane);
		
		steamGroupsDisplayModeLabel = new TranslatableLabel(me, "steamGroupsDisplayModeLabel");
		steamGroupsDisplayModePane.add(steamGroupsDisplayModeLabel);
		
		ButtonGroup steamGroupsDisplayModeButtonGroup = new ButtonGroup();
		
		steamGroupsDisplayModeList = (JToggleButton) ActionGroupFactory.getToggleButton(steamGroupsDisplayModeListAction);
		
		steamGroupsDisplayModePane.add(steamGroupsDisplayModeList);
		steamGroupsDisplayModeButtonGroup.add(steamGroupsDisplayModeList);
		
		steamGroupsDisplayModeGrid = (JToggleButton) ActionGroupFactory.getToggleButton(steamGroupsDisplayModeGridAction);
		
		steamGroupsDisplayModePane.add(steamGroupsDisplayModeGrid);
		steamGroupsDisplayModeButtonGroup.add(steamGroupsDisplayModeGrid);
		
		// steamFriendsDisplayMode
		steamFriendsDisplayModePane = new JPanel();
		profileOptionsPane.add(steamFriendsDisplayModePane);
		
		steamFriendsDisplayModeLabel = new TranslatableLabel(me, "steamFriendsDisplayModeLabel");
		steamFriendsDisplayModePane.add(steamFriendsDisplayModeLabel);
		
		ButtonGroup steamFriendsDisplayModeButtonGroup = new ButtonGroup();
		
		steamFriendsDisplayModeList = (JToggleButton) ActionGroupFactory.getToggleButton(steamFriendsDisplayModeListAction);
		
		steamFriendsDisplayModePane.add(steamFriendsDisplayModeList);
		steamFriendsDisplayModeButtonGroup.add(steamFriendsDisplayModeList);
		
		steamFriendsDisplayModeGrid = (JToggleButton) ActionGroupFactory.getToggleButton(steamFriendsDisplayModeGridAction);
		
		steamFriendsDisplayModePane.add(steamFriendsDisplayModeGrid);
		steamFriendsDisplayModeButtonGroup.add(steamFriendsDisplayModeGrid);
		
		profilePane = new JTabbedPane(JTabbedPane.TOP);
		gamesLibrarianProfile.add(profilePane, "cell 0 3,grow");
		
		// Create the Profile Summary Sub Tab
		createProfileSummarySubTab();
		
		// Create the Profile Status Sub Tab
		createProfileStatusSubTab();

		// Create the Profile Groups Sub Tab
		createProfileGroupsSubTab();
		
		// Create the Profile Friends Sub Tab
		createProfileFriendsSubTab();
	}
	
	/**
	 * Create the Options Tab
	 */
	private void createOptionsTab() {
		
		// Main pane
		gamesLibrarianOptions = new JPanel();
		gamesLibrarianOptions.setName("optionsTab");
		gamesLibrarianOptions.setLayout(new MigLayout("", "[][][][grow]", "[][][][][][][][][][]"));
		
		mainPane.addTab(getTabTitle(BundleManager.getUITexts(me, "optionsTabTitle")), null, gamesLibrarianOptions, null);

		// windowsDistribution
		windowsDistributionLabel = new TranslatableLabel(me, "windowsDistributionLabel");
		gamesLibrarianOptions.add(windowsDistributionLabel, "cell 0 0,alignx trailing");
		
		windowsDistributionTextField = new TranslatableTextField(me, "windowsDistributionTextFieldToolTip");
		windowsDistributionTextField.setEditable(false);
		windowsDistributionTextField.setColumns(10);
		windowsDistributionLabel.setLabelFor(windowsDistributionTextField);
		
		gamesLibrarianOptions.add(windowsDistributionTextField, "cell 3 0,growx");
		
		// resetOptions
		resetOptionsButton = new CommandButton(me, resetOptionsAction);
		gamesLibrarianOptions.add(resetOptionsButton, "cell 1 0 2 1");
		
		// steamExecutable
		steamExecutableLabel = new TranslatableLabel(me, "steamExecutableOptionLabel");
		gamesLibrarianOptions.add(steamExecutableLabel, "cell 0 1,alignx trailing");
		
		steamExecutableButton = new CommandButton(me, steamExecutableAction);
		gamesLibrarianOptions.add(steamExecutableButton, "cell 1 1 2 1");
		
		steamExecutableTextField = new TranslatableTextField(me, "steamExecutableTextFieldToolTip");
		steamExecutableTextField.setEditable(false);
		steamExecutableTextField.setColumns(10);
		steamExecutableLabel.setLabelFor(steamExecutableTextField);
		
		gamesLibrarianOptions.add(steamExecutableTextField, "cell 3 1,growx");
		
		// gamerSteamId
		gamerSteamIdLabel = new TranslatableLabel(me, "gamerSteamIdLabel");
		gamesLibrarianOptions.add(gamerSteamIdLabel, "cell 0 2,alignx trailing");
		
		gamerSteamIdTextField = new TranslatableTextField(me, "gamerSteamIdTextFieldToolTip");
		gamerSteamIdTextField.setColumns(10);
		gamerSteamIdLabel.setLabelFor(gamerSteamIdTextField);
		
		gamesLibrarianOptions.add(gamerSteamIdTextField, "cell 1 2 3 1,growx");
		
		// defaultSteamLaunchMethod
		defaultSteamLaunchMethodLabel = new TranslatableLabel(me, "defaultSteamLaunchMethodLabel");
		gamesLibrarianOptions.add(defaultSteamLaunchMethodLabel, "cell 0 3,alignx trailing");
		
		defaultSteamLaunchMethodComboBox = new SteamLaunchMethodComboBox(me, null, SteamLaunchMethodComboBox.Type.DefaultMethod);
		
		gamesLibrarianOptions.add(defaultSteamLaunchMethodComboBox, "cell 1 3 3 1");
		
		new EnumSelectionStateAdapter<SteamLaunchMethod>(defaultSteamLaunchMethodActionGroup, defaultSteamLaunchMethodComboBox).configure();
		
		// gameLeftClickAction
		gameLeftClickActionOptionLabel = new TranslatableLabel(me, "gameLeftClickActionLabel");
		gamesLibrarianOptions.add(gameLeftClickActionOptionLabel, "cell 0 4,alignx trailing");
		
		gameLeftClickActionComboBox = new GameLeftClickActionComboBox(me);
		
		gamesLibrarianOptions.add(gameLeftClickActionComboBox, "cell 1 4 3 1");
		
		// dumpMode
		dumpModeLabel = new TranslatableLabel(me, "dumpModeLabel");
		gamesLibrarianOptions.add(dumpModeLabel, "cell 0 5,alignx trailing");
		
		dumpModeComboBox = new DumpModeComboBox(me);
		
		gamesLibrarianOptions.add(dumpModeComboBox, "cell 1 5 3 1");
		
		new EnumSelectionStateAdapter<DumpMode>(dumpModeActionGroup, dumpModeComboBox).configure();
		
		// displayTooltips
		displayToolTipsLabel = new TranslatableLabel(me, "displayToolTipsLabel");
		gamesLibrarianOptions.add(displayToolTipsLabel, "cell 0 6,alignx trailing");

		ButtonGroup displayTooltipsButtonGroup = new ButtonGroup();
		
		displayTooltipsYesButton = (JToggleButton) ActionGroupFactory.getToggleButton(displayToolTipsYesAction);
		
		gamesLibrarianOptions.add(displayTooltipsYesButton, "cell 1 6");
		displayTooltipsButtonGroup.add(displayTooltipsYesButton);
		
		displayTooltipsNoButton = (JToggleButton) ActionGroupFactory.getToggleButton(displayToolTipsNoAction);
		
		gamesLibrarianOptions.add(displayTooltipsNoButton, "cell 2 6");
		displayTooltipsButtonGroup.add(displayTooltipsNoButton);
		
		// buttonsDisplayMode
		buttonsDisplayModeLabel = new TranslatableLabel(me, "buttonsDisplayModeLabel");
		gamesLibrarianOptions.add(buttonsDisplayModeLabel, "cell 0 7,alignx trailing");
		
		buttonsDisplayModeComboBox = new ButtonsDisplayModeComboBox(me);
		gamesLibrarianOptions.add(buttonsDisplayModeComboBox, "cell 1 7 3 1");
		
		new EnumSelectionStateAdapter<ButtonsDisplayMode>(buttonsDisplayModeActionGroup, buttonsDisplayModeComboBox).configure();
		
		// localeChoice
		localeChoiceLabel = new TranslatableLabel(me, "localeChoiceLabel");
		gamesLibrarianOptions.add(localeChoiceLabel, "cell 0 8,alignx trailing");
		
		localeChoiceComboBox = new LocaleChoiceComboBox(me);
		
		gamesLibrarianOptions.add(localeChoiceComboBox, "cell 1 8 3 1");
		
		new EnumSelectionStateAdapter<LocaleChoice>(localeChoiceActionGroup, localeChoiceComboBox).configure();
		
		// lookAndFeel
		lookAndFeelLabel = new TranslatableLabel(me, "lookAndFeelLabel");
		gamesLibrarianOptions.add(lookAndFeelLabel, "cell 0 9,alignx trailing");
		
		lookAndFeelInfoComboBox = new LookAndFeelInfoComboBox(me);
		gamesLibrarianOptions.add(lookAndFeelInfoComboBox, "cell 1 9 3 1");
		
		new TextSelectionStateAdapter<LookAndFeelInfo>(lookAndFeelActionGroup, lookAndFeelInfoComboBox).configure();
	}
	
	/**
	 * Create the application GUI.
	 */
	private void create() {
		
		// Initialize main frame exit
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new LibrarianCloser(me));
        Runtime.getRuntime().addShutdownHook(new LibrarianCleaner(me));
        
		// Initialize main frame title/dimensions/position
		setTitle(getApplicationTitle());
		setBounds(100, 100, initialFrameWidth, initialFrameHeight);
		setInitialiseSizeAndCenter();
		
		// Pointer to localization bundles
		bundleManager = new BundleManager(me);
		
		// Create the Main Menu
		createMainMenu();
		
		// Create the Main pane
		mainPane = new JTabbedPane(JTabbedPane.TOP);
		mainPane.setBorder(new EmptyBorder(2, 1, 1, 0));
		setContentPane(mainPane);
	
		// Create the Controls Tab
		createControlsTab();
		
		// Create the Library Tab
		createLibraryMainTab();
		
		// Create the Game Tab
		createGameTab();
		
		// Create the Profile Tab
		createProfileTab();
		
		// Create the Options Tab
		createOptionsTab();
		
		// Hook Tab ChangeListener
		mainPane.addChangeListener(new GamesLibrarianTabChangeListener(me, mainPane));
	}
	
	/**
	 * Startup the application
	 */
	private boolean startup() {
		if (librarian != null) { // WindowBuilder
			librarian.setView(this);
			return librarian.startup();
		}
		return false;
	}

	/**
	 * Setup, create the application ui and startup context
	 * 
	 * @throws HeadlessException
	 */
	public GamesLibrarian() throws HeadlessException {
		super();
		setup();
		create();
		if (!startup())
			exitAction.close(this);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GamesLibrarian frame = new GamesLibrarian();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
