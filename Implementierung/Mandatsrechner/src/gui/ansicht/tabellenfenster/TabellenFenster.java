package gui.ansicht.tabellenfenster;

import gui.GUIKandidat;
import gui.GUIPartei;

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
 * @author Anton
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
			daten.addZeile(gp.getName(), zw, Double.toString(prozentualeZweit), 
					Integer.toString(gp.getSitze()), Integer.toString(gp.getDirektmandate()), 
					Integer.toString(gp.getUeberhangsmandate()), Integer.toString(gp.getAusgleichsmandate()));
		}
		
		BundTableModel tabelle = new BundTableModel(daten);
		JTable jTabelle = new JTable(tabelle);
		this.setViewportView(jTabelle);
	}
	
	/**
	 * Befüllt die Zeilen und Spalten der Tabelle in der Landesansicht 
	 * mit den relevanten Daten.
	 * @param bl Bundesland-Objekt welches visualisiert werden soll
	 */
	public void tabellenFuellen(Bundesland bl) {
		LandDaten daten = new LandDaten();
		// Zweitstimmen Gesamtanzahl
		int gesamt = 0;
		for (Zweitstimme zw : bl.getZweitstimmen()) {
			gesamt += zw.getAnzahl();
		}
		for (Zweitstimme zw : bl.getZweitstimmen()) {
			GUIPartei gp = parteiErstellen(zw);
			double prozentualeZweit = (Math.rint(((double) zw.getAnzahl() / (double) gesamt) * 1000) / 10);
			gp.setProzentualeZweit(prozentualeZweit);
			daten.addZeile(gp.getName(), zw, Double.toString(prozentualeZweit), 
					Integer.toString(gp.getDirektmandate()), Integer.toString(gp.getUeberhangsmandate()));
		}
		
		LandTableModel tabelle = new LandTableModel(daten);
		JTable jTabelle = new JTable(tabelle);
		this.setViewportView(jTabelle);
	}
	
	/**
	 * Befüllt die Zeilen und Spalten der Tabelle in der Wahlkreisansicht 
	 * mit den relevanten Daten.
	 * @param wk Wahlkreis-Objekt welcher visualisiert werden soll
	 */
	public void tabellenFuellen(Wahlkreis wk) {
		WahlkreisDaten daten = new WahlkreisDaten();
		int gesamtErst = 0;
		for (Erststimme er : wk.getErststimmen()) {
			gesamtErst += er.getAnzahl();
		}
		int gesamtZweit = 0;
		for (Zweitstimme zw : wk.getZweitstimmen()) {
			gesamtZweit += zw.getAnzahl();
		}
		
		for (int i = 0; i < wk.getErststimmen().size(); i++) {
			Erststimme er = wk.getErststimmen().get(i);
			Zweitstimme zw = wk.getZweitstimmen().get(i);
			if ((zw.getAnzahl() != 0) || (er.getAnzahl() != 0)) {
				GUIKandidat gk = kandidatErstellen(er);
				double prozentualeErst = (Math.rint(((double) er.getAnzahl() / (double) gesamtErst) * 1000) / 10);
				gk.setProzErst(prozentualeErst);
				double prozentualeZweit = (Math.rint(((double) zw.getAnzahl() / (double) gesamtZweit) * 1000) / 10);
				gk.setProzZweit(prozentualeZweit);
				daten.addZeile(gk.getPartei(), gk.getName(), zw, er, Double.toString(prozentualeZweit), 
						Double.toString(prozentualeErst), gk.isDirektman());
			}
		}

		WahlkreisTableModel tabelle = new WahlkreisTableModel(daten);
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
	
	/**
	 * Diese Methode erstellt aus einer Erststimme ein 
	 * GUIKandidat-Objekt, welches alle Daten, die angezeigt
	 * werden müssen beinhaltet.
	 * @param erst Erststimme
	 * @return den GUIKandidaten
	 */
	private GUIKandidat kandidatErstellen(Erststimme erst) {
		Kandidat kandidat = erst.getKandidat();
		String partei = "-";
		if (kandidat.getPartei() != null) {
			partei = kandidat.getPartei().getName();
		}
		GUIKandidat kan = new GUIKandidat(partei, kandidat.getName(), erst, -1.0, null, -1.0, false);
		if (kandidat.getMandat().equals("Direktmadat")) {
			kan.setDirektman(true);
		}		
		return kan;
	}
}
