/**
 * 
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
