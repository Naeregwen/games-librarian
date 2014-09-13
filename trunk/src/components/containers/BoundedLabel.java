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

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * @author Naeregwen
 *
 */
public class BoundedLabel extends JLabel {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6671304941283427825L;

	int width;
	int height;
	
	public BoundedLabel(String label, int width, int height) {
		super(label);
		this.width = width;
		this.height = height;
	}
	
	protected ImageIcon resizeAndCenterIcon(ImageIcon icon) {
		return BoundedComponent.resizeAndCenterIcon(this, icon, width, height);
	}
	
	/**
	 * Size icon to button limits
	 * 
	 * @param icon
	 */
	public void resizeAndSetIcon(ImageIcon icon) {
		super.setIcon(BoundedComponent.resizeAndSetIcon(this, icon, width, height));
	}
	
}
