package main.java.gui.ansicht.diagrammfenster;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

import main.java.model.Bundestagswahl;
import main.java.model.Partei;
import main.java.model.Zweitstimme;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;

/**
 * Diese Klasse zeichnet das Sitzverteilungsdiagramm der Bundesansicht.
 * 
 * @author Anton
 * 
 */
public class BundDiagramm extends JPanel {

	private static final long serialVersionUID = 5890986355952177696L;

	/**
	 * Konstruktor erstellt ein Diagramm unter Verwendung der privaten Methode
	 * createChart(Deutschland) und fï¿½gt es hinzu.
	 * 
	 * @param land
	 *            Deutschland
	 * @throws IllegalArgumentException
	 *             wenn die Parameter null sind
	 */
	public BundDiagramm(Bundestagswahl btw) {
		if (btw == null) {
			throw new IllegalArgumentException("Deutschland ist null.");
		}
		setLayout(new BorderLayout());
		final JFreeChart chart = createChart(btw);
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
	 * Diese private Methode wird vom Konstruktor verwendet und erstellt ein
	 * Kuchendiagramm zur Sitzverteilung im Bundestag.
	 * 
	 * @param land
	 *            Deutschland
	 * @throws IllegalArgumentException
	 *             wenn das Deutschland-Objekt ist
	 * @return Kuchendiagramm
	 */
	private JFreeChart createChart(Bundestagswahl btw) {
		if (btw == null) {
			throw new IllegalArgumentException(
					"Bundestagswahl Objekt ist null.");
		}
		final ArrayList<Integer> daten = new ArrayList<Integer>();
		final ArrayList<Partei> parteien = new ArrayList<Partei>();
		final List<Zweitstimme> stimmen = btw.getDeutschland()
				.getZweitstimmenProPartei();
		Collections.sort(stimmen);
		final DefaultPieDataset result = new DefaultPieDataset();
		for (final Zweitstimme zw : stimmen) {
			// int sitze = zw.getPartei().getAnzahlMandate();
			final int sitze = btw.getSitzverteilung().getAnzahlSitze(
					zw.getPartei());
			if (sitze > 0) {
				daten.add(sitze);
				parteien.add(zw.getPartei());
				result.setValue(zw.getPartei().getName(), sitze);
			}
		}

		final JFreeChart chart = ChartFactory.createPieChart3D(
				"Sitzverteilung im Deutschen Bundestag", result, true, true,
				true);
		final PiePlot3D plot = (PiePlot3D) chart.getPlot();

		// fï¿½rben
		for (int i = 0; i < daten.size(); i++) {
			plot.setSectionPaint(result.getKey(i), parteien.get(i).getFarbe());
		}
		plot.setLabelLinksVisible(false);
		plot.setLabelBackgroundPaint(Color.white);

		plot.setStartAngle(225);
		plot.setDirection(Rotation.CLOCKWISE);
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
