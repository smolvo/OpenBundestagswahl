package gui;

import steuerung.Steuerung;
import wahlvergleich.Wahlvergleich;
import model.Bundestagswahl;
import model.Gebiet;
import model.Stimme;

/**
 * Diese Klasse repräsentiert die GUI Steuerung. Diese ist die Verbindung
 * zwischen GUI und der Hauptsteuerung.
 * 
 * @author Anton
 * 
 */
public class GUISteuerung {

	/** repräsentiert die aktuell angezeigte Bundestagswahl der GUI */
	private final Bundestagswahl btw;

	/** repräsentiert das Wahlfenster in dem die BTW angezeigt wird */
	private final WahlFenster wahlfenster;

	/**
	 * Der Konstruktor initialisiert eine neue GUI Steuerung.
	 * 
	 * @param btw
	 *            aktuelle Bundestagswahl
	 * @param wahlfenster
	 *            dazugehöriges Wahlfenster
	 */
	public GUISteuerung(Bundestagswahl btw, WahlFenster wahlfenster) {
		this.btw = btw;
		this.wahlfenster = wahlfenster;
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
	 * Diese Methode übergibt an die Steuerung die Wertänderung im
	 * Tabellenfenster.
	 * 
	 * @param stimme
	 *            die betroffene Stimmenanzahl
	 * @param anzahl
	 *            der neue Wert
	 */
	public void wertAenderung(Stimme stimme, int anzahl) {
		Steuerung.aktualisiereDaten(stimme, anzahl);
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
		Wahlvergleich vergleich = new Wahlvergleich(btw1, btw2);
		VergleichsFenster vergleichsFenster = new VergleichsFenster(vergleich);
		// TODO logisch?
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
