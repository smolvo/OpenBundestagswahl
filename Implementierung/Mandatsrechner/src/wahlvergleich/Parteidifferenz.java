package wahlvergleich;

import model.Partei;

/**
 * Diese Klasse hält die Differenzen zwischen zwei Wahlen
 * einer Partei.
 * @author Anton
 *
 */
public class Parteidifferenz {

	/** repräsentiert die Partei */
	private final Partei partei;
	
	/** repräsentiert die Erststimmendifferenz */
	private final int erststimmenDifferenz;
	
	/** repräsentiert de Zweitstimmendifferenz */
	private final int zweitstimmenDifferenz;
	
	/** repräsentiert die Sitzdifferenz einer Partei */
	private final int sitzDifferenz;

	/**
	 * Der Konstruktor initialisiert ein Parteidifferenz-Objekt.
	 * @param partei die Partei
	 * @param erststimmenDifferenz die Erststimmendifferenz
	 * @param zweitstimmenDifferenz die Zweitstimmendifferenz
	 * @param sitzDifferenz die Sitzdifferenz
	 */
	public Parteidifferenz(Partei partei, int erststimmenDifferenz,
			int zweitstimmenDifferenz, int sitzDifferenz) {
		this.partei = partei;
		this.erststimmenDifferenz = erststimmenDifferenz;
		this.zweitstimmenDifferenz = zweitstimmenDifferenz;
		this.sitzDifferenz = sitzDifferenz;
	}
	
	/**
	 * Gibt die Partei aus.
	 * @return Partei
	 */
	public Partei getPartei() {
		return partei;
	}

	/**
	 * Gibt die Erststimmendifferenz aus.
	 * @return Erststimmendifferenz
	 */
	public int getErststimmenDifferenz() {
		return erststimmenDifferenz;
	}
	
	/**
	 * Gibt die Zweitstimmendifferenz aus.
	 * @return Zweitstimmendifferenz
	 */
	public int getZweitstimmenDifferenz() {
		return zweitstimmenDifferenz;
	}
	
	/**
	 * Gibt die Sitzdifferenz aus.
	 * @return Sitzdifferenz
	 */
	public int getSitzDifferenz() {
		return sitzDifferenz;
	}
}
