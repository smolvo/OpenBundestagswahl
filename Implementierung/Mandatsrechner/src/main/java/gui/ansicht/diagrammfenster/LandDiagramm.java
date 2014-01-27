package main.java.gui.ansicht.diagrammfenster;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;

import main.java.model.Bundesland;
import main.java.model.Partei;
import main.java.model.Zweitstimme;

/**
 * Diese Klasse stellt das Diagramm der Landesansicht dar.
 * 
 * @author Anton
 * 
 */
public class LandDiagramm {

	/** reptäsentiert den Bereich auf dem das Diagramm angezeigt wird. */
	private final DiagrammFenster flaeche;

	/**
	 * Konstruktor erstellt ein Diagramm unter Verwendung der privaten Methode
	 * createChart(Bundesland) und fügt es hinzu.
	 * 
	 * @param bundLand
	 *            Bundesland
	 */
	public LandDiagramm(Bundesland bundLand, DiagrammFenster flaeche) {
		this.flaeche = flaeche;
		JFreeChart chart = createChart(bundLand);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				ChartPanel panel = (ChartPanel) e.getComponent();
				panel.setSize(resize());
			}

		});
		chartPanel.setPreferredSize(new Dimension(450, 250));
		flaeche.add(chartPanel, BorderLayout.CENTER);
	}

	/**
	 * Diese private Methode wird vom Konstruktor verwendet und erstellt das
	 * Diagramm.
	 * 
	 * @param bundLand
	 *            Bundesland
	 * @return Diagramm
	 */
	private JFreeChart createChart(Bundesland bundLand) {
		DefaultCategoryDataset result = new DefaultCategoryDataset();
		ArrayList<Partei> parteien = new ArrayList<Partei>();
		List<Zweitstimme> zw = bundLand.getZweitstimmenProPartei();
		Collections.sort(zw);
		for (int i = 0; i < 6; i++) {
			double proZweit = (Math
					.rint(((double) zw.get(i).getAnzahl() / (double) bundLand
							.getAnzahlZweitstimmen()) * 1000) / 10);
			parteien.add(zw.get(i).getPartei());
			result.setValue(proZweit, " ", zw.get(i).getPartei().getName());
		}
		// Collections.sort(parteien);
		double sonstige = 0;
		for (int i = 6; i < zw.size(); i++) {
			sonstige += (Math
					.rint(((double) zw.get(i).getAnzahl() / (double) bundLand
							.getAnzahlZweitstimmen()) * 1000) / 10);
		}
		result.setValue(sonstige, " ", "Sonstige");

		JFreeChart chart = ChartFactory.createBarChart("Stimmenanteile", null,
				"proz. Zweitstimmen", result, PlotOrientation.VERTICAL, false,
				false, false);
		CategoryPlot plot = chart.getCategoryPlot();

		// y-Achsenabschnitt festlegen
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setRange(new Range(0, 75));
		plot.setRangeAxis(rangeAxis);

		// färben der Parteienbalken
		Paint[] farben = new Paint[parteien.size() + 1];
		for (int i = 0; i < parteien.size(); i++) {
			farben[i] = parteien.get(i).getFarbe();
		}
		farben[farben.length - 1] = Color.GRAY;
		BarRenderer barRenderer = new BalkenRenderer(farben);
		plot.setRenderer(barRenderer);
		plot.setForegroundAlpha(1.0f);

		return chart;
	}

	/**
	 * Diese Methode gibt eine Dimension, abhängig von der Fläche auf der sich
	 * das Diagramm befindet, aus.
	 * 
	 * @return Dimension
	 */
	public Dimension resize() {
		return new Dimension(this.flaeche.getWidth(), (int) (0.6 * this.flaeche.getWidth()));
	}
}
