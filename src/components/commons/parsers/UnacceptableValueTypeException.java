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

import commons.enums.exceptions.AcceptableValueType;

/**
 * @author Naeregwen
 *
 */
public class UnacceptableValueTypeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6003932071606778764L;

	AcceptableValueType acceptableValueType;
	
	/**
	 * 
	 */
	public UnacceptableValueTypeException(AcceptableValueType acceptableValueType) {
		this.acceptableValueType = acceptableValueType;
	}

}
