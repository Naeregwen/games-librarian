/**
 * 
 */
package components.labels;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import components.GamesLibrarian;

/**
 * @author Naeregwen
 *
 */
public class ErroneousLabel extends JLabel {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5568727152397280380L;
	
	private static Font uhOhFont;
	
	// Set the font and text when no image was found.
	protected static void setUhOhText(JLabel label, String uhOhText, Font normalFont) {
		// Lazily create this font
		if (uhOhFont == null)
			uhOhFont = normalFont.deriveFont(Font.ITALIC);
		label.setFont(uhOhFont);
		label.setText(uhOhText);
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imageURL = GamesLibrarian.class.getResource(path);
		if (imageURL != null)
			return new ImageIcon(imageURL);
		else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

}
