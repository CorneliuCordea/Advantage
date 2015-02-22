package com.cordea.tennis.client.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Match implements Serializable{
	private String matchType;
	private String matchTime;
	private String scoringType;
	private boolean indoor;
	private String matchUUID;
	private String pebbleUser;
	private int pebbleUserTpRating;
	private String opponent;
	private int opponentTpRating;
	private String comments;

	public String getMatchType() {
		return matchType;
	}

	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}

	public String getMatchUUID() {
		return matchUUID;
	}

	public void setMatchUUID(String matchUUID) {
		this.matchUUID = matchUUID;
	}

	public String getPebbleUser() {
		return pebbleUser;
	}

	public void setPebbleUser(String pebbleUser) {
		this.pebbleUser = pebbleUser;
	}

	public String getOpponent() {
		return opponent;
	}

	public void setOpponent(String opponent) {
		this.opponent = opponent;
	}

	public int getOpponentTpRating() {
		return opponentTpRating;
	}

	public void setOpponentTpRating(int opponentRating) {
		this.opponentTpRating = opponentRating;
	}

	public String getMatchTime() {
		return matchTime;
	}

	public void setMatchTime(String matchTime) {
		this.matchTime = matchTime;
	}

	public String getScoringType() {
		return scoringType;
	}

	public void setScoringType(String scoringType) {
		this.scoringType = scoringType;
	}

	public boolean isIndoor() {
		return indoor;
	}

	public void setIndoor(boolean indoor) {
		this.indoor = indoor;
	}

	public int getPebbleUserTpRating() {
		return pebbleUserTpRating;
	}

	public void setPebbleUserTpRating(int pebbleUserTpRating) {
		this.pebbleUserTpRating = pebbleUserTpRating;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}
