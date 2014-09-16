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

import commons.BundleManager;
import commons.enums.ProfileTabEnum;
import commons.enums.SteamGroupsSortMethod;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.comboboxes.renderers.enums.GamesLibrarianActionEnumCellRenderer;
import components.commons.interfaces.Translatable;
import components.labels.TranslatableLabel;

/**
 * @author Naeregwen
 *
 */
public class SteamGroupsSortMethodComboBox extends JComboBox<SteamGroupsSortMethod> implements Translatable, ActionListener {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7229666048316853293L;

	WindowBuilderMask me;
	Librarian librarian;
	TranslatableLabel translatableLabel;
	
	SteamGroupsSortMethod currentSteamGroupsSortMethod;
	
	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 * @param translatableLabel the linked label for toolTip translation
	 */
	@SuppressWarnings("unchecked")
	public SteamGroupsSortMethodComboBox(WindowBuilderMask me, TranslatableLabel translatableLabel) {
		super(SteamGroupsSortMethod.values());
		this.translatableLabel = translatableLabel;
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		this.currentSteamGroupsSortMethod = (SteamGroupsSortMethod) getSelectedItem();
		if (librarian != null) // WindowBuilder
			librarian.addTranslatable(this);
		setRenderer(new GamesLibrarianActionEnumCellRenderer(me, (ListCellRenderer<SteamGroupsSortMethod>) this.getRenderer()));
		addActionListener(this);
		translate();
	}
	
	/* (non-Javadoc)
	 * @see components.commons.interfaces.Translatable#translate()
	 */
	@Override
	public void translate() {
		String tooltipText = String.format(BundleManager.getUITexts(me, "steamGroupsSortMethodTooltip"), 
				BundleManager.getUITexts(me, ((SteamGroupsSortMethod) getSelectedItem()).getLabelKey()));
		setToolTipText(tooltipText);
		translatableLabel.setToolTipText(tooltipText);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComboBox#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		librarian.displaySubTab(ProfileTabEnum.Groups);
		if (currentSteamGroupsSortMethod != (SteamGroupsSortMethod) getSelectedItem()) {
			currentSteamGroupsSortMethod = (SteamGroupsSortMethod) getSelectedItem();
			librarian.sort((SteamGroupsSortMethod) getSelectedItem());
		}
    }

}
