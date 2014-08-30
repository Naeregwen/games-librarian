package commons.groups;

import java.beans.*;
import java.util.*;
 
import javax.swing.Action;
 
/**
 * http://bugs.java.com/bugdatabase/view_bug.do?bug_id=4133141
 * http://www.javalobby.org/java/forums/t53484.html
 */
public class ActionGroup {
 
	private List<Action> actions;
	private boolean notifyLock;
	private PropertyChangeListener selectedListener;
	
	public ActionGroup() {
		actions = new ArrayList<Action>();
		selectedListener = new SelectedListener();
	}
	
	public void add(Action action) {
		actions.add(action);
		action.addPropertyChangeListener(selectedListener);
	}
	
	public void remove(Action action) {
		actions.remove(action);
		action.removePropertyChangeListener(selectedListener);
	}
	
	public List<Action> getActions() {
		return new ArrayList<Action>(actions);
	}
	
	private class SelectedListener implements PropertyChangeListener {
		public void propertyChange(PropertyChangeEvent evt) {
			// prevent any poorly implemented components from 
			// causing us to get stuck in a feedback loop.
			if (notifyLock) return;
			
			// If it isn't a selected key change, or 
			// someone set it to false we just avoid doing anything.
			if (evt.getPropertyName().equals(ActionConstants.SELECTED_KEY) && evt.getNewValue().equals(Boolean.TRUE)) {				
				try {
					notifyLock = true;
					for (int i = 0; i < actions.size(); i++) {
						Action action = (Action)actions.get(i);
						if (!action.equals(evt.getSource())) {
							action.putValue(ActionConstants.SELECTED_KEY, Boolean.FALSE);
						}
					}
				} finally {
					notifyLock = false;
				}
			}
		}
	}
}