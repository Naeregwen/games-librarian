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
package commons;

import java.util.ResourceBundle;

import components.Librarian;
import components.GamesLibrarian.WindowBuilderMask;

/**
 * 
 * Allow us to manage bundle keys search usable
 * <ul>
 * <li>through design/preview mode of WindowBuilder,</li>
 * <li>through development times as shortcut calls,</li>
 * <li>through runtime</li>
 * </ul>
 * only with 
 * <ul>
 * <li>known bundle key</li>
 * <li>known bundle location (embedded in prototype of static call name)</li>
 * </ul>
 * 
 * @author Naeregwen
 * 
 */
public class BundleManager {

	Librarian librarian;

	public BundleManager(WindowBuilderMask me) {
		this.librarian = me.getLibrarian();
	}

	/**
	 * Not needed to be visible anymore. 
	 * The method is called through static call
	 * 
	 * @param key
	 * @return
	 */
	private String getUITexts(String key) {
		return librarian.getParameters().getUITexts().getString(key);
	}

	/**
	 * Visible call to search key inside named bundle UITexts
	 * 
	 * @param me
	 * @param key
	 * @return
	 */
	public static String getUITexts(WindowBuilderMask me, String key) {
		return me != null && me.getLibrarian() != null && me.getLibrarian().getBundleManager() != null ? // WindowBuilder
				me.getLibrarian().getBundleManager().getUITexts(key)
				: ResourceBundle.getBundle("i18n/UITexts").getString(key);
	}

	/**
	 * Not needed to be visible anymore. 
	 * The method is called through static call
	 * 
	 * @param key
	 * @return
	 */
	private String getMessages(String key) {
		return librarian.getParameters().getMessages().getString(key);
	}

	/**
	 * Visible call to search key inside named bundle UITexts
	 * 
	 * @param me
	 * @param key
	 * @return
	 */
	public static String getMessages(WindowBuilderMask me, String key) {
		return me != null && me.getLibrarian() != null && me.getLibrarian().getBundleManager() != null ? // WindowBuilder
				me.getLibrarian().getBundleManager().getMessages(key)
				: ResourceBundle.getBundle("i18n/messages").getString(key);
	}

	/**
	 * Not needed to be visible anymore. 
	 * The method is called through static call
	 * 
	 * @param key
	 * @return
	 */
	private String getResources(String key) {
		return librarian.getParameters().getResources().getString(key);
	}

	/**
	 * Visible call to search key inside named bundle Resources
	 * 
	 * @param me
	 * @param key
	 * @return
	 */
	public static String getResources(WindowBuilderMask me, String key) {
		return me != null && me.getLibrarian() != null && me.getLibrarian().getBundleManager() != null ? // WindowBuilder
				me.getLibrarian().getBundleManager().getResources(key)
				: ResourceBundle.getBundle("i18n/resources").getString(key);
	}

}
