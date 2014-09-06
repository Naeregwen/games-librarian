/**
 * 
 */
package components.workers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import commons.ColoredTee;
import components.containers.commons.RemoteIconButton;
import components.workers.responsehandlers.ImageIconResponseHandler;

/**
 * @author Naeregwen
 *
 */
public class RemoteIconReader extends SwingWorker<ImageIcon, String> {

	ColoredTee tee;
	RemoteIconButton remoteIconButton;
	
	private Exception exception;
	
	/**
	 * Create a RemoteIconReader
	 * @param tee
	 * @param remoteIconButton
	 */
	public RemoteIconReader(ColoredTee tee, RemoteIconButton remoteIconButton) {
		this.tee = tee;
		this.remoteIconButton = remoteIconButton;
	}

	/*/
	 * (non-Javadoc)
	 * @see javax.swing.SwingWorker#doInBackground()
	 */
	@Override
	protected ImageIcon doInBackground() throws Exception {
		if (remoteIconButton.getIconURL() != null && !remoteIconButton.getIconURL().trim().equals("")) {
			if (remoteIconButton.getIconURL().trim().toLowerCase().startsWith("http")) {
				
				CloseableHttpClient httpclient = HttpClients.createDefault();
				HttpGet httpget = new HttpGet(remoteIconButton.getIconURL());
				publish("RemoteIconReader Executing request " + httpget.getRequestLine());
				
				try {
					return httpclient.execute(httpget, new ImageIconResponseHandler());
				} finally {
					httpclient.close();
				}
			} else {
				
				publish("RemoteIconReader Executing request " + remoteIconButton.getIconURL());
				
				try {
					BufferedImage image = ImageIO.read(new URL(remoteIconButton.getIconURL()));
					return new ImageIcon(image);
				} catch (MalformedURLException e) {
					exception = e;
				} catch (IOException ie) {
					exception = ie;
				}
			}
		}
		return null;
	}

	/*/
	 * (non-Javadoc)
	 * @see javax.swing.SwingWorker#done()
	 */
	@Override
	protected void done() {
		try {
			ImageIcon icon = get();
			if (exception != null) {
				remoteIconButton.setAvatarIconAsResource();
				if (!(exception instanceof MalformedURLException))
					if (tee != null) tee.printStackTrace(exception);
			} else if (icon != null && icon.getImage() != null && icon.getIconHeight() > 0 && icon.getIconWidth() > 0) {
				if (remoteIconButton instanceof AbstractButton || remoteIconButton instanceof JLabel)
					remoteIconButton.setIcon(icon);
			} else {
				remoteIconButton.setAvatarIconAsResource();
			}
		} catch (InterruptedException | ExecutionException e) {
			if (tee != null) tee.printStackTrace(e);
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
