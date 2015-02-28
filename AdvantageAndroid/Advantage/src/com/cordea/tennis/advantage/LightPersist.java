package com.cordea.tennis.advantage;

public class LightPersist {
	public static String opponentName = "Name";
	public static String pebbleuserName = "Corneliu Cordea";
	public static String uuid = "NOT GENERATED";
	public static int pebbleuserRating = 6;
	public static int opponentRating = 6;
	public static String scoring = "2/3";
	public static boolean competition = false;
	public static boolean indoor = false;
	public static int pointIndex;

	public static int getPointIndex() {
		pointIndex = pointIndex + 1;
		return pointIndex;
	}
}
