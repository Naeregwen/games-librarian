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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Naeregwen
 *
 */
@XmlRootElement (name = "steamAchievement")
//@XmlAccessorType(XmlAccessType.FIELD)
@XmlType (propOrder = { 
	"name",
	"description",
	"unlockTimestamp",
	"iconClosed",
	"iconOpen",
	"apiname",
	"initialPosition"
})
public class SteamAchievement {

	Boolean closed;
	
	String name;
	String description;
	String unlockTimestamp;
	String iconClosed;
	String iconOpen;
	String apiname;
	
	Integer initialPosition;
	
	/**
	 * 
	 */
	public SteamAchievement() {}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	@XmlElement
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the unlockTimestamp
	 */
	public String getUnlockTimestamp() {
		return unlockTimestamp;
	}

	/**
	 * @param unlockTimestamp the unlockTimestamp to set
	 */
	@XmlElement
	public void setUnlockTimestamp(String unlockTimestamp) {
		this.unlockTimestamp = unlockTimestamp;
	}

	/**
	 * @return the closed
	 */
	public Boolean isClosed() {
		return closed;
	}

	/**
	 * @param closed the closed to set
	 */
	@XmlAttribute
	public void setClosed(Boolean closed) {
		this.closed = closed;
	}

	/**
	 * @return the iconClosed
	 */
	public String getIconClosed() {
		return iconClosed;
	}

	/**
	 * @param iconClosed the iconClosed to set
	 */
	@XmlElement
	public void setIconClosed(String iconClosed) {
		this.iconClosed = iconClosed;
	}

	/**
	 * @return the iconOpen
	 */
	public String getIconOpen() {
		return iconOpen;
	}

	/**
	 * @param iconOpen the iconOpen to set
	 */
	@XmlElement
	public void setIconOpen(String iconOpen) {
		this.iconOpen = iconOpen;
	}

	/**
	 * @return the apiname
	 */
	public String getApiname() {
		return apiname;
	}

	/**
	 * @param apiname the apiname to set
	 */
	@XmlElement
	public void setApiname(String apiname) {
		this.apiname = apiname;
	}

	
	/**
	 * @return the initialPosition
	 */
	public Integer getInitialPosition() {
		return initialPosition;
	}

	/**
	 * @param initialPosition the initialPosition to set
	 */
	@XmlElement
	public void setInitialPosition(Integer initialPosition) {
		this.initialPosition = initialPosition;
	}

	/*/
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getName() + "[name=" + name + ", initialPosition=" + initialPosition + ", closed=" + closed + ", unlockTimestamp=" + unlockTimestamp + ", apiname=" + apiname + ", description=" + description + ", iconClosed=" + iconClosed + ", iconOpen=" + iconOpen + "]";
	}
	
	//
	// Utilities
	//
	
	/**
	 * get appropriate icon URL
	 * @return
	 */
	public String getIconURL() {
		return closed ? iconClosed : iconOpen;
	}
	
	/**
	 * xmlStamp returned by Steam API are 1000 times less than java long used by java.util.Date#Date(long date)
	 * @see java.util.Date#Date(long date)
	 * 
	 * @param xmlStamp
	 * @return
	 */
	public static long steamStampToEpochStamp(String xmlStamp) {
		return Long.parseLong(xmlStamp) * 1000;
	} 
}
