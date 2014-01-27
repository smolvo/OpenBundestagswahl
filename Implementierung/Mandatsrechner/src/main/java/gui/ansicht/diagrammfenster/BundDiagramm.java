package main.java.gui.ansicht.diagrammfenster;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;

import main.java.model.Bundestagswahl;
import main.java.model.Deutschland;
import main.java.model.Kandidat;
import main.java.model.Mandat;
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
public class BundDiagramm {

	/** repr�sentiert den Bereich auf dem das Diagramm angezeigt wird. */
	private final DiagrammFenster flaeche;

	/**
	 * Konstruktor erstellt ein Diagramm unter Verwendung der privaten Methode
	 * createChart(Deutschland) und f�gt es hinzu.
	 * 
	 * @param land
	 *            Deutschland
	 * @param flaeche
	 *            Diagrammfl�che
	 */
	public BundDiagramm(Deutschland land, DiagrammFenster flaeche) {
		this.flaeche = flaeche;
		JFreeChart chart = createChart(land);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				ChartPanel panel = (ChartPanel) e.getComponent();
				panel.setSize(resize());
			}

		});
		listenerSetzen(chartPanel, flaeche);
		chartPanel.setPreferredSize(new Dimension(450, 250));
		flaeche.add(chartPanel, BorderLayout.CENTER);
	}

	/**
	 * Diese Methode setzt einen Listener zu einem ChartPanel.
	 * 
	 * @param chartPanel
	 *            ChartPanel
	 * @param flaeche
	 *            die Fl�che unter dem ChartPanel
	 */
	private void listenerSetzen(ChartPanel chartPanel,
			final DiagrammFenster flaeche) {
		chartPanel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Bundestagswahl btw = flaeche.getAnsicht().getFenster().getBtw();
					flaeche.zeigeSitzverteilung(btw);
				} catch (NullPointerException npe) {
		        	JOptionPane.showMessageDialog(e.getComponent(),
							"Berichstanzeige momentan nicht m�glich.", "Meldung",
							JOptionPane.INFORMATION_MESSAGE, null);
				}
			}
		});
	}

	/**
	 * Diese private Methode wird vom Konstruktor verwendet und erstellt ein
	 * Kuchendiagramm zur Sitzverteilung im Bundestag.
	 * 
	 * @param land
	 *            Deutschland
	 * @return Kuchendiagramm
	 */
	private JFreeChart createChart(Deutschland land) {
		ArrayList<Integer> daten = new ArrayList<Integer>();
		ArrayList<Partei> parteien = new ArrayList<Partei>();
		List<Zweitstimme> stimmen = land.getZweitstimmenProPartei();
		Collections.sort(stimmen);
		DefaultPieDataset result = new DefaultPieDataset();
		for (Zweitstimme zw : stimmen) {
			int sitze = 0;
			for (Kandidat kan : zw.getPartei().getMitglieder()) {
				if (!(kan.getMandat().equals(Mandat.KEINMANDAT))) {
					sitze++;
				}
			}
			if (sitze > 0) {
				daten.add(sitze);
				parteien.add(zw.getPartei());
				result.setValue(zw.getPartei().getName(), sitze);
			}
		}

		JFreeChart chart = ChartFactory.createPieChart3D("Sitzverteilung",
				result, false, true, false);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();

		// f�rben
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
	 * Diese Methode gibt eine Dimension, abh�ngig von der Fl�che auf der sich
	 * das Diagramm befindet, aus.
	 * 
	 * @return Dimension
	 */
	public Dimension resize() {
		return new Dimension(this.flaeche.getWidth(), (int) (0.6 * this.flaeche.getWidth()));
	}
}
