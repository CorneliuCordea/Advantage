package com.cordea.tennis.client.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Point implements Serializable {
	private boolean pebbleuserServing;/* true/false */
	private boolean win;/* true/false */
	private boolean firstServe;/* true/false */
	private boolean doubleFault;/* true/false */
	private boolean ace;/* true/false */
	private boolean winner;/* true/false */
	private boolean forced;/* true/false */
	private String unforcedType;/* active+/-/passive */
	private String handSide;/* backHand/foreHand */
	private int pointIndex;
	private boolean pointAtNet;/* true/false */

	public boolean isPebbleuserServing() {
		return pebbleuserServing;
	}

	public void setPebbleuserServing(boolean playerServing) {
		this.pebbleuserServing = playerServing;
	}

	public boolean isFirstServe() {
		return firstServe;
	}

	public void setFirstServe(boolean firstServe) {
		this.firstServe = firstServe;
	}

	public boolean isDoubleFault() {
		return doubleFault;
	}

	public void setDoubleFault(boolean doubleFault) {
		this.doubleFault = doubleFault;
	}

	public boolean isAce() {
		return ace;
	}

	public void setAce(boolean ace) {
		this.ace = ace;
	}

	public boolean isWinner() {
		return winner;
	}

	public void setWinner(boolean winner) {
		this.winner = winner;
	}

	public boolean isForced() {
		return forced;
	}

	public void setForced(boolean forced) {
		this.forced = forced;
	}

	public String getUnforcedType() {
		return unforcedType;
	}

	public void setUnforcedType(String unforcedType) {
		this.unforcedType = unforcedType;
	}

	public String getHandSide() {
		return handSide;
	}

	public void setHandSide(String handSide) {
		this.handSide = handSide;
	}

	public int getPointIndex() {
		return pointIndex;
	}

	public void setPointIndex(int pointIndex) {
		this.pointIndex = pointIndex;
	}

	public boolean isPointAtNet() {
		return pointAtNet;
	}

	public void setPointAtNet(boolean netPoint) {
		this.pointAtNet = netPoint;
	}

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}
}
