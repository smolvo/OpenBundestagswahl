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
 * Diese Klasse reprï¿½sentiert das Tabellenfenster einer Ansicht. In diesem
 * werden bestimmmte Daten eines BTW-Objektes angezeigt.
 * 
 */
public class TabellenFenster extends JScrollPane {

	/** Automatisch generierte serialVersionUID */
	private static final long serialVersionUID = -8830377095217386485L;

	/** reprï¿½sentiert die Ansicht in der sich die Tabelle befindet */
	private final Ansicht ansicht;

	/**
	 * Der Kostruktor erstellt ein neues Tabellenfenster.
	 * 
	 * @param ansicht
	 *            die Ansicht
	 * @throws IllegalArgumentException
	 *             wenn das Ansicht-Objekt null ist.
	 */
	public TabellenFenster(Ansicht ansicht) {
		if (ansicht == null) {
			throw new IllegalArgumentException("Ansicht ist null.");
		}
		this.ansicht = ansicht;
	}

	/**
	 * Diese Methode identifiziert das Gebiets-Objekt.
	 * 
	 * @param gebiet
	 *            Gebiet
	 */
	public void tabellenFuellen(Gebiet gebiet) {
		// wenn kein Gebiet ï¿½bergeben wird wird eine leere Tabelle erstellt
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
	 * Befï¿½llt die Zeilen und Spalten der Tabelle in der Bundesansicht mit den
	 * relevanten Daten.
	 * 
	 * @param land
	 *            Deutschland-Objekt welches visualisiert werden soll
	 * @throws IllegalArgumentException
	 *             wenn das Deutschland-Objekt null ist.
	 */
	private void tabellenFuellen(Deutschland land) {
		if (land == null) {
			throw new IllegalArgumentException("Deutschland-Objekt ist null");
		}
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
	 * Diese private Methode wird von tabellenFuellen fï¿½r Deutschland verwendet
	 * und erstellt aus einer Sitzverteilung und einer Partei ein
	 * GUIPartei-Objekt, welches alle Daten, die angezeigt werden mï¿½ssen
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
		// Anzahl Direkt-, ï¿½berhangs-, und Ausgleichsmandate
		int sitze = partei.getAnzahlMandate();
		int direktMan = 0;
		int ueberMan = partei.getUeberhangMandate();
		int ausglMan = partei.getAusgleichsMandate();
		for (Kandidat kan : bundestag.getAbgeordnete()) {
			if (kan.getPartei().getName().equals(partei.getName())) {
				if (kan.getMandat().equals(Mandat.DIREKTMANDAT)) {
					direktMan++;
				} else if (kan.getMandat().equals(Mandat.AUSGLEICHSMANDAT)) {
					ausglMan++;
				}
			}
		}
		GUIPartei gp = new GUIPartei(sitze, direktMan, ueberMan, ausglMan);
		return gp;
	}

	/**
	 * Befï¿½llt die Zeilen und Spalten der Tabelle in der Landesansicht mit den
	 * relevanten Daten.
	 * 
	 * @param bl
	 *            Bundesland-Objekt welches visualisiert werden soll
	 * @throws IllegalArgumentException
	 *             wenn das Bundesland-Objekt null ist.
	 */
	private void tabellenFuellen(Bundesland bl) {
		if (bl == null) {
			throw new IllegalArgumentException(
					"Das Bundesland-Objekt ist null.");
		}
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
			double proZweit = (Math.rint(((double) zw.getAnzahl() / (double) bl
					.getAnzahlZweitstimmen()) * 1000) / 10);
			daten.addZeile(zw.getPartei().getName(), zw,
					Double.toString(proZweit), Integer.toString(direktMan),
					Integer.toString(zw.getPartei().getUeberhangMandate(bl)));
		}

		LandTableModel tabelle = new LandTableModel(daten);
		JTable jTabelle = new JTable(tabelle);
		this.setViewportView(jTabelle);
	}

	/**
	 * Befï¿½llt die Zeilen und Spalten der Tabelle in der Wahlkreisansicht mit
	 * den relevanten Daten.
	 * 
	 * @param wk
	 *            Wahlkreis-Objekt welcher visualisiert werden soll
	 * @throws IllegalArgumentException
	 *             wenn das Wahlkreis-Objekt leer ist.
	 */
	private void tabellenFuellen(Wahlkreis wk) {
		if (wk == null) {
			throw new IllegalArgumentException("Wahlkreis-Objekt ist null.");
		}
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
		WahlkreisTableModel tabelle = new WahlkreisTableModel(daten, this);
		JTable jTabelle = new JTable(tabelle);
		this.setViewportView(jTabelle);
	}

	/**
	 * Gibt die Ansicht aus.
	 * 
	 * @return Ansicht
	 */
	public Ansicht getAnsicht() {
		return this.ansicht;
	}
}
