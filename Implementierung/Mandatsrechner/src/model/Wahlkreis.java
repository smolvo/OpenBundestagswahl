package model;

/**
 * Klasse die mit der Deutschland-Klasse, der Kandidat-Klasse und
 * der Erststimmen-Klasse zusammenarbeitet. Sie erbt von der Oberklasse Gebiet
 */
public class Wahlkreis extends Gebiet{
	
	/** Kandidat mit den meisten Erststimmen */
	private Kandidat wahlkreisSieger;
	
	/** Erststimmen-Objekt */
	private Erststimme erststimme;
	
	
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
	 * @param erststimme
	 */
	public Wahlkreis(String name, int wahlberechtigte, Erststimme erststimme){
		this.setName(name);
		this.setWahlberechtigte(wahlberechtigte);
		this.setErststimme(erststimme);
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
	 * @return das Erststimme-Objekt
	 */
	public Erststimme getErststimme() {
		return erststimme;
	}

	/**
	 * Setzt das Erstimme-Objekt
	 * @param erststimme das Objekt
	 * @exception wenn das Erstimme-Objekt leer ist
	 */
	public void setErststimme(Erststimme erststimme) {
		if (erststimme == null) {
		      throw new IllegalArgumentException("Erststimme ist leer!");
		}
		this.erststimme = erststimme;
	}
}
