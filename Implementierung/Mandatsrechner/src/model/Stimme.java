package model;

import java.io.Serializable;

/** 
 * Oberklasse von der Erst- oder Zweitstimme-Klasse
 */
public abstract class Stimme implements Serializable {
	
	/** Automatisch generierte serialVersionUID die f�r das De-/Serialisieren verwendet wird. */
	private static final long serialVersionUID = -4835288729845295736L;
	
	/** Die Anzahl der Stimmen. */
	protected int anzahl;

	/** Das zugeh�rige Gebiet. */
	private Gebiet gebiet;
	
	/**
	 * Gibt das zugeh�rige Gebiet zur�ck
	 * @return das zugeh�rige Gebiet
	 */
	public Gebiet getGebiet() {
		return this.gebiet;
	}
	
	/**
	 * Setze das Gebiet
	 * @param gebiet Das zugeh�rige Gebiet.
	 * @throws IllegalArgumentException wenn das �bergebende Gebiet leer ist
	 */
	public void setGebiet(Gebiet gebiet) throws IllegalArgumentException {
		if (gebiet.equals(null)) {
		      throw new IllegalArgumentException("Gebiet ist null!");
		}
		this.gebiet = gebiet;
	}
	
	/**
	 * Gibt die Anzahl der Stimmen zur�ck
	 * @return Die Anzahl der Stimmen
	 */
	public int getAnzahl() {
		return anzahl;
	}
	
	/**
	 * Setzt die Anzahl der Stimmmen in der Klasse
	 * @param anzahl ist die Anzahl der Stimmen die die Klasse h�lt
	 * @throws IllegalArgumentException Wenn die neue Anzahl eine �berschreitung
	 * der Anzahl der Wahlberechtigten in dem Gebiet zur Folge hat. Oder die neue Anzahl negativ ist.
	 */
	public abstract void setAnzahl(int anzahl) throws IllegalArgumentException;
	
	/**
	 * Erh�ht das anzahl Attribut um die gegebene Anzahl.
	 * @param anzahl die gegebene Anzahl um die erh�ht werden soll.
	 * @throws IllegalArgumentException Wenn die neue Anzahl eine �berschreitung
	 * der Anzahl der Wahlberechtigten in dem Gebiet zur Folge hat. Oder die neue Anzahl negativ ist.
	 */
	public abstract void erhoeheAnzahl(int anzahl) throws IllegalArgumentException;
	
}
