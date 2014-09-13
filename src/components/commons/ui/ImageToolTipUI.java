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
package components.commons.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicToolTipUI;

/**
 * Tool Tip with an image on bottom
 * 
 * @author Naeregwen
 *
 */
public class ImageToolTipUI extends BasicToolTipUI {
	
	private final int imagePadding = 5;
	
	ImageToolTipUIHelper helper;
	
	public ImageToolTipUI(ImageIcon gameIcon, ImageToolTipUIHelper helper) {
		this.helper = helper;
		helper.setImageIcon(gameIcon);
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicToolTipUI#paint(java.awt.Graphics, javax.swing.JComponent)
	 */
	public void paint(Graphics graphics, JComponent component) {

		super.paint(graphics, component);
        Dimension size = component.getSize();
		Image image = ((ImageIcon) helper.getImageIcon()).getImage();
		ImageIcon imageIcon = (ImageIcon) helper.getImageIcon();
		graphics.drawImage(image, (size.width - imageIcon.getIconWidth()) / 2, size.height - imageIcon.getIconHeight() - imagePadding, component);
		
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicToolTipUI#getPreferredSize(javax.swing.JComponent)
	 */
	public Dimension getPreferredSize(JComponent component) {

		Dimension dimensions = super.getPreferredSize(component);
		Image image = ((ImageIcon) helper.getImageIcon()).getImage();
		if (dimensions.width < image.getWidth(component))
			dimensions.width = image.getWidth(component) + imagePadding * 2;
		dimensions.height += image.getHeight(component) + imagePadding * 2;
		return dimensions;
	}
	
}

