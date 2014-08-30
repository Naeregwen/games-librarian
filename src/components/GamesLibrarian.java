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

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
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
import commons.enums.DumpMode;
import commons.enums.GameChoice;
import commons.enums.GameLeftClickAction;
import commons.enums.LaunchType;
import commons.enums.LocaleChoice;
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
import components.actions.LoadAllAchievementsAction;
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
import components.buttons.GameLeftClickActionButton;
import components.buttons.NextSteamAchievementsColumnsSortMethodButton;
import components.buttons.NextSteamAchievementsSortMethodButton;
import components.buttons.NextSteamFriendSortMethodButton;
import components.buttons.NextSteamGameDisplayModeButton;
import components.buttons.NextSteamGameSortMethodButton;
import components.buttons.NextSteamGroupSortMethodButton;
import components.buttons.NextSteamProfileButton;
import components.buttons.PreviousSteamAchievementsColumnsSortMethodButton;
import components.buttons.PreviousSteamAchievementsSortMethodButton;
import components.buttons.PreviousSteamFriendSortMethodButton;
import components.buttons.PreviousSteamGameDisplayModeButton;
import components.buttons.PreviousSteamGameSortMethodButton;
import components.buttons.PreviousSteamGroupSortMethodButton;
import components.buttons.PreviousSteamProfileButton;
import components.buttons.listeners.GameLeftClickActionButtonListener;
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
import components.containers.GameLauncher;
import components.containers.IconPane;
import components.containers.MostPlayedGameLauncher;
import components.containers.remotes.LaunchButton;
import components.containers.remotes.SteamFriendButton;
import components.containers.remotes.SteamGroupButton;
import components.labels.Label;
import components.labels.TitleLabel;
import components.tables.listeners.GamesLibrarianTabChangeListener;
import components.textfields.GameArgumentsTextField;
import components.workers.cleaners.LibrarianCleaner;


public class GamesLibrarian extends JFrame {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -904623119948462522L;
	
	/**
	 * Main frame initial dimensions 
	 */
	private static final int initialFrameWidth = 1120;
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
	private JPanel gameLaunchersPane;

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
	
	JButton rollButton;
	JTextField gameNameTextField;
	
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
	GameArgumentsTextField argumentsGame1TextField;
	GameArgumentsTextField argumentsGame2TextField;
	GameArgumentsTextField argumentsGame3TextField;
	
	JButton refreshGamesListButton;
	JButton scrollLockButton;
	JButton debugButton;
	JButton clearConsoleButton;
	JButton loadParametersButton;
	JButton saveParametersButton;
	JButton viewParametersButton;
	
	JTextPane consoleTextPane;
	
	// Library Tab
	
	JPanel libraryCommandsPane;
	
	TitleLabel libraryTitleLabel;
	JButton loadLibraryButton;
	Label librarySortMethodLabel;
	PreviousSteamGameSortMethodButton previousSteamGameSortMethodButton;
	SteamGamesSortMethodComboBox librarySortMethodComboBox;
	NextSteamGameSortMethodButton nextSteamGameSortMethodButton;
	Label libraryDisplayModeLabel;
	PreviousSteamGameDisplayModeButton previousSteamGameDisplayModeButton;
	SteamGamesDisplayModeComboBox libraryDisplayModeComboBox;
	NextSteamGameDisplayModeButton nextSteamGameDisplayModeButton;
	
	private JPanel libraryOptionsPane;
	Label libraryLeftClickActionLabel;
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
	
	// Game Tab
	
	JPanel gameCommandsPane;
	
	TitleLabel currentGameTitleLabel;
	JButton loadGameStatsButton;
	Label steamAchievementsSortMethodLabel;
	PreviousSteamAchievementsSortMethodButton previousSteamAchievementsSortMethodButton;
	SteamAchievementsSortMethodComboBox steamAchievementsSortMethodComboBox;
	NextSteamAchievementsSortMethodButton nextSteamAchievementsSortMethodButton;
	Label steamAchievementsColumnsSortMethodLabel;
	PreviousSteamAchievementsColumnsSortMethodButton previousSteamAchievementsColumnsSortMethodButton;
	SteamAchievementsListsSortMethodComboBox steamAchievementsListsSortMethodComboBox;
	NextSteamAchievementsColumnsSortMethodButton nextSteamAchievementsColumnsSortMethodButton;

	private JPanel gameOptionsPane;
	Label gameLeftClickActionLabel;
	GameLeftClickActionButton gameLeftClickSelectButton;
	GameLeftClickActionButton gameLeftClickLaunchButton;
	
	GameLauncher currentGameLauncher;
	LaunchButton currentLaunchButton;
	SteamLaunchMethodComboBox currentSteamLaunchMethodComboBox;
	JTextField currentGameArgumentsTextField;
	
	Label currentGameHoursPlayedLast2WeeksLabel;
	JTextField currentGameHoursPlayedLast2Weeks;
	Label currentGameHoursPlayedTotalLabel;
	JTextField currentGameHoursPlayedTotal;

	JButton loadAllAchievementsButton;
	Label friendsWithSameGameLabel;
	JScrollPane friendsWithSameGameScrollPane;
	JPanel friendsWithSameGamePane;

	JScrollPane steamAchievementsScrollPane;
	
	// Profile Tab
	
	JPanel profileCommandsPane;
	
	TitleLabel currentProfileTitleLabel;
	
	JButton loadProfileButton;
	JButton addProfileButton;
	PreviousSteamProfileButton previousKnownProfileButton;
	KnownProfilesComboBox knownProfilesComboBox;
	NextSteamProfileButton nextKnownProfileButton;
	Label steamGroupsSortMethodLabel;
	PreviousSteamGroupSortMethodButton previousSteamGroupSortMethodButton;
	SteamGroupsSortMethodComboBox steamGroupsSortMethodComboBox;
	NextSteamGroupSortMethodButton nextSteamGroupSortMethodButton;
	Label steamFriendsSortMethodLabel;
	PreviousSteamFriendSortMethodButton previousSteamFriendSortMethodButton;
	SteamFriendsSortMethodComboBox steamFriendsSortMethodComboBox;
	NextSteamFriendSortMethodButton nextSteamFriendSortMethodButton;
	
	Label steamGroupsDisplayModeLabel;
	JToggleButton steamGroupsDisplayModeList;
	JToggleButton steamGroupsDisplayModeGrid;
	Label steamFriendsDisplayModeLabel;
	JToggleButton steamFriendsDisplayModeList;
	JToggleButton steamFriendsDisplayModeGrid;
	
	private JPanel profileOptionsPane;
	Label profileLeftClickActionLabel;
	GameLeftClickActionButton profileLeftClickSelectButton;
	GameLeftClickActionButton profileLeftClickLaunchButton;

	// Profile Sub Tab
	
	JTabbedPane profilePane;
	JPanel profileSummaryPane;
	JPanel profileStatusPane;
	JPanel profileGroupsPane;
	JPanel profileFriendsPane;

	// Profile Summary Sub Tab
	
	IconPane gamerProfileIconFull;
	
	Label realnameLabel;
	JTextField gamerProfileRealname;
	Label locationLabel;
	JTextField gamerProfileLocation;
	Label customURLLabel;
	JTextField gamerProfileCustomURL;
	Label summaryLabel;
	JScrollPane gamerProfileSummaryScrollPane;
	JEditorPane gamerProfileSummary;
	
	Label memberSinceLabel;
	JTextField gamerProfileMemberSince;
	Label steamRatingLabel;
	JTextField gamerProfileSteamRating;
	Label hoursPlayedLastTwoWeeksLabel;
	JTextField gamerProfileHoursPlayedLast2Weeks;
	
	Label headlineLabel;
	JTextPane gamerProfileHeadline;
	
	JScrollPane gamerProfileHeadlineScrollPane;
	JPanel mostPlayedGamesPane;

	JScrollPane mostPlayedGamesScrollPane;
	MostPlayedGameLauncher mostPlayedGameLauncher1;
	MostPlayedGameLauncher mostPlayedGameLauncher2;
	MostPlayedGameLauncher mostPlayedGameLauncher3;

	// Profile Status Sub Tab
	
	Label gamerProfileAccountGamerSteamId64Label;
	JTextField gamerProfileAccountGamerSteamId64;
	Label gamerProfileAccountOnlineStateLabel;
	JTextField gamerProfileAccountOnlineState;
	Label gamerProfileAccountGamerSteamIdLabel;
	JTextField gamerProfileAccountGamerSteamId;
	Label gamerProfileAccountStateMessageLabel;
	JTextField gamerProfileAccountStateMessage;
	Label gamerProfileAccountPrivacyStateLabel;
	JTextField gamerProfileAccountPrivacyState;
	Label gamerProfileAccountVisibilityStateLabel;
	JTextField gamerProfileAccountVisibilityState;
	Label gamerProfileAccountVacBannedLabel;
	JTextField gamerProfileAccountVacBanned;
	Label gamerProfileAccountTradeBanStateLabel;
	JTextField gamerProfileAccountTradeBanState;
	Label gamerProfileAccountLimitedAccountLabel;
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
	
	Label windowsDistributionLabel;
	JButton resetOptionsButton;
	JTextField windowsDistributionTextField;
	
	Label steamExecutableLabel;
	JButton steamExecutableButton;
	JTextField steamExecutableTextField;
	
	Label gamerSteamIdLabel;
	JTextField gamerSteamIdTextField;
	
	Label defaultSteamLaunchMethodLabel;
	SteamLaunchMethodComboBox defaultSteamLaunchMethodComboBox;
	
	Label gameLeftClickActionOptionLabel;
	GameLeftClickActionComboBox gameLeftClickActionComboBox;
	
	JLabel displayToolTipsLabel;
	JToggleButton displayTooltipsYesButton;
	JToggleButton displayTooltipsNoButton;
	
	Label dumpModeLabel;
	DumpModeComboBox dumpModeComboBox;
	
	Label localeChoiceLabel;
	LocaleChoiceComboBox localeChoiceComboBox;
	
	Label LookAndFeelLabel;
	LookAndFeelInfoComboBox lookAndFeelInfoComboBox;

	//
	// Main Menu
	//
	
	private JMenuBar mainMenuBar;
	
	// File Menu
	
	private JMenu fileMenu;

	LoadParametersAction loadParametersAction;
	private JMenuItem loadParametersMenuItem;
	
	SaveParametersAction saveParametersAction;
	private JMenuItem saveParametersMenuItem;
	
	ExitAction exitAction;
	private JMenuItem exitMenuItem;

	// Controls Menu
	
	private JMenu controlMenu;
	
	RollAction rollAction;
	private JMenuItem rollMenuItem;
	
	private JMenu gameChoiceMenu;
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
	
	private JMenu libraryMenu;
	
	LoadLibraryAction loadLibraryAction;
	private JMenuItem loadLibraryMenuItem;

	private JMenu librarySortMethodMenu;
	private ActionGroup librarySortMethodActionGroup;
	
	private JMenu libraryDisplayModeMenu;
	private ActionGroup libraryDisplayModeActionGroup;
	
	// Game Menu
	
	private JMenu gameMenu;
	
	LoadGameStatsAction loadGameStatsAction;
	private JMenuItem loadGameStatsMenuItem;
	
	LoadAllAchievementsAction loadAllAchievementsAction;
	private JMenuItem loadAllAchievementsMenuItem;
	
	private JMenu steamAchievementsSortMethodMenu;
	private ActionGroup steamAchievementsSortMethodActionGroup;
	
	private JMenu steamAchievementsListsSortMethodMenu;
	private ActionGroup steamAchievementsListsSortMethodActionGroup;
	
	// Profile Menu
	
	private JMenu profileMenu;
	
	LoadProfileAction loadProfileAction;
	private JMenuItem loadProfileMenuItem;
	
	AddProfileAction addProfileAction;
	private JMenuItem addProfileMenuItem;
	
	private JMenu steamGroupsSortMethodMenu;
	private ActionGroup steamGroupsSortMethodActionGroup;
	
	private JMenu steamFriendsSortMethodMenu;
	private ActionGroup steamFriendsSortMethodActionGroup;
	
	private JMenu steamFriendsDisplayModeMenu;
	ActionGroup steamFriendsDisplayModeActionGroup;
	SteamFriendsDisplayModeAction steamFriendsDisplayModeListAction;
	SteamFriendsDisplayModeAction steamFriendsDisplayModeGridAction;
	
	private JMenu steamGroupsDisplayModeMenu;
	ActionGroup steamGroupsDisplayModeActionGroup;
	SteamGroupsDisplayModeAction steamGroupsDisplayModeListAction;
	SteamGroupsDisplayModeAction steamGroupsDisplayModeGridAction;
	
	// Options Menu
	
	private JMenu optionsMenu;
	
	ResetOptionsAction resetOptionsAction;
	private JMenuItem resetOptionsMenuItem;
	SteamExecutableAction steamExecutableAction;
	private JMenuItem steamExecutableMenuItem;
	
	private JMenu displayToolTipsMenu;
	private ActionGroup displayToolTipsActionGroup;
	DisplayToolTipsAction displayToolTipsYesAction;
	DisplayToolTipsAction displayToolTipsNoAction;

	private JMenu defaultSteamLaunchMethodMenu;
	private ActionGroup defaultSteamLaunchMethodActionGroup;
	
	private JMenu dumpModeMenu;
	private ActionGroup dumpModeActionGroup;
	
	private JMenu localeChoiceMenu;
	private ActionGroup localeChoiceActionGroup;
	
	private JMenu lookAndFeelMenu;
	private ActionGroup lookAndFeelActionGroup;
	
	// Help Menu
	
	private JMenu helpMenu;
	
	AboutAction aboutAction;
	private JMenuItem aboutMenuItem;
	
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
		
		fileMenu = new JMenu(BundleManager.getUITexts(me, "fileMenuLabel"));
		mainMenuBar.add(fileMenu);
		
		// loadParametersMenuItem
		loadParametersAction = new LoadParametersAction(me);
		loadParametersMenuItem = fileMenu.add(loadParametersAction);
		
		// saveParametersMenuItem
		saveParametersAction = new SaveParametersAction(me);
		saveParametersMenuItem = fileMenu.add(saveParametersAction);
		
		// exitMenuItem
		exitAction = new ExitAction(me);
		exitMenuItem = fileMenu.add(exitAction);
		
		//
		// Controls Menu
		//
		
		controlMenu = new JMenu(BundleManager.getUITexts(me, "controlsTabTitle"));
		mainMenuBar.add(controlMenu);
		
		// rollMenuItem
		rollAction = new RollAction(me);
		rollMenuItem = controlMenu.add(rollAction);
		
		// gameChoiceMenu
		gameChoiceMenu = new JMenu(BundleManager.getUITexts(me, "gameChoiceMenuLabel"));
		gameChoiceMenu.setIcon(GamesLibrary.gameChoiceIcon);
		controlMenu.add(gameChoiceMenu);
		
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

		// refreshGamesListMenuItem
		refreshGamesListAction = new RefreshGamesListAction(me);
		refreshGamesListMenuItem = controlMenu.add(refreshGamesListAction);
		
		// scrollLockMenuItem
		scrollLockAction = new ScrollLockAction(me);
		scrollLockMenuItem = controlMenu.add(scrollLockAction);
				
		// debugMenuItem
		debugAction = new DebugAction(me);
		debugMenuItem = controlMenu.add(debugAction);
		
		// clearConsoleMenuItem
		clearConsoleAction = new ClearConsoleAction(me);
		clearConsoleMenuItem = controlMenu.add(clearConsoleAction);
		
		// viewParametersMenuItem
		viewParametersAction = new ViewParametersAction(me);
		viewParametersMenuItem = controlMenu.add(viewParametersAction);
		
		//
		// Library Menu
		//
		
		libraryMenu = new JMenu(BundleManager.getUITexts(me, "libraryMenuLabel"));
		mainMenuBar.add(libraryMenu);
		
		// loadLibraryMenuItem
		loadLibraryAction = new LoadLibraryAction(me);
		loadLibraryMenuItem = libraryMenu.add(loadLibraryAction);
		
		// libraryDisplayModeMenu
		libraryDisplayModeMenu = new JMenu(BundleManager.getUITexts(me, "libraryDisplayModeMenuLabel"));
		libraryDisplayModeMenu.setIcon(GamesLibrary.libraryDisplayMode);
		libraryMenu.add(libraryDisplayModeMenu);
		
		libraryDisplayModeActionGroup = new ActionGroup();
		for (SteamGamesDisplayMode steamGamesDisplayMode : SteamGamesDisplayMode.values()) {
			SteamGamesDisplayModeAction steamGamesSortMethodAction = new SteamGamesDisplayModeAction(me, steamGamesDisplayMode);
			libraryDisplayModeActionGroup.add(steamGamesSortMethodAction);
			libraryDisplayModeMenu.add(ActionGroupFactory.getRadioMenuItem(steamGamesSortMethodAction));
		}
		
		// librarySortMethodMenu
		librarySortMethodMenu = new JMenu(BundleManager.getUITexts(me, "librarySortMethodMenuLabel"));
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
		
		gameMenu = new JMenu(BundleManager.getUITexts(me, "gameMenuLabel"));
		mainMenuBar.add(gameMenu);
		
		// loadGameStatsMenuItem
		loadGameStatsAction = new LoadGameStatsAction(me);
		loadGameStatsMenuItem = gameMenu.add(loadGameStatsAction);
		
		// loadAllAchievementsMenuItem
		loadAllAchievementsAction = new LoadAllAchievementsAction(me);
		loadAllAchievementsMenuItem = gameMenu.add(loadAllAchievementsAction);
		
		// achievementsSortMethodMenu
		steamAchievementsSortMethodMenu = new JMenu(BundleManager.getUITexts(me, "steamAchievementsSortMethodMenuLabel"));
		steamAchievementsSortMethodMenu.setIcon(GamesLibrary.librarySortMethod);
		gameMenu.add(steamAchievementsSortMethodMenu);
		
		steamAchievementsSortMethodActionGroup = new ActionGroup();
		for (SteamAchievementsSortMethod steamAchievementsSortMethod : SteamAchievementsSortMethod.values()) {
			SteamAchievementsSortMethodAction steamAchievementsSortMethodAction = new SteamAchievementsSortMethodAction(me, steamAchievementsSortMethod);
			steamAchievementsSortMethodActionGroup.add(steamAchievementsSortMethodAction);
			steamAchievementsSortMethodMenu.add(ActionGroupFactory.getRadioMenuItem(steamAchievementsSortMethodAction));
		}

		// steamAchievementsListsSortMethodMenu
		steamAchievementsListsSortMethodMenu = new JMenu(BundleManager.getUITexts(me, "steamAchievementsListsSortMethodMenuLabel"));
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
		
		profileMenu = new JMenu(BundleManager.getUITexts(me, "profileMenuLabel"));
		mainMenuBar.add(profileMenu);
		
		// loadProfileMenuItem
		loadProfileAction = new LoadProfileAction(me);
		loadProfileMenuItem = profileMenu.add(loadProfileAction);
		
		// addProfileMenuItem
		addProfileAction = new AddProfileAction(me);
		addProfileMenuItem = profileMenu.add(addProfileAction);
		
		// steamGroupsSortMethodMenu
		steamGroupsSortMethodMenu = new JMenu(BundleManager.getUITexts(me, "steamGroupsSortMethodMenuLabel"));
		steamGroupsSortMethodMenu.setIcon(GamesLibrary.librarySortMethod);
		profileMenu.add(steamGroupsSortMethodMenu);
		
		steamGroupsSortMethodActionGroup = new ActionGroup();
		for (SteamGroupsSortMethod steamGroupsSortMethod : SteamGroupsSortMethod.values()) {
			SteamGroupsSortMethodAction steamGroupsSortMethodAction = new SteamGroupsSortMethodAction(me, steamGroupsSortMethod);
			steamGroupsSortMethodActionGroup.add(steamGroupsSortMethodAction);
			steamGroupsSortMethodMenu.add(ActionGroupFactory.getRadioMenuItem(steamGroupsSortMethodAction));
		}

		// steamFriendsSortMethodMenu
		steamFriendsSortMethodMenu = new JMenu(BundleManager.getUITexts(me, "steamFriendsSortMethodMenuLabel"));
		steamFriendsSortMethodMenu.setIcon(GamesLibrary.librarySortMethod);
		profileMenu.add(steamFriendsSortMethodMenu);
		
		steamFriendsSortMethodActionGroup = new ActionGroup();
		for (SteamFriendsSortMethod steamFriendsSortMethod : SteamFriendsSortMethod.values()) {
			SteamFriendsSortMethodAction steamFriendsSortMethodAction = new SteamFriendsSortMethodAction(me, steamFriendsSortMethod);
			steamFriendsSortMethodActionGroup.add(steamFriendsSortMethodAction);
			steamFriendsSortMethodMenu.add(ActionGroupFactory.getRadioMenuItem(steamFriendsSortMethodAction));
		}
		
		// steamGroupsDisplayModeMenu
		steamGroupsDisplayModeMenu = new JMenu(BundleManager.getUITexts(me, "steamGroupsDisplayModeMenuLabel"));
		steamGroupsDisplayModeMenu.setIcon(GamesLibrary.groupsIcon);
		profileMenu.add(steamGroupsDisplayModeMenu);
		
		steamGroupsDisplayModeListAction = new SteamGroupsDisplayModeAction(me, SteamGroupsDisplayMode.LIST); // WindowBuilder
		steamGroupsDisplayModeGridAction = new SteamGroupsDisplayModeAction(me, SteamGroupsDisplayMode.GRID); // WindowBuilder
		steamGroupsDisplayModeActionGroup = new ActionGroup();
		SteamGroupsDisplayModeAction steamGroupsDisplayModeAction = null;
		for (SteamGroupsDisplayMode steamGroupsDisplayMode : SteamGroupsDisplayMode.values()) {
			switch (steamGroupsDisplayMode) {
			case LIST : steamGroupsDisplayModeAction = steamGroupsDisplayModeListAction; break;
			case GRID : steamGroupsDisplayModeAction = steamGroupsDisplayModeGridAction; break;
			}
			steamGroupsDisplayModeActionGroup.add(steamGroupsDisplayModeAction);
			steamGroupsDisplayModeMenu.add(ActionGroupFactory.getRadioMenuItem(steamGroupsDisplayModeAction));
		}
		
		// steamFriendsDisplayModeMenu
		steamFriendsDisplayModeMenu = new JMenu(BundleManager.getUITexts(me, "steamFriendsDisplayModeMenuLabel"));
		steamFriendsDisplayModeMenu.setIcon(GamesLibrary.friendsIcon);
		profileMenu.add(steamFriendsDisplayModeMenu);
		
		steamFriendsDisplayModeListAction = new SteamFriendsDisplayModeAction(me, SteamFriendsDisplayMode.LIST); // WindowBuilder
		steamFriendsDisplayModeGridAction = new SteamFriendsDisplayModeAction(me, SteamFriendsDisplayMode.GRID); // WindowBuilder
		steamFriendsDisplayModeActionGroup = new ActionGroup();
		SteamFriendsDisplayModeAction steamFriendsDisplayModeAction = null;
		for (SteamFriendsDisplayMode steamFriendsDisplayMode : SteamFriendsDisplayMode.values()) {
			switch (steamFriendsDisplayMode) {
			case LIST : steamFriendsDisplayModeAction = steamFriendsDisplayModeListAction; break;
			case GRID : steamFriendsDisplayModeAction = steamFriendsDisplayModeGridAction; break;
			}
			steamFriendsDisplayModeActionGroup.add(steamFriendsDisplayModeAction);
			steamFriendsDisplayModeMenu.add(ActionGroupFactory.getRadioMenuItem(steamFriendsDisplayModeAction));
		}
		
		//
		// Options Menu
		//
		
		optionsMenu = new JMenu(BundleManager.getUITexts(me, "optionsMenuLabel"));
		mainMenuBar.add(optionsMenu);
		
		// resetOptionsMenuItem
		resetOptionsAction = new ResetOptionsAction(me);
		resetOptionsMenuItem = optionsMenu.add(resetOptionsAction);
		
		// steamExecutableMenuItem
		steamExecutableAction = new SteamExecutableAction(me);
		steamExecutableMenuItem = optionsMenu.add(steamExecutableAction);

		// defaultSteamLaunchMethodMenu
		defaultSteamLaunchMethodMenu = new JMenu(BundleManager.getUITexts(me, "defaultSteamLaunchMethodMenuLabel"));
		defaultSteamLaunchMethodMenu.setIcon(GamesLibrary.defaultSteamLaunchMethodIcon);
		optionsMenu.add(defaultSteamLaunchMethodMenu);

		defaultSteamLaunchMethodActionGroup = new ActionGroup();
		for (SteamLaunchMethod steamLaunchMethod : SteamLaunchMethod.values()) {
			DefaultSteamLaunchMethodAction defaultSteamLaunchMethodAction = new DefaultSteamLaunchMethodAction(me, steamLaunchMethod);
			defaultSteamLaunchMethodActionGroup.add(defaultSteamLaunchMethodAction);
			defaultSteamLaunchMethodMenu.add(ActionGroupFactory.getRadioMenuItem(defaultSteamLaunchMethodAction));
		}
		
		// displayToolTipsMenu
		displayToolTipsMenu = new JMenu(BundleManager.getUITexts(me, "displayToolTipsMenuLabel"));
		displayToolTipsMenu.setIcon(GamesLibrary.displayToolTipsIcon);
		optionsMenu.add(displayToolTipsMenu);

		displayToolTipsYesAction = new DisplayToolTipsAction(me, true);
		displayToolTipsNoAction = new DisplayToolTipsAction(me, false);
		
		displayToolTipsActionGroup = new ActionGroup();
		displayToolTipsActionGroup.add(displayToolTipsYesAction);
		displayToolTipsActionGroup.add(displayToolTipsNoAction);

		displayToolTipsMenu.add(ActionGroupFactory.getRadioMenuItem(displayToolTipsYesAction));
		displayToolTipsMenu.add(ActionGroupFactory.getRadioMenuItem(displayToolTipsNoAction));

		// dumpModeMenu
		dumpModeMenu = new JMenu(BundleManager.getUITexts(me, "dumpModeMenuLabel"));
		dumpModeMenu.setIcon(GamesLibrary.dumpModeIcon);
		optionsMenu.add(dumpModeMenu);
		
		dumpModeActionGroup = new ActionGroup();
		for (DumpMode dumpMode : DumpMode.values()) {
			DumpModeAction dumpModeAction = new DumpModeAction(me, dumpMode);
			dumpModeActionGroup.add(dumpModeAction);
			dumpModeMenu.add(ActionGroupFactory.getRadioMenuItem(dumpModeAction));
		}
		
		// localeChoiceMenu
		localeChoiceMenu = new JMenu(BundleManager.getUITexts(me, "localeChoiceMenuLabel"));
		localeChoiceMenu.setIcon(GamesLibrary.localeChoiceIcon);
		optionsMenu.add(localeChoiceMenu);
		
		localeChoiceActionGroup = new ActionGroup();
		for (LocaleChoice localeChoice : LocaleChoice.values()) {
			LocaleChoiceAction localeChoiceAction = new LocaleChoiceAction(me, localeChoice);
			localeChoiceActionGroup.add(localeChoiceAction);
			localeChoiceMenu.add(ActionGroupFactory.getRadioMenuItem(localeChoiceAction));
		}
		
		// lookAndFeelMenu
		lookAndFeelMenu = new JMenu(BundleManager.getUITexts(me, "lookAndFeelMenuLabel"));
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
		
		helpMenu = new JMenu(BundleManager.getUITexts(me, "helpMenuLabel"));
		mainMenuBar.add(helpMenu);
		
		// aboutMenuItem
		aboutAction = new AboutAction(me);
		aboutMenuItem = helpMenu.add(aboutAction);
		
		@SuppressWarnings("unused")
		JMenuItem[] menuitems = {
			loadParametersMenuItem, saveParametersMenuItem, exitMenuItem, 
			rollMenuItem, oneGameChoiceMenuItem, twoGamesChoiceMenuItem, threeGamesChoiceMenuItem,
			refreshGamesListMenuItem, scrollLockMenuItem, debugMenuItem, clearConsoleMenuItem, viewParametersMenuItem,
			loadLibraryMenuItem,
			loadGameStatsMenuItem, loadAllAchievementsMenuItem,
			loadProfileMenuItem, addProfileMenuItem,
			resetOptionsMenuItem, steamExecutableMenuItem,
			aboutMenuItem
		};
		
	}
	
	/**
	 * Do I18n on Actions and Menu components
	 * TODO: Observer
	 */
	public void translateActionsAndMenus() {
		
		// File Menu
		
		fileMenu.setText(BundleManager.getUITexts(me, "fileMenuLabel"));
		
		loadParametersAction.translate();
		saveParametersAction.translate();
		exitAction.translate();
		
		// Controls Menu
		
		controlMenu.setText(BundleManager.getUITexts(me, "controlsTabTitle"));
		
		rollAction.translate();
		
		gameChoiceMenu.setText(BundleManager.getUITexts(me, "gameChoiceMenuLabel"));
		oneGameChoiceAction.translate();
		oneGameChoiceMenuItem.setText(BundleManager.getUITexts(me, "oneGameChoiceLabel"));
		twoGamesChoiceAction.translate();
		twoGamesChoiceMenuItem.setText(BundleManager.getUITexts(me, "twoGamesChoiceLabel"));
		threeGamesChoiceAction.translate();
		threeGamesChoiceMenuItem.setText(BundleManager.getUITexts(me, "threeGamesChoiceLabel"));
		
		refreshGamesListAction.translate();
		scrollLockAction.translate();
		debugAction.translate();
		clearConsoleAction.translate();
		viewParametersAction.translate();
		
		// Library Menu
		
		libraryMenu.setText(BundleManager.getUITexts(me, "libraryMenuLabel"));
		
		loadLibraryAction.translate();
		
		librarySortMethodMenu.setText(BundleManager.getUITexts(me, "librarySortMethodMenuLabel"));
		for (Action action : librarySortMethodActionGroup.getActions())
			((SteamGamesSortMethodAction) action).translate();
		
		libraryDisplayModeMenu.setText(BundleManager.getUITexts(me, "libraryDisplayModeMenuLabel"));
		for (Action action : libraryDisplayModeActionGroup.getActions())
			((SteamGamesDisplayModeAction) action).translate();
		
		// Game Menu
		
		gameMenu.setText(BundleManager.getUITexts(me, "gameMenuLabel"));
		
		loadGameStatsAction.translate();
		loadAllAchievementsAction.translate();
		
		steamAchievementsSortMethodMenu.setText(BundleManager.getUITexts(me, "steamAchievementsSortMethodMenuLabel"));
		for (Action action : steamAchievementsSortMethodActionGroup.getActions())
			((SteamAchievementsSortMethodAction) action).translate();
		
		steamAchievementsListsSortMethodMenu.setText(BundleManager.getUITexts(me, "steamAchievementsListsSortMethodMenuLabel"));
		for (Action action : steamAchievementsListsSortMethodActionGroup.getActions())
			((SteamAchievementsListsSortMethodAction) action).translate();
		
		// Profile Menu
		
		profileMenu.setText(BundleManager.getUITexts(me, "profileMenuLabel"));
		
		loadProfileAction.translate();
		addProfileAction.translate();
		
		steamGroupsSortMethodMenu.setText(BundleManager.getUITexts(me, "steamGroupsSortMethodMenuLabel"));
		for (Action action : steamGroupsSortMethodActionGroup.getActions())
			((SteamGroupsSortMethodAction) action).translate();
		
		steamFriendsSortMethodMenu.setText(BundleManager.getUITexts(me, "steamFriendsSortMethodMenuLabel"));
		for (Action action : steamFriendsSortMethodActionGroup.getActions())
			((SteamFriendsSortMethodAction) action).translate();
		
		steamFriendsDisplayModeMenu.setText(BundleManager.getUITexts(me, "steamFriendsDisplayModeMenuLabel"));
		for (Action action : steamFriendsDisplayModeActionGroup.getActions())
			((SteamFriendsDisplayModeAction) action).translate();
		
		steamGroupsDisplayModeMenu.setText(BundleManager.getUITexts(me, "steamGroupsDisplayModeMenuLabel"));
		for (Action action : steamGroupsDisplayModeActionGroup.getActions())
			((SteamGroupsDisplayModeAction) action).translate();
		
		// Options Menu
		
		optionsMenu.setText(BundleManager.getUITexts(me, "optionsMenuLabel"));
		
		resetOptionsAction.translate();
		steamExecutableAction.translate();
		
		defaultSteamLaunchMethodMenu.setText(BundleManager.getUITexts(me, "defaultSteamLaunchMethodMenuLabel"));
		for (Action action : defaultSteamLaunchMethodActionGroup.getActions())
			((DefaultSteamLaunchMethodAction) action).translate();
		
		displayToolTipsMenu.setText(BundleManager.getUITexts(me, "displayToolTipsMenuLabel"));
		for (Action action : displayToolTipsActionGroup.getActions())
			((DisplayToolTipsAction) action).translate();
		
		dumpModeMenu.setText(BundleManager.getUITexts(me, "dumpModeMenuLabel"));
		for (Action action : dumpModeActionGroup.getActions())
			((DumpModeAction) action).translate();
		
		localeChoiceMenu.setText(BundleManager.getUITexts(me, "localeChoiceMenuLabel"));
		for (Action action : localeChoiceActionGroup.getActions())
			((LocaleChoiceAction) action).translate();
		
		lookAndFeelMenu.setText(BundleManager.getUITexts(me, "lookAndFeelMenuLabel"));
		for (Action action : lookAndFeelActionGroup.getActions())
			((LookAndFeelAction) action).translate();
		
		// Help Menu
		
		helpMenu.setText(BundleManager.getUITexts(me, "helpMenuLabel"));
		
		aboutAction.translate();
		aboutMenuItem.setText(BundleManager.getUITexts(me, "aboutMenuLabel"));
	}
	
	/**
	 * Done at end of work
	 */
	public void translateLoadAllAchievements() {
		loadAllAchievementsAction.translate();
	}
	
	/**
	 * Create the Controls Tab
	 */
	private void createControlsTab() {
		gamesLibrarianControls = new JPanel();
		gamesLibrarianControls.setName("controlsTab");
		
		mainPane.addTab(BundleManager.getUITexts(me, "controlsTabTitle"), null, gamesLibrarianControls, null);
		gamesLibrarianControls.setLayout(new MigLayout("", "[][][grow]", "[][][][][][][][][grow]"));
		
		rollButton = new JButton(rollAction);
		gamesLibrarianControls.add(rollButton, "cell 0 0,alignx left");
		
		gameNameTextField = new JTextField();
		gameNameTextField.setFont(new Font("Tahoma", Font.BOLD, 14));
		gameNameTextField.setToolTipText(BundleManager.getUITexts(me, "gameNameTextFieldToolTip"));
		gameNameTextField.setEditable(false);
		gamesLibrarianControls.add(gameNameTextField, "cell 1 0 2 1,growx");
		
		gameChoicePane = new JPanel();
		gamesLibrarianControls.add(gameChoicePane, "cell 0 1,alignx center,aligny center");
		gameChoicePane.setLayout(new MigLayout("", "[]", "[][][]"));
		
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
		
		WrapLayout gameLaunchersPaneWrapLayout = new WrapLayout();
		gameLaunchersPaneWrapLayout.setAlignment(FlowLayout.LEFT);
		gameLaunchersPane = new JPanel();
		gameLaunchersPane.setLayout(gameLaunchersPaneWrapLayout);

		gamesLibrarianControls.add(gameLaunchersPane, "cell 1 1 2 1,growx,aligny top");

		launchGame1Button = new LaunchButton(me, "", LaunchType.rolled, GameChoice.One, null);
		steamLaunchMethod1ComboBox = new SteamLaunchMethodComboBox(me, launchGame1Button, SteamLaunchMethodComboBox.Type.GameLauncher);
		argumentsGame1TextField = new GameArgumentsTextField(me, launchGame1Button);
		argumentsGame1TextField.setColumns(10);
		gameLauncher1 = new GameLauncher(me, launchGame1Button, steamLaunchMethod1ComboBox, argumentsGame1TextField);
		gameLaunchersPane.add(gameLauncher1);
		
		gameLauncher1.setLayout(new MigLayout("", "[grow]", "[][][]"));
		gameLauncher1.add(launchGame1Button, "cell 0 0,growx");
		gameLauncher1.add(steamLaunchMethod1ComboBox, "cell 0 1,growx");
		gameLauncher1.add(argumentsGame1TextField, "cell 0 2,growx");
		
		launchGame2Button = new LaunchButton(me, "", LaunchType.rolled, GameChoice.Two, null);
		steamLaunchMethod2ComboBox = new SteamLaunchMethodComboBox(me, launchGame2Button, SteamLaunchMethodComboBox.Type.GameLauncher);
		argumentsGame2TextField = new GameArgumentsTextField(me, launchGame2Button);
		argumentsGame2TextField.setColumns(10);
		gameLauncher2 = new GameLauncher(me, launchGame2Button, steamLaunchMethod2ComboBox, argumentsGame2TextField);
		gameLaunchersPane.add(gameLauncher2);
		
		gameLauncher2.setLayout(new MigLayout("", "[grow]", "[][][]"));
		gameLauncher2.add(launchGame2Button, "cell 0 0,growx");
		gameLauncher2.add(steamLaunchMethod2ComboBox, "cell 0 1,growx");
		gameLauncher2.add(argumentsGame2TextField, "cell 0 2,growx");
		
		launchGame3Button = new LaunchButton(me, "", LaunchType.rolled, GameChoice.Three, null);
		steamLaunchMethod3ComboBox = new SteamLaunchMethodComboBox(me, launchGame3Button, SteamLaunchMethodComboBox.Type.GameLauncher);
		argumentsGame3TextField = new GameArgumentsTextField(me, launchGame3Button);
		argumentsGame3TextField.setColumns(10);
		gameLauncher3 = new GameLauncher(me, launchGame3Button, steamLaunchMethod3ComboBox, argumentsGame3TextField);
		gameLaunchersPane.add(gameLauncher3);
		
		gameLauncher3.setLayout(new MigLayout("", "[grow]", "[][][]"));
		gameLauncher3.add(launchGame3Button, "cell 0 0,growx");
		gameLauncher3.add(steamLaunchMethod3ComboBox, "cell 0 1,growx");
		gameLauncher3.add(argumentsGame3TextField, "cell 0 2,growx");
		
		refreshGamesListButton = new JButton(refreshGamesListAction);
		gamesLibrarianControls.add(refreshGamesListButton, "cell 0 2 2 1,alignx left");
		
		JScrollPane consoleScrollPane = new JScrollPane();
		gamesLibrarianControls.add(consoleScrollPane, "cell 2 2 1 7,grow");
		
		consoleTextPane = new JTextPane();
		consoleTextPane.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		consoleTextPane.setEditable(false);
		consoleScrollPane.setViewportView(consoleTextPane);
		
		scrollLockButton = new JButton(scrollLockAction);
		gamesLibrarianControls.add(scrollLockButton, "cell 0 3 2 1,alignx left");
		
		debugButton = new JButton(debugAction);
		gamesLibrarianControls.add(debugButton, "cell 0 4 2 1,alignx left");
		
		clearConsoleButton = new JButton(clearConsoleAction);
		gamesLibrarianControls.add(clearConsoleButton, "cell 0 5 2 1,alignx left");
		
		loadParametersButton = new JButton(loadParametersAction);
		gamesLibrarianControls.add(loadParametersButton, "cell 0 6 2 1,alignx left");
		
		saveParametersButton = new JButton(saveParametersAction);
		gamesLibrarianControls.add(saveParametersButton, "cell 0 7 2 1,alignx left");
		
		viewParametersButton = new JButton(viewParametersAction);
		gamesLibrarianControls.add(viewParametersButton, "cell 0 8 2 1,alignx left,aligny top");
	}
	
	/**
	 * Create the Library Tab
	 */
	private void createLibraryTab() {
		gamesLibrarianLibrary = new JPanel();
		gamesLibrarianLibrary.setName("libraryTab");
		
		mainPane.addTab(BundleManager.getUITexts(me, "libraryTabTitle"), null, gamesLibrarianLibrary, null);
		
		libraryCommandsPane = new JPanel();
		libraryCommandsPane.setBorder(new LineBorder(Color.GRAY));
		libraryCommandsPane.setLayout(new MigLayout("", "[][][][][][][][][grow]", "[][]"));
				
		libraryTitleLabel = new TitleLabel(me, "libraryTitleLabelEmpty");
		libraryCommandsPane.add(libraryTitleLabel, "cell 0 0 8 1,alignx left,aligny center");
		
		loadLibraryButton = new JButton(loadLibraryAction);
		libraryCommandsPane.add(loadLibraryButton, "cell 0 1,alignx left,aligny top");
		
		librarySortMethodLabel = new Label(me, "librarySortMethodLabel");
		libraryCommandsPane.add(librarySortMethodLabel, "cell 1 1,alignx left,aligny center");
		
		previousSteamGameSortMethodButton = new PreviousSteamGameSortMethodButton(me);
		libraryCommandsPane.add(previousSteamGameSortMethodButton, "cell 2 1");
		
		librarySortMethodComboBox = new SteamGamesSortMethodComboBox(me);
		libraryCommandsPane.add(librarySortMethodComboBox, "cell 3 1,alignx left,aligny center");
		
		nextSteamGameSortMethodButton = new NextSteamGameSortMethodButton(me);
		libraryCommandsPane.add(nextSteamGameSortMethodButton, "cell 4 1");
		
		new EnumSelectionStateAdapter<SteamGamesSortMethod>(librarySortMethodActionGroup, librarySortMethodComboBox).configure();
		
		libraryDisplayModeLabel = new Label(me, "libraryDisplayModeLabel");
		libraryCommandsPane.add(libraryDisplayModeLabel, "cell 5 1,alignx left,aligny center");
		
		previousSteamGameDisplayModeButton = new PreviousSteamGameDisplayModeButton(me);
		libraryCommandsPane.add(previousSteamGameDisplayModeButton, "cell 6 1");
		
		libraryDisplayModeComboBox = new SteamGamesDisplayModeComboBox(me);
		libraryCommandsPane.add(libraryDisplayModeComboBox, "cell 7 1,alignx left,aligny center");
		
		new EnumSelectionStateAdapter<SteamGamesDisplayMode>(libraryDisplayModeActionGroup, libraryDisplayModeComboBox).configure();
		
		// Fill libraryPane Tab
		WrapLayout libraryPaneWrapLayout = new WrapLayout();
		libraryPaneWrapLayout.setAlignment(FlowLayout.LEFT);
		buttonsLibraryPane = new JPanel();
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
		
		gamesLibrarianLibrary.setLayout(new MigLayout("", "[grow]", "[][][grow]"));
		gamesLibrarianLibrary.add(libraryCommandsPane, "cell 0 0,grow");
		
		nextSteamGameDisplayModeButton = new NextSteamGameDisplayModeButton(me);
		libraryCommandsPane.add(nextSteamGameDisplayModeButton, "cell 8 1");
		
		libraryOptionsPane = new JPanel();
		libraryOptionsPane.setBorder(new LineBorder(Color.GRAY));
		libraryOptionsPane.setLayout(new MigLayout("", "[][][grow]", "[]"));
		
		libraryLeftClickActionLabel = new Label(me, "leftClickActionLabel");
		libraryOptionsPane.add(libraryLeftClickActionLabel, "cell 0 0");
		
		gamesLibrarianLibrary.add(libraryOptionsPane, "cell 0 1,grow");
		
		ButtonGroup libraryLeftClickActionButtonGroup = new ButtonGroup();
		
		libraryLeftClickSelectButton = new GameLeftClickActionButton(me, "leftClickActionSelect", GameLeftClickAction.Select);
		libraryLeftClickSelectButton.addItemListener(new GameLeftClickActionButtonListener(me));
		libraryOptionsPane.add(libraryLeftClickSelectButton, "cell 1 0");
		libraryLeftClickActionButtonGroup.add(libraryLeftClickSelectButton);
		
		libraryLeftClickLaunchButton = new GameLeftClickActionButton(me, "leftClickActionLaunch", GameLeftClickAction.Launch);
		libraryLeftClickLaunchButton.addItemListener(new GameLeftClickActionButtonListener(me));
		libraryOptionsPane.add(libraryLeftClickLaunchButton, "cell 2 0");
		libraryLeftClickActionButtonGroup.add(libraryLeftClickLaunchButton);
		
		CardLayout libraryPaneCardLayout = new CardLayout(0, 0);
		libraryPane = new JPanel();
		libraryPane.setLayout(libraryPaneCardLayout);
		
		gamesLibrarianLibrary.add(libraryPane, "cell 0 2,grow");
		
		libraryPane.add(libraryScrollPane, SteamGamesDisplayMode.LaunchPane.name());
		
		gamesLibraryListScrollPane = new JScrollPane();
		libraryPane.add(gamesLibraryListScrollPane, SteamGamesDisplayMode.ConfigurationPane.name());

		if (librarian != null)
			librarian.updateSteamGamesTable(launchButtons);
		
		// First visible card panel
		libraryPaneCardLayout.show(libraryPane, SteamGamesDisplayMode.LaunchPane.name());
	}
		
	/**
	 * Create the Game Tab
	 */
	private void createGameTab() {
		gamesLibrarianGame = new JPanel();
		gamesLibrarianGame.setName("gameTab");
		
		mainPane.addTab(BundleManager.getUITexts(me, "gameTabTitle"), null, gamesLibrarianGame, null);
		gamesLibrarianGame.setLayout(new MigLayout("", "[][][][grow]", "[][][][][][][grow]"));
		
		gameCommandsPane = new JPanel();
		gameCommandsPane.setBorder(new LineBorder(Color.GRAY));
		gamesLibrarianGame.add(gameCommandsPane, "cell 0 0 4 1,grow");
		gameCommandsPane.setLayout(new MigLayout("", "[][][][][][][][][grow]", "[][]"));
		
		currentGameTitleLabel = new TitleLabel(me, "currentGameTitleLabelEmpty");
		gameCommandsPane.add(currentGameTitleLabel, "flowx,cell 0 0 8 1,alignx left,aligny center");
		
		loadGameStatsButton = new JButton(loadGameStatsAction);
		gameCommandsPane.add(loadGameStatsButton, "cell 0 1,alignx left,aligny top");
		
		steamAchievementsSortMethodLabel = new Label(me, "achievementsSortMethodLabel");
		gameCommandsPane.add(steamAchievementsSortMethodLabel, "cell 1 1,alignx left,aligny center");
		
		previousSteamAchievementsSortMethodButton = new PreviousSteamAchievementsSortMethodButton(me);
		gameCommandsPane.add(previousSteamAchievementsSortMethodButton, "cell 2 1");
		
		steamAchievementsSortMethodComboBox = new SteamAchievementsSortMethodComboBox(me);
		gameCommandsPane.add(steamAchievementsSortMethodComboBox, "cell 3 1,alignx left,aligny center");
		
		new EnumSelectionStateAdapter<SteamAchievementsSortMethod>(steamAchievementsSortMethodActionGroup, steamAchievementsSortMethodComboBox).configure();
		
		nextSteamAchievementsSortMethodButton = new NextSteamAchievementsSortMethodButton(me);
		gameCommandsPane.add(nextSteamAchievementsSortMethodButton, "cell 4 1");
		
		steamAchievementsColumnsSortMethodLabel = new Label(me, "steamAchievementsListsSortMethodLabel");
		gameCommandsPane.add(steamAchievementsColumnsSortMethodLabel, "cell 5 1,alignx trailing");
		
		previousSteamAchievementsColumnsSortMethodButton = new PreviousSteamAchievementsColumnsSortMethodButton(me);
		gameCommandsPane.add(previousSteamAchievementsColumnsSortMethodButton, "cell 6 1");
		
		steamAchievementsListsSortMethodComboBox = new SteamAchievementsListsSortMethodComboBox(me);
		gameCommandsPane.add(steamAchievementsListsSortMethodComboBox, "cell 7 1,alignx left,aligny center");
		
		new EnumSelectionStateAdapter<SteamAchievementsListsSortMethod>(steamAchievementsListsSortMethodActionGroup, steamAchievementsListsSortMethodComboBox).configure();
		
		nextSteamAchievementsColumnsSortMethodButton = new NextSteamAchievementsColumnsSortMethodButton(me);
		gameCommandsPane.add(nextSteamAchievementsColumnsSortMethodButton, "cell 8 1");
		
		gameOptionsPane = new JPanel();
		gameOptionsPane.setBorder(new LineBorder(Color.GRAY));
		gameOptionsPane.setLayout(new MigLayout("", "[][][grow]", "[]"));
		
		gamesLibrarianGame.add(gameOptionsPane, "cell 0 1 4 1,grow");
		
		gameLeftClickActionLabel = new Label(me, "leftClickActionLabel");
		gameOptionsPane.add(gameLeftClickActionLabel, "cell 0 0");
		
		ButtonGroup gameLeftClickActionButtonGroup = new ButtonGroup();
		
		gameLeftClickSelectButton = new GameLeftClickActionButton(me, "leftClickActionSelect", GameLeftClickAction.Select);
		gameLeftClickSelectButton.addItemListener(new GameLeftClickActionButtonListener(me));
		gameOptionsPane.add(gameLeftClickSelectButton, "cell 1 0");
		gameLeftClickActionButtonGroup.add(gameLeftClickSelectButton);
		
		gameLeftClickLaunchButton = new GameLeftClickActionButton(me, "leftClickActionLaunch", GameLeftClickAction.Launch);
		gameLeftClickLaunchButton.addItemListener(new GameLeftClickActionButtonListener(me));
		gameOptionsPane.add(gameLeftClickLaunchButton, "cell 2 0");
		gameLeftClickActionButtonGroup.add(gameLeftClickLaunchButton);
		
		currentLaunchButton = new LaunchButton(me, "", LaunchType.current, null, null);
		currentSteamLaunchMethodComboBox = new SteamLaunchMethodComboBox(me, currentLaunchButton, SteamLaunchMethodComboBox.Type.GameLauncher);
		currentGameArgumentsTextField = new GameArgumentsTextField(me, currentLaunchButton);
		currentGameArgumentsTextField.setColumns(10);
		currentGameLauncher = new GameLauncher(me, currentLaunchButton, currentSteamLaunchMethodComboBox, currentGameArgumentsTextField);
		
		gamesLibrarianGame.add(currentGameLauncher, "cell 0 2 1 4,alignx left,aligny top");
		
		currentGameLauncher.setLayout(new MigLayout("", "[]", "[][][]"));
		currentGameLauncher.add(currentLaunchButton, "cell 0 0,growx");
		currentGameLauncher.add(currentSteamLaunchMethodComboBox, "cell 0 1,growx");
		currentGameLauncher.add(currentGameArgumentsTextField, "cell 0 2,growx");
		
		currentGameHoursPlayedLast2WeeksLabel = new Label(me, "accountHoursPlayedLastTwoWeeks");
		gamesLibrarianGame.add(currentGameHoursPlayedLast2WeeksLabel, "cell 1 2 2 1,alignx trailing");
		
		currentGameHoursPlayedLast2Weeks = new JTextField();
		currentGameHoursPlayedLast2Weeks.setEditable(false);
		gamesLibrarianGame.add(currentGameHoursPlayedLast2Weeks, "cell 3 2,growx");
		currentGameHoursPlayedLast2Weeks.setColumns(10);
		
		currentGameHoursPlayedTotalLabel = new Label(me, "mostPlayedGameHoursTotal");
		gamesLibrarianGame.add(currentGameHoursPlayedTotalLabel, "cell 1 3 2 1,alignx trailing");
		
		currentGameHoursPlayedTotal = new JTextField();
		currentGameHoursPlayedTotal.setEditable(false);
		gamesLibrarianGame.add(currentGameHoursPlayedTotal, "cell 3 3,growx");
		currentGameHoursPlayedTotal.setColumns(10);
		
		loadAllAchievementsButton = new JButton(loadAllAchievementsAction);
		gamesLibrarianGame.add(loadAllAchievementsButton, "cell 1 4,alignx right,aligny top");
		
		friendsWithSameGameLabel = new Label(me, "friendsWithSameGameLabel");
		gamesLibrarianGame.add(friendsWithSameGameLabel, "cell 2 4,alignx trailing");
		
		WrapLayout friendsWithSameGameLayout = new WrapLayout();
		friendsWithSameGameLayout.setAlignment(FlowLayout.LEFT);
		friendsWithSameGamePane = new JPanel();
		friendsWithSameGamePane.setLayout(friendsWithSameGameLayout);
		
		friendsWithSameGameScrollPane = new JScrollPane();
		friendsWithSameGameScrollPane.add(friendsWithSameGamePane);
		friendsWithSameGameScrollPane.setViewportView(friendsWithSameGamePane);
		
		gamesLibrarianGame.add(friendsWithSameGameScrollPane, "cell 3 4 1 2,grow");
		
		steamAchievementsScrollPane = new JScrollPane();
		gamesLibrarianGame.add(steamAchievementsScrollPane, "cell 0 6 4 1,grow");
	}
	
	/**
	 * Create the Profile Summary Sub Tab
	 */
	private void createProfileSummarySubTab() {
		profileSummaryPane = new JPanel();
		profileSummaryPane.setName("profileSummaryTab");
		profileSummaryPane.setLayout(new MigLayout("", "[][][][grow]", "[][][][][][][][grow]"));
		profilePane.addTab(BundleManager.getUITexts(me, profileSummaryPane.getName() + "Title"), null, profileSummaryPane, null);
		
		gamerProfileIconFull = new IconPane(me);
		gamerProfileIconFull.setMinimumSize(new Dimension(Steam.avatarFullIconWidth, Steam.avatarFullIconHeight));
		profileSummaryPane.add(gamerProfileIconFull, "cell 0 0 1 4,alignx center");
		
		gamerProfileIconFull.setPreferredSize(new Dimension(Steam.avatarFullIconWidth, Steam.avatarFullIconHeight));
		gamerProfileIconFull.setLayout(new BorderLayout(0, 0));

		realnameLabel = new Label(me, "accountRealname");
		profileSummaryPane.add(realnameLabel, "cell 1 0,alignx trailing");
		
		gamerProfileRealname = new JTextField();
		profileSummaryPane.add(gamerProfileRealname, "cell 2 0 2 1,growx");
		gamerProfileRealname.setEditable(false);
		gamerProfileRealname.setColumns(10);
		
		locationLabel = new Label(me, "accountLocation");
		profileSummaryPane.add(locationLabel, "cell 1 1,alignx trailing");
		
		gamerProfileLocation = new JTextField();
		profileSummaryPane.add(gamerProfileLocation, "cell 2 1 2 1,growx");
		gamerProfileLocation.setEditable(false);
		gamerProfileLocation.setColumns(10);
		
		customURLLabel = new Label(me, "accountCustomURL");
		profileSummaryPane.add(customURLLabel, "cell 1 2,alignx trailing");
		
		gamerProfileCustomURL = new JTextField();
		gamerProfileCustomURL.setEditable(false);
		profileSummaryPane.add(gamerProfileCustomURL, "cell 2 2 2 1,growx");
		gamerProfileCustomURL.setColumns(10);
		
		summaryLabel = new Label(me, "accountSummary");
		profileSummaryPane.add(summaryLabel, "cell 1 3,alignx trailing,aligny top");
		
		gamerProfileSummaryScrollPane = new JScrollPane();
		profileSummaryPane.add(gamerProfileSummaryScrollPane, "cell 2 3 2 1,grow");
		
		gamerProfileSummary = new JEditorPane();
		gamerProfileSummaryScrollPane.setViewportView(gamerProfileSummary);
		gamerProfileSummary.setContentType("text/html");
		gamerProfileSummary.setEditable(false);
		
		memberSinceLabel = new Label(me, "accountMemberSince");
		profileSummaryPane.add(memberSinceLabel, "cell 0 4,alignx trailing");
		
		gamerProfileMemberSince = new JTextField();
		profileSummaryPane.add(gamerProfileMemberSince, "cell 1 4,growx");
		gamerProfileMemberSince.setEditable(false);
		gamerProfileMemberSince.setColumns(10);
		
		headlineLabel = new Label(me, "accountHeadline");
		profileSummaryPane.add(headlineLabel, "cell 2 4");
		
		gamerProfileHeadlineScrollPane = new JScrollPane();
		profileSummaryPane.add(gamerProfileHeadlineScrollPane, "cell 3 4 1 3,grow");
		
		gamerProfileHeadline = new JTextPane();
		gamerProfileHeadline.setEditable(false);
		gamerProfileHeadlineScrollPane.setViewportView(gamerProfileHeadline);
		
		steamRatingLabel = new Label(me, "accoutSteamRating");
		profileSummaryPane.add(steamRatingLabel, "cell 0 5,alignx trailing");
		
		gamerProfileSteamRating = new JTextField();
		profileSummaryPane.add(gamerProfileSteamRating, "cell 1 5,growx");
		gamerProfileSteamRating.setEditable(false);
		gamerProfileSteamRating.setColumns(10);
		
		hoursPlayedLastTwoWeeksLabel = new Label(me, "accountHoursPlayedLastTwoWeeks");
		profileSummaryPane.add(hoursPlayedLastTwoWeeksLabel, "cell 0 6,alignx trailing");
		
		gamerProfileHoursPlayedLast2Weeks = new JTextField();
		profileSummaryPane.add(gamerProfileHoursPlayedLast2Weeks, "cell 1 6,growx");
		gamerProfileHoursPlayedLast2Weeks.setEditable(false);
		gamerProfileHoursPlayedLast2Weeks.setColumns(10);
		
		WrapLayout mostPlayedGamesPaneLayout = new WrapLayout();
		mostPlayedGamesPaneLayout.setVgap(1);
		mostPlayedGamesPaneLayout.setHgap(1);
		mostPlayedGamesPaneLayout.setAlignment(FlowLayout.LEFT);
		mostPlayedGamesPane = new JPanel();
		mostPlayedGamesPane.setLayout(mostPlayedGamesPaneLayout);
		
		mostPlayedGamesScrollPane = new JScrollPane();
		profileSummaryPane.add(mostPlayedGamesScrollPane, "cell 0 7 4 1,grow");
		
		mostPlayedGamesScrollPane.setViewportView(mostPlayedGamesPane);
		
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
		profileStatusPane = new JPanel();
		profileStatusPane.setName("profileStatusTab");
		profileStatusPane.setLayout(new MigLayout("", "[][grow][][grow]", "[][][][][]"));
		profilePane.addTab(BundleManager.getUITexts(me, profileStatusPane.getName() + "Title"), null, profileStatusPane, null);
		
		gamerProfileAccountGamerSteamId64Label = new Label(me, "accountSteamID64");
		profileStatusPane.add(gamerProfileAccountGamerSteamId64Label, "cell 0 0,alignx trailing");
		
		gamerProfileAccountGamerSteamId64 = new JTextField();
		gamerProfileAccountGamerSteamId64.setEditable(false);
		profileStatusPane.add(gamerProfileAccountGamerSteamId64, "cell 1 0,growx");
		gamerProfileAccountGamerSteamId64.setColumns(10);
		
		gamerProfileAccountOnlineStateLabel = new Label(me, "accountOnlineState");
		profileStatusPane.add(gamerProfileAccountOnlineStateLabel, "cell 2 0,alignx trailing");
		
		gamerProfileAccountOnlineState = new JTextField();
		gamerProfileAccountOnlineState.setEditable(false);
		profileStatusPane.add(gamerProfileAccountOnlineState, "cell 3 0,growx");
		gamerProfileAccountOnlineState.setColumns(10);
		
		gamerProfileAccountGamerSteamIdLabel = new Label(me, "accountGamerSteamID");
		profileStatusPane.add(gamerProfileAccountGamerSteamIdLabel, "cell 0 1,alignx trailing");
		
		gamerProfileAccountGamerSteamId = new JTextField();
		gamerProfileAccountGamerSteamId.setEditable(false);
		profileStatusPane.add(gamerProfileAccountGamerSteamId, "cell 1 1,growx");
		gamerProfileAccountGamerSteamId.setColumns(10);
		
		gamerProfileAccountStateMessageLabel = new Label(me, "accountStateMessage");
		profileStatusPane.add(gamerProfileAccountStateMessageLabel, "flowx,cell 2 1,alignx trailing");
		
		gamerProfileAccountStateMessage = new JTextField();
		gamerProfileAccountStateMessage.setEditable(false);
		profileStatusPane.add(gamerProfileAccountStateMessage, "cell 3 1,growx");
		gamerProfileAccountStateMessage.setColumns(10);
		
		gamerProfileAccountPrivacyStateLabel = new Label(me, "accountPrivacystate");
		profileStatusPane.add(gamerProfileAccountPrivacyStateLabel, "cell 0 2,alignx trailing");
		
		gamerProfileAccountPrivacyState = new JTextField();
		gamerProfileAccountPrivacyState.setEditable(false);
		profileStatusPane.add(gamerProfileAccountPrivacyState, "cell 1 2,growx");
		gamerProfileAccountPrivacyState.setColumns(10);
		
		gamerProfileAccountVisibilityStateLabel = new Label(me, "accountVisibilityState");
		profileStatusPane.add(gamerProfileAccountVisibilityStateLabel, "cell 2 2,alignx trailing");
		
		gamerProfileAccountVisibilityState = new JTextField();
		gamerProfileAccountVisibilityState.setEditable(false);
		profileStatusPane.add(gamerProfileAccountVisibilityState, "cell 3 2,growx");
		gamerProfileAccountVisibilityState.setColumns(10);
		
		gamerProfileAccountVacBannedLabel = new Label(me, "accountVACBanned");
		profileStatusPane.add(gamerProfileAccountVacBannedLabel, "cell 0 3,alignx trailing");
		
		gamerProfileAccountVacBanned = new JTextField();
		gamerProfileAccountVacBanned.setEditable(false);
		profileStatusPane.add(gamerProfileAccountVacBanned, "cell 1 3,growx");
		gamerProfileAccountVacBanned.setColumns(10);
		
		gamerProfileAccountTradeBanStateLabel = new Label(me, "accountTradeBanState");
		profileStatusPane.add(gamerProfileAccountTradeBanStateLabel, "cell 2 3,alignx trailing");
		
		gamerProfileAccountTradeBanState = new JTextField();
		gamerProfileAccountTradeBanState.setEditable(false);
		profileStatusPane.add(gamerProfileAccountTradeBanState, "cell 3 3,growx");
		gamerProfileAccountTradeBanState.setColumns(10);
		
		gamerProfileAccountLimitedAccountLabel = new Label(me, "accountLimitedAccount");
		profileStatusPane.add(gamerProfileAccountLimitedAccountLabel, "cell 0 4,alignx trailing");
		
		gamerProfileAccountLimitedAccount = new JTextField();
		gamerProfileAccountLimitedAccount.setEditable(false);
		profileStatusPane.add(gamerProfileAccountLimitedAccount, "cell 1 4,growx");
		gamerProfileAccountLimitedAccount.setColumns(10);
	}
	
	/**
	 * Create the Profile Groups Sub Tab
	 */
	private void createProfileGroupsSubTab() {
		profileGroupsPane = new JPanel();
		profileGroupsPane.setName("profileGroupsTab");
		profilePane.addTab(BundleManager.getUITexts(me, profileGroupsPane.getName() + "Title"), null, profileGroupsPane, null);

		profileSteamGroupsTabCardLayout = new CardLayout(0, 0);
		profileGroupsPane.setLayout(profileSteamGroupsTabCardLayout);
		
		steamGroupsScrollPane = new JScrollPane();
		steamGroupsScrollPane.setName(SteamGroupsDisplayMode.LIST.name());
		
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
		
		profileGroupsPane.add(steamGroupsScrollPane, SteamGroupsDisplayMode.GRID.name());
		
		steamGroupsListScrollPane = new JScrollPane();
		steamGroupsListScrollPane.setName(SteamGroupsDisplayMode.LIST.name());
		
		profileGroupsPane.add(steamGroupsListScrollPane, SteamGroupsDisplayMode.LIST.name());

		// First visible Groups card pane
		profileSteamGroupsTabCardLayout.show(profileGroupsPane, SteamGroupsDisplayMode.LIST.name());
	}
	
	/**
	 * Create the Profile Friends Sub Tab
	 */
	private void createProfileFriendsSubTab() {
		profileFriendsPane = new JPanel();
		profileFriendsPane.setName("profileFriendsTab");
		profilePane.addTab(BundleManager.getUITexts(me, profileFriendsPane.getName() + "Title"), null, profileFriendsPane, null);

		profileSteamFriendsTabCardLayout = new CardLayout(0, 0);
		profileFriendsPane.setLayout(profileSteamFriendsTabCardLayout);
		
		steamFriendsScrollPane = new JScrollPane();
		steamFriendsScrollPane.setName(SteamFriendsDisplayMode.GRID.name());
		
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
		
		profileFriendsPane.add(steamFriendsScrollPane, SteamFriendsDisplayMode.GRID.name());
		
		steamFriendsListScrollPane = new JScrollPane();
		steamFriendsListScrollPane.setName(SteamFriendsDisplayMode.LIST.name());
		
		profileFriendsPane.add(steamFriendsListScrollPane, SteamFriendsDisplayMode.LIST.name());
		
		// First visible Friends card pane
		profileSteamFriendsTabCardLayout.show(profileFriendsPane, SteamFriendsDisplayMode.GRID.name());
	}
	
	/**
	 * Create the Profile Tab
	 */
	private void createProfileTab() {
		// Create the Main Pane components
		gamesLibrarianProfile = new JPanel();
		gamesLibrarianProfile.setName("profileTab");
		
		mainPane.addTab(BundleManager.getUITexts(me, "profileTabTitle"), null, gamesLibrarianProfile, null);
		gamesLibrarianProfile.setLayout(new MigLayout("", "[grow]", "[][][grow]"));
		
		profileCommandsPane = new JPanel();
		profileCommandsPane.setBorder(new LineBorder(Color.GRAY));
		gamesLibrarianProfile.add(profileCommandsPane, "cell 0 0,grow");
		profileCommandsPane.setLayout(new MigLayout("", "[][][][][][][][][][][][][grow]", "[][]"));
		
		currentProfileTitleLabel = new TitleLabel(me, "currentProfileTitleLabelEmpty");
		profileCommandsPane.add(currentProfileTitleLabel, "cell 0 0 13 1,alignx left,aligny center");
		
		loadProfileButton = new JButton(loadProfileAction);
		profileCommandsPane.add(loadProfileButton, "cell 0 1");
		
		addProfileButton = new JButton(addProfileAction);
		profileCommandsPane.add(addProfileButton, "cell 1 1");
		
		previousKnownProfileButton = new PreviousSteamProfileButton(me);
		profileCommandsPane.add(previousKnownProfileButton, "cell 2 1");
		
		knownProfilesComboBox = new KnownProfilesComboBox(me);
		profileCommandsPane.add(knownProfilesComboBox, "cell 3 1,growx");
		
		nextKnownProfileButton = new NextSteamProfileButton(me);
		profileCommandsPane.add(nextKnownProfileButton, "cell 4 1");
		
		steamGroupsSortMethodLabel = new Label(me, "steamGroupsSortMethodLabel");
		profileCommandsPane.add(steamGroupsSortMethodLabel, "cell 5 1,alignx trailing");
		
		previousSteamGroupSortMethodButton = new PreviousSteamGroupSortMethodButton(me);
		profileCommandsPane.add(previousSteamGroupSortMethodButton, "cell 6 1");
		
		steamGroupsSortMethodComboBox = new SteamGroupsSortMethodComboBox(me);
		profileCommandsPane.add(steamGroupsSortMethodComboBox, "cell 7 1,growx");
		
		new EnumSelectionStateAdapter<SteamGroupsSortMethod>(steamGroupsSortMethodActionGroup, steamGroupsSortMethodComboBox).configure();
		
		nextSteamGroupSortMethodButton = new NextSteamGroupSortMethodButton(me);
		profileCommandsPane.add(nextSteamGroupSortMethodButton, "cell 8 1");
		
		steamFriendsSortMethodLabel = new Label(me, "steamFriendsSortMethodLabel");
		profileCommandsPane.add(steamFriendsSortMethodLabel, "cell 9 1,alignx trailing");
		
		previousSteamFriendSortMethodButton = new PreviousSteamFriendSortMethodButton(me);
		profileCommandsPane.add(previousSteamFriendSortMethodButton, "cell 10 1");
		
		steamFriendsSortMethodComboBox = new SteamFriendsSortMethodComboBox(me);
		profileCommandsPane.add(steamFriendsSortMethodComboBox, "cell 11 1,growx");
		
		new EnumSelectionStateAdapter<SteamFriendsSortMethod>(steamFriendsSortMethodActionGroup, steamFriendsSortMethodComboBox).configure();
		
		nextSteamFriendSortMethodButton = new NextSteamFriendSortMethodButton(me);
		profileCommandsPane.add(nextSteamFriendSortMethodButton, "cell 12 1");
		
		profileOptionsPane = new JPanel();
		profileOptionsPane.setBorder(new LineBorder(Color.GRAY));
		profileOptionsPane.setLayout(new MigLayout("", "[][][][][][][][][grow]", "[]"));
		
		gamesLibrarianProfile.add(profileOptionsPane, "cell 0 1,grow");
		
		profileLeftClickActionLabel = new Label(me, "leftClickActionLabel");
		profileOptionsPane.add(profileLeftClickActionLabel, "cell 0 0");
		
		ButtonGroup profileLeftClickActionButtonGroup = new ButtonGroup();
		
		profileLeftClickSelectButton = new GameLeftClickActionButton(me, "leftClickActionSelect", GameLeftClickAction.Select);
		profileLeftClickSelectButton.addItemListener(new GameLeftClickActionButtonListener(me));
		profileOptionsPane.add(profileLeftClickSelectButton, "cell 1 0");
		profileLeftClickActionButtonGroup.add(profileLeftClickSelectButton);
		
		profileLeftClickLaunchButton = new GameLeftClickActionButton(me, "leftClickActionLaunch", GameLeftClickAction.Launch);
		profileLeftClickLaunchButton.addItemListener(new GameLeftClickActionButtonListener(me));
		profileOptionsPane.add(profileLeftClickLaunchButton, "cell 2 0");
		profileLeftClickActionButtonGroup.add(profileLeftClickLaunchButton);
		
		ButtonGroup steamGroupsDisplayModeButtonGroup = new ButtonGroup();
		
		steamGroupsDisplayModeLabel = new Label(me, "steamGroupsDisplayModeLabel");
		profileOptionsPane.add(steamGroupsDisplayModeLabel, "cell 3 0");
		steamGroupsDisplayModeList = (JToggleButton) ActionGroupFactory.getToggleButton(steamGroupsDisplayModeListAction);
		profileOptionsPane.add(steamGroupsDisplayModeList, "cell 4 0");
		steamGroupsDisplayModeButtonGroup.add(steamGroupsDisplayModeList);
		steamGroupsDisplayModeGrid = (JToggleButton) ActionGroupFactory.getToggleButton(steamGroupsDisplayModeGridAction);
		profileOptionsPane.add(steamGroupsDisplayModeGrid, "cell 5 0");
		steamGroupsDisplayModeButtonGroup.add(steamGroupsDisplayModeGrid);
		
		ButtonGroup steamFriendsDisplayModeButtonGroup = new ButtonGroup();
		
		steamFriendsDisplayModeLabel = new Label(me, "steamFriendsDisplayModeLabel");
		profileOptionsPane.add(steamFriendsDisplayModeLabel, "cell 6 0");
		steamFriendsDisplayModeList = (JToggleButton) ActionGroupFactory.getToggleButton(steamFriendsDisplayModeListAction);
		profileOptionsPane.add(steamFriendsDisplayModeList, "cell 7 0");
		steamFriendsDisplayModeButtonGroup.add(steamFriendsDisplayModeList);
		steamFriendsDisplayModeGrid = (JToggleButton) ActionGroupFactory.getToggleButton(steamFriendsDisplayModeGridAction);
		profileOptionsPane.add(steamFriendsDisplayModeGrid, "cell 8 0");
		steamFriendsDisplayModeButtonGroup.add(steamFriendsDisplayModeGrid);
		
		profilePane = new JTabbedPane(JTabbedPane.TOP);
		gamesLibrarianProfile.add(profilePane, "cell 0 2,grow");
		
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
		gamesLibrarianOptions = new JPanel();
		gamesLibrarianOptions.setName("optionsTab");
		
		mainPane.addTab(BundleManager.getUITexts(me, "optionsTabTitle"), null, gamesLibrarianOptions, null);
		gamesLibrarianOptions.setLayout(new MigLayout("", "[][][][grow]", "[][][][][][][][][]"));
		windowsDistributionLabel = new Label(me, "windowsDistributionLabel");
		gamesLibrarianOptions.add(windowsDistributionLabel, "cell 0 0,alignx trailing");
		
		resetOptionsButton = new JButton(resetOptionsAction);
		gamesLibrarianOptions.add(resetOptionsButton, "cell 1 0 2 1");
		
		windowsDistributionTextField = new JTextField();
		windowsDistributionTextField.setToolTipText(BundleManager.getUITexts(me, "windowsDistributionTextFieldToolTip"));
		windowsDistributionTextField.setEditable(false);
		windowsDistributionTextField.setColumns(10);
		windowsDistributionLabel.setLabelFor(windowsDistributionTextField);
		gamesLibrarianOptions.add(windowsDistributionTextField, "cell 3 0,growx");
		
		steamExecutableLabel = new Label(me, "steamExecutableOptionLabel");
		gamesLibrarianOptions.add(steamExecutableLabel, "cell 0 1,alignx trailing");
		
		steamExecutableButton = new JButton(steamExecutableAction);
		gamesLibrarianOptions.add(steamExecutableButton, "cell 1 1 2 1");
		
		steamExecutableTextField = new JTextField();
		steamExecutableTextField.setToolTipText(BundleManager.getUITexts(me, "steamExecutableTextFieldToolTip"));
		steamExecutableTextField.setEditable(false);
		steamExecutableTextField.setColumns(10);
		steamExecutableLabel.setLabelFor(steamExecutableTextField);
		gamesLibrarianOptions.add(steamExecutableTextField, "cell 3 1,growx");
		
		gamerSteamIdLabel = new Label(me, "gamerSteamIdLabel");
		gamesLibrarianOptions.add(gamerSteamIdLabel, "cell 0 2,alignx trailing");
		
		gamerSteamIdTextField = new JTextField();
		gamerSteamIdLabel.setLabelFor(gamerSteamIdTextField);
		gamerSteamIdTextField.setToolTipText(BundleManager.getUITexts(me, "gamerSteamIdTextFieldToolTip"));
		gamerSteamIdTextField.setColumns(10);
		gamesLibrarianOptions.add(gamerSteamIdTextField, "cell 1 2 3 1,growx");
		
		defaultSteamLaunchMethodLabel = new Label(me, "defaultSteamLaunchMethodLabel");
		gamesLibrarianOptions.add(defaultSteamLaunchMethodLabel, "cell 0 3,alignx trailing");
		
		defaultSteamLaunchMethodComboBox = new SteamLaunchMethodComboBox(me, null, SteamLaunchMethodComboBox.Type.DefaultMethod);
		defaultSteamLaunchMethodComboBox.setToolTipText(BundleManager.getUITexts(me, "defaultSteamLaunchMethodComboBoxTootlTip"));
		gamesLibrarianOptions.add(defaultSteamLaunchMethodComboBox, "cell 1 3 3 1");
		
		new EnumSelectionStateAdapter<SteamLaunchMethod>(defaultSteamLaunchMethodActionGroup, defaultSteamLaunchMethodComboBox).configure();
		
		gameLeftClickActionOptionLabel = new Label(me, "gameLeftClickActionLabel");
		gamesLibrarianOptions.add(gameLeftClickActionOptionLabel, "cell 0 4,alignx trailing");
		
		gameLeftClickActionComboBox = new GameLeftClickActionComboBox(me);
		gameLeftClickActionComboBox.setToolTipText(BundleManager.getUITexts(me, "gameLeftClickActionToolTip"));
		gamesLibrarianOptions.add(gameLeftClickActionComboBox, "cell 1 4 3 1");
		
		displayToolTipsLabel = new Label(me, "displayToolTipsLabel");
		gamesLibrarianOptions.add(displayToolTipsLabel, "cell 0 5,alignx trailing");
		
		ButtonGroup displayTooltipsButtonGroup = new ButtonGroup();
		displayTooltipsYesButton = (JToggleButton) ActionGroupFactory.getToggleButton(displayToolTipsYesAction);
		gamesLibrarianOptions.add(displayTooltipsYesButton, "cell 1 5");
		displayTooltipsButtonGroup.add(displayTooltipsYesButton);
		displayTooltipsNoButton = (JToggleButton) ActionGroupFactory.getToggleButton(displayToolTipsNoAction);
		gamesLibrarianOptions.add(displayTooltipsNoButton, "cell 2 5");
		displayTooltipsButtonGroup.add(displayTooltipsNoButton);
		
		dumpModeLabel = new Label(me, "dumpModeLabel");
		gamesLibrarianOptions.add(dumpModeLabel, "cell 0 6,alignx trailing");
		
		dumpModeComboBox = new DumpModeComboBox(me);
		dumpModeComboBox.setToolTipText(BundleManager.getUITexts(me, "dumpModeToolTip"));
		gamesLibrarianOptions.add(dumpModeComboBox, "cell 1 6 3 1");
		
		new EnumSelectionStateAdapter<DumpMode>(dumpModeActionGroup, dumpModeComboBox).configure();
		
		localeChoiceLabel = new Label(me, "localeChoiceLabel");
		gamesLibrarianOptions.add(localeChoiceLabel, "cell 0 7,alignx trailing");
		
		localeChoiceComboBox = new LocaleChoiceComboBox(me);
		localeChoiceComboBox.setToolTipText(BundleManager.getUITexts(me, "languageComboBoxToolTip"));
		gamesLibrarianOptions.add(localeChoiceComboBox, "cell 1 7 3 1");
		
		new EnumSelectionStateAdapter<LocaleChoice>(localeChoiceActionGroup, localeChoiceComboBox).configure();
		
		LookAndFeelLabel = new Label(me, "lookAndFeelLabel");
		gamesLibrarianOptions.add(LookAndFeelLabel, "cell 0 8,alignx trailing");
		
		lookAndFeelInfoComboBox = new LookAndFeelInfoComboBox(me);
		gamesLibrarianOptions.add(lookAndFeelInfoComboBox, "cell 1 8 3 1");
		
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
		
		// Create the Main Pane
		mainPane = new JTabbedPane(JTabbedPane.TOP);
		mainPane.setBorder(new EmptyBorder(2, 1, 1, 0));
		setContentPane(mainPane);
	
		// Create the Controls Tab
		createControlsTab();
		
		// Create the Library Tab
		createLibraryTab();
		
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
