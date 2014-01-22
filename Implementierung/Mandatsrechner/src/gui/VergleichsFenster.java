package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import gui.ansicht.diagrammfenster.BundDiagramm;
import gui.ansicht.diagrammfenster.DiagrammFenster;
import gui.ansicht.tabellenfenster.BundTableModel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import wahlvergleich.Wahlvergleich;
import wahlvergleich.WahlvergleichDaten;
import wahlvergleich.WahlvergleichTableModel;

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
		JScrollPane scrollPane = new JScrollPane(jTabelle);

		// Das Vergleichstabellenfenster
		this.gbc = new GridBagConstraints();
		gbc.weightx = 0.5;
		gbc.weighty = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.BOTH;
		add(scrollPane, gbc);

		// Diagramm der ersten Bundestagswahl
		DiagrammFenster diagramm1 = new DiagrammFenster();
		diagramm1.erstelleDiagramm(vergleich.getBtw1().getDeutschland());
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		add(diagramm1, gbc);

		// Diagramm der zweiten Bundestagswahl
		DiagrammFenster diagramm2 = new DiagrammFenster();
		diagramm2.erstelleDiagramm(vergleich.getBtw1().getDeutschland());
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		add(diagramm2, gbc);

	}
}
