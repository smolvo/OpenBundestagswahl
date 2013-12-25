package model;

/** 
 * Oberklasse von der Erst- oder Zweitstimme-Klasse
 * @author Nick
 */

public abstract class Stimme {
	/**
	 * Die Anzahl der Stimme wird in einer Integer-Variable gespeichert
	 */
	private int anzahl;

	/**
	 * Gibt die Anzahl der Stimmen zurück
	 * @return Die Anzahl der Stimmen
	 */
	public int getAnzahl() {
		return anzahl;
	}
	/**
	 * Setzt die Anzahl der Stimmmen in der Klasse
	 * @param anzahl ist die Anzahl der Stimmen die die Klasse hält
	 * @exception IllegalArgumentException wenn die Anzahl negativ ist
	 */
	public void setAnzahl(int anzahl) {
		if (anzahl > 0) {
		      throw new IllegalArgumentException("Stimme hat eine negative Anzahl");
		}
		this.anzahl = anzahl;
	}
}
