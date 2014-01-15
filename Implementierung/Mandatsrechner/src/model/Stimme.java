package model;

import java.io.Serializable;

/** 
 * Oberklasse von der Erst- oder Zweitstimme-Klasse
 */
public abstract class Stimme implements Serializable {
	
	/** Automatisch generierte serialVersionUID die für das De-/Serialisieren verwendet wird. */
	private static final long serialVersionUID = -4835288729845295736L;
	
	/** Die Anzahl der Stimmen. */
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
		if (anzahl < 0) {
		      throw new IllegalArgumentException("Stimme hat eine negative Anzahl");
		}
		this.anzahl = anzahl;
	}
}
