package com.cordea.tennis.client;

import java.util.List;

import com.cordea.tennis.client.layouts.HistoryLayout;
import com.cordea.tennis.client.layouts.MatchStatisticsLayout;
import com.cordea.tennis.client.model.Match;
import com.cordea.tennis.client.model.Point;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;

public class AdvantageGWT implements EntryPoint {

	@Override
	public void onModuleLoad() {
		Window.enableScrolling(true);

		final TennisServiceAsync tennisService = GWT.create(TennisService.class);
		tennisService.getLiveMatch(new AsyncCallback<Match>() {

			@Override
			public void onSuccess(final Match liveMatch) {
				tennisService.getLiveMatchPoints(new AsyncCallback<List<Point>>() {

					@Override
					public void onSuccess(List<Point> livePoints) {
						final TabLayoutPanel mainTabs = new TabLayoutPanel(20, Unit.PX);
						MatchStatisticsLayout liveLayout = new MatchStatisticsLayout(Unit.PCT, liveMatch, livePoints);
						mainTabs.add(liveLayout, "Live");
						HistoryLayout historyLayout = new HistoryLayout(Unit.PCT);
						mainTabs.add(historyLayout, "History");
						mainTabs.add(new Label("Not yet available!"), "Statistics");
						RootLayoutPanel.get().add(mainTabs);
						
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
}
