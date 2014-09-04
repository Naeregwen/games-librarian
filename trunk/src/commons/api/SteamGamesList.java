/**
 * 
 */
package commons.api;

import java.util.Vector;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import commons.GamesLibrary.LoadingSource;


/**
 * @author Naeregwen
 *
 */

@XmlRootElement (name = "steamGamesList")
@XmlType (propOrder = { 
	"steamID64",
	"steamID",
	"loadingSource",
	"steamGames"
})
public class SteamGamesList {
	
	String steamID64;
	String steamID;
	Vector<SteamGame> steamGames;
	
	LoadingSource loadingSource;

	/**
	 * Initialize base members
	 */
	public SteamGamesList() {
		steamGames = new Vector<SteamGame>();
	}
	
	/**
	 * Initialization based on another profile  
	 */
	public SteamGamesList(SteamProfile steamProfile) {
		steamID64 = steamProfile.getSteamID64();
		steamID = steamProfile.getSteamID();
		loadingSource = steamProfile.getLoadingSource();
		steamGames = steamProfile.getSteamGames();
	}
	
	/**
	 * @return the steamID64
	 */
	public String getSteamID64() {
		return steamID64;
	}

	/**
	 * @param steamID64 the steamID64 to set
	 */
	@XmlElement
	public void setSteamID64(String steamID64) {
		this.steamID64 = steamID64;
	}

	/**
	 * @return the steamID
	 */
	public String getSteamID() {
		return steamID;
	}

	/**
	 * @param steamID the steamID to set
	 */
	@XmlElement
	public void setSteamID(String steamID) {
		this.steamID = steamID;
	}

	/**
	 * @return the steamGames
	 */
	public Vector<SteamGame> getSteamGames() {
		return steamGames;
	}

	/**
	 * @param steamGames the steamGames to set
	 */
	@XmlElements(value = { @XmlElement(name = "steamGame", type = SteamGame.class) })
	@XmlElementWrapper(name = "steamGames")
	public void setSteamGames(Vector<SteamGame> steamGames) {
		this.steamGames = steamGames;
	}

	/**
	 * @return the loadingSource
	 */
	public LoadingSource getLoadingSource() {
		return loadingSource;
	}

	/**
	 * @param loadingSource the loadingSource to set
	 */
	@XmlElement
	public void setLoadingSource(LoadingSource loadingSource) {
		this.loadingSource = loadingSource;
	}
	
	/**
	 * @param steamGame the steamGame to add
	 */
	public void add(SteamGame steamGame) {
		steamGames.add(steamGame);
	}

}
