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

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.ListCellRenderer;

import commons.enums.LibrarianTabEnum;
import commons.enums.SteamAchievementsListsSortMethod;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.comboboxes.renderers.enums.GamesLibrarianActionEnumCellRenderer;
import components.commons.BundleManager;
import components.commons.interfaces.Translatable;
import components.labels.TranslatableLabel;

/**
 * @author Naeregwen
 *
 */
public class SteamAchievementsListsSortMethodComboBox extends JComboBox<SteamAchievementsListsSortMethod> implements Translatable, ItemListener {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5295790922474873666L;

	WindowBuilderMask me;
	Librarian librarian;
	
	TranslatableLabel translatableLabel;
	
	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 * @param translatableLabel the linked label for toolTip translation
	 */
	@SuppressWarnings("unchecked")
	public SteamAchievementsListsSortMethodComboBox(WindowBuilderMask me, TranslatableLabel translatableLabel) {
		super(SteamAchievementsListsSortMethod.values());
		this.me = me;
		this.translatableLabel = translatableLabel;
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		if (librarian != null) // WindowBuilder
			librarian.addTranslatable(this);
		setRenderer(new GamesLibrarianActionEnumCellRenderer(me, (ListCellRenderer<SteamAchievementsListsSortMethod>) this.getRenderer()));
		addItemListener(this);
	}
	
	/* (non-Javadoc)
	 * @see components.commons.interfaces.Translatable#translate()
	 */
	@Override
	public void translate() {
		String tooltipText = String.format(BundleManager.getUITexts(me, "steamAchievementsListsSortMethodTooltip"), 
				BundleManager.getUITexts(me, ((SteamAchievementsListsSortMethod) getSelectedItem()).getLabelKey()));
		setToolTipText(tooltipText);
		translatableLabel.setToolTipText(tooltipText);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(ItemEvent itemEvent) {
		librarian.displayMainTab(LibrarianTabEnum.Game);
		if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
			librarian.sortSteamAchievementsList((SteamAchievementsListsSortMethod) itemEvent.getItem());
			translate();
		}
	}

}
