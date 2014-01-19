package model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Abstrakte Oberklasse die den Namen und die Anzahl der Wahlberechtigten eines Gebiets haltet.
 */
public abstract class Gebiet implements Serializable {
	
	/** Automatisch generierte serialVersionUID die für das De-/Serialisieren verwendet wird. */
	private static final long serialVersionUID = -5067472345236494574L;
	
	/** Der Name des Gebiets. */
	private String name;

	/**Zweitstimmenanzahl in ganz Deutschland*/
	protected int zweitstimmeGesamt;
	

	/**
	 * Gibt die Zweitstimmenanzahl aller Parteien im Gebiet
	 * @return die Zweistimmenanzahl aller PArtein
	 */
	public int getZweitstimmeGesamt() {
		zweitstimmeGesamt = 0;
		for(Zweitstimme zweit : this.getZweitstimmen()){
			zweitstimmeGesamt += zweit.getAnzahl();
		}
		return zweitstimmeGesamt;
	}
	
	/**
	 * Gibt die Erststimmenanzahl aller Kandidaten im Gebiet
	 * @return die Erststimmenanzahl aller Kandidaten
	 */
	public int getErststimmeGesamt() {
		int erststimmeGesamt = 0;
		for(Erststimme erst : this.getErststimmen()){
			erststimmeGesamt += erst.getAnzahl();
		}
		return erststimmeGesamt;
	}
	
	/**
	 * Gibt den Namen des Gebietes zurück
	 * @return der Name des Gebiets
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Setzt einen Namen für das Gebiet
	 * @param name der Name des Gebiets
	 * @throws IllegalArgumentException wenn der Name leer ist
	 */
	public void setName(String name) throws IllegalArgumentException {
		if (name.equals(null) || name.equals("")) {
		      throw new IllegalArgumentException("Wahlkreis ist leer!");
		}
		this.name = name;
	}
	
	/**
	 * Gibt die Anzahl der Wahlberechtigten zurück
	 * @return die Anzahl der Wahlberechtigten
	 */
	abstract public int getWahlberechtigte();
	
	/**
	 * Setzt die Anzahl der Wahlberechtigten
	 * @param wahlberechtigte die Anzahl der Wahlberechtigten
	 * @throws IllegalArgumentException wenn die Anzahl negativ ist
	 */
	abstract public void setWahlberechtigte(int wahlberechtigte) throws IllegalArgumentException;
	
	/**
	 * Setzt die Liste aller Zweitstimmenobjekte für dieses Gebiet (pro Partei eins).
	 * @param zweitstimmen eine Liste aller Zweitstimmenobjekte für dieses Gebiet (pro Partei eins).
	 * @throws IllegalArgumentException wenn die zweitstimmenliste null oder leer ist.
	 */
	abstract public void setZweitstimmen(LinkedList<Zweitstimme> zweitstimmen) throws IllegalArgumentException;
	
	/**
	 * Gibt die Erststimmen in einem Gebiet zurueck.
	 * Falls das aktuelle Gebiet ein "Bundesland"-Objekt ist,
	 * wird die Summe aller Erststimmen der Wahlkreise zurueck-
	 * gegeben.
	 * @return die Erststimmen in einem Gebiet
	 */
	abstract public List<Erststimme> getErststimmen();
	
	/**
	 * Gibt die Zweitstimmen in einem Gebiet zurueck.
	 * @return die Zweitstimmen in einem Gebiet
	 */
	abstract public List<Zweitstimme> getZweitstimmen();
	
	/**
	 * Beschreibt dieses Gebiet.
	 * @return einen String der dieses Gebiet beschreibt.
	 */
	public String toString() {
		return this.name;
	}
	
	/**
	 * Gibt die anzahl der Zweitstimmen einer bestimmten
	 * Partei zurück.
	 * @param partei Die gesuchte Partei.
	 * @return die Anzahl der Stimmen.
	 */
	abstract public int getZweitstimmenAnzahl(Partei partei);
}
