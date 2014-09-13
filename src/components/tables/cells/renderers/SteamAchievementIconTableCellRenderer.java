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
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import commons.api.Parameters;
import commons.api.SteamAchievement;

/**
 * @author Naeregwen
 *
 */
public class SteamAchievementIconTableCellRenderer extends DefaultTableCellRenderer {

	private Parameters parameters;
	
	public SteamAchievementIconTableCellRenderer(Parameters parameters) {
		this.parameters = parameters;
	}
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6584273431686752376L;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    	
        super.getTableCellRendererComponent(table, "", isSelected, hasFocus, row, column);
		setHorizontalAlignment(LEFT);
        setVerticalAlignment(TOP);
        setVerticalTextPosition(JLabel.TOP);
        SteamAchievement steamAchievement = (SteamAchievement) value;
        if (steamAchievement != null) {
        	try {
        		setIcon(new ImageIcon(new URL(steamAchievement.getIconURL())));
        	} catch (MalformedURLException e) {
        		e.printStackTrace();
        	}
        	if (steamAchievement.getUnlockTimestamp() != null && !steamAchievement.getUnlockTimestamp().trim().equals(""))
        		setText("<html><div style='font-size: 1.1em; font-weight: bold'>"+steamAchievement.getName()+"</div><div style='font-size: 1em'>"+steamAchievement.getDescription()+"</div>"
        				+ String.format(parameters.getUITexts().getString("unlockedAchievement"), 
        				(new SimpleDateFormat(parameters.getResources().getString("simpleDateFormat")))
        				.format(new Date(SteamAchievement.steamStampToEpochStamp((steamAchievement.getUnlockTimestamp())))))
        				+ "</html>");
        	else
        		setText("<html><div style='font-size: 1.1em; font-weight: bold'>"+steamAchievement.getName()+"</div><div style='font-size: 1em'>"+steamAchievement.getDescription()+"</div>" 
        				+ parameters.getUITexts().getString("lockedAchievement")
        				+ "</html>");
        }
        return this;
   }
}
