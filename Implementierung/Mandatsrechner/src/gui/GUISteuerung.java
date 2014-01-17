package gui;

import model.Bundestagswahl;
import model.Gebiet;

/**
 * Diese Klasse repräsentiert die GUI Steuerung.
 * Diese ist die Verbindung zwischen GUI und der
 * Hauptsteuerung.
 * @author Anton
 *
 */
public class GUISteuerung {
	
	/** repräsentiert die aktuell angezeigte Bundestagswahl der GUI */
	private final Bundestagswahl btw;
	
	/** rerpäsentiert das Wahlfenster in dem die BTW angezeigt wird */
	private final WahlFenster wahlfenster;

	/**
	 * Der Konstruktor initialisiert eine neue GUI Steuerung.
	 * @param btw aktuelle Bundestagswahl
	 * @param wahlfenster dazugehöriges Wahlfenster
	 */
	public GUISteuerung(Bundestagswahl btw, WahlFenster wahlfenster) {
		this.btw = btw;
		this.wahlfenster = wahlfenster;
	}

	/**
	 * Diese Methode aktualisiert das Wahlfenster.
	 * @param gebiet Gebiet, welches angezeigt werden soll
	 */
	public void aktualisiereWahlfenster(Gebiet gebiet) {
		this.wahlfenster.wechsleAnsicht(gebiet);
	}
	
	/**
	 * Gibt die aktuelle Bundestagswahl aus.
	 * @return Bundestagswahl
	 */
	public Bundestagswahl getBundestagswahl() {
		return btw;
	}
	
	/**
	 * Gibt das Wahlfenster aus.
	 * @return Wahlfenster
	 */
	public WahlFenster getWahlfenster() {
		return wahlfenster;
	}
}
