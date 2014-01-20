package gui;

import steuerung.Steuerung;
import model.Bundestagswahl;
import model.Gebiet;
import model.Stimme;

/**
 * Diese Klasse repr�sentiert die GUI Steuerung. Diese ist die Verbindung
 * zwischen GUI und der Hauptsteuerung.
 * 
 * @author Anton
 * 
 */
public class GUISteuerung {

	/** repr�sentiert die aktuell angezeigte Bundestagswahl der GUI */
	private final Bundestagswahl btw;

	/** repr�sentiert das Wahlfenster in dem die BTW angezeigt wird */
	private final WahlFenster wahlfenster;

	/** repr�sentiert die Steuerung des Programms */
	private final Steuerung steuerung;

	/**
	 * Der Konstruktor initialisiert eine neue GUI Steuerung.
	 * 
	 * @param btw
	 *            aktuelle Bundestagswahl
	 * @param wahlfenster
	 *            dazugeh�riges Wahlfenster
	 */
	public GUISteuerung(Bundestagswahl btw, WahlFenster wahlfenster,
			Steuerung steuerung) {
		this.btw = btw;
		this.wahlfenster = wahlfenster;
		this.steuerung = steuerung;
	}

	/**
	 * Diese Methode aktualisiert das Wahlfenster.
	 * 
	 * @param gebiet
	 *            Gebiet, welches angezeigt werden soll
	 */
	public void aktualisiereWahlfenster(Gebiet gebiet) {
		this.wahlfenster.wechsleAnsicht(gebiet);
	}

	/**
	 * Diese Methode �bergibt an die Steuerung die Wert�nderung im
	 * Tabellenfenster.
	 * 
	 * @param stimme
	 *            die betroffene Stimmenanzahl
	 * @param anzahl
	 *            der neue Wert
	 */
	public void wertAenderung(Stimme stimme, int anzahl) {
		steuerung.aktualisiereDaten(stimme, anzahl);
	}

	/**
	 * Gibt die aktuelle Bundestagswahl aus.
	 * 
	 * @return Bundestagswahl
	 */
	public Bundestagswahl getBundestagswahl() {
		return btw;
	}

	/**
	 * Gibt das Wahlfenster aus.
	 * 
	 * @return Wahlfenster
	 */
	public WahlFenster getWahlfenster() {
		return wahlfenster;
	}

	/**
	 * Gibt die Steuerung aus.
	 * 
	 * @return Steuerung
	 */
	public Steuerung getSteuerung() {
		return this.steuerung;
	}
}
