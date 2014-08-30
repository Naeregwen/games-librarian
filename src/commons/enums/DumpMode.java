/**
 * 
 */
package commons.enums;

/**
 * @author Naeregwen
 *
 */
public enum DumpMode  {

	Text ("dumpModeTextLabel", "dumpModeTextMnemonic", "dumpModeTextAccelerator", "/images/icons/script_edit.png"), 
	XML ("dumpModeXMLLabel", "dumpModeXMLMnemonic", "dumpModeXMLAccelerator", "/images/icons/script_code.png"), 
	Both ("dumpModeBothLabel", "dumpModeBothMnemonic", "dumpModeBothAccelerator", "/images/icons/script_gear.png");
	
	String labelKey;
	String mnemonicKey;
	String acceleratorKey;
	String iconPath;
	
	
	DumpMode(String labelKey, String mnemonicKey, String acceleratorKey, String iconPath) {
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

	/**
	 * @return a list of acceptable string values for parsing
	 */
	public static String getAcceptableValues() {
		StringBuilder acceptableValues = new StringBuilder("");
		for (DumpMode dumpMode : DumpMode.values()) 
			acceptableValues.append((acceptableValues.length() == 0 ? dumpMode : ", " + dumpMode) + ", " + dumpMode.ordinal());
		return acceptableValues.toString();	
	}
	
	/**
	 * Return the DumpMode the most corresponding to passed value
	 *  
	 * @param value the value to make pass the test
	 * @return DumpMode if found, null otherwise
	 */
	public static DumpMode getAcceptableValue (String value) {
		for (DumpMode dumpMode : DumpMode.values())
			if (dumpMode.toString().equalsIgnoreCase(value))
				return dumpMode;
		return null;
	}
}
