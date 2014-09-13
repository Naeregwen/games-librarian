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

import java.util.List;
import java.util.Vector;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import commons.GamesLibrary;

/**
 * @author Naeregwen
 *
 */
@XmlRootElement(name = "steamGroup")
@XmlType (propOrder = { 
	"groupID64", 
	"initialPosition",
	
	"groupName", 
	"groupURL", 
	
	"headline", 
	"summary", 
	
	"avatarIcon", 
	"avatarMedium", 
	"avatarFull", 
	
	"memberCount", 
	"membersInChat", 
	"membersInGame", 
	"membersOnline"
})
public class SteamGroup {

	public static enum ColumnsOrder { 
		
		logo("groupHeaderLogo"),
		name("groupHeaderName"),
		headline("groupHeaderHeadline"),
		summary("groupHeaderSummary"),
		groupID64("groupHeaderGroupID64");
		
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
		
	};
	
	Boolean primary;
	String groupID64;
	Integer initialPosition;
	
	String groupName;
	String groupURL;
	
	String headline;
	String summary;
	
	String avatarIcon;
	String avatarMedium;
	String avatarFull;
	
	Integer memberCount;
	Integer membersInChat;
	Integer membersInGame;
	Integer membersOnline;
	
	/**
	 * @return the primary
	 */
	public Boolean getPrimary() {
		return primary;
	}
	/**
	 * @param primary the primary to set
	 */
	@XmlAttribute
	public void setPrimary(Boolean primary) {
		this.primary = primary;
	}
	/**
	 * @return the groupID64
	 */
	public String getGroupID64() {
		return groupID64;
	}
	/**
	 * @param groupID64 the groupID64 to set
	 */
	@XmlElement
	public void setGroupID64(String groupID64) {
		this.groupID64 = groupID64;
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
	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * @param groupName the groupName to set
	 */
	@XmlElement
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	/**
	 * @return the groupURL
	 */
	public String getGroupURL() {
		return groupURL;
	}
	/**
	 * @param groupURL the groupURL to set
	 */
	@XmlElement
	public void setGroupURL(String groupURL) {
		this.groupURL = groupURL;
	}
	/**
	 * @return the headline
	 */
	public String getHeadline() {
		return headline;
	}
	/**
	 * @param headline the headline to set
	 */
	@XmlElement
	public void setHeadline(String headline) {
		this.headline = headline;
	}
	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}
	/**
	 * @param summary the summary to set
	 */
	@XmlElement
	public void setSummary(String summary) {
		this.summary = summary;
	}
	/**
	 * @return the avatarIcon
	 */
	public String getAvatarIcon() {
		return avatarIcon;
	}
	/**
	 * @param avatarIcon the avatarIcon to set
	 */
	@XmlElement
	public void setAvatarIcon(String avatarIcon) {
		this.avatarIcon = avatarIcon;
	}
	/**
	 * @return the avatarMedium
	 */
	public String getAvatarMedium() {
		return avatarMedium;
	}
	/**
	 * @param avatarMedium the avatarMedium to set
	 */
	@XmlElement
	public void setAvatarMedium(String avatarMedium) {
		this.avatarMedium = avatarMedium;
	}
	/**
	 * @return the avatarFull
	 */
	public String getAvatarFull() {
		return avatarFull;
	}
	/**
	 * @param avatarFull the avatarFull to set
	 */
	@XmlElement
	public void setAvatarFull(String avatarFull) {
		this.avatarFull = avatarFull;
	}
	/**
	 * @return the memberCount
	 */
	public Integer getMemberCount() {
		return memberCount;
	}
	/**
	 * @param memberCount the memberCount to set
	 */
	@XmlElement
	public void setMemberCount(Integer memberCount) {
		this.memberCount = memberCount;
	}
	/**
	 * @return the membersInChat
	 */
	public Integer getMembersInChat() {
		return membersInChat;
	}
	/**
	 * @param membersInChat the membersInChat to set
	 */
	@XmlElement
	public void setMembersInChat(Integer membersInChat) {
		this.membersInChat = membersInChat;
	}
	/**
	 * @return the membersInGame
	 */
	public Integer getMembersInGame() {
		return membersInGame;
	}
	/**
	 * @param membersInGame the membersInGame to set
	 */
	@XmlElement
	public void setMembersInGame(Integer membersInGame) {
		this.membersInGame = membersInGame;
	}
	/**
	 * @return the membersOnline
	 */
	public Integer getMembersOnline() {
		return membersOnline;
	}
	/**
	 * @param membersOnline the membersOnline to set
	 */
	@XmlElement
	public void setMembersOnline(Integer membersOnline) {
		this.membersOnline = membersOnline;
	}	
	
	/**
	 * Determine ID between groupName and groupID64 in this order
	 * @return ID if found, null otherwise
	 */
	public String getId() {
		return groupName != null && groupName.length() > 0 ? groupName : groupID64 != null && groupID64.length() > 0 ? groupID64 : null;
	}
	
	/**
	 * Build tooltip content
	 * @return tooltip content
	 */
	public String getTooltipText() {
		StringBuffer tooltipText = new StringBuffer("<html>");
		tooltipText.append("<table border='0'>");
		tooltipText.append("<tr>");
		tooltipText.append("<td rowspan='2'>");
		if (getAvatarFull() != null)
			tooltipText.append("<img src='"+getAvatarFull()+"'/>");
		else
			tooltipText.append("<img src='"+GamesLibrary.noAvatarFull+"'/>");
		tooltipText.append("</td>");
		tooltipText.append("<td>");
		tooltipText.append("<h1>");
		tooltipText.append(getGroupName() != null ? getGroupName() : (getGroupID64() != null ? getGroupID64() : ""));
		tooltipText.append("</h1>");
		tooltipText.append("</td>");
		tooltipText.append("</tr>");
		tooltipText.append("<tr>");
		tooltipText.append("<td>");
		
		if (getHeadline() != null) {
			tooltipText.append("<h2>");
			tooltipText.append(getHeadline());
			tooltipText.append("</h2>");
		}
		tooltipText.append("</td>");
		tooltipText.append("</tr>");
		tooltipText.append("</table>");
		
		if (getSummary() != null)
			tooltipText.append(getSummary());
		tooltipText.append("</html>");
		
		return tooltipText.toString();
	}

	public List<String> toStringList(String prefix) {
		
		List<String> result = new Vector<String>();
		
		result.add(prefix + " - groupID64 :" + (groupID64 != null ? groupID64 : "null"));
		result.add(prefix + " - groupName :" + (groupName != null ? groupName : "null"));
		result.add(prefix + " - groupURL :" + (groupURL != null ? groupURL : "null"));
		result.add(prefix + " - headline :" + (headline != null ? headline : "null"));
		result.add(prefix + " - summary :" + (summary != null ? summary : "null"));
		result.add(prefix + " - tavatarIcon :" + (avatarIcon != null ? avatarIcon : "null"));
		result.add(prefix + " - avatarMedium :" + (avatarMedium != null ? avatarMedium : "null"));
		result.add(prefix + " - avatarFull :" + (avatarFull != null ? avatarFull : "null"));
		result.add(prefix + " - memberCount :" + (memberCount != null ? memberCount : "null"));
		result.add(prefix + " - membersInChat :" + (membersInChat != null ? membersInChat : "null"));
		result.add(prefix + " - membersInGame :" + (membersInGame != null ? membersInGame : "null"));
		result.add(prefix + " - membersOnline :" + (membersOnline != null ? membersOnline : "null"));
		
		return result;
	}
}
