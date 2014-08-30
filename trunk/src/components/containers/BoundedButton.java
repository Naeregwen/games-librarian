/**
 * 
 */
package components.containers;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * @author Naeregwen
 *
 */
public class BoundedButton extends JButton {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7230946196179463043L;
	
	int width;
	int height;
	
	public BoundedButton(String label, int width, int height) {
		super(label);
		this.width = width;
		this.height = height;
	}
	
	protected ImageIcon resizeAndCenterIcon(ImageIcon icon) {
		return BoundedComponent.resizeAndCenterIcon(this, icon, width, height);
	}
	
	/**
	 * Size icon to button limits
	 * 
	 * @param icon
	 */
	public void resizeAndSetIcon(ImageIcon icon) {
		ImageIcon newIcon = BoundedComponent.resizeAndSetIcon(this, icon, width, height);
		super.setIcon(newIcon);
	}
	
}
