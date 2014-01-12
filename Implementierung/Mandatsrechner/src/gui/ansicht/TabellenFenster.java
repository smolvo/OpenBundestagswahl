package gui.ansicht;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.Bundesland;
import model.Deutschland;
import model.Erststimme;
import model.Partei;
import model.Kandidat;
import model.Wahlkreis;
import model.Zweitstimme;

/**
 * Diese Klasse repräsentiert das Tabellenfenster einer Ansicht.
 * In diesem werden bestimmmte Daten eines BTW-Objektes angezeigt.
 *
 */
public class TabellenFenster extends JScrollPane {

	private LinkedList<TabellenZelle> zellen;
	
	private LinkedList<Zweitstimme> zweitstimme = new LinkedList<Zweitstimme>();
	
	/**
	 * Befüllt die Zeilen und Spalten der Tabelle in der Bundesansicht 
	 * mit den relevanten Daten.
	 * @param land Deutschland-Objekt welches visualisiert werden soll
	 */
	public void tabellenFuellen(Deutschland land) {
		String[] spaltenNamen = new String[] {
				"Partei", "Zweitstimme", "%", "Sitze", "Direktmandate", "Überhangmandate", "Ausgleichsmandate"
				};
		String[][] daten = new String[land.getZweitstimmen().size()][7];
		
		int gesamt = 0;
		// Prozentuale Berechnung
		for (Zweitstimme zw : land.getZweitstimmen()) {
			gesamt += zw.getAnzahl();
		}
		int zaehler = 0;
		// pro Zeile (Partei)
		for(Zweitstimme zw : land.getZweitstimmen()) {
			// Zweitstimmen-Objekt der Liste hinzufügen, für changeListener
			this.zweitstimme.add(zw);
			Partei partei = zw.getPartei();
			daten[zaehler][0] = partei.getName();
			daten[zaehler][1] = String.valueOf(zw.getAnzahl());
			daten[zaehler][2] = String.valueOf(zw.getAnzahl() / gesamt);
			
			
			// Anzahl Direkt-, Überhangs-, und Ausgleichsmandate
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
		
		// Tabelle anzeigen
		JTable tabelle = new JTable(daten, spaltenNamen);
		this.setViewportView(tabelle);
		
	}
	
	/**
	 * Befüllt die Zeilen und Spalten der Tabelle in der Landesansicht 
	 * mit den relevanten Daten.
	 * @param bl Bundesland-Objekt welches visualisiert werden soll
	 */
	public void tabellenFuellen(Bundesland bl) {
		String[] spaltenNamen = new String[] {
				"Partei", "Zweitstimme", "%", "Direktmandate", "Überhangmandate"
				};
		String[][] daten = new String[bl.getZweitstimmen().size()][7];
		
		int zaehler = 0;
		// pro Zeile (Partei)
		for(Zweitstimme zw : bl.getZweitstimmen()) {
			this.zweitstimme.add(zw);
			Partei partei = zw.getPartei();
			daten[zaehler][0] = partei.getName();
			daten[zaehler][1] = String.valueOf(zw.getAnzahl());
			
			// Anzahl Direkt- und Überhangsmandate
			int direktMan = 0;
			int ueberMan = 0;
			for (Kandidat kan : partei.getMitglieder()) {
				if (kan.getMandat().equals("Direktmandat")) {
					direktMan++;
				} else if (kan.getMandat().equals("Überhangmandat")) {
					ueberMan++;
				}
			}
			daten[zaehler][3] = String.valueOf(direktMan);
			daten[zaehler][4] = String.valueOf(ueberMan);
			zaehler++;
			}

		// Tabelle anzeigen
		JTable tabelle = new JTable(daten, spaltenNamen);
		this.setViewportView(tabelle);
	}
	
	/**
	 * Befüllt die Zeilen und Spalten der Tabelle in der Wahlkreisansicht 
	 * mit den relevanten Daten.
	 * @param wk Wahlkreis-Objekt welcher visualisiert werden soll
	 */
	public void tabellenFuellen(Wahlkreis wk) {
		String[] spaltenNamen = new String[] {
				"Partei", "Erststimme", "%", "Zweitstimme", "%", "Direktmandate"
				};
		String[][] daten = new String[wk.getZweitstimmen().size()][7];
		
		int zaehler = 0;
		// pro Zeile (Partei)
		for(Erststimme er : wk.getErststimmen()) {
			Partei partei = er.getKandidat().getPartei();
			daten[zaehler][0] = partei.getName();
			daten[zaehler][1] = String.valueOf(er.getAnzahl());
			
			// Anzahl Zweitstimmen
			for (Zweitstimme zw : wk.getZweitstimmen()) {
				if (zw.getPartei().getName().equals(er.getKandidat().getPartei().getName())) {
					daten[zaehler][3] = String.valueOf(zw.getAnzahl());
				}
			}
			
			// Anzahl Direktmandate
			int direktMan = 0;
			if (er.getKandidat().getMandat().equals("Direktmandat")) {
				direktMan++;
			}
			daten[zaehler][5] = String.valueOf(direktMan);
			zaehler++;
		}
		
		
		
		// Tabelle anzeigen
		JTable tabelle = new JTable(daten, spaltenNamen);
		this.setViewportView(tabelle);
	}
}
