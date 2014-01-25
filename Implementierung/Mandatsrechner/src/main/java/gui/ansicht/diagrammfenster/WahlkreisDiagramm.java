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
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;

import main.java.model.Erststimme;
import main.java.model.Partei;
import main.java.model.Wahlkreis;

/**
 * Diese Klasse repräsentiert das Diagramm der Wahlkreisansicht.
 * 
 * @author Anton
 * 
 */
public class WahlkreisDiagramm {

	/** reptäsentiert den Bereich auf dem das Diagramm angezeigt wird. */
	private final JPanel flaeche;

	/**
	 * Der Konstruktor erstellt das Diagramm.
	 * 
	 * @param wk
	 *            Wahlkreis
	 */
	public WahlkreisDiagramm(Wahlkreis wk, JPanel flaeche) {
		this.flaeche = flaeche;
		JFreeChart chart = createChart(wk);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(450, 250));
		flaeche.add(chartPanel, BorderLayout.CENTER);
	}

	/**
	 * Diese Mehthode wird vom Konstruktor verwendet, um das Diagramm zu
	 * erstellen.
	 * 
	 * @param wk
	 *            der Wahlkreis
	 * @return JFreeChart
	 */
	private JFreeChart createChart(Wahlkreis wk) {
		DefaultCategoryDataset result = new DefaultCategoryDataset();
		ArrayList<Partei> parteien = new ArrayList<Partei>();
		List<Erststimme> er = wk.getErststimmen();
		Collections.sort(er);
		for (int i = 0; i < 4; i++) {
			double proZweit = (Math
					.rint(((double) er.get(i).getAnzahl() / (double) wk
							.getErststimmeGesamt()) * 1000) / 10);
			parteien.add(er.get(i).getKandidat().getPartei());
			result.setValue(proZweit, " ", er.get(i).getKandidat().getPartei()
					.getName());
		}
		double sonstige = 0;
		for (int i = 4; i < er.size(); i++) {
			sonstige += (Math
					.rint(((double) er.get(i).getAnzahl() / (double) wk
							.getErststimmeGesamt()) * 1000) / 10);
		}
		result.setValue(sonstige, " ", "Sonstige");

		JFreeChart chart = ChartFactory.createBarChart("Stimmenanteile", null,
				null, result, PlotOrientation.VERTICAL, false, false, false);
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
