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
package commons;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;

/**
 * @author Naeregwen
 *
 */
public class Strings {

	// Useful pattern
	public static final Pattern fullNumericPattern = Pattern.compile("^(\\d+)$");
	public static final Pattern startNumericPattern = Pattern.compile("^(\\d+)");
	private static final Pattern stringWithNonEndingSpacePattern = Pattern.compile("(.*)\\s([^\\s])$");
	
	/**
	 * Pad left a string
	 * @param string the string to pad
	 * @param number of characters to use for padding
	 * @return padded string
	 */
	public static String padLeft(String string, int number) {
	    return String.format("%1$" + number + "s", string);  
	}
	
	/**
	 * Pad right a string
	 * @param string the string to pad
	 * @param number of characters to use for padding
	 * @return padded string
	 */
	public static String padRight(String string, int number) {
	     return String.format("%1$-" + number + "s", string);  
	}

	/**
	 * Format a UTF-8 string terminating by space and a non space character
	 * into HTML equivalent with non breaking space character = &nbsp; + character
	 * To make a convenient HTML string to display, without printing alone character on a wrapped line
	 * @param string
	 * @return
	 */
	public static String htmlEOL(String string) {
		Matcher matcher = stringWithNonEndingSpacePattern.matcher(string);
		return matcher.find() ? matcher.group(1) + "&nbsp;" + matcher.group(2): string;
	}

    /**
     * <p>Modified from {@link org.apache.commons.lang3.time.DurationFormatUtils#formatDurationWords(long, boolean, boolean)}}</p> 
     * <p>Formats an elapsed time into a pluralization correct string.</p>
     * 
     * <p>This method formats durations using the days and lower fields of the
     * format pattern. Months and Years are used in this version but seconds are ignored.</p>
     * 
     * @param durationMillis  the elapsed time to report in milliseconds
     * @param suppressLeadingZeroElements  suppresses leading 0 elements
     * @param suppressTrailingZeroElements  suppresses trailing 0 elements
     * @return the formatted text in years/months/days/hours/minutes/seconds, not null
     */
    public static String formatDurationWords(final long durationMillis, final boolean suppressLeadingZeroElements, final boolean suppressTrailingZeroElements) {

        // This method is generally replaceable by the format method, but 
        // there are a series of tweaks and special cases that require 
        // trickery to replicate.
        String duration = DurationFormatUtils.formatDuration(durationMillis, "y' years 'M' months 'd' days 'H' hours 'm' minutes'");
        if (suppressLeadingZeroElements) {
        	
            // this is a temporary marker on the front. Like ^ in regexp.
            duration = " " + duration;
            String tmp = StringUtils.replaceOnce(duration, " 0 years", "");
            if (tmp.length() != duration.length()) {
                duration = tmp;
                tmp = StringUtils.replaceOnce(duration, " 0 months", "");
                if (tmp.length() != duration.length()) {
                    duration = tmp;
                    tmp = StringUtils.replaceOnce(duration, " 0 days", "");
                    if (tmp.length() != duration.length()) {
                        duration = tmp;
                        tmp = StringUtils.replaceOnce(tmp, " 0 hours", "");
                        duration = tmp;
                        tmp = StringUtils.replaceOnce(duration, " 0 minutes", "");
                    }
                }
            }
            if (duration.length() != 0) {
                // strip the space off again
                duration = duration.substring(1);
            }
        }
        
        if (suppressTrailingZeroElements) {
            String tmp = StringUtils.replaceOnce(duration, " 0 minutes", "");
            if (tmp.length() != duration.length()) {
                duration = tmp;
                tmp = StringUtils.replaceOnce(duration, " 0 hours", "");
                if (tmp.length() != duration.length()) {
                    duration = tmp;
                    tmp = StringUtils.replaceOnce(duration, " 0 days", "");
                    if (tmp.length() != duration.length()) {
                        duration = tmp;
                        tmp = StringUtils.replaceOnce(duration, " 0 months", "");
                        duration = tmp;
                        tmp = StringUtils.replaceOnce(duration, " 0 years", "");
                    }
                }
            }
        }
        
        // handle plurals
        duration = " " + duration;
        duration = StringUtils.replaceOnce(duration, " 1 minutes", " 1 minute");
        duration = StringUtils.replaceOnce(duration, " 1 hours", " 1 hour");
        duration = StringUtils.replaceOnce(duration, " 1 days", " 1 day");
        duration = StringUtils.replaceOnce(duration, " 1 months", " 1 month");
        duration = StringUtils.replaceOnce(duration, " 1 years", " 1 year");
        return duration.trim();
    }

    /**
     * <p>Translate a duration text by replacing tokens by their equivalent value found in a set of tokens</p>
     * <p>
     * tokens are composed of :
     * <ul>
     * <li>key = the token to find in duration text</li>
     * <li>value = the text replacement for the key</li>
     * </ul>
     * </p>
     * @param durationText the duration text to translate
     * @param tokens the set of tokens to find and replace inside the duration template
     * @return translation of durationTemplate
     */
    public static String translateDurationTextValue(String durationText, HashMap<String,String> tokens) {
    	
		// Create pattern of the format "(key1|key2)"
		String regularExpression = "(" + StringUtils.join(tokens.keySet(), "|") + ")";
		Pattern pattern = Pattern.compile(regularExpression);
		Matcher matcher = pattern.matcher(durationText);

		// Do the replacement for each expression found
		StringBuffer stringBuffer = new StringBuffer();
		while(matcher.find())
		    matcher.appendReplacement(stringBuffer, tokens.get(matcher.group(1)));
		matcher.appendTail(stringBuffer);
		
		return stringBuffer.toString();
    }
    
    /**
     * Convert a decimal hours duration value to milliseconds, generate corresponding English text representation, 
     * then translate text representation to current Locale using tokens
     * @param duration decimal hours duration value
     * @param tokens duration translation tokens for current Locale
     * @return text translation for the given duration
     */
	public static String translateDurationDoubleValue(Double duration, HashMap<String,String> tokens) {
		
		int hours = (int) duration.doubleValue();
		double decimalMinutes = ((duration - hours) * 60d);
		int minutes = (int) decimalMinutes;
		int seconds = (int) ((decimalMinutes - minutes) * 60d);
		
		long milliseconds = (hours * 3600 + minutes * 60 + seconds) * 1000L;
		
		return Strings.translateDurationTextValue(formatDurationWords(milliseconds, true, true), tokens);
	}
	
}
