package main.java.model;

import java.io.Serializable;

/**
 * Abstrakte Oberklasse die den Namen und die Anzahl der Wahlberechtigten eines
 * Gebiets haltet.
 */
public abstract class Gebiet implements Serializable {

	/**
	 * Automatisch generierte serialVersionUID die fuer das De-/Serialisieren
	 * verwendet wird.
	 */
	private static final long serialVersionUID = -5067472345236494574L;

	/** Der Name des Gebiets. */
	private String name;

	/** Zweitstimmenanzahl in ganz Deutschland. */
	protected int zweitstimmeGesamt;

	/**
	 * Gibt die Erststimmenanzahl aller Kandidaten im Gebiet.
	 * 
	 * @return die Erststimmenanzahl aller Kandidaten.
	 */
	public abstract int getAnzahlErststimmen();

	/**
	 * Gibt die anzahl der Zweitstimmen einer bestimmten Partei zurï¿½ck.
	 * 
	 * @param partei
	 *            Die Partei zu der die Stimmen gegeben werden sollen.
	 * @return Die anzahl der Zweitstimmen einer bestimmten Partei.
	 */
	abstract public int getAnzahlErststimmen(Partei partei);

	/**
	 * Gibt die Zweitstimmenanzahl aller Parteien im Gebiet.
	 * 
	 * @return die Zweistimmenanzahl aller Partein.
	 */
	public abstract int getAnzahlZweitstimmen();

	/**
	 * Gibt die anzahl der Zweitstimmen einer bestimmten Partei zurï¿½ck.
	 * 
	 * @param partei
	 *            Die Partei zu der die Stimmen gegeben werden sollen.
	 * @return Die anzahl der Zweitstimmen einer bestimmten Partei.
	 */
	abstract public int getAnzahlZweitstimmen(Partei partei);

	/**
	 * Gibt den Namen des Gebietes zurueck.
	 * 
	 * @return der Name des Gebiets.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gibt die Anzahl der Wahlberechtigten zurueck.
	 * 
	 * @return die Anzahl der Wahlberechtigten.
	 */
	abstract public int getWahlberechtigte();

	/**
	 * Setzt einen Namen fuer das Gebiet.
	 * 
	 * @param name
	 *            der Name des Gebiets.
	 * @throws IllegalArgumentException
	 *             wenn der Name leer ist.
	 */
	public void setName(String name) throws IllegalArgumentException {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException(
					"Der Parameter \"name\" ist null oder ein leerer String!");
		}
		this.name = name;
	}

	/**
	 * Beschreibt dieses Gebiet.
	 * 
	 * @return einen String der dieses Gebiet beschreibt.
	 */
	@Override
	public String toString() {
		return this.name;
	}
}
