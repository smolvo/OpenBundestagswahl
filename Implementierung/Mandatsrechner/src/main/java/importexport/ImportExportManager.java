package main.java.importexport;

import java.io.File;

import main.java.model.Bundestagswahl;

/**
 * Der ImportExportManager ist zustaendig fuer das Erstellen von
 * "Bundestagswahl"-Objekten.
 * 
 * @author Enes Oerdek
 * 
 */
public class ImportExportManager {
	/**
	 * Ein Array, dass alle vorhandenen Crawler-Algorithmen beinhaltet.
	 */
	private Crawler crawler[];
	private Export exporter[];

	/**
	 * Ein default-Wert fuer unbekannte Kandidaten.
	 */
	// public static Kandidat unbekannterKandidat = new Kandidat("-", "-", 0,
	// Mandat.KEINMANDAT, null);

	/**
	 * Hier koennen weitere Algorithmen ergaenzt werden.
	 */
	public ImportExportManager() {
		crawler = new Crawler[1];
		crawler[0] = new Crawler2013();
		exporter = new Export[1];
		exporter[0] = new Export2013();
	}

	/**
	 * Importiert eine Datei.
	 * 
	 * @param csvDateien
	 *            Datei die importiert werden soll.
	 * @return das Ergebnis-Objekt.
	 */
	public Bundestagswahl importieren(File[] csvDateien) {

		Bundestagswahl imported = null;
		if (this.pruefeDateityp(csvDateien)) {
			imported = this.leseCSVDatei(csvDateien);
		}
		return imported;
	}

	/**
	 * Exportiert eine Bundestagswahl und Speichert die exportierte
	 * Bundestagswahl als eine CSV-Datei in ein bestimmtes Verzeichnis.
	 * 
	 * @param pfad
	 *            Das Verzeichnis, in das die exportierte Datei gespeichert
	 *            werden soll.
	 * @param bw
	 *            die zu exportierende Bundestagswahl.
	 * @return true wenn Erfolgreich. Ansosnten false.
	 */
	public boolean exportieren(String pfad, Bundestagswahl bw) {
		return exporter[0].exportieren(pfad, bw);
	}

	/**
	 * Prï¿½ft, ob eine Datei die gewï¿½nschte Dateiendung (.csv) hat.
	 * 
	 * @param csvDateien
	 *            Dateien, die zu ï¿½berprï¿½fen sind.
	 * @return true, wenn alle Dateien die Endung .csv haben.
	 */
	private boolean pruefeDateityp(File[] csvDateien) {
		boolean isCSV = false;
		for (int i = 0; i < csvDateien.length; i++) {
			String fileName = csvDateien[i].getName();
			String fileExtension = "";
			if (fileName.length() > 4) {
				fileExtension = fileName.substring(fileName.length() - 4,
						fileName.length());
			}

			// System.out.println(fileExtension);
			if (fileExtension.equals(".csv") && csvDateien[i].isFile()) {
				isCSV = true;
			}
			// else: Es handelt sich um keine CSV-Datei.
		}
		return isCSV;
	}

	/**
	 * Ruft den Crawler auf und importiert die gewï¿½nschten Dateien. Falls das
	 * importierte null ist, ist der C
	 * 
	 * @param csvDateien
	 *            Dateien die zu importieren sind.
	 * @return gibt die importierte Bundestgswahl zurï¿½ck.
	 */
	private Bundestagswahl leseCSVDatei(File[] csvDateien) {
		Bundestagswahl imported = null;
		for (int i = 0; i < this.crawler.length; i++) {
			crawler[i].getCrawlerInformation();
			imported = crawler[i].erstelleBundestagswahl(csvDateien);
			if (imported != null) {
				break;
			}
		}

		// if imported==null > Keine gï¿½ltige Datei.

		return imported;

	}
}
