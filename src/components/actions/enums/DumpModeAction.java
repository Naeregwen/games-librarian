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
package components.actions.enums;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import commons.BundleManager;
import commons.enums.DumpMode;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.actions.interfaces.EnumAction;

/**
 * @author Naeregwen
 *
 */
public class DumpModeAction extends AbstractAction implements EnumAction<DumpMode> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2269763346796610621L;

	WindowBuilderMask me;
	Librarian librarian;
	DumpMode dumpMode;

	/**
	 * 
	 */
	public DumpModeAction(WindowBuilderMask me, DumpMode dumpMode) {
		this.me = me;
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		this.dumpMode = dumpMode;
		translate();
	}

	/**
	 * Translate using the BundleManager
	 */
	public void translate() {
		if (librarian == null) {
			putValue(NAME, BundleManager.getUITexts(me, dumpMode.getLabelKey()));
			putValue(SMALL_ICON, dumpMode.getIcon());
		} else {
			putValue(NAME, BundleManager.getUITexts(me, dumpMode.getLabelKey()));
			putValue(SMALL_ICON, dumpMode.getIcon());
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {}

	/*/
	 * (non-Javadoc)
	 * @see components.actions.interfaces.EnumAction#getCurrentItem()
	 */
	@Override
	public DumpMode getCurrentItem() {
		return dumpMode;
	}

}
