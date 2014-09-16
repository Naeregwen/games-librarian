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

import commons.BundleManager;
import commons.enums.LibrarianTabEnum;
import commons.enums.SteamAchievementsSortMethod;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.comboboxes.observers.SteamAchievementsSortMethodObservable;
import components.comboboxes.renderers.enums.GamesLibrarianActionEnumCellRenderer;
import components.commons.interfaces.Translatable;
import components.labels.TranslatableLabel;

/**
 * @author Naeregwen
 *
 */
public class SteamAchievementsSortMethodComboBox extends JComboBox<SteamAchievementsSortMethod> implements Translatable, ItemListener, SteamAchievementsSortMethodObservable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1503901705561301531L;

	WindowBuilderMask me;
	Librarian librarian;
	TranslatableLabel translatableLabel;
	
	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 * @param translatableLabel the linked label for toolTip translation
	 */
	@SuppressWarnings("unchecked")
	public SteamAchievementsSortMethodComboBox(WindowBuilderMask me, TranslatableLabel translatableLabel) {
		super(SteamAchievementsSortMethod.values());
		this.me = me;
		this.translatableLabel = translatableLabel;
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		if (librarian != null) { // WindowBuilder
			librarian.addTranslatable(this);
			librarian.addSteamAchievementsSortMethodObservable(this);
		}
		setRenderer(new GamesLibrarianActionEnumCellRenderer(me, (ListCellRenderer<SteamAchievementsSortMethod>) this.getRenderer()));
		addItemListener(this);
	}
	
	/* (non-Javadoc)
	 * @see components.commons.interfaces.Translatable#translate()
	 */
	@Override
	public void translate() {
		String tooltipText = String.format(BundleManager.getUITexts(me, "achievementsSortMethodTooltip"), 
				BundleManager.getUITexts(me, ((SteamAchievementsSortMethod) getSelectedItem()).getLabelKey()));
		setToolTipText(tooltipText);
		translatableLabel.setToolTipText(tooltipText);
	}

	/* (non-Javadoc)
	 * @see components.comboboxes.observers.SteamAchievementsSortMethodObserver#update()
	 */
	@Override
	public void update() {
		setSelectedItem(librarian.getSteamAchievementsSortMethod());
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(ItemEvent itemEvent) {
		librarian.displayMainTab(LibrarianTabEnum.Game);
		if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
			librarian.sortSteamAchievements((SteamAchievementsSortMethod) itemEvent.getItem());
			translate();
		}
	}

}
