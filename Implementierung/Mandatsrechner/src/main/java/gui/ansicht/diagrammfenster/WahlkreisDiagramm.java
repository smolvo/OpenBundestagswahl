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

import main.java.model.Erststimme;
import main.java.model.Partei;
import main.java.model.Wahlkreis;

/**
 * Diese Klasse repr�sentiert das Diagramm der Wahlkreisansicht.
 * 
 */
public class WahlkreisDiagramm {

	/** rept�sentiert den Bereich auf dem das Diagramm angezeigt wird. */
	private final DiagrammFenster flaeche;

	/**
	 * Konstruktor erstellt ein Diagramm unter Verwendung der privaten Methode
	 * createChart(Wahlkreis) und f�gt es hinzu.
	 * 
	 * @param wk
	 *            Wahlkreis
	 * @param flaeche
	 *            Diagrammfl�che
	 * @throws IllegalArgumentException
	 *             wenn die Parameter null sind
	 */
	public WahlkreisDiagramm(Wahlkreis wk, final DiagrammFenster flaeche) {
		if (wk == null || flaeche == null) {
			throw new IllegalArgumentException("Einer der Parameter ist null.");
		}
		this.flaeche = flaeche;
		flaeche.setLayout(new BorderLayout());
		JFreeChart chart = createChart(wk);
		final ChartPanel chartPanel = new ChartPanel(chart);

		chartPanel.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				ChartPanel panel = (ChartPanel) e.getComponent();
				panel.setSize(resize());
				flaeche.add(chartPanel, BorderLayout.LINE_START);
			}

		});
		chartPanel.setPreferredSize(new Dimension(450, 250));
		flaeche.add(chartPanel, BorderLayout.LINE_START);
	}

	/**
	 * Diese Mehthode wird vom Konstruktor verwendet, um das Diagramm zu
	 * erstellen.
	 * 
	 * @param wk
	 *            der Wahlkreis
	 * @throws IllegalArgumentException
	 *             wenn das Wahlkreis-Objekt null ist.
	 * @return JFreeChart die neue Chart
	 */
	private JFreeChart createChart(Wahlkreis wk) {
		if (wk == null) {
			throw new IllegalArgumentException("Wahlkreis ist leer");
		}
		DefaultCategoryDataset result = new DefaultCategoryDataset();
		ArrayList<Partei> parteien = new ArrayList<Partei>();
		List<Erststimme> er = wk.getErststimmenProPartei();
		Collections.sort(er);
		int count = 0;
		int sonstige = 100;
		// solange sonstige �ber 5% der Gesamtstimmen haben soll ein weiterer
		// Balken hinzugef�gt werden
		while (sonstige > 5) {
			sonstige = 0;
			double proZweit = (Math
					.rint(((double) er.get(count).getAnzahl() / (double) wk
							.getAnzahlErststimmen()) * 1000) / 10);
			parteien.add(er.get(count).getKandidat().getPartei());
			result.setValue(proZweit, " ", er.get(count).getKandidat()
					.getPartei().getName());

			for (int i = count; i < er.size(); i++) {
				sonstige += (Math
						.rint(((double) er.get(i).getAnzahl() / (double) wk
								.getAnzahlErststimmen()) * 1000) / 10);
			}
			count++;
		}
		result.setValue(sonstige, " ", "Sonstige");

		JFreeChart chart = ChartFactory.createBarChart("Stimmanteile von \n"
				+ wk.getName(), null, "proz. Erststimmen", result,
				PlotOrientation.VERTICAL, false, false, false);
		CategoryPlot plot = chart.getCategoryPlot();

		// y-Achsenabschnitt festlegen
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setRange(new Range(0, 60));
		plot.setRangeAxis(rangeAxis);

		// f�rben der Parteienbalken
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
	 * Diese Methode gibt eine Dimension, abh�ngig von der Fl�che auf der sich
	 * das Diagramm befindet, aus.
	 * 
	 * @return Dimension
	 */
	public Dimension resize() {
		return new Dimension(this.flaeche.getWidth(),
				(int) (this.flaeche.getHeight()));
	}
}
