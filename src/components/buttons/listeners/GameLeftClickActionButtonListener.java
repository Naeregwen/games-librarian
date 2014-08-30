package components.buttons.listeners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import components.Librarian;
import components.GamesLibrarian.WindowBuilderMask;

public class GameLeftClickActionButtonListener implements ItemListener {

	Librarian librarian;
	
	public GameLeftClickActionButtonListener(WindowBuilderMask me) {
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
	}

	@Override
	public void itemStateChanged(ItemEvent event) {
		librarian.updateAllLeftClickActionButtonAndLabel();
	}

}
