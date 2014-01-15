package model;

/**
 * Assoziationsklasse die Anzahl aller Erststimmen beinhaltet und mit den Klassen 
 * Wahlkreis und Kandidat zusammen arbeitet. Außerdem erbt diese von der Klasse Stimme.
 */
public class Erststimme extends Stimme{

	/** Der verbundene Wahlkreis */
	private Gebiet gebiet;
	
	/** Der verbundende Kanditat */
	private Kandidat kandidat;
	
	/**
	 * Mit dem Konsruktor ist es möglich alle Attribute auf einmal zu setzen
	 * @param anzahl
	 * @param wahlkreis
	 * @param kandidat
	 */
	public Erststimme(int anzahl, Gebiet gebiet, Kandidat kandidat){
		this.setAnzahl(anzahl);
		this.setKandidat(kandidat);
		this.setGebiet(gebiet);
	}
	
	/**
	 * Gibt den verbundenen Wahlkreis zurück
	 * @return der verbundene Wahlkreis 
	 */
	public Gebiet getGebiet() {
		return this.gebiet;
	}
	
	/**
	 * Setze den Wahlkreis
	 * @param wahlkreis der neue Wahlkreis
	 * @exception wenn der übergebende Wahlkreis leer ist
	 */
	public void setGebiet(Gebiet gebiet) {
		if (gebiet.equals(null)) {
		      throw new IllegalArgumentException("Wahlkreis ist leer!");
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
	 * @exception wenn der Kandidat leer ist
	 */
	public void setKandidat(Kandidat kandidat) {
		if (kandidat.equals(null)) {
		      throw new IllegalArgumentException("Kandidat ist leer!");
		}
		this.kandidat = kandidat;
	}
 
}
