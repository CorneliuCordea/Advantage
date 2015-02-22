package com.cordea.tennis.client.layouts;

import java.util.Iterator;
import java.util.List;

import com.cordea.tennis.client.TennisService;
import com.cordea.tennis.client.TennisServiceAsync;
import com.cordea.tennis.client.model.Match;
import com.cordea.tennis.client.model.Point;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class HistoryLayout extends DockLayoutPanel {
	private static final String NOT_A_MATCH = "not a match";
	private final TennisServiceAsync tennisService = GWT.create(TennisService.class);
	private DockLayoutPanel layout = new DockLayoutPanel(Unit.PCT);

	public HistoryLayout(Unit unit) {
		super(unit);
		addNorth(createNorth(), 3);
	}

	private Widget createNorth() {
		final VerticalPanel h = new VerticalPanel();
		final ListBox historicalMatches = new ListBox();
		h.add(historicalMatches);
		historicalMatches.addItem("Select a match....", NOT_A_MATCH);
		historicalMatches.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				// TODO GET MATCH POINTS AND SHOW STATS
				tennisService.getMatch(historicalMatches.getSelectedValue(), new AsyncCallback<Match>() {

					@Override
					public void onSuccess(final Match match) {

						tennisService.getMatchPoints(historicalMatches.getSelectedValue(), new AsyncCallback<List<Point>>() {

							@Override
							public void onSuccess(List<Point> points) {
								remove(layout);
								layout = new LiveLayout(Unit.PCT, match, points);
								addSouth(layout, 97);
							}

							@Override
							public void onFailure(Throwable caught) {
							}
						});
					}

					@Override
					public void onFailure(Throwable caught) {

					}
				});
			}
		});
		tennisService.getAllMatches(new AsyncCallback<List<Match>>() {

			@Override
			public void onSuccess(List<Match> result) {
				for (Iterator<Match> iterator = result.iterator(); iterator.hasNext();) {
					Match match = iterator.next();
					historicalMatches.addItem(formatMatch(match), match.getMatchUUID());
				}
			}

			@Override
			public void onFailure(Throwable caught) {
			}
		});
		return h;
	}

	private String formatMatch(Match match) {
		return match.getPebbleUser() + " vs " + match.getOpponent() + " | " + match.getMatchTime();
	}
}
