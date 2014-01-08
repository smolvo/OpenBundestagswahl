package model;

import java.util.LinkedList;

/**
 * Abstrakte Oberklasse die den Namen und die Anzahl der Wahlberechtigten eines Gebiets haltet.
 */
public abstract class Gebiet {
	
	/** Der Name des Gebiets. */
	private String name;
	
	/** Die Anzahl der Wahlberechtigten */
	private int wahlberechtigte;
	
	private LinkedList<Zweitstimme> zweitstimmen;
	
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
	/**
	 * Gibt die Anzahl der Wahlberechtigten zurück
	 * @return die Anzahl der Wahlberechtigten
	 */
	public int getWahlberechtigte() {
		return wahlberechtigte;
	}
	
	/**
	 * Setzt die Anzahl der Wahlberechtigten
	 * @param wahlberechtigte die Anzahl der Wahlberechtigten
	 * @exception wenn die Anzahl negativ ist
	 */
	public void setWahlberechtigte(int wahlberechtigte) {
		if (wahlberechtigte < 0) {
		      throw new IllegalArgumentException("Anzahl der Wahlberechtige ist negativ!");
		}
		this.wahlberechtigte = wahlberechtigte;
	}
	public LinkedList<Zweitstimme> getZweitstimmen() {
		return zweitstimmen;
	}
	public void setZweitstimmen(LinkedList<Zweitstimme> zweitstimmen) {
		this.zweitstimmen = zweitstimmen;
	}
}
