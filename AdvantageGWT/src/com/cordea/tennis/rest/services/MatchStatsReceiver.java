package com.cordea.tennis.rest.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.cordea.tennis.datastore.DatastoreManager;
import com.cordea.tennis.client.model.Match;
import com.cordea.tennis.client.model.Point;

@Path("/receiver/")
public class MatchStatsReceiver {

	private static final String MATCH_TYPE_FRIENDLY = "Friendly";
	private static final String MATCH_TYPE_COMPETITION = "Competition";
	private static final String MATCH_SCORING_TYPE_1 = "2/3";
	private static final String MATCH_SCORING_TYPE_2 = "2/3STB";
	private static final String MATCH_UNFORCED_PASSIVE = "passive";
	private static final String MATCH_UNFORCED_ACTIVE_POSITIVE = "active+";
	private static final String MATCH_UNFORCED_ACTIVE_NEGATIVE = "active-";

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/add/comment/{user}/{comment}/{uuid}")
	public String addComment(@PathParam("user") String user, @PathParam("comment") String comment, @PathParam("uuid") String uuid) {
		DatastoreManager datastoreManager = new DatastoreManager();
		datastoreManager.addMatchComment(user, comment, uuid);
		return "success";
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/add/match")
	public String addMatch(Match match) {
		DatastoreManager datastoreManager = new DatastoreManager();
		if (validate(match)) {
			datastoreManager.createNewMatch(match);
			return "success";
		} else
			return "failed";
	}

	private boolean validate(Match match) {
		if (!match.getMatchType().equals(MATCH_TYPE_FRIENDLY) && !match.getMatchType().equals(MATCH_TYPE_COMPETITION))
			return false;
		if (!match.getScoringType().equals(MATCH_SCORING_TYPE_1) && !match.getScoringType().equals(MATCH_SCORING_TYPE_2))
			return false;
		return true;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/add/point/{uuid}")
	public String addPoint(@PathParam("uuid") String uuid, Point point) {
		DatastoreManager datastoreManager = new DatastoreManager();
		datastoreManager.createNewPoint(point, uuid);
		return "success";
	}
}
