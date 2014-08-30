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
	 * Action properties used with Actions specialized by the enumeration type
	 */
	String getLabelKey();
	String getMnemonicKey();
	String getAcceleratorKey();
	Icon getIcon();
}
