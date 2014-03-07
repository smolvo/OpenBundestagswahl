package main.java.gui;

/**
 * Diese Klasse hält alle Dateien die eine Partei betreffen, um diese im
 * Tabellenfenster anzeigen zu lassen.
 * 
 */
public class GUIPartei {

	/** repräsentiert die Sitze der Partei */
	private final int sitze;

	/** repräsentiert die Direktmandaten der Partei */
	private final int direktmandate;

	/** repräsentiert die Überhangsmandaten der Partei */
	private final int ueberhangsmandate;

	/** repräsentiert die Ausgleichsmandate der Partei */
	private final int ausgleichsmandate;

	/**
	 * Konstruktor
	 * 
	 * @param sitze
	 *            Anzahl Sitze
	 * @param direktmandate
	 *            Anzahl Direktmandate
	 * @param ueberhangsmandate
	 *            Anzahl Überhangsmandate
	 * @param ausgleichsmandate
	 *            Anzahl Ausgleichsmandate
	 */
	public GUIPartei(int sitze, int direktmandate, int ueberhangsmandate,
			int ausgleichsmandate) {
		this.sitze = sitze;
		this.direktmandate = direktmandate;
		this.ueberhangsmandate = ueberhangsmandate;
		this.ausgleichsmandate = ausgleichsmandate;
	}

	/**
	 * Gibt die Ausgleichsmandatanzahl aus.
	 * 
	 * @return Ausgleichsmandatanzahl
	 */
	public int getAusgleichsmandate() {
		return this.ausgleichsmandate;
	}

	/**
	 * Gibt die Direktmandatanzahl aus.
	 * 
	 * @return Direktmandatanzahl
	 */
	public int getDirektmandate() {
		return this.direktmandate;
	}

	/**
	 * Gibt die Sitzanzahl aus.
	 * 
	 * @return Sitzanzahl
	 */
	public int getSitze() {
		return this.sitze;
	}

	/**
	 * Gibt die Überhangsmandatanzahl aus.
	 * 
	 * @return Überhangsmandatanzahl
	 */
	public int getUeberhangsmandate() {
		return this.ueberhangsmandate;
	}
}
