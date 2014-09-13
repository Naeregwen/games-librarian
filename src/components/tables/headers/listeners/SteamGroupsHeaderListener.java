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
package components.tables.headers.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import components.Librarian;
import components.tables.models.SteamGroupsTableModel;

/**
 * @author Naeregwen
 *
 */
public class SteamGroupsHeaderListener extends MouseAdapter {

	Librarian librarian;
	JTableHeader header;

	public SteamGroupsHeaderListener(Librarian librarian, JTableHeader header) {
		this.librarian = librarian;
		this.header = header;
	}

	public void mouseClicked(MouseEvent e) {
		TableModel model = header.getTable().getModel();
		if (model instanceof SteamGroupsTableModel) {
			librarian.updateSteamGroupsSortMethod();
		}
	}
}
