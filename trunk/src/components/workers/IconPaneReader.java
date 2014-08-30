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
import javax.swing.SwingWorker;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import commons.ColoredTee;
import components.containers.IconPane;
import components.workers.responsehandlers.BufferedImageResponseHandler;

/**
 * @author Naeregwen
 *
 */
public class IconPaneReader extends SwingWorker<BufferedImage, String>  {

	private ColoredTee tee;
	IconPane iconPane;
	String uri;
	IOException io;
	
	public IconPaneReader(ColoredTee tee, IconPane iconPane, String uri) {
		this.tee = tee;
		this.iconPane = iconPane;
		this.uri = uri;
	}

	/*/
	 * (non-Javadoc)
	 * @see javax.swing.SwingWorker#doInBackground()
	 */
	@Override
	protected BufferedImage doInBackground() throws Exception {
		if (uri.trim().toLowerCase().startsWith("http")) {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpGet httpget = new HttpGet(uri);
			publish("IconPaneReader Executing request " + httpget.getRequestLine());
			try {
				return httpclient.execute(httpget, new BufferedImageResponseHandler());
			} finally {
				httpclient.close();
			}
		} else {
			try {
				return ImageIO.read(new URL(uri));
			} catch (MalformedURLException e) {
				return null;
			} catch (IOException ie) {
				return null;
			}
		}
	}
	
	/*/
	 * (non-Javadoc)
	 * @see javax.swing.SwingWorker#done()
	 */
	@Override
	protected void done() {
		try {
			if (io != null)
				tee.printStackTrace(io);
			else if (get() != null) 
				iconPane.setImage(get());
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
