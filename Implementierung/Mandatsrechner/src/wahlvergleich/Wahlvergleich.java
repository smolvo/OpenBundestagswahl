package wahlvergleich;

import java.util.LinkedList;

import model.Bundestagswahl;

/**
 * Diese Klasse implementiert den Vergleich zwischen zwei
 * Bundestagswahlen.
 * @author Anton
 *
 */
public class Wahlvergleich {

	/** repräsentiert alle Differenzen aller Parteien */
	private LinkedList<Parteidifferenz> differenzen;
	
	/**
	 * Der Konstruktor initialisiert die Differenzen.
	 */
	public Wahlvergleich() {
		this.differenzen = new LinkedList<Parteidifferenz>();
	}
	
	/**
	 * Diese Methode vergleich zwei Bundestagswahlen.
	 * @param btw1 erste Bundestagswahl
	 * @param btw2 zweite Bundestagswahl
	 * @return alle Daten, die in der Tabelle stehen sollen
	 */
	public WahlvergleichDaten wahlvergleich(Bundestagswahl btw1, Bundestagswahl btw2) {
		WahlvergleichDaten daten = new WahlvergleichDaten();
		
		return daten;
	}
}
