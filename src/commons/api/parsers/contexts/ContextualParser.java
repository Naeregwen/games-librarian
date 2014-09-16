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
package commons.api.parsers.contexts;

import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import commons.Strings;
import commons.api.Parameters;
import components.commons.ColoredTee;

/**
 * @author Naeregwen
 *
 */
public class ContextualParser<Context extends Enum<Context>> extends DefaultHandler {

	/**
	 * Use a stack of contexts to check XML validity during parsing
	 * Validity : only tag position is checked
	 */
	protected Stack<Context> contexts;
	protected Context context;
	
	/**
	 * Parsing context
	 * Final parsing container
	 */
	protected Parameters parameters;
	
	/**
	 * Output for errors/messages
	 */
	protected ColoredTee tee;
	
	/**
	 * Temporary parsing containers
	 */
	protected String characters;
	
	public ContextualParser(Parameters parameters, ColoredTee tee) {
		super();
		this.parameters = parameters == null ? new Parameters() : parameters;
		this.tee = tee;
	}

	/**
	 * @return the parameters
	 */
	public Parameters getParameters() {
		return parameters;
	}

	/**
	 * Dump current contexts stack to String
	 * 
	 * @param contexts
	 * @return dumped stack
	 */
	String dump(Stack<Context> contexts) {
		StringBuilder s = new StringBuilder("");
		for (Context context : contexts) 
			s.append(s.length() == 0 ? context.name() : ", " + context.name());
		return s.toString();
	}
	
	
	/**
	 * Output trace of current context and current contexts stack
	 * 
	 * @param headline
	 * @param qName
	 */
	protected void trace(String headline, String qName) {
		tee.writelnMessage(headline + Strings.padRight(qName, 25) + " - context : " + Strings.padRight(context.name(), 11) + " - stack : " + dump(contexts));
	}
	
	/**
	 * Check current context against passed context
	 * 
	 * @param qName
	 * @param context
	 * @return
	 */
	protected boolean checkContext(String qName, Context context, Context nextContext, int contextsArrayLength, boolean update) {
		if (this.context == context) {
			if (update) {
				contexts.push(this.context);
				if (this.context.ordinal() < contextsArrayLength - 1)
					this.context = nextContext;
			}
			return true;
		} else {
			if (this.context.ordinal() == 0) 
				tee.writelnError(String.format(parameters.getMessages().getString("errorRootTagOutOfContext"), qName));
			else
				tee.writelnError(String.format(parameters.getMessages().getString("errorTagOutOfContext"), qName, context));
		}
		return false;
	}
	
	/**
	 * Check current context against passed context before stack pop
	 * 
	 * @param qName
	 * @param context
	 * @return
	 */
	protected boolean checkPopContext(String qName, Context context) {
		if (this.context == context) {
			return true;
		} else {
			tee.writelnError(String.format(parameters.getMessages().getString("errorClosingTagOutOfContext"), qName, context));
		}
		return false;
	}
	
	/**
	 * Parse boolean alternatives
	 * 
	 * @param characters
	 * @param qName
	 * @return
	 */
	protected boolean parseBooleanAlternatives(String characters, String qName) {
		if (characters.equals("0") || characters.equalsIgnoreCase("false"))
			return false;
		else if (characters.equals("1") || characters.equalsIgnoreCase("true"))
			return true;
		else 
			tee.writelnError(String.format(parameters.getMessages().getString("errorUnexpectedValue"), characters, qName, "0, 1, true, false"));
		return false;
	}
	
	/*/
	 * (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#startDocument()
	 */
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		contexts = new Stack<Context>();
	}
	
	/*/
	 * (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		// Reset characters container
		characters = "";
		// Trace start element and context
		if (parameters.isDebug()) trace("startElement qName : ", qName);
	}
	
	/*/
	 * (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		// Trace end element and context
		if (parameters.isDebug()) trace("endElement qName   : ", qName);
	}
}
