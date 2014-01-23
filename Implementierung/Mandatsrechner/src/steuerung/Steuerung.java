package steuerung;

import importexport.ImportExportManager;

import java.io.File;

import wahlgenerator.Stimmanteile;
import model.Bundestagswahl;
import model.Stimme;

/**
 * Diese Klasse repräsentiert die Hauptsteuerung des Programmes.
 * 
 * @author Anton
 * 
 */
public final class Steuerung {

	/** zeigt an ob ein Steuerungs-Objekt bereits existiert */
	private static Steuerung instance;

	/** repräsentiert die aktuelle Bundestagswahl mit der gearbeitet wird */
	private Bundestagswahl btw;

	/**
	 * Privater Konstruktor, wegen Benutzung des Singleton-Patterns.
	 */
	private Steuerung() {
	}

	/**
	 * Diese Methode importiert eine neue Bundestagswahl in das Programm mit
	 * einem Vektor, der gefüllt ist mit den dazu relevanten Daten.
	 * 
	 * @param csvDateien
	 *            relevante Daten
	 * @return neue Bundestagswahl
	 */
	public static Bundestagswahl importieren(File[] csvDateien) {
		ImportExportManager i = new ImportExportManager();
		Bundestagswahl w = null;
		try {
			w = i.importieren(csvDateien);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return w;
	}

	/**
	 * Diese Methode exportiert die aktuelle Bundestagswahl auf den angegebenen
	 * Pfad.
	 * 
	 * @param pfad
	 *            Pfad
	 */
	public static void exportieren(String pfad) {
		ImportExportManager i = new ImportExportManager();
		i.exportieren(pfad, btw);
	}

	/**
	 * Diese Methode startet eine neue Berechnung der Sitzverteilung der
	 * aktuellen Bundestagswahl.
	 * 
	 * @param btw
	 *            aktuelle Bundestagswahl
	 */
	public static void berechneSitzverteilung(Bundestagswahl btw) {
		// TODO
	}

	/**
	 * Diese Methode generiert eine zufällige Wahl auf Grund bestimmter
	 * Stimmenanteile.
	 * 
	 * @param anteile
	 *            die Stimmenanteile
	 */
	public static Bundestagswahl zufaelligeWahlgenerierung(Stimmanteile anteile) {
		// TODO
		return null;
	}

	public static void negStimmgewichtGenerierung(Stimmanteile anteile) {
		// TODO
	}

	/**
	 * Diese Methode aktualisiert den Datensatz, sobald eine bestimmte Anzahl an
	 * Erst- oder Zweitstimmen geändert wurde.
	 * 
	 * @param stimme
	 *            die betroffene Stimme
	 * @param anzahl
	 *            der neue Wert
	 * @return true or false
	 */
	public static boolean aktualisiereDaten(Stimme stimme, int anzahl) {
		return true;
		// TODO
	}

	/**
	 * Durch diese Methode wird die aktuelle Bundestagswahl mit einer
	 * ausgewählten Bundestagswahl verglichen.
	 * 
	 * @param vergleichsWahl
	 *            andere Wahl
	 */
	public static void vergleicheWahlen(Bundestagswahl vergleichsWahl) {
		// TODO
	}

	/**
	 * Mit dieser Methode wird das Programm eine Stimmenänderung zurück gesetzt.
	 * Es wird ausgegeben, ob dies erfolgreich war.
	 * 
	 * @return true false
	 */
	public static boolean zurueckSetzen() {
		// TODO
		return false;
	}

	/**
	 * Gibt die aktuelle Steuerung aus.
	 * 
	 * @return Steuerung
	 */
	public static Steuerung getInstance() {
		if (instance == null) {
			instance = new Steuerung();
		}
		return instance;
	}

	/**
	 * Gibt die Bundestagswahl aus.
	 * 
	 * @return Bundestagswahl
	 */
	public Bundestagswahl getBtw() {
		return btw;
	}

	/**
	 * Setzt die Bundestagswahl.
	 * 
	 * @param btw
	 *            Bundestagswahl
	 */
	public void setBtw(Bundestagswahl btw) {
		this.btw = btw;
	}
}
