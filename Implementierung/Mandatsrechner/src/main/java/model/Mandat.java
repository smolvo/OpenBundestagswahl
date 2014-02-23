package main.java.model;

/**
 * Enum-Klasse fuer die verschiedenen Arten von Mandaten.
 */
public enum Mandat {

	/** Durch Zweitstimmen zugeteiltes Mandat. */
	LISTENMANDAT("Listenmandat"),

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
	 * @throws IllegalArgumentException
	 *             wenn die Bezeichnung leer ist.
	 */
	Mandat(String mandat) {
		if (mandat == null) {
			throw new IllegalArgumentException("Bezeichnung ist leer");
		}
		this.mandat = mandat;
	}

	/**
	 * Gibt die Bezeichnung des Mandats zurueck.
	 * 
	 * @return die Bezeichnung des Mandats.
	 */
	@Override
	public String toString() {
		return this.mandat;
	}
}
