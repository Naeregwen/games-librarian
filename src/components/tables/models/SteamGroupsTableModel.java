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

import java.util.List;

import javax.swing.table.AbstractTableModel;

import commons.api.SteamGroup;
import commons.api.SteamGroup.ColumnsOrder;

/**
 * @author Naeregwen
 *
 */
public class SteamGroupsTableModel extends AbstractTableModel {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7669292064902599734L;
	
	private List<SteamGroup> groupsList;
	private String[] columnNames;
	
	public SteamGroupsTableModel(List<SteamGroup> groups) {
		this.groupsList = groups;
		columnNames = new String[ColumnsOrder.values().length];
		for (int i = 0; i < ColumnsOrder.values().length; i++) 
			columnNames[i] = ColumnsOrder.values()[i].name();
	}

	public List<SteamGroup> getGroups() {
		return this.groupsList;
	}
	
	/**
	 * Return SteamGroup corresponding to rowIndex
	 * @param rowIndex
	 * @return
	 */
	public SteamGroup getGroupAtRow(int rowIndex) {
		return groupsList != null ? groupsList.get(rowIndex) : null;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return groupsList != null ? groupsList.size() : 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return columnNames != null ? columnNames.length : 0;
	}

	/*
	 * / (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<? extends Object> getColumnClass(int c) {
		switch(ColumnsOrder.values()[c]) {
		case logo:
		case name:
		case groupID64:
		case summary:
		case headline:
			return String.class;
		}
		return null;
	}

	/*
	 * / (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int columnIndex) {
		switch(ColumnsOrder.values()[columnIndex]) {
		case logo: return ColumnsOrder.logo.name();
		case name: return ColumnsOrder.name.name();
		case groupID64:  return ColumnsOrder.groupID64.name();
		case summary: return ColumnsOrder.summary.name();
		case headline: return ColumnsOrder.headline.name();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (groupsList == null || rowIndex >= groupsList.size() || columnIndex >= ColumnsOrder.values().length) return null;
		SteamGroup group = groupsList.get(rowIndex);
		switch(ColumnsOrder.values()[columnIndex]) {
		case logo:
			return group.getAvatarIcon();
		case name:
			return group.getGroupName();
		case groupID64:
			return group.getGroupID64();
		case summary:
			return group.getSummary();
		case headline:
			return group.getHeadline();
		}
		return null;
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
