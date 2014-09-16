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

import javax.swing.ImageIcon;

import commons.GamesLibrarianIcons;
import commons.enums.interfaces.GamesLibrarianActionEnum;

/**
 * @author Naeregwen
 *
 */
public enum SteamGroupsSortMethod implements GamesLibrarianActionEnum {

	InitialAscendingOrder("initialOrder", GamesLibrarianIcons.steamGroupsSortMethodInitialAscendingOrderIcon),
	NameAscendingOrder("nameAscendingOrder", GamesLibrarianIcons.steamGroupsSortMethodNameAscendingOrderIcon),
	NameDescendingOrder("nameDescendingOrder", GamesLibrarianIcons.steamGroupsSortMethodNameDescendingOrderIcon),
	LogoAscendingOrder("logoAscendingOrder", GamesLibrarianIcons.steamGroupsSortMethodLogoAscendingOrderIcon),
	LogoDescendingOrder("logoDescendingOrder", GamesLibrarianIcons.steamGroupsSortMethodLogoDescendingOrderIcon),
	HeadlineAscendingOrder("headlineAscendingOrder", GamesLibrarianIcons.steamGroupsSortMethodHeadlineAscendingOrderIcon),
	HeadlineDescendingOrder("headlineDescendingOrder", GamesLibrarianIcons.steamGroupsSortMethodHeadlineDescendingOrderIcon),
	SummaryAscendingOrder("summaryAscendingOrder", GamesLibrarianIcons.steamGroupsSortMethodSummaryAscendingOrderIcon),
	SummaryDescendingOrder("summaryDescendingOrder", GamesLibrarianIcons.steamGroupsSortMethodSummaryDescendingOrderIcon),
	SteamId64AscendingOrder("steamId64AscendingOrder", GamesLibrarianIcons.steamGroupsSortMethodSteamId64AscendingOrderIcon),
	SteamId64DescendingOrder("steamId64DescendingOrder", GamesLibrarianIcons.steamGroupsSortMethodSteamId64DescendingOrderIcon);

	String labelKey;
	ImageIcon icon;
	
	SteamGroupsSortMethod(String labelKey, ImageIcon icon) {
		this.labelKey = labelKey;
		this.icon = icon;
	}

	/**
	 * @return the labelKey
	 */
	@Override
	public String getLabelKey() {
		return labelKey;
	}

	/**
	 * @return the icon
	 */
	@Override
	public ImageIcon getIcon() {
		return icon;
	}
	
}
