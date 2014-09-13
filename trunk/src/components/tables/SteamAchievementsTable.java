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

import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import commons.api.Steam;
import commons.api.SteamAchievementsList;
import commons.comparators.SteamAchievementsComparator;
import commons.comparators.SteamAchievementsListsComparator;
import commons.enums.SteamAchievementsListsSortMethod;
import commons.enums.SteamAchievementsSortMethod;
import components.Librarian;
import components.tables.cells.renderers.SteamAchievementIconTableCellRenderer;
import components.tables.headers.listeners.SteamAchievementsHeaderListener;
import components.tables.headers.renderers.SteamAchievementsTableHeaderRenderer;
import components.tables.models.SteamAchievementsTableModel;

/**
 * @author Naeregwen
 *
 */
public class SteamAchievementsTable extends JTable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1849197694718935230L;

	Librarian librarian;
	
	/**
	 * Build a SteamAchievementsTable with defaults properties
	 */
	public SteamAchievementsTable(Librarian librarian, SteamAchievementsList achievements, SteamAchievementsSortMethod achievementsSortMethod, SteamAchievementsListsSortMethod achievementsListSortMethod) {
		
		super();
		this.librarian = librarian;
		Vector<SteamAchievementsList> achievementsList = new Vector<SteamAchievementsList>(){private static final long serialVersionUID = 55410372109303511L;};
		achievementsList.add(achievements);
		
		// Model and column sorting
		setModel(new SteamAchievementsTableModel(achievementsList, new SteamAchievementsComparator(achievementsSortMethod), new SteamAchievementsListsComparator(achievementsListSortMethod)));
		
        // Table header listener
		JTableHeader tableHeader = getTableHeader();
		tableHeader.addMouseListener(new SteamAchievementsHeaderListener(librarian, getTableHeader()));
		// Table header renderer
		tableHeader.setDefaultRenderer(new SteamAchievementsTableHeaderRenderer());
		
		// Table cell renderer
		TableColumn unlockTimestampColumn = getColumnModel().getColumn(0);
		unlockTimestampColumn.setCellRenderer(new SteamAchievementIconTableCellRenderer(librarian.getParameters()));
		
		// Selection handler
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		// Layout & display
		setRowHeight(getPreferredRowHeight());
		setFillsViewportHeight(true);		
		
	}

	/**
	 * Default row height = Steam.achievementIconHeight + 1 default line of text (sum arbitrary promoted to The Height, like 42)
	 * @return Default row height
	 */
	public int getPreferredRowHeight() {
		return Steam.steamAchievementIconHeight + getFontMetrics(getFont()).getHeight();
	}
	
	/**
	 * Add a vector of achievements to model
	 * Then build a column with appropriates identifier and renderer
	 * 
	 * @param achievements
	 * @return new column to manage new achievementsList
	 */
	public TableColumn addColumn(SteamAchievementsList achievements) {
		SteamAchievementsTableModel model = (SteamAchievementsTableModel) getModel();
		model.addAchievementsList(achievements);
		TableColumn column = new TableColumn(model.getColumnCount() - 1);
		column.setIdentifier(achievements.getPlayerSteamID());
		column.setCellRenderer(new SteamAchievementIconTableCellRenderer(librarian.getParameters()));
		return column;
	}
}
