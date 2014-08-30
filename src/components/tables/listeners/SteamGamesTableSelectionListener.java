/**
 * 
 */
package components.tables.listeners;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import commons.api.SteamGame;
import commons.api.SteamGame.ColumnsOrder;
import components.Librarian;
import components.tables.SteamGamesTable;

/**
 * @author Naeregwen
 *
 */
public class SteamGamesTableSelectionListener implements ListSelectionListener {

	Librarian librarian;
	SteamGamesTable table;
	int currentRow = -1;
	
	public SteamGamesTableSelectionListener(Librarian librarian, SteamGamesTable table) {
		super();
		this.librarian = librarian;
		this.table = table;
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
    public void valueChanged(ListSelectionEvent event) {
		
 		// See if this is a valid table selection
		if (event.getSource() == table.getSelectionModel() && event.getFirstIndex() >= 0) {
			if (currentRow == table.getSelectedRow()) return;
			currentRow = table.getSelectedRow();
	        if (currentRow < 0) {
	            // Selection got filtered away.
	        	if (librarian.getParameters().isDebug())
	        		librarian.getTee().writelnInfos("valueChanged Selection got filtered away");
	        } else {
	            int modelRow = table.convertRowIndexToModel(currentRow);
	            SteamGame game = table.getGameAt(modelRow);
	            if (librarian.getParameters().isDebug())
	            		librarian.getTee().writelnInfos(String.format("valueChanged Selected Row in view: %d. Selected Row in model: %d. name: [%s], game = [%s]", 
	            				currentRow, modelRow, table.getModel().getValueAt(modelRow, ColumnsOrder.name.ordinal()), game.getName())
	            );
	            librarian.updateGameTab(game);
	        }
		}		
		
    }

}
