package com.cordea.tennis.client.business;

public class Player {
	private int points = 0;
	private int[] games = { 0, 0, 0 };
	private int sets = 0;

	public int getPoints() {
		return points;
	}

	public void addPoint() {
		this.points++;
	}

	public int[] getGames() {
		return games;
	}

	public void addGame(int set) {
		this.games[set]++;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void setGames(int[] games) {
		this.games = games;
	}

	public void setSets(int sets) {
		this.sets = sets;
	}

	public int getSets() {
		return sets;
	}

	public void addSet() {
		this.sets++;
	}

}
