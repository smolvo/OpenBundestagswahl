package model;

import java.util.LinkedList;

/**
 * Klasse die mit der Deutschland-Klasse, der Kandidat-Klasse und
 * der Erststimmen-Klasse zusammenarbeitet. Sie erbt von der Oberklasse Gebiet
 */
public class Wahlkreis extends Gebiet{
	
	/** Kandidat mit den meisten Erststimmen */
	private Kandidat wahlkreisSieger;
	
	/** Erststimmen-Objekt */
	private LinkedList<Erststimme> erststimmen;
	
	
	/**
	 * Parametrisierter Konstruktor zum erzeugen von Wahlkreisen.
	 * @param name
	 * @param wahlberechtigte
	 */
	public Wahlkreis(String name, int wahlberechtigte){
		this.setName(name);
		this.setWahlberechtigte(wahlberechtigte);
	}
	
	/**
	 * Parametrisierter Konstruktor zum erzeugen von Wahlkreisen.
	 * @param name
	 * @param wahlberechtigte
	 * @param erststimmen
	 */
	public Wahlkreis(String name, int wahlberechtigte, LinkedList<Erststimme> erststimmen){
		this.setName(name);
		this.setWahlberechtigte(wahlberechtigte);
		this.setErststimmen(erststimmen);
	}
	
	/**
	 * Gibt den Kandidaten mit den meisten Erststimmen zurück
	 * @return Kandidat mit den meisten Erststimmen
	 */
	public Kandidat getWahlkreisSieger() {
		return wahlkreisSieger;
	}
	
	/**
	 * Setzt den Wahlkreissieger
	 * @param wahlkreisSieger
	 * @exception wenn der Wahlkreissieger leer ist
	 */
	public void setWahlkreisSieger(Kandidat wahlkreisSieger) {
		if(wahlkreisSieger == null){
			throw new IllegalArgumentException("Wahlkreissieger ist leer!");
		}
		this.wahlkreisSieger = wahlkreisSieger;
	}
	
	/**
	 * Gibt das Erststimme-Objekt zurück
	 * @return alle Erststimmen des Wahlkreises
	 */
	public LinkedList<Erststimme> getErststimmen() {
		return erststimmen;
	}

	/**
	 * Setzt das Erstimme-Objekt
	 * @param erststimme das Objekt
	 * @exception wenn das Erstimme-Objekt leer ist
	 */
	public void setErststimmen(LinkedList<Erststimme> erststimmen) {
		if (erststimmen == null) {
		      throw new IllegalArgumentException("Erststimme ist leer!");
		}
		this.erststimmen = erststimmen;
	}
}
