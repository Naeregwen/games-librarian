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
package components.buttons;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JToggleButton;

import commons.api.Parameters;
import commons.enums.GameLeftClickAction;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.buttons.observers.GameLeftClickActionObserver;
import components.commons.BundleManager;
import components.commons.interfaces.Translatable;
import components.labels.TranslatableLabel;

/**
 * @author Naeregwen
 *
 */
public class GameLeftClickActionButton extends JToggleButton implements Translatable, ItemListener, GameLeftClickActionObserver {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1193478661179172687L;
	
	WindowBuilderMask me;
	Librarian librarian;
	
	GameLeftClickAction action;
	
	String labelKey;
	TranslatableLabel translatableLabel;
	
	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 * @param action the linked action
	 * @param labelKey the labelKey for label translation
	 * @param translatableLabel the linked label for toolTip translation
	 */
	public GameLeftClickActionButton(WindowBuilderMask me, GameLeftClickAction action, String labelKey, TranslatableLabel translatableLabel) {
		this.me = me;
		this.action = action;
		this.labelKey = labelKey;
		this.translatableLabel = translatableLabel;
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		if (librarian != null) { // WindowBuilder
			librarian.addTranslatable(this);
			librarian.getParameters().addGameLeftClickActionObserver(this);
		}
		setIcon(action.getIcon());
		addItemListener(this);
		translate();
	}

	/* (non-Javadoc)
	 * @see components.commons.interfaces.Translatable#translate()
	 */
	@Override
	public void translate() {
		setText(BundleManager.getUITexts(me, labelKey));
		if (librarian != null) { // WindowBuilder
			String tooltipText = librarian.getParameters().getGameLeftClickAction().equals(GameLeftClickAction.Select) ?
					BundleManager.getUITexts(me, "leftClickActionTooltipSelect") : 
						BundleManager.getUITexts(me, "leftClickActionTooltipLaunch");
			setToolTipText(tooltipText);
			translatableLabel.setToolTipText(tooltipText);
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(ItemEvent event) {
		if (event.getStateChange() == ItemEvent.SELECTED)
			librarian.getParameters().setGameLeftClickAction(action);
	}

	/* (non-Javadoc)
	 * @see components.observers.GameLeftClickActionObserver#update()
	 */
	@Override
	public void update() {
		Parameters parameters = librarian.getParameters();
		setSelected(parameters.getGameLeftClickAction().equals(action) ? true : false);
	}

}
