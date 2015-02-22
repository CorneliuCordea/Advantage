package com.cordea.tennis.client.charts;


import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.BarChart;

public class DiffBarChart extends DockLayoutPanel {
	private BarChart chart;

	public DiffBarChart() {
		super(Unit.PX);
		Button b=new Button("Da-i Sa Puta!");
		addNorth(b, 100);
		b.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				initialize();
			}
		});
		
	}

	private void initialize() {
		ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
		chartLoader.loadApi(new Runnable() {

			@Override
			public void run() {
				// Create and attach the chart
				chart = new BarChart();
				add(chart);
				draw();
			}
		});
	}

	private void draw() {
		// Prepare the data
		DataTable colOldData = DataTable.create();
		colOldData.addColumn(ColumnType.STRING, "Name");
		colOldData.addColumn(ColumnType.NUMBER, "Popularity");
		colOldData.addRow("Cesar", 250);
		colOldData.addRow("Rachel", 4200);
		colOldData.addRow("Patrick", 2900);
		colOldData.addRow("Eric", 8200);

		DataTable colNewData = DataTable.create();
		colNewData.addColumn(ColumnType.STRING, "Name");
		colNewData.addColumn(ColumnType.NUMBER, "Popularity");
		colNewData.addRow("Cesar", 370);
		colNewData.addRow("Rachel", 600);
		colNewData.addRow("Patrick", 700);
		colNewData.addRow("Eric", 1500);

		chart.draw(chart.computeDiff(colOldData, colNewData));
	}
}
