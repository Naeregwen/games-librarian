/**
 * 
 */
package components.actions.interfaces;

import javax.swing.ImageIcon;

/**
 * @author Naeregwen
 *
 */
public interface IconAndTextAction {

	/**
	 * Get the label key for Bundle
	 * @return the Label key
	 */
	public String getLabelKey();
	
	/**
	 * Get the icon
	 * @return the icon
	 */
	public ImageIcon getIcon();
	
	
}
