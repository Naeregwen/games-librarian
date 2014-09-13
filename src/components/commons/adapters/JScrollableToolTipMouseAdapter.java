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
package components.commons.adapters;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JToolTip;

/**
 * @author Naeregwen
 *
 */
public class JScrollableToolTipMouseAdapter extends MouseAdapter {

	JToolTip toolTip;
	JEditorPane tipArea;
	
	/**
	 * 
	 */
	public JScrollableToolTipMouseAdapter(JToolTip toolTip, JEditorPane tipArea) {
		this.toolTip = toolTip;
		this.tipArea = tipArea;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		JComponent component = toolTip.getComponent();
	 	if (component != null) {
            tipArea.dispatchEvent(new MouseEvent(tipArea, e.getID(), e.getWhen(), e.getModifiers(), 0, 0, 
            		e.getClickCount(), e.isPopupTrigger()));
        }
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseAdapter#mouseWheelMoved(java.awt.event.MouseWheelEvent)
	 */
    @Override
    public void mouseWheelMoved(final MouseWheelEvent e) {
        JComponent component = toolTip.getComponent();
        if(component != null) {
            tipArea.dispatchEvent(new MouseWheelEvent(tipArea, e.getID(), e.getWhen(), e.getModifiers(), 0, 0, 
            		e.getClickCount(), e.isPopupTrigger(), e.getScrollType(), e.getScrollAmount(), e.getWheelRotation()));
        }
    }
}
