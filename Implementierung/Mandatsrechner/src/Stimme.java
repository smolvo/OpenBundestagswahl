
/**
 * @author Nick
 * 
 * Diese Klasse ist die Oberklasse von der Erst- oder Zweitstimme-Klasse
 *
 */

public abstract class Stimme {
	/**
	 * Die Anzahl der Stimme wird in einer Integer-Variable gespeichert
	 */
	private int anzahl;

	/**
	 * Gibt die Anzahl der Stimmen zur�ck
	 * @return Die Anzahl der Stimmen
	 */
	public int getAnzahl() {
		return anzahl;
	}
	/**
	 * Setzt die Anzahl der Stimmmen in der Klasse
	 * @param anzahl ist die Anzahl der Stimmen die die Klasse h�lt
	 * @exception IllegalArgumentException if a param does not comply.
	 */
	public void setAnzahl(int anzahl) {
		if (anzahl > 0) {
		      throw new IllegalArgumentException("Stimme hat eine negative Anzahl");
		}
		this.anzahl = anzahl;
	}
}
