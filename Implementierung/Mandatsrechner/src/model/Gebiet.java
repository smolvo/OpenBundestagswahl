package model;

import java.util.LinkedList;
import java.util.List;

/**
 * Abstrakte Oberklasse die den Namen und die Anzahl der Wahlberechtigten eines Gebiets haltet.
 */
public abstract class Gebiet {
	
	/** Der Name des Gebiets. */
	private String name;
	
	
	
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
	 * @exception wenn der Name leer ist
	 */
	public void setName(String name) {
		if (name.equals(null) || name.equals("")) {
		      throw new IllegalArgumentException("Wahlkreis ist leer!");
		}
		this.name = name;
	}
	
	public String toString() {
		return this.name;
	}
	
	/**
	 * Gibt die Anzahl der Wahlberechtigten zurück
	 * @return die Anzahl der Wahlberechtigten
	 */
	abstract public int getWahlberechtigte();
	
	/**
	 * Setzt die Anzahl der Wahlberechtigten
	 * @param wahlberechtigte die Anzahl der Wahlberechtigten
	 * @exception wenn die Anzahl negativ ist
	 */
	abstract public void setWahlberechtigte(int wahlberechtigte);

		
	abstract public void setZweitstimmen(LinkedList<Zweitstimme> zweitstimmen);
	
	/**
	 * Gibt die Erststimmen in einem Gebiet zurueck.
	 * Falls das aktuelle Gebiet ein "Bundesland"-Objekt ist,
	 * wird die Summe aller Erststimmen der Wahlkreise zurueck-
	 * gegeben.
	 * @return
	 */
	abstract public List<Erststimme> getErststimmen();
	
	abstract public List<Zweitstimme> getZweitstimmen();
}
