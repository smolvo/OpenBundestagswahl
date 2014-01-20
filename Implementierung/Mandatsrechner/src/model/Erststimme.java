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
	
	/** Der zugeh�rige Kanditat. */
	private Kandidat kandidat;
	
	
	/**
	 * Mit diesem Konsruktor ist es m�glich alle Attribute auf einmal zu setzen
	 * @param anzahl Die Anzahl der Stimmen
	 * @param gebiet Das zugeh�rige Gebiet.
	 * @param kandidat Der zugeh�rige Kanditat.
	 */
	public Erststimme(int anzahl, Gebiet gebiet, Kandidat kandidat) {
		this.setGebiet(gebiet);
		this.setKandidat(kandidat);
		this.setAnzahl(anzahl);
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
	
	@Override
	public void setAnzahl(int anzahl) throws IllegalArgumentException {
		if (anzahl < 0) {
			throw new IllegalArgumentException("Anzahl ist negativ!");
		}
		/*
		if (this.getGebiet().getWahlberechtigte() < 
				(this.getGebiet().getErststimmeGesamt() - this.getAnzahl() + anzahl)) {
			throw new IllegalArgumentException("Anzahl der Erststimmen > Anzahl der Wahlberechtigten!");
		}
		*/
		this.anzahl = anzahl;
	}

	@Override
	public void erhoeheAnzahl(int anzahl) throws IllegalArgumentException {
		if (anzahl < 0) {
			throw new IllegalArgumentException("Anzahl ist negativ!");
		}
		if (this.getGebiet().getWahlberechtigte() < 
				(this.getGebiet().getErststimmeGesamt() + anzahl)) {
			throw new IllegalArgumentException("Anzahl der Erststimmen > Anzahl der Wahlberechtigten!");
		}
		this.anzahl += anzahl;
	}
 
}
