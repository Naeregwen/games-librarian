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

import javax.swing.Action;
import javax.swing.JButton;

import commons.BundleManager;
import commons.enums.ButtonsDisplayMode;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.actions.interfaces.IconAndTextAction;
import components.comboboxes.observers.ButtonsDisplayModeObservable;

/**
 * @author Naeregwen
 *
 */
public class CommandButton extends JButton implements ButtonsDisplayModeObservable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9176047651723253919L;

	WindowBuilderMask me;
	Librarian librarian;
	
	public CommandButton(WindowBuilderMask me, Action action) {
		super(action);
		this.me = me;
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		if (librarian != null) // WindowBuilder
			librarian.addButtonsDisplayModeObservable(this);
	}
	
	/* (non-Javadoc)
	 * @see components.comboboxes.observers.ButtonsDisplayModeObserver#update()
	 */
	@Override
	public void update() {
		IconAndTextAction iconAndTextAction = (IconAndTextAction) getAction();
		ButtonsDisplayMode buttonsDisplayMode = librarian == null ? ButtonsDisplayMode.IconAndText : librarian.getParameters().getButtonsDisplayMode(); // WindowBuilder
		if (buttonsDisplayMode.equals(ButtonsDisplayMode.IconAndText)) {
			setText(BundleManager.getUITexts(me, iconAndTextAction.getLabelKey()));
			setIcon(iconAndTextAction.getIcon());
		} else if (buttonsDisplayMode.equals(ButtonsDisplayMode.Icon)) {
			setText(null);
			setIcon(iconAndTextAction.getIcon());
		} else {
			setText(BundleManager.getUITexts(me, iconAndTextAction.getLabelKey()));
			setIcon(null);
		}
	}

}
