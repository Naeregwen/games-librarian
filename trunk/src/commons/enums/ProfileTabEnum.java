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
package commons.enums;

import javax.swing.Icon;

import commons.GamesLibrarianIcons;

/**
 * @author Naeregwen
 *
 */
public enum ProfileTabEnum implements TabEnum {

	Summary ("gotoSummaryMenuLabel", "gotoSummaryMnemonic", "gotoSummaryAccelerator", GamesLibrarianIcons.accountSummaryIcon),
	Status ("gotoStatusMenuLabel", "gotoStatusMnemonic", "gotoStatusAccelerator", GamesLibrarianIcons.accountStatusIcon),
	Groups ("gotoGroupsMenuLabel", "gotoGroupsMnemonic", "gotoGroupsAccelerator", GamesLibrarianIcons.groupsIcon),
	Friends ("gotoFriendsMenuLabel", "gotoFriendsMnemonic", "gotoFriendsAccelerator", GamesLibrarianIcons.friendsIcon);
	
	String labelKey;
	String mnemonicKey;
	String acceleratorKey;
	Icon icon;

	ProfileTabEnum(String labelKey, String mnemonicKey, String acceleratorKey, Icon icon) {
		this.labelKey = labelKey;
		this.mnemonicKey = mnemonicKey;
		this.acceleratorKey = acceleratorKey;
		this.icon = icon;
	}

	/* (non-Javadoc)
	 * @see commons.enums.TabEnum#getLabelKey()
	 */
	@Override
	public String getLabelKey() {
		return labelKey;
	}

	@Override
	/* (non-Javadoc)
	 * @see commons.enums.TabEnum#getMnemonicKey()
	 */
	public String getMnemonicKey() {
		return mnemonicKey;
	}

	/* (non-Javadoc)
	 * @see commons.enums.TabEnum#getAcceleratorKey()
	 */
	@Override
	public String getAcceleratorKey() {
		return acceleratorKey;
	}

	/* (non-Javadoc)
	 * @see commons.enums.TabEnum#getIcon()
	 */
	@Override
	public Icon getIcon() {
		return icon;
	}

	/* (non-Javadoc)
	 * @see commons.enums.TabEnum#isMain()
	 */
	@Override
	public boolean isMain() {
		return false;
	}

}
