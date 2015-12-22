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
package components.containers.remotes;

import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolTip;

import commons.GamesLibrarianIcons;
import commons.api.SteamProfile;
import commons.enums.OnlineState;
import components.GamesLibrarian;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.commons.BundleManager;
import components.commons.ColoredTee;
import components.commons.adapters.SteamObjectsMouseAdapter;
import components.commons.adapters.SteamProfileMouseAdapter;
import components.containers.commons.RemoteIconButton;
import components.tooltips.JScrollableToolTip;
import components.workers.RemoteIconReader;

/**
 * @author Naeregwen
 *
 */
public class SteamFriendButton extends JButton implements RemoteIconButton {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 707601786981108394L;
	
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
		setIcon(GamesLibrarianIcons.noAvatarIcon); // WindowBuilder
		addMouseListener(new SteamObjectsMouseAdapter());
		addMouseListener(new SteamProfileMouseAdapter(me));
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
		setIcon(GamesLibrarianIcons.noAvatarIcon);
		if (steamProfile.getAvatarIcon() != null) 
			(new RemoteIconReader(tee, this)).execute();
		// Set tooltip
		setToolTipText(steamProfile.getTooltipText());
		// Set format
		setIconTextGap(getIconTextGap() + 10);
		setHorizontalAlignment(LEFT);
		// Set listeners
		addMouseListener(new SteamObjectsMouseAdapter());
		addMouseListener(new SteamProfileMouseAdapter(me));
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
	
}
