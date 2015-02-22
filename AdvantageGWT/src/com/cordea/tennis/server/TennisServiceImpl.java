package com.cordea.tennis.server;

import java.util.List;

import com.cordea.tennis.client.TennisService;
import com.cordea.tennis.client.model.Match;
import com.cordea.tennis.client.model.Point;
import com.cordea.tennis.datastore.DatastoreManager;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class TennisServiceImpl extends RemoteServiceServlet implements TennisService {

	@Override
	public Match getMatch(String uuid) throws IllegalArgumentException {
		DatastoreManager datastoreManager = new DatastoreManager();
		return datastoreManager.getMatch(uuid);
	}

	@Override
	public List<Match> getAllMatches() throws IllegalArgumentException {
		DatastoreManager datastoreManager = new DatastoreManager();
		List<Match> matches = datastoreManager.getAllMatches();
		return matches;
	}

	@Override
	public List<Point> getMatchPoints(String matchUuid) throws IllegalArgumentException {
		DatastoreManager datastoreManager = new DatastoreManager();
		List<Point> points = datastoreManager.getAllMatchPoints(matchUuid);
		return points;
	}

	@Override
	public Match getLiveMatch() throws IllegalArgumentException {
		DatastoreManager datastoreManager = new DatastoreManager();
		Match match = datastoreManager.getLatestMatch();
		return match;
	}

	@Override
	public List<Point> getLiveMatchPoints() throws IllegalArgumentException {
		DatastoreManager datastoreManager = new DatastoreManager();
		Match match = datastoreManager.getLatestMatch();
		return datastoreManager.getAllMatchPoints(match.getMatchUUID());
	}

}
