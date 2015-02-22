package com.cordea.tennis.client.business;

import com.cordea.tennis.client.model.Point;

public class MatchScore {
	public static final int MATCH_TYPE_2_OUT_OF_3 = 1;
	public static final int SET_TYPE_NORMAL = 1;
	public static final int MATCH_TYPE_2_OUT_OF_3_WITH_SUPER_TIE_BREAK = 2;
	public static final int SET_TYPE_SUPER_TIE_BREAK = 2;
	public static final int GAME_TYPE_TIEBREAK_GAME = 2;
	public static final int GAME_TYPE_NORMAL_GAME = 1;

	public static final int PLAYER1_SERVING = 1;
	public static final int PLAYER2_SERVING = 2;

	private Player player1 = new Player();
	private Player player2 = new Player();
	int currentSet = 0;
	boolean breakPoint = false;
	int matchType;
	private int playerServing;

	public boolean isBreakPoint() {
		int player1Points = getPlayer1().getPoints();
		int player2Points = getPlayer2().getPoints();
		if (getGameType() == GAME_TYPE_NORMAL_GAME) {
			if ((player1Points>=3 || player2Points >= 3)&& Math.abs(player1Points-player2Points)>=1 )
				if (player1Points > player2Points && getPlayerServing() == PLAYER2_SERVING) {
					return true;
				} else if (player1Points < player2Points && getPlayerServing() == PLAYER1_SERVING) {
					return true;
				}
		}
		return false;
	}

	public void setBreakPoint(boolean theBreakPoint) {
		this.breakPoint = theBreakPoint;
	}
	
	public void computeMatchScore(Point p) {
		if (p.isWin()) {
			if (getSetType() == SET_TYPE_NORMAL)
				player1.addPoint();
			else
				player1.addGame(currentSet);
		} else {
			if (getSetType() == SET_TYPE_NORMAL)
				player2.addPoint();
			else
				player2.addGame(currentSet);
		}

		if (getSetType() == SET_TYPE_SUPER_TIE_BREAK) {
			// Super tie break set type first to 10 points
			if (Math.abs(player1.getGames()[currentSet] - player2.getGames()[currentSet]) >= 2
					&& getSetLeadPlayer().getGames()[currentSet] >= 10)
				currentSet++;
		} else {
			boolean gameFinished = false;
			// Normal set type first to 6/7 games
			if (getGameType() == GAME_TYPE_NORMAL_GAME) {
				if (Math.abs(player1.getPoints() - player2.getPoints()) >= 2 && getGameLeadPlayer().getPoints() >= 4) {
					getGameLeadPlayer().addGame(currentSet);
					gameFinished = true;
					resetPoints();
				}
			} else if (getGameType() == GAME_TYPE_TIEBREAK_GAME) {
				if (Math.abs(player1.getPoints() - player2.getPoints()) >= 2 && getGameLeadPlayer().getPoints() >= 7) {
					getGameLeadPlayer().addGame(currentSet);
					resetPoints();
					gameFinished = true;
				}
			}
			if (gameFinished) {
				if ((Math.abs(player1.getGames()[currentSet] - player2.getGames()[currentSet]) >= 2 && getSetLeadPlayer().getGames()[currentSet] >= 6)
						|| getSetLeadPlayer().getGames()[currentSet] == 7)
					currentSet++;
			}
		}
	}

	public int getSetType() {
		if (matchType == MATCH_TYPE_2_OUT_OF_3)
			return SET_TYPE_NORMAL;
		else if (matchType == MATCH_TYPE_2_OUT_OF_3_WITH_SUPER_TIE_BREAK && (currentSet == 1 || currentSet == 2))
			return SET_TYPE_NORMAL;
		else if (matchType == MATCH_TYPE_2_OUT_OF_3_WITH_SUPER_TIE_BREAK && currentSet == 3)
			return SET_TYPE_SUPER_TIE_BREAK;
		else
			return SET_TYPE_NORMAL;
	}

	public int getGameType() {
		if (player1.getGames()[currentSet] == player2.getGames()[currentSet] && player2.getGames()[currentSet] == 6)
			return GAME_TYPE_TIEBREAK_GAME;
		else
			return GAME_TYPE_NORMAL_GAME;
	}

	private void resetPoints() {
		player1.setPoints(0);
		player2.setPoints(0);
	}

	private void incrementSet() {
		currentSet++;
	}

	public Player getGameLeadPlayer() {
		return (player1.getPoints() > player2.getPoints()) ? player1 : player2;
	}

	public Player getSetLeadPlayer() {
		return (player1.getGames()[currentSet] > player2.getGames()[currentSet]) ? player1 : player2;
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public int getPlayerServing() {
		return playerServing;
	}

	public void setPlayerServing(int playerServing) {
		this.playerServing = playerServing;
	}

	public int getMatchType() {
		return matchType;
	}

	public void setMatchType(int matchType) {
		this.matchType = matchType;
	}

}
