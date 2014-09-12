/**
 * 
 */
package commons.enums;

import javax.swing.Icon;

/**
 * @author Naeregwen
 *
 */
public interface TabEnum {
	
	/**
	 * Indicates if this TabEnum does represents the main Librarian or not
	 * @return true if the enumeration represent the main TabEnum (LibrarianTab)
	 */
	boolean isMain();

	/**
	 * Properties belonging to the specialized enumeration
	 * and corresponding to the same property for each associated GotoAction.
	 */
	
	/**
	 * @return the LabelKey for the associated GotoAction
	 */
	String getLabelKey();
	
	/**
	 * @return the MnemonicKey for the associated GotoAction
	 */
	String getMnemonicKey();
	
	/**
	 * @return the AcceleratorKey for the associated GotoAction
	 */
	String getAcceleratorKey();
	
	/**
	 * @return the icon for the associated GotoAction
	 */
	Icon getIcon();
}
