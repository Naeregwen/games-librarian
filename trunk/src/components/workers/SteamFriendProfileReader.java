package components.workers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import commons.ColoredTee;
import commons.ColoredTee.TeeColor;
import commons.api.Parameters;
import commons.api.Steam;
import commons.api.SteamProfile;
import commons.api.parsers.SteamProfileParser;
import components.Librarian;
import components.workers.responsehandlers.XMLResponseHandler;

public class SteamFriendProfileReader extends SwingWorker<SteamProfile, String> {

	Librarian librarian;
	String steamId64;
	CountDownLatch doneSignal;
	int index;
	
	public SteamFriendProfileReader(Librarian librarian, String steamId64, CountDownLatch doneSignal, int index) {
		this.librarian = librarian;
		this.steamId64 = steamId64;
		this.doneSignal = doneSignal;
		this.index = index;
	}

	/**
	 * @return the steamId64
	 */
	public String getSteamId64() {
		return steamId64;
	}

	/*/
	 * Read friend profile
	 * 
	 * (non-Javadoc)
	 * @see javax.swing.SwingWorker#doInBackground()
	 */
	@Override
	protected SteamProfile doInBackground() throws Exception {
		
		if (steamId64 == null) return null;
		
		Parameters parameters = librarian.getParameters();
		ResourceBundle messages = parameters.getMessages();
		SteamProfileParser steamProfileParser = new SteamProfileParser(parameters, librarian.getTee(), index);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		try {
			
	    	String url = Steam.profileURLCommand(steamId64);
	    	publish(TeeColor.Message.name(), String.format(messages.getString("infosReadProfileIsStarting"), index), TeeColor.Info.name(), url);
	    	
	    	new URL(url); // Reject malformed URL
	    	
			XMLReader steamProfileReader = XMLReaderFactory.createXMLReader();
			steamProfileReader.setContentHandler(steamProfileParser);
			HttpGet httpget = new HttpGet(url);
			publish("SteamFriendProfileReader Executing request " + httpget.getRequestLine());
			
            Exception exception = httpclient.execute(httpget, new XMLResponseHandler(steamProfileReader));
            if (exception != null)
            	publish(TeeColor.Error.name(), String.format(messages.getString("errorExceptionMessageWithSteamID64"), steamId64, 
            			 exception.getClass().getName(), exception.getLocalizedMessage()));
            
		} catch (CancellationException e) {
			publish("SteamFriendProfileReader " + steamId64 + " cancelled during doInBackground");
		} finally {
			httpclient.close();
		}
    	return steamProfileParser != null ? steamProfileParser.getSteamProfile() : null;
	}

	/*/
	 * Update runtime data and UI
	 * 
	 * (non-Javadoc)
	 * @see javax.swing.SwingWorker#done()
	 */
	@Override
	protected void done() {
		if (isCancelled()) {
			librarian.getTee().writelnMessage("SteamFriendProfileReader " + steamId64 + " cancelled before done");
		} else {
			try {
				SteamProfile steamProfile = get();
				if (steamProfile != null) {
					// Update profiles list
					librarian.addProfile(steamProfile, false);
					// Add/Copy To main profile friends list
					librarian.getCurrentSteamProfile().addFriend(steamProfile);
					// Add to ui as button in friends panel
					librarian.addFriendButton(steamProfile);
				}
			} catch (InterruptedException e) {
				librarian.getTee().writelnMessage("SteamFriendProfileReader " + steamId64 + " interrupted during done");
				librarian.getTee().printStackTrace(e);
			} catch (CancellationException e) {
				librarian.getTee().writelnInfos("SteamFriendProfileReader " + steamId64 + " cancelled during done");
			} catch (ExecutionException e) {
				librarian.getTee().writelnMessage("SteamFriendProfileReader " + steamId64 + " execution exception during done");
				librarian.getTee().printStackTrace(e);
			}
		}
		doneSignal.countDown();
	}
	
	/*/
	 * (non-Javadoc)
	 * @see javax.swing.SwingWorker#process(java.util.List)
	 */
	@Override
    protected void process(List<String> strings) {
		ColoredTee.writeln(librarian.getTee(), strings);
    }

}
