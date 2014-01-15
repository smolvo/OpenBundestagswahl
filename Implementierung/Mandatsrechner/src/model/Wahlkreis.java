package model;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Klasse die mit der Deutschland-Klasse, der Kandidat-Klasse und
 * der Erststimmen-Klasse zusammenarbeitet. Sie erbt von der Oberklasse Gebiet
 */
public class Wahlkreis extends Gebiet implements Serializable {
	
	/** Automatisch generierte serialVersionUID die für das De-/Serialisieren verwendet wird. */
	private static final long serialVersionUID = 8492979454628956125L;

	/** Die Anzahl der Wahlberechtigten */
	private int wahlberechtigte;
	
	/** Offiziell zugeordnete Nummer */
	private int wahlkreisnummer;
	
	/** Kandidat mit den meisten Erststimmen */
	private Kandidat wahlkreisSieger;
	
	/** Erststimmen-Objekt */
	private LinkedList<Erststimme> erststimmen;
	
	/** Zweitstimmen-Objekt */
	private LinkedList<Zweitstimme> zweitstimmen;
	
	/**
	 * Parametrisierter Konstruktor zum erzeugen von Wahlkreisen.
	 * @param name
	 * @param wahlberechtigte
	 */
	public Wahlkreis(String name, int wahlberechtigte) {
		this.setName(name);
		this.setWahlberechtigte(wahlberechtigte);
	}
	
	/**
	 * Parametrisierter Konstruktor zum erzeugen von Wahlkreisen.
	 * @param name
	 * @param wahlberechtigte
	 * @param erststimmen
	 */
	public Wahlkreis(String name, int wahlberechtigte, LinkedList<Erststimme> erststimmen) {
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
	 * @param wahlkreisSieger der Kandidat mit den meisten Stimmen
	 * @exception wenn der Wahlkreissieger leer ist
	 */
	public void setWahlkreisSieger(Kandidat wahlkreisSieger) {
		if (wahlkreisSieger == null) {
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
	 * Gibt das Zweitstimme-Objekt zurück
	 * @return alle Zweitstimmen des Wahlkreises
	 */
	public LinkedList<Zweitstimme> getZweitstimmen() {
		return zweitstimmen;
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
	
	public void setZweitstimmen(LinkedList<Zweitstimme> zweitstimmen) {
		this.zweitstimmen = zweitstimmen;
	}

	@Override
	public int getWahlberechtigte() {
		// TODO Auto-generated method stub
		return this.wahlberechtigte;
	}
	
	public void setWahlberechtigte(int wahlberechtigte) {
		if (wahlberechtigte < 0) {
		      throw new IllegalArgumentException("Anzahl der Wahlberechtige ist negativ!");
		}
		this.wahlberechtigte = wahlberechtigte;
	}
	
	public int getWahlkreisnummer() {
		return wahlkreisnummer;
	}

	public void setWahlkreisnummer(int wahlkreisnummer) {
		this.wahlkreisnummer = wahlkreisnummer;
	}
}
