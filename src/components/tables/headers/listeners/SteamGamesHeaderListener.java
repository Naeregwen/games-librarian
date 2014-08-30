/**
 * 
 */
package components.tables.headers.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import components.Librarian;
import components.tables.models.SteamGamesTableModel;

/**
 * @author Naeregwen
 *
 */
public class SteamGamesHeaderListener extends MouseAdapter {

	Librarian librarian;
	JTableHeader header;

	public SteamGamesHeaderListener(Librarian librarian, JTableHeader header) {
		this.librarian = librarian;
		this.header = header;
	}

	public void mouseClicked(MouseEvent e) {
		TableModel model = header.getTable().getModel();
		if (model instanceof SteamGamesTableModel) {
			librarian.updateSteamGamesSortMethod();
		}
	}
}
