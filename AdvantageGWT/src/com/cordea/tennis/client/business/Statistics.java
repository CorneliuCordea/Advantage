package com.cordea.tennis.client.business;

public class Statistics {

	private int totalPoints = 0;
	private int opponentSecondServeIn = 0;
	private int pebbleUserSecondServeWon = 0;
	private int pebbleUserSecondServeIn = 0;
	private int opponentFirstServeWon = 0;
	private int pebbleUserFirstServeWon = 0;
	private int pebbleUserBreakPointsWon = 0;
	private int pebbleUserBreakPoints = 0;
	private int opponentBreakPointsWon = 0;
	private int opponentBreakPoints = 0;
	private int opponentUnforcedPassive = 0;
	private int opponentUnforcedActiveMinus = 0;
	private int opponentUnforcedActivePlus = 0;
	private int pebbleUserUnforcedPassive = 0;
	private int pebbleUserUnforcedActiveMinus = 0;
	private int pebbleUserUnforcedActivePlus = 0;
	private int opponentForced = 0;
	private int pebbleUserForced = 0;
	private int opponentAce = 0;
	private int pebbleUserAce = 0;
	private int opponentDoubleFault = 0;
	private int pebbleUserDoubleFault = 0;
	private int opponentFirstServeIn = 0;
	private int opponentWinner = 0;
	private int pebbleUserWinner = 0;
	private int opponentSecondServeWon = 0;
	private int pebbleUserFirstServeIn = 0;
	private int opponentServingPointsWon = 0;
	private int pebbleUserServingPointsWon = 0;
	private int opponentServingPoint = 0;
	private int pebbleUserServingPoint = 0;
	private int opponentPointWon = 0;
	private int pebbleUserPointsWon = 0;
	private int pebbleUserUnforced=0;
	private int opponentUnforced=0;

	public void addTotalPoints() {
		totalPoints++;

	}

	public void addPebbleUserPointWon() {

		pebbleUserPointsWon++;
	}

	public void addOpponentPointWon() {
		opponentPointWon++;

	}

	public void addPebbleUserServingPoint() {
		pebbleUserServingPoint++;

	}

	public void addOpponentServingPoint() {
		opponentServingPoint++;

	}

	public void addPebbleUserServingPointsWon() {
		pebbleUserServingPointsWon++;

	}

	public void addOpponentServingPointsWon() {
		opponentServingPointsWon++;

	}

	public void addPebbleUserFirstServeIn() {
		pebbleUserFirstServeIn++;

	}

	public void addOpponentFirstServeIn() {
		opponentFirstServeIn++;

	}

	public void addPebbleUserDoubleFault() {
		pebbleUserDoubleFault++;

	}

	public void addOpponentDoubleFault() {
		opponentDoubleFault++;

	}

	public void addPebbleUserAce() {

		pebbleUserAce++;
	}

	public void addOpponentAce() {
		opponentAce++;

	}

	public void addPebbleUserForced() {
		pebbleUserForced++;

	}

	public void addOpponentForced() {

		opponentForced++;
	}

	public void addPebbleUserUnforcedActivePlus() {

		pebbleUserUnforcedActivePlus++;
	}

	public void addPebbleUserUnforcedActiveMinus() {

		pebbleUserUnforcedActiveMinus++;
	}

	public void addPebbleUserUnforcedPassive() {
		pebbleUserUnforcedPassive++;

	}

	public void addOpponentUnforcedActivePlus() {
		opponentUnforcedActivePlus++;

	}

	public void addOpponentUnforcedActiveMinus() {
		opponentUnforcedActiveMinus++;

	}

	public void addOpponentUnforcedPassive() {

		opponentUnforcedPassive++;
	}

	public void addOpponentBreakPoints() {

		opponentBreakPoints++;
	}

	public void addOpponentBreakPointsWon() {

		opponentBreakPointsWon++;
	}

	public void addPebbleUserBreakPoints() {
		pebbleUserBreakPoints++;

	}

	public void addPebbleUserBreakPointsWon() {

		pebbleUserBreakPointsWon++;
	}

	public void addPebbleUserFirstServeWon() {
		pebbleUserFirstServeWon++;

	}

	public void addOpponentFirstServeWon() {
		opponentFirstServeWon++;

	}

	public void addPebbleUserSecondServeIn() {

		pebbleUserSecondServeIn++;
	}

	public void addPebbleUserSecondServeWon() {

		pebbleUserSecondServeWon++;
	}

	public void addOpponentSecondServeIn() {
		opponentSecondServeIn++;

	}

	public void addOpponentSecondServeWon() {

		opponentSecondServeWon++;
	}

	public void addPebbleUserWinner() {
		pebbleUserWinner++;

	}

	public void addOpponentWinner() {
		opponentWinner++;

	}
	public void addPebbleUserUnforced() {
		pebbleUserUnforced++;
		
	}

	public void addOpponentUnforced() {
		opponentUnforced++;
	}

	
	public int getTotalPoints() {
		return totalPoints;
	}

	public int getOpponentSecondServeIn() {
		return opponentSecondServeIn;
	}

	public int getPebbleUserSecondServeWon() {
		return pebbleUserSecondServeWon;
	}

	public int getPebbleUserSecondServeIn() {
		return pebbleUserSecondServeIn;
	}

	public int getOpponentFirstServeWon() {
		return opponentFirstServeWon;
	}

	public int getPebbleUserFirstServeWon() {
		return pebbleUserFirstServeWon;
	}

	public int getPebbleUserBreakPointsWon() {
		return pebbleUserBreakPointsWon;
	}

	public int getPebbleUserBreakPoints() {
		return pebbleUserBreakPoints;
	}

	public int getOpponentBreakPointsWon() {
		return opponentBreakPointsWon;
	}

	public int getOpponentBreakPoints() {
		return opponentBreakPoints;
	}

	public int getOpponentUnforcedPassive() {
		return opponentUnforcedPassive;
	}

	public int getOpponentUnforcedActiveMinus() {
		return opponentUnforcedActiveMinus;
	}

	public int getOpponentUnforcedActivePlus() {
		return opponentUnforcedActivePlus;
	}

	public int getPebbleUserUnforcedPassive() {
		return pebbleUserUnforcedPassive;
	}

	public int getPebbleUserUnforcedActiveMinus() {
		return pebbleUserUnforcedActiveMinus;
	}

	public int getPebbleUserUnforcedActivePlus() {
		return pebbleUserUnforcedActivePlus;
	}

	public int getOpponentForced() {
		return opponentForced;
	}

	public int getPebbleUserForced() {
		return pebbleUserForced;
	}

	public int getOpponentAce() {
		return opponentAce;
	}

	public int getPebbleUserAce() {
		return pebbleUserAce;
	}

	public int getOpponentDoubleFault() {
		return opponentDoubleFault;
	}

	public int getPebbleUserDoubleFault() {
		return pebbleUserDoubleFault;
	}

	public int getOpponentFirstServeIn() {
		return opponentFirstServeIn;
	}

	public int getOpponentWinner() {
		return opponentWinner;
	}

	public int getPebbleUserWinner() {
		return pebbleUserWinner;
	}

	public int getOpponentSecondServeWon() {
		return opponentSecondServeWon;
	}

	public int getPebbleUserFirstServeIn() {
		return pebbleUserFirstServeIn;
	}

	public int getOpponentServingPointsWon() {
		return opponentServingPointsWon;
	}

	public int getPebbleUserServingPointsWon() {
		return pebbleUserServingPointsWon;
	}

	public int getOpponentServingPoint() {
		return opponentServingPoint;
	}

	public int getPebbleUserServingPoint() {
		return pebbleUserServingPoint;
	}

	public int getOpponentPointsWon() {
		return opponentPointWon;
	}

	public int getPebbleUserPointsWon() {
		return pebbleUserPointsWon;
	}

	public int getPebbleUserUnforced() {
		return pebbleUserUnforced;
	}

	public void setPebbleUserUnforced(int pebbleUserUnforced) {
		this.pebbleUserUnforced = pebbleUserUnforced;
	}

	public int getOpponentUnforced() {
		return opponentUnforced;
	}

	public void setOpponentUnforced(int opponentUnforced) {
		this.opponentUnforced = opponentUnforced;
	}

}
