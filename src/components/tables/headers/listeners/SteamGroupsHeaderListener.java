/**
 * 
 */
package components.tables.headers.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import components.Librarian;
import components.tables.models.SteamGroupsTableModel;

/**
 * @author Naeregwen
 *
 */
public class SteamGroupsHeaderListener extends MouseAdapter {

	Librarian librarian;
	JTableHeader header;

	public SteamGroupsHeaderListener(Librarian librarian, JTableHeader header) {
		this.librarian = librarian;
		this.header = header;
	}

	public void mouseClicked(MouseEvent e) {
		TableModel model = header.getTable().getModel();
		if (model instanceof SteamGroupsTableModel) {
			librarian.updateSteamGroupsSortMethod();
		}
	}
}
