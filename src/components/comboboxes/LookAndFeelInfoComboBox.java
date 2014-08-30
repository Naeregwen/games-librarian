/**
 * 
 */
package components.comboboxes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager.LookAndFeelInfo;

import commons.BundleManager;
import commons.api.Parameters;
import components.GamesLibrarian.WindowBuilderMask;
import components.comboboxes.renderers.LookAndFeelInfoComboBoxRenderer;

/**
 * @author Naeregwen
 *
 */
public class LookAndFeelInfoComboBox extends JComboBox<LookAndFeelInfo> implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9028612930149929536L;
	
	WindowBuilderMask me;
	
	@SuppressWarnings("unchecked")
	public LookAndFeelInfoComboBox(WindowBuilderMask me) {
		super(Parameters.lookAndFeelInfos);
		this.me = me;
		setRenderer(new LookAndFeelInfoComboBoxRenderer((ListCellRenderer<LookAndFeelInfo>) this.getRenderer()));
		addActionListener(this);
	}
	
	/*/
	 * (non-Javadoc)
	 * @see javax.swing.JComboBox#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		LookAndFeelInfo lookAndFeelInfo = (LookAndFeelInfo) this.getSelectedItem();
		me.getLibrarian().setLookAndFeel(lookAndFeelInfo);
		me.getLibrarian().getTee().writelnMessage(String.format(BundleManager.getMessages(me, "lookAndFeelMessage"), lookAndFeelInfo.getName()));
	}
}
