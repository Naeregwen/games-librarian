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
package components.tables.cells.renderers;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import components.tables.SteamGamesTable;

/**
 * @author Naeregwen
 *
 */
public class SteamAchievementsRatioTableCellRenderer extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8925121476699710327L;

	private SteamGamesTable steamGamesTable;
	
    /**
	 * @param me
	 */
	public SteamAchievementsRatioTableCellRenderer(SteamGamesTable steamGamesTable) {
		super();
		this.steamGamesTable = steamGamesTable;
	}

	@Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JComponent component = (JComponent) steamGamesTable.getCellRenderer(row, column);
		Double steamAchievementsRatio = (Double) value;
		if (component instanceof JLabel)
	        ((JLabel) component).setText(steamAchievementsRatio != -1.0 ? String.format("%.2f%%", steamAchievementsRatio * 100.0) : "N/A");
        return component;
    }	
}
