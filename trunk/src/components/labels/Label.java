/**
 * 
 */
package components.labels;

import javax.swing.JLabel;

import commons.BundleManager;
import components.GamesLibrarian.WindowBuilderMask;

/**
 * @author Naeregwen
 *
 */
public class Label extends JLabel {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4096860122866218141L;
	
	public Label(WindowBuilderMask me, String key) {
		super(BundleManager.getUITexts(me, key));
	}

}
