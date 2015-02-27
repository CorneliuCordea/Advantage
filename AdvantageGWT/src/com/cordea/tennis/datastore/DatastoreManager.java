package com.cordea.tennis.datastore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cordea.tennis.client.model.Match;
import com.cordea.tennis.client.model.Point;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

public class DatastoreManager {
	/**
	 * OK
	 * 
	 * @param match
	 * @return
	 */
	public boolean createNewMatch(Match match) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key matchParentKey = KeyFactory.createKey(EntityMapper.MATCH_ENTITY_NAME, match.getMatchUUID());
		Entity existingMatch;
		try {
			existingMatch = datastore.get(matchParentKey);
			Date matchTime = (Date) existingMatch.getProperty(EntityMapper.MATCH_TIME);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			match.setMatchTime(df.format(matchTime));

			String comments = (String) existingMatch.getProperty(EntityMapper.MATCH_COMMENTS);
			if (comments == null)
				comments = match.getPebbleUser() + ":  " + match.getComments() + "<br>";
			else
				comments = comments + match.getPebbleUser() + ":  " + match.getComments() + "<br>";
			match.setComments(comments);
		} catch (EntityNotFoundException e) {
			match.setComments(match.getPebbleUser() + ":  " + match.getComments() + "<br>");
			e.printStackTrace();
		}

		datastore.put(EntityMapper.matchToEntity(match));
		return true;
	}

	/**
	 * OK
	 * 
	 * @param point
	 * @param matchUUID
	 * @return
	 */
	public boolean createNewPoint(Point point, String matchUUID) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key matchParentKey = KeyFactory.createKey(EntityMapper.MATCH_ENTITY_NAME, matchUUID);
		datastore.put(EntityMapper.pointToEntity(point, matchParentKey));
		return true;
	}

	/**
	 * OK
	 * 
	 * @param user
	 * @param comment
	 * @param matchUUID
	 * @return
	 */
	public boolean addMatchComment(String user, String comment, String matchUUID) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key matchParentKey = KeyFactory.createKey(EntityMapper.MATCH_ENTITY_NAME, matchUUID);
		Entity match;
		try {
			match = datastore.get(matchParentKey);
			String comments = (String) match.getProperty(EntityMapper.MATCH_COMMENTS);
			if (comments == null)
				comments = user + ":  " + comment + "<br>";
			else
				comments = comments + user + ":  " + comment + "<br>";
			match.setProperty(EntityMapper.MATCH_COMMENTS, comments);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		datastore.put(match);
		return true;
	}

	/**
	 * OK
	 * 
	 * @param uuid
	 * @return
	 */
	public Match getMatch(String uuid) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key matchUUID = KeyFactory.createKey(EntityMapper.MATCH_ENTITY_NAME, uuid);
		Filter keyFilter = new FilterPredicate(Entity.KEY_RESERVED_PROPERTY, FilterOperator.EQUAL, matchUUID);
		Query q = new Query(EntityMapper.MATCH_ENTITY_NAME).setFilter(keyFilter);
		PreparedQuery pq = datastore.prepare(q);
		for (Entity result : pq.asIterable()) {
			return EntityMapper.entityToMatch(result);
		}
		return null;
	}

	/**
	 * OK
	 * 
	 * @param uuid
	 * @return
	 */
	public List<Point> getAllMatchPoints(String uuid) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key matchUUID = KeyFactory.createKey(EntityMapper.MATCH_ENTITY_NAME, uuid);
		Query q = new Query(EntityMapper.POINT_ENTITY_NAME, matchUUID).addSort("index", SortDirection.ASCENDING);
		PreparedQuery pq = datastore.prepare(q);
		List<Point> points = new ArrayList<Point>();
		for (Entity result : pq.asIterable()) {
			points.add(EntityMapper.entityToPoint(result));
		}
		return points;
	}
	
	public void removeLastPoint(String uuid) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key matchUUID = KeyFactory.createKey(EntityMapper.MATCH_ENTITY_NAME, uuid);
		Query q = new Query(EntityMapper.POINT_ENTITY_NAME, matchUUID).addSort("index", SortDirection.ASCENDING);
		PreparedQuery pq = datastore.prepare(q);
		List<Point> points = new ArrayList<Point>();
		Key lastPointKey=null;
		for (Entity result : pq.asIterable()) {
			lastPointKey=result.getKey();
		}
		datastore.delete(lastPointKey);
	}

	/**
	 * OK
	 * 
	 * @return
	 */
	public List<Match> getAllMatches() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(EntityMapper.MATCH_ENTITY_NAME);
		PreparedQuery pq = datastore.prepare(q);
		List<Match> matches = new ArrayList<Match>();
		for (Entity entityMatch : pq.asIterable()) {
			matches.add(EntityMapper.entityToMatch(entityMatch));
		}
		return matches;
	}

	/**
	 * OK
	 * 
	 * @return
	 */
	public Match getLatestMatch() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(EntityMapper.MATCH_ENTITY_NAME).addSort(EntityMapper.MATCH_TIME, SortDirection.DESCENDING);
		PreparedQuery pq = datastore.prepare(q);
		List<Match> matches = new ArrayList<Match>();
		for (Entity entityMatch : pq.asIterable()) {
			matches.add(EntityMapper.entityToMatch(entityMatch));
		}
		return matches.get(0);
	}
}
