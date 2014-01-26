package main.java.gui.ansicht.diagrammfenster;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

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

	/** repräsentiert den Bereich auf dem das Diagramm angezeigt wird. */
	private final DiagrammFenster flaeche;

	/**
	 * Konstruktor erstellt ein Diagramm und fügt es hinzu.
	 * 
	 * @param land
	 *            Deutschland
	 * @param flaeche
	 *            Diagrammfläche
	 */
	public BundDiagramm(Deutschland land, DiagrammFenster flaeche) {
		this.flaeche = flaeche;
		JFreeChart chart = createChart(land);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setBackground(Color.WHITE);
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
	 * @param chartPanel ChartPanel
	 * @param flaeche die Fläche unter dem ChartPanel
	 */
	private void listenerSetzen(ChartPanel chartPanel, final DiagrammFenster flaeche) {
		chartPanel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				Bundestagswahl btw = flaeche.getAnsicht().getFenster().getBtw();
				flaeche.zeigeSitzverteilung(btw);
			}
		});
	}
	
	/**
	 * Diese Methode erstellt ein Kuchendiagramm zur Sitzverteilung im
	 * Bundestag.
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

		// färben
		for (int i = 0; i < daten.size(); i++) {
			/* TODO
			 * FARBEN ÜBERPRÜFEN!!!
			 */
			//plot.setSectionPaint(result.getKey(i), parteien.get(i).getFarbe());
		}
		plot.setLabelLinksVisible(false);
		plot.setLabelBackgroundPaint(Color.white);

		plot.setStartAngle(225);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(1.0f);
		return chart;
	}
	
	public Dimension resize() {
		return new Dimension(this.flaeche.getWidth(), this.flaeche.getHeight());
	}
}
