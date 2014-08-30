package components.containers.remotes;

import javax.swing.ImageIcon;

import commons.GamesLibrary;
import commons.api.Parameters;
import commons.api.Steam;
import commons.api.SteamAchievement;
import components.GamesLibrarian;
import components.Librarian;
import components.containers.BoundedButton;
import components.containers.commons.RemoteIconButton;
import components.workers.RemoteIconReader;

public class SteamAchievementButton extends BoundedButton implements RemoteIconButton {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6147355066559977453L;

	Librarian librarian;
	SteamAchievement steamAchievement;
	
	public SteamAchievementButton(Librarian librarian, String label, SteamAchievement steamAchievement) {
		super(label, Steam.steamAchievementIconWidth, Steam.steamAchievementIconHeight);
		this.librarian = librarian;
		setAchievement(steamAchievement);
	}

	/**
	 * @param steamAchievement the steamAchievement to set
	 */
	public void setAchievement(SteamAchievement steamAchievement) {
		
		this.steamAchievement = steamAchievement;
		
		Parameters parameters = librarian.getParameters();
		
		String message;
		
		if (steamAchievement != null) {
			// New Achievement, set button properties
			librarian.getGameNameTextField().setText(steamAchievement.getName());
			// Check game image availability
			if (getIconURL() != null && !getIconURL().trim().equals("")) {
				(new RemoteIconReader(librarian.getTee(), this)).execute();
			} else {
				// Button image will be set with empty image in translate
				message = String.format(parameters.getMessages().getString("achievementImageUnavailable"), steamAchievement.getName());
				librarian.getTee().writelnError(message);
			}
		} else {
			// Reset button properties
			librarian.getGameNameTextField().setText("");
			// Clear image
			updateIcon();
		}
	}

	/**
	 * @return the steamAchievement
	 */
	public SteamAchievement getSteamAchievement() {
		return steamAchievement;
	}

	/**
	 * Set icon to "invalidAchievement"
	 */
	private void setIconItemUnavailable() {
		ImageIcon icon = GamesLibrary.invalidAchievementIcon;
		if (icon != null && icon.getImage() != null && icon.getIconWidth() > 0 && icon.getIconHeight() > 0)
			super.setIcon(icon);
	}
	
	/**
	 * Set icon to "achievementImageUnavailable"
	 */
	private void setIconImageItemUnavailable() {
		ImageIcon icon = GamesLibrary.achievementImageUnavailableIcon;
		if (icon != null && icon.getImage() != null && icon.getIconWidth() > 0 && icon.getIconHeight() > 0)
			super.setIcon(icon);
	}

	/**
	 * Update Icon
	 * Translate UI elements
	 */
	public void updateIcon() {
		if (steamAchievement == null)
			setIconItemUnavailable();
		else
			if (((ImageIcon)getIcon()).getImage() == null)
				setIconImageItemUnavailable();
	}

	/*/
	 * (non-Javadoc)
	 * @see components.containers.commons.RemoteIconButton#getIconURL()
	 */
	@Override
	public String getIconURL() {
		return getSteamAchievement().getIconURL();
	}
	
	/*/
	 * (non-Javadoc)
	 * @see components.containers.commons.RemoteIconButton#setIcon(javax.swing.ImageIcon)
	 */
	@Override
	public void setIcon(ImageIcon icon) {
		resizeAndSetIcon(icon);
	}
	
	/*/
	 * (non-Javadoc)
	 * @see components.containers.commons.RemoteIconButton#setAvatarIconAsResource()
	 */
	@Override
	public void setAvatarIconAsResource() {
		ImageIcon icon = new ImageIcon(GamesLibrarian.class.getResource(getIconURL()));
		if (icon != null && icon.getImage() != null && icon.getIconHeight() > 0 && icon.getIconWidth() > 0)
			resizeAndSetIcon(icon);
	}
	
}
