/**
 * 
 */
package commons.enums.helpers;


/**
 * @author Naeregwen
 *
 */
public class ParsableEnum {

	/**
	 * @param defaultValue the defaultValue used to identify the Enumeration type
	 * @return a list of acceptable string values for parsing
	 */
	public static String getAcceptableValues(Enum<?> defaultValue) {
		StringBuilder acceptableValues = new StringBuilder("");
		for (Enum<?> value : defaultValue.getClass().getEnumConstants())
			acceptableValues.append((acceptableValues.length() == 0 ? value : ", " + value) + ", " + value.ordinal());
		return acceptableValues.toString();	
	}
	
	/**
	 * Return the the most corresponding Enumeration value to passed value
	 *  
	 * @param newValue the value to make pass the test
	 * @return Enumeration value if found, null otherwise
	 */
	public static Enum<?> getAcceptableValue (String newValue, Enum<?> defaultValue) {
		for (Enum<?> value : defaultValue.getClass().getEnumConstants())
			if (value.toString().equalsIgnoreCase(newValue))
				return value;
		return null;
	}
}
