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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

/**
 * @author Naeregwen
 *
 */
public class BoundedComponent {

	public static ImageIcon resizeAndCenterIcon(JComponent component, ImageIcon icon, int width, int height) {
		int x = 0;
		int y = 0;
		
		boolean sameSize = false;
		
		if (icon.getIconHeight() < height) {
			if (icon.getIconWidth() < width) {
				x = (width - icon.getIconWidth()) / 2; 
				y = (height - icon.getIconHeight()) / 2;
			} else if (icon.getIconWidth() > width) {
				x = (icon.getIconWidth() - width) / 2; 
				y = (height - icon.getIconHeight()) / 2;
			} else {
				x = 0; 
				y = (height - icon.getIconHeight()) / 2; //0;
			}
		} else if (icon.getIconHeight() > height) {
			if (icon.getIconWidth() < width) {
				x = (width - icon.getIconWidth()) / 2;
				y = 0;
			} else if (icon.getIconWidth() > width) {
				x = 0;
				y = 0;
			} else {
				x = 0;
				y = 0;
			}
		} else if (icon.getIconWidth() < width) {
			x = (width - icon.getIconWidth()) / 2;
			y = 0;
		} else if (icon.getIconWidth() > width) {
			x = 0;
			y = 0;
		} else {
			x = 0;
			y = 0;
			sameSize = true;
		}
		
		if (!sameSize) {
			// Create new image with alpha support
			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			
			// Print icon inside new image
			Graphics graphics = bufferedImage.createGraphics();
			graphics.drawImage(icon.getImage(), x, y, component);
			graphics.dispose();
			
			// Replace icon with new bufferedImage
			icon = new ImageIcon(bufferedImage);
		}
		
		return icon;
	}
	
	/**
	 * Size icon to button limits
	 * 
	 * @param icon
	 */
	public static ImageIcon resizeAndSetIcon(JComponent component, ImageIcon icon, int width, int height) {
		if (icon.getIconHeight() > height || icon.getIconWidth() > width)
			icon = new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
		else if (icon.getIconHeight() < height || icon.getIconWidth() < width)
			icon = resizeAndCenterIcon(component, icon, width, height);
		return icon;
	}
	
	/**
	 * Redraw an ImageIcon with icon centered into new (width, height) image
	 * 
	 * @param icon
	 * @param newWidth
	 * @param newHeight
	 * @return
	 */
	public static ImageIcon resizeAndCenterIcon(ImageIcon icon, int newWidth, int newHeight) {
		if (icon == null || icon.getIconWidth() > newWidth || icon.getIconHeight() > newHeight) 
			return icon;
		// Create a buffered image with the size of the new image
		Image image = icon.getImage();
		BufferedImage bufferedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		// Blit the icon image to the center of buffered image
		Graphics graphics = bufferedImage.createGraphics();
		Color color = graphics.getColor();
		graphics.setColor(Color.BLACK);
		graphics.drawRect(0, 0, newWidth, newHeight);
		graphics.setColor(color);
		graphics.drawImage(image, (newWidth - icon.getIconWidth())/2, (newHeight - icon.getIconHeight())/2, icon.getIconWidth(), icon.getIconHeight(), null);
		// Replace icon with new bufferedImage
		icon = new ImageIcon(bufferedImage);
		// Dispose useless material
		graphics.dispose();
		return icon;		
	}
}
