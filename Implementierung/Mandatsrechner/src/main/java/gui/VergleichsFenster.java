package main.java.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import main.java.gui.ansicht.diagrammfenster.DiagrammFenster;
import main.java.gui.ansicht.diagrammfenster.DiffDiagramm;
import main.java.model.Bundestagswahl;
import main.java.model.Kandidat;
import main.java.model.Partei;
import main.java.wahlvergleich.ParteiDifferenzen;
import main.java.wahlvergleich.Wahlvergleich;
import main.java.wahlvergleich.WahlvergleichDaten;
import main.java.wahlvergleich.WahlvergleichTableModel;

/**
 * Diese Klasse repräsentiert das Wahlvergleichsfenster. In diesem werden
 * bestimmmte Daten zweier BTW-Objektes angezeigt.
 */
public class VergleichsFenster extends JFrame {

	private static final long serialVersionUID = -5493451520927891697L;

	/** repräsentiert das Layout */
	private final GridBagConstraints gbc;

	/**
	 * Der Konstruktor erstellt ein Vergleichsfenster.
	 * 
	 * @param vergleich
	 *            Vergleichsdaten
	 * @throws IllegalArgumentException
	 *             wenn der Vergleich null ist.
	 */
	public VergleichsFenster(Wahlvergleich vergleich) {
		if (vergleich == null) {
			throw new IllegalArgumentException("Wahlvergleich ist null.");
		}
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		this.gbc = new GridBagConstraints();
		this.setSize(1024, 768);
		this.setTitle("Vergleich");
		zeigeVergleich(vergleich);
		erstelleDiagramme(vergleich);
		this.pack();
	}

	/**
	 * Diese Methode füllt das Fenster mit der Tabelle, den zwei
	 * Sitzverteilungsdiagrammen und dem Differenzendiagramm.
	 * 
	 * @param vergleich
	 *            der Vergleich
	 * @throws IllegalArgumentException
	 *             wenn der Vergleich null ist.
	 */
	private void zeigeVergleich(Wahlvergleich vergleich) {
		if (vergleich == null) {
			throw new IllegalArgumentException("Wahlvergleich ist null");
		}
		WahlvergleichDaten daten = vergleich.wahlvergleich();
		WahlvergleichTableModel tabelle = new WahlvergleichTableModel(daten);
		JTable jTabelle = new JTable(tabelle);
		JScrollPane skrollFenster = new JScrollPane(jTabelle);
		this.gbc.anchor = GridBagConstraints.CENTER;
		this.gbc.fill = GridBagConstraints.BOTH;

		// Überschriften der Wahlen
		JLabel ersteWahlName = new JLabel(vergleich.getBtw1().getName());
		gbc.weightx = 0.5;
		gbc.weighty = 0.025;
		gbc.gridx = 1;
		gbc.gridy = 0;
		add(ersteWahlName, gbc);
		JLabel zweiteWahlName = new JLabel(vergleich.getBtw2().getName());
		gbc.gridx = 2;
		add(zweiteWahlName, gbc);

		// Das Vergleichstabellenfenster
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.gridwidth = 4;
		JPanel flaeche = new JPanel();
		flaeche.add(skrollFenster);
		add(skrollFenster, gbc);
	}

	/**
	 * Diese Methode wird vom Konstruktor verwendet, um die Diagramme zu
	 * erstellen.
	 */
	private void erstelleDiagramme(Wahlvergleich vergleich) {
		if (vergleich == null) {
			throw new IllegalArgumentException("Wahlvergleich ist null.");
		}
		// Diagramm der ersten Bundestagswahl
		DiagrammFenster diagramm1 = new DiagrammFenster(null);
		diagramm1.erstelleDiagramm(vergleich.getBtw1().getDeutschland());
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(diagramm1, gbc);

		// Diagramm der Differenzen
		DiffDiagramm diagramm3 = new DiffDiagramm(holeDifferenzen(
				vergleich.getBtw1(), vergleich.getBtw2()));
		gbc.gridx = 1;
		gbc.gridy = 2;
		add(diagramm3, gbc);

		// Diagramm der zweiten Bundestagswahl
		DiagrammFenster diagramm2 = new DiagrammFenster(null);
		diagramm2.erstelleDiagramm(vergleich.getBtw2().getDeutschland());
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbc.gridx = 2;
		gbc.gridy = 2;
		add(diagramm2, gbc);
	}

	/**
	 * Diese private Methode wird von der Methode zeigeVergleich verwendet, um
	 * die Sitzplatzdifferenzen einer Partei zwischen zwei Wahlen zu holen.
	 * 
	 * @param btw1
	 *            erste Bundestagswahl
	 * @param btw2
	 *            zweite Bundestagswahl
	 * @throws IllegalArgumentException
	 *             wenn die Parameter null sind.
	 * @return die Differenzen
	 */
	private ParteiDifferenzen[] holeDifferenzen(Bundestagswahl btw1,
			Bundestagswahl btw2) {
		if (btw1 == null || btw2 == null) {
			throw new IllegalArgumentException("Eingabeparameter sind null.");
		}
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
				if (parteien.get(i).getName()
						.equals(kandidat.getPartei().getName())) {
					sitzeErsteWahl++;
				}
			}
			for (Kandidat kandidat : btw2.getSitzverteilung().getAbgeordnete()) {
				if (parteien.get(i).getName()
						.equals(kandidat.getPartei().getName())) {
					sitzeZweiteWahl++;
				}
			}
			int diff = sitzeErsteWahl - sitzeZweiteWahl;
			differenzen[i] = new ParteiDifferenzen(parteien.get(i), diff);
		}
		return differenzen;
	}
}
