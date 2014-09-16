package components.commons.groups;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToggleButton;


/**
 * http://bugs.java.com/bugdatabase/view_bug.do?bug_id=4133141
 * http://www.javalobby.org/java/forums/t53484.html
 */
public final class ActionGroupFactory {
 
	private ActionGroupFactory() {}
	
	public static AbstractButton getRadioButton(Action action) {
		JRadioButton button = new JRadioButton(action);
		connectActionAndButton(action, button);
		return button;
	}
	
	public static AbstractButton getToggleButton(Action action) {
		JToggleButton button = new JToggleButton(action);
		connectActionAndButton(action, button);
		return button;
	}
	
	public static JMenuItem getRadioMenuItem(Action action) {
		JRadioButtonMenuItem menu = new JRadioButtonMenuItem(action);
		connectActionAndButton(action, menu);
		return menu;
	}	
 
	private static void connectActionAndButton(Action action, AbstractButton button) {
		SelectionStateAdapter adapter = new SelectionStateAdapter(action, button);
		adapter.configure();
	}
 
	/**
	 * Class that connects the selection state of the action 
	 * to the selection state of the button.
	 * 
	 * @author R.J. Lorimer
	 */
	private static class SelectionStateAdapter implements PropertyChangeListener, ItemListener {
		private Action action;
		private AbstractButton button;
		
		public SelectionStateAdapter(Action theAction, AbstractButton theButton) {
			action = theAction;
			button = theButton;			
		}
		
		protected void configure() {
			action.addPropertyChangeListener(this);
			button.addItemListener(this);
		}
		
		public void itemStateChanged(ItemEvent e) {
			boolean value = e.getStateChange() == ItemEvent.SELECTED;
			Boolean valueObj = Boolean.valueOf(value);
			action.putValue(ActionConstants.SELECTED_KEY, valueObj);
		}
		
		public void propertyChange(PropertyChangeEvent evt) {
			if(evt.getPropertyName().equals(ActionConstants.SELECTED_KEY)) {
				Boolean newSelectedState = (Boolean)evt.getNewValue();
				button.setSelected(newSelectedState.booleanValue());
			}
		}
	}
	
}