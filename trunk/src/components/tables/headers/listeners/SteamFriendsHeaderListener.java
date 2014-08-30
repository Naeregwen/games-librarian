/**
 * 
 */
package components.tables.headers.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import components.Librarian;
import components.tables.models.SteamFriendsTableModel;

/**
 * @author Naeregwen
 *
 */
public class SteamFriendsHeaderListener extends MouseAdapter {

	Librarian librarian;
	JTableHeader header;

	public SteamFriendsHeaderListener(Librarian librarian, JTableHeader header) {
		this.librarian = librarian;
		this.header = header;
	}

	public void mouseClicked(MouseEvent e) {
		TableModel model = header.getTable().getModel();
		if (model instanceof SteamFriendsTableModel) {
			librarian.updateSteamFriendsSortMethod();
		}
	}
}
