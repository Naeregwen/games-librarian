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
package components.tables;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SortOrder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import commons.api.SteamGame;
import commons.enums.SteamGamesSortMethod;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.comboboxes.SteamLaunchMethodComboBox;
import components.containers.remotes.LaunchButton;
import components.tables.cells.editors.SteamLaunchMethodTableCellEditor;
import components.tables.cells.renderers.IconTableCellRenderer;
import components.tables.cells.renderers.SteamLaunchMethodTableCellRenderer;
import components.tables.headers.listeners.SteamGamesHeaderListener;
import components.tables.headers.renderers.SteamGamesTableHeaderRenderer;
import components.tables.listeners.SteamGamesTableSelectionListener;
import components.tables.models.SteamGamesTableModel;

/**
 * @author Naeregwen
 *
 */
public class SteamGamesTable extends JTable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8168403098358208244L;
	
	WindowBuilderMask me;
	Librarian librarian;
	
	public SteamGamesTable(WindowBuilderMask me, List<SteamGame> games, List<LaunchButton> launchButtons) {
		
		super();
		this.me = me;
		this.librarian = me.getLibrarian();
		
		// Model
		setModel(new SteamGamesTableModel(games));
		
        // Sorting
		setAutoCreateRowSorter(true);
		
		// Table header renderer
		JTableHeader tableHeader = getTableHeader();
		tableHeader.setDefaultRenderer(new SteamGamesTableHeaderRenderer());
		
        // Table header listener
		tableHeader.addMouseListener(new SteamGamesHeaderListener(librarian, tableHeader));
		
		// Logo renderer
		TableColumn logoColumn = getColumnModel().getColumn(SteamGame.ColumnsOrder.logo.ordinal());
		logoColumn.setCellRenderer(new IconTableCellRenderer());
		
		// Selection handlers
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        getSelectionModel().addListSelectionListener(new SteamGamesTableSelectionListener(librarian, this));
        
		// SteamLaunchMethodComboBox handlers
		TableColumn steamLaunchMethodColumn = getColumnModel().getColumn(SteamGame.ColumnsOrder.steamLaunchMethod.ordinal());
		steamLaunchMethodColumn.setCellRenderer(new SteamLaunchMethodTableCellRenderer(me, this));
		steamLaunchMethodColumn.setCellEditor(new SteamLaunchMethodTableCellEditor(me, launchButtons));
		
		// Layout & display
		setLayout(new GridLayout(1, 0, 0, 0));
		setRowHeight(getPreferredRowHeight());
		setFillsViewportHeight(true);		
		
	}
	
	public int getPreferredRowHeight() {
		return new SteamLaunchMethodComboBox(me, null, SteamLaunchMethodComboBox.Type.LibraryList).getPreferredSize().height;
//		return Steam.gameIconHeight;
	}
	
	public List<SteamGame> getGames() {
		return ((SteamGamesTableModel) getModel()).getGames();
	}
	
	public SteamGame getGameAt(int modelRow) {
		return ((SteamGamesTableModel)getModel()).getGameAt(modelRow);
	}
	
	public void sorter() {
		if (getRowSorter().getSortKeys().size() > 0) {
			
			getRowSorter().toggleSortOrder(getRowSorter().getSortKeys().get(0).getColumn());
			String columnName = getModel().getColumnName(getRowSorter().getSortKeys().get(0).getColumn());
			SortOrder sortOrder = getRowSorter().getSortKeys().get(0).getSortOrder();
			SteamGame.ColumnsOrder columnOrder = SteamGame.ColumnsOrder.valueOf(columnName);
				
			if (columnOrder != null) {
				Integer steamGamesSortMethodIndex = null;
				SteamGamesSortMethod steamGamesSortMethod = null;
				switch (columnOrder) {
				case logo:
					steamGamesSortMethodIndex = sortOrder.equals(SortOrder.ASCENDING) ? SteamGamesSortMethod.LogoDescendingOrder.ordinal() : SteamGamesSortMethod.LogoAscendingOrder.ordinal();
					steamGamesSortMethod = sortOrder.equals(SortOrder.ASCENDING) ? SteamGamesSortMethod.LogoDescendingOrder : SteamGamesSortMethod.LogoAscendingOrder;
					break;
				case name:
					steamGamesSortMethodIndex = sortOrder.equals(SortOrder.ASCENDING) ? SteamGamesSortMethod.NameDescendingOrder.ordinal() : SteamGamesSortMethod.NameAscendingOrder.ordinal();
					steamGamesSortMethod = sortOrder.equals(SortOrder.ASCENDING) ? SteamGamesSortMethod.NameDescendingOrder : SteamGamesSortMethod.NameAscendingOrder;
					break;
				case arguments:
					steamGamesSortMethodIndex = sortOrder.equals(SortOrder.ASCENDING) ? SteamGamesSortMethod.ArgumentsDescendingOrder.ordinal() : SteamGamesSortMethod.ArgumentsAscendingOrder.ordinal();
					steamGamesSortMethod = sortOrder.equals(SortOrder.ASCENDING) ? SteamGamesSortMethod.ArgumentsDescendingOrder : SteamGamesSortMethod.ArgumentsAscendingOrder;
					break;
				case steamLaunchMethod:
					steamGamesSortMethodIndex = sortOrder.equals(SortOrder.ASCENDING) ? SteamGamesSortMethod.SteamLaunchMethodDescendingOrder.ordinal() : SteamGamesSortMethod.SteamLaunchMethodAscendingOrder.ordinal();
					steamGamesSortMethod = sortOrder.equals(SortOrder.ASCENDING) ? SteamGamesSortMethod.SteamLaunchMethodDescendingOrder : SteamGamesSortMethod.SteamLaunchMethodAscendingOrder;
					break;
				case hoursLast2Weeks:
					steamGamesSortMethodIndex = sortOrder.equals(SortOrder.ASCENDING) ? SteamGamesSortMethod.HoursLast2WeeksDescendingOrder.ordinal() : SteamGamesSortMethod.HoursLast2WeeksAscendingOrder.ordinal();
					steamGamesSortMethod = sortOrder.equals(SortOrder.ASCENDING) ? SteamGamesSortMethod.HoursLast2WeeksDescendingOrder : SteamGamesSortMethod.HoursLast2WeeksAscendingOrder;
					break;
				case hoursOnRecord:
					steamGamesSortMethodIndex = sortOrder.equals(SortOrder.ASCENDING) ? SteamGamesSortMethod.HoursOnRecordDescendingOrder.ordinal() : SteamGamesSortMethod.HoursOnRecordAscendingOrder.ordinal();
					steamGamesSortMethod = sortOrder.equals(SortOrder.ASCENDING) ? SteamGamesSortMethod.HoursOnRecordDescendingOrder : SteamGamesSortMethod.HoursOnRecordAscendingOrder;
					break;
				case appID:
					steamGamesSortMethodIndex = sortOrder.equals(SortOrder.ASCENDING) ? SteamGamesSortMethod.AppIdDescendingOrder.ordinal() : SteamGamesSortMethod.AppIdAscendingOrder.ordinal();
					steamGamesSortMethod = sortOrder.equals(SortOrder.ASCENDING) ? SteamGamesSortMethod.AppIdDescendingOrder : SteamGamesSortMethod.AppIdAscendingOrder;
					break;
				case storeLink:
					steamGamesSortMethodIndex = sortOrder.equals(SortOrder.ASCENDING) ? SteamGamesSortMethod.StoreLinkDescendingOrder.ordinal() : SteamGamesSortMethod.StoreLinkAscendingOrder.ordinal();
					steamGamesSortMethod = sortOrder.equals(SortOrder.ASCENDING) ? SteamGamesSortMethod.StoreLinkDescendingOrder : SteamGamesSortMethod.StoreLinkAscendingOrder;
					break;
				case globalStatsLink:
					steamGamesSortMethodIndex = sortOrder.equals(SortOrder.ASCENDING) ? SteamGamesSortMethod.GlobalStatsLinkDescendingOrder.ordinal() : SteamGamesSortMethod.GlobalStatsLinkAscendingOrder.ordinal();
					steamGamesSortMethod = sortOrder.equals(SortOrder.ASCENDING) ? SteamGamesSortMethod.GlobalStatsLinkDescendingOrder : SteamGamesSortMethod.GlobalStatsLinkAscendingOrder;
					break;
				case statsLink:
					steamGamesSortMethodIndex = sortOrder.equals(SortOrder.ASCENDING) ? SteamGamesSortMethod.StatsLinkDescendingOrder.ordinal() : SteamGamesSortMethod.StatsLinkAscendingOrder.ordinal();
					steamGamesSortMethod = sortOrder.equals(SortOrder.ASCENDING) ? SteamGamesSortMethod.StatsLinkDescendingOrder : SteamGamesSortMethod.StatsLinkAscendingOrder;
					break;
				}
				
				if (steamGamesSortMethodIndex != null) {
					if (librarian.getSteamGamesSortMethodComboBox().getSelectedItem() != steamGamesSortMethod)
						librarian.getSteamGamesSortMethodComboBox().setSelectedItem(steamGamesSortMethod);
				}
				
			}
		}
	}
	
}
