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
package components.tooltips;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JToolTip;

/**
 * @author Naeregwen
 *
 */
public class ImageToolTip extends JToolTip {
	
	JLabel label = new JLabel();
	JProgressBar progressBar;
	
	private static final int iconPadding = 2;
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7046647446472695503L;

	public ImageToolTip(ImageIcon imageIcon, Integer achievementsRatio) {
		setLayout(new BorderLayout());
		
		if (achievementsRatio != null) {
			progressBar = new JProgressBar(0, 100);
			progressBar.setValue(achievementsRatio);
			progressBar.setStringPainted(true);
			add(progressBar);
		}
	    
	    label = new JLabel();
	    label.setBorder(BorderFactory.createCompoundBorder(label.getBorder(), BorderFactory.createEmptyBorder(iconPadding, iconPadding, iconPadding, iconPadding)));
	    label.setVerticalTextPosition(JLabel.TOP);
	    
	    add(label, BorderLayout.NORTH);
	    
	    label.setIcon(imageIcon);
	}
	
    
    /*/
     * (non-Javadoc)
     * @see javax.swing.JToolTip#setTipText(java.lang.String)
     */
	@Override
    public void setTipText(String tipText) {
        label.setText(tipText);
    }

	/*/
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#getPreferredSize()
	 */
    @Override
    public Dimension getPreferredSize() {
        return getLayout().preferredLayoutSize(this);
    }

}
