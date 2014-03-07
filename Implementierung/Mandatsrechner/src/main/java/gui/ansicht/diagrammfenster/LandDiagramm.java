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

import main.java.model.Bundesland;
import main.java.model.Partei;
import main.java.model.Zweitstimme;

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
 * Diese Klasse stellt das Diagramm der Landesansicht dar.
 * 
 */
public class LandDiagramm extends JPanel {

	private static final long serialVersionUID = 7314443190665182978L;

	/**
	 * Konstruktor erstellt ein Diagramm unter Verwendung der privaten Methode
	 * createChart(Bundesland) und fï¿½gt es hinzu.
	 * 
	 * @param bundLand
	 *            Bundesland
	 * @throws IllegalArgumentException
	 *             Eingabeparameter sind null.
	 */
	public LandDiagramm(Bundesland bundLand) {
		if (bundLand == null) {
			throw new IllegalArgumentException("Bundesland ist null.");
		}
		setLayout(new BorderLayout());
		final JFreeChart chart = createChart(bundLand);
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
	 * Diese private Methode wird vom Konstruktor verwendet und erstellt das
	 * Diagramm.
	 * 
	 * @param bundLand
	 *            Bundesland fuer die das ein Diagramm erstellt werden
	 * @throws IllegalArgumentException
	 *             wenn das Bundesland-Objekt null ist.
	 * @return Diagramm das erstellte Diagramm
	 */
	private JFreeChart createChart(Bundesland bundLand) {
		if (bundLand == null) {
			throw new IllegalArgumentException("Bundesland ist null.");
		}
		final DefaultCategoryDataset result = new DefaultCategoryDataset();
		final ArrayList<Partei> parteien = new ArrayList<Partei>();
		final List<Zweitstimme> zw = bundLand.getZweitstimmenProPartei();
		Collections.sort(zw);
		int count = 0;
		int sonstige = 100;
		// solange sonstige ï¿½ber 5% der Gesamtstimmen haben soll ein weiterer
		// Balken hinzugefï¿½gt werden
		while (sonstige > 5) {
			sonstige = 0;
			final double proZweit = Math.rint((double) zw.get(count)
					.getAnzahl()
					/ (double) bundLand.getAnzahlZweitstimmen()
					* 1000) / 10;
			parteien.add(zw.get(count).getPartei());
			result.setValue(proZweit, " ", zw.get(count).getPartei().getName());

			for (int i = count; i < zw.size(); i++) {
				sonstige += Math.rint((double) zw.get(i).getAnzahl()
						/ (double) bundLand.getAnzahlZweitstimmen() * 1000) / 10;
			}
			count++;
		}
		result.setValue(sonstige, " ", "Sonstige");

		final JFreeChart chart = ChartFactory.createBarChart(
				"Stimmanteile von " + bundLand.getName(), null,
				"proz. Zweitstimmen", result, PlotOrientation.VERTICAL, false,
				false, false);
		final CategoryPlot plot = chart.getCategoryPlot();

		// y-Achsenabschnitt festlegen
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setRange(new Range(0, 75));
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
