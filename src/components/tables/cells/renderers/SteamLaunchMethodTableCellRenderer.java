/**
 * 
 */
package components.tables.cells.renderers;

import java.awt.Component;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import commons.api.SteamLaunchMethod;
import components.GamesLibrarian;


/**
 * @author Naeregwen
 *
 */
public class SteamLaunchMethodTableCellRenderer extends DefaultTableCellRenderer {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4149260492431144390L;

	private static ImageIcon[] images;
	
	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path) {
		URL imagePath = GamesLibrarian.class.getResource(path);
		if (imagePath != null) {
			return new ImageIcon(imagePath);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	static {
		// Create the combo box components.
		images = new ImageIcon[SteamLaunchMethod.values().length];
		int i = 0;
		for (SteamLaunchMethod steamLaunchMethod : SteamLaunchMethod.values()) {
			images[i] = createImageIcon(steamLaunchMethod.getIconPath());
			if (images[i] != null) 
				images[i].setDescription(steamLaunchMethod.name());
			i += 1;
		}
	}

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        SteamLaunchMethod steamLaunchMethod = (SteamLaunchMethod) value; 
        // Update UI
        ResourceBundle UITexts = ResourceBundle.getBundle("i18n/UITexts");
        setText(UITexts.getString(SteamLaunchMethod.values()[steamLaunchMethod.ordinal()].getLabelKey()));
        setIcon(images[steamLaunchMethod.ordinal()]);
        return this;
    }	
}
