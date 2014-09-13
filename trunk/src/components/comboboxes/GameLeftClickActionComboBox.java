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
import commons.api.Parameters;
import commons.enums.GameLeftClickAction;
import components.GamesLibrarian.WindowBuilderMask;
import components.buttons.observers.GameLeftClickActionObserver;
import components.comboboxes.renderers.enums.GamesLibrarianActionEnumCellRenderer;

/**
 * @author Naeregwen
 *
 */
public class GameLeftClickActionComboBox extends JComboBox<GameLeftClickAction> implements ActionListener, GameLeftClickActionObserver {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8747266149937225133L;

	WindowBuilderMask me;
	
	@SuppressWarnings("unchecked")
	public GameLeftClickActionComboBox(WindowBuilderMask me) {
		super(GameLeftClickAction.values());
		this.me = me;
		setRenderer(new GamesLibrarianActionEnumCellRenderer(me, (ListCellRenderer<GameLeftClickAction>) this.getRenderer()));
		addActionListener(this);
		if (me != null && me.getLibrarian() != null) // WindowBuilder
			 me.getLibrarian().getParameters().addGameLeftClickActionObserver(this);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		GameLeftClickAction selectedGameLeftClickAction = (GameLeftClickAction) this.getSelectedItem();
		Parameters parameters =  me.getLibrarian().getParameters();
		parameters.setGameLeftClickAction(selectedGameLeftClickAction);
		me.getLibrarian().getTee().writelnMessage(String.format(BundleManager.getMessages(me, "gameLeftClickActionSelectionMessage"), BundleManager.getUITexts(me, selectedGameLeftClickAction.getTranslationKey())));
	}

	@Override
	public void update() {
		Parameters parameters =  me.getLibrarian().getParameters();
		if (parameters.getGameLeftClickAction().equals(GameLeftClickAction.Launch) && ((GameLeftClickAction)getSelectedItem()).equals(GameLeftClickAction.Select)) {
			setSelectedItem(GameLeftClickAction.Launch);
		} else if (parameters.getGameLeftClickAction().equals(GameLeftClickAction.Select) && ((GameLeftClickAction)getSelectedItem()).equals(GameLeftClickAction.Launch)) {
			setSelectedItem(GameLeftClickAction.Select);
		}
	}

}
