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
package components.workers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import commons.GamesLibrarianIcons;
import components.commons.ColoredTee;
import components.containers.commons.RemoteIconComponent;
import components.workers.responsehandlers.ImageIconResponseHandler;

/**
 * @author Naeregwen
 *
 */
public class RemoteIconComponentReader extends SwingWorker<ImageIcon, String> {

	ColoredTee tee;
	RemoteIconComponent remoteIconComponent;
	
	private Exception exception;
	
	/**
	 * Create a RemoteIconReader
	 * @param tee
	 * @param remoteIconButton
	 */
	public RemoteIconComponentReader(ColoredTee tee, RemoteIconComponent remoteIconComponent) {
		this.tee = tee;
		this.remoteIconComponent = remoteIconComponent;
	}

	@Override
	protected ImageIcon doInBackground() throws Exception {
		
		if ((remoteIconComponent.getIcon() != null && !remoteIconComponent.getIcon().equals(GamesLibrarianIcons.noAvatarIcon))
				|| (remoteIconComponent.getIconURL() != null && !remoteIconComponent.getIconURL().trim().equals("")))
			return null;
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		try {
			
			HttpGet httpget = new HttpGet(remoteIconComponent.getIconURL());
			publish("RemoteIconComponentReader Executing request " + httpget.getRequestLine());
			return httpclient.execute(httpget, new ImageIconResponseHandler());
			
		} catch (MalformedURLException e) {
			exception = e;
		} catch (IOException ie) {
			exception = ie;
		} finally {
			httpclient.close();
		}
		return null;
	}

	/*/
	 * (non-Javadoc)
	 * @see javax.swing.SwingWorker#done()
	 */
	@Override
	protected void done() {
		if (remoteIconComponent.getIcon() != null && !remoteIconComponent.getIcon().equals(GamesLibrarianIcons.noAvatarIcon))
			return;
		try {
			ImageIcon icon = get();
			if (exception != null) {
				remoteIconComponent.setAvatarIconAsResource();
				if (!(exception instanceof MalformedURLException))
					tee.printStackTrace(exception);
			} else if (icon != null && icon.getImage() != null && icon.getIconHeight() > 0 && icon.getIconWidth() > 0) {
				if (remoteIconComponent instanceof AbstractButton || remoteIconComponent instanceof JLabel)
					remoteIconComponent.setIcon(icon);
			} else {
				remoteIconComponent.setAvatarIconAsResource();
			}
		} catch (InterruptedException | ExecutionException e) {
			tee.printStackTrace(e);
		}
	}

	/*/
	 * (non-Javadoc)
	 * @see javax.swing.SwingWorker#process(java.util.List)
	 */
	@Override
    protected void process(List<String> strings) {
		ColoredTee.writeln(tee, strings);
    }
}
