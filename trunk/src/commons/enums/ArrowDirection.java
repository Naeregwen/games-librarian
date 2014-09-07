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
public enum ArrowDirection {
	
	NEXT(GamesLibrary.nextIcon), PREVIOUS(GamesLibrary.previousIcon);
	
	ImageIcon imageIcon;
	
	ArrowDirection(ImageIcon imageIcon) {
		this.imageIcon = imageIcon;
	}

	/**
	 * @return the imageIcon
	 */
	public ImageIcon getImageIcon() {
		return imageIcon;
	}
	
	
}
