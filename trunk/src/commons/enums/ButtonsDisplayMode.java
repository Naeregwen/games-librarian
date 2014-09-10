/**
 * 
 */
package commons.enums;

import javax.swing.ImageIcon;

import commons.GamesLibrary;

/**
 * @author Naeregwen
 *
 */
public enum ButtonsDisplayMode {
	
	IconAndText ("buttonsDisplayModeIconAndTextLabel", "buttonsDisplayModeIconAndTextMnemonic", "buttonsDisplayModeIconAndTextAccelerator", GamesLibrary.buttonsDisplayModeIconAndTextIcon), 
	Icon ("buttonsDisplayModeIconLabel", "buttonsDisplayModeIconMnemonic", "buttonsDisplayModeIconAccelerator", GamesLibrary.buttonsDisplayModeIconIcon), 
	Text ("buttonsDisplayModeTextLabel", "buttonsDisplayModeTextMnemonic", "buttonsDisplayModeTextAccelerator", GamesLibrary.buttonsDisplayModeTextIcon);
	
	String labelKey;
	String mnemonicKey;
	String acceleratorKey;
	ImageIcon icon;
	
	ButtonsDisplayMode(String labelKey, String mnemonicKey, String acceleratorKey, ImageIcon icon) {
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
	public ImageIcon getIcon() {
		return icon;
	}

	

}
