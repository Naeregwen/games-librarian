/**
 * 
 */
package components.workers.responsehandlers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

/**
 * @author anguyen
 *
 */
public class BufferedImageResponseHandler implements ResponseHandler<BufferedImage> {

	@Override
	public BufferedImage handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
	    int status = response.getStatusLine().getStatusCode();
	    if (status >= 200 && status < 300) {
	        HttpEntity entity = response.getEntity();
	        if (entity != null) {
	            InputStream inputStream = entity.getContent();
	            try {
	            	return ImageIO.read(inputStream);
	            } catch (IOException ex) {
	                throw ex;
	            } finally {
	                inputStream.close();
	            }
	        }
	    } else {
	        throw new ClientProtocolException("Unexpected response status: " + status);
	    }
	    return null;
	}

}
