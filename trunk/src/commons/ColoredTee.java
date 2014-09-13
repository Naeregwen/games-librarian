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

	public enum TeeColor {
		
		Message(Color.BLUE),
		Info(Color.BLACK),
		Error(Color.RED);
		
		Color color;
		
		TeeColor(Color color) {
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
		write(message, TeeColor.Message.color, nl, System.out);
	}
	
	public void writelnMessage(String message) {
		write(message, TeeColor.Message.color, true, System.out);
	}
	
	public void writeMessage(List<String> messages, boolean nl) {
		for (String message : messages) 
			write(message, TeeColor.Message.color, nl, System.out);
	}
	
	public void writelnMessage(List<String> messages) {
		for (String message : messages) 
			write(message, TeeColor.Message.color, true, System.out);
	}
	
	public void writeInfos(String message, boolean nl) {
		write(message, TeeColor.Info.color, nl, System.out);
	}
	
	public void writelnInfos(String message) {
		write(message, TeeColor.Info.color, true, System.out);
	}
	
	public void writeInfos(List<String> messages, boolean nl) {
		for (String message : messages)
			write(message, TeeColor.Info.color, nl, System.out);
	}
	
	public void writelnInfos(List<String> messages) {
		for (String message : messages)
			write(message, TeeColor.Info.color, true, System.out);
	}
	
	public void writeError(String message, boolean nl) {
		write(message, TeeColor.Error.color, nl, System.err);
	}		

	public void writelnError(String message) {
		write(message, TeeColor.Error.color, true, System.err);
	}		

	public void writeError(List<String> messages, boolean nl) {
		for (String message : messages)
			write(message, TeeColor.Error.color, nl, System.err);
	}
	
	public void writelnError(List<String> messages) {
		for (String message : messages)
			write(message, TeeColor.Error.color, true, System.err);
	}
	
	public void printStackTrace(Exception e) {
		String formattedDate = parameters.isUseDateTime() ? dateFormat.format(new Date()) : "";
		if (parameters.isUseConsole()) e.printStackTrace();
		if (librarian != null) {
			if (e.getLocalizedMessage() != null)
				librarian.appendMessage(formattedDate + "Exception " + e.getClass().getCanonicalName() + " : " + e.getLocalizedMessage() + "\n", TeeColor.Error.color);
			else if (e.getMessage() != null)
				librarian.appendMessage(formattedDate + "Exception " + e.getClass().getCanonicalName() + " : " + e.getMessage() + "\n", TeeColor.Error.color);
			else
				librarian.appendMessage(formattedDate + "Exception " + e.getClass().getCanonicalName() + "\n", TeeColor.Error.color);
			if (parameters.isDebug())
				for (StackTraceElement stackTraceElement : e.getStackTrace()) {
					librarian.appendMessage(formattedDate + "\t" + stackTraceElement.toString() + "\n", TeeColor.Error.color);
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
		boolean writeMessage = firstString.equalsIgnoreCase(ColoredTee.TeeColor.Message.name());
		boolean writeInfo = firstString.equalsIgnoreCase(ColoredTee.TeeColor.Info.name());
		boolean writeError = firstString.equalsIgnoreCase(ColoredTee.TeeColor.Error.name());
		if (writeError || writeMessage | writeInfo) strings.remove(0);
        for (String string : strings) {
        	if (!isFirstString) {
	    		writeMessage = string.equalsIgnoreCase(ColoredTee.TeeColor.Message.name());
	    		writeInfo = string.equalsIgnoreCase(ColoredTee.TeeColor.Info.name());
	    		writeError = string.equalsIgnoreCase(ColoredTee.TeeColor.Error.name());
	    		if (writeError || writeMessage | writeInfo) continue;
        	} else
        		isFirstString = false;
        	if (writeError) {
        		if (!string.equalsIgnoreCase(ColoredTee.TeeColor.Error.name()))
        			tee.writelnError(string);
        	} else if (writeMessage) {
        		if (!string.equalsIgnoreCase(ColoredTee.TeeColor.Message.name()))
        			tee.writelnMessage(string);
        	} else 
        		tee.writelnInfos(string);
        }
	}
}
