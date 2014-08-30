/**
 * 
 */
package components.containers.remotes;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolTip;
import javax.swing.ToolTipManager;

import commons.BundleManager;
import commons.ColoredTee;
import commons.GamesLibrary;
import commons.api.SteamGroup;
import components.GamesLibrarian;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.commons.JScrollableToolTip;
import components.containers.commons.RemoteIconButton;
import components.workers.RemoteIconReader;

/**
 * @author Naeregwen
 *
 */
public class SteamGroupButton extends JButton implements RemoteIconButton, MouseListener {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7750421251529628291L;

    private final int defaultDismissDelay = ToolTipManager.sharedInstance().getDismissDelay();
    private final int currentDismissDelay = (int) TimeUnit.MINUTES.toMillis(10); // 10 minutes
    JScrollableToolTip tooltip;
    
	Librarian librarian;
	SteamGroup steamGroup;
	
	/**
	 * Create a default SteamGroupButton (WindowBuilder)
	 * @param label
	 */
	public SteamGroupButton(WindowBuilderMask me, String key) {
		super(BundleManager.getUITexts(me, key));
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		setIcon(GamesLibrary.noAvatarIcon); // WindowBuilder
	}
	
	/**
	 * Create a SteamGroupButton (Runtime)
	 * 
	 * @param application
	 * @param steamGroup
	 */
	public SteamGroupButton(ColoredTee tee, SteamGroup steamGroup) {
		super("");
		this.steamGroup = steamGroup;
		// Set text
		String groupName = "<br/>";
		String groupHeadline = "<br/>";
		if (steamGroup != null) {
			groupName = steamGroup.getGroupName() != null ? steamGroup.getGroupName() : steamGroup.getGroupID64();
			groupHeadline = steamGroup.getHeadline() != null ? steamGroup.getHeadline() : "<br/>";
		}
		setText("<html>"+groupName+"<br/>"+groupHeadline+"</html>");
		// Set icon
		setIcon(GamesLibrary.noAvatarIcon);
		if (steamGroup != null && steamGroup.getAvatarIcon() != null)
			(new RemoteIconReader(tee, this)).execute();
		// Set tooltip
		setToolTipText(steamGroup.getTooltipText());
		// Set format
		setHorizontalAlignment(LEFT);
		// Set listeners
		addMouseListener(this);
	}

	/**
	 * @return the steamGroup
	 */
	public SteamGroup getSteamGroup() {
		return steamGroup;
	}
	
	/*/
	 * (non-Javadoc)
	 * @see components.containers.commons.RemoteIconButton#getIconURL()
	 */
	@Override
	public String getIconURL() {
		return steamGroup.getAvatarIcon();
	}

	/*/
	 * (non-Javadoc)
	 * @see components.containers.commons.RemoteIconButton#setAvatarIconAsResource()
	 */
	@Override
	public void setAvatarIconAsResource() {
		if (getIconURL() == null || GamesLibrarian.class.getResource(getIconURL()) == null) return;
		ImageIcon icon = new ImageIcon(GamesLibrarian.class.getResource(getIconURL()));
		if (icon != null && icon.getImage() != null && icon.getIconHeight() > 0 && icon.getIconWidth() > 0)
			super.setIcon(icon);
	}

	/*/
	 * (non-Javadoc)
	 * @see components.containers.commons.RemoteIconButton#setIcon(javax.swing.ImageIcon)
	 */
	@Override
	public void setIcon(ImageIcon icon) {
		super.setIcon(icon);
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
		return ((SteamGroupButton) event.getSource()).getSteamGroup().getTooltipText();
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
