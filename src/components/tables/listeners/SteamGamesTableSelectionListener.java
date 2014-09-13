/**
 * Copyright 2012-2014 Naeregwen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
