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
public enum ProfileTab implements TabEnum {

	Summary ("gotoSummaryMenuLabel", "gotoSummaryMnemonic", "gotoSummaryAccelerator", GamesLibrary.accountSummaryIcon),
	Status ("gotoStatusMenuLabel", "gotoStatusMnemonic", "gotoStatusAccelerator", GamesLibrary.accountStatusIcon),
	Groups ("gotoGroupsMenuLabel", "gotoGroupsMnemonic", "gotoGroupsAccelerator", GamesLibrary.groupsIcon),
	Friends ("gotoFriendsMenuLabel", "gotoFriendsMnemonic", "gotoFriendsAccelerator", GamesLibrary.friendsIcon);
	
	String labelKey;
	String mnemonicKey;
	String acceleratorKey;
	Icon icon;

	ProfileTab(String labelKey, String mnemonicKey, String acceleratorKey, Icon icon) {
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
		return false;
	}

}
