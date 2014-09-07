/**
 * 
 */
package commons.api.scrapers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Vector;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import commons.api.SteamAchievement;

/**
 * @author Naeregwen
 * 
 */
public class SteamAchievementsListScraper {

	String htmlURL;

	public SteamAchievementsListScraper(String htmlURL) {
		this.htmlURL = htmlURL;
	}

	public Vector<SteamAchievement> getAchievements() {

		Elements achievementsIcons = null;
		Elements achievementsDescriptions = null;

		Vector<SteamAchievement> achievements = new Vector<SteamAchievement>();

		CloseableHttpClient httpclient = HttpClients.createDefault();

		try {
			HttpGet httpGet = new HttpGet(htmlURL);
			CloseableHttpResponse response = httpclient.execute(httpGet);

			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {

				HttpEntity entity = response.getEntity();
				String htmlResponse = entity != null ? EntityUtils.toString(entity) : null;
				Document document = Jsoup.parse(htmlResponse);

				achievementsIcons = document.select("div.achieveImgHolder > img");
				achievementsDescriptions = document.select("div.achieveTxtHolder");

				if (achievementsIcons.size() == 0
						|| achievementsDescriptions.size() == 0
						|| achievementsIcons.size() != achievementsDescriptions.size()) {
					return achievements;
				}

				// We expect the date in this format
				SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd MMM, yyyy @ hh:mmaa", Locale.US);

				/**
				 * <p>Guessing Steam achievements unlock time stamp from HTML representation :</p>
				 * 
				 * <p>To get more accurate data in Steam achievements unlock time stamp, we have to determine the TimeZone difference between :</p>
				 * 
				 * <ul>
				 * <li>the locale JVM on which the application is running</li>
				 * <li>and the provided HTML representation of Steam achievements unlock date</li>
				 * </ul>
				 * 
				 * <ul>
				 * <li>First, the provided Steam achievements unlock date is not accurate as it does not display enough informations (missing seconds and milliseconds)</li>
				 * <li>Second, the determination is empirical due to lack of documentation. So no guaranty.</li>
				 * </ul>
				 * 
				 * <p>Here is the path :</p>
				 * 
				 * <ul>
				 * <li>Valve Corporation is located on Bellevue, WA.</li>
				 * <li>The original Steam platform development must have been done near this location, and historically use this time zone as reference to store data.
				 * <li>Real time zone code for this location is PDT (Pacific Daylight Time).</li>
				 * <li>But, PDT is not a supported code in Java/ZoneInfo</li>
				 * <li>And unfortunately, PDT zone is divided into sub zones across multiple regions</li>
				 * </ul>
				 * 
				 * <p>We have the choice between these time zone codes :</p>
				 *
				 * <ul>
				 * <li>UTC-8 : Physical location of area, but Java unsupported. Eliminated.</li>
				 * <li>America/Vancouver : this is an canadian city. Eliminated.</li>
				 * <li>America/Los_Angeles : it does represent the same time zone and same country. Promoted.</li>
				 * </ul>
				 * 
				 * <p>With this choice, the difference between XML data and HTML data seems not to be more than 60 seconds.</p>
				 */
				inputDateFormat.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));

				Iterator<Element> achievementsDescriptionsIterator = achievementsDescriptions.iterator();
				int initialPosition = 0;

				while (achievementsDescriptionsIterator.hasNext()) {

					Element element = achievementsDescriptionsIterator.next();

					SteamAchievement achievement = new SteamAchievement();

					achievement.setInitialPosition(initialPosition);
					achievement.setName(element.select("div.achieveTxt > h3").text());
					achievement.setDescription(element.select("div.achieveTxt > h5").text());

					String unlockTime = element.select("div.achieveTxt > div.achieveUnlockTime").text();

					// Guessing time stamp based on guessed time zone
					if (!unlockTime.trim().equals("")) {
						// Unlocked: dd MMM, yyyy @ hh:mmam
						unlockTime = unlockTime.replaceAll("Unlocked: ", "");
						// Inject current year when year marker not found
						if (unlockTime.indexOf(",") == -1)
							unlockTime = unlockTime.replaceFirst(" @", ", " + new SimpleDateFormat("yyyy").format(new Date()) + " @");
						try {
							achievement.setUnlockTimestamp((new Long(inputDateFormat.parse(unlockTime).getTime() / 1000)).toString());
						} catch (ParseException e) {
							System.err.printf("%s is not parsable!%n", unlockTime);
						}
					}

					// Guessing closed status and icon URL properties based on unlockTime found
					if (initialPosition < achievementsIcons.size()) {
						String iconURL = achievementsIcons.get(initialPosition).attr("src");
						if (unlockTime.equals("")) {
							achievement.setClosed(false);
							achievement.setIconOpen(iconURL);
						} else {
							achievement.setClosed(true);
							achievement.setIconClosed(iconURL);
						}
					}

					achievements.add(achievement);
					initialPosition++;
				}

			} else {
				throw new ClientProtocolException("Unexpected response status: " + status);
			}

		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (achievementsIcons == null 
				|| achievementsIcons.size() == 0
				|| achievementsDescriptions == null
				|| achievementsDescriptions.size() == 0
				|| achievementsIcons.size() != achievementsDescriptions.size()
				|| achievements.size() != achievements.size()) {
			achievements.clear();
			return achievements;
		}

		return achievements;
	}
}
