package com.cordea.tennis.client.business;

import java.util.ArrayList;
import java.util.List;

import sun.security.krb5.SCDynamicStoreConfig;

import com.cordea.tennis.client.model.Match;
import com.cordea.tennis.client.model.Point;

public class ScoreProcessor {

	private static final Object UNFORCED_ACTIVE_PLUS = "active+";
	private static final Object UNFORCED_ACTIVE_MINUS = "active-";
	private static final Object UNFORCED_PASSIVE = "passive";
	private List<Point> matchPoints;
	private MatchScore matchScore;
	private Statistics statistics;
	private int gameNumber = 0;
	private List<Statistics> gameStatistics = new ArrayList<Statistics>();
	private Match match;

	public Statistics getStatistics() {
		return statistics;
	}

	public void setStatistics(Statistics statistics) {
		this.statistics = statistics;
	}

	public MatchScore getMatchScore() {
		return matchScore;
	}

	public void setMatchScore(MatchScore matchScore) {
		this.matchScore = matchScore;
	}

	public ScoreProcessor(List<Point> matchPoints, Match liveMatch) {
		this.matchPoints = matchPoints;
		this.match = liveMatch;
		statistics = new Statistics();
		matchScore = new MatchScore();
		if (match.getMatchType().equals("2/3STB"))
			matchScore.setMatchType(2);
		else
			matchScore.setMatchType(1);
		processScore();
	}

	private void processScore() {
		gameStatistics.add(new Statistics());
		for (Point p : matchPoints) {
			if (p.isPebbleuserServing())
				matchScore.setPlayerServing(MatchScore.PLAYER1_SERVING);
			else
				matchScore.setPlayerServing(MatchScore.PLAYER2_SERVING);

			extractStatistics(p, matchScore, statistics);

			//extract game stats
			if ((matchScore.getPlayer1().getPoints() == 0 && matchScore.getPlayer2().getPoints() == 0)) {
				gameNumber++;
				gameStatistics.add(new Statistics());
				extractStatistics(p, matchScore, gameStatistics.get(gameNumber));
			} else {
				extractStatistics(p, matchScore, gameStatistics.get(gameNumber));
			}
			
			
			this.matchScore.computeMatchScore(p);
		}
	}

	private void extractStatistics(Point p, MatchScore matchScore, Statistics statistics) {
		// TOTAL NUMBER OF POINTS
		statistics.addTotalPoints();

		// NUMBER OF POINTS WON FOR EACH PLAYER
		if (p.isWin())
			statistics.addPebbleUserPointWon();
		else
			statistics.addOpponentPointWon();

		// NUMBER OF POINTS SERVED OF EACH PLAYER
		if (p.isPebbleuserServing())
			statistics.addPebbleUserServingPoint();
		else
			statistics.addOpponentServingPoint();

		// NUMBER OF POINTS SERVED and WON FOR EACH PLAYER
		if (p.isPebbleuserServing() && p.isWin())
			statistics.addPebbleUserServingPointsWon();
		else if (!p.isPebbleuserServing() && !p.isWin())
			statistics.addOpponentServingPointsWon();

		// NUMBER OF POINTS PLAYED/WON WITH FIRST SERVE BY EACH PLAYER
		if (p.isPebbleuserServing() && p.isFirstServe()) {
			statistics.addPebbleUserFirstServeIn();
			if (p.isWin())
				statistics.addPebbleUserFirstServeWon();
		} else if (!p.isPebbleuserServing() && p.isFirstServe()) {
			statistics.addOpponentFirstServeIn();
			if (!p.isWin()) {
				statistics.addOpponentFirstServeWon();
			}
		}

		// NUMBER OF POINTS PLAYED/WIN WITH Second SERVE BY EACH PLAYER
		if (p.isPebbleuserServing() && !p.isFirstServe()) {
			statistics.addPebbleUserSecondServeIn();
			if (p.isWin())
				statistics.addPebbleUserSecondServeWon();
		} else if (!p.isPebbleuserServing() && !p.isFirstServe()) {
			statistics.addOpponentSecondServeIn();
			if (!p.isWin()) {
				statistics.addOpponentSecondServeWon();
			}
		}

		// NUMBER OF DOUBLES FOR EACH PLAYER P
		if (p.isPebbleuserServing() && p.isDoubleFault())
			statistics.addPebbleUserDoubleFault();
		else if (!p.isPebbleuserServing() && p.isDoubleFault())
			statistics.addOpponentDoubleFault();

		// NUMBER OF ACES FOR EACH PLAYER P
		if (p.isPebbleuserServing() && p.isAce())
			statistics.addPebbleUserAce();
		else if (!p.isPebbleuserServing() && p.isAce())
			statistics.addOpponentAce();

		// NUMBER OF ACES FOR EACH PLAYER P
		if (p.isWin() && p.isWinner())
			statistics.addPebbleUserWinner();
		else if (!p.isWin() && p.isWinner())
			statistics.addOpponentWinner();

		// NUMBER OF FORCED ERRORS
		if (p.isWin() && p.isForced())
			statistics.addOpponentForced();
		else if (!p.isWin() && p.isForced())
			statistics.addPebbleUserForced();

		// NUMBER OF UNFORCED ERRORS
		if (!p.isWin() && !p.isForced() && !p.isWinner() && !p.isDoubleFault() && !p.isAce()) {
			statistics.addPebbleUserUnforced();
			if (p.getUnforcedType().equals(ScoreProcessor.UNFORCED_ACTIVE_PLUS))
				statistics.addPebbleUserUnforcedActivePlus();
			else if (p.getUnforcedType().equals(ScoreProcessor.UNFORCED_ACTIVE_MINUS))
				statistics.addPebbleUserUnforcedActiveMinus();
			else if (p.getUnforcedType().equals(ScoreProcessor.UNFORCED_PASSIVE))
				statistics.addPebbleUserUnforcedPassive();
		} else if (p.isWin() && !p.isForced() && !p.isWinner() && !p.isDoubleFault() && !p.isAce()) {
			statistics.addOpponentUnforced();
			if (p.getUnforcedType().equals(ScoreProcessor.UNFORCED_ACTIVE_PLUS))
				statistics.addOpponentUnforcedActivePlus();
			else if (p.getUnforcedType().equals(ScoreProcessor.UNFORCED_ACTIVE_MINUS))
				statistics.addOpponentUnforcedActiveMinus();
			else if (p.getUnforcedType().equals(ScoreProcessor.UNFORCED_PASSIVE))
				statistics.addOpponentUnforcedPassive();
		}

		// NUMBER OF BREAK POINTS
		if (matchScore.isBreakPoint() && p.isPebbleuserServing()) {
			statistics.addOpponentBreakPoints();
			if (!p.isWin())
				statistics.addOpponentBreakPointsWon();
		} else if (matchScore.isBreakPoint() && !p.isPebbleuserServing()) {
			statistics.addPebbleUserBreakPoints();
			if (p.isWin())
				statistics.addPebbleUserBreakPointsWon();
		}
	}

	public List<Statistics> getGameStatistics() {
		return gameStatistics;
	}
}
