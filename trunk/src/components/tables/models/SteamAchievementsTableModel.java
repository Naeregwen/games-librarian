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
package components.tables.models;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

import commons.api.SteamAchievement;
import commons.api.SteamAchievementsList;
import commons.api.SteamProfile;
import commons.comparators.SteamAchievementsComparator;
import commons.comparators.SteamAchievementsListsComparator;
import commons.enums.SteamAchievementsListsSortMethod;
import commons.enums.SteamAchievementsSortMethod;
import commons.sortables.SortableAchievementsList;

/**
 * DefaultTableModel has been reversed to allow transposed store, display and sorting of data,
 * which is a better layout for achievementsList,
 * row = columns and columns = rows
 * <br/>Allows also columns sorting
 * 
 * @author Naeregwen
 * 
 */
public class SteamAchievementsTableModel extends DefaultTableModel implements SortableAchievementsList {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6489878381650408577L;

	private Vector<SteamAchievementsList> achievementsLists;
	private SteamAchievementsComparator steamAchievementsComparator = null;
	private SteamAchievementsListsComparator steamAchievementsListComparator = null;

	/**
	 * Create a new SteamAchievementsTableModel
	 */
	public SteamAchievementsTableModel(Vector<SteamAchievementsList> achievementsList, SteamAchievementsComparator comparator, SteamAchievementsListsComparator steamAchievementsListComparator) {
		this.achievementsLists = achievementsList;
		this.steamAchievementsComparator = comparator;
		this.steamAchievementsListComparator = steamAchievementsListComparator;
	}

	/**
	 * @return the steamAchievementsComparator
	 */
	public SteamAchievementsComparator getSteamAchievementsComparator() {
		return steamAchievementsComparator;
	}

	/**
	 * @return the AchievementsList
	 */
	public Vector<SteamAchievementsList> getAchievementsList() {
		return this.achievementsLists;
	}

	/**
	 * Set the steamAchievementsComparator with achievementsSortMethod, then sort table
	 * 
	 * @param achievementsSortMethod
	 */
	public void setAchievementSortMethod(SteamAchievementsSortMethod achievementsSortMethod) {
		if (steamAchievementsComparator != null) {
			steamAchievementsComparator.setSteamAchievementsSortMethod(achievementsSortMethod);
			steamAchievementsComparator.setAscending(achievementsSortMethod.equals(SteamAchievementsSortMethod.InitialAscendingOrder) ||
					achievementsSortMethod.equals(SteamAchievementsSortMethod.NameAscendingOrder) ||
					achievementsSortMethod.equals(SteamAchievementsSortMethod.UnlockDateAscendingOrder) ? true : false);
		}
	}
	
	/**
	 * Set the steamAchievementsListComparator with achievementsListSortMethod, then sort table
	 * 
	 * @param achievementsListSortMethod the AchievementsListSortMethod to set
	 */
	public void setAchievementsListSortMethod(SteamAchievementsListsSortMethod achievementsListSortMethod) {
		if (steamAchievementsListComparator != null) {
			steamAchievementsListComparator.setSteamAchievementsListSortMethod(achievementsListSortMethod);
			steamAchievementsListComparator.setAscending(achievementsListSortMethod.equals(SteamAchievementsListsSortMethod.InitialAscendingOrder) ||
					achievementsListSortMethod.equals(SteamAchievementsListsSortMethod.NameAscendingOrder) ||
					achievementsListSortMethod.equals(SteamAchievementsListsSortMethod.AchievementsRatioAscendingOrder) ? true : false);
		}
	}

	/**
	 * Check columnIndex/rowIndex bounds and get achievements at columnIndex/rowIndex
	 * 
	 * @param columnIndex the columnIndex to look at
	 * @param rowIndex the rowIndex to look at
	 * @return SteamAchievement/null
	 */
	public SteamAchievement getAchievementAt(int columnIndex, int rowIndex) {
		if (achievementsLists == null
				|| achievementsLists.isEmpty()
				|| achievementsLists.size() - 1 < columnIndex
				|| achievementsLists.get(columnIndex) == null
				|| achievementsLists.get(columnIndex).getSteamAchievements() == null
				|| achievementsLists.get(columnIndex).getSteamAchievements().size() - 1 < rowIndex)
			return null;
		return achievementsLists.get(columnIndex).getSteamAchievements().get(rowIndex);
	}

	/**
	 * Check columnIndex bounds and get achievementsList at columnIndex
	 * 
	 * @param columnIndex the columnIndex to retrieve achievementsList 
	 * @return SteamAchievementsList/null
	 */
	public SteamAchievementsList getAchievementListAt(int columnIndex) {
		if (achievementsLists == null || achievementsLists.isEmpty() || achievementsLists.size() - 1 < columnIndex)
			return null;
		return achievementsLists.get(columnIndex);
	}
	
	/**
	 * Add a SteamAchievementsList
	 * 
	 * @param friendAchievementsList the SteamAchievementsList to add
	 */
	public void addAchievementsList(SteamAchievementsList friendAchievementsList) {
		achievementsLists.add(friendAchievementsList);
	}

	/**
	 * Remove a SteamAchievementsList corresponding to SteamProfile argument
	 * 
	 * @param friend the SteamProfile where SteamID & SteamID64 can be found to identify the SteamAchievementsList
	 * @return true if removed
	 */
	public boolean removeAchievementList(SteamProfile friend) {
		boolean found = false;
		for (int index = 0; !found && index < achievementsLists.size(); index++) {
			SteamAchievementsList steamAchievementsList = achievementsLists.get(index);
			if (steamAchievementsList.getPlayerSteamID().equalsIgnoreCase(friend.getSteamID64()) || steamAchievementsList.getPlayerSteamID().equalsIgnoreCase(friend.getSteamID())) {
				achievementsLists.remove(index);
				fireTableStructureChanged();
				found = true;
			}
		}
		return found;
	}
	
	/**
	 * Clear achievementsLists
	 */
	public void clear() {
		achievementsLists.clear();
	}
	
	public void reverseSort() {
		if (steamAchievementsComparator != null) {
			SteamAchievementsSortMethod achievementsSortMethod = steamAchievementsComparator.getSteamAchievementsSortMethod();
			if (achievementsSortMethod.equals(SteamAchievementsSortMethod.InitialAscendingOrder)) return;
			if (achievementsSortMethod.equals(SteamAchievementsSortMethod.NameAscendingOrder))
				steamAchievementsComparator.setSteamAchievementsSortMethod(SteamAchievementsSortMethod.NameDescendingOrder);
			else if (achievementsSortMethod.equals(SteamAchievementsSortMethod.NameDescendingOrder))
				steamAchievementsComparator.setSteamAchievementsSortMethod(SteamAchievementsSortMethod.NameAscendingOrder);
			else if (achievementsSortMethod.equals(SteamAchievementsSortMethod.UnlockDateAscendingOrder))
				steamAchievementsComparator.setSteamAchievementsSortMethod(SteamAchievementsSortMethod.UnlockDateDescendingOrder);
			else
				steamAchievementsComparator.setSteamAchievementsSortMethod(SteamAchievementsSortMethod.UnlockDateAscendingOrder);
			steamAchievementsComparator.setAscending(!steamAchievementsComparator.isAscending());
			sort();
		}
	}

	/**
	 * Sort achievements of each achievementsList
	 */
	public void sort() {
		if (steamAchievementsComparator != null) {
			Iterator<SteamAchievementsList> achievementsListIterator = achievementsLists.iterator();
			while (achievementsListIterator.hasNext())
				Collections.sort(achievementsListIterator.next().getSteamAchievements(), steamAchievementsComparator);
			fireTableChanged(new TableModelEvent(this));
		}
	}
	
	/**
	 * Sort achievementsLists 
	 * Used before reordering table columns
	 * @param achievementsListSortMethod
	 * @return achievementsLists in new order for reassigning columns
	 */
	public List<SteamAchievementsList> sortColumns() {
		List<SteamAchievementsList> list = null;
		if (steamAchievementsListComparator != null) {
			Enumeration<SteamAchievementsList> enumeration = achievementsLists.elements();
			list = new Vector<SteamAchievementsList>();
			while (enumeration.hasMoreElements())
				list.add(enumeration.nextElement());
			Collections.sort(list, steamAchievementsListComparator);
		}
		return list;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return achievementsLists != null && !achievementsLists.isEmpty() ? (achievementsLists.get(0) != null ? achievementsLists.get(0).getSteamAchievements().size() : 0) : 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return achievementsLists != null ? achievementsLists.size() : 0;
	}

	/*
	 * / (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<? extends Object> getColumnClass(int c) {
		return SteamAchievement.class;
	}

	/*
	 * / (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int columnIndex) {
		SteamAchievementsList achievementsList = getAchievementListAt(columnIndex);
		return achievementsList != null ? achievementsList.getPlayerSteamID() : "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return getAchievementAt(columnIndex, rowIndex);
	}

	/*
	 * / (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}

	/*
	 * / (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object,
	 * int, int)
	 */
	@Override
	public void setValueAt(Object value, int row, int col) {}

}
