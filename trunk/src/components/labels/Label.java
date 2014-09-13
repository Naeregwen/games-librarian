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
package components.labels;

import javax.swing.JLabel;

import commons.BundleManager;
import components.GamesLibrarian.WindowBuilderMask;

/**
 * @author Naeregwen
 *
 */
public class Label extends JLabel {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4096860122866218141L;
	
	public Label(WindowBuilderMask me, String key) {
		super(BundleManager.getUITexts(me, key));
	}

}
