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

}