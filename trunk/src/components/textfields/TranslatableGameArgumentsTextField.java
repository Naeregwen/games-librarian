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
import components.commons.BundleManager;
import components.commons.adapters.LaunchButtonMouseAdapter;
import components.commons.interfaces.Translatable;
import components.containers.remotes.LaunchButton;

/**
 * @author Naeregwen
 *
 */
public class TranslatableGameArgumentsTextField extends JTextField implements Translatable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5013384168738432216L;

	WindowBuilderMask me;
	Librarian librarian;
	
	LaunchButton launchButton;
	
	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 * @param launchButton the LaunchButton when applicable
	 */
	public TranslatableGameArgumentsTextField(WindowBuilderMask me, LaunchButton launchButton) {
		this.me = me;
		this.launchButton = launchButton;
		addMouseListener(new LaunchButtonMouseAdapter(me, launchButton));
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		if (librarian != null) // WindowBuilder
			librarian.addTranslatable(this);
		translate();
	}

	/* (non-Javadoc)
	 * @see components.commons.interfaces.Translatable#translate()
	 */
	@Override
	public void translate() {
		if (launchButton == null)
			setToolTipText(BundleManager.getUITexts(me, "gameNameTextFieldToolTip"));
	}

}
