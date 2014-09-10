/**
 * 
 */
package components.comboboxes.observables;

import components.comboboxes.observers.ButtonsDisplayModeObserver;

/**
 * @author Naeregwen
 *
 */
public interface ButtonsDisplayModeObservables {

	public void addButtonsDisplayModeObserver(ButtonsDisplayModeObserver buttonsDisplayModeObserver);
	public void updateButtonsDisplayModeObservers();
	public void removeButtonsDisplayModeObserver(ButtonsDisplayModeObserver buttonsDisplayModeObserver);
}
