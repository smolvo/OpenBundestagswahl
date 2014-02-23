package main.java.model;

import java.io.Serializable;
import java.util.List;

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
	 * Gibt die Zweitstimmenanzahl aller Parteien im Gebiet.
	 * 
	 * @return die Zweistimmenanzahl aller Partein.
	 */
	public int getAnzahlZweitstimmen() {
		zweitstimmeGesamt = 0;
		for (Zweitstimme zweit : this.getZweitstimmenProPartei()) {
			zweitstimmeGesamt += zweit.getAnzahl();
		}
		return zweitstimmeGesamt;
	}

	/**
	 * Gibt die Erststimmenanzahl aller Kandidaten im Gebiet.
	 * 
	 * @return die Erststimmenanzahl aller Kandidaten.
	 */
	public int getAnzahlErststimmen() {
		int erststimmeGesamt = 0;
		for (Erststimme erst : this.getErststimmenProPartei()) {
			erststimmeGesamt += erst.getAnzahl();
		}
		return erststimmeGesamt;
	}

	/**
	 * Gibt den Namen des Gebietes zurueck.
	 * 
	 * @return der Name des Gebiets.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Setzt einen Namen fuer das Gebiet.
	 * 
	 * @param name
	 *            der Name des Gebiets.
	 * @throws IllegalArgumentException
	 *             wenn der Name leer ist.
	 */
	public void setName(String name) throws IllegalArgumentException {
		if (name.equals(null) || name.equals("")) {
			throw new IllegalArgumentException("Wahlkreis ist leer!");
		}
		this.name = name;
	}

	/**
	 * Gibt die Anzahl der Wahlberechtigten zurueck.
	 * 
	 * @return die Anzahl der Wahlberechtigten.
	 */
	abstract public int getWahlberechtigte();

	/**
	 * Gibt Pro Partei ein Erststimmen-Objekt als Liste zurï¿½ck. Jedes Objekt hat
	 * als Anzahl die Summe aller Stimmen dem jeweiligen Gebiet bzw. in den
	 * Untergebieten.
	 * 
	 * @return die Erststimmen in einem Gebiet.
	 */
	abstract public List<Erststimme> getErststimmenProPartei();

	/**
	 * Gibt Pro Partei ein Zweitstimmen-Objekt als Liste zurï¿½ck. Jedes Objekt
	 * hat als Anzahl die Summe aller Stimmen dem jeweiligen Gebiet bzw. in den
	 * Untergebieten.
	 * 
	 * @return die Zweitstimmen in einem Gebiet.
	 */
	abstract public List<Zweitstimme> getZweitstimmenProPartei();

	/**
	 * Gibt die anzahl der Zweitstimmen einer bestimmten Partei zurï¿½ck.
	 * 
	 * @param partei
	 *            Die Partei zu der die Stimmen gegeben werden sollen.
	 * @return Die anzahl der Zweitstimmen einer bestimmten Partei.
	 */
	abstract public int getAnzahlZweitstimmen(Partei partei);

	/**
	 * Gibt die anzahl der Zweitstimmen einer bestimmten Partei zurï¿½ck.
	 * 
	 * @param partei
	 *            Die Partei zu der die Stimmen gegeben werden sollen.
	 * @return Die anzahl der Zweitstimmen einer bestimmten Partei.
	 */
	abstract public int getAnzahlErststimmen(Partei partei);

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
