/**
 * 
 */
package components.comboboxes.observables;

import components.comboboxes.observers.SteamAchievementsSortMethodObserver;

/**
 * @author Naeregwen
 *
 */
public interface SteamAchievementsSortMethodObservables {

	public void addSteamAchievementsSortMethodObserver(SteamAchievementsSortMethodObserver steamAchievementsSortMethodObserver);
	public void updateSteamAchievementsSortMethodObservers();
	public void removeSteamAchievementsSortMethodObserver(SteamAchievementsSortMethodObserver steamAchievementsSortMethodObserver);
}
