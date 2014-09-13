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
package components.comboboxes;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JToolTip;
import javax.swing.ListCellRenderer;

import commons.api.SteamGamesList;
import commons.api.SteamProfile;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.comboboxes.commons.SortedComboBoxModel;
import components.comboboxes.renderers.KnownProfilesComboBoxRenderer;
import components.commons.JScrollableToolTip;
import components.commons.adapters.SteamObjectsMouseAdapter;

/**
 * @author Naeregwen
 *
 */
public class KnownProfilesComboBox extends JComboBox<SteamProfile> implements ItemListener {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4849948849703234395L;

    JScrollableToolTip tooltip;

	Librarian librarian;
	
	@SuppressWarnings("unchecked")
	public KnownProfilesComboBox(WindowBuilderMask me) {
		super((ComboBoxModel<SteamProfile>)new SortedComboBoxModel<SteamProfile>());
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		setRenderer(new KnownProfilesComboBoxRenderer((ListCellRenderer<SteamProfile>) this.getRenderer()));
		// Set tooltip
		if (getSelectedItem() != null)
			setToolTipText(((SteamProfile)getSelectedItem()).getTooltipText());
		addItemListener(this);
		addMouseListener(new SteamObjectsMouseAdapter());
	}
	
	public void addProfile(SteamProfile item) {
		for (int index = 0; index < getItemCount(); index++) {
			SteamProfile steamProfile = getItemAt(index);
			if ((item.getSteamID64() != null && steamProfile.getSteamID64() != null && item.getSteamID64().equalsIgnoreCase(steamProfile.getSteamID64()))
					|| (item.getSteamID() != null && steamProfile.getSteamID() != null && item.getSteamID().equalsIgnoreCase(steamProfile.getSteamID()))) {
				removeItem(steamProfile);
				break;
			}
		}
		addItem(item);
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#createToolTip()
	 */
	@Override
	public JToolTip createToolTip() {
		tooltip = new JScrollableToolTip(getGraphicsConfiguration().getDevice().getDisplayMode().getWidth()/2, getGraphicsConfiguration().getDevice().getDisplayMode().getHeight()/2);
		return tooltip;
    }

	/*
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#getToolTipText(java.awt.event.MouseEvent)
	 */
	@Override
	public String getToolTipText(MouseEvent event) {
		return ((SteamProfile)((KnownProfilesComboBox) event.getSource()).getSelectedItem()).getTooltipText();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(ItemEvent itemEvent) {
		if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
			SteamProfile steamProfile = (SteamProfile) itemEvent.getItem();
			// Update Profile Tab
			librarian.updateProfileTab(steamProfile);
			// Update Library Tab
			librarian.updateGamesLibraryTab(new SteamGamesList(steamProfile));
			// Set tool tip based on profile data
			setToolTipText(((SteamProfile)getSelectedItem()).getTooltipText());
		}
	}
}
