package com.cordea.tennis.server.datastore;


import com.cordea.tennis.client.model.Match;
import com.cordea.tennis.client.model.Point;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public class EntityMapper {
	public static final String MATCH_ENTITY_NAME = "match";/*	 */
	private static final String MATCH_TYPE = "match_type";/* competition/friendly */
	private static final String MATCH_SCORING_TYPE = "scoring_type";/*2/3,2/3TB*/
	public static final String MATCH_TIME = "match_time";/* YYYY.MM.DD */
	private static final String MATCH_INDOOR = "indoor";/* true,false */
	private static final String MATCH_OPPONENT_NAME = "opponent_name";/*	 */
	private static final String MATCH_OPPONENT_TP_RATING = "opponent_tp_rating";/*	 */
	private static final String MATCH_PEBBLE_USER_NAME = "pebble_user_name";/*	 */
	private static final String MATCH_PEBBLE_USER_TP_RATING = "pebble_user_tp_rating";/*	 */
	public static final String MATCH_COMMENTS = "match_comments";/*	 */
	
	private static final String POINT_ENTITY_NAME = "point";/*	 */
	private static final String POINT_HAND_SIDE = "hand_side";/*FH/BH	 */
	private static final String POINT_PEBBLE_USER_SERVING = "pebbleuser_serving";/*true/false	 */
	private static final String POINT_INDEX = "index";/*1..n	 */
	private static final String POINT_UNFORCED = "unforced";/*active+/active-/passive	 */
	private static final String POINT_ACE = "ace";/*true/false	 */
	private static final String POINT_DOUBLE_FAULT = "double_fault";/*true/false	 */
	private static final String POINT_FIRST_SERVE = "first_serve";/*true/false	 */
	private static final String POINT_FORCED = "forced";/*true/false	 */
	private static final String POINT_AT_NET = "at_net";/*true/false	 */
	private static final String POINT_WINNER = "winner";/*true/false	 */
	private static final String POINT_PEBBLEUSER_WON = "pebbleuser_won";/*true/false	 */

	public static Entity matchToEntity(Match match) {
		Entity matchEntity = new Entity(MATCH_ENTITY_NAME, match.getMatchUUID());
		matchEntity.setProperty(MATCH_TYPE, match.getMatchType());
		matchEntity.setProperty(MATCH_OPPONENT_NAME, match.getOpponent());
		matchEntity.setProperty(MATCH_OPPONENT_TP_RATING, match.getOpponentTpRating());
		matchEntity.setProperty(MATCH_PEBBLE_USER_NAME, match.getPebbleUser());
		matchEntity.setProperty(MATCH_PEBBLE_USER_TP_RATING, match.getPebbleUserTpRating());
		matchEntity.setProperty(MATCH_SCORING_TYPE, match.getScoringType());
		matchEntity.setProperty(MATCH_TIME, match.getMatchTime());
		matchEntity.setProperty(MATCH_INDOOR, match.isIndoor());
		matchEntity.setProperty(MATCH_COMMENTS, match.getComments());
		return matchEntity;
	}

	public static Entity entityToMatch(Entity matchEntity) {
		Match match = new Match();
		match.setMatchType((String) matchEntity.getProperty(MATCH_TYPE));
		match.setOpponent((String) matchEntity.getProperty(MATCH_OPPONENT_NAME));
		match.setOpponentTpRating((int) matchEntity.getProperty(MATCH_OPPONENT_TP_RATING));
		match.setPebbleUser((String) matchEntity.getProperty(MATCH_PEBBLE_USER_NAME));
		match.setPebbleUserTpRating((int) matchEntity.getProperty(MATCH_PEBBLE_USER_TP_RATING));
		match.setScoringType((String) matchEntity.getProperty(MATCH_SCORING_TYPE));
		match.setMatchTime((String) matchEntity.getProperty(MATCH_TIME));
		match.setIndoor((boolean) matchEntity.getProperty(MATCH_INDOOR));
		return matchEntity;
	}

	public static Entity pointToEntity(Point point, Key matchKey) {
		Entity newPoint = new Entity(POINT_ENTITY_NAME, matchKey);
		newPoint.setProperty(POINT_HAND_SIDE, point.getHandSide());
		newPoint.setProperty(POINT_PEBBLE_USER_SERVING, point.isPebbleuserServing());
		newPoint.setProperty(POINT_INDEX, point.getPointIndex());
		newPoint.setProperty(POINT_UNFORCED, point.getUnforcedType());
		newPoint.setProperty(POINT_ACE, point.isAce());
		newPoint.setProperty(POINT_DOUBLE_FAULT, point.isDoubleFault());
		newPoint.setProperty(POINT_FIRST_SERVE, point.isFirstServe());
		newPoint.setProperty(POINT_FORCED, point.isForced());
		newPoint.setProperty(POINT_AT_NET, point.isPointAtNet());
		newPoint.setProperty(POINT_WINNER, point.isWinner());
		newPoint.setProperty(POINT_PEBBLEUSER_WON, point.isWin());
		return newPoint;
	}

	public static Point entityToPoint(Entity entityPoint) {
		Point point = new Point();
		point.setHandSide((String) entityPoint.getProperty(POINT_HAND_SIDE));
		point.setPebbleuserServing((boolean) entityPoint.getProperty(POINT_PEBBLE_USER_SERVING));
		point.setPointIndex(((Long) entityPoint.getProperty(POINT_INDEX)).intValue());
		point.setUnforcedType((String) entityPoint.getProperty(POINT_UNFORCED));
		point.setAce((boolean) entityPoint.getProperty(POINT_ACE));
		point.setDoubleFault((boolean) entityPoint.getProperty(POINT_DOUBLE_FAULT));
		point.setFirstServe((boolean) entityPoint.getProperty(POINT_FIRST_SERVE));
		point.setForced((boolean) entityPoint.getProperty(POINT_FORCED));
		point.setPointAtNet((boolean) entityPoint.getProperty(POINT_AT_NET));
		point.setWinner((boolean) entityPoint.getProperty(POINT_WINNER));
		point.setWin((boolean) entityPoint.getProperty(POINT_PEBBLEUSER_WON));
		return point;
	}

}
