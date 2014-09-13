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

import commons.BundleManager;
import components.GamesLibrarian.WindowBuilderMask;

/**
 * @author Naeregwen
 * 
 */
public class NumberParser {

	NumberFormat defaultDoubleNumberFormat = NumberFormat.getNumberInstance();
	NumberFormat defaultIntegerNumberFormat = NumberFormat.getIntegerInstance();

	NumberFormat englishDoubleNumberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);
	NumberFormat englishIntegerNumberFormat = NumberFormat.getIntegerInstance(Locale.ENGLISH);

	ParsePosition parsePosition = new ParsePosition(0);
	
	private static NumberParser numberParser;
	private static WindowBuilderMask me;
	
	/**
	 * @param me 
	 * @return the NumberParser instance
	 */
	public static NumberParser getInstance(WindowBuilderMask me) {
		NumberParser.me = me;
		if (numberParser == null)
			numberParser = new NumberParser();
		return numberParser;
	}
	
	/**
	 * Create the NumberParser instance 
	 */
	private NumberParser() {
		super();
		defaultDoubleNumberFormat.setMinimumFractionDigits(1);
		englishDoubleNumberFormat.setMinimumFractionDigits(1);
	}

	/**
	 * Parse double with ParsePosition
	 * @param doubleStringValue
	 */
	public Double parseDouble(String doubleStringValue) {

		Number number;

		parsePosition.setIndex(0);
		number = defaultDoubleNumberFormat.parse(doubleStringValue, parsePosition);

		if (doubleStringValue.length() != parsePosition.getIndex() || number == null) {
			parsePosition.setIndex(0);
			number = englishDoubleNumberFormat.parse(doubleStringValue, parsePosition);
			if (doubleStringValue.length() != parsePosition.getIndex() || number == null)
				throw new NumberFormatException(String.format(BundleManager.getMessages(me, "doubleStringValueError"), doubleStringValue));
			return number.doubleValue();
		}
		
		return number.doubleValue();
	}

	/**
	 * Parse integer with ParsePosition
	 * @param integerStringValue
	 */
	public Integer parseInteger(String integerStringValue) {

		Number number;

		parsePosition.setIndex(0);
		number = defaultIntegerNumberFormat.parse(integerStringValue, parsePosition);
		if (integerStringValue.length() != parsePosition.getIndex() || number == null) {
			parsePosition.setIndex(0);
			number = englishIntegerNumberFormat.parse(integerStringValue, parsePosition);
			if (integerStringValue.length() != parsePosition.getIndex() || number == null)
				throw new NumberFormatException(String.format(BundleManager.getMessages(me, "integerStringValueError"), integerStringValue));
			return number.intValue();
		}
			
		return number.intValue();
	}

}
