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
package components.textfields;

import javax.swing.JTextField;

import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.commons.adapters.LaunchButtonMouseAdapter;
import components.containers.remotes.LaunchButton;

/**
 * @author Naeregwen
 *
 */
public class GameArgumentsTextField extends JTextField {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6326520602558561598L;
	
	Librarian librarian;
	
	public GameArgumentsTextField(WindowBuilderMask me, LaunchButton launchButton) {
		addMouseListener(new LaunchButtonMouseAdapter(me, launchButton));
	}
	
}
