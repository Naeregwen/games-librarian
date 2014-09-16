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

package components.commons.parsers;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;

import commons.enums.exceptions.AcceptableValueType;

/**
 * @author Naeregwen
 * http://www.ibm.com/developerworks/library/j-numberformat/
 */
public class NumberParser {

	private static final NumberFormat defaultDoubleNumberFormat = NumberFormat.getNumberInstance();
	private static final NumberFormat defaultIntegerNumberFormat = NumberFormat.getIntegerInstance();

	private static final NumberFormat englishDoubleNumberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);
	private static final NumberFormat englishIntegerNumberFormat = NumberFormat.getIntegerInstance(Locale.ENGLISH);

	static {
		defaultDoubleNumberFormat.setMinimumFractionDigits(1);
		englishDoubleNumberFormat.setMinimumFractionDigits(1);
	}
	
	ParsePosition parsePosition;
	
	/**
	 * Create the NumberParser instance 
	 */
	public NumberParser() {		
		parsePosition = new ParsePosition(0);
	}

	/**
	 * Parse double with ParsePosition
	 * @param doubleStringValue
	 * @throws UnacceptableValueTypeException 
	 */
	public Double parseDouble(String doubleStringValue) throws UnacceptableValueTypeException {

		Number number;

		parsePosition.setIndex(0);
		number = defaultDoubleNumberFormat.parse(doubleStringValue, parsePosition);

		if (doubleStringValue.length() != parsePosition.getIndex() || number == null) {
			parsePosition.setIndex(0);
			number = englishDoubleNumberFormat.parse(doubleStringValue, parsePosition);
			if (doubleStringValue.length() != parsePosition.getIndex() || number == null)
				throw new UnacceptableValueTypeException(AcceptableValueType.Double);
//			throw new NumberFormatException(String.format(BundleManager.getMessages(me, "doubleStringValueError"), doubleStringValue));
			return number.doubleValue();
		}
		
		return number.doubleValue();
	}

	/**
	 * Parse integer with ParsePosition
	 * @param integerStringValue
	 * @throws UnacceptableValueTypeException 
	 */
	public Integer parseInteger(String integerStringValue) throws UnacceptableValueTypeException {

		Number number;

		parsePosition.setIndex(0);
		number = defaultIntegerNumberFormat.parse(integerStringValue, parsePosition);
		if (integerStringValue.length() != parsePosition.getIndex() || number == null) {
			parsePosition.setIndex(0);
			number = englishIntegerNumberFormat.parse(integerStringValue, parsePosition);
			if (integerStringValue.length() != parsePosition.getIndex() || number == null)
				throw new UnacceptableValueTypeException(AcceptableValueType.Integer);
//			throw new NumberFormatException(String.format(BundleManager.getMessages(me, "integerStringValueError"), integerStringValue));
			return number.intValue();
		}
			
		return number.intValue();
	}

}
