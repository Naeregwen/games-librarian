/**
 * 
 */
package commons;

import java.awt.Color;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import commons.api.Parameters;
import components.Librarian;


/**
 * @author Naeregwen
 *
 */
public class ColoredTee {

	public enum TextColor {
		
		Message(Color.BLUE),
		Info(Color.BLACK),
		Error(Color.RED);
		
		Color color;
		
		TextColor(Color color) {
			this.color = color;
		}

	}

	private DateFormat dateFormat;
	
	Parameters parameters;
	Librarian librarian;
	
	public ColoredTee(Parameters parameters, Librarian librarian) {
		this.dateFormat = new SimpleDateFormat("[yyyy/MM/dd HH:mm:ss:SSS] ");
		this.parameters = parameters;
		this.librarian = librarian;
	}

	private void write(String message, Color color, boolean nl, PrintStream printStream) {
		String formattedDate = parameters.isUseDateTime() ? dateFormat.format(new Date()) : "";
		message = formattedDate + message + (nl ? "\n" : "");
		if (parameters.isUseConsole()) printStream.print(message);
		if (librarian != null) librarian.appendMessage(message, color);
	}
	
	public void writeMessage(String message, boolean nl) {
		write(message, TextColor.Message.color, nl, System.out);
	}
	
	public void writelnMessage(String message) {
		write(message, TextColor.Message.color, true, System.out);
	}
	
	public void writeMessage(List<String> messages, boolean nl) {
		for (String message : messages) 
			write(message, TextColor.Message.color, nl, System.out);
	}
	
	public void writelnMessage(List<String> messages) {
		for (String message : messages) 
			write(message, TextColor.Message.color, true, System.out);
	}
	
	public void writeInfos(String message, boolean nl) {
		write(message, TextColor.Info.color, nl, System.out);
	}
	
	public void writelnInfos(String message) {
		write(message, TextColor.Info.color, true, System.out);
	}
	
	public void writeInfos(List<String> messages, boolean nl) {
		for (String message : messages)
			write(message, TextColor.Info.color, nl, System.out);
	}
	
	public void writelnInfos(List<String> messages) {
		for (String message : messages)
			write(message, TextColor.Info.color, true, System.out);
	}
	
	public void writeError(String message, boolean nl) {
		write(message, TextColor.Error.color, nl, System.err);
	}		

	public void writelnError(String message) {
		write(message, TextColor.Error.color, true, System.err);
	}		

	public void writeError(List<String> messages, boolean nl) {
		for (String message : messages)
			write(message, TextColor.Error.color, nl, System.err);
	}
	
	public void writelnError(List<String> messages) {
		for (String message : messages)
			write(message, TextColor.Error.color, true, System.err);
	}
	
	public void printStackTrace(Exception e) {
		String formattedDate = parameters.isUseDateTime() ? dateFormat.format(new Date()) : "";
		if (parameters.isUseConsole()) e.printStackTrace();
		if (librarian != null) {
			if (e.getLocalizedMessage() != null)
				librarian.appendMessage(formattedDate + "Exception " + e.getClass().getCanonicalName() + " : " + e.getLocalizedMessage() + "\n", TextColor.Error.color);
			else if (e.getMessage() != null)
				librarian.appendMessage(formattedDate + "Exception " + e.getClass().getCanonicalName() + " : " + e.getMessage() + "\n", TextColor.Error.color);
			else
				librarian.appendMessage(formattedDate + "Exception " + e.getClass().getCanonicalName() + "\n", TextColor.Error.color);
			if (parameters.isDebug())
				for (StackTraceElement stackTraceElement : e.getStackTrace()) {
					librarian.appendMessage(formattedDate + "\t" + stackTraceElement.toString() + "\n", TextColor.Error.color);
			} 
		}
	}
	
	/**
	 * Writeln a list of strings to tee.
	 * if a string of this list contains one name from ColoredTee.TextColor ("message", "info", "error"), then it is considered as a String marker.
	 * String markers from ColoredTee.TextColor.name are ignored
	 * but messages following those String markers are written in corresponding ColoredTee.TextColor.color
	 * until a new String marker is found that change again the color
	 * 
	 * @param tee the targeted tee
	 * @param strings the list of strings to writeln
	 */
	public static void writeln(ColoredTee tee, List<String> strings) {
		if (strings.size() <= 0) return;
		String firstString = strings.get(0);
		boolean isFirstString = true;
		boolean writeMessage = firstString.equalsIgnoreCase(ColoredTee.TextColor.Message.name());
		boolean writeInfo = firstString.equalsIgnoreCase(ColoredTee.TextColor.Info.name());
		boolean writeError = firstString.equalsIgnoreCase(ColoredTee.TextColor.Error.name());
		if (writeError || writeMessage | writeInfo) strings.remove(0);
        for (String string : strings) {
        	if (!isFirstString) {
	    		writeMessage = string.equalsIgnoreCase(ColoredTee.TextColor.Message.name());
	    		writeInfo = string.equalsIgnoreCase(ColoredTee.TextColor.Info.name());
	    		writeError = string.equalsIgnoreCase(ColoredTee.TextColor.Error.name());
	    		if (writeError || writeMessage | writeInfo) continue;
        	} else
        		isFirstString = false;
        	if (writeError) {
        		if (!string.equalsIgnoreCase(ColoredTee.TextColor.Error.name()))
        			tee.writelnError(string);
        	} else if (writeMessage) {
        		if (!string.equalsIgnoreCase(ColoredTee.TextColor.Message.name()))
        			tee.writelnMessage(string);
        	} else 
        		tee.writelnInfos(string);
        }
	}
}
