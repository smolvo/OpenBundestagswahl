package gui;

import gui.ansicht.tabellenfenster.BundTableModel;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import wahlvergleich.WahlvergleichDaten;
import wahlvergleich.WahlvergleichTableModel;

/**
 * Diese Klasse repräsentiert das Wahlvergleichsfenster. In diesem werden
 * bestimmmte Daten zweier BTW-Objektes angezeigt.
 * 
 * @author Anton
 * 
 */
public class VergleichsFenster extends JScrollPane {

	/**
	 * Der Konstruktor erstellt ein Vergleichsfenster.
	 * 
	 * @param daten
	 */
	public VergleichsFenster(WahlvergleichDaten daten) {
		zeigeVergleich(daten);
	}

	/**
	 * Diese Methode füllt das Fenster mit der Tabelle.
	 * 
	 * @param daten
	 *            Daten
	 */
	private void zeigeVergleich(WahlvergleichDaten daten) {
		WahlvergleichTableModel tabelle = new WahlvergleichTableModel(daten);
		JTable jTabelle = new JTable(tabelle);
		this.setViewportView(jTabelle);
	}
}
