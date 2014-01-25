package main.java.gui.ansicht.tabellenfenster;

import java.awt.Color;
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
import main.java.model.Wahlkreis;
import main.java.model.Zweitstimme;

/**
 * Diese Klasse repr�sentiert das Tabellenfenster einer Ansicht. In diesem
 * werden bestimmmte Daten eines BTW-Objektes angezeigt.
 * 
 * @author Anton
 * 
 */
public class TabellenFenster extends JScrollPane {

	/** repr�sentiert die Ansicht in der sich die Tabelle befindet */
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
	 * Bef�llt die Zeilen und Spalten der Tabelle in der Bundesansicht mit den
	 * relevanten Daten.
	 * 
	 * @param land
	 *            Deutschland-Objekt welches visualisiert werden soll
	 */
	private void tabellenFuellen(Deutschland land) {
		BundDaten daten = new BundDaten();
		List<Zweitstimme> stimmen = land.getZweitstimmen();
		Collections.sort(stimmen);
		for (Zweitstimme zw : stimmen) {
			GUIPartei gp = parteiErstellen(zw);
			double proZweit = (Math
					.rint(((double) zw.getAnzahl() / (double) land
							.getZweitstimmeGesamt()) * 1000) / 10);
			daten.addZeile(zw.getPartei().getName(), zw,
					Double.toString(proZweit), Integer.toString(gp.getSitze()),
					Integer.toString(gp.getDirektmandate()),
					Integer.toString(gp.getUeberhangsmandate()),
					Integer.toString(gp.getAusgleichsmandate()));
		}

		BundTableModel tabelle = new BundTableModel(daten, this);
		JTable jTabelle = new JTable(tabelle);
		this.setViewportView(jTabelle);
	}

	/**
	 * Bef�llt die Zeilen und Spalten der Tabelle in der Landesansicht mit den
	 * relevanten Daten.
	 * 
	 * @param bl
	 *            Bundesland-Objekt welches visualisiert werden soll
	 */
	private void tabellenFuellen(Bundesland bl) {
		LandDaten daten = new LandDaten();
		List<Zweitstimme> stimmen = bl.getZweitstimmen();
		Collections.sort(stimmen);
		for (Zweitstimme zw : stimmen) {
			GUIPartei gp = parteiErstellen(zw);
			double proZweit = (Math.rint(((double) zw.getAnzahl() / (double) bl
					.getZweitstimmeGesamt()) * 1000) / 10);
			daten.addZeile(zw.getPartei().getName(), zw,
					Double.toString(proZweit),
					Integer.toString(gp.getDirektmandate()),
					Integer.toString(gp.getUeberhangsmandate()));
		}

		LandTableModel tabelle = new LandTableModel(daten, this);
		JTable jTabelle = new JTable(tabelle);
		this.setViewportView(jTabelle);
	}

	/**
	 * Bef�llt die Zeilen und Spalten der Tabelle in der Wahlkreisansicht mit
	 * den relevanten Daten.
	 * 
	 * @param wk
	 *            Wahlkreis-Objekt welcher visualisiert werden soll
	 */
	private void tabellenFuellen(Wahlkreis wk) {
		WahlkreisDaten daten = new WahlkreisDaten();
		for (Erststimme er: wk.getErststimmen()) {
			Zweitstimme korresZweit = null;
			for (Zweitstimme zw : wk.getZweitstimmen()) {
				if ((er.getKandidat().getPartei() != null) && (zw.getPartei().getName().equals(er.getKandidat().getPartei().getName()))) {
					korresZweit = zw;
				}
			}
			if (korresZweit != null) {
				boolean direktMan = false;
				if (er.getKandidat().getMandat().equals(Mandat.DIREKTMANDAT)) {
					direktMan = true;
				}
				if ((korresZweit.getAnzahl() != 0) || (er.getAnzahl() != 0)) {
					double prozentualeErst = (Math
							.rint(((double) er.getAnzahl() / (double) wk
									.getErststimmeGesamt()) * 1000) / 10);
					double prozentualeZweit = (Math
							.rint(((double) korresZweit.getAnzahl() / (double) wk
									.getZweitstimmeGesamt()) * 1000) / 10);
					daten.addZeile(korresZweit.getPartei().getName(), er.getKandidat()
							.getName(), korresZweit, er, Double.toString(prozentualeZweit),
							Double.toString(prozentualeErst), direktMan);
				}
			}
		}
		WahlkreisTableModel tabelle = new WahlkreisTableModel(daten, this);
		JTable jTabelle = new JTable(tabelle);
		this.setViewportView(jTabelle);
	}

	/**
	 * Diese Methode erstellt aus einer Zweitstimme ein GUIPartei-Objekt,
	 * welches alle Daten, die angezeigt werden m�ssen beinhaltet.
	 * 
	 * @param zw
	 *            Zweitstimmen Objekt
	 * @return GUIPartei-Objekt
	 */
	private GUIPartei parteiErstellen(Zweitstimme zw) {
		Partei partei = zw.getPartei();
		// Anzahl Direkt-, �berhangs-, und Ausgleichsmandate
		int sitze = 0;
		int direktMan = 0;
		int ueberMan = 0;
		int ausglMan = 0;
		for (Kandidat kan : partei.getMitglieder()) {
			if (kan.getMandat().equals(Mandat.MANDAT)) {
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
		GUIPartei gp = new GUIPartei(sitze, direktMan, ueberMan, ausglMan);
		return gp;
	}

	/**
	 * Gibt die Ansicht aus.
	 * 
	 * @return Ansicht
	 */
	public Ansicht getAnsicht() {
		return ansicht;
	}
}
