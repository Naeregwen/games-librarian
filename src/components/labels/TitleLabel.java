/**
 * 
 */
package components.labels;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.UIManager;

import commons.BundleManager;
import components.GamesLibrarian.WindowBuilderMask;

/**
 * @author Naeregwen
 *
 */
public class TitleLabel extends JLabel {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4172290672637965066L;
	
	public TitleLabel(WindowBuilderMask me, String key) {
		super(BundleManager.getUITexts(me, key));
		setFont(new Font(UIManager.getFont("Label.font").getFontName(), Font.BOLD, UIManager.getFont("Label.font").getSize() + 4));
	}

}
