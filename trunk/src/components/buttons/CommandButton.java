/**
 * 
 */
package components.buttons;

import javax.swing.Action;
import javax.swing.JButton;

import commons.BundleManager;
import commons.enums.ButtonsDisplayMode;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.actions.interfaces.IconAndTextAction;
import components.comboboxes.observers.ButtonsDisplayModeObserver;

/**
 * @author Naeregwen
 *
 */
public class CommandButton extends JButton implements ButtonsDisplayModeObserver {

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
			librarian.addButtonsDisplayModeObserver(this);
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
