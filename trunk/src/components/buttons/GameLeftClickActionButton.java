/**
 * 
 */
package components.buttons;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

import commons.BundleManager;
import commons.api.Parameters;
import commons.enums.GameLeftClickAction;
import components.GamesLibrarian;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.buttons.observers.GameLeftClickActionObserver;

/**
 * @author Naeregwen
 *
 */
public class GameLeftClickActionButton extends JToggleButton implements ItemListener, GameLeftClickActionObserver {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1193478661179172687L;
	
	Librarian librarian;
	GameLeftClickAction action;
	
	/**
	 * 
	 */
	public GameLeftClickActionButton(WindowBuilderMask me, String key, GameLeftClickAction action) {
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		this.action = action;
		setText(BundleManager.getUITexts(me, key));
		if (librarian != null) // WindowBuilder
			librarian.getParameters().addGameLeftClickActionObserver(this);
		setIcon(new ImageIcon(GamesLibrarian.class.getResource(action.getIconPath())));
		addItemListener(this);
	}

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
