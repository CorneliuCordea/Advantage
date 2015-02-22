package com.cordea.tennis.client;

import java.util.List;

import com.cordea.tennis.client.model.Match;
import com.cordea.tennis.client.model.Point;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;
	List<Point> getPoints() throws IllegalArgumentException;
	Match getLatestMatch() throws IllegalArgumentException;
}
