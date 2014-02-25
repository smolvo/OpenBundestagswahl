package main.java.model;

import java.io.Serializable;

/**
 * Oberklasse von der Erst- oder Zweitstimme-Klasse.
 */
public abstract class Stimme implements Serializable {

	/**
	 * Automatisch generierte serialVersionUID die fuer das De-/Serialisieren
	 * verwendet wird.
	 */
	private static final long serialVersionUID = -4835288729845295736L;

	/** Die Anzahl der Stimmen. */
	protected int anzahl;

	/** Das zugehoerige Gebiet. */
	protected Gebiet gebiet;

	/**
	 * Gibt das zugehoerige Gebiet zurueck.
	 * 
	 * @return das zugehï¿½rige Gebiet.
	 */
	public Gebiet getGebiet() {
		return this.gebiet;
	}

	/**
	 * Setze das Gebiet.
	 * 
	 * @param gebiet
	 *            Das zugehoerige Gebiet.
	 * @throws IllegalArgumentException
	 *             wenn das ï¿½bergebende Gebiet leer ist.
	 */
	public void setGebiet(Gebiet gebiet) throws IllegalArgumentException {
		if (gebiet == null) {
			throw new IllegalArgumentException("Der Parameter \"gebiet\" ist null!");
		}
		this.gebiet = gebiet;
	}

	/**
	 * Gibt die Anzahl der Stimmen zurueck
	 * 
	 * @return Die Anzahl der Stimmen
	 */
	public int getAnzahl() {
		return this.anzahl;
	}

	/**
	 * Setzt die Anzahl der Stimmmen in der Klasse.
	 * 
	 * @param anzahl
	 *            ist die Anzahl der Stimmen die die Klasse haelt.
	 * @throws IllegalArgumentException
	 *             Wenn die neue Anzahl eine Ueberschreitung der Anzahl der
	 *             Wahlberechtigten in dem Gebiet zur Folge hat. Oder die neue
	 *             Anzahl negativ ist.
	 */
	public abstract void setAnzahl(int anzahl) throws IllegalArgumentException;

	/**
	 * Erhoeht das anzahl Attribut um die gegebene Anzahl.
	 * 
	 * @param anzahl
	 *            die gegebene Anzahl um die erhï¿½ht werden soll.
	 * @throws IllegalArgumentException
	 *             Wenn die neue Anzahl eine Ueberschreitung der Anzahl der
	 *             Wahlberechtigten in dem Gebiet zur Folge hat. Oder die neue
	 *             Anzahl negativ ist.
	 */
	public abstract void erhoeheAnzahl(int anzahl)
			throws IllegalArgumentException;

	/**
	 * Erzeugt eine Deep-Copy einer Stimme.
	 * 
	 * @return ein neues Stimmen-Objekt.
	 */
	public abstract Stimme deepCopy();

}
