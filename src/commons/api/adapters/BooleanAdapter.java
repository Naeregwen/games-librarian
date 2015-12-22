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
package commons.api.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author Naeregwen
 *
 */
public class BooleanAdapter extends XmlAdapter<Boolean, Boolean>{

	@Override
	public Boolean unmarshal(Boolean v) throws Exception {
		return Boolean.TRUE.equals(v);
	}

	@Override
	public Boolean marshal(Boolean v) throws Exception {
		return Boolean.TRUE.equals(v);
//		if (v) {
//            return v;
//        }
//        return null;
	}

//	@Override
//	public Boolean unmarshal(String value) throws Exception {
//		return value == null ? false : (value.equals("1") || value.equalsIgnoreCase("true") ? true : false);
//	}
//
//	@Override
//	public String marshal(Boolean value) throws Exception {
//		return value == null ? "0" : (value ? "1" : "0");
//	}

}
