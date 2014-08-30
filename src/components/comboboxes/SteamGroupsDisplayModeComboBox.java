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

import commons.enums.ProfileTab;
import commons.enums.SteamGroupsDisplayMode;
import components.Librarian;
import components.GamesLibrarian.WindowBuilderMask;
import components.comboboxes.renderers.SteamGroupsDisplayModeComboBoxRenderer;

/**
 * @author Naeregwen
 *
 */
public class SteamGroupsDisplayModeComboBox extends JComboBox<SteamGroupsDisplayMode> implements ItemListener {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7676968374003438627L;

	Librarian librarian;
	
	@SuppressWarnings("unchecked")
	public SteamGroupsDisplayModeComboBox(WindowBuilderMask me) {
		super(SteamGroupsDisplayMode.values());
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		setRenderer(new SteamGroupsDisplayModeComboBoxRenderer((ListCellRenderer<SteamGroupsDisplayMode>) this.getRenderer()));
		addItemListener(this);
	}
	
	/*/
	 * (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		JPanel cards = librarian.getProfileGroupsTab();
        CardLayout cardLayout = (CardLayout)(cards.getLayout());
        cardLayout.show(cards, ((SteamGroupsDisplayMode)e.getItem()).name());
        librarian.displaySubTab(ProfileTab.Groups);
	}

}
