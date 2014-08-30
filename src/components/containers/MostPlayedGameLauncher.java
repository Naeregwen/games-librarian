/**
 * 
 */
package components.containers;

import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import commons.api.Parameters;
import commons.api.SteamGame;
import commons.enums.LaunchType;
import components.GamesLibrarian.WindowBuilderMask;
import components.Librarian;
import components.containers.remotes.LaunchButton;

/** 
 * @author Naeregwen
 *
 */
public class MostPlayedGameLauncher extends JPanel {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6259936472154426116L;

	Librarian librarian;
	LaunchButton mostPlayedGameButton;
	JLabel mostPlayedGameHoursPlayedLabel;
	JTextField mostPlayedGameHoursPlayed;
	JLabel mostPlayedGameHoursOnRecordLabel;
	JTextField mostPlayedGameHoursOnRecord;
	
	/**
	 * 
	 */
	public MostPlayedGameLauncher(WindowBuilderMask me) {
		
		this.librarian = me != null ? me.getLibrarian() : null; // WindowBuilder
		Parameters parameters = librarian != null ? librarian.getParameters() : null;
		
		setLayout(new MigLayout("", "3[]0", "3[][][]0"));
		mostPlayedGameButton = new LaunchButton(me, "", LaunchType.mostPlayed, null, null);
		add(mostPlayedGameButton, "cell 0 0 2 1,alignx center,aligny top");
		
		mostPlayedGameHoursPlayedLabel = new JLabel(parameters != null ? parameters.getUITexts().getString("mostPlayedGameHoursTotal") : ResourceBundle.getBundle("i18n/UITexts").getString("mostPlayedGameHoursTotal"));
		add(mostPlayedGameHoursPlayedLabel, "cell 0 1,alignx trailing,aligny center");
		
		mostPlayedGameHoursPlayed = new JTextField();
		mostPlayedGameHoursPlayed.setEditable(false);
		add(mostPlayedGameHoursPlayed, "cell 1 1");
		mostPlayedGameHoursPlayed.setColumns(10);
		
		mostPlayedGameHoursOnRecordLabel = new JLabel(parameters != null ? parameters.getUITexts().getString("mostPlayedGameHoursOnRecord") : ResourceBundle.getBundle("i18n/UITexts").getString("mostPlayedGameHoursOnRecord"));
		add(mostPlayedGameHoursOnRecordLabel, "cell 0 2,alignx trailing,aligny center");
		
		mostPlayedGameHoursOnRecord = new JTextField();
		mostPlayedGameHoursOnRecord.setEditable(false);
		add(mostPlayedGameHoursOnRecord, "cell 1 2");
		mostPlayedGameHoursOnRecord.setColumns(10);
	}
	
	public void translate() {
		mostPlayedGameButton.updateIconAndTooltip();
		Parameters parameters = librarian != null ? librarian.getParameters() : null;
		mostPlayedGameHoursPlayedLabel.setText(parameters != null ? parameters.getUITexts().getString("mostPlayedGameHoursTotal") : ResourceBundle.getBundle("i18n/UITexts").getString("mostPlayedGameHoursTotal"));
		mostPlayedGameHoursOnRecordLabel.setText(parameters != null ? parameters.getUITexts().getString("mostPlayedGameHoursOnRecord") : ResourceBundle.getBundle("i18n/UITexts").getString("mostPlayedGameHoursOnRecord"));
	}
	
	/**
	 * @return the launchButton
	 */
	public void setGame(SteamGame game) {
		mostPlayedGameButton.setGame(game);
		if (game != null) {
			mostPlayedGameHoursPlayed.setText(game.getHoursLast2Weeks());
			mostPlayedGameHoursOnRecord.setText(game.getHoursOnRecord());
		} else {
			mostPlayedGameHoursPlayed.setText("");
			mostPlayedGameHoursOnRecord.setText("");
			mostPlayedGameButton.setIconNoGameSelected();
		}
	}

}
