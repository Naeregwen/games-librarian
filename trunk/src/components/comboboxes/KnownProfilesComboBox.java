/**
 * 
 */
package components.comboboxes;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.TimeUnit;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JToolTip;
import javax.swing.ListCellRenderer;
import javax.swing.ToolTipManager;

import commons.api.SteamProfile;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.comboboxes.commons.SortedComboBoxModel;
import components.comboboxes.renderers.KnownProfilesComboBoxRenderer;
import components.commons.JScrollableToolTip;

/**
 * @author Naeregwen
 *
 */
public class KnownProfilesComboBox extends JComboBox<SteamProfile> implements ItemListener, MouseListener {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4849948849703234395L;

    private final int defaultDismissDelay = ToolTipManager.sharedInstance().getDismissDelay();
    private final int currentDismissDelay = (int) TimeUnit.MINUTES.toMillis(10); // 10 minutes
    JScrollableToolTip tooltip;

	Librarian librarian;
	
	@SuppressWarnings("unchecked")
	public KnownProfilesComboBox(WindowBuilderMask me) {
		super((ComboBoxModel<SteamProfile>)new SortedComboBoxModel<SteamProfile>());
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		setRenderer(new KnownProfilesComboBoxRenderer((ListCellRenderer<SteamProfile>) this.getRenderer()));
		// Set tooltip
		if (getSelectedItem() != null)
			setToolTipText(((SteamProfile)getSelectedItem()).getTooltipText());
		addItemListener(this);
		addMouseListener(this);
	}
	
	public void addProfile(SteamProfile item) {
		for (int index = 0; index < getItemCount(); index++) {
			SteamProfile steamProfile = getItemAt(index);
			if ((item.getSteamID64() != null && steamProfile.getSteamID64() != null && item.getSteamID64().equalsIgnoreCase(steamProfile.getSteamID64()))
					|| (item.getSteamID() != null && steamProfile.getSteamID() != null && item.getSteamID().equalsIgnoreCase(steamProfile.getSteamID()))) {
				removeItem(steamProfile);
				break;
			}
		}
		addItem(item);
	}

	/*
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
	 * @see javax.swing.JComponent#getToolTipText(java.awt.event.MouseEvent)
	 */
	@Override
	public String getToolTipText(MouseEvent event) {
		return ((SteamProfile)((KnownProfilesComboBox) event.getSource()).getSelectedItem()).getTooltipText();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(ItemEvent itemEvent) {
		if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
			librarian.updateProfileTab((SteamProfile) itemEvent.getItem());
			setToolTipText(((SteamProfile)getSelectedItem()).getTooltipText());
		}
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
	
}
