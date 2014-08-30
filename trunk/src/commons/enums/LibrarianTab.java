/**
 * 
 */
package commons.enums;

import javax.swing.Icon;

import commons.GamesLibrary;

/**
 * @author Naeregwen
 *
 */
public enum LibrarianTab implements TabEnum {

	Controls ("gotoControlsMenuLabel", "gotoControlsMnemonic", "gotoControlsAccelerator", GamesLibrary.controlsMenuIcon),
	Library ("gotoLibraryMenuLabel", "gotoLibraryMnemonic", "gotoLibraryAccelerator", GamesLibrary.libraryMenuIcon),
	Game ("gotoGameMenuLabel", "gotoGameMnemonic", "gotoGameAccelerator", GamesLibrary.gameMenuIcon),
	Profile ("gotoProfileMenuLabel", "gotoProfileMnemonic", "gotoProfileAccelerator", GamesLibrary.profileMenuIcon),
	Options ("gotoOptionsMenuLabel", "gotoOptionsMnemonic", "gotoOptionsAccelerator", GamesLibrary.optionsMenuIcon);
	
	String labelKey;
	String mnemonicKey;
	String acceleratorKey;
	Icon icon;

	LibrarianTab(String labelKey, String mnemonicKey, String acceleratorKey, Icon icon) {
		this.labelKey = labelKey;
		this.mnemonicKey = mnemonicKey;
		this.acceleratorKey = acceleratorKey;
		this.icon = icon;
	}
	
	/**
	 * @return the labelKey
	 */
	public String getLabelKey() {
		return labelKey;
	}

	/**
	 * @return the mnemonicKey
	 */
	public String getMnemonicKey() {
		return mnemonicKey;
	}

	/**
	 * @return the acceleratorKey
	 */
	public String getAcceleratorKey() {
		return acceleratorKey;
	}

	/**
	 * @return the icon
	 */
	public Icon getIcon() {
		return icon;
	}

	@Override
	public boolean isMain() {
		return true;
	}

}
