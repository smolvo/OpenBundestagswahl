package gui;

import java.util.LinkedList;

import javax.swing.JTable;

import model.Bundestagswahl;
import model.Partei;
import model.Kandidat;
import model.Gebiet;

/**
 * Diese Klasse repräsentiert das Tabellenfenster einer Ansicht.
 * In diesem werden bestimmmte Daten eines BTW-Objektes angezeigt.
 *
 */
public class TabellenFenster {

	private LinkedList<TabellenZelle> zellen;
	
	/**
	 * Befüllt die Zeilen und Spalten der Tabelle in der Bundesansicht 
	 * mit den relevanten Daten.
	 * @param btw Bundestagswahl-Objekt welches visualisiert werden soll
	 */
	public void BATabellenFuellen(Bundestagswahl btw) {
		String[] spaltenNamen = new String[] {
				"Partei", "Zweitstimme", "%", "Sitze", "Direktmandate", "Überhangmandate", "Ausgleichsmandate"
				};
		String[][] daten = new String[btw.getParteien().size()][7];
		int zaehler = 0;
		// pro Zeile (Partei)
		for(Partei partei : btw.getParteien()) {
			daten[zaehler][0] = partei.getName();
			daten[zaehler][1] = String.valueOf(partei.getZweitstimme().getAnzahl());
			
			int sitze = 0;
			int direktMan = 0;
			int ueberMan = 0;
			int ausglMan = 0;
			for (Kandidat kan : partei.getMitglieder()) {
				if (kan.getMandat().equals("Mandat")) {
					sitze++;
				} else if (kan.getMandat().equals("Direktmandat")) {
					direktMan++;
					sitze++;
				} else if (kan.getMandat().equals("Überhangmandat")) {
					ueberMan++;
					sitze++;
				} else if (kan.getMandat().equals("Ausgleichsmandat")) {
					ausglMan++;
					sitze++;
				}
			}
			daten[zaehler][3] = String.valueOf(sitze);
			daten[zaehler][4] = String.valueOf(direktMan);
			daten[zaehler][5] = String.valueOf(ueberMan);
			daten[zaehler][6] = String.valueOf(ausglMan);
			zaehler++;
		}
	}
	
	/**
	 * Befüllt die Zeilen und Spalten der Tabelle in der Landesansicht 
	 * mit den relevanten Daten.
	 * @param gebiet Gebiets-Objekt welches visualisiert werden soll
	 */
	public void LATabellenFuellen(Gebiet gebiet) {
		String[] spaltenNamen = new String[] {
				"Partei", "Zweitstimme", "%", "Direktmandate", "Überhangmandate"
				};
		String[][] daten = new String[5][];
	}
	
	/**
	 * Befüllt die Zeilen und Spalten der Tabelle in der Wahlkreisansicht 
	 * mit den relevanten Daten.
	 * @param gebiet Gebiets-Objekt welches visualisiert werden soll
	 */
	public void WATabellenFuellen(Gebiet gebiet) {
		String[] spaltenNamen = new String[] {
				"Partei", "Erststimme", "%", "Zweitstimme", "%", "Direktmandate"
				};
		String[][] daten = new String[6][];
	}
}
