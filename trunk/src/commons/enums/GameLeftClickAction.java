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
	
}
