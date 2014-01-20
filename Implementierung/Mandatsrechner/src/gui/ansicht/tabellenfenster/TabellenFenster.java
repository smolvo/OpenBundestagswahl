package gui.ansicht.tabellenfenster;

import java.awt.Color;

import gui.GUIPartei;


import gui.ansicht.Ansicht;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.Bundesland;
import model.Deutschland;
import model.Erststimme;
import model.Gebiet;
import model.Mandat;
import model.Partei;
import model.Kandidat;
import model.Wahlkreis;
import model.Zweitstimme;


/**
 * Diese Klasse repräsentiert das Tabellenfenster einer Ansicht.
 * In diesem werden bestimmmte Daten eines BTW-Objektes angezeigt.
 * @author Anton
 * 
 */
public class TabellenFenster extends JScrollPane {
	
	/** repräsentiert die Ansicht in der sich die Tabelle befindet */
	private final Ansicht ansicht;
	
	
	/**
	 * Der Kostruktor erstellt ein neues Tabellenfenster.
	 * @param ansicht die Ansicht
	 */
	public TabellenFenster(Ansicht ansicht) {
		this.ansicht = ansicht;
	}
	
	/**
	 * Diese Methode identifiziert das Gebiets-Objekt.
	 * @param gebiet Gebiet
	 */
	public void tabellenFuellen(Gebiet gebiet) {
		this.setBackground(Color.WHITE);
		if (gebiet instanceof Deutschland) {
			Deutschland land = (Deutschland) gebiet;
			tabellenFuellen(land);
		} else if (gebiet instanceof Bundesland) {
			Bundesland bundLand = (Bundesland) gebiet;
			tabellenFuellen(bundLand);
		} else {
			Wahlkreis wk = (Wahlkreis) gebiet;
			tabellenFuellen(wk);
		}
	}
	
	/**
	 * Befüllt die Zeilen und Spalten der Tabelle in der Bundesansicht 
	 * mit den relevanten Daten.
	 * @param land Deutschland-Objekt welches visualisiert werden soll
	 */
	private void tabellenFuellen(Deutschland land) {
		BundDaten daten = new BundDaten();
		for (Zweitstimme zw : land.getZweitstimmen()) {
			GUIPartei gp = parteiErstellen(zw);
			double proZweit = (Math.rint(((double) zw.getAnzahl() / (double) land.getZweitstimmeGesamt()) * 1000) / 10);
			daten.addZeile(zw.getPartei().getName(), zw, Double.toString(proZweit), 
					Integer.toString(gp.getSitze()), Integer.toString(gp.getDirektmandate()), 
					Integer.toString(gp.getUeberhangsmandate()), Integer.toString(gp.getAusgleichsmandate()));
		}
		
		BundTableModel tabelle = new BundTableModel(daten, this);
		JTable jTabelle = new JTable(tabelle);
		this.setViewportView(jTabelle);
	}
	
	/**
	 * Befüllt die Zeilen und Spalten der Tabelle in der Landesansicht 
	 * mit den relevanten Daten.
	 * @param bl Bundesland-Objekt welches visualisiert werden soll
	 */
	private void tabellenFuellen(Bundesland bl) {
		LandDaten daten = new LandDaten();
		for (Zweitstimme zw : bl.getZweitstimmen()) {
			GUIPartei gp = parteiErstellen(zw);
			double proZweit = (Math.rint(((double) zw.getAnzahl() / (double) bl.getZweitstimmeGesamt()) * 1000) / 10);
			daten.addZeile(zw.getPartei().getName(), zw, Double.toString(proZweit), 
					Integer.toString(gp.getDirektmandate()), Integer.toString(gp.getUeberhangsmandate()));
		}
		
		LandTableModel tabelle = new LandTableModel(daten, this);
		JTable jTabelle = new JTable(tabelle);
		this.setViewportView(jTabelle);
	}
	
	/**
	 * Befüllt die Zeilen und Spalten der Tabelle in der Wahlkreisansicht 
	 * mit den relevanten Daten.
	 * @param wk Wahlkreis-Objekt welcher visualisiert werden soll
	 */
	private void tabellenFuellen(Wahlkreis wk) {
		WahlkreisDaten daten = new WahlkreisDaten();
		for (int i = 0; i < wk.getErststimmen().size(); i++) {
			Erststimme er = wk.getErststimmen().get(i);
			Zweitstimme zw = wk.getZweitstimmen().get(i);
			boolean direktMan = false;
			if (er.getKandidat().getMandat().equals(Mandat.DIREKMANDAT)) {
				direktMan = true;
			}
			if ((zw.getAnzahl() != 0) || (er.getAnzahl() != 0)) {
				double prozentualeErst = (Math.rint(((double) er.getAnzahl() / (double) wk.getErststimmeGesamt()) * 1000) / 10);
				double prozentualeZweit = (Math.rint(((double) zw.getAnzahl() / (double) wk.getZweitstimmeGesamt()) * 1000) / 10);
				daten.addZeile(zw.getPartei().getName(), er.getKandidat().getName(), zw, er, Double.toString(prozentualeZweit), 
						Double.toString(prozentualeErst), direktMan);
			}
		}

		WahlkreisTableModel tabelle = new WahlkreisTableModel(daten, this);
		JTable jTabelle = new JTable(tabelle);
		this.setViewportView(jTabelle);
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
		GUIPartei gp = new GUIPartei(sitze, direktMan, ueberMan, ausglMan);
		return gp;
	}
	
	/**
	 * Gibt die Ansicht aus.
	 * @return Ansicht
	 */
	public Ansicht getAnsicht() {
		return ansicht;
	}
}
