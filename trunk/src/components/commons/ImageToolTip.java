/**
 * 
 */
package components.commons;

import javax.swing.ImageIcon;
import javax.swing.JToolTip;

import components.commons.ui.ImageToolTipUI;
import components.commons.ui.ImageToolTipUIHelper;
/**
 * @author Naeregwen
 *
 */
public class ImageToolTip extends JToolTip {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7046647446472695503L;

	public ImageToolTip(ImageIcon imageIcon, ImageToolTipUIHelper helper) {
		setUI(new ImageToolTipUI(imageIcon, helper));
	}
}
