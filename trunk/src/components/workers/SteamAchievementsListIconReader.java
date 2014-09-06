/**
 * 
 */
package components.workers;

import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javax.swing.ImageIcon;
import javax.swing.SwingWorker;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import commons.ColoredTee;
import commons.api.SteamAchievementsList;
import components.workers.responsehandlers.ImageIconResponseHandler;

/**
 * @author Naeregwen
 *
 */
public class SteamAchievementsListIconReader extends SwingWorker<ImageIcon, String> {

	ColoredTee tee;
	SteamAchievementsList steamAchievementsList;
	String playerAvatarIconURL;
	ResourceBundle messages;
	Exception exception;
	
	public SteamAchievementsListIconReader(ColoredTee tee, SteamAchievementsList steamAchievementsList, String playerAvatarIconURL, ResourceBundle messages) {
		this.tee = tee;
		this.steamAchievementsList = steamAchievementsList;
		this.playerAvatarIconURL = playerAvatarIconURL;
		this.messages = messages;
	}
	
	@Override
	protected ImageIcon doInBackground() throws Exception {
		
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(playerAvatarIconURL);
        publish("IconPaneReader Executing request " + httpget.getRequestLine());
        
        try {
            return httpclient.execute(httpget, new ImageIconResponseHandler());
        } finally {
            httpclient.close();
        }
	}

	/*/
	 * (non-Javadoc)(
	 * @see javax.swing.SwingWorker#done()
	 */
	@Override
	protected void done() {
		try {
			ImageIcon icon = get();
			if (exception != null) {
				tee.printStackTrace(exception);
			} else if (icon != null && icon.getImage() != null && icon.getIconHeight() >= 0 && icon.getIconWidth() >= 0) {
				steamAchievementsList.setPlayerAvatarIcon(icon);
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
