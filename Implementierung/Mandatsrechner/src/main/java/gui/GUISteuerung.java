package main.java.gui;

import main.java.model.Bundestagswahl;
import main.java.model.Gebiet;
import main.java.model.Stimme;
import main.java.steuerung.Steuerung;
import main.java.wahlvergleich.Wahlvergleich;

/**
 * Diese Klasse repr�sentiert die GUI Steuerung. Diese ist die Verbindung
 * zwischen GUI und der Hauptsteuerung.
 * 
 */
public class GUISteuerung {

	/** repr�sentiert die aktuell angezeigte Bundestagswahl der GUI */
	private final Bundestagswahl btw;

	/** repr�sentiert das Wahlfenster in dem die BTW angezeigt wird */
	private final WahlFenster wahlfenster;

	/**
	 * Der Konstruktor initialisiert eine neue GUI Steuerung.
	 * 
	 * @param btw
	 *            aktuelle Bundestagswahl
	 * @param wahlfenster
	 *            dazugeh�riges Wahlfenster
	 * @throws IllegalArgumentException
	 *             wenn die Eingabeparamter null sind
	 */
	public GUISteuerung(Bundestagswahl btw, WahlFenster wahlfenster) {
		if (btw == null || wahlfenster == null) {
			throw new IllegalArgumentException("Eingabe-Parameter sind null.");
		}
		this.btw = btw;
		this.wahlfenster = wahlfenster;
	}

	/**
	 * Diese Methode aktualisiert das Wahlfenster.
	 * 
	 * @param gebiet
	 *            Gebiet, welches angezeigt werden soll.
	 * @throws IllegalArgumentException
	 *             wenn das Gebiet-Objekt null ist.
	 */
	public void aktualisiereWahlfenster(Gebiet gebiet) {
		if (gebiet == null) {
			throw new IllegalArgumentException("Gebiet ist null.");
		}
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
	 * @return ob der Wert ge�ndert werden konnte
	 */
	public boolean wertAenderung(Stimme stimme, int anzahl) {
		return Steuerung.getInstance().aktualisiereDaten(stimme, anzahl);
	}

	/**
	 * Diese Methode erstellt ein neues Fenster in dem zwei Bundestagswahlen
	 * verglichen werden.
	 * 
	 * @param btw1
	 *            erste Bundestagswahl
	 * @param btw2
	 *            zweite Bundestagswahl
	 */
	public void vergleichen(Bundestagswahl btw1, Bundestagswahl btw2) {
		if (btw1 == null || btw2 == null) {
			throw new IllegalArgumentException("Bundestagswahlen sind null.");
		}
		Wahlvergleich vergleich = new Wahlvergleich(btw1, btw2);
		new VergleichsFenster(vergleich);
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
}
