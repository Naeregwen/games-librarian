/**
 * 
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
