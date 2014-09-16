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
package components.comboboxes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.ListCellRenderer;

import commons.enums.ButtonsDisplayMode;
import components.Librarian;
import components.GamesLibrarian.WindowBuilderMask;
import components.comboboxes.renderers.enums.GamesLibrarianActionEnumCellRenderer;
import components.commons.BundleManager;
import components.commons.interfaces.Translatable;

/**
 * @author Naeregwen
 *
 */
public class ButtonsDisplayModeComboBox extends JComboBox<ButtonsDisplayMode> implements Translatable, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3634423743186840715L;

	WindowBuilderMask me;
	Librarian librarian;
	
	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	@SuppressWarnings("unchecked")
	public ButtonsDisplayModeComboBox(WindowBuilderMask me) {
		super(ButtonsDisplayMode.values());
		this.me = me;
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		if (librarian != null) // WindowBuilder
			librarian.addTranslatable(this);
		// http://www.fnogol.de/archives/2008/02/07/dont-subclass-defautlistcellrenderer-for-nimbus/
		setRenderer(new GamesLibrarianActionEnumCellRenderer(me, (ListCellRenderer<ButtonsDisplayMode>) this.getRenderer()));
		addActionListener(this);
		translate();
	}
	
	/* (non-Javadoc)
	 * @see components.commons.interfaces.Translatable#translate()
	 */
	@Override
	public void translate() {
		setToolTipText(BundleManager.getUITexts(me, "buttonsDisplayModeToolTip"));
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComboBox#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		ButtonsDisplayMode selectedButtonsDisplayMode = (ButtonsDisplayMode) this.getSelectedItem();
		me.getLibrarian().updateCommandButtons(selectedButtonsDisplayMode);
		me.getLibrarian().getTee().writelnMessage(String.format(BundleManager.getMessages(me, "buttonsDisplayModeSelectionMessage"), BundleManager.getUITexts(me, selectedButtonsDisplayMode.getLabelKey())));
	}

}
