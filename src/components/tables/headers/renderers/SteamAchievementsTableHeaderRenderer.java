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
package components.tables.headers.renderers;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.table.TableCellRenderer;

import commons.GamesLibrarianIcons;
import commons.api.SteamAchievementsList;
import commons.enums.SteamAchievementsSortMethod;
import components.tables.models.SteamAchievementsTableModel;

/**
 * @author Naeregwen
 *
 */
public class SteamAchievementsTableHeaderRenderer implements TableCellRenderer {

    /* (non-Javadoc)
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		
		JPanel avatarPanel = new JPanel();
		avatarPanel.setLayout(new FlowLayout());
		avatarPanel.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		
		JLabel avatarIcon = new JLabel();
        avatarIcon.setHorizontalAlignment(JLabel.CENTER);
        
        SteamAchievementsList achievementsList = ((SteamAchievementsTableModel) table.getModel()).getAchievementListAt(table.convertColumnIndexToModel(column));
        Double achievementsRatio = achievementsList != null ? achievementsList.getAchievementsRatio() * 100 : 0;
        Icon playerAvatarIcon = achievementsList != null ? achievementsList.getPlayerAvatarIcon() : null;
        avatarIcon.setIcon(playerAvatarIcon != null ? playerAvatarIcon : GamesLibrarianIcons.noAvatarIcon);
        SteamAchievementsSortMethod achievementsSortMethod = ((SteamAchievementsTableModel) table.getModel()).getSteamAchievementsComparator().getSteamAchievementsSortMethod();
        BasicArrowButton sortDirectionIndicator = null;
        
        switch (achievementsSortMethod) {
        case InitialAscendingOrder : 
        	sortDirectionIndicator = new BasicArrowButton(BasicArrowButton.EAST);
        	break;
        case NameAscendingOrder : 
        case UnlockDateAscendingOrder : 
        	sortDirectionIndicator = new BasicArrowButton(BasicArrowButton.NORTH);
        	break;
        case NameDescendingOrder : 
        case UnlockDateDescendingOrder : 
        	sortDirectionIndicator = new BasicArrowButton(BasicArrowButton.SOUTH);
        	break;
        }
        sortDirectionIndicator.setBorder(new EmptyBorder(0, 0, 0, 0));
        avatarIcon.setText("<html>" + ((String) value) + "<br/>" + String.format("%4.2f %%", achievementsRatio) + "</html>");
        
        avatarPanel.add(avatarIcon);
        avatarPanel.add(sortDirectionIndicator);
        
        mainPanel.add(avatarPanel);

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(Math.round(achievementsRatio.intValue()));
        progressBar.setStringPainted(true);
        progressBar.setBorder(new LineBorder(Color.BLACK));
        progressBar.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        mainPanel.add(progressBar);
        
        return mainPanel;
	}

}
