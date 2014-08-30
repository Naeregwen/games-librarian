/**
 * 
 */
package components.buttons.observables;

import components.buttons.observers.GameLeftClickActionObserver;

/**
 * @author Naeregwen
 *
 */
public interface GameLeftClickActionObservables {
	
	public void addGameLeftClickActionObserver(GameLeftClickActionObserver gameLeftClickActionObserver);
	public void updateGameLeftClickActionObservers();
	public void removeGameLeftClickActionObserver(GameLeftClickActionObserver gameLeftClickActionObserver);
}
