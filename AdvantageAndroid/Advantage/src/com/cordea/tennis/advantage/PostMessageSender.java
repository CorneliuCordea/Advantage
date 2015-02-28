package com.cordea.tennis.advantage;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.os.Looper;

public class PostMessageSender {
	protected static final String SUCCESS = "Success";
	private static Queue<JSONObject> pointsQueue;

	public static Queue<JSONObject> getPointsQueue() {
		if (pointsQueue == null) {
			pointsQueue = new LinkedList<JSONObject>();
		}
		return pointsQueue;
	}

	protected void sendJson(final JSONObject jsonPoint, final String url) {
		getPointsQueue().add(jsonPoint);

		Thread t = new Thread() {

			public void run() {
				Looper.prepare(); // For Preparing Message Pool for the child
									// Thread
				HttpClient client = new DefaultHttpClient();
				HttpConnectionParams.setConnectionTimeout(client.getParams(),
						1000); // Timeout Limit
				HttpResponse response;
				try {
					HttpPost post = new HttpPost(url);
					while (getPointsQueue().peek() != null) {
						JSONObject currentJsonPoint = (JSONObject) getPointsQueue()
								.peek();

						StringEntity se = new StringEntity(
								currentJsonPoint.toString());
						se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
								"application/json"));
						post.setEntity(se);
						response = client.execute(post);
						if (response != null) {
							InputStream in = response.getEntity().getContent(); // entity
						}

						getPointsQueue().poll();
					}

				} catch (Exception e) {
					e.printStackTrace();

				}

				Looper.loop(); // Loop in the message queue
			}
		};
		t.start();
	}

	protected String createMatch(final JSONObject jsonPoint, final String url) {
		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 4000); // Timeout
																				// Limit
		HttpResponse response;
		try {
			HttpPost post = new HttpPost(url);
			StringEntity se = new StringEntity(jsonPoint.toString());
			se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json"));
			post.setEntity(se);
			response = client.execute(post);
			if (response != null) {
				InputStream in = response.getEntity().getContent(); // entity
			}

		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return SUCCESS;
	}

}
