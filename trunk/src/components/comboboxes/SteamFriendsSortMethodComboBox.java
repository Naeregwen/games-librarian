/**
 * 
 */
package components.comboboxes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.ListCellRenderer;

import commons.enums.ProfileTabEnum;
import commons.enums.SteamFriendsSortMethod;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.comboboxes.renderers.SteamFriendsSortMethodComboBoxRenderer;

/**
 * @author Naeregwen
 *
 */
public class SteamFriendsSortMethodComboBox extends JComboBox<SteamFriendsSortMethod> implements ActionListener {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2309717919379074157L;

	Librarian librarian;
	SteamFriendsSortMethod currentSteamFriendsSortMethod;
	
	@SuppressWarnings("unchecked")
	public SteamFriendsSortMethodComboBox(WindowBuilderMask me) {
		super(SteamFriendsSortMethod.values());
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		setRenderer(new SteamFriendsSortMethodComboBoxRenderer((ListCellRenderer<SteamFriendsSortMethod>) this.getRenderer()));
		addActionListener(this);
		currentSteamFriendsSortMethod = (SteamFriendsSortMethod) getSelectedItem();
	}
	
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		librarian.displaySubTab(ProfileTabEnum.Friends);
		if (currentSteamFriendsSortMethod != (SteamFriendsSortMethod) getSelectedItem()) {
			currentSteamFriendsSortMethod = (SteamFriendsSortMethod) getSelectedItem();
			librarian.sort((SteamFriendsSortMethod) getSelectedItem());
		}
    }
	
}
