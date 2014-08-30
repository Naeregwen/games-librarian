package commons.windows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import commons.ColoredTee;


public class WinProcesses {
	
	private ColoredTee tee;
	
	/**
	 * Create a WinProcesses
	 * 
	 * @param tee
	 */
	public WinProcesses(ColoredTee tee) {
		this.tee = tee;
	}

	/**
	 * Get a list of running processes
	 * Using tasklist shell command
	 * 
	 * @return a list of processes names
	 */
	public List<String> listRunningProcesses() {
		List<String> processes = new ArrayList<String>();
		String line;
		// Get list in csv format, no headers
		try {
			Process process = Runtime.getRuntime().exec("tasklist.exe /fo csv /nh");
			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
			// keep only the process name
			Pattern processNamePattern = Pattern.compile("^\"?([\\w\\. -]+)\"?");
			Matcher matcher;
			while ((line = input.readLine()) != null) {
				line = line.trim();
				matcher = processNamePattern.matcher(line);
				if (!line.equals("") && matcher.find())
					processes.add(matcher.group(1));
			}
			input.close();
		} catch (IOException e) {
			tee.printStackTrace(e);
		}
		return processes;
	}

}