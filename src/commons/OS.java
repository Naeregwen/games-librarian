/**
 * 
 */
package commons;

/**
 * @author Naeregwen
 *
 */
public class OS {
	
	// Useful, sometimes
	public static enum Prefix {Unknown, Win, Mac, Nix};

	Prefix prefix;

	/**
	 * @return the prefix
	 */
	public Prefix getPrefix() {
		return prefix;
	}

	/**
	 * @param prefix the prefix to set
	 */
	public void setPrefix(Prefix prefix) {
		this.prefix = prefix;
	}
	
	
}
