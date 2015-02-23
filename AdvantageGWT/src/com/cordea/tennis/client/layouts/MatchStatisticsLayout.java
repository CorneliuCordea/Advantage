package com.cordea.tennis.client.layouts;

import java.util.ArrayList;
import java.util.List;

import com.cordea.tennis.client.business.MatchScore;
import com.cordea.tennis.client.business.ScoreProcessor;
import com.cordea.tennis.client.business.Statistics;
import com.cordea.tennis.client.model.Match;
import com.cordea.tennis.client.model.Point;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.ColumnChart;
import com.googlecode.gwt.charts.client.corechart.ColumnChartOptions;
import com.googlecode.gwt.charts.client.options.HAxis;
import com.googlecode.gwt.charts.client.options.VAxis;

public class MatchStatisticsLayout extends DockLayoutPanel {

	protected static final String FIRST_SERVE_IN = "1st Serve In";
	protected static final String ACES = "Aces";
	protected static final String DOUBLE_FAULTS = "Double Faults";
	protected static final String FIRST_SERVE_POINTS_WON = "1st Serve Points Won % ";
	protected static final String SECOND_SERVE_POINTS_WON = "2nd Serve Points Won %";
	protected static final String WINNERS = "Winners";
	protected static final String BREAK_POINTS = "Break Points";
	protected static final String UNFORCED = "Unforced";
	protected static final String FORCED = "Forced";
	protected static final String TOTAL_POINTS = "Total points";
	protected static final String PEBBLE = "Pebble ";
	protected static final String OPPONENT = "Opponent ";
	private ColumnChart gamesChart;
	private Grid chartsGrid = new Grid(1, 1);
	private List<String> displayedValues = new ArrayList<String>();

	private Label pebbleUser1stServe = new Label("10");
	private Label pebbleUserAces = new Label("10");
	private Label pebbleUserDoubleFaults = new Label("10");
	private Label pebbleUser1stServePointsWon = new Label("10");
	private Label pebbleUser2ndServePointsWon = new Label("10");
	private Label pebbleUserBreakPoints = new Label("10");
	private Label pebbleUserUnforcedPassive = new Label("10");
	private Label pebbleUserUnforcedActiveP = new Label("10");
	private Label pebbleUserUnforcedActiveN = new Label("10");
	private Label pebbleUserForced = new Label("10");
	private Label pebbleUserWinner = new Label("10");
	private Label pebbleUserTotalPoints = new Label("10");
	private Label pebbleUserUnforced = new Label("10");

	private Label opponent1stServe = new Label("10");
	private Label opponentAces = new Label("10");
	private Label opponentDoubleFaults = new Label("10");
	private Label opponent1stServePointsWon = new Label("10");
	private Label opponent2ndServePointsWon = new Label("10");
	private Label opponentBreakPoints = new Label("10");
	private Label opponentUnforcedPassive = new Label("10");
	private Label opponentUnforcedActiveP = new Label("10");
	private Label opponentUnforcedActiveN = new Label("10");
	private Label opponentForced = new Label("10");
	private Label opponentWinner = new Label("10");
	private Label opponentTotalPoints = new Label("10");
	private Label opponentUnforced = new Label("10");

	private Label player1Set1 = new Label("0");
	private Label player1Set2 = new Label("0");
	private Label player1Set3 = new Label("0");
	private Label player1GameScore = new Label("0");
	private Label player2Set1 = new Label("0");
	private Label player2Set2 = new Label("0");
	private Label player2Set3 = new Label("0");
	private Label player2GameScore = new Label("0");

	private Label player1Serving = new Label("*");
	private Label player2Serving = new Label("*");

	private ScoreProcessor gameScoreProcessor;

	private List<Point> points;
	private Match match;

	public MatchStatisticsLayout(Unit unit, Match match, List<Point> points) {
		super(unit);
		this.points = points;
		this.match = match;
		gameScoreProcessor = new ScoreProcessor(points, match);
		createWest();
		createEast();
	}

	private void createWest() {
		Grid scoreGrid = createScoreGrid(match);
		Grid statisticsGrid = createStatisticsGrid(match);
		ListBox gamesDropDownList = createGamesDropDownList();
		VerticalPanel liveInfoPannel = new VerticalPanel();
		addWest(liveInfoPannel, 25);
		liveInfoPannel.add(scoreGrid);
		liveInfoPannel.add(gamesDropDownList);
		liveInfoPannel.add(statisticsGrid);
		liveInfoPannel.setStyleName("allignCenter");

		showStatistics(gameScoreProcessor.getStatistics());
		for (int i = 1; i < gameScoreProcessor.getGameStatistics().size(); i++) {
			gamesDropDownList.addItem("Game " + i + " stats");
		}
		showScore(gameScoreProcessor);
	}

	private ListBox createGamesDropDownList() {
		final ListBox gamesDropDownList = new ListBox();
		gamesDropDownList.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				if (gamesDropDownList.getSelectedIndex() == 0)
					showStatistics(gameScoreProcessor.getStatistics());
				else
					showStatistics(gameScoreProcessor.getGameStatistics().get(gamesDropDownList.getSelectedIndex()));
			}
		});
		gamesDropDownList.addItem("Match Statistics");
		return gamesDropDownList;
	}

	private Grid createStatisticsGrid(Match liveMatch) {
		Grid statisticsGrid = new Grid(14, 5);
		statisticsGrid.setWidget(0, 0, new Label(liveMatch.getPebbleUser()));
		statisticsGrid.setWidget(0, 4, new Label(liveMatch.getOpponent()));

		addStatisticRow(statisticsGrid, 1, pebbleUser1stServe, opponent1stServe, FIRST_SERVE_IN, false, false);
		addStatisticRow(statisticsGrid, 2, pebbleUserAces, opponentAces, ACES, true, false);
		addStatisticRow(statisticsGrid, 3, pebbleUserDoubleFaults, opponentDoubleFaults, DOUBLE_FAULTS, true, false);
		addStatisticRow(statisticsGrid, 4, pebbleUser1stServePointsWon, opponent1stServePointsWon, FIRST_SERVE_POINTS_WON, false, false);
		addStatisticRow(statisticsGrid, 5, pebbleUser2ndServePointsWon, opponent2ndServePointsWon, SECOND_SERVE_POINTS_WON, false, false);
		addStatisticRow(statisticsGrid, 6, pebbleUserWinner, opponentWinner, WINNERS, true, false);
		addStatisticRow(statisticsGrid, 7, pebbleUserBreakPoints, opponentBreakPoints, BREAK_POINTS, false, false);
		addStatisticRow(statisticsGrid, 8, pebbleUserUnforced, opponentUnforced, UNFORCED, true, false);
		addStatisticRow(statisticsGrid, 9, pebbleUserUnforcedPassive, opponentUnforcedPassive, "Unforced Passive", false, false);
		addStatisticRow(statisticsGrid, 10, pebbleUserUnforcedActiveP, opponentUnforcedActiveP, "Unforced Attack+", false, false);
		addStatisticRow(statisticsGrid, 11, pebbleUserUnforcedActiveN, opponentUnforcedActiveN, "Unforced Attack-", false, false);
		addStatisticRow(statisticsGrid, 12, pebbleUserForced, opponentForced, FORCED, true, false);
		addStatisticRow(statisticsGrid, 13, pebbleUserTotalPoints, opponentTotalPoints, TOTAL_POINTS, true, true);
		return statisticsGrid;
	}

	private Grid createScoreGrid(Match liveMatch) {
		Grid scoreGrid = new Grid(3, 6);
		scoreGrid.setWidget(0, 2, new Label("Set 1"));
		scoreGrid.setWidget(0, 3, new Label("Set 2"));
		scoreGrid.setWidget(0, 4, new Label("Set 3"));
		scoreGrid.setWidget(0, 5, new Label("GameScore"));

		scoreGrid.setWidget(1, 0, player1Serving);
		scoreGrid.setWidget(1, 1, new Label(liveMatch.getPebbleUser()));
		scoreGrid.setWidget(1, 2, player1Set1);
		scoreGrid.setWidget(1, 3, player1Set2);
		scoreGrid.setWidget(1, 4, player1Set3);
		scoreGrid.setWidget(1, 5, player1GameScore);

		scoreGrid.setWidget(2, 0, player2Serving);
		scoreGrid.setWidget(2, 1, new Label(liveMatch.getOpponent()));
		scoreGrid.setWidget(2, 2, player2Set1);
		scoreGrid.setWidget(2, 3, player2Set2);
		scoreGrid.setWidget(2, 4, player2Set3);
		scoreGrid.setWidget(2, 5, player2GameScore);

		scoreGrid.setBorderWidth(2);
		return scoreGrid;
	}

	private void showScore(ScoreProcessor gameScoreProcessor) {
		if (gameScoreProcessor.getMatchScore().getPlayerServing() == MatchScore.PLAYER1_SERVING) {
			player1Serving.setText("*");
			player2Serving.setText("");
		} else {
			player1Serving.setText("");
			player2Serving.setText("*");
		}

		int player1Points = gameScoreProcessor.getMatchScore().getPlayer1().getPoints();
		int player2Points = gameScoreProcessor.getMatchScore().getPlayer2().getPoints();
		if (player1Points + player2Points >= 8 && gameScoreProcessor.getMatchScore().getGameType() == MatchScore.GAME_TYPE_NORMAL_GAME)
			if (player1Points > player2Points) {
				player1Points = 4;
				player2Points = 3;
			} else if (player1Points < player2Points) {
				player1Points = 3;
				player2Points = 4;
			} else {
				player1Points = 3;
				player2Points = 3;
			}
		String[] gameScore = { "0", "15", "30", "40", "ADV" };
		String player1Score = "";
		String player2Score = "";
		if (gameScoreProcessor.getMatchScore().getGameType() == MatchScore.GAME_TYPE_NORMAL_GAME) {
			player1Score = gameScore[player1Points];
			player2Score = gameScore[player2Points];
		} else {
			player1Score = "" + player1Points;
			player2Score = "" + player2Points;
		}
		player1GameScore.setText(player1Score);
		player1Set1.setText(gameScoreProcessor.getMatchScore().getPlayer1().getGames()[0] + "");
		player1Set2.setText(gameScoreProcessor.getMatchScore().getPlayer1().getGames()[1] + "");
		player1Set3.setText(gameScoreProcessor.getMatchScore().getPlayer1().getGames()[2] + "");

		player2GameScore.setText(player2Score);
		player2Set1.setText(gameScoreProcessor.getMatchScore().getPlayer2().getGames()[0] + "");
		player2Set2.setText(gameScoreProcessor.getMatchScore().getPlayer2().getGames()[1] + "");
		player2Set3.setText(gameScoreProcessor.getMatchScore().getPlayer2().getGames()[2] + "");
	}

	private void showStatistics(Statistics stats) {
		int pebbleUserFirstServeWon = stats.getPebbleUserFirstServeWon();
		int pebbleUserSecondServeWon = stats.getPebbleUserSecondServeWon();
		int pebbleUserFirstServeIn = stats.getPebbleUserFirstServeIn();
		int pebbleUserSecondServeIn = stats.getPebbleUserSecondServeIn();
		int pebbleUserTotalServe = stats.getPebbleUserServingPoint();

		double pebbleUserFirstServePercentage = 100;
		if (pebbleUserTotalServe != 0)
			pebbleUserFirstServePercentage = 100 * pebbleUserFirstServeIn / pebbleUserTotalServe;

		double pebbleUserFirstServeWonPercentage = 100;
		if (pebbleUserFirstServeIn != 0)
			pebbleUserFirstServeWonPercentage = 100 * pebbleUserFirstServeWon / pebbleUserFirstServeIn;

		double pebbleUserSecondServeWonPercentage = 100;
		if (pebbleUserSecondServeIn != 0)
			pebbleUserSecondServeWonPercentage = 100 * pebbleUserSecondServeWon / pebbleUserSecondServeIn;

		pebbleUser1stServe
				.setText("" + pebbleUserFirstServeIn + " of " + pebbleUserTotalServe + "=" + pebbleUserFirstServePercentage + "%");
		pebbleUserAces.setText("" + stats.getPebbleUserAce());
		pebbleUserDoubleFaults.setText("" + stats.getPebbleUserDoubleFault());
		pebbleUser1stServePointsWon.setText("" + pebbleUserFirstServeWon + " of " + pebbleUserFirstServeIn + "="
				+ pebbleUserFirstServeWonPercentage + "%");
		pebbleUser2ndServePointsWon.setText("" + pebbleUserSecondServeWon + " of " + pebbleUserSecondServeIn + "="
				+ pebbleUserSecondServeWonPercentage + "%");
		pebbleUserBreakPoints.setText("" + stats.getPebbleUserBreakPointsWon() + "/" + stats.getPebbleUserBreakPoints());
		pebbleUserUnforced.setText("" + stats.getPebbleUserUnforced());
		pebbleUserUnforcedPassive.setText("" + stats.getPebbleUserUnforcedPassive());
		pebbleUserUnforcedActiveP.setText("" + stats.getPebbleUserUnforcedActivePlus());
		pebbleUserUnforcedActiveN.setText("" + stats.getPebbleUserUnforcedActiveMinus());
		pebbleUserForced.setText("" + stats.getPebbleUserForced());
		pebbleUserWinner.setText("" + stats.getPebbleUserWinner());
		pebbleUserTotalPoints.setText("" + stats.getPebbleUserPointsWon());

		int opponentFirstServeWon = stats.getOpponentFirstServeWon();
		int opponentSecondServeWon = stats.getOpponentSecondServeWon();
		int opponentFirstServeIn = stats.getOpponentFirstServeIn();
		int opponentSecondServeIn = stats.getOpponentSecondServeIn();
		int opponentTotalServe = stats.getOpponentServingPoint();

		double opponentFirstServePercentage = 100;
		if (opponentFirstServeIn != 0 && opponentTotalServe != 0)
			opponentFirstServePercentage = 100 * opponentFirstServeIn / opponentTotalServe;

		double opponentFirstServeWonPercentage = 100;
		if (opponentFirstServeIn != 0 && opponentFirstServeWon != 0)
			opponentFirstServeWonPercentage = 100 * opponentFirstServeWon / opponentFirstServeIn;

		double opponentSecondServeWonPercentage = 100;
		if (opponentSecondServeIn != 0 && opponentSecondServeWon != 0)
			opponentSecondServeWonPercentage = 100 * opponentSecondServeWon / opponentSecondServeIn;

		opponent1stServe.setText("" + opponentFirstServeIn + " of " + opponentTotalServe + "=" + opponentFirstServePercentage + "%");
		opponentAces.setText("" + stats.getOpponentAce());
		opponentDoubleFaults.setText("" + stats.getOpponentDoubleFault());
		opponent1stServePointsWon.setText("" + opponentFirstServeWon + " of " + opponentFirstServeIn + "="
				+ opponentFirstServeWonPercentage + "%");
		opponent2ndServePointsWon.setText("" + opponentSecondServeWon + " of " + opponentSecondServeIn + "="
				+ opponentSecondServeWonPercentage + "%");
		opponentBreakPoints.setText("" + stats.getOpponentBreakPointsWon() + "/" + stats.getOpponentBreakPoints());
		opponentUnforced.setText("" + stats.getOpponentUnforced());
		opponentUnforcedPassive.setText("" + stats.getOpponentUnforcedPassive());
		opponentUnforcedActiveP.setText("" + stats.getOpponentUnforcedActivePlus());
		opponentUnforcedActiveN.setText("" + stats.getOpponentUnforcedActiveMinus());
		opponentForced.setText("" + stats.getOpponentForced());
		opponentWinner.setText("" + stats.getOpponentWinner());
		opponentTotalPoints.setText("" + stats.getOpponentPointsWon());
	}

	private void addStatisticRow(HTMLTable statisticsGrid, int rowNumber, final Label pebbleUser, final Label opponent,
			final String buttonCaption, boolean displayOnChart, boolean showOnChart) {
		Label currentStatisticLabel = new Label(buttonCaption);
		// currentStatisticLabel.setWidth("100%");
		CheckBox pebbleUserCheckBox = new CheckBox();
		pebbleUserCheckBox.setValue(showOnChart);
		if (showOnChart)
			displayedValues.add(PEBBLE + buttonCaption);
		pebbleUserCheckBox.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					displayedValues.add(PEBBLE + buttonCaption);
				} else {
					displayedValues.remove(PEBBLE + buttonCaption);
				}
				drawChart();
			}
		});
		CheckBox opponentCheckBox = new CheckBox();
		opponentCheckBox.setValue(showOnChart);
		if (showOnChart)
			displayedValues.add(OPPONENT + buttonCaption);
		opponentCheckBox.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					displayedValues.add(OPPONENT + buttonCaption);
				} else {
					displayedValues.remove(OPPONENT + buttonCaption);
				}
				drawChart();
			}
		});
		statisticsGrid.setWidget(rowNumber, 0, pebbleUser);
		if (displayOnChart)
			statisticsGrid.setWidget(rowNumber, 1, pebbleUserCheckBox);
		statisticsGrid.setWidget(rowNumber, 2, currentStatisticLabel);
		if (displayOnChart)
			statisticsGrid.setWidget(rowNumber, 3, opponentCheckBox);
		statisticsGrid.setWidget(rowNumber, 4, opponent);
	}

	private void createEast() {
		addEast(chartsGrid, 75);
		chartsGrid.setBorderWidth(1);
		chartsGrid.setWidth("100%");
		chartsGrid.setHeight("100%");
		initializeChart("Games Statistics");
	}

	private void initializeChart(final String title) {
		ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
		chartLoader.loadApi(new Runnable() {

			@Override
			public void run() {
				// Create and attach the chart
				gamesChart = new ColumnChart();
				gamesChart.setHeight("200px");
				chartsGrid.setWidget(0, 0, gamesChart);
				drawChart();
			}
		});
	}

	private void drawChart() {

		List<Statistics> gameStats = gameScoreProcessor.getGameStatistics();
		int[][] values = new int[displayedValues.size()][gameStats.size() - 1];
		String[] stats = new String[displayedValues.size()];
		for (int index = 0; index < displayedValues.size(); index++) {
			stats[index] = displayedValues.get(index);
			if (displayedValues.get(index).equals(PEBBLE + FORCED) && displayedValues.contains(PEBBLE + FORCED)) {
				for (int i = 1; i < gameStats.size(); i++) {
					values[index][i - 1] = gameStats.get(i).getPebbleUserForced();
				}
			} else if (displayedValues.get(index).equals(OPPONENT + FORCED) && displayedValues.contains(OPPONENT + FORCED)) {
				for (int i = 1; i < gameStats.size(); i++) {
					values[index][i - 1] = gameStats.get(i).getOpponentForced();
				}
			} else if (displayedValues.get(index).equals(PEBBLE + ACES) && displayedValues.contains(PEBBLE + ACES)) {
				for (int i = 1; i < gameStats.size(); i++) {
					values[index][i - 1] = gameStats.get(i).getPebbleUserAce();
				}
			} else if (displayedValues.get(index).equals(OPPONENT + ACES) && displayedValues.contains(OPPONENT + ACES)) {
				for (int i = 1; i < gameStats.size(); i++) {
					values[index][i - 1] = gameStats.get(i).getOpponentAce();
				}
			} else if (displayedValues.get(index).equals(PEBBLE + DOUBLE_FAULTS) && displayedValues.contains(PEBBLE + DOUBLE_FAULTS)) {
				for (int i = 1; i < gameStats.size(); i++) {
					values[index][i - 1] = gameStats.get(i).getPebbleUserDoubleFault();
				}
			} else if (displayedValues.get(index).equals(OPPONENT + DOUBLE_FAULTS) && displayedValues.contains(OPPONENT + DOUBLE_FAULTS)) {
				for (int i = 1; i < gameStats.size(); i++) {
					values[index][i - 1] = gameStats.get(i).getOpponentDoubleFault();
				}
			} else if (displayedValues.get(index).equals(PEBBLE + WINNERS) && displayedValues.contains(PEBBLE + WINNERS)) {
				for (int i = 1; i < gameStats.size(); i++) {
					values[index][i - 1] = gameStats.get(i).getPebbleUserWinner();
				}
			} else if (displayedValues.get(index).equals(OPPONENT + WINNERS) && displayedValues.contains(OPPONENT + WINNERS)) {
				for (int i = 1; i < gameStats.size(); i++) {
					values[index][i - 1] = gameStats.get(i).getOpponentWinner();
				}
			} else if (displayedValues.get(index).equals(PEBBLE + UNFORCED) && displayedValues.contains(PEBBLE + UNFORCED)) {
				for (int i = 1; i < gameStats.size(); i++) {
					values[index][i - 1] = gameStats.get(i).getPebbleUserUnforced();
				}
			} else if (displayedValues.get(index).equals(OPPONENT + UNFORCED) && displayedValues.contains(OPPONENT + UNFORCED)) {
				for (int i = 1; i < gameStats.size(); i++) {
					values[index][i - 1] = gameStats.get(i).getOpponentUnforced();
				}
			} else if (displayedValues.get(index).equals(PEBBLE + TOTAL_POINTS) && displayedValues.contains(PEBBLE + TOTAL_POINTS)) {
				for (int i = 1; i < gameStats.size(); i++) {
					values[index][i - 1] = gameStats.get(i).getPebbleUserPointsWon();
				}
			} else if (displayedValues.get(index).equals(OPPONENT + TOTAL_POINTS) && displayedValues.contains(OPPONENT + TOTAL_POINTS)) {
				for (int i = 1; i < gameStats.size(); i++) {
					values[index][i - 1] = gameStats.get(i).getOpponentPointsWon();
				}
			}
		}

		// new String[] { "Forced", "Unforced", "Winners", "Doubles" };

		String[] games = new String[gameStats.size() - 1];
		for (int i = 1; i < gameStats.size(); i++) {
			if (gameStats.get(i).getPebbleUserPointsWon() > gameStats.get(i).getOpponentPointsWon())
				games[i - 1] = i + "W";
			else if (gameStats.get(i).getPebbleUserPointsWon() < gameStats.get(i).getOpponentPointsWon())
				games[i - 1] = i + "L";
			else
				games[i - 1] = i + "";
		}

		// Prepare the data
		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, "Number of...");
		for (int i = 0; i < stats.length; i++) {
			dataTable.addColumn(ColumnType.NUMBER, stats[i]);
		}
		dataTable.addRows(games.length);
		for (int i = 0; i < games.length; i++) {
			dataTable.setValue(i, 0, games[i]);
		}
		for (int col = 0; col < values.length; col++) {
			for (int row = 0; row < values[col].length; row++) {
				dataTable.setValue(row, col + 1, values[col][row]);
			}
		}

		// Set options
		ColumnChartOptions options = ColumnChartOptions.create();
		options.setFontName("Tahoma");
		options.setTitle("Game statistics over time");
		HAxis hAxis = HAxis.create("Games");
		// hAxis.setSlantedTextAngle(90);
		hAxis.setMaxTextLines(5);
		options.setHAxis(hAxis);
		options.setVAxis(VAxis.create("Number of..."));

		// Draw the chart
		gamesChart.draw(dataTable, options);
	}

}
