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
package commons.api;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Naeregwen
 *
 */
@XmlRootElement(name = "steamFriend")
@XmlType (propOrder = { 
	"name",
	"steamID64",
	"logo",
	"onlineState",
	"stateMessage"
})
public class SteamFriend {

	public static enum ColumnsOrder {
		
		logo("friendHeaderLogo"),
		name("friendHeaderName"),
		onlineState("friendHeaderOnlineState"),
		stateMessage("friendHeaderStateMessage"),
		steamID64("friendHeaderSteamID64");
		
		String headerName;
		
		ColumnsOrder(String headerName) {
			this.headerName = headerName;
		}

		/**
		 * @return the headerName
		 */
		public String getHeaderName() {
			return headerName;
		}	
		
	}

}
