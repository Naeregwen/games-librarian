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
import commons.api.SteamProfile;
import commons.enums.OnlineState;
import commons.enums.ProfileTab;
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
public class SteamFriendButton extends JButton implements RemoteIconButton, MouseListener {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 707601786981108394L;
	
    private final int defaultDismissDelay = ToolTipManager.sharedInstance().getDismissDelay();
    private final int currentDismissDelay = (int) TimeUnit.MINUTES.toMillis(10); // 10 minutes
    JScrollableToolTip tooltip;

	// Controller
	Librarian librarian;
	// Friend profile
	private SteamProfile steamProfile;
	
	/**
	 * Create a default SteamFriendButton (WindowBuilder)
	 */
	public SteamFriendButton(WindowBuilderMask me, String key) {
		super(BundleManager.getUITexts(me, key));
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		setIcon(GamesLibrary.noAvatarIcon); // WindowBuilder
	}
	
	/**
	 * Create a SteamFriendButton (Runtime)
	 * 
	 * @param tee
	 * @param application
	 * @param steamProfile
	 */
	public SteamFriendButton(ColoredTee tee, WindowBuilderMask me, SteamProfile steamProfile) {
		super("");
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		this.steamProfile = steamProfile;
		// Set text
		String steamId = steamProfile.getSteamID() != null && !steamProfile.getSteamID().trim().equals("") ? steamProfile.getSteamID() : steamProfile.getSteamID64();
		String onlineState = steamProfile.getOnlineState() != null && !steamProfile.getOnlineState().trim().equals("") ? steamProfile.getOnlineState() : "&nbsp;";
		String stateMessage = steamProfile.getStateMessage() != null && !steamProfile.getStateMessage().trim().equals("") ? steamProfile.getStateMessage() : "&nbsp;";
		boolean isInGame = steamProfile.getOnlineState() != null && steamProfile.getOnlineState().trim().equalsIgnoreCase(OnlineState.INGAME.getSteamLabel());
		String privacyStateIcon = "<img src='" + steamProfile.getPrivacyStateIconURL() + "'>";
		String onlineStateIcon = "<img src='" + steamProfile.getOnlineStateIconURL() + "'>";
		setText("<html>" +
				"<style type='text/css'>"+
				".steamId { font-weight: bold; font-size: medium }" +
				".statusIcon { margin-right: 3px }" +
				"</style>"+
				"<div class='steamId'>" + 
				"<table border='0' cellpadding='0' cellspacing='0'>" +
				"<tr>" +
				"<td class='statusIcon'>" + privacyStateIcon + "</td>" +
				"<td>" + steamId + "</td>" +
				"</tr>" +
				"</table>" +
				"</div>" +
				"<div>" +
				"<table border='0' cellpadding='0' cellspacing='0'>" +
				"<tr>" +
				"<td class='statusIcon'>" + onlineStateIcon + "</td>" +
				"<td>" + onlineState + "</td>" +
				"</tr>" +
				"</table>" +
				"</div>" + stateMessage + (isInGame ? "" : "<div>&nbsp;</div>") +
				"</html>");
		// Set icon
		setIcon(GamesLibrary.noAvatarIcon);
		if (steamProfile.getAvatarIcon() != null) 
			(new RemoteIconReader(tee, this)).execute();
		// Set tooltip
		setToolTipText(steamProfile.getTooltipText());
		// Set format
		setIconTextGap(getIconTextGap() + 10);
		setHorizontalAlignment(LEFT);
		// Set listeners
		addMouseListener(this);
	}

	/**
	 * @return the steamProfile
	 */
	public SteamProfile getSteamProfile() {
		return steamProfile;
	}

	/*/
	 * (non-Javadoc)
	 * @see components.containers.commons.RemoteIconButton#getIconURL()
	 */
	@Override
	public String getIconURL() {
		return steamProfile.getAvatarIcon();
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
		return ((SteamFriendButton) event.getSource()).getSteamProfile().getTooltipText();
	}
	
	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
		if ((mouseEvent.getClickCount() == 1 || mouseEvent.getClickCount() == 2) && steamProfile != null)
			librarian.updateProfileTab(steamProfile);
        if (mouseEvent.getClickCount() == 2)
        	librarian.displaySubTab(ProfileTab.Summary);
	}

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