/**
 * 
 */
package components.tables.cells.renderers;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import commons.api.SteamAchievement;

/**
 * @author Naeregwen
 *
 */
public class SteamAchievementNameTableCellRenderer extends DefaultTableCellRenderer {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6454186218853668906L;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, "", isSelected, hasFocus, row, column);
        setVerticalAlignment(JLabel.CENTER);
        setHorizontalAlignment(JLabel.LEFT);
        SteamAchievement steamAchievement = (SteamAchievement) value;
        if (steamAchievement != null)
        	setText("<html><div style='font-size: 1.1em; font-weight: bold'>"+steamAchievement.getName()+"</div><br/><div style='font-size: 1em'>"+steamAchievement.getDescription()+"</div></html>");
        return this;
   }
}
