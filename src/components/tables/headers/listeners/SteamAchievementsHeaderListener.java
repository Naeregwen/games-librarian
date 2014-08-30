/**
 * 
 */
package components.tables.headers.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import commons.sortables.SortableAchievementsList;
import components.Librarian;

/**
 * @author Naeregwen
 * 
 */
public class SteamAchievementsHeaderListener extends MouseAdapter {
	
	Librarian librarian;
	JTableHeader header;

	public SteamAchievementsHeaderListener(Librarian librarian, JTableHeader header) {
		this.librarian = librarian;
		this.header = header;
	}

	public void mouseClicked(MouseEvent e) {
		TableModel model = header.getTable().getModel();
		if (model instanceof SortableAchievementsList) {
			((SortableAchievementsList) model).reverseSort();
			librarian.updateSteamAchievementsSortMethodObservers();
		}
	}
}
