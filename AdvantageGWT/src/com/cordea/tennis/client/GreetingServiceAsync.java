package com.cordea.tennis.client;

import java.util.List;

import com.cordea.tennis.client.model.Match;
import com.cordea.tennis.client.model.Point;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback) throws IllegalArgumentException;
	void getPoints(AsyncCallback<List<Point>> callback) throws IllegalArgumentException;
	void getLatestMatch(AsyncCallback<Match> callback) throws IllegalArgumentException;

}
