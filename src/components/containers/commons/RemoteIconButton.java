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
package components.containers.commons;

import javax.swing.ImageIcon;

/**
 * @author Naeregwen
 *
 */
public interface RemoteIconButton {

	/**
	 * Get the Icon URL from Button
	 * @return the Icon URL
	 */
	public String getIconURL();
	
	/**
	 * Set icon
	 * @param icon the icon to set
	 */
	public void setIcon(ImageIcon icon);
	
	/**
	 * Get AvatarIcon as Resource
	 * Try to read icon data from AvatarIcon as a locale resource key
	 */
	public void setAvatarIconAsResource();

}
