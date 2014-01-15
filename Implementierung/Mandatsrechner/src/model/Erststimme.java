package model;

import java.io.Serializable;

/**
 * Assoziationsklasse die Anzahl aller Erststimmen beinhaltet und mit den Klassen 
 * Gebiet und Kandidat zusammen arbeitet.
 * Au�erdem erbt diese von der Klasse Stimme.
 */
public class Erststimme extends Stimme implements Serializable {

	/** Automatisch generierte serialVersionUID die f�r das De-/Serialisieren verwendet wird. */
	private static final long serialVersionUID = -950583325552486249L;

	/** Das zugeh�rige Gebiet. */
	private Gebiet gebiet;
	
	/** Der zugeh�rige Kanditat. */
	private Kandidat kandidat;
	
	
	/**
	 * Mit diesem Konsruktor ist es m�glich alle Attribute auf einmal zu setzen
	 * @param anzahl Die Anzahl der Stimmen
	 * @param gebiet Das zugeh�rige Gebiet.
	 * @param kandidat Der zugeh�rige Kanditat.
	 */
	public Erststimme(int anzahl, Gebiet gebiet, Kandidat kandidat) {
		this.setAnzahl(anzahl);
		this.setKandidat(kandidat);
		this.setGebiet(gebiet);
	}
	
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
	 * Gibt den verbundenen Kandidaten zur�ck
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
