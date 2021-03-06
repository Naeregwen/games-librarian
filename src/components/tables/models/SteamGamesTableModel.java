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

import commons.api.SteamGame;
import commons.api.SteamGame.ColumnsOrder;
import commons.api.SteamLaunchMethod;

/**
 * @author Naeregwen
 *
 */
public class SteamGamesTableModel extends DefaultTableModel {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3557358000882954551L;
	
	private String[] columnNames;
	private List<SteamGame> games;
	
	public SteamGamesTableModel(List<SteamGame> games) {
		this.games = games;
		columnNames = new String[ColumnsOrder.values().length];
		for (int i = 0; i < ColumnsOrder.values().length; i++)
			columnNames[i] = ColumnsOrder.values()[i].name();
	}
	
	public List<SteamGame> getGames() {
		return this.games;
	}
	
	public SteamGame getGameAt(int modelRow) {
		return games.get(modelRow);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return games != null ? games.size() : 0;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return columnNames != null ? columnNames.length : 0;
	}

	/*/
	 * (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<? extends Object> getColumnClass(int c) {
		switch(ColumnsOrder.values()[c]) {
		case logo:
		case name:
		case arguments:
			return String.class;
		case steamLaunchMethod:
			return SteamLaunchMethod.class;
		case hoursLast2Weeks:
		case hoursOnRecord:
		case steamAchievementsRatio:
			return Double.class;
		case appID:
			return Integer.class;
		case storeLink:
		case globalStatsLink:
		case statsLink:
			return String.class;
		}
		return null;
    }
	
	/*/
	 * (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int columnIndex) {
		switch(ColumnsOrder.values()[columnIndex]) {
		case logo: return ColumnsOrder.logo.name();
		case name: return ColumnsOrder.name.name();
		case arguments:  return ColumnsOrder.arguments.name();
		case steamLaunchMethod: return ColumnsOrder.steamLaunchMethod.name();
		case hoursLast2Weeks:  return ColumnsOrder.hoursLast2Weeks.name();
		case hoursOnRecord: return ColumnsOrder.hoursOnRecord.name();
		case appID: return ColumnsOrder.appID.name();
		case storeLink: return ColumnsOrder.storeLink.name();
		case globalStatsLink: return ColumnsOrder.globalStatsLink.name();
		case statsLink: return ColumnsOrder.statsLink.name();
		case steamAchievementsRatio: return ColumnsOrder.steamAchievementsRatio.name();
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		if (games == null || rowIndex >= games.size() || columnIndex >= ColumnsOrder.values().length) return null;
		SteamGame game = games.get(rowIndex);
		switch(ColumnsOrder.values()[columnIndex]) {
		case logo:
			return game.getLogo();
//			return game.getLogoSmall();
		case name:
			return game.getName();
		case arguments:
			return game.getArguments();
		case steamLaunchMethod:
			return game.getSteamLaunchMethod();
		case hoursLast2Weeks:
			Double hoursLast2Weeks = null;
			try {
				hoursLast2Weeks = Double.parseDouble(game.getHoursLast2Weeks()); 
			} catch (NumberFormatException nfe) {
				hoursLast2Weeks = 0.0;
			}
			return hoursLast2Weeks;
		case hoursOnRecord:
			Double hoursOnRecord = null;
			try {
				hoursOnRecord = Double.parseDouble(game.getHoursOnRecord()); 
			} catch (NumberFormatException nfe) {
				hoursOnRecord = 0.0;
			}
			return hoursOnRecord;
		case appID:
			Integer appID = null;
			try {
				appID = Integer.parseInt(game.getAppID()); 
			} catch (NumberFormatException nfe) {
				appID = 0;
			}
			return appID;
		case storeLink:
			return game.getStoreLink();
		case globalStatsLink:
			return game.getGlobalStatsLink();
		case statsLink:
			return game.getStatsLink();
		case steamAchievementsRatio:
			return game.getAchievementsRatio();
		}
		return null;
	}
	
	/*/
	 * (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int row, int col) {
		return 
				col == ColumnsOrder.arguments.ordinal() 
				|| col == ColumnsOrder.steamLaunchMethod.ordinal()
				;
	}
	
	/*/
	 * (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int, int)
	 */
	@Override
	public void setValueAt(Object value, int row, int col) {
		if (games == null || row >= games.size() || col >= ColumnsOrder.values().length) return;
		SteamGame game = games.get(row);
		boolean hasNewValue = false;
		switch(ColumnsOrder.values()[col]) {
		case logo:
			game.setLogo((String) value);
//			game.setLogoSmall((String) value);
			hasNewValue = true;
			break;
		case name:
			game.setName((String) value);
			hasNewValue = true;
			break;
		case arguments:
			game.setArguments((String) value);
			hasNewValue = true;
			break;
		case steamLaunchMethod:
			game.setSteamLaunchMethod((SteamLaunchMethod) value);
			hasNewValue = true;
			break;
		case hoursLast2Weeks:
			game.setHoursLast2Weeks((String) value);
			hasNewValue = true;
			break;
		case hoursOnRecord:
			game.setHoursOnRecord((String) value);
			hasNewValue = true;
			break;
		case appID:
			game.setAppID((String) value);
			hasNewValue = true;
			break;
		case storeLink:
			game.setStoreLink((String) value);
			hasNewValue = true;
			break;
		case globalStatsLink:
			game.setGlobalStatsLink((String) value);
			hasNewValue = true;
			break;
		case statsLink:
			game.setStatsLink((String) value);
			hasNewValue = true;
			break;
		case steamAchievementsRatio:
			game.setAchievementsRatio((Double) value);
			hasNewValue = true;
			break;
		}
		if (hasNewValue)
			fireTableCellUpdated(row, col);
	}

}
