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
package components.containers;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToolTip;
import javax.swing.ToolTipManager;

import commons.api.Steam;
import components.GamesLibrarian.WindowBuilderMask;
import components.commons.JScrollableToolTip;
import components.workers.IconPaneReader;

/**
 * @author Naeregwen
 *
 */
public class IconPane extends JPanel implements MouseListener{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8319020237703389805L;

    private final int defaultDismissDelay = ToolTipManager.sharedInstance().getDismissDelay();
    private final int currentDismissDelay = (int) TimeUnit.MINUTES.toMillis(10); // 10 minutes
    JScrollableToolTip tooltip;

	WindowBuilderMask me;
	BufferedImage image;

	/**
	 * Setup instances
	 */
	public IconPane(WindowBuilderMask me) {
		this.me = me;
		if (me != null && me.getLibrarian().getCurrentSteamProfile() != null) // WindowBuilder
			setToolTipText(me.getLibrarian().getCurrentSteamProfile().getTooltipText());
		addMouseListener(this);
	}

	/**
	 * Clear image data reference
	 * Then repaint
	 */
	public void clearImage() {
		this.image = null;
		repaint();
	}
	
	/**
	 * Start a distant image data request
	 * Repaint will be done by reader at end of execution
	 * @param url
	 */
	public void setImage(String url) {
		(new IconPaneReader(me.getLibrarian().getTee(), this, url)).execute();
	}

	/**
	 * Assign new local image data
	 * Then repaint
	 * @param image
	 */
	public void setImage (BufferedImage image) {
		this.image = image;
		setToolTipText(me.getLibrarian().getCurrentSteamProfile().getTooltipText());
		repaint();
	}
	
	/**
	 * Convert a local ImageIcon to a BufferedImage without alpha support
	 * In order to copy image data into the new Graphics
	 * Then repaint
	 * @param icon
	 */
	public void setImage (ImageIcon icon) {
		BufferedImage bufferedImage = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics graphics = bufferedImage.createGraphics();
		icon.paintIcon(null, graphics, 0, 0);
		graphics.dispose();
		this.image = bufferedImage;
		repaint();
	}
	
	/*/
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		if (image != null)
			g.drawImage(image, 0, 0, null);
		else
			g.clearRect(0, 0, Steam.avatarFullIconWidth, Steam.avatarFullIconHeight);
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
		return me.getLibrarian().getCurrentSteamProfile() != null ? (me.getLibrarian().getCurrentSteamProfile()).getTooltipText() : null;
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
