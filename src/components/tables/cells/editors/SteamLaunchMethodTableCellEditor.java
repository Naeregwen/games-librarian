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
package components.tables.cells.editors;

import java.awt.Component;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import commons.api.SteamGame;
import commons.api.SteamLaunchMethod;
import components.Librarian;
import components.GamesLibrarian.WindowBuilderMask;
import components.comboboxes.SteamLaunchMethodComboBox;
import components.containers.remotes.LaunchButton;
import components.tables.models.SteamGamesTableModel;

/**
 * @author Naeregwen
 *
 */
public class SteamLaunchMethodTableCellEditor extends AbstractCellEditor implements TableCellEditor {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3283568749658807114L;

	Librarian librarian;
	List<LaunchButton> launchButtons;
	SteamLaunchMethodComboBox steamLaunchMethodComboBox;
	
	public SteamLaunchMethodTableCellEditor(WindowBuilderMask me, List<LaunchButton> launchButtons) {
		super();
		this.librarian = me.getLibrarian();
		this.launchButtons = launchButtons;
		steamLaunchMethodComboBox = new SteamLaunchMethodComboBox(me, null, SteamLaunchMethodComboBox.Type.LibraryList);
	}

	@Override
	public Object getCellEditorValue() {
		return steamLaunchMethodComboBox.getSelectedItem();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		int selectedRow = table.convertRowIndexToModel(row);
		SteamGame game = ((SteamGamesTableModel) table.getModel()).getGameAt(selectedRow);
		Iterator<LaunchButton> launchButtonsIterator = launchButtons.iterator();
		while (launchButtonsIterator.hasNext()) {
			LaunchButton launchButton = launchButtonsIterator.next();
			if (launchButton.getGame().getAppID().equalsIgnoreCase(game.getAppID())) {
				steamLaunchMethodComboBox.setLaunchButton(launchButton);
				break;
			}
		}
		steamLaunchMethodComboBox.setSelectedItem((SteamLaunchMethod) value);
		return steamLaunchMethodComboBox;
	}

}
