/**
 * 
 */
package components.comboboxes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.ListCellRenderer;

import commons.enums.SteamGroupsSortMethod;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.comboboxes.renderers.SteamGroupsSortMethodComboBoxRenderer;

/**
 * @author Naeregwen
 *
 */
public class SteamGroupsSortMethodComboBox extends JComboBox<SteamGroupsSortMethod> implements ActionListener {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7229666048316853293L;

	Librarian librarian;
	SteamGroupsSortMethod currentSteamGroupsSortMethod;
	
	@SuppressWarnings("unchecked")
	public SteamGroupsSortMethodComboBox(WindowBuilderMask me) {
		super(SteamGroupsSortMethod.values());
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		setRenderer(new SteamGroupsSortMethodComboBoxRenderer((ListCellRenderer<SteamGroupsSortMethod>) this.getRenderer()));
		addActionListener(this);
		currentSteamGroupsSortMethod = (SteamGroupsSortMethod) getSelectedItem();
	}
	
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		if (currentSteamGroupsSortMethod != (SteamGroupsSortMethod) getSelectedItem()) {
			currentSteamGroupsSortMethod = (SteamGroupsSortMethod) getSelectedItem();
			librarian.sort((SteamGroupsSortMethod) getSelectedItem());
		}
    }
	
}
