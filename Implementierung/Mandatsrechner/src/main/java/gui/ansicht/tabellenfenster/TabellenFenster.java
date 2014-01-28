package main.java.gui.ansicht.tabellenfenster;

import java.util.Collections;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import main.java.gui.GUIPartei;
import main.java.gui.ansicht.Ansicht;
import main.java.model.Bundesland;
import main.java.model.Deutschland;
import main.java.model.Erststimme;
import main.java.model.Gebiet;
import main.java.model.Kandidat;
import main.java.model.Mandat;
import main.java.model.Partei;
import main.java.model.Sitzverteilung;
import main.java.model.Wahlkreis;
import main.java.model.Zweitstimme;

/**
 * Diese Klasse repräsentiert das Tabellenfenster einer Ansicht. In diesem
 * werden bestimmmte Daten eines BTW-Objektes angezeigt.
 * @author Anton
 * 
 */
public class TabellenFenster extends JScrollPane {
	
	/** repräsentiert die Ansicht in der sich die Tabelle befindet */
	private final Ansicht ansicht;

	/**
	 * Der Kostruktor erstellt ein neues Tabellenfenster.
	 * 
	 * @param ansicht
	 *            die Ansicht
	 */
	public TabellenFenster(Ansicht ansicht) {
		this.ansicht = ansicht;
	}

	/**
	 * Diese Methode identifiziert das Gebiets-Objekt.
	 * 
	 * @param gebiet
	 *            Gebiet
	 */
	public void tabellenFuellen(Gebiet gebiet) {
		// wenn kein Gebiet übergeben wird wird eine leere Tabelle erstellt
		if (gebiet == null) {
			BundDaten daten = new BundDaten();
			BundTableModel tabelle = new BundTableModel(daten);
			JTable jTabelle = new JTable(tabelle);
			this.setViewportView(jTabelle);
		}
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
	 * Befüllt die Zeilen und Spalten der Tabelle in der Bundesansicht mit den
	 * relevanten Daten.
	 * 
	 * @param land
	 *            Deutschland-Objekt welches visualisiert werden soll
	 */
	private void tabellenFuellen(Deutschland land) {
		Sitzverteilung bundestag = this.ansicht.getFenster().getBtw()
				.getSitzverteilung();
		BundDaten daten = new BundDaten();
		List<Zweitstimme> stimmen = land.getZweitstimmenProPartei();
		Collections.sort(stimmen);
		for (Zweitstimme zw : stimmen) {
			GUIPartei gp = parteiDatenDeutschland(zw.getPartei(), bundestag);
			double proZweit = (Math
					.rint(((double) zw.getAnzahl() / (double) land
							.getAnzahlZweitstimmen()) * 1000) / 10);
			daten.addZeile(zw.getPartei().getName(), zw,
					Double.toString(proZweit), Integer.toString(gp.getSitze()),
					Integer.toString(gp.getDirektmandate()),
					Integer.toString(gp.getUeberhangsmandate()),
					Integer.toString(gp.getAusgleichsmandate()));
		}

		BundTableModel tabelle = new BundTableModel(daten);
		JTable jTabelle = new JTable(tabelle);
		this.setViewportView(jTabelle);
	}

	/**
	 * Diese private Methode wird von tabellenFuellen für Deutschland verwendet
	 * und erstellt aus einer Sitzverteilung und einer Partei ein
	 * GUIPartei-Objekt, welches alle Daten, die angezeigt werden müssen
	 * beinhaltet.
	 * 
	 * @param partei
	 *            Partei
	 * @param bundestag
	 *            die Sitzplatzverteilung
	 * @return GUIPartei-Objekt
	 */
	private GUIPartei parteiDatenDeutschland(Partei partei,
			Sitzverteilung bundestag) {
		if (partei == null || bundestag.getAbgeordnete() == null) {
			return new GUIPartei(0, 0, 0, 0);
		}
		// Anzahl Direkt-, Überhangs-, und Ausgleichsmandate
		int sitze = 0;
		int direktMan = 0;
		int ueberMan = partei.getUeberhangMandate();
		int ausglMan = 0;
		for (Kandidat kan : bundestag.getAbgeordnete()) {
			if (kan.getPartei().getName().equals(partei.getName())) {
				if (kan.getMandat().equals(Mandat.LISTENMANDAT)) {
					sitze++;
				} else if (kan.getMandat().equals(Mandat.DIREKTMANDAT)) {
					direktMan++;
					sitze++;
				} else if (kan.getMandat().equals(Mandat.AUSGLEICHSMANDAT)) {
					ausglMan++;
					sitze++;
				}
			}
		}
		GUIPartei gp = new GUIPartei(sitze, direktMan, ueberMan, ausglMan);
		return gp;
	}

	/**
	 * Befüllt die Zeilen und Spalten der Tabelle in der Landesansicht mit den
	 * relevanten Daten.
	 * 
	 * @param bl
	 *            Bundesland-Objekt welches visualisiert werden soll
	 */
	private void tabellenFuellen(Bundesland bl) {
		Sitzverteilung bundestag = this.ansicht.getFenster().getBtw()
				.getSitzverteilung();
		LandDaten daten = new LandDaten();
		List<Zweitstimme> stimmen = bl.getZweitstimmenProPartei();
		Collections.sort(stimmen);
		for (Zweitstimme zw : stimmen) {
			int direktMan = 0;
			for (Wahlkreis wk : bl.getWahlkreise()) {
				if (wk.getWahlkreisSieger().getPartei().getName()
						.equals(zw.getPartei().getName())) {
					direktMan++;
				}
			}
			GUIPartei gp = parteiDatenBundesland(zw.getPartei(), bl.getName(), bundestag);
			double proZweit = (Math.rint(((double) zw.getAnzahl() / (double) bl
					.getAnzahlZweitstimmen()) * 1000) / 10);
			daten.addZeile(zw.getPartei().getName(), zw,
					Double.toString(proZweit), Integer.toString(direktMan),
					Integer.toString(gp.getUeberhangsmandate()));
		}

		LandTableModel tabelle = new LandTableModel(daten, this);
		JTable jTabelle = new JTable(tabelle);
		this.setViewportView(jTabelle);
	}

	/**
	 * Diese private Methode wird von tabellenFuellen für Bundesländer verwendet
	 * und erstellt aus einer Sitzverteilung, einem Bundesland und einer Partei ein
	 * GUIPartei-Objekt, welches alle Daten, die angezeigt werden müssen
	 * beinhaltet.
	 * @param partei die Partei
	 * @param bundesland das Bundesland
	 * @param bundestag die Sitzplatzverteilung
	 * @return Daten
	 */
	private GUIPartei parteiDatenBundesland(Partei partei, String bundesland,
			Sitzverteilung bundestag) {
		if (partei == null || bundestag.getAbgeordnete() == null) {
			return new GUIPartei(0, 0, 0, 0);
		}
		int sitze = 0;
		int direktMan = 0;
		int ueberMan = 0;
		int ausglMan = 0;
		int listenZaehler = 0;
		for (Kandidat kan : bundestag.getAbgeordnete()) {
			if ((kan.getPartei().getName().equals(partei.getName()))
					&& (bundestag.getBericht().getBundeslaender()
							.get(listenZaehler).equals(bundesland))) {
				if (kan.getMandat().equals(Mandat.LISTENMANDAT)) {
					sitze++;
				} else if (kan.getMandat().equals(Mandat.DIREKTMANDAT)) {
					direktMan++;
					sitze++;
				} else if (kan.getMandat().equals(Mandat.UEBERHANGMADAT)) {
					ueberMan++;
					sitze++;
				} else if (kan.getMandat().equals(Mandat.AUSGLEICHSMANDAT)) {
					ausglMan++;
					sitze++;
				}
			}
			listenZaehler++;
		}
		GUIPartei gp = new GUIPartei(sitze, direktMan, ueberMan, ausglMan);
		return gp;
	}

	/**
	 * Befüllt die Zeilen und Spalten der Tabelle in der Wahlkreisansicht mit
	 * den relevanten Daten.
	 * 
	 * @param wk
	 *            Wahlkreis-Objekt welcher visualisiert werden soll
	 */
	private void tabellenFuellen(Wahlkreis wk) {
		WahlkreisDaten daten = new WahlkreisDaten();
		for (Erststimme er : wk.getErststimmenProPartei()) {
			Zweitstimme korresZweit = null;
			for (Zweitstimme zw : wk.getZweitstimmenProPartei()) {
				if ((er.getKandidat().getPartei() != null)
						&& (zw.getPartei().getName().equals(er.getKandidat()
								.getPartei().getName()))) {
					korresZweit = zw;
				}
			}
			if (korresZweit != null) {
				boolean direktManBoolean = false;
				if (er.getKandidat().getMandat().equals(Mandat.DIREKTMANDAT)) {
					direktManBoolean = true;
				}
				if ((korresZweit.getAnzahl() != 0) || (er.getAnzahl() != 0)) {
					double prozentualeErst = (Math
							.rint(((double) er.getAnzahl() / (double) wk
									.getAnzahlErststimmen()) * 1000) / 10);
					double prozentualeZweit = (Math
							.rint(((double) korresZweit.getAnzahl() / (double) wk
									.getAnzahlZweitstimmen()) * 1000) / 10);
					String direktMan = "nein";
					if (direktManBoolean) {
						direktMan = "ja";
					}
					daten.addZeile(korresZweit.getPartei().getName(), er
							.getKandidat().getName(), korresZweit, er, Double
							.toString(prozentualeZweit), Double
							.toString(prozentualeErst), direktMan);
				}
			}
		}
		WahlkreisTableModel tabelle = new WahlkreisTableModel(daten, this);
		JTable jTabelle = new JTable(tabelle);
		this.setViewportView(jTabelle);
	}
	
	/**
	 * Gibt die Ansicht aus.
	 * @return Ansicht
	 */
	public Ansicht getAnsicht() {
		return this.ansicht;
	}
}
