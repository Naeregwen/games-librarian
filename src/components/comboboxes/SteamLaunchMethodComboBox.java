/**
 * 
 */
package components.comboboxes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComboBox;
import javax.swing.ListCellRenderer;

import commons.BundleManager;
import commons.api.Parameters;
import commons.api.SteamLaunchMethod;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.comboboxes.renderers.SteamLaunchMethodComboBoxRenderer;
import components.containers.remotes.LaunchButton;

/**
 * @author Naeregwen
 *
 */
public class SteamLaunchMethodComboBox extends JComboBox<SteamLaunchMethod> implements ActionListener, MouseListener {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7505492166723454021L;

	public enum Type {
		GameLauncher,
		LibraryList,
		DefaultMethod
	}
	
	WindowBuilderMask me;
	Librarian librarian;
	LaunchButton launchButton;
	Type type;
	
	@SuppressWarnings("unchecked")
	public SteamLaunchMethodComboBox(WindowBuilderMask me, LaunchButton launchButton, Type type) {
		super(SteamLaunchMethod.values());
		this.me = me;
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		this.launchButton = launchButton;
		this.type = type;
		setRenderer(new SteamLaunchMethodComboBoxRenderer((ListCellRenderer<SteamLaunchMethod>) this.getRenderer()));
		setMaximumRowCount(SteamLaunchMethod.values().length);
		addActionListener(this);
		addMouseListener(this);
	}

	/**
	 * @return the launchButton
	 */
	public LaunchButton getLaunchButton() {
		return launchButton;
	}

	/**
	 * @param launchButton the launchButton to set
	 */
	public void setLaunchButton(LaunchButton launchButton) {
		this.launchButton = launchButton;
	}

	/*/
	 * (non-Javadoc)
	 * @see javax.swing.JComboBox#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		
		Parameters parameters = librarian.getParameters();
		SteamLaunchMethod steamLaunchMethod = (SteamLaunchMethod) this.getSelectedItem();
		// Options DefaultSteamLaunchMethod
		if (launchButton == null) {
			if (type.equals(Type.DefaultMethod)) {
				if (!parameters.getDefaultSteamLaunchMethod().equals(steamLaunchMethod)) {
					parameters.setDefaultSteamLaunchMethod(steamLaunchMethod);
					librarian.getTee().writelnMessage(BundleManager.getMessages(me, steamLaunchMethod.getDefaultSelectionMessageKey()));
				}
			} 
		// Buttons SteamLaunchMethod
		} else {
			if (launchButton.getGame() == null || launchButton.getGame().getSteamLaunchMethod().equals(steamLaunchMethod)) return;
			launchButton.setSteamLaunchMethod(steamLaunchMethod);
			switch (type) {
			case LibraryList:
				librarian.updateGameLauncher(launchButton);
				break;
			case GameLauncher:
				librarian.updateLibraryTooltip(launchButton);
				break;
			default:
				break;
			}
			if (parameters.isDebug()) {
				librarian.getTee().writelnMessage(String.format(BundleManager.getMessages(me, steamLaunchMethod.getSelectionMessageKey()), launchButton.getGame().getName()));
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (launchButton != null)
			librarian.enterGame(launchButton.getGame());
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (launchButton != null)
			librarian.leaveGame(launchButton.getGame());
	}
	
}
