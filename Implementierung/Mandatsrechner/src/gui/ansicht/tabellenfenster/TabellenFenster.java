package gui.ansicht.tabellenfenster;

import gui.GUIPartei;

import java.text.DecimalFormat;
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
	
	/**
	 * Befüllt die Zeilen und Spalten der Tabelle in der Bundesansicht 
	 * mit den relevanten Daten.
	 * @param land Deutschland-Objekt welches visualisiert werden soll
	 */
	public void tabellenFuellen(Deutschland land) {
		BundDaten daten = new BundDaten();
		// Zweitstimmen Gesamtanzahl
		int gesamt = 0;
		for (Zweitstimme zw : land.getZweitstimmen()) {
			gesamt += zw.getAnzahl();
		}
		for (Zweitstimme zw : land.getZweitstimmen()) {
			GUIPartei gp = parteiErstellen(zw);
			double prozentualeZweit = (Math.rint(((double) zw.getAnzahl() / (double) gesamt) * 1000) / 10);
			gp.setProzentualeZweit(prozentualeZweit);
			daten.addParteien(gp.getName(), zw, Double.toString(prozentualeZweit), 
					Integer.toString(gp.getSitze()), Integer.toString(gp.getDirektmandate()), 
					Integer.toString(gp.getUeberhangsmandate()), Integer.toString(gp.getAusgleichsmandate()));
		}
		
		GUITableModel tabelle = new GUITableModel(daten);
		JTable jTabelle = new JTable(tabelle);
		this.setViewportView(jTabelle);
	}
	
	/**
	 * Befüllt die Zeilen und Spalten der Tabelle in der Landesansicht 
	 * mit den relevanten Daten.
	 * @param bl Bundesland-Objekt welches visualisiert werden soll
	 */
	public void tabellenFuellen(Bundesland bl) {
		
	}
	
	/**
	 * Befüllt die Zeilen und Spalten der Tabelle in der Wahlkreisansicht 
	 * mit den relevanten Daten.
	 * @param wk Wahlkreis-Objekt welcher visualisiert werden soll
	 */
	public void tabellenFuellen(Wahlkreis wk) {
		
	}
	
	/**
	 * Diese Methode erstellt aus einer Zweitstimme ein 
	 * GUIPartei-Objekt, welches alle Daten, die angezeigt
	 * werden müssen beinhaltet.
	 * @param zw Zweitstimmen Objekt
	 * @return GUIPartei-Objekt
	 */
	private GUIPartei parteiErstellen(Zweitstimme zw) {
		Partei partei = zw.getPartei();
		GUIPartei gp = new GUIPartei(partei.getName(), zw.getAnzahl(), -1, -1.0, -1.0, -1, -1, -1, -1, false);
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
		gp.setSitze(sitze);
		gp.setDirektmandate(direktMan);
		gp.setUeberhangsmandate(ueberMan);
		gp.setAusgleichsmandate(ausglMan);
		return gp;
	}
}
