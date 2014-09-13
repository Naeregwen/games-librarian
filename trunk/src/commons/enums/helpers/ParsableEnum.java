/**
 * Copyright 2012-2014 Naeregwen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
