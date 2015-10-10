package com.hadican.bildirim.resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hadican.bildirim.model.GCMResponse;

@Path("/notification")
@Produces(MediaType.APPLICATION_JSON)
public class NotificationResource {

	private final HttpClient httpClient;
	private final String apiKey;

	private static final Logger logger = LoggerFactory.getLogger(NotificationResource.class);

	public NotificationResource(HttpClient httpClient, String apiKey) {
		this.httpClient = httpClient;
		this.apiKey = apiKey;
	}

	/**
	 * Posts provided {@code body} to GCM. Returns {@code true} if successful.<br />
	 * Returns {@code true} for success, {@code false} for failure.
	 * 
	 * @param body
	 * @return
	 */
	@POST
	@Path("/send")
	public boolean sendNotification(String body) {
		try {
			// create HTTP post request to GCM
			HttpPost httpRequest = new HttpPost("https://android.googleapis.com/gcm/send");
			// add headers
			httpRequest.addHeader("Authorization", "key=" + apiKey);
			httpRequest.addHeader("Content-Type", "application/json");
			// add posted body directly
			httpRequest.setEntity(new StringEntity(body));
			// make the call
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			// get the response
			GCMResponse gcmResponse = new ObjectMapper().readValue(httpResponse.getEntity().getContent(), GCMResponse.class);
			// return true for success
			return gcmResponse.getSuccess() == 1;
		} catch (Exception e) {
			logger.error("Error occurred while sending notification to GCM.", e);
			return false;
		}
	}

}
