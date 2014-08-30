/**
 * 
 */
package components.containers.remotes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

import commons.GamesLibrary;
import commons.api.SteamProfile;
import components.GamesLibrarian;
import components.Librarian;
import components.containers.commons.RemoteIconButton;
import components.workers.RemoteIconReader;

/**
 * @author Naeregwen
 *
 */
public class SteamFriendWithSameGameButton extends JToggleButton implements RemoteIconButton, ActionListener {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8555219080720123957L;

	Librarian librarian;
	
	private SteamProfile steamProfile;
	private Integer initialPosition;
	private boolean hasStats;
	
	/**
	 * Create a default SteamFriendWithSameGameButton (WindowBuilder)
	 */
	public SteamFriendWithSameGameButton(String label) {
		super(label);
		setIcon(GamesLibrary.noAvatarIcon);
	}

	/**
	 * Create a SteamFriendWithSameGameButton (Runtime)
	 * 
	 * @param tee
	 * @param application
	 * @param steamProfile
	 * @param initialPosition
	 * @param hasStats
	 */
	public SteamFriendWithSameGameButton(Librarian librarian, SteamProfile steamProfile, Integer initialPosition, boolean hasStats) {
		super();
		this.librarian = librarian;
		this.steamProfile = steamProfile;
		this.initialPosition = initialPosition;
		this.hasStats = hasStats;
		// Set text
		String steamId = steamProfile.getSteamID() != null && !steamProfile.getSteamID().trim().equals("") ? steamProfile.getSteamID() : steamProfile.getSteamID64();
		String onlineState = steamProfile.getOnlineState() != null && !steamProfile.getOnlineState().trim().equals("") ? steamProfile.getOnlineState() : "?";
		setText("<html>" + steamId + "<br/>" + onlineState + "</html>");
		// Set icon
		setIcon(GamesLibrary.noAvatarIcon);
		if (steamProfile.getAvatarIcon() != null)
			(new RemoteIconReader(librarian.getTee(), this)).execute();
		// Set format
		setHorizontalAlignment(LEFT);
		// Set listeners
		addActionListener(this);
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
	 * @see components.containers.commons.RemoteIconButton#setIconAsResource()
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

	/*/
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		if (steamProfile == null || !hasStats) return;
		if (isSelected()) {
			librarian.readSteamFriendSteamGameStats(steamProfile, initialPosition, this.getIcon());
		} else {
			librarian.removeFriendAchievements(steamProfile);
		}
		librarian.getView().translateLoadAllAchievements();
	}

}
