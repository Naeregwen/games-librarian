/**
 * 
 */
package components.comboboxes;

import java.awt.CardLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import commons.enums.ProfileTabEnum;
import commons.enums.SteamFriendsDisplayMode;
import components.Librarian;
import components.GamesLibrarian.WindowBuilderMask;
import components.comboboxes.renderers.SteamFriendsDisplayModeComboBoxRenderer;

/**
 * @author Naeregwen
 *
 */
public class SteamFriendsDisplayModeComboBox extends JComboBox<SteamFriendsDisplayMode> implements ItemListener {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6562585050291479810L;
	
	Librarian librarian;
	
	@SuppressWarnings("unchecked")
	public SteamFriendsDisplayModeComboBox(WindowBuilderMask me) {
		super(SteamFriendsDisplayMode.values());
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		setRenderer(new SteamFriendsDisplayModeComboBoxRenderer((ListCellRenderer<SteamFriendsDisplayMode>) this.getRenderer()));
		addItemListener(this);
	}
	
	/*/
	 * (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		librarian.displaySubTab(ProfileTabEnum.Friends);
		JPanel cards = librarian.getProfileFriendsTab();
        CardLayout cardLayout = (CardLayout)(cards.getLayout());
        cardLayout.show(cards, ((SteamFriendsDisplayMode)e.getItem()).name());
        librarian.displaySubTab(ProfileTabEnum.Friends);

	}

}
