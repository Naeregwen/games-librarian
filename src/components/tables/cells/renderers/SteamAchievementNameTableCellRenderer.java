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

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import commons.api.SteamAchievement;

/**
 * @author Naeregwen
 *
 */
public class SteamAchievementNameTableCellRenderer extends DefaultTableCellRenderer {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6454186218853668906L;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, "", isSelected, hasFocus, row, column);
        setVerticalAlignment(JLabel.CENTER);
        setHorizontalAlignment(JLabel.LEFT);
        SteamAchievement steamAchievement = (SteamAchievement) value;
        if (steamAchievement != null)
        	setText("<html><div style='font-size: 1.1em; font-weight: bold'>"+steamAchievement.getName()+"</div><br/><div style='font-size: 1em'>"+steamAchievement.getDescription()+"</div></html>");
        return this;
   }
}
