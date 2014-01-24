package model;

/**
 * Enum-Klasse fuer die verschiedenen Arten von Mandaten.
 */
public enum Mandat {

	/** Durch Zweitstimmen zugeteiltes Mandat. */
	MANDAT("Mandat"),

	/** Durch die meisten Erststimmen zugeteiltes Mandat. */
	DIREKTMANDAT("Direktmadat"),

	/** Mandat mit mehr Direkmandaten als durch Zweitstimmen zugeteilte Mandate. */
	UEBERHANGMADAT("Ueberhangmandat"),

	/** Durch Ueberhangmandate zugeteilte Mandate zum Ausgleich */
	AUSGLEICHSMANDAT("Ausgleichsmandat"),

	/** Kein Mandat */
	KEINMANDAT("Kein Mandat");

	/**
	 * Beinhaltet die Bezeichnung des Mandats.
	 */
	private String mandat;

	/**
	 * Kontsruktor der die Bezeichnung setzt.
	 * 
	 * @param mandat
	 *            die Bezeichnung des Mandats.
	 */
	Mandat(String mandat) {
		this.mandat = mandat;
	}

	/**
	 * Gibt die Bezeichnung des Mandats zurueck.
	 * 
	 * @return die Bezeichnung des Mandats.
	 */
	public String toString() {
		return this.mandat;
	}
}
