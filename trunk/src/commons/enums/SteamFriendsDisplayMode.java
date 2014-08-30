/**
 * 
 */
package commons.enums;

/**
 * @author Naeregwen
 *
 */
public enum SteamFriendsDisplayMode {

	LIST ("steamFriendsDisplayModeList", "steamFriendsDisplayModeListMnemonic", "steamFriendsDisplayModeListAccelerator", "/images/icons/application_view_list.png"),
	GRID ("steamFriendsDisplayModeGrid", "steamFriendsDisplayModeGridMnemonic", "steamFriendsDisplayModeGridAccelerator", "/images/icons/application_view_tile.png");
	
	String labelKey;
	String mnemonicKey;
	String acceleratorKey;
	String iconPath;
	
	SteamFriendsDisplayMode(String labelKey, String mnemonicKey, String acceleratorKey, String iconPath) {
		this.labelKey = labelKey;
		this.mnemonicKey = mnemonicKey;
		this.acceleratorKey = acceleratorKey;
		this.iconPath = iconPath;
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
	 * @return the iconPath
	 */
	public String getIconPath() {
		return iconPath;
	}
	
}
