package com.cordea.tennis.advantage;

public class PointManager {
	private static int UnforcedPassiveOrMask = 512;// 01000000000=512 unforced
													// passive
	private static int UnforcedActiveInopportune = 1024;// 10000000000=1024
														// unforced active
														// negative
	private static int UnforcedActiveOpportune = 1536;// 11000000000=1536
														// unforced active
														// positive
	private static int AtNetOrMask = 256;// 9th bit is for atNet
	private static int AceOrMask = 128;
	private static int DoubleOrMask = 64;
	private static int WinOrMask = 32;
	private static int FirstOrMask = 16;
	private static int ForcedOrMask = 8;
	private static int WinnerOrMask = 4;
	private static int ForehandOrMask = 2;
	private static int PebbleUserServingOrMask = 1;

	public static boolean isAce(int point) {
		return (point | AceOrMask) == point;
	}

	public static boolean isDouble(int point) {
		return (point | DoubleOrMask) == point;
	}

	public static boolean isWin(int point) {
		return (point | WinOrMask) == point;
	}

	public static boolean isFirstServe(int point) {
		return (point | FirstOrMask) == point;
	}

	public static boolean isForced(int point) {
		return (point | ForcedOrMask) == point;
	}

	public static boolean isWinner(int point) {
		return (point | WinnerOrMask) == point;
	}

	public static boolean isForehand(int point) {
		return (point | ForehandOrMask) == point;
	}

	public static boolean isPeebbleUserServing(int point) {
		return (point | PebbleUserServingOrMask) == point;
	}

	public static boolean isPointAtNet(int point) {
		return (point | AtNetOrMask) == point;
	}

	public static boolean isUnforcedPassive(int point) {
		return (point | UnforcedPassiveOrMask) == point;
	}

	public static boolean isUnforcedActiveOpportune(int point) {
		return (point | UnforcedActiveOpportune) == point;
	}

	public static boolean isUnforcedActiveInopportune(int point) {
		return (point | UnforcedActiveInopportune) == point;
	}

}
