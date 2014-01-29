package main.java.wahlvergleich;

import main.java.model.Partei;

/**
 * Diese Klasse realisiert die Sitzdifferenz einer Partei
 * von einer Wahl zur anderen.
 *
 */
public class ParteiDifferenzen {

	/** repräsentiert die Partei */
	private final Partei partei;
	
	/** repräsentiert die Differenz */
	private final int diff;
	
	
	/**
	 * Der Konstruktor erstellt eine neue Differenz.
	 * @param partei Partei
	 * @param diff Differenz
	 */
	public ParteiDifferenzen(Partei partei, int diff) {
		this.partei = partei;
		this.diff = diff;
	}

	/**
	 * Gibt die Partei aus.
	 * @return Partei
	 */
	public Partei getPartei() {
		return partei;
	}

	/**
	 * Gibt die Differenz aus.
	 * @return Differenz
	 */
	public int getDiff() {
		return diff;
	}
}