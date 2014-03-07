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

import javax.swing.JPanel;

import main.java.model.Erststimme;
import main.java.model.Partei;
import main.java.model.Wahlkreis;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Diese Klasse reprï¿½sentiert das Diagramm der Wahlkreisansicht.
 * 
 */
public class WahlkreisDiagramm extends JPanel {

	private static final long serialVersionUID = 8859046341751767496L;

	/**
	 * Konstruktor erstellt ein Diagramm unter Verwendung der privaten Methode
	 * createChart(Wahlkreis) und fï¿½gt es hinzu.
	 * 
	 * @param wk
	 *            Wahlkreis
	 * @throws IllegalArgumentException
	 *             wenn die Parameter null sind
	 */
	public WahlkreisDiagramm(Wahlkreis wk) {
		if (wk == null) {
			throw new IllegalArgumentException("Einer der Parameter ist null.");
		}
		setLayout(new BorderLayout());
		final JFreeChart chart = createChart(wk);
		final ChartPanel chartPanel = new ChartPanel(chart);

		chartPanel.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				final ChartPanel panel = (ChartPanel) e.getComponent();
				panel.setSize(resize());
				add(chartPanel, BorderLayout.LINE_START);
			}

		});
		chartPanel.setPreferredSize(new Dimension(450, 250));
		this.add(chartPanel, BorderLayout.LINE_START);
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
		final DefaultCategoryDataset result = new DefaultCategoryDataset();
		final ArrayList<Partei> parteien = new ArrayList<Partei>();
		final List<Erststimme> er = wk.getErststimmenProPartei();
		Collections.sort(er);
		int count = 0;
		int sonstige = 100;
		// solange sonstige ï¿½ber 5% der Gesamtstimmen haben soll ein weiterer
		// Balken hinzugefï¿½gt werden
		while (sonstige > 5) {
			sonstige = 0;
			final double proZweit = Math.rint((double) er.get(count)
					.getAnzahl() / (double) wk.getAnzahlErststimmen() * 1000) / 10;
			parteien.add(er.get(count).getKandidat().getPartei());
			result.setValue(proZweit, " ", er.get(count).getKandidat()
					.getPartei().getName());

			for (int i = count; i < er.size(); i++) {
				sonstige += Math.rint((double) er.get(i).getAnzahl()
						/ (double) wk.getAnzahlErststimmen() * 1000) / 10;
			}
			count++;
		}
		result.setValue(sonstige, " ", "Sonstige");

		final JFreeChart chart = ChartFactory.createBarChart(
				"Stimmanteile von \n" + wk.getName(), null,
				"proz. Erststimmen", result, PlotOrientation.VERTICAL, false,
				false, false);
		final CategoryPlot plot = chart.getCategoryPlot();

		// y-Achsenabschnitt festlegen
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setRange(new Range(0, 60));
		plot.setRangeAxis(rangeAxis);

		// fï¿½rben der Parteienbalken
		final Paint[] farben = new Paint[parteien.size() + 1];
		for (int i = 0; i < parteien.size(); i++) {
			farben[i] = parteien.get(i).getFarbe();
		}
		farben[farben.length - 1] = Color.GRAY;
		final BarRenderer barRenderer = new BalkenRenderer(farben);
		plot.setRenderer(barRenderer);
		plot.setForegroundAlpha(1.0f);

		return chart;
	}

	/**
	 * Diese Methode gibt eine Dimension, abhï¿½ngig von der Flï¿½che auf der
	 * sich das Diagramm befindet, aus.
	 * 
	 * @return Dimension
	 */
	public Dimension resize() {
		return new Dimension(getWidth(), getHeight());
	}
}
