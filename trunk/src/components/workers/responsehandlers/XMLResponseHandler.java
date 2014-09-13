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
package components.workers.responsehandlers;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * @author Naeregwen
 *
 */
public class XMLResponseHandler implements ResponseHandler<Exception> {

	XMLReader xmlReader;
	int httpStatus;
	
	public XMLResponseHandler(XMLReader xmlReader) {
		super();
		this.xmlReader = xmlReader;
		httpStatus = -1;
	}

	public int getHttpStatus() {
		return httpStatus;
	}
	
	public Exception handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        httpStatus = response.getStatusLine().getStatusCode();
        if (httpStatus >= 200 && httpStatus < 300) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inputStream = entity.getContent();
                try {
                	xmlReader.parse(new InputSource(inputStream));
				} catch (SAXException exception) {
					return exception;
                } finally {
                    inputStream.close();
                }
            }
        } else {
            throw new ClientProtocolException("Unexpected response status: " + httpStatus);
        }
        return null;
	}
}
