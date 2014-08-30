package commons.api;



public enum SteamLaunchMethod {
	
	/**
	 * i18n keys from bundles
	 */
	SteamProtocol (
			"steamProtocolLaunchOptionLabel", 
			"steamProtocolLaunchOptionToolTip",
			"steamProtocolOptionSelectionMessage",
			"steamProtocolOptionDefaultSelectionMessage",
			"/images/icons/application_lightning.png"),
			
	SteamExecutable (
			"steamExecutableLaunchOptionLabel", 
			"steamExecutableLaunchOptionToolTip",
			"steamExecutableOptionSelectionMessage",
			"steamExecutableOptionDefaultSelectionMessage",
			"/images/icons/application_xp_terminal.png");
	
	String labelKey;
	String toolTipKey;
	String selectionMessageKey;
	String defaultSelectionMessageKey;
	String iconPath;
	
	SteamLaunchMethod(String labelKey, String toolTipKey, String selectionMessageKey, String defaultSelectionMessageKey, String iconPath) {
		this.labelKey = labelKey;
		this.toolTipKey = toolTipKey;
		this.selectionMessageKey = selectionMessageKey;
		this.defaultSelectionMessageKey = defaultSelectionMessageKey;
		this.iconPath = iconPath;
	}

	/**
	 * @return the iconPath
	 */
	public String getIconPath() {
		return iconPath;
	}
	
	/**
	 * @return the labelKey
	 */
	public String getLabelKey() {
		return labelKey;
	}

	/**
	 * @return the toolTipKey
	 */
	public String getToolTipKey() {
		return toolTipKey;
	}

	/**
	 * @return the selectionMessageKey
	 */
	public String getSelectionMessageKey() {
		return selectionMessageKey;
	}

	/**
	 * @return the defaultSelectionMessageKey
	 */
	public String getDefaultSelectionMessageKey() {
		return defaultSelectionMessageKey;
	}

	/**
	 * @return a list of acceptable string values for parsing
	 */
	public static String getAcceptableValues() {
		StringBuilder acceptableValues = new StringBuilder("");
		for (SteamLaunchMethod steamLaunchMethod : SteamLaunchMethod.values()) 
			acceptableValues.append((acceptableValues.length() == 0 ? steamLaunchMethod : ", " + steamLaunchMethod) + ", " + steamLaunchMethod.ordinal());
		return acceptableValues.toString();	
	}
	
	/**
	 * Return the SteamLaunchMethod the most corresponding to passed value
	 *  
	 * @param value the value to make pass the test
	 * @return SteamLaunchMethod if found, null otherwise
	 */
	public static SteamLaunchMethod getAcceptableValue (String value) {
		for (SteamLaunchMethod steamLaunchMethod : SteamLaunchMethod.values())
			if (steamLaunchMethod.toString().equalsIgnoreCase(value))
				return steamLaunchMethod;
		return null;
	}
}

