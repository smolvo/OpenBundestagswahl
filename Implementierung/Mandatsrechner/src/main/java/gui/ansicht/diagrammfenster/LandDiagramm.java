package main.java.gui.ansicht.diagrammfenster;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.axis.ValueAxis;
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

	private static final long serialVersionUID = 5982851750497212488L;

	/** reptäsentiert den Bereich auf dem das Diagramm angezeigt wird. */
	private final JPanel flaeche;

	/**
	 * Der Konstruktor erstellt das Diagramm.
	 * 
	 * @param bundLand
	 *            Bundesland
	 */
	public LandDiagramm(Bundesland bundLand, JPanel flaeche) {
		this.flaeche = flaeche;
		JFreeChart chart = createChart(bundLand);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(450, 250));
		flaeche.add(chartPanel, BorderLayout.CENTER);
	}

	/**
	 * Diese Methode erstellt das Diagramm.
	 * 
	 * @param bundLand
	 *            Bundesland
	 * @return Diagramm
	 */
	private JFreeChart createChart(Bundesland bundLand) {
		DefaultCategoryDataset result = new DefaultCategoryDataset();
		ArrayList<Partei> parteien = new ArrayList<Partei>();
		List<Zweitstimme> zw = bundLand.getZweitstimmen();
		Collections.sort(zw);
		for (int i = 0; i < 6; i++) {
			double proZweit = (Math
					.rint(((double) zw.get(i).getAnzahl() / (double) bundLand
							.getAnzahlZweitstimmen()) * 1000) / 10);
			parteien.add(zw.get(i).getPartei());
			result.setValue(proZweit, " ", zw.get(i).getPartei().getName());
		}
//		Collections.sort(parteien);
		double sonstige = 0;
		for (int i = 6; i < zw.size(); i++) {
			sonstige += (Math
					.rint(((double) zw.get(i).getAnzahl() / (double) bundLand
							.getAnzahlZweitstimmen()) * 1000) / 10);
		}
		result.setValue(sonstige, " ", "Sonstige");

		JFreeChart chart = ChartFactory.createBarChart("Stimmenanteile", null,
				"proz. Zweitstimmen", result, PlotOrientation.VERTICAL, false, false, false);
		CategoryPlot plot = chart.getCategoryPlot();

		// y-Achsenabschnitt festlegen
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setRange(new Range(0, 60));
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
}
