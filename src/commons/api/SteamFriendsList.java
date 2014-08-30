/**
 * 
 */
package commons.api;

import java.util.Vector;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Naeregwen
 *
 */
@XmlRootElement (name = "friendsList")
@XmlType (propOrder = { 
	"steamID64", 
	"steamID", 
	
	"friends"
})
public class SteamFriendsList {

	String steamID64;
	String steamID;
	Vector<SteamProfile> friends;

	/**
	 * 
	 */
	public SteamFriendsList() {
		friends = new Vector<SteamProfile>();
	}

	/**
	 * @return the steamID64
	 */
	public String getSteamID64() {
		return steamID64;
	}

	/**
	 * @param steamID64 the steamID64 to set
	 */
	@XmlElement
	public void setSteamID64(String steamID64) {
		this.steamID64 = steamID64;
	}

	/**
	 * @return the steamID
	 */
	public String getSteamID() {
		return steamID;
	}

	/**
	 * @param steamID the steamID to set
	 */
	@XmlElement
	public void setSteamID(String steamID) {
		this.steamID = steamID;
	}

	/**
	 * @return the friends
	 */
	public Vector<SteamProfile> getFriends() {
		return friends;
	}

	/**
	 * @param friends the friends to set
	 */
	@XmlElements(value = { @XmlElement(name = "friend", type = SteamProfile.class) })
	@XmlElementWrapper(name = "friends")
	public void setFriends(Vector<SteamProfile> friends) {
		this.friends = friends;
	}
	
	/**
	 * Add a friend to the friends list
	 * @param friend the SteamProfile to add
	 */
	public void add(SteamProfile friend) {
		friends.add(friend);
	}

}
