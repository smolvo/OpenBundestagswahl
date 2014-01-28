package main.java.gui;

import java.util.List;

import main.java.model.Stimme;

/**
 * Diese Klasse h�lt alle Dateien die eine Partei betreffen, um diese im
 * Tabellenfenster anzeigen zu lassen.
 * 
 */
public class GUIPartei {

	/** repr�sentiert die Sitze der Partei */
	private final int sitze;

	/** repr�sentiert die Direktmandaten der Partei */
	private final int direktmandate;

	/** repr�sentiert die �berhangsmandaten der Partei */
	private final int ueberhangsmandate;

	/** repr�sentiert die Ausgleichsmandate der Partei */
	private final int ausgleichsmandate;

	/**
	 * Konstruktor
	 * 
	 * @param sitze
	 *            Anzahl Sitze
	 * @param direktmandate
	 *            Anzahl Direktmandate
	 * @param ueberhangsmandate
	 *            Anzahl �berhangsmandate
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
	 * Gibt die Sitzanzahl aus.
	 * 
	 * @return Sitzanzahl
	 */
	public int getSitze() {
		return sitze;
	}

	/**
	 * Gibt die Direktmandatanzahl aus.
	 * 
	 * @return Direktmandatanzahl
	 */
	public int getDirektmandate() {
		return direktmandate;
	}

	/**
	 * Gibt die �berhangsmandatanzahl aus.
	 * 
	 * @return �berhangsmandatanzahl
	 */
	public int getUeberhangsmandate() {
		return ueberhangsmandate;
	}

	/**
	 * Gibt die Ausgleichsmandatanzahl aus.
	 * 
	 * @return Ausgleichsmandatanzahl
	 */
	public int getAusgleichsmandate() {
		return ausgleichsmandate;
	}
}
