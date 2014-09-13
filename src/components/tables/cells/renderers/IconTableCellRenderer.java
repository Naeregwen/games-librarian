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
package components.tables.cells.renderers;

import java.awt.Component;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import commons.GamesLibrary;
import components.GamesLibrarian;
import components.containers.commons.RemoteIconComponent;

/**
 * @author Naeregwen
 *
 */
public class IconTableCellRenderer extends DefaultTableCellRenderer implements RemoteIconComponent {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7129539614596667413L;
	
	private String url;
	
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    	
//        Component component = super.getTableCellRendererComponent(table, null, isSelected, hasFocus, row, column);
        url = (String) value;
        URL iconURL = null;
        ImageIcon icon = null;
        
//        if (url != null && component instanceof JLabel)
        if (url != null)
//        if ((getIcon() == null || getIcon().equals(GamesLibrary.noAvatarIcon)) && url != null)
			try {
				iconURL = new URL(url);
				// TODO: add a worker
//				(new RemoteIconComponentReader(null, this)).execute();
//				RemoteIconComponentReader reader = new RemoteIconComponentReader(this);
		        icon = new ImageIcon(iconURL);
		        setIcon(icon);
			} catch (MalformedURLException e) {
				icon = new ImageIcon(GamesLibrarian.class.getResource(url));
				setIcon(icon);
			} catch (Exception e) {
				e.printStackTrace();
			}
        else
        	setIcon(GamesLibrary.noAvatarIcon);
        setHorizontalAlignment(CENTER);
        return this;
   }

	@Override
	public Icon getIcon() {
		return super.getIcon();
	}

	@Override
	public String getIconURL() {
		return url;
	}

	@Override
	public void setIcon(ImageIcon icon) {
		if (icon != null)
			super.setIcon(icon);
	}

	@Override
	public void setAvatarIconAsResource() {
		if (getIconURL() != null && GamesLibrarian.class.getResource(getIconURL()) != null) {
			ImageIcon icon = new ImageIcon(GamesLibrarian.class.getResource(getIconURL()));
			if (icon != null && icon.getImage() != null && icon.getIconHeight() > 0 && icon.getIconWidth() > 0)
				super.setIcon(icon);
		}
	}

        
}
