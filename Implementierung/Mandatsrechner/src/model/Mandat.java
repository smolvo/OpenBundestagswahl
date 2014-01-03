package model;

/**
 * Enum-Klasse f�r die verschiedenen Arten von Mandaten.
 */
public enum Mandat {
	
	/** Durch Zweitstimmen zugeteiltes Mandat */
	MANDAT("Mandat"),
	
	/** Durch die meisten Erststimmen zugeteiltes Mandat */
	DIREKMANDAT("Direktmadat"), 
	
	/** Mandat mit mehr Direkmandaten als durch Zweitstimmen zugeteilte Mandate */
	UEBERHANGMADAT("�berhangmandat"),
	
	/** Durch �berhangmandate zugeteilte Mandate zum Ausgleich */
	AUSGLEICHSMANDAT("Ausgleichsmandat"),
	
	/** Kein Mandat */
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
	 * Gibt die Bezeichnung des Mandats zur�ck
	 * @return die Bezeichnung des Mandats
	 */
	public String toString(){
		return this.mandat;
	}
	
	/**
	 * Vergleicht das Mandat mit einem gegebenem Mandat.
	 * @param mand gegebenes Mandat
	 * @return wahr oder falsch
	 */
	public boolean equals(String mand) {
		if (this.mandat.equals(mand)) {
			return true;
		} else {
			return false;
		}
	}
}
