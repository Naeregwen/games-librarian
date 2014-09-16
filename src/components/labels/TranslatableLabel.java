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
package components.labels;

import javax.swing.JLabel;

import commons.BundleManager;
import components.Librarian;
import components.GamesLibrarian.WindowBuilderMask;
import components.commons.interfaces.Translatable;

/**
 * @author Naeregwen
 *
 */
public class TranslatableLabel extends JLabel implements Translatable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4096860122866218141L;
	
	WindowBuilderMask me;
	Librarian librarian;

	String labelKey;
	
	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 * @param labelKey the labelKey for label translation
	 */
	public TranslatableLabel(WindowBuilderMask me, String labelKey) {
		this.me = me;
		this.labelKey = labelKey;
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
		setText(BundleManager.getUITexts(me, labelKey));
	}

}
