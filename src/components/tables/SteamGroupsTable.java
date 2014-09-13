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
import java.util.List;

import javax.swing.JTable;
import javax.swing.JToolTip;
import javax.swing.ListSelectionModel;
import javax.swing.SortOrder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import commons.api.Steam;
import commons.api.SteamGroup;
import commons.enums.SteamGroupsSortMethod;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.commons.JScrollableToolTip;
import components.commons.adapters.SteamObjectsMouseAdapter;
import components.tables.cells.renderers.IconTableCellRenderer;
import components.tables.headers.listeners.SteamGroupsHeaderListener;
import components.tables.headers.renderers.SteamGroupsTableHeaderRenderer;
import components.tables.models.SteamGroupsTableModel;

/**
 * @author Naeregwen
 *
 */
public class SteamGroupsTable extends JTable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4680353226405356583L;

	WindowBuilderMask me;
	Librarian librarian;
	
	JScrollableToolTip tooltip;
	
	public SteamGroupsTable(WindowBuilderMask me, List<SteamGroup> groups) {
		
		super();
		this.me = me;
		this.librarian = me.getLibrarian();
		
		// Model
		setModel(new SteamGroupsTableModel(groups));
		
        // Sorting
		setAutoCreateRowSorter(true);
		
		// Table header renderer
		JTableHeader tableHeader = getTableHeader();
		tableHeader.setDefaultRenderer(new SteamGroupsTableHeaderRenderer());
		
        // Table header listener
		tableHeader.addMouseListener(new SteamGroupsHeaderListener(librarian, tableHeader));
		
        // Table listener
		addMouseListener(new SteamObjectsMouseAdapter());
		
		// Logo renderer
		TableColumn logoColumn = getColumnModel().getColumn(SteamGroup.ColumnsOrder.logo.ordinal());
		logoColumn.setCellRenderer(new IconTableCellRenderer());
		
		// Selection handlers
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Layout & display
		setLayout(new GridLayout(1, 0, 0, 0));
		setRowHeight(Steam.steamGroupIconHeight);
		setFillsViewportHeight(true);	
		
	}
	
	public List<SteamGroup> getGroups() {
		return ((SteamGroupsTableModel) getModel()).getGroups();
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
		return ((SteamGroupsTableModel) getModel()).getGroupAtRow(convertRowIndexToModel(row)).getTooltipText();
	}
	
	public void sorter() {
		if (getRowSorter().getSortKeys().size() > 0) {
			
			getRowSorter().toggleSortOrder(getRowSorter().getSortKeys().get(0).getColumn());
			String columnName = getModel().getColumnName(getRowSorter().getSortKeys().get(0).getColumn());
			SortOrder sortOrder = getRowSorter().getSortKeys().get(0).getSortOrder();
			SteamGroup.ColumnsOrder columnOrder = SteamGroup.ColumnsOrder.valueOf(columnName);
				
			if (columnOrder != null) {
				Integer steamGroupsSortMethodIndex = null;
				SteamGroupsSortMethod steamGroupsSortMethod = null;
				switch (columnOrder) {
				case logo:
					steamGroupsSortMethodIndex = sortOrder.equals(SortOrder.ASCENDING) ? SteamGroupsSortMethod.LogoDescendingOrder.ordinal() : SteamGroupsSortMethod.LogoAscendingOrder.ordinal();
					steamGroupsSortMethod = sortOrder.equals(SortOrder.ASCENDING) ? SteamGroupsSortMethod.LogoDescendingOrder : SteamGroupsSortMethod.LogoAscendingOrder;
					break;
				case name:
					steamGroupsSortMethodIndex = sortOrder.equals(SortOrder.ASCENDING) ? SteamGroupsSortMethod.NameDescendingOrder.ordinal() : SteamGroupsSortMethod.NameAscendingOrder.ordinal();
					steamGroupsSortMethod = sortOrder.equals(SortOrder.ASCENDING) ? SteamGroupsSortMethod.NameDescendingOrder : SteamGroupsSortMethod.NameAscendingOrder;
					break;
				case headline:
					steamGroupsSortMethodIndex = sortOrder.equals(SortOrder.ASCENDING) ? SteamGroupsSortMethod.HeadlineDescendingOrder.ordinal() : SteamGroupsSortMethod.HeadlineAscendingOrder.ordinal();
					steamGroupsSortMethod = sortOrder.equals(SortOrder.ASCENDING) ? SteamGroupsSortMethod.HeadlineDescendingOrder : SteamGroupsSortMethod.HeadlineAscendingOrder;
					break;
				case summary:
					steamGroupsSortMethodIndex = sortOrder.equals(SortOrder.ASCENDING) ? SteamGroupsSortMethod.SummaryDescendingOrder.ordinal() : SteamGroupsSortMethod.SummaryAscendingOrder.ordinal();
					steamGroupsSortMethod = sortOrder.equals(SortOrder.ASCENDING) ? SteamGroupsSortMethod.SummaryDescendingOrder : SteamGroupsSortMethod.SummaryAscendingOrder;
					break;
				case groupID64:
					steamGroupsSortMethodIndex = sortOrder.equals(SortOrder.ASCENDING) ? SteamGroupsSortMethod.SteamId64AscendingOrder.ordinal() : SteamGroupsSortMethod.SteamId64AscendingOrder.ordinal();
					steamGroupsSortMethod = sortOrder.equals(SortOrder.ASCENDING) ? SteamGroupsSortMethod.SteamId64DescendingOrder : SteamGroupsSortMethod.SteamId64AscendingOrder;
					break;
				}
				
				if (steamGroupsSortMethodIndex != null) {
					if (librarian.getSteamGroupsSortMethodComboBox().getSelectedItem() != steamGroupsSortMethod)
						librarian.getSteamGroupsSortMethodComboBox().setSelectedItem(steamGroupsSortMethod);
				}
				
			}
		}
	}
}
