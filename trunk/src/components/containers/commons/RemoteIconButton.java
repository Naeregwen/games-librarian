/**
 * 
 */
package components.containers.commons;

import javax.swing.ImageIcon;


/**
 * @author Naeregwen
 *
 */
public interface RemoteIconButton {

	/**
	 * Get the Icon URL from Button
	 * @return the Icon URL
	 */
	public String getIconURL();
	
	/**
	 * Set icon
	 * @param icon the icon to set
	 */
	public void setIcon(ImageIcon icon);
	
	/**
	 * Get AvatarIcon as Resource
	 * Try to read icon data from AvatarIcon as a locale resource key
	 */
	public void setAvatarIconAsResource();

}
