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
import commons.api.SteamGroup;
import components.GamesLibrarian;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.commons.BundleManager;
import components.commons.ColoredTee;
import components.commons.adapters.SteamObjectsMouseAdapter;
import components.containers.commons.RemoteIconButton;
import components.tooltips.JScrollableToolTip;
import components.workers.RemoteIconReader;

/**
 * @author Naeregwen
 *
 */
public class SteamGroupButton extends JButton implements RemoteIconButton {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7750421251529628291L;

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
		setIcon(GamesLibrarianIcons.noAvatarIcon); // WindowBuilder
		addMouseListener(new SteamObjectsMouseAdapter());
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
		setIcon(GamesLibrarianIcons.noAvatarIcon);
		if (steamGroup != null && steamGroup.getAvatarIcon() != null)
			(new RemoteIconReader(tee, this)).execute();
		// Set tooltip
		setToolTipText(steamGroup.getTooltipText());
		// Set format
		setHorizontalAlignment(LEFT);
		// Set listeners
		addMouseListener(new SteamObjectsMouseAdapter());
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
	
}
