/**
 * 
 */
package components.dialogs;

import java.awt.Component;
import java.awt.HeadlessException;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

/**
 * @author Naeregwen
 *
 */
public class CenteredFileChooser extends JFileChooser {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5724354835079983953L;
	
	private JComponent relative;

	public CenteredFileChooser(JComponent relative) {
		this.relative = relative;
	}
	
	/*/
	 * (non-Javadoc)
	 * @see javax.swing.JFileChooser#createDialog(java.awt.Component)
	 */
    protected JDialog createDialog(Component parent) throws HeadlessException {
        JDialog dialog = super.createDialog(parent);
        dialog.setLocationRelativeTo(relative);
        return dialog;
    }

}
