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

import javax.swing.table.DefaultTableModel;

import commons.api.SteamFriend;
import commons.api.SteamProfile;

/**
 * @author Naeregwen
 *
 */
public class SteamFriendsTableModel extends DefaultTableModel {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8082445807375429566L;

	private List<SteamProfile> friendsList;
	private String[] columnNames;
	
	public SteamFriendsTableModel(List<SteamProfile> groups) {
		this.friendsList = groups;
		columnNames = new String[SteamFriend.ColumnsOrder.values().length];
		for (int i = 0; i < SteamFriend.ColumnsOrder.values().length; i++) 
			columnNames[i] = SteamFriend.ColumnsOrder.values()[i].name();
	}

	public List<SteamProfile> getFriends() {
		return this.friendsList;
	}
	
	/**
	 * Return SteamProfile corresponding to rowIndex
	 * @param rowIndex
	 * @return
	 */
	public SteamProfile getFriendAtRow(int rowIndex) {
		return friendsList != null ? friendsList.get(rowIndex) : null;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return friendsList != null ? friendsList.size() : 0;
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
		switch(SteamFriend.ColumnsOrder.values()[c]) {
		case logo:
		case name:
		case steamID64:
		case onlineState:
		case stateMessage:
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
		switch(SteamFriend.ColumnsOrder.values()[columnIndex]) {
		case logo: return SteamFriend.ColumnsOrder.logo.name();
		case name: return SteamFriend.ColumnsOrder.name.name();
		case steamID64:  return SteamFriend.ColumnsOrder.steamID64.name();
		case onlineState: return SteamFriend.ColumnsOrder.onlineState.name();
		case stateMessage: return SteamFriend.ColumnsOrder.stateMessage.name();
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
		if (friendsList == null || rowIndex >= friendsList.size() || columnIndex >= SteamFriend.ColumnsOrder.values().length) return null;
		SteamProfile friend = friendsList.get(rowIndex);
		switch(SteamFriend.ColumnsOrder.values()[columnIndex]) {
		case logo:
			return friend.getAvatarIcon();
		case name:
			return friend.getSteamID();
		case onlineState:
			return friend.getOnlineState();
		case stateMessage: 
			return friend.getStateMessage();
		case steamID64:
			return friend.getSteamID64();
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
