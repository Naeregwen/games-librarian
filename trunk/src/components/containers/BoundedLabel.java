/**
 * 
 */
package components.containers;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * @author Naeregwen
 *
 */
public class BoundedLabel extends JLabel {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6671304941283427825L;

	int width;
	int height;
	
	public BoundedLabel(String label, int width, int height) {
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
		super.setIcon(BoundedComponent.resizeAndSetIcon(this, icon, width, height));
	}
	
}
