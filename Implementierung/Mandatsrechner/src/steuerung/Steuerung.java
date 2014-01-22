package steuerung;

import wahlgenerator.Stimmanteile;
import model.Bundestagswahl;
import model.Stimme;

/**
 * Diese Klasse repräsentiert die Hauptsteuerung des Programmes.
 * @author Batman
 *
 */
public final class Steuerung {

	/** zeigt an ob ein Steuerungs-Objekt bereits existiert */
	private static Steuerung instance;
	
	/** repräsentiert die aktuelle Bundestagswahl mit der gearbeitet wird */
	private Bundestagswahl btw;
	
	
	private Steuerung() {
		// TODO
	}
	
	public static void importieren() {
		// TODO
	}
	
	public static void exportieren() {
		// TODO
	}
	
	public static void berechneSitzverteilung(Bundestagswahl btw) {
		// TODO
	}
	
	public static void zufaelligeWahlgenerierung(Stimmanteile anteile) {
		// TODO
	}
	
	public static void negStimmgewichtGenerierung(Stimmanteile anteile) {
		// TODO
	}
	
	public static void aktualisiereDaten(Stimme stimme, int anzahl) {
		// TODO
	}
	
	public static void vergleicheWahlen(Bundestagswahl vergleichsWahl) {
		// TODO
	}
	
	public static boolean zurueckSetzen() {
		// TODO
		return false;
	}
	
	/**
	 * Gibt die aktuelle Steuerung aus.
	 * @return Steuerung
	 */
	public static Steuerung getInstance() {
		if (instance == null) {
			instance = new Steuerung();
		}
		return instance;
	}
}
