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
public enum LibraryTab implements TabEnum {

	LibraryGamesList ("gotoLibraryGamesListMenuLabel", "gotoLibraryGamesListMnemonic", "gotoLibraryGamesListAccelerator", GamesLibrary.libraryMenuIcon),
	LibraryStatistics ("gotoLibraryStatisticsMenuLabel", "gotoLibraryStatisticsMnemonic", "gotoLibraryStatisticsAccelerator", GamesLibrary.libraryStatisticsMenuIcon);
	
	String labelKey;
	String mnemonicKey;
	String acceleratorKey;
	Icon icon;

	LibraryTab(String labelKey, String mnemonicKey, String acceleratorKey, Icon icon) {
		this.labelKey = labelKey;
		this.mnemonicKey = mnemonicKey;
		this.acceleratorKey = acceleratorKey;
		this.icon = icon;
	}
	
	/* (non-Javadoc)
	 * @see commons.enums.TabEnum#isMain()
	 */
	@Override
	public boolean isMain() {
		return false;
	}

	/* (non-Javadoc)
	 * @see commons.enums.TabEnum#getLabelKey()
	 */
	@Override
	public String getLabelKey() {
		return labelKey;
	}

	/* (non-Javadoc)
	 * @see commons.enums.TabEnum#getMnemonicKey()
	 */
	@Override
	public String getMnemonicKey() {
		return mnemonicKey;
	}

	/* (non-Javadoc)
	 * @see commons.enums.TabEnum#getAcceleratorKey()
	 */
	@Override
	public String getAcceleratorKey() {
		return acceleratorKey;
	}

	/* (non-Javadoc)
	 * @see commons.enums.TabEnum#getIcon()
	 */
	@Override
	public Icon getIcon() {
		return icon;
	}

}
