package model;

import java.io.Serializable;

/**
 * Assoziationsklasse die Anzahl aller Erststimmen beinhaltet und mit den Klassen 
 * Gebiet und Kandidat zusammen arbeitet.
 * Außerdem erbt diese von der Klasse Stimme.
 */
public class Erststimme extends Stimme implements Serializable {

	/** Automatisch generierte serialVersionUID die für das De-/Serialisieren verwendet wird. */
	private static final long serialVersionUID = -950583325552486249L;

	/** Das zugehörige Gebiet. */
	private Gebiet gebiet;
	
	/** Der zugehörige Kanditat. */
	private Kandidat kandidat;
	
	
	/**
	 * Mit diesem Konsruktor ist es möglich alle Attribute auf einmal zu setzen
	 * @param anzahl Die Anzahl der Stimmen
	 * @param gebiet Das zugehörige Gebiet.
	 * @param kandidat Der zugehörige Kanditat.
	 */
	public Erststimme(int anzahl, Gebiet gebiet, Kandidat kandidat) {
		this.setAnzahl(anzahl);
		this.setKandidat(kandidat);
		this.setGebiet(gebiet);
	}
	
	/**
	 * Gibt das zugehörige Gebiet zurück
	 * @return das zugehörige Gebiet
	 */
	public Gebiet getGebiet() {
		return this.gebiet;
	}
	
	/**
	 * Setze das Gebiet
	 * @param gebiet Das zugehörige Gebiet.
	 * @throws IllegalArgumentException wenn das übergebende Gebiet leer ist
	 */
	public void setGebiet(Gebiet gebiet) throws IllegalArgumentException {
		if (gebiet.equals(null)) {
		      throw new IllegalArgumentException("Gebiet ist null!");
		}
		this.gebiet = gebiet;
	}
	
	/**
	 * Gibt den verbundenen Kandidaten zurück
	 * @return der verbundene Kandidat
	 */
	public Kandidat getKandidat() {
		return kandidat;
	}
	
	/**
	 * Setze den Kandidaten
	 * @param kandidat der neue Kanditat
	 * @throws IllegalArgumentException wenn der Kandidat leer ist
	 */
	public void setKandidat(Kandidat kandidat) throws IllegalArgumentException {
		if (kandidat.equals(null)) {
		      throw new IllegalArgumentException("Kandidat ist null!");
		}
		this.kandidat = kandidat;
	}
 
}
