package model;

/**
 * Enum-Klasse für die verschiedenen Arten von Mandaten.
 */
public enum Mandat {
	/**
	 * Durch Zweitstimmen zugeteiltes Mandat
	 */
	MANDAT("Mandat"),
	/**
	 * Durch die meisten Erststimmen zugeteiltes Mandat
	 */
	DIREKMANDAT("Direktmadat"), 
	/**
	 * Mandat mit mehr Direkmandate als durch Zweitstimmen zugeteilte Mandate
	 */
	UEBERHANGMADAT("Überhangmandat"),
	/**
	 * Durch Überhangmandate zugeteilte Mandate zum Ausgleich
	 */
	AUSGLEICHSMANDAT("Ausgleichsmandat"),
	/**
	 * Kein Mandat
	 */
	KEINMANDAT("Kein Mandat");
	
	/**
	 * Beinhaltet die Bezeichnung des Mandats
	 */
	private String mandat;
	
	/**
	 * Kontsruktor der die Bezeichnung setzt
	 * @param mandat die Bezeichnung des Mandats
	 */
	Mandat (String mandat){
		this.mandat = mandat;
	}
	
	/**
	 * Gibt die Bezeichnung des Mandats zurück
	 * @return die Bezeichnung des Mandats
	 */
	public String toString(){
		return this.mandat;
	}
}
