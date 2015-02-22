package com.cordea.tennis.client;

import java.util.List;

import com.cordea.tennis.client.model.Match;
import com.cordea.tennis.client.model.Point;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("tennis")
public interface TennisService extends RemoteService {
	Match getMatch(String uuid) throws IllegalArgumentException;
	
	Match getLiveMatch() throws IllegalArgumentException;
	
	List<Match> getAllMatches() throws IllegalArgumentException;
	
	List<Point> getMatchPoints(String matchUuid) throws IllegalArgumentException;
	
	List<Point> getLiveMatchPoints() throws IllegalArgumentException;
}


