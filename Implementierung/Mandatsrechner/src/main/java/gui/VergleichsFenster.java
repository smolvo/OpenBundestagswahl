package main.java.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import main.java.gui.ansicht.diagrammfenster.BundDiagramm;
import main.java.gui.ansicht.diagrammfenster.DiagrammFenster;
import main.java.gui.ansicht.tabellenfenster.BundTableModel;
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
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.BOTH;
		JPanel flaeche = new JPanel();
		flaeche.add(skrollFenster);
		add(skrollFenster, gbc);

		// Diagramm der ersten Bundestagswahl
		DiagrammFenster diagramm1 = new DiagrammFenster();
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
		DiagrammFenster diagramm2 = new DiagrammFenster();
		diagramm2.erstelleDiagramm(vergleich.getBtw1().getDeutschland());
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		add(diagramm2, gbc);

	}
}
