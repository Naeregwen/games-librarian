package commons.enums;

import javax.swing.Icon;

import commons.GamesLibrary;

public enum GameChoice {

	One ("oneGameChoiceLabel", "oneGameChoiceToolTip", "oneGameChoiceMnemonic", "oneGameChoiceAccelerator", GamesLibrary.oneGameChoiceIcon),
	Two ("twoGamesChoiceLabel", "twoGamesChoiceToolTip", "twoGamesChoiceMnemonic", "twoGamesChoiceAccelerator", GamesLibrary.twoGamesChoiceIcon),
	Three ("threeGamesChoiceLabel", "threeGamesChoiceToolTip", "threeGamesChoiceMnemonic", "threeGamesChoiceAccelerator", GamesLibrary.threeGamesChoiceIcon);
	
	String labelKey;
	String toolTipKey;
	String mnemonicKey;
	String acceleratorKey;
	Icon icon;

	GameChoice(String labelKey, String toolTipKey, String mnemonicKey, String acceleratorKey, Icon icon) {
		this.labelKey = labelKey;
		this.toolTipKey = toolTipKey;
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
	 * @return the toolTipKey
	 */
	public String getToolTipKey() {
		return toolTipKey;
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

	/**
	 * @return a list of acceptable string values for parsing
	 */
	public static String getAcceptableValues() {
		StringBuilder acceptableValues = new StringBuilder("");
		for (GameChoice gameChoice : GameChoice.values()) 
			acceptableValues.append((acceptableValues.length() == 0 ? gameChoice : ", " + gameChoice) + ", " + gameChoice.ordinal());
		return acceptableValues.toString();	
	}
	
	/**
	 * Return the GameChoice the most corresponding to passed value
	 *  
	 * @param value the value to make pass the test
	 * @return GameChoice if found, null otherwise
	 */
	public static GameChoice getAcceptableValue (String value) {
		for (GameChoice gameChoice : GameChoice.values())
			if (gameChoice.toString().equalsIgnoreCase(value))
				return gameChoice;
		return null;
	}
}