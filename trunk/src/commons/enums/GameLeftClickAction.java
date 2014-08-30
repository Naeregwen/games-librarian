package commons.enums;

public enum GameLeftClickAction {

	Launch ("gameLeftClickActionLaunchLabel", "/images/icons/page_white_text.png", "gameLeftClickActionLaunchLabel"),
	Select ("gameLeftClickActionSelectLabel", "/images/icons/page_white_text.png", "gameLeftClickActionSelectLabel");
	
	String optionLabel;
	String iconPath;
	String translationKey;
	
	private GameLeftClickAction(String optionLabel, String iconPath,
			String translationKey) {
		this.optionLabel = optionLabel;
		this.iconPath = iconPath;
		this.translationKey = translationKey;
	}
	
	/**
	 * @return the optionLabel
	 */
	public String getOptionLabel() {
		return optionLabel;
	}

	/**
	 * @return the iconPath
	 */
	public String getIconPath() {
		return iconPath;
	}

	/**
	 * @return the translationKey
	 */
	public String getTranslationKey() {
		return translationKey;
	}
	
	/**
	 * @return a list of acceptable string values for parsing
	 */
	public static String getAcceptableValues() {
		StringBuilder acceptableValues = new StringBuilder("");
		for (GameLeftClickAction gameLeftClickAction : GameLeftClickAction.values()) 
			acceptableValues.append((acceptableValues.length() == 0 ? gameLeftClickAction : ", " + gameLeftClickAction) + ", " + gameLeftClickAction.ordinal());
		return acceptableValues.toString();	
	}
	
	/**
	 * Return the GameLeftClickAction the most corresponding to passed value
	 *  
	 * @param value the value to make pass the test
	 * @return GameLeftClickAction if found, null otherwise
	 */
	public static GameLeftClickAction getAcceptableValue (String value) {
		for (GameLeftClickAction gameLeftClickAction : GameLeftClickAction.values())
			if (gameLeftClickAction.toString().equalsIgnoreCase(value))
				return gameLeftClickAction;
		return null;
	}
}
