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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JTable;
import javax.swing.JToolTip;
import javax.swing.ListSelectionModel;
import javax.swing.SortOrder;
import javax.swing.ToolTipManager;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import commons.api.Steam;
import commons.api.SteamFriend;
import commons.api.SteamProfile;
import commons.enums.SteamFriendsSortMethod;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.commons.JScrollableToolTip;
import components.tables.cells.renderers.IconTableCellRenderer;
import components.tables.headers.listeners.SteamFriendsHeaderListener;
import components.tables.headers.renderers.SteamFriendsTableHeaderRenderer;
import components.tables.models.SteamFriendsTableModel;

public class SteamFriendsTable extends JTable implements MouseListener {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1061790093680415329L;

	WindowBuilderMask me;
	Librarian librarian;
	
    private final int defaultDismissDelay = ToolTipManager.sharedInstance().getDismissDelay();
    private final int currentDismissDelay = (int) TimeUnit.MINUTES.toMillis(10); // 10 minutes
	JScrollableToolTip tooltip;
	
	public SteamFriendsTable(WindowBuilderMask me, List<SteamProfile> friends) {
		
		super();
		this.me = me;
		this.librarian = me.getLibrarian();
		
		// Model
		setModel(new SteamFriendsTableModel(friends));
		
		// Sorting
		setAutoCreateRowSorter(true);
		
		// Table header renderer
		getTableHeader().setDefaultRenderer(new SteamFriendsTableHeaderRenderer());
		
        // Table header listener
		JTableHeader tableHeader = getTableHeader();
		tableHeader.addMouseListener(new SteamFriendsHeaderListener(librarian, tableHeader));
		
		// Logo renderer
		TableColumn logoColumn = getColumnModel().getColumn(SteamFriend.ColumnsOrder.logo.ordinal());
		logoColumn.setCellRenderer(new IconTableCellRenderer());
		
		// Selection handlers
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
        // Table listener
		addMouseListener(this);

		// Layout & display
		setLayout(new GridLayout(1, 0, 0, 0));
		setRowHeight(Steam.steamFriendIconHeight);
		setFillsViewportHeight(true);		
		
	}
	
	public List<SteamProfile> getFriends() {
		return ((SteamFriendsTableModel) getModel()).getFriends();
	}

	/*/
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#createToolTip()
	 */
	@Override
	public JToolTip createToolTip() {
		tooltip = new JScrollableToolTip(getGraphicsConfiguration().getDevice().getDisplayMode().getWidth()/2, getGraphicsConfiguration().getDevice().getDisplayMode().getHeight()/2);
		return tooltip;
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.JTable#getToolTipText(java.awt.event.MouseEvent)
	 */
	@Override
	public String getToolTipText(MouseEvent event) {
		int row = rowAtPoint(event.getPoint());
		if (row == -1) return null;
		return ((SteamFriendsTableModel) getModel()).getFriendAtRow(convertRowIndexToModel(row)).getTooltipText();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		ToolTipManager.sharedInstance().setDismissDelay(currentDismissDelay);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		ToolTipManager.sharedInstance().setDismissDelay(defaultDismissDelay);
	}
	
	public void sorter() {
		if (getRowSorter().getSortKeys().size() > 0) {
			
			getRowSorter().toggleSortOrder(getRowSorter().getSortKeys().get(0).getColumn());
			String columnName = getModel().getColumnName(getRowSorter().getSortKeys().get(0).getColumn());
			SortOrder sortOrder = getRowSorter().getSortKeys().get(0).getSortOrder();
			SteamFriend.ColumnsOrder columnOrder = SteamFriend.ColumnsOrder.valueOf(columnName);
				
			if (columnOrder != null) {
				Integer steamFriendsSortMethodIndex = null;
				SteamFriendsSortMethod steamFriendsSortMethod = null;
				switch (columnOrder) {
				case logo:
					steamFriendsSortMethodIndex = sortOrder.equals(SortOrder.ASCENDING) ? SteamFriendsSortMethod.LogoDescendingOrder.ordinal() : SteamFriendsSortMethod.LogoAscendingOrder.ordinal();
					steamFriendsSortMethod = sortOrder.equals(SortOrder.ASCENDING) ? SteamFriendsSortMethod.LogoDescendingOrder : SteamFriendsSortMethod.LogoAscendingOrder;
					break;
				case name:
					steamFriendsSortMethodIndex = sortOrder.equals(SortOrder.ASCENDING) ? SteamFriendsSortMethod.NameDescendingOrder.ordinal() : SteamFriendsSortMethod.NameAscendingOrder.ordinal();
					steamFriendsSortMethod = sortOrder.equals(SortOrder.ASCENDING) ? SteamFriendsSortMethod.NameDescendingOrder : SteamFriendsSortMethod.NameAscendingOrder;
					break;
				case onlineState:
					steamFriendsSortMethodIndex = sortOrder.equals(SortOrder.ASCENDING) ? SteamFriendsSortMethod.OnlineStateDescendingOrder.ordinal() : SteamFriendsSortMethod.OnlineStateAscendingOrder.ordinal();
					steamFriendsSortMethod = sortOrder.equals(SortOrder.ASCENDING) ? SteamFriendsSortMethod.OnlineStateDescendingOrder : SteamFriendsSortMethod.OnlineStateAscendingOrder;
					break;
				case stateMessage:
					steamFriendsSortMethodIndex = sortOrder.equals(SortOrder.ASCENDING) ? SteamFriendsSortMethod.StateMessageDescendingOrder.ordinal() : SteamFriendsSortMethod.StateMessageAscendingOrder.ordinal();
					steamFriendsSortMethod = sortOrder.equals(SortOrder.ASCENDING) ? SteamFriendsSortMethod.StateMessageDescendingOrder : SteamFriendsSortMethod.StateMessageAscendingOrder;
					break;
				case steamID64:
					steamFriendsSortMethodIndex = sortOrder.equals(SortOrder.ASCENDING) ? SteamFriendsSortMethod.SteamId64DescendingOrder.ordinal() : SteamFriendsSortMethod.SteamId64AscendingOrder.ordinal();
					steamFriendsSortMethod = sortOrder.equals(SortOrder.ASCENDING) ? SteamFriendsSortMethod.SteamId64DescendingOrder : SteamFriendsSortMethod.SteamId64AscendingOrder;
					break;
				}
				
				if (steamFriendsSortMethodIndex != null) {
					if (librarian.getSteamFriendsSortMethodComboBox().getSelectedItem() != steamFriendsSortMethod)
						librarian.getSteamFriendsSortMethodComboBox().setSelectedItem(steamFriendsSortMethod);
				}
				
			}
		}
	}
}
