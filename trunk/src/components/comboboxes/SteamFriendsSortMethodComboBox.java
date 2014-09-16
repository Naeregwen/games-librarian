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
import commons.enums.SteamFriendsSortMethod;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.comboboxes.renderers.enums.GamesLibrarianActionEnumCellRenderer;
import components.commons.interfaces.Translatable;
import components.labels.TranslatableLabel;

/**
 * @author Naeregwen
 *
 */
public class SteamFriendsSortMethodComboBox extends JComboBox<SteamFriendsSortMethod> implements Translatable, ActionListener {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2309717919379074157L;

	WindowBuilderMask me;
	Librarian librarian;
	TranslatableLabel translatableLabel;
	
	SteamFriendsSortMethod currentSteamFriendsSortMethod;
	
	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 * @param translatableLabel the linked label for toolTip translation
	 */
	@SuppressWarnings("unchecked")
	public SteamFriendsSortMethodComboBox(WindowBuilderMask me, TranslatableLabel translatableLabel) {
		super(SteamFriendsSortMethod.values());
		this.me = me;
		this.translatableLabel = translatableLabel;
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		this.currentSteamFriendsSortMethod = (SteamFriendsSortMethod) getSelectedItem();
		if (librarian != null) // WindowBuilder
			librarian.addTranslatable(this);
		setRenderer(new GamesLibrarianActionEnumCellRenderer(me, (ListCellRenderer<SteamFriendsSortMethod>) this.getRenderer()));
		addActionListener(this);
		translate();
	}
	
	/* (non-Javadoc)
	 * @see components.commons.interfaces.Translatable#translate()
	 */
	@Override
	public void translate() {
		String tooltipText = String.format(BundleManager.getUITexts(me, "steamFriendsSortMethodTooltip"), 
				BundleManager.getUITexts(me, ((SteamFriendsSortMethod) getSelectedItem()).getLabelKey()));
		setToolTipText(tooltipText);
		translatableLabel.setToolTipText(tooltipText);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComboBox#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		librarian.displaySubTab(ProfileTabEnum.Friends);
		if (currentSteamFriendsSortMethod != (SteamFriendsSortMethod) getSelectedItem()) {
			currentSteamFriendsSortMethod = (SteamFriendsSortMethod) getSelectedItem();
			librarian.sort((SteamFriendsSortMethod) getSelectedItem());
		}
    }

}
