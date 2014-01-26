package main.java.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import main.java.gui.ansicht.diagrammfenster.BundDiagramm;
import main.java.gui.ansicht.diagrammfenster.DiagrammFenster;
import main.java.gui.ansicht.tabellenfenster.BundTableModel;
import main.java.model.Bundestagswahl;
import main.java.model.Kandidat;
import main.java.model.Mandat;
import main.java.model.Partei;
import main.java.model.Zweitstimme;
import main.java.wahlvergleich.DiffDiagramm;
import main.java.wahlvergleich.ParteiDifferenzen;
import main.java.wahlvergleich.Wahlvergleich;
import main.java.wahlvergleich.WahlvergleichDaten;
import main.java.wahlvergleich.WahlvergleichTableModel;

/**
 * Diese Klasse repräsentiert das Wahlvergleichsfenster. In diesem werden
 * bestimmmte Daten zweier BTW-Objektes angezeigt.
 * 
 * @author Anton
 * 
 */
public class VergleichsFenster extends JFrame {

	/** repräsentiert das Layout */
	private GridBagConstraints gbc;

	/**
	 * Der Konstruktor erstellt ein Vergleichsfenster.
	 * 
	 * @param vergleich
	 *            Vergleichsdaten
	 */
	public VergleichsFenster(Wahlvergleich vergleich) {
		this.setLayout(new GridBagLayout());
		this.setSize(1024, 768);
		this.setTitle("Vergleich");
		zeigeVergleich(vergleich);
	}

	/**
	 * Diese Methode füllt das Fenster mit der Tabelle.
	 * 
	 * @param vergleich
	 *            der Vergleich
	 */
	private void zeigeVergleich(Wahlvergleich vergleich) {
		WahlvergleichDaten daten = vergleich.wahlvergleich();
		WahlvergleichTableModel tabelle = new WahlvergleichTableModel(daten);
		JTable jTabelle = new JTable(tabelle);
		JScrollPane skrollFenster = new JScrollPane(jTabelle);
		
		// Das Vergleichstabellenfenster
		this.gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 4;
		gbc.fill = GridBagConstraints.BOTH;
		JPanel flaeche = new JPanel();
		flaeche.add(skrollFenster);
		add(skrollFenster, gbc);

		// Diagramm der ersten Bundestagswahl
		DiagrammFenster diagramm1 = new DiagrammFenster(null);
		diagramm1.erstelleDiagramm(vergleich.getBtw1().getDeutschland());
		gbc.weightx = 0.5;
		gbc.weighty = 0.5;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		add(diagramm1, gbc);

		// Diagramm der zweiten Bundestagswahl
		
		DiffDiagramm diagramm3 = new DiffDiagramm(holeDifferenzen(vergleich.getBtw1(), vergleich.getBtw2()));
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		add(diagramm3, gbc);
		
		// Diagramm der zweiten Bundestagswahl
		DiagrammFenster diagramm2 = new DiagrammFenster(null);
		diagramm2.erstelleDiagramm(vergleich.getBtw1().getDeutschland());
		gbc.gridx = 3;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		add(diagramm2, gbc);

	}
	
	private ParteiDifferenzen[] holeDifferenzen(Bundestagswahl btw1, Bundestagswahl btw2) {
		int anzahlBalken = 0;
		LinkedList<Partei> parteien = new LinkedList<Partei>();
		for (Partei partei : btw1.getParteien()) {
			if (partei.isImBundestag()) {
				anzahlBalken++;
				parteien.add(partei);
			}
		}
		ParteiDifferenzen[] differenzen = new ParteiDifferenzen[anzahlBalken];
		for (int i = 0; i < parteien.size(); i++) {
			int sitzeErsteWahl = 0;
			int sitzeZweiteWahl = 0;
			for (Kandidat kandidat : btw1.getSitzverteilung().getAbgeordnete()) {
				if (parteien.get(i).getName().equals(kandidat.getPartei().getName())) {
					sitzeErsteWahl++;
				}
			}
			for (Kandidat kandidat : btw2.getSitzverteilung().getAbgeordnete()) {
				if (parteien.get(i).getName().equals(kandidat.getPartei().getName())) {
					sitzeZweiteWahl++;
				}
			}
			int diff = sitzeErsteWahl - sitzeZweiteWahl;
			differenzen[i] = new ParteiDifferenzen(parteien.get(i), diff);
		}
		return differenzen;
	}
}
