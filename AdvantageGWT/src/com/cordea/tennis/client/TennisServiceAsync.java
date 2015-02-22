package com.cordea.tennis.client;

import java.util.List;

import com.cordea.tennis.client.model.Match;
import com.cordea.tennis.client.model.Point;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface TennisServiceAsync {

	void getMatch(String uuid, AsyncCallback<Match> callback);

	void getAllMatches(AsyncCallback<List<Match>> callback);

	void getMatchPoints(String matchUuid, AsyncCallback<List<Point>> callback);

	void getLiveMatch(AsyncCallback<Match> callback);
	
	void getLiveMatchPoints(AsyncCallback<List<Point>> callback);

}
