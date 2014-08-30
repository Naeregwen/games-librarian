/**
 * 
 */
package components.comboboxes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.ListCellRenderer;

import commons.enums.SteamGamesSortMethod;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.comboboxes.renderers.SteamGamesSortMethodComboBoxRenderer;

/**
 * @author Naeregwen
 *
 */
public class SteamGamesSortMethodComboBox extends JComboBox<SteamGamesSortMethod> implements ActionListener {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7195246176791216591L;

	Librarian librarian;
	SteamGamesSortMethod currentSteamGamesSortMethod;
	
	@SuppressWarnings("unchecked")
	public SteamGamesSortMethodComboBox(WindowBuilderMask me) {
		super(SteamGamesSortMethod.values());
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		setRenderer(new SteamGamesSortMethodComboBoxRenderer((ListCellRenderer<SteamGamesSortMethod>)this.getRenderer()));
		addActionListener(this);
		currentSteamGamesSortMethod = (SteamGamesSortMethod) getSelectedItem();
	}
	
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		if (currentSteamGamesSortMethod != (SteamGamesSortMethod) getSelectedItem()) {
			currentSteamGamesSortMethod = (SteamGamesSortMethod) getSelectedItem();
			librarian.sort((SteamGamesSortMethod) getSelectedItem());
			librarian.updateLibrarySortMethodTooltips();
		}
    }
	
}
