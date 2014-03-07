package main.java.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Comparator;
import java.util.LinkedList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

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
public class VergleichsFenster extends JDialog {

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
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLayout(new GridBagLayout());
		this.gbc = new GridBagConstraints();
		this.setSize(1024, 768);
		setTitle("Vergleich");
		zeigeVergleich(vergleich);
		erstelleDiagramme(vergleich);
		pack();
		setAlwaysOnTop(true);
		setAlwaysOnTop(false);

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
		final DiagrammFenster diagramm1 = new DiagrammFenster(null,
				vergleich.getBtw1());
		diagramm1.erstelleDiagramm(vergleich.getBtw1());
		this.gbc.gridheight = 1;
		this.gbc.gridwidth = 1;
		this.gbc.gridx = 0;
		this.gbc.gridy = 2;
		add(diagramm1, this.gbc);

		// Diagramm der Differenzen
		final DiffDiagramm diagramm3 = new DiffDiagramm(holeDifferenzen(
				vergleich.getBtw1(), vergleich.getBtw2()));
		this.gbc.gridx = 1;
		this.gbc.gridy = 2;
		add(diagramm3, this.gbc);

		// Diagramm der zweiten Bundestagswahl
		final DiagrammFenster diagramm2 = new DiagrammFenster(null,
				vergleich.getBtw2());
		diagramm2.erstelleDiagramm(vergleich.getBtw2());
		this.gbc.gridheight = 1;
		this.gbc.gridwidth = 1;
		this.gbc.gridx = 2;
		this.gbc.gridy = 2;
		add(diagramm2, this.gbc);
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
		final LinkedList<Partei> parteien = new LinkedList<Partei>();
		for (final Partei partei : btw1.getParteien()) {
			if (partei.isImBundestag()) {
				anzahlBalken++;
				parteien.add(partei);
			}
		}
		final ParteiDifferenzen[] differenzen = new ParteiDifferenzen[anzahlBalken];
		for (int i = 0; i < parteien.size(); i++) {
			int sitzeErsteWahl = 0;
			int sitzeZweiteWahl = 0;
			for (final Kandidat kandidat : btw1.getSitzverteilung()
					.getAbgeordnete()) {
				if (parteien.get(i).getName()
						.equals(kandidat.getPartei().getName())) {
					sitzeErsteWahl++;
				}
			}
			for (final Kandidat kandidat : btw2.getSitzverteilung()
					.getAbgeordnete()) {
				if (parteien.get(i).getName()
						.equals(kandidat.getPartei().getName())) {
					sitzeZweiteWahl++;
				}
			}
			final int diff = sitzeErsteWahl - sitzeZweiteWahl;
			differenzen[i] = new ParteiDifferenzen(parteien.get(i), diff);
		}
		return differenzen;
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
		final WahlvergleichDaten daten = vergleich.wahlvergleich();
		final TableRowSorter<TableModel> sorterVergleich = new TableRowSorter<TableModel>();
		final WahlvergleichTableModel tabelle = new WahlvergleichTableModel(
				daten);
		final JTable jTabelle = new JTable(tabelle);
		jTabelle.setRowSorter(sorterVergleich);
		sorterVergleich.setModel(jTabelle.getModel());
		sorterVergleich.setComparator(1, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				final int i1 = Integer.parseInt(s1), i2 = Integer.parseInt(s2);
				return Integer.compare(i1, i2);
			}
		});
		sorterVergleich.setComparator(3, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				final int i1 = Integer.parseInt(s1), i2 = Integer.parseInt(s2);
				return Integer.compare(i1, i2);
			}
		});
		sorterVergleich.setComparator(4, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				final int i1 = Integer.parseInt(s1), i2 = Integer.parseInt(s2);
				return Integer.compare(i1, i2);
			}
		});
		sorterVergleich.setComparator(6, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				final int i1 = Integer.parseInt(s1), i2 = Integer.parseInt(s2);
				return Integer.compare(i1, i2);
			}
		});
		sorterVergleich.setComparator(7, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				final int i1 = Integer.parseInt(s1), i2 = Integer.parseInt(s2);
				return Integer.compare(i1, i2);
			}
		});
		sorterVergleich.setComparator(9, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				final int i1 = Integer.parseInt(s1), i2 = Integer.parseInt(s2);
				return Integer.compare(i1, i2);
			}
		});
		sorterVergleich.setComparator(2, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				final Double d1 = Double.parseDouble(s1), d2 = Double
						.parseDouble(s2);
				return Double.compare(d1, d2);
			}
		});
		sorterVergleich.setComparator(5, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				final Double d1 = Double.parseDouble(s1), d2 = Double
						.parseDouble(s2);
				return Double.compare(d1, d2);
			}
		});
		sorterVergleich.setComparator(8, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				final Double d1 = Double.parseDouble(s1), d2 = Double
						.parseDouble(s2);
				return Double.compare(d1, d2);
			}
		});
		sorterVergleich.setComparator(10, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				final Double d1 = Double.parseDouble(s1), d2 = Double
						.parseDouble(s2);
				return Double.compare(d1, d2);
			}
		});

		final JScrollPane skrollFenster = new JScrollPane(jTabelle);
		this.gbc.anchor = GridBagConstraints.CENTER;
		this.gbc.fill = GridBagConstraints.BOTH;

		// Überschriften der Wahlen
		final JLabel ersteWahlName = new JLabel(vergleich.getBtw1().getName());
		this.gbc.weightx = 0.5;
		this.gbc.weighty = 0.025;
		this.gbc.gridx = 1;
		this.gbc.gridy = 0;
		add(ersteWahlName, this.gbc);
		final JLabel zweiteWahlName = new JLabel(vergleich.getBtw2().getName());
		this.gbc.gridx = 2;
		add(zweiteWahlName, this.gbc);

		// Das Vergleichstabellenfenster
		this.gbc.weightx = 1;
		this.gbc.weighty = 1;
		this.gbc.gridx = 0;
		this.gbc.gridy = 1;
		this.gbc.gridheight = 1;
		this.gbc.gridwidth = 4;
		final JPanel flaeche = new JPanel();
		flaeche.add(skrollFenster);
		add(skrollFenster, this.gbc);
	}
}
