/**
 * 
 */
package commons;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Naeregwen
 *
 */
public class Strings {

	// Useful pattern
	public static final Pattern fullNumericPattern = Pattern.compile("^(\\d+)$");
	public static final Pattern startNumericPattern = Pattern.compile("^(\\d+)");
	private static final Pattern strinWithNonEndingSpacePattern = Pattern.compile("(.*)\\s([^\\s])$");
	
	/**
	 * Pad left a string
	 * @param s
	 * @param n
	 * @return padded string
	 */
	public static String padLeft(String s, int n) {
	    return String.format("%1$" + n + "s", s);  
	}
	
	/**
	 * Pad right a string
	 * @param s
	 * @param n
	 * @return padded string
	 */
	public static String padRight(String s, int n) {
	     return String.format("%1$-" + n + "s", s);  
	}

	/**
	 * Format a UTF-8 string terminating by space and a non space character
	 * into HTML equivalent with non breaking space character = &nbsp; + character
	 * To make a convenient HTML string to display, without printing alone character on a wrapped line
	 * @param s
	 * @return
	 */
	public static String htmlEOL(String s) {
		Matcher matcher = strinWithNonEndingSpacePattern.matcher(s);
		return matcher.find() ? matcher.group(1) + "&nbsp;" + matcher.group(2): s;
	}

}
