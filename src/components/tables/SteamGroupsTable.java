/**
 * 
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
import commons.api.SteamGroup;
import commons.enums.SteamGroupsSortMethod;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.commons.JScrollableToolTip;
import components.tables.cells.renderers.IconTableCellRenderer;
import components.tables.headers.listeners.SteamGroupsHeaderListener;
import components.tables.headers.renderers.SteamGroupsTableHeaderRenderer;
import components.tables.models.SteamGroupsTableModel;

/**
 * @author Naeregwen
 *
 */
public class SteamGroupsTable extends JTable implements MouseListener {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4680353226405356583L;

	WindowBuilderMask me;
	Librarian librarian;
	
    private final int defaultDismissDelay = ToolTipManager.sharedInstance().getDismissDelay();
    private final int currentDismissDelay = (int) TimeUnit.MINUTES.toMillis(10); // 10 minutes
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
		
		// Logo renderer
		TableColumn logoColumn = getColumnModel().getColumn(SteamGroup.ColumnsOrder.logo.ordinal());
		logoColumn.setCellRenderer(new IconTableCellRenderer());
		
		// Selection handlers
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Table listener
		addMouseListener(this);
		
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
