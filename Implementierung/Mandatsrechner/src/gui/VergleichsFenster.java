package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import gui.ansicht.diagrammfenster.BundDiagramm;
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

		this.gbc = new GridBagConstraints();
		gbc.weightx = 0.5;
		gbc.weighty = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.BOTH;
		add(scrollPane, gbc);

		BundDiagramm diagrammErsteWahl = new BundDiagramm(vergleich.getBtw1(),
				new JPanel());
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		add(diagrammErsteWahl, gbc);

		BundDiagramm diagrammZweiteWahl = new BundDiagramm(vergleich.getBtw2(),
				new JPanel());
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		add(diagrammZweiteWahl, gbc);

	}
}
